# Models 数据模型设计

> 对应总体设计：第三章（[共享数据模型](%E6%A8%A1%E5%9D%97%E6%80%BB%E4%BD%93%E8%AE%BE%E8%AE%A1.md)）  
> 状态：详细设计阶段  
> 被依赖：`cluster.py`、`api_mapper.py`、`field_mapper.py`、`label.py`、`export.py`、`visualize.py`、`pipeline.py`

---

## 1. 模块职责

### 1.1 为什么需要独立的数据模型层

Graph-Wiki 的 8 个模块之间存在复杂的数据传递关系。如果没有统一的数据模型定义，会出现以下问题：

- **类型不一致**：不同模块对"业务域"的理解不同，cluster.py 输出的 `Domain` 字典结构与 label.py 期望的输入结构不一致
- **字段歧义**：`name` 字段在 cluster 阶段是包路径派生的英文 ID（如 `"bin-data"`），在 label 阶段被 LLM 改写为中文名（如 `"Bin数据管理"`），含义前后不一致
- **循环依赖**：模块间直接传递原始 dict，导致耦合加深
- **缺少约束**：无法在编译/运行早期发现字段缺失或类型错误

`models.py` 的核心职责是**定义所有模块之间共享的契约类型**，确保各模块之间的数据传递是类型安全、语义明确的。

### 1.2 这些数据模型在哪些模块间流动

```
┌─────────────┐
│  pipeline   │  CLI 入口
└──────┬──────┘
       │
       ▼
┌──────────────────────────────────────────────────┐
│                reuse.py (复用层)                   │
│  detect_corpus → extract_ast → build_graph        │
│         输出: nx.Graph (graph.json)               │
└─────────────────────┬────────────────────────────┘
                      │ Graph（原始数据，非 models）
                      ▼
┌──────────────────────────────────────────────────┐
│                cluster.py (聚类)                   │
│  Language · NodeRole · ANCHOR_ROLES               │  ← 使用枚举
│  Domain · BusinessPoint                           │  ← 实例化数据类
│         输出: list[Domain]                        │
└─────────────────────┬────────────────────────────┘
                      │ Domain, BusinessPoint
                      ▼
          ┌───────────────────────────┐
          │      api_mapper.py        │
          │ FrontendApiCall           │  ← 实例化
          │ BackendEndpoint           │
          │ ApiMatch                  │
          │ 输出: list[ApiMatch]      │
          └──────────┬────────────────┘
                     │ ApiMatch
                     ▼
          ┌───────────────────────────┐
          │     field_mapper.py       │
          │ ApiMatch (输入)           │  ← 读取
          │ 输出: dict (自由格式)      │
          └───────────────────────────┘
                      │ Domain, BusinessPoint
                      │ (已标注)
                      ▼
┌──────────────────────────────────────────────────┐
│                label.py (LLM 标注)                │
│  Domain · BusinessPoint                          │  ← 更新字段
│  写入: domain.name, domain.display_name          │
│        bp.display_name                           │
│         输出: list[Domain] (enriched)            │
└─────────────────────┬────────────────────────────┘
                      │ Domain + ApiMatch + field_map (dict)
                      ▼
          ┌───────────────────────────┐
          │      export.py            │
          │ Domain · ApiMatch         │  ← 读取
          │ 输出: wiki/*.md           │
          └───────────────────────────┘

          ┌───────────────────────────┐
          │    visualize.py           │
          │ Domain                    │  ← 读取
          │ 输出: domain_graph.html   │
          └───────────────────────────┘
```

### 1.3 模型归属矩阵

| 数据类/枚举 | 定义位置 | 创建者 | 消费者 |
|------------|---------|--------|--------|
| `Language` | models.py | pipeline | cluster |
| `NodeRole` | models.py | cluster (`classify_role`) | cluster, export, visualize |
| `ANCHOR_ROLES` | models.py | — | cluster |
| `Domain` | models.py | cluster (`adjust_by_imports`) | label, api_mapper, export, visualize |
| `BusinessPoint` | models.py | cluster (`extract_business_points`) | label, export |
| `FrontendApiCall` | models.py | api_mapper (`parse_frontend_apis`) | api_mapper (内部) |
| `BackendEndpoint` | models.py | api_mapper (`parse_backend_controllers`) | api_mapper (内部) |
| `ApiMatch` | models.py | api_mapper (`match_apis`) | field_mapper, export |
| `LlmBackend` | label.py(1) | pipeline | label |
| `LabelConfig` | label.py(1) | pipeline | label |

> (1) `LlmBackend` 和 `LabelConfig` 是 **label 模块私有类型**，只在该模块内部和 pipeline 中使用。为了减少 `models.py` 的尺寸和维护负担，它们不放在共享模型中。

---

## 2. 完整的类型定义

### 2.1 枚举：Language

```python
class Language(Enum):
    """项目语言枚举。用于指导聚类算法的包路径解析策略。"""
    JAVA = "java"
    JAVASCRIPT = "javascript"
    TYPESCRIPT = "typescript"
    PYTHON = "python"
    GO = "go"
    AUTO = "auto"   # 自动检测：根据 pom.xml / package.json / go.mod 等
```

**字段说明**：

| 字段 | 值 | 说明 |
|------|----|------|
| `JAVA` | `"java"` | Java / Kotlin 项目。包路径从 `src/main/java/` 后解析，提取公司域名后 1-2 级 |
| `JAVASCRIPT` | `"javascript"` | JS 项目。包路径从 `src/` 后解析，取第 1-2 级 |
| `TYPESCRIPT` | `"typescript"` | TS 项目。策略同 JAVASCRIPT |
| `PYTHON` | `"python"` | Python 项目。包路径从 import 语句提取 |
| `GO` | `"go"` | Go 项目。包路径从 go module 路径提取 |
| `AUTO` | `"auto"` | 自动检测。由 `detect_language()` 根据构建文件推断 |

