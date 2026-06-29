# Graph-Wiki v1 详细架构设计：Domain Read Model

> 日期：2026-06-29  
> 状态：详细架构设计已完成，第一版工程实现已接入 `graph_wiki/domain_read_model.py` 与 `graph_wiki/evidence.py`  
> 上游依据：`.omx/specs/deep-interview-graph-wiki-restart-requirements.md`、`.omx/plans/prd-graph-wiki-product-restart-20260629T083605Z.md`  
> 结论：v1 以 `domain-read-model.json` 为业务域深度阅读的唯一产品输入。

---

## 1. 架构目标

Graph-Wiki v1 的产品中心不再是“生成一组文件”，而是让用户围绕一个业务域完成深度阅读：

```text
打开业务域
  ↓
阅读业务流程
  ↓
理解业务规则 / 字段规则
  ↓
追溯证据到 API、前端、后端、DTO、Entity、DB、源码
```

核心体验路径：**流程 → 规则 → 证据**。

因此 v1 架构的核心产物是：

```text
domain-read-model.json
```

该产物是 Workbench / Wiki / Agent 的 v1 产品真相源。旧产物仍可存在，但只能作为中间、派生、诊断或兼容产物。

---

## 2. 架构分层

v1 架构分 6 层：

```text
┌────────────────────────────────────────────┐
│ 第 6 层：产品呈现与质量层                    │
│ Workbench / Wiki / build-report             │
└─────────────────────▲──────────────────────┘
                      │ 只消费 domain-read-model
┌─────────────────────┴──────────────────────┐
│ 第 5 层：业务域阅读模型层                    │
│ domain-read-model.json                      │
└─────────────────────▲──────────────────────┘
                      │ claim + evidence
┌─────────────────────┴──────────────────────┐
│ 第 4 层：规则与流程生成层                    │
│ flow extractor / rule extractor / field rule │
└─────────────────────▲──────────────────────┘
                      │ normalized evidence
┌─────────────────────┴──────────────────────┐
│ 第 3 层：证据归一化层                        │
│ EvidenceRef / source refs / API refs / field │
└─────────────────────▲──────────────────────┘
                      │ raw analysis artifacts
┌─────────────────────┴──────────────────────┐
│ 第 2 层：代码分析与映射层                    │
│ graph-lite / domains / api-map / field-map   │
└─────────────────────▲──────────────────────┘
                      │ source files
┌─────────────────────┴──────────────────────┐
│ 第 1 层：源码输入层                          │
│ Java/Spring + Vue/React full-stack project   │
└────────────────────────────────────────────┘
```

### 第 1 层：源码输入层

v1 优先目标是前后端混合企业项目：

- Java / Spring 风格后端
- Vue / React 风格前端
- Controller / Service / DTO / Entity / DB 字段链路
- 前端页面 / API 函数 / 表单字段

非目标：30+ 语言同等深度支持。

### 第 2 层：代码分析与映射层

保留并重构现有能力：

| 能力 | 现有来源 | v1 用途 |
| --- | --- | --- |
| 文件检测 / AST / graph-lite | `graph_wiki/reuse.py` | 基础代码结构 |
| 业务域聚类 | `graph_wiki/cluster.py` | domain 候选和业务点 |
| API 映射 | `graph_wiki/api_mapper.py` | 前端 ↔ 后端 API 证据 |
| 字段映射 | `graph_wiki/field_mapper.py` | DTO ↔ Entity ↔ DB 字段证据 |
| 本体关系 | `graph_wiki/ontology.py` | typed relationship 证据 |

这些产物不再直接定义产品体验，而是给上层提供输入。

### 第 3 层：证据归一化层

新增核心模块建议：

```text
graph_wiki/evidence.py
```

职责：把分散证据统一成稳定 ID 与结构化引用。

Canonical EvidenceRef 格式：

| 类型 | 格式 | 示例 |
| --- | --- | --- |
| API | `api:<METHOD>:<PATH>` | `api:POST:/orders` |
| 源码 | `source:<path>#<symbol-or-line>` | `source:src/main/java/.../OrderService.java#checkStock` |
| 字段 | `field:<table>.<column>` | `field:orders.customer_id` |
| Wiki | `wiki:<path>#<heading>` | `wiki:order/rules.md#库存校验` |
| 本体 | `ontology:<relationship-id>` | `ontology:rel-123` |

