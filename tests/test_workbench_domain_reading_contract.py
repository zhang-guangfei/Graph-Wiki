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
                "fieldRules": [{"fieldRuleId": "fr1", "fieldId": "orders.customer_id", "statement": "customer_id 字段链路。", "chain": [{"layer": "db", "ref": "field:orders.customer_id"}], "mapping": {"api": {"method": "POST", "url": "/orders", "functionName": "createOrder"}, "dto": {"className": "CreateOrderRequest", "field": "customerId", "file": "backend/src/main/java/com/acme/order/dto/CreateOrderRequest.java"}, "entity": {"className": "OrderEntity", "field": "customerId", "file": "backend/src/main/java/com/acme/order/entity/OrderEntity.java"}, "frontendCallers": ["frontend/src/views/order/CreateOrder.vue"]}, "evidenceRefs": ["field:orders.customer_id", "api:POST:/orders"], "status": "ready", "confidence": 1, "partialReason": ""}],
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
    assert detail["fieldFlows"]["items"][0]["api"] == {"method": "POST", "url": "/orders", "functionName": "createOrder"}
    assert detail["fieldFlows"]["items"][0]["dto"]["className"] == "CreateOrderRequest"
    assert detail["fieldFlows"]["items"][0]["entity"]["field"] == "customerId"
    assert detail["fieldFlows"]["items"][0]["frontendCallers"] == ["frontend/src/views/order/CreateOrder.vue"]


def test_workbench_domain_page_keeps_backward_compatible_field_mapping_fallback(tmp_path: Path):
    model = {
        "schema": {"version": "domain-read-model-v1"},
        "project": {"projectName": "Fixture", "sourceRoot": "fixture"},
        "evidenceIndex": {
            "api:PUT:/orders/1": {"id": "api:PUT:/orders/1", "type": "api", "label": "PUT /orders/1", "path": "api-map.json", "confidence": 1.0, "status": "ready"},
            "field:orders.status": {"id": "field:orders.status", "type": "field", "label": "orders.status", "path": "field-map.json", "confidence": 1.0, "status": "ready"},
        },
        "domains": [{
            "domainKey": "order",
            "displayName": "订单管理",
            "summary": "处理订单。",
            "core": True,
            "flows": [{"flowId": "order.update", "title": "更新订单", "summary": "", "steps": [{"stepId": "s1", "order": 1, "text": "调用 API", "evidenceRefs": ["api:PUT:/orders/1"], "ruleRefs": ["r1"], "status": "ready", "confidence": 1}], "evidenceRefs": ["api:PUT:/orders/1"], "status": "ready", "confidence": 1}],
            "rules": [{"ruleId": "r1", "statement": "更新订单必须调用后端。", "flowRefs": ["order.update"], "evidenceRefs": ["api:PUT:/orders/1"], "status": "ready", "confidence": 1}],
            "fieldRules": [{"fieldRuleId": "fr1", "fieldId": "orders.status", "statement": "status 字段链路。", "chain": [{"layer": "db", "ref": "field:orders.status"}], "evidenceRefs": ["field:orders.status", "api:PUT:/orders/1"], "status": "ready", "confidence": 1, "partialReason": ""}],
            "evidenceRefs": ["api:PUT:/orders/1"],
            "quality": {"deepReadingStatus": "passed"},
        }],
        "quality": {"deepReadingStatus": "passed", "coreDomainEvidenceStatus": "passed", "ruleCorrectnessRisk": "low", "warnings": [], "errors": []},
    }
    (tmp_path / "domain-read-model.json").write_text(json.dumps(model, ensure_ascii=False), encoding="utf-8")
    (tmp_path / "build-report.json").write_text(json.dumps({"project": {"root": "fixture"}, "quality": {"status": "passed"}, "productQuality": model["quality"]}, ensure_ascii=False), encoding="utf-8")

    field_flow = ProductDataService(tmp_path).export_workbench_data()["domainDetails"]["order"]["fieldFlows"]["items"][0]

    assert field_flow["api"] == {"method": "PUT", "url": "/orders/1", "functionName": ""}
    assert field_flow["dto"] == {"className": "", "field": "", "file": ""}
    assert field_flow["entity"] == {"className": "", "field": "", "file": ""}
    assert field_flow["frontendCallers"] == []
