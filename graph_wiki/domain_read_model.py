"""Domain Read Model v1 builder and quality gates.

This module turns existing Graph-Wiki analysis artifacts into the product-facing
contract used by v1 deep reading: domain -> flows -> rules -> evidence.
"""

from __future__ import annotations

import re
from datetime import datetime, timezone
from pathlib import Path
from typing import Any, Iterable

from .evidence import (
    api_ref,
    field_ref,
    is_valid_evidence_ref,
    make_evidence,
    normalize_api_path,
    normalize_source_path,
    source_ref,
)


def build_domain_read_model(
    *,
    project_id: str,
    project_name: str,
    source_root: Path,
    domains: list[Any],
    api_matches: list[Any],
    field_map: dict[str, Any],
    ontology: dict[str, Any] | None = None,
) -> dict[str, Any]:
    """Build `domain-read-model.json` payload from generated artifacts."""
    root = Path(source_root)
    evidence_index: dict[str, dict[str, Any]] = {}
    api_by_domain = _apis_by_domain(api_matches)
    field_entries_by_domain = _field_entries_by_domain(field_map)

    domain_reads = []
    for domain in domains:
        domain_key = _domain_key(domain)
        domain_apis = api_by_domain.get(domain_key, [])
        domain_field_entries = field_entries_by_domain.get(domain_key, [])
        field_rules = _build_field_rules(domain_key, domain_field_entries, domain_apis, root, evidence_index)
        flows, rules = _build_flows_and_rules(domain, domain_apis, field_rules, root, evidence_index)

        domain_evidence_refs = _domain_evidence_refs(domain, root, evidence_index)
        for flow in flows:
            domain_evidence_refs.extend(flow.get("evidenceRefs", []))
        domain_evidence_refs = _unique(domain_evidence_refs)

        domain_reads.append({
            "domainKey": domain_key,
            "displayName": _domain_display_name(domain),
            "summary": _domain_summary(domain),
            "core": _is_core_domain(domain, domain_apis, field_rules),
            "flows": flows,
            "rules": rules,
            "fieldRules": field_rules,
            "evidenceRefs": domain_evidence_refs,
            "quality": {},
        })

    _attach_ontology_evidence(ontology or {}, evidence_index)

    model = {
        "schema": {"version": "domain-read-model-v1"},
        "project": {
            "projectId": project_id,
            "projectName": project_name,
            "sourceRoot": str(source_root).replace("\\", "/"),
            "generatedAt": datetime.now(timezone.utc).replace(microsecond=0).isoformat().replace("+00:00", "Z"),
        },
        "domains": domain_reads,
        "evidenceIndex": dict(sorted(evidence_index.items())),
        "quality": {},
    }
    model["quality"] = evaluate_domain_read_model(model)
    return model


