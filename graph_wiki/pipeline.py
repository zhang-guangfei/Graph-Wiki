"""CLI 入口：graph-wiki build | update | query | path | explain"""

import argparse
import sys
import os
import json
import time
from dataclasses import asdict, is_dataclass
from pathlib import Path

# 强制 UTF-8 输出，解决 Windows GBK 终端中文乱码
if sys.stdout.encoding != "utf-8":
    sys.stdout.reconfigure(encoding="utf-8")
if sys.stderr.encoding != "utf-8":
    sys.stderr.reconfigure(encoding="utf-8")

from .reuse import (
    build_graph,
    build_light_graph,
    detect_corpus,
    extract_ast,
    filter_extraction_for_wiki,
    save_graph_artifacts,
)
from .cluster import business_cluster, Language
from .api_mapper import build_api_map
from .field_mapper import build_field_map
from .label import label_domains, LabelConfig, LlmBackend
from .export import evaluate_wiki_quality, export_wiki
from .ontology import build_code_ontology, collect_wiki_evidence, evaluate_phase3_acceptance
from .impact import build_impact_analysis, evaluate_phase4_acceptance, write_impact_wiki
from .dream import build_dream_cycle_report, render_changelog
from .product_data import ProductDataService
from .domain_read_model import build_domain_read_model, product_quality_for_report
from .visualize import export_domain_html

try:
    from .label import label_domains, LabelConfig, LlmBackend
except ImportError:
    label_domains = None
    LabelConfig = None
    LlmBackend = None


def main():
    parser = argparse.ArgumentParser(prog="graph-wiki")
    sub = parser.add_subparsers(dest="command")

    # build
    bp = sub.add_parser("build", help="完整构建")
    bp.add_argument("path", type=Path, default=Path("."), nargs="?")
    bp.add_argument("--name", default=None, help="项目名称")
    bp.add_argument("--language", default="auto")
    bp.add_argument("--llm-backend", default="claude")
    bp.add_argument("--no-llm", action="store_true")
    bp.add_argument("--output-dir", type=Path, default=Path("."), help="输出目录，默认当前目录")

    # update
    up = sub.add_parser("update", help="增量更新")
    up.add_argument("path", type=Path, default=Path("."), nargs="?")
    up.add_argument("--recluster", action="store_true")
    up.add_argument("--summary", action="store_true")

    # query / path / explain
    qp = sub.add_parser("query", help="BFS 图遍历查询")
    qp.add_argument("question", type=str)

    pp = sub.add_parser("path", help="最短路径查询")
    pp.add_argument("source", type=str)
    pp.add_argument("target", type=str)

    ep = sub.add_parser("explain", help="节点解释")
    ep.add_argument("concept", type=str)

    args = parser.parse_args()

    if args.command == "build":
        _cmd_build(args)
    elif args.command == "update":
        _cmd_update(args)
    elif args.command == "query":
        _run_graphify("query", args.question)
    elif args.command == "path":
        _run_graphify("path", args.source, args.target)
    elif args.command == "explain":
        _run_graphify("explain", args.concept)
    else:
        parser.print_help()


