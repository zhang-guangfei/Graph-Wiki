from pathlib import Path
from types import SimpleNamespace

import pytest

from graph_wiki import pipeline
from graph_wiki.cluster import classify_business_point_type
from graph_wiki.export import evaluate_wiki_quality
from graph_wiki.models import BusinessPoint, Domain


def test_cmd_build_honors_output_dir_and_reports_timing(tmp_path, monkeypatch):
    project = tmp_path / "project"
    source_dir = project / "src" / "views" / "order"
    source_dir.mkdir(parents=True)
    source_file = source_dir / "index.vue"
    source_file.write_text("export default { methods: { submitOrder() {} } }", encoding="utf-8")

    extraction = {
        "nodes": [
            {"id": "order_file", "label": "index.vue", "file_type": "code", "source_file": str(source_file)},
            {"id": "submit", "label": "submitOrder()", "file_type": "code", "source_file": str(source_file)},
        ],
        "edges": [{"source": "order_file", "target": "submit", "relation": "contains"}],
    }
    domain = Domain(
        id="order",
        name="order",
        display_name="订单管理",
        anchors={"controller": [{"id": "order_file", "label": "index.vue", "source_file": str(source_file)}]},
        business_points=[BusinessPoint(name="submitOrder()", point_type="core_action")],
        total_files=1,
    )

    monkeypatch.chdir(tmp_path)
    monkeypatch.setattr(pipeline, "detect_corpus", lambda root: {
        "total_files": 1,
        "total_words": 10,
        "files": {"code": [str(source_file)]},
    })
    monkeypatch.setattr(pipeline, "extract_ast", lambda files: extraction)
    monkeypatch.setattr(pipeline, "business_cluster", lambda graph, root, language=None: [domain])
    monkeypatch.setattr(pipeline, "build_api_map", lambda *args, **kwargs: [])
    monkeypatch.setattr(pipeline, "build_field_map", lambda *args, **kwargs: {})

    output_dir = tmp_path / "isolated-output"
    args = SimpleNamespace(
        path=project,
        name=None,
        language="javascript",
        llm_backend="claude",
        no_llm=True,
        output_dir=output_dir,
    )

    pipeline._cmd_build(args)

    for artifact in [
        "graph-lite.json",
        "domains.json",
        "api-map.json",
        "field-map.json",
        "build-report.json",
        "manifest.json",
        "wiki/index.md",
        "wiki/api-index.md",
        "domain_graph.html",
    ]:
        assert (output_dir / artifact).exists(), artifact
        assert not (tmp_path / artifact).exists(), artifact

    report = pipeline._read_json(output_dir / "build-report.json")
    assert report["quality"]["status"] == "passed"
    assert report["pipeline_timings"]["total_seconds"] >= 0
    assert report["scale"]["graph_lite"]["nodes"] == 2
    assert report["quality"]["performance_status"] == "passed"
    assert report["service_impl_capacity"]["threshold_lines"] == 2000
    assert report["service_impl_capacity"]["status"] == "passed"


