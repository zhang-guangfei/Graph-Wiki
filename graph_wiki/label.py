"""LLM 标注：为业务域生成名称、描述、summary.md"""

import json
import re
import time
from datetime import datetime
from pathlib import Path
from enum import Enum
from concurrent.futures import ThreadPoolExecutor, as_completed
from dataclasses import dataclass

from .models import Domain


class LlmBackend(Enum):
    CLAUDE = "claude"
    OPENAI = "openai"
    GEMINI = "gemini"


@dataclass
class LabelConfig:
    backend: LlmBackend = LlmBackend.CLAUDE
    model: str = "claude-haiku-4-5-20251001"
    parallel_calls: int = 5
    retry_count: int = 3
    sampling_lines: int = 50


def label_domains(
    domains: list[Domain],
    graph_data: dict,
    backend_root: Path,
    config: LabelConfig = LabelConfig(),
) -> list[Domain]:
    with ThreadPoolExecutor(max_workers=config.parallel_calls) as executor:
        futures = {
            executor.submit(
                _label_single, d, graph_data, backend_root, config
            ): d
            for d in domains
        }
        results = []
        for future in as_completed(futures):
            domain = futures[future]
            try:
                result = future.result()
                results.append(result)
            except Exception:
                results.append(domain)
        return results


def _label_single(
    domain: Domain,
    graph_data: dict,
    backend_root: Path,
    config: LabelConfig,
) -> Domain:
    prompt = _build_prompt(domain, backend_root, config)
    response = _call_llm(prompt, config)
    result = _parse_response(response)

    domain.name = result.get("name", domain.id)
    domain.display_name = result.get("display_name", domain.id)

    for bp in domain.business_points:
        bp_name = bp.name.lstrip(".")
        bp.display_name = result.get("business_points", {}).get(bp_name, bp_name)

    _write_summary(domain, result, backend_root)
    return domain


def _build_prompt(domain: Domain, backend_root: Path, config: LabelConfig) -> str:
    # 采样核心 Service 源码
    sample = ""
    service_anchors = domain.anchors.get("service_impl", [])[:2]
    for anchor in service_anchors:
        src_file = anchor.get("source_file", "")
        if src_file:
            try:
                lines = (backend_root / src_file).read_text(encoding="utf-8").split("\n")
                sample += f"\n## {src_file}\n" + "\n".join(lines[:config.sampling_lines])
            except (IOError, UnicodeDecodeError):
                pass

    controllers = [a.get("label", "") for a in domain.anchors.get("controller", [])]
    services = [a.get("label", "") for a in domain.anchors.get("service_api", [])] + \
               [a.get("label", "") for a in domain.anchors.get("service_impl", [])]
    mappers = [a.get("label", "") for a in domain.anchors.get("mapper", [])] + \
              [a.get("label", "") for a in domain.anchors.get("dao", [])]
    deps = [f"- {k}: {v} 次 import" for k, v in sorted(domain.dependencies.items(), key=lambda x: -x[1])[:5]]

    return f"""你是代码库业务分析专家。根据以下信息分析业务模块。

## 包路径
{chr(10).join(domain.packages[:5])}

## Controller ({len(controllers)}个)
{chr(10).join(f"- {c}" for c in controllers[:10])}

## Service ({len(services)}个)
{chr(10).join(f"- {s}" for s in services[:10])}

## Mapper/DAO ({len(mappers)}个)
{chr(10).join(f"- {m}" for m in mappers[:10])}

## 跨域依赖
{chr(10).join(deps) if deps else "无"}

## 源码采样
{sample or "无"}

返回纯 JSON（不要其他文字）：
{{
  "name": "英文ID(2-3词)",
  "display_name": "中文名(2-5字)",
  "description": "一句话业务目标",
  "core_flows": [{{"name":"流程","steps":["步骤1","步骤2"]}}],
  "dependencies": [{{"domain":"依赖域","reason":"原因"}}]
}}"""


def _call_llm(prompt: str, config: LabelConfig) -> str:
    if config.backend == LlmBackend.CLAUDE:
        from anthropic import Anthropic
        client = Anthropic()
        resp = client.messages.create(
            model=config.model,
            max_tokens=1024,
            messages=[{"role": "user", "content": prompt}],
        )
        return resp.content[0].text
    else:
        raise ValueError(f"Unsupported backend: {config.backend}")


def _parse_response(response: str) -> dict:
    try:
        return json.loads(response)
    except json.JSONDecodeError:
        pass
    match = re.search(r"\{[\s\S]*\}", response)
    if match:
        try:
            return json.loads(match.group(0))
        except json.JSONDecodeError:
            pass
    return {"name": "", "display_name": "", "description": ""}


def _write_summary(domain: Domain, result: dict, backend_root: Path):
    wiki_dir = Path("wiki") / (domain.name or domain.id)
    wiki_dir.mkdir(parents=True, exist_ok=True)

    lines = [
        f"---",
        f"domain: {result.get('display_name', domain.id)}",
        f"anchors: {domain.anchors_count()}",
        f"updated: {datetime.now().isoformat()}",
        f"---",
        f"",
        f"# {result.get('display_name', domain.id)}",
        f"",
        f"## 业务目标",
        f"{result.get('description', '')}",
        f"",
    ]

    for flow in result.get("core_flows", []):
        lines.append(f"### {flow.get('name', '')}")
        for i, step in enumerate(flow.get("steps", []), 1):
            lines.append(f"{i}. {step}")
        lines.append("")

    lines += [
        "## 代码入口",
        "",
        "| 层级 | 文件 |",
        "|------|------|",
    ]
    for role_name, anchors in domain.anchors.items():
        for a in anchors[:3]:
            src = a.get("source_file", "")
            label = a.get("label", "")
            lines.append(f"| {role_name} | [{label}]({src}) |")

    lines += [
        "",
        "## 关联域",
    ]
    for dep in result.get("dependencies", []):
        lines.append(f"- [[{dep.get('domain', '')}]] — {dep.get('reason', '')}")

    lines += [
        "",
        "---",
        "*此文档由 LLM 自动生成，请人工审核确认。*",
    ]

    (wiki_dir / "summary.md").write_text("\n".join(lines), encoding="utf-8")


def is_locked(summary_path: Path) -> bool:
    if not summary_path.exists():
        return False
    first_line = summary_path.read_text(encoding="utf-8").split("\n")[0]
    return "@locked" in first_line
