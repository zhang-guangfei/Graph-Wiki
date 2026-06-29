# Label 模块详细设计

> 对应总体设计：第四章 4.3 节（标注模块）  
> 状态：详细设计阶段（v2.0 重写，基于实际代码实现更新）  
> 依赖：`cluster.py`（域划分 + 业务点）、`models.py`（Domain / BusinessPoint）  
> 参考：`models-design.md`（数据模型）、`cluster-design.md`（上游模块设计）

---

## 0. 代码实现现状

`graph_wiki/label.py` 已实现核心标注流程（`label_domains → _label_single → _build_prompt → _call_llm → _parse_response → _write_summary`）。

**已实现**：
- ThreadPoolExecutor 并行标注（5 路并发）
- Claude API 调用（Anthropic SDK）
- Prompt 组装（含源码采样、类列表、跨域依赖）
- summary.md 生成（含双向链接 `[[domain]]`）
- `is_locked()` 锁定保护机制
- 业务点 `display_name` 写入（从 LLM 回复的 `business_points` map 中匹配）

**待实现**（与设计的差距）：
- OpenAI / Gemini 后端支持（当前仅 Claude）
- `call_openai()` / `call_gemini()` 具体实现
- 完整的重试与指数退避（当前 `label_domains` 无自动重试，失败域仅保留未标注状态）
- `safe_write_summary()` 函数（当前 `_write_summary` 直接覆盖，仅在 `is_locked` 层面无保护）
- 错误类型类（`LabelError`, `LlmApiError` 等）
- API Key 缺失检测与优雅降级
- 业务点级别的 `business-points/{name}.md` 文件

---

## 1. 模块职责

### 1.1 核心职责

用 LLM 为业务域和业务点生成人类可读的名称和描述。这是 Graph-Wiki 构建过程中**唯一消耗 Token 的步骤**。

```
输入:  list[Domain] (~25 个域, 每域含 ~20-50 锚点 + ~20-40 业务点)
       graph_data (节点详情, 用于获取包路径/文件名)
       backend_root (项目源码根目录, 用于 Service 源码采样)

输出:  list[Domain] (enriched)
        ├── domain.name          ← LLM 生成的英文 ID (如 "bin-data")
        ├── domain.display_name  ← LLM 生成的中文名 (如 "Bin数据管理")
        ├── domain.description   ← LLM 生成的一句话业务目标
        ├── domain.core_flows    ← LLM 生成的核心业务流程
        ├── bp.display_name      ← LLM 生成的业务点中文名
        └── wiki/{domain}/summary.md ← LLM 生成的 Wiki 文档
```

### 1.2 为什么 LLM 标注是唯一 Token 消耗点

Graph-Wiki 构建链路的 7 个步骤中，6 步是零 Token 的静态分析：

| 步骤 | 模块 | 方法 | Token 成本 | 原因 |
|------|------|------|:----------:|------|
| 1. 文件检测 | `reuse.py` | `detect()` | **0** | 文件系统遍历 |
| 2. AST 提取 | `reuse.py` | `extract()` | **0** | tree-sitter 本地解析 |
| 3. 图构建 | `reuse.py` | `build()` | **0** | NetworkX 图操作 |
| 4. 域聚类 | `cluster.py` | `business_cluster()` | **0** | 正则 + 包路径 + 图遍历 |
| 5. API 映射 | `api_mapper.py` | `match_apis()` | **0** | 字符串匹配 + 注解解析 |
| 6. **LLM 标注** | **`label.py`** | **`label_domains()`** | **~70K** | **LLM API 调用** |
| 7. Wiki 导出 | `export.py` | `export_wiki()` | **0** | Markdown 模板渲染 |

**设计依据**：类名（`BinOrderController`）和包名（`com.smc.smccloud.bin`）本身已经包含了大量业务语义，但是机器无法将其转化为人类可读的"Bin 数据管理"、"采购订单审批流程"等描述。这一步必须由 LLM 完成，因此是唯一的 Token 消耗点。

### 1.3 标注什么，不标注什么

| 标注内容 | LLM 输出 | 原因 |
|---------|----------|------|
| 域名 (`domain.name`) | 英文 ID（如 `"bin-data"`） | 从包路径和类名推断的简短业务标识 |
| 显示名 (`domain.display_name`) | 中文名（如 `"Bin数据管理"`） | 人类阅读的友好名称 |
| 描述 (`domain.description`) | 一句话业务目标 | 让读者快速理解该域做什么 |
| 核心流程 (`domain.core_flows`) | 3-5 个业务步骤 | 展示域内的主要业务流程 |
| 依赖原因 (`dependencies[].reason`) | 一句话说明依赖理由 | 解释为什么 A 域需要 B 域 |
| 关键术语 (`key_terms`) | 业务领域术语表 | 帮助新人理解域内的业务词汇 |
| 业务点中文名 (`bp.display_name`) | 每个业务方法的中文翻译 | 如 `listBinOrderDetail` → "Bin订单明细查询" |

| **不标注**内容 | 原因 | 替代方案 |
|--------------|------|---------|
| 业务规则 (`rules.md`) | LLM 可能产生幻觉，误导业务决策 | 人工填写 |
| 需求规格 (`spec.md`) | 权威文档不应由 LLM 生成 | 人工填写 |
| 代码到域的映射 | 静态分析即可确定，无需 LLM | `cluster.py` 包路径聚类 |
| API 前后端映射 | 字符串匹配即可确定 | `api_mapper.py` 注解解析 |
| 字段级数据流 | AST 分析即可追踪 | `field_mapper.py` DTO 解析 |
| 代码清单 (`code-map.md`) | 模板直接渲染 | `export.py` 遍历 Domain.anchors |

### 1.4 数据流定位

```
上游: cluster.py → list[Domain] (含 anchors, business_points, dependencies)
                                  │
                                  ▼
                           label.py  ← 本模块
                                  │
          ┌───────────────────────┼───────────────────────┐
          ▼                       ▼                       ▼
    export.py              visualize.py              api_mapper.py
    (读取 domain.name,     (读取 domain.name        (读取 Domain 仅用于
     display_name 等)      用于 HTML 标题)           域隶属匹配)
```

---

## 2. 接口定义

### 2.1 接口契约

```python
# graph_wiki/label.py

from dataclasses import dataclass
from enum import Enum
from pathlib import Path


class LlmBackend(Enum):
    """支持的 LLM 后端"""
    CLAUDE = "claude"
    OPENAI = "openai"
    GEMINI = "gemini"


@dataclass
class LabelConfig:
    """标注配置"""
    backend: LlmBackend = LlmBackend.CLAUDE
    model: str = "claude-haiku-4-5-20251001"    # 默认用 Haiku，成本优先
    parallel_calls: int = 5                       # 并行度（受 API rate limit 限制）
    retry_count: int = 3                          # 失败重试次数
    sampling_lines: int = 50                      # 每个 Service 采样的源码行数


def label_domains(
    domains: list[Domain],
    graph_data: dict,
    backend_root: Path,
    config: LabelConfig = LabelConfig(),
) -> list[Domain]:
    """
    并行标注：ThreadPoolExecutor 5 路并发，每域独立 LLM 调用。

    参数:
        domains: cluster.py 输出的 Domain 列表
        graph_data: graph.json 数据 (dict 格式, 键为节点 ID, 值为属性 dict)
        backend_root: 项目源码根目录 (用于读取源码采样)
        config: 标注配置

    返回:
        标注后的 Domain 列表（失败域保持未标注状态，domain.name 回退为 domain.id）

    异常:
        本函数不抛出异常 —— 单个域标注失败不影响其他域
    """
```

