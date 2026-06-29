# Graph-Wiki 项目全局指南

## 项目定位

Graph-Wiki 是一个**知识图谱构建与使用项目**——从源代码中自动识别业务域边界，用 LLM 生成业务级 Wiki 文档。其独特定位是填补市场上"自动从代码推断业务语义 + 以人类为第一客户"这一空缺。

**核心区别**：Graphify 以 Agent (AI) 为第一客户（图数据/索引），Graph-Wiki 以**人类程序员**为第一客户（Wiki 文档/业务导航），同时兼容 Agent 消费。

---

## 目录角色与优先级

```
Graph-Wiki/
├── docs/
│   ├── reference/        ← 前期痛点分析、项目立项方向、竞品能力对比
│   ├── project-management/ ← ★ CEO/董事会项目管理入口、阶段目标、验收报告
│   ├── architecture/     ← ★ 核心设计产出（项目唯一真相源，必须以此为目标）
│   └── design/           ← 待完善的详细设计方案（决定最终输出质量）
├── graph_wiki/           ← 代码目录（8 个模块，务必保证代码质量）
├── tests/                ← 端到端集成测试（对真实代码库执行分析）
├── examples/             ← 示例/参考文件
└── pyproject.toml
```

### `docs/reference/` — 前期分析
- 只放调研、竞品、外部启发、历史分析。**不要再放 CEO 推进计划、阶段验收报告、董事会视角文档。**
- **`graphify-limitations-analysis.md`** — 基于 OPS 项目（5,200 Java 文件）实际运行结果的量化分析，揭示了 8 个核心缺陷：Louvain 社区 ≈ Maven module 无业务语义、96.5% 节点是噪音、0 条业务边直接连接锚点、可视化无实用性等。**为 Graph-Wiki 立项提供精确依据。**
- **`code-analysis-tools-comparison.md`** — 六款同类工具对比（Spring Modulith、Context Mapper、jQAssistant、Graphify、CodeScene 等），定位市场空缺。
- **`graph-wiki-audit-report.md`** — 对 architecture 设计文档的 3-Agent 并行审核，评分 6.8/10，指出 4 项严重问题（field_mapper 缺失等）。
- **`graph-wiki-capability-assessment.md`** — 10 项需求对照分析，Token 成本评估，与 Graphify 能力对比矩阵。

### `docs/project-management/` — ★ CEO / 董事会项目管理入口
- **`README.md`** — 项目管理入口，说明董事会应该看哪些文档
- **`progress-log.md`** — 开发进度日志一览，用最少内容记录日期和当前成功
- **`board-roadmap.md`** — 董事会推进总览，只讲阶段、产物、下一步，不讲本阶段开发细节
- **`ceo-progress-dashboard.html`** — CEO 级别 HTML 仪表盘
- **`stage-reports/`** — 阶段验收报告
- **`execution-plans/`** — CEO/Agent 内部执行计划，董事会不需要日常跟踪

**项目管理原则**：
- 董事会看宏观进度和阶段交付，不看模块细节。
- 本阶段细节由 CEO 主控拆解并持续解决。
- 未达到阶段交付标准时，不应包装成交付成果。
- 每次阶段完成后，只更新：`progress-log.md`、对应阶段报告、`ceo-progress-dashboard.html`。

### `docs/architecture/` — ★ 核心设计文档（最重要）
- **`graph-wiki-design.md`** — **项目的唯一设计真相源（Single Source of Truth），v1.0**。所有设计决策、模块接口、数据流、消费链路均以此为准。定义了：
  - 三层架构：graph.json（代码思维）→ 业务域划分（领域思维）→ LLM Wiki（业务思维）
  - 复用策略：复用 graphifyy 的 detect/extract/build，替换 Louvain 聚类/关键词标注/毛球可视化
  - 8 个核心模块的设计与接口
  - 此文档已经过审核，**但存在遗留问题（S1-S4）待完善**，后续迭代中要修复

### `docs/design/` — 详细设计方案（待完成，接下来重点）
- `cluster-design.md` — 聚类模块详细设计
- `label-design.md` — LLM 标注模块详细设计
- `api-mapper-design.md` — API 映射模块详细设计
- `field-mapper-design.md` — 字段映射模块详细设计
- `pipeline-design.md` — 流水线模块详细设计
- **这些文档的质量直接决定最终输出物的质量**，必须保证设计充分后再进入代码实现

