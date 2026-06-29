"""Product-facing DTO aggregation for generated Graph-Wiki artifacts."""

from __future__ import annotations

import json
import re
from pathlib import Path
from typing import Any


class ProductDataService:
    """Read generated artifacts and expose stable frontend DTOs."""

    def __init__(self, output_dir: str | Path):
        self.output_dir = Path(output_dir)

    def load_project_overview(self) -> dict[str, Any]:
        build = self._read_json("build-report.json", {})
        manifest = self._read_json("manifest.json", {})
        domains = self.list_domains()
        dream = self.get_dream_cycle_summary()

        project = build.get("project", {})
        quality = build.get("quality", {})
        product_quality = build.get("productQuality", {})
        build_status = build.get("build", {}).get("status") or quality.get("status", "unknown")
        scale = build.get("scale", {})

        return {
            "projectId": self.output_dir.name,
            "projectName": _project_name(project.get("root") or self.output_dir.name),
            "generatedAt": manifest.get("meta", {}).get("last_full_build"),
            "sourceRoot": project.get("root", ""),
            "quality": {
                "build": build_status,
                "deepReading": product_quality.get("deepReadingStatus", "unknown"),
                "coreEvidence": product_quality.get("coreDomainEvidenceStatus", "unknown"),
                "performance": quality.get("performance_status", "unknown"),
                "phase3": _nested_status(quality, ["phase3", "acceptance", "status"]),
                "phase4": _nested_status(quality, ["phase4", "acceptance", "status"]),
                "phase5": _nested_status(quality, ["phase5", "dream_cycle_status"]),
            },
            "scale": {
                "totalFiles": project.get("total_files") or scale.get("files", {}).get("total", 0),
                "codeFiles": project.get("code_files") or scale.get("files", {}).get("code", 0),
                "graphNodes": scale.get("graph_lite", {}).get("nodes", 0),
                "graphEdges": scale.get("graph_lite", {}).get("edges", 0),
                "apis": build.get("api", {}).get("total", 0),
                "fields": build.get("field_map", {}).get("fields", 0),
                "domains": build.get("domains", {}).get("count", len(domains)),
            },
            "domains": domains,
            "topQuestions": self.get_impact_summary().get("queryExamples", []),
            "recommendedReading": _recommended_reading(domains),
            "latestDreamCycle": dream,
            "evidence": [_evidence("build_report", "build-report.json", "build-report.json")],
        }

    def export_workbench_data(self) -> dict[str, Any]:
        """Return the static data bundle consumed by the Vue workbench."""
        domains = self.list_domains()
        has_read_model = bool(self._domain_read_model())
        return {
            "schema": {
                "version": "graph-wiki-workbench-v1" if has_read_model else "graph-wiki-workbench-v0",
                "source": "domain-read-model.json" if has_read_model else "ProductDataService",
            },
            "overview": self.load_project_overview(),
            "domains": domains,
            "domainDetails": {
                domain["domainKey"]: self.get_domain_detail(domain["domainKey"])
                for domain in domains
            },
            "apis": self.list_apis(),
            "fields": self.list_fields(),
            "impact": self.get_impact_summary(),
            "dreamCycle": self.get_dream_cycle_summary(),
        }

    def list_domains(self) -> list[dict[str, Any]]:
        read_model = self._domain_read_model()
        if read_model:
            evidence_index = read_model.get("evidenceIndex", {})
            result = []
            for domain in read_model.get("domains", []):
                evidence_refs = domain.get("evidenceRefs", [])
                result.append({
                    "domainKey": domain.get("domainKey", "unknown"),
                    "displayName": domain.get("displayName", domain.get("domainKey", "unknown")),
                    "summary": domain.get("summary", ""),
                    "anchorCount": len([ref for ref in evidence_refs if str(ref).startswith("source:")]),
                    "businessPointCount": len(domain.get("flows", [])),
                    "apiCount": len(_api_refs_for_domain(domain)),
                    "fieldFlowCount": len(domain.get("fieldRules", [])),
                    "dependencyCount": 0,
                    "quality": domain.get("quality", {}).get("deepReadingStatus", "unknown"),
                    "evidence": _evidence_objects_from_refs(evidence_index, evidence_refs),
                })
            return result

        domains = self._domains()
        apis = self._apis()
        field_counts = self._field_counts_by_domain()

        result = []
        for domain in domains:
            key = _domain_key(domain)
            result.append({
                "domainKey": key,
                "displayName": _domain_display_name(domain),
                "summary": _domain_summary(domain),
                "anchorCount": _anchors_count(domain),
                "businessPointCount": len(domain.get("business_points", [])),
                "apiCount": sum(1 for item in apis if item.get("domain") == key),
                "fieldFlowCount": field_counts.get(key, 0),
                "dependencyCount": len(domain.get("dependencies", [])),
                "quality": "ready" if _anchors_count(domain) else "partial",
                "evidence": [_domain_wiki_evidence(key, "summary.md")],
            })
        return result

    def get_domain_detail(self, domain_key: str) -> dict[str, Any]:
        read_model = self._domain_read_model()
        if read_model:
            domain = next((item for item in read_model.get("domains", []) if item.get("domainKey") == domain_key), None)
            if not domain:
                return {
                    "domainKey": domain_key,
                    "health": {"status": "empty", "warnings": ["Domain not found"]},
                    "emptyState": _empty("Domain not found.", "Run graph-wiki build again or check the domain key."),
                }
            return _domain_detail_from_read_model(domain, read_model)

        domain = self._find_domain(domain_key)
        if not domain:
            return {
                "domainKey": domain_key,
                "health": {"status": "empty", "warnings": ["Domain not found"]},
                "emptyState": _empty("Domain not found.", "Run graph-wiki build again or check the domain key."),
            }

        grouped = {"coreActions": [], "interactions": [], "helpers": []}
        for point in domain.get("business_points", []):
            dto = _business_point_dto(_domain_key(domain), point)
            bucket = {
                "core_action": "coreActions",
                "interaction": "interactions",
                "helper": "helpers",
            }.get(point.get("point_type", "interaction"), "interactions")
            grouped[bucket].append(dto)

        field_flows = self.list_fields(domain_key=domain_key)
        rules_status = _manual_page_status(self.output_dir / "wiki" / domain_key / "rules.md")
        spec_status = _manual_page_status(self.output_dir / "wiki" / domain_key / "spec.md")

        return {
            "domainKey": _domain_key(domain),
            "displayName": _domain_display_name(domain),
            "businessMeaning": _domain_summary(domain),
            "health": {
                "status": "ready" if _anchors_count(domain) and domain.get("business_points") else "partial",
                "warnings": _domain_warnings(domain, rules_status, spec_status),
            },
            "entryFiles": _entry_files(domain),
            "businessPoints": grouped,
            "apis": [item for item in self.list_apis() if item.get("domainKey") == domain_key],
            "fieldFlows": {
                "status": "ready" if field_flows else "empty",
                "items": field_flows,
                "emptyState": None if field_flows else _empty(
                    "No field mapping was detected for this domain.",
                    "Check DTO/entity naming or add field mapping rules.",
                ),
            },
            "dependencies": domain.get("dependencies", []),
            "rules": {"status": rules_status, "wikiPage": f"wiki/{domain_key}/rules.md"},
            "spec": {"status": spec_status, "wikiPage": f"wiki/{domain_key}/spec.md"},
            "agentScope": self._agent_scope(domain_key),
            "evidence": [_domain_wiki_evidence(domain_key, "code-map.md")],
        }

    def list_apis(self, domain_key: str | None = None) -> list[dict[str, Any]]:
        read_model = self._domain_read_model()
        if read_model:
            result = []
            evidence_index = read_model.get("evidenceIndex", {})
            for domain in read_model.get("domains", []):
                if domain_key and domain.get("domainKey") != domain_key:
                    continue
                result.extend(_apis_from_read_model(domain, evidence_index))
            return result

        result = []
        for item in self._apis():
            domain = item.get("domain", "")
            if domain_key and domain != domain_key:
                continue

            frontend = item.get("frontend", {})
            backend = item.get("backend", {})
            callers = [
                caller.get("page", str(caller))
                for caller in frontend.get("callers", [])
            ]
            url = frontend.get("url") or backend.get("url", "")
            method = frontend.get("http_method") or backend.get("http_method", "")
            function_name = frontend.get("function_name") or backend.get("method_name", "")

            result.append({
                "apiId": item.get("id") or f"{method}:{url}",
                "method": method,
                "url": url,
                "domainKey": domain,
                "businessUse": _api_business_use(method, url, function_name, domain),
                "frontendCallers": callers,
                "frontendCallerStatus": "detected" if callers else "no_frontend_caller_detected",
                "backend": {
                    "controller": Path(backend.get("controller_file", "")).name,
                    "controllerPath": backend.get("controller_file", ""),
                    "method": backend.get("method_name", ""),
                    "serviceCall": (backend.get("service_chain") or [""])[0],
                },
                "dto": backend.get("param_type"),
                "confidence": item.get("match_confidence", 0),
                "evidence": [
                    _evidence("api_map", item.get("id", url), "api-map.json"),
                    _domain_wiki_evidence(domain, "api-docs.md"),
                ],
            })
        return result

    def list_fields(self, domain_key: str | None = None) -> list[dict[str, Any]]:
        read_model = self._domain_read_model()
        if read_model:
            result = []
            evidence_index = read_model.get("evidenceIndex", {})
            for domain in read_model.get("domains", []):
                if domain_key and domain.get("domainKey") != domain_key:
                    continue
                result.extend(_field_flow_items_from_read_model(domain, evidence_index))
            return result

        field_map = self._read_json("field-map.json", {})
        result = []
        for domain, tables in field_map.items():
            if domain_key and domain != domain_key:
                continue
            if not isinstance(tables, dict):
                continue
            for table, columns in tables.items():
                if not isinstance(columns, dict):
                    continue
                for column, mappings in columns.items():
                    for mapping in mappings if isinstance(mappings, list) else []:
                        result.append({
                            "fieldId": f"{table}.{column}",
                            "domainKey": domain,
                            "table": table,
                            "column": column,
                            "api": {
                                "method": _method_from_url(mapping.get("api_url", "")),
                                "url": mapping.get("api_url", ""),
                                "functionName": mapping.get("api_function", ""),
                            },
                            "dto": {
                                "className": mapping.get("dto_class", ""),
                                "field": mapping.get("dto_field", ""),
                            },
                            "entity": {
                                "className": mapping.get("entity_class", ""),
                                "field": mapping.get("entity_field", ""),
                            },
                            "frontendCallers": mapping.get("callers", []),
                            "confidence": mapping.get("confidence", 0),
                            "confidenceLabel": mapping.get("confidence_level", "unknown"),
                            "evidence": [
                                _evidence("field_map", f"{table}.{column}", "field-map.json"),
                                _domain_wiki_evidence(domain, "data-flow.md"),
                            ],
                        })
        return result

    def get_impact_summary(self) -> dict[str, Any]:
        impact = self._read_json("impact-analysis.json", {})
        examples = []
        for item in impact.get("query_examples", []):
            examples.append({
                "type": item.get("type", ""),
                "question": item.get("question", ""),
                "answer": item.get("answer", ""),
                "riskLevel": _impact_risk(item.get("type", "")),
                "recommendedActions": _recommended_actions(item),
                "evidence": _evidence_from_mapping(item.get("evidence", {}), "impact"),
            })
        return {
            "status": impact.get("acceptance", {}).get("status", "unknown") if impact else "unsupported",
            "queryExamples": examples,
            "coverage": impact.get("coverage", {}),
            "evidence": [_evidence("impact", "impact-analysis.json", "impact-analysis.json")] if impact else [],
        }

    def get_dream_cycle_summary(self) -> dict[str, Any]:
        dream = self._read_json("dream-cycle-report.json", {})
        if not dream:
            return {"status": "unsupported", "reviewQueue": []}

        detect = dream.get("detect", {})
        reconcile = dream.get("reconcile", {})
        synthesize = dream.get("synthesize", {})
        review_queue = []
        review_queue += _review_items("orphan_wiki_domain", reconcile.get("orphan_wiki_domains", []))
        review_queue += _review_items("archived_stale_domain", reconcile.get("archived_stale_domains", []))
        review_queue += _review_items("duplicate_business_point", synthesize.get("duplicate_business_points", []))

        return {
            "status": dream.get("quality", {}).get("status", "unknown"),
            "version": dream.get("schema", {}).get("version", ""),
            "changes": {
                "newFiles": len(detect.get("new_files", [])),
                "modifiedFiles": len(detect.get("modified_files", [])),
                "deletedFiles": len(detect.get("deleted_files", [])),
                "changedDomains": reconcile.get("changed_domains", []),
            },
            "maintenance": {
                "orphanWikiDomains": reconcile.get("orphan_wiki_domains", []),
                "archivedStaleDomains": reconcile.get("archived_stale_domains", []),
                "manualFilesProtected": len(dream.get("preserve", {}).get("manual_files", [])),
                "duplicateBusinessPoints": synthesize.get("duplicate_business_points", []),
            },
            "reviewQueue": review_queue,
            "message": "本次知识库稳定，无需人工处理。" if not review_queue else "本次知识库存在待审核事项。",
            "evidence": [_evidence("dream_cycle", "dream-cycle-report.json", "dream-cycle-report.json")],
        }

    def search(self, query: str) -> list[dict[str, Any]]:
        needle = query.lower().strip()
        if not needle:
            return []

        results = []
        for domain in self.list_domains():
            if _matches(needle, domain["domainKey"], domain["displayName"], domain["summary"]):
                results.append(_search_result("domain", domain["domainKey"], domain["displayName"], domain["summary"], domain["domainKey"]))

        for api in self.list_apis():
            if _matches(needle, api["url"], api["businessUse"], api.get("backend", {}).get("method", "")):
                results.append(_search_result("api", api["apiId"], api["url"], api["businessUse"], api["domainKey"]))

        read_model = self._domain_read_model()
        if read_model:
            for domain in read_model.get("domains", []):
                domain_key = domain.get("domainKey", "unknown")
                for flow in domain.get("flows", []):
                    title = flow.get("title", flow.get("flowId", ""))
                    if _matches(needle, flow.get("flowId", ""), title, flow.get("summary", "")):
                        results.append(_search_result("business_point", flow.get("flowId", ""), title, flow.get("summary", ""), domain_key))
        else:
            for domain in self._domains():
                for point in domain.get("business_points", []):
                    title = _business_title(point.get("name", ""))
                    if _matches(needle, point.get("name", ""), title, point.get("entry_file", "")):
                        results.append(_search_result("business_point", point.get("name", ""), title, point.get("entry_file", ""), _domain_key(domain)))

        for field in self.list_fields():
            if _matches(needle, field["fieldId"], field["api"]["url"], field["dto"]["className"]):
                results.append(_search_result("field", field["fieldId"], field["fieldId"], field["api"]["url"], field["domainKey"]))
        return results

    def _domain_read_model(self) -> dict[str, Any]:
        data = self._read_json("domain-read-model.json", {})
        return data if isinstance(data, dict) and data.get("schema", {}).get("version") == "domain-read-model-v1" else {}

    def _domains(self) -> list[dict[str, Any]]:
        data = self._read_json("domains.json", [])
        return data if isinstance(data, list) else []

    def _apis(self) -> list[dict[str, Any]]:
        data = self._read_json("api-map.json", [])
        return data if isinstance(data, list) else []

    def _find_domain(self, domain_key: str) -> dict[str, Any] | None:
        return next((item for item in self._domains() if _domain_key(item) == domain_key), None)

    def _field_counts_by_domain(self) -> dict[str, int]:
        counts: dict[str, int] = {}
        for item in self.list_fields():
            key = item["domainKey"]
            counts[key] = counts.get(key, 0) + 1
        return counts

    def _agent_scope(self, domain_key: str) -> dict[str, Any]:
        ontology = self._read_json("ontology.json", {})
        return ontology.get("agent_scope", {}).get("domains", {}).get(domain_key, {
            "entryFiles": [],
            "apis": [],
            "tables": [],
        })

    def _read_json(self, name: str, default: Any) -> Any:
        path = self.output_dir / name
        if not path.exists():
            return default
        return json.loads(path.read_text(encoding="utf-8"))