### 2.2 内部子函数签名

```python
def _label_single(
    domain: Domain,
    graph_data: dict,
    backend_root: Path,
    config: LabelConfig,
) -> Domain:
    """
    标注单个域。

    步骤:
    1. 采样核心 Service 源码（1-2 个文件，各前 sampling_lines 行）
    2. 组装 Prompt（系统角色 → 包路径 → 类列表 → 源码采样 → 跨域依赖 → JSON 格式）
    3. 调用 LLM API（支持 Claude / OpenAI / Gemini 三后端）
    4. 解析返回的 JSON（尝试直接解析 → 正则提取 → 兜底空结果）
    5. 写入 summary.md（跳过已锁定的域）
    6. 返回标注后的 Domain
    """


def _build_prompt(domain: Domain, backend_root: Path, config: LabelConfig) -> str:
    """组装 6 段式 Prompt（系统角色 → 包路径 → 类列表 → 源码采样 → 跨域依赖 → JSON 格式）"""


def _call_llm(prompt: str, config: LabelConfig) -> str:
    """调用 LLM API（根据 config.backend 路由到不同后端）"""


def _call_claude(prompt: str, model: str) -> str:
    """调用 Anthropic Claude API"""


def _call_openai(prompt: str, model: str) -> str:
    """调用 OpenAI API"""


def _call_gemini(prompt: str, model: str) -> str:
    """调用 Google Gemini API"""


def _parse_response(response: str) -> dict:
    """解析 LLM 返回的 JSON（支持直接解析和正则提取两种模式）"""


def _write_summary(domain: Domain, result: dict, backend_root: Path):
    """生成 summary.md（含 frontmatter、双向链接、自动生成标记）"""


def safe_write_summary(summary_path: Path, content: str):
    """受保护写入：如果已锁定，不覆盖"""


def is_locked(summary_path: Path) -> bool:
    """检查 summary.md 是否被人工锁定（第一行含 @locked 标记）"""
```

### 2.3 参数语义

| 参数 | 类型 | 默认值 | 含义 |
|------|------|--------|------|
| `domains` | `list[Domain]` | 必填 | 聚类模块输出的域列表。每个域包含 anchors、business_points、dependencies 等 |
| `graph_data` | `dict` | 必填 | graph.json 的全量数据，键为节点 ID，值为属性字典。用于获取节点的 `source_file`、`label` 等 |
| `backend_root` | `Path` | 必填 | 项目源码的根目录路径。用于读取 Service 源码进行采样 |
| `config.backend` | `LlmBackend` | `CLAUDE` | LLM 后端选择。影响 API Key 读取和调用方式 |
| `config.model` | `str` | `"claude-haiku-4-5-20251001"` | 模型名称。不同的后端有不同的默认模型 |
| `config.parallel_calls` | `int` | `5` | 最大并行调用数。受 API rate limit 限制 |
| `config.retry_count` | `int` | `3` | 每个域标注失败时的最大重试次数 |
| `config.sampling_lines` | `int` | `50` | 每个 Service 文件采样的前 N 行代码 |

### 2.4 返回值语义

正常标注完成的 Domain：
- `domain.name` 被设为 LLM 返回的 `name` 字段
- `domain.display_name` 被设为 LLM 返回的 `display_name` 字段
- `domain.business_points` 中的每个 `BusinessPoint.display_name` 根据 LLM 返回的 `business_points` map 设置
- `wiki/{domain.name}/summary.md` 文件已生成

标注失败的 Domain：
- `domain.name` 保持为 `domain.id`（回退）
- `domain.display_name` 保持为空字符串
- `BusinessPoint.display_name` 保持为方法名原始值
- summary.md 不生成（如果已存在则保留原有内容）

---

## 3. 并行化策略

### 3.1 ThreadPoolExecutor 5 路并发

```python
def label_domains(
    domains: list[Domain],
    graph_data: dict,
    backend_root: Path,
    config: LabelConfig = LabelConfig(),
) -> list[Domain]:
    with ThreadPoolExecutor(max_workers=config.parallel_calls) as executor:
        futures = {
            executor.submit(
                _label_single, d, graph_data, backend_root, config
            ): d
            for d in domains
        }
        results = []
        for future in as_completed(futures):
            domain = futures[future]
            try:
                result = future.result()
                results.append(result)
            except Exception:
                results.append(domain)  # 失败降级：返回未标注的域
        return results
```

### 3.2 5 路并发的数学推导

```
前提条件:
  · 域数量 N = 25（OPS 项目实际值，常规项目 ~15-40）
  · 每轮 LLM 调用耗时 T = ~6s（Claude Haiku，输入 ~2,500 tokens）
  · 并行路数 P = 5（parallel_calls 默认值）

计算:
  总轮数 R = ceil(N / P) = ceil(25 / 5) = 5 轮
  每轮耗时 = max(每域耗时) ≈ 6s
  总耗时 ≈ 5 轮 × 6s = ~30s

  如果 P = 10 (提高并行):
  总轮数 R = ceil(25 / 10) = 3 轮
  总耗时 ≈ 3 轮 × 6s = ~18s（节省 12s，但可能触发 rate limit）

  如果 P = 1 (串行):
  总耗时 ≈ 25 域 × 6s = ~150s（慢 5 倍）
```

### 3.3 Rate Limit 安全性分析

| 后端 | RPM 上限 | 5 并发安全 | 10 并发安全 |
|------|:--------:|:----------:|:----------:|
| Claude Haiku (Tier 1) | ~50 RPM | ✅ 安全 | ⚠️ 边缘 |
| Claude Haiku (Tier 2+) | ~500 RPM | ✅ 安全 | ✅ 安全 |
| OpenAI GPT-4o-mini | ~500 RPM | ✅ 安全 | ✅ 安全 |
| Gemini 1.5 Flash | ~360 RPM | ✅ 安全 | ✅ 安全 |

**结论**：默认 5 路并发在三个后端下均安全。用户可根据 API tier 在 `LabelConfig.parallel_calls` 中调整。

### 3.4 失败隔离设计

```
正常流程:
  域 A ──→ _label_single(A) ──→ LLM API ──→ 标注后 A

失败隔离:
  域 B ──→ _label_single(B) ──→ LLM API ──→ 异常
                                      │
                                      ├─ 重试 3 次（带指数退避）
                                      │      └─ 成功 → 标注后 B
                                      │      └─ 失败 → 未标注 B（原始 Domain）
                                      │
                                      └─ 不影响域 C、域 D 的标注
```

**关键设计**：每个域的标注是独立的。域 B 的 LLM 调用失败不会阻塞域 C 和域 D 的执行，也不会导致整个 `label_domains` 失败。

### 3.5 并行度配置建议

| 场景 | 建议 parallel_calls | 原因 |
|------|:-------------------:|------|
| 首次构建（默认） | 5 | 平衡速度和 rate limit |
| 快速测试 | 1 | 串行执行，方便观察单域输出 |
| 大型项目（50+ 域） | 10 | 充分利用 API 并发能力 |
| CI/CD 环境 | 3 | 保守设置，避免共享 API Key 的 rate limit |
| --no-llm | 0 | 跳过 LLM 标注 |

