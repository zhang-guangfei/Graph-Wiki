"""端到端集成测试：加载 tests/fixtures/test_graph.json，跑完整 build 链路并产出 Wiki"""
import json
import networkx as nx
from pathlib import Path
import sys

# 确保能导入 graph_wiki
sys.path.insert(0, str(Path(__file__).parent.parent))

from graph_wiki.cluster import business_cluster
from graph_wiki.models import Language, ApiMatch
from graph_wiki.export import export_wiki
from graph_wiki.visualize import export_domain_html


def run_full_pipeline(graph_path: Path, output_root: Path):
    """完整 pipeline：cluster → export → visualize"""

    # ── 加载 graph ──
    print(f"[1/5] 加载图数据: {graph_path}")
    data = json.loads(graph_path.read_text(encoding="utf-8"))
    G = nx.Graph()
    for node in data["nodes"]:
        G.add_node(node["id"], **node)
    edges_key = "edges" if "edges" in data else "links"
    for edge in data[edges_key]:
        G.add_edge(edge["source"], edge["target"], **edge)
    print(f"  节点: {G.number_of_nodes()}, 边: {G.number_of_edges()}")

    # ── 聚类 ──
    print(f"[2/5] 业务域聚类...")
    lang = Language.JAVASCRIPT  # tests/fixtures/test_graph.json 是 Vue/JS 前端项目
    domains = business_cluster(G, Path("."), language=lang)
    print(f"  识别出 {len(domains)} 个业务域")
    total_bps = sum(len(d.business_points) for d in domains)
    print(f"  共 {total_bps} 个业务点")
    for d in domains:
        dep_count = len(d.dependencies)
        print(f"    {d.id}: {d.anchors_count()} 锚点, {len(d.business_points)} 业务点, {dep_count} 依赖域")

    # ── API/字段映射（test_graph.json 无源代码目录，跳过文件解析） ──
    print(f"[3/5] API 映射: 跳过（测试数据无源代码目录）")
    api_matches: list[ApiMatch] = []

    print(f"[4/5] 字段映射: 跳过（测试数据无源代码目录）")
    field_map: dict = {}

    # ── 导出 Wiki ──
    print(f"[5/5] 导出 Wiki 和可视化...")
    export_wiki(domains, api_matches, field_map, output_root / "wiki")
    export_domain_html(domains, output_root / "domain_graph.html")

    # ── 统计输出 ──
    wiki_files = list((output_root / "wiki").rglob("*.md"))
    print(f"\n=== 输出统计 ===")
    print(f"Wiki 文件: {len(wiki_files)} 个")
    for f in sorted(wiki_files):
        rel = f.relative_to(output_root)
        size = f.stat().st_size
        print(f"  {rel} ({size} bytes)")
    print(f"可视化: {output_root / 'domain_graph.html'}")
    print(f"\n完成。入口: {output_root / 'wiki' / 'index.md'}")


if __name__ == "__main__":
    root = Path(__file__).parent.parent
    graph_path = root / "tests" / "fixtures" / "test_graph.json"
    output_root = root / "output"

    if not graph_path.exists():
        print(f"错误: 找不到 {graph_path}")
        sys.exit(1)

    output_root.mkdir(parents=True, exist_ok=True)
    run_full_pipeline(graph_path, output_root)
