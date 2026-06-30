# Reuse 模块详细设计

> 对应总体设计：[第七章 7.1 节](../architecture/graph-wiki%20工程架构设计.md#41-复用层-reuse-layer)、[第六章 6.5 节](../architecture/graph-wiki%20工程架构设计.md#65-manifestjson-规范)、[第四章 4.6 节](../architecture/graph-wiki%20工程架构设计.md#46-graphjson-内部结构)
> 状态：详细设计阶段  
> 被依赖：`cluster.py`, `api_mapper.py`, `field_mapper.py`, `label.py`, `pipeline.py`

---

## 1. 模块职责

### 1.1 为什么需要复用层

Graph-Wiki 定位为 graphify 的**增量替代**而非完全重写。graphify 在文件检测（30+ 语言、tree-sitter AST 解析、.graphifyignore 支持）方面已经具备成熟且经过生产验证的能力。图构建能力仍可复用，但 v1.0 默认不会先构建完整 graphify 大图，而是在 extraction 之后先做业务级预过滤，再构建 `graph-lite.json`。直接复用这些能力可以：

- **节约开发时间**：避免重新实现 30+ 语言的 AST 解析器
- **保持兼容**：graphify 已有的 .graphifyignore、增量检测等机制可无缝继承
- **聚焦核心创新**：将开发资源集中在业务域聚类、LLM 标注、Wiki 导出等 Graph-Wiki 独有的能力上

### 1.2 复用什么，不重造什么

```
┌──────────────────────────────────────────────────────────────────┐
│                    复用（透传 graphify API）                       │
├──────────────────────────────────────────────────────────────────┤
│  graphify.detect.detect()                文件检测 + 分类           │
│  graphify.detect.detect_incremental()    增量文件检测（基于 hash） │
│  graphify.extract.extract()             tree-sitter AST 提取     │
│  graphify.extract.collect_files()        目录展开为文件列表        │
│  graphify.build.build_from_json()        完整 NetworkX 图构建（可选）│
│  graphify.build.build_merge()            完整图增量合并（后续）      │
│  graphify.cache.*                        AST 缓存（check/save）   │
├──────────────────────────────────────────────────────────────────┤
│                    替换（Graph-Wiki 自有实现）                      │
├──────────────────────────────────────────────────────────────────┤
│  graphify.manifest.*                     用 build_manifest() 替代  │
│  graphify.cluster.*                      用 business_cluster()     │
│  graphify.label.*                        用 label_domains()        │
│  graphify.export.*                       用 export_wiki()          │
├──────────────────────────────────────────────────────────────────┤
│                    新增（Graph-Wiki 独有）                         │
├──────────────────────────────────────────────────────────────────┤
│  filter_extraction_for_wiki()            build 前业务级预过滤       │
│  build_light_graph()                     Graph-Wiki 轻量图构建      │
│  build_manifest()                        自定义 manifest 格式      │
│  build_api_map() / build_field_map()     前后端 API / 字段映射     │
│  export_domain_html()                    域级可视化（D3.js）        │
└──────────────────────────────────────────────────────────────────┘
```

**核心原则**：graphify 是 Python 库，不是 CLI 工具。Graph-Wiki 通过 `import graphify` 直接调用其函数，不产生子进程、不依赖 graphify CLI。

### 1.3 模块边界

```
reuse.py 封装层
├── 公开接口（8 个函数）
│   ├── detect_corpus()        ← 文件检测入口
│   ├── extract_ast()          ← AST 提取入口
│   ├── filter_extraction_for_wiki() ← build 前预过滤入口
│   ├── build_light_graph()    ← 轻量图构建入口
│   ├── build_graph()          ← 完整图构建入口（可选）
│   ├── merge_graph()          ← 完整图增量合并入口（后续）
│   ├── save_graph_artifacts() ← 图产物持久化入口
│   └── build_manifest()       ← manifest 生成入口
│
├── 内部依赖
│   ├── graphify.detect        ← 文件检测
│   ├── graphify.extract       ← AST 提取
│   ├── graphify.build         ← 图构建/合并
│   ├── graphify.cache         ← AST 缓存
│   └── graphify.manifest      ← 仅用 detect_incremental
│
└── 不依赖
    ├── graph_wiki.models      ← 复用层不依赖业务数据模型
    ├── graph_wiki.cluster     ← 复用层是下游模块的基础
    └── networkx (但返回它)    ← 返回 nx.Graph 但不使用 nx 内部功能
```

---

## 2. 完整接口定义

### 2.1 `detect_corpus()`

```python
def detect_corpus(
    root: Path,
    *,
    follow_symlinks: bool | None = None,
    extra_excludes: list[str] | None = None,
) -> dict:
    """文件检测：扫描项目目录，对文件按类型分类。

    Args:
        root: 项目根目录（绝对路径）。
        follow_symlinks: 是否跟随符号链接。None 时自动检测。
        extra_excludes: 额外的排除模式列表（附加到 .graphifyignore 之后）。

    Returns:
        dict: 包含以下字段的检测结果：
            - files: dict[str, list[str]]，按 FileType 分组的文件路径列表
                {code: [...], doc: [...], paper: [...], image: [...], video: [...]}
                其中 code 包含所有可解析的源代码文件（.java/.vue/.js/.ts/.py/.go 等）
            - total_files: int，文件总数
            - total_words: int，文档类文件的总词数
            - by_extension: dict[str, int]，按扩展名统计的文件数
            - gitignore_files: list[str]，被 .gitignore 忽略的文件数

    Raises:
        DetectError: 根目录不存在、无读取权限
        FileNotFoundError: root 路径不存在（通过 graphify 抛出）

    性能:
        - 扫描 5,000 文件平均 < 1s（纯文件系统遍历）
        - 使用 os.walk + .graphifyignore 剪枝

    示例:
        >>> result = detect_corpus(Path("/workspace/myapp"))
        >>> result["total_files"]
        5200
        >>> len(result["files"]["code"])
        3841
    """
```

### 2.2 `extract_ast()`

```python
def extract_ast(
    code_files: list[Path],
    *,
    parallel: bool = True,
    max_workers: int | None = None,
    cache_root: Path | None = None,
) -> dict:
    """AST 提取：对指定的源代码文件执行 tree-sitter 解析。

    这是 Graph-Wiki 最耗时的单步操作（对 5,000 文件项目约 40-60 秒）。
    支持 30+ 语言（通过 graphify 的 tree-sitter 语法定义）。

    接收目录和文件混合输入：
        - 传入目录时自动展开为文件列表（递归）
        - 传入文件时直接解析
        - graphify.extract.collect_files() 负责展开目录

    Args:
        code_files: 要解析的源代码文件或目录列表。
        parallel: 是否使用多进程并行解析（默认 True）。
        max_workers: 并行 worker 数。None 时使用 os.cpu_count()。
        cache_root: AST 缓存根目录。None 时不使用缓存。

    Returns:
        dict: 包含以下字段的提取结果：
            - nodes: list[dict]，AST 节点列表
                每个节点至少包含以下字段：
                {
                    "id": "smccloud_opsadapterapplication",   # 全局唯一 ID
                    "label": "OpsAdapterApplication.java",    # 节点标签（类名/方法名）
                    "file_type": "code",                      # 文件类型
                    "source_file": "ops-backend/.../OpsAdapterApplication.java",  # 源文件路径
                    "source_location": "L1",                  # 源文件中的位置
                    "_origin": "ast",                         # 来源标记
                    "norm_label": "opsadapterapplication.java" # 归一化标签（用于去重）
                }
            - edges: list[dict]，AST 边列表
                每条边至少包含以下字段：
                {
                    "source": "caller_id",  # 源节点 ID
                    "target": "callee_id",  # 目标节点 ID
                    "relation": "calls",     # 关系类型
                }
            - input_tokens: int，LLM 输入 token 数（仅语义提取时）
            - output_tokens: int，LLM 输出 token 数（仅语义提取时）

        nodes 中的节点分类（参考架构文档 §8.4）：
            - 类节点 (~2,800 个)：label 以 .java/.vue/.js 等后缀结尾
            - 方法节点 (~14,700 个)：label 以 '.' 或 '_' 开头
            - 类型引用节点 (~3,100 个)：label 为 String/Integer 等
            - 其他节点 (~13,700 个)：字段、DTO、VO、Enum、Config 等

        edges 中的边类型（参考架构文档 §8.4）：
            - imports (11,335 条)：类与类之间的 import 关系
            - contains (4,645 条)：类包含方法/字段
            - calls (14,542 条)：方法调用方法
            - references (41,450 条)：方法引用类型
            - inherits / implements：继承与实现关系

    Raises:
        ExtractError: tree-sitter 解析失败、语言不支持
        FileNotFoundError: code_files 中存在不存在的路径

    性能:
        - 12 workers 并行解析
        - 对 5,000 文件 Java 项目约 40-60 秒
        - 使用 cache_root 可缓存已解析文件的 AST 结果
    """
```

### 2.3 `filter_extraction_for_wiki()`

```python
def filter_extraction_for_wiki(
    extraction: dict,
    root: Path,
    *,
    keep_business_methods: bool = True,
) -> dict:
    """从 graphify extraction 中筛出 Graph-Wiki v1.0 需要的节点和边。

    这是大项目可承载性的关键入口：过滤发生在 NetworkX 图构建之前，
    避免先生成 20 万级节点/百万级边完整图再降噪。

    Args:
        extraction: extract_ast() 的返回值（含 nodes, edges）。
        root: 项目根目录，用于识别 target/classes、dist、node_modules 等输出目录。
        keep_business_methods: 是否保留 Controller/Service public 业务方法候选节点。

    Returns:
        dict: filtered_extraction，仍保持 graphify extraction 结构：
            {
              "nodes": [...],
              "edges": [...],
              "input_tokens": 0,
              "output_tokens": 0,
              "meta": {
                "original_nodes": 207000,
                "filtered_nodes": 18000,
                "original_edges": 7200000,
                "filtered_edges": 160000
              }
            }

    保留节点:
        - code file/class/component 节点
        - Controller / Service / Mapper / DAO / Adapter 候选节点
        - public 业务方法候选节点
        - DTO / Entity / VO 候选节点

    剔除节点:
        - JDK / 浏览器 / 框架基础类型
        - getter/setter/toString/hashCode/equals
        - Vue 生命周期方法
        - 匿名调用节点，如 .build()、.then()、.map()
        - target/classes、logs、node_modules、dist、build 输出目录

    保留边:
        - imports / imports_from
        - calls
        - contains
        - implements
        - inherits
    """
```

### 2.4 `build_light_graph()`

```python
def build_light_graph(
    filtered_extraction: dict,
    *,
    directed: bool = False,
) -> nx.Graph:
    """基于预过滤 extraction 构建 Graph-Wiki 轻量图。

    轻量图是 v1.0 的默认聚类输入和 Wiki 数据后端，默认写出为
    graph-lite.json。它保留业务域划分、业务点提取、API/字段映射
    所需的结构关系，但不保留完整 graphify 噪音节点。

    Returns:
        nx.Graph: 节点和边规模应满足 v1.0 大项目约束：
            - 节点建议 < 20,000
            - 边建议 < 200,000
    """
```

### 2.5 `build_graph()`

```python
def build_graph(
    extraction: dict,
    *,
    directed: bool = False,
    root: Path | None = None,
) -> nx.Graph:
    """构建完整 NetworkX 图：将 AST 提取结果序列化为可查询的完整图结构。

    这是 graphify 流水线中 build 步骤的直接封装。v1.0 中它是可选后端，
    不再是默认聚类/Wiki 生成路径。大项目默认应调用
    filter_extraction_for_wiki() + build_light_graph()。

    Args:
        extraction: extract_ast() 的返回值（含 nodes, edges）。
        directed: 是否构建有向图。Graph-Wiki 默认使用无向图（
            因为聚类只需要 import 关系，不关心方向）。
        root: 项目根目录。传入时，source_file 路径会被相对化，
            使 graph.json 可移植（跨机器/跨检出位置）。

    Returns:
        nx.Graph: NetworkX 无向图（当 directed=False 时）。
                  NetworkX DiGraph（当 directed=True 时）。

        图的节点属性（每个节点是一个 dict）:
            - id: str — 全局唯一节点 ID（graphify 自动生成）
            - label: str — 节点显示标签
                - 类节点: "OpsAdapterApplication.java"
                - 方法节点: ".main()"
                - 字段节点: "supplierCode"
                - 类型引用: "String"
            - file_type: str — 文件类型 ("code" / "doc" / 或缺失)
            - source_file: str — 源文件路径（相对或绝对，取决于 root 参数）
            - source_location: str — 源码位置，如 "L1"
            - _origin: str — 来源标记 ("ast" / "semantic")
            - norm_label: str — 归一化标签（用于去重）

        图的边属性（每条边是一个 dict）:
            - relation: str — 关系类型
                - "imports"：类 A import 类 B
                - "contains"：类包含方法/字段
                - "calls"：方法调用方法
                - "references"：方法引用类型
                - "inherits"：类继承父类
                - "implements"：类实现接口
            - confidence: str — 置信度 ("EXTRACTED" / "INFERRED")
            - confidence_score: float — 置信度分数 (0.0 ~ 1.0)
            - weight: float — 边权重 (默认 1.0)

    Raises:
        ExtractError: extraction 格式无效（缺少 nodes/edges 字段）

    注意:
        返回的 nx.Graph 中的 community 属性是 graphify 的 Louvain 聚类结果，
        Graph-Wiki 不使用此属性。它由 cluster.py 的 filter_noise() 完全忽略。

    示例:
        >>> extraction = extract_ast([Path("src/main/java/MyApp.java")])
        >>> G = build_graph(extraction)
        >>> G.number_of_nodes()
        34298
        >>> G.number_of_edges()
        87779
    """
```

### 2.6 `merge_graph()`

```python
def merge_graph(
    new_extraction: dict,
    graph_path: str | Path = "graph.json",
    *,
    prune_sources: list[str] | None = None,
    directed: bool = False,
) -> nx.Graph:
    """增量合并：将新提取的 AST 节点/边合并到现有的图结构中。

    用于 graph-wiki update 命令。只重解析变更的文件，然后将新的
    节点/边合并到原有的 graph.json 中，同时移除旧版本的节点。

    Args:
        new_extraction: extract_ast() 对变更文件的返回结果。
        graph_path: 现有 graph.json 的路径。默认在当前目录查找。
        prune_sources: 需要移除的源文件路径列表（已删除或已变更的文件）。
            merge_graph 会移除所有 source_file 在此列表中的节点和边，
            再合并新的节点和边。
        directed: 是否构建有向图（应与 build_graph 保持一致）。

    Returns:
        nx.Graph: 合并后的完整图（包含原有未变更节点 + 新增节点）。

    实现机制（透传 graphify.build.build_merge）:
        1. 从 graph_path 加载现有的 graph.json
        2. 如果提供了 prune_sources，从图中移除这些文件对应的节点和边
        3. 将 new_extraction 中的节点和边合并到图中
        4. 执行节点去重（基于 norm_label）
        5. 返回合并后的图

    Raises:
        FileNotFoundError: graph_path 不存在（首次 build 不应调用 merge_graph）
        ExtractError: new_extraction 格式无效

    性能:
        - 对单文件变更约 60 秒（主要是加载和重写 57MB graph.json 的 I/O 开销）
        - 节点去重约 1-2 秒

    使用场景:
        >>> # graph-wiki update 的典型用法
        >>> changed = detect_incremental(root)
        >>> if changed["new_files"]["code"]:
        ...     extraction = extract_ast([Path(f) for f in changed["new_files"]["code"]])
        ...     G = merge_graph(
        ...         extraction,
        ...         "graphify-out/graph.json",
        ...         prune_sources=changed["new_files"]["code"] + changed.get("deleted_files", [])
        ...     )
    """
```

### 2.7 `save_graph_artifacts()`

```python
def save_graph_artifacts(
    *,
    light_graph: nx.Graph,
    output_dir: Path,
    full_graph: nx.Graph | None = None,
) -> None:
    """持久化图产物。

    必须写出:
        - output_dir / "graph-lite.json"

    可选写出:
        - output_dir / "graph.json" 仅当 full_graph 不为 None

    注意:
        大项目默认不写完整 graph.json，避免 I/O 和内存成本成为
        Wiki 生成链路的硬依赖。
    """
```

### 2.8 `build_manifest()`

```python
def build_manifest(
    files: list[Path],
    domains: list[Domain],
    *,
    output_path: str | Path = "manifest.json",
) -> dict:
    """生成文件哈希清单（manifest.json），供增量更新检测变更。

    Graph-Wiki 的 manifest 格式不同于 graphify 的扁平格式。
    Graph-Wiki 需要记录每个文件所属的域和域的版本号，以便：
    1. 检测文件是否变更（mtime + 内容 hash）
    2. 检测域边界是否变化（anchors_hash）
    3. 精确判断需要重建哪些下游产物

    Args:
        files: 项目中的所有源文件路径列表。
        domains: 聚类产出的 Domain 列表（含每个域包含的文件路径）。
        output_path: manifest 的输出路径（默认当前目录下的 manifest.json）。

    Returns:
        dict: manifest 数据（同时写入 output_path），结构如下：

        {
            "files": {
                "src/main/java/com/smc/smccloud/bin/controller/BinController.java": {
                    "hash": "a1b2c3d4e5f6...",        # 文件内容 SHA256
                    "last_modified": "2026-06-15T10:30:00Z",  # mtime ISO 格式
                    "domain": "bin-data"               # 所属域 ID
                },
                "src/api/binorder.js": {
                    "hash": "f6e5d4c3b2a1...",
                    "last_modified": "2026-06-15T09:00:00Z",
                    "domain": "bin-data"
                }
            },
            "domains": {
                "bin-data": {
                    "version": 3,                     # 域数据版本号
                    "anchors_hash": "abc123...",       # 锚点列表的 hash
                    "anchor_count": 45,                # 锚点数量
                    "last_build": "2026-06-15T10:30:00Z"  # 最后构建时间
                }
            },
            "meta": {
                "project": "OPS",                     # 项目名称
                "last_full_build": "2026-06-14T08:00:00Z",  # 最后全量构建时间
                "total_files": 5200                   # 文件总数
            }
        }

    Raises:
        ExportError: output_path 目录不可写、磁盘满

    用途:
        - graph-wiki update 对比文件 hash → 检测变更
        - domains.{id}.anchors_hash 变更 → 触发 --recluster
        - 仅 API/Entity 文件变更 → 仅重建 api_mapper / field_mapper

    每个域维护的 version 号策略:
        - 每次 build 时 version+1
        - 域文件添加/移除时 version+1
        - 域合并/拆分时 version+1
        - 仅域内文件内容变更不影响 version

    anchors_hash 计算:
        - 取域中所有锚点（Controller/Service/Mapper）的 source_file 路径
        - 按字母序排序后拼接为字符串
        - 计算 SHA256 的前 12 位作为 anchors_hash
        - 此值不变 → 域边界未变 → 无需重新聚类

    实现注意:
        本函数是 Graph-Wiki 自有实现，不依赖 graphify.manifest。
        graphify.manifest 使用扁平结构（{file_path: {mtime, hash}}），
        Graph-Wiki 需要多层级结构（含 domain 分组信息），两者不兼容。
    """
```

### 2.6 辅助函数

```python
def save_graph(
    G: nx.Graph,
    output_path: str | Path = "graph.json",
) -> None:
    """将 NetworkX 图保存为 graph.json（node-link 格式）。

    Args:
        G: 要保存的 NetworkX 图。
        output_path: 输出路径。默认当前目录下的 graph.json。

    保存的 graph.json 是 Graph-Wiki 后续模块的数据源，
    也是 Agent 查询的入口点（参见工程架构设计“graph.json 内部结构”）。

    graph.json 的磁盘占用：
        - 34K 节点 + 88K 边 = ~57MB
        - 此文件体积较大，应在 .gitignore 中排除
    """
    import networkx as nx
    data = nx.node_link_data(G, edges="links")
    Path(output_path).write_text(
        __import__("json").dumps(data, indent=2, ensure_ascii=False),
        encoding="utf-8",
    )


def load_graph(
    graph_path: str | Path = "graph.json",
) -> nx.Graph:
    """从 graph.json 加载 NetworkX 图。

    Args:
        graph_path: graph.json 的路径。

    Returns:
        nx.Graph: 加载的 NetworkX 图。

    加载 57MB 的 graph.json 约需 8-12 秒。
    """
    import json
    import networkx as nx
    data = json.loads(Path(graph_path).read_text(encoding="utf-8"))
    return nx.node_link_graph(data, edges="links", directed=False)


def detect_incremental(
    root: Path,
    manifest_path: str | Path = "manifest.json",
    *,
    extra_excludes: list[str] | None = None,
) -> dict:
    """增量文件检测：对比 manifest，找出新增/修改/删除的文件。

    封装 graphify.detect.detect_incremental()，但使用 Graph-Wiki 的
    manifest 路径（默认 graph-wiki-out/manifest.json 而非
    graphify-out/manifest.json）。

    Args:
        root: 项目根目录。
        manifest_path: Graph-Wiki manifest 路径。
        extra_excludes: 额外的排除模式。

    Returns:
        dict:
            - new_files: dict[str, list[str]]，按类型分组的新增/修改文件
            - deleted_files: list[str]，已删除的文件路径
            - unchanged_files: dict[str, list[str]]，未变更文件
            - new_total: int，变更文件总数
            - total_files: int，项目文件总数

    Examples:
        >>> inc = detect_incremental(Path("/workspace/myapp"))
        >>> inc["new_total"]
        3
        >>> len(inc["deleted_files"])
        1
    """
```

---

## 3. graphify API 依赖表

### 3.1 透传关系

| Graph-Wiki 函数 | graphify 函数 | 封装方式 | 差异 |
|-----------------|---------------|----------|------|
| `detect_corpus()` | `graphify.detect.detect()` | 直接调用 | 参数透传，返回结构不变 |
| `extract_ast()` | `graphify.extract.extract()` + `graphify.extract.collect_files()` | 先展开目录再提取 | 混合目录/文件输入 |
| `build_graph()` | `graphify.build.build_from_json()` | 直接调用 | 添加 `directed` 参数（默认 False） |
| `merge_graph()` | `graphify.build.build_merge()` | 直接调用 | 简化 `new_chunks` 为单参数 |
| `detect_incremental()` | `graphify.detect.detect_incremental()` | 包装调用 | 使用 Graph-Wiki 的 manifest 路径 |
| `save_graph()` | `nx.node_link_data()` + `json.dump()` | 自有实现 | — |
| `load_graph()` | `nx.node_link_graph()` + `json.load()` | 自有实现 | — |
| `build_manifest()` | — | **自有实现** | 格式不同于 graphify.manifest |

### 3.2 为什么不复用 graphify.manifest

| 维度 | graphify.manifest | Graph-Wiki build_manifest |
|------|-------------------|--------------------------|
| 结构 | 扁平 dict：`{file_path: {mtime, ast_hash, semantic_hash}}` | 三层结构：`{files: {...}, domains: {...}, meta: {...}}` |
| 文件分组 | 无分组 | 按 domain 分组，每文件记录所属域 |
| 域信息 | 无 | 含 version、anchors_hash、anchor_count |
| 用途 | 仅检测文件变更 | 检测文件变更 + 域边界变化 + 下游产物版本管理 |
| 扩展性 | 仅 AST 级 | 支持模块级增量（域稳定不变时跳过聚类） |

### 3.3 未使用但可用的 graphify API

| graphify API | 原因 | 何时可能使用 |
|---|---|---|
| `graphify.cache.check_semantic_cache()` | 第一阶段不需要语义缓存 | Phase 3+ 考虑启用 |
| `graphify.cache.save_semantic_cache()` | 同上 | Phase 3+ |
| `graphify.build.deduplicate_by_label()` | build_graph 内部已调用 | 需自定义去重策略时 |
| `graphify.build.validate_extraction()` | 调试用 | 调试提取问题时 |
| `graphify.extract.load_cached()` | 独立缓存文件粒度 | Phase 3+ 考虑 |

---

## 4. graph-lite.json / graph.json 节点边结构详解

`graph-lite.json` 与完整 `graph.json` 都采用 NetworkX node-link 结构，字段形态一致。区别在于：

| 产物 | 生成方式 | 默认用途 | 大项目默认写出 |
|------|----------|----------|:--:|
| `graph-lite.json` | `filter_extraction_for_wiki()` → `build_light_graph()` | 聚类、Wiki、质量评估、Agent 范围缩小 | 是 |
| `graph.json` | `build_graph()` | 精确方法级下钻、graphify query/path/explain 委托 | 否 |

除非特别说明，本章后续的节点/边结构优先指 `graph-lite.json`。

### 4.1 节点结构

每个节点是一个 dict，在 node-link JSON 的 `"nodes"` 列表中存储：

```
{
    "id": "smccloud_opsadapterapplication",   # <-- ① 全局唯一 ID
    "label": "OpsAdapterApplication.java",    # <-- ② 节点标签
    "file_type": "code",                      # <-- ③ 文件类型
    "source_file": "ops-backend/.../OpsAdapterApplication.java",  # <-- ④ 源文件
    "source_location": "L1",                  # <-- ⑤ 源代码位置
    "_origin": "ast",                         # <-- ⑥ 来源标记
    "community": 1428,                        # <-- ⑦ Graphify 社区 ID（Graph-Wiki 不使用）
    "norm_label": "opsadapterapplication.java" # <-- ⑧ 归一化标签（用于去重）
}
```

**字段说明**：

| 字段 | 类型 | 必需 | 说明 | 使用场景 |
|------|------|------|------|----------|
| `id` | str | 是 | graphify 自动生成的全局唯一 ID，通常为 `模块名_类名` | 所有模块都需要引用节点 |
| `label` | str | 是 | 节点显示标签。类节点以 `.java/.vue/.js` 等结尾；方法节点以 `.` 开头 | `cluster.filter_noise()` 通过后缀判断节点类型 |
| `file_type` | str | 是 | `"code"` 或缺失（Missing = "concept" 类型节点） | `filter_noise()` 只保留 `file_type='code'` 的节点 |
| `source_file` | str | 类节点必需 | 源文件的相对/绝对路径 | 包路径聚类（Step 3）、API/字段映射 |
| `source_location` | str | 否 | 源码位置 `L<行号>` | 调试用 |
| `_origin` | str | 否 | `"ast"`=AST 提取，`"semantic"`=语义提取 | 质量评估 |
| `community` | int | 否 | Graphify 的 Louvain 社区 ID | Graph-Wiki **不使用** |
| `norm_label` | str | 否 | 小写 + 去特殊字符的 label | 节点去重 |

### 4.2 节点分类（按 label 特征）

| 分类 | label 特征 | 数量（OPS 项目） | graph.json 中占比 |
|------|-----------|-----------------|-------------------|
| 类节点 | 以 `.java/.vue/.js/.ts/.py` 结尾 | ~2,800 | 8% |
| 方法节点 | 以 `.` 或 `_` 开头 | ~14,700 | 43% |
| 类型引用 | String/Integer/List 等 JDK 类型 | ~3,100 | 9% |
| 其他 | 字段、DTO、VO、Enum、Config | ~13,700 | 40% |
| **总计** | | **~34,298** | **100%** |

### 4.3 边结构

每条边是一个 dict，在 graph.json 的 `"links"` 列表中存储：

```
{
    "source": "class_A_id",       # <-- 源节点 ID
    "target": "class_B_id",       # <-- 目标节点 ID
    "relation": "imports",        # <-- 关系类型（最重要字段）
    "confidence": "EXTRACTED",    # <-- 置信度
    "confidence_score": 1.0,      # <-- 置信度分数
    "weight": 1.0                 # <-- 边权重
}
```

### 4.4 边类型一览

| relation 值 | 数量（OPS） | 语义 | 聚类层使用 | 业务点层使用 | 字段映射层使用 |
|-------------|-----------|------|-----------|-------------|---------------|
| `imports` | 11,335 | 类 A import 类 B | 是（域间依赖计算） | 否 | 否 |
| `contains` | 4,645 | 类包含方法/字段 | 否 | 是（方法→类映射） | 是（类→字段映射） |
| `calls` | 14,542 | 方法调用方法 | 否 | 是（跨域调用分析） | 否 |
| `references` | 41,450 | 方法引用类型 | 否 | 否 | 否 |
| `inherits` | - | 类继承父类 | 可能用于角色推断 | 否 | 否 |
| `implements` | - | 类实现接口 | 可能用于角色推断 | 否 | 否 |

### 4.5 各下游模块使用的图数据子集

```
graph-lite.json (预过滤后的轻量图)
    │
    ├── cluster.py 使用:
    │    类/文件节点 + 业务方法节点 + imports/calls/contains 边
    │    → 域划分 + 域间依赖计算
    │
    ├── api_mapper.py 使用:
    │    Controller 类节点 + 方法节点 + 注解信息
    │    前端 JS/Vue 类节点
    │    → 前后端 API 匹配
    │
    ├── field_mapper.py 使用:
    │    实体类节点 + 字段节点 + @TableField/@Column 注解
    │    DTO 类节点 + 字段节点
    │    → field-map.json
    │
    ├── Agent 常规导航使用:
    │    域内轻量节点 + 业务边
    │    → Wiki 定位后的范围缩小
    │
    ├── 完整 graph.json（可选）:
    │    全量节点 + 全量边
    │    → graphify query / path / explain 精确下钻
    │
    └── 噪音节点（不使用）:
        方法级匿名调用 (~14,700)
        类型引用节点 (~3,100 JDK 类型)
        references 边 (41,450 方法→JDK类型)
```

---

## 5. 增量更新流程

### 5.1 流程总图

```
graph-wiki update
    │
    │  detect_incremental(root)          ← 对比 manifest.json，找出变更文件
    │    │
    │    ├── 无变更 → 跳过，输出 "无文件变更"
    │    │
    │    ├── 仅前端 api/ 目录变更
    │    │      → 增量重建 api_mapper（重新解析变更的 JS/Vue 文件）
    │    │      → 刷新对应域的 api-docs.md
    │    │
    │    ├── 仅后端 Entity/DTO 变更
    │    │      → 增量重建 field_mapper（重新解析变更的实体类）
    │    │      → 刷新对应域的 data-flow.md
    │    │
    │    ├── 代码文件变更 + 域边界未变
    │    │      │
    │    │      ▼
    │    │  extract_ast(changed_files)    ← 只解析变更文件
    │    │      │
    │    │      ▼
    │    │  merge_graph(extraction, graph.json, prune_sources=deleted)
    │    │      │                     ← 合并到现有图
    │    │      │
    │    │      ▼
    │    │  刷新 code-map.md             ← 只更新变更域的文件列表
    │    │      │
    │    │      ▼
    │    │  重新导出 HTML（仅当域依赖变化）
    │    │
    │    └── 检测到新的包路径/模块
    │           │
    │           ▼
    │        提示: "检测到新的包路径，建议运行 --recluster"
    │        或 --recluster 时自动重新聚类
    │
    └── 输出变更摘要
```

### 5.2 变更分类决策树

```python
def classify_changes(manifest: dict, inc_result: dict) -> ChangeCategory:
    """分析变更类型，决定需要重建的下游产物。

    根据 manifest.json 中的 domain 信息和文件类型，判断变更的影响范围。
    """
    changed_code = inc_result["new_files"].get("code", [])
    changed_docs = inc_result["new_files"].get("doc", [])
    deleted = inc_result.get("deleted_files", [])

    if not changed_code and not changed_docs and not deleted:
        return ChangeCategory.NO_CHANGE

    # 检查是否有新的顶层包路径（新模块）
    known_packages = set()
    for file_info in manifest["files"].values():
        if "domain" in file_info:
            known_packages.add(extract_package_root(file_info))

    for f in changed_code:
        pkg = extract_package_root(f)
        if pkg and pkg not in known_packages:
            return ChangeCategory.NEW_MODULE_DETECTED

    # 检查是否有 API/Entity 文件变更
    has_api_change = any("src/api/" in f for f in changed_code)
    has_entity_change = any(
        "/entity/" in f or "/dto/" in f or "/vo/" in f
        for f in changed_code
    )
    has_core_change = any(
        not ("src/api/" in f or "/entity/" in f or "/dto/" in f or "/vo/" in f)
        for f in changed_code
    )

    if has_api_change and not has_core_change:
        return ChangeCategory.API_ONLY
    if has_entity_change and not has_core_change:
        return ChangeCategory.ENTITY_ONLY
    if has_core_change:
        return ChangeCategory.CORE_CHANGE

    return ChangeCategory.OTHER
```

### 5.3 性能对比

```
graph-wiki update (改 1 个文件):

  detect_incremental()     ~0.5s     仅 stat + hash 对比
  extract_ast()            ~2s       单文件 tree-sitter
  merge_graph()           ~60s       I/O 瓶颈（读/写 57MB graph.json）
  build_manifest()         ~1s       更新 manifest
  refresh code-map        ~5s        只更新变更域
  refresh HTML            ~10s       小图（~25 节点）
  ─────────────────────────────
  总计                    ~78s

对比 graphify --update (改 1 个文件，来自架构文档 §4.8):

  AST 重提取               ~2s
  build_merge             ~60s
  Louvain 重聚类          ~3min     ← 不可跳过
  导出                    ~2min
  ─────────────────────────────
  总计                    ~6min

加速比: ~78s vs ~360s → 约 4.6 倍
```

---

## 6. manifest.json 的生成和维护策略

### 6.1 生成时机

| 操作 | 生成/更新 manifest |
|------|------------------|
| `graph-wiki build`（首次） | 创建新 manifest |
| `graph-wiki build`（已有 manifest） | 全量更新 |
| `graph-wiki update` | 增量更新（仅变更文件） |
| `graph-wiki update --recluster` | 全量更新（含域版本变更） |

### 6.2 文件 hash 策略

```python
import hashlib
from datetime import datetime, timezone

def _file_hash(path: Path) -> str:
    """计算文件内容的 SHA256 摘要（前 16 位，足够检测变更）。"""
    return hashlib.sha256(path.read_bytes()).hexdigest()[:16]

def _file_mtime(path: Path) -> str:
    """返回文件修改时间的 ISO 8601 格式。"""
    ts = path.stat().st_mtime
    return datetime.fromtimestamp(ts, tz=timezone.utc).isoformat()
```

### 6.3 域版本升级规则

```python
def _update_domain_version(
    manifest: dict,
    domain_id: str,
    anchors: list[dict],
    force: bool = False,
) -> None:
    """更新指定域的版本信息。

    版本升级触发条件:
        - 域内文件添加/移除 → version += 1
        - 域合并/拆分 → version += 1
        - --recluster → version += 1
        - 仅文件内容变更 → version 不变（域边界未变）

    锚点 hash 变更 → 触发下游模块刷新:
        - anchors_hash 变化 → api_mapper / field_mapper 需要重建
        - version 不变但 anchors_hash 变 → 域内文件内容变更影响了锚点
    """
    domain_info = manifest["domains"].get(domain_id, {
        "version": 0,
        "anchors_hash": "",
        "anchor_count": 0,
    })

    # 计算新的锚点 hash
    anchor_files = sorted(a.get("source_file", "") for a in anchors)
    new_hash = hashlib.sha256(
        "|".join(anchor_files).encode()
    ).hexdigest()[:12]

    if new_hash != domain_info["anchors_hash"] or force:
        domain_info["version"] += 1
        domain_info["anchors_hash"] = new_hash
        domain_info["anchor_count"] = len(anchors)
        domain_info["last_build"] = datetime.now(timezone.utc).isoformat()

    manifest["domains"][domain_id] = domain_info
```

### 6.4 增量检测示例

```python
def has_domain_boundary_changed(manifest: dict, domain_id: str) -> bool:
    """检查域的边界是否发生变化（通过 anchors_hash）。"""
    return False  # 简化实现，实际在 update 流程中判断


def get_changed_domains(inc_result: dict, manifest: dict) -> set[str]:
    """返回变更文件所属的所有域 ID 集合。"""
    changed_domains = set()
    for f in inc_result["new_files"].get("code", []):
        domain = manifest.get("files", {}).get(f, {}).get("domain")
        if domain:
            changed_domains.add(domain)
    for f in inc_result.get("deleted_files", []):
        domain = manifest.get("files", {}).get(f, {}).get("domain")
        if domain:
            changed_domains.add(domain)
    return changed_domains
```

---

## 7. 错误处理

### 7.1 异常体系

```python
class ReuseError(PipelineError):
    """复用层通用错误基类。"""
    pass

class DetectError(ReuseError):
    """文件检测失败。
    可能原因：根目录不存在、无读取权限、磁盘错误。
    """
    pass

class ExtractError(ReuseError):
    """AST 提取失败。
    可能原因：tree-sitter 语法不支持该语言、文件编码问题、.graphifyignore 配置错误。
    """
    pass
```

### 7.2 优雅降级策略

- `detect_corpus()` 失败 → 无法继续，报告错误退出（pipeline 无法在没有文件清单的情况下继续）
- `extract_ast()` 失败 → 单文件解析失败不影响其他文件的提取。重新抛出异常时附带已成功解析的文件数
- `build_graph()` 失败 → 无法继续，报告错误退出（后续所有模块都依赖 graph.json）
- `merge_graph()` 失败 → 保持旧的 graph.json 不变，输出警告
- `build_manifest()` 失败 → 输出警告，不影响 build 流程。下次 build 时会重新生成

### 7.3 错误处理示例

```python
def safe_extract_ast(code_files: list[Path]) -> dict:
    """安全的 AST 提取，单文件失败不阻塞整体流程。"""
    successful_files = []
    failed_files = []
    all_nodes = []
    all_edges = []

    for f in code_files:
        try:
            result = extract_ast([f])
            all_nodes.extend(result.get("nodes", []))
            all_edges.extend(result.get("edges", []))
            successful_files.append(f)
        except Exception as e:
            failed_files.append((str(f), str(e)))

    if failed_files:
        print(f"警告: {len(failed_files)} 个文件提取失败，已跳过")
        for path, reason in failed_files:
            print(f"  {path}: {reason}")

    if not successful_files:
        raise ExtractError("所有文件提取失败")

    return {
        "nodes": all_nodes,
        "edges": all_edges,
        "successful_files": successful_files,
        "failed_files": [f[0] for f in failed_files],
    }
```

---

## 8. 依赖关系

```
reuse.py 依赖:
  ├── graphify.detect          ← Python 包
  │     ├── detect()
  │     └── detect_incremental()
  ├── graphify.extract         ← Python 包
  │     ├── extract()
  │     └── collect_files()
  ├── graphify.build           ← Python 包
  │     ├── build_from_json()
  │     └── build_merge()
  ├── graphify.cache           ← Python 包（可选）
  │     ├── check_semantic_cache()
  │     └── save_semantic_cache()
  ├── networkx                 ← Python 包
  ├── hashlib                  ← Python 标准库
  └── pathlib.Path             ← Python 标准库

被依赖:
  ├── pipeline.py              ← CLI 入口
  ├── cluster.py               ← 聚类模块
  ├── api_mapper.py            ← API 映射
  ├── field_mapper.py          ← 字段映射
  └── label.py                 ← LLM 标注
```

---

## 9. 测试用例

### 9.1 测试 1：`detect_corpus()` 文件分类

```python
# tests/test_reuse.py

def test_detect_corpus_classifies_files(tmp_path):
    """验证 detect_corpus 能正确识别不同类型的文件。"""
    # 准备测试目录
    src = tmp_path / "src"
    src.mkdir()
    (src / "App.java").write_text("public class App {}")
    (src / "App.js").write_text("function hello() {}")
    (src / "README.md").write_text("# Documentation")
    (src / "image.png").write_bytes(b"\x89PNG...")
    (src / "secret.env").write_text("PASSWORD=123")

    result = detect_corpus(tmp_path)

    code_files = [Path(f).name for f in result["files"]["code"]]
    doc_files = [Path(f).name for f in result["files"]["doc"]]

    assert "App.java" in code_files, "Java 文件应归类为 code"
    assert "App.js" in code_files, "JS 文件应归类为 code"
    assert "README.md" in doc_files, "Markdown 应归类为 doc"
    # image.png 不应在 code 或 doc 中
    assert "image.png" not in code_files
    assert "image.png" not in doc_files
    # graphify 内置了 .env 的默认排除（通过 .graphifyignore 等机制）
    assert result["total_files"] >= 4  # 至少检测到 4 个文件
```

### 9.2 测试 2：`extract_ast()` → `build_graph()` 完整链路

```python
def test_extract_and_build_graph(tmp_path):
    """验证从源代码到图构建的完整链路。"""
    # 准备一个简单的 Java 文件
    src = tmp_path / "src"
    src.mkdir()
    main_java = src / "Main.java"
    main_java.write_text("""
package com.example;

import java.util.List;

public class Main {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
""")

    # Step 1: 检测
    corpus = detect_corpus(tmp_path)
    code_files = [Path(f) for f in corpus["files"]["code"]]
    assert len(code_files) == 1
    assert code_files[0].name == "Main.java"

    # Step 2: 提取
    extraction = extract_ast(code_files)
    assert "nodes" in extraction
    assert "edges" in extraction

    # 类节点应该存在
    class_nodes = [n for n in extraction["nodes"]
                   if n.get("label") == "Main.java"]
    assert len(class_nodes) == 1, "应该有一个 Main.java 类节点"
    assert class_nodes[0]["file_type"] == "code"

    # 方法节点应该存在（getName / setName）
    method_nodes = [n for n in extraction["nodes"]
                    if n.get("label", "").startswith(".")]
    # getter/setter 也是方法节点，但会被后续 filter_noise 排除
    # 这里确认 AST 阶段方法节点存在

    # Step 3: 构建图
    G = build_graph(extraction)
    assert G.number_of_nodes() > 0
    assert G.number_of_edges() >= 0

    # 验证节点属性
    for nid, data in G.nodes(data=True):
        assert "id" in data
        assert "label" in data
```

### 9.3 测试 3：`merge_graph()` 增量合并

```python
def test_merge_graph_incremental(tmp_path):
    """验证增量合并在添加新文件时的正确行为。"""
    # 创建初始项目
    src = tmp_path / "src"
    src.mkdir()

    # 初始文件
    (src / "ServiceA.java").write_text("""
package com.example;
public class ServiceA {
    public void doSomething() {}
}
""")

    # Build 初始图
    corpus = detect_corpus(tmp_path)
    code_files = [Path(f) for f in corpus["files"]["code"]]
    extraction = extract_ast(code_files)
    G = build_graph(extraction)

    graph_path = tmp_path / "graph.json"
    import networkx as nx
    nx.node_link_data(G, edges="links")
    with open(graph_path, "w") as f:
        import json
        json.dump(nx.node_link_data(G, edges="links"), f)

    initial_node_count = G.number_of_nodes()

    # 新增文件
    (src / "ServiceB.java").write_text("""
package com.example;
import com.example.ServiceA;
public class ServiceB {
    public void execute() {
        new ServiceA().doSomething();
    }
}
""")

    # 重新检测（假设已知变更文件）
    new_extraction = extract_ast([src / "ServiceB.java"])

    # 增量合并
    merged_G = merge_graph(
        new_extraction,
        graph_path=str(graph_path),
        prune_sources=None,
    )

    # 验证新节点存在
    assert merged_G.number_of_nodes() > initial_node_count
    # ServiceB 的节点应该存在
    service_b_nodes = [
        nid for nid, data in merged_G.nodes(data=True)
        if "ServiceB" in data.get("label", "")
    ]
    assert len(service_b_nodes) > 0, "ServiceB 应出现在合并后的图中"
```

### 9.4 测试 4：`build_manifest()` 格式验证

```python
def test_build_manifest_structure(tmp_path):
    """验证 build_manifest 输出的结构和内容正确性。"""
    from graph_wiki.models import Domain

    # 准备测试数据
    files = [
        tmp_path / "src/main/java/com/example/UserController.java",
        tmp_path / "src/main/java/com/example/UserService.java",
        tmp_path / "src/main/java/com/example/User.java",
    ]
    for f in files:
        f.parent.mkdir(parents=True, exist_ok=True)
        f.write_text("// placeholder")

    domains = [
        Domain(
            id="user-management",
            name="用户管理",
            packages=["com.example"],
            anchors={
                "controllers": [{"label": "UserController.java", "file": str(files[0])}],
                "services": [{"label": "UserService.java", "file": str(files[1])}],
            },
            total_files=3,
        )
    ]

    manifest_path = tmp_path / "manifest.json"
    manifest = build_manifest(files, domains, output_path=manifest_path)

    # 验证结构
    assert "files" in manifest
    assert "domains" in manifest
    assert "meta" in manifest

    # 验证每个文件都有 hash/last_modified/domain
    for file_path, info in manifest["files"].items():
        assert "hash" in info, f"{file_path} 缺少 hash"
        assert "last_modified" in info, f"{file_path} 缺少 last_modified"
        assert "domain" in info, f"{file_path} 缺少 domain"
        assert isinstance(info["hash"], str)
        assert len(info["hash"]) == 16  # SHA256 前 16 位

    # 验证域信息
    assert "user-management" in manifest["domains"]
    domain_info = manifest["domains"]["user-management"]
    assert "version" in domain_info
    assert "anchors_hash" in domain_info
    assert "anchor_count" in domain_info
    assert domain_info["anchor_count"] == 2  # 1 controller + 1 service

    # 验证 meta
    assert "total_files" in manifest["meta"]
    assert manifest["meta"]["total_files"] == 3
```

### 9.5 测试 5：`detect_incremental()` 变更检测

```python
def test_detect_incremental_detects_changes(tmp_path):
    """验证增量检测能正确识别新增、修改、删除的文件。"""
    # 创建初始项目并生成 manifest
    src = tmp_path / "src"
    src.mkdir()
    (src / "App.java").write_text("public class App {}")
    (src / "Util.java").write_text("public class Util {}")

    # 先执行一次完整检测并生成 manifest
    from graph_wiki.models import Domain
    corpus = detect_corpus(tmp_path)
    manifest = build_manifest(
        [Path(f) for f in corpus["files"]["code"]],
        [],
        output_path=tmp_path / "manifest.json",
    )

    # 修改一个文件
    (src / "App.java").write_text("public class App { public void newMethod() {} }")

    # 添加一个新文件
    (src / "NewService.java").write_text("public class NewService {}")

    # 删除一个文件（记录路径后删除）
    deleted_path = src / "Util.java"
    deleted_path_str = str(deleted_path)
    deleted_path.unlink()

    # 执行增量检测
    inc = detect_incremental(
        tmp_path,
        manifest_path=tmp_path / "manifest.json",
    )

    changed_code = [f for f in inc["new_files"].get("code", [])]
    changed_names = [Path(f).name for f in changed_code]

    assert "App.java" in changed_names, "修改的文件应被检测到"
    assert "NewService.java" in changed_names, "新增文件应被检测到"
    assert "Util.java" not in changed_names, "已删除的文件不应出现在 new_files 中"
    assert deleted_path_str in inc.get("deleted_files", []), \
        "已删除文件应出现在 deleted_files 中"
```

---

## 10. 实施注意事项

### 10.1 当前状态

现有的 `reuse.py` 已实现 `detect_corpus()`、`extract_ast()`、`build_graph()`、`merge_graph()` 四个函数。但存在以下不足：

- `build_manifest()` 尚未实现（架构文档 §4.1 定义的接口，当前为 `pass`）
- 缺少辅助函数 `save_graph()`、`load_graph()`、`detect_incremental()`
- 缺少异常处理
- 缺少类型注解完善

### 10.2 下一步实现优先级

1. **实现 `build_manifest()`** — 增量更新的基础，pipeline 依赖
2. **添加异常处理** — 复用层异常基类 DetectError / ExtractError
3. **添加辅助函数** — `save_graph()` / `load_graph()` / `detect_incremental()`
4. **添加 AST 缓存支持** — 利用 graphify.cache 加速重复提取

### 10.3 与 graphify 版本兼容性

Graph-Wiki 开发阶段锁定 graphify 版本为 `>=0.x`（具体版本号待确认）。兼容性关注点：

- **graph.json node-link 格式**：graphify.build 输出的序列化格式稳定（NetworkX 标准格式）
- **detect_incremental 返回值**：`new_files`/`deleted_files` 字段名
- **manifest 路径**：Graph-Wiki 使用自己的 manifest 路径，不与 graphify 共享

### 10.4 大文件处理建议

graph.json 约 57MB 的 I/O 瓶颈是 `merge_graph()` 耗时 60 秒的主要原因。Phase 3 可考虑：

1. **懒加载**：只在需要时加载 graph.json 的特定子集（如通过 graphify query 接口）
2. **增量序列化**：只写出变更部分而非全量重写
3. **二进制格式**：考虑使用 Protocol Buffers 或 MessagePack 替代 JSON
4. **数据库后端**：使用 SQLite/Neo4j 存储图数据（大规模项目）

这些优化优先级较低，第一阶段先保证功能正确。
