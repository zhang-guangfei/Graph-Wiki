"""Wiki 导出：生成 Obsidian 兼容的双向链接 Markdown 文档库"""

import json
import re
import shutil
from pathlib import Path
from datetime import datetime
from collections import Counter
from typing import Any

from .models import Domain, ApiMatch, BusinessPoint, FrontendApiCall


TECHNICAL_DOMAIN_NAMES = {
    "controller", "controllers", "service", "services", "serviceimpl",
    "service-impl", "mapper", "dao", "common", "utils", "util",
    "components", "component", "api", "web", "config", "handler",
    "v1", "v2", "v3", "v4", "v5",
}

DOMAIN_DISPLAY_NAMES = {
    "svn": "SVN操作",
    "repository": "仓库管理",
    "requirement": "需求管理",
    "order": "订单管理",
    "inventory": "库存管理",
    "customer": "客户管理",
    "invoice": "发票管理",
    "purchase": "采购管理",
    "supplier": "供应商管理",
    "product": "产品管理",
    "workflow": "流程管理",
}

POINT_TYPE_LABELS = {
    "core_action": "核心业务动作",
    "interaction": "查询/交互动作",
    "helper": "辅助方法",
}


def export_wiki(
    domains: list[Domain],
    api_matches: list[ApiMatch],
    field_map: dict,
    output_dir: Path = Path("wiki"),
    clean_stale: bool = False,
    ontology: dict | None = None,
    domain_read_model: dict | None = None,
):
    output_dir.mkdir(parents=True, exist_ok=True)
    if clean_stale:
        _archive_stale_domain_dirs(domains, output_dir)

    read_model_domains = _read_model_domains_by_key(domain_read_model or {})
    evidence_index = (domain_read_model or {}).get("evidenceIndex", {})
    _write_index(domains, output_dir, domain_read_model=domain_read_model)
    _write_api_index(api_matches, output_dir)

    for domain in domains:
        dir_name = domain.name or domain.id
        domain_dir = output_dir / dir_name
        domain_dir.mkdir(parents=True, exist_ok=True)

        read_model_domain = read_model_domains.get(domain.name or domain.id) or read_model_domains.get(domain.id)
        _write_summary(domain, api_matches, domain_dir, read_model_domain=read_model_domain)
        _write_code_map(domain, domain_dir)
        _write_api_docs(domain, api_matches, domain_dir)
        _write_dependencies(domain, domain_dir, domains=domains, ontology=ontology or {}, field_map=field_map)
        _write_ontology_docs(domain, ontology or {}, domain_dir)

        if domain.id in field_map or domain.name in field_map:
            _write_data_flow(domain, field_map, domain_dir)
        if read_model_domain:
            _write_domain_read_model_pages(read_model_domain, evidence_index, domain_dir)

        # 占位文件（人工填写）
        for fname in ("rules.md", "spec.md"):
            p = domain_dir / fname
            if not p.exists():
                p.write_text(
                    f"# {domain.name or domain.id} — {fname.replace('.md', '').upper()}\n\n*待填写*\n",
                    encoding="utf-8",
                )


def _archive_stale_domain_dirs(domains: list[Domain], output_dir: Path) -> None:
    current_names = {domain.name or domain.id for domain in domains}
    stale_root = output_dir / "_stale" / datetime.now().strftime("%Y%m%d%H%M%S")
    for child in list(output_dir.iterdir()):
        if not child.is_dir():
            continue
        if child.name.startswith("_") or child.name in current_names:
            continue
        stale_root.mkdir(parents=True, exist_ok=True)
        target = stale_root / child.name
        counter = 1
        while target.exists():
            target = stale_root / f"{child.name}-{counter}"
            counter += 1
        shutil.move(str(child), str(target))


def evaluate_wiki_quality(
    domains: list[Domain],
    api_matches: list[ApiMatch],
    field_map: dict,
) -> dict:
    """评估 Wiki 是否具备可交付的业务导航质量。"""
    warnings = []
    errors = []

    domain_count = len(domains)
    business_point_types = Counter()
    for domain in domains:
        for point in domain.business_points:
            business_point_types[getattr(point, "point_type", "core_action") or "core_action"] += 1
    business_points = business_point_types["core_action"] + business_point_types["interaction"]
    technical_domains = [
        domain for domain in domains
        if _is_technical_domain_name(domain.name or domain.id, domain.display_name)
    ]
    technical_ratio = (len(technical_domains) / domain_count) if domain_count else 1.0

    total_api = len(api_matches)
    uncategorized_api = sum(1 for item in api_matches if not getattr(item, "domain", ""))
    uncategorized_ratio = (uncategorized_api / total_api) if total_api else 0.0
    api_with_callers = sum(1 for item in api_matches if _api_callers(item))
    domains_with_description = sum(1 for domain in domains if _domain_business_sentence(domain))
    business_points_with_type = sum(
        1
        for domain in domains
        for point in domain.business_points
        if getattr(point, "point_type", "")
    )

    if domain_count == 0:
        errors.append("未识别出业务域")
    if business_points == 0:
        errors.append("未识别出业务点")
    if total_api and uncategorized_ratio >= 0.5:
        errors.append("API 未分类比例过高")
    elif total_api and uncategorized_ratio >= 0.2:
        warnings.append("API 未分类比例偏高")
    if domain_count == 1 and business_points >= 50:
        warnings.append("业务域过粗：单个域承载过多业务点")
    if technical_ratio >= 0.5:
        warnings.append("技术域名比例偏高")
    if any(not domain.anchors_flat() for domain in domains):
        warnings.append("存在缺少代码锚点的业务域")

    status = "failed" if errors else ("warning" if warnings else "passed")
    return {
        "domains": {
            "count": domain_count,
            "business_points": business_points,
            "business_point_types": {
                "core_action": business_point_types["core_action"],
                "interaction": business_point_types["interaction"],
                "helper": business_point_types["helper"],
            },
            "technical_name_ratio": round(technical_ratio, 4),
            "technical_names": [domain.name or domain.id for domain in technical_domains],
            "max_domain_business_points": max(
                (
                    sum(
                        1 for point in domain.business_points
                        if getattr(point, "point_type", "core_action") != "helper"
                    )
                    for domain in domains
                ),
                default=0,
            ),
        },
        "api": {
            "total": total_api,
            "uncategorized": uncategorized_api,
            "uncategorized_ratio": round(uncategorized_ratio, 4),
        },
        "field_map": {
            "domains": len(field_map),
            "fields": _count_field_entries(field_map),
        },
        "phase3": {
            "explanation_status": "ready" if domain_count and domains_with_description == domain_count else "incomplete",
            "domains_with_description": domains_with_description,
            "business_points_with_type": business_points_with_type,
            "api_with_callers": api_with_callers,
        },
        "quality": {
            "status": status,
            "warnings": warnings,
            "errors": errors,
        },
    }


