import json
from pathlib import Path

from graph_wiki.product_data import ProductDataService


def test_workbench_domain_page_is_derived_from_domain_read_model(tmp_path: Path):
    model = {
        "schema": {"version": "domain-read-model-v1"},
        "project": {"projectName": "Fixture", "sourceRoot": "fixture"},
        "evidenceIndex": {
            "api:POST:/orders": {"id": "api:POST:/orders", "type": "api", "label": "POST /orders", "path": "api-map.json", "sourcePath": "frontend/src/api/order.js", "confidence": 1.0, "status": "ready"},
            "source:frontend/src/api/order.js#createOrder": {"id": "source:frontend/src/api/order.js#createOrder", "type": "source", "label": "createOrder", "path": "frontend/src/api/order.js", "sourcePath": "frontend/src/api/order.js", "confidence": 1.0, "status": "ready"},
            "field:orders.customer_id": {"id": "field:orders.customer_id", "type": "field", "label": "orders.customer_id", "path": "field-map.json", "sourcePath": "", "confidence": 1.0, "status": "ready"},
        },
        "domains": [
            {
                "domainKey": "order",
                "displayName": "订单管理",
                "summary": "处理订单。",
                "core": True,
                "flows": [
                    {
                        "flowId": "order.createOrder",
                        "title": "创建订单",
                        "summary": "提交订单表单。",
                        "steps": [{"stepId": "s1", "order": 1, "text": "调用 API", "evidenceRefs": ["api:POST:/orders"], "ruleRefs": ["r1"], "status": "ready", "confidence": 1}],
                        "evidenceRefs": ["api:POST:/orders", "source:frontend/src/api/order.js#createOrder"],
                        "status": "ready",
                        "confidence": 1,
                    }
                ],
                "rules": [{"ruleId": "r1", "statement": "创建订单必须调用后端。", "flowRefs": ["order.createOrder"], "evidenceRefs": ["api:POST:/orders"], "status": "ready", "confidence": 1}],
                "fieldRules": [{"fieldRuleId": "fr1", "fieldId": "orders.customer_id", "statement": "customer_id 字段链路。", "chain": [{"layer": "frontend", "ref": "source:frontend/src/api/order.js#createOrder"}, {"layer": "dto", "ref": "source:backend/src/main/java/com/acme/order/dto/CreateOrderRequest.java#customerId"}, {"layer": "entity", "ref": "source:backend/src/main/java/com/acme/order/entity/OrderEntity.java#customerId"}, {"layer": "db", "ref": "field:orders.customer_id"}], "api": {"method": "POST", "url": "/orders", "functionName": "createOrder"}, "dto": {"className": "CreateOrderRequest", "field": "customerId"}, "entity": {"className": "OrderEntity", "field": "customerId"}, "frontendCallers": ["frontend/src/api/order.js"], "evidenceRefs": ["field:orders.customer_id", "api:POST:/orders", "source:frontend/src/api/order.js#createOrder"], "status": "ready", "confidence": 1, "partialReason": ""}],
                "evidenceRefs": ["api:POST:/orders", "source:frontend/src/api/order.js#createOrder"],
                "quality": {"deepReadingStatus": "passed"},
            }
        ],
        "quality": {"deepReadingStatus": "passed", "coreDomainEvidenceStatus": "passed", "ruleCorrectnessRisk": "low", "warnings": [], "errors": []},
    }
    (tmp_path / "domain-read-model.json").write_text(json.dumps(model, ensure_ascii=False), encoding="utf-8")
    (tmp_path / "build-report.json").write_text(json.dumps({"project": {"root": "fixture"}, "quality": {"status": "passed"}, "productQuality": model["quality"]}, ensure_ascii=False), encoding="utf-8")

    bundle = ProductDataService(tmp_path).export_workbench_data()
    detail = bundle["domainDetails"]["order"]

    assert bundle["schema"]["version"] == "graph-wiki-workbench-v1"
    assert bundle["schema"]["source"] == "domain-read-model.json"
    assert detail["deepReadingPath"]["order"] == ["flows", "rules", "evidence"]
    assert detail["flows"][0]["steps"][0]["evidenceRefs"] == ["api:POST:/orders"]
    assert detail["rules"]["items"][0]["statement"] == "创建订单必须调用后端。"
    assert detail["fieldRules"][0]["fieldId"] == "orders.customer_id"
    field_item = detail["fieldFlows"]["items"][0]
    assert field_item["api"] == {"method": "POST", "url": "/orders", "functionName": "createOrder"}
    assert field_item["dto"] == {"className": "CreateOrderRequest", "field": "customerId"}
    assert field_item["entity"] == {"className": "OrderEntity", "field": "customerId"}
    assert field_item["frontendCallers"] == ["frontend/src/api/order.js"]
