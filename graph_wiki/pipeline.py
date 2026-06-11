"""CLI 入口：graph-wiki build | update | query | path | explain"""

import argparse
import sys
from pathlib import Path

from .reuse import detect_corpus, extract_ast, build_graph
from .cluster import business_cluster, Language
from .api_mapper import build_api_map
from .field_mapper import build_field_map
from .label import label_domains, LabelConfig, LlmBackend
from .export import export_wiki
from .visualize import export_domain_html


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

    print(f"[1/7] 文件检测: {root}")
    corpus = detect_corpus(root)
    print(f"  语料库: {corpus['total_files']} 文件, ~{corpus['total_words']:,} 词")

    code_files = []
    for f in corpus.get("files", {}).get("code", []):
        code_files.append(Path(f))

    if not code_files:
        print("错误: 未找到代码文件")
        sys.exit(1)

    print(f"[2/7] AST 提取 ({len(code_files)} 文件)...")
    extraction = extract_ast(code_files)
    print(f"  {len(extraction['nodes'])} 节点, {len(extraction['edges'])} 边")

    print(f"[3/7] 图构建...")
    G = build_graph(extraction)

    print(f"[4/7] 业务域聚类...")
    try:
        lang = Language(args.language) if args.language != "auto" else Language.AUTO
    except ValueError:
        lang = Language.AUTO
    domains = business_cluster(G, root, language=lang)
    print(f"  识别出 {len(domains)} 个业务域")

    # 查找前后端目录
    frontend_api = _find_dir(root, "src/api")
    frontend_views = _find_dir(root, "src/views")
    backend_src = _find_dir(root, "src/main/java") or root

    print(f"[5/7] API 映射...")
    api_matches = build_api_map(
        frontend_api, frontend_views, backend_src, domains, root
    )
    print(f"  匹配 {len(api_matches)} 个接口")

    print(f"[6/7] 字段映射...")
    entity_dirs = list(root.rglob("entity")) + list(root.rglob("domain"))
    dto_dirs = list(root.rglob("dto")) + list(root.rglob("vo"))
    entity_dir = entity_dirs[0] if entity_dirs else root
    dto_dir = dto_dirs[0] if dto_dirs else root
    field_map = build_field_map(api_matches, entity_dir, dto_dir, root)
    print(f"  映射 {_count_fields(field_map)} 个字段")

    if not args.no_llm:
        print(f"[7/7] LLM 标注...")
        backend = (
            LlmBackend.CLAUDE if args.llm_backend == "claude"
            else LlmBackend.OPENAI if args.llm_backend == "openai"
            else LlmBackend.GEMINI
        )
        config = LabelConfig(backend=backend)
        domains = label_domains(domains, extraction, root, config)

    print(f"\n导出 Wiki 和可视化...")
    export_wiki(domains, api_matches, field_map)
    export_domain_html(domains)
    print(f"\n完成. wiki/index.md | domain_graph.html")


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


if __name__ == "__main__":
    main()
