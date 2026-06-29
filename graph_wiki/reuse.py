"""复用层：封装 graphify 的 detect/extract/build API"""

import hashlib
import json
import re
from pathlib import Path
from datetime import datetime, timezone

import networkx as nx


# ── 异常定义 ──

class ReuseError(Exception): pass
class DetectError(ReuseError): pass
class ExtractError(ReuseError): pass


# ── 核心复用函数 ──

def detect_corpus(root: Path) -> dict:
    """文件检测 → {files: {code: [...], doc: [...]}, total_files, total_words}"""
    try:
        from graphify.detect import detect
        return detect(root)
    except ImportError as e:
        raise DetectError(f"graphify 未安装或不可用: {e}") from e
    except Exception as e:
        raise DetectError(f"文件检测失败 ({root}): {e}") from e


def extract_ast(code_files: list[Path]) -> dict:
    """AST 提取 → {nodes: [...], edges: [...], input_tokens, output_tokens}"""
    try:
        from graphify.extract import extract, collect_files
        files = []
        for f in code_files:
            files.extend(collect_files(f) if f.is_dir() else [f])
        return extract(files)
    except ImportError as e:
        raise ExtractError(f"graphify 未安装或不可用: {e}") from e
    except Exception as e:
        raise ExtractError(f"AST 提取失败: {e}") from e


def build_graph(extraction: dict) -> nx.Graph:
    """构建 NetworkX 图（无向图）"""
    from graphify.build import build_from_json
    return build_from_json(extraction)


NOISE_SOURCE_PARTS = {
    "node_modules", "dist", "build", "target", ".git", ".venv",
    "__pycache__", "coverage",
}

NOISE_METHOD_NAMES = {
    "tostring", "equals", "hashcode", "clone",
    "mounted", "created", "updated", "destroyed", "beforedestroy",
    "then", "catch", "finally", "map", "filter", "foreach", "reduce",
}


def filter_extraction_for_wiki(extraction: dict, root: Path) -> dict:
    """在构图前过滤出 Wiki 需要的业务级节点和边。"""
    nodes = extraction.get("nodes", [])
    edges = extraction.get("edges", extraction.get("links", []))

    kept_nodes = []
    kept_ids = set()
    for node in nodes:
        if _is_wiki_relevant_node(node):
            kept_nodes.append(node)
            kept_ids.add(node.get("id"))

    allowed_relations = {"contains", "imports", "imports_from", "calls", "implements", "inherits"}
    kept_edges = [
        edge for edge in edges
        if edge.get("source") in kept_ids
        and edge.get("target") in kept_ids
        and edge.get("relation", "") in allowed_relations
    ]

    return {
        "nodes": kept_nodes,
        "edges": kept_edges,
        "input_tokens": extraction.get("input_tokens", 0),
        "output_tokens": extraction.get("output_tokens", 0),
        "meta": {
            "root": str(root),
            "original_nodes": len(nodes),
            "filtered_nodes": len(kept_nodes),
            "original_edges": len(edges),
            "filtered_edges": len(kept_edges),
        },
    }


def _is_wiki_relevant_node(node: dict) -> bool:
    label = str(node.get("label", ""))
    source_file = str(node.get("source_file", "")).replace("\\", "/")
    source_lower = source_file.lower()
    label_lower = label.lower().strip()

    if not node.get("id"):
        return False
    if any(f"/{part}/" in f"/{source_lower}/" for part in NOISE_SOURCE_PARTS):
        return False
    if label.startswith(".") or label.startswith("_"):
        return False

    method_match = re.match(r"^(?P<name>[A-Za-z_$][\w$]*)\(\)$", label)
    if method_match:
        name = method_match.group("name")
        name_lower = name.lower()
        if name_lower in NOISE_METHOD_NAMES:
            return False
        if re.match(r"^(get|set|is)[A-Z]", name):
            return False
        return True

    if re.search(r"\.(java|js|ts|vue|tsx|jsx|py|go)$", label_lower):
        return True

    business_suffixes = (
        "controller", "service", "serviceimpl", "mapper", "dao",
        "adapter", "entity", "dto", "vo", "enum", "handler",
    )
    compact_label = re.sub(r"[^a-z0-9]", "", label_lower)
    return any(suffix in compact_label for suffix in business_suffixes)


