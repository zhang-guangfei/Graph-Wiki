import json
import importlib.util
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
    model = {
        "schema": {"version": "domain-read-model-v1"},
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
