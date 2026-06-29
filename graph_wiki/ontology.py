"""Code Ontology v0：把业务域、API、字段和代码链路编译成 typed relationships。"""

from collections import defaultdict
from pathlib import Path
from typing import Any

from .models import ApiMatch, Domain, FrontendApiCall


ONTOLOGY_VERSION = "code-ontology-v0"


PHASE3_ACCEPTANCE_ITEMS = [
    {
        "id": "formal_schema",
        "label": "正式 Code Ontology v0 schema",
        "description": "产物必须包含可机读的本体 schema 和版本号。",
    },
    {
        "id": "typed_relationships",
        "label": "typed relationships",
        "description": "至少产出 contains、exposes、consumes、calls、maps_to、depends_on 中的 5 类关系。",
    },
    {
        "id": "human_wiki",
        "label": "人能读懂的 Wiki",
        "description": "业务摘要、代码地图、API 文档、本体关系页必须形成可读入口。",
    },
    {
        "id": "field_flow",
        "label": "字段级链路",
        "description": "至少一个样本能展示 API → DTO → Entity → DB 字段的链路。",
    },
    {
        "id": "agent_scope",
        "label": "Agent 可缩小阅读范围",
        "description": "ontology.json 必须能按业务域列出入口文件、业务点和 API。",
    },
]


