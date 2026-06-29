"""Phase 5 lightweight Dream Cycle maintenance report."""

from collections import Counter
from pathlib import Path
from typing import Any

from .models import Domain


DREAM_VERSION = "dream-cycle-v0"


def build_dream_cycle_report(
    wiki_root: Path,
    domains: list[Domain],
    previous_manifest: dict | None,
    current_manifest: dict,
    impact_analysis: dict | None = None,
) -> dict[str, Any]:
    previous_manifest = previous_manifest or {"files": {}, "domains": {}}
    impact_analysis = impact_analysis or {}
    detect = _diff_files(previous_manifest.get("files", {}), current_manifest.get("files", {}))
    reconcile = _diff_domains(previous_manifest.get("domains", {}), current_manifest.get("domains", {}))
    current_domain_keys = {domain.name or domain.id for domain in domains}
    reconcile["orphan_wiki_domains"] = sorted(_wiki_domain_dirs(wiki_root) - current_domain_keys)
    reconcile["archived_stale_domains"] = _archived_stale_domains(wiki_root)
    backlink = _check_dependency_backlinks(wiki_root, domains)
    warnings = []
    if backlink["status"] != "passed":
        warnings.append("存在缺失的域依赖回链")
    return {
        "schema": {"version": DREAM_VERSION},
        "detect": detect,
        "reconcile": reconcile,
        "preserve": {"manual_files": _manual_file_status(wiki_root, domains)},
        "backlink": backlink,
        "synthesize": {"duplicate_business_points": _duplicate_business_points(domains)},
        "validate": {"impact_available": bool(impact_analysis.get("impacts"))},
        "quality": {"status": "warning" if warnings else "passed", "warnings": warnings, "errors": []},
    }


def render_changelog(report: dict) -> str:
    detect = report.get("detect", {})
    reconcile = report.get("reconcile", {})
    lines = [
        "# Dream Cycle Changelog",
        "",
        "- 新增文件：{}".format(len(detect.get("new_files", []))),
        "- 修改文件：{}".format(len(detect.get("modified_files", []))),
        "- 删除文件：{}".format(len(detect.get("deleted_files", []))),
        "- 变更业务域：{}".format(", ".join(reconcile.get("changed_domains", [])) or "—"),
        "- 移除业务域：{}".format(", ".join(reconcile.get("removed_domains", [])) or "—"),
        "- 孤立 Wiki 域：{}".format(", ".join(reconcile.get("orphan_wiki_domains", [])) or "—"),
        "- 归档旧域：{}".format(", ".join(reconcile.get("archived_stale_domains", [])) or "—"),
        "",
        "质量状态：{}".format(report.get("quality", {}).get("status", "unknown")),
    ]
    return "\n".join(lines)


def _diff_files(previous: dict, current: dict) -> dict[str, list[str]]:
    previous_keys = set(previous)
    current_keys = set(current)
    common = previous_keys & current_keys
    return {
        "new_files": sorted(current_keys - previous_keys),
        "modified_files": sorted(
            path for path in common
            if previous.get(path, {}).get("hash") != current.get(path, {}).get("hash")
        ),
        "deleted_files": sorted(previous_keys - current_keys),
        "unchanged_files": sorted(
            path for path in common
            if previous.get(path, {}).get("hash") == current.get(path, {}).get("hash")
        ),
    }


def _diff_domains(previous: dict, current: dict) -> dict[str, list[str]]:
    previous_keys = set(previous)
    current_keys = set(current)
    common = previous_keys & current_keys
    return {
        "new_domains": sorted(current_keys - previous_keys),
        "removed_domains": sorted(previous_keys - current_keys),
        "changed_domains": sorted(
            domain for domain in common
            if previous.get(domain, {}).get("anchors_hash") != current.get(domain, {}).get("anchors_hash")
        ),
        "unchanged_domains": sorted(
            domain for domain in common
            if previous.get(domain, {}).get("anchors_hash") == current.get(domain, {}).get("anchors_hash")
        ),
    }


def _manual_file_status(wiki_root: Path, domains: list[Domain]) -> list[dict[str, str]]:
    results = []
    for domain in domains:
        key = domain.name or domain.id
        for filename in ("rules.md", "spec.md"):
            path = wiki_root / key / filename
            text = path.read_text(encoding="utf-8", errors="ignore") if path.exists() else ""
            status = "preserved" if path.exists() and "*待填写*" not in text else "placeholder"
            results.append({"domain": key, "file": filename, "status": status})
    return results


def _wiki_domain_dirs(wiki_root: Path) -> set[str]:
    if not wiki_root.exists():
        return set()
    return {
        child.name for child in wiki_root.iterdir()
        if child.is_dir() and not child.name.startswith("_")
    }


def _archived_stale_domains(wiki_root: Path) -> list[str]:
    stale_root = wiki_root / "_stale"
    if not stale_root.exists():
        return []
    return sorted({
        path.name
        for path in stale_root.rglob("*")
        if path.is_dir() and path.parent != stale_root
    })


def _check_dependency_backlinks(wiki_root: Path, domains: list[Domain]) -> dict:
    missing = []
    for domain in domains:
        key = domain.name or domain.id
        path = wiki_root / key / "dependencies.md"
        text = path.read_text(encoding="utf-8", errors="ignore") if path.exists() else ""
        for dep in domain.dependencies:
            target = dep.get("domain", "")
            if target and f"[[{target}]]" not in text:
                missing.append({"domain": key, "target": target, "file": f"{key}/dependencies.md"})
    return {"status": "passed" if not missing else "warning", "missing": missing}


def _duplicate_business_points(domains: list[Domain]) -> list[dict]:
    duplicates = []
    for domain in domains:
        key = domain.name or domain.id
        counts = Counter(
            point.name
            for point in domain.business_points
            if getattr(point, "point_type", "") != "helper"
        )
        for name, count in sorted(counts.items()):
            if count > 1:
                duplicates.append({"domain": key, "business_point": name, "count": count})
    return duplicates
