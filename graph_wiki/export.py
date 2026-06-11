"""Wiki 导出：生成 Obsidian 兼容的双向链接 Markdown 文档库"""

import json
from pathlib import Path
from datetime import datetime

from .models import Domain, ApiMatch


def export_wiki(
    domains: list[Domain],
    api_matches: list[ApiMatch],
    field_map: dict,
    output_dir: Path = Path("wiki"),
):
    output_dir.mkdir(parents=True, exist_ok=True)

    _write_index(domains, output_dir)
    _write_api_index(api_matches, output_dir)

    for domain in domains:
        dir_name = domain.name or domain.id
        domain_dir = output_dir / dir_name
        domain_dir.mkdir(parents=True, exist_ok=True)

        _write_code_map(domain, domain_dir)
        _write_api_docs(domain, api_matches, domain_dir)
        _write_dependencies(domain, domain_dir)

        if domain.id in field_map or domain.name in field_map:
            _write_data_flow(domain, field_map, domain_dir)

        # 占位文件（人工填写）
        for fname in ("rules.md", "spec.md"):
            p = domain_dir / fname
            if not p.exists():
                p.write_text(
                    f"# {domain.name or domain.id} — {fname.replace('.md', '').upper()}\n\n*待填写*\n",
                    encoding="utf-8",
                )


def _write_index(domains: list[Domain], output_dir: Path):
    lines = [
        f"# 业务域目录",
        f"",
        f"> {len(domains)} 个业务域 | 自动生成于 {datetime.now():%Y-%m-%d}",
        f"",
        f"| 业务域 | 锚点 | 文件 | 依赖域 |",
        f"|--------|------|------|--------|",
    ]
    for d in domains:
        deps = ", ".join(f"[[{k}]]" for k in list(d.dependencies.keys())[:3]) or "—"
        lines.append(
            f"| [[{d.name or d.id}]] | {d.anchors_count()} | {d.total_files} | {deps} |"
        )
    (output_dir / "index.md").write_text("\n".join(lines), encoding="utf-8")


def _write_api_index(api_matches: list[ApiMatch], output_dir: Path):
    by_domain: dict[str, list[ApiMatch]] = {}
    for m in api_matches:
        d = m.domain or "未分类"
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
        lines.append("| 方法 | URL | 前端调用者 | 后端 Controller |")
        lines.append("|------|-----|-----------|----------------|")
        for m in matches[:20]:
            callers = ", ".join(c.get("page", "") for c in m.frontend.callers[:3]) or "—"
            lines.append(
                f"| {m.frontend.http_method} | {m.frontend.url} | {callers} | "
                f"{m.backend.controller_class}.{m.backend.method_name}() |"
            )
        lines.append("")
    (output_dir / "api-index.md").write_text("\n".join(lines), encoding="utf-8")


def _write_code_map(domain: Domain, domain_dir: Path):
    lines = [f"# {domain.name or domain.id} — 代码地图", ""]
    for role_name, anchors in domain.anchors.items():
        labels = [a.get("label", "") for a in anchors[:10]]
        if labels:
            lines.append(f"## {role_name}")
            lines.append("")
            for lbl in sorted(labels):
                lines.append(f"- {lbl}")
            lines.append("")

    bps = domain.business_points
    if bps:
        lines.append(f"## 业务点（{len(bps)} 个）")
        lines.append("")
        for bp in bps[:20]:
            lines.append(f"- **{bp.display_name or bp.name.lstrip('.')}**")

    (domain_dir / "code-map.md").write_text("\n".join(lines), encoding="utf-8")


def _write_api_docs(domain: Domain, api_matches: list[ApiMatch], domain_dir: Path):
    domain_matches = [
        m for m in api_matches
        if m.domain in (domain.name, domain.id)
    ]
    if not domain_matches:
        return

    lines = [f"# {domain.name or domain.id} — API 文档", ""]
    for m in domain_matches:
        lines += [
            f"## {m.frontend.http_method} {m.frontend.url}",
            f"",
            f"- **前端**: `{m.frontend.source_file}` → `{m.frontend.function_name}()`",
            f"- **后端**: `{m.backend.controller_class}.{m.backend.method_name}()`",
            f"- **入参**: `{m.backend.param_type or '无'}`",
            f"- **匹配置信度**: {m.match_confidence:.0%}",
            f"",
        ]
        if m.frontend.callers:
            lines.append("**调用者**:")
            for c in m.frontend.callers[:5]:
                lines.append(f"- {c.get('page', '')}")
            lines.append("")
    (domain_dir / "api-docs.md").write_text("\n".join(lines), encoding="utf-8")


def _write_dependencies(domain: Domain, domain_dir: Path):
    lines = [f"# {domain.name or domain.id} — 域间依赖", ""]
    for dep_id, count in sorted(domain.dependencies.items(), key=lambda x: -x[1]):
        strength = "强" if count >= 50 else ("中" if count >= 20 else "弱")
        lines.append(f"- [[{dep_id}]] — {count} 次 import ({strength}依赖)")
    if not domain.dependencies:
        lines.append("*无跨域依赖*")
    (domain_dir / "dependencies.md").write_text("\n".join(lines), encoding="utf-8")


def _write_data_flow(domain: Domain, field_map: dict, domain_dir: Path):
    key = domain.id or domain.name
    tables = field_map.get(key, field_map.get(domain.name, {}))
    if not tables:
        return

    lines = [f"# {domain.name or domain.id} — 数据流", ""]
    for table, columns in tables.items():
        lines.append(f"## {table}")
        lines.append("")
        for col, entries in columns.items():
            lines.append(f"### {col}")
            for e in entries[:3]:
                callers = ", ".join(e.get("callers", []))
                lines.append(f"- {e.get('api_url', '')} → {e.get('dto_field', '')} → {e.get('entity_field', '')}")
                if callers:
                    lines.append(f"  - 前端: {callers}")
        lines.append("")
    (domain_dir / "data-flow.md").write_text("\n".join(lines), encoding="utf-8")
