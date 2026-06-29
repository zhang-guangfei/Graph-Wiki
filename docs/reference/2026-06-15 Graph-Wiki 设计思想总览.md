# Graph-Wiki 设计思想总览

> 基于 `docs/architecture/graph-wiki-design.md` 及 5 份模块详细设计文档提炼

---

## 一、要解决什么问题

程序员面对一个 5,000+ 文件的遗留代码库时，需要回答：
- 这个系统有哪些业务模块？各负责什么？
- "供应商编码"这个字段从数据库哪张表来？经过哪些 DTO、Controller、前端页面？
- 改一个 Service 方法会影响哪些上游调用者？

现有方案的缺陷：
- **人工文档**：写完就过时，维护成本极高
- **Graphify**：产出 1,935 个"社区"，96.5% 是噪音（方法调用、JDK 类型），0 条边直接连接业务类
- **Spring Modulith**：需要开发者手工声明模块边界

**Graph-Wiki 的答案**：从代码结构自动推断业务语义，产出人类可读的 Wiki 文档。

---

## 二、核心设计决策

### 决策 1：三层思维模型（代码 → 领域 → 业务）

```
第 3 层  LLM Wiki         业务思维   人类和 Agent 的共同入口
第 2 层  业务域划分        领域思维   将代码文件聚合为有业务含义的域
第 1 层  graph.json       代码思维   AST 提取的原始结构（复用 graphifyy）
```

每往上一层，就离"人类理解的业务"更近一步。每一层都依赖下一层的数据，但不反向依赖。

### 决策 2：复用 vs 重建的边界

```
复用 graphifyy 的部分：          替换为自己实现的部分：
  detect()   文件检测              cluster()   包路径聚类（替代 Louvain）
  extract()  AST 提取              label()     LLM 语义标注（替代关键词拼凑）
  build()    图构建                visualize() 域级可视化（替代全图毛球）
                                  export()    Wiki 导出（替代社区页面）
```

**原则**：AST 提取是成熟能力（tree-sitter 支持 30+ 语言），不重造。聚类和标注才是 Graphify 的真正短板，必须重做。

### 决策 3：类/文件为最小粒度

Graphify 产出方法级节点（34K），96.5% 是噪音。Graph-Wiki 默认以**类/文件**为最小节点（~2,000），需要时才下钻到方法级。这直接将噪音比从 96.5% 降到可管理的范围。

### 决策 4：LLM 生成初稿 + 人工定稿

LLM 负责 `summary.md`（业务摘要、核心流程），人工负责 `rules.md`（业务规则）和 `spec.md`（需求规格）。LLM 不替代人类的判断，只替代"从零开始写"的体力活。

### 决策 5：Wiki 同时服务人类和 Agent

```
人类：Obsidian 打开 wiki/index.md → 点击进入域 → 阅读 summary.md
Agent：读取 wiki/index.md 定位域 → 读取域级文件 → 必要时查 graph.json
```

同一个入口，两种消费方式。Agent 通过 Wiki 将搜索范围从 34K 节点缩小到 ~500 节点。

---

## 三、聚类模块设计思想（最核心的创新）

### 为什么 Louvain 不行

Louvain 算法优化 modularity（模块度），即"实际连接比随机更密集的节点归为一组"。在 Java Maven 项目中，同一个 module 内的类天然 import 更多 → Louvain 结果 = Maven module 物理结构，不是业务语义。

OPS 项目实证：1,935 个社区，从第 15 个开始 100% 以 Maven module 命名。

### Graph-Wiki 的方案：包路径 + import 聚合

核心假设：**Java 的包路径命名习惯已经携带了业务语义**。`com.smc.smccloud.bin.service` → "bin" 就是业务域。

五步算法：

```
Step 1  过滤噪音    34K → ~400-2,800 业务节点      排除方法调用/JDK类型/Config/Util
Step 2  提取锚点    业务节点 → ~100-1,200 锚点       只保留 Controller/Service/Mapper
Step 3  包路径聚类  锚点 → 候选域                    按包路径的"业务级"前缀分组
Step 4  import 聚合  候选域 → 最终域                 密度合并 + 小域吸收 + 预定义规则
Step 5  业务点提取  域 → 业务点                      提取 public 方法 + 分析跨域调用
```

关键设计点：
- **噪音在第一步就彻底排除**：Config/Util 角色永远不进入聚类（role_importance 为 -1）
- **锚点先行**：只对业务骨架节点聚类，实体/DTO/VO 后续归入最近域
- **预定义合并规则**：`('bin', 'bindata', 'binorder') → 'bin-data'`，处理同义包名
- **适应多种语言**：Java 取公司域名后第 1 级，JS 取 src/ 后第 1 级，Python/Go 取项目根后第 1 级

---

## 四、LLM 标注模块设计思想

### 为什么不用关键词拼凑

Graphify 的社区"名称"是最高频 3 个关键词的拼接（如 `opsserviceImpl_bin_ops`），毫无语义价值。

### Graph-Wiki 的方案：结构化 Prompt + 并行调用

输入给 LLM 的不是全部源码，而是 **结构化采样**：
- 包路径前缀（20 tokens）
- Controller/Service/Mapper 类名列表（200 tokens）
- 1-2 个核心 Service 源码前 50 行（2,000 tokens）
- 跨域 import 统计（100 tokens）

