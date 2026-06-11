# API Mapper 模块详细设计

> 对应总体设计：第四章 4.6 节  
> 状态：详细设计阶段  
> 依赖：`reuse.py`、`cluster.py`（域划分结果）

---

## 1. 模块职责

解析前端 API 文件和后端 Controller，建立前后端接口的**双向映射**。

**输入**：
- 前端 `src/api/*.js`（47 个文件，~5500 行）
- 后端 Controller Java 文件（~143 个）
- `domains.json`（来自 cluster.py）

**输出**：
- `api-map.json`（按域拆分的前后端 API 映射）
- `wiki/api-index.md`（全部 API 总览）
- `wiki/{domain}/api-docs.md`（单域 API 文档）

---

## 2. 接口定义

```python
# graph_wiki/api_mapper.py

from dataclasses import dataclass, field
from pathlib import Path
from typing import Optional

@dataclass
class FrontendApiCall:
    """前端 API 调用"""
    function_name: str           # 函数名（如 "listBinOrderDetail"）
    http_method: str             # HTTP 方法（GET/POST/PUT/DELETE）
    url: str                     # 请求 URL（如 "/api/binorder/detail"）
    params: list[dict]           # 请求参数 [{name, type_hint}]
    source_file: str             # 源文件路径
    source_line: int             # 源文件行号
    callers: list[dict]          # Vue 页面调用者 [{page, component, fields_used}]

@dataclass
class BackendEndpoint:
    """后端 Controller 端点"""
    controller_file: str         # Controller 文件路径
    controller_class: str        # Controller 类名
    method_name: str             # 方法名
    http_method: str             # HTTP 方法
    url: str                     # 映射路径
    param_type: Optional[str]    # 入参类型（如 "BinOrderQueryDTO"）
    param_fields: list[dict]     # 入参字段 [{name, type, annotations}]
    service_chain: list[str]     # 调用链 [Service.method, Mapper.method]
    return_type: Optional[str]   # 返回类型

@dataclass
class ApiMatch:
    """前后端 API 匹配结果"""
    id: str                      # 唯一 ID
    frontend: FrontendApiCall
    backend: BackendEndpoint
    match_confidence: float      # 匹配置信度（0-1）
    domain: str                  # 所属业务域

# ── 主接口 ──

def build_api_map(
    frontend_api_dir: Path,
    frontend_views_dir: Path,
    backend_controller_dir: Path,
    domains: list,  # list[Domain] from cluster.py
    backend_root: Path,
) -> list[ApiMatch]:
    """
    三步流程：
    1. parse_frontend_apis() — 解析前端 API 模块
    2. parse_backend_controllers() — 解析后端 Controller
    3. match_apis() — URL 路径匹配
    """
    frontend_apis = parse_frontend_apis(frontend_api_dir)
    frontend_apis = trace_frontend_callers(frontend_apis, frontend_views_dir)
    backend_endpoints = parse_backend_controllers(backend_controller_dir, backend_root)
    matches = match_apis(frontend_apis, backend_endpoints, domains)
    return matches
```

---

## 3. 前端 API 解析

### 3.1 axios 调用解析

```python
import re

# 匹配模式
AXIOS_CALL_PATTERNS = [
    # axios.post('/api/xxx', data) 或 axios.get('/api/xxx', {params})
    re.compile(
        r"""axios\s*\.\s*(?P<method>get|post|put|delete|patch)
            \s*\(\s*['\"](?P<url>[^'\"]+)['\"]""",
        re.VERBOSE | re.IGNORECASE
    ),
    # request({ method: 'post', url: '/api/xxx', data })
    re.compile(
        r"""method\s*:\s*['\"](?P<method>get|post|put|delete|patch)['\"]
            .*?url\s*:\s*['\"](?P<url>[^'\"]+)['\"]""",
        re.VERBOSE | re.IGNORECASE
    ),
]

def parse_frontend_apis(api_dir: Path) -> list[FrontendApiCall]:
    """
    遍历 src/api/*.js，提取每个 export 函数的 API 调用信息。

    支持模式：
      export function importBindata(data) {
        return axios.post('/api/bindata/import', data)
      }

    提取：
      - function_name: "importBindata"
      - http_method: "POST"
      - url: "/api/bindata/import"
      - params: ["data"]  (从函数签名提取)
    """
    results = []
    for file_path in api_dir.rglob("*.js"):
        if "node_modules" in str(file_path):
            continue
        source = file_path.read_text(encoding="utf-8")
        functions = extract_functions_with_axios(source, file_path)
        results.extend(functions)
    return results


def extract_functions_with_axios(source: str, file_path: Path) -> list[FrontendApiCall]:
    """从 JS 源码中提取 export function + axios 调用"""
    results = []
    # 匹配 export function xxx(params) { ... axios.xxx(url, params) ... }
    export_pattern = re.compile(
        r"""export\s+function\s+(?P<name>\w+)\s*\((?P<params>[^)]*)\)
            \s*\{(?P<body>[^}]*(?:\{[^}]*\}[^}]*)*)\}""",
        re.VERBOSE
    )
    for match in export_pattern.finditer(source):
        name = match.group("name")
        params_str = match.group("params")
        body = match.group("body")
        
        # 提取参数名
        param_names = [
            p.strip().split("=")[0].strip().rstrip("?") 
            for p in params_str.split(",") if p.strip()
        ]
        
        # 在函数体中找 axios 调用
        for pattern in AXIOS_CALL_PATTERNS:
            axios_match = pattern.search(body)
            if axios_match:
                results.append(FrontendApiCall(
                    function_name=name,
                    http_method=axios_match.group("method").upper(),
                    url=axios_match.group("url"),
                    params=[{"name": p, "type_hint": infer_js_type(p)}
                            for p in param_names if p not in ("data", "params", "config")],
                    source_file=str(file_path),
                    source_line=source[:match.start()].count("\n") + 1,
                    callers=[],
                ))
                break
    return results
```

