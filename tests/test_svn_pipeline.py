"""Graph-Wiki 集成测试：SVN Platform 项目端到端验证"""
import sys
import json
import time
import shutil
from pathlib import Path
from types import SimpleNamespace

sys.path.insert(0, str(Path(__file__).parent.parent))

from graph_wiki.reuse import detect_corpus, extract_ast, build_graph, save_graph
from graph_wiki.cluster import business_cluster
from graph_wiki.api_mapper import parse_frontend_apis, trace_frontend_callers
from graph_wiki.models import Language
from graph_wiki.export import export_wiki
from graph_wiki.visualize import export_domain_html
from graph_wiki import pipeline


def test_svn_full_pipeline():
    """SVN Platform 前端项目（29 文件）端到端管道"""
    root = Path(__file__).parent.parent / "tests" / "svn-platform"
    assert root.exists(), f"测试项目不存在: {root}"

    output_dir = root / "output"
    if output_dir.exists():
        shutil.rmtree(output_dir)
    output_dir.mkdir(parents=True)

    t0 = time.time()

    # Step 1-3: 复用层
    corpus = detect_corpus(root)
    assert corpus["total_files"] > 0, "文件检测失败"
    code_files = [Path(f) for f in corpus.get("files", {}).get("code", [])]
    assert len(code_files) > 0, "未找到代码文件"

    extraction = extract_ast(code_files)
    assert len(extraction["nodes"]) > 0, "AST 提取失败: 节点数为 0"

    G = build_graph(extraction)
    assert G.number_of_nodes() > 0, "图构建失败"

    # 保存 graph.json
    save_graph(G, output_dir / "graph.json")

    # Step 4: 聚类
    domains = business_cluster(G, root, language=Language.JAVASCRIPT, min_domain_size=2)
    assert len(domains) > 0, "聚类失败: 未识别出业务域"
    assert sum(len(d.business_points) for d in domains) > 0, "业务点提取失败"

    # Step 5: API 映射
    api_dir = root / "src" / "api"
    views_dir = root / "src" / "views"
    if api_dir.exists() and views_dir.exists():
        apis = parse_frontend_apis(api_dir)
        assert len(apis) > 0, "前端 API 解析失败"
        apis = trace_frontend_callers(apis, views_dir)
        called = [a for a in apis if a.callers]
        assert len(called) > 0, "前端调用者追踪失败"

    # Step 6-7: 字段映射 + LLM 标注跳过（前端项目）

    # Step 8: Wiki 导出
    wiki_dir = output_dir / "wiki"
    export_wiki(domains, [], {}, wiki_dir)
    assert wiki_dir.exists(), "Wiki 导出失败"
    assert (wiki_dir / "index.md").exists(), "index.md 未生成"

    # Step 9: 可视化
    html_path = output_dir / "domain_graph.html"
    export_domain_html(domains, html_path)
    assert html_path.exists(), "可视化导出失败"

    elapsed = time.time() - t0
    wiki_files = list(wiki_dir.rglob("*.md"))

    # 输出验证
    print(f"  Time: {elapsed:.0f}s")
    print(f"  Nodes: {G.number_of_nodes()}, Edges: {G.number_of_edges()}")
    print(f"  Domains: {len(domains)}")
    for d in domains:
        print(f"    {d.id}: {d.anchors_count()} anchors, {len(d.business_points)} bps")
    print(f"  Wiki files: {len(wiki_files)}")
    for f in sorted(wiki_files):
        print(f"    {f.relative_to(output_dir)}")
    print(f"  PASSED")



if __name__ == "__main__":
    test_svn_full_pipeline()
    print("\nAll integration tests passed.")


def test_svn_platform_pipeline_build_emits_domain_read_model_and_workbench_smoke(tmp_path: Path):
    """Current release-gate smoke: full graph-wiki build emits v1 product artifacts."""
    root = Path(__file__).parent.parent / "tests" / "svn-platform"
    output_dir = tmp_path / "svn-output"

    pipeline._cmd_build(SimpleNamespace(
        path=root,
        name="SVN Platform Fixture",
        language="javascript",
        llm_backend="claude",
        no_llm=True,
        output_dir=output_dir,
    ))

    domain_read_model = pipeline._read_json(output_dir / "domain-read-model.json")
    workbench = pipeline._read_json(output_dir / "workbench-data.json")
    report = pipeline._read_json(output_dir / "build-report.json")

    assert report["build"]["status"] == "passed"
    assert report["productQuality"]["deepReadingStatus"] != "failed"
    assert domain_read_model["schema"]["version"] == "domain-read-model-v1"
    assert workbench["schema"] == {
        "version": "graph-wiki-workbench-v1",
        "source": "domain-read-model.json",
    }
    assert (output_dir / "wiki" / "index.md").exists()
    assert (output_dir / "domain_graph.html").exists()

    core_domains = [domain for domain in domain_read_model["domains"] if domain["core"]]
    assert core_domains
    evidence_index = domain_read_model["evidenceIndex"]
    for domain in core_domains:
        for flow in domain["flows"]:
            assert flow["evidenceRefs"]
            assert all(ref in evidence_index for ref in flow["evidenceRefs"])
        for rule in domain["rules"]:
            assert rule["evidenceRefs"]
            assert all(ref in evidence_index for ref in rule["evidenceRefs"])
