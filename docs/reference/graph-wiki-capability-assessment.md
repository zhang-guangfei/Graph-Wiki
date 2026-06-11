# Graph-Wiki 能力评估：10 项需求对照分析

> 日期：2026-06-11  
> 用途：验证 Graph-Wiki 设计是否能满足全部需求，识别差距和 Token 成本

---

## 评估符号说明

| 符号 | 含义 |
|:--:|------|
| ✅ | 当前设计已覆盖，可直接实现 |
| ⚠️ | 部分覆盖，需补充设计或消耗 Token |
| ❌ | 当前设计未覆盖，需新增模块 |
| 🔧 | 可通过保留 Graphify 能力来解决 |

---

## 逐项评估

### 需求 1：AI 读取单业务或跨业务关联

> "ai 能够通过它来读取单个业务或多个业务之间的关联"

**当前设计覆盖**：✅ 基本覆盖

| 能力 | 现状 | 数据来源 |
|------|------|---------|
| 读取单个域的完整信息 | ✅ | `wiki/{domain}/summary.md` + `code-map.md` |
| 查询跨域依赖 | ✅ | `domains.json` → `dependencies[]` + `wiki/{domain}/dependencies.md` |
| 理解依赖的原因 | ⚠️ | 需 LLM 读取两个域的 summary.md 来推断"为什么依赖" |

**Token 成本**：低（读取 domains.json ~2K tokens，读取 1 个域 ~3K tokens）

**差距**：
- 依赖关系基于 import 统计，缺少"调用方向"（A 调 B 还是 B 调 A？）
- **需补充**：`dependencies` 中增加 `direction` 字段（`A→B` / `B→A` / `双向`），通过 import 方向判断

---

### 需求 2：表/字段来源追溯 + 前后端数据流

> "ai 能够通过它来查询某个表和表中某个字段的来源...前端数据到后端数据库的数据流能够清晰的梳理出"

**当前设计覆盖**：❌ 未覆盖，需新增模块

**差距分析**：

```
需要追踪的链路：
  Vue 表单字段 → API 请求参数 → Controller 接收 → 
  Service 处理 → DTO 转换 → Entity 映射 → 
  @TableField / @Column 注解 → 数据库列
```

**当前 Graph-Wiki 只到类级别（code-map.md），没有字段级别的映射。**

**解决方案**：新增 **字段映射层（Field Mapping Layer）**

```
graph.json (保留全量 AST 数据)
    │
    ▼
┌─────────────────────────────┐
│ field_mapper.py  ← 新增     │
│                             │
│ 1. 从 AST 边中提取:         │
│    - @TableField / @Column  │
│      → DB 列映射            │
│    - DTO → Entity 转换      │
│    - API 参数 → DTO 映射    │
│                             │
│ 2. 前后端映射:              │
│    - 前端 API 请求字段       │
│    - → 后端 Controller 参数  │
│    - → Service 方法入参      │
│    - → DTO 字段              │
│    - → Entity 字段           │
│    - → 数据库列              │
│                             │
│ 3. 输出: field-map.json     │
│    每个域一个字段映射表       │
└─────────────────────────────┘
```

**field-map.json 结构**：

```json
{
  "bin-data": {
    "fields": [
      {
        "frontend_field": "supplierCode",
        "frontend_source": "csstock/apply/index.vue → uploadFile()",
        "api_endpoint": "POST /api/bindata/import",
        "controller_param": "BindataApply.supplierCode",
        "dto_field": "BindataApplyDTO.supplierCode",
        "entity_field": "BindataApply.supplierCode",
        "db_column": "bin_data_apply.supplier_code",
        "db_table": "bin_data_apply",
        "annotation": "@TableField(\"supplier_code\")",
        "value_rules": [
          {"priority": 1, "source": "Excel 列 31", "condition": "batch 模式"},
          {"priority": 2, "source": "mainSupplierCode", "condition": "默认"}
        ]
      }
    ],
    "tables": ["bin_data_apply", "bin_order_detail", ...]
  }
}
```

**Token 成本**：
- 生成阶段：中（需读取 Entity 和 DTO 的源码，解析注解）
- 查询阶段：低（直接读 field-map.json）
- LLM 总结字段规则：高（需要读取 ServiceImpl 中的赋值逻辑）

**Agent 使用示例**：

```
用户："前端的 supplierCode 对应数据库哪个字段？"
Agent：读取 field-map.json → 返回完整链路
      "前端 csstock/apply/index.vue 的上传 → 
       API POST /api/bindata/import → 
       BindataApply.supplierCode → 
       bin_data_apply.supplier_code"
```