def _is_technical_domain_name(name: str, display_name: str = "") -> bool:
    if display_name and display_name != name:
        return False
    normalized = name.lower().replace("_", "-")
    return normalized in TECHNICAL_DOMAIN_NAMES


def _count_field_entries(field_map: dict) -> int:
    count = 0
    for tables in field_map.values():
        if not isinstance(tables, dict):
            continue
        for columns in tables.values():
            if isinstance(columns, dict):
                count += len(columns)
            elif isinstance(columns, list):
                count += len(columns)
    return count


def _write_index(domains: list[Domain], output_dir: Path, domain_read_model: dict | None = None):
    product_quality = (domain_read_model or {}).get("quality", {}) if isinstance(domain_read_model, dict) else {}
    lines = [
        f"# 业务域目录",
        f"",
        f"> {len(domains)} 个业务域 | 自动生成于 {datetime.now():%Y-%m-%d}",
        f"",
        "## Domain Read Model 对齐",
        "",
        f"- 真相源: `../domain-read-model.json`",
        f"- schema: `{(domain_read_model or {}).get('schema', {}).get('version', 'legacy') if isinstance(domain_read_model, dict) else 'legacy'}`",
        f"- deepReadingStatus: `{product_quality.get('deepReadingStatus', 'unknown')}`",
        f"- coreDomainEvidenceStatus: `{product_quality.get('coreDomainEvidenceStatus', 'unknown')}`",
        "",
        f"| 业务域 | 锚点 | 文件 | 依赖域 |",
        f"|--------|------|------|--------|",
    ]
    for d in domains:
        deps = ", ".join(f"[[{dep['domain']}]]" for dep in d.dependencies[:3]) or "—"
        lines.append(
            f"| [[{d.name or d.id}]] | {d.anchors_count()} | {d.total_files} | {deps} |"
        )
    (output_dir / "index.md").write_text("\n".join(lines), encoding="utf-8")


def _write_summary(
    domain: Domain,
    api_matches: list[ApiMatch],
    domain_dir: Path,
    read_model_domain: dict | None = None,
) -> None:
    label = _domain_display_name(domain)
    domain_apis = _domain_api_matches(domain, api_matches)
    core_points = [
        point for point in domain.business_points
        if getattr(point, "point_type", "core_action") == "core_action"
    ]
    interaction_points = [
        point for point in domain.business_points
        if getattr(point, "point_type", "core_action") == "interaction"
    ]
    helper_points = [
        point for point in domain.business_points
        if getattr(point, "point_type", "core_action") == "helper"
    ]
    lines = [
        f"# {domain.name or domain.id} — 业务摘要",
        "",
        "## 业务说明",
        "",
        _domain_business_sentence(domain),
        "",
        "## 业务能力概览",
        "",
        f"- **代码锚点**: {domain.anchors_count()} 个",
        f"- **核心业务动作**: {len(core_points)} 个",
        f"- **查询/交互动作**: {len(interaction_points)} 个",
        f"- **辅助方法**: {len(helper_points)} 个",
        f"- **相关 API**: {len(domain_apis)} 个",
        f"- **DRM 流程**: {len((read_model_domain or {}).get('flows', []))} 个",
        f"- **DRM 业务规则**: {len((read_model_domain or {}).get('rules', []))} 条",
        f"- **DRM 字段规则**: {len((read_model_domain or {}).get('fieldRules', []))} 条",
        "",
        "## 阅读入口",
        "",
        f"- [[code-map]]: 查看 {label} 的页面/类职责和业务动作",
        f"- [[api-docs]]: 查看 {label} 的接口用途和调用入口",
        f"- [[ontology]]: 查看 {label} 的本体对象和 typed relationships",
        f"- [[dependencies]]: 查看 {label} 与其他业务域的依赖",
        f"- [[domain-read-model]]: 查看从 `domain-read-model.json` 派生的流程、规则、字段规则和证据",
    ]
    (domain_dir / "summary.md").write_text("\n".join(lines), encoding="utf-8")