def evaluate_domain_read_model(model: dict[str, Any]) -> dict[str, Any]:
    """Evaluate the deep-reading contract separately from build success."""
    errors: list[str] = []
    warnings: list[str] = []
    evidence_index = model.get("evidenceIndex", {}) if isinstance(model.get("evidenceIndex"), dict) else {}

    for ref, evidence in evidence_index.items():
        if not is_valid_evidence_ref(ref):
            errors.append(f"EvidenceRef 格式非法: {ref}")
        if isinstance(evidence, dict) and evidence.get("status") not in {"ready", "partial", "missing"}:
            warnings.append(f"EvidenceRef 状态未知: {ref}")

    domains = model.get("domains", []) if isinstance(model.get("domains"), list) else []
    core_domains = [domain for domain in domains if domain.get("core")]
    if not core_domains:
        errors.append("没有 core=true 的业务域")

    for domain in core_domains:
        domain_key = domain.get("domainKey", "unknown")
        domain_errors_before = len(errors)
        domain_warnings_before = len(warnings)
        flows = domain.get("flows") or []
        rules = domain.get("rules") or []
        field_rules = domain.get("fieldRules") or []

        if not flows:
            errors.append(f"{domain_key}: 缺少业务流程")
        if not rules:
            errors.append(f"{domain_key}: 缺少业务规则")
        if not field_rules:
            warnings.append(f"{domain_key}: 缺少字段规则，字段链路不可深读")

        flow_ids = {flow.get("flowId") for flow in flows}
        rule_ids = {rule.get("ruleId") for rule in rules}

        for flow in flows:
            if not flow.get("steps"):
                errors.append(f"{domain_key}: flow {flow.get('flowId')} 缺少步骤")
            _check_refs(errors, warnings, evidence_index, f"{domain_key}: flow {flow.get('flowId')}", flow.get("evidenceRefs", []))
            for step in flow.get("steps") or []:
                _check_refs(errors, warnings, evidence_index, f"{domain_key}: step {step.get('stepId')}", step.get("evidenceRefs", []))
                for rule_ref in step.get("ruleRefs") or []:
                    if rule_ref not in rule_ids:
                        errors.append(f"{domain_key}: step {step.get('stepId')} 引用不存在的 rule {rule_ref}")

        for rule in rules:
            if not rule.get("statement"):
                errors.append(f"{domain_key}: rule {rule.get('ruleId')} 缺少 statement")
            for flow_ref in rule.get("flowRefs") or []:
                if flow_ref not in flow_ids:
                    errors.append(f"{domain_key}: rule {rule.get('ruleId')} 引用不存在的 flow {flow_ref}")
            _check_refs(errors, warnings, evidence_index, f"{domain_key}: rule {rule.get('ruleId')}", rule.get("evidenceRefs", []))

        for field_rule in field_rules:
            if not field_rule.get("statement"):
                errors.append(f"{domain_key}: fieldRule {field_rule.get('fieldRuleId')} 缺少 statement")
            _check_refs(
                errors,
                warnings,
                evidence_index,
                f"{domain_key}: fieldRule {field_rule.get('fieldRuleId')}",
                field_rule.get("evidenceRefs", []),
            )
            if field_rule.get("status") == "partial" and not field_rule.get("partialReason"):
                warnings.append(f"{domain_key}: fieldRule {field_rule.get('fieldRuleId')} partial 但缺少 partialReason")

        domain_status = "failed" if len(errors) > domain_errors_before else (
            "warning" if len(warnings) > domain_warnings_before else "passed"
        )
        domain.setdefault("quality", {})["deepReadingStatus"] = domain_status

    status = "failed" if errors else ("warning" if warnings else "passed")
    return {
        "deepReadingStatus": status,
        "coreDomainEvidenceStatus": "failed" if any("EvidenceRef" in item or "证据" in item for item in errors) else status,
        "ruleCorrectnessRisk": "high" if errors else ("medium" if warnings else "low"),
        "warnings": warnings,
        "errors": errors,
    }


def product_quality_for_report(domain_read_model: dict[str, Any] | None) -> dict[str, Any]:
    if not domain_read_model:
        return {
            "deepReadingStatus": "failed",
            "coreDomainEvidenceStatus": "failed",
            "ruleCorrectnessRisk": "high",
            "warnings": [],
            "errors": ["domain-read-model.json 未生成"],
        }
    return dict(domain_read_model.get("quality") or {})


