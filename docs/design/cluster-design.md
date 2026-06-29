# Cluster 模块详细设计

> 对应总体设计：第四章 4.2 节、第十二章 三层粒度模型
> 状态：详细设计阶段（v2.0，重写）
> 依赖：`reuse.py`（graphify API 封装）、`models.py`（数据模型）
> 参考：`models-design.md`（数据模型设计，本模块使用的类型定义以此为据）

---

## 1. 模块职责

### 1.1 核心职责

将 reuse 层构建的 `graph-lite.json` 轻量图中的业务节点，通过**结构语义聚类**转化为**业务域**（~25 个），并从每个域中提取**业务点**（~500-800 个）。

```
输入:  nx.Graph (graph-lite.json, 已经过 build 前预过滤)
输出:  list[Domain] (~25 个业务域, 每域含 ~20-50 个锚点和 ~20-40 个业务点)
```

> 重要边界：大项目可承载性由 reuse 层的 `filter_extraction_for_wiki()` 首先保证。`cluster.filter_noise()` 是第二道防线，用于处理残余噪音和兼容旧图输入，不承担“从 20 万节点完整大图中降噪”的主要职责。

### 1.2 为什么聚类是 Graph-Wiki 的核心创新

Graph-Wiki 与 Graphify 的根本区别在于聚类方法：

| 维度 | Graphify (Louvain) | Graph-Wiki (包路径 + import 聚合) |
|------|-------------------|--------------------------------|
| **算法基础** | 链接密度（图拓扑） | 开发者意图（包路径层级）+ 编译期依赖（import） |
| **聚类粒度** | 方法/字段级（全量 ~34K） | 类/文件级（过滤后 ~2.8K） |
| **输出数量** | 1,935 社区（OPS 数据） | ~25 域 |
| **业务语义** | 98% 以 Maven module 命名，无业务含义 | 包路径直接反映业务分组 |
| **稳定性** | 代码变更可能导致 Louvain 全图重分 | 包路径不变则域边界不变 |
| **人类可理解** | 社区编号无意义 | `bin-data`、`purchase` 等 ID 直观 |

**数据驱动依据**（来自 OPS 项目实际分析）：
- Louvain 产出的 1,935 个社区中，98% 以 Maven module 命名（`ops-delivery.bin` 等），本质上是已有模块结构的复述
- 开发者通过包路径（`com.smc.smccloud.bin.*`）有意表达了业务分组意图
- Java 包路径 + import 关系是**人类可理解的业务语义**，而链接密度是**机器可算的数学指标**
- 包路径聚类在 99% 的情况下与企业真实的业务域划分一致

**核心创新点**："用开发者已有的设计意图（包结构）代替纯数学的链接密度分析"，使得聚类结果天然具有业务解释性。

### 1.3 与其他模块的关系

```
上游: reuse.py (detect → extract → filter_extraction_for_wiki → build_light_graph) → nx.Graph
                              │
                              ▼
                       cluster.py          ← 本模块
                              │
          ┌───────────────────┼───────────────────┐
          ▼                   ▼                   ▼
    api_mapper.py        label.py           export.py / visualize.py
    (读取 Domain.anchors) (改写 Domain.name,   (读取 Domain 全部字段)
                          BusinessPoint.display_name)
```

### 1.4 数据类依赖关系

本模块使用 `models.py` 中定义的以下类型（完整定义见 `models-design.md`）：

| 类型 | 用途 | 说明 |
|------|------|------|
| `Language` | 枚举，指导包路径解析策略 | pipeline 传入或在模块内自动检测 |
| `NodeRole` | 枚举，标记每个节点的业务角色 | 在 filter_noise 阶段写入 `_role` 属性 |
| `ANCHOR_ROLES` | 集合，定义哪些角色可作为锚点 | 在 extract_anchors 阶段筛选 |
| `Domain` | 数据类，代表一个业务域 | adjust_by_imports 创建，extract_business_points 追加 |
| `BusinessPoint` | 数据类，代表一个业务方法 | extract_business_points 创建 |

---

## 2. 接口定义

### 2.1 主接口

```python
def business_cluster(
    G: nx.Graph,
    root_path: Path,
    language: Language = Language.AUTO,
    merge_threshold: float = 0.3,
    min_domain_size: int = 5,
) -> list[Domain]:
```

**参数说明**：

| 参数 | 类型 | 默认值 | 含义 |
|------|------|--------|------|
| `G` | `nx.Graph` | 必填 | reuse.build_light_graph() 输出的轻量 NetworkX 图（无向图） |
| `root_path` | `Path` | 必填 | 项目根目录，用于语言检测和路径解析 |
| `language` | `Language` | `AUTO` | 项目语言。AUTO 时自动检测，检测失败默认为 JAVA |
| `merge_threshold` | `float` | `0.3` | 域合并密度阈值 (0-1)。越大越不易合并，越小越容易合并 |
| `min_domain_size` | `int` | `5` | 最小域大小（锚点数）。小于此值的域被吸收到最近的大域 |

**返回**：按 `anchors_count()` 降序排列的 `Domain` 列表。

**异常**：

| 异常类 | 抛出条件 |
|--------|---------|
| `NoBusinessNodesError` | filter_noise 后无节点：项目可能没有源代码文件 |
| `TooFewAnchorsError` | 锚点 < 3：无法形成有意义的域划分 |

### 2.2 五步子函数签名

```python
def filter_noise(G: nx.Graph) -> list[dict]
    # Step 1: 噪音过滤
    # 输入: nx.Graph (graph-lite.json，已预过滤)
    # 输出: list[dict] (业务节点)

def extract_anchors(business_nodes: list[dict]) -> list[dict]
    # Step 2: 锚点提取
    # 输入: 业务节点列表
    # 输出: list[dict] (~1.2K 锚点)

def cluster_by_package(anchors: list[dict], language: Language, root_path: Path) -> dict[str, list[dict]]
    # Step 3: 包路径聚类
    # 输入: 锚点列表
    # 输出: {domain_key: [anchor_nodes]} 候选域字典

def adjust_by_imports(candidates: dict, G: nx.Graph, merge_threshold: float, min_domain_size: int) -> list[Domain]
    # Step 4: import 聚合调整
    # 输入: 候选域字典 + 图
    # 输出: list[Domain] 最终域划分

def extract_business_points(domains: list[Domain], anchors: list[dict], G: nx.Graph) -> list[Domain]
    # Step 5: 业务点提取
    # 输入: 已划分的域
    # 输出: list[Domain] (含 business_points)
```

---

## 3. 五步法完整描述

### 3.0 大项目规模约束

`business_cluster()` 默认输入是 `graph-lite.json`。在进入聚类前，pipeline 应记录轻量图规模，并在 `build-report.json` 中输出：

| 指标 | v1.0 建议阈值 | 超限处理 |
|------|:--:|------|
| light graph 节点 | < 20,000 | warning；提示预过滤规则不足 |
| light graph 边 | < 200,000 | warning；提示 calls/imports 仍过密 |
| 业务域数量 | 5-50 | 超出则 warning；小项目可低于 5 |
| 单域锚点数 | 3-300 | 超出则 warning；提示域过粗或未合并 |
| 单域业务点数 | < 300 | 超出则 warning；提示需要子域或业务点过滤 |

这些阈值不是硬错误，目的是避免重新落入 Graphify 全图毛球问题：节点越多并不代表理解越好，Graph-Wiki 的价值来自压缩到人类可读的业务域。

### 3.1 总体流程