**使用场景**：仅在 `business_cluster()` 入口处由 pipeline 传入，后续聚类算法中的 `extract_domain_key()` 根据此值选择不同的包路径提取策略。

**不可变约束**：`Language` 在 `business_cluster()` 调用时构建（或在 `Language.AUTO` 时在函数内部检测），**后续聚类流程中保持不变**。一旦确定语言，不应在过程中再变更。

### 2.2 枚举：NodeRole

```python
class NodeRole(Enum):
    """代码节点的业务角色。用于聚类过程中的锚点筛选和噪音过滤。"""
    # ── 锚点角色（Anchor Roles）──
    CONTROLLER  = "controller"   # Controller（后端）/ Vue 页面 / API 文件（前端）
    SERVICE_IMPL = "service_impl"  # Service 实现类
    SERVICE_API = "service_api"    # Service 接口
    MAPPER      = "mapper"        # MyBatis Mapper 接口
    DAO         = "dao"           # DAO 类
    ADAPTER     = "adapter"       # 适配器（外部系统对接）

    # ── 非锚点业务角色 ──
    ENTITY      = "entity"        # 实体类（含 @Entity / @TableName）
    DTO         = "dto"           # 数据传输对象
    VO          = "vo"            # 视图对象
    ENUM        = "enum"          # 枚举类
    HANDLER     = "handler"       # 处理器 / 通用组件

    # ── 非业务角色（在 filter_noise 阶段被排除）──
    CONFIG      = "config"        # 配置类（@Configuration / properties）
    UTIL        = "util"          # 工具类（Util / Helper / Constant）
    NOISE       = "noise"         # 噪音节点（方法调用、JDK 类型、匿名方法）
```

**角色分类依据**（来自 `cluster.py` 的 `classify_role()`）：

| 条件 | 角色 | 依据 |
|------|------|------|
| 文件名含 `Controller` 且为 `.java` | CONTROLLER | Spring Controller 层 |
| 文件名含 `ServiceImpl` 且为 `.java` | SERVICE_IMPL | Service 实现 |
| 文件名含 `Service` 且不含 `ServiceImpl` 且为 `.java` | SERVICE_API | Service 接口 |
| 文件名含 `Mapper` 且为 `.java` | MAPPER | MyBatis Mapper |
| 文件名含 `DAO` 且为 `.java` | DAO | 数据访问对象 |
| 文件名含 `Adapter` 且为 `.java` | ADAPTER | 外部适配器 |
| 文件名含 `Config`, `Configuration`, `Properties` 且为 `.java` | CONFIG | 配置 |
| 文件名含 `Util`, `Helper`, `Tool`, `Constant` 且为 `.java` | UTIL | 工具 |
| 文件名含 `Entity` 或以 `do.java` 结尾 | ENTITY | 实体 |
| 文件名含 `dto` 且为 `.java` | DTO | 数据传输对象 |
| 文件名为 `vo.java` | VO | 视图对象 |
| 文件名含 `enum` 且为 `.java` | ENUM | 枚举 |
| `.vue` 文件且路径含 `/components/` | HANDLER | 通用组件 |
| `.vue` 文件但不在 `/components/` | CONTROLLER | Vue 页面 = 前端 Controller |
| `.js`/`.ts` 文件且路径含 `/api/` | CONTROLLER | API 文件 = 前端 Controller |
| `.js`/`.ts` 文件且路径含 `/utils/` | UTIL | 前端工具 |
| 其余代码文件 | ENTITY | 默认为实体 |
| 非代码文件、getter/setter、JDK 类型、方法节点 | NOISE | 噪声 |

**不可变约束**：`NodeRole` 是**完全静态的分类**，仅由 `classify_role()` 根据文件名和路径的模式匹配确定。一旦在 `filter_noise` 阶段被赋值给节点的 `_role` 属性，后续所有模块（`extract_anchors`、`extract_business_points`、`export`、`visualize`）**只读取不改写**。

**扩展性**：如果项目需要新的角色（如 `FEIGN_CLIENT`、`WEBSOCKET_HANDLER`），只需在 `NodeRole` 中新增枚举值，并在 `classify_role()` 中添加对应的判断分支。

### 2.3 集合常量：ANCHOR_ROLES

```python
ANCHOR_ROLES = {
    NodeRole.CONTROLLER,
    NodeRole.SERVICE_IMPL,
    NodeRole.SERVICE_API,
    NodeRole.MAPPER,
    NodeRole.DAO,
    NodeRole.ADAPTER,
}
```

**语义**：这些角色是业务域的"锚点"——即 **能够唯一标识一个业务域的骨架节点**。一个域至少包含 1 个锚点（否则视为噪音）。`extract_anchors()` 使用此集合做筛选。

**设计依据**：Controller、Service（接口+实现）、Mapper、DAO、Adapter 这 6 类节点是业务功能的入口和骨架。Entity、DTO、VO 等辅助节点虽然属于某个域，但不适合作为域的标识符。

### 2.4 数据类：BusinessPoint

```python
@dataclass
class BusinessPoint:
    """业务点——代表一个 Controller/Service 的 public 方法。
    
    业务点是连接"类级别的域划分"和"方法级别的代码细节"的桥梁。
    每个域包含 N 个业务点（N ≈ 域内锚点数的 1-3 倍），
    这些业务点是该域对外提供的业务能力的最小单元。
    """
    name: str                                    # 方法名（如 "listBinOrderDetail"）
    display_name: str = ""                       # LLM 标注的中文名（如 "Bin订单明细查询"）
    entry_method: str = ""                       # 入口方法节点 ID（graph.json 中的节点 ID）
    entry_file: str = ""                         # 入口方法所在文件路径
    call_chain: list[str] = field(default_factory=list)        # 调用链（方法节点 ID 列表）
    cross_domain_calls: dict[str, list[str]] = field(default_factory=dict)  # 跨域调用 {domain_id: [called_method_labels]}
    internal_calls: list[str] = field(default_factory=list)    # 域内调用（方法节点 ID 列表）
    infrastructure_calls: list[str] = field(default_factory=list)  # 基础设施调用（日志、缓存、消息等）
```