def _write_api_index(api_matches, output_dir: Path):
    by_domain: dict[str, list] = {}
    for m in api_matches:
        d = getattr(m, "domain", "") or "未分类"
        if not d:
            d = "未分类"
        by_domain.setdefault(d, []).append(m)

    lines = [
        "# API 接口索引",
        f"",
        f"> {len(api_matches)} 个接口 | {len(by_domain)} 个域",
        f"",
    ]
    for domain, matches in sorted(by_domain.items()):
        lines.append(f"## [[{domain}]]")
        lines.append("")
        lines.append("| 方法 | URL | 用途 | 前端调用者 |")
        lines.append("|------|-----|------|-----------|")
        for m in matches[:20]:
            # m 可以是 ApiMatch 或 FrontendApiCall
            if hasattr(m, "frontend"):
                fe = m.frontend
                backend_label = f"{m.backend.controller_class}.{m.backend.method_name}()" if hasattr(m, "backend") else "—"
            else:
                fe = m
                backend_label = "—"
            callers = ", ".join(c.get("page", "") for c in (getattr(fe, "callers", []) or [])[:3]) or "—"
            purpose = _describe_api_purpose(fe, domain)
            lines.append(
                f"| {fe.http_method} | {fe.url} | {purpose} | {callers} |"
            )
        lines.append("")
    (output_dir / "api-index.md").write_text("\n".join(lines), encoding="utf-8")


def _write_code_map(domain: Domain, domain_dir: Path):
    lines = [
        f"# {domain.name or domain.id} — 代码地图",
        "",
        "## 业务说明",
        "",
        _domain_business_sentence(domain),
        "",
        "## 代码职责",
        "",
    ]
    for role_name, anchors in domain.anchors.items():
        if anchors:
            lines.append(f"### {role_name}")
            lines.append("")
            for anchor in sorted(anchors[:10], key=lambda item: item.get("label", "")):
                label = anchor.get("label", "")
                lines.append(f"- **{label}**: {_describe_anchor(domain, anchor, role_name)}")
            lines.append("")

    bps = [
        point for point in domain.business_points
        if getattr(point, "point_type", "core_action") != "helper"
    ]
    helper_bps = [
        point for point in domain.business_points
        if getattr(point, "point_type", "core_action") == "helper"
    ]
    if bps:
        lines.append(f"## 业务点（{len(bps)} 个）")
        lines.append("")
        for bp in bps[:20]:
            lines.append(f"- **{bp.display_name or bp.name.lstrip('.')}**")
            lines.append(f"  - 类型：{_business_point_type_label(bp)}")
            lines.append(f"  - 说明：{_describe_business_point(domain, bp)}")
            if bp.entry_file:
                lines.append(f"  - 触发入口：`{bp.entry_file}`")
    if helper_bps:
        lines.append("")
        lines.append(f"## 辅助方法（{len(helper_bps)} 个）")
        lines.append("")
        for bp in helper_bps[:10]:
            lines.append(f"- {bp.display_name or bp.name.lstrip('.')} — {_describe_business_point(domain, bp)}")

    (domain_dir / "code-map.md").write_text("\n".join(lines), encoding="utf-8")


def _write_api_docs(domain: Domain, api_matches: list[ApiMatch], domain_dir: Path):
    domain_matches = _domain_api_matches(domain, api_matches)
    if not domain_matches:
        return

    lines = [f"# {domain.name or domain.id} — API 文档", ""]
    for m in domain_matches:
        # m 可以是 ApiMatch 或 FrontendApiCall
        if hasattr(m, "frontend"):
            fe, be = m.frontend, m.backend
            conf = f"- **匹配置信度**: {m.match_confidence:.0%}" if m.match_confidence else ""
        else:
            fe, be = m, None
            conf = ""
        callers = ", ".join(c.get("page", "") for c in (getattr(fe, "callers", []) or [])[:3]) or "—"
        backend_str = f"{be.controller_class}.{be.method_name}()" if be else "—"
        related_bp = _related_business_point(domain, fe)
        param_summary = _describe_api_params(fe, be)
        return_summary = getattr(be, "return_type", "") if be else ""
        dto_field_summary = _describe_dto_fields(be)
        lines += [
            f"## {fe.http_method} {fe.url}",
            f"",
            f"- 用途：{_describe_api_purpose(fe, _domain_display_name(domain))}",
            f"- 关联业务点：{related_bp.name if related_bp else '—'}",
            f"- 入参：{param_summary}",
            f"- 出参：{return_summary or '—'}",
            f"- DTO 字段：{dto_field_summary}",
            f"- **前端**: `{getattr(fe, 'source_file', '')}` → `{getattr(fe, 'function_name', '')}()`",
            f"- **调用者**: {callers}",
            f"- **后端**: {backend_str}",
            f"- **前后端链路**: {_describe_api_chain(fe, be)}",
            conf,
            f"",
        ]
    (domain_dir / "api-docs.md").write_text("\n".join(lines), encoding="utf-8")


def _write_dependencies(
    domain: Domain,
    domain_dir: Path,
    domains: list[Domain] | None = None,
    ontology: dict | None = None,
    field_map: dict | None = None,
):
    lines = [f"# {domain.name or domain.id} — 域间依赖", ""]
    dependencies = _domain_dependency_rows(domain, domains or [domain], ontology or {}, field_map or {})
    for dep in sorted(dependencies, key=lambda x: (-x.get("import_count", 0), x.get("domain", ""))):
        name = dep.get("domain", "")
        count = dep.get("import_count", 0)
        strength = "强" if count >= 50 else ("中" if count >= 20 else "弱")
        evidence = dep.get("evidence", "")
        suffix = f"；证据：{evidence}" if evidence else ""
        lines.append(f"- [[{name}]] — {count} 次 import ({strength}依赖){suffix}")
    if not dependencies:
        lines.append("*无跨域依赖*")
    (domain_dir / "dependencies.md").write_text("\n".join(lines), encoding="utf-8")