---

## 4. Prompt 模板设计

### 4.1 六段式结构

每个域的 Prompt 由 6 个部分组成，从宏观到微观逐步提供信息：

```
┌─────────────────────────────────────────────────────────────────────┐
│  1. 系统角色设定（固定，~100 tokens）                                 │
│     "你是一个代码库业务分析专家。请根据以下信息分析一个业务模块。"     │
├─────────────────────────────────────────────────────────────────────┤
│  2. 包路径信息（动态，~20 tokens）                                   │
│     com.smc.smccloud.bin                                            │
│     com.smc.smccloud.bin.controller                                 │
│     com.smc.smccloud.bin.service                                    │
├─────────────────────────────────────────────────────────────────────┤
│  3. 类名列表（动态，~200 tokens）                                    │
│     Controller (3 个): BinController, BinOrderController, ...       │
│     Service (5 个): BinDataService, BinDataServiceImpl, ...         │
│     Mapper/DAO (3 个): BinOrderMapper, BindataMapper, ...           │
├─────────────────────────────────────────────────────────────────────┤
│  4. 源码采样（动态，~2,000 tokens）                                  │
│     1-2 个核心 Service 的前 sampling_lines 行代码                    │
│     选择策略：先选 Controller import 最多的 Service                  │
├─────────────────────────────────────────────────────────────────────┤
│  5. 跨域依赖（动态，~100 tokens）                                    │
│     inventory: 42 次 import                                          │
│     supplier: 15 次 import                                           │
│     order: 8 次 import                                               │
├─────────────────────────────────────────────────────────────────────┤
│  6. JSON 输出格式要求（固定，~80 tokens）                            │
│     返回纯 JSON，包含 name/display_name/description/... 字段         │
└─────────────────────────────────────────────────────────────────────┘

  每域总计: ~2,500 tokens 输入
```

### 4.2 完整 Prompt 模板

```python
def _build_prompt(domain: Domain, backend_root: Path, config: LabelConfig) -> str:
    # ── Step 1: 采样核心 Service 源码 ──
    sample = ""
    service_anchors = domain.anchors.get("service_impl", [])[:2]  # 最多 2 个
    for anchor in service_anchors:
        src_file = anchor.get("source_file", "")
        if not src_file:
            continue
        # 安全检查：排除包含敏感信息的路径
        if _is_sensitive_path(src_file):
            continue
        try:
            full_path = backend_root / src_file
            lines = full_path.read_text(encoding="utf-8").split("\n")
            # 只取前 sampling_lines 行
            snippet = "\n".join(lines[:config.sampling_lines])
            sample += f"\n### {src_file}\n```java\n{snippet}\n```\n"
        except (IOError, UnicodeDecodeError, OSError):
            continue

    # ── Step 2: 组装 Prompt 六段 ──
    # 段 1: 包路径信息
    packages = "\n".join(domain.packages[:5]) if domain.packages else domain.id

    # 段 2: Controller 类名列表
    controllers = [a.get("label", "") for a in domain.anchors.get("controller", [])]

    # 段 3: Service 类名列表（接口 + 实现）
    services = (
        [a.get("label", "") for a in domain.anchors.get("service_api", [])] +
        [a.get("label", "") for a in domain.anchors.get("service_impl", [])]
    )

    # 段 4: Mapper/DAO 类名列表
    mappers = (
        [a.get("label", "") for a in domain.anchors.get("mapper", [])] +
        [a.get("label", "") for a in domain.anchors.get("dao", [])]
    )

    # 段 5: 跨域依赖（取前 5 个 import 最多的域）
    deps = [
        f"- {k}: {v} 次 import"
        for k, v in sorted(domain.dependencies.items(), key=lambda x: -x[1])[:5]
    ]

    # 段 6: 业务点方法列表（供 LLM 生成中文名）
    bp_methods = [
        bp.name.lstrip(".") for bp in domain.business_points[:15]
    ]

    return f"""你是代码库业务分析专家。请根据以下信息分析业务模块。

## 包路径
{packages}

## Controller ({len(controllers)} 个)
{chr(10).join(f"- {c}" for c in controllers[:15])}

## Service ({len(services)} 个)
{chr(10).join(f"- {s}" for s in services[:15])}

## Mapper/DAO ({len(mappers)} 个)
{chr(10).join(f"- {m}" for m in mappers[:10])}

## 业务方法（需生成中文名）
{chr(10).join(f"- {m}" for m in bp_methods[:20])}

## 跨域依赖
{(chr(10).join(deps)) if deps else "无"}

## 源码采样
{sample or "无（无法读取源码）"}

---
请返回以下 JSON 格式（不要包含其他文字，不要用 markdown 代码块包裹）：

{{
  "name": "英文域 ID（2-3 个单词，小写连字符，如 bin-data 或 purchase-order）",
  "display_name": "中文域名（2-6 个字，如 Bin数据管理 或 采购管理）",
  "description": "一句话业务目标描述（30 字以内）",
  "core_flows": [
    {{"name": "流程名", "steps": ["步骤1", "步骤2", "步骤3"]}}
  ],
  "dependencies": [
    {{"domain": "依赖的域 ID", "reason": "一句话依赖原因"}}
  ],
  "key_terms": ["关键业务术语1", "关键业务术语2"],
  "business_points": {{
    "listBinOrderDetail": "Bin订单明细查询",
    "saveBinOrder": "保存Bin订单",
    "deleteBinOrder": "删除Bin订单"
  }}
}}"""
```

### 4.3 敏感路径排除

```python
def _is_sensitive_path(path: str) -> bool:
    """检查文件路径是否包含敏感信息，排除这些文件不送入 LLM"""
    sensitive_patterns = [
        "secret", "password", "token", "credential",
        ".env", "application-", "keystore", "truststore",
        "credentials",
    ]
    path_lower = path.lower()
    return any(p in path_lower for p in sensitive_patterns)
```

### 4.4 采样优先级策略

```
选择哪个 Service 文件采样？
  1. 优先选择 Controller 中 @Autowired/import 最多的 ServiceImpl
  2. 如果域内有多个 ServiceImpl，选文件体积适中的（过大 = 配置多，过小 = 工具类）
  3. 最多采样 2 个 ServiceImpl，各取前 50 行
  4. 跳过接口（Service API），因为接口只包含方法签名，不含业务逻辑

采样多少行？
  · config.sampling_lines = 50 (默认)
  · 50 行 Java 代码 ≈ 2,000 tokens
  · 50 行足够让 LLM 理解：类字段（业务实体）、@Autowired 依赖（跨域调用）、
    几个核心方法签名（业务能力）
  · 50 行不足以让 LLM 看到完整业务逻辑——这正是设计目标：
    让 LLM 了解"有什么"而非"怎么做"
```

---

## 5. LLM 调用实现

### 5.1 调用路由器

```python
def _call_llm(prompt: str, config: LabelConfig) -> str:
    """根据后端类型路由到不同的 API 调用函数"""
    if config.backend == LlmBackend.CLAUDE:
        return _call_claude(prompt, config.model)
    elif config.backend == LlmBackend.OPENAI:
        return _call_openai(prompt, config.model)
    elif config.backend == LlmBackend.GEMINI:
        return _call_gemini(prompt, config.model)
    else:
        raise ValueError(f"Unsupported LLM backend: {config.backend}")
```