```
                    ┌──────────────┐
                    │  nx.Graph    │  graph-lite.json
                    └──────┬───────┘
                           │
              ┌────────────▼────────────┐
              │  filter_noise()         │  Step 1
              │  排除: 噪音/测试/        │
              │  JDK 类型/Config/Util   │
              └────────────┬────────────┘
                           │ 业务节点
              ┌────────────▼────────────┐
              │  extract_anchors()      │  Step 2
              │  保留: CONTROLLER/      │
              │  SERVICE/MAPPER/DAO    │
              └────────────┬────────────┘
                           │ ~1,200 锚点
              ┌────────────▼────────────┐
              │  cluster_by_package()   │  Step 3
              │  按包路径域键分组       │
              └────────────┬────────────┘
                           │ ~40-80 候选域
              ┌────────────▼────────────┐
              │  adjust_by_imports()    │  Step 4
              │  预定义合并 → 密度合并 → │
              │  小域吸收               │
              └────────────┬────────────┘
                           │ ~25 个 Domain
              ┌────────────▼────────────┐
              │  extract_business_      │  Step 5
              │  points()               │
              │  提取业务点 + 跨域调用   │
              └────────────┬────────────┘
                           │ list[Domain] (含 business_points)
```

### 3.2 Step 1: filter_noise — 噪音过滤

**输入**：`nx.Graph` 轻量图（来自 `graph-lite.json`）。旧版本完整图也可兼容，但不作为 v1.0 默认路径。

**输出**：`list[dict]` 业务节点列表。

**定位**：二次防线。主要噪音应已在 reuse 层的 `filter_extraction_for_wiki()` 中被剔除；本步骤负责统一角色分类、兜底排除测试/工具/配置节点，并兼容历史完整图输入。

**过滤规则链**（严格按优先级顺序，任一规则命中即排除）：

| 优先级 | 规则 | 条件 | 排除量级(OPS 参考) | 代码检查点 |
|--------|------|------|:-----------------:|-----------|
| 1 | 非代码文件 | `file_type != "code"` | ~2,000 | `is_business_relevant()` |
| 2 | 测试路径 | `source_file` 含 `/src/test/` | ~500 | `is_business_relevant()` |
| 3 | 匿名方法调用 | `label` 以 `.` 或 `_` 开头 | ~14,700 | `is_business_relevant()` |
| 4 | JDK 基础类型 | `label` 在 `NOISE_LABELS` 集合中 | ~3,100 | `is_business_relevant()` |
| 5 | getter/setter | `label` 匹配 `^(get\|set\|is)[A-Z]` | ~5,000 | `is_business_relevant()` |
| 6 | Object 方法 | `label` 为 `toString()`/`equals()`/`hashCode()`/`clone()`/`finalize()` | ~1,000 | `is_business_relevant()` |
| 7 | 无效后缀 | `label` 不以 `.java/.vue/.js/.ts/.jsx/.tsx/.py/.go` 结尾 | ~2,500 | `is_business_relevant()` |
| 8 | CONFIG 角色 | `classify_role()` 返回 `NodeRole.CONFIG` | ~500 | `filter_noise()` |
| 9 | UTIL 角色 | `classify_role()` 返回 `NodeRole.UTIL` | ~800 | `filter_noise()` |
| 10 | NOISE 角色 | `classify_role()` 返回 `NodeRole.NOISE` | ~0（兜底） | `filter_noise()` |

**`NOISE_LABELS` 完整列表**：

```python
NOISE_LABELS = {
    # JDK 基础类型
    "String", "Integer", "Long", "Double", "Float", "Boolean",
    "BigDecimal", "Date", "List", "Map", "Set", "Object", "Class",
    # Java 原生类型（方法返回/参数中出现的裸类型）
    "void", "int", "long", "boolean", "byte", "char",
    # Java 通用接口
    "Serializable", "Cloneable", "Comparable",
}
```

**`VALID_SUFFIXES` 完整列表**：

```python
VALID_SUFFIXES = (".java", ".vue", ".js", ".ts", ".jsx", ".tsx", ".py", ".go")
```

**边界条件**：

| 条件 | 行为 |
|------|------|
| 空图（G 无节点） | 返回空列表 |
| 全部节点被过滤 | 后续 `business_cluster` 抛出 `NoBusinessNodesError` |
| 混合语言项目（Java + JS） | 分别按各自规则分类，统一进入后续流程 |
| Windows 路径分隔符 `\` | 统一替换为 `/` 再判断测试路径 |

**副作用**：每个通过过滤的节点被注入两个属性：

```python
node_entry["id"] = node_id           # 图节点 ID（字符串）
node_entry["_role"] = role.value     # NodeRole 枚举的 value 字符串
node_entry["_importance"] = score    # 重要性评分（整数 -5 ~ 5）
```

### 3.3 Step 2: extract_anchors — 锚点提取

**输入**：`list[dict]` 业务节点列表（~2,800 个）

**输出**：`list[dict]` 锚点列表（~1,200 个）

**算法**：从业务节点中筛选 `_role` 属于 `ANCHOR_ROLES` 的节点。

```python
ANCHOR_ROLES = {NodeRole.CONTROLLER, NodeRole.SERVICE_IMPL, NodeRole.SERVICE_API,
                NodeRole.MAPPER, NodeRole.DAO, NodeRole.ADAPTER}

def extract_anchors(business_nodes: list[dict]) -> list[dict]:
    return [n for n in business_nodes if NodeRole(n["_role"]) in ANCHOR_ROLES]
```

**设计原理**：

这 6 类角色代表业务功能的"骨架"——它们是业务域的入口和主干：
- **Controller**：外部请求的入口（HTTP 端点），每个 Controller 明确属于一个业务域
- **Service (Impl + API)**：业务逻辑核心，实现与接口分离时两者都保留
- **Mapper/DAO**：数据访问层，紧耦合于特定业务域
- **Adapter**：外部系统适配器，代表域与外部交互的边界

**为什么 Entity、DTO、VO 不是锚点？**
- Entity 虽然有业务含义，但一个域可能有 10+ 个 Entity，它们不适合作为域的"标识符"
- DTO/VO 是数据传输结构，跨域复用常见，不适合作为域键
- 锚点的定义是"最能唯一标识一个业务域的节点"，Controller 和 Service 满足此条件

**边界条件**：

| 条件 | 行为 |
|------|------|
| 锚点 < 3 | 抛出 `TooFewAnchorsError`，无法形成有意义的域划分 |
| 锚点全为同一角色（如全 CONTROLLER） | 仍正常进入 Step 3，但域质量可能偏低 |
| 无锚点 | 抛出 `TooFewAnchorsError` |

### 3.4 Step 3: cluster_by_package — 包路径聚类

**输入**：
- `anchors: list[dict]` — 锚点列表
- `language: Language` — 项目语言
- `root_path: Path` — 项目根目录

**输出**：`dict[str, list[dict]]` — `{domain_key: [anchor_nodes]}` 候选域字典

**算法三子步骤**：

```
Step 3.1: extract_package_path(node, language, root_path) → str
  从 source_file 中提取可聚类的包路径字符串
  
  Java:   com/smc/smccloud/bin/controller/BinController.java
          → src/main/java/ 之后的路径: "com.smc.smccloud.bin.controller"
          → 去掉文件名: "com.smc.smccloud.bin"
  
  JS/TS:  src/api/binorder.js
          → src/ 之后的路径: "api.binorder"
  
  Vue:    src/views/csstock/apply/index.vue
          → src/ 之后的路径: "views.csstock.apply"

Step 3.2: extract_domain_key(pkg_path, language) → str | None
  从包路径中提取"域键"——域的唯一标识符
  
  Java:   "com.smc.smccloud.bin" → "bin"
  JS:     "api.binorder"         → "binorder"
  Vue:    "views.csstock.apply"  → "csstock"

Step 3.3: 标准化 key 并按 key 分组
  key = re.sub(r"[^a-z0-9]", "", key.lower())
  → 分组: {"bin": [node1, node2], "order": [node3], ...}
```

**边界条件**：

| 条件 | 行为 |
|------|------|
| `extract_domain_key` 返回 `None` | 该锚点被跳过，不加入任何候选域（极少发生） |
| 两个锚点路径不同但域键相同 | 自动归入同一候选域（Java: `bin.controller` + `bin.service` 均 → `bin`） |
| 语言为 JAVA 但找不到 `java/` 路径分隔 | 回退到 `src/` 后查找 |
| 语言为非 Java 且找不到 `src/` | 从根目录开始取路径 |

### 3.5 Step 4: adjust_by_imports — import 聚合调整

**输入**：
- `candidates: dict[str, list[dict]]` — 候选域字典
- `G: nx.Graph` — 全量图
- `merge_threshold: float` — 合并密度阈值 (默认 0.3)
- `min_domain_size: int` — 最小域大小 (默认 5)

**输出**：`list[Domain]` 最终域列表

**四子步骤**：

```
Step 4.1: apply_merge_rules(candidates) → candidates
  应用预定义的关键词合并规则（见第 6 章）
  例: 'bin' + 'bindata' + 'binorder' → 'bin-data'