def _cmd_build(args):
    root = args.path.resolve()
    name = args.name or root.name

    output_base = Path(getattr(args, "output_dir", Path("."))).resolve()
    output_base.mkdir(parents=True, exist_ok=True)
    timings = {}
    total_started = time.perf_counter()

    corpus = {}
    filtered_extraction = {"nodes": [], "edges": [], "meta": {}}
    G = None
    domains = []
    api_matches = []
    field_map = {}
    ontology = {}
    impact_analysis = {}
    dream_report = {}
    domain_read_model = {}
    manifest = {}
    current_step = "start"

    try:
        previous_manifest = _read_json(output_base / "manifest.json") if (output_base / "manifest.json").exists() else {}

        current_step = "detect_corpus"
        started = time.perf_counter()
        print(f"[1/13] 文件检测: {root}")
        corpus = detect_corpus(root)
        timings[current_step] = round(time.perf_counter() - started, 4)
        print(f"  语料库: {corpus['total_files']} 文件, ~{corpus['total_words']:,} 词")

        code_files = [Path(f) for f in corpus.get("files", {}).get("code", [])]
        if not code_files:
            raise RuntimeError("未找到代码文件")

        current_step = "extract_ast"
        started = time.perf_counter()
        print(f"[2/13] AST 提取 ({len(code_files)} 文件)...")
        extraction = extract_ast(code_files)
        timings[current_step] = round(time.perf_counter() - started, 4)
        print(f"  {len(extraction['nodes'])} 节点, {len(extraction['edges'])} 边")

        current_step = "filter_extraction"
        started = time.perf_counter()
        print(f"[3/13] Wiki 预过滤...")
        filtered_extraction = filter_extraction_for_wiki(extraction, root)
        timings[current_step] = round(time.perf_counter() - started, 4)
        meta = filtered_extraction.get("meta", {})
        print(
            f"  节点 {meta.get('original_nodes', 0)} → {meta.get('filtered_nodes', 0)}, "
            f"边 {meta.get('original_edges', 0)} → {meta.get('filtered_edges', 0)}"
        )

        current_step = "build_light_graph"
        started = time.perf_counter()
        print(f"[4/13] 轻量图构建...")
        G = build_light_graph(filtered_extraction)
        save_graph_artifacts(None, G, output_base)
        timings[current_step] = round(time.perf_counter() - started, 4)
        print(f"  graph-lite.json: {G.number_of_nodes()} 节点, {G.number_of_edges()} 边")

        current_step = "business_cluster"
        started = time.perf_counter()
        print(f"[5/13] 业务域聚类...")
        try:
            lang = Language(args.language) if args.language != "auto" else Language.AUTO
        except ValueError:
            lang = Language.AUTO
        domains = business_cluster(G, root, language=lang)
        _write_json(output_base / "domains.json", [_serialize_domain(d) for d in domains])
        timings[current_step] = round(time.perf_counter() - started, 4)
        for d in domains:
            bp_count = len(d.business_points)
            if bp_count > 0:
                print(f"  {d.id}: {d.anchors_count()} 锚点, {bp_count} 业务点")

        current_step = "api_map"
        started = time.perf_counter()
        print(f"[6/13] API 映射...")
        frontend_api = _find_dir(root, "src/api")
        frontend_views = _find_dir(root, "src/views")
        backend_src = _find_dir(root, "src/main/java")
        try:
            api_matches = build_api_map(frontend_api, frontend_views, backend_src, domains, root)
            categorized = [m for m in api_matches if getattr(m, "domain", "")]
            print(f"  解析 {len(api_matches)} 个 API, {len(categorized)} 个已归域")
        except Exception as e:
            print(f"  跳过 (API 映射失败: {e})")
        _write_json(output_base / "api-map.json", [_serialize_api_item(m) for m in api_matches])
        timings[current_step] = round(time.perf_counter() - started, 4)

        current_step = "field_map"
        started = time.perf_counter()
        print(f"[7/13] 字段映射...")
        entity_dirs = list(root.rglob("entity")) + list(root.rglob("domain"))
        dto_dirs = list(root.rglob("dto")) + list(root.rglob("vo"))
        entity_dir = entity_dirs[0] if entity_dirs else root
        dto_dir = dto_dirs[0] if dto_dirs else root
        try:
            field_map = build_field_map(api_matches, entity_dir, dto_dir, root)
            print(f"  映射 {_count_fields(field_map)} 个字段")
        except Exception as e:
            print(f"  跳过 (字段映射失败: {e})")
        _write_json(output_base / "field-map.json", field_map)
        timings[current_step] = round(time.perf_counter() - started, 4)

        current_step = "llm_label"
        started = time.perf_counter()
        if not args.no_llm:
            print(f"[8/13] LLM 标注...")
            try:
                backend = (
                    LlmBackend.CLAUDE if args.llm_backend == "claude"
                    else LlmBackend.OPENAI if args.llm_backend == "openai"
                    else LlmBackend.GEMINI
                )
                config = LabelConfig(backend=backend)
                graph_data = {n["id"]: n for n in filtered_extraction.get("nodes", [])}
                domains = label_domains(domains, graph_data, root, config)
                _write_json(output_base / "domains.json", [_serialize_domain(d) for d in domains])
            except Exception as e:
                print(f"  跳过 (LLM 标注失败: {e})")
        else:
            print(f"[8/13] LLM 标注: 跳过 (--no-llm)")
        timings[current_step] = round(time.perf_counter() - started, 4)

        current_step = "ontology"
        started = time.perf_counter()
        print(f"[9/13] Code Ontology v0...")
        ontology = build_code_ontology(domains, api_matches, field_map)
        _write_json(output_base / "ontology.json", ontology)
        timings[current_step] = round(time.perf_counter() - started, 4)
        print(
            f"  ontology.json: {ontology['coverage']['relationships']} 关系, "
            f"{ontology['coverage']['relationship_types_count']} 类关系"
        )

        current_step = "export_wiki"
        started = time.perf_counter()
        print(f"[10/13] Wiki 导出...")
        export_wiki(domains, api_matches, field_map, output_base / "wiki", clean_stale=True, ontology=ontology)
        timings[current_step] = round(time.perf_counter() - started, 4)

        current_step = "impact_analysis"
        started = time.perf_counter()
        print(f"[11/13] Phase 4 影响分析...")
        impact_analysis = build_impact_analysis(
            domains=domains,
            api_matches=api_matches,
            field_map=field_map,
            ontology=ontology,
            wiki_root=output_base / "wiki",
        )
        _write_json(output_base / "impact-analysis.json", impact_analysis)
        write_impact_wiki(impact_analysis, output_base / "wiki")
        timings[current_step] = round(time.perf_counter() - started, 4)
        print(
            f"  impact-analysis.json: {len(impact_analysis.get('query_examples', []))} 类查询样例, "
            f"{impact_analysis.get('acceptance', {}).get('status', 'unknown')}"
        )

        current_step = "visualize"
        started = time.perf_counter()
        print(f"[12/13] 域级可视化...")
        export_domain_html(domains, output_base / "domain_graph.html")
        timings[current_step] = round(time.perf_counter() - started, 4)

        current_step = "manifest"
        started = time.perf_counter()
        try:
            from .reuse import build_manifest
            code_files = [Path(f) for f in corpus.get("files", {}).get("code", [])]
            manifest = build_manifest(code_files, domains)
            _write_json(output_base / "manifest.json", manifest)
            print(f"  manifest.json: {len(manifest['files'])} 文件, {len(manifest['domains'])} 域")
        finally:
            timings[current_step] = round(time.perf_counter() - started, 4)

        current_step = "dream_cycle"
        started = time.perf_counter()
        print(f"[13/13] Phase 5 Dream Cycle...")
        dream_report = build_dream_cycle_report(
            wiki_root=output_base / "wiki",
            domains=domains,
            previous_manifest=previous_manifest,
            current_manifest=manifest,
            impact_analysis=impact_analysis,
        )
        _write_json(output_base / "dream-cycle-report.json", dream_report)
        (output_base / "wiki").mkdir(parents=True, exist_ok=True)
        (output_base / "wiki" / "changelog.md").write_text(render_changelog(dream_report), encoding="utf-8")
        timings[current_step] = round(time.perf_counter() - started, 4)
        print(f"  dream-cycle-report.json: {dream_report['quality']['status']}")

        current_step = "domain_read_model"
        started = time.perf_counter()
        print(f"[v1] Domain Read Model...")
        domain_read_model = build_domain_read_model(
            project_id=output_base.name,
            project_name=name,
            source_root=root,
            domains=domains,
            api_matches=api_matches,
            field_map=field_map,
            ontology=ontology,
        )
        _write_json(output_base / "domain-read-model.json", domain_read_model)
        timings[current_step] = round(time.perf_counter() - started, 4)
        print(f"  domain-read-model.json: {domain_read_model['quality']['deepReadingStatus']}")

        timings["total_seconds"] = round(time.perf_counter() - total_started, 4)
        report = _write_build_report(
            output_base / "build-report.json",
            root,
            corpus,
            filtered_extraction,
            G,
            domains,
            api_matches,
            field_map,
            ontology,
            impact_analysis,
            dream_report,
            domain_read_model=domain_read_model,
            timings=timings,
        )
        _write_json(output_base / "workbench-data.json", ProductDataService(output_base).export_workbench_data())

        print(f"\n完成. {output_base / 'wiki' / 'index.md'} | {output_base / 'domain_graph.html'}")
        print(f"  build.status: {report['build']['status']}")
        print(f"  artifactStatus: {report['build'].get('artifactStatus', report['quality']['status'])}")
        print(f"  productQuality.deepReadingStatus: {report['productQuality']['deepReadingStatus']}")
    except Exception as e:
        timings["total_seconds"] = round(time.perf_counter() - total_started, 4)
        _write_failure_report(
            output_base / "build-report.json",
            root,
            corpus,
            filtered_extraction,
            G,
            domains,
            api_matches,
            field_map,
            ontology,
            impact_analysis,
            dream_report,
            timings,
            current_step,
            e,
        )
        print(f"错误: {current_step} 失败: {e}")
        sys.exit(1)


