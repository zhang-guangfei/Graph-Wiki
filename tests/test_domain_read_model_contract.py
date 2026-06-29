from pathlib import Path

from graph_wiki.domain_read_model import build_domain_read_model, evaluate_domain_read_model
from graph_wiki.evidence import is_valid_evidence_ref
from graph_wiki.models import ApiMatch, BackendEndpoint, BusinessPoint, Domain, FrontendApiCall


def _sample_domain() -> Domain:
    return Domain(
        id="order",
        name="order",
        display_name="订单管理",
        description="处理订单创建、校验和落库。",
        anchors={"controller": [{"label": "OrderController.java", "source_file": "backend/src/main/java/com/acme/order/OrderController.java"}]},
        business_points=[BusinessPoint(name="createOrder()", point_type="core_action", entry_file="frontend/src/views/order/CreateOrder.vue")],
    )


def _sample_api() -> ApiMatch:
    return ApiMatch(
        id="api-create-order",
        frontend=FrontendApiCall(
            function_name="createOrder",
            http_method="POST",
            url="/orders",
            source_file="frontend/src/api/order.js",
            callers=[{"page": "frontend/src/views/order/CreateOrder.vue"}],
        ),
        backend=BackendEndpoint(
            controller_file="backend/src/main/java/com/acme/order/OrderController.java",
            controller_class="OrderController",
            method_name="createOrder",
            http_method="POST",
            url="/orders",
            param_type="CreateOrderRequest",
            service_chain=["orderService.createOrder()", "inventoryService.checkStock()"],
        ),
        match_confidence=0.95,
        domain="order",
    )


def _sample_field_map() -> dict:
    return {
        "order": {
            "orders": {
                "customer_id": [
                    {
                        "api_url": "/orders",
                        "api_function": "createOrder",
                        "dto_class": "CreateOrderRequest",
                        "dto_field": "customerId",
                        "dto_file": "backend/src/main/java/com/acme/order/dto/CreateOrderRequest.java",
                        "entity_class": "OrderEntity",
                        "entity_field": "customerId",
                        "entity_file": "backend/src/main/java/com/acme/order/entity/OrderEntity.java",
                        "db_table": "orders",
                        "db_column": "customer_id",
                        "callers": ["frontend/src/views/order/CreateOrder.vue"],
                        "confidence": 1.0,
                        "confidence_level": "high",
                    }
                ]
            }
        }
    }


def test_domain_read_model_contract_has_flow_rule_field_rule_and_evidence(tmp_path: Path):
    for file in [
        "frontend/src/api/order.js",
        "frontend/src/views/order/CreateOrder.vue",
        "backend/src/main/java/com/acme/order/OrderController.java",
        "backend/src/main/java/com/acme/order/dto/CreateOrderRequest.java",
        "backend/src/main/java/com/acme/order/entity/OrderEntity.java",
    ]:
        path = tmp_path / file
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text("// fixture\n", encoding="utf-8")

    model = build_domain_read_model(
        project_id="fixture",
        project_name="Fixture",
        source_root=tmp_path,
        domains=[_sample_domain()],
        api_matches=[_sample_api()],
        field_map=_sample_field_map(),
        ontology={},
    )

    assert model["schema"]["version"] == "domain-read-model-v1"
    assert model["quality"]["deepReadingStatus"] == "passed"
    domain = model["domains"][0]
    assert domain["core"] is True
    assert domain["flows"] and domain["flows"][0]["steps"]
    assert domain["rules"] and domain["rules"][0]["review"]["state"] == "machine_draft"
    assert domain["fieldRules"] and domain["fieldRules"][0]["fieldId"] == "orders.customer_id"

    claim_refs = []
    for flow in domain["flows"]:
        claim_refs += flow["evidenceRefs"]
        for step in flow["steps"]:
            claim_refs += step["evidenceRefs"]
            assert step["ruleRefs"]
    for rule in domain["rules"] + domain["fieldRules"]:
        claim_refs += rule["evidenceRefs"]

    assert claim_refs
    assert all(is_valid_evidence_ref(ref) for ref in claim_refs)
    assert all(ref in model["evidenceIndex"] for ref in claim_refs)


def test_domain_read_model_quality_fails_when_core_rule_has_missing_evidence():
    model = {
        "schema": {"version": "domain-read-model-v1"},
        "domains": [
            {
                "domainKey": "order",
                "core": True,
                "flows": [{"flowId": "order.submit", "steps": [], "evidenceRefs": []}],
                "rules": [{"ruleId": "order.submit.rule", "flowRefs": ["order.submit"], "statement": "rule", "evidenceRefs": []}],
                "fieldRules": [],
                "evidenceRefs": [],
                "quality": {},
            }
        ],
        "evidenceIndex": {},
    }

    quality = evaluate_domain_read_model(model)

    assert quality["deepReadingStatus"] == "failed"
    assert any("缺少" in item for item in quality["errors"])
