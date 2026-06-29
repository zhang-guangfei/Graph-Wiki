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
| A10 | UI 顺序 | domain page 按 flow → rules → evidence 呈现，Workbench build 不破坏 DTO | DOM/static render / `npm run build` | high |
| A11 | 质量报告 | build 与 productQuality 分离 | pytest | blocker |
| A12 | 回归 | 旧测试通过或迁移理由明确；Phase 3/4/5 failure 不得被误报为产品 passed | pytest + migration note + build-report 检查 | high |

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
tests/test_fullstack_enterprise_acceptance.py
tests/test_product_quality_report.py
tests/test_workbench_domain_reading_contract.py

# TODO: 如需把 EvidenceRef 路径/符号解析拆成独立测试，可新增：
# tests/test_domain_read_model_evidence.py
```

---

## 5. 验证命令

推荐使用统一发布门禁脚本，避免人工只检查 `build.status` 而漏看 `productQuality`：

```bash
python3 scripts/release_quality_gate.py
```

CI 使用同一入口：`.github/workflows/ci.yml`。

该脚本必须顺序覆盖：

```bash
python3 -m pytest -q
cd workbench && npm ci && npm run build
python3 -m graph_wiki.pipeline build tests/fixtures/fullstack-enterprise --no-llm --output-dir output/fullstack-enterprise
python3 -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir output/svn-platform
python3 scripts/release_quality_gate.py --output-root output/release-quality-gate
```

如果需要手工复核，必须同时读取每个 smoke 输出的：

- `build-report.json.build.status == passed`：只代表流水线执行成功。
- `build-report.json.productQuality == domain-read-model.json.quality`：代表产品深度阅读质量。
- `productQuality.deepReadingStatus` 与 `productQuality.coreDomainEvidenceStatus` 不得为 `failed`。
- `workbench-data.json.schema.source == domain-read-model.json`。

---

## 6. 关键术语锚点

- 产品阅读路径：**流程 → 规则 → 证据**
- 证据引用模型：`EvidenceRef`
- 核心产品输入：`domain-read-model.json`
- 产品质量状态：`productQuality.deepReadingStatus`

## 7. 当前已知限制（2026-06-29）

- `EvidenceRef` 当前已验证格式和 `evidenceIndex` 引用存在性；`source:<path>#<symbol>` 的符号级精确定位仍需后续独立 gate 补强。
- Workbench 当前已展示 flow / rule chip / field rule chain；最终“证据面板”还需要继续增强为可筛选、可定位的独立面板。
- `build.status=passed` 只代表流水线执行成功；Phase 3/4/5 acceptance 或 `productQuality` warning/failed 必须单独展示，不能被发布说明省略。


## 8. Release / CI 质量门禁固化

本轮新增 `scripts/release_quality_gate.py` 作为本地 release gate 和 CI job 的共同入口。 GitHub Actions 入口为 `.github/workflows/ci.yml`。默认不可跳过任何步骤；`--skip-*` 仅允许本地定位问题，不能用于发布证据。

脚本必须输出：

- pytest 命令结果；
- Workbench `npm ci` 与 `npm run build` 结果；
- `fullstack-enterprise` 与 `svn-platform` 两个 smoke build 结果；
- 每个 build 的 `build.status`、`productQuality`、phase gate status；
- `release-quality-gate-report.json`，用于审计。

发布判定必须同时满足：

1. 命令均通过；
2. `build.status == passed`；
3. `productQuality.deepReadingStatus != failed`；
4. `productQuality.coreDomainEvidenceStatus != failed`；
5. `workbench-data.schema.source == domain-read-model.json`；
6. 核心域 flow / rule / fieldRule 的 `evidenceRefs` 均可解析到 `evidenceIndex`；
7. phase gates 单独列出，不得用 `build.status` 代替。