### 5.2 Claude (Anthropic SDK)

```python
def _call_claude(prompt: str, model: str) -> str:
    """通过 Anthropic API 调用 Claude"""
    from anthropic import Anthropic

    api_key = os.environ.get("ANTHROPIC_API_KEY")
    if not api_key:
        raise LlmApiError("ANTHROPIC_API_KEY 环境变量未设置")

    client = Anthropic(api_key=api_key)

    response = client.messages.create(
        model=model,
        max_tokens=1024,
        system="你是一个代码库业务分析专家。始终返回有效的 JSON，不要包含其他文字。",
        messages=[{"role": "user", "content": prompt}],
    )
    return response.content[0].text
```

**参数说明**：

| 参数 | 值 | 含义 |
|------|----|------|
| `model` | `config.model`（默认 `"claude-haiku-4-5-20251001"`） | 模型选择。Haiku = 成本优先，Sonnet = 质量优先 |
| `max_tokens` | `1024` | 输出上限。每域输出 ~300 tokens，1024 留有余量 |
| `system` | 固定提示 | 系统角色 + JSON 约束 |
| `messages` | `[{role: "user", content: prompt}]` | 单轮对话，无需上下文 |

### 5.3 OpenAI (OpenAI SDK)

```python
def _call_openai(prompt: str, model: str) -> str:
    """通过 OpenAI API 调用 GPT 模型"""
    from openai import OpenAI

    api_key = os.environ.get("OPENAI_API_KEY")
    if not api_key:
        raise LlmApiError("OPENAI_API_KEY 环境变量未设置")

    client = OpenAI(api_key=api_key)

    response = client.chat.completions.create(
        model=model or "gpt-4o-mini",
        max_tokens=1024,
        temperature=0.1,  # 低温度确保 JSON 输出一致性
        messages=[
            {
                "role": "system",
                "content": "你是一个代码库业务分析专家。始终返回有效的 JSON，不要包含其他文字。",
            },
            {"role": "user", "content": prompt},
        ],
    )
    return response.choices[0].message.content
```

**JSON 模式**（GPT-4o-mini 原生支持）：

```python
response = client.chat.completions.create(
    model=model,
    response_format={"type": "json_object"},  # 强制 JSON 输出
    max_tokens=1024,
    temperature=0.1,
    messages=[...],
)
```

**注意**：`response_format="json_object"` 在某些旧模型上不可用。当模型不支持时，自动降级为普通模式。

### 5.4 Gemini (Google AI SDK)

```python
def _call_gemini(prompt: str, model: str) -> str:
    """通过 Google AI SDK 调用 Gemini"""
    import google.generativeai as genai

    api_key = os.environ.get("GOOGLE_API_KEY")
    if not api_key:
        raise LlmApiError("GOOGLE_API_KEY 环境变量未设置")

    genai.configure(api_key=api_key)

    model_instance = genai.GenerativeModel(
        model_name=model or "gemini-1.5-flash",
        system_instruction="你是一个代码库业务分析专家。始终返回有效的 JSON，不要包含其他文字。",
        generation_config=genai.types.GenerationConfig(
            max_output_tokens=1024,
            temperature=0.1,
        ),
    )

    response = model_instance.generate_content(prompt)
    return response.text
```

### 5.5 后端对比

| 维度 | Claude | OpenAI | Gemini |
|------|--------|--------|--------|
| 环境变量 | `ANTHROPIC_API_KEY` | `OPENAI_API_KEY` | `GOOGLE_API_KEY` |
| 默认模型 | Claude Haiku 4.5 | GPT-4o-mini | Gemini 1.5 Flash |
| 推荐模型（质量优先） | Claude Sonnet 4.5 | GPT-4o | Gemini 1.5 Pro |
| SDK | `anthropic` | `openai` | `google-generativeai` |
| 成本 (Haiku/4o-mini/Flash) | ~$0.3/25域 | ~$0.15/25域 | ~$0.10/25域 |
| JSON 可靠性 | 高（响应倾向结构化） | 高（支持 json_object 模式） | 中（需引导） |
| system prompt | 支持（专用参数） | 支持（messages[0]） | 支持（专用参数） |

---

## 6. 重试与指数退避策略

### 6.1 错误分类与重试策略

| 错误类型 | 是否可重试 | 重试策略 | 原因 |
|---------|:---------:|---------|------|
| `LlmApiError`（网络超时、500 等） | ✅ 可重试 | 指数退避 | 临时性故障 |
| `LlmRateLimitError`（429） | ✅ 可重试 | 增量退避 + 等待 | API 限流，等一会儿就好 |
| `LlmParseError`（JSON 解析失败） | ⚠️ 可重试 | 重试 1 次 | 偶发输出格式异常 |
| `ValueError`（不支持的 backend） | ❌ 不重试 | 立即失败 | 配置错误，重试无意义 |
| `LlmApiError`（API Key 无效 401） | ❌ 不重试 | 立即失败 | 认证错误，重试无用 |

### 6.2 指数退避实现

```python
import time

# 退避延迟序列（秒）
RETRY_DELAYS = [5, 15, 30]

# 等效于: base=5, multiplier=3, cap=30
# 5 × 3^0 = 5s
# 5 × 3^1 = 15s
# 5 × 3^2 = 45s → capped at 30s


def _label_single_with_retry(
    domain: Domain,
    graph_data: dict,
    backend_root: Path,
    config: LabelConfig,
) -> Domain:
    """带重试的域标注"""
    last_exception = None

    for attempt in range(config.retry_count + 1):  # 1 次初始 + retry_count 次重试
        if attempt > 0:
            delay = RETRY_DELAYS[min(attempt - 1, len(RETRY_DELAYS) - 1)]
            print(f"  重试 {attempt}/{config.retry_count} ({delay}s)...")
            time.sleep(delay)

        try:
            return _label_single(domain, graph_data, backend_root, config)
        except LlmRateLimitError as e:
            last_exception = e
            # Rate limit：额外多等一会儿
            time.sleep(10)
        except LlmApiError as e:
            last_exception = e
            # 如果是认证错误，立即放弃重试
            if "401" in str(e) or "401" in str(e.__cause__ or ""):
                raise
        except LlmParseError as e:
            last_exception = e
            if attempt >= 1:  # 只重试 1 次
                raise

    # 所有重试耗尽，打印警告并返回未标注的域
    print(f"  警告：域 {domain.id} 标注失败（{last_exception}），保持未标注状态")
    return domain
```

### 6.3 在 label_domains 中的集成

```python
def label_domains(
    domains: list[Domain],
    graph_data: dict,
    backend_root: Path,
    config: LabelConfig = LabelConfig(),
) -> list[Domain]:
    with ThreadPoolExecutor(max_workers=config.parallel_calls) as executor:
        futures = {
            executor.submit(
                _label_single_with_retry, d, graph_data, backend_root, config
            ): d
            for d in domains
        }
        results = []
        for future in as_completed(futures):
            domain = futures[future]
            try:
                result = future.result()
                results.append(result)
            except Exception:
                results.append(domain)  # 兜底：未标注
        return results
```

### 6.4 重试效果估算

