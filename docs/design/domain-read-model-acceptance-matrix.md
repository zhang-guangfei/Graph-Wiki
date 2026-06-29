# Domain Read Model v1 验收矩阵

> 状态：验收设计已完成，并已落地 `tests/fixtures/fullstack-enterprise/` 端到端验收夹具  
> 对应测试规格：`.omx/plans/test-spec-graph-wiki-product-restart-20260629T083605Z.md`

---

## 1. 验收基准项目

路径：

```text
tests/fixtures/fullstack-enterprise/
```

必须包含：

- 前端页面表单字段
- 前端 API 函数
- 后端 Controller
- Service 业务规则 / validation 条件
- DTO 字段
- Entity 字段 + DB 注解
- 至少一个核心业务域

---

## 2. 验收矩阵

| 编号 | 能力 | 必须条件 | 验证方式 | 失败等级 |
| --- | --- | --- | --- | --- |
| A1 | 生成 domain-read-model | 存在 `domain-read-model.json`，schema=`domain-read-model-v1` | pytest JSON 断言 | blocker |
| A2 | 核心域识别 | 至少一个 `core=true` domain | pytest | blocker |
| A3 | 业务流程 | 核心域至少一个 flow，且有 ordered steps | pytest | blocker |
| A4 | 业务规则 | 核心域至少一条 rule | pytest | blocker |
| A5 | 字段规则 | fixture 存在字段链路时至少一条 fieldRule | pytest | blocker |
| A6 | 证据引用 | flow step / rule / fieldRule 均有 evidenceRefs | pytest | blocker |
| A7 | 证据可解析 | evidenceRefs 均能解析到 evidenceIndex 或源码路径 | pytest | blocker |
| A8 | 字段链路完整度 | 存在层级必须被追踪；缺层必须 partialReason | pytest | high |
| A9 | Workbench 派生 | workbench-data domain view 来自 domain-read-model | pytest / DTO 断言 | blocker |
| A10 | UI 顺序 | domain page 按 flow → rules → evidence 呈现 | DOM/static render | high |
| A11 | 质量报告 | build 与 productQuality 分离 | pytest | blocker |
| A12 | 回归 | 旧测试通过或迁移理由明确 | pytest + migration note | high |

---

## 3. Product Quality 判定

### passed

- A1~A7、A9、A11 全部通过。
- A8/A10 无 high 级失败。
- 核心规则大部分为 `ready`，没有明显错误规则。

### warning

- 构建成功。
- 核心域可阅读，但存在 partial / needs_review。
- 不影响主流程理解。

### failed

任一情况：

- 没有 core domain。
- 没有 flow 或 rule。
- 关键 claim 无 evidenceRefs。
- evidenceRef 不可解析。
- Workbench 不能展示 flow → rules → evidence。
- build 成功但产品质量状态未能反映失败。

---

## 4. 推荐测试文件

```text
tests/test_domain_read_model_contract.py
tests/test_domain_read_model_evidence.py
tests/test_fullstack_enterprise_acceptance.py
tests/test_product_quality_report.py
tests/test_workbench_domain_reading_contract.py
```

---

## 5. 验证命令

```bash
. .venv/bin/activate
python -m pytest -q
graph-wiki build tests/fixtures/fullstack-enterprise --no-llm --output-dir output/fullstack-enterprise
cd workbench && npm ci && npm run build
```

---

## 6. 关键术语锚点

- 产品阅读路径：**流程 → 规则 → 证据**
- 证据引用模型：`EvidenceRef`
- 核心产品输入：`domain-read-model.json`
- 产品质量状态：`productQuality.deepReadingStatus`