def _build_flows_and_rules(
    domain: Any,
    domain_apis: list[Any],
    field_rules: list[dict[str, Any]],
    root: Path,
    evidence_index: dict[str, dict[str, Any]],
) -> tuple[list[dict[str, Any]], list[dict[str, Any]]]:
    domain_key = _domain_key(domain)
    flows: list[dict[str, Any]] = []
    rules: list[dict[str, Any]] = []
    field_rules_by_api = _field_rules_by_api(field_rules)

    if domain_apis:
        for order, api_item in enumerate(domain_apis, start=1):
            api = _api_view(api_item)
            action_slug = _slug(api["function"] or f"{api['method']}-{api['url']}")
            flow_id = f"{domain_key}.{action_slug}"
            rule_id = f"{flow_id}.business-rule"
            api_evidence_ref = _register_api_evidence(api_item, root, evidence_index)
            source_refs = _register_api_source_evidence(api_item, root, evidence_index)
            field_refs = [ref for rule in field_rules_by_api.get(api_evidence_ref, []) for ref in rule.get("evidenceRefs", [])]
            step_refs = _unique([api_evidence_ref, *source_refs])

            steps = [{
                "stepId": f"{flow_id}.step1",
                "order": 1,
                "text": f"前端/调用方发起 {api['method']} {api['url']}，进入 {_clean_title(api['function'])}。",
                "evidenceRefs": step_refs,
                "ruleRefs": [rule_id],
                "status": "ready" if step_refs else "partial",
                "confidence": api["confidence"],
            }]
            if api.get("controller") or api.get("backendMethod"):
                steps.append({
                    "stepId": f"{flow_id}.step2",
                    "order": 2,
                    "text": f"后端 Controller {api.get('controller') or 'unknown'}.{api.get('backendMethod') or 'unknown'} 接收请求并分派业务处理。",
                    "evidenceRefs": _unique([api_evidence_ref, *[ref for ref in source_refs if ref.startswith("source:")]]),
                    "ruleRefs": [rule_id],
                    "status": "ready",
                    "confidence": api["confidence"],
                })
            if api.get("serviceChain"):
                steps.append({
                    "stepId": f"{flow_id}.step3",
                    "order": 3,
                    "text": "Service/Mapper 执行业务处理：" + "，".join(api["serviceChain"][:3]) + "。",
                    "evidenceRefs": _unique([api_evidence_ref, *source_refs]),
                    "ruleRefs": [rule_id],
                    "status": "ready",
                    "confidence": max(0.65, api["confidence"] - 0.05),
                })
            if field_refs:
                steps.append({
                    "stepId": f"{flow_id}.step4",
                    "order": 4,
                    "text": "关键请求字段映射到 DTO / Entity / 数据库字段，形成可追踪字段链路。",
                    "evidenceRefs": _unique([api_evidence_ref, *field_refs]),
                    "ruleRefs": [rule_id],
                    "status": "ready",
                    "confidence": max(0.65, api["confidence"] - 0.05),
                })

            flow_evidence = _unique([api_evidence_ref, *source_refs, *field_refs])
            default_rule = {
                "ruleId": rule_id,
                "statement": _business_rule_statement(api, domain),
                "ruleType": _rule_type(api),
                "flowRefs": [flow_id],
                "evidenceRefs": _unique([api_evidence_ref, *source_refs]),
                "status": "ready" if source_refs else "partial",
                "confidence": max(0.65, api["confidence"] - 0.05),
                "review": {"state": "machine_draft", "reviewedBy": "", "reviewedAt": ""},
            }
            code_rules = _extract_code_rules(domain_key, flow_id, api, root, evidence_index)
            rule_refs = [default_rule["ruleId"], *[rule["ruleId"] for rule in code_rules]]
            for step in steps:
                step["ruleRefs"] = rule_refs
            flows.append({
                "flowId": flow_id,
                "title": _clean_title(api["function"] or api["backendMethod"] or api["url"]),
                "summary": f"{_domain_display_name(domain)}通过 {api['method']} {api['url']} 完成该业务动作。",
                "steps": steps,
                "evidenceRefs": flow_evidence,
                "status": "ready" if flow_evidence else "partial",
                "confidence": api["confidence"],
            })
            rules.append(default_rule)
            rules.extend(code_rules)
    else:
        for order, point in enumerate(_business_points(domain), start=1):
            code_name = _get(point, "name", "businessPoint")
            if _get(point, "point_type", "interaction") == "helper":
                continue
            action_slug = _slug(code_name)
            flow_id = f"{domain_key}.{action_slug}"
            rule_id = f"{flow_id}.business-rule"
            refs = []
            entry_file = _get(point, "entry_file", "")
            if entry_file:
                refs.append(_register_source_evidence(entry_file, code_name, root, evidence_index))
            flows.append({
                "flowId": flow_id,
                "title": _clean_title(_get(point, "display_name", "") or code_name),
                "summary": f"从业务点 {code_name} 识别出的业务流程。",
                "steps": [{
                    "stepId": f"{flow_id}.step1",
                    "order": 1,
                    "text": f"执行业务点 {code_name}。",
                    "evidenceRefs": refs,
                    "ruleRefs": [rule_id],
                    "status": "ready" if refs else "partial",
                    "confidence": 0.72,
                }],
                "evidenceRefs": refs,
                "status": "ready" if refs else "partial",
                "confidence": 0.72,
            })
            rules.append({
                "ruleId": rule_id,
                "statement": f"{_clean_title(code_name)}必须以识别到的代码入口为准执行，缺少人工规则时保持机器草稿状态。",
                "ruleType": "business",
                "flowRefs": [flow_id],
                "evidenceRefs": refs,
                "status": "ready" if refs else "partial",
                "confidence": 0.68,
                "review": {"state": "machine_draft", "reviewedBy": "", "reviewedAt": ""},
            })

    return flows, rules