def build_light_graph(filtered_extraction: dict) -> nx.Graph:
    """从预过滤 extraction 构建 Graph-Wiki 默认轻量图。"""
    graph = nx.Graph()
    for node in filtered_extraction.get("nodes", []):
        node_id = node.get("id")
        if not node_id:
            continue
        graph.add_node(node_id, **node)
    for edge in filtered_extraction.get("edges", filtered_extraction.get("links", [])):
        source = edge.get("source")
        target = edge.get("target")
        if source in graph and target in graph:
            graph.add_edge(source, target, **edge)
    graph.graph["meta"] = filtered_extraction.get("meta", {})
    return graph


def save_graph_artifacts(
    full_graph: nx.Graph | None,
    light_graph: nx.Graph,
    output_dir: Path,
) -> None:
    """持久化 graph-lite.json，并按需持久化完整 graph.json。"""
    output_dir.mkdir(parents=True, exist_ok=True)
    save_graph(light_graph, output_dir / "graph-lite.json")
    if full_graph is not None:
        save_graph(full_graph, output_dir / "graph.json")


def merge_graph(
    new_extraction: dict,
    graph_path: str,
    prune_sources: list[str] | None = None,
) -> nx.Graph:
    """增量合并已变更文件的 AST 到现有图"""
    from graphify.build import build_merge
    return build_merge(
        [new_extraction],
        graph_path=graph_path,
        prune_sources=prune_sources,
    )


# ── 图持久化 ──

def save_graph(G: nx.Graph, path: Path):
    """保存 NetworkX 图为 node-link JSON。

    NetworkX 3.x renamed the node-link edge-list keyword from ``link`` to
    ``edges``. Support both so release gates pass on distro NetworkX 2.x and
    newer developer environments without changing the persisted ``links``
    schema.
    """
    try:
        data = nx.node_link_data(G, edges="links")
    except TypeError:
        data = nx.node_link_data(G, link="links")
    path.write_text(json.dumps(data, ensure_ascii=False, indent=2), encoding="utf-8")


def load_graph(path: Path) -> nx.Graph:
    """从 node-link JSON 加载 NetworkX 图"""
    data = json.loads(path.read_text(encoding="utf-8"))
    try:
        return nx.node_link_graph(data, edges="links")
    except TypeError:
        return nx.node_link_graph(data, link="links")


# ── Manifest 管理 ──

def build_manifest(files: list[Path], domains: list) -> dict:
    """生成文件哈希清单，供增量更新检测变更
    返回格式见架构文档 §6.4
    """
    manifest_files = {}
    for f in files:
        try:
            content = f.read_bytes()
            file_hash = hashlib.sha256(content).hexdigest()[:16]
        except (IOError, OSError):
            file_hash = "UNREADABLE"
        manifest_files[str(f)] = {
            "hash": file_hash,
            "last_modified": datetime.fromtimestamp(
                f.stat().st_mtime, tz=timezone.utc
            ).isoformat() if f.exists() else "",
            "domain": "",
        }

    manifest_domains = {}
    for d in domains:
        anchor_ids = sorted(a.get("id", "") for a in d.anchors_flat())
        anchors_hash = hashlib.sha256(
            ",".join(anchor_ids).encode()
        ).hexdigest()[:16]
        manifest_domains[d.id] = {
            "version": 1,
            "anchors_hash": anchors_hash,
            "anchor_count": d.anchors_count(),
            "last_build": datetime.now(timezone.utc).isoformat(),
        }

    return {
        "files": manifest_files,
        "domains": manifest_domains,
        "meta": {
            "last_full_build": datetime.now(timezone.utc).isoformat(),
            "total_files": len(files),
        },
    }


def detect_incremental(root: Path, manifest_path: Path) -> dict:
    """检测增量变更：对比当前文件系统与 manifest，找出新增/修改/删除的文件"""
    if not manifest_path.exists():
        return {"new_files": [], "deleted_files": [], "unchanged_files": [], "new_total": 0}

    manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
    old_files = set(manifest.get("files", {}).keys())

    try:
        from graphify.detect import detect
        corpus = detect(root)
        current_files = set(corpus.get("files", {}).get("code", []))
    except Exception:
        return {"new_files": [], "deleted_files": [], "unchanged_files": [], "new_total": 0}

    new_files = sorted(current_files - old_files)
    deleted_files = sorted(old_files - current_files)
    unchanged_files = sorted(current_files & old_files)

    return {
        "new_files": new_files,
        "deleted_files": deleted_files,
        "unchanged_files": unchanged_files,
        "new_total": len(current_files),
        "total_files": len(old_files),
    }