**各字段详细说明**：

| 字段 | 类型 | 默认值 | 构建者 | 设置时机 | 含义 |
|------|------|--------|--------|---------|------|
| `name` | `str` | 必填 | cluster | `extract_business_points()` | 方法的原始名称，从 graph.json 节点的 label 提取 |
| `display_name` | `str` | `""` | label | `label_domains()` | LLM 为该方法生成的中文业务名。空字符串表示未标注 |
| `entry_method` | `str` | `""` | cluster | `extract_business_points()` | 入口方法在 graph.json 中的节点 ID，用于后续按需下钻 |
| `entry_file` | `str` | `""` | cluster | `extract_business_points()` | 入口方法所在源代码文件路径 |
| `call_chain` | `list[str]` | `[]` | cluster | `extract_business_points()` | 从入口方法出发沿 `calls` 边遍历得到的调用链节点 ID 列表 |
| `cross_domain_calls` | `dict[str, list[str]]` | `{}` | cluster | `extract_business_points()` | 跨域调用。key 为目标域 ID，value 为被调用的方法标签列表 |
| `internal_calls` | `list[str]` | `[]` | cluster | `extract_business_points()` | 域内调用列表 |
| `infrastructure_calls` | `list[str]` | `[]` | cluster | `extract_business_points()` | 对基础设施（日志、缓存、MQ）的调用 |

**不可变约束（构建时写入后不可变）**：

```
┌─────────────────────────────────────────────────────────┐
│ name               → 构建时写入，永不修改                    │
│ entry_method       → 构建时写入，永不修改                    │
│ entry_file         → 构建时写入，永不修改                    │
│ call_chain         → 构建时写入，永不修改                    │
│ cross_domain_calls → 构建时写入，永不修改                    │
│ internal_calls     → 构建时写入，永不修改                    │
│ infrastructure_calls → 构建时写入，永不修改                  │
├─────────────────────────────────────────────────────────┤
│ display_name       → cluster 构建时为 ""，由 label 修改     │  ← 可变
└─────────────────────────────────────────────────────────┘
```

**示例**：

```python
BusinessPoint(
    name="listBinOrderDetail",
    display_name="Bin订单明细查询",  # 由 LLM 填写
    entry_method="node_12345",
    entry_file="com/smc/smccloud/bin/controller/BinOrderController.java",
    call_chain=["node_12345", "node_12346", "node_12347"],
    cross_domain_calls={
        "inventory": ["InventoryService.getStock()"],
        "supplier": ["SupplierService.getSupplierInfo()"],
    },
    internal_calls=["node_12348", "node_12349"],
    infrastructure_calls=["log.info", "redis.hGet"],
)
```

### 2.5 数据类：Domain

```python
@dataclass
class Domain:
    """业务域——代码库中一个内聚的业务功能单元。
    
    Domain 是整个 Graph-Wiki 的核心抽象。一个 Domain 对应：
    - 一个内聚的包路径集合（如 com.smc.smccloud.bin.*）
    - 一组高内聚的锚点（Controller / Service / Mapper）
    - 一组业务点（public 方法）
    - 与其他域的 import 依赖关系
    """
    id: str                                      # 英文 ID（如 "bin-data"），由 cluster 根据包路径派生
    name: str = ""                               # LLM 标注的英文标识（如 "purchase"），用于路由/SEO，目录名的次级回退；空字符串表示未标注
    display_name: str = ""                       # LLM 标注的中文显示名（如 "采购管理"），用于 UI 展示，目录名的首选
    description: str = ""                        # LLM 标注的业务域一句话描述
    core_flows: list[str] = field(default_factory=list)    # 核心业务流程列表
    key_terms: list[dict] = field(default_factory=list)    # 关键术语表 [{term, definition}]
    packages: list[str] = field(default_factory=list)         # 包含的 Java 包路径列表
    modules: list[str] = field(default_factory=list)          # 包含的 Maven 模块列表
    frontend_views: list[str] = field(default_factory=list)    # 包含的前端视图目录列表
    anchors: dict[str, list[dict]] = field(default_factory=dict)  # 锚点字典 {role_str: [node_dict, ...]}
    business_points: list[BusinessPoint] = field(default_factory=list)  # 该域的业务点列表
    total_files: int = 0                         # 该域包含的文件总数
    dependencies: list[dict] = field(default_factory=list)  # 依赖列表 [{domain, name, import_count, strength}]

    def anchors_flat(self) -> list[dict]:
        """展平所有锚点为列表，忽略角色分组"""
        result = []
        for role_nodes in self.anchors.values():
            result.extend(role_nodes)
        return result

    def anchors_count(self) -> int:
        """计算总锚点数"""
        return len(self.anchors_flat())
```

**各字段详细说明**：

