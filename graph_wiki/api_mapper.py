"""前后端接口映射：解析前端 API 和后端 Controller，建立双向映射"""

import re
from pathlib import Path
from collections import defaultdict
from typing import Optional

from .trust import is_sensitive_path
from .models import ApiMatch, FrontendApiCall, BackendEndpoint, Domain

JAVA_NON_BUSINESS_TYPES = {
    "String", "Boolean", "Byte", "Short", "Integer", "Long", "Float", "Double",
    "BigDecimal", "BigInteger", "Date", "LocalDate", "LocalDateTime", "Instant",
    "Map", "HashMap", "LinkedHashMap", "List", "ArrayList", "Set", "HashSet",
    "Collection", "Iterable", "Optional", "Object", "MultipartFile",
}

# ── 前端 API 解析 ──

AXIOS_CALL_PATTERNS = [
    # 模式 1: axios.post / request.post / http.get 等 HTTP 客户端方法调用
    re.compile(
        r"""(?:axios|request|http|api|client)\s*\.\s*(?P<method>get|post|put|delete|patch)
            \s*\(\s*['\"`](?P<url>[^'\"`]+)['\"`]""",
        re.VERBOSE | re.IGNORECASE,
    ),
    # 模式 2: method: 'post', url: '/api/xxx' 配置模式
    re.compile(
        r"""method\s*:\s*['\"](?P<method>get|post|put|delete|patch)['\"]
            .*?url\s*:\s*['\"`](?P<url>[^'\"`]+)['\"`]""",
        re.VERBOSE | re.IGNORECASE,
    ),
]


def parse_frontend_apis(api_dir: Path) -> list[FrontendApiCall]:
    results = []
    for file_path in api_dir.rglob("*.js"):
        if "node_modules" in str(file_path):
            continue
        try:
            source = file_path.read_text(encoding="utf-8")
        except (IOError, UnicodeDecodeError):
            continue
        results.extend(_extract_functions(source, file_path))
    return results


def _extract_functions(source: str, file_path: Path) -> list[FrontendApiCall]:
    results = []
    export_pattern = re.compile(
        r"""export\s+(?:async\s+)?function\s+(?P<name>\w+)\s*
            \((?P<params>[^)]*)\)\s*\{(?P<body>(?:[^{}]|\{[^{}]*\})*)\}""",
        re.VERBOSE,
    )
    for match in export_pattern.finditer(source):
        name = match.group("name")
        params_str = match.group("params")
        body = match.group("body")

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
                    params=param_names,
                    source_file=str(file_path),
                    source_line=source[: match.start()].count("\n") + 1,
                )
                results.append(api_call)
                break
    return results


def trace_frontend_callers(
    api_calls: list[FrontendApiCall], views_dir: Path
) -> list[FrontendApiCall]:
    api_index = defaultdict(list)
    for call in api_calls:
        module_name = Path(call.source_file).stem
        api_index[module_name].append(call)

    for vue_file in views_dir.rglob("*.vue"):
        if is_sensitive_path(vue_file):
            continue
        try:
            source = vue_file.read_text(encoding="utf-8")
        except (IOError, UnicodeDecodeError):
            continue
        imports = _parse_vue_imports(source)
        for module_name, funcs in imports.items():
            if module_name not in api_index:
                continue
            for func in funcs:
                if f"this.{func}" not in source and f"{func}(" not in source:
                    continue
                for call in api_index[module_name]:
                    if call.function_name == func:
                        call.callers.append({
                            "page": str(vue_file.relative_to(views_dir.parent)).replace("\\", "/"),
                            "fields_used": _extract_used_fields(source, func),
                        })
    return api_calls