def _domain_dependency_rows(domain: Domain, domains: list[Domain], ontology: dict, field_map: dict) -> list[dict]:
    source = domain.name or domain.id
    domain_keys = {item.name or item.id for item in domains}
    rows: dict[str, dict] = {}
    for dep in domain.dependencies:
        target = dep.get("domain")
        if target:
            rows[target] = {
                "domain": target,
                "import_count": dep.get("import_count", 0),
                "evidence": "domain.dependencies",
            }
    for rel in (ontology or {}).get("relationships", []):
        if rel.get("type") != "depends_on":
            continue
        rel_source = str(rel.get("source", "")).removeprefix("domain:")
        rel_target = str(rel.get("target", "")).removeprefix("domain:")
        if rel_source != source or not rel_target:
            continue
        rows.setdefault(rel_target, {
            "domain": rel_target,
            "import_count": rel.get("import_count", 0),
            "evidence": "ontology.depends_on",
        })
    for table_name, columns in (field_map or {}).get(source, {}).items():
        target = _table_to_domain(table_name, domain_keys)
        if not target or target == source:
            continue
        rows.setdefault(target, {
            "domain": target,
            "import_count": 0,
            "evidence": "field_map.cross_domain_table",
        })
    return list(rows.values())


def _table_to_domain(table_name: str, domain_keys: set[str]) -> str:
    normalized = str(table_name).lower().replace("_", "").replace("-", "")
    for key in domain_keys:
        candidate = str(key).lower().replace("_", "").replace("-", "")
        if normalized == candidate or normalized.rstrip("s") == candidate.rstrip("s"):
            return key
    return ""




def _write_ontology_docs(domain: Domain, ontology: dict, domain_dir: Path) -> None:
    domain_key = domain.name or domain.id
    relationships = [
        item for item in ontology.get("relationships", [])
        if _relationship_touches_domain(item, domain_key)
    ]
    lines = [
        f"# {domain_key} — 本体关系",
        "",
        "## 关系概览",
        "",
    ]
    if relationships:
        for rel in relationships[:50]:
            lines.append(
                f"- `{rel.get('type')}`: `{rel.get('source')}` → `{rel.get('target')}`"
            )
            evidence = rel.get("evidence") or rel.get("confidence") or rel.get("import_count")
            if evidence:
                lines.append(f"  - 证据：{evidence}")
    else:
        lines.append("*暂未生成本体关系*")

    scope = ontology.get("agent_scope", {}).get("domains", {}).get(domain_key, {})
    if scope:
        lines += [
            "",
            "## Agent 阅读范围",
            "",
            f"- 入口文件：{', '.join(f'`{item}`' for item in scope.get('entry_files', [])[:10]) or '—'}",
            f"- 业务点：{', '.join(scope.get('business_points', [])[:10]) or '—'}",
            f"- API：{', '.join(scope.get('apis', [])[:10]) or '—'}",
            f"- 数据表：{', '.join(scope.get('field_tables', [])[:10]) or '—'}",
        ]
    (domain_dir / "ontology.md").write_text("\n".join(lines), encoding="utf-8")


def _write_data_flow(domain: Domain, field_map: dict, domain_dir: Path):
    key = domain.id or domain.name
    tables = field_map.get(key, field_map.get(domain.name, {}))
    if not tables:
        return
    reliable_tables = _filter_reliable_field_tables(tables)
    if not reliable_tables:
        return

    lines = [f"# {domain.name or domain.id} — 数据流", ""]
    lines += [
        "## 业务含义",
        "",
        "这里展示 API 入参字段如何映射到后端实体字段和数据库列，用来判断字段变更会影响哪些业务入口。",
        "",
    ]
    for table, columns in reliable_tables.items():
        lines.append(f"## {table}")
        lines.append("")
        for col, entries in columns.items():
            lines.append(f"### {col}")
            for e in entries[:3]:
                callers = ", ".join(e.get("callers", []))
                lines.append(f"- {e.get('api_url', '')} → {e.get('dto_field', '')} → {e.get('entity_field', '')}")
                lines.append(
                    f"  - 链路：API `{e.get('api_function', '')}` → DTO `{e.get('dto_class', '')}.{e.get('dto_field', '')}` "
                    f"→ Entity `{e.get('entity_class', '')}.{e.get('entity_field', '')}` → DB `{e.get('db_table', table)}.{e.get('db_column', col)}`"
                )
                lines.append(f"  - 可信度：{_field_confidence_label(e)}")
                if callers:
                    lines.append(f"  - 前端: {callers}")
        lines.append("")
    (domain_dir / "data-flow.md").write_text("\n".join(lines), encoding="utf-8")


def _filter_reliable_field_tables(tables: dict) -> dict:
    result = {}
    for table, columns in tables.items():
        kept_columns = {}
        for column, entries in columns.items():
            kept_entries = [
                entry for entry in entries
                if entry.get(
                    "is_reliable",
                    True if "confidence" not in entry else entry.get("confidence", 0) >= 0.9,
                )
            ]
            if kept_entries:
                kept_columns[column] = kept_entries
        if kept_columns:
            result[table] = kept_columns
    return result