def _build_field_rules(
    domain_key: str,
    entries: list[dict[str, Any]],
    domain_apis: list[Any],
    root: Path,
    evidence_index: dict[str, dict[str, Any]],
) -> list[dict[str, Any]]:
    api_lookup = {_api_view(api)["url"]: api for api in domain_apis}
    api_lookup.update({normalize_api_path(_api_view(api)["url"]): api for api in domain_apis})
    rules = []
    seen = set()
    for entry in entries:
        table = entry.get("db_table") or entry.get("table") or "unknown_table"
        column = entry.get("db_column") or entry.get("column") or "unknown_column"
        api_url = normalize_api_path(entry.get("api_url") or entry.get("url") or "/")
        api_item = api_lookup.get(api_url)
        api = _api_view(api_item) if api_item else {
            "method": _guess_method(api_url),
            "url": api_url,
            "function": entry.get("api_function", ""),
            "confidence": float(entry.get("confidence", 0.7) or 0.7),
        }
        field_evidence_ref = _register_field_evidence(table, column, entry, evidence_index)
        api_evidence_ref = _register_api_evidence(api_item, root, evidence_index) if api_item else _register_raw_api_evidence(api, evidence_index)
        evidence_refs = [field_evidence_ref, api_evidence_ref]
        chain = []

        callers = entry.get("callers") or entry.get("frontendCallers") or []
        if callers:
            frontend_ref = _register_source_evidence(str(callers[0]), entry.get("dto_field") or column, root, evidence_index)
            chain.append({"layer": "frontend", "ref": frontend_ref})
            evidence_refs.append(frontend_ref)
        chain.append({"layer": "api", "ref": api_evidence_ref})

        if api_item:
            for ref in _register_api_source_evidence(api_item, root, evidence_index):
                if ref.startswith("source:") and "Controller" in ref:
                    chain.append({"layer": "controller", "ref": ref})
                evidence_refs.append(ref)

        if entry.get("dto_file"):
            dto_ref = _register_source_evidence(entry["dto_file"], entry.get("dto_field") or column, root, evidence_index)
            chain.append({"layer": "dto", "ref": dto_ref})
            evidence_refs.append(dto_ref)
        if entry.get("entity_file"):
            entity_ref = _register_source_evidence(entry["entity_file"], entry.get("entity_field") or column, root, evidence_index)
            chain.append({"layer": "entity", "ref": entity_ref})
            evidence_refs.append(entity_ref)
        chain.append({"layer": "db", "ref": field_evidence_ref})

        confidence = float(entry.get("confidence", api.get("confidence", 0.7)) or 0.7)
        required_layers = {"api", "db"}
        present_layers = {item["layer"] for item in chain}
        missing_layers = sorted(required_layers - present_layers)
        optional_missing = [layer for layer in ["frontend", "controller", "dto", "entity"] if layer not in present_layers]
        status = "ready" if confidence >= 0.9 and not missing_layers else "partial"
        partial_reason = ""
        if status == "partial" or optional_missing:
            partial_reason = "缺少可解析层级: " + ", ".join(missing_layers + optional_missing)

        rule_id = f"{domain_key}.field.{_slug(table)}.{_slug(column)}"
        dedupe_key = (rule_id, api_evidence_ref)
        if dedupe_key in seen:
            continue
        seen.add(dedupe_key)
        rules.append({
            "fieldRuleId": rule_id,
            "fieldId": f"{table}.{column}",
            "statement": _field_rule_statement(entry, table, column),
            "chain": chain,
            "mapping": _field_mapping(entry, api, table, column, callers),
            "chainCompleteness": {
                "presentLayers": sorted(present_layers),
                "missingRequiredLayers": missing_layers,
                "missingOptionalLayers": optional_missing,
            },
            "evidenceRefs": _unique(evidence_refs),
            "status": status,
            "confidence": round(confidence, 4),
            "partialReason": partial_reason if status == "partial" else "",
        })
    return rules