def _cmd_update(args):
    root = args.path.resolve()
    print(f"增量更新: {root}")
    # 1. detect_incremental
    from graphify.detect import detect_incremental
    result = detect_incremental(root)
    changed = result.get("new_files", {})
    deleted = result.get("deleted_files", [])
    if not changed and not deleted:
        print("无文件变更, 跳过.")
        return
    changed_code = changed.get("code", [])
    print(f"  变更: {len(changed_code)} 文件, 删除: {len(deleted)} 文件")
    if changed_code:
        extraction = extract_ast([Path(f) for f in changed_code])
        from .reuse import merge_graph
        G = merge_graph(extraction, "graph.json", prune_sources=changed_code + deleted)
    else:
        import networkx as nx
        G = nx.node_link_graph(
            __import__("json").loads(Path("graph.json").read_text(encoding="utf-8")),
            edges="links",
        )
    if args.recluster:
        domains = business_cluster(G, root)
    else:
        domains = []
    print("刷新 code-map...")


def _run_graphify(subcmd: str, *args):
    import subprocess
    subprocess.run(["graphify", subcmd] + list(args))


def _find_dir(root: Path, suffix: str) -> Path:
    candidate = root / suffix
    if candidate.exists():
        return candidate
    for p in root.rglob(suffix.split("/")[-1]):
        if p.is_dir():
            return p
    return root