---

### 需求 3：完整业务流程 + 字段取值规则

> "ai 能够通过它来描述单个完整业务的总体流程和各个字段的取值规则"

**当前设计覆盖**：⚠️ 部分覆盖

| 能力 | 现状 | 
|------|------|
| 业务流程描述 | ✅ summary.md 有 LLM 生成的核心流程 |
| 跨模块影响 | ✅ dependencies.md 列出影响的域 |
| 字段取值规则 | ❌ 需要 LLM 读取实际 Service 代码 |
| 字段来源优先级 | ❌ 同上 |

**Token 成本**：
- 业务流程生成：中（LLM 采样核心 Service，~2K tokens/域）
- 字段规则提取：高（需要深入读取包含赋值逻辑的代码，可能涉及多个方法）
- 查询：低（规则已写入文档后无需重复调用 LLM）

**需补充的设计**：
- `summary.md` 中增加"关键字段规则"章节
- 字段规则由 LLM 采样 ServiceImpl 源码提取，写入后人工确认

---

### 需求 4：代码缺陷识别 + 重构设计

> "ai 能够通过它来描述当前代码中的缺陷并进行代码重构设计"

**当前设计覆盖**：❌ 未覆盖，需新增模块

**差距分析**：这是代码审查/重构能力，不是知识图谱的核心功能。但 Graph-Wiki 可以**辅助**这个场景：

| 场景 | 能否支持 | 方式 |
|------|:--:|------|
| 识别大方法（>2000 行） | ✅ | graph.json 已有方法级节点，统计即可 |
| 识别大实体（>20 字段） | ✅ | AST 提取了字段节点，统计即可 |
| 识别 if 树复杂度 | ⚠️ | 需要 AST 提供方法体结构（当前 AST 不做） |
| 提出方法拆分方案 | ⚠️ | 需要 LLM 阅读完整方法源码 |
| 业务逻辑总结 | ⚠️ | LLM 可总结，但 2000 行方法需要大量 context |

**解决方案**：新增 **代码质量分析层（Code Quality Layer）**

```
graph.json (AST 全量数据)
    │
    ▼
┌─────────────────────────────┐
│ quality.py  ← 新增          │
│                             │
│ 静态分析（零 Token）：        │
│   - 方法行数统计             │
│   - 实体字段数统计           │
│   - 方法调用深度             │
│   - 循环复杂度（需要 AST 深度）│
│                             │
│ LLM 辅助（消耗 Token）：      │
│   - 大方法逻辑总结           │
│   - 重构方案建议             │
│   - if 树梳理                │
└─────────────────────────────┘
```

**Token 成本**：
- 静态指标：零
- 大方法重构建议：极高（需要把整个 2000 行方法发给 LLM）

**诚实结论**：Graph-Wiki 可以**识别**需要重构的代码（零成本），但**提出重构方案**需要 LLM 深度阅读代码（高成本），这不是知识图谱工具的强项。

---

### 需求 5：跨模块关联关系查询

> "ai 能够通过它查询跨多个模块的关联关系，比如事件总线...转订业务影响订单删单等"

**当前设计覆盖**：✅ 基本覆盖 + 🔧 依赖 graph.json

| 场景 | 方式 | Token 成本 |
|------|------|:--:|
| 查询所有事件发布器 | `graphify query` 在 graph.json 中搜索 | 零 |
| 汇总事件发布器 → 域映射 | 读取 graph.json 结果 + domains.json | 低 |
| 查询转订 → 删单的影响链 | `graphify path "A" "B"` | 零 |

**关键**：这类查询需要 graph.json 保留方法级节点。Graph-Wiki 的 Wiki 层是类级的，但 **graph.json 保留全量 AST 数据不变**。Agent 通过 Wiki 定位相关域后，在 graph.json 中做精确的方法级查询。

**Token 成本**：零（纯图遍历）到低（LLM 总结图遍历结果）

---

### 需求 6：业务逻辑 vs 工具类/基础设施分类

> "ai 能够识别出哪些代码是业务逻辑方法，哪些是工具类和基础设施"

**当前设计覆盖**：✅ 已内置

`business_cluster()` 的 Step 1（过滤噪音）已经做了这个分类：

```python
# cluster.py 中的角色分类
ANCHOR_ROLES = {'controller', 'service_impl', 'service_api', 
                'mapper', 'dao', 'adapter'}  # 业务骨架

NOISE_ROLES = {'config', 'util', 'helper'}    # 基础设施

# 输出到 domains.json 中
# 每个域只包含业务骨架节点
# 未分类的工具类放在 uncategorized 中
```