def _field_rule_mapping(entry: dict[str, Any], api: dict[str, Any], api_evidence_ref: str, callers: list[Any]) -> dict[str, Any]:
    """Preserve field-chain metadata for Workbench without requiring raw field-map.json."""
    return {
        "api": {
            "ref": api_evidence_ref,
            "method": api.get("method", ""),
            "url": api.get("url", ""),
            "functionName": entry.get("api_function") or api.get("function", ""),
        },
        "dto": {
            "className": entry.get("dto_class", ""),
            "field": entry.get("dto_field", ""),
            "file": entry.get("dto_file", ""),
        },
        "entity": {
            "className": entry.get("entity_class", ""),
            "field": entry.get("entity_field", ""),
            "file": entry.get("entity_file", ""),
        },
        "frontendCallers": [
            caller.get("page", "") if isinstance(caller, dict) else str(caller)
            for caller in callers
            if caller
        ],
    }

def _check_refs(
    errors: list[str],
    warnings: list[str],
    evidence_index: dict[str, Any],
    label: str,
    refs: Iterable[str],
) -> None:
    refs = list(refs or [])
    if not refs:
        errors.append(f"{label} 缺少 evidenceRefs")
        return
    for ref in refs:
        if not is_valid_evidence_ref(ref):
            errors.append(f"{label} EvidenceRef 格式非法: {ref}")
            continue
        if ref not in evidence_index:
            errors.append(f"{label} 引用不存在的 evidenceRef: {ref}")
            continue
        if isinstance(evidence_index[ref], dict) and evidence_index[ref].get("status") == "missing":
            warnings.append(f"{label} 引用了 missing evidence: {ref}")


def _apis_by_domain(api_matches: list[Any]) -> dict[str, list[Any]]:
    result: dict[str, list[Any]] = {}
    for item in api_matches or []:
        domain = _get(item, "domain", "")
        if not domain and hasattr(item, "frontend"):
            domain = _get(getattr(item, "frontend"), "domain", "")
        if domain:
            result.setdefault(domain, []).append(item)
    return result


def _field_entries_by_domain(field_map: dict[str, Any]) -> dict[str, list[dict[str, Any]]]:
    result: dict[str, list[dict[str, Any]]] = {}
    for domain, tables in (field_map or {}).items():
        if not isinstance(tables, dict):
            continue
        for table, columns in tables.items():
            if not isinstance(columns, dict):
                continue
            for column, mappings in columns.items():
                for mapping in mappings if isinstance(mappings, list) else []:
                    entry = dict(mapping)
                    entry.setdefault("db_table", table)
                    entry.setdefault("db_column", column)
                    result.setdefault(domain, []).append(entry)
    return result


def _api_view(api_item: Any) -> dict[str, Any]:
    if api_item is None:
        return {"method": "GET", "url": "/", "function": "", "confidence": 0.7, "serviceChain": []}
    frontend = getattr(api_item, "frontend", api_item)
    backend = getattr(api_item, "backend", None)
    method = _get(frontend, "http_method", "") or _get(backend, "http_method", "") or "GET"
    url = normalize_api_path(_get(frontend, "url", "") or _get(backend, "url", "") or "/")
    return {
        "method": method.upper(),
        "url": url,
        "function": _get(frontend, "function_name", "") or _get(backend, "method_name", ""),
        "frontendSource": _get(frontend, "source_file", ""),
        "callers": _get(frontend, "callers", []) or [],
        "controllerFile": _get(backend, "controller_file", ""),
        "controller": _get(backend, "controller_class", ""),
        "backendMethod": _get(backend, "method_name", ""),
        "serviceChain": _get(backend, "service_chain", []) or [],
        "paramType": _get(backend, "param_type", ""),
        "confidence": float(_get(api_item, "match_confidence", 0.85) or 0.85),
    }


def _register_api_evidence(api_item: Any, root: Path, evidence_index: dict[str, dict[str, Any]]) -> str:
    api = _api_view(api_item)
    return _register_raw_api_evidence(api, evidence_index)


def _register_raw_api_evidence(api: dict[str, Any], evidence_index: dict[str, dict[str, Any]]) -> str:
    ref = api_ref(api.get("method"), api.get("url"))
    evidence_index.setdefault(ref, make_evidence(
        ref,
        label=f"{api.get('method', 'GET')} {api.get('url', '/')}",
        path="api-map.json",
        source_path=api.get("frontendSource", ""),
        confidence=api.get("confidence", 0.85),
        status="ready",
    ))
    return ref