def build_code_ontology(
    domains: list[Domain],
    api_matches: list[ApiMatch | FrontendApiCall],
    field_map: dict,
) -> dict[str, Any]:
    """生成 Code Ontology v0 JSON。

    这个产物面向两个客户：
    - 人：Wiki 页面可以引用其中的关系，解释业务链路。
    - Agent：按 domain 缩小需要阅读的文件、API 和字段范围。
    """
    nodes: dict[str, dict[str, Any]] = {}
    relationships: list[dict[str, Any]] = []

    def add_node(node_id: str, node_type: str, **attrs) -> str:
        payload = {"id": node_id, "type": node_type, **{k: v for k, v in attrs.items() if v not in (None, "", [], {})}}
        nodes[node_id] = {**nodes.get(node_id, {}), **payload}
        return node_id

    def add_rel(source: str, target: str, rel_type: str, **attrs) -> None:
        if not source or not target:
            return
        relationships.append({
            "type": rel_type,
            "source": source,
            "target": target,
            **{k: v for k, v in attrs.items() if v not in (None, "", [], {})},
        })

    domain_ids = {}
    for domain in domains:
        domain_key = domain.name or domain.id
        domain_id = f"domain:{domain_key}"
        domain_ids[domain_key] = domain_id
        add_node(
            domain_id,
            "Domain",
            name=domain_key,
            display_name=domain.display_name,
            description=domain.description,
        )

        for point in domain.business_points:
            bp_name = _clean_name(point.name)
            bp_id = f"business_point:{domain_key}:{bp_name}"
            add_node(
                bp_id,
                "BusinessPoint",
                name=point.name,
                display_name=point.display_name or bp_name,
                point_type=getattr(point, "point_type", "core_action"),
                entry_file=point.entry_file,
                domain=domain_key,
            )
            add_rel(domain_id, bp_id, "contains", evidence="domain.business_points")

        for role, anchors in domain.anchors.items():
            for anchor in anchors:
                source_file = anchor.get("source_file", "")
                anchor_id = f"code:{anchor.get('id') or source_file or anchor.get('label')}"
                add_node(
                    anchor_id,
                    "CodeAnchor",
                    label=anchor.get("label"),
                    role=role,
                    source_file=source_file,
                    domain=domain_key,
                )
                add_rel(domain_id, anchor_id, "contains", evidence="domain.anchors")

        for dep in domain.dependencies:
            dep_domain = dep.get("domain")
            if dep_domain:
                dep_id = f"domain:{dep_domain}"
                add_node(dep_id, "Domain", name=dep_domain)
                add_rel(domain_id, dep_id, "depends_on", import_count=dep.get("import_count"))

    for item in api_matches:
        fe = item.frontend if hasattr(item, "frontend") else item
        be = item.backend if hasattr(item, "backend") else None
        domain_key = getattr(item, "domain", "") or getattr(fe, "domain", "")
        api_id = _api_id(fe)
        add_node(
            api_id,
            "API",
            method=fe.http_method,
            url=fe.url,
            function_name=fe.function_name,
            source_file=fe.source_file,
            domain=domain_key,
        )
        if domain_key:
            domain_id = domain_ids.get(domain_key, f"domain:{domain_key}")
            add_node(domain_id, "Domain", name=domain_key)
            add_rel(domain_id, api_id, "exposes", evidence="api.domain")

        for caller in getattr(fe, "callers", []) or []:
            page = caller.get("page", "")
            caller_id = f"frontend_caller:{page}"
            add_node(caller_id, "FrontendCaller", page=page, fields_used=caller.get("fields_used", []))
            add_rel(caller_id, api_id, "consumes", evidence="frontend.imports")

        if be:
            controller_id = f"controller:{be.controller_class}.{be.method_name}"
            add_node(
                controller_id,
                "Controller",
                class_name=be.controller_class,
                method_name=be.method_name,
                source_file=be.controller_file,
                param_type=be.param_type,
                param_fields=be.param_fields,
                return_type=be.return_type,
            )
            add_rel(api_id, controller_id, "calls", confidence=getattr(item, "match_confidence", 0.0))

            if be.param_type:
                dto_id = f"dto:{be.param_type}"
                add_node(dto_id, "DTO", name=be.param_type, fields=be.param_fields)
                add_rel(api_id, dto_id, "consumes", evidence="controller.param_type")

            for service_call in be.service_chain or []:
                service_id = f"service:{service_call}"
                add_node(service_id, "Service", call=service_call)
                add_rel(controller_id, service_id, "calls", evidence="controller.method_body")

    for domain_key, tables in (field_map or {}).items():
        domain_id = domain_ids.get(domain_key, f"domain:{domain_key}")
        add_node(domain_id, "Domain", name=domain_key)
        for table_name, columns in tables.items():
            table_id = f"table:{table_name}"
            add_node(table_id, "Table", name=table_name, domain=domain_key)
            add_rel(domain_id, table_id, "contains", evidence="field_map")
            for column_name, entries in columns.items():
                column_id = f"field:{table_name}.{column_name}"
                add_node(column_id, "Field", name=column_name, table=table_name, domain=domain_key)
                add_rel(table_id, column_id, "contains", evidence="field_map")
                for entry in entries:
                    api_id = f"api:{entry.get('api_function', '')}:{entry.get('api_url', '')}"
                    if entry.get("api_function") or entry.get("api_url"):
                        add_node(api_id, "API", function_name=entry.get("api_function"), url=entry.get("api_url"))
                        add_rel(api_id, column_id, "maps_to", evidence="field_map")
                    dto_id = f"dto_field:{entry.get('dto_class', '')}.{entry.get('dto_field', '')}"
                    entity_id = f"entity_field:{entry.get('entity_class', '')}.{entry.get('entity_field', '')}"
                    add_node(dto_id, "DTOField", name=entry.get("dto_field"), dto_class=entry.get("dto_class"))
                    add_node(entity_id, "EntityField", name=entry.get("entity_field"), entity_class=entry.get("entity_class"))
                    add_rel(dto_id, entity_id, "maps_to", confidence=entry.get("confidence"), evidence="dto_entity_match")
                    add_rel(entity_id, column_id, "maps_to", evidence="entity_db_mapping")

    relation_types = sorted({rel["type"] for rel in relationships})
    object_types = sorted({node["type"] for node in nodes.values()})
    return {
        "schema": {
            "version": ONTOLOGY_VERSION,
            "objects": [
                "Domain", "BusinessPoint", "API", "FrontendCaller", "Controller",
                "Service", "DTO", "Entity", "Field", "Rule", "Spec", "Decision",
            ],
            "relationships": [
                "contains", "exposes", "consumes", "calls", "maps_to",
                "depends_on", "implements_rule", "changed_by",
            ],
        },
        "nodes": sorted(nodes.values(), key=lambda node: node["id"]),
        "relationships": relationships,
        "coverage": {
            "objects": object_types,
            "object_types_count": len(object_types),
            "relationship_types": relation_types,
            "relationship_types_count": len(relation_types),
            "relationships": len(relationships),
        },
        "agent_scope": _build_agent_scope(domains, api_matches, field_map),
    }


