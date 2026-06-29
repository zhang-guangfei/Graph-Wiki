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

## 主要风险与处置

| 风险 | 影响 | 当前处置 | 后续建议 |
| --- | --- | --- | --- |
| 前端契约仍保留 v0 表述 | 容易让实现绕回 `domains.json` / `api-map.json` 拼业务域详情 | 已修订 `docs/design/frontend-data-contract.md`，补充 v1 派生规则和质量门禁 | 继续让测试断言 `workbench-data.schema.source` |
| 规则抽取仍偏 deterministic heuristic | 真实企业代码中复杂规则可能只生成弱规则 | `BusinessRule.status/confidence/review` 区分 ready/partial/machine_draft | 增加 Service 条件、注解、枚举状态机的专门夹具 |
| 字段链路完整度依赖命名和目录约定 | DTO/Entity/DB 映射可能 partial | `FieldRule.chain` 与 `partialReason` 已进入契约 | 对 MyBatis/JPA/手写 SQL 分别扩展夹具 |
| `quality.status` 与 `productQuality` 并存 | 使用者可能只看构建成功 | 架构文档和契约要求分离 | README/CI 输出优先展示 `productQuality` |
| Wiki 派生仍未完全从 read model 重构 | Markdown 可能与 Workbench 主体验不一致 | v1 当前以 Workbench 深度阅读为主验收 | 下一轮把 `export.py` 的域页从 read model 派生 |

## 发布质量门禁

合并/发布前必须保留以下命令证据：

```bash
python3 -m pytest -q
cd workbench && npm ci && npm run build
python3 -m graph_wiki.pipeline build tests/fixtures/fullstack-enterprise --no-llm --output-dir output/fullstack-enterprise
python3 -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir output/svn-platform
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
