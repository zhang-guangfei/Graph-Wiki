import json
import importlib.util

import pytest
from pathlib import Path


def _load_gate_module():
    path = Path(__file__).resolve().parents[1] / "scripts" / "release_quality_gate.py"
    spec = importlib.util.spec_from_file_location("release_quality_gate", path)
    module = importlib.util.module_from_spec(spec)
    assert spec.loader is not None
    spec.loader.exec_module(module)
    return module


def _write_minimal_artifacts(output_dir: Path, *, product_quality: dict | None) -> None:
    output_dir.mkdir(parents=True)
    evidence = {"api:POST:/orders": {"id": "api:POST:/orders", "type": "api", "status": "ready"}}
    model_quality = product_quality or {
        "deepReadingStatus": "warning",
        "coreDomainEvidenceStatus": "passed",
        "ruleCorrectnessRisk": "medium",
    }
    model = {
        "schema": {"version": "domain-read-model-v1"},
        "quality": model_quality,
        "evidenceIndex": evidence,
        "domains": [{
            "domainKey": "order",
            "core": True,
            "flows": [{"flowId": "order.create", "evidenceRefs": ["api:POST:/orders"]}],
            "rules": [{"ruleId": "order.rule", "evidenceRefs": ["api:POST:/orders"]}],
            "fieldRules": [{"fieldRuleId": "order.field", "evidenceRefs": ["api:POST:/orders"]}],
        }],
    }
    report = {
        "build": {"status": "passed"},
        "quality": {
            "status": "warning",
            "phase3": {"acceptance": {"status": "warning"}},
            "phase4": {"acceptance": {"status": "passed"}},
            "phase5": {"dream_cycle_status": "not_run"},
        },
    }
    if product_quality is not None:
        report["productQuality"] = product_quality
    workbench = {"schema": {"version": "graph-wiki-workbench-v1", "source": "domain-read-model.json"}}
    (output_dir / "domain-read-model.json").write_text(json.dumps(model), encoding="utf-8")
    (output_dir / "build-report.json").write_text(json.dumps(report), encoding="utf-8")
    (output_dir / "workbench-data.json").write_text(json.dumps(workbench), encoding="utf-8")


def test_release_gate_accepts_build_pass_with_separate_product_quality_warning(tmp_path: Path):
    gate = _load_gate_module()
    output_dir = tmp_path / "fixture"
    _write_minimal_artifacts(output_dir, product_quality={
        "deepReadingStatus": "warning",
        "coreDomainEvidenceStatus": "passed",
        "ruleCorrectnessRisk": "medium",
    })

    result = gate.validate_build_artifacts(output_dir, require_field_rules=True)

    assert result["status"] == "passed"
    assert result["buildStatus"] == "passed"
    assert result["productQuality"]["deepReadingStatus"] == "warning"
    assert result["phaseStatuses"]["phase3"] == "warning"


def test_release_gate_rejects_build_pass_without_product_quality(tmp_path: Path):
    gate = _load_gate_module()
    output_dir = tmp_path / "fixture"
    _write_minimal_artifacts(output_dir, product_quality=None)

    result = gate.validate_build_artifacts(output_dir)

    assert result["status"] == "failed"
    assert "build.status alone is not a release signal" in "\n".join(result["errors"])


def test_release_gate_copies_generated_workbench_data_for_build(tmp_path: Path):
    gate = _load_gate_module()
    product_output = tmp_path / "product"
    workbench = tmp_path / "workbench"
    product_output.mkdir()
    (product_output / "workbench-data.json").write_text(json.dumps({
        "schema": {"version": "graph-wiki-workbench-v1", "source": "domain-read-model.json"},
        "project": {},
        "domains": [],
        "domainDetails": {},
    }), encoding="utf-8")

    action = gate.copy_workbench_data_for_build(product_output, workbench)

    copied = json.loads((workbench / "public" / "workbench-data.json").read_text(encoding="utf-8"))
    assert action["status"] == "passed"
    assert copied["schema"]["source"] == "domain-read-model.json"