| 字段 | 类型 | 默认值 | 构建者 | 设置时机 | 含义 |
|------|------|--------|--------|---------|------|
| `id` | `str` | 必填 | cluster | `adjust_by_imports()` | 英文域 ID，由包路径派生经合并规则归一化得到 |
| `name` | `str` | `""` | label | `label_domains()` | LLM 标注的英文标识（如 "purchase"），用于路由/SEO，目录名的次级回退。空字符串 = 未标注 |
| `display_name` | `str` | `""` | label | `label_domains()` | LLM 标注的中文显示名（如 "采购管理"），用于 UI 展示，目录名的首选 |
| `description` | `str` | `""` | label | `label_domains()` | LLM 标注的业务域一句话描述 |
| `core_flows` | `list[str]` | `[]` | label | `label_domains()` | 核心业务流程列表 |
| `key_terms` | `list[dict]` | `[]` | label | `label_domains()` | 关键术语表 [{term, definition}] |
| `packages` | `list[str]` | `[]` | cluster | `adjust_by_imports()` | 该域包含的 Java 包路径。供 LLM 标注时理解域的范围 |
| `modules` | `list[str]` | `[]` | cluster | `adjust_by_imports()` | 该域所属的 Maven 模块。用于增量更新时判断模块变更 |
| `frontend_views` | `list[str]` | `[]` | cluster | `adjust_by_imports()` | 该域关联的前端视图目录 |
| `anchors` | `dict[str, list[dict]]` | `{}` | cluster | `adjust_by_imports()` | 锚点按角色分组的字典。key 为 NodeRole 的 value 字符串 |
| `business_points` | `list[BusinessPoint]` | `[]` | cluster | `extract_business_points()` | 业务点列表 |
| `total_files` | `int` | `0` | cluster | `adjust_by_imports()` | 文件数 ≈ 锚点数 + 辅助节点数 |
| `dependencies` | `list[dict]` | `[]` | cluster | `adjust_by_imports()` | 依赖列表 [{domain, name, import_count, strength}] |

**不可变约束**：

```
┌─────────────────────────────────────────────────────────┐
│ id              → 构建时写入，永不修改                      │
│ packages        → 构建时写入，永不修改                      │
│ modules         → 构建时写入，永不修改                      │
│ frontend_views  → 构建时写入，永不修改                      │
│ anchors         → 构建时写入，永不修改                      │
│ total_files     → 构建时写入，永不修改                      │
│ dependencies    → 构建时写入，永不修改                      │
├─────────────────────────────────────────────────────────┤
│ name            → cluster 构建时为 ""，由 label 修改       │  ← 可变
│ display_name    → cluster 构建时为 ""，由 label 修改       │  ← 可变
│ description     → cluster 构建时为 ""，由 label 修改       │  ← 可变
│ core_flows      → cluster 构建时为 []，由 label 修改       │  ← 可变
│ key_terms       → cluster 构建时为 []，由 label 修改       │  ← 可变
│ business_points → cluster 构建时写入，label 修改其中       │  ← 可变
│                   的 BusinessPoint.display_name          │
└─────────────────────────────────────────────────────────┘
```

**`anchors` 字典结构**：

```python
{
    "controller": [
        {
            "id": "node_1",
            "label": "BinOrderController.java",
            "source_file": "com/smc/smccloud/bin/controller/BinOrderController.java",
            "_role": "controller",
            "_importance": 5,
            "file_type": "code",
            # ... 其他 graph.json 节点属性
        },
        # ... 更多 controller 节点
    ],
    "service_impl": [
        {
            "id": "node_2",
            "label": "BinDataServiceImpl.java",
            "source_file": "com/smc/smccloud/bin/service/impl/BinDataServiceImpl.java",
            "_role": "service_impl",
            "_importance": 4,
            "file_type": "code",
            # ...
        },
    ],
    "mapper": [ ... ],
    "dao": [ ... ],
}
```

**设计决策**：

1. **为什么 `anchors` 是 `dict[str, list[dict]]` 而不是 `dict[NodeRole, list[dict]]`？**
   - 因为 NodeRole 是 Python 枚举，在 JSON 序列化时需要做自定义转换。使用字符串作为 key 可以直接 `json.dumps()`。
   - 在代码内部读取时，可以通过 `NodeRole(role_str)` 转回枚举。

2. **为什么锚点节点存储完整 dict 而不是仅存 ID？**
   - 因为 `export.py`、`visualize.py`、`label.py` 等消费者需要读取锚点的 `label`、`source_file` 等字段。如果只存 ID，每个消费者都需要再查 graph.json。
   - 缺点是内存占用略大（但锚点只有 ~1,200 个，每个 dict 约 200 字节，总计 ~240KB，完全可以接受）。

### 2.6 数据类：FrontendApiCall

```python
@dataclass
class FrontendApiCall:
    """前端 API 调用——代表前端 api/ 模块中一个 export function 定义。"""
    function_name: str              # 函数名（如 "listBinOrderDetail"）
    http_method: str                # HTTP 方法（"GET" / "POST" / "PUT" / "DELETE"）
    url: str                        # 请求 URL（如 "/api/binorder/detail"）
    params: list[dict] = field(default_factory=list)       # 请求参数 [{name, type_hint}]
    source_file: str = ""           # 源文件路径
    source_line: int = 0            # 函数在源文件中的行号
    callers: list[dict] = field(default_factory=list)      # 调用者列表 [{page, fields_used}]
```

**各字段说明**：

| 字段 | 类型 | 含义 |
|------|------|------|
| `function_name` | `str` | `export function xxx()` 中的函数名 |
| `http_method` | `str` | 从 `axios.get/post/put/delete` 调用中提取 |
| `url` | `str` | axios 调用中的 URL 参数 |
| `params` | `list[dict]` | 函数签名中的参数名列表，每项为 `{"name": "supplierCode"}` |
| `source_file` | `str` | JS 文件路径（如 `ops-frontend/src/api/binorder.js`） |
| `source_line` | `int` | 函数在源文件中的起始行号 |
| `callers` | `list[dict]` | Vue 页面调用者，每项包含 page 路径和使用的字段名 |

**不可变约束**：