def _read_model_domains_by_key(domain_read_model: dict[str, Any]) -> dict[str, dict[str, Any]]:
    result: dict[str, dict[str, Any]] = {}
    for domain in domain_read_model.get("domains", []) if isinstance(domain_read_model, dict) else []:
        if not isinstance(domain, dict):
            continue
        for key in (domain.get("domainKey"), domain.get("id"), domain.get("name")):
            if key:
                result[str(key)] = domain
    return result


def _write_domain_read_model_pages(
    domain: dict[str, Any],
    evidence_index: dict[str, Any],
    domain_dir: Path,
) -> None:
    lines = _domain_read_model_markdown(domain, evidence_index)
    (domain_dir / "domain-read-model.md").write_text("\n".join(lines), encoding="utf-8")
    _write_drm_rules_page(domain, evidence_index, domain_dir)


def _domain_read_model_markdown(domain: dict[str, Any], evidence_index: dict[str, Any]) -> list[str]:
    key = str(domain.get("domainKey") or "unknown")
    lines = [
        f"# {key} — Domain Read Model",
        "",
        "> 本页从 `domain-read-model.json` 派生，是 Wiki 与 Agent/CI 共享的产品语义视图。",
        "",
        "## Domain",
        "",
        f"- displayName: {domain.get('displayName', key)}",
        f"- core: `{domain.get('core', False)}`",
        f"- deepReadingStatus: `{(domain.get('quality') or {}).get('deepReadingStatus', 'unknown') if isinstance(domain.get('quality'), dict) else domain.get('quality', 'unknown')}`",
        f"- summary: {domain.get('summary', '')}",
        "",
        "## Flows",
        "",
    ]
    flows = domain.get("flows", []) if isinstance(domain.get("flows"), list) else []
    if not flows:
        lines.append("*未生成业务流程。*")
    for flow in flows:
        if not isinstance(flow, dict):
            continue
        lines += [
            f"### {flow.get('title') or flow.get('flowId')}",
            "",
            f"- flowId: `{flow.get('flowId', '')}`",
            f"- status: `{flow.get('status', 'unknown')}`",
            f"- evidenceRefs: {_format_refs(flow.get('evidenceRefs', []))}",
            "",
        ]
        for idx, step in enumerate(flow.get("steps", []) if isinstance(flow.get("steps"), list) else [], start=1):
            if not isinstance(step, dict):
                continue
            lines.append(f"{idx}. {step.get('title') or step.get('description') or step.get('stepId', '')}")
            if step.get("ruleRefs"):
                lines.append(f"   - ruleRefs: {_format_refs(step.get('ruleRefs', []))}")
            lines.append(f"   - evidenceRefs: {_format_refs(step.get('evidenceRefs', []))}")
        lines.append("")

    lines += ["## Business Rules", ""]
    rules = domain.get("rules", []) if isinstance(domain.get("rules"), list) else []
    if not rules:
        lines.append("*未生成业务规则。*")
    for rule in rules:
        if not isinstance(rule, dict):
            continue
        lines += _rule_markdown(rule)

    lines += ["## Field Rules", ""]
    field_rules = domain.get("fieldRules", []) if isinstance(domain.get("fieldRules"), list) else []
    if not field_rules:
        lines.append("*未生成字段规则。*")
    for rule in field_rules:
        if not isinstance(rule, dict):
            continue
        lines += _field_rule_markdown(rule)

    lines += ["## Evidence", ""]
    refs = _domain_claim_refs(domain)
    if not refs:
        lines.append("*无证据引用。*")
    for ref in refs:
        evidence = evidence_index.get(ref, {}) if isinstance(evidence_index.get(ref), dict) else {}
        label = evidence.get("label") or evidence.get("sourcePath") or evidence.get("path") or ref
        lines.append(f"- `{ref}` — {label}")
    return lines


def _write_drm_rules_page(domain: dict[str, Any], evidence_index: dict[str, Any], domain_dir: Path) -> None:
    p = domain_dir / "rules.md"
    if p.exists() and "<!-- GRAPH_WIKI_DRM_RULES:START -->" not in p.read_text(encoding="utf-8"):
        return
    lines = [
        f"# {domain.get('domainKey', 'unknown')} — RULES",
        "",
        "<!-- GRAPH_WIKI_DRM_RULES:START -->",
        "## DRM-derived Business Rules",
        "",
    ]
    rules = domain.get("rules", []) if isinstance(domain.get("rules"), list) else []
    lines += [line for rule in rules if isinstance(rule, dict) for line in _rule_markdown(rule)] or ["*未生成业务规则。*", ""]
    lines += ["## DRM-derived Field Rules", ""]
    field_rules = domain.get("fieldRules", []) if isinstance(domain.get("fieldRules"), list) else []
    lines += [line for rule in field_rules if isinstance(rule, dict) for line in _field_rule_markdown(rule)] or ["*未生成字段规则。*", ""]
    lines += ["<!-- GRAPH_WIKI_DRM_RULES:END -->", ""]
    p.write_text("\n".join(lines), encoding="utf-8")