def test_release_gate_rejects_product_quality_that_drifted_from_read_model(tmp_path: Path):
    gate = _load_gate_module()
    output_dir = tmp_path / "fixture"
    _write_minimal_artifacts(output_dir, product_quality={
        "deepReadingStatus": "passed",
        "coreDomainEvidenceStatus": "passed",
        "ruleCorrectnessRisk": "low",
    })
    model_path = output_dir / "domain-read-model.json"
    model = json.loads(model_path.read_text(encoding="utf-8"))
    model["quality"] = {
        "deepReadingStatus": "failed",
        "coreDomainEvidenceStatus": "failed",
        "ruleCorrectnessRisk": "high",
    }
    model_path.write_text(json.dumps(model), encoding="utf-8")

    result = gate.validate_build_artifacts(output_dir)

    assert result["status"] == "failed"
    assert "productQuality must match domain-read-model.json quality" in "\n".join(result["errors"])
    assert result["productQuality"]["deepReadingStatus"] == "failed"


def test_release_gate_refuses_to_delete_repo_root():
    gate = _load_gate_module()

    with pytest.raises(gate.GateFailure, match="unsafe output directory"):
        gate.prepare_output_dir(gate.ROOT)


def test_release_gate_pins_npm_audit_to_official_registry():
    gate = _load_gate_module()

    command = gate.npm_audit_command()

    assert command == [
        "npm",
        "audit",
        "--registry",
        "https://registry.npmjs.org",
        "--audit-level=high",
        "--omit=dev",
    ]


def test_release_gate_records_pip_check_and_npm_audits(monkeypatch, tmp_path: Path):
    gate = _load_gate_module()
    output = tmp_path / "gate"
    calls = []

    def fake_prepare_output_dir(path):
        output.mkdir(parents=True, exist_ok=True)
        return output

    def fake_run_command(name, command, cwd):
        calls.append((name, command, cwd))
        return {
            "name": name,
            "command": " ".join(command),
            "cwd": str(cwd),
            "returncode": 0,
            "status": "passed",
            "outputTail": "",
        }

    def fake_validate_build_artifacts(*args, **kwargs):
        return {"status": "passed"}

    monkeypatch.setattr(gate, "prepare_output_dir", fake_prepare_output_dir)
    monkeypatch.setattr(gate, "run_command", fake_run_command)
    monkeypatch.setattr(gate, "validate_build_artifacts", fake_validate_build_artifacts)
    monkeypatch.setattr(gate, "copy_workbench_data_for_build", lambda *args: {"status": "passed"})

    assert gate.main(["--output-dir", str(output)]) == 0

    names = [name for name, _, _ in calls]
    assert "python dependency check" in names
    assert "workbench npm audit high" in names
    assert "svn-platform npm audit high" in names
    assert calls[names.index("python dependency check")][1][:4] == [gate.sys.executable, "-m", "pip", "check"]
    assert calls[names.index("workbench npm audit high")][1] == gate.npm_audit_command()
    assert calls[names.index("svn-platform npm audit high")][1] == gate.npm_audit_command()


def test_release_gate_rejects_sensitive_and_escaping_source_evidence(tmp_path: Path):
    gate = _load_gate_module()
    output_dir = tmp_path / "fixture"
    product_quality = {
        "deepReadingStatus": "passed",
        "coreDomainEvidenceStatus": "passed",
        "ruleCorrectnessRisk": "low",
    }
    _write_minimal_artifacts(output_dir, product_quality=product_quality)
    model_path = output_dir / "domain-read-model.json"
    model = json.loads(model_path.read_text(encoding="utf-8"))
    model["evidenceIndex"].update({
        "source:redacted/sensitive-source#password": {
            "id": "source:redacted/sensitive-source#password",
            "type": "source",
            "status": "missing",
            "redacted": True,
        },
        "source:../outside/Controller.java#create": {
            "id": "source:../outside/Controller.java#create",
            "type": "source",
            "status": "ready",
            "path": "../outside/Controller.java",
            "sourcePath": "../outside/Controller.java",
        },
        "source:backend/src/main/resources/application-prod.yml#password": {
            "id": "source:backend/src/main/resources/application-prod.yml#password",
            "type": "source",
            "status": "ready",
            "path": "backend/src/main/resources/application-prod.yml",
            "sourcePath": "backend/src/main/resources/application-prod.yml",
        },
    })
    model_path.write_text(json.dumps(model), encoding="utf-8")

    result = gate.validate_build_artifacts(output_dir)

    assert result["status"] == "failed"
    errors = "\n".join(result["errors"])
    assert "sensitive source evidence" in errors
    assert "escapes sourceRoot" in errors