def _parse_vue_imports(source: str) -> dict[str, list[str]]:
    # 支持 '@/api/module'、'../../api/module'、'@/api' 和 '../../api'
    results = {}
    pattern = re.compile(
        r"""import\s+\{([^}]+)\}\s+from\s+['\"](?P<path>[^'\"]+)['\"]"""
    )
    for match in pattern.finditer(source):
        import_path = match.group("path").replace("\\", "/")
        module = ""
        if import_path in {"@/api", "./api", "../api", "../../api"} or import_path.endswith("/api"):
            module = "index"
        elif "/api/" in import_path:
            module = import_path.rsplit("/api/", 1)[1].split("/", 1)[0]
        if not module:
            continue
        functions = [f.strip().split(" as ")[0].strip() for f in match.group(1).split(",")]
        results[module] = functions
    return results


def _extract_used_fields(source: str, func_name: str) -> list[str]:
    fields = []
    call_site = re.search(
        r"this\." + re.escape(func_name) + r"\s*\(\s*\{([^}]*)\}\s*\)", source
    )
    if call_site:
        for field in re.findall(r"(\w+)\s*:", call_site.group(1)):
            fields.append(field)
    return fields


# ── 后端 Controller 解析 ──

def parse_backend_controllers(
    controller_dir: Path, backend_root: Path
) -> list[BackendEndpoint]:
    endpoints = []
    for java_file in controller_dir.rglob("*Controller.java"):
        if "/src/test/" in str(java_file):
            continue
        try:
            source = java_file.read_text(encoding="utf-8")
        except (IOError, UnicodeDecodeError):
            continue
        base_path = _extract_base_path(source)
        endpoints.extend(_extract_endpoints(source, base_path, java_file, backend_root))
    return endpoints


def _extract_base_path(source: str) -> str:
    match = re.search(r'@RequestMapping\s*\(\s*["\']([^"\']+)["\']', source)
    return match.group(1) if match else ""


def _extract_endpoints(
    source: str, base_path: str, file_path: Path, backend_root: Path
) -> list[BackendEndpoint]:
    endpoints = []
    mapping_pattern = re.compile(
        r"@(?P<method_type>Post|Get|Put|Delete|Patch)Mapping\b(?:\s*\((?P<args>[^)]*)\))?"
    )
    for match in mapping_pattern.finditer(source):
        signature = _extract_java_method_signature(source, match.end())
        if not signature:
            continue
        method_name = signature["method_name"]
        param_type = _select_request_body_param_type(signature["params"])
        url = _join_url(base_path, _extract_mapping_path(match.group("args") or ""))

        # 提取 DTO 参数字段列表（Phase 2+ 增强）
        param_fields = _extract_dto_fields_from_source(source, param_type, file_path) if param_type else []

        # 提取 Controller 方法体内的 Service 调用链
        method_source = _extract_method_body(source, method_name)
        service_chain = _extract_service_chain_from_body(method_source) if method_source else []

        endpoints.append(BackendEndpoint(
            controller_file=str(file_path.relative_to(backend_root)),
            controller_class=_extract_class_name(source),
            method_name=method_name,
            http_method=match.group("method_type").upper(),
            url=url,
            param_type=param_type,
            param_fields=param_fields,
            service_chain=service_chain,
            return_type=signature["return_type"],
        ))
    return endpoints


def _extract_mapping_path(args: str) -> str:
    """解析 @GetMapping("/x")、@GetMapping(value="/x") 和 @PostMapping。"""
    if not args:
        return ""
    match = re.search(r"""(?:value|path)?\s*=?\s*["']([^"']*)["']""", args)
    return match.group(1) if match else ""


def _join_url(base_path: str, path: str) -> str:
    base = (base_path or "").strip("/")
    child = (path or "").strip("/")
    if base and child:
        url = f"/{base}/{child}"
    elif base:
        url = f"/{base}"
    elif child:
        url = f"/{child}"
    else:
        url = "/"
    return url.rstrip("/") or "/"


def _extract_java_method_signature(source: str, start: int) -> dict | None:
    public_idx = source.find("public", start, start + 2000)
    if public_idx == -1:
        return None
    open_paren = source.find("(", public_idx, public_idx + 2000)
    if open_paren == -1:
        return None
    prefix = " ".join(source[public_idx:open_paren].split())
    match = re.match(r"public\s+(?P<return_type>.+)\s+(?P<method_name>\w+)$", prefix)
    if not match:
        return None
    close_paren = _find_matching_delimiter(source, open_paren, "(", ")")
    if close_paren == -1:
        return None
    params_source = source[open_paren + 1:close_paren]
    return {
        "method_name": match.group("method_name"),
        "return_type": match.group("return_type").strip(),
        "params": _parse_java_params(params_source),
    }


