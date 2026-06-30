# API Mapper 模块详细设计

> 对应总体设计：第七章 [7.6 节](../architecture/graph-wiki%20工程架构设计.md#46-api-映射模块-apimapper)
> 状态：详细设计阶段（v2.0，重写）
> 依赖：`reuse.py`（复用层）、`cluster.py`（域划分结果）、`models.py`（数据模型）
> 参考：`models-design.md`（API Mapper 相关数据类：`FrontendApiCall`、`BackendEndpoint`、`ApiMatch`）

---

## 1. 模块职责

### 1.1 核心职责

解析前端 API 文件和后端 Controller，建立前后端接口的**双向映射**，解决以下问题：

- "这个前端页面提交的字段，对应后端哪个接口的哪个参数？"
- "这个后端接口被哪些前端页面调用？字段传值链路是什么？"
- "整个项目有多少 API？完整的接口文档在哪里？"
- "前端修改了 API 调用参数，后端对应的 DTO 需要同步修改吗？"

```
输入:  前端 src/api/*.js      → axios 调用 + export function
      前端 src/views/*.vue     → import 语句 + this.xxx() 调用
      后端 *Controller.java    → @XxxMapping + @RequestMapping 注解
      业务域划分 (list[Domain]) → 来自 cluster.py，用于端点域归属

输出:  list[ApiMatch]          → 前后端 API 匹配列表
      api-map.json             → JSON 序列化（按域拆分）
      wiki/api-index.md        → 全部 API 总览
      wiki/{domain}/api-docs.md → 单域 API 文档
```

### 1.2 为什么需要前后端 API 映射

Graphify 的原始设计仅覆盖了后端内部链路（Controller → Service → Mapper → DB），缺少前端到后端的对接。开发者无法回答：

- 前端一个字段变更（如 `supplierCode` → `supplierId`），需要同步修改哪些后端 DTO/Entity？
- 一个后端接口重构（如 URL 从 `/api/binorder/detail` 改为 `/api/bin-order/details`），会影响哪些前端页面？
- 前后端 API 的不一致（接口已实现但前端未调用，或前端已调用但后端未实现）

Graph-Wiki 的 API Mapping 模块填补了这一空缺，通过**纯静态分析**（零 Token 成本）建立前后端之间的完整映射链路。

### 1.3 解决的完整链路

```
前端 Vue 页面          前端 API 模块          后端 Controller        后端 Service/Mapper     数据库
────────────────    ────────────────      ──────────────────     ───────────────────     ──────
csstock/apply/      binorder.js           BinOrderController     BinOrderCalcService     bin_order_
index.vue           .listBinOrderDetail() .listBinOrderDetail()  .listBinOrderDetail()   detail
  │                   │                     │                      │                       │
  │ import            │ axios.post(         │ @PostMapping(         │ 参数: BinOrderDTO     │ @TableField
  │ binorder.js       │ '/api/binorder/     │ '/api/binorder/      │                      │ ("supplier_code")
  │                   │  detail',           │  detail')             │                      │
  │                   │  {supplierCode,     │ 入参: BinOrderDTO    │                      │
  │                   │   modelNo}          │                      │                      │
  │                   │                     │                      │                      │
  ├─ 字段: supplierCode ──→ 请求体字段 ────→ DTO 字段 ──────────→ Entity 字段 ──────────→ DB 列
  └─ 字段: modelNo ──────→ 请求体字段 ────→ DTO 字段 ──────────→ Entity 字段 ──────────→ DB 列
                                                                                      
  ↑                    ↑                    ↑                      ↑                    ↑
  └── api_mapper ──────┘                    └── field_mapper ─────────────────────────────┘
      (本模块覆盖)                                (field_mapper.py 覆盖)
```

**本模块覆盖范围**：前端 Vue 页面 → 前端 API 模块 → 后端 Controller，即前 3 层。
**field_mapper 覆盖**：后端 DTO → Entity → DB 列，即后 3 层（见 `field-mapper-design.md`）。

### 1.4 与其他模块的关系

```
上游: cluster.py (list[Domain], 含域锚点信息)
         │
         ▼
  api_mapper.py            ← 本模块
         │
         ├──→ field_mapper.py (消费 ApiMatch 做字段级追踪)
         ├──→ export.py (消费 ApiMatch 生成 wiki/api-docs.md)
         └──→ visualize.py (消费 Domain 中的 API 信息)
```

### 1.5 数据类依赖关系

本模块使用 `models.py` 中定义的以下类型（完整定义见 `models-design.md` §2.6-2.8）：

| 类型 | 用途 | 说明 |
|------|------|------|
| `FrontendApiCall` | 数据类，代表前端一个 export function | 在 `parse_frontend_apis()` 中实例化 |
| `BackendEndpoint` | 数据类，代表后端一个 @XxxMapping 方法 | 在 `parse_backend_controllers()` 中实例化 |
| `ApiMatch` | 数据类，代表一次前后端匹配结果 | 在 `match_apis()` 中实例化 |
| `Domain` | 数据类，读取 anchors 做域归属匹配 | 从 cluster 模块传入 |

---

## 2. 接口定义

### 2.1 主接口

```python
def build_api_map(
    frontend_api_dir: Path,
    frontend_views_dir: Path,
    backend_src_dir: Path,
    domains: list[Domain],
    backend_root: Path,
    project_root: Path | None = None,
) -> list[ApiMatch]:
    """
    三步流程：
    1. parse_frontend_apis()  — 解析前端 API 模块
    2. trace_frontend_callers() — 追踪 Vue 页面调用者
    3. parse_backend_controllers() — 解析后端 Controller
    4. match_apis() — URL 路径匹配 + 域归属分配
    5. infer_frontend_api_domain() — 后端不可用或未匹配时的前端优先归域
    """
```

**参数说明**：

| 参数 | 类型 | 含义 |
|------|------|------|
| `frontend_api_dir` | `Path` | 前端 `src/api/` 目录（含 JS/TS API 定义文件） |
| `frontend_views_dir` | `Path` | 前端 `src/views/` 目录（含 Vue 页面组件） |
| `backend_src_dir` | `Path` | 后端 Controller 源码目录（递归扫描 `*Controller.java`） |
| `domains` | `list[Domain]` | cluster 模块产出的域列表，用于端点→域归属匹配 |
| `backend_root` | `Path` | 后端项目根目录，用于将 Controller 文件路径转为相对路径 |
| `project_root` | `Path | None` | 项目根目录，用于前端 API 文件、views 路径、URL 业务段归域；None 时回退为 `frontend_api_dir.parent.parent` |

**返回值**：`list[ApiMatch]` — 前后端 API 匹配结果列表。匹配失败的 API 不会出现在列表中（信息记录在内部用于降级报告）。

**异常**：

| 异常类 | 抛出条件 |
|--------|---------|
| 无（设计原则：前端或后端不存在时静默降级，返回空列表） |

### 2.2 四步子函数签名

```python
def parse_frontend_apis(api_dir: Path) -> list[FrontendApiCall]
    # Step 1: 前端 API 解析
    # 输入: 前端 src/api/ 目录
    # 输出: list[FrontendApiCall] 前端 API 调用列表

def trace_frontend_callers(api_calls: list[FrontendApiCall], views_dir: Path) -> list[FrontendApiCall]
    # Step 2: 前端调用者追踪
    # 输入: FrontendApiCall 列表 + src/views/ 目录
    # 输出: FrontendApiCall 列表（callers 字段已填充）

def parse_backend_controllers(controller_dir: Path, backend_root: Path) -> list[BackendEndpoint]
    # Step 3: 后端 Controller 解析
    # 输入: 后端 Controller 源码目录
    # 输出: list[BackendEndpoint] 后端端点列表

def match_apis(frontend: list[FrontendApiCall], backend: list[BackendEndpoint], domains: list[Domain]) -> list[ApiMatch]
    # Step 4: URL 匹配 + 域归属
    # 输入: 前端 API + 后端端点 + 域定义
    # 输出: list[ApiMatch] 匹配列表

def infer_frontend_api_domain(api_call: FrontendApiCall, domains: list[Domain], project_root: Path) -> str
    # Step 5: 前端优先归域
    # 输入: 单个前端 API + 域定义 + 项目根
    # 输出: domain id/name/display_name 或 ""（未分类）
```

### 2.3 辅助函数

```python
def url_match_score(fe_url: str, be_url: str) -> float
    # URL 匹配评分: 0.0 ~ 1.0

def _find_endpoint_domain(ep: BackendEndpoint, domains: list[Domain]) -> str
    # 端点→域归属查找

# ── Phase 4+ 增强函数（当前设计预留，v1.0 暂不实现）──

def extract_dto_fields(param_type: str, controller_file: Path) -> list[dict]
    # 提取 Controller 方法入参 DTO 的字段列表（解析 DTO 类源码）
    # Phase 4+ 实现，当前返回空列表

def extract_service_chain(source: str, method_name: str) -> list[str]
    # 从 Controller 方法体中提取 Service/Mapper 调用链
    # Phase 4+ 实现，当前返回空列表
```

---

## 3. 前端 API 解析

### 3.1 总体流程

```
前端 src/api/*.js
       │
       ├── 遍历目录下所有 .js 文件（跳过 node_modules）
       │
       ├── 对每个文件：
       │     ├── 匹配 export function xxx(params) { ... }
       │     ├── 从函数体匹配 axios 调用模式
       │     └── 提取: function_name, http_method, url, params
       │
       └── 返回 list[FrontendApiCall]
```

### 3.2 axios 调用模式匹配

支持 **2 种 axios 调用模式**：

**模式 1：直接 axios 调用**（最常见）

```javascript
export function importBindata(data) {
  return axios.post('/api/bindata/import', data)
}
```

**模式 2：request 配置模式**（使用 `method/url` 配置对象）

```javascript
export function listBinOrderDetail(data) {
  return request({
    method: 'post',
    url: '/api/binorder/detail',
    data: data
  })
}
```

**正则匹配**：

```python
AXIOS_CALL_PATTERNS = [
    # 模式 1: axios.post('/api/xxx', data)
    re.compile(
        r"""axios\s*\.\s*(?P<method>get|post|put|delete|patch)
            \s*\(\s*['\"](?P<url>[^'\"]+)['\"]""",
        re.VERBOSE | re.IGNORECASE,
    ),
    # 模式 2: request({ method: 'post', url: '/api/xxx', data })
    re.compile(
        r"""method\s*:\s*['\"](?P<method>get|post|put|delete|patch)['\"]
            .*?url\s*:\s*['\"](?P<url>[^'\"]+)['\"]""",
        re.VERBOSE | re.IGNORECASE,
    ),
]
```

**为什么不使用 tree-sitter AST 解析？**

当前 API 模块设计使用**正则表达式**而非 tree-sitter AST 进行前端 API 解析，原因如下：

| 维度 | 正则表达是 | tree-sitter AST |
|------|:----------:|:---------------:|
| 依赖安装 | 零（Python 内置 `re`） | 需要安装 `tree-sitter` + 语言 grammar |
| 处理速度 | ~50ms（47 个 API 文件） | ~500ms（含加载 grammar 时间） |
| 准确率 | ~95%（常见模式全覆盖） | ~99% |
| 代码复杂度 | 简单，~50 行 | 复杂，~200 行 |
| 误报率 | 在字符串模板拼接 URL 时可能误报 | 结构化解析，零误报 |

**设计决策**：v1.0 采用正则表达，因为：
1. 前端 API 文件的模式高度统一（`export function` + `axios.xxx()` / `request({...})`）
2. 视频面试约 95% 的调用可以被正确提取
3. 零依赖，即时可用
4. Phase 2 如需更高准确率可升级为 tree-sitter

### 3.3 export function 提取

```python
def _extract_functions(source: str, file_path: Path) -> list[FrontendApiCall]:
    """
    从 JS 源码中提取 export function + axios 调用。
    
    支持:
      export function xxx(params) { ... }
      export async function xxx(params) { ... }
    
    提取:
      - function_name: 函数名
      - http_method: 从 axios 调用中提取
      - url: 从 axios 调用中提取
      - params: 从函数签名中提取（排除 data/params/config 等泛用参数名）
      - source_file: 文件路径
      - source_line: 函数起始行号
    """
    results = []
    export_pattern = re.compile(
        r"""export\s+(?:async\s+)?function\s+(?P<name>\w+)\s*
            \((?P<params>[^)]*)\)\s*\{(?P<body>(?:[^{}]|\{[^{}]*\})*)\}""",
        re.VERBOSE,
    )
    for match in export_pattern.finditer(source):
        name = match.group("name")
        params_str = match.group("body")
        body = match.group("body")

        # 提取参数名，排除泛用参数
        param_names = [
            p.strip().split("=")[0].strip().rstrip("?")
            for p in params_str.split(",")
            if p.strip() and p.strip() not in ("data", "params", "config")
        ]

        for pattern in AXIOS_CALL_PATTERNS:
            m = pattern.search(body)
            if m:
                api_call = FrontendApiCall(
                    function_name=name,
                    http_method=m.group("method").upper(),
                    url=m.group("url"),
                    params=[{"name": p} for p in param_names],
                    source_file=str(file_path),
                    source_line=source[:match.start()].count("\n") + 1,
                )
                results.append(api_call)
                break
    return results
```

**边界条件**：

| 条件 | 行为 |
|------|------|
| 文件中无 `export function` | 返回空列表，该文件跳过 |
| 函数体不包含 axios 调用 | 跳过该函数（非 API 层的辅助函数） |
| `export const xxx = () => { axios... }` | **不支持**箭头函数模式（v1.0 范围外） |
| URL 使用模板字符串（`（`url + param + `）`） | URL 提取失败，该函数跳过 |
| 多个同级 axios 调用在同一函数体 | 取第一个匹配的模式 |
| 参数为解构 `{a, b}` | 提取完整解构字符串（含大括号） |

### 3.4 前端调用者追踪

**目的**：建立了 API 函数 → Vue 页面的反向引用，回答"一个后端接口被哪些前端页面调用"。

```python
def trace_frontend_callers(
    api_calls: list[FrontendApiCall],
    views_dir: Path,
) -> list[FrontendApiCall]:
    """
    扫描 src/views/**/*.vue，追踪每个 API 函数被哪些 Vue 页面调用。
    
    匹配模式：
      1. import { listBinOrderDetail } from '@/api/binorder'
      2. this.listBinOrderDetail(params) 或 listBinOrderDetail(params)
    """
```

**三个追踪步骤**：

```
Step 2.1: 构建 API 索引
  {module_name: [FrontendApiCall, ...]}
  例: {"binorder": [FrontendApiCall(function_name="listBinOrderDetail"), ...]}

Step 2.2: 扫描 Vue 文件
  对每个 .vue 文件：
    a. 解析 import { xxx } from '@/api/yyy'   → 得到 {yyy: [xxx]}
    b. 检查 yyy 是否在 API 索引中
    c. 如果是，检查 this.xxx() 或 xxx() 是否出现在源文件中

Step 2.3: 填充 caller 信息
  对匹配到的调用：
    call.callers.append({
        "page": "csstock/apply/index.vue",            # 相对路径
        "fields_used": ["supplierCode", "modelNo"],   # 从 this.xxx({...}) 解构提取
    })
```

**`parse_vue_imports()` 实现**：

```python
def _parse_vue_imports(source: str) -> dict[str, list[str]]:
    """
    解析 import { a, b, c } from '@/api/xxx'
    返回: {"xxx": ["a", "b", "c"]}
    
    支持:
      - import { listBinOrderDetail } from '@/api/binorder'
      - import { listBinOrderDetail as queryDetail } from '@/api/binorder'
        → 跟踪原始名 "listBinOrderDetail"
    """
    pattern = re.compile(
        r"""import\s+\{([^}]+)\}\s+from\s+['\"]@/api/(?P<module>\w+)['\"]"""
    )
    results = {}
    for match in pattern.finditer(source):
        module = match.group("module")
        functions = [
            f.strip().split(" as ")[0].strip()   # 取 as 前的原始函数名
            for f in match.group(1).split(",")
        ]
        results[module] = functions
    return results
```

**提取调用上下文字段**：

```python
def _extract_used_fields(source: str, func_name: str) -> list[str]:
    """
    从 this.xxx({supplierCode: this.form.supplierCode, ...}) 中
    提取在调用点实际使用的字段名。
    
    匹配模式：
      this.listBinOrderDetail({supplierCode: ..., modelNo: ...})
      → ["supplierCode", "modelNo"]
    """
    fields = []
    call_site = re.search(
        r"this\." + re.escape(func_name) + r"\s*\(\s*\{([^}]*)\}\s*\)", source
    )
    if call_site:
        for field in re.findall(r"(\w+)\s*:", call_site.group(1)):
            fields.append(field)
    return fields
```

**边界条件**：

| 条件 | 行为 |
|------|------|
| views 目录不存在 | 跳过追踪，callers 保留空列表 |
| Vue 文件语法错误无法解析 | 跳过该文件（`try/except` 容错） |
| 同一函数被多个页面 import | 多个 caller 追加到列表 |
| 使用 `as` 别名 import | 解析 `原始名 as 别名`，用原始名匹配 |
| 非 `@/api/` 路径的 import（如 `@/utils/`） | 不追踪（不是 API 模块） |
| Vue 文件中直接调用（无 `this.`） | 额外匹配 `xxx(` 模式 |

---

## 4. 后端 Controller 解析

### 4.1 总体流程

```
后端 *Controller.java
       │
       ├── 递归扫描所有 *Controller.java（排除 src/test/ 路径）
       │
       ├── 对每个文件：
       │     ├── 提取 @RequestMapping base_path
       │     ├── 提取类名
       │     ├── 对每个 @XxxMapping 方法：
       │     │     ├── 获取 HTTP 方法类型
       │     │     ├── 拼接完整 url = base_path + method_path
       │     │     ├── 提取入参类型名
       │     │     └── 提取返回值类型
       │     └── 返回 list[BackendEndpoint]
       │
       └── 返回 list[BackendEndpoint]
```

### 4.2 文件扫描规则

```python
def parse_backend_controllers(
    controller_dir: Path, backend_root: Path
) -> list[BackendEndpoint]:
    endpoints = []
    for java_file in controller_dir.rglob("*Controller.java"):
        # 排除测试路径
        if "/src/test/" in str(java_file):
            continue
        try:
            source = java_file.read_text(encoding="utf-8")
        except (IOError, UnicodeDecodeError):
            continue
        base_path = _extract_base_path(source)
        endpoints.extend(
            _extract_endpoints(source, base_path, java_file, backend_root)
        )
    return endpoints
```

**扫描规则**：

| 规则 | 说明 |
|------|------|
| 文件名模式 | `*Controller.java`（大小写敏感，因 Java 文件名就是类名） |
| 排除路径 | `**/src/test/**/*Controller.java` |
| 编码 | UTF-8（回退：跳过无法解码的文件） |
| 自定义后缀 | 项目可通过 `graph-wiki.yaml` 配置额外后缀（如 `*Facade.java`） |

### 4.3 注解解析

支持 **5 种 Spring MVC 注解**：

| 注解 | HTTP 方法 | 示例 |
|------|-----------|------|
| `@PostMapping("/detail")` | POST | `@PostMapping("/api/binorder/detail")` |
| `@GetMapping("/detail")` | GET | `@GetMapping("/api/binorder/detail")` |
| `@PutMapping("/detail")` | PUT | `@PutMapping("/api/binorder/detail")` |
| `@DeleteMapping("/detail")` | DELETE | `@DeleteMapping("/api/binorder/detail")` |
| `@PatchMapping("/detail")` | PATCH | `@PatchMapping("/api/binorder/detail")` |

**基路径提取**：

```python
def _extract_base_path(source: str) -> str:
    """
    提取类级别的 @RequestMapping base_path。
    
    支持:
      @RequestMapping("/api/binorder")        → "/api/binorder"
      @RequestMapping(value = "/api/order")    → "/api/order"
      @RequestMapping("/api/binorder")         → "/api/binorder" (类级与多个方法级拼接)
      ｜ 无 @RequestMapping                    → "" (空字符串)
    """
    match = re.search(r'@RequestMapping\s*\(\s*["\']([^"\']+)["\']', source)
    return match.group(1) if match else ""
```

**方法端点提取**：

```python
def _extract_endpoints(
    source: str, base_path: str, file_path: Path, backend_root: Path
) -> list[BackendEndpoint]:
    """
    提取每个 @XxxMapping 方法的信息。
    
    支持的 Controller 方法结构:
      @PostMapping("/detail")
      public Result listBinOrderDetail(@RequestBody BinOrderQueryDTO dto) {
          return binOrderService.listBinOrderDetail(dto);
      }
      
      @GetMapping("/{id}")
      public Result getDetail(@PathVariable Long id) {
          return binOrderService.getDetail(id);
      }
    """
    endpoints = []
    mapping_pattern = re.compile(
        r"""@(?P<method_type>Post|Get|Put|Delete|Patch)Mapping\s*\(\s*
            ["\'](?P<path>[^"\']*)["\']\s*\)\s*
            (?:@\w+(?:\([^)]*\))?\s*)*        # 跳过其他注解
            public\s+(?P<return_type>\S+)\s+(?P<method_name>\w+)\s*
            \(\s*(?:@\w+(?:\([^)]*\))?\s*)*   # 跳过参数注解
            (?P<param_type>\S+)?\s*(?P<param_name>\w+)?\s*\)""",
        re.VERBOSE,
    )
    for match in mapping_pattern.finditer(source):
        url = (base_path + match.group("path")).rstrip("/")
        if not url.startswith("/"):
            url = "/" + url
        endpoints.append(BackendEndpoint(
            controller_file=str(file_path.relative_to(backend_root)),
            controller_class=_extract_class_name(source),
            method_name=match.group("method_name"),
            http_method=match.group("method_type").upper(),
            url=url,
            param_type=match.group("param_type"),
            return_type=match.group("return_type"),
        ))
    return endpoints
```

**不支持的模式**（v1.0 范围外）：

| 模式 | 原因 | 优先级 |
|------|------|:------:|
| `@RequestMapping(method = RequestMethod.POST)` | 类级 @RequestMapping 的 method 属性 | 🔴 低 |
| 多个 URL 路径 `@PostMapping({"/a", "/b"})` | 多路径映射，常见于 RESTful 设计 | 🟡 中 |
| `@XxxMapping(path = "/detail")` | 使用 `path` 属性而非直接字符串 | 🟡 中 |
| 通配符路径 `@GetMapping("/api/**")` | 通配符匹配，无法确定具体 URL | 🟢 低 |
| 嵌套 Controller（内部类） | 极少使用 | 🟢 低 |
| Kotlin Controller | 语法与 Java 不同 | 🔴 低 |

**边界条件**：

| 条件 | 行为 |
|------|------|
| 类无 @RequestMapping | base_path = ""，url 仅由 method 路径组成 |
| 方法无参数 | param_type = None |
| 多个 @RequestMapping 类级注解 | 取第一个匹配（极少有两个） |
| 文件名包含多个 `Controller`（如 `BinOrderControllerHelper.java`） | 仍被扫描（符合 `*Controller.java` 模式） |
| 非 Controller 文件（如 `BinConfig.java`） | 不会被扫描（不匹配 `*Controller.java`） |

---

## 5. URL 路径匹配算法

### 5.1 匹配原则

URL 匹配是整个 API Mapper 的核心。由于前后端 URL 通常由同一个团队遵循同一份 API 文档编写，URL 结构高度一致。匹配算法基于 **HTTP 方法 + URL 路径** 的组合，分为 4 级评分：

| 优先级 | 条件 | 置信度 | 含义 |
|:------:|------|:------:|------|
| 1 | HTTP 方法相同 + URL **完全相同** | 1.0 | 精确匹配 |
| 2 | HTTP 方法相同 + URL **去末尾斜杠**后相同 | 0.95 | 格式差异（常见于 `/api/xxx/` vs `/api/xxx`） |
| 3 | HTTP 方法相同 + URL **路径段数相同，参数化后匹配** | 0.8 | 路径参数（`/api/order/123` vs `/api/order/{id}`） |
| 4 | HTTP 方法相同 + URL **前缀相同，最后一段不同** | 0.5 | 模糊匹配（前端简化了路径末尾） |
| — | HTTP 方法不同 | 0.0 | 不匹配 |

### 5.2 `url_match_score()` 实现

```python
def url_match_score(fe_url: str, be_url: str) -> float:
    """
    计算前端 URL 到后端 URL 的匹配度。
    
    前端 URL 通常来自: axios.get('/api/binorder/detail')
    后端 URL 来自: @RequestMapping("/api/binorder") + @GetMapping("/detail")
    
    匹配流程:
      1. 两端去首尾 / → 比较字符串完全相等 → 1.0
      2. 去末尾 / 后相等（/api/xxx/ == /api/xxx） → 0.95
      3. 路径段数相同，逐段比较 → 至少 n-1 段相同 → 0.8
      4. 路径最后一段不同但前缀相同 → 0.5
      5. 无匹配 → 0.0
    """
    fe_url = fe_url.strip("/")
    be_url = be_url.strip("/")
    
    # 完全匹配
    if fe_url == be_url:
        return 1.0
    # 去掉尾斜杠后相同
    if fe_url.rstrip("/") == be_url.rstrip("/"):
        return 0.95
    # 路径段数相同，参数化后匹配
    fe_segs, be_segs = fe_url.split("/"), be_url.split("/")
    if len(fe_segs) == len(be_segs):
        match_count = sum(1 for f, b in zip(fe_segs, be_segs) if f == b)
        if match_count >= len(fe_segs) - 1:
            return 0.8
    # 前缀相同，最后一段不同
    if fe_segs[:-1] == be_segs[:-1]:
        return 0.5
    
    return 0.0
```

### 5.3 匹配示例

| 前端 URL | 后端 URL | 置信度 | 说明 |
|----------|----------|:------:|------|
| `/api/binorder/detail` | `/api/binorder/detail` | 1.0 | 完全匹配 |
| `POST /api/order` | `POST /api/order/` | 0.95 | 尾斜杠差异 |
| `/api/order/123` | `/api/order/{id}` | 0.8 | 路径参数化匹配 |
| `/api/order/list` | `/api/order/detail` | 0.5 | 仅最后一段不同 |
| `POST /api/order` | `GET /api/order` | 0.0 | HTTP 方法不同 |
| `/api/order/detail` | `/api/supplier/detail` | 0.0 | 完全不匹配 |

### 5.4 `match_apis()` 实现

```python
def match_apis(
    frontend: list[FrontendApiCall],
    backend: list[BackendEndpoint],
    domains: list[Domain],
) -> list[ApiMatch]:
    """
    将前端 API 逐个匹配置到后端端点。
    
    匹配策略:
      1. 对每个前端 API，在后端端点中找最佳匹配
      2. HTTP 方法不同则跳过（不匹配）
      3. 取 `url_match_score` 最高分且 >= 0.5 的后端端点
      4. 一个后端端点只能匹配一个前端 API（贪心匹配，前者优先）
      5. 匹配后通过 domain.anchors 确定端点域归属
    
    未匹配的前端/后端:
      - 前端 URL 在后端没有对应端点 → 可能是未实现的 API 或过时的前端
      - 后端端点在前端没有调用方 → 可能是废弃接口或未接入前端
    """
    matches = []
    unmatched_backend = list(backend)      # 候补池，已匹配的移除
    
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
            domain = _find_endpoint_domain(best_match, domains)
            matches.append(ApiMatch(
                id=f"api-{_slugify(fe_api.function_name)}",
                frontend=fe_api,
                backend=best_match,
                match_confidence=best_score,
                domain=domain,
            ))
            unmatched_backend.remove(best_match)
    
    return matches
```

**未匹配端点处理**：

匹配过程中被移除的后端端点和未被匹配的前端 API 不会被丢弃——它们在 `export.py` 中被记录到报告文件中：

```
# 隐式设计：match_apis 返回的 list[ApiMatch] 只包含匹配成功的项。
# 未匹配信息存储在以下文件中：
#   - unmatched_frontend_apis: 前端调用了但后端没有对应 Controller 的 API
#   - unmatched_backend_endpoints: 后端实现了但前端没有调用的端点
```

**设计决策**：不在 `ApiMatch` 中包含未匹配项，因为：
1. `ApiMatch` 的语义是"一次成功的匹配"，未匹配不属于此类型
2. 未匹配信息的消费者主要是开发者，写入报告文件即可
3. 保持数据模型简洁

---

## 6. API → 域归属算法

### 6.1 算法原理

匹配后的 API 需要归属到某个业务域，以便在按域拆分的 API 文档中展示。v1.0 不能只依赖后端 Controller，因为纯前端项目、前后端分离项目、后端未纳入扫描时都无法通过 Controller 归域。

**归域优先级**：

1. 已匹配后端 Controller → 后端 Controller 所属域
2. 前端 API 文件路径 → `src/api/{module}.js` 对应域
3. 前端调用页面路径 → `src/views/{domain}/...` 对应域
4. URL 第一业务段 → `/svn/...`、`/repo/...` 等
5. 仍无法判断 → `""`，由 export 显示为 `未分类`

**核心逻辑**：能从更强语义来源归域时，绝不直接落入未分类。纯前端项目也必须能通过 API 模块、调用页面和 URL 业务段得到可用归属。

### 6.2 实现

```python
def _find_endpoint_domain(ep: BackendEndpoint, domains: list[Domain]) -> str:
    """
    通过 controller_file 路径匹配 domain.anchors 中的 source_file。
    
    匹配规则:
      1. 遍历所有域的 anchors
      2. 对每个锚点，检查其 source_file 是否：
         a. 包含端点的 controller_file 路径（子串包含）
         b. 或端点的 controller_file 的文件名在 source_file 中
      3. 返回第一个匹配的域名或域 ID
    
    示例:
      ep.controller_file = "com/smc/smccloud/bin/controller/BinOrderController.java"
      domain "bin-data" 的 anchors:
        - controller: [{"source_file": "com/smc/smccloud/bin/controller/BinController.java"}, ...]
      
      匹配: "com/smc/smccloud/bin/controller/BinOrderController.java"
          包含 "com/smc/smccloud/bin/controller/BinController.java"
          → 归属到 "bin-data"
    """
    for d in domains:
        for roles in d.anchors.values():
            for anchor in roles:
                file = anchor.get("source_file", "")
                if ep.controller_file in file or Path(ep.controller_file).name in file:
                    return d.name or d.id
    return ""  # 未匹配到任何域


def infer_frontend_api_domain(
    api_call: FrontendApiCall,
    domains: list[Domain],
    project_root: Path,
) -> str:
    """
    后端 Controller 不存在或未匹配时，根据前端线索推断 API 归属域。

    匹配顺序:
      1. API 文件模块名: src/api/svn.js → svn
      2. 调用页面首级目录: src/views/svn/status.vue → svn
      3. URL 首个业务段: /svn/{repoId}/status → svn
      4. 与 domains 的 id/name/display_name 做大小写归一化匹配
    """
    candidates: list[str] = []

    api_path = Path(api_call.source_file)
    if api_path.stem not in ("index", "request", "http", "client"):
        candidates.append(api_path.stem)

    for caller in api_call.callers:
        page = caller.get("page", "")
        parts = page.replace("\\", "/").split("/")
        if "views" in parts:
            idx = parts.index("views")
            if idx + 1 < len(parts):
                candidates.append(parts[idx + 1])

    url_parts = api_call.url.strip("/").split("/")
    if url_parts:
        first = url_parts[0]
        if first not in ("api", "v1", "v2", "admin"):
            candidates.append(first)
        elif len(url_parts) > 1:
            candidates.append(url_parts[1])

    normalized_domains = {
        (d.id or "").lower(): d.id,
        (d.name or "").lower(): d.name or d.id,
        (d.display_name or "").lower(): d.display_name or d.id,
    }

    for c in candidates:
        key = c.lower().replace("_", "-")
        if key in normalized_domains:
            return normalized_domains[key]
        for domain_key, domain_value in normalized_domains.items():
            if key and (key in domain_key or domain_key in key):
                return domain_value

    return ""
```

### 6.3 匹配示例

| 场景 | 输入 | 归属结果 |
|------|------|---------|
| 后端 Controller 命中 | `bin/controller/BinOrderController.java` 匹配 `bin-data` 域锚点 | **bin-data** |
| 前端 API 文件命中 | `src/api/svn.js`，domain id 为 `svn` | **svn** |
| 前端调用页面命中 | caller page 为 `views/svn/status.vue` | **svn** |
| URL 业务段命中 | `GET /repo/list`，domain id 为 `repo` | **repo** |
| 技术 URL 跳过 | `/api/v1/svn/status` | 跳过 `api/v1` 后归属 **svn** |
| 无任何线索 | `common/request.js` 调 `/health` | **""**（未归属） |

**边界条件**：

| 条件 | 行为 |
|------|------|
| Controller 不属于任何域 | 继续执行前端优先归域 |
| 纯前端项目无后端端点 | 直接执行前端优先归域 |
| 一个域有多个匹配 | 取第一个强信号；优先级为 Controller > API 文件 > views > URL |
| 两个域都匹配 | 取更长的归一化域键，避免 `po` 抢占 `purchase-order` |
| 仍无法判断 | domain 字段为空字符串，export 显示为 `未分类` |

### 6.4 质量阈值

API 归域结果必须写入 `build-report.json`：

| 指标 | v1.0 验收线 |
|------|:--:|
| 小型前端项目 API 未分类比例 | < 30% |
| 中型前后端项目 API 未分类比例 | < 20% |

如果 `api_uncategorized_ratio` 超过阈值，pipeline 应将质量状态标记为 `failed` 或 `warning`，而不是只报告“API 解析成功”。

---

## 7. 输出格式

### 7.1 api-map.json（按域拆分）

```json
{
  "domain": "Bin数据管理",
  "apis": [
    {
      "id": "api-list-bin-order-detail",
      "url": "POST /api/binorder/detail",
      "frontend": {
        "api_module": "binorder.js",
        "function": "listBinOrderDetail",
        "params": ["data"],
        "callers": [
          {"page": "csstock/apply/index.vue", "fields_used": ["supplierCode", "modelNo"]}
        ]
      },
      "backend": {
        "controller": "BinOrderController.java",
        "controller_class": "BinOrderController",
        "method": "listBinOrderDetail()",
        "param_type": "BinOrderQueryDTO",
        "param_fields": [],          // Phase 4+ 由 extract_dto_fields 填充
        "service_chain": [],         // Phase 4+ 由 extract_service_chain 填充
        "return_type": "Result"
      },
      "match_confidence": 1.0,
      "domain": "bin-data"
    }
  ]
}
```

### 7.2 wiki/api-index.md（全部 API 总览）

```markdown
# API 接口总览

按业务域分组，共 47 个 API。

## Bin数据管理（12 个 API）

| 方法 | URL | 前端函数 | 后端方法 | 参数类型 |
|------|-----|---------|---------|---------|
| POST | `/api/binorder/detail` | `listBinOrderDetail` | `BinOrderController.listBinOrderDetail()` | BinOrderQueryDTO |
| POST | `/api/binorder/import` | `importBindata` | `BinOrderController.importBindata()` | MultipartFile |
| ... | ... | ... | ... | ... |

## 采购管理（8 个 API）

| 方法 | URL | 前端函数 | 后端方法 | 参数类型 |
|------|-----|---------|---------|---------|
| POST | `/api/purchase/order/create` | `createPurchaseOrder` | `PoController.createOrder()` | PurchaseOrderDTO |
| ... | ... | ... | ... | ... |
```

### 7.3 wiki/{domain}/api-docs.md（单域 API 文档）

```markdown
# Bin数据管理 - API 文档

## POST /api/binorder/detail

**前端函数**：`listBinOrderDetail(data)`  
**后端方法**：`BinOrderController.listBinOrderDetail()`  
**参数类型**：`BinOrderQueryDTO`  
**前端调用页面**：

- `csstock/apply/index.vue` — 使用字段：supplierCode, modelNo
- `csstock/query/index.vue` — 使用字段：supplierCode, modelNo, startDate, endDate
```

### 7.4 增量更新

每次 `--update` 检测到前端 `api/` 或后端 Controller 文件变更时，只对变更的文件进行增量解析：

| 变更类型 | 触发操作 |
|---------|---------|
| 前端 `src/api/` 下文件新增/修改 | 重新解析该文件，追加/更新 `FrontendApiCall` |
| 前端 `src/api/` 下文件删除 | 移除对应的 `FrontendApiCall` |
| 后端 `*Controller.java` 新增/修改 | 重新解析该文件，追加/更新 `BackendEndpoint` |
| 后端 `*Controller.java` 删除 | 移除对应的 `BackendEndpoint` |
| 前端 `src/views/` 下 Vue 文件变更 | 重新扫描调用者（仅影响 `callers` 字段） |

---

## 8. 错误处理

### 8.1 异常层级

```
PipelineError (pipeline.py 定义)
  └── ApiMapperError                # API Mapper 模块基类（后续扩展预留）
       ├── FrontendParseError       # 前端解析错误（Phase 2+ 结构化异常）
       └── BackendParseError        # 后端解析错误（Phase 2+ 结构化异常）
```

### 8.2 降级策略

| 场景 | 检测条件 | 默认行为 |
|------|---------|---------|
| **前端目录不存在** | `not frontend_api_dir.exists()` | 静默跳过，frontend_apis = []，输出不带前端信息的匹配 |
| **前端 views 目录不存在** | `not frontend_views_dir.exists()` | 跳过调用者追踪，callers 保持空列表 |
| **后端 Controller 目录不存在** | `not backend_src_dir.exists()` | 静默跳过，backend_endpoints = []，返回空匹配列表 |
| **单个 JS 文件解析失败** | `IOError` / `UnicodeDecodeError` | 跳过该文件，继续解析其他文件 |
| **单个 Java 文件解析失败** | `IOError` / `UnicodeDecodeError` | 跳过该文件，继续解析 |
| **域名归属无匹配** | `_find_endpoint_domain` 返回 `""` | domain 字段保留空字符串，不影响匹配本身 |
| **URL 无匹配** | `url_match_score` 均 < 0.5 | 不创建 ApiMatch，前端 API 被丢弃（记录到报告） |

### 8.3 防御性编程

```python
# 前端目录不存在时的降级
if frontend_api_dir.exists():
    frontend_apis = parse_frontend_apis(frontend_api_dir)
    if frontend_views_dir.exists():
        frontend_apis = trace_frontend_callers(frontend_apis, frontend_views_dir)
else:
    frontend_apis = []

# 后端目录不存在时的降级
if backend_src_dir.exists():
    backend_endpoints = parse_backend_controllers(backend_src_dir, backend_root)
else:
    backend_endpoints = []

# 即使一方为空，match_apis 仍可执行（返回空列表或只含单方信息的匹配）
return match_apis(frontend_apis, backend_endpoints, domains)
```

### 8.4 Token 成本

**整个 `api_mapper.py` 模块零 Token 成本**。所有解析（正则匹配、import 追踪、注解提取、URL 比对）均为纯静态分析，不调用任何 LLM API。

### 8.5 性能估计

| 操作 | 时间估计 | 说明 |
|------|:--------:|------|
| 解析 47 个前端 API 文件 | ~50ms | 正则匹配，纯 CPU |
| 扫描 ~100 个 Vue 文件做调用者追踪 | ~100ms | 文件 IO + 正则 |
| 解析 ~143 个后端 Controller 文件 | ~150ms | 文件 IO + 正则 |
| URL 匹配（47 × 143 ≈ 6,700 对） | ~20ms | 纯内存字符串比较 |
| **合计** | **~320ms** | 对 OPS 级项目（~5,000 文件） |

---

## 9. 当前实现与设计的差距

`graph_wiki/api_mapper.py` 已实现核心映射流程，但以下功能处于**待实现**状态，将在后续迭代中补齐：

| 功能 | 设计状态 | 实现状态 | 优先级 | 说明 |
|------|:--------:|:--------:|:------:|------|
| `parse_frontend_apis()` | ✅ 完整设计 | ✅ 已实现 | — | |
| `trace_frontend_callers()` | ✅ 完整设计 | ✅ 已实现 | — | |
| `parse_backend_controllers()` | ✅ 完整设计 | ✅ 已实现 | — | |
| `url_match_score()` | ✅ 完整设计 | ✅ 已实现 | — | |
| `match_apis()` | ✅ 完整设计 | ✅ 已实现 | — | |
| `infer_frontend_api_domain()` | ✅ 完整设计 | ❌ 未实现 | 🔴 P0 | 解决纯前端项目 API 全部未分类 |
| `extract_dto_fields()` | 📝 设计预留 | ❌ 未实现 | 🟡 Phase 4+ | 需要 DTO 类源码解析 |
| `extract_service_chain()` | 📝 设计预留 | ❌ 未实现 | 🟡 Phase 4+ | 需要 Java AST 方法体解析 |
| `unmatched_frontend_apis` 输出 | 📝 设计预留 | ❌ 未实现 | 🟢 低 | 未匹配 API 记录到报告 |
| `unmatched_backend_endpoints` 输出 | 📝 设计预留 | ❌ 未实现 | 🟢 低 | 未匹配端点记录到报告 |
| `callers[i].component` | 📝 设计预留 | ❌ 未实现 | 🟢 低 | 从 Vue 文件提取组件名 |

**Phase 4+ 功能说明**（当前设计仅预留接口，不予实现）：

**`extract_dto_fields(param_type, controller_file)`**：

```python
def extract_dto_fields(param_type: str, controller_file: Path) -> list[dict]:
    """
    根据 param_type 类名（如 "BinOrderQueryDTO"），
    在 Controller 文件所在模块的 dto/ 目录下找到对应的 DTO 类，
    解析其字段列表：字段名、字段类型、注解。
    
    实现方式（Phase 4+）：
      1. 从 controller_file 推断 DTO 文件路径
         controller: .../controller/BinOrderController.java
         → dto:      .../dto/BinOrderQueryDTO.java
      2. 解析 DTO 文件中的字段声明
         private String supplierCode;
         private List<String> modelNos;
      3. 返回 [{name: "supplierCode", type: "String"}, ...]
    
    当前实现（v1.0）：
      返回空列表 []
    """
    return []
```

**`extract_service_chain(source, method_name)`**：

```python
def extract_service_chain(source: str, method_name: str) -> list[str]:
    """
    从 Controller 方法的 source 中提取 Service/Mapper 调用链。
    
    实现方式（Phase 4+）：
      1. 找到 method_name 对应的方法体
      2. 匹配 xxxService.yyy() 和 xxxMapper.zzz() 调用
      3. 返回 ["binOrderService.listBinOrderDetail()", "bindataMapper.selectByCondition()"]
    
    当前实现（v1.0）：
      返回空列表 []
    """
    return []
```

---

## 10. 测试用例

### 10.1 测试前端 axios POST 解析

```python
def test_parse_frontend_axios_post():
    """验证 axios.post() 模式提取——最常见的 API 调用模式。"""
    from graph_wiki.api_mapper import _extract_functions

    source = '''
    export function importBindata(data) {
      return axios.post('/api/bindata/import', data)
    }'''
    results = _extract_functions(source, Path("test.js"))
    assert len(results) == 1
    assert results[0].function_name == "importBindata"
    assert results[0].http_method == "POST"
    assert results[0].url == "/api/bindata/import"
    assert results[0].params == [{"name": "data"}]


def test_parse_frontend_async_function():
    """验证 async function 模式。"""
    from graph_wiki.api_mapper import _extract_functions

    source = '''
    export async function listBinOrderDetail(supplierCode, modelNo) {
      return axios.get('/api/binorder/detail', {params: {supplierCode, modelNo}})
    }'''
    results = _extract_functions(source, Path("test.js"))
    assert len(results) == 1
    assert results[0].function_name == "listBinOrderDetail"
    assert results[0].http_method == "GET"
    assert results[0].params == [{"name": "supplierCode"}, {"name": "modelNo"}]


def test_parse_frontend_request_config():
    """验证 request({method: ..., url: ...}) 配置模式。"""
    from graph_wiki.api_mapper import _extract_functions

    source = '''
    export function deleteOrder(id) {
      return request({
        method: 'delete',
        url: '/api/order/' + id
      })
    }'''
    results = _extract_functions(source, Path("test.js"))
    # URL 包含模板字符串拼接，当前正则无法提取完整 URL
    # 但 method "DELETE" 应被正确提取
    assert len(results) == 1
    assert results[0].http_method == "DELETE"
```

### 10.2 测试 URL 匹配评分

```python
def test_url_match_exact():
    """验证完全匹配返回 1.0。"""
    from graph_wiki.api_mapper import url_match_score

    assert url_match_score("/api/binorder/detail", "/api/binorder/detail") == 1.0
    assert url_match_score("POST /api/order", "POST /api/order") == 1.0  # 含 method 前缀


def test_url_match_trailing_slash():
    """验证尾斜杠差异返回 0.95。"""
    from graph_wiki.api_mapper import url_match_score

    assert url_match_score("/api/order/", "/api/order") == 0.95
    assert url_match_score("/api/order", "/api/order/") == 0.95


def test_url_match_parameterized():
    """验证路径参数化匹配返回 0.8。"""
    from graph_wiki.api_mapper import url_match_score

    # /api/order/123 → /api/order/{id}
    assert url_match_score("/api/order/123", "/api/order/{id}") == 0.8
    # /api/user/detail/456 → /api/user/detail/{userId}
    assert url_match_score("/api/user/detail/456", "/api/user/detail/{userId}") == 0.8


def test_url_match_prefix_only():
    """验证仅前缀匹配返回 0.5。"""
    from graph_wiki.api_mapper import url_match_score

    # 最后一段不同
    assert url_match_score("/api/order/list", "/api/order/detail") == 0.5
    assert url_match_score("/api/user/profile", "/api/user/settings") == 0.5


def test_url_match_none():
    """验证完全不匹配返回 0.0。"""
    from graph_wiki.api_mapper import url_match_score

    # 不同路径
    assert url_match_score("/api/order", "/api/user") == 0.0
    # 不同路径段数
    assert url_match_score("/api/order/detail", "/api/order") == 0.0
```

### 10.3 测试后端 Controller 解析

```python
def test_parse_backend_controller_post():
    """验证 @PostMapping + @RequestMapping 的拼接。"""
    from graph_wiki.api_mapper import _extract_endpoints
    from pathlib import Path

    source = '''
    @RestController
    @RequestMapping("/api/binorder")
    public class BinOrderController {
        @PostMapping("/detail")
        public Result listBinOrderDetail(@RequestBody BinOrderQueryDTO dto) {
            return binOrderService.listBinOrderDetail(dto);
        }
    }'''
    endpoints = _extract_endpoints(
        source, "/api/binorder", Path("/project/BinOrderController.java"), Path("/project")
    )
    assert len(endpoints) == 1
    assert endpoints[0].url == "/api/binorder/detail"
    assert endpoints[0].http_method == "POST"
    assert endpoints[0].method_name == "listBinOrderDetail"
    assert endpoints[0].controller_class == "BinOrderController"
    assert endpoints[0].param_type == "BinOrderQueryDTO"
    assert endpoints[0].return_type == "Result"


def test_parse_backend_controller_no_base_path():
    """验证没有 @RequestMapping 时 base_path 为空字符串。"""
    from graph_wiki.api_mapper import _extract_base_path

    source = '''
    @RestController
    public class SimpleController {
        @GetMapping("/api/simple/hello")
        public String hello() { return "hello"; }
    }'''
    base_path = _extract_base_path(source)
    assert base_path == ""
```

### 10.4 测试 Vue 调用者追踪

```python
def test_trace_vue_caller():
    """验证 Vue import 追踪和 this.xxx() 调用匹配。"""
    from graph_wiki.api_mapper import (
        FrontendApiCall, _parse_vue_imports, _extract_used_fields
    )
    from pathlib import Path

    vue_source = '''
    <template>...</template>
    <script>
    import { listBinOrderDetail } from '@/api/binorder'
    import { importBindata } from '@/api/binorder'

    export default {
      methods: {
        handleQuery() {
          this.listBinOrderDetail({supplierCode: this.form.supplierCode, modelNo: this.form.modelNo})
        }
      }
    }
    </script>'''

    # 测试 import 解析
    imports = _parse_vue_imports(vue_source)
    assert "binorder" in imports
    assert "listBinOrderDetail" in imports["binorder"]
    assert "importBindata" in imports["binorder"]

    # 测试字段提取
    fields = _extract_used_fields(vue_source, "listBinOrderDetail")
    assert "supplierCode" in fields
    assert "modelNo" in fields


def test_parse_vue_imports_with_alias():
    """验证 import { x as y } 别名解析。"""
    from graph_wiki.api_mapper import _parse_vue_imports

    source = "import { listBinOrderDetail as queryDetail } from '@/api/binorder'"
    imports = _parse_vue_imports(source)
    # 应解析别名前的原始名
    assert "binorder" in imports
    assert "listBinOrderDetail" in imports["binorder"]
```

### 10.5 端到端：完整 API 匹配流程

```python
def test_build_api_map_end_to_end():
    """验证完整的 build_api_map 流程：前端解析 → 后端解析 → 匹配。"""
    from pathlib import Path
    from graph_wiki.api_mapper import build_api_map
    from graph_wiki.models import Domain
    import tempfile, os

    with tempfile.TemporaryDirectory() as tmpdir:
        # 创建前端 API 文件
        api_dir = Path(tmpdir) / "api"
        api_dir.mkdir(parents=True)
        (api_dir / "binorder.js").write_text('''
        export function listBinOrderDetail(data) {
          return axios.post('/api/binorder/detail', data)
        }
        ''', encoding="utf-8")

        # 创建前端 views 目录（含调用者追踪）
        views_dir = Path(tmpdir) / "views"
        views_dir.mkdir(parents=True)
        (views_dir / "ApplyView.vue").write_text('''
        <script>
        import { listBinOrderDetail } from '@/api/binorder'
        export default {
          methods: {
            handleQuery() {
              this.listBinOrderDetail({supplierCode: '123'})
            }
          }
        }
        </script>
        ''', encoding="utf-8")

        # 创建后端 Controller 文件
        backend_root = Path(tmpdir) / "backend"
        controller_dir = backend_root / "com/smc/bin/controller"
        controller_dir.mkdir(parents=True)
        (controller_dir / "BinOrderController.java").write_text('''
        @RestController
        @RequestMapping("/api/binorder")
        public class BinOrderController {
            @PostMapping("/detail")
            public Result listBinOrderDetail(@RequestBody BinOrderQueryDTO dto) {
                return binOrderService.listBinOrderDetail(dto);
            }
        }
        ''', encoding="utf-8")

        # 构建 Domain（模拟 cluster 输出）
        domains = [
            Domain(
                id="bin-data",
                anchors={
                    "controller": [{
                        "id": "n1",
                        "label": "BinOrderController.java",
                        "source_file": str(controller_dir / "BinOrderController.java"),
                        "_role": "controller",
                    }]
                },
            )
        ]

        # 执行完整流程
        matches = build_api_map(
            frontend_api_dir=api_dir,
            frontend_views_dir=views_dir,
            backend_src_dir=controller_dir,
            domains=domains,
            backend_root=backend_root,
        )

        # 验证结果
        assert len(matches) == 1
        match = matches[0]
        assert match.match_confidence == 1.0  # 完全匹配
        assert match.domain == "bin-data"  # 域归属正确
        assert match.frontend.function_name == "listBinOrderDetail"
        assert match.backend.method_name == "listBinOrderDetail"
        assert match.backend.url == "/api/binorder/detail"

        # 验证调用者追踪
        assert len(match.frontend.callers) == 1
        assert "ApplyView.vue" in match.frontend.callers[0]["page"]
```

### 10.6 测试降级行为

```python
def test_build_api_map_no_frontend():
    """验证前端目录不存在时静默降级。"""
    from pathlib import Path
    from graph_wiki.api_mapper import build_api_map
    from graph_wiki.models import Domain
    import tempfile

    with tempfile.TemporaryDirectory() as tmpdir:
        # 只有后端，无前端目录
        backend_root = Path(tmpdir) / "backend"
        controller_dir = backend_root / "controller"
        controller_dir.mkdir(parents=True)

        matches = build_api_map(
            frontend_api_dir=Path(tmpdir) / "nonexistent-api",
            frontend_views_dir=Path(tmpdir) / "nonexistent-views",
            backend_src_dir=controller_dir,
            domains=[Domain(id="test")],
            backend_root=backend_root,
        )
        # 后端 Controller 目录存在但为空 → 返回空列表
        assert isinstance(matches, list)
        assert len(matches) == 0


def test_build_api_map_no_backend():
    """验证后端目录不存在时静默降级。"""
    from pathlib import Path
    from graph_wiki.api_mapper import build_api_map
    from graph_wiki.models import Domain
    import tempfile

    with tempfile.TemporaryDirectory() as tmpdir:
        api_dir = Path(tmpdir) / "api"
        api_dir.mkdir()
        (api_dir / "test.js").write_text('''
        export function test(data) {
          return axios.get('/api/test', data)
        }
        ''')

        matches = build_api_map(
            frontend_api_dir=api_dir,
            frontend_views_dir=Path(tmpdir) / "nonexistent-views",
            backend_src_dir=Path(tmpdir) / "nonexistent-backend",
            domains=[Domain(id="test")],
            backend_root=Path(tmpdir),
        )
        # 前端有 API 但后端不存在 → 返回空列表
        assert isinstance(matches, list)
        assert len(matches) == 0
```

---

## 11. 配置项清单

用户可通过 `graph-wiki.yaml` 配置以下 API Mapper 参数：

```yaml
api_mapper:
  # 后端 Controller 文件名模式（支持 glob）
  controller_pattern: "*Controller.java"
  
  # 前端 API 文件目录（相对项目根）
  frontend_api_dir: "src/api"
  
  # 前端视图目录
  frontend_views_dir: "src/views"
  
  # URL 匹配阈值（低于此值的匹配不输出）
  match_threshold: 0.5
  
  # 自定义后缀映射（扩展 Controller 识别）
  custom_controllers:
    - "*Facade.java"
    - "*Resource.java"  # JAX-RS 风格
```

---

## 12. 变更记录

| 日期 | 变更内容 | 版本 |
|------|---------|:----:|
| 2026-06-11 | 初始版本（基于架构文档 v1.0） | v1.0 |
| 2026-06-15 | 重写：根据实际代码实现更新，补充完整接口定义、匹配算法数学定义、错误处理、测试用例 | v2.0 |