def _rule_markdown(rule: dict[str, Any]) -> list[str]:
    return [
        f"### {rule.get('title') or rule.get('ruleId')}",
        "",
        f"- ruleId: `{rule.get('ruleId', '')}`",
        f"- type: `{rule.get('ruleType', rule.get('type', 'business'))}`",
        f"- status: `{rule.get('status', 'unknown')}`",
        f"- confidence: `{rule.get('confidence', '')}`",
        f"- statement: {rule.get('statement') or rule.get('summary') or ''}",
        f"- flowRefs: {_format_refs(rule.get('flowRefs', []))}",
        f"- evidenceRefs: {_format_refs(rule.get('evidenceRefs', []))}",
        "",
    ]


def _field_rule_markdown(rule: dict[str, Any]) -> list[str]:
    completeness = rule.get("chainCompleteness", {}) if isinstance(rule.get("chainCompleteness"), dict) else {}
    lines = [
        f"### {rule.get('fieldId') or rule.get('ruleId')}",
        "",
        f"- ruleId: `{rule.get('ruleId', '')}`",
        f"- status: `{rule.get('status', 'unknown')}`",
        f"- confidence: `{rule.get('confidence', '')}`",
        f"- presentLayers: {_format_refs(completeness.get('presentLayers', []))}",
        f"- missingRequiredLayers: {_format_refs(completeness.get('missingRequiredLayers', []))}",
        f"- partialReason: {rule.get('partialReason', '') or '—'}",
        f"- evidenceRefs: {_format_refs(rule.get('evidenceRefs', []))}",
        "",
    ]
    chain = rule.get("chain", []) if isinstance(rule.get("chain"), list) else []
    if chain:
        lines += ["| layer | ref | label |", "|---|---|---|"]
        for item in chain:
            if not isinstance(item, dict):
                continue
            lines.append(f"| {item.get('layer', '')} | `{item.get('ref', '')}` | {item.get('label', '')} |")
        lines.append("")
    return lines


def _domain_claim_refs(domain: dict[str, Any]) -> list[str]:
    refs: list[str] = []
    containers = [domain, *domain.get("flows", []), *domain.get("rules", []), *domain.get("fieldRules", [])]
    for container in containers:
        if not isinstance(container, dict):
            continue
        for ref in container.get("evidenceRefs", []) or []:
            if ref not in refs:
                refs.append(ref)
        for step in container.get("steps", []) if isinstance(container.get("steps"), list) else []:
            if isinstance(step, dict):
                for ref in step.get("evidenceRefs", []) or []:
                    if ref not in refs:
                        refs.append(ref)
    return refs


def _format_refs(refs: Any) -> str:
    if not refs:
        return "—"
    if not isinstance(refs, list):
        refs = [refs]
    return ", ".join(f"`{ref}`" for ref in refs if ref) or "—"

def _field_confidence_label(entry: dict) -> str:
    labels = {"high": "高", "medium": "中", "low": "低"}
    level = entry.get("confidence_level") or "high"
    score = entry.get("confidence")
    score_text = f" ({score:.0%})" if isinstance(score, (int, float)) else ""
    return labels.get(level, level) + score_text


def _domain_api_matches(domain: Domain, api_matches: list[ApiMatch]) -> list:
    return [
        m for m in api_matches
        if getattr(m, "domain", "") in (domain.name, domain.id)
    ]


def _domain_display_name(domain_or_name) -> str:
    if isinstance(domain_or_name, Domain):
        if domain_or_name.display_name:
            return domain_or_name.display_name
        key = domain_or_name.name or domain_or_name.id
    else:
        key = str(domain_or_name)
    return DOMAIN_DISPLAY_NAMES.get(str(key).lower(), str(key))


def _domain_business_sentence(domain: Domain) -> str:
    if domain.description:
        return domain.description
    label = _domain_display_name(domain)
    anchors = _primary_anchor_labels(domain)
    anchor_hint = "、".join(anchors[:3]) if anchors else "相关页面和服务"
    return f"{label}负责围绕{label}的核心页面、接口和业务动作，主要入口包括 {anchor_hint}。"


def _primary_anchor_labels(domain: Domain) -> list[str]:
    labels = []
    for anchors in domain.anchors.values():
        for anchor in anchors:
            label = anchor.get("label", "")
            if label:
                labels.append(label)
    return labels


def _describe_anchor(domain: Domain, anchor: dict, role_name: str) -> str:
    label = _domain_display_name(domain)
    source = str(anchor.get("source_file", "")).replace("\\", "/")
    file_name = anchor.get("label", Path(source).name)
    if "/views/" in source or file_name.endswith(".vue"):
        return f"负责{label}的页面入口，承载用户查看、操作和提交相关业务。"
    if "/api/" in source:
        return f"负责{label}的前端接口封装，把页面动作连接到后端服务。"
    if "controller" in role_name:
        return f"负责{label}的外部请求入口，将接口请求转入业务服务。"
    if "service" in role_name:
        return f"负责{label}的业务规则编排和核心处理。"
    if role_name in {"mapper", "dao"}:
        return f"负责{label}的数据访问和持久化操作。"
    return f"支撑{label}的代码单元。"


def _business_point_type_label(point: BusinessPoint) -> str:
    return POINT_TYPE_LABELS.get(getattr(point, "point_type", ""), "查询/交互动作")


