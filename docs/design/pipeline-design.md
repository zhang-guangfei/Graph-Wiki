# Pipeline 模块详细设计

> 对应总体设计：第四章 4.8 节  
> 状态：详细设计阶段  
> 依赖：所有模块

---

## 1. CLI 入口

```python
# graph_wiki/pipeline.py

import argparse
import sys
from pathlib import Path
from .reuse import detect_corpus, extract_ast, build_graph
from .cluster import business_cluster, Language
from .api_mapper import build_api_map
from .field_mapper import build_field_map
from .label import label_domains, LabelConfig
from .export import export_wiki, export_domain_html

def main():
    parser = argparse.ArgumentParser(prog="graph-wiki")
    sub = parser.add_subparsers(dest="command", required=True)

    # graph-wiki build
    build_parser = sub.add_parser("build")
    build_parser.add_argument("path", type=Path, default=Path("."), nargs="?")
    build_parser.add_argument("--name", help="项目名称")
    build_parser.add_argument("--language", default="auto")
    build_parser.add_argument("--llm-backend", default="claude")
    build_parser.add_argument("--no-llm", action="store_true")

    # graph-wiki update
    update_parser = sub.add_parser("update")
    update_parser.add_argument("path", type=Path, default=Path("."), nargs="?")
    update_parser.add_argument("--recluster", action="store_true")
    update_parser.add_argument("--summary", action="store_true")

    # graph-wiki query / path / explain
    query_parser = sub.add_parser("query")
    query_parser.add_argument("question", type=str)

    path_parser = sub.add_parser("path")
    path_parser.add_argument("source", type=str)
    path_parser.add_argument("target", type=str)

    explain_parser = sub.add_parser("explain")
    explain_parser.add_argument("concept", type=str)

    args = parser.parse_args()

    if args.command == "build":
        cmd_build(args)
    elif args.command == "update":
        cmd_update(args)
    elif args.command == "query":
        cmd_query(args)
    elif args.command == "path":
        cmd_path(args)
    elif args.command == "explain":
        cmd_explain(args)


def cmd_build(args):
    """完整构建流程"""
    root = args.path.resolve()
    print(f"[1/7] 文件检测: {root}")
    corpus = detect_corpus(root)
    print(f"  语料库: {corpus['total_files']} 文件, ~{corpus['total_words']:,} 词")

    print(f"[2/7] AST 提取 ...")
    code_files = collect_files(corpus)
    extraction = extract_ast(code_files)
    print(f"  AST: {len(extraction['nodes'])} 节点, {len(extraction['edges'])} 边")

    print(f"[3/7] 图构建 ...")
    G = build_graph(extraction)

    print(f"[4/7] 业务域聚类 ...")
    language = Language(args.language) if args.language != "auto" else Language.AUTO
    domains = business_cluster(G, root, language=language)
    print(f"  识别出 {len(domains)} 个业务域")

    print(f"[5/7] API 映射 ...")
    api_matches = build_api_map(
        root / "ops-frontend/src/api",
        root / "ops-frontend/src/views",
        root / "ops-backend",
        domains, root
    )
    print(f"  匹配 {len(api_matches)} 个 API")

    print(f"[6/7] 字段映射 ...")
    field_map = build_field_map(api_matches, root / "ops-backend", root)
    print(f"  映射 {count_fields(field_map)} 个字段")

    if not args.no_llm:
        print(f"[7/7] LLM 标注 ...")
        config = LabelConfig(backend=args.llm_backend)
        domains = label_domains(domains, extraction, root, config)

    print(f"\n导出 Wiki 和可视化 ...")
    export_wiki(domains, api_matches, field_map, root)
    export_domain_html(domains, root)

    print(f"\n✅ 完成。Wiki: wiki/index.md, 可视化: domain_graph.html")


def cmd_update(args):
    """增量更新流程"""
    # 1. 检测变更文件
    # 2. 只对变更文件重跑 AST
    # 3. build_merge 合并到现有图
    # 4. 如果 --recluster: 重新跑 business_cluster
    # 5. 刷新 code-map.md
    # 6. 如果 api 有变更: 重新跑 api_mapper
    # 7. 如果 entity 有变更: 重新跑 field_mapper
    # 8. 可选: --summary 生成变更摘要
    pass


def cmd_query(args):
    """BFS 图遍历查询（复用 graphify query）"""
    import subprocess
    subprocess.run(["graphify", "query", args.question])


def cmd_path(args):
    """最短路径查询"""
    import subprocess
    subprocess.run(["graphify", "path", args.source, args.target])


def cmd_explain(args):
    """节点解释"""
    import subprocess
    subprocess.run(["graphify", "explain", args.concept])
```

---

## 2. 增量更新逻辑