def _find_matching_delimiter(source: str, start: int, open_char: str, close_char: str) -> int:
    depth = 0
    for i in range(start, len(source)):
        if source[i] == open_char:
            depth += 1
        elif source[i] == close_char:
            depth -= 1
            if depth == 0:
                return i
    return -1


def _parse_java_params(params_source: str) -> list[dict]:
    params = []
    for raw_param in _split_top_level_commas(params_source):
        raw_param = raw_param.strip()
        if not raw_param:
            continue
        cleaned = re.sub(r"@\w+(?:\s*\([^)]*\))?\s*", "", raw_param).strip()
        cleaned = re.sub(r"\bfinal\s+", "", cleaned)
        parts = cleaned.split()
        if len(parts) < 2:
            continue
        params.append({
            "raw": raw_param,
            "type": " ".join(parts[:-1]).strip(),
            "name": parts[-1].strip(),
        })
    return params


def _split_top_level_commas(text: str) -> list[str]:
    parts = []
    start = 0
    angle_depth = paren_depth = 0
    for i, char in enumerate(text):
        if char == "<":
            angle_depth += 1
        elif char == ">" and angle_depth:
            angle_depth -= 1
        elif char == "(":
            paren_depth += 1
        elif char == ")" and paren_depth:
            paren_depth -= 1
        elif char == "," and angle_depth == 0 and paren_depth == 0:
            parts.append(text[start:i])
            start = i + 1
    parts.append(text[start:])
    return parts


def _select_request_body_param_type(params: list[dict]) -> str:
    for param in params:
        if "@RequestBody" in param["raw"]:
            return _normalize_payload_type(param["type"])
    return ""


def _normalize_payload_type(param_type: str) -> str:
    cleaned = " ".join(param_type.replace("...", "").split()).strip()
    cleaned = cleaned.rsplit(".", 1)[-1]
    collection_match = re.search(r"(?:List|Set|Collection|Iterable)\s*<\s*([\w.]+)", cleaned)
    if collection_match:
        return collection_match.group(1).rsplit(".", 1)[-1]
    return cleaned.split("<", 1)[0].split("[", 1)[0].strip()


def _extract_class_name(source: str) -> str:
    match = re.search(r"public\s+class\s+(\w+)", source)
    return match.group(1) if match else ""


# ── DTO 字段 + Service 链提取（Phase 2+）──

def _extract_dto_fields_from_source(source: str, param_type: str, file_path: Path) -> list[dict]:
    """从 Controller 入参类型解析 DTO 字段。"""
    if not param_type:
        return []
    java_file = _find_java_class_file(param_type, file_path)
    if not java_file:
        return []
    try:
        dto_source = java_file.read_text(encoding="utf-8")
    except (IOError, UnicodeDecodeError):
        return []
    return _extract_java_fields(dto_source)


def _find_java_class_file(class_name: str, near_file: Path) -> Path | None:
    simple_name = _normalize_payload_type(class_name)
    if not simple_name or simple_name in JAVA_NON_BUSINESS_TYPES:
        return None

    search_roots = _java_search_roots(near_file)
    for parent in search_roots:
        candidate = parent / f"{simple_name}.java"
        if candidate.exists():
            return candidate
        matches = list(parent.rglob(f"{simple_name}.java"))
        if matches:
            return matches[0]
    return None


def _java_search_roots(near_file: Path) -> list[Path]:
    parts = near_file.parts
    for i in range(len(parts) - 2):
        if parts[i:i + 3] == ("src", "main", "java"):
            return [Path(*parts[:i + 3])]
    return [near_file.parent]


