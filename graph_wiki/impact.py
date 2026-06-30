"""Phase 4 影响分析：基于 Wiki、本体和字段/API 链路生成可审计影响报告。"""

from pathlib import Path
from typing import Any

from .models import ApiMatch, Domain, FrontendApiCall


IMPACT_VERSION = "phase4-impact-v0"


PHASE4_ACCEPTANCE_ITEMS = [
    {
        "id": "five_query_types",
        "label": "5 类高价值查询样例",
        "description": "必须覆盖字段变更、API 变更、业务点变更、规则追踪、域依赖。"
    },
    {
        "id": "structured_impacts",
        "label": "结构化影响范围",
        "description": "影响分析必须覆盖字段、API、业务点、规则/规格和域依赖。"
    },
    {
        "id": "evidence_paths",
        "label": "证据路径",
        "description": "结果必须能回到 Wiki 页面或源码路径，不能只靠自然语言猜测。"
    },
    {
        "id": "rule_spec_connection",
        "label": "rules/spec 连接",
        "description": "每个业务域必须连接到人工定稿入口 rules.md / spec.md。"
    },
    {
        "id": "non_llm",
        "label": "非 LLM 猜测",
        "description": "Phase 4 影响分析必须来自结构化产物。"
    },
]


def build_impact_analysis(
    domains: list[Domain],
    api_matches: list[ApiMatch | FrontendApiCall],
    field_map: dict,
    ontology: dict,
    wiki_root: Path,
) -> dict[str, Any]:
    """生成 Phase 4 结构化影响分析。

    该产物不调用 LLM，只编译现有 ontology、API、field-map 与 Wiki 路径。
    """
    domain_index = {domain.name or domain.id: domain for domain in domains}
    impacts = {
        "fields": _field_impacts(field_map, domains, wiki_root),
        "apis": _api_impacts(api_matches, wiki_root),
        "business_points": _business_point_impacts(domains, api_matches, wiki_root),
        "rules": _rule_spec_impacts(domains, wiki_root),
        "domain_dependencies": _domain_dependency_impacts(domains, ontology, wiki_root, field_map),
    }
    query_examples = _build_query_examples(impacts)
    coverage = {
        "query_types": sorted({item["type"] for item in query_examples}),
        "field_impacts": len(impacts["fields"]),
        "api_impacts": len(impacts["apis"]),
        "business_point_impacts": len(impacts["business_points"]),
        "rule_links": len(impacts["rules"]),
        "domain_dependency_impacts": len(impacts["domain_dependencies"]),
        "domains": sorted(domain_index.keys()),
    }
    impact = {
        "schema": {
            "version": IMPACT_VERSION,
            "source": "ontology + api-map + field-map + wiki",
            "llm_required": False,
        },
        "query_examples": query_examples,
        "impacts": impacts,
        "coverage": coverage,
    }
    impact["acceptance"] = evaluate_phase4_acceptance(impact)
    return impact


def evaluate_phase4_acceptance(impact: dict[str, Any]) -> dict[str, Any]:
    impacts = impact.get("impacts", {})
    query_types = {item.get("type") for item in impact.get("query_examples", [])}
    required_query_types = {
        "field_change", "api_change", "business_point_change",
        "rule_trace", "domain_dependency",
    }
    items = [
        _audit_item(
            "five_query_types",
            required_query_types <= query_types,
            f"query_types={sorted(query_types)}",
        ),
        _audit_item(
            "structured_impacts",
            all(impacts.get(key) for key in ("fields", "apis", "business_points", "rules", "domain_dependencies")),
            f"counts={{{', '.join(f'{key}:{len(impacts.get(key, []))}' for key in ('fields', 'apis', 'business_points', 'rules', 'domain_dependencies'))}}}",
        ),
        _audit_item(
            "evidence_paths",
            _has_evidence_paths(impact),
            "wiki/source paths present",
        ),
        _audit_item(
            "rule_spec_connection",
            all(
                item.get("rules_page")
                and item.get("spec_page")
                and item.get("code_carrier_status") == "ready"
                for item in impacts.get("rules", [])
            ),
            f"rule_links={len(impacts.get('rules', []))}",
        ),
        _audit_item(
            "non_llm",
            impact.get("schema", {}).get("llm_required") is False,
            f"llm_required={impact.get('schema', {}).get('llm_required')}",
        ),
    ]
    return {
        "status": "passed" if all(item["status"] == "passed" for item in items) else "failed",
        "items": items,
        "acceptance_items": PHASE4_ACCEPTANCE_ITEMS,
    }


