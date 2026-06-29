from pathlib import Path
from types import SimpleNamespace

from graph_wiki import pipeline


def test_fullstack_enterprise_fixture_satisfies_domain_deep_reading_acceptance(tmp_path: Path):
    fixture = Path("tests/fixtures/fullstack-enterprise").resolve()
    output_dir = tmp_path / "fullstack-output"

    pipeline._cmd_build(SimpleNamespace(
        path=fixture,
        name="Fullstack Enterprise Fixture",
        language="auto",
        llm_backend="claude",
        no_llm=True,
        output_dir=output_dir,
    ))

    model = pipeline._read_json(output_dir / "domain-read-model.json")
    report = pipeline._read_json(output_dir / "build-report.json")
    workbench = pipeline._read_json(output_dir / "workbench-data.json")

    assert model["schema"]["version"] == "domain-read-model-v1"
    assert report["build"]["status"] == "passed"
    assert report["productQuality"]["deepReadingStatus"] == "passed"
    assert workbench["schema"]["source"] == "domain-read-model.json"

    core_domains = [domain for domain in model["domains"] if domain["core"]]
    assert core_domains
    order = next(domain for domain in core_domains if domain["domainKey"] == "order")
    assert order["flows"]
    assert order["rules"]
    assert any("库存" in rule["statement"] for rule in order["rules"])
    assert any("数量" in rule["statement"] for rule in order["rules"])
    assert order["fieldRules"]

    evidence_index = model["evidenceIndex"]
    for flow in order["flows"]:
        assert flow["steps"]
        assert flow["evidenceRefs"]
        assert all(ref in evidence_index for ref in flow["evidenceRefs"])
        for step in flow["steps"]:
            assert step["evidenceRefs"]
            assert step["ruleRefs"]
            assert all(ref in evidence_index for ref in step["evidenceRefs"])
    for rule in order["rules"]:
        assert rule["statement"]
        assert rule["flowRefs"]
        assert rule["evidenceRefs"]
        assert all(ref in evidence_index for ref in rule["evidenceRefs"])
    field_rule_ids = [field_rule["fieldRuleId"] for field_rule in order["fieldRules"]]
    assert len(field_rule_ids) == len(set(field_rule_ids))
    for field_rule in order["fieldRules"]:
        assert field_rule["fieldId"]
        assert field_rule["chain"]
        assert field_rule["evidenceRefs"]
        assert all(ref in evidence_index for ref in field_rule["evidenceRefs"])
        assert all(item["ref"] in field_rule["evidenceRefs"] for item in field_rule["chain"])
        if field_rule["status"] == "partial":
            assert field_rule["partialReason"]

    customer_rule = next(rule for rule in order["fieldRules"] if rule["fieldId"] == "orders.customer_id" and rule["mapping"]["api"]["functionName"] == "createOrder")
    assert [item["layer"] for item in customer_rule["chain"]] == ["frontend", "api", "controller", "dto", "entity", "db"]
    assert customer_rule["status"] == "ready"
    assert customer_rule["chainCompleteness"] == {
        "presentLayers": ["api", "controller", "db", "dto", "entity", "frontend"],
        "missingRequiredLayers": [],
        "missingOptionalLayers": [],
    }
    assert customer_rule["mapping"]["dto"] == {
        "className": "CreateOrderRequest",
        "field": "customerId",
        "sourcePath": "backend/src/main/java/com/acme/order/dto/CreateOrderRequest.java",
    }
    assert customer_rule["mapping"]["entity"] == {
        "className": "OrderEntity",
        "field": "customerId",
        "sourcePath": "backend/src/main/java/com/acme/order/entity/OrderEntity.java",
    }

    detail = workbench["domainDetails"]["order"]
    assert detail["deepReadingPath"]["order"] == ["flows", "rules", "evidence"]
    assert detail["flows"]
    assert detail["rules"]["items"]
    assert detail["fieldRules"]
    workbench_customer_rule = next(rule for rule in detail["fieldRules"] if rule["fieldRuleId"] == customer_rule["fieldRuleId"])
    assert workbench_customer_rule["mapping"] == customer_rule["mapping"]
    assert workbench_customer_rule["chainCompleteness"] == customer_rule["chainCompleteness"]
