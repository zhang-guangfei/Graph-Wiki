# Label 模块详细设计

> 对应总体设计：第四章 4.3 节  
> 状态：详细设计阶段  
> 依赖：`cluster.py`（域划分 + 业务点）

---

## 1. 模块职责

用 LLM 为业务域和业务点生成人类可读的名称和描述。这是构建时**唯一消耗 Token 的步骤**。

**输入**：`domains.json`（来自 cluster.py）  
**输出**：
- 域名称 + 描述（写回 `domains.json`）
- `wiki/{domain}/summary.md`（每个域的业务摘要）
- `wiki/{domain}/business-points/{name}.md`（每个业务点的描述）

---

## 2. LLM 调用策略

```
采样策略（每个域 ~2,500 tokens）：
  ├── 包路径前缀                    ~20 tokens
  ├── Controller 类名列表            ~100 tokens
  ├── Service 类名列表               ~100 tokens
  ├── Mapper 类名列表                ~80 tokens
  ├── 1-2 个核心 Service 源码片段    ~2,000 tokens (采样)
  └── 跨域 import 统计               ~100 tokens

25 个域 × 2,500 tokens 输入 = 62,500 tokens
+ 系统提示词 300 tokens × 25 = 7,500 tokens
= 总计 ~70,000 tokens 输入

输出：每域 ~300 tokens × 25 = 7,500 tokens
成本：~$0.3 (Claude Haiku) / ~$1 (Claude Sonnet)
```

---

## 3. 接口定义

```python
# graph_wiki/label.py

from dataclasses import dataclass
from enum import Enum
from typing import Optional

class LlmBackend(Enum):
    CLAUDE = "claude"
    OPENAI = "openai"
    GEMINI = "gemini"

@dataclass
class LabelConfig:
    backend: LlmBackend = LlmBackend.CLAUDE
    model: str = "claude-haiku-4-5-20251001"   # 用 Haiku，成本优先
    parallel_calls: int = 5                      # 并行度（受 API rate limit 限制）
    retry_count: int = 3                         # 失败重试次数
    sampling_lines: int = 50                     # 每个 Service 采样的源码行数

def label_domains(
    domains: list,                     # Domain 列表
    graph: dict,                       # graph.json 数据（用于获取节点详情）
    backend_root: Path,                # 后端源码根目录
    config: LabelConfig = LabelConfig(),
) -> list:
    """
    对每个域调用 LLM，生成名称、描述、核心流程。

    策略：
    1. 并行调用（parallel_calls=5 路并发）
    2. 每个域独立调用，互不影响
    3. 调用失败重试 retry_count 次
    4. 结果写回 domains.json 和 summary.md
    """
    import concurrent.futures
    
    results = []
    with concurrent.futures.ThreadPoolExecutor(max_workers=config.parallel_calls) as executor:
        futures = {
            executor.submit(label_single_domain, domain, graph, backend_root, config): domain
            for domain in domains
        }
        for future in concurrent.futures.as_completed(futures):
            domain = futures[future]
            try:
                result = future.result()
                results.append(result)
            except Exception as e:
                # 重试逻辑
                for attempt in range(config.retry_count):
                    try:
                        result = label_single_domain(domain, graph, backend_root, config)
                        results.append(result)
                        break
                    except:
                        if attempt == config.retry_count - 1:
                            results.append(domain)  # 返回未标注的域
    return results


def label_single_domain(
    domain, graph, backend_root: Path, config: LabelConfig
):
    """
    标注单个域。

    步骤：
    1. 采样核心 Service 源码（1-2 个文件，各前 50 行）
    2. 组装 Prompt
    3. 调用 LLM API
    4. 解析返回的 JSON
    5. 写入 summary.md
    """
    # Step 1: 采样源码
    service_sample = sample_service_code(domain, backend_root, config.sampling_lines)
    
    # Step 2: 组装 Prompt
    prompt = build_label_prompt(domain, service_sample)
    
    # Step 3: 调用 LLM
    response = call_llm(prompt, config)
    
    # Step 4: 解析结果
    result = parse_llm_response(response)
    
    # Step 5: 写回 domain 对象
    domain.name = result["name"]
    domain.display_name = result["display_name"]
    # ... 写入 business points 的 display_name
    
    # Step 6: 生成 summary.md
    generate_summary_md(domain, result)
    
    return domain
```

---

## 4. Prompt 模板

```python
def build_label_prompt(domain, service_sample: str) -> str:
    """
    组装 LLM Prompt。

    结构：
    1. 系统角色设定
    2. 包路径 + 类名列表（结构化数据）
    3. 源码采样（1-2 个核心 Service）
    4. JSON 输出格式要求
    """
    return f"""你是一个代码库业务分析专家。请根据以下信息，分析一个 Java 项目的业务模块。

## 包路径信息
{format_package_info(domain)}

## 核心类列表
### Controller ({len(domain.controllers) if hasattr(domain, 'controllers') else 0} 个)
{format_class_list(domain, 'controller')}

### Service ({len(domain.services) if hasattr(domain, 'services') else 0} 个)
{format_class_list(domain, 'service')}

### Mapper/DAO
{format_class_list(domain, 'mapper')}

## 核心 Service 源码（采样前 50 行）
{service_sample}

## 跨域依赖
{format_dependencies(domain)}

---

请返回 JSON 格式（不要包含其他文字）：

{{
  "name": "业务域英文 ID（2-3 个英文单词，如 bin-data 或 purchase-order）",
  "display_name": "业务域中文名称（2-5 个汉字）",
  "description": "一句话业务目标描述",
  "core_flows": [
    {{"name": "流程名", "steps": ["步骤1", "步骤2", ...]}}
  ],
  "dependencies": [
    {{"domain": "依赖的域", "reason": "依赖原因"}}
  ],
  "key_terms": ["关键业务术语1", "关键业务术语2"]
}}"""
```

