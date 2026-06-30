from pathlib import Path

import pytest

from graph_wiki.domain_read_model import build_domain_read_model, evaluate_domain_read_model
from graph_wiki.label import LabelConfig, _build_prompt
from graph_wiki.models import BusinessPoint, Domain
from graph_wiki.reuse import build_manifest, filter_extraction_for_wiki
from graph_wiki.trust import filter_safe_paths, is_sensitive_path


def _domain_with_anchor(source_file: str) -> Domain:
    return Domain(
        id="security",
        name="security",
        display_name="安全域",
        anchors={"service_impl": [{"label": Path(source_file).name, "source_file": source_file}]},
        business_points=[BusinessPoint(name="secureAction()", entry_file=source_file)],
    )


def test_sensitive_path_policy_blocks_secret_like_files_but_allows_samples():
    assert is_sensitive_path(".env")
    assert is_sensitive_path("config/prod.credentials.yml")
    assert is_sensitive_path("src/main/resources/payment-token.yaml")
    assert is_sensitive_path("private-key.pem")

    assert not is_sensitive_path(".env.example")
    assert not is_sensitive_path("tests/fixtures/payment-token.sample.yaml")
    assert not is_sensitive_path("docs/secret-management.md")

    paths = [Path(".env"), Path("src/OrderService.java"), Path("token.sample")]
    assert filter_safe_paths(paths) == [Path("src/OrderService.java"), Path("token.sample")]


def test_wiki_extraction_and_manifest_exclude_sensitive_sources(tmp_path: Path):
    safe = tmp_path / "src" / "OrderService.java"
    secret = tmp_path / ".env"
    safe.parent.mkdir(parents=True)
    safe.write_text("class OrderService {}", encoding="utf-8")
    secret.write_text("TOKEN=real-secret", encoding="utf-8")

    extraction = {
        "nodes": [
            {"id": "safe", "label": "OrderService.java", "source_file": str(safe)},
            {"id": "secret", "label": ".env", "source_file": str(secret)},
        ],
        "edges": [{"source": "safe", "target": "secret", "relation": "imports"}],
    }

    filtered = filter_extraction_for_wiki(extraction, tmp_path)
    assert [node["id"] for node in filtered["nodes"]] == ["safe"]
    assert filtered["edges"] == []

    manifest = build_manifest([safe, secret], [])
    assert str(safe) in manifest["files"]
    assert str(secret) not in manifest["files"]
    assert manifest["meta"]["total_files"] == 1


def test_llm_prompt_sampling_skips_sensitive_service_sources(tmp_path: Path):
    secret_file = ".env"
    path = tmp_path / secret_file
    path.write_text("REAL_TOKEN=should-not-leak\n", encoding="utf-8")

    prompt = _build_prompt(_domain_with_anchor(secret_file), tmp_path, LabelConfig())

    assert "should-not-leak" not in prompt
    assert ".env" in prompt  # anchor label is fine; source sample is not


def test_domain_read_model_rejects_sensitive_source_evidence(tmp_path: Path):
    secret_file = ".env"
    path = tmp_path / secret_file
    path.write_text("REAL_TOKEN=should-not-leak\n", encoding="utf-8")

    model = build_domain_read_model(
        project_id="fixture",
        project_name="Fixture",
        source_root=tmp_path,
        domains=[_domain_with_anchor(secret_file)],
        api_matches=[],
        field_map={},
        ontology={},
    )

    assert model["quality"]["deepReadingStatus"] == "failed"
    assert any("敏感文件" in item for item in model["quality"]["errors"])
    assert all(".env" not in ref or model["evidenceIndex"][ref]["status"] == "missing" for ref in model["evidenceIndex"])


def test_domain_read_model_flags_unresolvable_source_refs():
    model = {
        "schema": {"version": "domain-read-model-v1"},
        "project": {"sourceRoot": "/definitely/missing/root"},
        "domains": [
            {
                "domainKey": "order",
                "core": True,
                "flows": [{"flowId": "order.create", "steps": [{"stepId": "s1", "evidenceRefs": ["source:missing/Order.java#create"], "ruleRefs": ["r1"]}], "evidenceRefs": ["source:missing/Order.java#create"]}],
                "rules": [{"ruleId": "r1", "flowRefs": ["order.create"], "statement": "rule", "evidenceRefs": ["source:missing/Order.java#create"]}],
                "fieldRules": [],
                "evidenceRefs": ["source:missing/Order.java#create"],
                "quality": {},
            }
        ],
        "evidenceIndex": {
            "source:missing/Order.java#create": {
                "id": "source:missing/Order.java#create",
                "type": "source",
                "path": "missing/Order.java",
                "sourcePath": "missing/Order.java",
                "status": "partial",
            }
        },
    }

    quality = evaluate_domain_read_model(model)

    assert quality["deepReadingStatus"] == "failed"
    assert any("源文件不可解析" in item for item in quality["errors"])
    assert any("源文件未解析确认" in item for item in quality["warnings"])


def test_domain_read_model_evaluator_rejects_existing_sensitive_config_source(tmp_path: Path):
    sensitive = tmp_path / "backend" / "src" / "main" / "resources" / "application-prod.yml"
    sensitive.parent.mkdir(parents=True)
    sensitive.write_text("token: real-secret\n", encoding="utf-8")
    ref = "source:backend/src/main/resources/application-prod.yml#token"
    model = {
        "schema": {"version": "domain-read-model-v1"},
        "project": {"sourceRoot": str(tmp_path)},
        "domains": [
            {
                "domainKey": "order",
                "core": True,
                "flows": [{"flowId": "order.create", "steps": [{"stepId": "s1", "evidenceRefs": [ref], "ruleRefs": ["r1"]}], "evidenceRefs": [ref]}],
                "rules": [{"ruleId": "r1", "flowRefs": ["order.create"], "statement": "rule", "evidenceRefs": [ref]}],
                "fieldRules": [],
                "evidenceRefs": [ref],
                "quality": {},
            }
        ],
        "evidenceIndex": {
            ref: {
                "id": ref,
                "type": "source",
                "path": "backend/src/main/resources/application-prod.yml",
                "sourcePath": "backend/src/main/resources/application-prod.yml",
                "status": "ready",
            }
        },
    }

    quality = evaluate_domain_read_model(model)

    assert quality["deepReadingStatus"] == "failed"
    assert any("敏感文件" in item for item in quality["errors"])
