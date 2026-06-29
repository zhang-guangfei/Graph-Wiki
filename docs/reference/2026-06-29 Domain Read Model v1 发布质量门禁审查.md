# 2026-06-29 Domain Read Model v1 发布质量门禁审查

## 审查结论

Domain Read Model v1 已形成可验收的主链路：`domain-read-model.json` → `ProductDataService` → `workbench-data.json` → Workbench 业务域深度阅读页。当前交付可以进入持续回归阶段，但发布口径必须坚持“构建成功”和“产品可读”分离，避免把旧 Wiki/旧 JSON 聚合误认为 v1 产品体验。

## 已确认的正向证据

- 架构真相源已明确 v1 产品输入：`docs/architecture/domain-read-model-v1-architecture.md`。
- 数据契约已定义 `DomainRead`、`Flow`、`BusinessRule`、`FieldRule`、`EvidenceRef`：`docs/design/domain-read-model-contract.md`。
- 验收矩阵已覆盖 A1~A12：`docs/design/domain-read-model-acceptance-matrix.md`。
- 工程链路已接入：`graph_wiki/domain_read_model.py`、`graph_wiki/evidence.py`、`graph_wiki/product_data.py`、`graph_wiki/pipeline.py`。
- Workbench UI 已展示“流程 → 规则 → 证据”阅读路径：`workbench/src/App.vue`。
- 回归测试已覆盖企业全栈夹具和 Workbench DTO：`tests/test_fullstack_enterprise_acceptance.py`、`tests/test_workbench_domain_reading_contract.py`、`tests/test_product_quality_report.py`。
- 发布门禁已固化为 `scripts/release_quality_gate.py`，统一串联 pytest、Workbench build、fullstack-enterprise smoke、svn-platform smoke，并显式校验 `build.status` 与 `productQuality` 分离。
- CI 入口已新增 `.github/workflows/release-quality-gate.yml`，PR/push 可复用同一脚本并上传 `output/release-gate/` 验收证据。

## 主要风险与处置

| 风险 | 影响 | 当前处置 | 后续建议 |
| --- | --- | --- | --- |
| 前端契约仍保留 v0 表述 | 容易让实现绕回 `domains.json` / `api-map.json` 拼业务域详情 | 已修订 `docs/design/frontend-data-contract.md`，补充 v1 派生规则和质量门禁 | 继续让测试断言 `workbench-data.schema.source` |
| 规则抽取仍偏 deterministic heuristic | 真实企业代码中复杂规则可能只生成弱规则 | `BusinessRule.status/confidence/review` 区分 ready/partial/machine_draft | 增加 Service 条件、注解、枚举状态机的专门夹具 |
| 字段链路完整度依赖命名和目录约定 | DTO/Entity/DB 映射可能 partial | `FieldRule.chain` 与 `partialReason` 已进入契约 | 对 MyBatis/JPA/手写 SQL 分别扩展夹具 |
| `quality.status` 与 `productQuality` 并存 | 使用者可能只看构建成功 | `scripts/release_quality_gate.py` 已校验 `build.status=passed` 且 `productQuality` 非 failed | README/CI 输出继续优先展示 `productQuality` |
| Wiki 派生仍未完全从 read model 重构 | Markdown 可能与 Workbench 主体验不一致 | v1 当前以 Workbench 深度阅读为主验收 | 下一轮把 `export.py` 的域页从 read model 派生 |

## 发布质量门禁

合并/发布前优先运行统一脚本并保留输出证据：

```bash
python3 scripts/release_quality_gate.py
```

脚本内部覆盖以下门禁：

```bash
python3 -m pytest -q
cd workbench && npm ci && npm run build
python3 -m graph_wiki.pipeline build tests/fixtures/fullstack-enterprise --no-llm --output-dir output/release-gate/fullstack-enterprise
python3 -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir output/release-gate/svn-platform
```

通过标准：