def _describe_business_point(domain: Domain, point: BusinessPoint) -> str:
    label = _domain_display_name(domain)
    name = _clean_method_name(point.name)
    lowered = name.lower()
    point_type = getattr(point, "point_type", "interaction")
    if point_type == "helper":
        return f"辅助{label}页面或流程展示，不直接代表核心业务结果。"
    if any(token in lowered for token in ("submit", "create", "save", "import", "approve", "delete", "remove", "update", "merge", "execute", "commit")):
        verb = _business_verb(name)
        return f"{verb}{label}相关数据或执行关键变更。"
    if any(token in lowered for token in ("load", "query", "search", "list", "find", "view", "open", "show")):
        return f"查询或展示{label}相关信息，帮助用户定位和理解业务状态。"
    if lowered.startswith("handle"):
        return f"响应{label}页面上的用户操作，并串联相关接口或页面状态。"
    return f"支撑{label}中的一个业务步骤或页面交互。"


def _business_verb(method_name: str) -> str:
    lowered = method_name.lower()
    if "delete" in lowered or "remove" in lowered:
        return "删除"
    if "import" in lowered:
        return "导入"
    if "approve" in lowered:
        return "审批"
    if "merge" in lowered:
        return "合并"
    if "commit" in lowered or "submit" in lowered:
        return "提交"
    if "update" in lowered or "save" in lowered:
        return "保存"
    if "create" in lowered:
        return "创建"
    if "execute" in lowered:
        return "执行"
    return "提交"


def _clean_method_name(name: str) -> str:
    return str(name).strip().lstrip(".").removesuffix("()")


def _describe_api_purpose(frontend_api: FrontendApiCall, domain_label: str) -> str:
    label = _domain_display_name(domain_label)
    text = f"{frontend_api.function_name} {frontend_api.url}".lower()
    method = frontend_api.http_method.upper()
    if method == "GET":
        return f"查询或加载{label}相关数据"
    if any(token in text for token in ("delete", "remove")) or method == "DELETE":
        return f"删除{label}相关数据"
    if any(token in text for token in ("import", "upload")):
        return f"导入{label}相关数据"
    if any(token in text for token in ("approve", "audit")):
        return f"审批{label}相关数据"
    if any(token in text for token in ("merge", "execute", "commit", "submit", "create")) or method == "POST":
        return f"提交{label}相关数据或执行关键变更"
    if method in {"PUT", "PATCH"}:
        return f"更新{label}相关数据"
    return f"处理{label}相关接口请求"


def _related_business_point(domain: Domain, frontend_api: FrontendApiCall) -> BusinessPoint | None:
    caller_pages = [
        str(caller.get("page", "")).replace("\\", "/")
        for caller in getattr(frontend_api, "callers", []) or []
    ]
    candidates = [
        point for point in domain.business_points
        if getattr(point, "point_type", "core_action") != "helper"
    ]
    api_tokens = set(_split_identifier(frontend_api.function_name))
    api_text = f"{frontend_api.function_name} {frontend_api.url}".lower()
    method = frontend_api.http_method.upper()
    best = None
    best_score = -1
    for point in candidates:
        score = 0
        entry = str(point.entry_file).replace("\\", "/")
        if any(entry and (entry.endswith(page) or page.endswith(Path(entry).name)) for page in caller_pages):
            score += 10
        point_type = getattr(point, "point_type", "interaction")
        point_name = _clean_method_name(point.name)
        point_lower = point_name.lower()
        if method == "GET" and point_type == "interaction":
            score += 4
        if method in {"POST", "PUT", "PATCH", "DELETE"} and point_type == "core_action":
            score += 6
        if method == "DELETE" and "delete" in point_lower:
            score += 10
        if method in {"POST", "PUT", "PATCH"} and any(token in point_lower for token in ("submit", "create", "save", "update", "import", "approve", "merge", "execute", "commit")):
            score += 8
        if any(token in api_text for token in ("delete", "remove")) and "delete" in point_lower:
            score += 10
        if any(token in api_text for token in ("create", "submit", "save")) and any(token in point_lower for token in ("submit", "create", "save")):
            score += 10
        point_tokens = set(_split_identifier(_clean_method_name(point.name)))
        score += len(api_tokens & point_tokens)
        if score > best_score:
            best = point
            best_score = score
    return best


def _split_identifier(value: str) -> list[str]:
    words = re.sub(r"([a-z])([A-Z])", r"\1 \2", value).replace("_", " ").replace("-", " ")
    return [word.lower() for word in words.split() if word]


def _api_callers(item) -> list[dict]:
    fe = item.frontend if hasattr(item, "frontend") else item
    return getattr(fe, "callers", []) or []


def _describe_api_params(frontend_api: FrontendApiCall, backend) -> str:
    parts = []
    if getattr(frontend_api, "params", None):
        parts.append("前端参数 " + ", ".join(frontend_api.params))
    if backend and getattr(backend, "param_type", None):
        parts.append(f"后端 DTO {backend.param_type}")
    return "；".join(parts) if parts else "—"


def _describe_dto_fields(backend) -> str:
    fields = getattr(backend, "param_fields", []) if backend else []
    if not fields:
        return "—"
    return ", ".join(f"{field.get('name')}:{field.get('type')}" for field in fields[:12])


def _relationship_touches_domain(rel: dict, domain_key: str) -> bool:
    needle = f":{domain_key}"
    return needle in str(rel.get("source", "")) or needle in str(rel.get("target", "")) or rel.get("domain") == domain_key


def _describe_api_chain(frontend_api: FrontendApiCall, backend) -> str:
    frontend = f"{getattr(frontend_api, 'source_file', '')}::{getattr(frontend_api, 'function_name', '')}()"
    if not backend:
        return f"{frontend} → 后端未匹配"
    backend_label = f"{backend.controller_class}.{backend.method_name}()"
    service = " → ".join(getattr(backend, "service_chain", []) or [])
    return f"{frontend} → {backend_label}" + (f" → {service}" if service else "")