def _domain_detail_from_read_model(domain: dict[str, Any], read_model: dict[str, Any]) -> dict[str, Any]:
    evidence_index = read_model.get("evidenceIndex", {})
    domain_key = domain.get("domainKey", "unknown")
    flows = domain.get("flows", [])
    rules = domain.get("rules", [])
    field_rules = domain.get("fieldRules", [])
    status = domain.get("quality", {}).get("deepReadingStatus", "unknown")
    flow_points = [_flow_to_business_point(domain_key, flow, evidence_index) for flow in flows]
    return {
        "domainKey": domain_key,
        "displayName": domain.get("displayName", domain_key),
        "businessMeaning": domain.get("summary", ""),
        "health": {
            "status": status,
            "warnings": domain.get("quality", {}).get("warnings", []),
        },
        "entryFiles": _entry_files_from_evidence(evidence_index, domain.get("evidenceRefs", [])),
        "businessPoints": {
            "coreActions": flow_points,
            "interactions": [],
            "helpers": [],
        },
        "flows": flows,
        "apis": _apis_from_read_model(domain, evidence_index),
        "fieldFlows": {
            "status": "ready" if field_rules else "empty",
            "items": _field_flow_items_from_read_model(domain, evidence_index),
            "emptyState": None if field_rules else _empty(
                "No field rules were generated for this domain.",
                "Check DTO/entity/database annotations or field mapping rules.",
            ),
        },
        "fieldRules": field_rules,
        "dependencies": [],
        "rules": {
            "status": "ready" if rules else "empty",
            "wikiPage": f"wiki/{domain_key}/rules.md",
            "items": rules,
        },
        "spec": {"status": "derived", "wikiPage": f"wiki/{domain_key}/spec.md"},
        "deepReadingPath": {
            "order": ["flows", "rules", "evidence"],
            "flowCount": len(flows),
            "ruleCount": len(rules),
            "evidenceCount": len(domain.get("evidenceRefs", [])),
        },
        "evidence": _evidence_objects_from_refs(evidence_index, domain.get("evidenceRefs", [])),
    }


