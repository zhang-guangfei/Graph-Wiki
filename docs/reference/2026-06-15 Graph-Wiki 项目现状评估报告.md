# Graph-Wiki 项目现状评估报告

> 日期：2026-06-15  
> 评估人：Claude Code  
> 评估范围：全部文档 + 全部代码

---

## 一、项目进度总览

| 维度 | 状态 | 完成度 |
|------|:--:|:--:|
| 架构设计文档 | 核心设计完成，有遗留问题 | 85% |
| 模块详细设计 | 5 份设计文档均已编写 | 80% |
| 代码实现 | 9 个模块全部有代码 | 70% |
| 集成测试 | 尚未进行 | 0% |
| 文档审核 | 3-Agent 审核完成，评分 6.8/10 | — |

**整体判断**：项目处于 Phase 1 中后期——模块代码骨架已搭建，核心聚类链路可跑通，但缺少端到端集成测试、LLM 标注模块未联调、设计文档与代码之间存在差距。

---

## 二、Reference 文档（前期分析）— 全部完成

| 文档 | 内容 | 价值 |
|------|------|------|
| `graphify-limitations-analysis.md` | 基于 OPS 项目 5,200 Java 文件的 8 个缺陷量化分析 | 为立项提供精确数据依据 |
| `code-analysis-tools-comparison.md` | 6 款同类工具对比 | 定位市场空缺 |
| `graph-wiki-audit-report.md` | 3-Agent 并行审核，评分 6.8/10 | 4 严重 + 8 中等 + 6 轻微问题 |
| `graph-wiki-capability-assessment.md` | 10 项需求对照，Token 成本评估 | 需求覆盖度验证 |

**关键发现**：
- Graphify 的 96.5% 节点是噪音，0 条业务边直接连接锚点
- Louvain 社区 ≈ Maven module，无业务语义
- 市场上无"自动从代码推断业务语义 + 以人类为第一客户"的方案

---

## 三、Architecture 文档（核心真相源）— 95% 完成

`graph-wiki-design.md` v1.0，17 章节（新增质量保障），3 万字级。

**2026-06-15 修复完成**：M1-M8 + L1-L6 全部修复，新增 ~240 行内容。

**审核遗留问题修复状态**：

| ID | 问题 | 状态 |
|:--|------|:--:|
| **S1** | field_mapper.py 缺失 | ✅ 已解决 |
| **S2** | graphify query 子图机制 | ✅ 已解决 |
| **S3** | 章节编号错乱 | ✅ 已修复 |
| **S4** | Java 偏见 / 非 Java 适配 | ⚠️ 代码支持，文档部分更新 |
| **M1** | 聚类步骤不一致 | ✅ 三步法→五步法 |
| **M2** | docs.py 引用 | ✅ 3 处引用已清除 |
| **M3** | LLM 并行化 | ✅ 新增 ThreadPoolExecutor 策略说明 |
| **M4** | 增量更新 | ✅ 4.6/4.7 各新增增量更新小节 |
| **M5** | JSON Schema | ✅ 新增 field-map.json + manifest.json 规范 |
| **M6** | Phase 时间线 | ✅ 更新为 9-10 周 + 完成项标记 |
| **M7** | Agent Token | ✅ 新增首次导航 + 跨域查询场景 |
| **M8** | 质量保障 | ✅ 新增 §17（测试策略 + 错误处理 + 安全） |
| **L1** | language=auto 检测 | ✅ 已有文档（4.2 多语言段落） |
| **L2** | 测试文件排除 | ✅ 代码已实现 |
| **L3** | 子域嵌套 | ✅ 加入弊端分析表（v1.0 限制） |
| **L4** | Lombok 样板代码 | ✅ §8.4 新增注释 |
| **L5** | 跨域 API 查询 | ✅ M7 更新中已覆盖 |
| **L6** | 域边界稳定性 | ✅ 加入弊端分析表 |

---

## 四、Design 文档（模块详细设计）— 80% 完成

| 文档 | 详细度 | 与代码对齐度 | 关键差距 |
|------|:--:|:--:|------|
| `cluster-design.md` | 高 | 90% | 边类型适配（imports_from）、JS 方法标签格式 |
| `label-design.md` | 高 | 70% | 缺 retry 逻辑、OpenAI/Gemini 后端、业务点文件 |
| `api-mapper-design.md` | 高 | 75% | 缺 service_chain 提取、param_fields、component 追踪 |
| `field-mapper-design.md` | 高 | 65% | 缺 build_full_chain 六层结构、前缀移除匹配 |
| `pipeline-design.md` | 高 | 60% | update 命令未实现、无配置文件加载 |

---

## 五、代码模块 — 70% 完成

| 模块 | 行数 | 核心功能 | 关键缺失 |
|------|:--:|------|------|
| `models.py` | 114 | 数据模型完整 | — |
| `reuse.py` | 40 | graphifyy 封装 | `merge_graph` 未测试 |
| `cluster.py` | 468 | **核心链路可跑通** | 边类型兼容性已修复 |
| `api_mapper.py` | 269 | 前后端匹配基础可用 | service_chain、param_fields |
| `field_mapper.py` | 185 | 字段映射基础可用 | build_full_chain 六层结构 |
| `label.py` | 210 | Claude 标注可用 | retry、OpenAI/Gemini、错误类 |
| `export.py` | 164 | Wiki 导出可用 | data-flow.md 质量待提升 |
| `visualize.py` | 99 | D3 可视化可用 | — |
| `pipeline.py` | 182 | CLI build 可用 | update 命令未完成、无配置文件 |

**本次 session 在 cluster.py 中修复的 5 个 bug**：
1. `imports` vs `imports_from` 边类型不匹配
2. JS 方法标签无 `.` 前缀导致全部过滤
3. `_role` 写入 entry 但 `extract_business_points` 从 `G.nodes` 读取
4. `Domain.dependencies` 未填充
5. `/api/` 路径匹配未处理无前导 `/`

---

## 六、测试 — 0% 完成

- `tests/` 目录为空
- 本次 session 中用 `test_graph.json`（Vue 前端项目，3,706 节点，4,769 边）验证了聚类模块
- **尚未对真实项目执行完整的 `graph-wiki build`**

---

## 七、下一步建议

### Phase 1 剩余工作（按优先级）

1. **完成架构文档 M1-M8 修复**（特别是 M1 步骤不一致、M5 Schema 定义）
2. **补齐 label.py**（retry 逻辑、错误处理）
3. **补齐 field_mapper.py**（build_full_chain 六层结构）
4. **编写集成测试**（对真实 Java+Vue 项目执行 build）
5. **跑通完整 `graph-wiki build`**（含 LLM 标注的真实端到端测试）