硬性规则：

- 每个 flow step 必须有 `evidenceRefs`。
- 每条 business rule 必须有 `evidenceRefs`。
- 每条 field rule 必须有 `evidenceRefs`。
- evidenceRef 必须可解析到当前构建产物或源码路径。
- 无法解析时该 claim 不能是 `ready`。

### 第 4 层：规则与流程生成层

新增核心模块建议：

```text
graph_wiki/domain_read_model.py
graph_wiki/rule_extractor.py
```

职责：从第 2/3 层输入生成可读 claim。

#### 4.1 Flow 生成

输入：

- Domain
- BusinessPoint
- API matches
- frontend callers
- backend service chain
- source evidence

输出：

- `flows[].steps[]`
- `flows[].steps[].ruleRefs`
- `flows[].steps[].evidenceRefs`

原则：

- 优先生成可追溯的结构化流程。
- 不要求长篇自然语言。
- 缺少证据的步骤标记为 `partial` 或不生成。

#### 4.2 Business Rule 生成

规则来源优先级：

1. Service / Controller 中可静态识别的条件判断、状态判断、权限/校验逻辑。
2. DTO / Entity 注解中的 validation / nullable / enum / table-field 约束。
3. API 方法名、业务点名、领域术语的弱推断。
4. LLM 增强摘要（可选，不作为 CI 必需）。

输出：

- `rules[]`
- `ruleType`
- `flowRefs`
- `evidenceRefs`
- `confidence`
- `status`

状态语义：

| status | 含义 |
| --- | --- |
| `ready` | 有足够证据，核心域可直接阅读使用 |
| `needs_review` | 有证据但推断存在不确定性，需要人工审阅 |
| `partial` | 只覆盖部分规则或证据链缺层 |
| `unsupported` | 当前项目/语言/代码形态不支持自动生成 |

#### 4.3 Field Rule 生成

输入：

- field-map
- API 参数
- DTO 字段
- Entity 字段
- DB 注解
- frontend caller / form field

输出：

- `fieldRules[]`
- `chain[]`
- `partialReason`
- `evidenceRefs`

链路完整度：

```text
frontend → api → controller → dto → entity → db
```

只要 fixture 中存在对应层，v1 验收必须能追踪到；缺失层必须说明原因，不能伪造。

### 第 5 层：业务域阅读模型层

核心产物：

```text
domain-read-model.json
```

定位：

- Workbench 业务域页唯一 v1 输入。
- Wiki 业务域页生成源。
- Agent scope reduction 的优先入口。
- build-report 产品质量判断的数据源。

禁止：

- Workbench v1 业务域页直接从 `domains.json`、`api-map.json`、`field-map.json`、`rules.md` 拼装主体验。
- 把 `rules.md` 占位当作业务规则交付。

### 第 6 层：产品呈现与质量层

#### 6.1 Workbench

Workbench 的业务域详情页必须按此顺序组织：

```text
业务域概览
  ↓
业务流程
  ↓
业务规则
  ↓
字段规则
  ↓
证据面板
```

验收重点不是“页面能打开”，而是用户不看 raw JSON 也能完成 flow → rules → evidence 阅读。

#### 6.2 Wiki

Wiki 可以继续生成，但 v1 中 Wiki 是 `domain-read-model.json` 的派生文档。

建议页面：

```text
wiki/{domain}/index.md          # flow → rules → evidence 总入口
wiki/{domain}/flows.md          # 业务流程
wiki/{domain}/rules.md          # 自动规则初稿 + 审阅状态
wiki/{domain}/field-rules.md    # 字段规则
wiki/{domain}/evidence.md       # 证据索引
```

#### 6.3 build-report

`build-report.json` 必须区分：

```json
{
  "build": {"status": "passed"},
  "productQuality": {
    "deepReadingStatus": "passed|warning|failed",
    "coreDomainEvidenceStatus": "passed|warning|failed",
    "ruleCorrectnessRisk": "low|medium|high"
  }
}
```

不能再用单一 `quality.status` 混淆“构建成功”和“产品合格”。发布/CI 门禁应调用 `scripts/release_quality_gate.py`，并同时检查 fullstack-enterprise 与 svn-platform smoke 输出的 `productQuality`。