def write_impact_wiki(impact: dict[str, Any], wiki_root: Path) -> None:
    wiki_root.mkdir(parents=True, exist_ok=True)
    lines = [
        "# Phase 4 — 影响分析",
        "",
        "## 董事会结论",
        "",
        "本页由结构化产物生成，不依赖 LLM 猜测。每条影响结论都应能回到 Wiki 页面、API、字段映射或源码路径。",
        "",
        "## 5 类高价值查询样例",
        "",
    ]
    for item in impact.get("query_examples", []):
        lines.append(f"### {item.get('question', '')}")
        lines.append("")
        lines.append(f"- 类型：`{item.get('type', '')}`")
        lines.append(f"- 答案入口：{item.get('answer', '')}")
        evidence = item.get("evidence", {})
        if evidence:
            lines.append("- 证据：")
            for key, value in evidence.items():
                lines.append(f"  - {key}: `{value}`")
        lines.append("")

    lines.append("## 覆盖统计")
    lines.append("")
    coverage = impact.get("coverage", {})
    for key in ("field_impacts", "api_impacts", "business_point_impacts", "rule_links", "domain_dependency_impacts"):
        lines.append(f"- {key}: {coverage.get(key, 0)}")
    lines.append("")
    lines.append(f"## 验收状态：{impact.get('acceptance', {}).get('status', 'unknown')}")
    lines.append("")
    for item in impact.get("acceptance", {}).get("items", []):
        lines.append(f"- {item.get('label')}: {item.get('status')} — {item.get('evidence')}")
    (wiki_root / "impact-analysis.md").write_text("\n".join(lines), encoding="utf-8")


def _field_impacts(field_map: dict, domains: list[Domain], wiki_root: Path) -> list[dict[str, Any]]:
    results = []
    domain_index = {domain.name or domain.id: domain for domain in domains}
    for domain, tables in (field_map or {}).items():
        for table_name, columns in tables.items():
            for column_name, entries in columns.items():
                for entry in entries:
                    if not entry.get("is_reliable", True if "confidence" not in entry else entry.get("confidence", 0) >= 0.9):
                        continue
                    affected_pages = entry.get("callers", [])
                    affected_business_points = _business_points_for_callers(
                        domain_index.get(domain),
                        affected_pages,
                        entry.get("api_function", ""),
                    )
                    results.append({
                        "domain": domain,
                        "table": table_name,
                        "column": column_name,
                        "api": f"{entry.get('api_function', '')} {entry.get('api_url', '')}".strip(),
                        "dto": f"{entry.get('dto_class', '')}.{entry.get('dto_field', '')}",
                        "entity": f"{entry.get('entity_class', '')}.{entry.get('entity_field', '')}",
                        "callers": affected_pages,
                        "affected_pages": affected_pages,
                        "affected_business_points": affected_business_points,
                        "confidence": entry.get("confidence"),
                        "evidence": {
                            "wiki_page": _path_str(wiki_root / domain / "data-flow.md"),
                            "source": "field-map.json",
                        },
                    })
    return results


def _api_impacts(api_matches: list[ApiMatch | FrontendApiCall], wiki_root: Path) -> list[dict[str, Any]]:
    results = []
    for item in api_matches:
        fe = item.frontend if hasattr(item, "frontend") else item
        be = item.backend if hasattr(item, "backend") else None
        domain = getattr(item, "domain", "") or getattr(fe, "domain", "") or "unknown"
        results.append({
            "domain": domain,
            "method": fe.http_method,
            "url": fe.url,
            "function_name": fe.function_name,
            "affected_pages": [caller.get("page", "") for caller in getattr(fe, "callers", []) or []],
            "backend": f"{be.controller_class}.{be.method_name}()" if be else "",
            "service_chain": getattr(be, "service_chain", []) if be else [],
            "source_paths": {
                "frontend": fe.source_file,
                "backend": getattr(be, "controller_file", "") if be else "",
                "wiki_page": _path_str(wiki_root / domain / "api-docs.md"),
            },
        })
    return results