**Token 成本**：零（纯规则匹配）

**可扩展**：增加更多角色分类（`validator`, `converter`, `handler`, `listener`, `aspect` 等）

---

### 需求 7：分层业务大纲

> "人能够通过它来得到一个项目的业务大纲...分层要明确条理"

**当前设计覆盖**：⚠️ 部分覆盖（缺少中间层）

当前设计是**两层**：

```
业务域（~25 个）
  └── Controller / Service / Mapper / Entity 列表
```

但用户需要的是**三层**：

```
业务大纲（1 个）
  └── 业务域（~25 个）
        └── 业务点（每个域 3-10 个）
              └── 实现细节（类 → 方法）
```

**需补充**：在 `business_cluster()` 中增加**子域/业务点**划分

```
新增中间层 "业务点"：

"采购管理" 域
  ├── 业务点: "采购申请"  (PurchaseApplyController + PurchaseApplyService)
  ├── 业务点: "采购订单"  (PurchaseOrderController + PurchaseOrderService)
  ├── 业务点: "发票结算"  (InvoiceController + InvoiceService)
  └── 业务点: "供应商管理" (SupplierController + SupplierService)
```

**实现方式**：
- 在同一个域内，按 Controller 分组
- 如果一个域有 3 个 Controller → 至少 3 个业务点
- 共享的 Service/Mapper 归入最相关 Controller 所在的业务点
- LLM 可以帮助命名业务点（Token 成本低）

**Token 成本**：零（纯结构分析）到低（LLM 命名业务点）

---

### 需求 8：业务细节文档

> "业务细节包括接口文档，需求规格说明书，业务流程文档，详细设计文档"

**当前设计覆盖**：⚠️ 部分覆盖

| 文档类型 | 当前设计 | 能否自动生成 | Token 成本 |
|------|------|:--:|:--:|
| 接口文档（REST API） | ❌ 未覆盖 | ✅ 可从 Controller 注解提取 | 低 |
| 需求规格说明书 | spec.md（人工占位） | ❌ 不应自动生成 | — |
| 业务流程文档 | summary.md（LLM 初稿） | ⚠️ 需 LLM 深度阅读 | 高 |
| 详细设计文档 | ❌ 未覆盖 | ⚠️ 可从代码结构 + LLM 生成 | 高 |
| 数据流文档 | ❌ 未覆盖 | ⚠️ 需字段映射层 | 中 |
| 实体关系图 | ❌ 未覆盖 | ⚠️ 可从 Entity + JPA 注解生成 | 中 |
| 图形化展示 | ❌ 仅域级 HTML | ⚠️ 可生成 Mermaid/PlantUML | 低 |

**需补充的设计**：

```
wiki/{domain}/
├── summary.md          ← 已有
├── rules.md            ← 已有（人工占位）
├── spec.md             ← 已有（人工占位）
├── code-map.md         ← 已有
├── api-docs.md         ← 新增：从 Controller 注解自动提取
├── data-flow.md        ← 新增：从 field-map.json 生成
├── er-diagram.md       ← 新增：Mermaid ER 图
└── dependencies.md     ← 已有
```

**补充模块**：`graph_wiki/docs.py` — 自动生成 API 文档、数据流文档、ER 图

**Token 成本总结**：
- API 文档：零（从注解提取，规则匹配）
- 数据流：零（从 field-map.json 格式化）
- ER 图：零（从 Entity 注解生成 Mermaid）
- 业务流程图：中（LLM 采样生成）
- 详细设计文档：高（LLM 深度阅读 + 生成）

---

### 需求 9：自动更新 + 变更日志

> "修改代码后可以自动更新知识库...记录日志和摘要"

**当前设计覆盖**：⚠️ 部分覆盖

| 场景 | 当前设计 | Token 成本 |
|------|------|:--:|
| 小范围修改（<10 行） | ✅ `--update` 刷新 code-map | 零 |
| 大范围重构 | ✅ `--recluster` 重新划分域 | 低 |
| 新增需求（新模块） | ✅ `--update` 检测新文件 → 提示 `--recluster` | 零 |
| 变更日志 | ❌ 未覆盖 | 低（LLM 总结 diff） |
| 变更摘要 | ❌ 未覆盖 | 中（LLM 阅读 diff + 生成摘要） |

**需补充**：
- `manifest.json` 扩展：记录每次 build/update 的时间戳和变更文件列表
- `CHANGELOG.md`：自动生成，每次 update 追加一条记录
- 变更摘要：可选的 LLM 总结（`--update --summary`）

