# Graph-Wiki 第二轮设计文档审核报告（仅设计层面）

> 审核对象：`docs/architecture/graph-wiki-design.md` + `docs/design/` 下 5 份模块设计文档  
> 审核日期：2026-06-15  
> 审核方式：3 Agent 并行审查，排除代码实现，仅关注设计文档质量  
> 对比基线：第一轮审核（2026-06-11，评分 6.8/10）

---

## 综合评分：7.2 / 10

| 维度 | 一轮 | 二轮 | 说明 |
|------|:--:|:--:|------|
| 架构完整性 | 6.8 | 7.8 | M1-M8 修复有效，数据流和接口定义仍有不一致 |
| 模块设计完整度 | — | 6.5 | 5 份设计文档均有内容，但互相对齐度不足 |
| 跨文档一致性 | — | 6.0 | 架构文档与设计文档之间存在多处签名/参数矛盾 |
| **综合** | **6.8** | **7.2** | **+0.4**，M1-M8 修复有效，但深入审查暴露了新的跨文档不一致 |

---

## 🔴 严重问题（5 项）

### S1. `business_cluster` 签名在三处文档中各不相同

**严重度 92/100。**

| 来源 | 签名 | 参数数 | 返回类型 |
|------|------|:--:|------|
| 架构 §4.2 L291 | `business_cluster(G, packages, language)` | 3 | `dict[str, list[str]]` |
| cluster-design.md §2 L84 | `business_cluster(G, root_path, language, merge_threshold=0.3, min_domain_size=5)` | 5 | `list[Domain]` |
| pipeline-design.md §1 L84 | `business_cluster(G, root, language=language)` | 3 | 未标注 |

架构文档用 `packages`，设计文档用 `root_path`；架构返回 `dict`，设计返回 `list[Domain]`。这是 API 的根本性分歧。

**修复**：以 cluster-design.md 的 5 参数签名为准（最完整），同步更新架构文档 §4.2。

---

### S2. `export_wiki` 参数三处不一致

**严重度 85/100。**

