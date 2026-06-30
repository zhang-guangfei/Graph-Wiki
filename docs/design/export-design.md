# Export 模块详细设计

> 对应总体设计：第七章 [7.8 节](../architecture/graph-wiki%20工程架构设计.md#44-wiki-导出模块-export)、[第六章 数据契约与输出规范](../architecture/graph-wiki%20工程架构设计.md#六输出规范)
> 状态：详细设计阶段（v2.0，重写）
> 依赖：`models.py`（Domain / ApiMatch / BusinessPoint）、`label.py`（summary.md 前提）
> 参考：`models-design.md`（数据模型）、`api-mapper-design.md`（ApiMatch 来源）、`field-mapper-design.md`（field_map 来源）

---

## 1. 模块职责

### 1.1 核心职责

将聚类、标注、API 映射、字段映射四个模块的处理结果，渲染为 **Obsidian 兼容的双向链接 Markdown 文档库**（Wiki）。

```
输入:  list[Domain]      → 来自 cluster.py + label.py（含 LLM 标注的名称/描述）
       list[ApiMatch]    → 来自 api_mapper.py（前后端 API 匹配）
       dict field_map    → 来自 field_mapper.py（字段级数据流）
       Path output_dir   → 输出目录（默认 "wiki"）

输出:  wiki/index.md              业务域总目录
       wiki/api-index.md          全部 API 总览
       wiki/{domain}/summary.md   业务摘要（前提：由 label.py 生成）
       wiki/{domain}/code-map.md  代码清单
       wiki/{domain}/api-docs.md  单域 API 文档
       wiki/{domain}/data-flow.md 字段级数据流
       wiki/{domain}/dependencies.md  域间依赖
       wiki/{domain}/rules.md     业务规则（占位，人工填写）
       wiki/{domain}/spec.md      需求规格（占位，人工填写）
       wiki/{domain}/er-diagram.md ER 图（Phase 4+ 占位）
```

### 1.2 为什么需要独立的导出模块

| 问题 | 说明 |
|------|------|
| **跨模块数据聚合** | export 是唯一同时消费 Domain、ApiMatch、field_map 三种数据类型的模块，需要将它们融合到同一文档树中 |
| **Obsidian 兼容性** | Markdown 生成需满足 Obsidian 双向链接语法（`[[domain]]`）、YAML frontmatter、相对路径链接，这些是独立的技术约束 |
| **幂等生成** | 多次执行 export 应产生相同结果（确定性输出）。人工填写的 rules.md/spec.md 不应被覆盖 |
| **增量更新友好** | 只重新生成自动化的 md 文件，不触碰人工编辑的文件 |

### 1.3 消费者链路

```
export 模块的输出由三类消费者使用：

1. 人类程序员（主要消费者）
   打开 wiki/index.md → 浏览域目录 → 进入具体域 → 阅读 summary/code-map/API/数据流
   
2. Agent（AI 辅助）
   读取 wiki/index.md 获得域列表 → [[采购管理]] 链接跳转 scope reduction
   
3. CI/CD 系统
   - 构建后自动生成 wiki/ 目录并提交到仓库
   - MR 中自动附上变更影响域列表
```

### 1.4 与其他模块的关系

```
上游模块:
  cluster.py  → list[Domain] (含 business_points、dependencies)
  label.py    → list[Domain].name + .display_name 标注 + summary.md 生成
  api_mapper.py → list[ApiMatch]
  field_mapper.py → dict field_map

本模块:
  export.py   ← 只读消费以上所有输出，不修改任何 models 实例

下游:
  wiki/*.md   → Obsidian vault 直接打开
  CI 流程     → git add wiki/ && git commit
```

---

## 2. 完整接口定义

### 2.1 主入口函数

```python
def export_wiki(
    domains: list[Domain],
    api_matches: list[ApiMatch],
    field_map: dict,
    output_dir: Path = Path("wiki"),
) -> Path:
    """生成 Obsidian 兼容的 Wiki 文档树。

    参数说明:
        domains:      聚类+标注后的业务域列表。每个域必须包含 id、anchors、business_points。
                      如果 label.py 已执行，domain.name 和 domain.display_name 应有值。
        api_matches:  api_mapper 产出的前后端匹配列表。项目无前端时传入空列表。
        field_map:    field_mapper 产出的字段映射字典。未执行 field_mapper 时传入空 dict。
        output_dir:   Wiki 输出根目录。默认 "wiki"，会自动创建。

    返回:
        Path: 生成的 wiki 根目录路径（即 output_dir 的 resolved 绝对路径）。

    异常:
        OSError:    目录创建失败、磁盘满、权限不足
        ValueError: domains 为空列表（至少需要一个域才能生成有意义的 wiki）

    幂等性保障:
        多次调用相同参数，自动生成的文件内容完全一致。
        人工填写的 rules.md、spec.md 不会被覆盖。
        summary.md 由 label.py 生成，export 不写入也不覆盖。
        er-diagram.md 为 Phase 4+ 占位，export 不写入具体内容。
    """
```

### 2.2 质量评估函数

```python
def evaluate_wiki_quality(
    domains: list[Domain],
    api_matches: list[ApiMatch],
    field_map: dict,
) -> dict:
    """评估 Wiki 输出是否具备交付价值。

    该函数不读取 Markdown 文件，而是基于 export 的输入数据计算质量指标，
    供 pipeline 写入 build-report.json。

    返回:
        {
          "domains": {
            "count": 12,
            "business_points": 420,
            "technical_name_ratio": 0.18,
            "max_domain_business_points": 88
          },
          "api": {
            "total": 230,
            "uncategorized": 31,
            "uncategorized_ratio": 0.13
          },
          "quality": {
            "status": "passed",
            "issues": []
          }
        }
    """
```

### 2.3 私有辅助函数

```python
def _write_index(domains: list[Domain], output_dir: Path) -> None:
    """生成 wiki/index.md：业务域总目录。

    内容:
        - YAML frontmatter（项目元信息）
        - 域列表表格（域名、锚点数、文件数、top-3 依赖域）
        - 未分类文件提示（若有域 ID 为 "uncategorized"）
        - 域间依赖图引用（![[domain_graph.png]]，条件性写入）
    """

def _write_api_index(api_matches: list[ApiMatch], output_dir: Path) -> None:
    """生成 wiki/api-index.md：全部 API 总览。

    内容:
        - 按域分组的 API 列表
        - 每个 API 显示方法、URL、前端调用者、后端 Controller
        - 每域最多显示 20 个 API（超出截断并提示 "更多请查看域内 api-docs.md"）
    """

def _write_code_map(domain: Domain, domain_dir: Path) -> None:
    """生成 {domain_dir}/code-map.md：代码地图。

    内容:
        - 按锚点角色分组的文件清单（Controller / Service / Mapper / DAO / Adapter）
        - 业务点列表（含 LLM 标注的 display_name）
        - 文件路径使用相对路径（从 wiki 根到源码的相对链接）
    """

def _write_api_docs(domain: Domain, api_matches: list[ApiMatch], domain_dir: Path) -> None:
    """生成 {domain_dir}/api-docs.md：单域 API 文档。

    内容:
        - 属于该域的所有 ApiMatch 列表
        - 每项包含 HTTP 方法、URL、前端文件/函数、后端类/方法、入参、匹配置信度
        - 前端调用者列表（Vue 页面路径）
    """

def _write_data_flow(domain: Domain, field_map: dict, domain_dir: Path) -> None:
    """生成 {domain_dir}/data-flow.md：字段级数据流。

    内容:
        - 从 field_map 中筛选属于该域的表和字段
        - 按"数据库表 → 列"两层展示
        - 每条数据流链路：API URL → DTO 字段 → Entity 字段 → DB 列
        - 调用该 API 的前端页面列表
    """

def _write_dependencies(domain: Domain, domain_dir: Path) -> None:
    """生成 {domain_dir}/dependencies.md：域间依赖。

    内容:
        - domain.dependencies 中的依赖表
        - 每项显示目标域名（带 [[双向链接]]）、import 次数、强度标注
        - 依赖强度分类：强(>=50) / 中(>=20) / 弱(<20)
    """

def _write_placeholder_files(domain: Domain, domain_dir: Path) -> None:
    """生成占位文件（仅首次创建，不覆盖已有内容）。

    占位文件清单:
        - rules.md:    业务规则（人工填写）
        - spec.md:     需求规格（人工填写）
        - er-diagram.md: ER 图（Phase 4+，初始仅含标题占位）

    行为:
        - 如果文件已存在（人工编辑过），跳过不覆盖
        - 如果文件不存在，写入带 YAML frontmatter 的标准模板
    """

def _resolve_output_dir(output_dir: Path) -> Path:
    """解析输出目录路径并创建。

    行为:
        - 扩展用户路径（~ 等）
        - 转换为绝对路径
        - 创建目录（parents=True, exist_ok=True）
        - 返回 resolved 绝对路径

    异常:
        OSError: 父目录不存在且无法创建、磁盘满、权限不足
    """

def _path_to_wiki_rel(source_file: str, domain_dir: Path, wiki_root: Path) -> str:
    """将源码文件路径转换为从 wiki 页面到源码的相对路径。

    示例:
        source_file: "com/smc/smccloud/bin/controller/BinController.java"
        wiki_root:   "/project/wiki/"
        domain_dir:  "/project/wiki/Bin数据管理/"
        返回:        "../../com/smc/smccloud/bin/controller/BinController.java"
    """
```

---

## 3. 输出目录结构

### 3.1 完整结构

```
{output_dir}/
├── index.md                    ← 业务域总目录
├── api-index.md                ← 全部 API 总览
│
├── 采购管理/                    ← 域名（由 label 标注的 domain.display_name）
│   ├── summary.md              ← 业务摘要（由 label.py 生成，export 不覆盖）
│   ├── code-map.md             ← 代码文件清单（全自动）
│   ├── api-docs.md             ← 单域 API 文档（全自动，条件生成）
│   ├── data-flow.md            ← 字段级数据流（全自动，条件生成）
│   ├── dependencies.md         ← 域间依赖表（全自动）
│   ├── rules.md                ← 业务规则（人工填写，仅首次创建占位）
│   ├── spec.md                 ← 需求规格（人工填写，仅首次创建占位）
│   └── er-diagram.md           ← ER 图（Phase 4+，仅首次创建占位）
│
├── Bin数据管理/                 ← 第二个业务域
│   ├── summary.md
│   ├── code-map.md
│   ├── api-docs.md
│   ├── data-flow.md
│   ├── dependencies.md
│   ├── rules.md
│   └── spec.md
│
├── 库存管理/                    ← 更多域...
├── .../
│
└── _uncategorized/              ← 当存在未分类文件时创建
    └── orphan-files.md          ← 未归属任何域的文件清单
```

### 3.2 目录创建策略

```
for domain in domains:
    # 目录命名策略：display_name 优先作目录名 → name 次之 → id 兜底
    dir_name = domain.display_name or domain.name or domain.id
    # display_name 优先（LLM 标注的中文名，如 "采购管理"）
    # name 其次（LLM 标注的英文标识，如 "purchase"）
    # id 兜底（cluster 生成的域键，如 "purchase"）
    
    domain_dir = output_dir / dir_name
    domain_dir.mkdir(parents=True, exist_ok=True)
    # parents=True: 确保多级目录可创建
    # exist_ok=True: 不因目录已存在而报错（幂等性保障）
```

### 3.3 文件条件生成矩阵

| 文件 | 生成条件 | 生成时机 | 可被覆盖 |
|------|---------|---------|:--------:|
| `index.md` | 总有 | 每次 export | 是 |
| `api-index.md` | `api_matches` 非空 | 每次 export | 是 |
| `summary.md` | **不由 export 生成**（由 label.py 生成） | — | — |
| `code-map.md` | 总有 | 每次 export | 是 |
| `api-docs.md` | 该域有匹配的 ApiMatch | 每次 export | 是 |
| `data-flow.md` | 该域在 field_map 中有条目 | 每次 export | 是 |
| `dependencies.md` | 总有（无依赖也生成空表） | 每次 export | 是 |
| `rules.md` | 仅首次 | 首次 export | **否**（保护人工编辑） |
| `spec.md` | 仅首次 | 首次 export | **否**（保护人工编辑） |
| `er-diagram.md` | 仅首次 | 首次 export | **否**（Phase 4+ 保留） |

---

## 4. index.md 生成

### 4.1 内容模板

```markdown
---
project: "{项目名称}"
generated: "{YYYY-MM-DD HH:mm}"
domains: {N}
total_anchors: {M}
---

# 业务域目录

> 自动生成于 {YYYY-MM-DD} | {N} 个业务域 | {M} 个锚点

## 业务域列表

| 业务域 | 锚点 | 文件 | 核心依赖域 |
|--------|:----:|:----:|-----------|
| [[采购管理]] | 12 | 45 | [[库存管理]], [[供应商管理]] |
| [[Bin数据管理]] | 8 | 28 | [[产品数据管理]], [[库存管理]] |
| [[客户订单管理]] | 6 | 32 | [[采购管理]], [[物流管理]] |

## 域间依赖图

![[domain_graph.png]]

---

*共 {N} 个业务域，{uncounted} 个文件未分类*
```

### 4.2 YAML frontmatter 字段

| 字段 | 类型 | 示例 | 来源 |
|------|------|------|------|
| `project` | `str` | `"OPS"` | 从 pipeline 参数传入 |
| `generated` | `str` | `"2026-06-15 14:30"` | 当前时间 |
| `domains` | `int` | `25` | `len(domains)` |
| `total_anchors` | `int` | `1215` | `sum(d.anchors_count() for d in domains)` |

### 4.3 域列表表格生成逻辑

```python
def _write_index(domains: list[Domain], output_dir: Path):
    lines = [
        "---",
        f'project: "{output_dir.parent.name}"',
        f'generated: "{datetime.now():%Y-%m-%d %H:%M}"',
        f"domains: {len(domains)}",
        f"total_anchors: {sum(d.anchors_count() for d in domains)}",
        "---",
        "",
        "# 业务域目录",
        "",
        f"> 自动生成于 {datetime.now():%Y-%m-%d} | {len(domains)} 个业务域 | "
        f"{sum(d.anchors_count() for d in domains)} 个锚点",
        "",
        "## 业务域列表",
        "",
        "| 业务域 | 锚点 | 文件 | 核心依赖域 |",
        "|--------|:----:|:----:|-----------|",
    ]

    for d in sorted(domains, key=lambda x: x.anchors_count(), reverse=True):
        display = d.display_name or d.name or d.id
        deps = ", ".join(
            f"[[{_resolve_dep_name(dep.get('domain', ''), domains)}]]"
            for dep in d.dependencies[:3]
        ) or "—"
        
        lines.append(
            f"| [[{display}]] | {d.anchors_count()} | {d.total_files} | {deps} |"
        )

    lines += [
        "",
        "## 域间依赖图",
        "",
        "![[domain_graph.png]]",
        "",
        "---",
        "",
        f"*共 {len(domains)} 个业务域*",
    ]

    (output_dir / "index.md").write_text("\n".join(lines) + "\n", encoding="utf-8")
```

### 4.4 域名解析辅助函数

```python
def _resolve_dep_name(dep_id: str, domains: list[Domain]) -> str:
    """将依赖域 ID 解析为可读的域名（用于 [[链接]] 显示文本）。"""
    for d in domains:
        if d.id == dep_id:
            return d.display_name or d.name or d.id
    return dep_id  # 如果找不到（外部依赖），原样返回
```

---

## 5. summary.md 前提

### 5.1 职责划分

| 模块 | 负责内容 | 写入文件 |
|------|---------|---------|
| `label.py` | LLM 生成 summary.md（初稿） | `wiki/{domain}/summary.md` |
| `export.py` | **不写入、不覆盖** summary.md | — |
| 人类 | 人工审阅、编辑定稿 | 编辑 `wiki/{domain}/summary.md` |

### 5.2 summary.md 内容模板（由 label.py 生成）

```markdown
---
domain: {domain.id}
name: {domain.display_name}
anchors: {N}
updated: {YYYY-MM-DD}
source: llm
---

# {domain.display_name}

## 业务目标

{LLM 生成的一句话描述，说明该域的核心业务职责}

## 核心流程

1. **{流程名 1}** — {流程描述}
2. **{流程名 2}** — {流程描述}

## 关键业务规则

> 详见 [[rules]]

## 代码入口

| 层级 | 文件 | 说明 |
|------|------|------|
| Controller | [`XxxController.java`](相对路径) | {LLM 推断的职责} |
| Service | [`XxxService.java`](相对路径) | {LLM 推断的职责} |
| Mapper | [`XxxMapper.java`](相对路径) | {LLM 推断的职责} |

## 核心业务点

| 业务点 | 说明 | 跨域调用 |
|--------|------|---------|
| `方法名()` | {LLM 标注的中文说明} | → [[依赖域]] |

## 关联域

| 域 | 关系 | 说明 |
|----|------|------|
| [[库存管理]] | 调用 | 下单时预留库存 |
| [[供应商管理]] | 被调用 | 供应商变更通知本域 |
```

### 5.3 如果 label.py 未执行

当 `--no-llm` 模式下运行时，summary.md 可能不存在。此时 export 的行为：

```python
# export 不自动生成 summary.md 内容
# 但确保域目录存在，为后续 label 执行做准备
# 如果域目录不存在，创建之（目录创建本身由主循环负责）

# 如果希望提示用户，可以在 pipeline 层输出警告
```

**设计决策**：export 不自动生成 summary.md 的"降级版"（如仅含标题的占位），原因：

1. 导出模块的职责是渲染已有数据，不是 LLM 生成内容
2. 如果没有 label 数据，生成空的 summary.md 反而让用户误以为"这个东西没用"
3. 让 summary.md 的缺失成为显式的信号，提示用户需要执行标注步骤

> 参见架构设计[决策 5](../architecture/graph-wiki%20工程架构设计.md#决策-5rulesmd-和-specmd-不由-llm-生成)：rules.md 和 spec.md 不由 LLM 生成。summary.md 是 LLM 生成的边界。

---

## 6. code-map.md 生成

### 6.1 内容模板

```markdown
# {domain.display_name} — 代码地图

> 自动生成 | {N} 个锚点 | {M} 个业务点

## Controller 层

- [`BinOrderController.java`](../../相对路径/BinOrderController.java)
- [`BinController.java`](../../相对路径/BinController.java)

## Service 层

- [`BinDataServiceImpl.java`](../../相对路径/BinDataServiceImpl.java)
- [`BinOrderService.java`](../../相对路径/BinOrderService.java)

## Mapper 层

- [`BinOrderMapper.java`](../../相对路径/BinOrderMapper.java)
- [`BindataApplyMapper.java`](../../相对路径/BindataApplyMapper.java)

## DAO 层

- [`BinDataDao.java`](../../相对路径/BinDataDao.java)

## Entity / DTO

- [`BindataApply.java`](../../相对路径/BindataApply.java)
- [`BinOrder.java`](../../相对路径/BinOrder.java)
- [`BinOrderQueryDTO.java`](../../相对路径/BinOrderQueryDTO.java)

## 业务点（12 个）

- **Bin订单明细查询** — `listBinOrderDetail()`
- **Bin数据导入** — `importBindata()`
- **Bin订单创建** — `createBinOrder()`

---

*共 {N} 个锚点，{M} 个业务点*
```

### 6.2 角色分组顺序

锚点角色在 code-map.md 中的呈现顺序（硬编码，以保持输出稳定）：

```python
_ROLE_DISPLAY_ORDER = [
    ("controller",   "Controller 层"),      # 后端控制器
    ("service_impl", "Service 实现层"),       # Service 实现
    ("service_api",  "Service 接口层"),       # Service 接口
    ("mapper",       "Mapper 层"),            # MyBatis Mapper
    ("dao",          "DAO 层"),               # DAO
    ("adapter",      "Adapter 层"),           # 适配器
    ("entity",       "Entity / 实体"),        # 实体
    ("dto",          "DTO"),                  # 数据传输对象
    ("vo",           "VO"),                   # 视图对象
    ("enum",         "枚举"),                 # 枚举
    ("handler",      "Handler / 组件"),       # 处理器
    ("config",       "配置文件"),             # 配置
    ("util",         "工具类"),               # 工具
]

# 只渲染该域中存在的角色（domains.anchors 中有的 key）
# 不存在的角色跳过，不输出空章节
```

### 6.3 文件路径为相对链接

```python
def _path_to_wiki_rel(source_file: str, domain_dir: Path, wiki_root: Path) -> str:
    """将源码文件路径转换为从 wiki 页面到源码的相对路径。

    例如:
        domain_dir:  wiki_root / "采购管理" / "code-map.md"
        source_file: "com/smc/smccloud/purchase/controller/PurchaseController.java"
        返回:        "../../com/smc/smccloud/purchase/controller/PurchaseController.java"
                     （从 wiki/采购管理/ 到项目根，再拼源码路径）
    """
    wiki_root = wiki_root.resolve()
    domain_abs = domain_dir.resolve()
    
    # 计算 wiki 根到 domain 目录的相对偏移
    rel_to_wiki = os.path.relpath(wiki_root, domain_abs)
    # rel_to_wiki = "../.."（如果 domain 在 wiki 的一级子目录中）
    
    return f"{rel_to_wiki}/{source_file}"
```

### 6.4 业务点列表

```python
def _write_business_points(business_points: list[BusinessPoint], lines: list[str]) -> None:
    if not business_points:
        return

    lines.append(f"## 业务点（{len(business_points)} 个）")
    lines.append("")

    # 按 display_name 是否为空排序：已标注的排前面
    sorted_bps = sorted(
        business_points,
        key=lambda bp: (0 if bp.display_name else 1, bp.name),
    )

    for bp in sorted_bps[:30]:  # 最多显示 30 个
        method_name = bp.name.lstrip(".")
        display = bp.display_name or method_name
        lines.append(f"- **{display}** — `{method_name}()`")
```

---

## 7. api-index.md 生成

### 7.1 内容模板

```markdown
# API 接口索引

> {N} 个接口 | {M} 个业务域 | 自动生成于 {YYYY-MM-DD}

## [[采购管理]]

| 方法 | URL | 前端调用者 | 后端 Controller |
|------|-----|-----------|----------------|
| POST | /api/purchase/order/create | apply/index.vue | PurchaseController.createPurchaseOrder() |
| GET  | /api/purchase/order/list   | query/index.vue | PurchaseController.listPurchaseOrder() |

## [[Bin数据管理]]

| 方法 | URL | 前端调用者 | 后端 Controller |
|------|-----|-----------|----------------|
| POST | /api/bindata/import    | csstock/apply/index.vue | BinDataController.importBindata() |
| POST | /api/binorder/detail   | csstock/query/index.vue | BinOrderController.listBinOrderDetail() |
| GET  | /api/binorder/status   | csstock/apply/index.vue | BinOrderController.getOrderStatus() |

---

*部分域接口较多，仅显示前 20 个。完整列表请查看域内 api-docs.md*
```

### 7.2 分组与截断逻辑

```python
def _write_api_index(api_matches: list[ApiMatch], domains: list[Domain], output_dir: Path):
    # 按 domain 分组（使用域名而不是 domain ID）
    by_domain: dict[str, list[ApiMatch]] = {}
    for m in api_matches:
        domain_key = _resolve_domain_display(m.domain, domains)
        by_domain.setdefault(domain_key, []).append(m)

    lines = [
        "---",
        f"generated: \"{datetime.now():%Y-%m-%d %H:%M}\"",
        f"total_apis: {len(api_matches)}",
        f"domains: {len(by_domain)}",
        "---",
        "",
        "# API 接口索引",
        "",
        f"> {len(api_matches)} 个接口 | {len(by_domain)} 个业务域 "
        f"| 自动生成于 {datetime.now():%Y-%m-%d}",
        "",
    ]

    # 按域名排序展示
    for domain_key in sorted(by_domain.keys()):
        matches = by_domain[domain_key]
        lines.append(f"## [[{domain_key}]]")
        lines.append("")
        lines.append("| 方法 | URL | 前端调用者 | 后端 Controller |")
        lines.append("|------|-----|-----------|----------------|")

        MAX_DISPLAY = 20
        for m in matches[:MAX_DISPLAY]:
            callers = "; ".join(
                c.get("page", "") for c in m.frontend.callers[:3]
            ) or "—"
            lines.append(
                f"| {m.frontend.http_method} | `{m.frontend.url}` | {callers} | "
                f"`{m.backend.controller_class}.{m.backend.method_name}()` |"
            )

        if len(matches) > MAX_DISPLAY:
            lines.append(
                f"| ... | *还有 {len(matches) - MAX_DISPLAY} 个接口* | ... | ... |"
            )

        lines.append("")

    (output_dir / "api-index.md").write_text("\n".join(lines) + "\n", encoding="utf-8")
```

### 7.3 条件跳过

当 `api_matches` 为空列表时（项目无前端或 API 映射未执行），跳过 api-index.md 生成。

---

## 8. api-docs.md 生成

### 8.1 内容模板

```markdown
# {domain.display_name} — API 文档

> {N} 个接口

## POST `/api/binorder/detail`

- **前端**: `src/api/binorder.js` → `listBinOrderDetail()`
- **后端**: `BinOrderController.listBinOrderDetail()`
- **入参**: `BinOrderQueryDTO`
- **返回**: `Result`
- **匹配置信度**: 100%

**调用者**:
- `csstock/apply/index.vue`
- `csstock/query/index.vue`

---

## POST `/api/bindata/import`

- **前端**: `src/api/bindata.js` → `importBindata()`
- **后端**: `BinDataController.importBindata()`
- **入参**: `BindataImportDTO`
- **返回**: `Result`
- **匹配置信度**: 100%

**调用者**:
- `csstock/apply/index.vue`
```

### 8.2 域匹配逻辑

```python
def _write_api_docs(domain: Domain, api_matches: list[ApiMatch], domain_dir: Path):
    # 一个 ApiMatch 属于该域的条件（三者任一匹配即可）：
    # 1. m.domain == domain.id
    # 2. m.domain == domain.name
    # 3. m.domain == domain.display_name
    domain_matches = [
        m for m in api_matches
        if m.domain in (domain.id, domain.name, domain.display_name)
    ]
    if not domain_matches:
        return  # 该域没有 API → 不生成 api-docs.md

    lines = [
        "---",
        f"domain: {domain.display_name or domain.name or domain.id}",
        f"apis: {len(domain_matches)}",
        f"generated: \"{datetime.now():%Y-%m-%d %H:%M}\"",
        "---",
        "",
        f"# {domain.display_name or domain.name or domain.id} — API 文档",
        "",
        f"> {len(domain_matches)} 个接口",
        "",
    ]

    for m in domain_matches:
        lines += [
            f"## {m.frontend.http_method} `{m.frontend.url}`",
            "",
            f"- **前端**: `{m.frontend.source_file}` → `{m.frontend.function_name}()`",
            f"- **后端**: `{m.backend.controller_class}.{m.backend.method_name}()`",
            f"- **入参**: `{m.backend.param_type or '无'}`",
        ]
        if m.backend.return_type:
            lines.append(f"- **返回**: `{m.backend.return_type}`")
        lines.append(f"- **匹配置信度**: {m.match_confidence:.0%}")
        lines.append("")

        if m.frontend.callers:
            lines.append("**调用者**:")
            for c in m.frontend.callers[:5]:
                lines.append(f"- `{c.get('page', '')}`")
            lines.append("")

        if m.backend.service_chain:
            lines.append("**后端调用链**:")
            for s in m.backend.service_chain[:5]:
                lines.append(f"- `{s}`")
            lines.append("")

        lines.append("---")
        lines.append("")

    (domain_dir / "api-docs.md").write_text("\n".join(lines) + "\n", encoding="utf-8")
```

---

## 9. data-flow.md 生成

### 9.1 内容模板

```markdown
# {domain.display_name} — 数据流

> 自动生成 | {N} 个表 | {M} 个字段链路

## bin_data_apply

### supplier_code

| API | DTO 字段 | Entity 字段 | 前端调用者 |
|-----|----------|------------|-----------|
| `POST /api/bindata/import` | `supplierCode` | `supplierCode` | `csstock/apply/index.vue` |

### model_no

| API | DTO 字段 | Entity 字段 | 前端调用者 |
|-----|----------|------------|-----------|
| `POST /api/bindata/import` | `modelNo` | `modelNo` | `csstock/apply/index.vue` |

---

## bin_order

### order_no

| API | DTO 字段 | Entity 字段 | 前端调用者 |
|-----|----------|------------|-----------|
| `POST /api/binorder/detail` | `orderNo` | `orderNo` | `csstock/query/index.vue` |

---

*共 {N} 个表，{M} 个字段*
```

### 9.2 field_map 数据结构

```python
# field_map 的结构（由 field_mapper.py 产出）：
{
    "bin-data": {                     # 域 ID 或域名
        "bin_data_apply": {           # 数据库表名
            "supplier_code": [        # 数据库列名
                {
                    "api_url": "POST /api/bindata/import",
                    "api_function": "importBindata",
                    "dto_field": "supplierCode",
                    "entity_class": "BindataApply",
                    "entity_field": "supplierCode",
                    "db_column": "supplier_code",
                    "db_table": "bin_data_apply",
                    "callers": ["csstock/apply/index.vue"],
                    "confidence": 1.0,
                }
            ]
        }
    }
}
```

### 9.3 域匹配逻辑

```python
def _write_data_flow(domain: Domain, field_map: dict, domain_dir: Path):
    # 依次尝试 domain.id, domain.name, domain.display_name 三个 key
    tables = field_map.get(domain.id, {})
    if not tables:
        tables = field_map.get(domain.name, {})
    if not tables:
        tables = field_map.get(domain.display_name, {})
    if not tables:
        return  # 该域没有数据流 → 不生成 data-flow.md

    total_columns = sum(len(cols) for cols in tables.values())

    lines = [
        "---",
        f"domain: {domain.display_name or domain.name or domain.id}",
        f"tables: {len(tables)}",
        f"fields: {total_columns}",
        f"generated: \"{datetime.now():%Y-%m-%d %H:%M}\"",
        "---",
        "",
        f"# {domain.display_name or domain.name or domain.id} — 数据流",
        "",
        f"> 自动生成 | {len(tables)} 个表 | {total_columns} 个字段链路",
        "",
    ]

    for table_name in sorted(tables.keys()):
        columns = tables[table_name]
        lines.append(f"## {table_name}")
        lines.append("")

        for col_name in sorted(columns.keys()):
            entries = columns[col_name]
            lines.append(f"### {col_name}")
            lines.append("")
            lines.append("| API | DTO 字段 | Entity 字段 | 前端调用者 |")
            lines.append("|-----|----------|------------|-----------|")

            for e in entries[:5]:  # 每列最多 5 条链路
                callers = "; ".join(e.get("callers", [])[:3]) or "—"
                lines.append(
                    f"| `{e.get('api_url', '')}` | `{e.get('dto_field', '')}` | "
                    f"`{e.get('entity_field', '')}` | {callers} |"
                )
            lines.append("")

        lines.append("---")
        lines.append("")

    lines.append(f"*共 {len(tables)} 个表，{total_columns} 个字段*")

    (domain_dir / "data-flow.md").write_text("\n".join(lines) + "\n", encoding="utf-8")
```

---

## 10. dependencies.md 生成

### 10.1 内容模板

```markdown
# {domain.display_name} — 域间依赖

> {N} 个依赖域 | 强(>=50) 中(>=20) 弱(<20)

| 依赖域 | Import 次数 | 强度 |
|--------|:-----------:|:----:|
| [[库存管理]] | 85 | 强 |
| [[供应商管理]] | 32 | 中 |
| [[产品数据管理]] | 8 | 弱 |
```

### 10.2 强度标注算法

```python
def _dependency_strength(count: int) -> str:
    """根据 import 次数标注依赖强度。

    强度标准:
        >= 50: "强" — 两个域深度耦合，变更时需同步考虑
        >= 20: "中" — 中度依赖，存在明确调用关系
        < 20:  "弱" — 轻度引用，一般是枚举/常量/工具的引用
    
    Args:
        count: 域间 import 次数
        
    Returns:
        str: '强' / '中' / '弱'
    """
    if count >= 50:
        return "强"
    elif count >= 20:
        return "中"
    else:
        return "弱"
```

### 10.3 输出格式

```python
def _write_dependencies(domain: Domain, domain_dir: Path):
    deps = domain.dependencies
    total = len(deps)

    lines = [
        "---",
        f"domain: {domain.display_name or domain.name or domain.id}",
        f"dependencies: {total}",
        f"generated: \"{datetime.now():%Y-%m-%d %H:%M}\"",
        "---",
        "",
        f"# {domain.display_name or domain.name or domain.id} — 域间依赖",
        "",
    ]

    if not deps:
        lines.append("*该域无跨域依赖*")
        (domain_dir / "dependencies.md").write_text(
            "\n".join(lines) + "\n", encoding="utf-8"
        )
        return

    # 计算各强度级别的数量
    strong = sum(1 for c in deps if c.get("import_count", 0) >= 50)
    medium = sum(1 for c in deps if 20 <= c.get("import_count", 0) < 50)
    weak = sum(1 for c in deps if c.get("import_count", 0) < 20)

    lines.append(
        f"> {total} 个依赖域 | "
        f"强({strong}) 中({medium}) 弱({weak})"
    )
    lines.append("")
    lines.append("| 依赖域 | Import 次数 | 强度 |")
    lines.append("|--------|:-----------:|:----:|")

    # 按 import 次数降序排列
    for dep in sorted(deps, key=lambda x: -x.get("import_count", 0)):
        name = dep.get("domain", "")
        count = dep.get("import_count", 0)
        strength = _dependency_strength(count)
        dep_display = f"[[{_resolve_dep_name(name, domains)}]]"
        lines.append(f"| {dep_display} | {count} | {strength} |")

    (domain_dir / "dependencies.md").write_text(
        "\n".join(lines) + "\n", encoding="utf-8"
    )
```

---

## 11. 占位文件生成

### 11.1 设计原则

根据架构设计 [决策 5](../architecture/graph-wiki%20工程架构设计.md#决策-5rulesmd-和-specmd-不由-llm-生成)：

> rules.md 和 spec.md 记录的是业务需求层面的权威信息——业务规则来自产品文档、需求规格来自 PRD。LLM 对这些内容的"猜测"可能不准确，甚至产生误导。因此 Graph-Wiki 的定位是**提供框架，不填充内容**。

| 文件 | 用途 | 填写者 | 内容预期 |
|------|------|--------|---------|
| `rules.md` | 业务规则 | 人工 | 领域专家编写的业务约束、校验规则、状态流转 |
| `spec.md` | 需求规格 | 人工 | PRD 中的功能描述、验收条件 |
| `er-diagram.md` | ER 图 | 自动（Phase 4+） | Mermaid ER 图，数据库表关系 |

### 11.2 占位文件模板

```markdown
---
domain: "{domain_id}"
type: "placeholder"
created: "{YYYY-MM-DD}"
status: "待填写"
---

# {domain.display_name} — {TITLE}

> 本文档为自动生成的占位文件，等待人工填写。
> 请根据业务需求补充具体内容。

## 填写说明

1. {填写指引 1}
2. {填写指引 2}

---

*文件自动生成于 {YYYY-MM-DD}*
```

### 11.3 不覆盖保护机制

```python
def _write_placeholder_files(domain: Domain, domain_dir: Path):
    placeholders = {
        "rules.md": {
            "title": "业务规则",
            "hint": "由领域专家填写业务约束、校验规则、状态流转等",
        },
        "spec.md": {
            "title": "需求规格",
            "hint": "基于 PRD 补充详细功能描述、验收条件",
        },
        "er-diagram.md": {
            "title": "ER 图",
            "hint": "Phase 4+ 自动生成 Mermaid ER 图，当前为占位",
        },
    }

    for fname, info in placeholders.items():
        filepath = domain_dir / fname
        if filepath.exists():
            continue  # ★ 关键：不覆盖已有文件

        content = [
            "---",
            f'domain: "{domain.id}"',
            'type: "placeholder"',
            f'created: "{datetime.now():%Y-%m-%d}"',
            'status: "待填写"',
            "---",
            "",
            f"# {domain.display_name or domain.name or domain.id} — {info['title']}",
            "",
            "> 本文档为自动生成的占位文件，等待人工填写。",
            f"> {info['hint']}",
            "",
            "---",
            "",
            f"*文件自动生成于 {datetime.now():%Y-%m-%d}*",
            "",
        ]
        filepath.write_text("\n".join(content), encoding="utf-8")
```

### 11.4 保护机制边界条件

| 场景 | 行为 | 理由 |
|------|------|------|
| 首次 build，文件不存在 | 创建初始占位 | 让用户知道有这个文件 |
| 第二次 build，文件未编辑 | 跳过（已存在） | 不上次保留内容（即使是空占位） |
| 第二次 build，人工编辑过 | 跳过（已存在） | 保护人工编辑内容 |
| 第二次 build，用户删除了文件 | 重新创建占位 | 用户删除 = 想要重新开始 |
| `--update` 模式 | 跳过（同第二次 build） | 增量更新不重建人工文件 |

---

## 12. 错误处理

### 12.1 异常分类与处理策略

| 异常类型 | 触发条件 | 处理策略 |
|---------|---------|---------|
| `ValueError` | `domains` 为空列表 | 抛出异常，提示"至少需要一个业务域" |
| `OSError` (目录创建) | 父目录不存在、路径无效 | 抛出异常，携带详细路径信息 |
| `OSError` (权限) | `output_dir` 不可写 | 抛出异常，提示检查目录权限 |
| `OSError` (磁盘满) | 写入时磁盘空间不足 | 抛出异常，携带已写入的文件列表（部分写入） |
| `UnicodeEncodeError` | 域名含特殊字符导致路径无效 | sanitize 域名后再创建目录（见 12.3） |
| `FileNotFoundError` | 写入过程中父目录被删除 | 捕获后重试 mkdir，重试失败则抛出 |

### 12.2 入口函数错误处理实现

```python
def export_wiki(
    domains: list[Domain],
    api_matches: list[ApiMatch],
    field_map: dict,
    output_dir: Path = Path("wiki"),
) -> Path:
    """生成 Obsidian 兼容的 Wiki 文档树。"""

    # ── 输入校验 ──
    if not domains:
        raise ValueError(
            "export_wiki: domains 为空列表。至少需要一个业务域才能生成 Wiki。"
        )

    # ── 解析输出目录 ──
    try:
        output_dir = _resolve_output_dir(output_dir)
    except OSError as e:
        raise OSError(
            f"export_wiki: 无法创建输出目录 '{output_dir}': {e}"
        ) from e

    # ── 生成根级索引 ──
    _write_index(domains, output_dir)

    if api_matches:
        _write_api_index(api_matches, domains, output_dir)

    # ── 遍历每个域生成域内文件 ──
    written_files: list[Path] = []
    for domain in domains:
        dir_name = domain.display_name or domain.name or domain.id

        # 域名 sanitize（去除路径非法字符）
        dir_name = _sanitize_dir_name(dir_name)

        domain_dir = output_dir / dir_name

        try:
            domain_dir.mkdir(parents=True, exist_ok=True)
        except OSError as e:
            raise OSError(
                f"export_wiki: 无法创建域目录 '{domain_dir}' (域名: {dir_name}): {e}"
            ) from e

        # 写入各文件
        try:
            _write_code_map(domain, domain_dir)
            written_files.append(domain_dir / "code-map.md")

            _write_api_docs(domain, api_matches, domain_dir)
            written_files.append(domain_dir / "api-docs.md")

            _write_data_flow(domain, field_map, domain_dir)
            written_files.append(domain_dir / "data-flow.md")

            _write_dependencies(domain, domain_dir)
            written_files.append(domain_dir / "dependencies.md")

            _write_placeholder_files(domain, domain_dir)
            # 占位文件可能因已存在而未写入，不加入 written_files
        except OSError as e:
            raise OSError(
                f"export_wiki: 写入域 '{dir_name}' 文件时出错 "
                f"(已成功写入 {len(written_files)} 个文件): {e}"
            ) from e

    return output_dir.resolve()
```

### 12.3 域名 sanitize

```python
import re

def _sanitize_dir_name(name: str) -> str:
    """去除目录名中的路径非法字符。

    Windows 不允许的字符: \ / : * ? " < > |
    Unix 不允许的字符: / 和空字符

    处理策略:
        - 将非法字符替换为下划线
        - 去除首尾空白
        - 确保名称不为空（回退为 "unnamed"）
    """
    if not name or not name.strip():
        return "unnamed"

    # 替换 Windows 和 Unix 路径非法字符
    sanitized = re.sub(r'[\\/:*?"<>|]', "_", name.strip())

    # 如果全部被替换了，回退
    if not sanitized:
        return "unnamed"

    return sanitized
```

### 12.4 部分写入的恢复策略

如果写入过程中发生错误（如磁盘满），已经写入的文件不会自动回滚。原因是：

1. **幂等重新执行**：用户修复问题后重新运行 `graph-wiki build`，export 会覆盖所有自动生成的文件（人工文件有保护）
2. **人工文件安全**：占位文件有 `filepath.exists()` 检查，不会因重试而覆盖
3. **最小意外原则**：不自动删除文件，避免在临界状态下丢失数据

---

## 13. Wiki 输出质量验收

`export_wiki()` 的成功只代表文件写入成功，不代表 Wiki 已经具备业务导航价值。v1.0 必须通过 `evaluate_wiki_quality()` 输出结构化质量结果，由 pipeline 写入 `build-report.json`。

### 13.1 验收指标

| 指标 | 小项目 | 500 文件级项目 | 失败/警告含义 |
|------|:--:|:--:|------|
| build 成功 | 必须 | 必须 | 否则无 Wiki 可用 |
| API 未分类比例 | < 30% | < 20% | API 文档无法按业务域导航 |
| 技术域名比例 | < 50% | < 30% | 域划分仍停留在目录/技术层 |
| 每个核心域有 code-map | 必须 | 必须 | 无法从业务域定位代码 |
| 每个核心域有 summary 或待标注提示 | 必须 | 必须 | 人类解释层缺失 |
| 单域业务点上限 | < 150 | < 300 | 域过粗或业务点过滤不足 |
| 生成 build-report.json | 必须 | 必须 | 无法自动回归 |

### 13.2 技术域名判定

```python
TECHNICAL_DOMAIN_NAMES = {
    "controller", "controllers",
    "service", "services", "service-impl",
    "mapper", "dao",
    "common", "utils", "util", "components",
    "api", "web", "config", "handler",
    "v1", "v2", "v3", "v4", "v5",
}
```

判定规则：

- 命中 `TECHNICAL_DOMAIN_NAMES` → 计入 `technical_name_ratio`
- 纯业务缩写如 `svn`、`repo` → 计为 warning，不直接 fatal
- 已有 `display_name` 且不是技术层名 → 不计入技术域名

### 13.3 质量状态

| 状态 | 条件 |
|------|------|
| `passed` | 必须产物完整，API 未分类比例和技术域名比例通过 |
| `warning` | build 成功，但存在可接受问题，如 summary 缺失、业务缩写未标注 |
| `failed` | build 成功但不适合交付，如 API 大量未分类、无业务点、核心域缺少 code-map |

### 13.4 输出示例

```json
{
  "domains": {
    "count": 1,
    "business_points": 77,
    "technical_name_ratio": 1.0,
    "max_domain_business_points": 77
  },
  "api": {
    "total": 46,
    "uncategorized": 46,
    "uncategorized_ratio": 1.0
  },
  "quality": {
    "status": "failed",
    "issues": [
      "api_uncategorized_ratio_too_high",
      "domain_name_is_technical"
    ]
  }
}
```

---

## 14. 测试用例

### 14.1 测试基本 Wiki 生成

```python
def test_export_wiki_basic(tmp_path):
    """验证基本 export 流程：3 个域 + 2 个 API 匹配 + field_map。

    预期:
        - index.md 存在且包含 3 个域条目
        - api-index.md 存在
        - 每个域的 code-map.md 存在
        - 每个域的 dependencies.md 存在
        - 有 ApiMatch 的域生成 api-docs.md
        - 有 field_map 的域生成 data-flow.md
        - rules.md / spec.md 占位文件首次生成
    """
    from graph_wiki.models import Domain, BusinessPoint, ApiMatch, FrontendApiCall, BackendEndpoint
    from graph_wiki.export import export_wiki

    # 准备测试数据
    domains = [
        Domain(
            id="purchase",
            name="purchase",
            display_name="采购管理",
            anchors={
                "controller": [{"id": "n1", "label": "PurchaseController.java", "_role": "controller"}],
                "service_impl": [{"id": "n2", "label": "PurchaseServiceImpl.java", "_role": "service_impl"}],
            },
            dependencies=[{"domain": "inventory", "import_count": 45}, {"domain": "supplier", "import_count": 12}],
            total_files=30,
        ),
        Domain(
            id="inventory",
            name="inventory",
            display_name="库存管理",
            anchors={
                "controller": [{"id": "n3", "label": "InventoryController.java", "_role": "controller"}],
            },
            dependencies=[{"domain": "purchase", "import_count": 30}],
            total_files=15,
        ),
        Domain(
            id="bin-data",
            name="bin-data",
            display_name="Bin数据管理",
            anchors={
                "controller": [{"id": "n4", "label": "BinController.java", "_role": "controller"}],
                "service_impl": [{"id": "n5", "label": "BinDataServiceImpl.java", "_role": "service_impl"}],
                "mapper": [{"id": "n6", "label": "BinDataMapper.java", "_role": "mapper"}],
            },
            business_points=[
                BusinessPoint(name="importBindata", display_name="Bin数据导入"),
                BusinessPoint(name="listBinOrderDetail", display_name="Bin订单明细查询"),
            ],
            dependencies=[{"domain": "product", "import_count": 5}],
            total_files=28,
        ),
    ]

    # 准备 API 匹配数据 (2 个匹配，分属 purchase 和 bin-data)
    frontend_po = FrontendApiCall(
        function_name="createPurchaseOrder",
        http_method="POST",
        url="/api/purchase/order/create",
        source_file="src/api/purchase.js",
        callers=[{"page": "purchase/create.vue", "fields_used": ["supplierCode"]}],
    )
    backend_po = BackendEndpoint(
        controller_class="PurchaseController",
        method_name="createPurchaseOrder",
        http_method="POST",
        url="/api/purchase/order/create",
    )
    frontend_bin = FrontendApiCall(
        function_name="importBindata",
        http_method="POST",
        url="/api/bindata/import",
        source_file="src/api/bindata.js",
        callers=[{"page": "bin/import.vue", "fields_used": ["supplierCode"]}],
    )
    backend_bin = BackendEndpoint(
        controller_class="BinDataController",
        method_name="importBindata",
        http_method="POST",
        url="/api/bindata/import",
    )

    api_matches = [
        ApiMatch(id="api-create-po", frontend=frontend_po, backend=backend_po,
                 match_confidence=1.0, domain="purchase"),
        ApiMatch(id="api-import-bin", frontend=frontend_bin, backend=backend_bin,
                 match_confidence=1.0, domain="bin-data"),
    ]

    # 准备 field_map（仅 bin-data 有数据流）
    field_map = {
        "bin-data": {
            "bin_data_apply": {
                "supplier_code": [
                    {
                        "api_url": "POST /api/bindata/import",
                        "dto_field": "supplierCode",
                        "entity_field": "supplierCode",
                        "callers": ["bin/import.vue"],
                        "confidence": 1.0,
                    }
                ]
            }
        }
    }

    # 执行 export
    output_dir = export_wiki(domains, api_matches, field_map, output_dir=tmp_path)

    # 验证根级文件
    assert (output_dir / "index.md").exists()
    assert (output_dir / "api-index.md").exists()

    # 验证每个域的文件
    for d in domains:
        domain_dir = output_dir / d.display_name
        assert domain_dir.exists(), f"域目录 {domain_dir} 不存在"

        # 自动生成的文件
        assert (domain_dir / "code-map.md").exists()
        assert (domain_dir / "dependencies.md").exists()

        # 占位文件
        assert (domain_dir / "rules.md").exists()
        assert (domain_dir / "spec.md").exists()
        assert (domain_dir / "er-diagram.md").exists()

        # api-docs.md: 只有有 ApiMatch 的域才生成
        if d.id == "purchase" or d.id == "bin-data":
            assert (domain_dir / "api-docs.md").exists()
        else:
            assert not (domain_dir / "api-docs.md").exists()

    # data-flow.md: 只有 bin-data 有
    assert (output_dir / "Bin数据管理" / "data-flow.md").exists()
    assert not (output_dir / "采购管理" / "data-flow.md").exists()
    assert not (output_dir / "库存管理" / "data-flow.md").exists()
```

### 14.2 测试幂等性

```python
def test_export_wiki_idempotent(tmp_path):
    """验证多次 export 产生相同输出（幂等性）。

    预期:
        - 第 1 次和第 2 次 export 产出的所有自动生成文件内容相同
        - 人工编辑 rules.md 后，第 3 次 export 不覆盖 rules.md
    """
    from graph_wiki.models import Domain
    from graph_wiki.export import export_wiki

    domains = [
        Domain(
            id="test-domain",
            name="test-domain",
            display_name="测试域",
            anchors={"controller": [{"id": "n1", "label": "TestController.java", "_role": "controller"}]},
            dependencies=[{"domain": "other", "import_count": 5}],
            total_files=5,
        ),
    ]

    # 第一次 export
    out1 = export_wiki(domains, [], {}, output_dir=tmp_path / "wiki1")

    # 第二次 export（不同输出目录）
    out2 = export_wiki(domains, [], {}, output_dir=tmp_path / "wiki2")

    # 比较自动生成的文件
    auto_files = ["index.md", "code-map.md", "dependencies.md"]
    for fname in auto_files:
        f1 = (out1 / "测试域" / fname).read_text(encoding="utf-8") if fname != "index.md" \
             else (out1 / fname).read_text(encoding="utf-8")
        f2 = (out2 / "测试域" / fname).read_text(encoding="utf-8") if fname != "index.md" \
             else (out2 / fname).read_text(encoding="utf-8")
        assert f1 == f2, f"{fname} 两次输出不一致"

    # 模拟人工编辑 rules.md
    rules_path = out1 / "测试域" / "rules.md"
    rules_path.write_text("# 人工编辑的业务规则\n\n1. 这是一个测试规则\n", encoding="utf-8")

    # 第三次 export（到相同目录）
    export_wiki(domains, [], {}, output_dir=out1)

    # 验证 rules.md 未覆盖
    assert rules_path.read_text(encoding="utf-8") == "# 人工编辑的业务规则\n\n1. 这是一个测试规则\n"

    # 验证 code-map.md 仍然覆盖（自动生成文件）
    # （此处 filepath 不变，内容应一致）
    assert (out1 / "测试域" / "code-map.md").exists()
```

### 14.3 测试空 ApiMatch 和空 field_map

```python
def test_export_empty_api_and_field_map(tmp_path):
    """验证无 API 匹配和无字段映射时的降级行为。

    场景:
        - api_matches 为空列表
        - field_map 为空 dict

    预期:
        - api-index.md 不生成（无 API 匹配）
        - 各域内不生成 api-docs.md
        - 各域内不生成 data-flow.md
        - 其他文件正常生成
    """
    from graph_wiki.models import Domain
    from graph_wiki.export import export_wiki

    domains = [
        Domain(
            id="test",
            display_name="测试域",
            anchors={"controller": [{"id": "n1", "label": "TestController.java", "_role": "controller"}]},
            total_files=3,
        ),
    ]

    output_dir = export_wiki(domains, [], {}, output_dir=tmp_path)

    # api-index.md 不生成
    assert not (output_dir / "api-index.md").exists()

    # api-docs.md 不生成
    assert not (output_dir / "测试域" / "api-docs.md").exists()

    # data-flow.md 不生成
    assert not (output_dir / "测试域" / "data-flow.md").exists()

    # 核心文件照常生成
    assert (output_dir / "index.md").exists()
    assert (output_dir / "测试域" / "code-map.md").exists()
    assert (output_dir / "测试域" / "dependencies.md").exists()
    assert (output_dir / "测试域" / "rules.md").exists()
    assert (output_dir / "测试域" / "spec.md").exists()
```

### 14.4 测试 index.md 域名 [[双向链接]]

```python
def test_index_bidirectional_links(tmp_path):
    """验证 index.md 中的域名使用 [[双向链接]] 语法。

    预期:
        - 表格中的"业务域"列每项为 [[域名]] 格式
        - 依赖域列每项为 [[域名]] 格式
        - 域名使用 display_name（优先）或 name 或 id
        - 域名排序按锚点数降序
    """
    from graph_wiki.models import Domain
    from graph_wiki.export import export_wiki

    domains = [
        Domain(
            id="purchase",
            display_name="采购管理",
            anchors={
                "controller": [{"id": "n1", "_role": "controller"}, {"id": "n2", "_role": "controller"}],
                "service_impl": [{"id": "n3", "_role": "service_impl"}],
            },
            dependencies=[{"domain": "inventory", "import_count": 45}],
            total_files=30,
        ),
        Domain(
            id="inventory",
            display_name="库存管理",
            anchors={
                "controller": [{"id": "n4", "_role": "controller"}],
            },
            dependencies=[{"domain": "purchase", "import_count": 30}],
            total_files=15,
        ),
    ]

    output_dir = export_wiki(domains, [], {}, output_dir=tmp_path)
    content = (output_dir / "index.md").read_text(encoding="utf-8")

    # 验证双向链接语法
    assert "[[采购管理]]" in content, "域目录应包含 [[采购管理]] 双向链接"
    assert "[[库存管理]]" in content, "域目录应包含 [[库存管理]] 双向链接"

    # 验证排名：采购管理（3 锚点）在库存管理（1 锚点）前面
    purchase_idx = content.index("采购管理")
    inventory_idx = content.index("库存管理")
    assert purchase_idx < inventory_idx, "锚点多的域应排前面"

    # 验证域名显示而非 ID
    assert "[[purchase]]" not in content, "不应使用 ID 作为链接显示文本"
    assert "[[inventory]]" not in content, "不应使用 ID 作为链接显示文本"
```

### 14.5 测试错误处理

```python
def test_export_error_handling(tmp_path):
    """验证各类错误场景的异常处理。

    场景:
        1. 空 domains 列表 → ValueError
        2. 域名含非法字符 → sanitize 后仍可创建目录
        3. 不可写输出目录 → OSError
    """
    from graph_wiki.models import Domain
    from graph_wiki.export import export_wiki

    # 场景 1: 空 domains 列表
    with pytest.raises(ValueError, match="至少需要一个业务域"):
        export_wiki([], [], {}, output_dir=tmp_path / "empty")

    # 场景 2: 域名含非法字符
    domains = [
        Domain(
            id="bad/name:test",
            display_name='非法:域名/测试',
            anchors={"controller": [{"id": "n1", "label": "Test.java", "_role": "controller"}]},
            total_files=1,
        ),
    ]
    # 不应抛出异常，非法字符应被替换为下划线
    output_dir = export_wiki(domains, [], {}, output_dir=tmp_path / "sanitize")
    # 验证目录名被 sanitize 了
    dirs = list(output_dir.iterdir())
    assert len(dirs) == 1
    dir_name = dirs[0].name
    assert "/" not in dir_name, f"目录名不应含斜杠: {dir_name}"
    assert ":" not in dir_name, f"目录名不应含冒号: {dir_name}"

    # 场景 3: 不可写输出目录（权限不足）
    # 在 tmp_path 下创建一个只读目录
    import os
    import stat
    readonly_dir = tmp_path / "readonly"
    readonly_dir.mkdir()
    # 注意：在 Windows 上，权限模型不同，os.chmod 的效果有限
    # 此处为 Unix/Linux 场景测试逻辑
    if os.name != "nt":  # 非 Windows
        os.chmod(readonly_dir, stat.S_IRUSR | stat.S_IXUSR)  # 去掉写权限
        try:
            with pytest.raises((OSError, PermissionError)):
                export_wiki(domains, [], {}, output_dir=readonly_dir / "nested")
        finally:
            os.chmod(readonly_dir, stat.S_IRWXU)  # 恢复权限
```

---

## 15. 增量更新影响

### 15.1 增量更新中各文件的处理

| 文件 | 全量 build | `--update`（无文件变更） | `--update`（有文件变更） |
|------|:----------:|:-----------------------:|:-----------------------:|
| `index.md` | 全新生成 | 跳过（无变更） | 重新生成 |
| `api-index.md` | 全新生成 | 跳过 | 重新生成（若 api_mapper 重跑） |
| `code-map.md` | 全新生成 | 跳过 | 重新生成 |
| `api-docs.md` | 全新生成 | 跳过 | 重新生成（若 api_mapper 重跑） |
| `data-flow.md` | 全新生成 | 跳过 | 重新生成（若 field_mapper 重跑） |
| `dependencies.md` | 全新生成 | 跳过 | 重新生成 |
| `summary.md` | 不由 export 生成 | 同上 | 同上 |
| `rules.md` | 仅首次创建 | 不创建（已存在） | 不覆盖 |
| `spec.md` | 仅首次创建 | 不创建（已存在） | 不覆盖 |
| `er-diagram.md` | 仅首次创建 | 不创建（已存在） | 不覆盖 |

### 15.2 增量更新的复杂度

增量更新在 pipeline 层面控制，export 本身不感知"增量"，而是保持"全量生成但保护人工文件"的策略。pipeline 判断哪些模块需要重新执行，然后将新数据完整传入 export。

---

## 16. 变更记录

| 日期 | 变更内容 | 原因 |
|------|---------|------|
| 2026-06-15 | 初始版本 | 基于架构设计文档 v1.0 §4.4、§6 和现有代码实现 |