---

## 5. LLM 调用实现

```python
def call_llm(prompt: str, config: LabelConfig) -> str:
    """
    调用 LLM API。
    
    支持三种后端：
    - Claude: 通过 Anthropic SDK
    - OpenAI: 通过 OpenAI SDK  
    - Gemini: 通过 Google AI SDK
    """
    if config.backend == LlmBackend.CLAUDE:
        return call_claude(prompt, config.model)
    elif config.backend == LlmBackend.OPENAI:
        return call_openai(prompt, config.model)
    elif config.backend == LlmBackend.GEMINI:
        return call_gemini(prompt, config.model)


def call_claude(prompt: str, model: str) -> str:
    """通过 Anthropic API 调用 Claude"""
    from anthropic import Anthropic
    
    client = Anthropic()  # 使用 ANTHROPIC_API_KEY 环境变量
    
    response = client.messages.create(
        model=model,
        max_tokens=1024,
        system="你是一个代码库业务分析专家。请始终返回有效的 JSON。",
        messages=[{"role": "user", "content": prompt}],
    )
    return response.content[0].text


def parse_llm_response(response: str) -> dict:
    """解析 LLM 返回的 JSON"""
    import json
    
    # 尝试直接解析
    try:
        return json.loads(response)
    except json.JSONDecodeError:
        pass
    
    # 尝试提取 JSON 块
    import re
    match = re.search(r'\{[\s\S]*\}', response)
    if match:
        try:
            return json.loads(match.group(0))
        except json.JSONDecodeError:
            pass
    
    raise ValueError(f"无法解析 LLM 响应: {response[:200]}")
```

---

## 6. summary.md 生成

```python
def generate_summary_md(domain, result: dict):
    """根据 LLM 标注结果生成 summary.md"""
    md = f"""---
domain: {result['display_name']}
anchors: {len(domain.anchors) if hasattr(domain, 'anchors') else 0}
updated: {datetime.now().isoformat()}
---

# {result['display_name']}

## 业务目标
{result['description']}

## 核心流程
"""
    for flow in result.get('core_flows', []):
        md += f"\n### {flow['name']}\n"
        for i, step in enumerate(flow['steps'], 1):
            md += f"{i}. {step}\n"
    
    md += f"""
## 代码入口

| 层级 | 文件 |
|------|------|
"""
    for anchor in domain.anchors_flat()[:10]:
        role = anchor.get('_role', '?')
        file = anchor.get('source_file', '?')
        md += f"| {role} | [{Path(file).name}]({file}) |\n"
    
    md += f"""
## 关联域
"""
    for dep in result.get('dependencies', []):
        md += f"- [[{dep['domain']}]] — {dep['reason']}\n"
    
    md += f"""
## 关键术语
"""
    for term in result.get('key_terms', []):
        md += f"- {term}\n"
    
    md += "\n---\n*此文档由 LLM 自动生成，请人工审核确认。*\n"
    
    # 写入文件
    output_path = Path(f"wiki/{result['name']}/summary.md")
    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text(md, encoding="utf-8")
```

---

## 7. 保护机制

```python
def is_locked(summary_path: Path) -> bool:
    """
    检查 summary.md 是否被人工锁定。
    
    锁定标志：文件的第一行注释包含 @locked
    <!-- @locked 2026-06-15 人工确认 -->
    """
    if not summary_path.exists():
        return False
    first_line = summary_path.read_text(encoding="utf-8").split("\n")[0]
    return "@locked" in first_line

def safe_write_summary(summary_path: Path, content: str):
    """受保护写入：如果已锁定，不覆盖"""
    if is_locked(summary_path):
        print(f"跳过 {summary_path}（已锁定）")
        return
    summary_path.write_text(content, encoding="utf-8")
```

---

## 8. 错误处理

```python
class LabelError(Exception): pass

class LlmApiError(LabelError):
    """LLM API 调用失败"""

class LlmParseError(LabelError):
    """LLM 返回非 JSON 格式"""

class LlmRateLimitError(LabelError):
    """API rate limit 超限，需等待重试"""

RETRY_DELAYS = [5, 15, 30]  # 秒，指数退避

def retry_with_backoff(func, *args, retries=3):
    for attempt in range(retries):
        try:
            return func(*args)
        except (LlmRateLimitError, LlmApiError) as e:
            if attempt == retries - 1:
                raise
            time.sleep(RETRY_DELAYS[attempt])
```

---

## 9. 测试用例

```python
def test_parse_valid_json():
    response = '{"name": "bin-data", "display_name": "Bin数据管理"}'
    result = parse_llm_response(response)
    assert result["name"] == "bin-data"

def test_parse_json_in_text():
    response = '分析结果：\n{"name": "bin-data", "display_name": "Bin数据管理"}\n以上是分析结果。'
    result = parse_llm_response(response)
    assert result["name"] == "bin-data"

def test_is_locked():
    path = Path("/tmp/test_locked.md")
    path.write_text("<!-- @locked -->\n# Title")
    assert is_locked(path) == True
```