def _register_api_source_evidence(api_item: Any, root: Path, evidence_index: dict[str, dict[str, Any]]) -> list[str]:
    api = _api_view(api_item)
    refs = []
    if api.get("frontendSource"):
        refs.append(_register_source_evidence(api["frontendSource"], api.get("function") or "api", root, evidence_index))
    for caller in api.get("callers") or []:
        page = caller.get("page", "") if isinstance(caller, dict) else str(caller)
        if page:
            refs.append(_register_source_evidence(page, api.get("function") or "api", root, evidence_index))
    if api.get("controllerFile"):
        refs.append(_register_source_evidence(api["controllerFile"], api.get("backendMethod") or "controller", root, evidence_index))
    return _unique(refs)


def _register_source_evidence(path: str | Path, symbol: str | int | None, root: Path, evidence_index: dict[str, dict[str, Any]]) -> str:
    normalized = _relative_or_normalized_path(path, root)
    ref = source_ref(normalized, symbol)
    resolved = _resolve_source_path(root, normalized)
    evidence_index.setdefault(ref, make_evidence(
        ref,
        label=f"{normalized}#{symbol or 'file'}",
        path=normalized,
        source_path=normalized,
        confidence=1.0 if resolved else 0.6,
        status="ready" if resolved else "partial",
    ))
    return ref


def _register_field_evidence(table: str, column: str, entry: dict[str, Any], evidence_index: dict[str, dict[str, Any]]) -> str:
    ref = field_ref(table, column)
    evidence_index.setdefault(ref, make_evidence(
        ref,
        label=f"{table}.{column}",
        path="field-map.json",
        source_path=entry.get("entity_file", ""),
        confidence=float(entry.get("confidence", 0.8) or 0.8),
        status="ready",
    ))
    return ref


def _domain_evidence_refs(domain: Any, root: Path, evidence_index: dict[str, dict[str, Any]]) -> list[str]:
    refs = []
    for role, anchors in (_get(domain, "anchors", {}) or {}).items():
        for anchor in anchors if isinstance(anchors, list) else []:
            path = anchor.get("source_file", "")
            label = anchor.get("label") or role
            if path:
                refs.append(_register_source_evidence(path, label, root, evidence_index))
    return refs


def _attach_ontology_evidence(ontology: dict[str, Any], evidence_index: dict[str, dict[str, Any]]) -> None:
    for idx, rel in enumerate(ontology.get("relationships", []) if isinstance(ontology, dict) else []):
        rel_id = rel.get("id") or f"rel-{idx + 1}"
        ref = f"ontology:{rel_id}"
        evidence_index.setdefault(ref, make_evidence(
            ref,
            label=rel.get("type", "ontology relationship"),
            path="ontology.json",
            source_path="ontology.json",
            confidence=0.8,
            status="ready",
        ))


def _field_rules_by_api(field_rules: list[dict[str, Any]]) -> dict[str, list[dict[str, Any]]]:
    result: dict[str, list[dict[str, Any]]] = {}
    for rule in field_rules:
        for ref in rule.get("evidenceRefs", []):
            if ref.startswith("api:"):
                result.setdefault(ref, []).append(rule)
    return result


def _extract_code_rules(
    domain_key: str,
    flow_id: str,
    api: dict[str, Any],
    root: Path,
    evidence_index: dict[str, dict[str, Any]],
) -> list[dict[str, Any]]:
    """Extract near-business rules from service implementation source using safe heuristics."""
    rules: list[dict[str, Any]] = []
    method_names = [api.get("backendMethod", "")]
    for service_call in api.get("serviceChain", []) or []:
        match = re.search(r"\.(?P<method>\w+)\s*\(", service_call)
        if match:
            method_names.append(match.group("method"))
    method_names = [name for name in _unique(method_names) if name]
    if not method_names:
        return rules

    service_files = [path for path in root.rglob("*ServiceImpl.java") if path.is_file()]
    for service_file in service_files:
        try:
            source = service_file.read_text(encoding="utf-8", errors="ignore")
        except OSError:
            continue
        for method_name in method_names:
            body = _extract_java_method_body(source, method_name)
            if not body:
                continue
            source_path = _relative_or_normalized_path(service_file, root)
            method_ref = _register_source_evidence(source_path, method_name, root, evidence_index)
            for statement, rule_type, anchor in _rule_statements_from_method_body(body, source, method_name):
                rule_id = f"{domain_key}.{_slug(method_name)}.{_slug(anchor)}"
                anchor_ref = _register_source_evidence(source_path, anchor or method_name, root, evidence_index)
                rules.append({
                    "ruleId": rule_id,
                    "statement": statement,
                    "ruleType": rule_type,
                    "flowRefs": [flow_id],
                    "evidenceRefs": _unique([method_ref, anchor_ref]),
                    "status": "ready",
                    "confidence": 0.82,
                    "review": {"state": "machine_draft", "reviewedBy": "", "reviewedAt": ""},
                })
    return _dedupe_rules(rules)


