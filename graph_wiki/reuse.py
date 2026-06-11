"""复用层：封装 graphifyy 的 detect/extract/build API"""

from pathlib import Path
import networkx as nx


def detect_corpus(root: Path) -> dict:
    """文件检测"""
    from graphify.detect import detect
    return detect(root)


def extract_ast(code_files: list[Path]) -> dict:
    """AST 提取"""
    from graphify.extract import extract, collect_files
    files = []
    for f in code_files:
        files.extend(collect_files(f) if f.is_dir() else [f])
    return extract(files)


def build_graph(extraction: dict) -> nx.Graph:
    """构建 NetworkX 图"""
    from graphify.build import build_from_json
    return build_from_json(extraction)


def merge_graph(
    new_extraction: dict,
    graph_path: str,
    prune_sources: list[str] | None = None,
) -> nx.Graph:
    """增量合并"""
    from graphify.build import build_merge
    return build_merge(
        [new_extraction],
        graph_path=graph_path,
        prune_sources=prune_sources,
    )
