# Pipeline 模块详细设计

> 对应总体设计：[第七章 7.10 节](../architecture/graph-wiki%20工程架构设计.md#48-流水线模块-pipeline)
> 状态：详细设计阶段（v2.0，重写）
> 依赖：`reuse.py`、`cluster.py`、`api_mapper.py`、`field_mapper.py`、`label.py`、`export.py`、`visualize.py`（全部 7 个上游模块）
> 参考：`cluster-design.md`、`api-mapper-design.md`、`field-mapper-design.md`、`label-design.md`、`graph-wiki 工程架构设计.md` 第五章/第三章/第四章/第七章

---

## 0. 代码实现现状

`graph_wiki/pipeline.py` 已实现完整 CLI 骨架和 build/update 主流程：

| 功能 | 实现状态 | 说明 |
|------|:--------:|------|
| `main() CLI 入口` | ✅ 已实现 | argparse 注册 build / update / query / path / explain |
| `cmd_build 十步流水线` | ⚠️ 需调整 | detect → extract → filter_extraction → build_light_graph → cluster → api_map → field_map → label → export → visualize，并写出 build-report |
| `cmd_update 增量更新骨架` | ⚠️ 已实现（简化） | 调用 `graphify.detect_incremental`，但未持久化 manifest.json |
| `_find_dir 模糊匹配` | ✅ 已实现 | 精确匹配 → rglob 递归搜索 |
| `_run_graphify 委托策略` | ✅ 已实现 | subprocess 调用 graphify CLI |
| 错误处理与优雅降级 | ⚠️ 部分实现 | cluster 失败时 `try/except`，但缺少统一错误处理框架 |
| `graph-wiki.yaml` 配置加载 | ❌ 未实现 | 当前全部使用默认值和 CLI 参数 |
| cmd_update 变更日志 | ❌ 未实现 | update 后不生成变更报告 |
| cmd_update merge_graph | ⚠️ 部分实现 | 直接 import graphify 的 build_merge，但未处理边缘情况 |
| `--update --recluster` 增量重聚 | ⚠️ 已实现（简化） | 调用 business_cluster，但无域名保留逻辑 |
| `graph-wiki.yaml` 的自定义 merge_rules | ❌ 未实现 | cluster 的 DOMAIN_MERGE_RULES 当前硬编码 |
| `label_domains` 的 API Key 缺失优雅降级 | ❌ 未实现 | 当前直接抛出异常，pipeline 中未捕获 |

---

## 1. 模块职责

### 1.1 核心职责

Pipeline 模块是 Graph-Wiki 的**中枢神经系统**，承担三大职责：

#### 职责 A：CLI 编排

将 `graph-wiki build` / `update` 等 CLI 命令转化为对各上游模块的有序调用，串联完整处理流程。

```
graph-wiki build <path>
         │
         ▼
  ┌─ pipeline.py ────────────────────────────────────────────┐
  │                                                           │
  │  [1/10] detect_corpus()   ──→ corpus (文件清单)              │
  │  [2/10] extract_ast()     ──→ extraction (AST 数据)         │
  │  [3/10] filter_extraction_for_wiki() → filtered AST       │
  │  [4/10] build_light_graph() ──→ G (graph-lite)             │
  │  [5/10] business_cluster()──→ domains (list[Domain])       │
  │  [6/10] build_api_map()   ──→ api_matches (list[ApiMatch]) │
  │  [7/10] build_field_map() ──→ field_map (dict)             │
  │  [8/10] label_domains()   ──→ domains (enriched)           │
  │  [9/10] export_wiki()     ──→ wiki/ 目录                    │
  │  [10/10] export_domain_html()──→ domain_graph.html         │
  │  [report] build-report.json                                │
  │                                                           │
  └───────────────────────────────────────────────────────────┘
```

#### 职责 B：优雅降级

每个步骤都可能失败。Pipeline 必须为每个步骤定义明确的降级行为：何时应终止整个构建，何时只需跳过该步骤并发出警告。

```
降级金字塔:

        ┌─── [4] cluster ──→ 终止构建（无域数据，后续无法执行）
        │
    严  ├─── [7] label ────→ 跳过该步（域保持未标注，Wiki 用域 ID 代替名称）
    重  │
    度  ├─── [1][2][3] reuse ─→ 终止构建（无图数据，无可执行）
    降  │
    级  ├─── [5] api_mapper ─→ 跳过该步（api-docs.md 留空，不终止）
        │
        ├─── [6] field_mapper ─→ 跳过该步（data-flow.md 留空，不终止）
    轻  │
        └─── [8][9] export/visualize ─→ 部分导出（失败文件跳过，已成功的保留）
```

#### 职责 C：进度输出

为用户提供显式的进度反馈，包括当前步骤编号、模块名称、处理量（文件数/节点数/域数）、耗时。

```
示例输出：

$ graph-wiki build .

[1/10] 文件检测...
  语料库: 142 代码文件, 25,180 词, 3 种类型
  ✓ 完成 (0.3s)

[2/10] AST 提取 (142 文件)...
  4,218 节点, 18,362 边
  ✓ 完成 (4.2s)

[3/10] 图构建...
  4,218 节点, 18,362 边, 34 种关系类型
  ✓ 完成 (1.1s)

[5/10] 业务域聚类...
  过滤噪音: 4,218 → 612 业务节点
  提取锚点: 612 → 218 个锚点
  识别出 8 个业务域
  ✓ 完成 (0.8s)

[6/10] API 映射...
  匹配 23 个接口 (前端 API 文件: 12, 后端 Controller: 18)
  ✓ 完成 (0.3s)

[7/10] 字段映射...
  映射 67 个字段 (Entity: 15 类, DTO: 22 类)
  ✓ 完成 (0.5s)

[8/10] LLM 标注 (8 域, 5 路并行)...
  域 1/8: bin-data ✓
  域 2/8: purchase ✓
  ...
  全部 8 个域标注完成
  ✓ 完成 (18.3s)  |  成本估算: $0.03 (Claude Haiku)

[9/10] 导出 Wiki...
  wiki/index.md, wiki/api-index.md, wiki/bin-data/* (6 文件)
  ✓ 完成 (0.2s)

[10/10] 域级可视化...
  domain_graph.html (8 节点, 12 边)
  ✓ 完成 (0.1s)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 完成!  wiki/index.md  |  domain_graph.html
 总耗时: 25.7s
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```

### 1.2 与其他模块的关系

```
                    ┌──────────────┐
                    │  pipeline    │  ← CLI 入口 & 编排核心
                    └──────┬───────┘
                           │
      ┌────────┬───────────┼───────────┬────────┐
      │        │           │           │        │
      ▼        ▼           ▼           ▼        ▼
  ┌──────┐ ┌──────┐ ┌──────────┐ ┌─────────┐ ┌─────────┐
  │reuse │ │cluster│ │api_mapper│ │field_map│ │  label  │
  │(复用)│ │(聚类) │ │(API映射)  │ │(字段映射) │ │(LLM标注)│
  └──────┘ └──────┘ └──────────┘ └─────────┘ └────┬────┘
                                                    │
                                              ┌─────┴─────┐
                                              │  export    │
                                              │  visualize │
                                              └───────────┘
```

| 关系 | 模块 | 传递数据 | 调用者 |
|------|------|---------|--------|
| 下游 | `reuse.detect_corpus` | `corpus: dict` | `cmd_build` |
| 下游 | `reuse.extract_ast` | `extraction: dict` | `cmd_build` |
| 下游 | `reuse.build_graph` | `G: nx.Graph` | `cmd_build` |
| 下游 | `reuse.merge_graph` | `G: nx.Graph` | `cmd_update` |
| 下游 | `cluster.business_cluster` | `domains: list[Domain]` | `cmd_build`, `cmd_update` |
| 下游 | `api_mapper.build_api_map` | `api_matches: list[ApiMatch]` | `cmd_build` |
| 下游 | `field_mapper.build_field_map` | `field_map: dict` | `cmd_build` |
| 下游 | `label.label_domains` | `domains: list[Domain] (enriched)` | `cmd_build` |
| 下游 | `export.export_wiki` | `None`（写入文件） | `cmd_build` |
| 下游 | `visualize.export_domain_html` | `None`（写入文件） | `cmd_build` |

### 1.3 涉及的数据类

| 类型 | 所在模块 | 本模块使用方式 | 说明 |
|------|---------|---------------|------|
| `Language` | `models.py` | `cluster.business_cluster` 入参 | 语言枚举，从 `--language` CLI 参数传入 |
| `LlmBackend` | `label.py` | `label.label_domains` 入参 | LLM 后端选择，从 `--llm-backend` CLI 参数传入 |
| `LabelConfig` | `label.py` | `label.label_domains` 入参 | 标注配置集，含 model/parallel_calls/retry_count 等 |
| `Domain` | `models.py` | 承载核心数据通过流水线 | cluster 产出，label 补充，export/visualize 消费 |
| `ApiMatch` | `models.py` | 从 api_mapper 传递给 field_mapper 和 export | 前后端匹配结果 |
| `Path` | Python 标准库 | 全流水线使用 | 所有文件路径操作 |

---

## 2. 完整 CLI 接口定义

### 2.1 命令行语法

```bash
# ── 完整构建 ──
graph-wiki build [path]                       # 对当前/指定目录运行完整流水线
graph-wiki build . --name "MyProject"         # 指定项目名称
graph-wiki build . --language java            # 指定语言（可绕过自动检测）
graph-wiki build . --llm-backend openai       # 指定 LLM 后端
graph-wiki build . --no-llm                   # 跳过 LLM 标注（仅聚类 + code-map）
graph-wiki build . --model gpt-4o-mini        # 指定 LLM 模型名
graph-wiki build . --sampling-lines 30        # 每域代码采样行数

# ── 增量更新 ──
graph-wiki update [path]                      # 增量更新（不重聚类，不重标注）
graph-wiki update . --recluster               # 强制重新聚类（包路径重命名时使用）
graph-wiki update . --summary                 # 仅对新增/变更域重新 LLM 标注
graph-wiki update . --force                   # 强制完全重建（等价于 build）
```

### 2.2 argparse 参数表

#### build 子命令

| 参数名 | 类型 | 默认值 | 含义 |
|--------|------|--------|------|
| `path` | `Path` | `Path(".")` | 项目根目录路径 |
| `--name` | `str` | 目录名 | 项目名称（用于 Wiki 标题） |
| `--language` | `str` | `"auto"` | 语言选择（`auto`/`java`/`javascript`/`python`/`go`） |
| `--llm-backend` | `str` | `"claude"` | LLM 后端（`claude`/`openai`/`gemini`） |
| `--no-llm` | `bool` | `False` | 跳过 LLM 标注步骤 |
| `--model` | `str` | `None` | LLM 模型名（None = 使用后端默认模型） |
| `--sampling-lines` | `int` | `50` | 每域 Service 源码采样行数 |
| `--parallel` | `int` | `5` | LLM 标注并行度 |
| `--config` | `Path` | `None` | 配置文件路径（默认在项目根目录查找） |
| `--output-dir` | `Path` | `Path("wiki")` | Wiki 输出目录 |
| `--verbose` / `-v` | `int` | `0` | 详细程度（0=标准, 1=详细, 2=调试） |

#### update 子命令

| 参数名 | 类型 | 默认值 | 含义 |
|--------|------|--------|------|
| `path` | `Path` | `Path(".")` | 项目根目录 |
| `--recluster` | `bool` | `False` | 强制重新聚类（包结构变化时使用） |
| `--summary` | `bool` | `False` | 仅对新增/变更域重新 LLM 标注 |
| `--force` | `bool` | `False` | 强制完全重建（等价于 build） |
| `--output-dir` | `Path` | `Path("wiki")` | Wiki 输出目录 |

#### query / path / explain 子命令

| 参数名 | 类型 | 默认值 | 含义 |
|--------|------|--------|------|
| `question` | `str` | 必填 | 自然语言查询（BFS 图遍历） |
| `source` | `str` | 必填 | 起点节点名 |
| `target` | `str` | 必填 | 终点节点名 |
| `concept` | `str` | 必填 | 要解释的节点名 |

### 2.3 CLI 入口实现

```python
def main():
    parser = argparse.ArgumentParser(prog="graph-wiki")
    sub = parser.add_subparsers(dest="command")

    # ── build ──
    bp = sub.add_parser("build", help="完整构建：9 步流水线")
    bp.add_argument("path", type=Path, default=Path("."), nargs="?")
    bp.add_argument("--name", default=None, help="项目名称（默认使用目录名）")
    bp.add_argument("--language", default="auto", choices=["auto", "java", "javascript", "python", "go"])
    bp.add_argument("--llm-backend", default="claude", choices=["claude", "openai", "gemini"])
    bp.add_argument("--model", default=None, help="LLM 模型名（默认使用后端默认模型）")
    bp.add_argument("--no-llm", action="store_true", help="跳过 LLM 标注")
    bp.add_argument("--sampling-lines", type=int, default=50, help="每域 Service 源码采样行数（默认 50）")
    bp.add_argument("--parallel", type=int, default=5, help="LLM 标注并行度（默认 5）")
    bp.add_argument("--config", type=Path, default=None, help="配置文件路径")
    bp.add_argument("--output-dir", type=Path, default=Path("wiki"), help="Wiki 输出目录（默认 wiki/）")
    bp.add_argument("--verbose", "-v", action="count", default=0, help="详细程度（-v = 详细, -vv = 调试）")

    # ── update ──
    up = sub.add_parser("update", help="增量更新（默认不重聚类，不重标注）")
    up.add_argument("path", type=Path, default=Path("."), nargs="?")
    up.add_argument("--recluster", action="store_true", help="强制重新聚类")
    up.add_argument("--summary", action="store_true", help="对新增/变更域重新 LLM 标注")
    up.add_argument("--force", action="store_true", help="强制完全重建（等价于 build）")
    up.add_argument("--output-dir", type=Path, default=Path("wiki"), help="Wiki 输出目录")

    # ── query / path / explain ──
    qp = sub.add_parser("query", help="BFS 图遍历查询（委托 graphify CLI）")
    qp.add_argument("question", type=str, help="自然语言查询")
    pp = sub.add_parser("path", help="最短路径查询（委托 graphify CLI）")
    pp.add_argument("source", type=str, help="起点节点名")
    pp.add_argument("target", type=str, help="终点节点名")
    ep = sub.add_parser("explain", help="节点解释（委托 graphify CLI）")
    ep.add_argument("concept", type=str, help="要解释的节点名")

    args = parser.parse_args()

    try:
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
    except PipelineError as e:
        print(f"错误: {e}", file=sys.stderr)
        sys.exit(1)
    except KeyboardInterrupt:
        print("\n用户中断", file=sys.stderr)
        sys.exit(130)
```

---

## 3. cmd_build 十步流水线详细设计

### 3.1 总体流程

```
cmd_build(args)
    │
    ├── 0. 加载配置: graph-wiki.yaml (可选) → 合并 CLI 参数
    │
    ├── [1/10] detect_corpus(root)                  ───→ corpus
    │
    ├── [2/10] extract_ast(code_files)              ───→ extraction
    │
    ├── [3/10] filter_extraction_for_wiki(...)      ───→ filtered_extraction
    │
    ├── [4/10] build_light_graph(filtered)          ───→ G (graph-lite)
    │
    ├── [5/10] business_cluster(G, ...)             ───→ domains
    │
    ├── [6/10] build_api_map(...)                   ───→ api_matches
    │
    ├── [7/10] build_field_map(...)                 ───→ field_map
    │
    ├── [8/10] label_domains(...)                   ───→ domains (enriched)
    │
    ├── [9/10] export_wiki(...)                     ───→ wiki/ 目录
    │
    ├── [10/10] export_domain_html(...)             ───→ domain_graph.html
    │
    └── [report] evaluate_wiki_quality + write_report → build-report.json
```

### 3.2 步骤详解

#### 步骤 0: 配置加载

```python
def _load_config(args) -> dict:
    """加载 graph-wiki.yaml 配置（可选）。
    
    搜索优先级:
      1. --config 指定的路径
      2. args.path 下的 graph-wiki.yaml
      3. args.path 下的 graph-wiki.yml
      4. 当前目录下的 graph-wiki.yaml
      5. 无配置文件 → 空 dict
    
    返回: CLI args 被配置文件覆盖后的合并配置
    """
```

**合并规则**：CLI 参数优先级 > 配置文件 > 代码默认值。配置文件只覆盖 CLI 未显式指定的参数。

**配置与 CLI 参数的映射**：

```yaml
# graph-wiki.yaml
project:
  name: "OPS"
  language: java

label:
  backend: claude
  model: claude-haiku-4-5-20251001
  parallel_calls: 5
  sampling_lines: 50

cluster:
  merge_threshold: 0.3
  min_domain_size: 5
  merge_rules:
    - keywords: ["mybiz", "mybizdata"]
      target: "my-business"

api_mapper:
  controller_pattern: "*Controller.java"
  frontend_api_dir: "src/api"
  frontend_views_dir: "src/views"

field_mapper:
  entity_dir: "entity"
  dto_dir: "dto"
```

#### 步骤 1: detect_corpus — 文件检测

| 项目 | 内容 |
|------|------|
| 调用函数 | `reuse.detect_corpus(root: Path) -> dict` |
| 输入 | `root` — 项目根目录（`Path`） |
| 输出 | `corpus` — 文件检测结果字典 |
| 关键字段 | `corpus["total_files"]`, `corpus["total_words"]`, `corpus["files"]["code"]` |
| 输出文件 | 无（全量检测结果在内存中传递） |
| 进度输出 | `[1/10] 文件检测: /path/to/project` + 文件统计 |

```python
def _step_detect(root: Path, verbose: int) -> dict:
    print(f"[1/10] 文件检测: {root}")
    corpus = detect_corpus(root)
    code_count = len(corpus.get("files", {}).get("code", []))
    total = corpus.get("total_files", 0)
    words = corpus.get("total_words", 0)
    print(f"  语料库: {code_count} 代码文件, {total} 总文件, ~{words:,} 词")

    if verbose >= 1:
        for ftype, files in corpus.get("files", {}).items():
            print(f"    {ftype}: {len(files)} 文件")

    return corpus
```

#### 步骤 2: extract_ast — AST 提取

| 项目 | 内容 |
|------|------|
| 调用函数 | `reuse.extract_ast(code_files: list[Path]) -> dict` |
| 输入 | `code_files` — 仅代码文件路径列表（排除文档、图片等） |
| 输出 | `extraction` — AST 解析结果字典 |
| 关键字段 | `extraction["nodes"]`, `extraction["edges"]` |
| 输出文件 | 无 |
| 进度输出 | `[2/10] AST 提取 (142 文件)...` + 节点/边统计 |

```python
def _step_extract(code_files: list[Path], verbose: int) -> dict:
    print(f"[2/10] AST 提取 ({len(code_files)} 文件)...")
    extraction = extract_ast(code_files)
    nodes = len(extraction.get("nodes", []))
    edges = len(extraction.get("edges", []))
    print(f"  {nodes} 节点, {edges} 边")

    if verbose >= 2:
        # 调试模式：打印节点类型分布
        from collections import Counter
        types = Counter(n.get("type", "?") for n in extraction.get("nodes", []))
        for t, c in types.most_common(10):
            print(f"    {t}: {c}")

    return extraction
```

#### 步骤 3: filter_extraction_for_wiki — build 前预过滤

| 项目 | 内容 |
|------|------|
| 调用函数 | `reuse.filter_extraction_for_wiki(extraction: dict, root: Path) -> dict` |
| 输入 | `extraction` — AST 提取结果 |
| 输出 | `filtered_extraction` — 仅保留 Graph-Wiki 需要的节点和边 |
| 输出文件 | 可选调试文件（默认不写） |
| 进度输出 | `[3/10] build 前预过滤...` + 原始/过滤后节点边统计 |

```python
def _step_filter_extraction(extraction: dict, root: Path) -> dict:
    print("[3/10] build 前预过滤...")
    filtered = filter_extraction_for_wiki(extraction, root)
    meta = filtered.get("meta", {})
    print(
        f"  nodes: {meta.get('original_nodes', '?')} → {meta.get('filtered_nodes', '?')}, "
        f"edges: {meta.get('original_edges', '?')} → {meta.get('filtered_edges', '?')}"
    )
    return filtered
```

#### 步骤 4: build_light_graph — 轻量图构建

| 项目 | 内容 |
|------|------|
| 调用函数 | `reuse.build_light_graph(filtered_extraction: dict) -> nx.Graph` |
| 输入 | `filtered_extraction` — 预过滤 AST |
| 输出 | `G` — 轻量 NetworkX 无向图 |
| 输出文件 | `graph-lite.json` |
| 可选输出 | `graph.json`（仅在显式启用完整图后端时写出） |
| 进度输出 | `[4/10] 轻量图构建...` + 节点/边统计 |

```python
def _step_build_light_graph(filtered_extraction: dict, output_dir: Path, verbose: int) -> nx.Graph:
    print(f"[4/10] 轻量图构建...")
    G = build_light_graph(filtered_extraction)
    print(f"  {G.number_of_nodes()} 节点, {G.number_of_edges()} 边")

    if verbose >= 1:
        # 统计边的关系类型
        from collections import Counter
        rels = Counter(d.get("relation", "?") for _, _, d in G.edges(data=True))
        for r, c in rels.most_common():
            print(f"    {r}: {c}")

    save_graph_artifacts(light_graph=G, output_dir=output_dir)
    return G
```

#### 步骤 5: business_cluster — 业务域聚类

| 项目 | 内容 |
|------|------|
| 调用函数 | `cluster.business_cluster(G, root, language, merge_threshold, min_domain_size)` |
| 输入 | `G`（graph-lite nx.Graph），`root`（Path），`language`（Language 枚举） |
| 输出 | `domains: list[Domain]` |
| 输出文件 | `domains.json` |
| 进度输出 | `[5/10] 业务域聚类...` + 域统计 |

```python
def _step_cluster(G: nx.Graph, root: Path, language: Language, verbose: int) -> list[Domain]:
    print(f"[5/10] 业务域聚类...")
    try:
        domains = business_cluster(G, root, language=language)
    except ValueError as e:
        # cluster.py 抛出 ValueError（无业务节点 / 锚点太少）
        raise PipelineError(f"聚类失败: {e}") from e

    total_anchors = sum(d.anchors_count() for d in domains)
    print(f"  识别出 {len(domains)} 个业务域, {total_anchors} 个锚点")

    if verbose >= 1:
        for d in domains:
            roles = {k: len(v) for k, v in d.anchors.items()}
            print(f"    {d.id}: {d.anchors_count()} 锚点, 角色: {roles}")

    return domains
```

**错误处理**：
- `ValueError`（无业务节点/锚点太少）→ 转换为 `PipelineError`，终止构建
- 其他异常 → 转换为 `PipelineError`，终止构建

#### 步骤 5: build_api_map — 前后端 API 映射

| 项目 | 内容 |
|------|------|
| 调用函数 | `api_mapper.build_api_map(fe_api_dir, fe_views_dir, be_src_dir, domains, root)` |
| 输入 | 前/后端目录、域列表 |
| 输出 | `api_matches: list[ApiMatch]` |
| 输出文件 | 无（匹配结果在内存中传递） |
| 进度输出 | `[6/10] API 映射...` + 匹配统计 |

```python
def _step_api_map(root: Path, domains: list[Domain], verbose: int) -> list[ApiMatch]:
    print(f"[6/10] API 映射...")

    # 查找前后端目录
    frontend_api = _find_dir(root, "src/api")
    frontend_views = _find_dir(root, "src/views")
    backend_src = _find_dir(root, "src/main/java") or root

    try:
        api_matches = build_api_map(
            frontend_api, frontend_views, backend_src, domains, root
        )
    except Exception as e:
        print(f"  警告: API 映射失败（{e}），跳过此步")
        return []

    print(f"  匹配 {len(api_matches)} 个接口")

    if verbose >= 1 and api_matches:
        by_domain = Counter(m.domain for m in api_matches)
        for domain, count in by_domain.most_common():
            print(f"    {domain}: {count} 接口")

    return api_matches
```

**错误处理**：
- 前端/后端目录不存在 → `build_api_map` 内部静默降级（返回空列表）
- 解析异常（IOError/UnicodeDecodeError）→ 跳过该文件，继续解析
- 全部失败 → 返回空列表，打印警告，不影响后续步骤

#### 步骤 6: build_field_map — 字段级数据流追踪

| 项目 | 内容 |
|------|------|
| 调用函数 | `field_mapper.build_field_map(api_matches, entity_dir, dto_dir, root)` |
| 输入 | API 匹配结果、Entity/DTO 目录 |
| 输出 | `field_map: dict` |
| 输出文件 | 无（结果在内存中传递） |
| 进度输出 | `[7/10] 字段映射...` + 字段统计 |

```python
def _step_field_map(root: Path, api_matches: list[ApiMatch], verbose: int) -> dict:
    print(f"[7/10] 字段映射...")

    # 查找 Entity 和 DTO 目录
    entity_dirs = list(root.rglob("entity")) + list(root.rglob("domain"))
    dto_dirs = list(root.rglob("dto")) + list(root.rglob("vo"))
    entity_dir = entity_dirs[0] if entity_dirs else root
    dto_dir = dto_dirs[0] if dto_dirs else root

    try:
        field_map = build_field_map(api_matches, entity_dir, dto_dir, root)
    except Exception as e:
        print(f"  警告: 字段映射失败（{e}），跳过此步")
        return {}

    count = _count_fields(field_map)
    print(f"  映射 {count} 个字段")

    if verbose >= 1 and field_map:
        for domain, tables in field_map.items():
            table_count = len(tables)
            field_count = sum(len(cols) for cols in tables.values())
            print(f"    {domain}: {table_count} 表, {field_count} 字段")

    return field_map
```

**错误处理**：
- Entity/DTO 目录不存在 → `build_field_map` 内部静默降级（返回空字典）
- 解析异常 → 跳过该文件
- `api_matches` 为空 → 返回空字典（无数据可关联）
- 全部失败 → 返回空字典，打印警告

#### 步骤 7: label_domains — LLM 语义标注

| 项目 | 内容 |
|------|------|
| 调用函数 | `label.label_domains(domains, graph_data, root, config)` |
| 输入 | 域列表、图数据、项目根、标注配置 |
| 输出 | `domains`（已标注：name/display_name/description/core_flows 已填充） |
| 输出文件 | `wiki/{domain}/summary.md` |
| 进度输出 | `[8/10] LLM 标注 (8 域, 5 路并行)...` |

```python
def _step_label(
    domains: list[Domain],
    extraction: dict,
    root: Path,
    args,
) -> list[Domain]:
    if args.no_llm:
        print(f"[8/10] LLM 标注... 跳过（--no-llm）")
        return domains

    print(f"[8/10] LLM 标注 ({len(domains)} 域, {args.parallel} 路并行)...")

    # 构建配置
    backend_map = {
        "claude": LlmBackend.CLAUDE,
        "openai": LlmBackend.OPENAI,
        "gemini": LlmBackend.GEMINI,
    }
    config = LabelConfig(
        backend=backend_map.get(args.llm_backend, LlmBackend.CLAUDE),
        model=args.model or _default_model(args.llm_backend),
        parallel_calls=args.parallel,
        sampling_lines=args.sampling_lines,
    )

    # 格式转换：extraction → graph_data ({node_id: {attributes}})
    graph_data = {}
    for node in extraction.get("nodes", []):
        node_id = node.get("id", "")
        if node_id:
            graph_data[node_id] = node

    try:
        domains = label_domains(domains, graph_data, root, config)
    except Exception as e:
        print(f"  警告: LLM 标注失败（{e}），域保持未标注状态")
        return domains

    labeled = sum(1 for d in domains if d.name and d.name != d.id)
    print(f"  完成: {labeled}/{len(domains)} 域已标注")

    # Token 成本估算（非精确，仅用于参考）
    cost = _estimate_cost(len(domains), args.llm_backend)
    print(f"  成本估算: ~${cost:.2f} (模型: {config.model})")

    return domains
```

**错误处理**：
- API Key 缺失 → `_call_llm` 抛出异常，`label_domains` 被 `except` 捕获，打印警告，域保持未标注
- 单域标注失败 → `label_domains` 内部隔离（不影响其他域），已标注的域正常输出
- 所有域标注失败 → `label_domains` 返回所有未标注域，打印警告
- `--no-llm` → 完全跳过此步

#### 步骤 9: export_wiki — Wiki 导出

| 项目 | 内容 |
|------|------|
| 调用函数 | `export.export_wiki(domains, api_matches, field_map, output_dir)` |
| 输入 | 域列表、API 匹配、字段映射、输出目录 |
| 输出 | `wiki/` 目录（含 index.md, api-index.md, 各域子目录） |
| 输出文件 | `wiki/index.md`, `wiki/api-index.md`, `wiki/{domain}/summary.md`, `wiki/{domain}/code-map.md`, `wiki/{domain}/api-docs.md`, `wiki/{domain}/dependencies.md`, `wiki/{domain}/data-flow.md`, `wiki/{domain}/rules.md`, `wiki/{domain}/spec.md` |
| 进度输出 | `[9/10] 导出 Wiki...` + 文件列表 |

```python
def _step_export(domains, api_matches, field_map, output_dir, verbose):
    print(f"[9/10] 导出 Wiki...")
    output_dir = Path(output_dir)
    try:
        export_wiki(domains, api_matches, field_map, output_dir)
    except Exception as e:
        print(f"  警告: Wiki 导出失败（{e}）")
        raise PipelineError(f"Wiki 导出失败: {e}") from e

    # 统计导出的文件
    md_files = list(output_dir.rglob("*.md"))
    print(f"  生成了 {len(md_files)} 个 Markdown 文件")

    if verbose >= 1:
        for f in sorted(md_files):
            print(f"    {f.relative_to(output_dir.parent)}")

    return output_dir
```

**错误处理**：
- 磁盘满或无权限 → `export_wiki` 抛出异常 → pipeline 终止，报告错误
- 部分文件写入失败（单个域）→ 当前实现中 `_write_code_map` 等子函数各自 try/except
- 目录已存在 → `mkdir(parents=True, exist_ok=True)` 安全覆盖

#### 步骤 10: export_domain_html — 域级可视化

| 项目 | 内容 |
|------|------|
| 调用函数 | `visualize.export_domain_html(domains, output_path)` |
| 输入 | 域列表 |
| 输出 | `domain_graph.html` |
| 输出文件 | `domain_graph.html` |
| 进度输出 | `[10/10] 域级可视化...` + 节点/边统计 |

```python
def _step_visualize(domains, verbose):
    print(f"[10/10] 域级可视化...")
    output_path = Path("domain_graph.html")
    try:
        export_domain_html(domains, output_path)
    except Exception as e:
        print(f"  警告: 可视化导出失败（{e}），跳过")
        return

    dep_count = sum(len(d.dependencies) for d in domains)
    print(f"  {output_path} ({len(domains)} 域, {dep_count} 依赖边)")
```

**错误处理**：
- 依赖数据异常 → `export_domain_html` 内部 try/except，不抛出异常
- D3 CDN 加载失败（HTML 内有 `https://d3js.org/d3.v7.min.js`）→ 浏览器打开时加载，不影响 pipeline
- 全部失败 → 打印警告，pipeline 正常完成

#### report: build-report.json — 质量报告

| 项目 | 内容 |
|------|------|
| 调用函数 | `evaluate_wiki_quality(domains, api_matches, field_map)` + pipeline 汇总 |
| 输入 | corpus、graph 规模、domains、api_matches、field_map、输出文件列表 |
| 输出 | `build-report.json` |
| 用途 | CI/集成测试/Agent 判断 Wiki 是否适合交付 |

```python
def _write_build_report(
    root: Path,
    corpus: dict,
    light_graph: nx.Graph,
    domains: list[Domain],
    api_matches: list[ApiMatch],
    field_map: dict,
    output_dir: Path,
    full_graph_written: bool,
) -> dict:
    quality = evaluate_wiki_quality(domains, api_matches, field_map)
    report = {
        "project": str(root),
        "generated_at": datetime.now(timezone.utc).isoformat(),
        "input": {
            "files": corpus.get("total_files", 0),
            "code_files": len(corpus.get("files", {}).get("code", [])),
        },
        "graph": {
            "light_nodes": light_graph.number_of_nodes(),
            "light_edges": light_graph.number_of_edges(),
            "full_graph_written": full_graph_written,
        },
        "domains": quality["domains"],
        "api": quality["api"],
        "quality": quality["quality"],
    }
    (output_dir / "build-report.json").write_text(
        json.dumps(report, ensure_ascii=False, indent=2),
        encoding="utf-8",
    )
    return report
```

质量状态不会阻止 Markdown 生成，但会决定本次 build 是否可交付：

| 状态 | pipeline 行为 |
|------|---------------|
| `passed` | 打印完成 |
| `warning` | 打印 warning 和问题列表 |
| `failed` | 打印 failed 和问题列表；CI 可据此失败 |

### 3.3 `_cmd_build()` 完整实现

```python
def _cmd_build(args):
    import time
    start = time.time()

    root = args.path.resolve()
    name = args.name or root.name

    # ── Step 0: 加载配置合并 ──
    config_path, config = _find_config(args)
    if config:
        args = _merge_config(args, config)

    print(f"Graph-Wiki build: {name}")
    print(f"  路径: {root}")
    print()

    # ── Step 1: 文件检测 ──
    corpus = _step_detect(root, args.verbose)

    # 提取代码文件列表
    code_files = []
    for f in corpus.get("files", {}).get("code", []):
        code_files.append(Path(f))

    if not code_files:
        raise PipelineError("未找到代码文件。请确认路径下包含 Java/Vue/JS/TS/Python/Go 源文件。")

    # ── Step 2: AST 提取 ──
    extraction = _step_extract(code_files, args.verbose)

    # ── Step 3: build 前预过滤 ──
    filtered_extraction = _step_filter_extraction(extraction, root)

    # ── Step 4: 轻量图构建 ──
    G = _step_build_light_graph(filtered_extraction, args.output_dir, args.verbose)

    # ── Step 5: 业务域聚类 ──
    if args.language != "auto":
        try:
            lang = Language(args.language)
        except ValueError:
            print(f"  警告: 不支持的语言 '{args.language}'，回退到自动检测")
            lang = Language.AUTO
    else:
        lang = Language.AUTO
    domains = _step_cluster(G, root, lang, args.verbose)

    # ── Step 6: API 映射 ──
    api_matches = _step_api_map(root, domains, args.verbose)

    # ── Step 7: 字段映射 ──
    field_map = _step_field_map(root, api_matches, args.verbose)

    # ── Step 8: LLM 标注 ──
    domains = _step_label(domains, extraction, root, args)

    # ── Step 9: Wiki 导出 ──
    output_dir = _step_export(domains, api_matches, field_map, args.output_dir, args.verbose)

    # ── Step 10: 域级可视化 ──
    _step_visualize(domains, args.verbose)

    # ── Report: 质量报告 ──
    report = _write_build_report(
        root=root,
        corpus=corpus,
        light_graph=G,
        domains=domains,
        api_matches=api_matches,
        field_map=field_map,
        output_dir=Path(args.output_dir),
        full_graph_written=False,
    )

    elapsed = time.time() - start
    print()
    print("━" * 50)
    print(f" 完成!  {output_dir / 'index.md'}  |  domain_graph.html")
    print(f" 总耗时: {elapsed:.1f}s")
    print("━" * 50)
```

---

## 4. cmd_update 增量更新流程

### 4.1 总体流程

```
cmd_update(args)

    ── if --force: 等价于 cmd_build, 返回

    ├── 0. 检测变更（detect_incremental）
    │       返回: changed_files, deleted_files
    │
    ├── 1. 分类变更：
    │     ├── code_changed    — 纯代码文件变更（非 API/非 Entity/非 DTO）
    │     ├── api_changed     — 前端 API 或后端 Controller 变更
    │     └── entity_changed  — Entity/DTO 文件变更
    │
    ├── 2. 增量 AST 提取 + 图合并
    │     └── extract_ast(changed) + merge_graph()
    │
    ├── 3. [可选] 重新聚类（--recluster）
    │     └── business_cluster(G)
    │
    ├── 4. [可选] 刷新 API 映射（api 变更 / recluster）
    ├── 5. [可选] 刷新字段映射（entity 变更 / recluster）
    ├── 6. [可选] 重新 LLM 标注（--summary / recluster）
    ├── 7. 重新导出 Wiki 和可视化
    └── 8. 生成变更日志
```

### 4.2 变更检测

```python
def _detect_changes(root: Path) -> dict:
    """
    检测项目文件变更。
    
    当前实现:
      使用 graphify.detect_incremental（对比 git 状态 + 文件时间戳）
    
    未来实现（Phase 2+）:
      使用 manifest.json 的文件哈希对比（更精确，不受 git 影响）
    
    返回:
      {
          "code_changed": [Path, ...],    // 纯业务代码变更
          "api_changed": [Path, ...],     // Controller 或前端 API 变更
          "entity_changed": [Path, ...],  // Entity/DTO 变更
          "all_changed": [Path, ...],     // 全部变更文件
          "all_deleted": [Path, ...],     // 全部删除文件
      }
    """
    from graphify.detect import detect_incremental
    result = detect_incremental(root)

    changed = result.get("new_files", {})
    deleted = result.get("deleted_files", [])

    all_changed = [
        Path(f) for f in
        changed.get("code", [])
    ]

    # 按类型分类
    code_changed, api_changed, entity_changed = [], [], []
    for f in all_changed:
        name = f.name.lower()
        if name.endswith("controller.java"):
            api_changed.append(f)
        elif "entity" in str(f).lower() or "domain" in str(f).lower():
            entity_changed.append(f)
        elif "dto" in str(f).lower() or "vo" in str(f).lower():
            entity_changed.append(f)
        else:
            code_changed.append(f)

    return {
        "code_changed": code_changed,
        "api_changed": api_changed,
        "entity_changed": entity_changed,
        "all_changed": all_changed,
        "all_deleted": [Path(f) for f in deleted],
    }
```

### 4.3 增量重建实现

```python
def _cmd_update(args):
    import time
    start = time.time()
    root = args.path.resolve()

    print(f"Graph-Wiki update: {root}")

    if args.force:
        print("  --force: 强制完全重建")
        build_args = _build_args_from_update(args)
        return _cmd_build(build_args)

    # ── 0. 检测变更 ──
    changes = _detect_changes(root)
    all_changed = changes["all_changed"]
    all_deleted = changes["all_deleted"]

    if not all_changed and not all_deleted:
        print("  无文件变更, 跳过.")
        return

    print(f"  变更: {len(all_changed)} 文件, 删除: {len(all_deleted)} 文件")

    has_api_change = len(changes["api_changed"]) > 0
    has_entity_change = len(changes["entity_changed"]) > 0

    # ── 增量 AST 提取 + 图合并 ──
    if all_changed:
        print(f"  增量提取 ({len(all_changed)} 文件)...")
        extraction = extract_ast(all_changed)
        G = merge_graph(
            extraction,
            graph_path="graph.json",
            prune_sources=[str(f) for f in all_deleted],
        )
    else:
        import json, networkx as nx
        G = nx.node_link_graph(
            json.loads(Path("graph.json").read_text(encoding="utf-8")),
            edges="links",
        )

    # ── [可选] 重新聚类 ──
    if args.recluster:
        print("  重新聚类...")
        domains = business_cluster(G, root)
        print(f"  识别出 {len(domains)} 个业务域")
    else:
        domains = _load_domains_cache(root)

    # ── [可选] 刷新 API 映射 ──
    if has_api_change or args.recluster:
        print("  刷新 API 映射...")
        api_matches = build_api_map(
            _find_dir(root, "src/api"),
            _find_dir(root, "src/views"),
            _find_dir(root, "src/main/java") or root,
            domains, root,
        )
    else:
        api_matches = _load_api_cache(root)

    # ── [可选] 刷新字段映射 ──
    if has_entity_change or args.recluster:
        print("  刷新字段映射...")
        entity_dirs = list(root.rglob("entity")) + list(root.rglob("domain"))
        dto_dirs = list(root.rglob("dto")) + list(root.rglob("vo"))
        field_map = build_field_map(
            api_matches,
            entity_dirs[0] if entity_dirs else root,
            dto_dirs[0] if dto_dirs else root,
            root,
        )
    else:
        field_map = _load_field_cache(root)

    # ── [可选] 重新 LLM 标注 ──
    if args.summary or args.recluster:
        print("  重新 LLM 标注...")
        config = LabelConfig()
        domains = label_domains(domains, {}, root, config)

    # ── 重新导出 ──
    print("  重新导出...")
    export_wiki(domains, api_matches, field_map, args.output_dir)
    export_domain_html(domains)

    # ── 生成变更日志 ──
    _write_changelog(all_changed, all_deleted, domains, args.output_dir)

    elapsed = time.time() - start
    print(f"  完成 ({elapsed:.1f}s)")
```

### 4.4 变更日志

```python
def _write_changelog(changed, deleted, domains, output_dir):
    """生成变更日志 wiki/CHANGELOG.md"""
    from datetime import datetime

    now = datetime.now().isoformat()
    lines = [
        f"# 变更日志",
        f"",
        f"> 更新于 {now}",
        f"",
        f"## 变更的文件",
        f"",
    ]

    for f in sorted(changed):
        lines.append(f"- `{f}` — 变更")
    for f in sorted(deleted):
        lines.append(f"- `{f}` — 删除")

    if not changed and not deleted:
        lines.append("*无变更*")

    lines.append("")
    lines.append("## 受影响的业务域")
    for d in domains:
        lines.append(f"- [[{d.name or d.id}]]")

    (Path(output_dir) / "CHANGELOG.md").write_text(
        "\n".join(lines), encoding="utf-8"
    )
```

### 4.5 缓存读取函数

```python
def _load_domains_cache(root: Path) -> list[Domain]:
    """从 domains.json 恢复域划分"""
    import json
    from .models import Domain

    cache_path = root / "domains.json"
    if not cache_path.exists():
        raise PipelineError("未找到 domains.json，请先运行 graph-wiki build 或指定 --recluster")

    data = json.loads(cache_path.read_text(encoding="utf-8"))
    domains = []
    for d in data.get("domains", []):
        domain = Domain(id=d["id"])
        domain.name = d.get("name", "")
        domain.packages = d.get("packages", [])
        domain.anchors = d.get("anchors", {})
        domain.dependencies = d.get("dependencies", [])
        domains.append(domain)
    return domains


def _load_api_cache(root: Path) -> list:
    """从 api-map.json 恢复 API 匹配结果"""
    import json
    from .models import ApiMatch, FrontendApiCall, BackendEndpoint

    cache_path = root / "api-map.json"
    if not cache_path.exists():
        return []

    data = json.loads(cache_path.read_text(encoding="utf-8"))
    matches = []
    for item in data:
        matches.append(ApiMatch(
            id=item.get("id", ""),
            frontend=FrontendApiCall(**item.get("frontend", {})),
            backend=BackendEndpoint(**item.get("backend", {})),
            match_confidence=item.get("match_confidence", 0.0),
            domain=item.get("domain", ""),
        ))
    return matches


def _load_field_cache(root: Path) -> dict:
    """从 field-map.json 恢复字段映射"""
    import json

    cache_path = root / "field-map.json"
    if not cache_path.exists():
        return {}

    return json.loads(cache_path.read_text(encoding="utf-8"))
```

---

## 5. 目录发现策略

### 5.1 `_find_dir()` 模糊匹配逻辑

```python
def _find_dir(root: Path, suffix: str) -> Path:
    """
    在项目目录中模糊查找目标目录。

    搜索顺序:
      1. 精确匹配: root / suffix
      2. 递归搜索: root.rglob(suffix 的最后一段)
      3. 回退: root（不存在的目录由调用模块内部降级处理）

    示例:
      输入 suffix="src/api":
        1. root / "src/api" → 存在？返回
        2. root.rglob("api") → 找到第一个名为 "api" 的目录？返回
        3. 否则 → root

      输入 suffix="src/main/java":
        1. root / "src/main/java" → 存在？返回
        2. root.rglob("java") → 找到第一个名为 "java" 的目录？返回
        3. 否则 → root
    """
    candidate = root / suffix
    if candidate.exists():
        return candidate
    for p in root.rglob(suffix.split("/")[-1]):
        if p.is_dir():
            return p
    return root
```

### 5.2 各目录查找示例

| 查找目标 | suffix | 精确匹配 | 模糊匹配 | 回退 |
|---------|--------|---------|---------|------|
| 前端 API 目录 | `src/api` | `root/src/api/` | 任意 `api/` 目录 | `root` |
| 前端视图目录 | `src/views` | `root/src/views/` | 任意 `views/` 目录 | `root` |
| 后端源码目录 | `src/main/java` | `root/src/main/java/` | 任意 `java/` 目录 | `root` |
| Entity 目录 | `entity` | 不适用（用 `rglob`） | 任意 `entity/` 或 `domain/` 目录 | `root` |
| DTO 目录 | `dto` | 不适用（用 `rglob`） | 任意 `dto/` 或 `vo/` 目录 | `root` |

### 5.3 设计原则

1. **精确优先**：先尝试精确路径（`root / suffix`），这是最可预测的行为
2. **回退兜底**：精确匹配失败后模糊搜索，确保在非标准项目结构中也能工作
3. **极端回退**：最终回退到 `root`，由各模块内部判断目录是否存在并降级
4. **Windows 兼容**：使用 Path 对象，自动处理路径分隔符

---

## 6. graph-wiki.yaml 配置文件加载

### 6.1 搜索策略

```python
def _find_config(args) -> tuple[Path | None, dict]:
    """
    搜索并加载 graph-wiki.yaml 配置文件。

    搜索顺序（优先级从高到低）:
      1. args.config（如果显式指定）
      2. args.path / "graph-wiki.yaml"
      3. args.path / "graph-wiki.yml"
      4. Path.cwd() / "graph-wiki.yaml"
      5. Path.cwd() / "graph-wiki.yml"

    返回: (config_path, config_dict) — 未找到时 path=None, dict={}
    """
    import yaml

    candidates = []
    if args.config:
        candidates.append(args.config)

    search_dirs = [args.path.resolve(), Path.cwd()]
    for d in search_dirs:
        candidates.append(d / "graph-wiki.yaml")
        candidates.append(d / "graph-wiki.yml")

    for path in candidates:
        if path and path.exists():
            try:
                with open(path, "r", encoding="utf-8") as f:
                    config = yaml.safe_load(f) or {}
                print(f"  加载配置: {path}")
                return path, config
            except (yaml.YAMLError, IOError) as e:
                print(f"  警告: 无法加载配置文件 {path}: {e}")
                return path, {}

    return None, {}
```

### 6.2 配置与 CLI 参数合并

```python
def _merge_config(args, config: dict):
    """
    将 graph-wiki.yaml 配置合并到 CLI 参数。
    合并规则：CLI 显式指定的参数 > 配置文件 > 默认值。

    具体覆盖逻辑:
      - 如果 CLI 参数使用默认值，且配置文件有对应值 → 用配置文件覆盖
      - 如果 CLI 参数显式指定了值（非默认）→ 保持 CLI 参数不变
      - 配置文件缺失的字段 → 保持 CLI 参数的默认值
    """
    project = config.get("project", {})
    label_cfg = config.get("label", {})

    if args.name is None and "name" in project:
        args.name = project["name"]
    if args.language == "auto" and "language" in project:
        args.language = project["language"]
    if args.llm_backend == "claude" and "backend" in label_cfg:
        args.llm_backend = label_cfg["backend"]
    if args.model is None and "model" in label_cfg:
        args.model = label_cfg["model"]
    if args.parallel == 5 and "parallel_calls" in label_cfg:
        args.parallel = label_cfg["parallel_calls"]
    if args.sampling_lines == 50 and "sampling_lines" in label_cfg:
        args.sampling_lines = label_cfg["sampling_lines"]

    return args
```

### 6.3 完整 YAML Schema

参见 `cluster-design.md` 附录 A、`label-design.md` 附录 A、`api-mapper-design.md` §11、`field-mapper-design.md` §11 中的各模块配置项。完整的 `graph-wiki.yaml` 示例：

```yaml
# graph-wiki.yaml — Graph-Wiki 项目配置文件

project:
  name: "MyProject"
  language: java                  # auto / java / javascript / python / go

cluster:
  merge_threshold: 0.3
  min_domain_size: 5
  merge_rules:
    - keywords: ["mybiz", "mybizdata", "mybizorder"]
      target: "my-business"
  role_overrides:
    - pattern: "*Facade.java"
      role: "controller"

label:
  backend: claude                 # claude / openai / gemini
  model: claude-haiku-4-5-20251001
  parallel_calls: 5
  retry_count: 3
  sampling_lines: 50
  skip_domains: []
  only_domains: []

api_mapper:
  controller_pattern: "*Controller.java"
  frontend_api_dir: "src/api"
  frontend_views_dir: "src/views"
  custom_controllers:
    - "*Facade.java"
    - "*Resource.java"

field_mapper:
  entity_dir: "entity"
  extra_entity_dirs:
    - "domain"
  dto_dir: "dto"
  extra_dto_dirs:
    - "vo"
    - "request"
    - "response"
  dto_file_keywords:
    - "dto"
    - "vo"
    - "request"
    - "response"
```

---

## 7. 错误处理与降级表

### 7.1 异常层级

```python
class PipelineError(Exception):
    """流水线通用错误"""
    pass

class DetectError(PipelineError):
    """文件检测失败（路径不存在、无权限）"""
    pass

class ExtractError(PipelineError):
    """AST 提取失败（tree-sitter 解析错误）"""
    pass

class ClusterError(PipelineError):
    """聚类失败"""
    pass

class NoBusinessNodesError(ClusterError):
    """未找到业务节点（可能不是代码项目）"""
    pass

class TooFewAnchorsError(ClusterError):
    """锚点 < 3，无法形成有意义的域划分"""
    pass

class LabelError(PipelineError):
    """标注失败"""
    pass

class LlmApiError(LabelError):
    """LLM API 调用失败"""
    pass

class LlmRateLimitError(LabelError):
    """API rate limit 超限"""
    pass

class ExportError(PipelineError):
    """导出失败（磁盘满、权限不足）"""
    pass
```

### 7.2 每步降级策略

| 步骤 | 模块 | 失败场景 | 降级行为 | 对后续影响 |
|:----:|------|---------|---------|-----------|
| [1/10] | `detect` | 路径不存在、无权限 | **终止构建**，报告错误 | 无后续步骤可执行 |
| [1/10] | `detect` | 未找到代码文件 | **终止构建**，报告错误 | 无 AST 数据可提取 |
| [2/10] | `extract` | tree-sitter 解析异常 | **终止构建**，报告错误 | 无 AST 数据 |
| [2/10] | `extract` | 单文件解析失败 | 跳过该文件，继续解析 | 该文件不会出现在图和分析结果中 |
| [3/10] | `filter/build_light_graph` | 预过滤或轻量图构建错误 | **终止构建**，报告错误 | 无图数据，无法聚类 |
| [5/10] | `cluster` | 无业务节点（`NoBusinessNodesError`） | **终止构建**，报告错误 | 无域数据，后续步骤无法执行 |
| [5/10] | `cluster` | 锚点太少（`TooFewAnchorsError`） | **终止构建**，报告错误 | 项目可能太小或全是工具类 |
| [6/10] | `api_mapper` | 前/后端目录不存在 | 静默跳过，返回空列表 | api-docs.md 留空，不影响域 Wiki |
| [6/10] | `api_mapper` | 单文件解析失败 | 跳过该文件，继续解析 | 该文件对应的 API 不会出现在结果中 |
| [7/10] | `field_mapper` | Entity/DTO 目录不存在 | 静默跳过，返回空字典 | data-flow.md 留空 |
| [7/10] | `field_mapper` | 单 Entity/DTO 文件解析失败 | 跳过该文件 | 该类的字段映射缺失 |
| [8/10] | `label` | API Key 缺失 | 打印警告，所有域保持未标注 | summary.md 使用域 ID 作为名称 |
| [8/10] | `label` | 单域标注失败 | 该域保持未标注，不影响其他域 | 部分域无 LLM 描述 |
| [8/10] | `label` | 所有域标注失败 | 打印警告，返回未标注域 | Wiki 无业务描述 |
| [9/10] | `export` | 磁盘满/无权限 | **终止构建**，报告 `ExportError` | Wiki 文件未写入 |
| [9/10] | `export` | 单文件写入失败 | 跳过该文件（当前实现不保证） | 单个域缺少部分文件 |
| [10/10] | `visualize` | 模板渲染错误 | 打印警告，跳过 | 无 domain_graph.html |
| [10/10] | `visualize` | D3 URL 不可达 | 不影响生成（D3 在浏览器中加载） | HTML 已生成但无交互效果 |

### 7.3 降级实现原则

```python
# 原则 1: 致命步骤（detect/extract/build/cluster）→ 终止
try:
    corpus = detect_corpus(root)
except Exception as e:
    raise DetectError(f"文件检测失败: {e}") from e

# 原则 2: 非致命步骤（api_mapper/field_mapper）→ 跳过 + 警告
try:
    api_matches = build_api_map(...)
except Exception as e:
    print(f"  警告: API 映射失败（{e}），跳过此步")
    api_matches = []

# 原则 3: LLM 标注 → 隔离失败 + 降级
try:
    domains = label_domains(...)
except Exception as e:
    print(f"  警告: LLM 标注失败（{e}），域保持未标注状态")

# 原则 4: 导出 → 部分失败不影响整体
try:
    export_wiki(...)
except ExportError:
    raise  # 致命错误（磁盘满）
except Exception as e:
    print(f"  导出警告: {e}")  # 非致命错误（单文件失败）
```

---

## 8. cmd_query/path/explain 委托策略

### 8.1 当前实现（Phase 1）

当前通过 `subprocess` 调用 `graphify` CLI：

```python
def _run_graphify(subcmd: str, *args):
    """委托给 graphify CLI 执行查询。
    
    当前实现（Phase 1）:
      使用 subprocess 调用 graphify 命令行工具。
      优点：零集成成本，graphify 的 query/path/explain 功能开箱即用。
      缺点：进程启动开销（~200ms），不支持 Windows（graphify CLI 可能不兼容）。
    
    Phase 4+ 目标:
      直接使用 graphify 的 Python API 调用，避免 subprocess 开销。
    """
    import subprocess
    try:
        subprocess.run(
            ["graphify", subcmd] + list(args),
            check=True,
        )
    except FileNotFoundError:
        print("错误: graphify 未安装或不在 PATH 中。请执行 'pip install graphify'")
        sys.exit(1)
    except subprocess.CalledProcessError as e:
        print(f"错误: graphify {subcmd} 执行失败: {e}")
        sys.exit(1)
```

### 8.2 局限性与未来方案

| 维度 | Phase 1（当前） | Phase 4+（目标） |
|------|:---------------:|:----------------:|
| 调用方式 | `subprocess.run(["graphify", ...])` | Python API 直接调用 |
| 进程开销 | ~200ms 每次 | ~0ms（同一进程） |
| Windows 兼容 | ❌ graphify CLI 可能不支持 | ✅ Python API 跨平台 |
| 返回值 | 打印到 stdout（无法捕获） | 返回 Python 对象 |
| 错误处理 | `try/except CalledProcessError` | 标准的 Python 异常 |
| 数据传递 | 通过 stdin/stdout（字符串） | 内存中的 Python 对象 |

### 8.3 委托策略决策

```
决策：Phase 4+ 前保持 subprocess

原因：
  1. graphify 的 query/path/explain 使用了其内部图索引和 LLM 接口，
     这些是 Graph-Wiki 没有复用的 graphify 独有功能
  2. 即使 Phase 4+ 直接调用 Python API，也不过是将 subprocess 替换为
     import + function call，功能逻辑不变
  3. graphify 的 query 功能不是 Graph-Wiki 的核心差异点
     （Graph-Wiki 的核心是聚类 → 标注 → Wiki 导出）
  4. Phase 4+ 的实施时间取决于 graphify 是否提供稳定的 Python API

标记：Phase 4+
```

### 8.4 子命令行为一览

| 子命令 | 作用 | 委托方式 | 对 graph.json 的依赖 |
|--------|------|---------|:-------------------:|
| `graph-wiki query "..."` | BFS 图遍历 + LLM 解释 | `subprocess: graphify query` | 需要 graph.json 存在 |
| `graph-wiki path A B` | 两点间最短路径 | `subprocess: graphify path` | 需要 graph.json 存在 |
| `graph-wiki explain X` | 节点属性和关联解释 | `subprocess: graphify explain` | 需要 graph.json 存在 |

---

## 9. 辅助函数

### 9.1 `_count_fields()` — 字段计数

```python
def _count_fields(field_map: dict) -> int:
    """统计 field_map 中的字段总数"""
    count = 0
    for tables in field_map.values():
        for columns in tables.values():
            count += len(columns)
    return count
```

### 9.2 `_default_model()` — 后端默认模型

```python
def _default_model(backend: str) -> str:
    """返回各后端推荐的默认模型名"""
    return {
        "claude": "claude-haiku-4-5-20251001",
        "openai": "gpt-4o-mini",
        "gemini": "gemini-1.5-flash",
    }.get(backend, "claude-haiku-4-5-20251001")
```

### 9.3 `_estimate_cost()` — 成本估算

```python
def _estimate_cost(domain_count: int, backend: str) -> float:
    """
    估算 LLM 标注成本。
    
    假设每域输入 ~2,500 tokens，输出 ~300 tokens。
    按各后端价格计算。
    """
    prices = {
        "claude": (0.0008, 0.004),    # Haiku 4.5: 输入 $0.0008/K, 输出 $0.004/K
        "openai": (0.00015, 0.0006),  # GPT-4o-mini
        "gemini": (0.000075, 0.0003), # Gemini 1.5 Flash
    }
    in_price, out_price = prices.get(backend, prices["claude"])
    total_input = domain_count * 2500 / 1000 * in_price
    total_output = domain_count * 300 / 1000 * out_price
    return round(total_input + total_output, 2)
```

### 9.4 `_build_args_from_update()` — update → build 转换

```python
def _build_args_from_update(args) -> argparse.Namespace:
    """将 update 命令的 args 转换为 build 命令兼容的格式"""
    import types
    build_args = types.SimpleNamespace()
    build_args.path = args.path
    build_args.name = None
    build_args.language = "auto"
    build_args.llm_backend = "claude"
    build_args.model = None
    build_args.no_llm = False
    build_args.sampling_lines = 50
    build_args.parallel = 5
    build_args.config = None
    build_args.output_dir = args.output_dir
    build_args.verbose = 0
    return build_args
```

### 9.5 `_print_step()` — 统一进度输出

```python
import time

def _print_step(step: int, total: int, message: str, start: float | None = None):
    """统一进度输出格式"""
    elapsed = f" ({time.time() - start:.1f}s)" if start else ""
    print(f"\n[{step}/{total}] {message}...{elapsed}")
    return time.time()
```

---

## 10. 性能估计

### 10.1 各步骤耗时

| 步骤 | 模块 | OPS 项目 (~5,000 文件) | 中型项目 (~500 文件) | 小型项目 (~100 文件) |
|:----:|------|:---------------------:|:-------------------:|:-------------------:|
| [1/10] | `detect` | ~2s | ~0.5s | ~0.2s |
| [2/10] | `extract` | ~30s | ~5s | ~1s |
| [3/10] | `build` | ~60s | ~8s | ~2s |
| [5/10] | `cluster` | ~1s | ~0.5s | ~0.2s |
| [6/10] | `api_mapper` | ~0.3s | ~0.2s | ~0.1s |
| [7/10] | `field_mapper` | ~1s | ~0.5s | ~0.1s |
| [8/10] | `label` | ~30s (25 域, 5 并行) | ~15s (10 域) | ~6s (5 域) |
| [9/10] | `export` | ~0.5s | ~0.3s | ~0.1s |
| [10/10] | `visualize` | ~0.1s | ~0.1s | ~0.1s |
| **合计** | | **~95s** | **~15s** | **~4s** |

### 10.2 增量更新耗时

| 变更类型 | 增量耗时 | 完全重建耗时 | 提速倍数 |
|---------|:--------:|:-----------:|:--------:|
| 修改 1 个 Java 文件 | ~65s | ~95s | ~1.5x |
| 新增 1 个 Controller | ~67s | ~95s | ~1.4x |
| 修改 1 个 Entity | ~66s | ~95s | ~1.4x |
| 新增模块（--recluster） | ~96s | ~95s | ~1x（等价） |

**增量更新的主要瓶颈**：`build_merge` 仍需要约 60s 加载和合并 graph.json，这是 graphify 库的限制。真正的增量优势在于：
- 避免 Louvain 全图重算（Graphify 的 ~3min 瓶颈）
- 避免 LLM 重新标注（~30s，除非 `--summary`）
- 只刷新变更域的 code-map

---

## 11. 测试用例

### 11.1 测试完整构建流程

```python
def test_cmd_build_small_project():
    """验证在小型项目上完整执行 cmd_build 流程"""
    import tempfile, os
    from pathlib import Path
    from graph_wiki.pipeline import _cmd_build
    import argparse

    with tempfile.TemporaryDirectory() as tmpdir:
        # 创建最小项目结构
        root = Path(tmpdir)

        # 创建一个 Java Controller 文件
        src_dir = root / "src/main/java/com/example/order"
        src_dir.mkdir(parents=True)
        (src_dir / "OrderController.java").write_text("""
        package com.example.order;
        @RestController
        @RequestMapping("/api/order")
        public class OrderController {
            @GetMapping("/detail")
            public String detail() { return "ok"; }
        }
        """, encoding="utf-8")

        # 构造 args（--no-llm 以加速测试）
        args = argparse.Namespace(
            path=root, name=None, language="auto",
            llm_backend="claude", model=None, no_llm=True,
            sampling_lines=50, parallel=5, config=None,
            output_dir=Path("wiki"), verbose=0,
        )

        # 执行完整构建
        _cmd_build(args)

        # 验证输出
        wiki_dir = root / "wiki"
        assert wiki_dir.exists()
        assert (wiki_dir / "index.md").exists()
        assert (root / "domain_graph.html").exists()


def test_cmd_build_no_code_files():
    """验证无代码文件时抛出 PipelineError"""
    import tempfile
    from pathlib import Path
    from graph_wiki.pipeline import _cmd_build, PipelineError
    import argparse

    with tempfile.TemporaryDirectory() as tmpdir:
        root = Path(tmpdir)
        (root / "README.md").write_text("# Empty Project")

        args = argparse.Namespace(
            path=root, name=None, language="auto",
            llm_backend="claude", model=None, no_llm=True,
            sampling_lines=50, parallel=5, config=None,
            output_dir=Path("wiki"), verbose=0,
        )

        with pytest.raises(PipelineError, match="未找到代码文件"):
            _cmd_build(args)
```

### 11.2 测试增量更新无变更

```python
def test_cmd_update_no_changes():
    """验证无文件变更时 update 跳过"""
    import tempfile
    from pathlib import Path
    from graph_wiki.pipeline import _cmd_update
    import argparse

    with tempfile.TemporaryDirectory() as tmpdir:
        root = Path(tmpdir)

        args = argparse.Namespace(
            path=root, recluster=False, summary=False,
            force=False, output_dir=Path("wiki"),
        )

        # 不应抛出异常，应打印 "无文件变更" 并返回
        _cmd_update(args)
```

### 11.3 测试目录发现策略

```python
def test_find_dir_exact():
    """验证精确路径匹配"""
    from graph_wiki.pipeline import _find_dir
    import tempfile
    from pathlib import Path

    with tempfile.TemporaryDirectory() as tmpdir:
        root = Path(tmpdir)
        (root / "src/api").mkdir(parents=True)

        result = _find_dir(root, "src/api")
        assert result == root / "src/api"


def test_find_dir_fuzzy():
    """验证模糊路径回退"""
    from graph_wiki.pipeline import _find_dir
    import tempfile
    from pathlib import Path

    with tempfile.TemporaryDirectory() as tmpdir:
        root = Path(tmpdir)
        (root / "deep/nested/api").mkdir(parents=True)

        result = _find_dir(root, "src/api")
        assert result.exists()
        assert result.name == "api"


def test_find_dir_fallback():
    """验证无匹配时回退到 root"""
    from graph_wiki.pipeline import _find_dir
    import tempfile
    from pathlib import Path

    with tempfile.TemporaryDirectory() as tmpdir:
        root = Path(tmpdir)
        result = _find_dir(root, "src/api")
        assert result == root
```

### 11.4 测试配置加载与合并

```python
def test_load_config():
    """验证 graph-wiki.yaml 的加载和合并逻辑"""
    from graph_wiki.pipeline import _find_config, _merge_config
    import tempfile
    import argparse
    from pathlib import Path

    with tempfile.TemporaryDirectory() as tmpdir:
        root = Path(tmpdir)

        # 创建配置文件
        (root / "graph-wiki.yaml").write_text("""
project:
  name: "TestProject"
  language: java
label:
  backend: openai
  model: gpt-4o-mini
""", encoding="utf-8")

        # 构造 args（全部默认值，应被配置覆盖）
        args = argparse.Namespace(
            path=root, config=None,
            name=None, language="auto",
            llm_backend="claude", model=None,
            parallel=5, sampling_lines=50,
            no_llm=False,
        )

        path, config = _find_config(args)
        assert path is not None
        assert "project" in config
        assert config["project"]["name"] == "TestProject"

        # 合并配置 → CLI 参数
        merged = _merge_config(args, config)

        # 验证配置生效
        assert merged.name == "TestProject"
        assert merged.language == "java"
        assert merged.llm_backend == "openai"
        assert merged.model == "gpt-4o-mini"


def test_load_config_cli_overrides():
    """验证 CLI 显式参数优先于配置文件"""
    from graph_wiki.pipeline import _merge_config
    import argparse

    args = argparse.Namespace(
        name="ExplicitName",   # 非默认值 → CLI 应保留
        language="auto",
        llm_backend="claude",  # 默认值 → 应被配置覆盖
        model=None,
    )

    config = {
        "project": {"name": "ConfigName"},
        "label": {"backend": "openai"},
    }

    merged = _merge_config(args, config)

    # --name 显式指定的值保留
    assert merged.name == "ExplicitName"
    # --llm-backend 使用默认值 → 被配置覆盖
    assert merged.llm_backend == "openai"
    # --model 默认值且配置没有 → 保持 None
    assert merged.model is None
```

### 11.5 测试成本估算

```python
def test_estimate_cost():
    """验证成本估算函数"""
    from graph_wiki.pipeline import _estimate_cost

    # 25 域, Claude Haiku
    cost = _estimate_cost(25, "claude")
    assert 0.05 < cost < 0.15  # ~$0.09

    # 25 域, GPT-4o-mini
    cost = _estimate_cost(25, "openai")
    assert 0.01 < cost < 0.03  # ~$0.02

    # 0 域 = $0
    cost = _estimate_cost(0, "claude")
    assert cost == 0.0

    # 未知后端回退到 claude 价格
    cost = _estimate_cost(25, "unknown")
    assert 0.05 < cost < 0.15
```

---

## 12. 变更记录

| 日期 | 变更内容 | 版本 |
|------|---------|:----:|
| 2026-06-11 | 初始版本（基于架构文档 v1.0） | v1.0 |
| 2026-06-15 | 重写：基于实际代码实现更新，补充完整 CLI 接口定义、十步流水线每步详细设计（输入/输出/错误处理/进度输出）、增量更新四阶段流程、目录发现策略、配置加载与合并、错误处理与降级表、委托策略分析（Phase 4+ 标记）、5 个测试用例、性能估计、完整输出文件清单 | v2.0 |

---

## 附录 A：输出文件清单完整版

| 文件 | 生成步骤 | 生成模块 | 与 build/update 关系 |
|------|:--------:|---------|:-------------------:|
| `graph-lite.json` | [4/10] | `reuse.build_light_graph` / `reuse.save_graph_artifacts` | 全量必须生成 |
| `graph.json` | 可选 | `reuse.build_graph` | 仅显式启用完整后端时生成 |
| `domains.json` | [5/10] | `cluster.business_cluster` | 全量生成，增量复用 |
| `api-map.json` | [6/10] | `api_mapper.build_api_map` | 全量生成，增量有条件重建 |
| `field-map.json` | [7/10] | `field_mapper.build_field_map` | 全量生成，空结果写 `{}` |
| `build-report.json` | report | `pipeline._write_build_report` | 全量必须生成 |
| `wiki/index.md` | [9/10] | `export.export_wiki` | 全量/增量都生成 |
| `wiki/api-index.md` | [9/10] | `export._write_api_index` | 全量/增量都生成 |
| `wiki/{domain}/summary.md` | [8/10] | `label._write_summary` | 全量生成，增量不覆盖（除非 `--summary`） |
| `wiki/{domain}/code-map.md` | [9/10] | `export._write_code_map` | 全量/增量都生成 |
| `wiki/{domain}/api-docs.md` | [9/10] | `export._write_api_docs` | 全量/增量都生成 |
| `wiki/{domain}/dependencies.md` | [9/10] | `export._write_dependencies` | 全量/增量都生成 |
| `wiki/{domain}/data-flow.md` | [9/10] | `export._write_data_flow` | 全量/增量都生成 |
| `wiki/{domain}/rules.md` | [9/10] | `export`（占位） | 已存在时不覆盖 |
| `wiki/{domain}/spec.md` | [9/10] | `export`（占位） | 已存在时不覆盖 |
| `wiki/CHANGELOG.md` | update | `_write_changelog` | 仅增量生成 |
| `domain_graph.html` | [10/10] | `visualize.export_domain_html` | 全量/增量都生成 |
| `manifest.json` | report/build | `reuse.build_manifest` | 全量生成，后续增量使用 |

## 附录 B：退出码规范

| 退出码 | 含义 | 场景 |
|:------:|------|------|
| 0 | 成功 | 构建/更新成功完成 |
| 1 | 通用错误 | 路径不存在、无代码文件、聚类失败 |
| 2 | 配置错误 | 不支持的参数值、配置文件语法错误 |
| 130 | 用户中断 | `Ctrl+C` 中断 |