def _flow_to_business_point(domain_key: str, flow: dict[str, Any], evidence_index: dict[str, Any]) -> dict[str, Any]:
    return {
        "pointId": flow.get("flowId", f"{domain_key}:flow"),
        "domainKey": domain_key,
        "codeName": flow.get("flowId", ""),
        "businessTitle": flow.get("title", ""),
        "pointType": "core_action",
        "businessMeaning": flow.get("summary", ""),
        "entryFile": _first_source_path(evidence_index, flow.get("evidenceRefs", [])),
        "relatedApis": [ref for ref in flow.get("evidenceRefs", []) if str(ref).startswith("api:")],
        "relatedFields": [ref.removeprefix("field:") for ref in flow.get("evidenceRefs", []) if str(ref).startswith("field:")],
        "implementationFiles": [item.get("sourcePath", "") for item in _evidence_objects_from_refs(evidence_index, flow.get("evidenceRefs", [])) if item.get("type") == "source"],
        "testSuggestions": ["按流程步骤回归 flow → rules → evidence 阅读路径"],
        "evidence": _evidence_objects_from_refs(evidence_index, flow.get("evidenceRefs", [])),
    }


def _entry_files_from_evidence(evidence_index: dict[str, Any], refs: list[str]) -> list[dict[str, str]]:
    result = []
    for evidence in _evidence_objects_from_refs(evidence_index, refs):
        if evidence.get("type") != "source":
            continue
        path = evidence.get("sourcePath") or evidence.get("path") or ""
        result.append({"role": "source", "name": Path(path).name, "path": path})
    return result