def _count_fields(field_map: dict) -> int:
    count = 0
    for tables in field_map.values():
        for columns in tables.values():
            count += len(columns)
    return count


def _write_build_report(
    path: Path,
    root: Path,
    corpus: dict,
    filtered_extraction: dict,
    graph,
    domains,
    api_matches,
    field_map,
    ontology,
    impact_analysis,
    dream_report,
    domain_read_model: dict | None = None,
    timings: dict | None = None,
) -> dict:
    quality = evaluate_wiki_quality(domains, api_matches, field_map)
    _attach_performance_quality(quality, graph)
    _attach_phase3_quality(quality, ontology, field_map, output_wiki_dir=path.parent / "wiki")
    _attach_phase4_quality(quality, impact_analysis)
    _attach_phase5_quality(quality, dream_report)
    report = {
        "build": {
            "status": "passed",
            "artifactStatus": quality.get("quality", {}).get("status", "unknown"),
        },
        "productQuality": product_quality_for_report(domain_read_model),
        "project": {
            "root": str(root),
            "total_files": corpus.get("total_files", 0),
            "code_files": len(corpus.get("files", {}).get("code", [])),
        },
        "scale": _build_scale(corpus, filtered_extraction, graph),
        "service_impl_capacity": _build_service_impl_capacity(root),
        "pipeline_timings": timings or {},
        "graph": {
            "nodes": graph.number_of_nodes(),
            "edges": graph.number_of_edges(),
            "filter": filtered_extraction.get("meta", {}),
        },
        **quality,
    }
    _write_json(path, report)
    return report