**Token 成本**：
- 零 Token：变更检测、文件列表、code-map 刷新
- 低 Token：LLM 生成变更摘要（diff + 文件列表 → 一句话总结）

---

### 需求 10：Obsidian + HTML + 独立搜索

> "完美支持 Obsidian 和 HTML 可视化重力图，不依赖 AI 也能支持业务点级别的搜索"

**当前设计覆盖**：✅ 基本覆盖

| 能力 | 当前设计 |
|------|---------|
| Obsidian 兼容 | ✅ 双向链接 Markdown，Wiki 目录可直接作为 Obsidian vault |
| HTML 可视化 | ✅ `domain_graph.html`（域级力导向图） |
| 独立搜索（不依赖 AI） | ⚠️ 需补充客户端搜索 |

**需补充**：
- HTML 中增加搜索栏（客户端 JS，搜索域名/类名）
- Obsidian 自带搜索，无需额外开发
- 可选的静态站点生成（VitePress 比 Obsidian 更适合非技术用户）

**Token 成本**：零

---

## 二、汇总矩阵

| # | 需求 | 覆盖状态 | Token 成本 | 需新增模块 | 
|:--:|------|:--:|:--:|------|
| 1 | 单业务/跨业务关联 | ✅ 基本覆盖 | 低 | 补充依赖方向字段 |
| 2 | 表/字段来源 + 前后端数据流 | ❌ 未覆盖 | 中（生成）/ 低（查询） | **field_mapper.py** |
| 3 | 完整业务流程 + 字段规则 | ⚠️ 部分覆盖 | 高（字段规则提取） | 扩展 summary.md 章节 |
| 4 | 代码缺陷 + 重构设计 | ❌ 未覆盖 | 零（识别）/ 极高（重构方案） | **quality.py** |
| 5 | 跨模块关联查询 | ✅ 覆盖 | 零~低 | 无（依赖 graph.json） |
| 6 | 业务 vs 基础设施分类 | ✅ 已内置 | 零 | 无 |
| 7 | 分层业务大纲 | ⚠️ 缺中间层 | 零~低 | 扩展 cluster.py 子域划分 |
| 8 | 业务细节文档 | ⚠️ 部分覆盖 | 零~高（因文档类型） | **docs.py** |
| 9 | 自动更新 + 变更日志 | ⚠️ 缺日志和摘要 | 零~低 | 扩展 manifest + CHANGELOG |
| 10 | Obsidian + HTML + 搜索 | ✅ 基本覆盖 | 零 | 补充客户端搜索 |

**总览**：

```
✅ 已覆盖（4 项）：  #1, #5, #6, #10
⚠️ 部分覆盖（4 项）：#3, #7, #8, #9
❌ 未覆盖（2 项）：  #2, #4

需新增模块（4 个）：
  field_mapper.py  — 字段级数据流追踪
  quality.py       — 代码质量静态分析
  docs.py          — API 文档 / 数据流 / ER 图自动生成
  + 扩展现有模块的子域划分
```

---

## 三、与 Graphify 的能力对比

### Graphify 能做到、Graph-Wiki 也能做到的

| 能力 | 方式 |
|------|------|
| AST 提取 | 复用 graphifyy 的 extract() |
| 文件检测 | 复用 graphifyy 的 detect() |
| 多语言支持 | 复用 graphifyy 的 30+ 语言解析器 |
| BFS/DFS 图遍历 | 保留 graph.json + query/path/explain CLI |
| 增量更新（manifest） | 复用 manifest.json 机制 |
| 提取缓存 | 复用 graphifyy 的 cache/ |
| HTML 导出 | 替换为域级可视化 |
| Wiki 导出 | 替换为业务 Wiki（核心差异） |
| Obsidian 导出 | 替换为 Obsidian 兼容 Wiki |

### Graphify 能做到、Graph-Wiki 做得更好的

| 能力 | Graphify | Graph-Wiki | 提升 |
|------|---------|-----------|------|
| 聚类质量 | 1,935 个社区（98% 无业务含义） | ~25 个域（LLM 标注） | **77:1 压缩比** |
| Wiki 可读性 | 方法名拼凑 | LLM 生成的业务描述 | **质的飞跃** |
| HTML 可用性 | 34K 节点乱麻 | ~25 节点域图 | **人类可用** |
| Agent 查询精度 | 全图搜索 | Wiki 定位 → 子图精确查 | **范围缩小 50x** |
| 增量更新速度 | 5-10 分钟 | ~80 秒 | **4-5x 加速** |
| 人类导航 | 无 | 三层业务大纲 | **从零到一** |

### Graphify 能做到、Graph-Wiki 做不到或更差的