def _apis_from_read_model(domain: dict[str, Any], evidence_index: dict[str, Any]) -> list[dict[str, Any]]:
    api_refs = _api_refs_for_domain(domain)
    result = []
    for ref in api_refs:
        method, url = _method_url_from_api_ref(ref)
        evidence = evidence_index.get(ref, {})
        result.append({
            "apiId": ref,
            "method": method,
            "url": url,
            "domainKey": domain.get("domainKey", ""),
            "businessUse": evidence.get("label", f"{method} {url}"),
            "frontendCallers": [],
            "frontendCallerStatus": "derived_from_domain_read_model",
            "backend": {"controller": "", "controllerPath": "", "method": "", "serviceCall": ""},
            "dto": "",
            "confidence": evidence.get("confidence", 0),
            "evidence": _evidence_objects_from_refs(evidence_index, [ref]),
        })
    return result


def _field_flow_items_from_read_model(domain: dict[str, Any], evidence_index: dict[str, Any]) -> list[dict[str, Any]]:
    result = []
    for rule in domain.get("fieldRules", []):
        field_id = rule.get("fieldId", "")
        table, _, column = field_id.partition(".")
        mapping = rule.get("mapping", {}) if isinstance(rule.get("mapping"), dict) else {}
        api_mapping = mapping.get("api", {}) if isinstance(mapping.get("api"), dict) else {}
        dto_mapping = mapping.get("dto", {}) if isinstance(mapping.get("dto"), dict) else {}
        entity_mapping = mapping.get("entity", {}) if isinstance(mapping.get("entity"), dict) else {}
        database_mapping = mapping.get("database", {}) if isinstance(mapping.get("database"), dict) else {}
        api_ref = next((ref for ref in rule.get("evidenceRefs", []) if str(ref).startswith("api:")), "")
        method, url = _method_url_from_api_ref(api_ref)
        mapping = _field_rule_mapping_from_read_model(rule, method, url)
        result.append({
            "fieldId": field_id,
            "domainKey": domain.get("domainKey", ""),
            "table": database_mapping.get("table") or table,
            "column": database_mapping.get("column") or column,
            "api": {
                "method": api_mapping.get("method") or method,
                "url": api_mapping.get("url") or url,
                "functionName": api_mapping.get("functionName", ""),
            },
            "dto": {
                "className": dto_mapping.get("className", ""),
                "field": dto_mapping.get("field", ""),
            },
            "entity": {
                "className": entity_mapping.get("className", ""),
                "field": entity_mapping.get("field", ""),
            },
            "frontendCallers": mapping.get("frontendCallers", []) if isinstance(mapping.get("frontendCallers"), list) else [],
            "confidence": rule.get("confidence", 0),
            "confidenceLabel": _confidence_label(rule.get("confidence")),
            "evidence": _evidence_objects_from_refs(evidence_index, rule.get("evidenceRefs", [])),
        })
    return result