---

## 3. 模块边界建议

| 模块 | 职责 | 备注 |
| --- | --- | --- |
| `reuse.py` | 底层代码图提取 | 保留，作为输入层 |
| `cluster.py` | 业务域与业务点识别 | 保留但输出需适配 domain-read model |
| `api_mapper.py` | 前后端 API 映射 | 保留/增强证据输出 |
| `field_mapper.py` | 字段链路映射 | 保留/增强完整链路和 partial reason |
| `ontology.py` | typed relationships | 保留为证据源 |
| `evidence.py` | EvidenceRef 归一化与校验 | 新增核心模块 |
| `rule_extractor.py` | 业务规则 / 字段规则提取 | 新增核心模块 |
| `domain_read_model.py` | 汇聚 domain-read-model | 新增核心模块 |
| `product_data.py` | Workbench DTO 派生 | 重构为从 domain-read-model 派生 |
| `export.py` | Wiki 派生输出 | 重构为从 domain-read-model 派生 |
| `pipeline.py` | 编排与质量门禁 | 增加 domain-read-model 阶段和 productQuality |

---

## 4. 构建流程

v1 推荐流水线：

```text
[1] detect/extract/filter/build graph-lite
[2] business_cluster
[3] build_api_map
[4] build_field_map
[5] build_ontology
[6] normalize_evidence
[7] extract_rules_and_flows
[8] build_domain_read_model
[9] export_wiki_from_domain_read_model
[10] export_workbench_data_from_domain_read_model
[11] evaluate_product_quality
[12] write build-report
```

其中 `[6]~[8]` 是 v1 新核心。

---

## 5. 质量门禁

### 5.1 Core Domain Deep Reading Gate

核心业务域满足以下条件才可 `passed`：

- 至少 1 个 flow。
- 至少 1 条 business rule。
- 若存在字段链路，则至少 1 条 field rule。
- 每个 flow step / rule / field rule 有可解析 evidenceRefs。
- 核心规则状态不能全部是 `needs_review` 或 `partial`。

### 5.2 Evidence Gate

失败条件：

- claim 没有证据。
- evidenceRef 格式非法。
- evidenceRef 指向不存在的路径/对象。
- field rule 声称完整链路但缺少实际层级证据。

### 5.3 Product Presentation Gate

失败条件：

- Workbench domain page 不能按 flow → rules → evidence 展示。
- Workbench domain page 直接依赖 legacy artifact 拼主体验。
- 空状态没有原因和建议动作。

---

## 6. 迁移策略

### 阶段 A：并存

- 继续生成旧产物。
- 新增 `domain-read-model.json`。
- Workbench v1 业务域页只读新模型。

### 阶段 B：降级旧产物

- 标记旧 JSON 为 intermediate / diagnostics。
- Wiki 从新模型派生。

### 阶段 C：清理

- 删除或隐藏不再服务产品体验的旧 UI 路径。
- 保留必要的 Agent/调试查询后端。

---

## 7. 架构风险

| 风险 | 说明 | 缓解 |
| --- | --- | --- |
| 规则误判 | 自动规则近乎正确要求高 | 证据、置信度、fixture 人工期望测试 |
| 双契约混乱 | `workbench-data.json` 与 `domain-read-model.json` 并存 | 明确前者只能派生自后者 |
| UI 先行 | 先改 UI 会掩盖数据不可靠 | 先做 domain-read-model acceptance |
| 过度 LLM 依赖 | CI 不稳定 | fixture 验收使用 deterministic extraction |

---

## 8. 未决策项

1. `domain-read-model.json` 是否长期独立存在，还是最终并入 `workbench-data.json` 作为其主 section？  
   - 当前决策：必须独立生成，Workbench 可派生 bundle。
2. 规则提取的 deterministic v1 范围有多深？  
   - 当前决策：先覆盖 Controller/Service 条件、DTO/Entity 注解、字段链路。
3. LLM 在 v1 中的角色？  
   - 当前决策：可增强表达，但 CI 验收不能依赖 LLM。

---

## 9. 验收夹具位置

v1 的第一验收夹具固定为：

```text
tests/fixtures/fullstack-enterprise/
```

该夹具必须服务于前后端混合企业项目验收，而不是只验证小型前端项目。
