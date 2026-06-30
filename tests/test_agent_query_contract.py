import json
from types import SimpleNamespace

from graph_wiki import pipeline


def test_query_uses_domain_read_model_for_structured_agent_output(tmp_path, capsys):
    read_model = {
        "schema": {"version": "domain-read-model-v1"},
        "quality": {"deepReadingStatus": "passed", "coreDomainEvidenceStatus": "passed"},
        "domains": [
            {
                "domainKey": "order",
                "displayName": "订单管理",
                "summary": "处理订单创建、校验和落库。",
                "quality": {"deepReadingStatus": "passed"},
                "evidenceRefs": ["api:POST:/orders"],
                "flows": [{"flowId": "order.create", "title": "创建订单", "evidenceRefs": ["api:POST:/orders"], "steps": []}],
                "rules": [{"ruleId": "order.create.rule", "statement": "创建订单前必须校验库存。", "evidenceRefs": ["source:backend/OrderService.java#checkStock"]}],
                "fieldRules": [{"ruleId": "order.customer.field", "fieldId": "orders.customer_id", "evidenceRefs": ["field:orders.customer_id"]}],
            }
        ],
        "evidenceIndex": {
            "api:POST:/orders": {"id": "api:POST:/orders", "label": "POST /orders"},
            "source:backend/OrderService.java#checkStock": {"id": "source:backend/OrderService.java#checkStock", "label": "checkStock"},
            "field:orders.customer_id": {"id": "field:orders.customer_id", "label": "orders.customer_id"},
        },
    }
    (tmp_path / "domain-read-model.json").write_text(json.dumps(read_model, ensure_ascii=False), encoding="utf-8")

    pipeline._cmd_query(SimpleNamespace(question="订单 库存", output_dir=tmp_path, output="json"))

    payload = json.loads(capsys.readouterr().out)
    assert payload["schema"] == {"version": "graph-wiki-agent-query-v1", "source": "domain-read-model.json"}
    assert payload["productQuality"] == read_model["quality"]
    assert payload["matches"][0]["domainKey"] == "order"
    assert payload["matches"][0]["flows"][0]["flowId"] == "order.create"
    assert payload["matches"][0]["rules"][0]["ruleId"] == "order.create.rule"
    assert "api:POST:/orders" in payload["matches"][0]["evidenceRefs"]