def _field_rule_mapping_from_read_model(rule: dict[str, Any], method: str, url: str) -> dict[str, Any]:
    mapping = rule.get("mapping") if isinstance(rule.get("mapping"), dict) else {}
    api = mapping.get("api") if isinstance(mapping.get("api"), dict) else {}
    dto = mapping.get("dto") if isinstance(mapping.get("dto"), dict) else {}
    entity = mapping.get("entity") if isinstance(mapping.get("entity"), dict) else {}
    callers = mapping.get("frontendCallers", [])
    if not isinstance(callers, list):
        callers = []
    return {
        "api": {
            "method": api.get("method") or method,
            "url": api.get("url") or url,
            "functionName": api.get("functionName", ""),
        },
        "dto": {
            "className": dto.get("className", ""),
            "field": dto.get("field", ""),
            "file": dto.get("file", ""),
        },
        "entity": {
            "className": entity.get("className", ""),
            "field": entity.get("field", ""),
            "file": entity.get("file", ""),
        },
        "frontendCallers": [str(caller) for caller in callers if caller],
    }

def _evidence_objects_from_refs(evidence_index: dict[str, Any], refs: list[str]) -> list[dict[str, Any]]:
    result = []
    for ref in refs or []:
        evidence = dict(evidence_index.get(ref, {})) if isinstance(evidence_index.get(ref, {}), dict) else {}
        evidence.setdefault("id", ref)
        evidence.setdefault("type", ref.split(":", 1)[0] if ":" in str(ref) else "unknown")
        evidence.setdefault("label", ref)
        evidence.setdefault("path", "")
        evidence.setdefault("section", "")
        evidence.setdefault("confidence", None)
        evidence.setdefault("confidenceLabel", _confidence_label(evidence.get("confidence")))
        result.append(evidence)
    return result