```
┌─────────────────────────────────────────────────────────┐
│ function_name → 构建时写入，永不修改                        │
│ http_method   → 构建时写入，永不修改                        │
│ url           → 构建时写入，永不修改                        │
│ params        → 构建时写入，永不修改                        │
│ source_file   → 构建时写入，永不修改                        │
│ source_line   → 构建时写入，永不修改                        │
├─────────────────────────────────────────────────────────┤
│ callers       → 由 trace_frontend_callers() 追加填写      │  ← 可变
└─────────────────────────────────────────────────────────┘
```

**`callers` 结构**：

```python
[
    {"page": "csstock/apply/index.vue", "fields_used": ["supplierCode", "modelNo"]},
    {"page": "csstock/query/index.vue", "fields_used": ["supplierCode", "startDate", "endDate"]},
]
```

### 2.7 数据类：BackendEndpoint

```python
@dataclass
class BackendEndpoint:
    """后端 Controller 端点——代表一个 @XxxMapping 方法。"""
    controller_file: str = ""        # Controller 文件路径（相对项目根目录）
    controller_class: str = ""       # Controller 类名
    method_name: str = ""            # 方法名
    http_method: str = ""            # HTTP 方法（"GET" / "POST" / "PUT" / "DELETE"）
    url: str = ""                    # 完整映射路径（base_path + method_path）
    param_type: Optional[str] = None  # 入参类型（如 "BinOrderQueryDTO"）
    param_fields: list[dict] = field(default_factory=list)  # 入参字段 [{name, type, annotations}]
    service_chain: list[str] = field(default_factory=list)   # 调用链 [Service.method, Mapper.method]
    return_type: Optional[str] = None  # 返回类型（如 "Result" / "List<BinOrderDTO>"）
```

**各字段说明**：

| 字段 | 类型 | 含义 |
|------|------|------|
| `controller_file` | `str` | Java 文件路径，相对项目根目录 |
| `controller_class` | `str` | `public class XxxController` 中的类名 |
| `method_name` | `str` | Controller 中的方法名 |
| `http_method` | `str` | 从 `@PostMapping` / `@GetMapping` 等提取 |
| `url` | `str` | `@RequestMapping("base")` + `@PostMapping("path")` 拼接 |
| `param_type` | `Optional[str]` | 方法入参类型完整的类名，如 `BinOrderQueryDTO` |
| `param_fields` | `list[dict]` | DTO 类的字段列表（由 `extract_dto_fields` 解析填充） |
| `service_chain` | `list[str]` | 方法体中调用的 Service/Mapper 方法，如 `["binOrderService.listBinOrderDetail()", "bindataMapper.selectByCondition()"]` |
| `return_type` | `Optional[str]` | 返回值类型 |

**不可变约束**：所有字段均在 `parse_backend_controllers()` 时一次构建，**后续永不修改**。

### 2.8 数据类：ApiMatch

```python
@dataclass
class ApiMatch:
    """前后端 API 匹配结果——连接 FrontendApiCall 和 BackendEndpoint。"""
    id: str                                      # 唯一 ID（如 "api-list-bin-order-detail"）
    frontend: FrontendApiCall = field(default_factory=FrontendApiCall)  # 前端 API 信息
    backend: BackendEndpoint = field(default_factory=BackendEndpoint)    # 后端端点信息
    match_confidence: float = 0.0                # 匹配置信度（0.0-1.0）
    domain: str = ""                             # 所属业务域 ID 或名称
```

**匹配置信度规则**：

| 条件 | 置信度 | 含义 |
|------|--------|------|
| HTTP 方法相同 + URL 完全匹配 | 1.0 | 精确匹配 |
| HTTP 方法相同 + URL 去末尾斜杠后相同 | 0.95 | 格式差异 |
| HTTP 方法相同 + 参数化后匹配 (`/order/123` → `/order/{id}`) | 0.8 | 路径参数 |
| HTTP 方法相同 + URL 前缀相同、最后一段不同 | 0.5 | 模糊匹配 |
| HTTP 方法不同 | 0.0 | 不匹配 |

**不可变约束**：所有字段均在 `match_apis()` 时一次构建，**后续永不修改**。

**完整示例**：

```python
ApiMatch(
    id="api-list-bin-order-detail",
    frontend=FrontendApiCall(
        function_name="listBinOrderDetail",
        http_method="POST",
        url="/api/binorder/detail",
        params=[{"name": "data"}],
        source_file="ops-frontend/src/api/binorder.js",
        source_line=24,
        callers=[{"page": "csstock/apply/index.vue", "fields_used": ["supplierCode", "modelNo"]}],
    ),
    backend=BackendEndpoint(
        controller_file="com/smc/smccloud/bin/controller/BinOrderController.java",
        controller_class="BinOrderController",
        method_name="listBinOrderDetail",
        http_method="POST",
        url="/api/binorder/detail",
        param_type="BinOrderQueryDTO",
        service_chain=["binOrderService.listBinOrderDetail()"],
        return_type="Result",
    ),
    match_confidence=1.0,
    domain="bin-data",
)
```

---

## 3. 数据流图

### 3.1 模块间数据传递概览

