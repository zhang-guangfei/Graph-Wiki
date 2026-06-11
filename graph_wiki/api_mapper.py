"""前后端接口映射：解析前端 API 和后端 Controller，建立双向映射"""

import re
from pathlib import Path
from collections import defaultdict
from typing import Optional

from .models import ApiMatch, FrontendApiCall, BackendEndpoint, Domain

# ── 前端 API 解析 ──

AXIOS_CALL_PATTERNS = [
    re.compile(
        r"""axios\s*\.\s*(?P<method>get|post|put|delete|patch)
            \s*\(\s*['\"](?P<url>[^'\"]+)['\"]""",
        re.VERBOSE | re.IGNORECASE,
    ),
    re.compile(
        r"""method\s*:\s*['\"](?P<method>get|post|put|delete|patch)['\"]
            .*?url\s*:\s*['\"](?P<url>[^'\"]+)['\"]""",
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
                    params=[{"name": p} for p in param_names],
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
                            "page": str(vue_file.relative_to(views_dir.parent)),
                            "fields_used": _extract_used_fields(source, func),
                        })
    return api_calls


def _parse_vue_imports(source: str) -> dict[str, list[str]]:
    pattern = re.compile(
        r"""import\s+\{([^}]+)\}\s+from\s+['\"]@/api/(?P<module>\w+)['\"]"""
    )
    results = {}
    for match in pattern.finditer(source):
        module = match.group("module")
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
        r"""@(?P<method_type>Post|Get|Put|Delete|Patch)Mapping\s*\(\s*
            ["\'](?P<path>[^"\']*)["\']\s*\)\s*
            (?:@\w+(?:\([^)]*\))?\s*)*
            public\s+(?P<return_type>\S+)\s+(?P<method_name>\w+)\s*
            \(\s*(?:@\w+(?:\([^)]*\))?\s*)*
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


def _extract_class_name(source: str) -> str:
    match = re.search(r"public\s+class\s+(\w+)", source)
    return match.group(1) if match else ""


# ── URL 匹配 ──

def url_match_score(fe_url: str, be_url: str) -> float:
    fe_url = fe_url.strip("/")
    be_url = be_url.strip("/")
    if fe_url == be_url:
        return 1.0
    if fe_url.rstrip("/") == be_url.rstrip("/"):
        return 0.95
    fe_segs, be_segs = fe_url.split("/"), be_url.split("/")
    if len(fe_segs) == len(be_segs):
        match_count = sum(1 for f, b in zip(fe_segs, be_segs) if f == b)
        if match_count >= len(fe_segs) - 1:
            return 0.8
    if fe_segs[:-1] == be_segs[:-1]:
        return 0.5
    return 0.0


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


def _slugify(text: str) -> str:
    return re.sub(r"[^a-z0-9-]", "", text.lower().replace(" ", "-"))


def _find_endpoint_domain(ep: BackendEndpoint, domains: list[Domain]) -> str:
    for d in domains:
        for roles in d.anchors.values():
            for anchor in roles:
                file = anchor.get("source_file", "")
                if ep.controller_file in file or Path(ep.controller_file).name in file:
                    return d.name or d.id
    return ""


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

    return match_apis(frontend_apis, backend_endpoints, domains)