```
假设每个域的 LLM 调用成功率 = 95%（Claude Haiku 典型值）

无重试时:
  P(全部 25 域成功) = 0.95^25 ≈ 27.7%
  P(至少 1 域失败)  = 72.3%

有 3 次重试后:
  P(单域 3 次都失败) = (1 - 0.95)^3 = 0.0125% 
  P(全部 25 域成功)  ≈ (1 - 0.000125)^25 ≈ 99.7%

结论：3 次重试将整体成功率从 27.7% 提升到 99.7%
```

---

## 7. summary.md 生成模板

### 7.1 生成函数

```python
def _write_summary(domain: Domain, result: dict, backend_root: Path):
    """根据 LLM 标注结果生成 summary.md"""
    wiki_dir = Path("wiki") / (domain.name or domain.id)
    wiki_dir.mkdir(parents=True, exist_ok=True)

    summary_path = wiki_dir / "summary.md"

    # 锁定保护：如果已锁定，不覆盖
    if is_locked(summary_path):
        print(f"  跳过 {summary_path}（已锁定）")
        return

    display_name = result.get("display_name", domain.id)
    description = result.get("description", "")
    core_flows = result.get("core_flows", [])
    deps = result.get("dependencies", [])
    key_terms = result.get("key_terms", [])

    lines = []

    # ── Frontmatter（YAML 格式）──
    lines.append("---")
    lines.append(f"domain: {display_name}")
    lines.append(f"domain_id: {domain.id}")
    lines.append(f"anchors: {domain.anchors_count()}")
    lines.append(f"files: {domain.total_files}")
    lines.append(f"updated: {datetime.now().isoformat()}")
    if domain.business_points:
        lines.append(f"business_points: {len(domain.business_points)}")
    lines.append("locked: false")
    lines.append("---")
    lines.append("")

    # ── 标题 ──
    lines.append(f"# {display_name}")
    lines.append("")

    # ── 业务目标 ──
    lines.append("## 业务目标")
    lines.append(description)
    lines.append("")

    # ── 核心流程 ──
    if core_flows:
        lines.append("## 核心流程")
        lines.append("")
        for flow in core_flows:
            flow_name = flow.get("name", "")
            steps = flow.get("steps", [])
            lines.append(f"### {flow_name}")
            for i, step in enumerate(steps, 1):
                lines.append(f"{i}. {step}")
            lines.append("")

    # ── 代码入口 ──
    lines.append("## 代码入口")
    lines.append("")
    lines.append("| 层级 | 文件 |")
    lines.append("|------|------|")
    for role_name, anchors in domain.anchors.items():
        for a in anchors[:5]:  # 每角色最多 5 个
            src = a.get("source_file", "")
            label = a.get("label", "")
            lines.append(f"| {role_name} | [{label}]({src}) |")

    lines.append("")

    # ── 关联域（含双向链接）──
    lines.append("## 关联域")
    lines.append("")
    for dep in deps:
        domain_name = dep.get("domain", "")
        reason = dep.get("reason", "")
        lines.append(f"- [[{domain_name}]] — {reason}")
    if not deps:
        lines.append("无")

    lines.append("")

    # ── 关键术语 ──
    if key_terms:
        lines.append("## 关键术语")
        lines.append("")
        for term in key_terms:
            lines.append(f"- {term}")
        lines.append("")

    # ── 业务点 ──
    if domain.business_points:
        lines.append("## 业务点")
        lines.append("")
        lines.append("| 方法 | 中文名 | 入口文件 |")
        lines.append("|------|--------|---------|")
        for bp in domain.business_points[:20]:  # 最多 20 个
            bp_name = bp.name.lstrip(".")
            bp_display = bp.display_name or bp_name
            bp_file = bp.entry_file
            lines.append(f"| `{bp_name}` | {bp_display} | {bp_file} |")
        lines.append("")

    # ── 自动生成标记 ──
    lines.append("---")
    lines.append("*此文档由 LLM 自动生成，请人工审核确认。如需锁定（防止下次被覆盖），请在第一行添加 `@locked` 标记。*")

    # 写入文件
    full_content = "\n".join(lines)
    summary_path.write_text(full_content, encoding="utf-8")
    print(f"  已生成 {summary_path}")
```

### 7.2 输出示例

```markdown
---
domain: Bin数据管理
domain_id: bin-data
anchors: 12
files: 28
updated: 2026-06-15T10:30:00
business_points: 15
locked: false
---

# Bin数据管理

## 业务目标
管理仓库中所有物理物料（库存单位/SKU）的收发存全生命周期，包括入库、出库、盘点、移库等核心操作。

## 核心流程

### 入库流程
1. 采购订单到达 → 创建入库单
2. 质检通过 → 确认入库
3. 系统自动分配库位并更新库存

### 出库流程
1. 销售订单/领料单 → 创建出库单
2. 库存预留 → 下架确认
3. 出库完成 → 更新库存台账

### 库存盘点
1. 创建盘点计划 → 盘点范围设定
2. 盘点执行 → 实盘数据录入
3. 差异处理 → 盘盈/盘亏调整

## 代码入口

| 层级 | 文件 |
|------|------|
| controller | [BinController.java](com/smc/smccloud/bin/controller/BinController.java) |
| controller | [BinOrderController.java](com/smc/smccloud/bin/controller/BinOrderController.java) |
| service_impl | [BinDataServiceImpl.java](com/smc/smccloud/bin/service/impl/BinDataServiceImpl.java) |
| mapper | [BinOrderMapper.java](com/smc/smccloud/bin/mapper/BinOrderMapper.java) |

## 关联域

- [[inventory]] — 库存管理是本域的上游，入库/出库操作直接影响库存台账
- [[supplier]] — 供应商信息查询，入库单需要关联供应商
- [[order]] — 订单数据是本域入库操作的业务来源

## 关键术语

- SKU: 库存量单位，唯一的物料标识
- 库位: 仓库中物理存放位置的编码
- 盘点: 对库存实物进行清点核对的操作

## 业务点

| 方法 | 中文名 | 入口文件 |
|------|--------|---------|
| `listBinOrderDetail` | Bin订单明细查询 | .../BinOrderController.java |
| `saveBinOrder` | 保存Bin订单 | .../BinOrderController.java |
| `deleteBinOrder` | 删除Bin订单 | .../BinOrderController.java |

---

*此文档由 LLM 自动生成，请人工审核确认。如需锁定（防止下次被覆盖），请在第一行添加 `@locked` 标记。*
```

### 7.3 输出文件结构

```
wiki/
├── index.md                          ← 业务域总目录（export.py 生成）
├── bin-data/
│   ├── summary.md                    ← 本模块生成（LLM 标注）
│   ├── rules.md                      ← 占位（人工填写）
│   ├── spec.md                       ← 占位（人工填写）
│   ├── code-map.md                   ← export.py 生成
│   └── api-docs.md                   ← api_mapper.py 生成
├── purchase/
│   ├── summary.md
│   ├── ...
└── ...
```

---

## 8. is_locked() 锁定保护机制

### 8.1 为什么需要锁定

LLM 标注是一次性操作，但 `--recluster` 或 `label-only` 重新运行时，会覆盖原有的 summary.md。如果人工已经 review 过某个域的 summary.md 并做了修改，应当保护这些修改不被 LLM 覆盖。

### 8.2 锁定标记

锁定标记在 summary.md 的 frontmatter 或第一行：

```markdown
<!-- @locked 2026-06-15 张工已确认 -->
---
domain: Bin数据管理
...
```

**检测规则**：文件第一行包含 `@locked` 字符串即视为已锁定。