def _write_failure_report(
    path: Path,
    root: Path,
    corpus: dict,
    filtered_extraction: dict,
    graph,
    domains,
    api_matches,
    field_map,
    ontology,
    impact_analysis,
    dream_report,
    timings: dict,
    step: str,
    error: Exception,
) -> dict:
    graph = graph or _EmptyGraph()
    quality = evaluate_wiki_quality(domains, api_matches, field_map)
    _attach_phase3_quality(quality, ontology, field_map, output_wiki_dir=path.parent / "wiki")
    _attach_phase4_quality(quality, impact_analysis)
    _attach_phase5_quality(quality, dream_report)
    quality["quality"]["status"] = "failed"
    quality["quality"]["performance_status"] = "failed"
    quality["quality"].setdefault("errors", []).append(f"{step} 失败")
    report = {
        "build": {
            "status": "failed",
            "failedStep": step,
        },
        "productQuality": product_quality_for_report(None),
        "project": {
            "root": str(root),
            "total_files": corpus.get("total_files", 0),
            "code_files": len(corpus.get("files", {}).get("code", [])),
        },
        "scale": _build_scale(corpus, filtered_extraction, graph),
        "service_impl_capacity": _build_service_impl_capacity(root),
        "pipeline_timings": timings,
        "graph": {
            "nodes": graph.number_of_nodes(),
            "edges": graph.number_of_edges(),
            "filter": filtered_extraction.get("meta", {}),
        },
        **quality,
        "failure": {
            "step": step,
            "type": type(error).__name__,
            "message": str(error),
            "suggestion": "查看该阶段输入规模与源文件结构，必要时收窄分析范围或补充解析规则。",
        },
    }
    _write_json(path, report)
    return report


class _EmptyGraph:
    def number_of_nodes(self) -> int:
        return 0

    def number_of_edges(self) -> int:
        return 0


def _build_scale(corpus: dict, filtered_extraction: dict, graph) -> dict:
    meta = filtered_extraction.get("meta", {})
    return {
        "files": {
            "total": corpus.get("total_files", 0),
            "code": len(corpus.get("files", {}).get("code", [])),
        },
        "extraction": {
            "original_nodes": meta.get("original_nodes", 0),
            "original_edges": meta.get("original_edges", 0),
            "filtered_nodes": meta.get("filtered_nodes", 0),
            "filtered_edges": meta.get("filtered_edges", 0),
        },
        "graph_lite": {
            "nodes": graph.number_of_nodes(),
            "edges": graph.number_of_edges(),
        },
    }


def _build_service_impl_capacity(root: Path, threshold_lines: int = 2000) -> dict:
    service_files = []
    for java_file in root.rglob("*ServiceImpl.java"):
        source = str(java_file).replace("\\", "/")
        if any(part in source for part in ("/target/", "/build/", "/.git/", "/graphify-out/")):
            continue
        try:
            line_count = len(java_file.read_text(encoding="utf-8", errors="ignore").splitlines())
        except OSError:
            continue
        service_files.append({
            "file": str(java_file.relative_to(root)),
            "lines": line_count,
        })
    largest = max(service_files, key=lambda item: item["lines"], default={"file": "", "lines": 0})
    over_threshold = [item for item in service_files if item["lines"] > threshold_lines]
    return {
        "status": "warning" if over_threshold else "passed",
        "threshold_lines": threshold_lines,
        "service_impl_files": len(service_files),
        "max_lines": largest["lines"],
        "largest_file": largest["file"],
        "over_threshold_files": over_threshold,
        "policy": "ServiceImpl 单文件 2000 行以内视为企业项目正常文件，必须正常解析。",
    }


def _attach_performance_quality(quality: dict, graph) -> None:
    warnings = quality["quality"].setdefault("warnings", [])
    nodes = graph.number_of_nodes()
    edges = graph.number_of_edges()
    if nodes > 20_000 or edges > 200_000:
        quality["quality"]["performance_status"] = "warning"
        warnings.append("graph-lite 规模超过 Phase 2 建议阈值")
        if quality["quality"].get("status") == "passed":
            quality["quality"]["status"] = "warning"
    else:
        quality["quality"]["performance_status"] = "passed"


def _attach_phase3_quality(quality: dict, ontology: dict, field_map: dict, output_wiki_dir: Path) -> None:
    phase3 = quality.setdefault("phase3", {})
    relation_types = set((ontology or {}).get("coverage", {}).get("relationship_types", []))
    phase3["ontology_status"] = (
        "ready"
        if (ontology or {}).get("schema", {}).get("version") == "code-ontology-v0"
        and len(relation_types) >= 5
        else "incomplete"
    )
    phase3["field_flow_status"] = "ready" if _count_fields(field_map) > 0 and "maps_to" in relation_types else "incomplete"
    wiki_evidence = collect_wiki_evidence(output_wiki_dir)
    phase3["wiki_evidence"] = wiki_evidence
    phase3["acceptance"] = evaluate_phase3_acceptance(ontology or {}, quality, wiki_evidence)
    quality["quality"]["phase3"] = phase3