def evaluate_phase3_acceptance(
    ontology: dict,
    quality_report: dict,
    wiki_evidence: dict,
) -> dict[str, Any]:
    """按董事会 Phase 3 验收标准出具可审计结论。"""
    phase3 = quality_report.get("phase3", {})
    coverage = ontology.get("coverage", {})
    relation_types = set(coverage.get("relationship_types", []))
    items = [
        _audit_item(
            "formal_schema",
            ontology.get("schema", {}).get("version") == ONTOLOGY_VERSION,
            f"schema={ontology.get('schema', {}).get('version', 'missing')}",
        ),
        _audit_item(
            "typed_relationships",
            len(relation_types) >= 5 and {"contains", "exposes", "calls"} <= relation_types,
            f"relationship_types={sorted(relation_types)}",
        ),
        _audit_item(
            "human_wiki",
            wiki_evidence.get("summary_pages", 0) > 0
            and wiki_evidence.get("code_map_pages", 0) > 0
            and wiki_evidence.get("api_docs_pages", 0) > 0
            and wiki_evidence.get("ontology_pages", 0) > 0,
            f"wiki_evidence={wiki_evidence}",
        ),
        _audit_item(
            "field_flow",
            phase3.get("field_flow_status") == "ready"
            and wiki_evidence.get("data_flow_pages", 0) > 0
            and "maps_to" in relation_types,
            f"field_flow_status={phase3.get('field_flow_status', 'missing')}",
        ),
        _audit_item(
            "agent_scope",
            bool(ontology.get("agent_scope", {}).get("domains")),
            "agent_scope.domains present",
        ),
    ]
    return {
        "status": "passed" if all(item["status"] == "passed" for item in items) else "failed",
        "items": items,
        "acceptance_items": PHASE3_ACCEPTANCE_ITEMS,
    }


def _audit_item(item_id: str, passed: bool, evidence: str) -> dict[str, str]:
    spec = next(item for item in PHASE3_ACCEPTANCE_ITEMS if item["id"] == item_id)
    return {
        "id": item_id,
        "label": spec["label"],
        "status": "passed" if passed else "failed",
        "evidence": evidence,
    }


def _build_agent_scope(
    domains: list[Domain],
    api_matches: list[ApiMatch | FrontendApiCall],
    field_map: dict,
) -> dict[str, Any]:
    api_by_domain: dict[str, list[str]] = defaultdict(list)
    for item in api_matches:
        fe = item.frontend if hasattr(item, "frontend") else item
        domain = getattr(item, "domain", "") or getattr(fe, "domain", "")
        if domain:
            api_by_domain[domain].append(f"{fe.http_method} {fe.url}")

    domains_scope = {}
    for domain in domains:
        key = domain.name or domain.id
        entry_files = sorted({
            anchor.get("source_file", "")
            for anchors in domain.anchors.values()
            for anchor in anchors
            if anchor.get("source_file")
        })
        domains_scope[key] = {
            "display_name": domain.display_name,
            "entry_files": entry_files,
            "business_points": [point.name for point in domain.business_points],
            "apis": sorted(set(api_by_domain.get(key, []))),
            "field_tables": sorted((field_map or {}).get(key, {}).keys()),
        }
    return {"domains": domains_scope}


def collect_wiki_evidence(wiki_root: Path) -> dict[str, int]:
    """统计 Wiki 产物是否具备 Phase 3 展示入口。"""
    domain_dirs = [item for item in wiki_root.iterdir() if item.is_dir() and not item.name.startswith("_")] if wiki_root.exists() else []
    return {
        "summary_pages": sum(1 for item in domain_dirs if (item / "summary.md").exists()),
        "code_map_pages": sum(1 for item in domain_dirs if (item / "code-map.md").exists()),
        "api_docs_pages": sum(1 for item in domain_dirs if (item / "api-docs.md").exists()),
        "data_flow_pages": sum(1 for item in domain_dirs if (item / "data-flow.md").exists()),
        "ontology_pages": sum(1 for item in domain_dirs if (item / "ontology.md").exists()),
    }


def _api_id(frontend: FrontendApiCall) -> str:
    return f"api:{frontend.function_name}:{frontend.url}"


def _clean_name(value: str) -> str:
    return str(value).strip().lstrip(".").removesuffix("()")
