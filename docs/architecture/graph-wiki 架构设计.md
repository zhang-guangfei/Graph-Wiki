# Graph-Wiki 总体设计文档

> 版本：v1.0  
> 日期：2026-06-11  
> 状态：设计阶段  
> **本文档是 Graph-Wiki 项目的唯一设计真相源（Single Source of Truth）。所有后续的设计决策、实现原理、模块接口均以此文档为准。**
>
> **v1 产品重启修订（2026-06-29）**：业务域深度阅读体验以 `domain-read-model.json` 为唯一 v1 产品输入，详细架构见 [`domain-read-model-v1-architecture.md`](./domain-read-model-v1-architecture.md)。当本文旧章节仍将 `rules.md` / `spec.md` 视为人工占位主入口时，以 v1 修订文档为准。

---

## 目录

1. [项目概述](#一项目概述)
   - 1.1 [什么是 Graph-Wiki](#11-什么是-graph-wiki)
   - 1.2 [核心目标](#12-核心目标)
   - 1.3 [与 Graphify 的关系](#13-与-graphify-的关系)
   - 1.4 [复用策略一览](#14-复用策略一览)
   - 1.5 [设计原则](#15-设计原则)
2. [市场定位](#二市场定位)
3. [核心架构](#三核心架构)
4. [模块设计](#四模块设计)
   - 4.1 [复用层](#41-复用层-reuse-layer)
   - 4.2 [聚类模块](#42-聚类模块-cluster)
   - 4.3 [标注模块](#43-标注模块-label)
   - 4.4 [Wiki 导出模块](#44-wiki-导出模块-export)
   - 4.5 [可视化模块](#45-可视化模块-visualize)
   - 4.6 [API 映射模块](#46-api-映射模块-apimapper)
   - 4.7 [字段映射模块](#47-字段映射模块-fieldmapper)
   - 4.8 [流水线模块](#48-流水线模块-pipeline)
5. [数据流（概要）](#五数据流)
6. [输出规范](#六输出规范)
7. [消费链路](#七消费链路)
8. [数据流详解：graph.json 的角色定位](#八数据流详解graphjson-的角色定位)
   - 8.1 [对比：两个项目的分层模型](#81-对比两个项目的分层模型)
   - 8.2 [完整数据流（从上到下）](#82-完整数据流从上到下)
   - 8.3 [两种消费路径](#83-两种消费路径)
   - 8.4 [graph.json 内部结构](#84-graphjson-内部结构)
   - 8.5 [各层使用的 graph.json 子集](#85-各层使用的-graphjson-子集)
9. [实施计划](#九实施计划)
10. [功能需求清单](#十功能需求清单)
11. [Token 与性能全面评估](#十一token-与性能全面评估)
12. [粒度设计：三层粒度模型](#十二粒度设计三层粒度模型)
13. [与 Graphify 的差距分析](#十三与-graphify-的差距分析)
14. [设计决策记录](#十四设计决策记录)
15. [项目结构](#十五项目结构)
16. [质量保障](#十六质量保障)
17. [关联文档](#十七关联文档)

---

## 一、项目概述

### 1.1 什么是 Graph-Wiki

Graph-Wiki 是一个**以人类为第一客户、同时兼容 Agent 消费**的代码库知识工具。它从源代码中自动识别业务域边界，用 LLM 生成业务级描述文档，最终输出一个包含业务摘要、规则、代码地图的双向链接 Markdown 文档库（Wiki）。

### 1.2 核心目标

> 让程序员（和 Agent）能够通过**业务域目录**来理解一个代码库，而不是通过文件目录或代码索引。

### 1.3 与 Graphify 的关系

| | Graphify | Graph-Wiki |
|------|----------|------------|
| **第一客户** | Agent (AI) | 人类（程序员） |
| **第二客户** | 通过 Agent 间接服务的人 | Agent（通过 Wiki 入口 → graph.json 查询） |
| **聚类方法** | Louvain（链接密度） | 包路径 + import 聚合（结构语义） |
| **标注方法** | 关键词频率拼凑 | LLM 语义理解 |
| **最小粒度** | 方法/字段 | 类/文件 → 域 |
| **核心输出** | graph.json（图数据） | Wiki 文档树（Markdown） |
| **可视化** | 全图力导向（乱麻） | 域级依赖图（~30 节点） |
| **增量更新** | 慢（Louvain 全图重算） | 快（域边界稳定，只重提取变更文件） |

**Graph-Wiki 复用 Graphify 的底层能力**（文件检测、AST 提取、图构建），但**在聚类、标注、导出三个核心环节采用了完全不同的方法论**。

### 1.4 复用策略一览

```
╔══════════════════════════════════════════════════════════════════╗
║              Graphify 原有流水线 (9 步)                            ║
╠══════════════════════════════════════════════════════════════════╣
║                                                                  ║
║  Step 1   环境检测                                                ║
║  Step 2   文件检测    ──→  graphify.detect()    ← Graph-Wiki 复用 ║
║  Step 3   实体提取    ──→  graphify.extract()   ← Graph-Wiki 复用 ║
║  Step 4   图构建      ──→  graphify.build()     ← Graph-Wiki 复用 ║
║  ═══════════════════════════════════════════════════════════════  ║
║  Step 5   社区聚类    ──→  Louvain 算法          ✕ 废弃            ║
║  Step 6   社区标注    ──→  关键词频率拼凑          ✕ 废弃            ║
║  Step 7   HTML 导出   ──→  D3 全图力导向          ✕ 废弃            ║
║  Step 8   Wiki 导出   ──→  社区级页面              ✕ 废弃            ║
║  Step 9   清理报告                                            ║
║                                                                  ║
╚══════════════════════════════════════════════════════════════════╝
                               │
                               │  复用前 3 步，替换后 6 步
                               ▼
╔══════════════════════════════════════════════════════════════════╗
║              Graph-Wiki 新流水线                                   ║
╠══════════════════════════════════════════════════════════════════╣
║                                                                  ║
║  复用层（graphify 作为 Python 库调用，不运行 Graphify CLI）        ║
║  ─────────────────────────────────────────────                   ║
║  graphify.detect()     ← 文件检测，原封不动复用                    ║
║  graphify.extract()    ← AST 提取，tree-sitter 30+ 语言          ║
║  filter_extraction_for_wiki() ← build 前预过滤噪音节点/边          ║
║  build_light_graph()   ← 构建 Graph-Wiki 轻量图                  ║
║       │                                                          ║
║       │  graph-lite.json (v1.0 默认中间产物)                       ║
║       │  graph.json (可选完整后端，供精确下钻查询)                   ║
║       │                                                          ║
║  ═══════════════════════════════════════════════════════════════  ║
║                                                                  ║
║  新建层（Graph-Wiki 核心）                                         ║
║  ─────────────────────────                                        ║
║  graph_wiki.business_cluster()   ← 替换 Louvain：包前缀 + import 聚合    ║
║  graph_wiki.build_api_map()      ← 新增：前后端 API 映射               ║
║  graph_wiki.build_field_map()    ← 新增：字段级数据流 + ER 图          ║
║  graph_wiki.label_domains()      ← 替换关键词拼凑：LLM 语义标注        ║
║  graph_wiki.export_wiki()        ← 替换导出：业务 Wiki                 ║
║  graph_wiki.export_domain_html() ← 替换 D3 毛球：域级交互可视化        ║
║                                                                  ║
╚══════════════════════════════════════════════════════════════════╝
```

**核心区别**：

```
Graphify:  graphify CLI 是一个完整的端到端工具
           用户运行 /graphify → 走 9 步流水线 → 产出 graph.json + HTML

Graph-Wiki: graphify 只是一个底层库，被 import 使用
            用户运行 graph-wiki build → 调用 graphify 的前 3 步
            → 然后用自己的后 6 步 → 产出 Wiki 文档树
```

### 1.5 设计原则

1. **LLM Wiki 是入口，graph-lite.json 是默认后端，graph.json 是可选完整后端** — 人类和 Agent 都通过 Wiki 进入，常规导航使用轻量图，需要精确方法级细节时才查完整 graph.json
2. **越上层越接近人的思维** — Part B（LLM 语义）权重大于 Part A（AST 结构）
3. **人和 Agent 共用同一入口** — Wiki 既是人类导航，也是 Agent 的 scope reduction 工具
4. **LLM 生成 + 人工定稿** — LLM 负责初稿（summary.md），人工负责权威内容（rules.md, spec.md）
5. **聚类以类/文件为单位，输出含方法级业务点** — 域划分在类级进行（~2,000 节点，避免方法级噪音淹没聚类），但每个域内提取 Controller/Service 的 public 方法作为业务点（~500-800 个），方法级调用链保留在 graph-lite.json 中；完整 graph.json 仅在开启完整后端时用于按需下钻
6. **大项目先过滤再建图** — v1.0 默认在 graphify extraction 之后、NetworkX 图构建之前执行业务级预过滤，避免 OPS 级项目先构建 20 万级节点/百万级边完整大图导致 OOM

---

## 二、市场定位

### 2.1 市场空缺

```
                          
  人类阅读 ─┤                                  ├─ 人类阅读
            │                                  │
    Backstage          Context Mapper     Spring Modulith
    (手动声明)          (DDD 建模)         (架构测试)
            │                                  │
            └──────────┬───────────────────────┘
                       │
              ★ Graph-Wiki 的位置
              (自动发现 + 人类优先 + Agent 兼容)
                       │
            ┌──────────┴───────────────────────┐
            │                                  │
      jQAssistant        Graphify        CodeScene
      (图查询)           (知识图谱)       (行为分析)
            │                                  │
   Agent 消费 ┤                                ├─ Agent 消费
```

**当前没有任何工具占据"自动从代码推断业务语义 + 以人类为第一客户"这个位置。** Graph-Wiki 填补了这个空缺。

### 2.2 与竞品的差异化

| 能力 | Spring Modulith | Backstage | Graphify | **Graph-Wiki** |
|------|:--:|:--:|:--:|:--:|
| 自动发现业务域 | ❌ 手动声明 | ❌ 手动声明 | ❌ Louvain | ✅ 结构语义聚类 |
| 人类可读 Wiki | ❌ | ⚠️ 手动维护 | ❌ 方法名拼凑 | ✅ LLM 生成 |
| Agent 友好 | ❌ | ❌ | ✅ 核心目标 | ✅ 兼容（Wiki 入口） |
| 前端支持 | ❌ | ✅ | ✅ | ✅ 计划支持 |
| 成本 | 免费 | 免费（自部署） | 免费 | 免费 |
| LLM Token 成本 | 无 | 无 | 无（仅代码） | ~$1/次（一次性标注） |

---

## 三、核心架构

### 3.1 分层模型

```
┌──────────────────────────────────────────────────────────────┐
│                                                              │
│  第 3 层    LLM Wiki（业务思维）                                │
│  ────────   权重最高，面向人类                                   │
│             · 业务域目录树（20-30 个域）                         │
│             · 每个域的摘要、规则、流程描述                        │
│             · LLM 生成初稿 + 人工定稿                           │
│             · Obsidian 兼容的双向链接 MD                        │
│             · 人类和 Agent 的共同入口                           │
│                                                              │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│  第 2 层    业务域划分（领域思维）                                │
│  ────────   包结构 + import 聚合 + LLM 语义                     │
│             · business_cluster() — 识别业务域边界               │
│             · label_domains() — 为每个域生成名称和描述            │
│             · 计算域间依赖强度                                  │
│             · 输出：domains.json                               │
│                                                              │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│  第 1 层    graph.json（代码思维）                               │
│  ────────   复用 graphify 的 detect/build/extract              │
│             · AST 提取的精确代码结构                             │
│             · 类/方法/字段级的调用关系（原始数据）                  │
│             · 不做业务解释                                      │
│             · 输出：NetworkX Graph → graph.json                │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

### 3.2 模块依赖关系

```
                    ┌──────────────┐
                    │   pipeline   │  ← CLI 入口：graph-wiki build
                    └──────┬───────┘
                           │
       ┌───────────────────┼───────────────────────┐
       │                   │                       │
       ▼                   ▼                       ▼
┌──────────┐   ┌──────────────┐   ┌──────────┐ ┌──────────┐
│ cluster  │   │  api_mapper  │   │  label   │ │  export  │
│ (聚类)    │   │  (API 映射)   │   │ (LLM标注) │ │ (Wiki导出)│
└────┬─────┘   └──────┬───────┘   └────┬─────┘ └────┬─────┘
     │                │                │             │
     │                │ (api-map.json) │             │
     │                ▼                │             │
     │         ┌──────────────┐        │             │
     │         │ field_mapper │        │             │
     │         │  (字段映射)   │        │             │
     │         └──────┬───────┘        │             │
     │                │ (field-map.json)             │
     │                ▼                │             │
     │         ┌──────────────┐        │             │
     │         │    impact    │        │             │
     │         │ (影响分析)    │        │             │
     │         └──────┬───────┘        │             │
     │                │ (impact-analysis.json)       │
     ▼                                 ▼             ▼
┌─────────────────────────────────────────────────────────┐
│                  graphify (复用层)                        │
│      detect() · extract() · build() · cache()            │
│                    (graph.json)                           │
└─────────────────────────────────────────────────────────┘
                    ⋮  ⋮  ⋮  (数据流)
                    ┌──────────────┐
                    │  visualize   │  ← 独立输出：域级 HTML
                    └──────────────┘
```

---

## 四、模块设计

### 4.1 复用层（Reuse Layer）

**职责**：文件检测、AST 提取、图构建。完全复用 graphify 的现有能力。

**接口**：

```python
# graph_wiki/reuse.py

from graphify.detect import detect
from graphify.extract import extract, collect_files
from graphify.build import build_from_json, build_merge
from graphify.cache import check_semantic_cache, save_semantic_cache
import networkx as nx
from pathlib import Path

def detect_corpus(root: Path) -> dict:
    """文件检测 → {files: {code: [...], doc: [...]}, total_files, total_words}"""
    return detect(root)

def extract_ast(code_files: list[Path]) -> dict:
    """AST 提取 → {nodes: [...], edges: [...], input_tokens, output_tokens}"""
    files = []
    for f in code_files:
        files.extend(collect_files(f) if f.is_dir() else [f])
    return extract(files)

def build_graph(extraction: dict, directed: bool = False) -> nx.Graph:
    """构建 NetworkX 图"""
    return build_from_json(extraction)

def merge_graph(new_extraction: dict, graph_path: str, 
                prune_sources: list[str] | None = None) -> nx.Graph:
    """增量合并"""
    return build_merge([new_extraction], graph_path=graph_path, 
                        prune_sources=prune_sources)

def build_manifest(files: list[Path], domains: dict) -> dict:
    """生成文件哈希清单，供增量更新检测变更"""
    # 返回 {file_path: {hash, last_modified, domain}} 结构
    # 完整 schema 见 §6.4
    pass
```

### 4.2 聚类模块（Cluster）

**职责**：将代码文件按业务语义聚类为业务域。这是 Graph-Wiki 最核心的创新模块。

**文件名**：`graph_wiki/cluster.py`

**核心函数**：`business_cluster(G: nx.Graph, root_path: Path, language: Language = Language.AUTO, merge_threshold: float = 0.3, min_domain_size: int = 5) -> list[Domain]`

**输入**：
- `G`：NetworkX 图（来自 build_graph）
- `packages`：包路径树（从文件路径解析）
- `language`：项目语言（"java" | "javascript" | "auto"）

**输出**：
```python
{
    "采购管理": {
        "node_ids": ["ctrl_1", "svc_1", "mapper_1", ...],
        "packages": ["com.smc.smccloud.purchase", "com.smc.smccloud.order.purchase"],
        "modules": ["ops-delivery", "po-adapter"],
        "anchor_count": 45,  # Controller + Service + Mapper 数量
        "total_files": 120,
    },
    "Bin数据管理": { ... },
    ...
}
```

**聚类算法（五步法）**：

```
Step 1 — 过滤噪音（filter_noise）
─────────────────────────────────
输入: NetworkX Graph (34K 节点)
操作:
  1. 保留 file_type='code' 且 label 以 '.java' / '.vue' / '.js' 等结尾的节点
  2. 排除测试文件（/src/test/ 路径）
  3. 排除 JDK 基础类型（String, Integer, Long, Date...）
  4. 排除 getter/setter/toString 等方法节点
  5. 排除 Config/Util/NOISE 角色节点
输出: ~400-2,800 个业务节点（类文件级，视项目规模）

Step 2 — 提取锚点（extract_anchors）
─────────────────────────────────────
输入: 业务节点列表
操作: 筛选角色为 CONTROLLER / SERVICE_IMPL / SERVICE_API / MAPPER / DAO / ADAPTER 的节点
输出: ~100-1,200 个锚点（业务骨架节点）

Step 3 — 包路径聚类（cluster_by_package）
────────────────────────────────────────
输入: 锚点 + 包路径
操作:
  1. 解析每个文件的包路径
     例 (Java): com.smc.smccloud.bin.service.impl.BindataServiceImpl
         → 提取公司域名后的层级: ['smccloud', 'bin', 'service', 'impl']
     例 (JS):   api/binorder.js → 候选域 'binorder'
     例 (Vue):  views/csstock/apply/index.vue → 候选域 'csstock'
  
  2. 提取域键（extract_domain_key）：
     - Java: 公司域名（com/org/io）后第 1-2 级 → 'bin', 'order'
     - JS/TS: src/ 后第 1 级 → 'binorder', 'csstock'
     - Python/Go: 项目根后第 1 级 → 'bin'
  
  3. 按域键分组 → 形成候选域
     例: 'bin' 候选域包含 com.smc.smccloud.bin.* 下所有锚点

Step 4 — import 聚合调整（adjust_by_imports）
───────────────────────────────────────────
输入: 候选域 + import 关系图
操作:
  1. 应用预定义合并规则（DOMAIN_MERGE_RULES）：
     - 'bin' + 'bindata' + 'binorder' → 'bin-data'
     - 'order' + 'preorder' + 'bigorder' → 'order'
     - 'purchase' + 'po' + 'purchaseorder' → 'purchase'
  
  2. 计算候选域间的 import 密度矩阵
  3. 合并互相 import 密度 > merge_threshold 的候选域（贪心迭代）
  4. 将 < min_domain_size 的微型候选域合并到最近的域
  
  5. 基于实际 import 边（含 imports 和 imports_from 类型）计算域间依赖
     填充 Domain.dependencies

Step 5 — 业务点提取（extract_business_points）
──────────────────────────────────────────────
输入: 已划分的域 + 图 G（含 contains/calls 边）
操作:
  1. 通过 contains 边构建 method → class 映射
  2. 对每个域中 role 为 controller/service_impl 的锚点：
     a. 沿 contains 边提取 public 方法（排除 getter/setter/Vue 生命周期）
     b. 沿 calls 边分析跨域调用（方法 → 被调用方法 → 所属域）
     c. 生成 BusinessPoint（含 entry_method, entry_file, cross_domain_calls）
  3. 每个域产出 1-500 个业务点（视域大小）
输出: Domain 列表（含 business_points）
```

**关键设计决策**：

- **不使用方法级边**：仅使用 `imports`/`imports_from` 和 `calls` 中两端都是 `file_type='code'` 的边
- **噪音在 Step 1 就过滤**：方法调用、JDK 类型、Config/Util 等永远不进入聚类流程
- **anchor_role_map 模式**：Step 5 通过 domain.anchors 中的角色信息判断锚点类型，而非依赖 G.nodes 中的属性
- **合并阈值可配置**：`merge_threshold` 默认 0.3，`min_domain_size` 默认 5，可根据项目类型调整
- **边类型兼容**：import 检测同时支持 `imports` 和 `imports_from` 两种边类型
- **多语言方法标签**：Java 方法以 `.` 开头（`.fetchList()`），JS/Vue 方法以 `()` 结尾（`fetchList()`），两者均被识别

**多语言支持**：

```
extract_domain_key() 按语言类型实现不同策略：

  Java / Kotlin:
    包路径 → 提取公司域名后 1-2 级
    例: com.smc.smccloud.bin.service → "bin"
        org.springframework.web → "springframework" (非公司域名，取根包)
        io.grpc.protobuf → "grpc"
    
  JavaScript / TypeScript / Vue:
    文件目录 → 取 src/ 后 1-2 级
    例: src/api/binorder.js       → "binorder" (取 api 目录下的文件名)
        src/views/csstock/apply/  → "csstock"  (取 views 后第 1 级)
        src/components/ECharts/   → "echarts"  (取 components 后第 1 级)
    
  Python:
    import 路径 → 取项目根后 1-2 级
    例: from ops.bin.service import BindataService → "bin"
        from ops.common.utils import StringHelper  → "common"
    
  Go:
    module 路径 → 取域名后 1-2 级
    例: github.com/company/ops/internal/bin → "bin"
    
  自动检测 (language="auto"):
    1. 检测 pom.xml / build.gradle → Java
    2. 检测 package.json / tsconfig.json → JavaScript/TypeScript
    3. 检测 pyproject.toml / setup.py → Python
    4. 检测 go.mod → Go
    5. 都未检测到 → 回退到基于文件目录的通用策略
```

**伪代码**：

```python
def business_cluster(
    G: nx.Graph,
    root_path: Path,
    language: Language = Language.AUTO,
    merge_threshold: float = 0.3,
    min_domain_size: int = 5,
) -> list[Domain]:
    # Step 1: 过滤噪音
    business_nodes = filter_noise(G)
    # ~400-2,800 个业务节点（类文件级）

    # Step 2: 提取锚点
    anchors = extract_anchors(business_nodes)
    # ~100-1,200 个锚点（Controller/Service/Mapper）

    # Step 3: 包路径聚类（仅对锚点）
    candidates = cluster_by_package(anchors, language, root_path)
    # 按域键分组 → 候选域 dict

    # Step 4: import 聚合调整
    domains = adjust_by_imports(candidates, G, merge_threshold, min_domain_size)
    # 密度合并 + 预定义规则 + 小域吸收 → list[Domain]

    # Step 5: 业务点提取
    domains = extract_business_points(domains, anchors, G)
    # 每个域提取 Controller/Service public 方法 → Domain.business_points

    return sorted(domains, key=lambda d: -d.anchors_count())
```

### 4.3 标注模块（Label）

**职责**：用 LLM 为每个业务域生成人类可读的名称和描述。

**文件名**：`graph_wiki/label.py`

**核心函数**：`label_domains(domains: list[Domain], graph_data: dict, backend_root: Path, config: LabelConfig = LabelConfig()) -> list[Domain]`

**输入**：
- `domains`：聚类模块的输出
- `graph_data`：NetworkX 图（用于获取节点详情）
- `backend_root`：LLM 后端（"claude" | "openai" | "gemini"）

**输出**：
```python
{
    "采购管理": {
        "display_name": "采购管理",
        "description": "管理采购订单的创建、审批、执行和跟踪",
        "core_flows": [
            "采购申请提交 → 审批 → 生成采购订单",
            "采购发票导入 → 校验 → 结算",
        ],
        "key_classes": {
            "controllers": ["PurchaseController", "PurchaseOrderController"],
            "services": ["PurchaseService", "PurchaseOrderService"],
            "mappers": ["PurchaseOrderMapper"],
            "entities": ["PurchaseOrder", "PurchaseApply"],
        },
        "dependencies": [
            {"domain": "库存管理", "reason": "下单时调用库存预留"},
            {"domain": "供应商管理", "reason": "查询供应商信息"},
        ],
    },
    ...
}
```

**LLM 采样策略**：

```
对于每个域（~25 个），输入 LLM 的数据量：

  包路径前缀                          ~20 tokens
  核心类的类名列表（Controller/Service） ~200 tokens
  1-2 个核心 Service 的源码片段        ~2,000 tokens
  import 的其他域的类名                 ~100 tokens
  ─────────────────────────────────
  每域 ~2,500 tokens × 25 域 ≈ 62,500 tokens 输入
  每域输出 ~300 tokens × 25 域 ≈ 7,500 tokens 输出
  
  总成本: ~$0.3 (Claude Haiku) / ~$1 (Claude Sonnet)
```

**并行化策略**：

```
label_domains() 使用 ThreadPoolExecutor 实现 5 路并发标注：

  25 个域 / 5 并发路 = 5 轮
  每轮 ~6 秒（LLM API 调用耗时）
  总耗时 ≈ 5 轮 × 6s = ~30s

  特点：
  · 每个域独立调用 LLM，互不干扰
  · 单个域失败不影响其他域（失败域保持未标注状态）
  · 受 API rate limit 限制，parallel_calls 默认 5（可在 LabelConfig 中调整）
  · Claude Haiku 的 RPM（requests per minute）上限 ~50，5 并发安全
```

**Prompt 模板**：

```
你是一个代码库分析专家。下面是一个 Java 项目的业务模块信息。

包路径前缀: {package_prefix}
核心类:
{core_classes}

请根据类名和包路径推断，回答以下问题：
1. 这个业务域的名称（2-5 个汉字）
2. 一句话描述它的业务目标
3. 列出 3-5 个核心业务流程
4. 它可能依赖哪些其他业务域？（根据类名中的 import 推断）

输出 JSON 格式。
```

**关键设计决策**：

- LLM 标注是**一次性操作**，不是每次构建都运行
- 域名称和描述人工 review 后锁定，后续 `--update` 只更新 code-map
- LLM 不需要读取全部源码，**类名 + 包名已经包含足够的语义信息**

### 4.4 Wiki 导出模块（Export）

**职责**：将业务域数据导出为 Obsidian 兼容的双向链接 Markdown 文档库。

**文件名**：`graph_wiki/export.py`

**核心函数**：`export_wiki(domains: list[Domain], api_matches: list[ApiMatch], field_map: dict, output_dir: Path = Path("wiki")) -> Path`
- `domains`: Domain 列表（含 LLM 标注的名称和描述）
- `api_matches`: api_mapper 产出的前后端匹配列表
- `field_map`: field_mapper 产出的字段映射（domain → table → column）

**输出目录结构**：

```
{output_dir}/wiki/
├── index.md                          ← 业务域总目录
├── 采购管理/
│   ├── summary.md                    ← 业务摘要（LLM 生成）
│   ├── rules.md                      ← 业务规则（占位，人工填写）
│   ├── spec.md                       ← 需求规格（占位，人工填写）
│   ├── code-map.md                   ← 代码清单（自动生成）
│   └── dependencies.md               ← 域间依赖（自动生成）
├── Bin数据管理/
│   ├── summary.md
│   ├── ...
├── 订单管理/
├── 库存管理/
└── ...（~25 个域）
```

**各文件格式规范**：

**index.md**：
```markdown
# {项目名称} 业务域目录

> 自动生成于 {日期} | {N} 个业务域 | {M} 个核心类

## 业务域列表

| 业务域 | 核心类 | 依赖域 |
|--------|--------|--------|
| [[采购管理]] | 45 | [[库存管理]], [[供应商管理]] |
| [[Bin数据管理]] | 32 | [[产品数据管理]], [[库存管理]] |
| ... | | |

## 域间依赖图

![[domain_graph.png]]

## 未分类文件

以下文件未被归入任何业务域：
- ...
```

**summary.md**（LLM 生成 + 人工定稿）：
```markdown
# {业务域名称}

## 业务目标
{LLM 生成的一句话描述}

## 核心流程
1. **{流程名}** — {简述}
2. ...

## 关键业务规则
> 详见 [[rules]]

## 代码入口

| 层级 | 文件 | 说明 |
|------|------|------|
| Controller | [XxxController.java](path) | {LLM 推断的职责} |
| Service | [XxxService.java](path) | |
| Mapper | [XxxMapper.java](path) | |

## 关联域
| 域 | 关系 | 说明 |
|----|------|------|
| [[库存管理]] | 调用 | 下单时预留库存 |
| [[供应商管理]] | 被调用 | 供应商变更通知采购域 |
```

**code-map.md**（全自动）：
```markdown
# {业务域名称} — 代码地图

## Controller 层
- [PurchaseController.java](../../ops-backend/ops-delivery/src/main/java/com/smc/smccloud/purchase/controller/PurchaseController.java)
- ...

## Service 层
- [PurchaseService.java](...)
- ...

## Mapper 层
- [PurchaseOrderMapper.java](...)
- ...

## Entity / DTO
- [PurchaseOrder.java](...)
- ...

## 配置文件
- [application-purchase.yml](...)
```

**关键设计决策**：

- **Obsidian 双向链接语法**：`[[采购管理]]` 实现域间跳转，`[文件名](相对路径)` 实现代码跳转
- **rules.md 和 spec.md 初始为空占位**，由人工填写——这是业务权威内容，LLM 不应越俎代庖
- **每次 `--update` 只刷新 code-map.md**，不覆盖人工填写的 rules.md 和 spec.md
- **summary.md 有保护机制**：如果人工修改过（检测到文件被编辑），`--update` 不会覆盖

### 4.5 可视化模块（Visualize）

**职责**：生成业务域级别的交互式依赖图。

**文件名**：`graph_wiki/visualize.py`

**核心函数**：`export_domain_html(domains: list[Domain], output_path: Path = Path("domain_graph.html")) -> Path`

**设计**：

- 使用 D3.js 力导向图
- 每个节点 = 一个业务域（~20-30 个）
- 节点大小 = 域内锚点数量
- 连线粗细 = 域间 import 次数
- 颜色按依赖强度分组
- 点击节点 → 展开域内 Controller → Service → Mapper 链
- 支持搜索过滤（输入 "bin" → 高亮 Bin 数据管理域）

**与 Graphify graph.html 的根本区别**：

| | Graphify 可视化 | Graph-Wiki 可视化 |
|------|---------------|-----------------|
| 节点数 | 34,000 | ~25 |
| 节点含义 | 方法/类（无业务语义） | 业务域（有名称和描述） |
| 交互 | 拖拽/缩放 | 拖拽/缩放 + 搜索 + 展开下钻 |
| 人类可用 | ❌ | ✅ |

### 4.6 API 映射模块（ApiMapper）

**职责**：构建前后端接口对接的完整链路。这是连接前端和后端的关键桥梁。

**文件名**：`graph_wiki/api_mapper.py`

**核心问题**：当前设计覆盖了后端内部链路（Controller → Service → Mapper → DB），但缺少前端到后端的对接。用户无法回答以下问题：

- "这个前端页面的这个字段，对应后端哪个接口的哪个参数？"
- "这个后端接口被哪些前端页面调用？"
- "整个项目有哪些 API？完整的接口文档在哪？"

**解决的完整链路**：

```
前端 Vue 页面          前端 API 模块          后端 Controller        后端 Service/Mapper     数据库
────────────────    ────────────────      ──────────────────     ───────────────────     ──────
csstock/apply/      binorder.js           BinOrderController     BinOrderCalcService     bin_order_
index.vue           .listBinOrderDetail() .listBinOrderDetail()  .listBinOrderDetail()   detail
  │                   │                     │                      │                       │
  │ import            │ axios.post(         │ @PostMapping(         │ 参数: BinOrderDTO     │ @TableField
  │ binorder.js       │ '/api/binorder/     │ '/api/binorder/      │                      │ ("supplier_code")
  │                   │  detail',           │  detail')            │                      │
  │                   │  {supplierCode,     │ 入参: BinOrderDTO    │                      │
  │                   │   modelNo}          │                      │                      │
  │                   │                     │                      │                      │
  ├─ 字段: supplierCode ──→ 请求体字段 ────→ DTO 字段 ──────────→ Entity 字段 ──────────→ DB 列
  └─ 字段: modelNo ──────→ 请求体字段 ────→ DTO 字段 ──────────→ Entity 字段 ──────────→ DB 列
```

**核心函数**：

```python
def build_api_map(
    frontend_api_dir: Path,
    frontend_views_dir: Path,
    backend_src_dir: Path,
    domains: list[Domain],
    backend_root: Path,
) -> list[ApiMatch]:
    """
    构建前后端 API 映射完整链路。

    输入:
      - frontend_api_dir: 前端 src/api/ 目录（含 JS/TS API 定义）
      - frontend_views_dir: 前端 src/views/ 目录（含 Vue 页面）
      - backend_src_dir: 后端 Controller 源码目录
      - domains: 聚类模块产出的业务域列表（用于匹配域归属）
      - backend_root: 后端项目根目录

    输出:
      - list[ApiMatch]: 前后端匹配列表，每项包含前端 API 信息、
        后端 Controller 信息、URL/方法匹配、参数映射、域归属
    """
    # 1. 解析前端 API 文件
    frontend_apis = parse_frontend_apis(frontend_api_dir)
    # 2. 追踪前端调用者
    callers = trace_frontend_callers(frontend_api_dir, frontend_views_dir)
    # 3. 解析后端 Controller
    backend_controllers = parse_backend_controllers(backend_src_dir)
    # 4. URL 路径匹配
    return match_apis(frontend_apis, backend_controllers, callers, domains)
```

**三个子功能**：

**功能 1：前端 API 解析**

```python
def parse_frontend_apis(api_dir: Path) -> dict:
    """
    解析前端 src/api/*.js 文件
    
    输入: ops-frontend/src/api/
    输出: {
        "binorder.js": {
            "functions": [
                {
                    "name": "listBinOrderDetail",
                    "method": "POST",
                    "url": "/api/binorder/detail",
                    "params": ["supplierCode", "modelNo", "startDate", "endDate"],
                    "source_file": "ops-frontend/src/api/binorder.js",
                    "source_line": 15,
                },
                ...
            ],
            "imported_by": ["csstock/apply/index.vue", "csstock/query/index.vue"]
        }
    }
    """
```

实现方式：
- 用 tree-sitter JavaScript parser 解析 `src/api/*.js`
- 提取 `export function xxx(data) { return axios.post(url, data) }` 模式
- 从函数签名提取参数名
- 零 Token 成本

**功能 2：URL 路径匹配**

```python
def match_apis(frontend_apis: dict, backend_controllers: dict) -> list:
    """
    将前端 API 调用匹配到后端 Controller
    
    匹配规则:
    1. HTTP 方法相同 (POST/GET/PUT/DELETE)
    2. URL 路径匹配: /api/binorder/detail → @PostMapping("/api/binorder/detail")
    3. 参数名模糊匹配: supplierCode → BindataQueryDTO.supplierCode
    
    输出: [
        {
            "frontend": {
                "api_module": "binorder.js",
                "function": "listBinOrderDetail",
                "method": "POST",
                "url": "/api/binorder/detail",
                "callers": ["csstock/apply/index.vue", "csstock/query/index.vue"]
            },
            "backend": {
                "controller": "BinOrderController.java",
                "method": "listBinOrderDetail()",
                "mapping": "@PostMapping(\"/api/binorder/detail\")",
                "param_type": "BinOrderQueryDTO",
                "service": "BinOrderCalcService.listBinOrderDetail()",
                "mapper": "BinOrderMapper.selectDetail()",
            },
            "match_confidence": 0.98  # URL 完全匹配
        }
    ]
    """
```

零 Token 成本，纯字符串匹配 + 注解解析。

**功能 3：前端调用者追踪**

```python
def trace_frontend_callers(api_dir: Path, views_dir: Path) -> dict:
    """
    追踪每个 API 函数被哪些 Vue 页面调用
    
    方法:
    1. 扫描 src/views/**/*.vue
    2. 解析 import { listBinOrderDetail } from '@/api/binorder'
    3. 追踪 this.listBinOrderDetail() 调用
    
    输出: {
        "binorder.js.listBinOrderDetail": {
            "callers": [
                {"page": "csstock/apply/index.vue", "component": "BinDataImport"},
                {"page": "csstock/query/index.vue", "component": "BinDataQuery"}
            ]
        }
    }
    """
```

零 Token 成本（tree-sitter Vue/JS parser + 静态 import 分析）。

**输出文件**：`api-map.json`

```json
{
  "apis": [
    {
      "id": "api-binorder-list-detail",
      "url": "POST /api/binorder/detail",
      "description": "查询 Bin 订单明细",
      
      "frontend": {
        "api_module": "binorder.js",
        "function": "listBinOrderDetail",
        "params": ["supplierCode", "modelNo", "startDate", "endDate"],
        "callers": [
          {"page": "csstock/apply/index.vue", "fields_used": ["supplierCode", "modelNo"]},
          {"page": "csstock/query/index.vue", "fields_used": ["supplierCode", "modelNo", "startDate", "endDate"]}
        ]
      },
      
      "backend": {
        "controller": "BinOrderController.java",
        "method": "listBinOrderDetail()",
        "param_type": "BinOrderQueryDTO",
        "param_fields": [
          {"name": "supplierCode", "type": "String", "db_column": "bin_order_detail.supplier_code"},
          {"name": "modelNo", "type": "String", "db_column": "bin_order_detail.model_no"},
          {"name": "startDate", "type": "Date", "db_column": null},
          {"name": "endDate", "type": "Date", "db_column": null}
        ],
        "service_chain": [
          "BinOrderCalcServiceImpl.listBinOrderDetail()",
          "→ BindataMapper.selectByCondition()"
        ]
      },
      
      "domain": "Bin数据管理"
    }
  ],
  
  "unmatched": [
    {"frontend": "notice.js.queryNotices", "url": "GET /api/notices", "reason": "后端无匹配 Controller"},
    {"backend": "HealthController.health()", "mapping": "@GetMapping(\"/health\")", "reason": "前端无调用"}
  ]
}
```

**在 Wiki 中的呈现方式**：

**1. `wiki/api-index.md` — 全部 API 索引**

```markdown
# API 接口索引

> 共 {N} 个接口 | {M} 个已匹配前后端 | {U} 个未匹配

## 按业务域浏览

### [[采购管理]]
| 方法 | URL | 前端调用者 | 后端 Controller |
|------|-----|-----------|----------------|
| POST | /api/purchase/apply | purchase/apply.vue | PurchaseController.submitApply() |
| POST | /api/purchase/order | purchase/order.vue | PurchaseOrderController.createOrder() |

### [[Bin数据管理]]
| 方法 | URL | 前端调用者 | 后端 Controller |
|------|-----|-----------|----------------|
| POST | /api/binorder/detail | csstock/apply.vue, csstock/query.vue | BinOrderController.listBinOrderDetail() |
| POST | /api/bindata/import | csstock/apply.vue | BindataController.importBindata() |
```

**2. `wiki/{domain}/api-docs.md` — 单域 API 文档**

```markdown
# Bin数据管理 — API 文档

## POST /api/binorder/detail
查询 Bin 订单明细

### 前端调用者
- [csstock/apply/index.vue](../../ops-frontend/src/views/csstock/apply/index.vue)
- [csstock/query/index.vue](../../ops-frontend/src/views/csstock/query/index.vue)

### 请求参数
| 参数 | 类型 | 必填 | 说明 | 对应数据库字段 |
|------|------|------|------|-------------|
| supplierCode | String | 是 | 供应商编码 | bin_order_detail.supplier_code |
| modelNo | String | 是 | 车型编号 | bin_order_detail.model_no |
| startDate | Date | 否 | 开始日期 | — |
| endDate | Date | 否 | 结束日期 | — |

### 后端处理链
1. `BinOrderController.listBinOrderDetail()` → 接收请求
2. `BinOrderCalcServiceImpl.listBinOrderDetail()` → 业务处理
3. `BindataMapper.selectByCondition()` → 数据库查询
   - 查询表: `bin_order_detail`

### 返回数据
`List<BinOrderDTO>` → 前端表格展示
```

**3. `wiki/{domain}/data-flow.md` — 域内数据流**

```markdown
# Bin数据管理 — 数据流

## Bin 数据导入
[csstock/apply/index.vue] --POST /api/bindata/import--> [BindataController.importBindata()]
  │
  ├─ supplierCode (Excel列31) ──→ BindataApplyDTO.supplierCode
  │                                └─→ BindataApply.supplierCode
  │                                     └─→ bin_data_apply.supplier_code
  │
  ├─ modelNo (Excel列5) ───────→ BindataApplyDTO.modelNo
  │                                └─→ BindataApply.modelNo
  │                                     └─→ bin_data_apply.model_no
  │
  └─ qty (Excel列10) ──────────→ BindataApplyDTO.qty
                                   └─→ BindataApply.qty
                                        └─→ bin_data_apply.qty
```

**Agent 查询示例**：

```
用户："csstock/apply 页面的 supplierCode 字段对应数据库哪个字段？"

Agent 读取 api-map.json
  → 找到 csstock/apply/index.vue → binorder.js → POST /api/binorder/detail
  → 参数 supplierCode → BinOrderQueryDTO.supplierCode
  → @TableField("supplier_code") → bin_order_detail.supplier_code
  → 返回完整链路

用户："哪些前端页面会触发库存预留操作？"

Agent 搜索 api-map.json 中 backend.service_chain 包含 "InventoryService" 的 API
  → 返回调用者列表
```

**Token 成本**：零（全静态解析，tree-sitter + 字符串匹配 + 注解提取）

**增量更新策略**：

- 前端 `src/api/` 目录中单个 JS 文件变更 → 重解析该文件的 export 函数和 axios 调用
- 前端 `src/views/` 目录中 Vue 文件变更 → 重跑 `trace_frontend_callers()` 更新调用者信息
- 后端 Controller Java 文件变更 → 重解析该文件的 `@XxxMapping` 注解
- 依赖 `manifest.json` 的文件哈希对比检测变更
- api-map.json 按域拆分 → 单域 API 变更不影响其他域的文档

### 4.7 字段映射模块（FieldMapper）

**职责**：构建字段级别的完整数据流链路：从数据库列到前端 UI 字段的逐层追踪。

**文件名**：`graph_wiki/field_mapper.py`

**解决的问题**：

```
需求场景：
  "前端的 supplierCode 字段对应数据库哪个表的哪个列？"
  "BinDataApply 这个实体的所有字段分别来自哪里？"
  "哪些前端字段会最终写入 bin_data_apply 表？"

当前 api_mapper 覆盖了：前端 API 模块 → 后端 Controller 参数
缺失的部分：       Controller 参数 → DTO 字段 → Entity 字段 → 数据库列
```

**完整六层链路**：

```
前端 Vue 组件          前端 API 模块          后端 Controller          后端 DTO              后端 Entity            数据库
─────────────────    ──────────────────    ─────────────────────    ──────────────────    ──────────────────    ────────
csstock/apply/        binorder.js           BinOrderController       BinOrderQueryDTO       BindataApply          bin_data_
index.vue             .listBinOrderDetail() .listBinOrderDetail()    .supplierCode          .supplierCode          apply
  │                     │                     │                       │                     │                     │
  │ :supplierCode       │ params: {           │ @RequestParam          │ private String       │ @TableField(         │ supplier_
  │                     │   supplierCode      │ BinOrderQueryDTO       │ supplierCode;        │   "supplier_code")   │ code
  │                     │ }                   │                       │                     │ private String       │ VARCHAR(50)
  │                     │                     │                       │                     │ supplierCode;        │
  │                     │                     │                       │                     │                     │
  └──→ api_mapper ──→ api_mapper ────→ api_mapper ──────┘         └──→ field_mapper ──→ field_mapper ──→ field_mapper
      (覆盖)          (覆盖)          (覆盖)                            (新增覆盖)         (新增覆盖)         (新增覆盖)
```

**核心函数**：

```python
def build_field_map(
    api_matches: list[ApiMatch],
    entity_dir: Path,
    dto_dir: Path,
    backend_root: Path,
) -> dict:
    """
    构建字段级完整数据流链路。

    输入:
      - api_matches: api_mapper 产出的 ApiMatch 列表
      - entity_dir: 后端 Entity/domain 源码目录（含 @TableField 注解）
      - dto_dir: 后端 DTO 源码目录
      - backend_root: 后端项目根目录

    输出:
      - dict: 按域→表→列聚合的字段映射字典，每项含完整六层链路
             （Vue 组件 → API 函数 → Controller → DTO → Entity → DB 列）

    实现步骤:
      1. extract_entity_db_mapping(entity_dir) → 解析 Entity → 数据库列映射
      2. match_dto_to_entity(dto_fields, entity_map) → DTO ↔ Entity 字段匹配
      3. 串联 api_matches 与 entity_map，生成完整六层链路
      4. 按域拆分输出 → field-map.json
    """
    entity_map = extract_entity_db_mapping(entity_dir)
    dto_fields = extract_dto_fields(api_matches)
    field_matches = match_dto_to_entity(dto_fields, entity_map)
    return build_data_flow_chain(api_matches, entity_map, field_matches)
```

**两个子功能**：

**功能 1：Entity → 数据库列映射（确定性，零 Token）**

```python
def extract_entity_db_mapping(entity_dir: Path) -> dict:
    """
    解析 @TableField / @Column 注解
    
    输入: ops-backend/*/src/main/java/**/entity/*.java
          ops-backend/*/src/main/java/**/domain/*.java
    输出: {
        "BindataApply": {
            "table": "bin_data_apply",
            "fields": [
                {"java_field": "supplierCode", "db_column": "supplier_code",
                 "annotation": "@TableField(\"supplier_code\")", "type": "String"},
                {"java_field": "id", "db_column": "id",
                 "annotation": "@TableId", "type": "Long"},
            ]
        }
    }
    """
```

支持注解：MyBatis-Plus `@TableField`、`@TableId`、`@TableName`、JPA `@Column`、`@Table`

**功能 2：DTO → Entity 字段匹配（启发式，零 Token）**

```python
def match_dto_to_entity(dto_fields: list[str], entity_map: dict) -> dict:
    """
    匹配规则（按优先级）：
    1. 完全同名 → 直接匹配（置信度 1.0）
    2. 去掉前缀后同名 (dto.supplierCode ≈ entity.supplierCode) → 模糊匹配（0.9）
    3. 驼峰转下划线后同名 (supplierCode ≈ supplier_code) → 模糊匹配（0.9）
    4. 部分包含 (dto.address ≈ entity.deliveryAddress) → 低置信匹配（0.5）
    
    输出: {
        "dto_field": "supplierCode",
        "entity_field": "supplierCode", 
        "db_column": "supplier_code",
        "confidence": 1.0,
        "match_type": "exact"
    }
    """
```

**输出文件**：`field-map.json`（按域拆分）

```json
{
  "bin-data": {
    "bin_data_apply": {
      "columns": {
        "supplier_code": [
          {
            "api_url": "POST /api/bindata/import",
            "api_function": "importBindata",
            "dto_field": "supplierCode",
            "entity_class": "BindataApply",
            "entity_field": "supplierCode",
            "db_column": "supplier_code",
            "db_table": "bin_data_apply",
            "callers": ["csstock/apply/index.vue"],
            "confidence": 1.0
          },
          {
            "api_url": "POST /api/binorder/detail",
            "api_function": "listBinOrderDetail",
            "dto_field": "supplierCode",
            "entity_field": "supplierCode",
            "callers": ["csstock/query/index.vue"]
          }
        ]
      }
    }
  }
}
```

Schema 结构：`domain_key → table_name → columns → 来源列表`。每条来源记录六层链路中的关键节点。完整 Schema 定义见 [§6.3](#63-field-mapjson-规范)。

**Agent 查询示例**：

```
用户："supplierCode 对应数据库哪个字段？"

Agent 读取 field-map.json (Bin数据管理, ~300 tokens)
  → supplierCode → bin_data_apply.supplier_code (VARCHAR(50))
  → 被 2 个前端页面使用：csstock/apply, csstock/query
  → 通过 2 个 API 传入：POST /api/bindata/import, POST /api/binorder/detail
  → 取值规则：Excel列31 (优先) / mainSupplierCode (默认)
```

**增量更新策略**：

- Entity 文件变更 → 重新解析该文件，更新 entity_map
- DTO 文件变更 → 重新运行 match_dto_to_entity
- 新增 API → 由 api_mapper 增量更新，field_mapper 仅追加映射
- 依赖 manifest.json 检测变更（与 code-map 共用同一增量机制）

**Token 成本**：零（全注解解析 + 字符串匹配 + 字段名相似度算法）

### 4.8 流水线模块（Pipeline）

**职责**：整合所有模块，提供 CLI 入口。

**文件名**：`graph_wiki/pipeline.py`

**CLI 命令**：

```bash
# 完整构建
graph-wiki build <path>                    # 对指定目录运行完整流水线
graph-wiki build . --name "OPS"            # 指定项目名称
graph-wiki build . --language java         # 指定语言
graph-wiki build . --llm-backend claude    # 指定 LLM 后端
graph-wiki build . --no-llm                # 跳过 LLM 标注（仅聚类 + code-map）

# 增量更新
graph-wiki update <path>                   # 仅重提取变更文件，复用域划分
graph-wiki update <path> --recluster       # 强制重新聚类（包路径重命名时使用）

# 仅聚类（不重新提取）
graph-wiki cluster-only <path>             # 在现有提取结果上重新聚类

# 仅 LLM 标注（不重新提取和聚类）
graph-wiki label-only <path>               # 在现有域划分上重新生成描述

# 查询（复用 graphify query）
graph-wiki query "<question>"              # BFS 遍历
graph-wiki path "<A>" "<B>"                # 最短路径
graph-wiki explain "<concept>"             # 节点解释
```

**完整流水线步骤**：

```python
def build(root_path, name=None, language="auto", 
          llm_backend="claude", llm=True):
    """
    Graph-Wiki 完整流水线
    
    1. detect_corpus()     ← 复用 graphify
    2. extract_ast()       ← 复用 graphify  
    3. build_graph()       ← 复用 graphify
    4. business_cluster()  ← Graph-Wiki 核心
    5. build_api_map()     ← 前后端接口对接（可选，需前端 api/ 目录）
    6. build_field_map()   ← 字段级数据流追踪（可选，需 Entity/DTO 源码）
    7. label_domains()         ← Graph-Wiki 核心 (if llm=True)
    8. export_wiki()       ← Graph-Wiki 核心
    9. export_domain_html()← Graph-Wiki 核心
    """
```

**`--update` 的性能优势**：

```
Graphify --update (改 1 个文件):
  AST 重提取       ~2s
  build_merge      ~60s
  Louvain 重聚类   ~3min   ← 不可跳过的瓶颈
  导出             ~2min
  ──────────────
  总计             ~6min

Graph-Wiki --update (改 1 个文件):
  AST 重提取       ~2s
  build_merge      ~60s
  域划分（复用）     ~0s    ← 域边界稳定，无需重聚
  LLM 标注（复用）   ~0s    ← 域名称不变
  刷新 code-map     ~5s    ← 只更新变更域的文件列表
  导出              ~10s   ← 小图（~25 节点）
  ──────────────
  总计             ~80s   ← 快 4-5 倍
```

**关键设计决策**：

- `--update` 的默认行为是**不重新聚类、不重新标注**，只刷新 code-map
- 如果新增了包路径（新模块），提供 `--recluster` 强制重新聚类
- 域划分结果保存在 `domains.json`，作为后续 `--update` 的基线

---

## 五、数据流

> **本章为概要级描述。详细数据流（含每层输入输出、Token 成本、graph.json 结构）请参见 [第八章](#八数据流详解graphjson-的角色定位)。**

### 5.1 完整构建流程

```
源代码 (.java / .vue / .js / .xml)
        │
        ▼
┌──────────────────────┐
│ 1. detect_corpus()   │  复用 graphify.detect
│    文件分类统计        │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ 2. extract_ast()     │  复用 graphify.extract
│    tree-sitter 解析   │  12 workers 并行
│    → ast_nodes, ast_edges
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ 3. filter_extraction │  Graph-Wiki 预过滤
│    _for_wiki()       │  build 前剔除噪音节点/边
│    → filtered AST    │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│ 4. build_light_graph │  构建轻量 NetworkX Graph
│    → graph-lite.json │  v1.0 默认聚类输入
└──────────┬───────────┘
           │
           ▼
┌──────────────────────────────────────┐
│ 5. business_cluster()               │  ★ Graph-Wiki 核心
│                                      │
│  Step 1: 二次过滤噪音                  │
│    graph-lite → 业务节点               │
│                                      │
│  Step 2: 提取锚点                     │
│    ~2,800 → ~1,200 锚点               │
│    (Controller/Service/Mapper)       │
│                                      │
│  Step 3: 包前缀聚类                   │
│    com.smc.smccloud.bin.* → 'bin'   │
│    com.smc.smccloud.order.* → 'order'│
│                                      │
│  Step 4: import 聚合调整              │
│    合并 import 密集的候选域            │
│    拆分 import 稀疏的候选域            │
│                                      │
│  Step 5: 业务点提取                   │
│    提取 Controller/Service public    │
│    方法 → ~500-800 业务点             │
│                                      │
│  → domains: list[Domain]             │
└──────────┬───────────────────────────┘
           │
           ▼
┌──────────────────────────────────────┐
│ 6. build_api_map()（可选）            │
│                                      │
│  解析前端 API 文件 + 后端 Controller   │
│  URL 路径匹配                         │
│                                      │
│  → api-map.json                      │
└──────────┬───────────────────────────┘
           │
           ▼
┌──────────────────────────────────────┐
│ 7. build_field_map()（可选）           │
│                                      │
│  Entity 注解解析 → 数据库列映射         │
│  DTO ↔ Entity 字段匹配               │
│                                      │
│  → field-map.json                    │
└──────────┬───────────────────────────┘
           │
           ▼
┌──────────────────────────────────────┐
│ 7. label_domains()                      │  ★ Graph-Wiki 核心
│                                      │
│  采样每个域的 Controller/Service 类名  │
│  → LLM 生成:                         │
│    - 域名称（2-5 字）                 │
│    - 业务目标描述                     │
│    - 核心流程                         │
│    - 域间依赖推断                     │
│                                      │
│  → domains.json（含业务语义）         │
└──────────┬───────────────────────────┘
           │
           ▼
┌──────────────────────────────────────┐
│ 8. export_wiki()                    │  ★ Graph-Wiki 核心
│                                      │
│  生成:                                │
│  wiki/index.md                       │
│  wiki/{domain}/summary.md            │
│  wiki/{domain}/code-map.md           │
│  wiki/{domain}/rules.md (占位)       │
│  wiki/{domain}/spec.md (占位)        │
│  wiki/{domain}/dependencies.md       │
└──────────┬───────────────────────────┘
           │
           ▼
┌──────────────────────────────────────┐
│ 9. export_domain_html()             │  ★ Graph-Wiki 核心
│                                      │
│  domain_graph.html (~25 节点)        │
│  可下钻到域内结构                      │
└──────────────────────────────────────┘
```

### 5.2 增量更新流程

```
代码变更
    │
    ▼
detect_incremental()  ← 对比 manifest.json（§6.4），找出变更文件（复用层 §4.1）
    │
    ├── 无变更 → 跳过
    │
    ├── 前端 api/ 目录变更 → 增量重建 api_mapper
    ├── 后端 Entity/DTO 变更 → 增量重建 field_mapper
    │
    ├── 仅代码文件变更
    │       │
    │       ▼
    │   extract_ast(changed_files)  ← 只解析变更文件
    │       │
    │       ▼
    │   build_merge()  ← 合并到现有图
    │       │
    │       ▼
    │   可选: business_cluster()  ← 仅当 --recluster
    │       │
    │       ▼
    │   刷新 code-map.md  ← 更新变更域的文件列表
    │       │
    │       ▼
    │   重新导出 HTML（仅当域依赖变化）
    │
    └── 包含新模块/目录
            │
            ▼
        提示: "检测到新的包路径，建议运行 --recluster"
```

---

## 六、输出规范

### 6.1 文件清单

| 文件 | 格式 | 生成方式 | 用途 |
|------|------|---------|------|
| `graph-lite.json` | NetworkX node-link JSON | 自动（reuse 预过滤 + 轻量图构建） | v1.0 默认聚类、Wiki、Agent 范围缩小 |
| `graph.json` | NetworkX node-link JSON | 可选（完整 graphify build） | 精确方法级下钻和 graphify query 后端 |
| `domains.json` | 自定义 JSON | 自动（clustering + LLM） | 业务域数据 |
| `api-map.json` | 自定义 JSON | 自动（api_mapper） | 前后端 API 映射（可按域拆分） |
| `field-map.json` | 自定义 JSON | 自动（field_mapper） | 字段级数据流追踪 |
| `impact-analysis.json` | 自定义 JSON | 自动（impact） | 字段/API/业务点/规则/域依赖的结构化影响分析 |
| `dream-cycle-report.json` | 自定义 JSON | 自动（dream） | Phase 5 维护循环报告：变更、保护、回链、重复候选、质量状态 |
| `manifest.json` | 文件哈希表 | 自动（reuse → build_manifest） | 增量更新 |
| `build-report.json` | 自定义 JSON | 自动（pipeline 质量评估） | 构建结果、规模指标、Wiki 质量验收 |
| `wiki/index.md` | Markdown | 自动 | 业务域总目录 |
| `wiki/impact-analysis.md` | Markdown | 自动（impact → export） | 面向人类的影响分析入口和查询样例 |
| `wiki/changelog.md` | Markdown | 自动（dream） | 面向人类的知识库维护摘要 |
| `wiki/{domain}/summary.md` | Markdown | LLM 初稿 + 人工定稿 | 业务摘要 |
| `wiki/{domain}/code-map.md` | Markdown | 自动 | 代码文件清单 |
| `wiki/{domain}/rules.md` | Markdown | 人工填写 | 业务规则 |
| `wiki/{domain}/spec.md` | Markdown | 人工填写 | 需求规格 |
| `wiki/{domain}/dependencies.md` | Markdown | 自动 | 域间依赖 |
| `wiki/{domain}/api-docs.md` | Markdown | 自动（api_mapper → export） | 单域 API 文档 |
| `wiki/{domain}/data-flow.md` | Markdown | 自动（field_mapper → export） | 字段级数据流 |
| `wiki/{domain}/er-diagram.md` | Markdown | 自动（field_mapper → export） | Mermaid ER 图（Phase 4+） |
| `domain_graph.html` | HTML | 自动（D3.js） | 域级交互可视化 |

### 6.1.1 v1.0 必须产物与可选产物

v1.0 的默认 `graph-wiki build` 必须生成以下产物：

```
wiki/
domain_graph.html
manifest.json
graph-lite.json
domains.json
api-map.json
field-map.json
impact-analysis.json
dream-cycle-report.json
build-report.json
```

完整 `graph.json` 不再是大项目默认必需产物。它作为可选完整后端存在，用于精确方法级查询、`graphify query/path/explain` 委托、或人工调试。对于 500+ 文件项目，默认链路必须能够只依赖 `graph-lite.json` 完成聚类和 Wiki 生成。

### 6.2 domains.json 规范

```json
{
  "meta": {
    "project": "OPS",
    "generated": "2026-06-11T10:00:00Z",
    "language": "java",
    "total_domains": 25,
    "total_anchors": 1215,
    "total_files": 5200
  },
  "domains": [
    {
      "id": "purchase",
      "name": "采购管理",
      "description": "管理采购订单的创建、审批、执行和跟踪",
      "packages": [
        "com.smc.smccloud.purchase",
        "com.smc.smccloud.order.purchase"
      ],
      "modules": ["ops-delivery", "po-adapter"],
      "anchors": {
        "controllers": [
          {"file": "ops-delivery/.../PurchaseController.java", "label": "PurchaseController.java"},
          {"file": "ops-delivery/.../PurchaseOrderController.java", "label": "PurchaseOrderController.java"}
        ],
        "services": [
          {"file": "opsserviceImpl/.../PurchaseServiceImpl.java", "label": "PurchaseServiceImpl.java"}
        ],
        "mappers": [
          {"file": "smccloud-ops/.../PurchaseOrderMapper.java", "label": "PurchaseOrderMapper.java"}
        ]
      },
      "total_files": 120,
      "dependencies": [
        {"domain": "inventory", "name": "库存管理", "import_count": 45, "strength": "strong"},
        {"domain": "supplier", "name": "供应商管理", "import_count": 12, "strength": "medium"}
      ]
    }
  ],
  "uncategorized": {
    "files": ["ops-common/.../StringUtils.java"],
    "reason": "工具类，不属于任何业务域"
  }
}
```

### 6.3 field-map.json 规范

字段级数据流追踪结果，按"域 → 数据库表 → 列 → 来源链路"四层聚合：

```json
{
  "bin-data": {
    "bin_data_apply": {
      "columns": {
        "supplier_code": [
          {
            "api_url": "POST /api/bindata/import",
            "api_function": "importBindata",
            "dto_field": "supplierCode",
            "entity_class": "BindataApply",
            "entity_field": "supplierCode",
            "db_column": "supplier_code",
            "db_table": "bin_data_apply",
            "callers": ["csstock/apply/index.vue"],
            "confidence": 1.0
          }
        ]
      }
    }
  }
}
```

字段说明：
- `domain_key` → `table_name` → `column_name` → 来源列表
- 每个来源记录六层链路：Vue 组件 → API 函数 → Controller → DTO → Entity → 数据库列
- `confidence`：DTO 字段 ↔ Entity 字段匹配置信度（0-1）

### 6.4 manifest.json 规范

增量更新的文件变更追踪清单，存储每个源码文件的哈希值和所属域：

```json
{
  "files": {
    "src/main/java/com/smc/smccloud/bin/controller/BinController.java": {
      "hash": "a1b2c3d4e5f6...",
      "last_modified": "2026-06-15T10:30:00Z",
      "domain": "bin-data"
    },
    "src/api/binorder.js": {
      "hash": "f6e5d4c3b2a1...",
      "last_modified": "2026-06-15T09:00:00Z",
      "domain": "bin-data"
    }
  },
  "domains": {
    "bin-data": {
      "version": 3,
      "anchors_hash": "abc123...",
      "anchor_count": 45,
      "last_build": "2026-06-15T10:30:00Z"
    }
  },
  "meta": {
    "project": "OPS",
    "last_full_build": "2026-06-14T08:00:00Z",
    "total_files": 5200
  }
}
```

用途：
- `graph-wiki update` 对比文件哈希 → 检测变更
- `domains.{id}.anchors_hash` 变更 → 触发 `--recluster`
- 仅 API/Entity 文件变更 → 仅重建 api_mapper / field_mapper

### 6.5 build-report.json 规范

`build-report.json` 是 v1.0 的质量验收入口。它不替代 Markdown Wiki，而是让集成测试、CI 和 Agent 能够用结构化指标判断本次输出是否真正可用。

```json
{
  "project": "tests/svn-platform",
  "generated_at": "2026-06-23T00:00:00Z",
  "input": {
    "files": 32,
    "code_files": 31
  },
  "graph": {
    "light_nodes": 288,
    "light_edges": 396,
    "full_graph_written": false
  },
  "domains": {
    "count": 1,
    "business_points": 77,
    "technical_name_ratio": 1.0
  },
  "api": {
    "total": 46,
    "uncategorized": 46,
    "uncategorized_ratio": 1.0
  },
  "quality": {
    "status": "failed",
    "issues": [
      "api_uncategorized_ratio_too_high",
      "domain_name_is_technical"
    ]
  }
}
```

v1.0 的质量状态分为：

| 状态 | 含义 |
|------|------|
| `passed` | 核心产物完整，关键质量指标通过 |
| `warning` | build 成功，但存在技术域名、summary 缺失等可接受问题 |
| `failed` | build 成功但 Wiki 不适合交付，例如 API 大量未分类或核心域缺少 code-map |

### 6.6 Obsidian 兼容性

Graph-Wiki 输出的 Markdown 使用标准 Obsidian 语法：

```markdown
<!-- 双向链接 -->
本域依赖 [[库存管理]] 提供库存预留服务。

<!-- 代码文件链接 -->
核心入口: [PurchaseController.java](../../ops-backend/ops-delivery/src/main/java/com/smc/smccloud/purchase/controller/PurchaseController.java)

<!-- 嵌入图片 -->
![[domain_graph.png]]

<!-- 元数据（YAML frontmatter） -->
---
domain: 采购管理
anchors: 45
updated: 2026-06-11
---
```

用户可以直接用 Obsidian 打开 `wiki/` 目录作为 vault，获得：
- 双向链接导航（点击 `[[库存管理]]` 跳转）
- 图谱视图（Obsidian 内置的局部图）
- 搜索（跨所有域搜索）

---

## 七、消费链路

### 7.1 人类浏览路径

```
打开 wiki/index.md（Obsidian 或任意 Markdown 阅读器）
    │
    ▼
浏览业务域目录（表格形式，25 个域）
    │
    ▼
点击 [[采购管理]] → 进入 summary.md
    │
    ├── 阅读业务目标、核心流程
    ├── 点击 [[rules]] → 查看业务规则
    ├── 点击 [[code-map]] → 查看 Controller/Service/Mapper 清单
    │        │
    │        └── 点击代码链接 → 跳转到 IDE / GitHub
    │
    └── 看到依赖域 [[库存管理]] → 点击跳转
```

### 7.2 Agent 查询路径

```
用户问: "supplierCode 在 Bin 导入流程中是怎么处理的？"
    │
    ▼
Agent 读取 wiki/index.md → 定位 "Bin数据管理" 域
    │
    ▼
读取 wiki/Bin数据管理/code-map.md → 获得文件列表
    │
    ▼
在限定文件范围内运行 graphify query "supplierCode 数据流"
    │  （搜索范围从 34K 节点缩小到 ~500 节点）
    ▼
返回精确结果，引用 source_location
```

**关键收益**：Agent 的搜索范围从全图缩小到单域，查询精度大幅提升。

**作用域缩小的实现机制**：

```
graphify query 的当前接口不支持文件级白名单参数。
因此"搜索范围缩小"是通过以下方式实现的：

  方式 A — Agent 认知层过滤（当前可行）：
    1. Agent 读取 code-map.md → 获得域内文件列表（~50-200 个文件）
    2. 运行 graphify query（全图 BFS，零 Token，~5-15s）
    3. Agent 在返回结果中只保留 source_file 在文件列表内的节点
    4. 过滤掉 ~90% 的噪音节点 → Token 节省来自 LLM 推理时
       不需要处理噪音，不是图遍历本身加速
    
  方式 B — 图查询层过滤（后续优化）：
    1. graphify query 增加 --scope-files 参数
    2. BFS 遍历时跳过不在白名单中的节点
    3. 图遍历时间从 ~15s 降到 ~2-5s
    4. 需要修改 graphify 源码或提交 PR

  当前设计基于方式 A，性能评估（Chapter 11）中的 Token 节省
  主要来自"Agent 不需要在噪音中筛选"，而非图遍历加速。
  Chapter 11.4 中深度查询的 BFS 耗时 ~15s 基于方式 A。
  方式 B 作为 Phase 4+ 的优化项，可将深度查询从 ~50s 降到 ~35s。
```

### 7.3 CI 集成路径

```
Git Push
    │
    ▼
CI Pipeline
    ├── 1. graph-wiki update --no-recluster
    │       只刷新 code-map.md，~80 秒
    │
    ├── 2. 检测 code-map 变更
    │       ├── 新增 Controller/Service → 通知架构师 review 域划分
    │       └── 仅 DTO/Entity 变化 → 自动通过
    │
    └── 3. 如果 wiki/ 内容有变更 → 自动 commit + push
            保证 wiki 始终和代码同步
```

### 7.4 Agent Token 消耗分析

> **关键问题：Agent 在查询时会读取哪些文件？一次查询消耗多少 Token？**

**graph.json 从不被直接读取。** 57MB 的 JSON 文件太大了，Agent 通过 `graphify query` CLI 在其上进行 BFS 遍历，只接收返回的子图结果，而非读取整个文件。

**各文件 Token 估算：**

| 文件 | 大小 | 内容 | Agent 直接读？ | Token |
|------|------|------|:--:|:--:|
| `graph.json` | 57 MB | 34K 节点 + 88K 边 | ❌ 从不！通过 CLI 查 | 0 |
| `wiki/index.md` | ~50 行 | 25 域目录 | ✅ 入口 | ~500 |
| `wiki/{domain}/summary.md` | ~50 行 | 单域业务描述 | ✅ 定位后 | ~800 |
| `wiki/{domain}/code-map.md` | ~40 行 | 文件清单 | ✅ 需要时 | ~500 |
| `wiki/{domain}/api-docs.md` | ~60 行 | 单域 API 文档 | ✅ 接口查询 | ~800 |
| `api-map.json`（全量） | ~1,500 行 | 300-500 API | ⚠️ 太大 | **~20,000** |
| `api-map.json`（按域拆分） | ~20 行 | 1 域 API | ✅ 推荐 | **~300** |

**核心设计优化：api-map.json 必须按域拆分，而非单一文件。**

```
❌ 单文件方案（浪费 Token）：
   api-map.json (20,000 tokens)
   → Agent 查一个字段也要读全部 500 个 API

✅ 拆分方案（当前设计）：
   wiki/api-index.md (1,500 tokens, 总览)     ← 浏览全部 API 时读
   wiki/{domain}/api-docs.md (800 tokens)      ← 字段映射时读
   wiki/{domain}/data-flow.md (600 tokens)     ← 数据流时读
   → Agent 按需读取，一次 300-1,500 tokens
```

**典型场景 Token 对比：**

| 场景 | Graph-Wiki 路径 | Token | Graphify 路径 | Token | 节省 |
|------|---------------|:--:|--------------|:--:|:--:|
| 查某域概要 | index.md → summary.md | 1,300 | query → 噪音筛选 | 8,000 | **84%** |
| 查字段映射 | index.md → api-docs.md | 800 | query → 读源码 | 12,000 | **93%** |
| 查跨域依赖 | dependencies.md | 400 | path + 分析 | 5,000 | **92%** |
| 查全量 API | api-index.md | 1,500 | 无此能力 | — | — |
| 精确代码查询 | Wiki + query | 4,300 | query | 5,000 | 14% |
| 深度跨域分析 | Wiki×3 + query + 源码 | 11,000 | query + 源码 | 15,000+ | 27% |

**首次导航开销**（Agent 初次接触项目时）：

| 步骤 | 操作 | Token |
|------|------|:--:|
| 1 | 读取 `wiki/index.md` 了解 25 域全貌 | ~500 |
| 2 | 浏览 3-5 个核心域的 `summary.md` | ~2,000-2,500 |
| 3 | 读取目标域的 `code-map.md` 建立文件认知 | ~500 |
| **总计** | | **~3,000-3,500** |

对比：Agent 在没有 Wiki 的情况下需要读取项目 README + 扫描目录结构 + BFS 查询，首次导航 Token 通常在 15,000-25,000 区间。Wiki 节省约 **85%** 的首次导航成本。

**跨域查询场景**（涉及多个域的关联查询）：

| 场景 | 读取文件 | Token |
|------|---------|:--:|
| 跨 2 域数据流 | 2 × `data-flow.md` + 2 × `api-docs.md` | ~2,800 |
| 跨 3 域调用链 | 3 × `summary.md` + domain_graph.html | ~3,000 |
| 全项目字段搜索 | `field-map.json`（按域拆分，3-5 域） | ~1,500-3,000 |

即使跨域查询，每个拆分文件仍在 300-800 tokens，不会出现单文件 20K tokens 的极端情况。更关键的是 Agent 可以**增量式**读取——先读一个域的 summary.md 判断是否相关，再按需扩展到其他域。

**结论**：
- **80% 的日常查询** Token 在 **1,500 以下**，比 Graphify 节省 **84-93%**
- **首次导航** ~3,500 tokens，比无 Wiki 场景节省 **85%**
- **跨域查询**仍然受益于文件拆分，不会出现单文件爆炸
- **精确代码查询**节省不明显（此时都依赖 graph.json 图遍历）

---

## 八、数据流详解：graph.json 的角色定位

> **核心认知：graph.json 在 Graphify 是最终产物，在 Graph-Wiki 是中间产物。这是两个项目架构上最根本的区别。**

### 8.1 对比：两个项目的分层模型

```
╔══════════════════════════════════════════════════════════════════╗
║                      Graphify 架构                                ║
╠══════════════════════════════════════════════════════════════════╣
║                                                                  ║
║  源代码 (.java / .vue / .js)                                     ║
║       │                                                          ║
║       ▼                                                          ║
║  ┌─────────────┐                                                 ║
║  │ detect()    │  文件检测                                        ║
║  └──────┬──────┘                                                 ║
║         │                                                        ║
║         ▼                                                        ║
║  ┌─────────────┐   ┌─────────────────┐                           ║
║  │ extract()   │   │ Part B (LLM)    │  ← 对等、并行              ║
║  │ AST 解析     │   │ 语义提取         │                           ║
║  └──────┬──────┘   └────────┬────────┘                           ║
║         │                   │                                    ║
║         └─────────┬─────────┘                                    ║
║                   │                                              ║
║                   ▼                                              ║
║  ┌─────────────────────────────────────┐                         ║
║  │           graph.json                │  ← ★ 最终产物            ║
║  │      (34K 节点, 88K 边)             │                          ║
║  └────────────────┬────────────────────┘                         ║
║                   │                                              ║
║     ┌─────────────┼─────────────┬──────────────┐                 ║
║     ▼             ▼             ▼              ▼                 ║
║  Louvain      graph.html    GRAPH_REPORT   wiki/obsidian        ║
║  聚类          (D3 毛球)     .md            (方法名拼凑)          ║
║  (1,935 社区)                                (1,935 篇)          ║
║                                                                  ║
║  Agent 通过 graph.json 的 query/path/explain 直接查询             ║
║                                                                  ║
╚══════════════════════════════════════════════════════════════════╝

╔══════════════════════════════════════════════════════════════════╗
║                    Graph-Wiki 架构                                ║
╠══════════════════════════════════════════════════════════════════╣
║                                                                  ║
║  源代码 (.java / .vue / .js)                                     ║
║       │                                                          ║
║       ▼                                                          ║
║  ┌─────────────────────────────────────────┐                     ║
║  │        复用层 (graphify API)            │                     ║
║  │  detect() → extract() → build()         │                     ║
║  │  → graph.json                           │                     ║
║  └────────────────┬────────────────────────┘                     ║
║                   │                                              ║
║                   ▼                                              ║
║  ┌─────────────────────────────────────────┐                     ║
║  │           graph.json                    │  ← ☆ 中间产物        ║
║  │      (34K 节点, 88K 边)                 │     （不是终点）      ║
║  └────────────────┬────────────────────────┘                     ║
║                   │                                              ║
║     ┌─────────────┼──────┬──────────┬──────────────┐            ║
║     ▼             ▼      ▼          ▼              ▼            ║
║  ┌────────┐ ┌──────────┐ ┌────────────┐ ┌────────┐ ┌──────────┐ ║
║  │cluster │ │api_mapper│ │field_mapper│ │  label │ │ visualize│ ║
║  │ (聚类)  │ │(API 映射) │ │ (字段映射)  │ │(LLM    │ │ (域级    │ ║
║  │        │ │          │ │            │ │ 标注)  │ │  HTML)   │ ║
║  └───┬────┘ └─────┬────┘ └──────┬─────┘ └────┬───┘ └────┬─────┘ ║
║      │            │             │            │          │       ║
║      └────────────┴─────────────┴────────────┴──────────┘       ║
║                         │                                        ║
║                         ▼                                        ║
║  ┌──────────────────────────────────────────────┐                ║
║  │              Wiki 文档树                      │  ← ★ 最终产物  ║
║  │  index.md + domains/ + code-map.md + ...     │                ║
║  │  domain_graph.html                           │                ║
║  └──────────────────────────────────────────────┘                ║
║                                                                  ║
║  人类通过 Wiki 浏览       Agent 通过 Wiki → graph.json 查询       ║
║                                                                  ║
╚══════════════════════════════════════════════════════════════════╝
```

**关键差异**：

| 维度 | Graphify | Graph-Wiki |
|------|---------|-----------|
| graph.json 角色 | **最终产物**（直接面向 Agent） | **中间产物**（Wiki 的代码数据源） |
| 核心产出 | graph.json + graph.html | Wiki 文档树 + domain_graph.html |
| 聚类 | Louvain（graph.json 之上） | 包前缀 + import（默认在 graph-lite.json 之上） |
| 标注 | 关键词拼凑 | LLM 语义生成 |
| 消费模式 | Agent → graph.json 查询 | 人 → Wiki → Agent → graph.json 查询 |

### 8.2 完整数据流（从上到下）

```
                          源代码
                  ops-backend/ + ops-frontend/
                            │
                            │
┌───────────────────────────┴───────────────────────────────┐
│                                                           │
│  第 0 层：复用层（graphify API）                            │
│  ─────────────────────────────                            │
│                                                           │
│  ┌─────────────────┐                                      │
│  │ detect()        │  文件检测                             │
│  │                 │  输入: 目录路径                        │
│  │                 │  输出: {files: {code: [...],          │
│  │                 │          doc: [...], image: [...]}}   │
│  └────────┬────────┘                                      │
│           │                                               │
│           ▼                                               │
│  ┌─────────────────┐                                      │
│  │ extract()       │  AST 提取 (tree-sitter)              │
│  │                 │  12 workers 并行                      │
│  │                 │  输入: code_files                     │
│  │                 │  输出: {nodes: [...], edges: [...]}   │
│  │                 │                                       │
│  │                 │  节点类型:                             │
│  │                 │    · 类节点 (.java/.vue label)        │
│  │                 │    · 方法节点 (label 以 '.' 开头)      │
│  │                 │    · 字段节点                          │
│  │                 │    · 类型引用节点 (String, Integer)    │
│  │                 │                                       │
│  │                 │  边类型:                               │
│  │                 │    · imports    class → imported class│
│  │                 │    · contains   class → method/field  │
│  │                 │    · calls      method → method       │
│  │                 │    · references method → type         │
│  │                 │    · inherits   class → parent        │
│  │                 │    · implements class → interface     │
│  └────────┬────────┘                                      │
│           │                                               │
│           ▼                                               │
│  ┌─────────────────┐                                      │
│  │ filter_extraction_for_wiki()                           │
│  │                 │  build 前业务级预过滤                  │
│  │                 │  输入: extraction {nodes, edges}      │
│  │                 │  输出: filtered_extraction            │
│  └────────┬────────┘                                      │
│           │                                               │
│           ▼                                               │
│  ┌─────────────────┐                                      │
│  │ build_light_graph()                                    │
│  │                 │  构建轻量 NetworkX Graph              │
│  │                 │  输入: filtered_extraction            │
│  │                 │  输出: nx.Graph → graph-lite.json     │
│  └────────┬────────┘                                      │
│           │                                               │
└───────────┼───────────────────────────────────────────────┘
            │
            │  graph-lite.json  ← Graph-Wiki v1.0 默认中间产物
            │  graph.json       ← 可选完整后端，供精确下钻查询
            │
┌───────────┴───────────────────────────────────────────────┐
│                                                           │
│  第 1 层：聚类层（graph_wiki.cluster）                       │
│  ─────────────────────────────────                        │
│                                                           │
│  ┌─────────────────────────────────────────┐              │
│  │ business_cluster()                       │              │
│  │                                          │              │
│  │  Step 1 — 二次过滤噪音（filter_noise）       │              │
│  │    输入: graph-lite.json                  │              │
│  │    规则:                                  │              │
│  │      · 排除 label 不以 .java/.vue 结尾    │              │
│  │      · 排除 label 以 '.' 或 '_' 开头     │              │
│  │        (匿名方法调用)                      │              │
│  │      · 排除 JDK 类型 (String, Integer...) │              │
│  │      · 排除 Config/Util/Constants         │              │
│  │    输出: 业务节点                          │              │
│  │    作用: 作为预过滤后的第二道质量防线        │              │
│  │                                          │              │
│  │  Step 2 — 提取锚点（extract_anchors）       │              │
│  │    输入: ~2,800 业务节点                   │              │
│  │    规则:                                  │              │
│  │      · 筛选 role = CONTROLLER /           │              │
│  │        SERVICE_IMPL / SERVICE_API /        │              │
│  │        MAPPER / DAO / ADAPTER 的节点       │              │
│  │    输出: ~100-1,200 个锚点（业务骨架节点）   │              │
│  │                                          │              │
│  │  Step 3 — 包前缀聚类（cluster_by_package）  │              │
│  │    输入: 锚点 + 包路径                     │              │
│  │    规则:                                  │              │
│  │      · 提取 Java 包前缀 (公司域名后 1-2 级) │              │
│  │      · 例: com.smc.smccloud.bin.* → 'bin' │              │
│  │      · 例: com.smc.smccloud.order.*→'order'│             │
│  │      · 合并高相似候选域                    │              │
│  │    输出: ~40 个候选域                     │              │
│  │                                          │              │
│  │  Step 4 — import 聚合调整（adjust_by_imports）│            │
│  │    输入: ~40 候选域 + import 关系          │              │
│  │    规则:                                  │              │
│  │      · 计算域间 import 密度               │              │
│  │      · 互相 import > 阈值 → 合并          │              │
│  │      · 内聚度 < 阈值 → 拆分               │              │
│  │    输出: ~25 个最终业务域                 │              │
│  │                                          │              │
│  │  Step 5 — 业务点提取（extract_business_points）│           │
│  │    输入: ~25 域 + graph-lite 方法级节点    │              │
│  │    规则:                                  │              │
│  │      · 提取每个域的 Controller/Service     │              │
│  │        的 public 方法                     │              │
│  │      · 排除 getter/setter/toString 等     │              │
│  │      · 沿 calls 边检测跨域调用             │              │
│  │    输出: ~500-800 业务点                   │              │
│  │                                          │              │
│  │  → domains.json                          │              │
│  └─────────────────────────────────────────┘              │
│                                                           │
│  Token 成本: 0 (纯规则 + 图遍历)                             │
│                                                           │
└───────────┬───────────────────────────────────────────────┘
            │
            │  domains.json (25 域 × N 业务点)
            │
┌───────────┴───────────────────────────────────────────────┐
│                                                           │
│  第 2 层：API 映射层（graph_wiki.api_mapper）                │
│  ─────────────────────────────────────                     │
│                                                           │
│  ┌─────────────────────────────────────────┐              │
│  │ build_api_map()                          │              │
│  │                                          │              │
│  │  输入:                                    │              │
│  │    · 前端 src/api/ 目录 (JS API 定义)     │              │
│  │    · 前端 src/views/ 目录 (Vue 页面)      │              │
│  │    · 后端 Controller 源码目录             │              │
│  │    · domains (聚类产出的域信息)            │              │
│  │    · graph.json (方法/参数级节点)          │              │
│  │                                          │              │
│  │  过程:                                    │              │
│  │    · 解析前端 API 文件 → 函数/URL/参数     │              │
│  │    · 追踪前端调用者 → Vue 页面映射         │              │
│  │    · 解析后端 Controller → @XxxMapping    │              │
│  │    · URL 路径匹配 + HTTP 方法匹配          │              │
│  │                                          │              │
│  │  输出: api-map.json (前后端 API 映射)      │              │
│  │                                          │              │
│  │  Token 成本: 0 (纯静态解析)                │              │
│  └─────────────────────────────────────────┘              │
│                                                           │
└───────────┬───────────────────────────────────────────────┘
            │
            │  api-map.json
            │
┌───────────┴───────────────────────────────────────────────┐
│                                                           │
│  第 3 层：字段映射层（graph_wiki.field_mapper）              │
│  ───────────────────────────────────────                   │
│                                                           │
│  ┌─────────────────────────────────────────┐              │
│  │ build_field_map()                        │              │
│  │                                          │              │
│  │  输入:                                    │              │
│  │    · api-map.json (api_mapper 输出)       │              │
│  │    · 后端 Entity/domain 源码目录           │              │
│  │    · 后端 DTO 源码目录                    │              │
│  │                                          │              │
│  │  过程:                                    │              │
│  │    · 解析 @TableField/@Column 注解       │              │
│  │    · DTO ↔ Entity 字段名匹配             │              │
│  │    · 生成六层链路 (Vue→API→Controller→     │              │
│  │      DTO→Entity→DB)                      │              │
│  │                                          │              │
│  │  输出: field-map.json (按域拆分)          │              │
│  │                                          │              │
│  │  Token 成本: 0 (注解 + 字段匹配)          │              │
│  └─────────────────────────────────────────┘              │
│                                                           │
└───────────┬───────────────────────────────────────────────┘
            │
            │  domains.json + api-map.json + field-map.json
            │
┌───────────┴───────────────────────────────────────────────┐
│                                                           │
│  第 4 层：标注层（graph_wiki.label）                        │
│  ───────────────────────────────                          │
│                                                           │
│  ┌─────────────────────────────────────────┐              │
│  │ label_domains()                              │              │
│  │                                          │              │
│  │  输入 (每个域 ~2,500 tokens):             │              │
│  │    · 包路径前缀                           │              │
│  │    · Controller/Service 类名列表          │              │
│  │    · 1-2 个核心 Service 的源码片段        │              │
│  │    · 跨域 import 统计                     │              │
│  │                                          │              │
│  │  LLM 生成:                                │              │
│  │    · 域名称 (2-5 字)                      │              │
│  │    · 业务目标描述 (1 句)                  │              │
│  │    · 核心流程 (3-5 步)                    │              │
│  │    · 域间依赖说明                         │              │
│  │    · summary.md 初稿                     │              │
│  │                                          │              │
│  │  Token 成本: ~$1 (25 域, 一次性)          │              │
│  └─────────────────────────────────────────┘              │
│                                                           │
└───────────┬───────────────────────────────────────────────┘
            │
            │  domains.json (含业务语义) + summary.md × 25
            │
┌───────────┴───────────────────────────────────────────────┐
│                                                           │
│  第 5 层：导出层（graph_wiki.export + visualize）            │
│  ─────────────────────────────────────────                │
│                                                           │
│  ┌──────────────────────┐  ┌──────────────────────┐       │
│  │ export_wiki()        │  │ export_domain_html()  │       │
│  │                      │  │                      │       │
│  │  wiki/               │  │ domain_graph.html    │       │
│  │  ├── index.md        │  │                      │       │
│  │  ├── 采购管理/        │  │ · D3 力导向图         │       │
│  │  │   ├── summary.md  │  │ · ~25 域节点          │       │
│  │  │   ├── rules.md    │  │ · 节点大小 = 锚点数    │       │
│  │  │   ├── spec.md     │  │ · 连线粗细 = import 数 │       │
│  │  │   ├── code-map.md │  │ · 点击展开域内结构     │       │
│  │  │   ├── api-docs.md │  │                      │       │
│  │  │   ├── data-flow.md│  │ Token: 0              │       │
│  │  │   ├── er-diagram.md│ │                      │       │
│  │  │   └── dependencies│  │                      │       │
│  │  │       .md         │  │                      │       │
│  │  └── ...             │  │                      │       │
│  │                      │  │                      │       │
│  │  + api-map.json      │  │                      │       │
│  │    (由 api_mapper    │  │                      │       │
│  │     生成，经 export   │  │                      │       │
│  │     复制到输出目录)    │  │                      │       │
│  │  + field-map.json    │  │                      │       │
│  │    (由 field_mapper  │  │                      │       │
│  │     生成，经 export   │  │                      │       │
│  │     复制到输出目录)    │  │                      │       │
│  │  Token: 0 (模板生成)  │  │                      │       │
│  └──────────────────────┘  └──────────────────────┘       │
│                                                           │
│  ┌──────────────────────┐                                 │
│  │ export.py（API/ER/     │                                 │
│  │ 数据流生成）           │                                 │
│  │                      │                                 │
│  │ · api-docs.md        │  从 api_mapper 结果生成 API 文档    │
│  │ · data-flow.md       │  从 field_mapper 结果生成数据流   │
│  │ · er-diagram.md      │  从 Entity 注解生成 Mermaid    │
│  │                      │                                 │
│  │ Token: 0 (注解 + 模板)│                                 │
│  └──────────────────────┘                                 │
│                                                           │
└───────────────────────────────────────────────────────────┘
```

### 8.3 两种消费路径

```
╔══════════════════════════════════════════════════════════════════╗
║                    人类的消费路径                                  ║
╠══════════════════════════════════════════════════════════════════╣
║                                                                  ║
║  wiki/index.md                                                   ║
║    │  （Obsidian 或 VitePress 打开）                               ║
║    │                                                             ║
║    ▼                                                             ║
║  业务域目录（~25 个域，表格形式）                                    ║
║    │                                                             ║
║    ├── 点击 [[采购管理]]                                          ║
║    │     │                                                       ║
║    │     ▼                                                       ║
║    │   summary.md（业务目标/核心流程）                              ║
║    │     │                                                       ║
║    │     ├── [[rules]] → 业务规则（人工填写）                       ║
║    │     ├── [[code-map]] → Controller/Service/Mapper 清单       ║
║    │     │     └── 点击代码路径 → 跳转 IDE/GitHub                   ║
║    │     ├── [[api-docs]] → REST API 文档                         ║
║    │     ├── [[data-flow]] → 数据流文档                            ║
║    │     └── [[er-diagram]] → Mermaid ER 图                       ║
║    │                                                             ║
║    └── 看到依赖 [[库存管理]] → 点击跳转                              ║
║                                                                  ║
║  全程不需要 AI 参与，纯 Markdown 阅读                                ║
║                                                                  ║
╚══════════════════════════════════════════════════════════════════╝

╔══════════════════════════════════════════════════════════════════╗
║                    Agent 的消费路径                                ║
╠══════════════════════════════════════════════════════════════════╣
║                                                                  ║
║  用户提问: "supplierCode 在 Bin 导入流程中怎么处理的？"              ║
║                                                                  ║
║  Agent 执行:                                                      ║
║                                                                  ║
║  第 1 步 — 定位域                                                  ║
║    读取 wiki/index.md                                            ║
║    → "Bin数据管理" 域                                              ║
║    Token: ~500                                                    ║
║                                                                  ║
║  第 2 步 — 定位业务点                                              ║
║    读取 wiki/Bin数据管理/summary.md                               ║
║    → "Bin 数据导入" 业务点                                         ║
║    Token: ~1,000                                                  ║
║                                                                  ║
║  第 3 步 — 获取字段映射                                            ║
║    读取 field-map.json → Bin数据管理                               ║
║    → supplierCode: Excel 列 31 → BindataApply.supplierCode       ║
║      → bin_data_apply.supplier_code                              ║
║    Token: 0 (JSON 纯数据)                                         ║
║                                                                  ║
║  第 4 步 — 精确代码查询（仅当需要）                                  ║
║    在限定文件范围内运行:                                            ║
║    graphify query "supplierCode 赋值逻辑"                         ║
║    （搜索范围从 34K 节点缩小到 ~500 节点）                           ║
║    Token: 0 (图遍历)                                              ║
║                                                                  ║
║  总计 Token: ~1,500（主要是 Wiki 页面读取，非 LLM 推理）             ║
║                                                                  ║
║  ─────────────────────────────────────────────                   ║
║                                                                  ║
║  对比 Graphify 直接查询:                                           ║
║     graphify query "supplierCode"                                ║
║     → 在 34K 节点全图中 BFS                                       ║
║     → 返回结果包含大量噪音（getter/setter/toString 等）              ║
║     → Agent 需要自己在噪音中筛选有效信息                            ║
║     → 精度低，消耗更多推理 Token                                   ║
║                                                                  ║
╚══════════════════════════════════════════════════════════════════╝
```

### 8.4 graph.json 内部结构

```
graph.json (NetworkX node-link 格式, ~57 MB)
│
├── "directed": false              ← 无向图
├── "multigraph": false
│
├── "nodes": [                     ← 34,298 个节点
│   │
│   │   类节点 (~2,800 个):
│   │   {
│   │     "id": "smccloud_opsadapterapplication",
│   │     "label": "OpsAdapterApplication.java",
│   │     "file_type": "code",
│   │     "source_file": "ops-backend/.../OpsAdapterApplication.java",
│   │     "source_location": "L1",
│   │     "_origin": "ast",
│   │     "community": 1428,       ← 仅 Graphify 使用
│   │     "norm_label": "opsadapterapplication.java"
│   │   }
│   │
│   │   方法节点 (~14,700 个):
│   │   {
│   │     "id": "smccloud_opsadapterapplication_main",
│   │     "label": ".main()",
│   │     "_origin": "ast"
│   │   }
│   │
│   │   类型引用节点 (~3,100 个):
│   │   {
│   │     "id": "...",
│   │     "label": "String"
│   │   }
│   │
│   │   其他 (~13,700 个): 字段、DTO、VO、Enum、Config...
│   ]
│
├── "links": [                     ← 87,779 条边
│   │
│   │   imports (11,335):
│   │   {
│   │     "source": "class_A_id",
│   │     "target": "class_B_id",
│   │     "relation": "imports",
│   │     "confidence": "EXTRACTED",
│   │     "confidence_score": 1.0,
│   │     "weight": 1.0
│   │   }
│   │
│   │   contains (4,645):
│   │   {
│   │     "source": "class_id",
│   │     "target": "method_id",
│   │     "relation": "contains"
│   │   }
│   │
│   │   calls (14,542):
│   │   {
│   │     "source": "caller_method_id",
│   │     "target": "callee_method_id",
│   │     "relation": "calls"
│   │   }
│   │
│   │   references (41,450):
│   │   {
│   │     "source": "method_id",
│   │     "target": "type_id (String/Integer/...)"
│   │     "relation": "references"
│   │   }
│   │
│   │   inherits / implements / method ...
│   ]
│
├── "hyperedges": []               ← 超边（跨社区关系）
└── "graph": {}                    ← 图元数据
```

> **Lombok 注释**：使用 `@Data`、`@Builder`、`@Getter`、`@Setter` 等 Lombok 注解生成的 getter/setter 方法，在 Step 1 噪音过滤阶段即被排除——它们符合 `^(get|set|is)[A-Z]` 模式匹配规则。因此 Lombok 样板代码不会进入聚类流程，也不会出现在业务点列表中。

### 8.5 各层使用的图数据子集

```
graph-lite.json (v1.0 默认轻量图)
    │
    ├── 聚类层使用:
    │    类/文件节点 + imports/imports_from 边
    │    → 域划分
    │
    ├── 业务点层使用:
    │    业务方法候选节点 + calls 边 + contains 边
    │    → 业务点提取 + 跨域调用分析
    │
    ├── 字段映射层使用:
    │    实体类节点 + 字段节点 + @TableField/@Column 注解
    │    → field-map.json
    │
    ├── Agent 常规导航使用:
    │    轻量节点 + 业务边
    │    → Wiki 定位后的范围缩小
    │
    └── 完整 graph.json（可选）:
       全量节点 + 全量边
       → graphify query / path / explain 精确下钻
       → 仅在 Agent 需要精确到方法签名时使用
```

---

## 九、实施计划

> 时间估算基于 2026-06-11 审核反馈修正，原估算 5-7 周 → 修正为 9-10 周。

### Phase 1：基础流水线 + 聚类模块（预计 2 周）

**目标**：能用 `graph-wiki build` 产出域划分和 code-map

- [x] 搭建项目骨架（`pyproject.toml`, `graph_wiki/` 包结构）
- [x] 实现 `reuse.py`（封装 graphify 的 detect/extract/build）
- [x] 实现 `cluster.py` — `business_cluster()` 五步算法
- [ ] 在 OPS 后端数据上验证：是否能产出 20-30 个有意义的域
- [ ] 对比基线：Graphify 1,935 社区 vs Graph-Wiki N 域

**验证标准**：产出的域划分方案让 OPS 项目开发者认可（≥80% 的域"合理"）

### Phase 2：LLM 标注模块（预计 2 周）

**目标**：每个域有 LLM 生成的名称和 summary.md 初稿

- [x] 实现 `label.py` — `label_domains()`
- [ ] 设计并调优 prompt（在 OPS 数据上迭代，预计 3-5 轮）
- [ ] 生成 25 个域的 summary.md 初稿
- [ ] 人工 review 并记录 prompt 改进点
- [ ] 实现 OpenAI / Gemini 后端支持

**验证标准**：LLM 生成的域描述经人工 review，≥80% "基本可用，仅需微调"

### Phase 3：Wiki 导出 + 可视化（预计 1.5 周）

**目标**：产出完整的 Wiki 文档库和域级可视化

- [x] 实现 `export.py` — `export_wiki()`
- [x] 实现 `visualize.py` — `export_domain_html()`
- [ ] 在 Obsidian 中验证双向链接效果
- [ ] 验证 Agent 通过 Wiki → code-map → graph.json 三级查询
- [ ] D3 下钻交互优化

**验证标准**：Wiki 可在 Obsidian 中打开并正常导航；Agent 查询时间减少 50%+

### Phase 4：流水线整合 + CLI（预计 2 周）

**目标**：一键构建 + 增量更新

- [x] 实现 `pipeline.py` — CLI 入口（`graph-wiki build`）
- [ ] 实现 `--update`（复用域划分，仅刷新 code-map）
- [ ] 实现 `--recluster`（强制重新聚类）
- [ ] 前端项目适配（Vue/JS 的包路径聚类逻辑）
- [ ] CLI edge cases 处理（~30-50% 额外工作量）

**验证标准**：`graph-wiki build .` 一键完成全流程；`graph-wiki update .` < 2 分钟

### Phase 5：文档 + 测试 + 发布（预计 1.5 周）

- [ ] 编写使用文档
- [ ] 在 3 个不同类型项目上测试（Spring Boot / Vue / 混合项目）
- [ ] 修复测试中发现的问题
- [ ] 发布到 PyPI（`pip install graph-wiki`）

**总计**：9-10 周

---

## 十、功能需求清单与实现逻辑

> 以下 10 项需求来自项目方，是 Graph-Wiki 必须满足的核心能力。每项均标注了设计覆盖状态和实现模块。

### 需求 1：AI 读取单业务或跨业务关联

> "ai 能够通过它来读取单个业务或多个业务之间的关联"

**覆盖状态**：✅ 已覆盖

| 能力 | 数据来源 | Token 成本 |
|------|---------|:--:|
| 读取单个域的完整信息 | `wiki/{domain}/summary.md` + `code-map.md` | 低 |
| 查询跨域依赖 | `domains.json` → `dependencies[]` + `wiki/{domain}/dependencies.md` | 低 |
| 理解依赖的原因 | LLM 读取两个域的 summary.md | 低 |

### 需求 2：表/字段来源追溯 + 前后端数据流

> "ai 能够通过它来查询某个表和表中某个字段的来源，前端数据到后端数据库的数据流能够清晰的梳理出"

**覆盖状态**：❌ 需新增 `field_mapper.py` 模块

追踪链路：`Vue 表单字段 → API 请求参数 → Controller 接收 → Service 处理 → DTO 转换 → Entity 映射 → @TableField 注解 → 数据库列`

| 数据层 | 提取方式 | Token 成本 |
|--------|---------|:--:|
| 前端 → API 映射 | 解析前端 API 文件的请求体字段名 | 零 |
| API → DTO 映射 | 解析 Controller 方法参数类型 | 零 |
| DTO → Entity 映射 | 字段名相似度匹配 + 类型匹配 | 零 |
| Entity → DB 列 | 解析 @TableField / @Column 注解 | 零 |
| 字段取值规则 | **LLM 读取 ServiceImpl 赋值代码** | 高 |

输出：`field-map.json`，每个域一个字段映射表。Agent 查询"前端 supplierCode 对应哪个数据库字段"时，直接读此文件。

### 需求 3：完整业务流程 + 字段取值规则

> "ai 能够通过它来描述单个完整业务的总体流程和各个字段的取值规则"

**覆盖状态**：⚠️ 部分覆盖

| 能力 | 现状 | Token 成本 |
|------|------|:--:|
| 业务流程描述 | ✅ `summary.md` 有 LLM 生成的核心流程 | 中 |
| 跨模块影响 | ✅ `dependencies.md` | 零 |
| 字段取值规则 | ❌ 需要 LLM 读取 ServiceImpl 赋值逻辑 | 高 |
| 字段来源优先级 | ❌ 同上 | 高 |

### 需求 4：代码缺陷识别 + 重构设计

> "ai 能够通过它来描述当前代码中的缺陷并进行代码重构设计"

**覆盖状态**：⚠️ Phase 4+ 计划模块（代码缺陷检测 + 重构建议），当前不在 v1.0 范围

| 场景             | 方式                               | Token 成本 |
| -------------- | -------------------------------- | :------: |
| 识别大方法（>2000 行） | graph.json 方法级节点统计               |    零     |
| 识别大实体（>20 字段）  | AST 字段节点统计                       |    零     |
| if 树复杂度        | 需 AST 提供方法体结构（当前 tree-sitter 可做） |    零     |
| 方法拆分方案         | **LLM 阅读完整方法源码**                 |    极高    |
| 业务逻辑总结         | LLM 深度阅读                         |    高     |
| 废弃、过期方法标注      | AST 提供方法调用链路+LLM阅读               |    低     |
| 未被调用方法标注       | AST 提供方法调用链路+LLM阅读               |    低     |

**诚实结论**：Graph-Wiki 可以零成本**识别**需要重构的代码，但**提出重构方案**需要 LLM 深度阅读（$0.5-2/方法），这不是知识图谱工具的强项，建议作为可选增强功能。

### 需求 5：跨模块关联关系查询

> "ai 能够通过它查询跨多个模块的关联关系，比如事件总线、转订业务影响订单删单等"

**覆盖状态**：✅ 已覆盖 + 🔧 依赖 graph.json

| 场景 | 方式 | Token 成本 |
|------|------|:--:|
| 查询所有事件发布器 | `graphify query` 搜索 graph.json | 零 |
| 汇总发布器 → 域映射 | graph.json 结果 + domains.json | 低 |
| 查询转订 → 删单影响链 | `graphify path "A" "B"` | 零 |

**关键**：这类查询需要 graph.json **保留方法级节点**。Wiki 层是类级/业务点级的，但 graph.json 保留全量 AST 数据供 Agent 精确查询。

### 需求 6：业务逻辑 vs 工具类/基础设施分类

> "ai 能够识别出哪些代码是业务逻辑方法，哪些是工具类和基础设施"

**覆盖状态**：✅ 已内置在 `business_cluster()` 的 Step 1

```
业务骨架: Controller, ServiceImpl, Service, Mapper, DAO, Adapter
基础设施: Config, Util, Helper, Constants, Builder, Converter
排除:     getter/setter, toString, equals, hashCode, 匿名方法调用
```

Token 成本：**零**（纯规则匹配）。

### 需求 7：分层业务大纲

> "人能够通过它来得到一个项目的业务大纲，业务大纲能够展开成业务域，业务域也能展开成各个业务点，业务点也能展开成业务实现细节，分层要明确条理"

**覆盖状态**：✅ 通过三层粒度模型实现（详见[第十二章](#十二粒度设计三层粒度模型)）

```
第 1 层 — 业务大纲（1 个 index.md）
  └── 第 2 层 — 业务域（~25 个 domain/）
        └── 第 3 层 — 业务点（~500 个 Controller public 方法）
              └── 实现细节（code-map.md + graph.json）
```

Token 成本：**零**（结构自动生成）。

### 需求 8：业务细节文档 + 图形化展示

> "业务细节包括接口文档，需求规格说明书，业务流程文档，详细设计文档，能展示数据流，字段对应规则，实体关联关系等"

**覆盖状态**：✅ 通过 api_mapper + field_mapper + export 联合覆盖

| 文档类型 | 生成方式 | Token 成本 |
|------|---------|:--:|
| API 接口文档 | 从 Controller 注解自动提取 | 零 |
| ER 图 | 从 Entity 注解生成 Mermaid | 零 |
| 数据流文档 | 从 field-map.json 生成 | 零 |
| 业务流程图 | LLM 采样 Service 生成 | 中 |
| 需求规格说明书 | **人工填写**（不应自动生成） | — |
| 详细设计文档 | LLM 深度阅读 + 生成 | 高 |

### 需求 9：自动更新 + 变更日志

> "修改代码后可以自动更新知识库，代码修改可以支持小范围更新和大更新，并记录日志和摘要"

**覆盖状态**：✅ 已设计（`--update` + `--recluster`）

| 场景 | 命令 | 刷新内容 | Token | 耗时 |
|------|------|---------|:--:|:--:|
| 小改（<10 行） | `graph-wiki update` | code-map | 零 | ~80s |
| 大改（重构） | `graph-wiki update --recluster` | 域划分 + code-map | 低 | ~3min |
| 新增模块 | `graph-wiki update` → 提示 `--recluster` | 域划分 | 零~低 | ~3min |
| 变更日志 | 自动追加 CHANGELOG.md | 文件列表 | 零 | ~1s |
| 变更摘要 | `graph-wiki update --summary` | LLM 一句话总结 | 低 | ~5s |

### 需求 10：Obsidian + HTML + 独立搜索

> "完美支持 Obsidian 和 HTML 可视化重力图，不依赖 AI 也能支持业务点级别的搜索功能"

**覆盖状态**：✅ 已覆盖

| 能力 | 实现 |
|------|------|
| Obsidian | 双向链接 Markdown，Wiki 目录直接作为 Obsidian vault 打开 |
| HTML 可视化 | `domain_graph.html`（域级 D3 力导向图，~25 节点） |
| 独立搜索 | HTML 内置客户端搜索栏 + Obsidian 自带搜索 |
| 静态站点 | 可选 VitePress 渲染（比 Obsidian 更适合非技术用户） |

---

## 十一、Token 与性能全面评估

> **目标：诚实地评估 Graph-Wiki 在整个生命周期中的 Token 消耗、速度和效果质量。**

### 11.1 构建时 Token 消耗（一次性）

```
首次构建 graph-wiki build ops-backend:

  detect()           ▏ 0 Token   ~5s     文件系统遍历
  extract()          ██████████  0 Token   ~10min  tree-sitter 12 workers (占 80% 时间)
  build()            ▏ 0 Token   ~30s     NetworkX 图构建
  business_cluster() ▏ 0 Token   ~10s     正则 + 包路径 + 图遍历
  api_mapper()       ▏ 0 Token   ~15s     tree-sitter JS + Java 注解 + 字符串匹配
  label_domains()        ▏ ~60K in   ~30s     LLM API (★唯一消耗 Token 的步骤)
                     ▏ ~7.5K out
  export_wiki()      ▏ 0 Token   ~5s      模板生成
  export_html()      ▏ 0 Token   ~3s      D3 模板
  ──────────────────────────────────────
  合计               ~70,000 Token   ~12-15min  成本 ~$0.3-1 (一次性)
```

**增量更新 Token 消耗：**

| 场景 | 触发条件 | Token | 耗时 | 说明 |
|------|---------|:--:|:--:|------|
| `--update` | 改几行代码 | **0** | ~80s | 只重提取变更文件，复用域划分和标注 |
| `--update --recluster` | 新增模块/重构 | **0~$0.2** | ~3min | 仅对新域调 LLM 标注 |
| 完整重建 | 极少需要 | **~$0.3-1** | ~15min | 等同于首次构建 |

**结论：日常使用的 Token 成本为 0。LLM 只在首次构建和重大重构时消耗一次。**

### 11.2 Token 消耗的影响因素

```
                    ┌─────────────────────────────┐
                    │   影响构建时 Token 的因素      │
                    └─────────────────────────────┘

  项目规模 ───────────→ 影响 AST 提取时间，不影响 Token
  (文件数, 代码行数)      extract() 始终 0 Token
  
  域数量 ────────────→ 影响 label_domains() Token
  (~25 vs ~50 个域)      每个域 ~2,500 输入 → 25 域 = ~62,500 Token
                                              50 域 = ~125,000 Token
  
  语言类型 ──────────→ 影响 api_mapper() 复杂度，不影响 Token
  (Java vs JS vs Go)     不同语言的注解格式不同，但都是零 Token 解析
  
  是否首次构建 ───────→ 最大影响因素
  (首次 vs --update)     首次: ~$1, 后续: $0
  
  LLM 后端 ───────────→ 影响成本但不影响质量
  (Claude Haiku vs Sonnet) Haiku: ~$0.3, Sonnet: ~$1
                          Sonnet 生成的描述质量更高
```

### 11.3 Agent 查询时 Token 消耗

**已详细分析于 7.4 节。概要：**

| 查询深度 | 典型场景 | Agent 读取的文件 | Token | 占比 |
|:--:|------|------|:--:|:--:|
| 浅 | 查域概要、字段映射 | index.md + summary.md | **400-1,500** | 80% |
| 中 | 精确代码查询、全量 API | Wiki + graphify query | **1,500-4,300** | 15% |
| 深 | 跨域依赖链、重构分析 | Wiki×3 + query + 源码 | **~11,000** | 5% |

**比 Graphify 直接查询节省 84-93%（日常场景）。**

### 11.4 深度跨域分析的速度

**最复杂场景：** "删单操作影响了哪些模块？各模块之间的调用链路是什么？"

```
Agent 执行步骤:

  Step 1: 读取 wiki/index.md ───────────── ~500 tokens, <1s
          定位 "订单管理" 域
          
  Step 2: 读取 wiki/订单管理/code-map.md ── ~500 tokens, <1s
          找到 deleteCustomerOrder() 方法
          
  Step 3: 读取跨域调用分析 ─────────────── ~600 tokens, <1s
          (已在构建时预计算，存在业务点详情中)
          → 物流管理、库存管理、事件处理
          
  Step 4: graphify path "deleteCustomerOrder" 
          "restoreStock" ────────────────── 0 Token, ~8s
          沿 calls 边 BFS 追踪调用链
          
  Step 5: graphify path "deleteCustomerOrder" 
          "cancelShipment" ───────────────── 0 Token, ~7s
          
  Step 6: 读取 3 个受影响域的 summary.md ── ~2,400 tokens, <3s
          理解每个域的业务含义
          
  Step 7: LLM 综合分析 ────────────────── ~5,000 tokens 输入, ~30s
          生成跨域影响报告
          
  ────────────────────────────────────────
  总计: ~9,000 tokens, ~50 秒
```

**速度瓶颈分析：**

| 步骤 | 耗时 | 瓶颈 | 可优化？ |
|------|:--:|------|:--:|
| Wiki 页面读取 | <5s | 文件 I/O | ❌ 已经很快 |
| graphify BFS 遍历 | ~15s (2次) | 34K 节点图遍历 | ⚠️ 可通过子图索引优化 |
| LLM 综合分析 | ~30s | LLM 推理速度 | ❌ 取决于 API |
| **总耗时** | **~50s** | graph 遍历 + LLM | |

**对比 Graphify 同样场景**：

```
Graphify 深度跨域分析:
  graphify query "deleteCustomerOrder" → BFS 全图
  → 返回 ~8,000 tokens 噪音子图
  → Agent 人工筛选有效节点
  → 多次 query 追踪每条调用链
  → LLM 综合分析
  总计: ~15,000 tokens, ~90-120 秒
```

### 11.5 质量和速度综合评估

**质量维度：**

| 维度 | 评分 | 说明 |
|------|:--:|------|
| **业务域划分准确性** | ⭐⭐⭐⭐ | 包路径 + import 聚合，OPS 项目预期 80%+ 合理。不如人工划分精确，但远好于 Louvain |
| **LLM 标注质量** | ⭐⭐⭐⭐ | Haiku 够用，Sonnet 更好。受限于类名包含的语义信息量，不读全部源码 |
| **API 映射准确性** | ⭐⭐⭐⭐⭐ | URL 精确匹配 + HTTP 方法，确定性 100%。参数名模糊匹配 ~90% |
| **字段映射准确性** | ⭐⭐⭐⭐ | 注解解析 100% 准确。DTO→Entity 字段名匹配 ~85%，需人工补充 |
| **跨域调用完整性** | ⭐⭐⭐ | 依赖 AST calls 边，接口调用可能漏。间接调用链默认不展开 |
| **Wiki 文档可读性** | ⭐⭐⭐⭐⭐ | LLM 生成 + 人工定稿，预期远超 Graphify 的方法名拼凑 |
| **Agent 查询精度** | ⭐⭐⭐⭐⭐ | Wiki scope reduction 将搜索范围缩小 50x，大幅提升精度 |

**速度维度：**

| 场景 | 耗时 | 评分 | 对比 Graphify |
|------|:--:|:--:|------|
| 首次构建 | ~12-15min | ⭐⭐⭐ | 相同（AST 提取相同） |
| 增量更新 | ~80s | ⭐⭐⭐⭐⭐ | 快 4-5x |
| 浅层 Agent 查询 | <3s | ⭐⭐⭐⭐⭐ | 快 10x+（无需图遍历） |
| 中层 Agent 查询 | ~10s | ⭐⭐⭐⭐ | 快 2-3x（scope 缩小后图遍历更快） |
| 深度跨域分析 | ~50s | ⭐⭐⭐ | 快 1.5-2x |
| Wiki 页面浏览 | <1s | ⭐⭐⭐⭐⭐ | 纯文件读取 |

**综合结论**：

```
                   质量
                    ▲
                    │   Graph-Wiki ★
                    │      (高精度, 中速构建)
                    │
                    │       人工文档
                    │       (最高精度, 最慢维护)
                    │
                    │               Graphify
                    │               (低精度, 中速构建)
                    │
                    └──────────────────────→ 速度
                    
  Graph-Wiki 的定位：在质量和速度之间取得平衡
  - 比人工文档快 100x（自动生成 vs 手动编写）
  - 比 Graphify 准 10x（业务语义 vs 链接密度）
  - 首次构建成本和 Graphify 一样（都受限于 AST 提取速度）
  - 日常使用 Token 成本几乎为零
```

**风险点**：

| 风险 | 影响 | 缓解 |
|------|:--:|------|
| 包结构不规范 → 域划分错误 | 🟡 中 | 手动调整 `graph-wiki.yaml` |
| 接口调用 AST 无法解析 → 跨域调用遗漏 | 🟡 中 | Spring Bean 命名约定模糊匹配 |
| LLM 标注幻觉 → 描述不准确 | 🟢 低 | 人工 review + 锁定机制 |
| Tree-sitter 版本兼容 | 🟢 低 | 锁定 graphify 主版本 |

---

## 十二、粒度设计：三层粒度模型

> **核心问题：Graph-Wiki 应该用类级还是方法级粒度？**
>
> 答案：**两者都要，但用在不同的层级上。** Graphify 的问题不是方法级粒度本身，而是对所有方法一视同仁——getter/setter/toString 和业务方法被同等对待。

### 12.1 为什么不能只用类级

**一个类可能包含多个业务点：**

```java
// 同一个 Controller 中，不同方法对应完全不同的业务操作
public class PurchaseController {
    
    // 业务点 1：采购申请提交
    @PostMapping("/apply")
    public Result submitApply(ApplyDTO dto) { ... }
    
    // 业务点 2：采购订单创建
    @PostMapping("/order")
    public Result createOrder(OrderDTO dto) { ... }
    
    // 业务点 3：采购发票导入（跨域操作）
    @PostMapping("/invoice/import")
    public Result importInvoice(MultipartFile file) { ... }
    
    // 噪音
    public PurchaseService getService() { return service; }
}
```

**如果只用类级**，以上三个不同的业务操作会被归为同一个"采购管理"域下的一个扁平列表，无法区分业务点。

### 12.2 为什么不能用 Graphify 的全量方法级

**Graphify 的方法级数据中，96.5% 是噪音：**

```
OPS 后端 34,298 个节点：
  匿名方法调用 (.build()/.getXxx()):  14,692  (42.8%)
  JDK 基础类型 (String/Integer):       3,108  (9.1%)
  getter/setter:                       大量
  toString/equals/hashCode:            大量
  ─────────────────────────────────────────
  真正的业务方法:                    ~5,000-8,000 (15-23%)
```

**直接用全量方法级聚类**，结果会和 Graphify 一样——被噪音淹没。

### 12.3 三层粒度模型

```
┌─────────────────────────────────────────────────────────┐
│  第 1 层：业务域（Domain）                                 │
│  ─────────────────────                                   │
│  粒度：类级聚类（~2,800 个业务类 → ~25 个域）              │
│  用途：业务大纲、域间依赖、Wiki 目录                        │
│  方法：包路径前缀 + import 聚合                            │
│  Token 成本：零                                           │
│                                                          │
│  示例：[[采购管理]] 包含 PurchaseController,              │
│        PurchaseService, PurchaseOrderMapper ...          │
├─────────────────────────────────────────────────────────┤
│  第 2 层：业务点（Business Point）                         │
│  ─────────────────────────────                           │
│  粒度：过滤后的方法级（~500-800 个）                        │
│  用途：业务操作清单、API 文档、字段数据流                    │
│  方法：提取 Controller public 方法 + Service public 方法   │
│        排除 getter/setter/toString/equals/hashCode       │
│  Token 成本：零（角色 + 注解过滤）                          │
│                                                          │
│  示例：[[采购申请提交]] = submitApply()                   │
│        → 调用链: Controller.submitApply()                │
│            → Service.submitApply()                       │
│            → Mapper.insertApply()                        │
├─────────────────────────────────────────────────────────┤
│  第 3 层：代码细节（Code Detail）                           │
│  ───────────────────────────                              │
│  粒度：全量 AST（~34,000 节点，保留在 graph.json）          │
│  用途：Agent 精确查询、方法级调用链追踪                      │
│  方法：graphify query / path / explain                   │
│  Token 成本：零（图遍历）                                  │
│                                                          │
│  示例：graphify query "supplierCode 在 importBindata    │
│        方法中的赋值逻辑"                                   │
└─────────────────────────────────────────────────────────┘
```

### 12.4 业务点的过滤规则

```python
def is_business_point_method(node, parent_class):
    """判断一个方法节点是否属于业务点"""
    label = node['label']
    
    # 排除匿名方法调用
    if label.startswith('.') or label.startswith('_'):
        return False
    
    # 排除 getter/setter
    if re.match(r'^(get|set|is)[A-Z]', label):
        return False
    
    # 排除 toString, equals, hashCode
    if label in ('toString()', 'equals()', 'hashCode()', 'clone()'):
        return False
    
    # 排除构造函数
    if label == parent_class['label'].replace('.java', '()'):
        return False
    
    # 只有 Controller/Service 的 public 方法才是业务点入口
    role = parent_class.get('_role', '')
    if role not in ('controller', 'service_impl', 'service_api'):
        return False
    
    # Controller 的 private 方法不是业务入口（但可能是业务实现）
    # ServiceImpl 的 private 方法是辅助方法，归入父业务点
    
    return True
```

**过滤效果**：

```
全量方法节点:                      34,298
  ↓ 排除匿名方法调用、JDK 类型等噪音
中等粒度（所有具名方法）:           ~12,000
  ↓ 排除 getter/setter/toString
实质方法:                           ~8,000
  ↓ 仅保留 Controller/Service public
业务点:                             ~500-800
```

### 12.5 三层粒度的 Wiki 呈现

**第 1 层 — wiki/index.md（业务大纲）**：
```markdown
# OPS 项目业务大纲
## 业务域
| 域 | 业务点数 | 核心类 |
|----|---------|--------|
| [[采购管理]] | 18 | PurchaseApplyController (3), PurchaseOrderController (5), ... |
| [[Bin数据管理]] | 12 | BinOrderController (4), BindataController (3), ... |
```

**第 2 层 — wiki/采购管理/summary.md（业务域）**：
```markdown
# 采购管理
## 业务点列表
### 采购申请
- [[采购申请提交]] — submitApply()
- [[采购申请查询]] — queryApply()
- [[采购申请审批]] — approveApply()

### 采购订单
- [[采购订单创建]] — createOrder()
- [[采购订单查询]] — queryOrder()

### 采购发票
- [[采购发票导入]] — importInvoice()
- [[采购发票校验]] — validateInvoice()
```

**第 3 层 — wiki/采购管理/业务点/采购申请提交.md（业务点详情，Phase 4+）**：
> v1.0 中业务点以域内列表形式展示在 summary.md 中，独立的业务点详情页面（含调用链、字段信息）列为 Phase 4+ 增强。
```markdown
# 采购申请提交
- **入口**: PurchaseController.submitApply()
- **调用链**:
  1. PurchaseController.submitApply(ApplyDTO)
  2. → PurchaseApplyService.submitApply(ApplyDTO)
  3.   → PurchaseApplyMapper.insert(ApplyEntity)
  4.   → InventoryService.reserveStock()  ← 跨域调用 [[库存管理]]
- **涉及字段**: (见 field-map.json)
- **API**: POST /api/purchase/apply
```

### 12.6 弊端分析

| 弊端 | 严重度 | 缓解措施 |
|------|:--:|------|
| 业务点数量多（~500）→ Wiki 页面多 | 🟡 中 | 默认折叠为域内列表，点击展开 |
| 一个类跨多个域的处理 | 🟡 中 | 按方法分别归属（Controller public 方法各归其域） |
| 不支持子域嵌套（如"采购管理/采购订单"） | 🟡 中 | v1.0 为扁平域结构，子域嵌套列为 Phase 4+ 优化项 |
| 方法名不够语义化 | 🟢 低 | LLM 可从方法名 + 注解推断中文名称 |
| 过滤规则可能遗漏边缘情况 | 🟢 低 | 提供手动调整 `graph-wiki.yaml` 配置 |
| 第 3 层仍依赖 graph.json 的全量噪音 | 🟡 中 | 为 graph.json 增加过滤视图查询接口 |
| 域边界稳定性假设未经验证 | 🟡 中 | 基于包路径聚类原理的理论推断，需在 Phase 4 实际 A/B 验证 |

### 12.7 跨域调用分析：一个方法调用多个模块

> **场景：** 客户订单删单方法 `deleteCustomerOrder()`，上百行代码，内部调用了物流指令模块、库存模块、接单表状态更新、事件发布、日志写入。Graph-Wiki 能否识别到这些跨域调用？

**答案：可以，且零 Token 成本。**

**原理**：graph.json 保留了两类关键边，只需一次 NetworkX 图遍历即可完成分析：

```
                ┌─────────────────────────────────┐
                │  deleteCustomerOrder()           │  ← 第 2 层：业务点方法
                │  (CustomerOrderService.java)     │
                └──────────┬──────────────────────┘
                           │
              ┌────────────┼────────────┬────────────┬──────────┐
              │ calls      │ calls      │ calls      │ calls    │ calls
              ▼            ▼            ▼            ▼          ▼
        cancelShipment  restoreStock  updateStatus  publish*   writeLog
        (LogisticsSvc)  (InventorySvc) (OrderMapper) (EventPub) (LogSvc)
              │            │            │            │          │
              │ contains   │ contains   │ contains   │ contains │ contains
              ▼            ▼            ▼            ▼          ▼
        LogisticsSvc   InventorySvc  OrderMapper  EventPub   LogSvc
              │            │            │            │          │
              │ domain     │ domain     │ domain     │ domain   │ role
              ▼            ▼            ▼            ▼          ▼
         物流管理域     库存管理域    订单管理域    事件处理域  基础设施
          (跨域!)       (跨域!)       (域内)      (跨域!)    (非业务)
```

**实现算法**（`business_cluster()` 的 Step 4，新增）：

```python
def analyze_method_cross_domain(method_node_id, G, domains):
    """
    输入: 一个方法节点 ID + 图 + 域划分
    输出: 该方法调用了哪些域
    
    零 Token 成本，纯图遍历
    """
    # 1. 沿着 calls 边，找到该方法调用的所有方法
    called_methods = set()
    for src, tgt, data in G.edges(method_node_id, data=True):
        if data.get('relation') == 'calls':
            called_methods.add(tgt)
    
    # 2. 对每个被调用的方法，反向沿 contains 边找父类
    parent_classes = {}
    for method_id in called_methods:
        for src, tgt, data in G.edges(method_id, data=True):
            if data.get('relation') == 'contains' and src != method_id:
                # src 是包含此方法的 class
                parent_classes[method_id] = src
                break
    
    # 3. 将 class 映射到业务域
    domain_calls = defaultdict(list)
    domain_internal = []
    infrastructure = []
    
    for method_id, class_id in parent_classes.items():
        domain = get_domain_for_class(class_id, domains)
        method_label = G.nodes[method_id].get('label', method_id)
        
        if domain is None:
            infrastructure.append(method_label)  # 工具类/日志等
        elif domain == current_domain:
            domain_internal.append(method_label)  # 域内调用
        else:
            domain_calls[domain].append(method_label)  # 跨域调用
    
    return {
        'cross_domain': dict(domain_calls),
        'internal': domain_internal,
        'infrastructure': infrastructure,
    }
```

**在 Wiki 中的呈现**（业务点详情页）：

```markdown
# 客户订单删单
- **入口**: CustomerOrderController.deleteOrder()

## 跨域调用分析
| 域 | 方法 | 说明 |
|----|------|------|
| [[物流管理]] | LogisticsService.cancelShipment() | 取消已创建的物流指令 |
| [[库存管理]] | InventoryService.restoreStock() | 恢复订单锁定的库存 |
| [[事件处理]] | EventPublisher.publishOrderCancelled() | 发布订单取消事件 |

## 域内调用
- OrderStatusMapper.updateStatus() — 更新接单表状态为 CANCELLED

## 基础设施调用
- LogService.writeLog() — 记录操作日志
- TransactionManager.commit() — 事务提交
```

**Agent 查询示例**：

```
用户："删单操作影响了哪些模块？"

Agent 读取 wiki/订单管理/业务点/客户订单删单.md
  → 直接返回跨域调用列表：
    - 物流管理（取消发货）
    - 库存管理（恢复库存）
    - 事件处理（发布删单事件）

用户："哪些业务点会触发库存恢复？"

Agent 遍历所有业务点的跨域调用表
  → 找到调用 InventoryService.restoreStock() 的业务点：
    - 订单管理 / 客户订单删单
    - 订单管理 / 退货处理
    - ...
```

**局限性**：

| 局限 | 原因 | 影响 |
|------|------|:--:|
| 接口调用无法解析 | `logisticsService.cancelShipment()` 如果 `logisticsService` 是接口类型，tree-sitter 可能无法解析到具体实现类 | 🟡 中 — 会丢失部分跨域调用 |
| Lambda/Stream 调用不可见 | `items.stream().map(Item::getXxx)` 的调用链在 AST 层面不形成 calls 边 | 🟢 低 — Lambda 一般不是跨域入口 |
| 反射/动态代理不可见 | 运行时动态调用的方法在 AST 中不存在 | 🟢 低 — 业务代码中少见 |
| 间接调用链不追踪 | 只追踪 1 跳 calls，不递归（避免爆炸）。A→B→C 中 C 不会被标记为 A 的跨域调用 | 🟡 中 — 可通过 `--deep` 模式递归追踪 |

**缓解措施**：
- 接口调用：通过 Spring Bean 命名约定（`@Service("logisticsService")`）和实现类命名约定（`LogisticsServiceImpl`）进行模糊匹配
- 间接调用链：`graph-wiki query --dfs` 可以递归追踪完整调用链（Agent 查询时按需触发）
- 提供手动补充跨域调用的配置入口（`wiki/{domain}/cross-domain.yaml`）

---

## 十三、与 Graphify 的差距分析

### 13.1 Graphify 能做到、Graph-Wiki 做得更好的

| 能力 | Graphify | Graph-Wiki | 提升幅度 |
|------|---------|-----------|:--:|
| 聚类质量 | 1,935 社区（无业务语义） | ~25 域（LLM 标注） | 77:1 压缩 |
| Wiki 可读性 | 方法名拼凑 | LLM 业务描述 | 质的飞跃 |
| HTML 可视化 | 34K 节点乱麻 | ~25 节点域图 | 人类可用 |
| Agent 查询精度 | 全图搜索 | Wiki 定位 → 子图查询 | 范围缩小 ~50x |
| 增量更新 | 5-10 分钟 | ~80 秒 | 4-5x 加速 |
| 业务大纲 | 无 | 三层结构 | 从零到一 |

### 13.2 Graphify 能做到、Graph-Wiki 当前做不到的

| 能力 | Graphify 如何做 | Graph-Wiki 的差距 | 严重度 | 解决方案 |
|------|---------------|-----------------|:--:|------|
| **多语言 AST 提取** | tree-sitter 支持 30+ 语言 | 优先实现 Java + Vue，其他语言逐步支持 | 🟡 中 | 复用 graphify 的多语言能力，只是聚类模块先不做 |
| **语义文档提取** | Part B 子 Agent 从 .md/.pdf 中提取概念 | 当前 LLM 用途是标注域，不是从文档提取概念 | 🟡 中 | 可选功能：文档提取结果合并到域的 summary.md |
| **MCP Server** | graphify --mcp 启动 stdio MCP 服务 | 未设计 MCP 接口 | 🟡 中 | 后续版本：`graph-wiki serve --mcp` |
| **社区凝度分数** | cohesion_score() 量化社区内聚度 | 不做 Louvain，无此指标 | 🟢 低 | 可替代：域内 import 内聚度 = 域内 import / 总 import |
| **意外连接发现** | surprising_connections() | 依赖 Louvain 做跨社区桥接检测 | 🟡 中 | 替代方案：统计每个域的"被其他域 import 最多的类"，识别意外耦合 |
| **--watch 文件监听** | graphify --watch | 未设计 | 🟢 低 | 后续版本 |
| **视频/音频转录** | --whisper-model | Graph-Wiki 不做多媒体 | 🟢 低 | 不在目标场景内 |
| **Pipeline 并行化** | AST + 语义提取并行 | 当前是串行流水线 | 🟢 低 | AST 提取和聚类可并行化 |

### 13.3 Graph-Wiki 的独特优势（Graphify 做不到的）

| 能力 | 说明 |
|------|------|
| **业务域自动划分** | 包路径 + import 聚合 → 人类可理解的业务域（Graphify 的 Louvain 做不到） |
| **LLM 业务描述** | 每个域有 LLM 生成的业务摘要和流程描述（Graphify 只做关键词拼凑） |
| **字段级数据流追踪** | 前端 ← API ← DTO ← Entity ← DB 列的完整链路（Graphify 没有此功能） |
| **代码质量静态分析** | 大方法/大实体检测、循环复杂度（Graphify 不做） |
| **三层业务大纲** | Domain → BusinessPoint → CodeDetail 的分层导航 |
| **人工定稿保护** | summary.md 经人工确认后锁定，`--update` 不覆盖 |
| **Obsidian 兼容 Wiki** | 双向链接 MD 文档库，可直接作为 Obsidian vault |

---

## 十四、设计决策记录

### 决策 1：为何复用 graphify AST 而不是从零写 AST 提取

**选择**：复用 graphify 的 `detect()` / `extract()` / `build()`

**理由**：
- graphify 的 tree-sitter 解析器支持 30+ 种语言，经过大量项目验证
- 自研 AST 提取的成本远高于复用，且无差异化价值
- Graph-Wiki 的核心创新在聚类和标注，不在 AST 解析
- graphify 是 MIT 许可的 Python 包，可以自由使用

**权衡**：依赖一个外部包有版本兼容风险。缓解措施：锁定 graphify 主版本号。

### 决策 2：为何用包路径 + import 聚合替代 Louvain

**选择**：替换 Louvain 为包路径前缀 + import 聚合聚类

**理由**（数据驱动）：
- OPS 项目中 Louvain 产出 1,935 个社区，98% 以 Maven module 命名，无业务含义
- Java 包路径是开发者有意设计的层级结构，比链接密度更能反映业务意图
- `com.smc.smccloud.bin.*` → "bin" 域 — 这个映射在 99% 的情况下是正确的
- import 聚合作为补充：两个包前缀互相 import 密集 → 可能属于同一域

**权衡**：包结构不规范的项目效果可能差。缓解措施：提供 `graph-wiki.yaml` 手动映射配置。

### 决策 3：为何类/文件为最小粒度

**选择**：聚类的最小单位是类/文件；Wiki 包含类级代码地图和方法级业务点列表

**理由**（数据驱动）：
- OPS 项目中 42.8% 的节点是匿名方法调用，聚类时全是噪音
- 一个 Java 类对应 30-50 个图节点 → 类级视图压缩比 30:1 到 50:1
- 业务域划分不需要方法级精度，类级足够

**权衡**：Agent 需要方法级调用链时怎么办？graph.json 保留全量数据，Agent 可以通过 Wiki 定位域后，下钻到 graph.json 查询方法级关系。

### 决策 4：LLM 标注是一次性操作

**选择**：LLM 在首次构建时运行一次，`--update` 不复跑

**理由**：
- 业务域的名称和描述不会因为代码变更而频繁变化
- 每次 `--update` 都调 LLM 会增加成本和延迟
- 域名称锁定后，人工 review 的成果得以保留

**权衡**：如果新增了一个全新的业务域怎么办？`--recluster` 或 `graph-wiki label-only` 针对性重跑标注。

### 决策 5：rules.md 和 spec.md 不由 LLM 生成

**选择**：这两个文件初始为空占位，由人工填写

**理由**：
- 业务规则和需求规格是权威文档，LLM 不应越俎代庖
- LLM 生成的规则可能包含幻觉，误导业务决策
- summary.md 提供"这里大概做什么"，rules.md 提供"具体怎么做"——前者适合 LLM，后者适合人类

### 决策 6：Wiki 文档和代码放在同一仓库

**选择**：`wiki/` 目录默认放在项目根目录，和代码一起版本管理

**理由**：
- 代码变更时同步更新 Wiki（CI 自动化）
- PR review 时同时 review 业务影响
- 不引入额外的文档托管系统

**权衡**：Wiki 文件会增加仓库大小（但 Markdown 几乎不占空间）。

---

## 十五、项目结构

```
graph-wiki/
├── pyproject.toml                    ← 包定义
├── README.md
├── graph_wiki/
│   ├── __init__.py
│   ├── reuse.py                      ← 复用层（封装 graphify）
│   ├── cluster.py                    ← 业务域聚类（★核心）
│   ├── api_mapper.py                 ← 前后端接口对接
│   ├── field_mapper.py               ← 字段级数据流追踪
│   ├── impact.py                     ← Phase 4 结构化影响分析
│   ├── dream.py                      ← Phase 5 轻量 Dream Cycle 维护报告
│   ├── product_data.py               ← 产品化工作台前端 DTO 聚合层
│   ├── label.py                      ← LLM 标注（★核心）
│   ├── export.py                     ← Wiki 导出
│   ├── visualize.py                  ← 域级可视化
│   ├── pipeline.py                   ← CLI 入口
│   └── models.py                     ← 数据模型（Domain, Anchor, ...）
├── tests/
│   ├── test_cluster.py
│   ├── test_label.py
│   └── fixtures/                     ← 测试用的图数据
└── examples/
    └── ops-backend/                  ← OPS 项目的运行示例
```

---

## 十六、质量保障

### 16.1 测试策略

| 层级 | 位置 | 内容 | 频率 |
|------|------|------|------|
| 单元测试 | 各模块文件内（`if __name__ == "__main__":` 或 `pytest`） | 函数级输入/输出验证 | 每次代码变更 |
| 集成测试 | `tests/` 目录 | 对真实项目执行 `graph-wiki build`，验证完整链路 | 每个 Phase 结束 |
| 回归测试 | `tests/` 目录 | 对比两次 build 的域划分结果，检测意外漂移 | 聚类算法修改后 |
| 人工验证 | 人工 review | 抽查域划分是否合理、LLM 标注是否可用 | 每次发布前 |

集成测试目标项目类型：
- Java Spring Boot（Maven 多模块，~5,000 文件） — 测试包路径聚类
- Vue 管理后台（~400 文件） — 测试前端目录聚类
- 混合项目（Java 后端 + Vue 前端） — 测试 api_mapper 前后端匹配

### 16.2 错误处理

采用分层错误类型，每层有明确的异常基类：

```python
class PipelineError(Exception): pass          # 流水线通用错误

# 复用层
class DetectError(PipelineError): pass        # 文件检测失败（路径不存在、无权限）
class ExtractError(PipelineError): pass       # AST 提取失败（tree-sitter 解析错误）

# 聚类层
class ClusterError(PipelineError): pass       # 聚类失败
class NoBusinessNodesError(ClusterError): pass # 未找到业务节点（可能不是代码项目）
class TooFewAnchorsError(ClusterError): pass   # 锚点 < 3，无法形成有意义的域划分

# 标注层
class LabelError(PipelineError): pass         # 标注失败
class LlmApiError(LabelError): pass           # LLM API 调用失败
class LlmRateLimitError(LabelError): pass     # API rate limit 超限

# 导出层
class ExportError(PipelineError): pass        # 导出失败（磁盘满、权限不足）
```

优雅降级原则：
- `cluster.py` 失败 → 无法继续，报告错误退出
- `label.py` 失败 → 域保持未标注状态，Wiki 使用域 ID 作为名称
- `api_mapper.py` 失败 → api-docs.md 留空，不影响域 Wiki 生成
- `field_mapper.py` 失败 → data-flow.md 留空，不影响核心输出

### 16.3 安全考量

- **LLM API Key**：通过环境变量（`ANTHROPIC_API_KEY`/`OPENAI_API_KEY`）管理，从不写入代码或配置文件
- **源码采样**：`label.py` 的源码采样仅读取前 N 行，且排除以下文件：
  - 包含 `secret`/`password`/`token`/`key` 字段的配置文件
  - `.env` / `application-*.yml` 等配置
  - `**/resources/credentials.*` 等密钥文件
- **Wiki 输出**：生成的 Markdown 文件不包含源代码完整内容，仅包含类名和方法签名级别的摘要
- **Git 安全**：`wiki/**/*.md` 随代码一起版本管理（支持 PR review 时检查业务影响，与决策 6 一致）；`graph.json` 等大型中间文件通过 `.gitignore` 排除；`domain_graph.html`、`api-map.json`、`field-map.json` 由 CI 自动 commit 到仓库

---

## 十七、关联文档

- [Graphify 缺陷深度分析](../reference/2026-06-11%20Graphify%20缺陷深度分析.md) — 为什么要做 Graph-Wiki 的量化依据
- [LLM Wiki 设计原则](./llm-wiki-design-principles.md) — Part B > Part A 的方法论（计划中，待创建）
- [三层文档体系方案](./three-layer-document-system.md) — Backstage 应用层的设计（计划中，待创建）
- [市面工具对比](../reference/2026-06-11%20各知识库工具对比.md) — 竞品分析和市场定位
- [Graph-Wiki 路线图](./graph-wiki-roadmap.md) — 方案 A vs B 的决策过程（计划中，待创建）

---

> **本文档是 Graph-Wiki 项目的唯一设计真相源。任何后续的架构变更、接口调整、功能增减，都必须先更新本文档，再修改代码。**
