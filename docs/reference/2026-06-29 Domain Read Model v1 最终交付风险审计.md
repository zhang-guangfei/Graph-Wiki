# Domain Read Model v1 最终交付风险审计

日期：2026-06-29  
范围：Task 4 工程化交付收口，聚焦生成物污染、发布质量门禁、Workbench v1 契约和剩余 blocker。

## 结论

Domain Read Model v1 主链路保持可交付：`domain-read-model.json` 是 Workbench v1 的产品真相源，`workbench-data.json` 从它派生，业务域详情页按“流程 → 规则 → 证据”组织。发布口径必须继续区分：

- `build-report.json.build.status`：流水线是否执行成功。
- `build-report.json.build.artifactStatus`：旧 Wiki/API/字段等分析产物质量。
- `build-report.json.productQuality.deepReadingStatus`：Domain Read Model v1 是否具备产品可读性。

本轮已把 `tests/svn-platform/node_modules/**` 与 `tests/svn-platform/package-lock.json` 从版本控制移除，并通过 `.gitignore` 保持本地安装产物不再入库。

## 已加固项

1. 新增本地/CI 统一门禁：`scripts/release_quality_gate.py`。
   - `python -m pytest -q`
   - `graph_wiki.pipeline build tests/fixtures/fullstack-enterprise --no-llm`
   - `graph_wiki.pipeline build tests/svn-platform --no-llm`
   - `workbench npm ci && npm run build`
   - `tests/svn-platform npm install --no-audit --no-fund && npm run build`
   - 校验 `build.status` 与 `productQuality.deepReadingStatus` 不混淆。
2. 新增 GitHub Actions 工作流：`.github/workflows/release-quality-gate.yml`。
3. CLI 成功输出改为分别打印 `build.status`、`artifactStatus`、`productQuality.deepReadingStatus`。
4. `ProductDataService` 不再用旧 `quality.status` 回退填充 `quality.build`，避免前端总览重新耦合构建状态与产物质量。

## 架构不变量

- Workbench v1 业务域详情页只能以 `domain-read-model.json` 为主输入。
- flow step、business rule、field rule 的 `evidenceRefs` 必须能解析到 `evidenceIndex`。
- 字段规则必须显式表达链路完整度：存在层级进入 `presentLayers`，缺失层级进入 `missingRequiredLayers` / `missingOptionalLayers`，partial 状态必须给出 `partialReason`。
- `build.status=passed` 不等于产品可读；发布说明、CI 和 README 必须展示 `productQuality`。
- 生成物、依赖目录和 smoke 输出不提交到 git。

## 剩余 blocker / 风险

| 风险 | 状态 | 处理建议 |
| --- | --- | --- |
| 字段规则严格度 | 需要产品/验收决策 | 当前缺少 `fieldRules` 是 warning；验收矩阵 A5 写为 blocker。建议下一轮按“检测到字段链路候选时必须有 fieldRule，否则 frontend-only 项目允许 warning”细化规则。 |
| Python lint / typecheck | 工程缺口 | 当前没有 ruff/mypy/pyright 配置。建议下一轮引入 `ruff` 作为非破坏性 lint gate，再评估类型检查成本。 |
| `tests/svn-platform` deterministic frontend install | 工程缺口 | 本轮移除污染 lockfile，smoke 使用 `npm install`。如要 CI 完全可复现，应重新生成并审查最小 lockfile，而不是提交 node_modules。 |
| post-build repo cleanliness gate | 工程缺口 | 建议 release gate 增加可选 `git status --porcelain` 检查，排除预期的 `output/`、`node_modules/`、`dist/` ignored 产物。 |
| Wiki Markdown 仍未完全由 read model 派生 | 已知后续项 | Workbench v1 已走新模型；下一轮应将 `export.py` 域页改为从 read model 派生，消除双轨维护。 |

## 下一步建议

1. 先做一次 Code Review：重点看 release gate、`product_data.py` 状态语义、清理大量 node_modules 的集成风险。
2. 若 Code Review 通过，再用 Ralph 或等价持久执行流完成下一轮：字段规则严格度细化 + Wiki 从 read model 派生 + cleanliness gate。
3. 不建议在未决策字段规则适用范围前，直接把“所有 core domain 必须有 fieldRules”改成全局 hard fail；这会把 frontend-only smoke 项目误判为失败。