1. `domain-read-model.json.schema.version == domain-read-model-v1`。
2. 至少一个 `core=true` 域拥有 flows、rules、fieldRules。
3. flow step / rule / fieldRule 的 `evidenceRefs` 可在 `evidenceIndex` 中解析。
4. `workbench-data.json.schema.source == domain-read-model.json`。
5. Workbench TypeScript build 通过。
6. `build-report.json.build.status == passed` 且 `productQuality.deepReadingStatus` 非 `failed`。
7. smoke 输出中不得通过删除测试、跳过失败或伪造空 evidence 达成通过。

## 下一轮建议

1. 将 `export.py` 的业务域 Markdown 从 `domain-read-model.json` 派生，减少 Wiki 与 Workbench 的双轨维护。
2. 为 Java/Spring 规则抽取补充更细粒度夹具：validation 注解、权限判断、状态流转、金额计算、库存扣减。
3. 为 Vue/React/TS 增加字段来源识别夹具：表单组件、组合式 API、fetch/axios wrapper、TS interface。
4. 将发布质量门禁固化为一条本地脚本或 CI job，统一输出 pytest、Workbench build、企业夹具 build、SVN smoke build 结果。

## 追加：工程化交付门禁固化（2026-06-29）

本轮将发布门禁固化为本地可执行脚本，并通过 `.github/workflows/ci.yml` 作为 PR/分支 CI 入口：

```bash
python3 scripts/release_quality_gate.py --output-root output/release-quality-gate
```

脚本默认顺序执行并记录证据：

1. `python3 -m pytest -q`
2. `cd workbench && npm ci && npm run build`
3. `python3 -m graph_wiki.pipeline build tests/fixtures/fullstack-enterprise --no-llm --output-dir <output>/fullstack-enterprise`
4. `python3 -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir <output>/svn-platform`
5. 校验两个 build 输出的 `domain-read-model.json`、`workbench-data.json`、`build-report.json`。

关键判定口径：

- `build.status=passed` 只说明流水线执行成功。
- `productQuality.deepReadingStatus` / `coreDomainEvidenceStatus` 才说明业务域深读体验是否可交付。
- `quality.phase3`、`quality.phase4`、`quality.phase5` 是阶段能力门禁，必须单独审查；不得被 `build.status` 或 `quality.status` 覆盖。
- `workbench-data.schema.source` 必须为 `domain-read-model.json`，否则 Workbench v1 业务域详情页不算通过。

脚本输出 `release-quality-gate-report.json`，作为最终交付验收证据索引。

## 最终交付风险审计（2026-06-29）

### 剩余 blocker

当前未发现必须阻塞 Domain Read Model v1 主线交付的工程 blocker。以下事项不阻塞本轮，但应进入下一轮 backlog：

- `export.py` 仍存在旧 Wiki 产物路径，尚未完全改为从 `domain-read-model.json` 派生。
- 规则抽取仍以 deterministic heuristic 为主；复杂权限、状态机、金额计算、库存扣减需要更多企业夹具。
- `source:<path>#<symbol>` 已有格式与引用存在性门禁，但符号级精准定位仍需独立 gate。
- Workbench 证据面板已支撑流程→规则→证据路径，但可筛选、可定位的证据浏览还需增强。

### 架构不变量

- Domain Read Model v1 的产品真相源是 `domain-read-model.json`。
- Workbench v1 业务域详情页只能从 `ProductDataService(domain-read-model.json)` 派生，不得直接拼接 legacy artifacts。
- 每个 flow step / rule / fieldRule 必须有 `evidenceRefs`，且引用必须能在 `evidenceIndex` 中解析。
- `build.status`、`productQuality.*`、phase gates 是三个不同层级的信号，发布说明和 CI 输出必须分开展示。
- 本轮清理后 `tests/svn-platform/node_modules` 和 `tests/svn-platform/package-lock.json` 不再作为源码仓库跟踪对象。

### 下一步建议

- 需要一次 Ralph 或 Code Review：建议需要。理由是本轮触及发布门禁、文档契约、仓库污染清理和验证脚本，适合由独立 reviewer 复核“是否存在口径漂移或隐藏跳过”。
- 若进入下一迭代，优先任务应是把 Wiki 域页从 `domain-read-model.json` 派生，并补强规则抽取夹具。