def _business_point_impacts(
    domains: list[Domain],
    api_matches: list[ApiMatch | FrontendApiCall],
    wiki_root: Path,
) -> list[dict[str, Any]]:
    results = []
    for domain in domains:
        key = domain.name or domain.id
        for point in domain.business_points:
            if getattr(point, "point_type", "interaction") == "helper":
                continue
            matched_apis = _apis_for_business_point(key, point, api_matches)
            results.append({
                "domain": key,
                "business_point": point.name,
                "point_type": getattr(point, "point_type", ""),
                "entry_file": point.entry_file,
                "related_apis": [item["api"] for item in matched_apis],
                "implementation_files": _implementation_files_for_point(point, matched_apis),
                "service_chain": _service_chain_for_point(matched_apis),
                "cross_domain_calls": point.cross_domain_calls or {},
                "evidence": {
                    "wiki_page": _path_str(wiki_root / key / "code-map.md"),
                    "entry_file": point.entry_file,
                },
            })
    return results


def _apis_for_business_point(
    domain_key: str,
    point,
    api_matches: list[ApiMatch | FrontendApiCall],
) -> list[dict[str, Any]]:
    point_page = _basename(getattr(point, "entry_file", ""))
    point_tokens = set(_split_words(point.name))
    page_matches = []
    token_matches = []
    for item in api_matches:
        fe = item.frontend if hasattr(item, "frontend") else item
        be = item.backend if hasattr(item, "backend") else None
        domain = getattr(item, "domain", "") or getattr(fe, "domain", "")
        if domain != domain_key:
            continue
        api_record = {
            "api": f"{fe.http_method} {fe.url}",
            "frontend": getattr(fe, "source_file", ""),
            "backend": getattr(be, "controller_file", "") if be else "",
            "service_chain": getattr(be, "service_chain", []) if be else [],
        }
        caller_pages = {_basename(caller.get("page", "")) for caller in getattr(fe, "callers", []) or []}
        if point_page and point_page in caller_pages:
            page_matches.append(api_record)
            continue
        api_tokens = set(_split_words(getattr(fe, "function_name", "")))
        if point_tokens and api_tokens and point_tokens & api_tokens:
            token_matches.append(api_record)
    matches = page_matches or token_matches
    seen = set()
    deduped = []
    for item in matches:
        if item["api"] in seen:
            continue
        seen.add(item["api"])
        deduped.append(item)
    return deduped


def _implementation_files_for_point(point, matched_apis: list[dict[str, Any]]) -> list[str]:
    files = [getattr(point, "entry_file", "")]
    for item in matched_apis:
        files.append(item.get("frontend", ""))
        files.append(item.get("backend", ""))
    return _dedupe_nonempty(files)


def _service_chain_for_point(matched_apis: list[dict[str, Any]]) -> list[str]:
    calls = []
    for item in matched_apis:
        calls.extend(item.get("service_chain", []) or [])
    return _dedupe_nonempty(calls)


def _dedupe_nonempty(items: list[str]) -> list[str]:
    result = []
    seen = set()
    for item in items:
        if not item or item in seen:
            continue
        seen.add(item)
        result.append(item)
    return result


def _rule_spec_impacts(domains: list[Domain], wiki_root: Path) -> list[dict[str, Any]]:
    results = []
    for domain in domains:
        key = domain.name or domain.id
        code_carriers = _rule_code_carriers(domain)
        results.append({
            "domain": key,
            "rules_page": _path_str(wiki_root / key / "rules.md"),
            "spec_page": _path_str(wiki_root / key / "spec.md"),
            "summary_page": _path_str(wiki_root / key / "summary.md"),
            "code_map": _path_str(wiki_root / key / "code-map.md"),
            "code_carriers": code_carriers,
            "code_carrier_status": "ready" if code_carriers else "missing",
            "connection": "人工定稿规则/规格入口与代码本体域绑定",
            "evidence": {
                "rules_page": _path_str(wiki_root / key / "rules.md"),
                "spec_page": _path_str(wiki_root / key / "spec.md"),
                "code_map": _path_str(wiki_root / key / "code-map.md"),
            },
        })
    return results