def _api_refs_for_domain(domain: dict[str, Any]) -> list[str]:
    refs = []
    for container in [domain, *domain.get("flows", []), *domain.get("rules", []), *domain.get("fieldRules", [])]:
        for ref in container.get("evidenceRefs", []) if isinstance(container, dict) else []:
            if str(ref).startswith("api:") and ref not in refs:
                refs.append(ref)
    return refs


def _method_url_from_api_ref(ref: str) -> tuple[str, str]:
    if not ref or not str(ref).startswith("api:"):
        return "", ""
    try:
        _, method, url = str(ref).split(":", 2)
        return method, url
    except ValueError:
        return "", ""


def _first_source_path(evidence_index: dict[str, Any], refs: list[str]) -> str:
    for evidence in _evidence_objects_from_refs(evidence_index, refs):
        if evidence.get("type") == "source":
            return evidence.get("sourcePath") or evidence.get("path") or ""
    return ""


def _project_name(root: str) -> str:
    text = str(root).replace("\\", "/")
    if "svn-platform" in text.lower():
        return "SVN Platform"
    return Path(text).name or "Graph-Wiki Project"


def _nested_status(data: dict[str, Any], keys: list[str]) -> str:
    current: Any = data
    for key in keys:
        if not isinstance(current, dict):
            return "unknown"
        current = current.get(key)
    return current or "unknown"


def _domain_key(domain: dict[str, Any]) -> str:
    return domain.get("id") or domain.get("name") or "unknown"