### `graph_wiki/` — 代码目录
| 文件 | 职责 | 状态 |
|------|------|:--:|
| `models.py` | 数据模型 | 已有 |
| `reuse.py` | graphifyy API 封装（复用层） | 已有 |
| `cluster.py` | 业务域聚类（核心，替换 Louvain） | 已有 |
| `api_mapper.py` | 前后端接口映射 | 已有 |
| `field_mapper.py` | 字段级数据流追踪 | 已有 |
| `label.py` | LLM 语义标注 | 已有 |
| `export.py` | Wiki 导出 | 已有 |
| `visualize.py` | 域级 HTML 可视化 | 已有 |
| `pipeline.py` | CLI 入口 & 流水线编排 | 已有 |

### `tests/` — 集成测试
- 对真实项目执行 `graph-wiki build`，做端到端验证
- 用于验证项目分析能力和输出质量

---

## 工作流（每次迭代闭环）

```
文档完善 → 代码编写 → 集成测试 → 项目总结 → 持续迭代
```

每次迭代闭环**必须覆盖上列目录**：完善 design 文档 → 在 graph_wiki/ 实现 → 用 tests/ 对真实项目验证 → 更新架构文档的遗留问题、总结 → 进入下轮。

### 迭代工作流详细规程

每个迭代周期严格按以下 5 步执行，**每步都需要深入思考、产出规划文档、工作完成后汇报，经用户指导后才能进入下一步**：

```
Step 1: 阅读关键文档，理解项目进度现状
    ├── 系统阅读 docs/architecture/（真相源）、docs/reference/（前期分析）、docs/design/（模块设计）
    ├── 对比代码实现与设计文档的差距
    └── ★ 产出：项目现状评估文档（含进度、差距、遗留问题）

Step 2: 完善设计文档
    ├── 以 architecture/ 文档为目标进行设计完善
    ├── 修复架构文档遗留问题（S1-S4 等）
    ├── 完善 design/ 中各模块的详细设计
    └── ★ 产出：更新后的设计文档

Step 3: 按步骤开发
    ├── 先完成设计文档再进行代码实现
    ├── 代码质量是硬性要求
    └── ★ 产出：功能代码 + 单元测试

Step 4: 测试（第一轮 / 回归测试）
    ├── tests/ 目录下编写集成测试
    ├── 对真实项目执行 graph-wiki build，做端到端验证
    └── ★ 产出：测试报告 + 输出物质量评估

Step 5: 项目总结 + 准备下一轮迭代
    ├── 更新 architecture/ 文档的遗留问题
    ├── 总结本轮成果和发现的问题
    ├── 规划下一轮迭代目标
    └── ★ 产出：迭代总结文档 + 下一轮规划
```

**关键行为准则**：
- **每步必先规划**：进行深入思考，产出规划文档
- **规划→执行→汇报**：产出文档后才能开始该步的实际工作
- **等指示再前进**：主控给出指导意见后才能进入下一步
- **一切以 architecture/ 为真相源**：设计决策不偏离 architecture 文档

---

## 阶段目标

### 第一阶段：跑通最小流程（当前）
- 对小型项目（~50-100 文件）完成完整 `build` 流程
- 产出可用 Wiki + 域级可视化
- 验证核心链路：detect → extract → build → cluster → label → export

### 第二阶段：保证输出质量（应尽快进入）
- **能够承载 500+ 文件体量的项目**
- 输出的 Wiki 具备实际业务导航价值
- 字段级数据流、API 文档等增强模块稳定可用

---

## 核心设计原则

1. **LLM Wiki 是入口，graph.json 是后端** — 人类和 Agent 都通过 Wiki 进入
2. **类/文件为最小粒度** — 不做方法级节点，默认类级视图，按需下钻
3. **人和 Agent 共用同一入口** — Wiki 同时是人类导航和 Agent scope reduction
4. **LLM 生成 + 人工定稿** — LLM 负责 summary.md 初稿，人工负责 rules.md / spec.md
5. **复用不重造** — AST 提取复用 graphifyy 的成熟能力（30+ 语言）

---

## 实现须知

1. **开始任何实现前，必须先仔细阅读 `docs/architecture/graph-wiki-design.md`**，以此为目标
2. architecture 文档的遗留问题（审核报告 S1-S4）后续必须完善
3. design/ 中的模块设计文档是代码的直接依据，先完成设计再写代码
4. 代码质量是硬性要求
5. tests/ 只放集成测试，单元测试放各模块文件中