def _extract_java_fields(source: str) -> list[dict]:
    fields = []
    pattern = re.compile(
        r"""(?:@\w+(?:\([^)]*\))?\s*)*
            (?:private|protected|public)\s+
            (?P<type>[\w<>?,\s]+?)\s+
            (?P<name>\w+)\s*;""",
        re.VERBOSE,
    )
    for match in pattern.finditer(source):
        fields.append({
            "name": match.group("name"),
            "type": " ".join(match.group("type").split()),
        })
    return fields


def _extract_method_body(source: str, method_name: str) -> str:
    """从 Java 源码中提取指定方法的方法体"""
    method_match = re.search(rf"\b{re.escape(method_name)}\s*\(", source)
    if not method_match:
        return ""
    public_idx = source.rfind("public", 0, method_match.start())
    if public_idx == -1 or method_match.start() - public_idx > 500:
        return ""
    open_paren = source.find("(", method_match.start())
    close_paren = _find_matching_delimiter(source, open_paren, "(", ")")
    if close_paren == -1:
        return ""
    open_brace = source.find("{", close_paren, close_paren + 400)
    if open_brace == -1:
        return ""
    start = open_brace + 1
    depth = 1
    i = start
    while i < len(source) and depth > 0:
        if source[i] == "{":
            _, _ = depth + 1, i + 1
            depth += 1
            i = min(i + 1, len(source))
        elif source[i] == "}":
            depth -= 1
        i += 1
    return source[start:i-1] if depth == 0 else ""


def _extract_service_chain_from_body(method_body: str) -> list[str]:
    """从 Controller 方法体中提取 Service/Mapper 调用链"""
    # 匹配 xxxService.yyy() 和 xxxMapper.yyy() 调用
    pattern = re.compile(r"(\w+(?:Service|Mapper|DAO))\s*\.\s*(\w+)\s*\(")
    chain = []
    for match in pattern.finditer(method_body):
        chain.append(f"{match.group(1)}.{match.group(2)}()")
    return chain[:10]  # 最多 10 个


# ── URL 匹配 ──

def url_match_score(fe_url: str, be_url: str) -> float:
    fe_url = fe_url.strip("/")
    be_url = be_url.strip("/")
    if fe_url == be_url:
        return 1.0
    if fe_url.rstrip("/") == be_url.rstrip("/"):
        return 0.95
    fe_segs, be_segs = _business_url_segments(fe_url), _business_url_segments(be_url)
    if fe_segs == be_segs:
        return 0.95
    if len(fe_segs) == len(be_segs):
        match_count = sum(1 for f, b in zip(fe_segs, be_segs) if f == b)
        dynamic_pairs = sum(1 for f, b in zip(fe_segs, be_segs) if _is_dynamic_segment(f) and _is_dynamic_segment(b))
        dynamic_literal_mismatch = any(
            _is_dynamic_segment(f) != _is_dynamic_segment(b)
            for f, b in zip(fe_segs, be_segs)
            if f != b
        )
        if match_count + dynamic_pairs == len(fe_segs):
            return 0.9
        if match_count >= len(fe_segs) - 1 and not dynamic_literal_mismatch:
            return 0.8
    if fe_segs[:-1] == be_segs[:-1]:
        return 0.5
    return 0.0


def _business_url_segments(url: str) -> list[str]:
    segments = [seg for seg in url.strip("/").split("/") if seg]
    if segments and segments[0] == "api":
        return segments[1:]
    return segments


def _is_dynamic_segment(segment: str) -> bool:
    return (
        segment.startswith("{") and segment.endswith("}")
        or segment.startswith("${") and segment.endswith("}")
        or segment.startswith(":")
    )


def match_apis(
    frontend: list[FrontendApiCall],
    backend: list[BackendEndpoint],
    domains: list[Domain],
) -> list[ApiMatch]:
    matches = []
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
            domain = infer_frontend_api_domain(fe_api, domains) or _find_endpoint_domain(best_match, domains)
            matches.append(ApiMatch(
                id=f"api-{_slugify(fe_api.function_name)}",
                frontend=fe_api,
                backend=best_match,
                match_confidence=best_score,
                domain=domain,
            ))
            unmatched_backend.remove(best_match)

    return matches