def export_domain_read_model_wiki(read_model: dict, wiki_root: Path) -> None:
    """Write DRM-derived Markdown pages without overwriting manual rules/spec.

    These generated pages are the Markdown/Agent counterpart of the Workbench
    read model, keeping flows/rules/fieldRules/evidence IDs consistent across
    product surfaces while preserving hand-authored `rules.md` and `spec.md`.
    """
    if not isinstance(read_model, dict) or read_model.get("schema", {}).get("version") != "domain-read-model-v1":
        return
    wiki_root.mkdir(parents=True, exist_ok=True)
    evidence_index = read_model.get("evidenceIndex", {}) if isinstance(read_model.get("evidenceIndex"), dict) else {}
    for domain in read_model.get("domains", []) if isinstance(read_model.get("domains"), list) else []:
        domain_key = str(domain.get("domainKey") or "unknown")
        domain_dir = wiki_root / domain_key
        domain_dir.mkdir(parents=True, exist_ok=True)
        lines = [
            f"# {domain.get('displayName', domain_key)} — Domain Read Model",
            "",
            "> 自动生成页；人工定稿仍在 `rules.md` / `spec.md`。Workbench、Wiki、Agent 应以本页引用的 DRM 证据 ID 为一致语义入口。",
            "",
            "## 流程 → 规则 → 证据",
            "",
        ]
        for flow in domain.get("flows", []) or []:
            lines += [f"### {flow.get('title') or flow.get('flowId')}", "", flow.get("summary", ""), ""]
            for step in flow.get("steps", []) or []:
                refs = ", ".join(f"`{ref}`" for ref in step.get("evidenceRefs", []) or []) or "—"
                rules = ", ".join(f"`{ref}`" for ref in step.get("ruleRefs", []) or []) or "—"
                lines.append(f"- {step.get('order', '-')}. {step.get('text', '')}")
                lines.append(f"  - rules: {rules}")
                lines.append(f"  - evidence: {refs}")
            lines.append("")
        lines += ["## 业务规则", ""]
        for rule in domain.get("rules", []) or []:
            refs = ", ".join(f"`{ref}`" for ref in rule.get("evidenceRefs", []) or []) or "—"
            lines.append(f"- `{rule.get('ruleId')}` {rule.get('statement', '')}")
            lines.append(f"  - status: {rule.get('status', 'unknown')} / confidence: {rule.get('confidence', 'unknown')}")
            lines.append(f"  - evidence: {refs}")
        lines += ["", "## 字段规则", ""]
        for field_rule in domain.get("fieldRules", []) or []:
            completeness = field_rule.get("chainCompleteness", {}) if isinstance(field_rule.get("chainCompleteness"), dict) else {}
            refs = ", ".join(f"`{ref}`" for ref in field_rule.get("evidenceRefs", []) or []) or "—"
            missing = list(completeness.get("missingRequiredLayers", []) or []) + list(completeness.get("missingOptionalLayers", []) or [])
            chain = " → ".join(f"{item.get('layer')}(`{item.get('ref')}`)" for item in field_rule.get("chain", []) or []) or "—"
            lines.append(f"- `{field_rule.get('fieldId')}` {field_rule.get('statement', '')}")
            lines.append(f"  - chain: {chain}")
            lines.append(f"  - missing: {', '.join(missing) or '—'}")
            lines.append(f"  - evidence: {refs}")
        lines += ["", "## 证据索引", ""]
        domain_refs = []
        for container_name in ("evidenceRefs",):
            domain_refs.extend(domain.get(container_name, []) or [])
        for container_name in ("flows", "rules", "fieldRules"):
            for item in domain.get(container_name, []) or []:
                domain_refs.extend(item.get("evidenceRefs", []) if isinstance(item, dict) else [])
                for step in item.get("steps", []) if isinstance(item, dict) else []:
                    domain_refs.extend(step.get("evidenceRefs", []) if isinstance(step, dict) else [])
        seen = set()
        for ref in domain_refs:
            if not ref or ref in seen:
                continue
            seen.add(ref)
            evidence = evidence_index.get(ref, {}) if isinstance(evidence_index.get(ref), dict) else {}
            path = evidence.get("sourcePath") or evidence.get("path") or ""
            lines.append(f"- `{ref}` — {evidence.get('label', ref)} ({evidence.get('status', 'unknown')}) {path}")
        (domain_dir / "domain-read-model.md").write_text("\n".join(lines), encoding="utf-8")

    agent_lines = ["# Agent / CI Domain Read Model Contract", "", "- source: `domain-read-model.json`", ""]
    for domain in read_model.get("domains", []) if isinstance(read_model.get("domains"), list) else []:
        domain_key = str(domain.get("domainKey") or "unknown")
        agent_lines.append(f"## {domain_key}")
        agent_lines.append(f"- flows: {len(domain.get('flows', []) or [])}")
        agent_lines.append(f"- rules: {len(domain.get('rules', []) or [])}")
        agent_lines.append(f"- fieldRules: {len(domain.get('fieldRules', []) or [])}")
        agent_lines.append(f"- generated page: `wiki/{domain_key}/domain-read-model.md`")
        agent_lines.append("")
    (wiki_root / "agent-contract.md").write_text("\n".join(agent_lines), encoding="utf-8")