def test_build_report_marks_service_impl_under_2000_lines_as_normal(tmp_path, monkeypatch):
    project = tmp_path / "project"
    service_dir = project / "src" / "main" / "java" / "com" / "acme" / "service" / "impl"
    service_dir.mkdir(parents=True)
    service_file = service_dir / "HugeServiceImpl.java"
    service_file.write_text("\n".join(["public class HugeServiceImpl {"] + ["    void step() {}"] * 1500 + ["}"]), encoding="utf-8")
    domain = Domain(
        id="acme",
        name="acme",
        anchors={"service_impl": [{"id": "HugeServiceImpl", "label": "HugeServiceImpl.java", "source_file": str(service_file)}]},
        business_points=[BusinessPoint(name="step()", point_type="core_action")],
    )

    monkeypatch.chdir(tmp_path)
    monkeypatch.setattr(pipeline, "detect_corpus", lambda root: {
        "total_files": 1,
        "total_words": 10,
        "files": {"code": [str(service_file)]},
    })
    monkeypatch.setattr(pipeline, "extract_ast", lambda files: {
        "nodes": [{"id": "HugeServiceImpl", "label": "HugeServiceImpl.java", "file_type": "code", "source_file": str(service_file)}],
        "edges": [],
    })
    monkeypatch.setattr(pipeline, "business_cluster", lambda graph, root, language=None: [domain])
    monkeypatch.setattr(pipeline, "build_api_map", lambda *args, **kwargs: [])
    monkeypatch.setattr(pipeline, "build_field_map", lambda *args, **kwargs: {})

    output_dir = tmp_path / "output"
    args = SimpleNamespace(
        path=project,
        name=None,
        language="java",
        llm_backend="claude",
        no_llm=True,
        output_dir=output_dir,
    )

    pipeline._cmd_build(args)

    report = pipeline._read_json(output_dir / "build-report.json")
    assert report["service_impl_capacity"]["status"] == "passed"
    assert report["service_impl_capacity"]["max_lines"] == 1502
    assert report["service_impl_capacity"]["largest_file"].endswith("HugeServiceImpl.java")


def test_cmd_build_writes_failure_report_when_pipeline_step_fails(tmp_path, monkeypatch):
    project = tmp_path / "broken"
    project.mkdir()
    source_file = project / "Broken.java"
    source_file.write_text("class Broken {}", encoding="utf-8")

    monkeypatch.chdir(tmp_path)
    monkeypatch.setattr(pipeline, "detect_corpus", lambda root: {
        "total_files": 1,
        "total_words": 10,
        "files": {"code": [str(source_file)]},
    })
    monkeypatch.setattr(pipeline, "extract_ast", lambda files: (_ for _ in ()).throw(RuntimeError("parse exploded")))

    output_dir = tmp_path / "failure-output"
    args = SimpleNamespace(
        path=project,
        name=None,
        language="java",
        llm_backend="claude",
        no_llm=True,
        output_dir=output_dir,
    )

    with pytest.raises(SystemExit):
        pipeline._cmd_build(args)

    report = pipeline._read_json(output_dir / "build-report.json")
    assert report["quality"]["status"] == "failed"
    assert report["quality"]["performance_status"] == "failed"
    assert report["failure"]["step"] == "extract_ast"
    assert "parse exploded" in report["failure"]["message"]


def test_business_point_type_classification_and_quality_counts():
    assert classify_business_point_type("submitOrder()") == "core_action"
    assert classify_business_point_type("loadOrders()") == "interaction"
    assert classify_business_point_type("formatDate()") == "helper"

    domain = Domain(
        id="order",
        name="order",
        anchors={"controller": [{"id": "OrderController"}]},
        business_points=[
            BusinessPoint(name="submitOrder()", point_type="core_action"),
            BusinessPoint(name="loadOrders()", point_type="interaction"),
            BusinessPoint(name="formatDate()", point_type="helper"),
        ],
    )

    report = evaluate_wiki_quality([domain], [], {})
    assert report["domains"]["business_points"] == 2
    assert report["domains"]["business_point_types"] == {
        "core_action": 1,
        "interaction": 1,
        "helper": 1,
    }


def test_phase_1_fixture_projects_exist():
    root = Path(__file__).parent
    assert (root / "fixtures" / "java-mini-backend").exists()
    assert (root / "fixtures" / "vue-mini-fullstack").exists()


def test_select_medium_target_prefers_candidate_nearest_300_to_500(tmp_path):
    ops = tmp_path / "ops"
    small = ops / "small"
    medium = ops / "medium"
    large = ops / "large"
    for root, count in [(small, 25), (medium, 360), (large, 800)]:
        root.mkdir(parents=True)
        for i in range(count):
            (root / f"File{i}.java").write_text("class File {}", encoding="utf-8")

    selected = pipeline.select_phase2_medium_target(ops)

    assert selected["path"] == str(medium)
    assert selected["code_files"] == 360
    assert selected["selection_reason"] == "within_300_500"