| 来源 | 参数 |
|------|------|
| 架构 §4.4 | `export_wiki(domains, labels, G, output_dir)` |
| pipeline-design.md §1 L106 | `export_wiki(domains, api_matches, field_map, root)` |
| export 模块（架构 §4.4 输出目录） | 预期输出 wiki/index.md + wiki/{domain}/*.md |

架构文档传入 `labels` 和 `G`，pipeline 设计传入 `api_matches` 和 `field_map`。两个设计文档描述的完全是不同的接口。

**修复**：统一为 `export_wiki(domains, api_matches, field_map, output_dir)`，更新架构 §4.4。

---

### S3. `export_domain_html` 模块归属矛盾

**严重度 80/100。**

| 来源 | 归属 |
|------|------|
| 架构 §4.5 | `visualize.py` |
| pipeline-design.md L13（import） | `from .export import export_domain_html` |
| pipeline-design.md §3 L238（构建顺序图） | `export_domain_html() ← visualize.py` |

同一份 pipeline 设计文档内，第 13 行从 export 导入，第 238 行标注属于 visualize。架构文档说是 visualize。三方各执一词。

**修复**：统一归属 `visualize.py`，修正 pipeline-design.md L13 的 import 语句。

---

### S4. 架构 §5.1 数据流图缺少 api_mapper 和 field_mapper 模块

**严重度 78/100。**

架构 §5.1"完整构建流程"的流程图中，从 `business_cluster()` 直接跳到 `llm_label()`，完全省略了 `api_mapper` 和 `field_mapper`。但同一文档的 §4.8 和 pipeline-design.md 均将这两个模块列为正式步骤。

**修复**：在 §5.1 流程图中补充 Step 5（API 映射）和 Step 6（字段映射），标注为可选步骤。

---

### S5. `Domain` 数据类缺少 `anchors_flat()` 方法定义

**严重度 75/100。**

Cluster-design.md 和 label-design.md 中的 `Domain` 数据类只定义了属性（`anchors: dict[str, list]`），没有任何方法。但两个设计文档的多个位置都调用了 `domain.anchors_flat()`：
- cluster-design.md L308（`extract_business_points`）
- cluster-design.md L513（`domain_anchor_ids`）
- label-design.md L306（`generate_summary_md`）

调用不存在的方法会在任何实现中导致运行时错误。

**修复**：在 cluster-design.md 的 Domain 数据类中新增 `anchors_flat()` 方法定义。

---

## 🟡 中等问题（10 项）

### M1. 架构 §4.2 伪代码引用 `split_loose_domains` 但 cluster 设计文档无定义

架构 §4.2 L449 在 `business_cluster` 伪代码中调用了 `split_loose_domains(domains, G)`。但 cluster-design.md 的"五步法"从头到尾没有提及此函数。如果它的作用是"拆分内聚度低的域"，则是聚类流程的必要步骤。

**修复**：要么在 cluster-design.md 中新增 `split_loose_domains` 的设计，要么从架构文档伪代码中移除该行。

---

### M2. `analyze_cross_domain` 在 cluster 设计文档中函数体为 `pass`

Cluster-design.md §7 L544-560，`analyze_cross_domain()` 的实现体为 `pass`，仅注释"见总体设计 12.7 节完整伪代码"。跨域调用分析是业务点提取的核心步骤，设计文档没有给出具体设计，完全外部依赖架构文档。

**修复**：将架构 §12.7 的伪代码原则浓缩到 cluster-design.md 中，至少给出输入/输出类型和算法步骤。

---

### M3. Label prompt 模板引用 `domain.controllers` 但 Domain 类型无此属性

Label-design.md §4 L183-189 的 prompt 组装代码写为 `hasattr(domain, 'controllers')` 和 `domain.services`，期望 Domain 具有 `.controllers`、`.services` 直接属性。但 cluster-design.md 的 Domain 数据类只有 `anchors: dict[str, list]`（按角色分组），正确访问方式应为 `domain.anchors.get('controller', [])`。

**修复**：统一 label-design.md 中的访问方式为 `domain.anchors.get('controller', [])`。

---

### M4. `build_field_map` 参数在 pipeline 设计和 field-mapper 设计中不一致

| 来源 | 签名 |
|------|------|
| field-mapper-design.md §2 L29 | `build_field_map(api_matches, entity_dir, dto_dir, backend_root)` |
| pipeline-design.md §1 L97 | `build_field_map(api_matches, root / "ops-backend", root)` |

Pipeline 设计传入 3 个参数，field-mapper 设计定义 4 个参数。`dto_dir` 在 pipeline 调用中缺失。

**修复**：统一为 4 参数，pipeline-design.md 补上 `dto_dir`。

---

### M5. `parse_frontend_apis` 返回类型架构文档与 api-mapper 设计文档不一致

| 来源 | 返回类型 |
|------|------|
| 架构 §4.6 | `dict`（嵌套结构 `{"module.js": {"functions": [...], "imported_by": [...]}}`） |
| api-mapper-design.md §3 | `list[FrontendApiCall]`（扁平列表） |

架构文档描述的是聚合后的 dict 结构（含 imported_by），设计文档描述的是扁平列表。两者语义不同。

**修复**：以 api-mapper-design.md 为准，更新架构 §4.6 的伪代码。

---

### M6. `BackendEndpoint` 字段命名不一致：`imported_by` vs `callers`

架构 §4.6 描述前端 API 解析输出时使用 `imported_by`，api-mapper-design.md 的 `FrontendApiCall` 使用 `callers`。同一概念两个名字。

**修复**：统一为 `callers`。

---

### M7. 架构 §8.2 数据流将 `field-map.json` 标注在 cluster 层产出

架构 §8.2 的三层数据流图中，第二层（cluster 层）的输出标注为 `→ domains.json` + `→ field-map.json`。但 field-map.json 的实际生成者是 field_mapper 模块，依赖 api_mapper 的匹配结果，不可能在 cluster 层产出。

**修复**：将 `field-map.json` 从第二层移至第三层（导出层）的输出中。

---

### M8. Pipeline 设计使用 subprocess 调用 graphify，与架构复用策略矛盾

架构 §1.3 明确说 graphifyy 是"Python 库被 import 使用"，§4.1 的 reuse.py 也按库 API 封装。但 pipeline-design.md §1 L126-140 使用 `subprocess.run(["graphify", ...])` 调用 CLI。

**修复**：统一为 Python API 调用，pipeline-design.md 的 query/path/explain 改为通过 reuse.py 调用。

---

### M9. `BusinessPoint.cross_domain_calls` 类型与架构分析函数返回类型不匹配

Cluster-design.md 中 `BusinessPoint.cross_domain_calls: dict`（扁平 dict），但架构 §12.7 的分析函数返回三层分类结构 `{'cross_domain': dict, 'internal': list, 'infrastructure': list}`。两者的结构不兼容。

**修复**：要么扩展 BusinessPoint 增加 `internal_calls` 和 `infrastructure_calls` 字段（架构方向），要么简化架构的返回类型为扁平 dict。

---

### M10. 数据模型分散在 3 份设计文档中，无集中定义

| 数据类 | 定义位置 |
|------|------|
| `Domain`, `BusinessPoint`, `NodeRole`, `Language` | cluster-design.md |
| `FrontendApiCall`, `BackendEndpoint`, `ApiMatch` | api-mapper-design.md |
| `LabelConfig`, `LlmBackend` | label-design.md |

Field-mapper-design.md 和 pipeline-design.md 引用这些类型但不定义它们。跨模块共享类型没有统一规范。

**修复**：在各设计文档的依赖章节中明确引用源文档，或创建独立的 `models-design.md`。

---

## 🟢 轻微问题（5 项）

### L1. `filter_noise` 签名：架构文档含 `root_path`，cluster 设计文档不含

架构 §4.2 伪代码中 `filter_noise(G, root_path)`，cluster-design.md 中 `filter_noise(G: nx.Graph) -> list[dict]`。

### L2. `detect_domain_boundary_change` 无法检测文件跨目录移动

Pipeline-design.md §2 L196-219 的检测逻辑只检查字符串包含（`/pom.xml`、`/src/main/java/`），无法检测包路径重命名。

### L3. Field-mapper-design.md §5 的 dict 推导式存在 Python 语法错误

L320-326 的 `"layer_1_vue": {...} for caller in ... for field in ...` 在 Python 字典推导式中是语法错误（不支持双循环逐键赋值）。

### L4. `LabelConfig` 在 `--no-llm` 模式下仍然被构造

Pipeline-design.md §1 L100-103 的 LabelConfig 构造在条件判断外部，属于设计上的冗余。

### L5. Pipeline 设计缺少 `--output` 参数，输出目录硬编码

Pipeline-design.md 的 CLI 定义没有 `--output` 参数，输出目录在代码中固定为 `wiki/` 和 `domain_graph.html`。

---

## 修复优先级

| 优先级 | 编号 | 问题 | 影响范围 |
|:--:|------|------|------|
| **P0** | S1 | business_cluster 签名三处不一致 | 架构 + cluster + pipeline 设计 |
| **P0** | S2 | export_wiki 参数三处不一致 | 架构 + pipeline 设计 |
| **P0** | S5 | Domain.anchors_flat() 缺失定义 | cluster + label 设计 |
| **P1** | S3 | export_domain_html 模块归属矛盾 | 架构 + pipeline 设计 |
| **P1** | S4 | §5.1 流程图缺失模块 | 架构文档 |
| **P1** | M1-M4 | 函数引用/类型/参数不一致 | 跨文档 |
| **P2** | M5-M10 | 命名/数据流层级/模型分散 | 各设计文档 |
| **P3** | L1-L5 | 细节瑕疵 | 各设计文档 |

---

## 与第一轮对比

| | 第一轮 | 第二轮 |
|------|:--:|:--:|
| 严重问题 | 4（S1-S4） | 5（S1-S5，全为新发现） |
| 中等问题 | 8（M1-M8） | 10（全为新发现） |
| 轻微问题 | 6（L1-L6） | 5（全为新发现） |
| 评分 | 6.8 | 7.2 |

**核心变化**：第一轮的 18 个问题全部修复，但深入审查暴露了新的问题层——**跨文档不一致**。这 20 个新问题在第一轮中未被发现，因为当时只审查了架构文档本身。第二轮将 5 份设计文档纳入审查范围后，签名矛盾、模块归属冲突、类型不匹配等问题浮现出来。