### 8.3 检测函数

```python
LOCK_MARKER = "@locked"


def is_locked(summary_path: Path) -> bool:
    """
    检查 summary.md 是否被人工锁定。

    锁定标记放在文件第一行 HTML 注释中：
    <!-- @locked 2026-06-15 审核人 -->

    返回 True 表示已锁定，不应被 LLM 覆盖。
    返回 False 表示未锁定或文件不存在。
    """
    if not summary_path.exists():
        return False
    try:
        first_line = summary_path.read_text(encoding="utf-8").split("\n")[0]
        return LOCK_MARKER in first_line
    except (IOError, UnicodeDecodeError):
        return False  # 无法读取时视为未锁定（安全侧）



def safe_write_summary(summary_path: Path, content: str):
    """
    受保护写入 summary.md。

    行为:
    - 如果文件已锁定: 不覆盖，打印跳过信息
    - 如果文件未锁定: 直接写入
    - 如果文件不存在: 直接写入
    """
    if is_locked(summary_path):
        print(f"  ⚠️ 跳过 {summary_path}（已锁定，人工修改保留）")
        return

    summary_path.parent.mkdir(parents=True, exist_ok=True)
    summary_path.write_text(content, encoding="utf-8")
    print(f"  ✓ 已写入 {summary_path}")
```

### 8.4 锁定标记的语义

```markdown
<!-- @locked 2026-06-15 张三 -->
```
| 组件 | 含义 | 是否可选 |
|------|------|:--------:|
| `@locked` | 锁定标记 | 必选 |
| `2026-06-15` | 锁定日期 | 推荐 |
| `张三` | 审核人 | 推荐 |

**锁定范围**：锁定后，整个 summary.md 不会被 `_write_summary` 覆盖。锁不能阻止 `safe_write_summary` 写入（因为只读不写 → 跳过写入）。

**解除锁定**：手动删除 `@locked` 标记即可。

### 8.5 锁定保护流程图

```
                   _write_summary() 被调用
                           │
                           ▼
               summary.md 存在吗？
                   /          \
                 Yes          No
                  │            │
                  ▼            ▼
           is_locked()?    创建新文件
            /       \       (无锁定)
          Yes       No
           │         │
           ▼         ▼
       跳过写入   覆盖写入
```

---

## 9. Token 成本估算

### 9.1 每域 Token 分解

| 组件 | Token 数 | 说明 |
|------|:--------:|------|
| 系统角色设定 | ~100 | 固定部分："你是代码库业务分析专家..." |
| 包路径信息 | ~20 | 5 个包路径，每路径 ~4 tokens |
| Controller 类名列表 | ~100 | 15 个类名，每名 ~6 tokens |
| Service 类名列表 | ~100 | 15 个类名，每名 ~6 tokens |
| Mapper 类名列表 | ~80 | 10 个类名，每名 ~8 tokens |
| 跨域依赖 | ~100 | 5 个依赖域，每项 ~20 tokens |
| 源码采样 (50 行 × 2 文件) | ~2,000 | 平均 40 tokens/行 Java 代码 |
| JSON 格式说明 + 业务点列表 | ~100 | 固定模板部分 |
| **每域输入合计** | **~2,500** | |
| **每域输出** | **~300** | name + display_name + 3 flows + 3 deps + key_terms + business_points |

### 9.2 项目级 Token 估算

```
假设: 25 个域

输入 Total = 25 域 × 2,500 tokens/域 = 62,500 tokens
系统提示词 = 25 域 × 300 tokens/域 = 7,500 tokens (Claude system prompt)
──────────────────────────────────────────────
输入合计:  ~70,000 tokens

输出 Total = 25 域 × 300 tokens/域 = ~7,500 tokens

总计: ~77,500 tokens (含 input + output)
```

### 9.3 成本对比（不同模型）

| 模型 | 输入价格 (/1K) | 输出价格 (/1K) | 25 域成本 | 50 域成本 |
|------|:-------------:|:--------------:|:---------:|:---------:|
| Claude Haiku 4.5 | $0.0008 | $0.004 | **~$0.09** | ~$0.17 |
| Claude Sonnet 4.5 | $0.003 | $0.015 | **~$0.32** | ~$0.64 |
| GPT-4o-mini | $0.00015 | $0.0006 | **~$0.02** | ~$0.03 |
| GPT-4o | $0.0025 | $0.01 | **~$0.25** | ~$0.50 |
| Gemini 1.5 Flash | $0.000075 | $0.0003 | **~$0.01** | ~$0.02 |
| Gemini 1.5 Pro | $0.00125 | $0.005 | **~$0.13** | ~$0.25 |

**结论**：使用 Haiku/4o-mini/Flash 级别的模型，25 域约 $0.01-0.09，成本极低。使用旗舰模型约 $0.25-0.32，仍非常经济。

### 9.4 影响 Token 消耗的因素

```
                  ┌──────────────────────────────┐
                  │    影响 Token 消耗的因素       │
                  └──────────────────────────────┘

域数量 ──────────→ 线性影响：25 域 → 70K, 50 域 → 140K
   (受项目规模影响)

采样行数 ────────→ 正比影响：50 行/域 → 2K, 100 行/域 → 4K
   (config.sampling_lines)

类名数量 ────────→ 亚线性影响：每增加 10 个类名约 +60 tokens
   (受域规模影响)

是否 rerun ──────→ 最大影响因素：首次 → $0.01-0.32
   (--update = 0 Token)       后续 → $0

LLM 后端 ────────→ 价格差异可达 10x (Flash vs Sonnet)
   (取决于 API Key)
```

### 9.5 Token 优化策略

| 策略 | 节省 | 实现方式 | 对质量影响 |
|------|:----:|---------|:----------:|
| 减少采样行数 (50→20) | ~60% tokens | `--sampling-lines 20` | 中（LLM 看到的信息更少） |
| 减少类名列表 (15→5) | ~10% tokens | 代码中 truncate | 低（5 个核心类已足够） |
| 使用 Haiku/Flash | ~10x 成本差 | `--llm-backend openai` | 低（标注任务简单） |
| 只标注新域 | ~变量 | `label-only --new-domains` | 不变（只跑新域） |

---

## 10. 错误处理

### 10.1 错误类型层级

```python
class LabelError(Exception):
    """标注模块基础异常"""
    pass


class LlmApiError(LabelError):
    """LLM API 调用失败（网络超时、500 错误、认证失败等）"""
    pass


class LlmRateLimitError(LabelError):
    """API rate limit 超限（HTTP 429），需等待后重试"""
    pass


class LlmParseError(LabelError):
    """LLM 返回非 JSON 格式，解析失败"""
    pass


class LlmConfigError(LabelError):
    """LLM 配置错误（不支持的 backend、缺失 API Key 等）"""
    pass
```

### 10.2 错误场景与降级策略