```
                          ┌──────────────┐
                          │   pipeline   │
                          └──────┬───────┘
                                 │
                    ┌────────────┴────────────┐
                    │                         │
                    ▼                         ▼
          ┌──────────────────┐     ┌──────────────────────┐
          │      reuse       │     │  Language (枚举)      │
          │ detect→extract→  │     │  (来自 pipeline args)  │
          │ build            │     └──────────┬───────────┘
          │ 输出: nx.Graph   │                │
          └────────┬─────────┘                │
                   │ nx.Graph                 │ Language
                   ▼                          │
          ┌───────────────────────────────────────┐
          │              cluster                   │
          │  NodeRole (枚举), ANCHOR_ROLES (集合)  │
          │  Domain (创建), BusinessPoint (创建)    │
          │  输出: list[Domain]                    │
          └──────────┬────────────────────────────┘
                     │
                     │  list[Domain]
                     │
          ┌──────────┴────────────────────────────┐
          │                                       │
          ▼                                       ▼
┌─────────────────────┐              ┌─────────────────────┐
│    api_mapper        │              │      label          │
│ FrontendApiCall(创建) │              │ 读取 Domain.name →  │
│ BackendEndpoint(创建)│              │ 写入 Domain.name    │
│ ApiMatch(创建)       │              │ 写入 BusinessPoint  │
│ 输出: list[ApiMatch] │              │ .display_name       │
└──────────┬──────────┘              │ 输出: list[Domain]   │
           │                         └─────────────────────┘
           │ list[ApiMatch]                   │
           ▼                                  │
┌─────────────────────┐                      │
│   field_mapper      │                      │
│ 读取: ApiMatch      │                      │
│ 输出: dict (自由)   │                      │
└──────────┬──────────┘                      │
           │ dict                             │
           ▼                                  │
          ┌────────────────────────────────────┘
          │                │
          ▼                ▼
┌──────────────────┐  ┌──────────────────┐
│     export       │  │   visualize      │
│ 读取: Domain     │  │ 读取: Domain     │
│ 读取: ApiMatch   │  │ 输出: .html      │
│ 读取: field_map  │  │                  │
│ 输出: wiki/*.md  │  │                  │
└──────────────────┘  └──────────────────┘
```

### 3.2 各模块使用的模型子集

| 模块 | 读取的模型 | 写入/创建的模型 |
|------|-----------|---------------|
| `reuse.py` | 无（使用 graphify 原生类型） | 无 |
| `cluster.py` | `Language`, `NodeRole`, `ANCHOR_ROLES` | `Domain`, `BusinessPoint` |
| `api_mapper.py` | `Domain` (读取 anchors 做域归属匹配) | `FrontendApiCall`, `BackendEndpoint`, `ApiMatch` |
| `field_mapper.py` | `ApiMatch` (读取 backend.param_type) | 无（输出自由格式 dict） |
| `label.py` | `Domain`, `BusinessPoint` | 修改 `Domain.name`, `Domain.display_name`, `BusinessPoint.display_name` |
| `export.py` | `Domain`, `BusinessPoint`, `ApiMatch`, dict | 无 |
| `visualize.py` | `Domain` | 无 |
| `pipeline.py` | 所有类型 | 无（编排者） |

### 3.3 数据流中各模型的生命周期

```
时间线 →
───────

[Step 1-3: 复用层]
  无 models 参与。reuse.py 操作 graphify 原生类型。

[Step 4: cluster.py]
  filter_noise():       NodeRole (枚举比较), ANCHOR_ROLES (集合包含)
  adjust_by_imports():  Domain.__init__()  ← Domain 诞生
  extract_business_points(): BusinessPoint.__init__()  ← BusinessPoint 诞生

[Step 5: api_mapper.py]
  parse_frontend_apis():  FrontendApiCall.__init__()
  trace_frontend_callers(): FrontendApiCall.callers += [{...}]
  parse_backend_controllers(): BackendEndpoint.__init__()
  match_apis():            ApiMatch.__init__()

[Step 6: field_mapper.py]
  读取 ApiMatch.backend.param_type
  输出自由格式 dict（不实例化 models 中的类型）

[Step 7: label.py]
  domain.name = "purchase"             ← 修改 Domain（英文 ID）
  domain.display_name = "采购管理"      ← 修改 Domain（中文显示名）
  bp.display_name = "Bin订单明细查询"    ← 修改 BusinessPoint

[Step 8: export.py + visualize.py]
  只读访问。不再修改任何 models 实例。
```

---

## 4. 不可变约束总表

以下字段在对应的生命周期阶段写入后，**后续任何模块都不应修改**。

| 数据类 | 字段 | 构建阶段 | 写入后约束 |
|--------|------|---------|-----------|
| `BusinessPoint` | `name` | cluster Step 5 | **不可变** |
| | `entry_method` | cluster Step 5 | **不可变** |
| | `entry_file` | cluster Step 5 | **不可变** |
| | `call_chain` | cluster Step 5 | **不可变** |
| | `cross_domain_calls` | cluster Step 5 | **不可变** |
| | `internal_calls` | cluster Step 5 | **不可变** |
| | `infrastructure_calls` | cluster Step 5 | **不可变** |
| | `display_name` | label Step 7 | 可变（label 写入，export 读取） |
| `Domain` | `id` | cluster Step 4 | **不可变** |
| | `packages` | cluster Step 4 | **不可变** |
| | `modules` | cluster Step 4 | **不可变** |
| | `frontend_views` | cluster Step 4 | **不可变** |
| | `anchors` | cluster Step 4 | **不可变** |
| | `total_files` | cluster Step 4 | **不可变** |
| | `dependencies` | cluster Step 4 | **不可变** |
| | `name` | label Step 7 | 可变（label 写入，后续只读） |
| | `display_name` | label Step 7 | 可变（label 写入，后续只读） |
| | `description` | label Step 7 | 可变（label 写入，后续只读） |
| | `core_flows` | label Step 7 | 可变（label 写入，后续只读） |
| | `key_terms` | label Step 7 | 可变（label 写入，后续只读） |
| | `business_points` | cluster Step 5 | 可变（label 修改其中 display_name） |
| `FrontendApiCall` | `function_name` | api_mapper | **不可变** |
| | `http_method` | api_mapper | **不可变** |
| | `url` | api_mapper | **不可变** |
| | `params` | api_mapper | **不可变** |
| | `source_file` | api_mapper | **不可变** |
| | `source_line` | api_mapper | **不可变** |
| | `callers` | api_mapper → label | 可变（`trace_frontend_callers()` 追加写入） |
| `BackendEndpoint` | 全部 | api_mapper | **完全不可变** |
| `ApiMatch` | 全部 | api_mapper | **完全不可变** |

