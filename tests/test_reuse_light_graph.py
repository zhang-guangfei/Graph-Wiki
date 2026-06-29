import json
from pathlib import Path

from graph_wiki.reuse import (
    build_light_graph,
    filter_extraction_for_wiki,
    save_graph_artifacts,
)


def test_filter_extraction_for_wiki_keeps_business_nodes_and_drops_noise():
    extraction = {
        "nodes": [
            {"id": "order_file", "label": "OrderController.java", "source_file": "src/order/OrderController.java"},
            {"id": "submit", "label": "submitOrder()", "source_file": "src/order/OrderController.java"},
            {"id": "getter", "label": "getName()", "source_file": "src/order/OrderController.java"},
            {"id": "anon", "label": ".then()", "source_file": "src/order/OrderController.java"},
            {"id": "dto", "label": "OrderDTO.java", "source_file": "src/order/dto/OrderDTO.java"},
            {"id": "dist", "label": "bundle.js", "source_file": "dist/bundle.js"},
        ],
        "edges": [
            {"source": "order_file", "target": "submit", "relation": "contains"},
            {"source": "order_file", "target": "getter", "relation": "contains"},
            {"source": "submit", "target": "dto", "relation": "calls"},
            {"source": "submit", "target": "anon", "relation": "calls"},
            {"source": "dist", "target": "submit", "relation": "imports_from"},
        ],
    }

    filtered = filter_extraction_for_wiki(extraction, Path("src"))
    kept_ids = {node["id"] for node in filtered["nodes"]}

    assert kept_ids == {"order_file", "submit", "dto"}
    assert filtered["edges"] == [
        {"source": "order_file", "target": "submit", "relation": "contains"},
        {"source": "submit", "target": "dto", "relation": "calls"},
    ]
    assert filtered["meta"]["original_nodes"] == 6
    assert filtered["meta"]["filtered_nodes"] == 3


def test_build_light_graph_and_save_graph_artifacts(tmp_path):
    filtered = {
        "nodes": [
            {"id": "order_file", "label": "OrderController.java", "source_file": "src/order/OrderController.java"},
            {"id": "submit", "label": "submitOrder()", "source_file": "src/order/OrderController.java"},
        ],
        "edges": [
            {"source": "order_file", "target": "submit", "relation": "contains", "weight": 1.0},
        ],
        "meta": {"filtered_nodes": 2},
    }

    graph = build_light_graph(filtered)
    assert graph.number_of_nodes() == 2
    assert graph.number_of_edges() == 1

    save_graph_artifacts(None, graph, tmp_path)
    graph_lite_path = tmp_path / "graph-lite.json"
    assert graph_lite_path.exists()
    assert not (tmp_path / "graph.json").exists()

    payload = json.loads(graph_lite_path.read_text(encoding="utf-8"))
    assert len(payload["nodes"]) == 2
    assert len(payload["links"]) == 1

