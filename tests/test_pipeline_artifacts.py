from pathlib import Path
from types import SimpleNamespace

import networkx as nx

from graph_wiki import pipeline
from graph_wiki.models import BusinessPoint, Domain, FrontendApiCall


def test_cmd_build_writes_v1_artifacts(tmp_path, monkeypatch):
    project = tmp_path / "project"
    (project / "src" / "api").mkdir(parents=True)
    (project / "src" / "views").mkdir(parents=True)
    source_file = project / "src" / "api" / "order.js"
    source_file.write_text("export function createOrder() {}", encoding="utf-8")

    extraction = {
        "nodes": [
            {"id": "order_file", "label": "order.js", "source_file": str(source_file)},
            {"id": "create", "label": "createOrder()", "source_file": str(source_file)},
        ],
        "edges": [
            {"source": "order_file", "target": "create", "relation": "contains"},
        ],
    }
    domain = Domain(
        id="order",
        name="order",
        display_name="订单管理",
        anchors={"controller": [{"id": "order_file", "source_file": str(source_file)}]},
        business_points=[BusinessPoint(name="createOrder()")],
    )
    api_call = FrontendApiCall(
        function_name="createOrder",
        http_method="POST",
        url="/order/create",
        source_file=str(source_file),
    )
    setattr(api_call, "domain", "order")

    monkeypatch.chdir(tmp_path)
    monkeypatch.setattr(pipeline, "detect_corpus", lambda root: {
        "total_files": 1,
        "total_words": 10,
        "files": {"code": [str(source_file)]},
    })
    monkeypatch.setattr(pipeline, "extract_ast", lambda files: extraction)
    monkeypatch.setattr(pipeline, "business_cluster", lambda graph, root, language=None: [domain])
    monkeypatch.setattr(pipeline, "build_api_map", lambda *args, **kwargs: [api_call])
    monkeypatch.setattr(pipeline, "build_field_map", lambda *args, **kwargs: {})

    args = SimpleNamespace(
        path=project,
        name=None,
        language="javascript",
        llm_backend="claude",
        no_llm=True,
    )

    pipeline._cmd_build(args)

    for artifact in [
        "graph-lite.json",
        "domains.json",
        "api-map.json",
        "field-map.json",
        "build-report.json",
        "workbench-data.json",
        "manifest.json",
        "wiki/index.md",
        "wiki/api-index.md",
        "domain_graph.html",
    ]:
        assert (tmp_path / artifact).exists(), artifact
