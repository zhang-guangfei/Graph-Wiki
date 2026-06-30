from __future__ import annotations

import importlib.util
import json
from pathlib import Path

import pytest


def _load_demo_module():
    path = Path(__file__).resolve().parents[1] / "scripts" / "prepare_workbench_demo.py"
    spec = importlib.util.spec_from_file_location("prepare_workbench_demo", path)
    module = importlib.util.module_from_spec(spec)
    assert spec.loader is not None
    spec.loader.exec_module(module)
    return module


def _write_generated_product(output_dir: Path, *, schema_source: str = "domain-read-model.json") -> None:
    output_dir.mkdir(parents=True)
    quality = {
        "deepReadingStatus": "passed",
        "coreDomainEvidenceStatus": "passed",
        "ruleCorrectnessRisk": "low",
        "warnings": [],
        "errors": [],
    }
    (output_dir / "domain-read-model.json").write_text(json.dumps({
        "schema": {"version": "domain-read-model-v1"},
        "quality": quality,
        "domains": [],
        "evidenceIndex": {},
    }), encoding="utf-8")
    (output_dir / "build-report.json").write_text(json.dumps({
        "build": {"status": "passed"},
        "productQuality": quality,
    }), encoding="utf-8")
    (output_dir / "workbench-data.json").write_text(json.dumps({
        "schema": {"version": "graph-wiki-workbench-v1", "source": schema_source},
        "overview": {"projectName": "Fixture"},
        "domains": [],
        "domainDetails": {},
        "apis": [],
        "fields": [],
        "impact": {"status": "unsupported", "queryExamples": [], "coverage": {}},
        "dreamCycle": {"status": "unsupported", "reviewQueue": []},
    }), encoding="utf-8")


def test_prepare_workbench_demo_copies_valid_v1_data_to_ignored_runtime_target(tmp_path: Path):
    demo = _load_demo_module()
    product_output = tmp_path / "generated"
    workbench = tmp_path / "workbench"
    workbench.mkdir()
    (workbench / "package.json").write_text(json.dumps({"scripts": {"build": "vue-tsc --noEmit && vite build"}}), encoding="utf-8")
    _write_generated_product(product_output)

    result = demo.prepare_workbench_demo_data(product_output, workbench)

    target = workbench / "public" / "workbench-data.json"
    copied = json.loads(target.read_text(encoding="utf-8"))
    assert result["status"] == "passed"
    assert result["target"] == str(target.resolve())
    assert copied["schema"] == {"version": "graph-wiki-workbench-v1", "source": "domain-read-model.json"}
    assert "npm run dev -- --port 5174" in result["nextCommands"]


def test_prepare_workbench_demo_rejects_non_domain_read_model_bundle(tmp_path: Path):
    demo = _load_demo_module()
    product_output = tmp_path / "generated"
    workbench = tmp_path / "workbench"
    workbench.mkdir()
    (workbench / "package.json").write_text("{}", encoding="utf-8")
    _write_generated_product(product_output, schema_source="ProductDataService")

    with pytest.raises(demo.DemoDataError, match="domain-read-model.json"):
        demo.prepare_workbench_demo_data(product_output, workbench)