def _rule_statements_from_method_body(body: str, source: str, method_name: str) -> list[tuple[str, str, str]]:
    statements: list[tuple[str, str, str]] = []
    lowered = body.lower()
    if "checkstock" in lowered:
        statements.append(("执行业务动作前必须校验库存是否充足。", "validation", "checkStock"))
    if re.search(r"validate\w*\s*\(", body):
        for call in re.findall(r"(validate\w*)\s*\(", body):
            helper_body = _extract_java_method_body(source, call)
            if "quantity" in call.lower() or "quantity" in helper_body.lower():
                statements.append(("订单数量必须存在且大于 0。", "validation", call))
            else:
                statements.append((f"提交前必须执行 {call} 校验。", "validation", call))
    for condition in re.findall(r"if\s*\(([^)]{1,160})\)\s*\{?\s*throw\s+new\s+(\w+)", body):
        expression, error_type = condition
        readable = _readable_condition(expression)
        statements.append((f"当 {readable} 时必须阻断流程并抛出 {error_type}。", "validation", "if-condition"))
    return _dedupe_statement_tuples(statements)


def _extract_java_method_body(source: str, method_name: str) -> str:
    match = re.search(rf"\b{re.escape(method_name)}\s*\(", source)
    if not match:
        return ""
    open_paren = source.find("(", match.start())
    close_paren = _find_matching(source, open_paren, "(", ")")
    if close_paren == -1:
        return ""
    open_brace = source.find("{", close_paren)
    if open_brace == -1:
        return ""
    close_brace = _find_matching(source, open_brace, "{", "}")
    if close_brace == -1:
        return ""
    return source[open_brace + 1:close_brace]


def _find_matching(source: str, start: int, open_char: str, close_char: str) -> int:
    if start < 0:
        return -1
    depth = 0
    for idx in range(start, len(source)):
        char = source[idx]
        if char == open_char:
            depth += 1
        elif char == close_char:
            depth -= 1
            if depth == 0:
                return idx
    return -1


def _readable_condition(expression: str) -> str:
    text = " ".join(expression.split())
    replacements = {
        "== null": "为空",
        "!= null": "不为空",
        "<= 0": "小于等于 0",
        ">= 0": "大于等于 0",
        "&&": "且",
        "||": "或",
    }
    for source, target in replacements.items():
        text = text.replace(source, target)
    return text


def _dedupe_rules(rules: list[dict[str, Any]]) -> list[dict[str, Any]]:
    result = []
    seen = set()
    for rule in rules:
        key = (rule.get("ruleId"), rule.get("statement"))
        if key in seen:
            continue
        seen.add(key)
        result.append(rule)
    return result


def _dedupe_statement_tuples(items: list[tuple[str, str, str]]) -> list[tuple[str, str, str]]:
    result = []
    seen = set()
    for item in items:
        if item[0] in seen:
            continue
        seen.add(item[0])
        result.append(item)
    return result


def _business_rule_statement(api: dict[str, Any], domain: Any) -> str:
    title = _clean_title(api.get("function") or api.get("backendMethod") or api.get("url"))
    service = "，".join(api.get("serviceChain", [])[:2])
    if service:
        return f"{title}必须经由后端业务服务 {service} 处理，不能只依据前端表单或 Wiki 占位描述判断业务规则。"
    return f"{title}必须经由 {api.get('method')} {api.get('url')} 的代码证据确认业务含义。"


def _field_rule_statement(entry: dict[str, Any], table: str, column: str) -> str:
    dto = ".".join(x for x in [entry.get("dto_class"), entry.get("dto_field")] if x)
    entity = ".".join(x for x in [entry.get("entity_class"), entry.get("entity_field")] if x)
    pieces = []
    if entry.get("api_function") or entry.get("api_url"):
        pieces.append(f"接口 {entry.get('api_function') or ''} {entry.get('api_url') or ''}".strip())
    if dto:
        pieces.append(f"DTO {dto}")
    if entity:
        pieces.append(f"Entity {entity}")
    pieces.append(f"数据库字段 {table}.{column}")
    return " 经由 ".join(pieces) + " 形成字段链路。"