### 3.2 前端调用者追踪

```python
def trace_frontend_callers(
    api_calls: list[FrontendApiCall],
    views_dir: Path,
) -> list[FrontendApiCall]:
    """
    扫描 src/views/**/*.vue，追踪每个 API 函数被哪些 Vue 页面调用。

    匹配模式：
      import { listBinOrderDetail } from '@/api/binorder'
      ...
      this.listBinOrderDetail(params)
    """
    # 构建 API 函数名 → 所属文件的映射
    api_index = defaultdict(list)
    for call in api_calls:
        module_name = Path(call.source_file).stem  # "binorder"
        api_index[module_name].append(call)
    
    # 扫描 Vue 文件
    for vue_file in views_dir.rglob("*.vue"):
        source = vue_file.read_text(encoding="utf-8")
        
        # 找 import { xxx } from '@/api/yyy' 语句
        imports = parse_vue_imports(source)
        
        for module_name, imported_functions in imports.items():
            if module_name in api_index:
                # 找 this.xxx() 调用
                for func in imported_functions:
                    if f"this.{func}" in source:
                        fields_used = extract_fields_in_context(
                            source, func, vue_file
                        )
                        for call in api_index[module_name]:
                            if call.function_name == func:
                                call.callers.append({
                                    "page": str(vue_file.relative_to(views_dir.parent)),
                                    "component": extract_component_name(source),
                                    "fields_used": fields_used,
                                })
    
    return api_calls


def parse_vue_imports(source: str) -> dict[str, list[str]]:
    """
    解析 import { a, b, c } from '@/api/xxx'
    返回: {"xxx": ["a", "b", "c"]}
    """
    pattern = re.compile(
        r"""import\s+\{([^}]+)\}\s+from\s+['\"]@/api/(?P<module>\w+)['\"]"""
    )
    results = {}
    for match in pattern.finditer(source):
        module = match.group("module")
        functions = [f.strip() for f in match.group(1).split(",")]
        results[module] = functions
    return results
```

---

## 4. 后端 Controller 解析

```python
def parse_backend_controllers(
    controller_dir: Path,
    backend_root: Path,
) -> list[BackendEndpoint]:
    """
    遍历后端 Controller Java 文件，提取 @RequestMapping 注解信息。

    支持：
      @RestController
      @RequestMapping("/api/binorder")
      public class BinOrderController {
          @PostMapping("/detail")
          public Result listBinOrderDetail(@RequestBody BinOrderQueryDTO dto) {
              return binOrderService.listBinOrderDetail(dto);
          }
      }
    """
    endpoints = []
    for java_file in controller_dir.rglob("*Controller.java"):
        source = java_file.read_text(encoding="utf-8")
        base_path = extract_base_path(source)
        methods = extract_endpoint_methods(source, base_path, java_file)
        endpoints.extend(methods)
    return endpoints


def extract_base_path(source: str) -> str:
    """提取 @RequestMapping("/api/binorder") 中的基础路径"""
    match = re.search(r'@RequestMapping\s*\(\s*["\']([^"\']+)["\']', source)
    return match.group(1) if match else ""


def extract_endpoint_methods(
    source: str, base_path: str, file_path: Path
) -> list[BackendEndpoint]:
    """提取类中每个 @XxxMapping 方法"""
    endpoints = []
    
    # 匹配 @PostMapping("/detail") + 方法签名
    mapping_pattern = re.compile(
        r"""@(?P<method_type>Post|Get|Put|Delete|Patch)Mapping\s*\(\s*
            ["\'](?P<path>[^"\']*)["\']\s*\)\s*
            (?:@\w+(?:\([^)]*\))?\s*)*   # 跳过其他注解
            public\s+(?P<return_type>\S+)\s+(?P<method_name>\w+)\s*
            \(\s*(?:@\w+(?:\([^)]*\))?\s*)*
            (?P<param_type>\S+)?\s*(?P<param_name>\w+)?\s*\)""",
        re.VERBOSE
    )
    
    for match in mapping_pattern.finditer(source):
        url = base_path + match.group("path")
        param_type = match.group("param_type")
        
        endpoints.append(BackendEndpoint(
            controller_file=str(file_path),
            controller_class=extract_class_name(source),
            method_name=match.group("method_name"),
            http_method=match.group("method_type").upper(),
            url=url,
            param_type=param_type,
            param_fields=extract_dto_fields(param_type, file_path),
            service_chain=extract_service_chain(source, match.group("method_name")),
            return_type=match.group("return_type"),
        ))
    
    return endpoints
```

