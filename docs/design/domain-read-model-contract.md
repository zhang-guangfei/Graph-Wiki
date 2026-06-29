# Domain Read Model 数据契约 v1

> 状态：详细契约已完成，第一版工程实现已接入 `domain-read-model.json` 生成链路  
> 对应架构：`docs/architecture/domain-read-model-v1-architecture.md`

---

## 1. 顶层结构

```json
{
  "schema": {
    "version": "domain-read-model-v1"
  },
  "project": {},
  "domains": [],
  "evidenceIndex": {},
  "quality": {}
}
```

## 2. Project

```json
{
  "projectId": "fullstack-enterprise",
  "projectName": "Fullstack Enterprise Fixture",
  "sourceRoot": "tests/fixtures/fullstack-enterprise",
  "generatedAt": "2026-06-29T00:00:00Z"
}
```

## 3. DomainRead

```json
{
  "domainKey": "order",
  "displayName": "订单管理",
  "summary": "处理订单创建、校验、提交与落库。",
  "core": true,
  "flows": [],
  "rules": [],
  "fieldRules": [],
  "evidenceRefs": [],
  "quality": {}
}
```

字段要求：

| 字段 | 必填 | 说明 |
| --- | --- | --- |
| `domainKey` | 是 | 稳定机器键 |
| `displayName` | 是 | 人类可读名称 |
| `summary` | 是 | 业务域摘要 |
| `core` | 是 | 是否为核心验收域 |
| `flows` | 是 | 业务流程 |
| `rules` | 是 | 业务规则 |
| `fieldRules` | 是 | 字段规则 |
| `evidenceRefs` | 是 | 域级证据 |
| `quality` | 是 | 域级质量 |

## 4. Flow

```json
{
  "flowId": "order.submit",
  "title": "提交订单",
  "summary": "用户提交订单表单，系统校验库存并保存订单。",
  "steps": [
    {
      "stepId": "order.submit.step1",
      "order": 1,
      "text": "前端提交订单表单并调用 POST /orders。",
      "evidenceRefs": ["api:POST:/orders", "source:frontend/src/api/order.ts#createOrder"],
      "ruleRefs": ["order.submit.stock-check"],
      "status": "ready",
      "confidence": 0.9
    }
  ],
  "evidenceRefs": ["api:POST:/orders"],
  "status": "ready",
  "confidence": 0.86
}
```

## 5. BusinessRule

```json
{
  "ruleId": "order.submit.stock-check",
  "statement": "提交订单前必须校验库存是否充足。",
  "ruleType": "business",
  "flowRefs": ["order.submit"],
  "evidenceRefs": ["source:backend/src/main/java/com/example/order/OrderService.java#checkStock"],
  "status": "ready",
  "confidence": 0.88,
  "review": {
    "state": "machine_draft",
    "reviewedBy": "",
    "reviewedAt": ""
  }
}
```

### ruleType 枚举

- `business`
- `validation`
- `permission`
- `state_transition`
- `calculation`
- `field`
- `integration`
- `unknown`

## 6. FieldRule

```json
{
  "fieldRuleId": "order.submit.field.customer_id",
  "fieldId": "orders.customer_id",
  "statement": "customerId 来自前端字段 customerId，经 CreateOrderDTO.customerId 映射到 Order.customerId 并落库到 orders.customer_id。",
  "chain": [
    {"layer": "frontend", "ref": "source:frontend/src/views/order/CreateOrder.vue#customerId"},
    {"layer": "api", "ref": "api:POST:/orders"},
    {"layer": "controller", "ref": "source:backend/src/main/java/com/example/order/OrderController.java#createOrder"},
    {"layer": "dto", "ref": "source:backend/src/main/java/com/example/order/CreateOrderDTO.java#customerId"},
    {"layer": "entity", "ref": "source:backend/src/main/java/com/example/order/Order.java#customerId"},
    {"layer": "db", "ref": "field:orders.customer_id"}
  ],
  "evidenceRefs": [
    "field:orders.customer_id",
    "api:POST:/orders",
    "source:backend/src/main/java/com/example/order/Order.java#customerId"
  ],
  "status": "ready",
  "confidence": 0.9,
  "partialReason": ""
}
```

## 7. EvidenceRef

### 7.1 ID 格式

| 类型 | 格式 | 示例 |
| --- | --- | --- |
| API | `api:<METHOD>:<PATH>` | `api:POST:/orders` |
| Source | `source:<path>#<symbol-or-line>` | `source:backend/.../OrderService.java#checkStock` |
| Field | `field:<table>.<column>` | `field:orders.customer_id` |
| Wiki | `wiki:<path>#<heading>` | `wiki:order/rules.md#库存校验` |
| Ontology | `ontology:<relationship-id>` | `ontology:rel-123` |

### 7.2 Evidence 对象

```json
{
  "id": "api:POST:/orders",
  "type": "api",
  "label": "POST /orders",
  "path": "api-map.json",
  "sourcePath": "frontend/src/api/order.ts",
  "confidence": 1.0,
  "status": "ready"
}
```

### 7.3 校验规则

- `id` 必须唯一。
- `id` 必须符合类型格式。
- claim 引用的 evidenceRef 必须存在于 `evidenceIndex`。
- `source:` 类型必须能定位到源码路径；符号定位不到时状态不能是 `ready`。

## 8. Quality

```json
{
  "deepReadingStatus": "passed",
  "coreDomainEvidenceStatus": "passed",
  "ruleCorrectnessRisk": "low",
  "warnings": [],
  "errors": []
}
```

状态含义：

| status | 含义 |
| --- | --- |
| `passed` | 满足 v1 核心体验要求 |
| `warning` | 可阅读但存在不完整或需审阅项 |
| `failed` | 核心域无法按 flow → rules → evidence 阅读 |

## 9. Workbench 派生规则

Workbench v1 业务域页只能从 `domain-read-model.json` 派生：

```text
domain-read-model.json
  ↓
ProductDataService
  ↓
workbench-data.json
  ↓
Workbench Domain Page
```

禁止 Workbench 业务域页直接把 `domains.json`、`api-map.json`、`field-map.json`、`rules.md` 拼成主体验。

---

## 10. 产品阅读路径约束

所有消费该契约的产品界面必须支持 **流程 → 规则 → 证据** 的连续阅读路径。

该路径对应字段关系：

```text
DomainRead.flows[].steps[].ruleRefs
  ↓
DomainRead.rules[].flowRefs
  ↓
EvidenceRef / evidenceIndex
```

## 11. build-report 映射

`domain-read-model.json` 中的质量信息必须映射到 `build-report.json` 的 `productQuality` 区域：

```json
{
  "productQuality": {
    "deepReadingStatus": "passed|warning|failed",
    "coreDomainEvidenceStatus": "passed|warning|failed"
  }
}
```


## 12. Release Gate 映射

`build-report.json` 必须被 release gate 按三类信号分别读取：

| 信号 | JSON 路径 | 含义 | 是否可单独代表发布通过 |
| --- | --- | --- | --- |
| 构建执行 | `build.status` | pipeline 是否完成 | 否 |
| 产品深读 | `productQuality.*` | flow → rules → evidence 是否可读 | 否，必须结合证据解析 |
| 阶段能力 | `quality.phase3/4/5` 或顶层 `phase3/4/5` | ontology、impact、dream-cycle 等阶段能力 | 否 |

发布脚本必须拒绝“`build.status=passed` 但缺失 `productQuality`”的输出，避免把构建成功误报为产品质量通过。
