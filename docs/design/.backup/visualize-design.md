# Visualize 模块详细设计

> 对应总体设计：[第四章 4.5 节](../architecture/graph-wiki%20架构设计.md#45-可视化模块-visualize)
> 状态：详细设计阶段（v1.0，根据实际代码实现更新）
> 依赖：`models.py`（数据模型：`Domain`）
> 参考：`models-design.md`（`Domain` 类型定义）、`cluster-design.md`（上游模块输出的域结构）

---

## 1. 模块职责

### 1.1 核心职责

将聚类模块产出的业务域（~25 个）及其依赖关系渲染为 **交互式 D3.js 力导向图**，提供项目架构的宏观鸟瞰视图。每个节点为一个业务域，节点大小为域内锚点数，连线粗细为域间 import 依赖强度。

```
输入:  list[Domain] (~25 个域, 每域含 anchors_count, dependencies)
       domain.name(或 domain.id) 作为节点标签

输出:  domain_graph.html (单文件 HTML，内联 CSS + CDN D3 + 内联 JSON 数据)
       视觉呈现:
         ├── 节点: 圆形, 半径 = √(锚点数) × 6, 颜色 = Tableau10 色板
         ├── 连线: 有向箭头, 宽度 = import 次数 / 200, 透明度 = import 次数 / 200 + 0.1
         ├── 交互: 拖拽固定 / 缩放 (0.15x-5x) / 悬浮 tooltip
         └── Phase 4+: 点击节点展开域内 Controller→Service→Mapper 链
```

### 1.2 与 Graphify 全图毛球可视化的对比

Graphify 的 graph.html 使用 D3 渲染全量图（~34K 节点），在所有方面均不符合人类可读要求。Graph-Wiki 的域级可视化是**针对人类读者的设计**：

| 维度 | Graphify (全图毛球) | Graph-Wiki (域级可视化) |
|------|-------------------|----------------------|
| **节点数量** | ~34,000（方法/字段/类） | ~25（业务域） |
| **节点含义** | 方法名 + 类名混合，无业务语义 | 业务域（有 `display_name` 和 `description`） |
| **边数量** | ~89,000（34 类关系混合） | ~200-600（仅域间 import 依赖） |
| **视觉可用性** | ❌ 无法交互，不可读 | ✅ 力导向图清晰展示域间关系 |
| **人类价值** | 低（"这图有什么用？"） | 高（"哪几个域耦合最严重？"） |
| **Agent 价值** | 低（34K 节点需要多次 API 调用） | 高（~25 节点，一眼可见架构） |
| **技术实现** | D3 力导向 + 毛球聚类 | D3 力导向 + 业务域着色 |
| **可交互性** | 仅缩放/拖拽 | 拖拽固定 + 缩放 + 悬浮 tooltip + 展开下钻(Phase 4+) |

### 1.3 解决的问题

| 问题 | 说明 |
|------|------|
| "这个项目有哪些业务域？每个域多大？" | 节点大小 = 锚点数量，一目了然 |
| "哪些域之间有依赖关系？依赖强度如何？" | 连线粗细 = import 次数，直观展示耦合度 |
| "哪个域是项目的核心枢纽？" | 连线最多的节点即为核心域（入边 + 出边最多） |
| "有没有孤立的域（无依赖）？" | 没有连线的节点即为孤立域 |
| "这些域之间的依赖方向是什么？" | 箭头指示依赖方向，A → B 表示 A import B |
| "点击一个域能看到什么？" | Phase 4+: 展开域内部结构（Controller → Service → Mapper 链） |

### 1.4 模块定位

```
上游: cluster.py → list[Domain] (含 anchors, dependencies, business_points)
       label.py  → Domain.name / Domain.display_name (LLM 标注后)
                      │
                      ▼
               visualize.py  ← 本模块
                      │
                      ▼
               domain_graph.html (独立 HTML 可视化)
```

本模块只消费 `Domain` 数据类中的以下字段：

| 字段 | 用途 |
|------|------|
| `Domain.name` 或 `Domain.id` | 节点标签（LLM 标注后优先使用 `name`，未标注使用 `id`） |
| `Domain.display_name` | tooltip 中显示的中文域名 |
| `Domain.anchors_count()` | 节点大小计算依据 |
| `Domain.anchors` | 节点角色分布信息（tooltip 中展示各角色锚点数） |
| `Domain.total_files` | tooltip 中展示的文件总数 |
| `Domain.dependencies` | 连线数据：{target_domain_id: import_count} |

---

## 2. 接口定义

### 2.1 主接口

```python
def export_domain_html(
    domains: list[Domain],
    output_path: Path = Path("domain_graph.html"),
) -> Path:
    """
    生成 D3.js 域级交互可视化 HTML。

    三步骤:
      1. 从 domains 构建节点数据 (domain_data) 和连线数据 (links)
      2. 将节点/连线数据 JSON 序列化
      3. 渲染完整 HTML（内联 CSS + D3 CDN CDN + 内联 JS + JSON 数据）

    参数:
        domains: cluster.py 产出的 Domain 列表（含 dependencies）
        output_path: 输出 HTML 文件路径，默认 "domain_graph.html"

    返回:
        Path: 生成的 HTML 文件路径

    异常:
        本函数不抛出异常 —— 空 domains 生成空白图，无依赖数据生成无连线节点
    """
```

**参数说明**：

| 参数 | 类型 | 含义 |
|------|------|------|
| `domains` | `list[Domain]` | 聚类模块输出的业务域列表。必须包含 `dependencies` 和 `anchors`。标注后调用时 `name` 字段也已填充 |
| `output_path` | `Path` | 输出文件路径。默认在当前目录生成 `domain_graph.html`。可指定为 `wiki/domain-graph.html` 放入 Wiki 目录 |

**返回值**：`Path` — 生成的 HTML 文件的绝对路径。调用者（pipeline）可将此路径写入日志或自动打开浏览器。

**异常**：

| 异常类 | 抛出条件 |
|--------|---------|
| 无（设计原则：空域列表或缺少依赖时生成空白图/弱信息图，不中断流水线） |

### 2.2 内部子函数签名

```python
def _build_domain_nodes(domains: list[Domain]) -> list[dict]
    # Step 1: 构建节点数据
    # 输入: list[Domain]
    # 输出: list[dict] 每个 dict 代表一个域节点:
    #     {
    #         "name": "bin-data",              # 节点 ID（用于连线 source/target 引用）
    #         "display_name": "Bin数据管理",    # 中文标签（用于显示）
    #         "anchors": 12,                    # 锚点数（用于计算节点半径）
    #         "total_nodes": 28,                # 总文件数（用于 tooltip）
    #         "roles": {"controller": 3, "service_impl": 5, "mapper": 2},  # 角色分布
    #         "description": "管理仓库中所有物理物料...",  # 业务描述（用于 tooltip）
    #     }

def _build_domain_links(domains: list[Domain]) -> list[dict]
    # Step 2: 构建连线数据
    # 输入: list[Domain]
    # 输出: list[dict] 依赖连线:
    #     {
    #         "source": "bin-data",       # 源域 ID（引用 node.name）
    #         "target": "inventory",       # 目标域 ID
    #         "weight": 42,                # import 次数（用于连线粗细/透明度）
    #     }

def _render_html(domains: list[dict], links: list[dict]) -> str
    # Step 3: 渲染完整 HTML 字符串
    # 输入: 节点列表 + 连线列表
    # 输出: HTML 字符串（内联 CSS + D3 CDN + 内联 JS + JSON 数据）
```

### 2.3 辅助函数

```python
def _count_roles(domain: Domain) -> dict[str, int]
    """
    统计域内各角色的锚点数量。
    
    从 Domain.anchors 字典中计算每个角色（controller/service_impl/mapper 等）的锚点数。
    用于 tooltip 中展示域的内部组成。
    
    示例:
        输入 domain.anchors = {
            "controller": [{...}, {...}],
            "service_impl": [{...}, {...}, {...}],
            "mapper": [{...}],
        }
        输出: {"controller": 2, "service_impl": 3, "mapper": 1}
    """

def _hex_to_rgba(hex_color: str, alpha: float) -> str
    """
    十六进制颜色转 rgba 字符串。
    
    用于节点填充颜色的透明度处理（默认 opacity=0.85）。
    
    示例:
        _hex_to_rgba("#4E79A7", 0.85) → "rgba(78,121,167,0.85)"
    """
```

### 2.4 与 pipeline 的集成

```python
# pipeline.py 中调用 visualize 的位置
from graph_wiki.visualize import export_domain_html

def run_pipeline(config):
    # ... 前序步骤 ...
    domains = business_cluster(G, root_path)   # Step 4: 聚类
    domains = label_domains(domains, ...)       # Step 5: 标注（可选）
    
    # Step 6: 导出（并行）
    export_wiki(domains, ...)                   # Wiki 导出
    export_domain_html(                          # 可视化导出
        domains=domains,
        output_path=config.output_dir / "domain_graph.html",
    )
```

---

## 3. D3.js 力导向图配置

### 3.1 整体配置

```javascript
const W = window.innerWidth;
const H = window.innerHeight;

const sim = d3.forceSimulation(domains)
    .force("link", d3.forceLink(links).id(d => d.name).distance(180))
    .force("charge", d3.forceManyBody().strength(-400))
    .force("center", d3.forceCenter(W / 2, H / 2))
    .force("collision", d3.forceCollide().radius(d => Math.sqrt(d.anchors) * 8 + 25));
```

### 3.2 各 force 参数详解

#### `forceLink` — 连线弹簧力

| 参数 | 值 | 含义 |
|------|:--:|------|
| `id` | `d => d.name` | 节点标识符，连线 `source`/`target` 引用 `name` 字段 |
| `distance` | `180` | 连线理想长度（像素）。当两个域之间有依赖时，互相期望相距 ~180px |
| `iterations` | 默认 (1) | 每 tick 松弛次数。值越大收敛越快，但可能不稳定 |

**为什么 distance=180**：~25 个节点在 1920×1080 屏幕上期望均匀分布，180px 边距使图既能展示全部节点又不显得松散。节点过多（50+）时可考虑缩小到 120-150。

#### `forceManyBody` — 电荷力（排斥）

| 参数 | 值 | 含义 |
|------|:--:|------|
| `strength` | `-400` | 节点间排斥强度。数值越大（绝对值），节点间距离越大 |
| `theta` | 默认 (0.9) | Barnes-Hut 近似阈值。值越小精度越高、性能越差 |

**为什么 strength=-400**：需要平衡排斥力和连线的拉力。值太小（如 -100）无法将无依赖的域弹开，值太大（如 -1000）会导致图过度扩散。25 个节点时 -400 是经验值。

**与其他配置的协调**：
```
节点半径范围:     ~8px (1 个锚点) ~40px (45 个锚点)
连线理想距离:     180px
电荷力强度:       -400
碰撞检测半径:     radius+25px padding

→ 每个域节点之间至少保持 8+25 = 33px 间距（最小节点）
→ 最大节点约 40+25 = 65px 间距
→ 连线力将依赖域拉到 ~180px 距离
→ 排斥力确保无依赖的域不会漂移太近
```

#### `forceCenter` — 中心引力

| 参数 | 值 | 含义 |
|------|:--:|------|
| `x` | `W / 2` | 中心点 x 坐标（窗口宽度的一半） |
| `y` | `H / 2` | 中心点 y 坐标（窗口高度的一半） |
| `strength` | 默认 (0.1) | 中心引力强度。值越小节点越容易偏离中心 |

`forceCenter` 确保整个图在窗口中居中显示。当用户缩放或拖拽后，图的中心会偏移，但算法不会自动重置——这是 D3 的预期行为。

#### `forceCollide` — 碰撞检测

| 参数 | 值 | 含义 |
|------|:--:|------|
| `radius` | `d => Math.sqrt(d.anchors) * 8 + 25` | 每个节点的碰撞半径（含 padding） |
| `strength` | 默认 (0.7) | 碰撞排斥强度 |
| `iterations` | 默认 (1) | 每 tick 碰撞解析次数 |

**为什么 radius 公式 = `√(anchors) × 8 + 25`**：
- `√(anchors) × 8` 是节点视觉半径（见第 4 章节点大小公式的 1.33 倍，留有余量）
- `+ 25` 是固定 padding，确保节点之间至少有 25px 的文本空间
- 节点被拖拽固定后，碰撞检测仍然生效，防止其他节点穿过固定节点

### 3.3 力模拟生命周期

```javascript
// 启动
sim.alpha(1).restart();     // alpha=1 表示高能量状态，节点活跃运动

// 拖拽时保持活性
.on("start", (e, d) => {
    if (!e.active) sim.alphaTarget(0.3).restart();
    d.fx = d.x;              // 固定节点当前位置
    d.fy = d.y;
})
.on("drag", (e, d) => {
    d.fx = e.x;              // 跟随鼠标
    d.fy = e.y;
})
.on("end", (e, d) => {
    if (!e.active) sim.alphaTarget(0);
    d.fx = null;             // 释放固定
    d.fy = null;
})

// 降温过程（alpha 衰减）
// alpha_min = 0.001, alpha_decay = 0.0228（默认值）
// 每次 tick: alpha += (alpha_target - alpha) * alpha_decay
// 约 300 tick 后 alpha < alpha_min → 自动停止
// 约 5-8 秒后图稳定（取决于节点数量和边密度）

// 稳定后
sim.on("end", () => {
    // 图已稳定，可输出最终布局信息
});
```

---

## 4. 可视化元素设计

### 4.1 节点（圆形）

每个业务域渲染为一个圆形节点。

**节点半径公式**：

```
radius = max(8, √(anchors) × 6)

说明:
  · min radius = 8px（当 anchors = 1 时: √1 × 6 = 6 < 8 → 取 8）
  · max radius 无硬上限，取决于域规模
  · anchors = 12 时: √12 × 6 ≈ 20.8px
  · anchors = 25 时: √25 × 6 = 30px
  · anchors = 45 时: √45 × 6 ≈ 40.2px

设计理由:
  · 使用 √(anchors) 而非线性: 锚点数差异可达 50 倍(1-50)，平方根将视觉差异压缩到 7 倍
  · √(25) / √(1) = 5 倍视觉差，一眼可区分"大域"和"小域"
  · 设 min=8 防止文字碰撞图中 1 个锚点的域
```

**节点颜色**：`d3.scaleOrdinal(d3.schemeTableau10)`

Tableau10 色板（10 种分类色，足够覆盖 ~25 个域的颜色需求）：

| 索引 | 色名 | 十六进制 | RGB | 用途（示例域类型） |
|:----:|------|:--------:|-----|-------------------|
| 0 | Blue | `#4E79A7` | (78,121,167) | 数据管理类域 |
| 1 | Orange | `#F28E2B` | (242,142,43) | 业务交易类域 |
| 2 | Red | `#E15759` | (225,87,89) | 核心流程类域 |
| 3 | Teal | `#76B7B2` | (118,183,178) | 配置/基础服务类域 |
| 4 | Green | `#59A14F` | (89,161,79) | 单据/审批类域 |
| 5 | Yellow | `#EDC948` | (237,201,72) | 报告/统计类域 |
| 6 | Purple | `#B07AA1` | (176,122,161) | 用户/权限类域 |
| 7 | Pink | `#FF9DA7` | (255,157,167) | 通知/消息类域 |
| 8 | Brown | `#9C755F` | (156,117,95) | 外部集成类域 |
| 9 | Grey | `#BAB0AC` | (186,176,172) | 工具/公共类域 |

**颜色分配策略**：由 `d3.scaleOrdinal()` 自动根据节点名映射。相同的域名始终得到相同的颜色（确定性映射），跨次运行视觉一致。

**节点填充透明度**：`0.85`（略微透明以显示底层连线，同时保持颜色辨识度）

**节点描边**：`stroke: #fff; stroke-width: 2.5px`（白色描边增强与背景的对比）

```
节点渲染代码 (D3):
─────────────────
node.append("circle")
    .attr("r", d => Math.max(8, Math.sqrt(d.anchors) * 6))
    .attr("fill", d => colors(d.name))
    .attr("opacity", 0.85);
```

**节点标签文字**：

```
文字位置: 节点右侧水平偏移 (radius + 10px)
文字大小: min(15, max(9, d.anchors / 3 + 7))  → 锚点越多，字号越大
           例: anchors=1 → max(9, 7.3) = 9px
               anchors=12 → max(9, 11) → min(15, 11) = 11px
               anchors=30 → max(9, 17) → min(15, 17) = 15px
文字对齐: text-anchor="start"（左对齐）
文字颜色: inherit（继承 SVG 默认黑色）
文字粗细: 500 (medium)
文字内容: d.display_name || d.name（优先中文名，回退英文 ID）
```

### 4.2 连线（有向箭头）

每个域间依赖关系渲染为一条有向箭头连线。

**连线宽度公式**：

```
stroke-width = max(0.3, min(10, weight / 200))

说明:
  · min stroke-width = 0.3px（当 weight = 1 时）
  · max stroke-width = 10px（当 weight = 2000 时硬上限）
  · weight = 42 时: 42/200 = 0.21px → max(0.3, 0.21) = 0.3px（低于阈值取最小值）
  · weight = 200 时: 200/200 = 1.0px
  · weight = 1000 时: 1000/200 = 5.0px

设计理由:
  · 除 200 将 import 次数(典型值 1-2000)映射到 0.005-10 的视觉范围
  · import 次数 = 42 时仅 0.21px 太细，故设 min=0.3 确保可见
  · 设 max=10 防止极端情况（如公共工具域被数千次 import）导致过粗
```

**连线透明度公式**：

```
stroke-opacity = min(0.6, weight / 200 + 0.1)

说明:
  · max opacity = 0.6（防止连线遮挡节点）
  · weight = 42 时: 42/200 + 0.1 = 0.31
  · weight = 100 时: 100/200 + 0.1 = 0.6（达上限）
  · weight = 0 时: 0 + 0.1 = 0.1（最低透明度，基本不可见）

设计理由:
  · 透明度+宽度双重编码，即使连接线很细也能通过透明度感知强度
  · 最高 0.6 透明度确保多条连线重叠时仍可辨识
```

**连线颜色**：`#bbb`（浅灰色，中性的依赖连线，不抢夺节点注意力）

**箭头定义**：

```javascript
// SVG marker 定义
svg.append("defs").selectAll("marker")
    .data(["arrow"]).join("marker")
    .attr("id", "arrow")
    .attr("viewBox", "0 -5 10 10")
    .attr("refX", 28)       // 箭头尖位置（偏移 28px，避让节点边缘）
    .attr("refY", 0)
    .attr("markerWidth", 6)
    .attr("markerHeight", 6)
    .attr("orient", "auto")  // 自动旋转
    .append("path")
    .attr("fill", "#aaa")
    .attr("d", "M0,-5L10,0L0,5");
```

**箭头偏移量 `refX=28` 的设计**：箭头需要指向目标节点的边缘而非中心。当目标节点半径为 `√(anchors) × 6` 时：
- 最小节点 (anchors=1): 半径 = 8px, refX=28 远大于 8 → 箭头悬空（但这是极少数情况）
- 中等节点 (anchors=12): 半径 ≈ 21px, refX=28 > 21 → 箭头刚好在节点边缘
- 大节点 (anchors=25): 半径 = 30px, refX=28 < 30 → 箭头在节点内部（可接受）

**接受此偏差**：因为 `refX` 是固定值，无法动态适配节点半径。在大多数域 (anchors 5-25) 的情况下效果可接受。

**连线渲染代码 (D3)**：

```javascript
g.append("g").selectAll("line").data(links).join("line")
    .attr("stroke", "#bbb")
    .attr("stroke-opacity", d => Math.min(0.6, d.weight / 200 + 0.1))
    .attr("stroke-width", d => Math.max(0.3, Math.min(10, d.weight / 6)))
    .attr("marker-end", "url(#arrow)");
```

### 4.3 图例（当前 v1.0 不包含）

Phase 2+ 可考虑在图左上角添加图例：

```
○ 节点大小 = 锚点数
─ 连线粗细 = import 次数
颜色 = 业务域（分类色）
```

v1.0 暂不包含图例，因为可视化图本身足够直观。图例在 Phase 2+ 中按需添加。

---

## 5. 交互设计

### 5.1 拖拽与节点固定

```javascript
const node = g.append("g").selectAll("g").data(domains).join("g")
    .call(d3.drag()
        .on("start", (e, d) => {
            if (!e.active) sim.alphaTarget(0.3).restart();
            d.fx = d.x;       // 记录拖拽开始位置
            d.fy = d.y;
        })
        .on("drag", (e, d) => {
            d.fx = e.x;       // 跟随鼠标移动
            d.fy = e.y;
        })
        .on("end", (e, d) => {
            if (!e.active) sim.alphaTarget(0);  // 停止力模拟活跃度
            d.fx = null;      // 释放固定 → 节点回到力模拟行为
            d.fy = null;
        }));
```

**拖拽状态转换**：

```
状态: 稳定 (alpha < alpha_min)
  │
  ├── 鼠标按下拖拽 ──→ 激活 (alpha = 0.3)
  │                      ├── 拖拽中: 节点跟随鼠标 (fx/fy = 鼠标位置)
  │                      └── 释放: alphaTarget = 0 → 图逐渐稳定
  │
  └── 鼠标点击但不拖拽 ──→ alpha 不变，节点不被固定
```

**固定行为**：拖拽时设置 `fx`/`fy` 会强制节点停留在该坐标。释放时清除 `fx`/`fy`，节点恢复到力模拟的自然位置。修改 `alphaTarget` 为 `0` 意味着拖拽结束后不再给力模拟补充能量，图会正常衰减到稳定。

### 5.2 缩放 (Zoom)

```javascript
const zoom = d3.zoom()
    .scaleExtent([0.15, 5])      // 缩放范围: 15% ~ 500%
    .on("zoom", (e) => {
        g.attr("transform", e.transform);  // 对整个图形容器做 transform
    });

svg.call(zoom);
```

**缩放范围选择**：

| 缩放值 | 效果 | 用途 |
|:------:|------|------|
| 0.15x | 缩小到 15%，~25 节点全部可见 | 初始视图，全图概览 |
| 0.5x | 缩小到 50% | 查看 50+ 节点的大项目 |
| 1.0x | 原始大小 | 默认视图 |
| 2.0x | 放大到 200% | 查看节点 label 和 tooltip |
| 5.0x | 放大到 500% | 查看紧密排列的小节点群 |

**为什么最小 0.15x**：~25 个节点在 1920px 宽屏幕上均匀分布时的理想间距约 180px。缩小到 0.15x 时，间距压缩到 27px，所有节点都可见但文字会很小。缩小到 0.1x 以下文字完全不可读，故 0.15x 作为下限。

**自动缩放适配**（Phase 2+ 可选）：

```javascript
// 可选: 初始化时自动缩放以适配所有节点
const initialTransform = d3.zoomIdentity
    .translate(W / 2, H / 2)
    .scale(0.8)
    .translate(-W / 2, -H / 2);
svg.call(zoom.transform, initialTransform);
```

v1.0 不实现自动缩放适配，用户可通过鼠标滚轮调整。

### 5.3 悬浮 Tooltip

```javascript
const tip = d3.select(".tooltip");

node
    .on("mouseenter", (e, d) => {
        const roles = Object.entries(d.roles)
            .map(([r, c]) => r + ": " + c)
            .join(", ");
        tip.style("display", "block")
            .html("<b>" + (d.display_name || d.name) + "</b><br>" +
                  "锚点: " + d.anchors +
                  " | 文件: " + d.total_nodes +
                  "<br>" + roles);
    })
    .on("mousemove", (e) => {
        tip.style("left", (e.pageX + 15) + "px")
           .style("top", (e.pageY - 30) + "px");
    })
    .on("mouseleave", () => {
        tip.style("display", "none");
    });
```

**Tooltip 内容示例**：

```
┌──────────────────────────────────┐
│  Bin数据管理                     │  ← 域名（粗体，中文优先）
│  锚点: 12 | 文件: 28             │  ← 规模信息
│  controller: 3, service_impl: 5, │  ← 角色分布
│  mapper: 2, dao: 1, adapter: 1   │
└──────────────────────────────────┘
```

**Tooltip 样式**：

```css
.tooltip {
    position: absolute;
    padding: 10px 14px;
    background: rgba(0, 0, 0, 0.88);
    color: #fff;
    border-radius: 6px;
    font-size: 13px;
    pointer-events: none;     /* 防止 tooltip 遮挡鼠标事件 */
    max-width: 300px;
    line-height: 1.5;
}
```

**Tooltip 定位**：`pageX + 15, pageY - 30`（鼠标右下方 15px 偏移，避免遮挡鼠标指针）

### 5.4 节点固定（Pin）

用户可通过双击节点来固定/释放节点，便于对比分析：

```javascript
node.on("dblclick", (e, d) => {
    if (d.fx === null) {
        // 固定当前节点
        d.fx = d.x;
        d.fy = d.y;
    } else {
        // 释放固定
        d.fx = null;
        d.fy = null;
    }
});
```

**固定指示**：固定节点在 Phase 2+ 中可通过加深描边（`stroke-width: 4px`）或添加小锁图标来指示。

**v1.0 简化**：v1.0 暂不实现双击固定（用户可通过拖拽然后不释放来"临时固定"，但释放后节点会回到自然位置。实际使用中这已足够）。

### 5.5 搜索高亮（Phase 2+）

```html
<!-- HTML 搜索输入框 -->
<div class="search-box">
    <input type="text" id="search" placeholder="搜索业务域..." />
</div>
```

```javascript
document.getElementById("search").addEventListener("input", (e) => {
    const query = e.target.value.toLowerCase();
    node.selectAll("circle")
        .attr("opacity", d => {
            if (!query) return 0.85;                 // 无查询→全显
            const match = d.name.toLowerCase().includes(query)
                      || (d.display_name && d.display_name.includes(query));
            return match ? 0.95 : 0.15;               // 匹配→高亮，不匹配→淡化
        });
});
```

v1.0 不实现搜索功能，仅预留 HTML 结构描述。

---

## 6. HTML 模板结构

### 6.1 整体结构

```html
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <title>业务域依赖图谱</title>
    <style>
        /* ─── 全局样式 ─── */
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: -apple-system, "Microsoft YaHei", sans-serif;
            overflow: hidden;
            background: #f5f5f5;
        }
        svg { width: 100vw; height: 100vh; }

        /* ─── 节点样式 ─── */
        .node circle {
            stroke: #fff;
            stroke-width: 2.5px;
            cursor: pointer;
        }
        .node text {
            font-size: 13px;
            pointer-events: none;
            font-weight: 500;
        }

        /* ─── 连线样式 ─── */
        .link { stroke: #ccc; stroke-opacity: 0.5; }

        /* ─── Tooltip 样式 ─── */
        .tooltip {
            position: absolute;
            padding: 10px 14px;
            background: rgba(0, 0, 0, 0.88);
            color: #fff;
            border-radius: 6px;
            font-size: 13px;
            pointer-events: none;
            max-width: 300px;
            line-height: 1.5;
        }

        /* ─── 标题栏 ─── */
        .header {
            position: absolute;
            top: 16px;
            left: 16px;
            background: white;
            padding: 14px 20px;
            border-radius: 10px;
            box-shadow: 0 2px 16px rgba(0, 0, 0, 0.12);
            font-size: 16px;
            font-weight: 600;
            z-index: 10;
        }

        /* ─── 搜索框 (Phase 2+) ─── */
        .search-box {
            position: absolute;
            top: 16px;
            right: 16px;
            z-index: 10;
        }
        .search-box input {
            padding: 10px 16px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 14px;
            width: 200px;
            outline: none;
        }
        .search-box input:focus {
            border-color: #4E79A7;
            box-shadow: 0 0 0 2px rgba(78, 121, 167, 0.2);
        }
    </style>
</head>
<body>
    <div class="header">业务域依赖图谱 &mdash; {{count}} 个域</div>
    <div class="search-box" style="display:none">
        <input type="text" id="search" placeholder="搜索业务域..." />
    </div>
    <div class="tooltip" style="display:none"></div>
    <svg></svg>

    <script src="https://d3js.org/d3.v7.min.js"></script>
    <script>
        // ─── JSON 数据 ───
        const domains = {{domain_json}};
        const links = {{link_json}};

        // ─── 所有 D3 代码 ───
        // (见第 3-5 章)
    </script>
</body>
</html>
```

### 6.2 模板变量说明

| 模板变量 | 值 | 说明 |
|---------|-----|------|
| `{{count}}` | `len(domains)` | 域总数，显示在标题栏 |
| `{{domain_json}}` | `json.dumps(domain_data, ensure_ascii=False)` | 节点数据 JSON |
| `{{link_json}}` | `json.dumps(links, ensure_ascii=False)` | 连线数据 JSON |

### 6.3 资源加载策略

| 资源 | 加载方式 | 大小 | 说明 |
|------|---------|:----:|------|
| D3.js v7 | CDN (`https://d3js.org/d3.v7.min.js`) | ~250KB (gzip ~80KB) | 浏览器缓存，通常已存在 |
| 内联 CSS | 内嵌在 `<style>` 中 | ~1KB | 无外部依赖 |
| 内联 JS | 内嵌在 `<script>` 中 | ~3KB | 线性逻辑，无外部依赖 |
| JSON 数据 | 内嵌在 JS 变量中 | ~5KB (25 域) | Python JSON 序列化注 |

**总大小**：~260KB（含 D3 CDN，浏览器加载后约 90KB 实际传输）

### 6.4 Python 渲染函数

```python
def _render_html(domains: list[dict], links: list[dict]) -> str:
    """
    渲染完整 HTML 字符串。

    使用 Python f-string 将 JSON 数据和计数注入到 HTML 模板中。
    JSON 使用 ensure_ascii=False 以支持中文域名显示。
    """
    djson = json.dumps(domains, ensure_ascii=False)
    ljson = json.dumps(links, ensure_ascii=False)
    count = len(domains)

    return f'''<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<title>业务域依赖图谱</title>
<style>
*{{margin:0;padding:0;box-sizing:border-box}}
body{{font-family:-apple-system,"Microsoft YaHei",sans-serif;overflow:hidden;background:#f5f5f5}}
svg{{width:100vw;height:100vh}}
.node circle{{stroke:#fff;stroke-width:2.5px;cursor:pointer}}
.node text{{font-size:13px;pointer-events:none;font-weight:500}}
.link{{stroke:#ccc;stroke-opacity:0.5}}
.tooltip{{position:absolute;padding:10px 14px;background:rgba(0,0,0,0.88);color:#fff;border-radius:6px;font-size:13px;pointer-events:none;max-width:300px;line-height:1.5}}
.header{{position:absolute;top:16px;left:16px;background:white;padding:14px 20px;border-radius:10px;box-shadow:0 2px 16px rgba(0,0,0,0.12);font-size:16px;font-weight:600;z-index:10}}
</style>
</head>
<body>
<div class="header">业务域依赖图谱 &mdash; {count} 个域</div>
<div class="tooltip" style="display:none"></div>
<svg></svg>
<script src="https://d3js.org/d3.v7.min.js"></script>
<script>
const domains={djson},links={ljson};
const W=window.innerWidth,H=window.innerHeight;
const colors=d3.scaleOrdinal(d3.schemeTableau10);
const sim=d3.forceSimulation(domains)
  .force("link",d3.forceLink(links).id(d=>d.name).distance(180))
  .force("charge",d3.forceManyBody().strength(-400))
  .force("center",d3.forceCenter(W/2,H/2))
  .force("collision",d3.forceCollide().radius(d=>Math.sqrt(d.anchors)*8+25));
const svg=d3.select("svg").attr("viewBox",[0,0,W,H]);
const g=svg.append("g");
svg.call(d3.zoom().scaleExtent([0.15,5]).on("zoom",e=>g.attr("transform",e.transform)));
svg.append("defs").selectAll("marker").data(["arrow"]).join("marker")
  .attr("id","arrow").attr("viewBox","0 -5 10 10").attr("refX",28).attr("refY",0)
  .attr("markerWidth",6).attr("markerHeight",6).attr("orient","auto")
  .append("path").attr("fill","#aaa").attr("d","M0,-5L10,0L0,5");
g.append("g").selectAll("line").data(links).join("line")
  .attr("stroke","#bbb")
  .attr("stroke-opacity",d=>Math.min(0.6,d.weight/200+0.1))
  .attr("stroke-width",d=>Math.max(0.3,Math.min(10,d.weight/6)))
  .attr("marker-end","url(#arrow)");
const tip=d3.select(".tooltip");
const node=g.append("g").selectAll("g").data(domains).join("g")
  .call(d3.drag()
    .on("start",(e,d)=>{{if(!e.active)sim.alphaTarget(0.3).restart();d.fx=d.x;d.fy=d.y}})
    .on("drag",(e,d)=>{{d.fx=e.x;d.fy=e.y}})
    .on("end",(e,d)=>{{if(!e.active)sim.alphaTarget(0);d.fx=null;d.fy=null}}))
  .on("mouseenter",(e,d)=>{{
    const roles=Object.entries(d.roles).map(([r,c])=>r+":"+c).join(", ");
    tip.style("display","block")
      .html("<b>"+(d.display_name||d.name)+"</b><br>锚点:"+d.anchors+" | 文件:"+d.total_nodes+"<br>"+roles);
  }})
  .on("mousemove",e=>tip.style("left",(e.pageX+15)+"px").style("top",(e.pageY-30)+"px"))
  .on("mouseleave",()=>tip.style("display","none"));
node.append("circle")
  .attr("r",d=>Math.max(8,Math.sqrt(d.anchors)*6))
  .attr("fill",d=>colors(d.name)).attr("opacity",0.85);
node.append("text").text(d=>d.display_name||d.name)
  .attr("x",d=>Math.sqrt(d.anchors)*6+10).attr("y",5)
  .attr("font-size",d=>Math.min(15,Math.max(9,d.anchors/3+7)));
sim.on("tick",()=>{{
  g.selectAll("line").attr("x1",d=>d.source.x).attr("y1",d=>d.source.y)
    .attr("x2",d=>d.target.x).attr("y2",d=>d.target.y);
  node.attr("transform",d=>`translate(${{d.x}},${{d.y}})`);
}});
</script>
</body>
</html>'''
```

---

## 7. Phase 4+ 下钻交互设计

### 7.1 设计目标

当前域级可视化（v1.0）仅展示**域之间的宏观依赖关系**。Phase 4+ 的目标是：**点击一个域节点，展开该域内部的 Controller → Service → Mapper 结构**，实现"从宏观到细节"的逐层下钻。

### 7.2 交互流程

```
初始状态: 域级依赖图（~25 个节点）
  │
  ├── 点击域 A（如 "Bin数据管理"）
  │     │
  │     ├── 域 A 展开为内部结构:
  │     │     Controller A1 ──→ Service A1 ──→ Mapper A1
  │     │     Controller A2 ──→ Service A2 ──→ Mapper A2
  │     │
  │     ├── 其他域 B、C、D... 淡化（opacity=0.1）
  │     │
  │     ├── 域 A 对外的依赖连线保持不变
  │     │   （Controller A1 → Supplier 域）
  │     │
  │     └── 点击其他区域或按 Escape → 返回域级视图
  │
  └── 双击空白区域 / 按 Escape → 返回域级视图
```

### 7.3 内部节点构建

展开域时，根据 `Domain.anchors` 中的角色信息构建内部节点：

```javascript
function expandDomain(domain) {
    const internalNodes = [];
    const internalLinks = [];

    // 按角色分组提取内部节点
    const roleOrder = ["controller", "service_api", "service_impl", "mapper", "dao"];

    roleOrder.forEach((role, layerIndex) => {
        const anchors = domain.anchors[role] || [];
        anchors.forEach((anchor, i) => {
            const nodeId = domain.name + "__" + anchor.label;
            internalNodes.push({
                id: nodeId,
                label: anchor.label,
                role: role,
                layer: layerIndex,  // 用于纵向排列
            });
        });
    });

    // 构建内部的 Controller→Service→Mapper 调用链连线
    // 如果 graph.json 数据可用，从实际 calls/imports 边提取
    // 否则按逻辑推断（Controller → Service_API → Service_Impl → Mapper）
    const roleChain = ["controller", "service_api", "service_impl", "mapper", "dao"];
    for (let i = 0; i < roleChain.length - 1; i++) {
        const upper = internalNodes.filter(n => n.role === roleChain[i]);
        const lower = internalNodes.filter(n => n.role === roleChain[i + 1]);
        upper.forEach(u => {
            lower.forEach(l => {
                // 命名匹配: 检查 Controller 名是否包含 Service 名关键词
                if (matchByNamingConvention(u.label, l.label)) {
                    internalLinks.push({source: u.id, target: l.id, internal: true});
                }
            });
        });
    }

    return {nodes: internalNodes, links: internalLinks};
}
```

### 7.4 命名约定匹配算法

在缺少 graph.json 数据的情况下，通过命名约定推断内部调用链：

| Controller | Service | Mapper | 推断规则 |
|-----------|---------|--------|---------|
| `BinController` | `BinDataService` | `BinMapper` | Controller 名去掉后缀后包含 Service 名的关键词 |
| `BinOrderController` | `BinOrderService` | `BinOrderMapper` | 前缀完全匹配 |
| `PurchaseController` | `PurchaseService` | `PurchaseMapper` | 前缀完全匹配 |

```javascript
function matchByNamingConvention(controllerName, serviceName) {
    // Controller: "BinOrderController.java" → "BinOrder"
    // Service: "BinOrderService.java" → "BinOrder"
    // → 两者共同前缀相同 → 匹配

    const ctrlBase = controllerName
        .replace(/Controller\.java$/, "")
        .replace(/\.java$/, "");
    const svcBase = serviceName
        .replace(/ServiceImpl\.java$/, "")
        .replace(/Service\.java$/, "")
        .replace(/Mapper\.java$/, "")
        .replace(/\.java$/, "");

    return ctrlBase.includes(svcBase) || svcBase.includes(ctrlBase);
}
```

### 7.5 内部布局算法

展开后的内部节点使用**分层布局**（纵向），非力导向：

```javascript
function layoutInternalNodes(nodes, centerX, centerY) {
    const layerWidth = 180;        // 层间水平间距
    const nodeHeight = 40;         // 节点垂直间距
    const roleOrder = ["controller", "service_api", "service_impl", "mapper", "dao"];

    nodes.forEach(n => {
        const layerIndex = roleOrder.indexOf(n.role);
        if (layerIndex === -1) return;

        // 同角色内的节点垂直排列
        const sameRole = nodes.filter(x => x.role === n.role);
        const indexInRole = sameRole.indexOf(n);
        const totalInRole = sameRole.length;

        n.x = centerX + (layerIndex - 2) * layerWidth;  // 左右居中
        n.y = centerY - (totalInRole - 1) * nodeHeight / 2 + indexInRole * nodeHeight;
    });
}
```

### 7.6 交互状态管理

```javascript
let expandedDomain = null;  // 当前展开的域节点 ID

function toggleExpand(domainNode) {
    if (expandedDomain === domainNode.name) {
        collapseAll();
        return;
    }

    // 展开
    expandedDomain = domainNode.name;
    const internal = expandDomain(domainNode);

    // 淡化外部节点（除当前域和其依赖域）
    node.attr("opacity", d => {
        if (d.name === domainNode.name) return 1.0;    // 当前域不变
        if (links.some(l => l.source === domainNode.name && l.target === d.name)) return 0.4;
        if (links.some(l => l.target === domainNode.name && l.source === d.name)) return 0.4;
        return 0.1;  // 其他域淡化
    });

    // 在原域节点位置渲染内部结构
    renderInternalNodes(internal, domainNode.x, domainNode.y);
}
```

### 7.7 数据要求与依赖

下钻交互需要额外的数据支持：

| 数据 | 来源 | 是否必须 | 阶段 |
|------|------|:--------:|:----:|
| `Domain.anchors` 中的角色信息 | cluster.py | 必须（已有） | v1.0 |
| 内部节点间的 `calls` 边 | graph.json | 推荐 | Phase 4+ |
| 内部节点间的命名约定推断 | visualize.py 内置 | 回退方案 | Phase 4+ |
| `BusinessPoint` 的跨域调用信息 | cluster.py | 可选增强 | Phase 4+ |

### 7.8 v1.0 占位设计

**v1.0 不实现点击下钻**。但为了 Phase 4+ 的平滑过渡，v1.0 在 `Domain` 数据生成时已包含 `anchors` 的角色分组结构，下钻交互只需读取 `Domain.anchors` 即可展开。

v1.0 中的点击保持为无操作（或者只突出显示该节点及其直接邻居）。

```javascript
// v1.0: 点击节点仅突出显示（淡化其他节点）
node.on("click", (e, d) => {
    const neighborNames = new Set();
    links.forEach(l => {
        if (l.source === d.name) neighborNames.add(l.target);
        if (l.target === d.name) neighborNames.add(l.source);
    });
    node.selectAll("circle").attr("opacity", n =>
        n.name === d.name || neighborNames.has(n.name) ? 0.85 : 0.15
    );
    node.selectAll("text").attr("opacity", n =>
        n.name === d.name || neighborNames.has(n.name) ? 1 : 0.2
    );
});
```

---

## 8. 离线可用性设计

### 8.1 CDN 回退

当前使用 CDN 加载 D3.js。网络不可用时，页面 D3 功能失效。提供以下离线策略：

**策略 1：CDN 超时回退到本地副本（推荐，Phase 2+）**

```html
<script src="https://d3js.org/d3.v7.min.js"></script>
<script>
// CDN 加载失败回退
if (typeof d3 === 'undefined') {
    document.write('<script src="d3.v7.min.js"><\/script>');
}
</script>
```

需要在输出目录存放 `d3.v7.min.js` 文件（~250KB）。

**策略 2：内联 D3 子集（极简离线，Phase 3+）**

将 D3 的 force 和 selection 模块内联到 HTML 中（使用 rollup/d3-optimizer 打包）：

```html
<script>
/* 内联 D3 force + selection + zoom 模块 */
!function(t,n){...}({},function(){...});
</script>
```

**策略 3：不处理，接受 CDN 依赖（v1.0 默认）**

v1.0 不实现离线回退，依赖 CDN 可用。理由：
- D3 CDN（jsDelivr / cdnjs）SLA 达 99.9%
- 浏览器缓存后后续加载 < 1ms
- 内联 D3（~250KB）对 HTML 文件大小影响显著
- 内联 D3 子集需要额外构建工具（rollup / d3-optimizer）

### 8.2 文件大小分析

| 组件 | 大小 | 说明 |
|------|:----:|------|
| HTML 模板（内联 CSS + 内联 JS） | ~4KB | 固定开销 |
| JSON 数据（25 个域 + 连线） | ~5KB | 随域数线性增长 |
| **HTML 文件本体** | **~9KB** | 不含 D3 |
| D3.js v7 CDN 加载 | ~250KB | 浏览器加载，非文件大小 |
| **完整页面加载** | **~9KB + 250KB CDN** | 首次加载约 90KB gzip |

### 8.3 浏览器兼容性

| 浏览器 | D3 v7 兼容性 | 说明 |
|--------|:------------:|------|
| Chrome 60+ | ✅ 完全支持 | ES6 模块支持，D3 标准环境 |
| Firefox 55+ | ✅ 完全支持 | |
| Safari 12+ | ✅ 完全支持 | |
| Edge 79+ (Chromium) | ✅ 完全支持 | |
| IE 11 | ❌ 不支持 | D3 v7 不再支持 IE，使用 v5 可支持但需降级 |

**设计决策**：v1.0 面向现代浏览器（Chrome/Firefox/Safari/Edge），不兼容 IE 11。如果项目需要 IE 支持，可使用 D3 v5（需修改部分 API）。

### 8.4 输出文件位置

默认在项目根目录生成 `domain_graph.html`。在 pipeline 中可配置输出到 Wiki 目录：

```python
# pipeline.py
export_domain_html(
    domains=domains,
    output_path=output_dir / "domain_graph.html",  # 如 wiki/domain_graph.html
)
```

`output_dir` 默认为当前目录，可通过命令行参数 `--output` 指定：

```bash
graph-wiki build --output ./wiki
# 生成: ./wiki/domain_graph.html
```

---

## 9. 错误处理

### 9.1 降级策略

| 场景 | 检测条件 | 默认行为 |
|------|---------|---------|
| **空 domains** | `len(domains) == 0` | 生成空白页，标题显示"业务域依赖图谱 &mdash; 0 个域"，无节点和连线 |
| **部分域无依赖** | `d.dependencies` 为空 dict | 该域作为孤立节点显示（无连线），仍渲染为圆形节点 |
| **所有域都无依赖** | 所有域的 `dependencies` 均为空 | 只渲染节点（无连线），无箭头的静态散点图 |
| **域名为空** | `d.name == ""` 且 `d.id == ""` | 使用 `"unknown"` 作为回退标签 |
| **锚点数为 0** | `d.anchors == 0` | 节点半径取最小值 8px（`max(8, sqrt(0)*6) = 8`） |
| **域名过长** | 域名 > 30 字符 | 在 tooltip 中完整显示，节点标签不变（按原始 `font-size` 公式） |
| **CDN 不可用** | D3.js 加载失败 | 页面无 D3 功能，显示空白 SVG（静默降级） |
| **nodes/links 过长** | JSON 序列化超 1MB | 正常序列化（HTML 大小变大但不影响渲染） |

### 9.2 防御性编程

```python
def export_domain_html(
    domains: list[Domain],
    output_path: Path = Path("domain_graph.html"),
) -> Path:
    # 空域列表降级：生成空白图
    if not domains:
        html = _render_html([], [])
        output_path.write_text(html, encoding="utf-8")
        return output_path

    # 构建节点数据（容错：域名缺失用 id 回退）
    domain_data = []
    for d in domains:
        domain_data.append({
            "name": d.name or d.id or "unknown",
            "display_name": d.display_name or d.name or d.id,
            "anchors": d.anchors_count(),
            "total_nodes": d.total_files,
            "roles": _count_roles(d),
        })

    # 构建连线数据（容错：缺失的目标域被忽略）
    domain_names = {n["name"] for n in domain_data}
    links = []
    for d in domains:
        src_name = d.name or d.id
        for dep_id, count in d.dependencies.items():
            # 只保留源和目标都在域列表中的连线
            if dep_id in domain_names:
                links.append({"source": src_name, "target": dep_id, "weight": count})
            # dep_id 不在 domain_names 中的情况：
            # 目标域可能是被合并规则合并的旧 ID，或者是外部系统
            # 静默丢弃，不影响其他域显示

    html = _render_html(domain_data, links)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text(html, encoding="utf-8")
    return output_path.resolve()
```

### 9.3 异常安全保证

```
export_domain_html() 被调用
  │
  ├─ domains 为空 → 空白页（无异常）
  │
  ├─ domains 非空
  │     ├─ 域名部分为空 → name 回退到 id
  │     ├─ 部分域无依赖 → 孤立节点
  │     └─ 依赖目标域不存在 → 该连线跳过
  │
  ├─ output_path 目录不存在 → mkdir(parents=True) 自动创建
  │
  └─ 写入 IO 失败 → Python IOError 抛出，由 pipeline 捕获
```

### 9.4 Token 成本

**整个 `visualize.py` 模块零 Token 成本**。所有操作均为纯计算（JSON 序列化 + 模板渲染 + D3 力导向图渲染），不调用任何 LLM API。

### 9.5 性能估计

| 操作 | 时间估计 | 说明 |
|------|:--------:|------|
| 构建 25 个节点数据 | < 1ms | 纯内存字典构建 |
| 构建 ~500 条连线数据 | < 1ms | 遍历 dependencies |
| JSON 序列化 | < 1ms | 10KB 的小数据量 |
| HTML 模板渲染 | < 1ms | Python f-string 拼接 |
| **total (Python 端)** | **~2ms** | 几乎无开销 |
| D3 力模拟（浏览器端） | ~5-8 秒 | 浏览器渲染，非 Python 耗时 |

---

## 10. 测试用例

### 10.1 测试正常 3 个域的可视化输出

```python
def test_export_domain_html_normal():
    """验证正常 3 个域的可视化生成。"""
    from graph_wiki.visualize import export_domain_html
    from graph_wiki.models import Domain
    from pathlib import Path
    import tempfile

    domains = [
        Domain(
            id="bin-data",
            name="bin-data",
            display_name="Bin数据管理",
            anchors={
                "controller": [{"label": "BinController.java", "_role": "controller"},
                               {"label": "BinOrderController.java", "_role": "controller"}],
                "service_impl": [{"label": "BinDataServiceImpl.java", "_role": "service_impl"}],
            },
            total_files=28,
            dependencies={"inventory": 42, "supplier": 15},
        ),
        Domain(
            id="inventory",
            name="inventory",
            display_name="库存管理",
            anchors={
                "controller": [{"label": "InventoryController.java", "_role": "controller"}],
                "service_impl": [{"label": "InventoryServiceImpl.java", "_role": "service_impl"}],
            },
            total_files=15,
            dependencies={"bin-data": 10},
        ),
        Domain(
            id="supplier",
            name="supplier",
            display_name="供应商管理",
            anchors={
                "controller": [{"label": "SupplierController.java", "_role": "controller"}],
            },
            total_files=8,
            dependencies={},
        ),
    ]

    with tempfile.TemporaryDirectory() as tmpdir:
        output_path = Path(tmpdir) / "domain_graph.html"
        result_path = export_domain_html(domains=domains, output_path=output_path)

        # 验证文件生成
        assert result_path.exists()
        assert result_path.suffix == ".html"

        # 验证 HTML 内容
        html = result_path.read_text(encoding="utf-8")

        # 验证结构元素
        assert "<!DOCTYPE html>" in html
        assert "业务域依赖图谱" in html
        assert "3 个域" in html
        assert "d3.v7.min.js" in html

        # 验证数据注入
        assert "bin-data" in html
        assert "inventory" in html
        assert "supplier" in html
        assert "Bin数据管理" in html
        assert "库存管理" in html
        assert "供应商管理" in html

        # 验证节点数据包含锚点数
        assert '"anchors": 2' in html  # bin-data 有 2 个 anchor
        assert '"anchors": 1' in html  # inventory 和 supplier 各有 1 个

        # 验证连线数据
        assert '"source": "bin-data"' in html
        assert '"target": "inventory"' in html
        assert '"weight": 42' in html
        assert '"source": "inventory"' in html

        # 验证 D3 配置关键字
        assert "forceSimulation" in html
        assert "forceLink" in html
        assert "forceManyBody" in html
        assert "forceCenter" in html
        assert "forceCollide" in html
        assert "d3.zoom" in html

        # 验证交互功能
        assert "mouseenter" in html
        assert "mousemove" in html
        assert "mouseleave" in html
        assert "d3.drag" in html

        # 验证 CSS 样式
        assert ".tooltip" in html
        assert ".node circle" in html
```

### 10.2 测试空域列表

```python
def test_export_domain_html_empty():
    """验证空 domains 时生成空白页面。"""
    from graph_wiki.visualize import export_domain_html
    from pathlib import Path
    import tempfile

    with tempfile.TemporaryDirectory() as tmpdir:
        output_path = Path(tmpdir) / "domain_graph.html"
        result_path = export_domain_html(domains=[], output_path=output_path)

        assert result_path.exists()
        html = result_path.read_text(encoding="utf-8")

        # 页面标题显示 0 个域
        assert "0 个域" in html

        # 数据注入为空
        assert "const domains=[" in html 或 "const domains=[]" in html
        assert "const links=[" in html 或 "const links=[]" in html

        # 基本的 HTML 结构完整
        assert "<svg></svg>" in html
        assert "d3.forceSimulation" in html  # D3 脚本仍然加载（JS 端处理空数据）

        # 无节点和连线数据
        assert len(html) > 500  # 即使空白页也要有基本结构
```

### 10.3 测试仅孤立域（无依赖）

```python
def test_export_domain_html_no_links():
    """验证所有域无依赖关系时生成无连线图。"""
    from graph_wiki.visualize import export_domain_html
    from graph_wiki.models import Domain
    from pathlib import Path
    import tempfile

    domains = [
        Domain(
            id="domain-a",
            anchors={"controller": [{"label": "A.java", "_role": "controller"}]},
            total_files=5,
            dependencies={},  # 无依赖
        ),
        Domain(
            id="domain-b",
            anchors={"controller": [{"label": "B.java", "_role": "controller"}]},
            total_files=3,
            dependencies={},  # 无依赖
        ),
    ]

    with tempfile.TemporaryDirectory() as tmpdir:
        output_path = Path(tmpdir) / "domain_graph.html"
        result_path = export_domain_html(domains=domains, output_path=output_path)

        html = result_path.read_text(encoding="utf-8")

        # 2 个节点
        assert "domain-a" in html
        assert "domain-b" in html

        # 连线数据为空
        assert "const links=[]" in html 或 "links=[]" in html

        # 节点半径使用最小值（1 个锚点 → max(8, sqrt(1)*6) = max(8, 6) = 8）
        assert '"anchors": 1' in html

        # D3 功能正常（无连线的力导向图=散点图）
        assert "forceSimulation" in html
```

### 10.4 测试域名回退

```python
def test_export_domain_html_name_fallback():
    """验证域名缺失时回退到 id 标签。"""
    from graph_wiki.visualize import export_domain_html
    from graph_wiki.models import Domain
    from pathlib import Path
    import tempfile

    # Domain.name 为空（未标注），应回退到 Domain.id
    domain = Domain(
        id="test-domain",
        name="",  # 未标注
        anchors={"controller": [{"_role": "controller"}]},
        dependencies={},
    )

    with tempfile.TemporaryDirectory() as tmpdir:
        output_path = Path(tmpdir) / "domain_graph.html"
        result_path = export_domain_html(domains=[domain], output_path=output_path)

        html = result_path.read_text(encoding="utf-8")

        # 节点名应为 id（回退值）
        assert "test-domain" in html

        # display_name 不存在时不回退显示（JS 端处理：d.display_name || d.name）
        # 但 d.display_name 在 Python 端被设置为 d.name or d.id
```

### 10.5 测试 Role 计数正确性

```python
def test_count_roles():
    """验证 _count_roles 正确统计各角色锚点数。"""
    from graph_wiki.visualize import _count_roles
    from graph_wiki.models import Domain

    domain = Domain(
        id="test",
        anchors={
            "controller": [
                {"_role": "controller"},
                {"_role": "controller"},
            ],
            "service_impl": [
                {"_role": "service_impl"},
            ],
            "mapper": [
                {"_role": "mapper"},
                {"_role": "mapper"},
                {"_role": "mapper"},
            ],
        },
    )

    roles = _count_roles(domain)
    assert roles["controller"] == 2
    assert roles["service_impl"] == 1
    assert roles["mapper"] == 3
    assert sum(roles.values()) == 6  # 总锚点数
```

### 10.6 测试连线去重与空依赖

```python
def test_export_domain_html_skips_missing_target():
    """验证依赖目标域不在域列表中时跳过该连线。"""
    from graph_wiki.visualize import export_domain_html, _build_domain_links
    from graph_wiki.models import Domain
    from pathlib import Path
    import tempfile

    # 域 A 依赖于域 B，但域 B 不在 domains 列表中
    domain_a = Domain(
        id="domain-a",
        dependencies={"domain-b": 10, "nonexistent": 5},  # domain-b 不在列表中
        anchors={"controller": [{"_role": "controller"}]},
    )

    with tempfile.TemporaryDirectory() as tmpdir:
        output_path = Path(tmpdir) / "domain_graph.html"
        result_path = export_domain_html(domains=[domain_a], output_path=output_path)

        html = result_path.read_text(encoding="utf-8")
        # 依赖目标不存在，连线应为空
        assert "const links=[]" in html or "links=[]" in html
```

### 10.7 端到端：HTML 渲染正确性

```python
def test_render_html_structure():
    """验证 _render_html 输出的 HTML 结构完整性。"""
    from graph_wiki.visualize import _render_html

    domains = [
        {"name": "domain-a", "display_name": "域A", "anchors": 3, "total_nodes": 10, "roles": {"controller": 1, "service": 2}},
        {"name": "domain-b", "display_name": "域B", "anchors": 1, "total_nodes": 5, "roles": {"controller": 1}},
    ]
    links = [
        {"source": "domain-a", "target": "domain-b", "weight": 5},
    ]

    html = _render_html(domains, links)

    # 基本结构
    assert html.startswith("<!DOCTYPE html>")
    assert "</html>" in html

    # 关键标签
    assert "<meta charset=\"utf-8\">" in html
    assert "<title>业务域依赖图谱</title>" in html
    assert "<style>" in html
    assert "</style>" in html
    assert "<script src=\"https://d3js.org/d3.v7.min.js\"></script>" in html

    # 数据注入
    assert "domain-a" in html
    assert "domain-b" in html
    assert "域A" in html
    assert "域B" in html

    # 计数
    assert "2 个域" in html

    # 无 Python f-string 遗留
    assert "{count}" not in html
    assert "{djson}" not in html
    assert "{ljson}" not in html
```

---

## 11. 配置项清单

用户可通过 `graph-wiki.yaml` 配置以下可视化参数：

```yaml
visualize:
  # 输出文件路径（相对项目根目录）
  output: "domain_graph.html"

  # 力导向图参数（可选，默认值已适合多数场景）
  force:
    link_distance: 180          # 连线理想距离
    charge_strength: -400       # 电荷排斥强度
    collision_padding: 25       # 碰撞检测额外 padding

  # 缩放范围
  zoom:
    min: 0.15                   # 最小缩放
    max: 5                      # 最大缩放

  # 节点大小公式参数
  node:
    radius_base: 6              # 半径基数: sqrt(anchors) * radius_base
    radius_min: 8               # 最小半径(px)
    label_font_min: 9           # 标签最小字号
    label_font_max: 15          # 标签最大字号

  # 连线样式参数
  link:
    width_divisor: 200          # 宽度除数: min(10, weight / width_divisor)
    width_min: 0.3              # 最小宽度(px)
    width_max: 10               # 最大宽度(px)
    opacity_divisor: 200        # 透明度除数: min(0.6, weight / opacity_divisor + 0.1)
    opacity_max: 0.6            # 最大透明度

  # CND 回退（Phase 2+）
  cdn_fallback: false           # 是否启用 CDN 离线回退
  offline_d3_path: ""           # 本地 D3 文件路径（如 "d3.v7.min.js"）
```

---

## 12. 与当前代码实现的差距

`graph_wiki/visualize.py` 已实现核心可视化流程，但以下功能处于**待实现**状态：

| 功能 | 设计状态 | 实现状态 | 优先级 | 说明 |
|------|:--------:|:--------:|:------:|------|
| `export_domain_html()` | ✅ 完整设计 | ✅ 已实现 | — | |
| `_build_domain_nodes()` | ✅ 完整设计 | ✅ 已实现 | — | 内联在 `export_domain_html` 中 |
| `_build_domain_links()` | ✅ 完整设计 | ✅ 已实现 | — | 内联在 `export_domain_html` 中 |
| `_render_html()` | ✅ 完整设计 | ✅ 已实现 | — | |
| `_count_roles()` | ✅ 完整设计 | ✅ 已实现 | — | |
| 节点大小 = √(anchors) × 6 | ✅ 完整设计 | ✅ 已实现 | — | D3 JS 中计算 |
| Tableau10 色板 | ✅ 完整设计 | ✅ 已实现 | — | `d3.schemeTableau10` |
| 连线粗细 = weight / 200 | ✅ 完整设计 | ✅ 已实现 | — | |
| 缩放 (0.15x-5x) | ✅ 完整设计 | ✅ 已实现 | — | `d3.zoom().scaleExtent([0.15, 5])` |
| 拖拽 + 节点固定 | ✅ 完整设计 | ✅ 已实现 | — | `d3.drag()` + `fx`/`fy` |
| 悬浮 tooltip | ✅ 完整设计 | ✅ 已实现 | — | `mouseenter/mousemove/mouseleave` |
| 箭头指示有向依赖 | ✅ 完整设计 | ✅ 已实现 | — | SVG marker |
| 参数化配置 (graph-wiki.yaml) | 📝 设计预留 | ❌ 未实现 | 🟢 低 | `visualize` 配置段 |
| 搜索高亮 | 📝 设计预留 | ❌ 未实现 | 🟢 Phase 2+ | 输入框过滤 |
| CDN 离线回退 | 📝 设计预留 | ❌ 未实现 | 🟢 Phase 2+ | 本地 D3 副本 |
| 点击下钻展开域内结构 | 📝 设计预留 | ❌ 未实现 | 🟡 Phase 4+ | Controller→Service→Mapper 链 |
| 图例 | 📝 设计预留 | ❌ 未实现 | 🟢 Phase 2+ | 节点大小/连线粗细说明 |
| 双击固定/释放节点 | 📝 设计预留 | ❌ 未实现 | 🟢 Phase 2+ | Pin 交互 |
| 全屏模式 | 📝 设计预留 | ❌ 未实现 | 🟢 低 | 浏览器全屏 API |
| `_hex_to_rgba()` 辅助函数 | 📝 设计预留 | ❌ 未实现 | 🟢 低 | 颜色透明度转换 |
| D3 内联（完全离线） | 📝 设计预留 | ❌ 未实现 | 🟢 Phase 3+ | rollup 打包 D3 子集 |
| `display_name` 在 tooltip 中显示 | 📝 设计预留 | ❌ 未实现 | 🟡 中 | 当前 tooltip 使用 `name` |
| 自动缩放适配 (fit-to-screen) | 📝 设计预留 | ❌ 未实现 | 🟢 Phase 2+ | 初始化时自动调整缩放 |

---

## 13. 变更记录

| 日期 | 变更内容 | 版本 |
|------|---------|:----:|
| 2026-06-15 | 初始版本：基于架构设计文档 v4.5 和现有代码实现 `graph_wiki/visualize.py` | v1.0 |
