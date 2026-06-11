"""字段级数据流追踪：Entity注解→DB列、DTO→Entity匹配、六层链路"""

import re
from pathlib import Path
from collections import defaultdict

from .models import ApiMatch


def camel_to_snake(name: str) -> str:
    return re.sub(r"([A-Z])", r"_\1", name).lower().lstrip("_")


def _extract_class_name(source: str) -> str:
    match = re.search(r"public\s+class\s+(\w+)", source)
    return match.group(1) if match else ""


def is_entity_class(source: str) -> bool:
    return any(kw in source for kw in ("@TableName", "@Entity", "@Table("))


def extract_table_name(source: str, class_name: str) -> str:
    for pat in [
        re.compile(r'@TableName\s*\(\s*["\']([^"\']+)["\']'),
        re.compile(r'@Table\s*\(\s*name\s*=\s*["\']([^"\']+)["\']'),
    ]:
        match = pat.search(source)
        if match:
            return match.group(1)
    return camel_to_snake(class_name)


def extract_db_column(annotation_block: str, field_name: str) -> str:
    if not annotation_block:
        return camel_to_snake(field_name)
    match = re.search(r'@TableField\s*\(\s*["\']([^"\']+)["\']', annotation_block)
    if match:
        return match.group(1)
    match = re.search(r'@Column\s*\([^)]*name\s*=\s*["\']([^"\']+)["\']', annotation_block)
    if match:
        return match.group(1)
    return camel_to_snake(field_name)


def extract_entity_db_mapping(entity_dir: Path, backend_root: Path) -> dict:
    entity_map = {}
    for java_file in entity_dir.rglob("*.java"):
        if "/src/test/" in str(java_file):
            continue
        try:
            source = java_file.read_text(encoding="utf-8")
        except (IOError, UnicodeDecodeError):
            continue
        if not is_entity_class(source):
            continue
        class_name = _extract_class_name(source)
        table_name = extract_table_name(source, class_name)

        fields = []
        field_pattern = re.compile(
            r"""(@\w+(?:\([^)]*\))?\s*)*private\s+(\S+)\s+(\w+)\s*;"""
        )
        for match in field_pattern.finditer(source):
            annotation_block = match.group(1) or ""
            fields.append({
                "java_field": match.group(3),
                "db_column": extract_db_column(annotation_block, match.group(3)),
                "java_type": match.group(2),
                "annotation": annotation_block.strip() if annotation_block else "",
            })

        entity_map[class_name] = {
            "table": table_name,
            "file": str(java_file.relative_to(backend_root)),
            "fields": fields,
        }
    return entity_map


def extract_dto_fields(dto_dir: Path, backend_root: Path) -> dict[str, list[dict]]:
    dto_map = defaultdict(list)
    for java_file in dto_dir.rglob("*.java"):
        if "/src/test/" in str(java_file):
            continue
        if not ("dto" in java_file.name.lower() or "vo" in java_file.name.lower()):
            continue
        try:
            source = java_file.read_text(encoding="utf-8")
        except (IOError, UnicodeDecodeError):
            continue
        class_name = _extract_class_name(source)
        for match in re.finditer(r"private\s+(\S+)\s+(\w+)\s*;", source):
            dto_map[class_name].append({"name": match.group(2), "type": match.group(1)})
    return dict(dto_map)


def field_match_score(dto_name: str, dto_type: str, entity_name: str, entity_type: str) -> tuple[float, str]:
    if dto_name == entity_name:
        return 1.0, "exact"
    if camel_to_snake(dto_name) == camel_to_snake(entity_name):
        return 0.9, "snake_match"
    if dto_type == entity_type and (
        dto_name.lower() in entity_name.lower()
        or entity_name.lower() in dto_name.lower()
    ):
        return 0.6, "type_and_substring"
    return 0.0, "none"


def find_entity_candidates(dto_class: str, entity_map: dict) -> list[str]:
    base = dto_class.replace("DTO", "").replace("Dto", "").replace("VO", "").replace("Vo", "")
    candidates = []
    for name in entity_map:
        if base.lower() in name.lower() or name.lower() in base.lower():
            candidates.append(name)
    return candidates or list(entity_map.keys())


def match_dto_to_entity(dto_map: dict, entity_map: dict) -> list[dict]:
    results = []
    for dto_class, dto_fields in dto_map.items():
        candidates = find_entity_candidates(dto_class, entity_map)
        for dto_field in dto_fields:
            best_match, best_score = None, 0
            for entity_class in candidates:
                if entity_class not in entity_map:
                    continue
                for ef in entity_map[entity_class]["fields"]:
                    score, mtype = field_match_score(
                        dto_field["name"], dto_field["type"],
                        ef["java_field"], ef["java_type"],
                    )
                    if score > best_score:
                        best_score = score
                        best_match = {
                            "dto_class": dto_class,
                            "dto_field": dto_field["name"],
                            "entity_class": entity_class,
                            "entity_field": ef["java_field"],
                            "db_column": ef["db_column"],
                            "db_table": entity_map[entity_class]["table"],
                            "confidence": score,
                            "match_type": mtype,
                        }
            if best_match:
                results.append(best_match)
    return results


def build_field_map(
    api_matches: list[ApiMatch],
    entity_dir: Path,
    dto_dir: Path,
    backend_root: Path,
) -> dict:
    entity_map = extract_entity_db_mapping(entity_dir, backend_root) if entity_dir.exists() else {}
    dto_map = extract_dto_fields(dto_dir, backend_root) if dto_dir.exists() else {}
    field_matches = match_dto_to_entity(dto_map, entity_map)

    # 构建 domain → table → fields 聚合
    domain_tables: dict[str, dict] = defaultdict(lambda: defaultdict(lambda: defaultdict(list)))

    for match in api_matches:
        domain = match.domain or "unknown"
        dto_class = match.backend.param_type or ""
        for fm in field_matches:
            if fm["dto_class"] == dto_class:
                entry = {
                    "api_url": match.frontend.url,
                    "api_function": match.frontend.function_name,
                    "dto_field": fm["dto_field"],
                    "entity_class": fm["entity_class"],
                    "entity_field": fm["entity_field"],
                    "db_column": fm["db_column"],
                    "db_table": fm["db_table"],
                    "callers": [
                        c.get("page", "") for c in match.frontend.callers
                    ],
                    "confidence": fm["confidence"],
                }
                domain_tables[domain][fm["db_table"]][fm["db_column"]].append(entry)

    return dict(domain_tables)