| 能力 | 原因 | 严重程度 |
|------|------|:--:|
| 方法级节点粒度 | Graph-Wiki 默认类级视图 | 🟡 中 |
| 视频/音频转录 | Graph-Wiki 不做多媒体处理 | 🟢 低（OPS 不需要） |
| 语义文档提取（从 .md/.pdf） | Graph-Wiki 的 LLM 用途不同（标注 vs 提取） | 🟡 中 |
| MCP Server | Graph-Wiki 当前未设计 MCP 接口 | 🟡 中 |
| --watch 模式 | 未设计 | 🟢 低 |
| 社区凝度分数 | Graph-Wiki 不做 Louvain，无此指标 | 🟢 低 |
| 意外连接发现 | 依赖 Louvain 的跨社区桥接 | 🟡 中（可用域间 import 异常替代） |

---

## 四、需新增模块的详细设计概要

### 4.1 field_mapper.py — 字段级数据流追踪

```
输入：
  - graph.json (全量 AST 数据)
  - 前端 API 文件 (ops-frontend/src/api/*.js)
  - 后端 Entity 文件 (带 @TableField / @Column 注解)

处理流程：
  1. 解析 Entity 注解 → DB 列映射
  2. 解析 DTO 字段 → Entity 字段映射（通过同名/相似名匹配）
  3. 解析 Controller 参数 → DTO 映射
  4. 解析前端 API 请求体 → Controller 参数映射

输出：
  field-map.json (每个域一个)
  
Token 成本：零（纯规则匹配 + 注解解析）
LLM 可选：对模糊匹配的字段进行确认
```

### 4.2 quality.py — 代码质量静态分析

```
输入：
  - graph.json (全量 AST 数据，含方法级节点)

处理流程（零 Token）：
  1. 统计每个方法节点的行数 → 标记超大方法
  2. 统计每个类的字段数 → 标记超大实体
  3. 统计方法调用深度
  4. 检测循环依赖

输出：
  quality-report.json
  quality-report.md

可选 LLM 增强（消耗 Token）：
  - 对标记的超大方法进行逻辑总结
  - 提出方法拆分建议
```

### 4.3 docs.py — 自动文档生成

```
输入：
  - Controller 源码（带 Spring MVC 注解）
  - Entity 源码（带 JPA 注解）
  - field-map.json

处理流程：
  1. 从 @RequestMapping / @GetMapping 等注解提取 API 列表
  2. 从 @TableField / @Column 注解生成 ER 图（Mermaid）
  3. 从 field-map.json 生成数据流文档

输出：
  wiki/{domain}/api-docs.md     ← API 接口文档
  wiki/{domain}/er-diagram.md   ← Mermaid ER 图
  wiki/{domain}/data-flow.md    ← 数据流文档

Token 成本：零（全注解解析 + 模板生成）
```

---

## 五、Token 成本总览

| 操作 | 是否必须 LLM | 单次成本 | 频率 |
|------|:--:|------|------|
| AST 提取 + 图构建 | ❌ | 0 | 每次 build/update |
| 业务域聚类 | ❌ | 0 | 每次 build/recluster |
| LLM 域标注（名称+描述） | ✅ | ~$1（25 域） | 首次 build 一次 |
| 字段映射生成 | ❌ | 0 | 每次 build |
| 字段规则提取（LLM） | ✅ | ~$2-5（深度读代码） | 首次 + 重大变更 |
| API 文档生成 | ❌ | 0 | 每次 build |
| ER 图生成 | ❌ | 0 | 每次 build |
| 数据流文档 | ❌ | 0 | 每次 build |
| 代码质量静态分析 | ❌ | 0 | 每次 build |
| 大方法重构建议（LLM） | ✅ | ~$0.5-2/方法 | 按需 |
| 业务流程图（LLM） | ✅ | ~$0.3/域 | 首次 + 重大变更 |
| 变更摘要（LLM） | ✅ | ~$0.1/次 | 每次 update（可选） |
| Agent 查询（单域） | ❌ | 0 | 频繁 |
| Agent 查询（跨域） | ❌ | 0 | 频繁 |
| Agent 深度分析（读源码） | ✅ | ~$0.1-0.5 | 按需 |

**总结**：
- **零 Token 操作**（规则/注解/静态分析）：聚类、字段映射、API 文档、ER 图、代码指标
- **低 Token 操作**（一次性或可选）：域标注（$1）、变更摘要（$0.1/次）
- **高 Token 操作**（按需触发）：深度业务规则提取（$2-5）、大方法重构建议（$0.5-2/方法）