| 错误场景 | 检测方式 | 降级策略 |
|---------|---------|---------|
| **API Key 缺失** | `_call_claude()` 中检查 `os.environ.get("ANTHROPIC_API_KEY")` | 抛出 `LlmConfigError`，`label_domains` 捕获后打印警告，域保持未标注 |
| **网络超时** | `requests` / SDK 抛出 `Timeout` | 重试 3 次（指数退避），仍失败则降级 |
| **HTTP 429 (Rate Limit)** | SDK 抛出 `RateLimitError` | 额外等待 10s + 重试 3 次 |
| **HTTP 500 / 503** | SDK 抛出 `APIStatusError` | 重试 3 次（指数退避），仍失败则降级 |
| **HTTP 401 (Unauthorized)** | SDK 抛出 `AuthenticationError` | **不重试**，立即降级（API Key 无效） |
| **JSON 解析失败** | `json.JSONDecodeError` | 尝试正则提取 JSON 块 → 重试 1 次 → 返回空 dict |
| **不支持的 backend** | `_call_llm` 中 `else` 分支 | 抛出 `LlmConfigError` |
| **源码读取失败** | `IOError` / `UnicodeDecodeError` | 跳过该文件采样，继续其他文件 |

### 10.3 API Key 缺失检测与降级

```python
def _check_api_key(config: LabelConfig):
    """检查对应后端的 API Key 是否配置"""
    env_var_map = {
        LlmBackend.CLAUDE: "ANTHROPIC_API_KEY",
        LlmBackend.OPENAI: "OPENAI_API_KEY",
        LlmBackend.GEMINI: "GOOGLE_API_KEY",
    }
    env_var = env_var_map.get(config.backend)
    if not env_var:
        raise LlmConfigError(f"未知后端: {config.backend}")

    api_key = os.environ.get(env_var)
    if not api_key:
        raise LlmConfigError(
            f"{env_var} 环境变量未设置。"
            f"请设置环境变量或在调用时指定 API Key。"
        )
```

**降级行为**：

```
label_domains() 调用时
  │
  ├─ API Key 未设置 → 打印警告:
  │   "ANTHROPIC_API_KEY 未设置，跳过 LLM 标注。所有域保持未标注状态。"
  │   → 所有 domain.name 回退为 domain.id
  │   → 所有 domain.display_name 为空
  │   → 不生成 summary.md（除非已存在且未锁定）
  │
  └─ API Key 已设置 → 正常标注流程
```

### 10.4 JSON 解析失败回退

```python
def _parse_response(response: str) -> dict:
    """
    解析 LLM 返回的 JSON。

    解析策略（按优先级）:
    1. 直接 json.loads() — 最严格的解析
    2. 正则提取 JSON 块 — 当 LLM 返回了额外文字时
    3. 返回空 dict — 解析失败兜底
    """
    # 策略 1: 直接解析
    try:
        return json.loads(response)
    except json.JSONDecodeError:
        pass

    # 策略 2: 从文本中提取 JSON 块
    # 匹配最外层 {} 包裹的内容（含嵌套 {}）
    match = re.search(r"\{[\s\S]*\}", response)
    if match:
        try:
            return json.loads(match.group(0))
        except json.JSONDecodeError:
            pass

    # 策略 3: 尝试去掉 markdown 代码块
    cleaned = re.sub(r"```(?:json)?\s*", "", response).strip()
    try:
        return json.loads(cleaned)
    except json.JSONDecodeError:
        pass

    # 策略 4: 兜底——返回空 dict，只保留 name 和 display_name 回退
    print(f"  警告：LLM 返回无法解析的响应：{response[:200]}...")
    return {"name": "", "display_name": "", "description": ""}
```

### 10.5 流水线集成

在 `pipeline.py` 中，label 模块的失败遵循"优雅降级"原则：

```python
try:
    domains = label_domains(domains, graph_data, backend_root, label_config)
except LlmConfigError as e:
    # 配置错误（如 API Key 缺失）→ 打印警告，继续使用未标注的域
    print(f"  警告：LLM 标注跳过（{e}），域保持未标注状态")
except LabelError as e:
    # 其他标注错误 → 打印错误，保留部分已标注的域
    print(f"  警告：LLM 标注部分失败（{e}）")
finally:
    # 无论标注是否成功，域数据已经可用（未标注域使用回退名称）
    export_wiki(domains, api_matches, field_map, output_dir)
```

---

## 11. 测试用例

### 11.1 测试 LLM 响应 JSON 解析（直接解析）

```python
def test_parse_valid_json():
    """验证直接从 LLM 返回的 JSON 能被正确解析。"""
    response = '{"name": "bin-data", "display_name": "Bin数据管理", "description": "管理仓库库存"}'
    result = _parse_response(response)
    assert result["name"] == "bin-data"
    assert result["display_name"] == "Bin数据管理"
    assert result["description"] == "管理仓库库存"


def test_parse_valid_json_with_code_block():
    """验证被 markdown 代码块包裹的 JSON 也能被解析。"""
    response = """```json
{
    "name": "purchase",
    "display_name": "采购管理",
    "description": "管理采购订单全生命周期"
}
```"""
    result = _parse_response(response)
    assert result["name"] == "purchase"
    assert result["display_name"] == "采购管理"
```

### 11.2 测试 LLM 响应 JSON 解析（从文本中提取）

```python
def test_parse_json_in_text():
    """验证 LLM 在 JSON 前后添加了说明文字时也能正确提取。"""
    response = (
        "分析结果如下：\n"
        '{"name": "bin-data", "display_name": "Bin数据管理"}\n'
        "以上是该业务域的分析结果。"
    )
    result = _parse_response(response)
    assert result["name"] == "bin-data"
    assert result["display_name"] == "Bin数据管理"


def test_parse_json_with_extra_fields():
    """验证 LLM 返回了额外字段时不会导致解析失败。"""
    response = """{
        "name": "bin-data",
        "display_name": "Bin数据管理",
        "description": "管理仓库库存",
        "core_flows": [
            {"name": "入库", "steps": ["创建", "确认"]}
        ],
        "dependencies": [
            {"domain": "inventory", "reason": "更新库存"}
        ],
        "key_terms": ["SKU", "库位"],
        "business_points": {
            "listBinOrder": "查询Bin订单",
            "saveBinOrder": "保存Bin订单"
        },
        "extra_unexpected_field": "不会被用到"
    }"""
    result = _parse_response(response)
    assert result["name"] == "bin-data"
    assert len(result["core_flows"]) == 1
    assert result["business_points"]["listBinOrder"] == "查询Bin订单"
    # 额外字段不会导致问题（宽容解析）
```

### 11.3 测试锁定保护机制

```python
def test_is_locked():
    """验证 is_locked 能正确检测锁定标记。"""
    import tempfile

    with tempfile.NamedTemporaryFile(mode="w", suffix=".md", delete=False, encoding="utf-8") as f:
        f.write("<!-- @locked 2026-06-15 张工已确认 -->\n# 锁定域")
        locked_path = Path(f.name)

    try:
        assert is_locked(locked_path) is True

        # 未锁定文件
        unlocked_path = locked_path.with_name("unlocked.md")
        unlocked_path.write_text("# 未锁定域\n\n内容...", encoding="utf-8")
        assert is_locked(unlocked_path) is False

        # 不存在的文件
        assert is_locked(Path("/nonexistent/summary.md")) is False
    finally:
        locked_path.unlink(missing_ok=True)
        unlocked_path.unlink(missing_ok=True)