def _slugify(text: str) -> str:
    return re.sub(r"[^a-z0-9-]", "", text.lower().replace(" ", "-"))


def _find_endpoint_domain(ep: BackendEndpoint, domains: list[Domain]) -> str:
    file_token = _normalize_domain_token(ep.controller_file)
    for d in domains:
        domain_value = d.name or d.id
        if not domain_value or domain_value in {"main", "api", "controller", "service"}:
            continue
        candidate_tokens = {
            d.id,
            d.name,
            d.display_name,
            *d.modules,
            *d.packages,
        }
        for token in candidate_tokens:
            normalized = _normalize_domain_token(token)
            if normalized and normalized in file_token:
                return domain_value

    for d in domains:
        for roles in d.anchors.values():
            for anchor in roles:
                file = anchor.get("source_file", "")
                if ep.controller_file in file or Path(ep.controller_file).name in file:
                    return d.name or d.id
    return ""


def infer_frontend_api_domain(
    api_call: FrontendApiCall,
    domains: list[Domain],
    project_root: Path | None = None,
) -> str:
    """根据前端 API 文件、调用页面和 URL 推断业务域。"""
    candidates = _domain_lookup(domains)

    module = Path(api_call.source_file).stem
    if module.lower() not in {"index", "api", "request", "http", "client"}:
        found = _match_domain_token(module, candidates)
        if found:
            return found

    for caller in api_call.callers or []:
        page = str(caller.get("page", "")).replace("\\", "/")
        parts = [p for p in page.split("/") if p and p not in {"src", "views", "view", "pages"}]
        for part in parts[:-1] or parts:
            found = _match_domain_token(part, candidates)
            if found:
                return found

    for segment in [s for s in api_call.url.strip("/").split("/") if s]:
        if segment.lower() in {"api", "v1", "v2", "admin"}:
            continue
        found = _match_domain_token(segment, candidates)
        if found:
            return found
    return ""


def _domain_lookup(domains: list[Domain]) -> dict[str, str]:
    lookup = {}
    for domain in domains:
        value = domain.name or domain.id
        for token in {domain.id, domain.name, domain.display_name, *domain.modules, *domain.packages}:
            if token:
                lookup[_normalize_domain_token(token)] = value
                lookup[_normalize_domain_token(Path(str(token)).name)] = value
        if _normalize_domain_token(value) == "repository":
            lookup["repo"] = value
    return lookup


def _match_domain_token(token: str, lookup: dict[str, str]) -> str:
    normalized = _normalize_domain_token(token)
    if normalized in lookup:
        return lookup[normalized]
    for key, value in lookup.items():
        if key and (normalized in key or key in normalized):
            return value
    return ""


def _normalize_domain_token(token: str) -> str:
    return re.sub(r"[^a-z0-9\u4e00-\u9fff]", "", str(token).lower())


# ── 主接口 ──

def build_api_map(
    frontend_api_dir: Path,
    frontend_views_dir: Path,
    backend_src_dir: Path,
    domains: list[Domain],
    backend_root: Path,
) -> list[ApiMatch]:
    if frontend_api_dir.exists():
        frontend_apis = parse_frontend_apis(frontend_api_dir)
        if frontend_views_dir.exists():
            frontend_apis = trace_frontend_callers(frontend_apis, frontend_views_dir)
    else:
        frontend_apis = []

    if backend_src_dir.exists():
        backend_endpoints = parse_backend_controllers(backend_src_dir, backend_root)
    else:
        backend_endpoints = []

    matches = match_apis(frontend_apis, backend_endpoints, domains)
    if matches:
        for match in matches:
            if not match.domain:
                match.domain = infer_frontend_api_domain(match.frontend, domains, backend_root)
        return matches

    for api_call in frontend_apis:
        domain = infer_frontend_api_domain(api_call, domains, backend_root)
        if domain:
            setattr(api_call, "domain", domain)
    return frontend_apis
