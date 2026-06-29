from pathlib import Path
from types import SimpleNamespace

from graph_wiki import pipeline
from graph_wiki.models import ApiMatch, BackendEndpoint, BusinessPoint, Domain, FrontendApiCall


def test_cmd_build_writes_domain_read_model_and_separate_product_quality(tmp_path, monkeypatch):
    project = tmp_path / "project"
    source_file = project / "frontend" / "src" / "api" / "order.js"
    source_file.parent.mkdir(parents=True)
    source_file.write_text("export function createOrder(data) { return request.post('/orders', data) }", encoding="utf-8")
    controller = project / "backend" / "src" / "main" / "java" / "com" / "acme" / "order" / "OrderController.java"
    controller.parent.mkdir(parents=True)
    controller.write_text("class OrderController {}", encoding="utf-8")

    domain = Domain(
        id="order",
        name="order",
        display_name="订单管理",
        anchors={"controller": [{"id": "OrderController", "label": "OrderController.java", "source_file": str(controller)}]},
        business_points=[BusinessPoint(name="createOrder()", point_type="core_action", entry_file=str(source_file))],
    )
    api = ApiMatch(
        id="api-create-order",
        frontend=FrontendApiCall(function_name="createOrder", http_method="POST", url="/orders", source_file=str(source_file)),
        backend=BackendEndpoint(controller_file=str(controller), controller_class="OrderController", method_name="createOrder", http_method="POST", url="/orders"),
        domain="order",
        match_confidence=0.95,
    )

    monkeypatch.setattr(pipeline, "detect_corpus", lambda root: {"total_files": 2, "total_words": 20, "files": {"code": [str(source_file), str(controller)]}})
    monkeypatch.setattr(pipeline, "extract_ast", lambda files: {"nodes": [{"id": "n1", "label": "order.js", "file_type": "code", "source_file": str(source_file)}], "edges": []})
    monkeypatch.setattr(pipeline, "business_cluster", lambda graph, root, language=None: [domain])
    monkeypatch.setattr(pipeline, "build_api_map", lambda *args, **kwargs: [api])
    monkeypatch.setattr(pipeline, "build_field_map", lambda *args, **kwargs: {})

    output_dir = tmp_path / "output"
    args = SimpleNamespace(path=project, name="Fixture", language="javascript", llm_backend="claude", no_llm=True, output_dir=output_dir)

    pipeline._cmd_build(args)

    domain_model = pipeline._read_json(output_dir / "domain-read-model.json")
    report = pipeline._read_json(output_dir / "build-report.json")
    workbench = pipeline._read_json(output_dir / "workbench-data.json")

    assert domain_model["schema"]["version"] == "domain-read-model-v1"
    assert report["build"]["status"] == "passed"
    assert "productQuality" in report
    assert report["productQuality"]["deepReadingStatus"] in {"passed", "warning"}
    assert workbench["schema"]["source"] == "domain-read-model.json"