def _domain_display_name(domain: dict[str, Any]) -> str:
    if domain.get("display_name"):
        return domain["display_name"]
    mapping = {"svn": "SVN 操作", "repository": "仓库管理", "requirement": "需求管理"}
    return mapping.get(_domain_key(domain), _domain_key(domain))


def _domain_summary(domain: dict[str, Any]) -> str:
    if domain.get("description"):
        return domain["description"]
    return f"{_domain_display_name(domain)}负责该业务域的页面、接口、业务动作和代码证据。"


def _anchors_count(domain: dict[str, Any]) -> int:
    return sum(len(items) for items in domain.get("anchors", {}).values() if isinstance(items, list))


def _entry_files(domain: dict[str, Any]) -> list[dict[str, str]]:
    result = []
    for role, anchors in domain.get("anchors", {}).items():
        for anchor in anchors if isinstance(anchors, list) else []:
            result.append({
                "role": role,
                "name": anchor.get("label", Path(anchor.get("source_file", "")).name),
                "path": anchor.get("source_file", ""),
            })
    return result


def _business_point_dto(domain_key: str, point: dict[str, Any]) -> dict[str, Any]:
    code_name = point.get("name", "")
    return {
        "pointId": f"{domain_key}:{_slug(code_name)}",
        "domainKey": domain_key,
        "codeName": code_name,
        "businessTitle": point.get("display_name") or _business_title(code_name),
        "pointType": point.get("point_type", "interaction"),
        "businessMeaning": _business_point_meaning(code_name, point.get("point_type", "interaction")),
        "entryFile": point.get("entry_file", ""),
        "relatedApis": [],
        "relatedFields": [],
        "implementationFiles": [point.get("entry_file", "")] if point.get("entry_file") else [],
        "testSuggestions": _test_suggestions(point.get("point_type", "interaction")),
        "evidence": [_evidence("source_file", point.get("entry_file", code_name), point.get("entry_file", ""))],
    }


def _business_title(code_name: str) -> str:
    lowered = code_name.lower()
    if "executemerge" in lowered or "svnmerge" in lowered:
        return "执行 SVN 合并"
    if "previewmerge" in lowered:
        return "预览 SVN 合并"
    if "filediff" in lowered or "viewdiff" in lowered:
        return "查看文件差异"
    if "updaterepos" in lowered:
        return "刷新合并仓库数据"
    if "import" in lowered:
        return "导入仓库"
    if "commit" in lowered:
        return "提交 SVN 变更"
    if "branch" in lowered or "tag" in lowered:
        return "维护分支标签"
    if "log" in lowered:
        return "查看 SVN 日志"
    if "status" in lowered:
        return "查看工作副本状态"
    return _humanize_code_name(code_name)


def _business_point_meaning(code_name: str, point_type: str) -> str:
    title = _business_title(code_name)
    if point_type == "core_action":
        return f"{title}是会改变业务状态或触发关键流程的核心动作。"
    if point_type == "helper":
        return f"{title}用于辅助页面展示或流程处理，不直接代表核心业务结果。"
    return f"{title}用于查询、查看或响应用户交互。"


def _api_business_use(method: str, url: str, function_name: str, domain: str) -> str:
    text = f"{function_name} {url}".lower()
    if "merge/preview" in text:
        return "预览 SVN 合并结果"
    if "merge/execute" in text or "merge" in text:
        return "执行 SVN 合并"
    if "commit" in text:
        return "提交 SVN 工作副本变更"
    if "repo/import" in text or "import" in text:
        return "导入本地 SVN 仓库"
    if "status" in text:
        return "查询工作副本状态"
    if "log" in text:
        return "查询 SVN 日志"
    if "requirement" in text:
        return "维护需求记录"
    if method.upper() == "GET":
        return f"查询{_domain_label(domain)}相关数据"
    if method.upper() == "DELETE":
        return f"删除{_domain_label(domain)}相关数据"
    if method.upper() == "PUT":
        return f"更新{_domain_label(domain)}相关数据"
    return f"提交{_domain_label(domain)}相关数据或执行关键变更"


def _domain_label(domain: str) -> str:
    return {"svn": "SVN 操作", "repository": "仓库管理", "requirement": "需求管理"}.get(domain, domain)