Step 4.2: 构建域间 import 密度矩阵
  遍历图的所有 imports/imports_from 边
  统计每对候选域之间的 import 次数
  输出: import_matrix[src_domain][tgt_domain] = count

Step 4.3: 贪心合并高密度候选域
  重复合并互相 import 密度 > merge_threshold 的候选域对
  密度定义: density = mutual_imports / (size_A × size_B)
  直到没有可合并的候选域对

Step 4.4: 合并小域
  将所有锚点数 < min_domain_size 的候选域
  合并到与之 import 最多的 >= min_domain_size 的域
  如果没有大域（所有域都小），跳过此步
```

**边界条件**：

| 条件 | 行为 |
|------|------|
| 无 import 边 | 跳过密度合并，只执行预定义规则合并和小域吸收 |
| 全部候选域 < `min_domain_size` | 跳过小域吸收，保留所有现有域 |
| 小域与大域无 import 关系 | 取该小域 import 最多的域，即使 import = 0 |
| 合并后域 ID 冲突 | 取 target ID（后合并的覆盖先合并的） |

**最终 Domain.dependencies 计算**：

在全部合并完成后，遍历图中所有 `imports` / `imports_from` 边，按 `anchor_id → domain_id` 映射统计：

```python
dep_matrix[src_domain][tgt_domain] += 1  # 每有一条 import 边计数 +1
```

**多模块处理**：如果项目是 Maven 多模块，`modules` 字段通过 `packages` 路径推断（取包路径第二级或根级）。

### 3.6 Step 5: extract_business_points — 业务点提取

**输入**：
- `domains: list[Domain]` — 已划分的域
- `anchors: list[dict]` — 锚点列表
- `G: nx.Graph` — 全量图

**输出**：`list[Domain]` — 每个域的 `business_points` 字段已填充

**算法**：

```python
for each domain in domains:
    1. 收集 domain 内所有锚点 ID: domain_anchor_ids
    
    for each anchor_id in domain_anchor_ids:
        2. 判断锚点角色: 只处理 controller / service_impl
        3. 沿 contains 边找到该锚点的所有子节点（方法）
        4. 过滤非业务方法（getter/setter/生命周期等）
        5. 对每个业务方法:
           a. 创建 BusinessPoint(name, entry_method, entry_file)
           b. 调用 analyze_cross_domain() 分析跨域调用
           c. 追加到 domain.business_points