---

## 5. URL 路径匹配

```python
def match_apis(
    frontend: list[FrontendApiCall],
    backend: list[BackendEndpoint],
    domains: list,
) -> list[ApiMatch]:
    """
    将前端 API 匹配到后端端点。

    匹配规则（按优先级）：
    1. HTTP 方法相同 + URL 路径完全相同 → 置信度 1.0
    2. HTTP 方法相同 + URL 路径去掉末尾 / 后相同 → 0.95
    3. HTTP 方法相同 + URL 参数化后匹配 (/order/123 → /order/{id}) → 0.8
    4. URL 路径的最后一个段不同（可能前端简化了）→ 0.5

    未匹配的放入 unmatched 列表。
    """
    matches = []
    unmatched_frontend = []
    unmatched_backend = list(backend)
    
    for fe_api in frontend:
        best_match = None
        best_score = 0
        
        for be_ep in unmatched_backend:
            if fe_api.http_method.upper() != be_ep.http_method.upper():
                continue
            score = url_match_score(fe_api.url, be_ep.url)
            if score > best_score:
                best_score = score
                best_match = be_ep
        
        if best_match and best_score >= 0.5:
            domain = find_domain_for_endpoint(best_match, domains)
            matches.append(ApiMatch(
                id=f"api-{slugify(fe_api.url)}-{fe_api.function_name}",
                frontend=fe_api,
                backend=best_match,
                match_confidence=best_score,
                domain=domain,
            ))
            unmatched_backend.remove(best_match)
        else:
            unmatched_frontend.append(fe_api)
    
    return matches


def url_match_score(fe_url: str, be_url: str) -> float:
    """计算两个 URL 的匹配度"""
    fe_url = fe_url.strip("/")
    be_url = be_url.strip("/")
    
    # 完全相同
    if fe_url == be_url:
        return 1.0
    # 去掉末尾 / 后相同
    if fe_url.rstrip("/") == be_url.rstrip("/"):
        return 0.95
    # 路径段数相同，参数化后匹配
    fe_segs, be_segs = fe_url.split("/"), be_url.split("/")
    if len(fe_segs) == len(be_segs):
        match_count = sum(1 for f, b in zip(fe_segs, be_segs) if f == b)
        if match_count >= len(fe_segs) - 1:
            return 0.8
    # 最后一段不同
    fe_prefix = "/".join(fe_segs[:-1])
    be_prefix = "/".join(be_segs[:-1])
    if fe_prefix == be_prefix:
        return 0.5
    
    return 0.0
```

---

## 6. 输出格式

### 6.1 api-map.json（按域拆分）

```json
{
  "domain": "Bin数据管理",
  "apis": [
    {
      "id": "api-binorder-list-detail",
      "url": "POST /api/binorder/detail",
      "frontend": {
        "api_module": "binorder.js",
        "function": "listBinOrderDetail",
        "params": ["supplierCode", "modelNo"],
        "callers": [
          {"page": "csstock/apply/index.vue", "fields_used": ["supplierCode", "modelNo"]}
        ]
      },
      "backend": {
        "controller": "BinOrderController.java",
        "method": "listBinOrderDetail()",
        "param_type": "BinOrderQueryDTO",
        "service_chain": [
          "BinOrderCalcServiceImpl.listBinOrderDetail()",
          "BindataMapper.selectByCondition()"
        ]
      },
      "match_confidence": 1.0
    }
  ]
}
```

每次 `--update` 检测到前端 api/ 或后端 Controller 文件变更时增量重建。

---

## 7. 测试用例

```python
def test_parse_frontend_axios_post():
    source = '''
    export function importBindata(data) {
      return axios.post('/api/bindata/import', data)
    }'''
    results = extract_functions_with_axios(source, Path("test.js"))
    assert len(results) == 1
    assert results[0].http_method == "POST"
    assert results[0].url == "/api/bindata/import"

def test_url_match_exact():
    assert url_match_score("/api/binorder/detail", "/api/binorder/detail") == 1.0

def test_url_match_parameterized():
    assert url_match_score("/api/binorder/detail", "/api/order/detail") == 0.0
```