def _attach_phase4_quality(quality: dict, impact_analysis: dict) -> None:
    phase4 = quality.setdefault("phase4", {})
    if impact_analysis:
        phase4.update({
            "impact_status": "ready",
            "query_examples": len(impact_analysis.get("query_examples", [])),
            "acceptance": impact_analysis.get("acceptance") or evaluate_phase4_acceptance(impact_analysis),
        })
    else:
        phase4.update({
            "impact_status": "incomplete",
            "query_examples": 0,
            "acceptance": evaluate_phase4_acceptance({}),
        })
    quality["quality"]["phase4"] = phase4


def _attach_phase5_quality(quality: dict, dream_report: dict) -> None:
    phase5 = quality.setdefault("phase5", {})
    if dream_report:
        phase5.update({
            "dream_cycle_status": dream_report.get("quality", {}).get("status", "unknown"),
            "new_files": len(dream_report.get("detect", {}).get("new_files", [])),
            "modified_files": len(dream_report.get("detect", {}).get("modified_files", [])),
            "deleted_files": len(dream_report.get("detect", {}).get("deleted_files", [])),
            "orphan_wiki_domains": len(dream_report.get("reconcile", {}).get("orphan_wiki_domains", [])),
            "archived_stale_domains": len(dream_report.get("reconcile", {}).get("archived_stale_domains", [])),
            "manual_files": len(dream_report.get("preserve", {}).get("manual_files", [])),
        })
    else:
        phase5.update({
            "dream_cycle_status": "not_run",
            "new_files": 0,
            "modified_files": 0,
            "deleted_files": 0,
            "orphan_wiki_domains": 0,
            "archived_stale_domains": 0,
            "manual_files": 0,
        })
    quality["quality"]["phase5"] = phase5


def _write_json(path: Path, payload) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(payload, ensure_ascii=False, indent=2), encoding="utf-8")


def _read_json(path: Path):
    return json.loads(path.read_text(encoding="utf-8"))


def select_phase2_medium_target(ops_root: Path) -> dict:
    candidates = []
    roots = [ops_root] + [child for child in ops_root.iterdir() if child.is_dir()]
    for candidate in roots:
        count = _count_code_files(candidate)
        if count:
            candidates.append((candidate, count))
    if not candidates:
        return {
            "path": str(ops_root),
            "code_files": 0,
            "selection_reason": "no_code_files",
        }

    within = [(path, count) for path, count in candidates if 300 <= count <= 500]
    if within:
        path, count = min(within, key=lambda item: abs(item[1] - 400))
        reason = "within_300_500"
    else:
        path, count = min(candidates, key=lambda item: min(abs(item[1] - 300), abs(item[1] - 500)))
        reason = "nearest_available"
    return {
        "path": str(path),
        "code_files": count,
        "selection_reason": reason,
    }


def _count_code_files(root: Path) -> int:
    suffixes = {".java", ".js", ".ts", ".vue", ".jsx", ".tsx", ".py", ".go"}
    noise = {"node_modules", "dist", "build", "target", ".git", "__pycache__", "coverage"}
    count = 0
    for path in root.rglob("*"):
        if not path.is_file() or path.suffix.lower() not in suffixes:
            continue
        normalized_parts = {part.lower() for part in path.parts}
        if normalized_parts & noise:
            continue
        count += 1
    return count


def _serialize_domain(domain) -> dict:
    return asdict(domain) if is_dataclass(domain) else dict(domain)


def _serialize_api_item(item) -> dict:
    if hasattr(item, "frontend") and hasattr(item, "backend"):
        return asdict(item)
    if is_dataclass(item):
        payload = asdict(item)
    else:
        payload = dict(getattr(item, "__dict__", {}))
    payload["domain"] = getattr(item, "domain", "")
    return payload


if __name__ == "__main__":
    main()