```

**方法节点检测规则**：

| 语言 | 方法节点特征 | 示例 |
|------|-------------|------|
| Java | `label` 以 `.` 开头 | `.listBinOrderDetail` |
| JS/TS/Vue | `label` 以 `)` 结尾 | `listBinOrderDetail()` |
| Python | `label` 以 `(` 结尾（推测） | `list_bin_order_detail(` |
| Go | `label` 以 `(` 结尾（推测） | `ListBinOrderDetail(` |

**当前代码检测逻辑**：

```python
is_method = label.startswith(".") or label.endswith(")")
```

**边界条件**：

| 条件 | 行为 |
|------|------|
| 域内无 controller/service_impl | 该域的 business_points 为空列表 |
| 所有方法都被非业务方法过滤 | business_points 为空列表 |
| 锚点在 G 中已不存在 | 跳过该锚点（容错） |

---

## 4. 噪音过滤详细规则表

### 4.1 `is_business_relevant()` 完整判断树

```
输入: data (图节点属性字典)
  │
  ├─ file_type != "code"?
  │    └─ YES → 排除（非代码文件：文档、图片等）
  │
  ├─ source_file 含 "/src/test/" (标准化后)?
  │    └─ YES → 排除（测试代码）
  │
  ├─ label 以 "." 或 "_" 开头?
  │    └─ YES → 排除（匿名方法调用/编译器生成）
  │
  ├─ label 在 NOISE_LABELS 集合中?
  │    └─ YES → 排除（JDK 基础类型）
  │
  ├─ label 匹配 regex ^(get|set|is)[A-Z]?
  │    └─ YES → 排除（getter/setter 方法节点）
  │
  ├─ label 在 {toString(), equals(), hashCode(), clone(), finalize()} 中?
  │    └─ YES → 排除（Object 类通用方法）
  │
  ├─ label 以 VALID_SUFFIXES 之一结尾?
  │    └─ NO → 排除（不支持的文件类型）
  │
  └─ 所有检查通过 → 保留为业务节点
```

### 4.2 非业务方法精确排除列表

除正则匹配的 `^(get|set|is)[A-Z]` getter/setter 外，以下方法名单精确排除：

#### Object 类方法
`toString`, `equals`, `hashCode`, `clone`, `finalize`

#### Java 函数式接口方法
`run`, `call`, `apply`, `accept`, `test`, `get`, `compare`, `andThen`, `compose`, `orElse`, `ifPresent`, `and`, `or`, `negate`

#### Spring 生命周期回调
`afterPropertiesSet`, `init`, `destroy`, `setApplicationContext`, `setServletContext`, `setBeanFactory`, `setBeanName`, `setBeanClassLoader`, `setResourceLoader`, `setEnvironment`, `setEmbeddedValueResolver`

#### Lombok / Builder 生成方法
`builder`, `build`, `toBuilder`, `canEqual`

#### MyBatis Generator / Criteria
`addCriterion`, `isValid`, `createCriteria`, `createCriteriaInternal`, `oredCriteria`, `clear`, `isDistinct`, `setDistinct`, `setOrderByClause`, `setOredCriteria`

#### Vue 生命周期钩子 (Options + Composition)
`data`, `beforeCreate`, `created`, `beforeMount`, `mounted`, `beforeUpdate`, `updated`, `activated`, `deactivated`, `beforeDestroy`, `destroyed`, `beforeUnmount`, `unmounted`, `errorCaptured`, `renderTracked`, `renderTriggered`, `serverPrefetch`, `render`

#### Vue Options API 特殊属性
`components`, `directives`, `filters`, `mixins`, `extends`, `provide`, `inject`, `setup`, `emits`, `expose`, `slots`, `props`, `computed`, `watch`, `methods`, `name`, `model`, `inheritAttrs`, `delimiters`

#### JS 关键字误解析
`if`, `for`, `while`, `switch`, `case`, `return`, `break`, `continue`, `new`, `delete`, `typeof`, `void`

#### 方法名精确匹配（含括号）
`.toString()`, `.equals()`, `.hashCode()`, `.clone()`, `.finalize()`, `.builder()`, `.build()`, `.toBuilder()`, `.addCriterion()`

#### 单字符方法名
任何方法名去掉括号后长度 <= 1（排除编译器生成的匿名方法）

**设计说明**：这份名单是开发过程中逐步积累的，覆盖了 OPS 项目实际遇到的非业务方法。随着支持新语言和新框架，名单会继续增长。新增规则遵循"宁可误杀也不要放过噪声"的原则。

---

## 5. 锚点角色分类决策树

### 5.1 `classify_role()` 决策树

```
输入: data (含 label, source_file 的节点属性字典)
  │
  ├─ label 小写 + ".java" 结尾?
  │    ├─ 含 "controller"       → NodeRole.CONTROLLER
  │    ├─ 含 "serviceimpl"      → NodeRole.SERVICE_IMPL
  │    ├─ 含 "service"          → NodeRole.SERVICE_API
  │    ├─ 含 "mapper"           → NodeRole.MAPPER
  │    ├─ 含 "dao"              → NodeRole.DAO
  │    ├─ 含 "adapter"          → NodeRole.ADAPTER
  │    ├─ 含 "config"/"configuration"/"properties" → NodeRole.CONFIG
  │    ├─ 含 "util"/"helper"/"tool"/"constant"     → NodeRole.UTIL
  │    ├─ 含 "entity" 或 以 "do.java" 结尾         → NodeRole.ENTITY
  │    ├─ 含 "dto"              → NodeRole.DTO
  │    ├─ "vo.java" 结尾        → NodeRole.VO
  │    ├─ 含 "enum"             → NodeRole.ENUM
  │    └─ 其他 .java 文件       → NodeRole.ENTITY（默认）
  │
  ├─ label 以 ".vue" 结尾?
  │    ├─ source_file 含 "/components/" → NodeRole.HANDLER
  │    └─ 其他 .vue 文件                 → NodeRole.CONTROLLER
  │
  ├─ label 以 ".js" / ".ts" 结尾?
  │    ├─ source_file 含 "/api/"         → NodeRole.CONTROLLER
  │    ├─ source_file 含 "/utils/"       → NodeRole.UTIL
  │    ├─ source_file 含 "/helpers/"     → NodeRole.UTIL
  │    ├─ source_file 含 "/constants/"   → NodeRole.UTIL
  │    └─ 其他 .js/.ts 文件              → NodeRole.ENTITY（默认）
  │
  ├─ label 以 ".py" 结尾?
  │    ├─ 文件名含 "controller" → NodeRole.CONTROLLER
  │    ├─ 文件名含 "service"   → NodeRole.SERVICE_API
  │    └─ 默认                   → NodeRole.ENTITY
  │    （Python 角色分类待扩展）
  │
  ├─ label 以 ".go" 结尾?
  │    └─ 默认                   → NodeRole.ENTITY
  │    （Go 角色分类待扩展）
  │
  └─ 非代码节点（已在 filter_noise 排除）
       → NodeRole.NOISE（兜底，理论上不会到达）
```

### 5.2 五种语言的角色分类说明

| 语言 | 已实现 | 锚点角色 | 备注 |
|------|:-----:|----------|------|
| Java / Kotlin | ✅ 完整 | CONTROLLER, SERVICE_IMPL, SERVICE_API, MAPPER, DAO, ADAPTER | Spring 命名惯例成熟 |
| JavaScript / TypeScript | ✅ 完整 | CONTROLLER (api/ 目录), UTIL (utils/ 目录) | 前端框架约定优于配置 |
| Vue | ✅ 完整 | CONTROLLER (页面), HANDLER (components/) | 页面=Controller，组件=Handler |
| Python | 基础 | CONTROLLER, SERVICE_API | Django/Flask 命名模式待扩展 |
| Go | 基础 | — | Go 没有通用"Controller"命名惯例 |

**设计原理**：角色分类完全基于**文件名模式匹配**，而非 AST 解析。这是因为：
1. graph.json 中已有完整的文件路径信息，无需额外 AST 解析
2. Spring 等框架的命名惯例（`XxxController`、`XxxServiceImpl`）已经是业界标准
3. 模式匹配零 Token 成本，OPS 项目 ~34K 节点的全量分类 < 100ms
4. 新语言支持只需添加新模式，无需改动核心算法

### 5.3 `_importance` 评分规则

```python
{
    NodeRole.CONTROLLER:   5,   # 业务入口，最高权重
    NodeRole.SERVICE_IMPL: 4,   # 业务逻辑核心
    NodeRole.SERVICE_API:  4,   # 业务接口契约
    NodeRole.MAPPER:       3,   # 数据访问层
    NodeRole.DAO:          3,   # 数据访问层
    NodeRole.ADAPTER:      3,   # 外部系统对接
    NodeRole.ENTITY:       2,   # 业务实体
    NodeRole.HANDLER:      2,   # 通用组件
    NodeRole.DTO:          1,   # 数据传输对象
    NodeRole.VO:           1,   # 视图对象
    NodeRole.ENUM:         1,   # 枚举
    NodeRole.CONFIG:      -1,   # 配置（非业务）
    NodeRole.UTIL:        -1,   # 工具（非业务）
    NodeRole.NOISE:       -5,   # 噪音（最低权重）
}
```

**用途**：`_importance` 在可视化模块中用于控制节点大小，在 LLM 标注采样时决定优先级。

---

## 6. 域键提取策略

### 6.1 各语言包路径截取规则

#### Java

```python
def extract_domain_key(pkg_path: str, language: Language) -> Optional[str]:
    parts = pkg_path.split(".")
    company_roots = {"com", "org", "io", "net", "gov", "edu"}
    
    # 跳过 TLD (com/org/io...)
    i = 0
    if parts and parts[0] in company_roots:
        i = 1                     # 跳过 com
        if i < len(parts):
            i += 1                # 跳过公司名 (smc)
    
    # 跳过平台/产品名
    platform_names = {"smccloud", "ops", "hshcore", "platform"}
    while i < len(parts) and parts[i] in platform_names:
        i += 1
    
    # 剩余的第一个包名即为域键
    if i < len(parts):
        return parts[i]
    return parts[0] if parts else None
```

| 完整包路径 | 跳过 TLD | 跳过公司名 | 跳过平台名 | 域键 |
|-----------|:--------:|:----------:|:----------:|:----:|
| `com.smc.smccloud.bin.service` | com | smc | smccloud | **bin** |
| `org.springframework.web` | org | —(没有公司名) | — | **springframework** |
| `io.grpc.protobuf` | io | —(没有公司名) | — | **grpc** |
| `com.smc.smccloud.purchase.controller` | com | smc | smccloud | **purchase** |
| `com.smc.hshcore.common` | com | smc | hshcore | **common** |
| `java.util` | —(不在 company_roots) | — | — | **java** |
| `com.smc.ops.delivery` | com | smc | ops | **delivery** |

#### JavaScript / TypeScript / Vue

```python
# 跳过框架目录名，取下一级
skip = {"api", "views", "components", "store", "router", "utils", "assets"}
if len(parts) >= 2 and parts[0] in skip:
    return parts[1]
return parts[0]
```

| 文件路径 | 包路径 | 框架目录 | 域键 |
|----------|--------|:--------:|:----:|
| `src/api/binorder.js` | `api.binorder` | api | **binorder** |
| `src/views/csstock/apply/index.vue` | `views.csstock.apply` | views | **csstock** |
| `src/components/ECharts/index.vue` | `components.echarts` | components | **echarts** |
| `src/store/modules/user.js` | `store.modules.user` | store | **modules** |
| `src/utils/date.js` | `utils.date` | utils | **date** |
| `src/assets/logo.png` | `assets.logo` | assets | **logo** |

#### Python

```python
return parts[0] if parts else None   # 取根后第 1 级
```

| Import 路径 | 包路径 | 域键 |
|-------------|--------|:----:|
| `from ops.bin.service import BindataService` | `ops.bin.service` | **ops** |
| `from bin.service import BindataService` | `bin.service` | **bin** |

#### Go

```python
return parts[0] if parts else None   # 取根后第 1 级
```

| Module 路径 | 包路径 | 域键 |
|-------------|--------|:----:|
| `github.com/company/ops/internal/bin` | `ops.internal.bin` | **ops** |
| `github.com/company/bin/service` | `bin.service` | **bin** |

### 6.2 `extract_package_path()` 路径解析

```python
def extract_package_path(node: dict, language: Language, root_path: Path) -> str:
    source = node.get("source_file", "").replace("\\", "/")
    path_parts = source.split("/")
    
    if language == Language.JAVA:
        java_idx = find_java_source_root(path_parts)  # 找 LAST "java"
        pkg_parts = path_parts[java_idx + 1:-1]       # 去文件名
        return ".".join(pkg_parts)
    else:
        src_idx = find_src_root(path_parts)            # 找第一个 "src"
        return ".".join(path_parts[src_idx + 1:-1])
```

**`find_java_source_root()` 关键逻辑**：
- 从后向前找第一个 `"java"`（处理 `src/main/java` 和 `src/test/java`）
- 测试路径已在 Step 1 被排除，所以 `src/test/java` 不会出现
- 如果找不到 `"java"`，回退到找 `"src"`
- 如果找不到 `"src"`，从根开始

---

## 7. DOMAIN_MERGE_RULES 设计原理

### 7.1 为什么需要预定义合并规则

包路径聚类（Step 3）按 `extract_domain_key()` 提取的单一级别域键分组，但同一业务域可能对应多个不同域键：

```
实际问题：
  com.smc.smccloud.bin.controller.BinController         → domain_key = "bin"
  com.smc.smccloud.bindata.service.BindataService        → domain_key = "bindata"  
  com.smc.smccloud.binorder.service.BinOrderService      → domain_key = "binorder"

这三个域键("bin", "bindata", "binorder")实际上属于同一个业务域："Bin数据管理"
但包名不同导致 extract_domain_key 提取出不同域键
```

**解决方案**：预定义的关键词 → 目标域映射表，将语义相近的域键合并。

### 7.2 完整规则表

```python
DOMAIN_MERGE_RULES = {
    # (关键词组) → 合并后的域 ID
    ("bin", "bindata", "binorder"):           "bin-data",       # Bin 数据管理
    ("order", "preorder", "bigorder", "orderdetail"): "order",  # 订单管理
    ("purchase", "po", "purchaseorder"):      "purchase",       # 采购管理
    ("delivery", "deliver", "ship"):          "delivery",       # 物流管理
    ("warehouse", "stock", "inventory", "allot"): "inventory",   # 库存管理
    ("invoice", "invoicedata", "billing"):    "invoice",        # 发票管理
    ("supplier", "vendor"):                   "supplier",       # 供应商管理
    ("product", "bom", "pd"):                 "product",        # 产品数据管理
    ("customer", "client"):                   "customer",       # 客户管理
    ("job", "task", "schedule", "xxl"):       "job",            # 任务调度
    ("event", "publisher", "listener"):       "event",          # 事件处理
}
```

### 7.3 设计原则

1. **以项目实际观察为基础**：规则表中的每个组合都来源于真实项目的包命名模式
2. **关键词聚合而非模糊匹配**：要求精确的关键词列表，不做语义相似度（零 Token 成本）
3. **可扩展性**：新项目可在 `graph-wiki.yaml` 配置中自定义规则
4. **`None` 目标排除**：某些关键词组（如 config/settings/properties）应被标记为 `None` 以排除（当前代码中未使用此功能，但在规则表设计中预留）

### 7.4 执行逻辑

```python
def apply_merge_rules(candidates: dict) -> dict:
    """应用预定义合并规则，将匹配的关键词组合并到目标域 ID"""
    merged = dict(candidates)
    for keywords, target in DOMAIN_MERGE_RULES.items():
        matched = {k: merged.pop(k) for k in keywords if k in merged}
        if matched and target is not None:
            combined = []
            for nodes in matched.values():
                combined.extend(nodes)
            merged[target] = combined
    return merged
```

**应用示例**：

```
合并前: {"bin": [node1], "bindata": [node2], "binorder": [node3], "order": [node4]}
   → 规则 ("bin", "bindata", "binorder") → "bin-data" 命中
   → bin, bindata, binorder 被弹出
   → "bin-data": [node1, node2, node3]
合并后: {"bin-data": [node1, node2, node3], "order": [node4]}
```

---

## 8. import 密度合并算法

### 8.1 数学定义

给定两个候选域 A 和 B，定义：

```
import_matrix[A][B] = 从域 A 的锚点指向域 B 的锚点的 import 边数量
import_matrix[B][A] = 从域 B 的锚点指向域 A 的锚点的 import 边数量
  
mutual_imports = import_matrix[A][B] + import_matrix[B][A]
density = mutual_imports / (len(A.nodes) * len(B.nodes))
```

**密度含义**：A 和 B 之间每对锚点的平均 import 次数。
- density = 0：A 和 B 完全没有 import 关系
- density = 1.0：A 的每个锚点都 import 了 B 的每个锚点（高度耦合）
- density > merge_threshold (0.3)：A 和 B 耦合度足够高，应合并为一个域

### 8.2 贪心合并算法

```python
def merge_dense_candidates(candidates, import_matrix, merge_threshold):
    merged = dict(candidates)
    changed = True
    
    while changed:
        changed = False
        keys = list(merged.keys())
        
        for i in range(len(keys)):
            for j in range(i + 1, len(keys)):
                a, b = keys[i], keys[j]
                if a not in merged or b not in merged:
                    continue
                
                # 计算合并密度
                mutual = import_matrix[a].get(b, 0) + import_matrix[b].get(a, 0)
                size_a = len(merged[a])
                size_b = len(merged[b])
                
                if size_a > 0 and size_b > 0:
                    density = mutual / (size_a * size_b)
                    if density > merge_threshold:
                        # 合并: 新 key = "a.b"
                        merged[a + "." + b] = merged.pop(a) + merged.pop(b)
                        changed = True
                        break
            if changed:
                break
    
    return merged
```

**算法特性**：
- **贪心**：每轮只合并第一对符合条件的候选域，然后重新扫描（因为合并后 import 矩阵需要重建）
- **迭代**：一轮迭代最多合并一对，上界为 O(N^3) 时间复杂度（N = 候选域数，通常 < 50）
- **key 命名**：合并后的 key 为 `"a.b"`（如 `"purchase.inventory"`），但后续不会用于显示

### 8.3 阈值含义

| `merge_threshold` 值 | 含义 | 适用场景 |
|:--------------------:|------|---------|
| 0.0 | 只要有 import 就合并 | 小项目，期望少域 |
| 0.1 | 松耦合合并 | 中等项目 |
| **0.3 (默认)** | **中等耦合合并** | **通用场景，平衡域内聚和粒度** |
| 0.5 | 高耦合才合并 | 大型项目，期望多域 |
| 1.0 | 永不合并 | 纯包路径聚类，不做 import 调整 |

### 8.4 小域吸收算法

```python
# 分离小域和大域
small_keys = [k for k in merged if len(merged[k]) < min_domain_size]
big_keys = [k for k in merged if len(merged[k]) >= min_domain_size]

# 每个小域合并到与之 import 最多的大域
for sk in small_keys:
    if big_keys:
        best = max(big_keys, key=lambda bk: import_matrix[sk].get(bk, 0))
        merged[best].extend(merged.pop(sk))
```

**设计考量**：小域（< 5 个锚点）大概率是命名不规范导致的"碎片域"。将它们合并到最常 import 的大域，而不是随机分配，保持了语义合理性。

---

## 9. 业务点提取方法过滤规则

### 9.1 完整过滤链

```
输入: Controller/ServiceImpl 的 contains 子节点（方法节点）
  │
  ├─ 方法名以 "." 开头 → 排除（匿名方法调用，初步已在 Step 1 排除）
  │
  ├─ 方法名匹配 ^(get|set|is)[A-Z] → 排除（Java getter/setter）
  │
  ├─ 方法名匹配 ^(get|set|is)[a-z] → 排除（JS get/set 属性访问器）
  │
  ├─ 方法名在 _NON_BUSINESS_METHOD_NAMES 集合中 → 排除（精确匹配含括号）
  │
  ├─ 去括号后方法名长度 <= 1 → 排除（单字符匿名方法）
  │
  ├─ 去括号后方法名在 _NON_BUSINESS_METHODS 集合中 → 排除
  │    ├─ Object 方法 (toString, equals, hashCode, ...)
  │    ├─ 函数式接口 (run, call, apply, accept, test, ...)
  │    ├─ Spring 生命周期 (afterPropertiesSet, init, destroy, ...)
  │    ├─ Lombok/Builder (builder, build, toBuilder, ...)
  │    ├─ MyBatis Generator (addCriterion, createCriteria, ...)
  │    ├─ Vue 生命周期 (created, mounted, beforeDestroy, ...)
  │    ├─ Vue Options API (data, props, computed, watch, ...)
  │    └─ JS 关键字 (if, for, while, return, ...)
  │
  └─ 通过所有检查 → 保留为业务点方法
```

### 9.2 各排除类别详解

#### (1) getter/setter/Boolean getter

```python
# Java 模式
re.match(r"^(get|set|is)[A-Z]", name)  # "getOrderNo", "setOrderNo", "isValid"
# JS 模式
re.match(r"^(get|set|is)[a-z]", name)  # "get orderno", "set orderno" (低概率命中)
```

**为什么 getter/setter 不是业务点？**
- 它们反映的是字段访问，不是业务操作
- Spring 项目中 `@Data` 注解自动生成，开发者甚至看不到这些方法
- 一个实体类可能有 10+ 个 getter/setter，会淹没真正的业务方法

#### (2) Java 函数式接口方法

| 方法名 | 所属接口 | 说明 |
|--------|---------|------|
| `run` | `Runnable` | 无参数无返回值执行 |
| `call` | `Callable` | 有返回值执行 |
| `apply` | `Function` | 函数应用 |
| `accept` | `Consumer` | 消费一个值 |
| `test` | `Predicate` | 测试条件 |
| `get` | `Supplier` | 提供值 |
| `compare` | `Comparator` | 比较两个值 |
| `andThen`/`compose` | `Function` | 函数组合 |
| `orElse`/`ifPresent` | `Optional` | 可选值处理 |

#### (3) Spring 生命周期回调

| 方法名 | 接口/注解 | 说明 |
|--------|----------|------|
| `afterPropertiesSet` | `InitializingBean` | Bean 初始化后 |
| `init` | `@PostConstruct` / `@Bean(initMethod)` | 初始化 |
| `destroy` | `@PreDestroy` / `@Bean(destroyMethod)` | 销毁 |
| `setApplicationContext` | `ApplicationContextAware` | 注入上下文 |
| `setBeanFactory` | `BeanFactoryAware` | 注入工厂 |
| `setBeanName` | `BeanNameAware` | 注入 Bean 名 |

#### (4) Vue 生命周期和方法属性

```
Options API:
  data, props, computed, watch, methods (组件选项，非方法)
  beforeCreate, created, beforeMount, mounted (生命周期)
  beforeUpdate, updated, beforeDestroy, destroyed

Composition API:
  setup, onMounted, onUnmounted (钩子)
  
Vue 3:
  beforeUnmount, unmounted (Vue 3 重命名)
  renderTracked, renderTriggered, serverPrefetch
```

### 9.3 过滤效果统计（OPS 项目估算）

```
全量图节点:                          34,298  (100%)
  Step 1 后（业务节点）:              ~2,800  (8.2%)
  Step 2 后（锚点）:                  ~1,200  (3.5%)
  Step 5:
    锚点中 controller + service_impl: ~400
    每锚点平均方法数:                 ~15
    方法总数:                         ~6,000
    排除非业务方法后:                 ~500-800 (1.5-2.3%)
```

---

## 10. 跨域调用分析算法

### 10.1 原理

跨域调用分析在 `extract_business_points()` 的 Step 5 中执行，针对每个已识别的业务方法，沿 `calls` 边找出它调用了哪些其他域的方法。

### 10.2 算法数据流

```
输入: method_id, G (NetworkX 图), domains (list[Domain])
  │
  ├─ step 1: 建立 domain_anchor_sets
  │    {domain.id: {anchor_id1, anchor_id2, ...}}
  │
  ├─ step 2: 沿 calls 边找到被调用的方法
  │    for _, tgt_id, data in G.edges(method_id, data=True):
  │        if data.get("relation") == "calls":
  │            called_methods.add(tgt_id)
  │
  ├─ step 3: 通过 contains 边找到被调用方法的所属类
  │    method_to_class = build_method_class_map(G)
  │    for method_id in called_methods:
  │        parent_class = method_to_class.get(method_id)
  │
  └─ step 4: 将 parent_class 映射到域
       for domain in domains:
           if parent_class in domain_anchor_sets[domain.id]:
               called_domains[domain.id].append(method_label)
```

### 10.3 `build_method_class_map()` 实现

```python
def build_method_class_map(G: nx.Graph) -> dict[str, str]:
    """通过 contains 边构建 method_id → class_id 映射"""
    mapping = {}
    for src_id, tgt_id, data in G.edges(data=True):
        if data.get("relation") == "contains":
            mapping[tgt_id] = src_id
    return mapping
```

**原理**：graph.json 中，class 节点通过 `contains` 边指向它包含的方法节点。因此 `(class) --contains--> (method)` 是标准的父子关系。

### 10.4 跨域调用结果在 BusinessPoint 中的呈现

```python
BusinessPoint(
    name=".deleteCustomerOrder",
    entry_method="node_12345",
    entry_file="com/.../order/service/CustomerOrderService.java",
    cross_domain_calls={
        "logistics": ["LogisticsService.cancelShipment()"],   # 跨域：物流域
        "inventory": ["InventoryService.restoreStock()"],     # 跨域：库存域
    },
    internal_calls=["OrderStatusMapper.updateStatus()"],      # 域内调用
    infrastructure_calls=["LogService.writeLog()"],           # 基础设施调用
)
```

**目前实现仅填充 `cross_domain_calls`**。`internal_calls` 和 `infrastructure_calls` 字段保留为 `[]`（v1.0 范围）。

### 10.5 局限性

| 局限 | 原因 | 严重度 | 缓解措施 |
|------|------|:------:|---------|
| 只追踪 1 跳 calls | 避免调用链爆炸 | 🟡 中 | 后续 `--deep` 递归模式 |
| 接口调用无法解析 | tree-sitter 只看到接口类型 | 🟡 中 | Bean 命名约定模糊匹配 |
| Lambda 调用不可见 | AST 不形成 calls 边 | 🟢 低 | 非跨域入口 |
| 反射不可见 | 运行时动态调用 | 🟢 低 | 业务代码中少见 |

---

## 11. 重要内部辅助函数

### 11.1 `detect_language()` — 自动语言检测

```python
def detect_language(root: Path) -> Language:
    if (root / "pom.xml").exists() or (root / "build.gradle").exists():
        return Language.JAVA
    if (root / "package.json").exists():
        return Language.JAVASCRIPT
    if (root / "pyproject.toml").exists() or (root / "setup.py").exists():
        return Language.PYTHON
    if (root / "go.mod").exists():
        return Language.GO
    return Language.JAVA  # 默认回退
```

**检测优先级**：Java > JS/TS > Python > Go > 默认 Java。

### 11.2 `find_java_source_root()` — Java 源根查找

```python
def find_java_source_root(path_parts: list[str]) -> int:
    # 从后向前找最后一个 "java" (处理 src/main/java/ 路径)
    for i in range(len(path_parts) - 1, -1, -1):
        if path_parts[i] == "java":
            return i
    # fallback: 第一个 "src"
    for i, p in enumerate(path_parts):
        if p == "src":
            return i
    return 0
```

**为什么从后向前找**：某些项目路径包含多个 `java`（如 `src/main/java/com/foo/java/...`），最后一个通常是包路径的起点。

### 11.3 `find_domain_for_node()` — 节点域查找

```python
def find_domain_for_node(node_id: str, domain_anchor_ids: dict[str, set[str]]) -> Optional[str]:
    for domain_key, anchor_ids in domain_anchor_ids.items():
        if node_id in anchor_ids:
            return domain_key
    return None
```

**用于**：在 `adjust_by_imports()` 中计算 import 矩阵时，判断每条 import 边的源和目标所属域。

---

## 12. 错误处理

### 12.1 异常层级

```
PipelineError (pipeline.py 定义)
  └── ClusterError                   # 聚类模块基类
       ├── NoBusinessNodesError      # 无业务节点
       └── TooFewAnchorsError        # 锚点太少
```

### 12.2 降级策略

| 异常 | 触发条件 | 默认行为 |
|------|---------|---------|
| `NoBusinessNodesError` | `filter_noise` 返回空列表 | pipeline 终止，报告错误 |
| `TooFewAnchorsError` | `extract_anchors` 返回 < 3 节点 | pipeline 终止，报告错误 |
| 其他 Python 异常 | 图数据结构损坏、空指针等 | pipeline 捕获后终止 |

### 12.3 防御性编程

```python
# 空图检测
if G.number_of_nodes() == 0:
    raise NoBusinessNodesError("图为空，无法聚类")

# 节点属性缺失兜底
label = data.get("label", "")
source = data.get("source_file", "")
if not label and not source:
    # 跳过无法分类的节点
    continue
```

### 12.4 Token 成本

**整个 `cluster.py` 模块零 Token 成本**。所有算法（噪音过滤、角色分类、包路径解析、import 密度计算、业务点提取、跨域分析）均为纯静态分析，不调用任何 LLM API。

---

## 13. 聚类质量指标

聚类输出必须进入 `build-report.json`，供集成测试和人工复盘使用。v1.0 至少记录以下指标：

| 指标 | 计算方式 | 目标 |
|------|----------|------|
| `domain_count` | `len(domains)` | 小项目可为 1；500 文件级建议 5-50 |
| `business_point_count` | `sum(len(d.business_points) for d in domains)` | 应大于 0，且单域不应异常爆炸 |
| `technical_name_ratio` | 技术域名数量 / 域总数 | 500 文件级应 < 30% |
| `tiny_domain_ratio` | 锚点数 < 3 的域 / 域总数 | 500 文件级应 < 20% |
| `max_domain_business_points` | 单域最大业务点数 | 建议 < 300 |
| `api_uncategorized_ratio` | 由 api_mapper 提供 | 小项目 < 30%，中型前后端 < 20% |
| `dependency_density` | 域间依赖边数 / 域数 | 过高提示域边界过碎或公共工具未过滤 |

技术域名判定：

- 明确技术层：`controller`、`service`、`mapper`、`dao`、`common`、`utils`、`components`
- 版本/协议层：`api`、`v1`、`v2`、`web`
- 业务缩写但未标注：如 `svn`、`repo` 记为 warning，不直接判 fatal

质量状态建议：

| 状态 | 条件 |
|------|------|
| `passed` | 关键阈值均通过 |
| `warning` | build 成功，但存在技术域名或小域偏多 |
| `failed` | 无业务点、域数量异常、或 API 大量未分类导致 Wiki 不适合交付 |

---

## 14. 测试用例

### 14.1 测试 Java 包路径域键提取

```python
def test_extract_domain_key_java():
    """验证 Java 包路径到域键的映射"""
    from graph_wiki.cluster import extract_domain_key
    
    # 标准 Spring 项目
    assert extract_domain_key("com.smc.smccloud.bin", Language.JAVA) == "bin"
    assert extract_domain_key("com.smc.smccloud.order.service", Language.JAVA) == "order"
    assert extract_domain_key("com.smc.smccloud.purchase.controller", Language.JAVA) == "purchase"
    
    # 非标准包名（无公司域名）
    assert extract_domain_key("org.springframework.web", Language.JAVA) == "springframework"
    
    # 多级平台名
    assert extract_domain_key("com.smc.ops.delivery.bill", Language.JAVA) == "delivery"
    
    # 空路径
    assert extract_domain_key("", Language.JAVA) is None
```

### 14.2 测试 JS/TS 域键提取

```python
def test_extract_domain_key_javascript():
    """验证 JS/TS 路径到域键的映射"""
    from graph_wiki.cluster import extract_domain_key
    
    # API 文件：跳过 api 取下一级
    assert extract_domain_key("api.binorder", Language.JAVASCRIPT) == "binorder"
    assert extract_domain_key("api.purchase", Language.JAVASCRIPT) == "purchase"
    
    # Views 文件：跳过 views 取下一级
    assert extract_domain_key("views.csstock.apply", Language.JAVASCRIPT) == "csstock"
    
    # 非框架目录：取第一级
    assert extract_domain_key("custom.module", Language.JAVASCRIPT) == "custom"
    
    # 空路径
    assert extract_domain_key("", Language.JAVASCRIPT) is None
```

### 14.3 测试预定义合并规则

```python
def test_apply_merge_rules():
    """验证 DOMAIN_MERGE_RULES 的合并效果"""
    from graph_wiki.cluster import apply_merge_rules
    
    candidates = {
        "bin": [{"id": "n1"}],
        "bindata": [{"id": "n2"}],
        "binorder": [{"id": "n3"}],
        "order": [{"id": "n4"}],
        "supplier": [{"id": "n5"}],
    }
    
    merged = apply_merge_rules(candidates)
    
    # bin/bindata/binorder 应合并为 bin-data
    assert "bin-data" in merged
    assert len(merged["bin-data"]) == 3  # n1 + n2 + n3
    assert "bin" not in merged           # 原 key 被移除
    assert "bindata" not in merged
    
    # 未匹配的 key 保持不变
    assert "order" in merged
    assert len(merged["order"]) == 1
    
    # supplier 未匹配（vendor 不在候选域中）
    assert "supplier" in merged
```

### 14.4 测试 import 密度合并

```python
def test_import_density_merge():
    """验证 import 密度合并算法"""
    import networkx as nx
    from graph_wiki.cluster import adjust_by_imports
    
    G = nx.Graph()
    
    # 设置: 两个候选域 A 和 B
    # A 有 2 个锚点 (a1, a2)
    # B 有 2 个锚点 (b1, b2)
    # A→B 有 2 条 import 边, B→A 有 1 条
    
    G.add_node("a1", label="AController.java", source_file="a/AController.java", _role="controller")
    G.add_node("a2", label="AService.java", source_file="a/AService.java", _role="service_impl")
    G.add_node("b1", label="BController.java", source_file="b/BController.java", _role="controller")
    G.add_node("b2", label="BService.java", source_file="b/BService.java", _role="service_impl")
    
    # 添加 import 边
    G.add_edge("a1", "b1", relation="imports")
    G.add_edge("a1", "b2", relation="imports")
    G.add_edge("a2", "b1", relation="imports")  # total: A→B = 3
    
    candidates = {
        "domain_a": [{"id": "a1"}, {"id": "a2"}],
        "domain_b": [{"id": "b1"}, {"id": "b2"}],
    }
    
    # merge_threshold = 0.3 时:
    # density = 3 imports / (2 * 2) = 0.75 > 0.3 → 应合并
    domains = adjust_by_imports(candidates, G, merge_threshold=0.3, min_domain_size=1)
    assert len(domains) == 1  # 合并为一个域
    assert domains[0].anchors_count() == 4
    
    # merge_threshold = 0.8 时:
    # density = 0.75 < 0.8 → 不应合并
    domains2 = adjust_by_imports(candidates, G, merge_threshold=0.8, min_domain_size=1)
    assert len(domains2) == 2  # 保留两个域
```

### 14.5 测试跨域调用分析

```python
def test_analyze_cross_domain():
    """验证跨域调用分析方法"""
    import networkx as nx
    from graph_wiki.cluster import analyze_cross_domain, build_method_class_map
    from graph_wiki.models import Domain
    
    G = nx.Graph()
    
    # class 节点
    G.add_node("cls_a", label="AController.java", _role="controller")
    G.add_node("cls_b", label="BController.java", _role="controller")
    
    # method 节点
    G.add_node("m_a1", label=".methodA", source_file="a/AController.java")
    G.add_node("m_b1", label=".methodB", source_file="b/BController.java")
    
    # contains 边: class → method
    G.add_edge("cls_a", "m_a1", relation="contains")
    G.add_edge("cls_b", "m_b1", relation="contains")
    
    # calls 边: method → method (跨域调用)
    G.add_edge("m_a1", "m_b1", relation="calls")
    
    # B 节点本身也有 contains
    # 注意: m_b1 被 cls_b contains, 所以 build_method_class_map 能找到 cls_b
    method_to_class = build_method_class_map(G)
    assert method_to_class["m_a1"] == "cls_a"
    assert method_to_class["m_b1"] == "cls_b"
    
    # 构建 domain
    domain_a = Domain(id="domain_a", anchors={"controller": [{"id": "cls_a"}]})
    domain_b = Domain(id="domain_b", anchors={"controller": [{"id": "cls_b"}]})
    domains = [domain_a, domain_b]
    
    # 分析 m_a1 的跨域调用
    cross = analyze_cross_domain("m_a1", G, method_to_class, domains)
    
    assert "domain_b" in cross  # 跨域调用了 domain_b
    assert len(cross["domain_b"]) == 1
    assert ".methodB" in cross["domain_b"] or "methodB" in cross["domain_b"][0]
    
    # domain_a 不应出现在跨域调用中（域内调用）
    assert "domain_a" not in cross
```

### 14.6 测试噪音过滤

```python
def test_filter_noise_removes_non_code():
    """验证噪音过滤排除非代码节点和 JDK 类型"""
    import networkx as nx
    from graph_wiki.cluster import filter_noise
    
    G = nx.Graph()
    
    # 正常业务节点（应保留）
    G.add_node("n1", label="BinController.java", source_file="com/../bin/controller/BinController.java", file_type="code")
    
    # 非代码节点（应排除）
    G.add_node("n2", label="readme.md", source_file="readme.md", file_type="doc")
    
    # JDK 基础类型（应排除）
    G.add_node("n3", label="String", source_file="", file_type="code")
    
    # getter/setter（应排除）
    G.add_node("n4", label="getOrderNo", source_file="", file_type="code")
    
    # 测试路径（应排除）
    G.add_node("n5", label="TestController.java", source_file="src/test/java/TestController.java", file_type="code")
    
    nodes = filter_noise(G)
    ids = [n["id"] for n in nodes]
    
    assert "n1" in ids  # 正常业务节点应保留
    assert "n2" not in ids  # 非代码文件排除
    assert "n3" not in ids  # JDK 类型排除
    assert "n4" not in ids  # getter 排除
    assert "n5" not in ids  # 测试路径排除
```

### 14.7 测试角色分类（多语言）

```python
def test_classify_role_multi_language():
    """验证五种语言的角色分类"""
    from graph_wiki.cluster import classify_role
    from graph_wiki.models import NodeRole
    
    # Java
    assert classify_role({"label": "BinController.java", "source_file": "..."}) == NodeRole.CONTROLLER
    assert classify_role({"label": "BinServiceImpl.java", "source_file": "..."}) == NodeRole.SERVICE_IMPL
    assert classify_role({"label": "BinService.java", "source_file": "..."}) == NodeRole.SERVICE_API
    assert classify_role({"label": "BinMapper.java", "source_file": "..."}) == NodeRole.MAPPER
    assert classify_role({"label": "BinDao.java", "source_file": "..."}) == NodeRole.DAO
    assert classify_role({"label": "BinAdapter.java", "source_file": "..."}) == NodeRole.ADAPTER
    assert classify_role({"label": "AppConfig.java", "source_file": "..."}) == NodeRole.CONFIG
    assert classify_role({"label": "StringUtil.java", "source_file": "..."}) == NodeRole.UTIL
    assert classify_role({"label": "BinEntity.java", "source_file": "..."}) == NodeRole.ENTITY
    
    # Vue
    assert classify_role({"label": "ApplyView.vue", "source_file": "src/views/apply/ApplyView.vue"}) == NodeRole.CONTROLLER
    assert classify_role({"label": "DataTable.vue", "source_file": "src/components/DataTable.vue"}) == NodeRole.HANDLER
    
    # JS
    assert classify_role({"label": "binorder.js", "source_file": "src/api/binorder.js"}) == NodeRole.CONTROLLER
    assert classify_role({"label": "date.js", "source_file": "src/utils/date.js"}) == NodeRole.UTIL
    
    # Python
    assert classify_role({"label": "bin_controller.py", "source_file": "..."}) == NodeRole.CONTROLLER
    assert classify_role({"label": "bin_service.py", "source_file": "..."}) == NodeRole.SERVICE_API
    assert classify_role({"label": "bin_model.py", "source_file": "..."}) == NodeRole.ENTITY
```

### 14.8 端到端：完整从图到域的最小项目

```python
def test_business_cluster_minimal_java_project():
    """验证在最小 Java 项目上完整运行 business_cluster"""
    import networkx as nx
    from pathlib import Path
    from graph_wiki.cluster import business_cluster
    from graph_wiki.models import Language
    
    G = nx.Graph()
    
    # 3 个 Controller, 3 个 ServiceImpl, 3 个 Service API, 3 个 Mapper
    # 全部在 bin 包下
    for suffix in ["Bin", "Order", "Purchase"]:
        cls_id = f"ctrl_{suffix}"
        G.add_node(cls_id, label=f"{suffix}Controller.java",
                   source_file=f"com/smc/smccloud/bin/controller/{suffix}Controller.java",
                   file_type="code")
        # Controller 包含方法
        G.add_node(f"{cls_id}.list", label=f".list{suffix}",
                   source_file=f".../{suffix}Controller.java", file_type="code")
        G.add_edge(cls_id, f"{cls_id}.list", relation="contains")
        
        svc_id = f"svc_{suffix}"
        G.add_node(svc_id, label=f"{suffix}ServiceImpl.java",
                   source_file=f"com/smc/smccloud/bin/service/impl/{suffix}ServiceImpl.java",
                   file_type="code")
        G.add_node(f"svc_api_{suffix}", label=f"{suffix}Service.java",
                   source_file=f"com/smc/smccloud/bin/service/{suffix}Service.java",
                   file_type="code")
        mapper_id = f"mapper_{suffix}"
        G.add_node(mapper_id, label=f"{suffix}Mapper.java",
                   source_file=f"com/smc/smccloud/bin/mapper/{suffix}Mapper.java",
                   file_type="code")
    
    # import 边
    G.add_edge("ctrl_Bin", "svc_Bin", relation="imports")
    G.add_edge("ctrl_Order", "svc_Order", relation="imports")
    G.add_edge("ctrl_Purchase", "svc_Purchase", relation="imports")
    
    root = Path("/fake/project")
    domains = business_cluster(G, root, language=Language.JAVA, min_domain_size=2)
    
    # 所有锚点应在同一个域 (bin)
    assert len(domains) == 1
    assert domains[0].id == "bin-data"  # 经过合并规则
    assert domains[0].anchors_count() == 9  # 3 ctrl + 3 svc_impl + 3 svc_api (mapper 的 _role 在 filter_noise 中设置)
```

---

## 15. 变更记录

| 日期 | 变更内容 | 版本 |
|------|---------|:----:|
| 2026-06-11 | 初始版本（基于架构文档 v1.0） | v1.0 |
| 2026-06-15 | 重写：根据实际代码实现更新，补充完整过滤规则、决策树、合并算法数学定义、多语言域键策略、测试用例 | v2.0 |

---

## 附录 A：配置项清单

用户可通过 `graph-wiki.yaml` 配置以下聚类参数：

```yaml
cluster:
  merge_threshold: 0.3        # 域合并密度阈值 (0-1)
  min_domain_size: 5          # 最小域锚点数
  language: auto              # 语言 (java/javascript/python/go/auto)
  
  # 自定义合并规则（扩展预定义规则）
  merge_rules:
    - keywords: ["mybiz", "mybizdata", "mybizorder"]
      target: "my-business"
  
  # 自定义角色映射（覆盖默认角色分类）
  role_overrides:
    - pattern: "*Facade.java"
      role: "controller"
  
  # 域键提取自定义跳过词
  domain_key_skip:
    java: ["smccloud", "ops", "hshcore", "platform"]
    js: ["api", "views", "components", "store", "router", "utils", "assets"]
```

## 附录 B：与 Graphify Louvain 的对比实验设计

Phase 4 需要 A/B 验证聚类质量，对比方法：

1. **准备测试集**：选取 3 个不同规模的项目（小型 ~100 文件，中型 ~500 文件，大型 ~5,000 文件）
2. **人工标注**：由项目开发者标注期望的域划分（ground truth）
3. **运行两种聚类**：
   - A 组：Graphify Louvain（原算法）
   - B 组：Graph-Wiki 包路径 + import 聚合（本算法）
4. **对比指标**：
   - 域数量与 ground truth 的偏差
   - 域内类与 ground truth 的 Jaccard 相似度
   - 人工评估：域命名是否可理解（1-5 分）
   - 处理时间（含全量 vs 增量）


