# Dream Cycle 设计说明

## 定位

Dream Cycle 是 Graph-Wiki Phase 5 的轻量维护循环。它不替代 `build`，也不做自动内容改写，而是在每次构建后说明知识库发生了什么变化、哪些人工内容被保护、哪些页面需要治理。

## 输入

- 上一次 `manifest.json`
- 本次 `manifest.json`
- 本次 `domains`
- 本次 `impact-analysis.json`
- 当前 `wiki/` 目录

## 输出

- `dream-cycle-report.json`：机器可读维护报告
- `wiki/changelog.md`：人类可读变更摘要
- `build-report.json.quality.phase5`：Phase 5 质量门禁摘要

## v0 能力

| 能力 | v0 行为 |
|---|---|
| Detect | 对比 manifest，列出新增、修改、删除、未变文件 |
| Reconcile | 对比域 anchors hash，列出新增、移除、变化、未变域 |
| Preserve | 检查 `rules.md` / `spec.md` 是人工内容还是占位 |
| Backlink | 检查 `dependencies.md` 是否包含域依赖双链 |
| Synthesize | 列出重复业务点候选，不自动合并 |
| Validate | 输出 `passed` / `warning` / `failed` 状态 |
| Changelog | 写出维护摘要 |

## 明确不做

- 不做局部 AST 增量重建。
- 不覆盖人工维护的 `rules.md` / `spec.md`。
- 不自动合并业务点。
- 不做聊天 UI。
- 不自动生成权威业务规则正文。

## 验收标准

- 同一产物目录连续 build 两次，第二次能基于旧 manifest 生成维护摘要。
- 人工写入的 `rules.md` / `spec.md` 在第二次 build 后仍保留。
- `dream-cycle-report.json` 能说明文件变化、域变化、孤立页面、归档旧域、回链和重复业务点候选。
- `wiki/changelog.md` 能给人类读懂的维护摘要。