每域 ~2,500 tokens × 25 域 = 62,500 tokens，总成本 ~$0.3（Claude Haiku）。

**并行化**：ThreadPoolExecutor 5 路并发，25 个域 5 轮，总耗时 ~30s。

**保护机制**：`@locked` 标记防止 LLM 覆盖人工确认过的内容。

---

## 五、API 映射 + 字段映射设计思想

### 为什么需要这两个模块

经典的"字段溯源"问题："前端的 `supplierCode` 字段最终写入数据库哪张表的哪个列？"

答案需要跨越六层：
```
Vue 组件字段 → API 请求参数 → Controller 入参 → DTO 字段 → Entity 字段 → 数据库列
```

- **api_mapper**：覆盖前三层（前端 API → 后端 Controller），URL 匹配 + HTTP 方法对齐
- **field_mapper**：覆盖后三层（DTO → Entity → 数据库列），注解解析 + 字段名相似度匹配

两个模块都是**零 Token 成本**——纯静态解析，不调用 LLM。

### 设计权衡

设计中的 `build_full_chain()` 六层结构（每层包含完整的元数据对象）在实际代码中简化为扁平的字段属性列表。这是因为：
- 六层对象结构在大项目中会产生大量冗余数据
- 扁平结构更易于按 domain → table → column 聚合查询
- 牺牲了结构完整性，换取了查询性能

---

## 六、输出设计思想

### Wiki 文档树结构

```
wiki/
├── index.md                    ← 25 域总目录（第一个被打开的文件）
├── api-index.md                ← 全部 API 索引
├── 采购管理/
│   ├── summary.md              ← LLM 生成的业务摘要（★ 核心页面）
│   ├── code-map.md             ← 代码文件清单（Controller/Service/Mapper 列表）
│   ├── api-docs.md             ← API 文档（自动生成）
│   ├── data-flow.md            ← 数据流（字段级六层链路）
│   ├── dependencies.md         ← 跨域依赖
│   ├── rules.md                ← 人工填写的业务规则
│   └── spec.md                 ← 人工填写的需求规格
├── 库存管理/
│   └── ...
└── domain_graph.html           ← D3 域级可视化（~25 节点，而非 34K 节点）
```

### 关键设计点

- **index.md 是进入点**：任何人（人/Agent）的第一接触点，表格形式一览 25 域
- **文件按域拆分而非单文件**：`api-map.json` 全量 20K tokens 会毁掉查询优势，拆成每域 300-800 tokens
- **双向链接**：`[[库存管理]]` 语法兼容 Obsidian，人类可以直接在 Obsidian 中导航
- **占位文件**：`rules.md` 和 `spec.md` 创建为空模板，由人工后续填写

---

## 七、Agent 消费路径设计

Agent 不是直接读 graph.json（57MB 太大），而是通过 **三级查询**：

```
第 1 级  Wiki 页面读取     index.md → summary.md              ~500-1,500 tokens
第 2 级  结构化数据查询     domains.json / api-map.json        0 tokens (JSON)
第 3 级  精确代码查询       graphify query（限定文件范围）      0 tokens (图遍历)
```

80% 的日常查询在前两级就能完成，Token 消耗 1,500 以下。只有需要精确代码细节时才走到第 3 级。

---

## 八、增量更新设计思想

核心假设：**域边界稳定**。包路径聚类基于目录结构，除非包重命名，否则同一个文件始终属于同一个域。

这带来两个优势：
1. `graph-wiki update` 只需重提取变更文件的 AST，不需要重聚类
2. 只刷新受影响的域的 code-map.md，其他域不动

增量更新耗时 ~80s vs 全量构建 ~12-15min。

---

## 九、与竞品的本质区别

| | Spring Modulith | Graphify | Graph-Wiki |
|------|:--:|:--:|:--:|
| 模块边界来源 | 人工声明 | 链接密度自动检测 | 包路径 + import 自动推断 |
| 输出对象 | 架构师 | Agent (AI) | 人类程序员 + Agent |
| 业务语义 | 人工编写 | 无（关键词拼凑） | LLM 自动生成 |
| 维护成本 | 高（需人工维护注解） | 零（全自动） | 零（全自动）+ 人工可选定稿 |
| 输出格式 | PlantUML 图 | D3 全图力导向 | Obsidian Wiki + 域级 D3 |

**Graph-Wiki 的独特位置**：自动推断业务语义 + 以人类为第一客户。这是目前市场上的空缺。

---

## 十、设计文档与代码的对应关系

| 设计文档 | 对应代码 | 核心设计思想 | 实现状态 |
|------|------|------|:--:|
| `graph-wiki-design.md` | 全部模块 | 三层架构 + 五步聚类 + 三级消费 | 架构层 |
| `cluster-design.md` | `cluster.py` | 包路径语义 > 链接密度 | 90% |
| `label-design.md` | `label.py` | 结构化采样 + 并行标注 + 锁定保护 | 70% |
| `api-mapper-design.md` | `api_mapper.py` | URL 匹配 + 前后端双向映射 | 75% |
| `field-mapper-design.md` | `field_mapper.py` | 六层数据流 + 注解解析 + 字段匹配 | 65% |
| `pipeline-design.md` | `pipeline.py` | CLI 编排 + 优雅降级 + 增量更新 | 60% |