def _manual_page_status(path: Path) -> str:
    if not path.exists():
        return "missing"
    text = path.read_text(encoding="utf-8")
    stripped = re.sub(r"#.*", "", text).strip()
    if not stripped or "*待填写*" in stripped:
        return "placeholder"
    return "ready"


def _domain_warnings(domain: dict[str, Any], rules_status: str, spec_status: str) -> list[str]:
    warnings = []
    if not domain.get("business_points"):
        warnings.append("No business points detected.")
    if rules_status == "placeholder":
        warnings.append("Rules page is still a placeholder.")
    if spec_status == "placeholder":
        warnings.append("Spec page is still a placeholder.")
    return warnings


def _field_counts_by_table(field_map: dict[str, Any]) -> int:
    count = 0
    for columns in field_map.values():
        if isinstance(columns, dict):
            for mappings in columns.values():
                if isinstance(mappings, list):
                    count += len(mappings)
    return count


def _recommended_reading(domains: list[dict[str, Any]]) -> list[dict[str, str]]:
    return [
        {"label": item["displayName"], "targetRoute": f"/domains/{item['domainKey']}"}
        for item in domains[:5]
    ]


def _impact_risk(kind: str) -> str:
    return {
        "field_change": "high",
        "api_change": "medium",
        "business_point_change": "medium",
        "rule_trace": "medium",
        "domain_dependency": "high",
    }.get(kind, "medium")


def _recommended_actions(item: dict[str, Any]) -> list[str]:
    kind = item.get("type", "")
    if kind == "field_change":
        return ["检查字段链路", "回归相关 API", "验证前端页面展示和提交"]
    if kind == "api_change":
        return ["检查前端调用者", "回归 Controller 和 DTO", "确认接口兼容性"]
    if kind == "business_point_change":
        return ["阅读业务点入口文件", "检查关联 API", "补充页面回归测试"]
    if kind == "domain_dependency":
        return ["检查依赖域变更", "确认跨域字段或表引用", "扩大回归范围"]
    return ["查看证据链", "补充人工确认"]


def _review_items(kind: str, items: list[Any]) -> list[dict[str, Any]]:
    return [{"type": kind, "item": item} for item in items]


def _method_from_url(url: str) -> str:
    return ""


def _test_suggestions(point_type: str) -> list[str]:
    if point_type == "core_action":
        return ["补充核心动作成功路径测试", "补充失败和回滚场景测试"]
    if point_type == "helper":
        return ["补充格式化或边界输入测试"]
    return ["补充页面交互和接口调用回归测试"]


def _evidence(kind: str, label: str, path: str, confidence: float | None = None) -> dict[str, Any]:
    return {
        "type": kind,
        "label": label,
        "path": path,
        "section": "",
        "confidence": confidence,
        "confidenceLabel": _confidence_label(confidence),
    }


def _evidence_from_mapping(mapping: dict[str, Any], kind: str) -> list[dict[str, Any]]:
    result = []
    for key, value in mapping.items():
        if isinstance(value, str):
            result.append(_evidence(kind, key, value))
    return result


def _domain_wiki_evidence(domain_key: str, page: str) -> dict[str, Any]:
    return _evidence("wiki_page", f"{domain_key}/{page}", f"wiki/{domain_key}/{page}")


def _confidence_label(confidence: float | None) -> str:
    if confidence is None:
        return "unknown"
    if confidence >= 0.85:
        return "high"
    if confidence >= 0.6:
        return "medium"
    return "low"


def _empty(reason: str, recommended_action: str) -> dict[str, str]:
    return {"status": "empty", "reason": reason, "recommendedAction": recommended_action}


def _humanize_code_name(name: str) -> str:
    text = re.sub(r"\.(vue|java|js|ts)$", "", name)
    text = text.replace("()", "")
    text = re.sub(r"([a-z])([A-Z])", r"\1 \2", text)
    return text.replace("_", " ").replace("-", " ").strip() or name


def _slug(text: str) -> str:
    return re.sub(r"[^a-zA-Z0-9_-]+", "-", text).strip("-")


def _matches(needle: str, *values: str) -> bool:
    return any(needle in str(value).lower() for value in values if value)


def _search_result(kind: str, item_id: str, title: str, subtitle: str, domain_key: str) -> dict[str, Any]:
    return {
        "id": item_id,
        "type": kind,
        "title": title,
        "subtitle": subtitle,
        "domainKey": domain_key,
        "matchedText": title,
        "targetRoute": f"/{kind}s/{item_id}",
        "evidence": [],
    }