def _field_mapping(
    entry: dict[str, Any],
    api: dict[str, Any],
    table: str,
    column: str,
    callers: list[Any],
) -> dict[str, Any]:
    """Expose machine-readable field-chain metadata for Workbench consumers."""
    return {
        "api": {
            "method": api.get("method", ""),
            "url": api.get("url", ""),
            "functionName": entry.get("api_function") or api.get("function", ""),
        },
        "dto": {
            "className": entry.get("dto_class", ""),
            "field": entry.get("dto_field", ""),
            "sourcePath": entry.get("dto_file", ""),
        },
        "entity": {
            "className": entry.get("entity_class", ""),
            "field": entry.get("entity_field", ""),
            "sourcePath": entry.get("entity_file", ""),
        },
        "database": {
            "table": table,
            "column": column,
        },
        "frontendCallers": [
            caller.get("page", "") if isinstance(caller, dict) else str(caller)
            for caller in callers
            if caller
        ],
    }


def _rule_type(api: dict[str, Any]) -> str:
    text = " ".join([api.get("function", ""), api.get("backendMethod", ""), " ".join(api.get("serviceChain", []))]).lower()
    if any(token in text for token in ("validate", "check", "ensure")):
        return "validation"
    if any(token in text for token in ("approve", "reject", "cancel", "submit")):
        return "state_transition"
    if any(token in text for token in ("calculate", "calc", "price", "amount")):
        return "calculation"
    return "business"


def _relative_or_normalized_path(path: str | Path, root: Path) -> str:
    raw = Path(str(path))
    try:
        if raw.is_absolute():
            return normalize_source_path(raw.relative_to(root))
    except ValueError:
        pass
    return normalize_source_path(path)


def _resolve_source_path(root: Path, normalized_path: str) -> Path | None:
    path = Path(normalized_path)
    candidates = []
    if path.is_absolute():
        candidates.append(path)
    else:
        candidates.append(root / normalized_path)
    for candidate in candidates:
        if candidate.exists():
            return candidate
    suffix = normalize_source_path(normalized_path)
    name = Path(suffix).name
    if not name or name == "unknown":
        return None
    for candidate in root.rglob(name):
        if normalize_source_path(candidate).endswith(suffix) or normalize_source_path(candidate.relative_to(root)).endswith(suffix):
            return candidate
    return None


def _domain_key(domain: Any) -> str:
    return _get(domain, "name", "") or _get(domain, "id", "unknown") or "unknown"


def _domain_display_name(domain: Any) -> str:
    return _get(domain, "display_name", "") or _domain_key(domain)


def _domain_summary(domain: Any) -> str:
    return _get(domain, "description", "") or f"{_domain_display_name(domain)}负责该业务域的流程、规则和证据链阅读。"


def _business_points(domain: Any) -> list[Any]:
    return list(_get(domain, "business_points", []) or [])


def _is_core_domain(domain: Any, domain_apis: list[Any], field_rules: list[dict[str, Any]]) -> bool:
    points = _business_points(domain)
    if any(_get(point, "point_type", "") == "core_action" for point in points):
        return True
    return bool(domain_apis or field_rules or _get(domain, "anchors", {}))


def _get(obj: Any, key: str, default: Any = None) -> Any:
    if obj is None:
        return default
    if isinstance(obj, dict):
        return obj.get(key, default)
    return getattr(obj, key, default)


def _clean_title(text: str) -> str:
    text = str(text or "业务动作")
    text = text.replace("()", "")
    text = re.sub(r"([a-z])([A-Z])", r"\1 \2", text)
    text = text.replace("_", " ").replace("-", " ").strip()
    return text[:1].upper() + text[1:] if text else "业务动作"


def _slug(text: str) -> str:
    slug = re.sub(r"[^A-Za-z0-9\u4e00-\u9fff]+", "-", str(text or "item")).strip("-").lower()
    return slug or "item"


def _guess_method(api_url: str) -> str:
    return "GET" if any(token in api_url.lower() for token in ("list", "query", "search", "get")) else "POST"


def _unique(values: Iterable[str]) -> list[str]:
    result = []
    seen = set()
    for value in values:
        if value and value not in seen:
            seen.add(value)
            result.append(value)
    return result
