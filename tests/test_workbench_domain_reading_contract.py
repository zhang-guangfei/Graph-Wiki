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
                "fieldRules": [
                    {
                        "fieldRuleId": "fr1",
                        "fieldId": "orders.customer_id",
                        "statement": "customer_id 字段链路。",
                        "chain": [{"layer": "db", "ref": "field:orders.customer_id"}],
                        "mapping": {
                            "api": {"method": "POST", "url": "/orders", "functionName": "createOrder"},
                            "dto": {"className": "CreateOrderRequest", "field": "customerId", "sourcePath": "backend/dto/CreateOrderRequest.java"},
                            "entity": {"className": "OrderEntity", "field": "customerId", "sourcePath": "backend/entity/OrderEntity.java"},
                            "database": {"table": "orders", "column": "customer_id"},
                            "frontendCallers": ["frontend/src/views/order/CreateOrder.vue"],
                        },
                        "chainCompleteness": {
                            "presentLayers": ["api", "db"],
                            "missingRequiredLayers": [],
                            "missingOptionalLayers": ["frontend", "controller", "dto", "entity"],
                        },
                        "evidenceRefs": ["field:orders.customer_id", "api:POST:/orders"],
                        "status": "ready",
                        "confidence": 1,
                        "partialReason": "",
                    }
                ],
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
    assert detail["spec"]["status"] == "placeholder"
    assert detail["fieldFlows"]["status"] == "ready"
    assert detail["fieldRules"][0]["fieldId"] == "orders.customer_id"
    assert detail["fieldRules"][0]["mapping"]["dto"] == {
        "className": "CreateOrderRequest",
        "field": "customerId",
        "sourcePath": "backend/dto/CreateOrderRequest.java",
    }
    assert detail["fieldRules"][0]["chainCompleteness"]["missingOptionalLayers"] == ["frontend", "controller", "dto", "entity"]
    assert bundle["apis"][0]["apiId"] == "api:POST:/orders"
    assert bundle["fields"][0]["dto"] == {"className": "CreateOrderRequest", "field": "customerId"}
    assert bundle["fields"][0]["entity"] == {"className": "OrderEntity", "field": "customerId"}
    assert bundle["fields"][0]["frontendCallers"] == ["frontend/src/views/order/CreateOrder.vue"]


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


def test_workbench_read_model_statuses_use_contract_values_and_preserve_partial_field_flows(tmp_path: Path):
    model = {
        "schema": {"version": "domain-read-model-v1"},
        "project": {"projectName": "Fixture", "sourceRoot": "fixture"},
        "evidenceIndex": {
            "field:orders.status": {"id": "field:orders.status", "type": "field", "label": "orders.status", "path": "field-map.json", "confidence": 0.7, "status": "partial"},
        },
        "domains": [{
            "domainKey": "order",
            "displayName": "订单管理",
            "summary": "处理订单。",
            "core": True,
            "flows": [],
            "rules": [],
            "fieldRules": [{
                "fieldRuleId": "fr-partial",
                "fieldId": "orders.status",
                "statement": "status 字段链路不完整。",
                "chain": [{"layer": "db", "ref": "field:orders.status"}],
                "evidenceRefs": ["field:orders.status"],
                "status": "partial",
                "confidence": 0.7,
                "partialReason": "缺少 DTO/entity 映射",
            }],
            "evidenceRefs": ["field:orders.status"],
            "quality": {"deepReadingStatus": "warning"},
        }],
        "quality": {"deepReadingStatus": "warning", "coreDomainEvidenceStatus": "warning", "ruleCorrectnessRisk": "medium", "warnings": [], "errors": []},
    }
    (tmp_path / "domain-read-model.json").write_text(json.dumps(model, ensure_ascii=False), encoding="utf-8")
    (tmp_path / "build-report.json").write_text(json.dumps({"project": {"root": "fixture"}, "build": {"status": "passed"}, "quality": {"status": "warning"}, "productQuality": model["quality"]}, ensure_ascii=False), encoding="utf-8")

    detail = ProductDataService(tmp_path).export_workbench_data()["domainDetails"]["order"]

    assert detail["rules"]["status"] in {"placeholder", "ready", "missing"}
    assert detail["rules"]["status"] == "missing"
    assert detail["spec"]["status"] in {"placeholder", "ready", "missing"}
    assert detail["fieldFlows"]["status"] == "partial"
    assert "No business flows were generated for this domain." in detail["health"]["warnings"]
    assert "No business rules were generated for this domain." in detail["health"]["warnings"]
    assert "Partial field rule: 缺少 DTO/entity 映射" in detail["health"]["warnings"]

    signals = ProductDataService(tmp_path).export_workbench_data()["overview"]["qualitySignals"]
    assert signals["statusItems"] == [
        "build.status=passed",
        "productQuality.deepReadingStatus=warning",
        "productQuality.coreDomainEvidenceStatus=warning",
    ]
    assert signals["partialDomains"] == [{"domainKey": "order", "displayName": "订单管理", "status": "warning"}]
    assert signals["recommendedActions"]