def test_safe_write_summary_does_not_overwrite_locked():
    """验证 safe_write_summary 不会覆盖已锁定的文件。"""
    import tempfile

    with tempfile.NamedTemporaryFile(mode="w", suffix=".md", delete=False, encoding="utf-8") as f:
        f.write("<!-- @locked -->\n# 原始内容")
        existing_content = f.name

    summary_path = Path(existing_content)

    try:
        # 尝试覆盖已锁定的文件
        safe_write_summary(summary_path, "新内容")
        result = summary_path.read_text(encoding="utf-8")
        assert "# 原始内容" in result  # 未被覆盖
        assert "新内容" not in result

        # 未锁定文件可以覆盖
        unlocked_path = summary_path.with_name("unlocked_summary.md")
        safe_write_summary(unlocked_path, "新内容")
        result = unlocked_path.read_text(encoding="utf-8")
        assert result == "新内容"
    finally:
        summary_path.unlink(missing_ok=True)
        unlocked_path.unlink(missing_ok=True)
```

### 11.4 测试 Prompt 构建

```python
def test_build_prompt_contains_all_sections():
    """验证 _build_prompt 生成的 Prompt 包含所有必要的 6 个段落。"""
    domain = Domain(
        id="bin-data",
        packages=["com.smc.smccloud.bin"],
        anchors={
            "controller": [
                {"label": "BinController.java", "source_file": "bin/controller/BinController.java"},
            ],
            "service_impl": [
                {"label": "BinDataServiceImpl.java", "source_file": "bin/service/impl/BinDataServiceImpl.java"},
            ],
            "mapper": [
                {"label": "BinMapper.java", "source_file": "bin/mapper/BinMapper.java"},
            ],
        },
        dependencies={"inventory": 10, "supplier": 5},
    )

    backend_root = Path("/fake")
    config = LabelConfig(sampling_lines=50)

    prompt = _build_prompt(domain, backend_root, config)

    # 验证所有段落存在
    assert "## 包路径" in prompt
    assert "com.smc.smccloud.bin" in prompt
    assert "## Controller" in prompt
    assert "BinController.java" in prompt
    assert "## Service" in prompt
    assert "BinDataServiceImpl.java" in prompt
    assert "## Mapper/DAO" in prompt
    assert "BinMapper.java" in prompt
    assert "## 跨域依赖" in prompt
    assert "inventory" in prompt
    assert "## 源码采样" in prompt
    # JSON 输出格式
    assert "name" in prompt
    assert "display_name" in prompt
    assert "description" in prompt
    assert "core_flows" in prompt
```

### 11.5 测试 summary.md 生成

```python
def test_write_summary_generates_correct_markdown():
    """验证 _write_summary 生成的 summary.md 格式正确。"""
    import tempfile

    domain = Domain(
        id="bin-data",
        anchors={
            "controller": [
                {"label": "BinController.java", "source_file": "com/../BinController.java"},
            ],
        },
        business_points=[
            BusinessPoint(name=".listBinOrderDetail", entry_file="BinController.java"),
        ],
    )

    result = {
        "name": "bin-data",
        "display_name": "Bin数据管理",
        "description": "管理仓库库存",
        "core_flows": [
            {"name": "入库流程", "steps": ["创建入库单", "确认入库"]},
        ],
        "dependencies": [
            {"domain": "inventory", "reason": "更新库存台账"},
        ],
        "key_terms": ["SKU"],
        "business_points": {"listBinOrderDetail": "Bin订单明细查询"},
    }

    # 使用临时目录模拟 wiki 输出
    original_cwd = Path.cwd()
    with tempfile.TemporaryDirectory() as tmpdir:
        os.chdir(tmpdir)
        try:
            _write_summary(domain, result, Path("/fake"))

            summary_path = Path("wiki/bin-data/summary.md")
            assert summary_path.exists()

            content = summary_path.read_text(encoding="utf-8")

            # 验证 frontmatter
            assert content.startswith("---")
            assert "domain: Bin数据管理" in content
            assert "domain_id: bin-data" in content
            assert "locked: false" in content

            # 验证正文
            assert "# Bin数据管理" in content
            assert "## 业务目标" in content
            assert "管理仓库库存" in content
            assert "## 核心流程" in content
            assert "入库流程" in content
            assert "创建入库单" in content

            # 验证双向链接
            assert "[[inventory]]" in content
            assert "更新库存台账" in content

            # 验证关键术语
            assert "SKU" in content

            # 验证业务点表格
            assert "listBinOrderDetail" in content
            assert "Bin订单明细查询" in content

            # 验证自动生成标记
            assert "此文档由 LLM 自动生成" in content
        finally:
            os.chdir(original_cwd)
```

---

## 12. 安全考量

### 12.1 API Key 管理

| 后端 | 环境变量 | 读取位置 |
|------|---------|---------|
| Claude | `ANTHROPIC_API_KEY` | `os.environ["ANTHROPIC_API_KEY"]` |
| OpenAI | `OPENAI_API_KEY` | `os.environ["OPENAI_API_KEY"]` |
| Gemini | `GOOGLE_API_KEY` | `os.environ["GOOGLE_API_KEY"]` |

**安全规则**：
- API Key 从环境变量读取，**绝不**写入代码或配置文件
- Python 代码中 `Anthropic(api_key=...)` 的 Key 不会被日志记录
- 如果环境变量未设置，**不**尝试从 `.env` 文件自动加载（除非用户显式配置）

### 12.2 源码采样安全

```python
SENSITIVE_PATH_PATTERNS = [
    "secret", "password", "token", "credential", "keystore",
    "truststore", "application-dev.yml", "application-prod.yml",
    ".env", "credentials",
]

def _is_sensitive_path(path: str) -> bool:
    """排除包含敏感信息的文件路径"""
    path_lower = path.lower().replace("\\", "/")
    return any(p in path_lower for p in SENSITIVE_PATH_PATTERNS)
```

### 12.3 输出安全

- summary.md 不包含完整的源码内容，仅包含类名和方法签名
- Wiki 文件随代码版本管理，通过 PR review 审查内容安全性
- 不包含实际业务数据（用户数据、数据库记录等）

---

## 13. 变更记录

| 日期 | 变更内容 | 版本 |
|------|---------|:----:|
| 2026-06-15 | v2.0 重写：基于实际代码实现更新，补充三后端调用、指数退避重试、完整 Prompt 模板、锁定保护、Token 成本表、5 个测试用例 | v2.0 |
| 2026-06-11 | 初始版本（基于架构文档 v1.0） | v1.0 |

---

## 附录 A：配置项清单

用户可通过 `graph-wiki.yaml` 配置以下标注参数：

```yaml
label:
  backend: claude                  # LLM 后端 (claude/openai/gemini)
  model: claude-haiku-4-5-20251001  # 模型名称
  parallel_calls: 5                 # 并行标注数
  retry_count: 3                    # 重试次数
  sampling_lines: 50                # 每域采样行数
  skip_domains: []                  # 跳过标注的域 ID 列表
  only_domains: []                  # 只标注的域 ID 列表（空 = 全部）
```

## 附录 B：与 Graphify 关键词标注的对比

| 维度 | Graphify (关键词拼凑) | Graph-Wiki (LLM 语义标注) |
|------|---------------------|------------------------|
| **方法** | 从类名中正则提取关键词拼接 | LLM 理解业务语义后生成 |
| **输出** | `bin_data_bindata_binorder` | `"Bin数据管理"` |
| **可读性** | 极差，机器友好 | 良好，人类友好 |
| **成本** | Token 0 | ~70K tokens / 25 域 |
| **质量** | 关键词罗列，无业务含义 | 有业务理解的描述 |
| **国际化** | 类名英文 → 关键词英文 | 可生成中文域名 |