def _domain_dependency_impacts(domains: list[Domain], ontology: dict, wiki_root: Path, field_map: dict | None = None) -> list[dict[str, Any]]:
    results = []
    seen = set()
    domain_keys = {domain.name or domain.id for domain in domains}
    for domain in domains:
        source = domain.name or domain.id
        for dep in domain.dependencies:
            target = dep.get("domain")
            if not target:
                continue
            key = (source, target)
            seen.add(key)
            results.append({
                "source_domain": source,
                "target_domain": target,
                "import_count": dep.get("import_count", 0),
                "evidence": {"wiki_page": _path_str(wiki_root / source / "dependencies.md")},
            })
    for rel in (ontology or {}).get("relationships", []):
        if rel.get("type") != "depends_on":
            continue
        source = str(rel.get("source", "")).removeprefix("domain:")
        target = str(rel.get("target", "")).removeprefix("domain:")
        key = (source, target)
        if source and target and key not in seen:
            seen.add(key)
            results.append({
                "source_domain": source,
                "target_domain": target,
                "import_count": rel.get("import_count", 0),
                "evidence": {"ontology_relationship": "depends_on"},
            })
    for source_domain, tables in (field_map or {}).items():
        for table_name in tables:
            target_domain = _table_to_domain(table_name, domain_keys)
            key = (source_domain, target_domain)
            if target_domain and source_domain != target_domain and key not in seen:
                seen.add(key)
                results.append({
                    "source_domain": source_domain,
                    "target_domain": target_domain,
                    "import_count": 0,
                    "evidence": {
                        "source": "field_map.cross_domain_table",
                        "table": table_name,
                        "wiki_page": _path_str(wiki_root / source_domain / "data-flow.md"),
                    },
                })
    if not results:
        for domain in domains:
            source = domain.name or domain.id
            if not source:
                continue
            results.append({
                "source_domain": source,
                "target_domain": "无外部域依赖",
                "import_count": 0,
                "dependency_status": "none_detected",
                "evidence": {
                    "source": "domain_dependency_scan",
                    "wiki_page": _path_str(wiki_root / source / "dependencies.md"),
                },
            })
    return results


def _build_query_examples(impacts: dict[str, list[dict[str, Any]]]) -> list[dict[str, Any]]:
    examples = []
    field = _first(impacts.get("fields", []))
    if field:
        business_points = ", ".join(field.get("affected_business_points", [])) or "—"
        examples.append({
            "type": "field_change",
            "question": f"如果字段 {field['table']}.{field['column']} 变化，会影响什么？",
            "answer": f"影响 API {field.get('api')}、DTO {field.get('dto')}、Entity {field.get('entity')}、前端页面 {', '.join(field.get('affected_pages', [])) or '—'}、业务点 {business_points}",
            "evidence": field.get("evidence", {}),
        })
    api = _first(impacts.get("apis", []))
    if api:
        examples.append({
            "type": "api_change",
            "question": f"如果接口 {api['method']} {api['url']} 变化，会影响什么？",
            "answer": f"影响前端函数 {api.get('function_name')}、页面 {', '.join(api.get('affected_pages', [])) or '—'} 和后端 {api.get('backend') or '—'}",
            "evidence": api.get("source_paths", {}),
        })
    bp = _first(impacts.get("business_points", []))
    if bp:
        examples.append({
            "type": "business_point_change",
            "question": f"如果业务点 {bp['business_point']} 变化，会影响什么？",
            "answer": f"影响业务域 {bp.get('domain')}、入口 {bp.get('entry_file') or '—'}、关联 API {', '.join(bp.get('related_apis', [])[:5]) or '—'}",
            "evidence": bp.get("evidence", {}),
        })
    rule = _first(impacts.get("rules", []))
    if rule:
        carriers = ", ".join(item.get("label", "") for item in rule.get("code_carriers", [])[:5]) or "—"
        examples.append({
            "type": "rule_trace",
            "question": f"{rule['domain']} 的业务规则和规格从哪里维护？",
            "answer": f"规则入口 {rule.get('rules_page')}，规格入口 {rule.get('spec_page')}，承载代码 {carriers}",
            "evidence": {"rules_page": rule.get("rules_page"), "spec_page": rule.get("spec_page"), "code_map": rule.get("code_map")},
        })
    dep = _first(impacts.get("domain_dependencies", []))
    if dep:
        examples.append({
            "type": "domain_dependency",
            "question": f"{dep['source_domain']} 依赖 {dep['target_domain']} 会带来什么影响？",
            "answer": f"{dep['source_domain']} 变更需关注 {dep['target_domain']}，import_count={dep.get('import_count', 0)}",
            "evidence": dep.get("evidence", {}),
        })
    return examples