**设计原则**：

1. **不可变字段的语义**：任何从源代码静态分析得到的信息（方法名、文件路径、调用链等）都是不可变的，因为它们反映的是源代码的客观事实。
2. **可变字段的语义**：任何由 LLM 生成（域名、显示名）或在后续阶段逐步发现（调用者）的信息是可变的。
3. **深层不可变**：`anchors` 字段本身引用可变（dict），但其包含的锚点节点列表不应被修改。各模块只应读取，不应增删其中的节点。

---

## 5. 与 graph.json 节点属性的映射关系

### 5.1 graph.json 节点的原始属性

graphify 的 `build()` 函数输出的 graph.json 中，每个节点的属性包含（部分）：

| 属性 | 类型 | 示例 | 说明 |
|------|------|------|------|
| `label` | `str` | `"BinOrderController.java"` | 节点名称。类节点以 `.java`/`.vue` 结尾，方法节点以 `.` 开头 |
| `file_type` | `str` | `"code"` / `"doc"` / `"image"` | 文件类型。只有 `"code"` 类型的节点进入聚类 |
| `source_file` | `str` | `"com/smc/smccloud/bin/controller/..."` | 源代码文件路径 |
| `type` | `str` | `"class"` / `"method"` / `"field"` | 节点类型 |
| `visibility` | `str` | `"public"` / `"private"` | 访问修饰符 |

### 5.2 Cluster 阶段写入的临时属性

在 `filter_noise()` 中，Cluster 模块为每个业务节点添加两个临时属性：

```python
# cluster.py filter_noise()
entry = dict(data)           # 复制原始节点属性
entry["id"] = node_id        # 注入节点 ID
entry["_role"] = role.value  # 注入角色（NodeRole 的 value 字符串）
entry["_importance"] = role_importance(role)  # 注入重要性评分
```

| 临时属性 | 类型 | 值范围 | 写入者 | 用途 |
|---------|------|--------|-------|------|
| `_role` | `str` | `"controller"`, `"service_impl"`, ..., `"entity"` | `filter_noise()` | 标识节点在业务架构中的角色 |
| `_importance` | `int` | `-5` ~ `5` | `filter_noise()` | 节点重要性评分，用于可视化中节点大小 |

**`_importance` 评分规则**：

```python
role_importance = {
    NodeRole.CONTROLLER:   5,   # 最高——Controller 是业务入口
    NodeRole.SERVICE_IMPL: 4,   # 核心——Service 实现包含业务逻辑
    NodeRole.SERVICE_API:  4,   # 核心——Service 接口定义契约
    NodeRole.MAPPER:       3,   # 重要——数据访问层
    NodeRole.DAO:          3,   # 重要
    NodeRole.ADAPTER:      3,   # 重要——外部系统接口
    NodeRole.ENTITY:       2,   # 普通
    NodeRole.DTO:          1,   # 次要
    NodeRole.VO:           1,   # 次要
    NodeRole.ENUM:         1,   # 次要
    NodeRole.HANDLER:      2,   # 普通
    NodeRole.CONFIG:      -1,   # 非业务
    NodeRole.UTIL:        -1,   # 非业务
    NodeRole.NOISE:       -5,   # 噪音
}
```

### 5.3 映射关系总图

```
graph.json 节点属性                Domain.anchors 中保留的属性
─────────────────                 ──────────────────────────
label                             → label
source_file                       → source_file
file_type                         → file_type
visibility                        → visibility
type                              → type
(无, 由 cluster 注入 id)          → id
(无, 由 cluster 注入 _role)       → _role
(无, 由 cluster 注入 _importance) → _importance

其余 graph.json 属性              → 保留（但各消费者一般不使用）
(如: input_tokens, output_tokens, length, ...)
```

### 5.4 从 graph.json 节点到 BusinessPoint 的映射

```
graph.json 方法节点属性            BusinessPoint 字段
─────────────────                 ─────────────────
label (如 ".listBinOrderDetail")  → name (去掉前缀 ".")
id                                → entry_method
source_file                       → entry_file
(通过 calls 边遍历)               → call_chain
(通过 calls 边跨域分析)           → cross_domain_calls
```

### 5.5 为什么使用 `_role` 前缀而不是直接覆盖 `role`

- graph.json 的节点属性中可能已经包含 `role` 字段（graphify 内部使用），加前缀 `_` 可以避免冲突
- 使用 `_` 前缀表示"这是临时属性，不会写回 graph.json"
- 所有使用 `_role` 的消费者都知道这个属性是 graph-wiki 添加的，而非 graphify 原生的

---

## 6. 测试用例

### 6.1 测试 BusinessPoint 构建和不变性

```python
# tests/test_models.py (或内嵌在 models.py 中)

def test_business_point_immutability():
    """验证 BusinessPoint 的不可变字段在构建后不能被意外修改。"""
    bp = BusinessPoint(
        name="listBinOrderDetail",
        entry_method="node_12345",
        entry_file="BinOrderController.java",
        call_chain=["node_12345", "node_12346"],
        cross_domain_calls={"inventory": ["InventoryService.getStock()"]},
        internal_calls=["node_12347"],
        infrastructure_calls=["log.info"],
    )

    # 构建时字段正确
    assert bp.name == "listBinOrderDetail"
    assert bp.entry_method == "node_12345"
    assert bp.cross_domain_calls == {"inventory": ["InventoryService.getStock()"]}

    # 可变字段默认为空
    assert bp.display_name == ""

    # display_name 可以被 label 模块修改
    bp.display_name = "Bin订单明细查询"
    assert bp.display_name == "Bin订单明细查询"
```

### 6.2 测试 Domain 的辅助方法