```python
def cmd_update(args):
    root = args.path.resolve()
    
    # Step 1: 检测变更
    from graphify.detect import detect_incremental
    result = detect_incremental(root)
    changed = result.get("new_files", {})
    deleted = result.get("deleted_files", [])
    
    if not changed and not deleted:
        print("无文件变更，跳过更新。")
        return
    
    # Step 2: 只重提取变更文件
    if changed:
        changed_code = changed.get("code", [])
        if changed_code:
            extraction = extract_ast(changed_code)
            G = build_merge(extraction, graph_path="graph.json")
    
    # Step 3: 是否需要重聚类
    needs_recluster = args.recluster or detect_domain_boundary_change(
        changed, deleted
    )
    
    if needs_recluster:
        domains = business_cluster(G, root)
    else:
        domains = load_domains("domains.json")
    
    # Step 4: 刷新 code-map（只刷新变更域）
    refresh_code_map(domains, changed, root)
    
    # Step 5: 选择性重建 API/字段映射
    if any("api/" in f for f in changed.get("code", [])):
        api_matches = build_api_map(...)
    if any("entity/" in f or "domain/" in f for f in changed.get("code", [])):
        field_map = build_field_map(...)
    
    # Step 6: 变更日志
    log_change(changed, deleted)
    
    # Step 7: 可选：LLM 变更摘要
    if args.summary:
        summary = generate_change_summary(changed, deleted)
        append_changelog(summary)


def detect_domain_boundary_change(changed: dict, deleted: list) -> bool:
    """
    检测是否需要重新聚类。
    
    触发条件：
    1. 新增/删除 Maven 模块
    2. 新增/删除前端 views/ 目录
    3. Java 包路径重命名
    
    不触发：
    - 类文件内容修改（不影响包结构）
    - DTO/Entity 字段增减
    - 方法内部逻辑修改
    """
    # 检查是否有模块级别的目录变更
    module_indicators = [
        "/pom.xml",                # 新增 Maven 模块
        "/src/main/java/",         # 新增源码目录
        "/src/views/",             # 新增前端视图
    ]
    for f in list(changed.get("code", [])) + deleted:
        if any(indicator in f for indicator in module_indicators):
            return True
    return False
```

---

## 3. 构建顺序与依赖

```
graph-wiki build .
    │
    ├── [1/7] detect_corpus()           ← reuse.py (graphifyy)
    ├── [2/7] extract_ast()             ← reuse.py (graphifyy)
    ├── [3/7] build_graph()             ← reuse.py (graphifyy)
    ├── [4/7] business_cluster()        ← cluster.py
    ├── [5/7] build_api_map()           ← api_mapper.py
    ├── [6/7] build_field_map()         ← field_mapper.py
    └── [7/7] label_domains()           ← label.py (optional: --no-llm)
         │
         ├── export_wiki()              ← export.py
         └── export_domain_html()       ← visualize.py

graph-wiki update .
    ├── detect_incremental()            ← reuse.py
    ├── extract_ast(changed_only)       ← reuse.py (只变更文件)
    ├── build_merge()                   ← reuse.py
    ├── business_cluster()              ← cluster.py (仅 --recluster)
    ├── build_api_map()                 ← api_mapper.py (仅 api 变更)
    ├── build_field_map()               ← field_mapper.py (仅 entity 变更)
    └── refresh_code_map()              ← export.py (始终)

graph-wiki query "xxx" / path "A" "B" / explain "concept"
    └── 透传至 graphify query/path/explain CLI
```

---

## 4. 错误处理

```python
class PipelineError(Exception): pass

class DetectError(PipelineError):
    """文件检测失败（路径不存在、无权限）"""

class ExtractError(PipelineError):
    """AST 提取失败（tree-sitter 解析错误）"""

class ClusterError(PipelineError):
    """聚类失败（锚点不足、无法形成域）"""

class ExportError(PipelineError):
    """导出失败（磁盘满、权限不足）"""

def safe_run(step_name: str, func, *args):
    """带错误捕获的步骤执行"""
    try:
        return func(*args)
    except Exception as e:
        print(f"[{step_name}] 失败: {e}")
        raise PipelineError(f"步骤 '{step_name}' 失败") from e
```

---

## 5. 配置文件（`graph-wiki.yaml`）

```yaml
# 项目根目录下的 graph-wiki.yaml（可选，覆盖默认配置）

project:
  name: "OPS"                    # 项目名称
  language: "auto"               # java | javascript | python | go | auto
  backend_dir: "ops-backend"     # 后端代码目录
  frontend_dir: "ops-frontend"   # 前端代码目录

cluster:
  merge_threshold: 0.3           # 候选域合并阈值
  min_domain_size: 5             # 最小域大小（锚点数）
  exclude_packages:              # 排除的包前缀
    - "org.springframework"
    - "com.smc.smccloud.config"
  manual_domain_map:             # 手动域映射（覆盖自动聚类）
    "com.smc.smccloud.bin": "bin-data"
    "com.smc.smccloud.order": "order"

label:
  llm_backend: "claude"           # claude | openai | gemini
  model: "claude-haiku-4-5-20251001"
  parallel_calls: 5
  sampling_lines: 50

export:
  wiki_dir: "wiki"
  obsidian_compatible: true
  include_api_docs: true
  include_er_diagrams: true
```

---

## 6. 测试用例

```python
def test_build_dry_run(tmp_path):
    """在空项目上运行构建，验证优雅失败"""
    with pytest.raises(DetectError):
        cmd_build(argparse.Namespace(path=tmp_path, ...))

def test_update_no_changes():
    """无变更时优雅跳过"""
    # mock detect_incremental 返回空
    pass

def test_update_with_recluster():
    """--recluster 触发重聚类"""
    pass
```