def _has_evidence_paths(impact: dict[str, Any]) -> bool:
    for item in impact.get("query_examples", []):
        evidence = item.get("evidence", {})
        if not evidence:
            return False
        if not any(str(value) for value in evidence.values()):
            return False
    return True


def _audit_item(item_id: str, passed: bool, evidence: str) -> dict[str, str]:
    spec = next(item for item in PHASE4_ACCEPTANCE_ITEMS if item["id"] == item_id)
    return {
        "id": item_id,
        "label": spec["label"],
        "status": "passed" if passed else "failed",
        "evidence": evidence,
    }


def _first(items: list[dict[str, Any]]) -> dict[str, Any] | None:
    return items[0] if items else None


def _business_points_for_callers(domain: Domain | None, callers: list[str], api_function: str = "") -> list[str]:
    if not domain:
        return []
    caller_names = {_basename(item) for item in callers}
    api_tokens = set(_split_words(api_function))
    page_matches = []
    token_matches = []
    for point in domain.business_points:
        if getattr(point, "point_type", "core_action") == "helper":
            continue
        if _looks_like_backend_file(point.name):
            continue
        entry_file = getattr(point, "entry_file", "")
        point_file = _basename(entry_file)
        point_tokens = set(_split_words(point.name))
        if point_file and point_file in caller_names:
            page_matches.append(point.name)
        elif api_tokens and point_tokens and api_tokens & point_tokens:
            token_matches.append(point.name)
    matches = page_matches or token_matches
    if not matches:
        matches = _page_anchor_points(domain, caller_names)
    return sorted(set(matches))


def _rule_code_carriers(domain: Domain) -> list[dict[str, str]]:
    preferred_roles = ("controller", "service_impl", "service_api", "service", "mapper", "dao")
    carriers = []
    for role in preferred_roles:
        for anchor in domain.anchors.get(role, []) or []:
            label = anchor.get("label") or anchor.get("id") or Path(str(anchor.get("source_file", ""))).name
            carriers.append({
                "role": role,
                "label": label,
                "source_file": anchor.get("source_file", ""),
            })
    return carriers


def _page_anchor_points(domain: Domain, caller_names: set[str]) -> list[str]:
    matches = []
    for anchors in domain.anchors.values():
        for anchor in anchors or []:
            source_file = anchor.get("source_file", "")
            label = anchor.get("label") or Path(str(source_file)).name
            if not source_file or _basename(source_file) not in caller_names:
                continue
            if str(source_file).replace("\\", "/").lower().find("/views/") >= 0 or str(label).lower().endswith(".vue"):
                matches.append(label)
    return matches


def _basename(path: str) -> str:
    return Path(str(path).replace("\\", "/")).name.lower()


def _looks_like_backend_file(value: str) -> bool:
    name = str(value).lower()
    return name.endswith(".java") or name.endswith(".kt") or name.endswith(".cs")


def _split_words(value: str) -> list[str]:
    text = str(value).replace("()", "")
    chars = []
    for char in text:
        if char.isupper():
            chars.append(" ")
        chars.append(char.lower() if char.isalnum() else " ")
    return [word for word in "".join(chars).replace("_", " ").replace("-", " ").split() if word]


def _path_str(path: Path) -> str:
    return str(path).replace("\\", "/")


def _table_to_domain(table_name: str, domain_keys: set[str]) -> str:
    normalized = str(table_name).lower().replace("_", "").replace("-", "")
    for key in domain_keys:
        candidate = str(key).lower().replace("_", "").replace("-", "")
        if normalized == candidate or normalized.rstrip("s") == candidate.rstrip("s"):
            return key
    return ""