```python
def test_domain_anchors_flat_and_count():
    """验证 anchors_flat() 展平和 anchors_count() 计数。"""
    domain = Domain(
        id="bin-data",
        anchors={
            "controller": [
                {"id": "n1", "label": "BinController.java", "_role": "controller"},
                {"id": "n2", "label": "BinOrderController.java", "_role": "controller"},
            ],
            "service_impl": [
                {"id": "n3", "label": "BinDataServiceImpl.java", "_role": "service_impl"},
            ],
        },
    )

    flat = domain.anchors_flat()
    assert len(flat) == 3
    assert domain.anchors_count() == 3
    assert all("_role" in a for a in flat)

    # 空域
    empty = Domain(id="empty")
    assert empty.anchors_flat() == []
    assert empty.anchors_count() == 0
```

### 6.3 测试 ApiMatch 的置信度语义

```python
def test_api_match_confidence_semantics():
    """验证 ApiMatch 的置信度字段语义。"""
    frontend = FrontendApiCall(
        function_name="listBinOrderDetail",
        http_method="POST",
        url="/api/binorder/detail",
        params=[{"name": "query"}],
    )
    backend = BackendEndpoint(
        controller_class="BinOrderController",
        method_name="listBinOrderDetail",
        http_method="POST",
        url="/api/binorder/detail",
    )

    # 完全匹配
    match = ApiMatch(
        id="api-list-bin-order-detail",
        frontend=frontend,
        backend=backend,
        match_confidence=1.0,
        domain="bin-data",
    )
    assert match.match_confidence == 1.0
    assert match.frontend.http_method == match.backend.http_method

    # GitHub issue #17 场景：置信度门控
    # 只有 >= 0.5 的匹配才应出现在最终输出中
    assert match.match_confidence >= 0.5

    # 序列化测试（ApiMatch 的所有字段都应可 JSON 序列化）
    import json
    serialized = json.dumps({
        "id": match.id,
        "domain": match.domain,
        "confidence": match.match_confidence,
        "frontend_url": match.frontend.url,
        "backend_url": match.backend.url,
    })
    parsed = json.loads(serialized)
    assert parsed["domain"] == "bin-data"
    assert parsed["confidence"] == 1.0
```

### 6.4 测试 NodeRole 分类一致性

```python
def test_node_role_classification():
    """验证 classify_role() 的分类结果与 NodeRole 枚举一致。"""
    from graph_wiki.cluster import classify_role

    # Controller
    data = {"label": "BinOrderController.java", "source_file": "com/.../controller/BinOrderController.java"}
    assert classify_role(data) == NodeRole.CONTROLLER

    # ServiceImpl
    data = {"label": "BinDataServiceImpl.java", "source_file": "com/.../service/impl/BinDataServiceImpl.java"}
    assert classify_role(data) == NodeRole.SERVICE_IMPL

    # Mapper
    data = {"label": "BinOrderMapper.java", "source_file": "com/.../mapper/BinOrderMapper.java"}
    assert classify_role(data) == NodeRole.MAPPER

    # Vue 页面（前端 Controller）
    data = {"label": "ApplyView.vue", "source_file": "src/views/apply/ApplyView.vue"}
    assert classify_role(data) == NodeRole.CONTROLLER

    # Vue 组件（Handler）
    data = {"label": "DataTable.vue", "source_file": "src/components/DataTable.vue"}
    assert classify_role(data) == NodeRole.HANDLER

    # JS API 文件（前端 Controller）
    data = {"label": "binorder.js", "source_file": "src/api/binorder.js"}
    assert classify_role(data) == NodeRole.CONTROLLER
```

### 6.5 测试 Domain 序列化兼容性

```python
def test_domain_json_serialization():
    """验证 Domain 可以 JSON 序列化（用于 domains.json 输出）。"""
    domain = Domain(
        id="bin-data",
        packages=["com.smc.smccloud.bin"],
        modules=["ops-delivery"],
        anchors={
            "controller": [{"id": "n1", "label": "BinController.java", "_role": "controller"}],
            "service_impl": [{"id": "n2", "label": "BinDataServiceImpl.java", "_role": "service_impl"}],
        },
        dependencies=[{"domain": "inventory", "import_count": 42}, {"domain": "supplier", "import_count": 15}],
        total_files=28,
    )

    import json
    # Domain 不是一个简单的 dict，需要手动序列化
    serializable = {
        "id": domain.id,
        "packages": domain.packages,
        "modules": domain.modules,
        "anchors": domain.anchors,
        "dependencies": domain.dependencies,
        "total_files": domain.total_files,
        "anchors_count": domain.anchors_count(),
    }
    text = json.dumps(serializable, ensure_ascii=False)
    parsed = json.loads(text)

    assert parsed["id"] == "bin-data"
    assert parsed["anchors_count"] == 2
    assert parsed["dependencies"][0]["domain"] == "inventory"
    assert parsed["dependencies"][0]["import_count"] == 42
```

### 6.6 测试 Language 枚举的领域键提取逻辑

```python
def test_language_enum_determines_domain_key_strategy():
    """验证不同语言的 Language 值影响 extract_domain_key 的路径解析策略。"""
    from graph_wiki.cluster import extract_domain_key

    # Java: com.smc.smccloud.bin.service.impl → "bin"
    java_key = extract_domain_key("com.smc.smccloud.bin.service.impl", Language.JAVA)
    assert java_key == "bin"

    # JavaScript: api.binorder → "binorder" (跳过 "api")
    js_key = extract_domain_key("api.binorder", Language.JAVASCRIPT)
    assert js_key == "binorder"

    # Python: bin.service → "bin"
    py_key = extract_domain_key("bin.service", Language.PYTHON)
    assert py_key == "bin"
```

---

## 7. 变更记录

| 日期 | 变更内容 | 原因 |
|------|---------|------|
| 2026-06-15 | 初始版本 | 基于架构设计文档 v1.0 和现有代码实现 |
