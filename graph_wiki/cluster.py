"""业务域聚类：将代码图聚类为业务域和业务点"""

import re
from pathlib import Path
from collections import defaultdict
from typing import Optional

import networkx as nx

from .models import (
    Language, NodeRole, ANCHOR_ROLES,
    Domain, BusinessPoint,
)

# ── 异常定义 ──

class ClusterError(Exception): pass
class NoBusinessNodesError(ClusterError):
    """未找到任何业务节点：项目可能没有 Java/JS 源文件"""
class TooFewAnchorsError(ClusterError):
    """锚点太少（< 3）：无法形成有意义的域划分"""

# ── Step 1: 噪音过滤 ──

NOISE_LABELS = {
    "String", "Integer", "Long", "Double", "Float", "Boolean",
    "BigDecimal", "Date", "List", "Map", "Set", "Object", "Class",
    "void", "int", "long", "boolean", "byte", "char",
    "Serializable", "Cloneable", "Comparable",
}

VALID_SUFFIXES = (".java", ".vue", ".js", ".ts", ".jsx", ".tsx", ".py", ".go")

# ── 非业务方法名（不含括号）──
# 这些方法即使出现在 Controller/Service 中也不是业务点
_NON_BUSINESS_METHODS = {
    # -- Object 类方法（精确匹配，已在前面单独处理，这里作兜底）--
    "toString", "equals", "hashCode", "clone", "finalize",
    # -- Java 函数式接口方法 --
    "run", "call", "apply", "accept", "test", "get",
    "compare", "andThen", "compose", "orElse", "ifPresent",
    "and", "or", "negate",
    # -- Spring 生命周期 --
    "afterPropertiesSet", "init", "destroy",
    "setApplicationContext", "setServletContext",
    "setBeanFactory", "setBeanName", "setBeanClassLoader",
    "setResourceLoader", "setEnvironment", "setEmbeddedValueResolver",
    # -- Lombok/Builder --
    "builder", "build", "toBuilder", "canEqual",
    # -- MyBatis Generator / Criteria --
    "addCriterion", "isValid", "createCriteria", "createCriteriaInternal",
    "oredCriteria", "clear", "isDistinct", "setDistinct",
    "setOrderByClause", "setOredCriteria",
    # -- Vue lifecycle hooks --
    "data", "beforeCreate", "created", "beforeMount", "mounted",
    "beforeUpdate", "updated", "activated", "deactivated",
    "beforeDestroy", "destroyed", "beforeUnmount", "unmounted",
    "errorCaptured", "renderTracked", "renderTriggered",
    "serverPrefetch", "render",
    # -- Vue options API --
    "components", "directives", "filters", "mixins", "extends",
    "provide", "inject", "setup", "emits", "expose", "slots",
    "props", "computed", "watch", "methods", "name", "model",
    "inheritAttrs", "delimiters",
    # -- Frontend UI helper methods --
    "formatDate", "formatTime", "calcTableHeight", "resetForm",
    # -- JS 关键字误解析 --
    "if", "for", "while", "switch", "case", "return",
    "break", "continue", "new", "delete", "typeof", "void",
}

# 方法名精确匹配（含括号），优先级高于 _NON_BUSINESS_METHODS
_NON_BUSINESS_METHOD_NAMES = {
    ".toString()", ".equals()", ".hashCode()", ".clone()", ".finalize()",
    ".builder()", ".build()", ".toBuilder()",
    ".addCriterion()",
}

_CORE_ACTION_PREFIXES = (
    "create", "submit", "approve", "import", "delete", "remove", "update",
    "save", "merge", "commit", "execute", "finish", "confirm", "cancel",
    "reject", "dispatch", "adjust", "convert", "bind", "transfer",
)

_INTERACTION_PREFIXES = (
    "load", "search", "query", "find", "fetch", "list", "open", "show",
    "view", "select", "go", "goto", "handle", "trace", "preview",
)

_HELPER_PREFIXES = (
    "format", "calc", "calculate", "reset", "check", "validate", "prev",
    "next", "parse", "normalize", "build", "render",
)


def is_business_relevant(data: dict) -> bool:
    label = data.get("label", "")
    source = data.get("source_file", "")

    if data.get("file_type") != "code":
        return False
    if "/src/test/" in source.replace("\\", "/"):
        return False
    if label.startswith(".") or label.startswith("_"):
        return False
    if label in NOISE_LABELS:
        return False
    if re.match(r"^(get|set|is)[A-Z]", label):
        return False
    if label in ("toString()", "equals()", "hashCode()", "clone()", "finalize()"):
        return False
    if not label.endswith(VALID_SUFFIXES):
        return False
    return True


def classify_role(data: dict) -> NodeRole:
    label = data.get("label", "").lower()
    source = data.get("source_file", "").lower()

    # Java 角色
    if "controller" in label and label.endswith(".java"):
        return NodeRole.CONTROLLER
    if "serviceimpl" in label and label.endswith(".java"):
        return NodeRole.SERVICE_IMPL
    if "service" in label and label.endswith(".java"):
        return NodeRole.SERVICE_API
    if "mapper" in label and label.endswith(".java"):
        return NodeRole.MAPPER
    if "dao" in label and label.endswith(".java"):
        return NodeRole.DAO
    if "adapter" in label and label.endswith(".java"):
        return NodeRole.ADAPTER
    if label.endswith(".java"):
        if any(kw in label for kw in ("config", "configuration", "properties")):
            return NodeRole.CONFIG
        if any(kw in label for kw in ("util", "helper", "tool", "constant")):
            return NodeRole.UTIL
        if "entity" in label or label.endswith("do.java"):
            return NodeRole.ENTITY
        if "dto" in label:
            return NodeRole.DTO
        if "vo.java" in label:
            return NodeRole.VO
        if "enum" in label:
            return NodeRole.ENUM

    # Vue/JS 角色
    if label.endswith(".vue"):
        if "/components/" in source:
            return NodeRole.HANDLER
        return NodeRole.CONTROLLER
    if label.endswith((".js", ".ts")):
        # 标准化路径：确保以 "/" 开头
        normalized = source if source.startswith("/") else "/" + source
        if "/api/" in normalized:
            return NodeRole.CONTROLLER
        if any(kw in normalized for kw in ("/utils/", "/helpers/", "/constants/")):
            return NodeRole.UTIL

    return NodeRole.ENTITY


def role_importance(role: NodeRole) -> int:
    return {
        NodeRole.CONTROLLER: 5,
        NodeRole.SERVICE_IMPL: 4,
        NodeRole.SERVICE_API: 4,
        NodeRole.MAPPER: 3,
        NodeRole.DAO: 3,
        NodeRole.ADAPTER: 3,
        NodeRole.ENTITY: 2,
        NodeRole.DTO: 1,
        NodeRole.VO: 1,
        NodeRole.ENUM: 1,
        NodeRole.HANDLER: 2,
        NodeRole.CONFIG: -1,
        NodeRole.UTIL: -1,
        NodeRole.NOISE: -5,
    }.get(role, 0)


def classify_business_point_type(method_name: str) -> str:
    """Classify a method-level business point for CEO-quality reporting."""
    bare = method_name.strip().lstrip(".").removesuffix("()")
    normalized = bare[:1].lower() + bare[1:]
    lowered = normalized.lower()
    action = lowered
    if lowered.startswith("handle") and len(lowered) > len("handle"):
        tail = bare[len("handle"):]
        action = tail[:1].lower() + tail[1:]
        action = action.lower()

    if action.startswith(_HELPER_PREFIXES):
        return "helper"
    if action.startswith(_CORE_ACTION_PREFIXES):
        return "core_action"
    if action.startswith(_INTERACTION_PREFIXES) or lowered.startswith(_INTERACTION_PREFIXES):
        return "interaction"
    return "interaction"


def filter_noise(G: nx.Graph) -> list[dict]:
    filtered = []
    for node_id, data in G.nodes(data=True):
        if not is_business_relevant(data):
            continue
        role = classify_role(data)
        if role in (NodeRole.CONFIG, NodeRole.UTIL, NodeRole.NOISE):
            continue
        entry = dict(data)
        entry["id"] = node_id
        entry["_role"] = role.value
        entry["_importance"] = role_importance(role)
        filtered.append(entry)
    return filtered


# ── Step 2: 锚点提取 ──

def extract_anchors(business_nodes: list[dict]) -> list[dict]:
    return [n for n in business_nodes if NodeRole(n["_role"]) in ANCHOR_ROLES]


# ── Step 3: 包路径聚类 ──

# 平台/产品名：公司域名后的第一级，不是业务域
_PLATFORM_NAMES = {"smccloud", "ops", "hshcore", "platform", "sales", "smc", "svnplatform"}

# 分层关键词：Java 项目中常见的分层名，不是业务域
_LAYER_NAMES = {
    # 数据层
    "db", "entity", "entities", "domain", "dao", "mapper", "mappers",
    "repository", "repositories",
    # 服务层
    "service", "services", "serviceimpl", "serviceImpl", "impl",
    # 接口/传输层
    "controller", "controllers", "web", "api",
    "dto", "dto", "vo", "vos", "model", "models", "pojo",
    # 基础设施
    "config", "conf", "configuration", "common", "utils", "util",
    "helper", "helpers", "constant", "constants", "enums",
    "interceptor", "interceptors", "filter", "filters",
    "annotation", "annotations", "aop", "aspect",
    "feign", "hystrix", "fallback",
    "batchdao", "assembly", "generator",
    # 事件/任务
    "event", "events", "listener", "listeners", "publisher",
    "job", "jobs", "task", "tasks", "schedule",
    # 远程调用
    "rpc", "adapter", "adapters", "extdao", "feignclient",
    "client", "clients",
    # 通用
    "core", "base", "support",
    # xxl-job
    "admin", "executor",
    # 测试
    "test", "tests",
    # nacos
    "nacos",
    # rabbitmq
    "rabbitmq",
    # API 版本号
    "v1", "v2", "v3", "v4", "v5",
}

_FRONTEND_GENERIC_DOMAIN_KEYS = {
    "api", "apis", "layout", "layouts", "dashboard", "home", "index",
    "components", "component", "utils", "util", "router", "store",
}

_CLASS_DOMAIN_ALIASES = {
    "repo": "repository",
    "repository": "repository",
    "requirement": "requirement",
    "svn": "svn",
    "svnoperation": "svn",
    "operation": "svn",
    "conflict": "svn",
    "merge": "svn",
    "branch": "svn",
    "tag": "svn",
    "commit": "svn",
}

_PROTECTED_BUSINESS_DOMAIN_KEYS = set(_CLASS_DOMAIN_ALIASES.values())


def detect_language(root: Path) -> Language:
    if (root / "pom.xml").exists() or (root / "build.gradle").exists():
        return Language.JAVA
    if (root / "package.json").exists():
        return Language.JAVASCRIPT
    if (root / "pyproject.toml").exists() or (root / "setup.py").exists():
        return Language.PYTHON
    if (root / "go.mod").exists():
        return Language.GO
    return Language.JAVA


def find_java_source_root(path_parts: list[str]) -> int:
    # 找 LAST "java" — Maven 项目的 src/main/java 路径
    for i in range(len(path_parts) - 1, -1, -1):
        if path_parts[i] == "java":
            return i
    # fallback: first "src"
    for i, p in enumerate(path_parts):
        if p == "src":
            return i
    return -1


def find_src_root(path_parts: list[str]) -> int:
    for i, p in enumerate(path_parts):
        if p == "src":
            return i
    return 0


def extract_package_path(node: dict, language: Language, root_path: Path) -> str:
    source = node.get("source_file", "").replace("\\", "/")
    path_parts = source.split("/")

    if language == Language.JAVA:
        java_idx = find_java_source_root(path_parts)
        pkg_parts = path_parts[java_idx + 1:-1] if java_idx >= 0 else path_parts[:-1]
        return ".".join(pkg_parts)
    else:
        src_idx = find_src_root(path_parts)
    return ".".join(path_parts[src_idx + 1:-1])


def _node_language(node: dict, default_language: Language) -> Language:
    source = node.get("source_file", "").lower()
    label = node.get("label", "").lower()
    value = f"{source}/{label}"
    if value.endswith(".java"):
        return Language.JAVA
    if value.endswith((".js", ".jsx", ".ts", ".tsx", ".vue")):
        return Language.JAVASCRIPT
    return default_language


def extract_domain_key(pkg_path: str, language: Language) -> Optional[str]:
    if not pkg_path:
        return None

    parts = pkg_path.split(".")

    if language == Language.JAVA:
        company_roots = {"com", "org", "io", "net", "gov", "edu"}
        i = 0
        # 跳过 com/org 等 TLD
        if parts and parts[0] in company_roots:
            i = 1
            # 跳过公司名（下一段）
            if i < len(parts):
                i += 1
        # 跳过平台/产品名
        while i < len(parts) and parts[i] in _PLATFORM_NAMES:
            i += 1
        # 跳过已知的分层名（db/service/mapper 等），找真正的业务域
        while i < len(parts) and parts[i] in _LAYER_NAMES:
            i += 1
        if i < len(parts):
            return parts[i]
        # 回退：如果全部都是分层名，取第一个非公司/非平台的包名
        for p in parts:
            if p not in company_roots and p not in _PLATFORM_NAMES:
                return p
    elif language in (Language.JAVASCRIPT, Language.TYPESCRIPT):
        # 跳过框架目录名（api/views/components/store/router），取下一个
        skip = {"api", "views", "components", "store", "router", "utils", "assets"}
        if len(parts) >= 2 and parts[0] in skip:
            return parts[1]
        return parts[0]
    else:
        return parts[0] if parts else None

    return parts[0] if parts else None


def _domain_key_from_class_name(anchor: dict) -> Optional[str]:
    label = anchor.get("label", "")
    stem = Path(label).stem if "." in label else label
    stem = re.sub(
        r"(Controller|ServiceImpl|Service|Repository|Repo|Mapper|DAO|Dao|Entity|DTO|Dto|VO|Vo)$",
        "",
        stem,
    )
    compact = re.sub(r"[^a-z0-9]", "", stem.lower())
    if not compact:
        return None
    if compact in _CLASS_DOMAIN_ALIASES:
        return _CLASS_DOMAIN_ALIASES[compact]
    for token, alias in sorted(_CLASS_DOMAIN_ALIASES.items(), key=lambda item: -len(item[0])):
        if compact.startswith(token):
            return alias
    return compact


def _refine_domain_key(anchor: dict, key: Optional[str], language: Language) -> Optional[str]:
    if language == Language.JAVA:
        if key in _PLATFORM_NAMES or key in _LAYER_NAMES or key in {None, ""}:
            return _domain_key_from_class_name(anchor) or key
        return _CLASS_DOMAIN_ALIASES.get(key, key)

    if language in (Language.JAVASCRIPT, Language.TYPESCRIPT):
        source = anchor.get("source_file", "").replace("\\", "/").lower()
        parts = source.split("/")
        if "views" in parts:
            idx = parts.index("views")
            if idx + 1 < len(parts):
                view_key = parts[idx + 1]
                if view_key in _FRONTEND_GENERIC_DOMAIN_KEYS:
                    return None
                return _CLASS_DOMAIN_ALIASES.get(view_key, view_key)
        if key in {"api", "apis"}:
            file_stem = Path(source).stem
            if file_stem not in {"index", "api", "request", "http", "client"}:
                return _CLASS_DOMAIN_ALIASES.get(file_stem, file_stem)
        if key in _FRONTEND_GENERIC_DOMAIN_KEYS:
            return None
        return _CLASS_DOMAIN_ALIASES.get(key, key) if key else None

    return key


def _extract_module_name(source_file: str) -> Optional[str]:
    """从 Maven 项目路径中提取模块名（如 smccloud-ops-cpfr → cpfr）"""
    parts = source_file.replace("\\", "/").split("/")
    # 找 src/ 之前的目录作为模块名
    for i, p in enumerate(parts):
        if p == "src" and i > 0:
            module = parts[i - 1]
            # 提取模块名的最后一段作为业务提示（如 smccloud-ops-cpfr → cpfr）
            segments = module.replace("-", ".").split(".")
            # 跳过通用前缀（smccloud/ops/smc 等），取最后一个有意义的段
            meaningful = [s for s in segments if s not in _PLATFORM_NAMES and s not in _LAYER_NAMES]
            if meaningful:
                return meaningful[-1]
            # 如果全部是平台/分层名，取模块名自身
            return module
    return None


def cluster_by_package(
    anchors: list[dict],
    language: Language,
    root_path: Path,
) -> dict[str, list[dict]]:
    candidates = defaultdict(list)
    for anchor in anchors:
        node_language = _node_language(anchor, language)
        pkg = extract_package_path(anchor, node_language, root_path)
        raw_key = extract_domain_key(pkg, node_language)
        key = _refine_domain_key(anchor, raw_key, node_language)
        # 回退：如果包路径无法提取业务域键，尝试 Maven 模块名
        if node_language == Language.JAVA and (key is None or (key == raw_key and key in _LAYER_NAMES)):
            source = anchor.get("source_file", "")
            module_hint = _extract_module_name(source)
            if module_hint:
                key = module_hint
        if key:
            key = re.sub(r"[^a-z0-9]", "", key.lower())
            candidates[key].append(anchor)
    return dict(candidates)


# ── Step 4: import 聚合调整 ──

DOMAIN_MERGE_RULES = {
    ("bin", "bindata"): "bin-data",
    ("binorder", "preorder", "prepareorder", "cpfr"): "binorder",
    ("order", "bigorder", "orderdetail", "modifyorder", "specorder",
     "orderstate", "rcvorder", "returnorder", "receiveorder"): "order",
    ("purchase", "po", "purchaseorder", "requestpurchase"): "purchase",
    ("delivery", "deliver", "ship"): "delivery",
    ("warehouse", "stock", "inventory", "allot", "warehousemanage"): "inventory",
    ("invoice", "invoicedata", "billing"): "invoice",
    ("supplier", "vendor"): "supplier",
    ("product", "bom", "pd"): "product",
    ("customer", "client"): "customer",
    ("job", "task", "schedule", "xxl"): "job",
    ("event", "publisher", "listener"): "event",
    ("shikomi",): "shikomi",
    ("inquiry", "queryprice", "inqb"): "inquiry",
    ("dispatch",): "dispatch",
    ("sampleorder",): "sampleorder",
    ("ips",): "ips",
    ("csstock", "prestock"): "csstock",
    ("wmorder",): "wmorder",
}


def apply_merge_rules(candidates: dict) -> dict:
    merged = dict(candidates)
    for keywords, target in DOMAIN_MERGE_RULES.items():
        matched = {k: merged.pop(k) for k in keywords if k in merged}
        if matched and target is not None:
            combined = []
            for nodes in matched.values():
                combined.extend(nodes)
            merged[target] = combined
    return merged


def find_domain_for_node(
    node_id: str,
    domain_anchor_ids: dict[str, set[str]],
) -> Optional[str]:
    for domain_key, anchor_ids in domain_anchor_ids.items():
        if node_id in anchor_ids:
            return domain_key
    return None


def adjust_by_imports(
    candidates: dict[str, list[dict]],
    G: nx.Graph,
    merge_threshold: float,
    min_domain_size: int,
) -> list[Domain]:
    # 先应用预定义合并规则
    candidates = apply_merge_rules(candidates)

    domain_anchor_ids = {
        key: {a["id"] for a in anchors}
        for key, anchors in candidates.items()
    }

    # 构建域间 import 矩阵
    import_matrix = defaultdict(lambda: defaultdict(int))
    for src_id, tgt_id, data in G.edges(data=True):
        rel = data.get("relation", "")
        if rel not in ("imports", "imports_from"):
            continue
        src_domain = find_domain_for_node(src_id, domain_anchor_ids)
        tgt_domain = find_domain_for_node(tgt_id, domain_anchor_ids)
        if src_domain and tgt_domain and src_domain != tgt_domain:
            import_matrix[src_domain][tgt_domain] += 1

    # 合并高密度候选域
    keys = list(candidates.keys())
    merged = dict(candidates)
    changed = True
    while changed:
        changed = False
        keys = list(merged.keys())
        for i in range(len(keys)):
            for j in range(i + 1, len(keys)):
                a, b = keys[i], keys[j]
                if a not in merged or b not in merged:
                    continue
                mutual = import_matrix[a].get(b, 0) + import_matrix[b].get(a, 0)
                if len(merged[a]) > 0 and len(merged[b]) > 0:
                    density = mutual / (len(merged[a]) * len(merged[b]))
                    if a in _PROTECTED_BUSINESS_DOMAIN_KEYS and b in _PROTECTED_BUSINESS_DOMAIN_KEYS:
                        continue
                    if density > merge_threshold:
                        merged[a + "." + b] = merged.pop(a) + merged.pop(b)
                        changed = True
                        break
            if changed:
                break

    # 合并小域
    small_keys = [
        k for k, v in merged.items()
        if len(v) < min_domain_size and not _is_frontend_view_domain(k, v)
    ]
    big_keys = [k for k, v in merged.items() if len(v) >= min_domain_size]
    for sk in small_keys:
        if big_keys:
            best = max(big_keys, key=lambda bk: import_matrix[sk].get(bk, 0))
            merged[best].extend(merged.pop(sk))

    # 构建 Domain 对象
    # 先建立 anchor_id → domain_id 映射
    anchor_to_domain: dict[str, str] = {}
    for key, nodes in merged.items():
        for node in nodes:
            anchor_to_domain[node.get("id", "")] = key

    # 基于实际 import 边计算域间依赖
    dep_matrix: dict[str, dict[str, int]] = defaultdict(lambda: defaultdict(int))
    for src_id, tgt_id, data in G.edges(data=True):
        rel = data.get("relation", "")
        if rel not in ("imports", "imports_from"):
            continue
        src_d = anchor_to_domain.get(src_id, "")
        tgt_d = anchor_to_domain.get(tgt_id, "")
        if src_d and tgt_d and src_d != tgt_d:
            dep_matrix[src_d][tgt_d] += 1

    domains = []
    for key, nodes in merged.items():
        d = Domain(
            id=key,
            total_files=len(nodes),
            dependencies=[{"domain": k, "import_count": v} for k, v in dep_matrix.get(key, {}).items()],
        )
        for node in nodes:
            role = node.get("_role", "entity")
            d.anchors.setdefault(role, []).append(node)
        domains.append(d)

    return domains


def _is_frontend_view_domain(key: str, nodes: list[dict]) -> bool:
    marker = f"/views/{key}/"
    for node in nodes:
        source = node.get("source_file", "").replace("\\", "/").lower()
        if marker in f"/{source}":
            return True
    return False


# ── Step 5: 业务点提取 ──

def build_method_class_map(G: nx.Graph) -> dict[str, str]:
    """通过 contains 边构建 method_id → class_id 映射"""
    mapping = {}
    for src_id, tgt_id, data in G.edges(data=True):
        if data.get("relation") == "contains":
            mapping[tgt_id] = src_id
    return mapping


def analyze_cross_domain(
    method_id: str,
    G: nx.Graph,
    method_to_class: dict[str, str],
    domains: list[Domain],
) -> dict[str, list[str]]:
    """分析一个方法调用了哪些其他域"""
    domain_anchor_sets = {
        d.id: {a["id"] for a in d.anchors_flat()}
        for d in domains
    }

    called_domains = defaultdict(list)

    # 沿 calls 边找到被调用方法
    for _, tgt_id, data in G.edges(method_id, data=True):
        if data.get("relation") != "calls":
            continue
        parent_class = method_to_class.get(tgt_id, "")
        for d in domains:
            if parent_class in domain_anchor_sets.get(d.id, set()):
                label = G.nodes[tgt_id].get("label", tgt_id) if tgt_id in G.nodes else tgt_id
                called_domains[d.id].append(label)
                break

    return dict(called_domains)


def extract_business_points(
    domains: list[Domain],
    anchors: list[dict],
    G: nx.Graph,
) -> list[Domain]:
    method_to_class = build_method_class_map(G)

    # 构建 anchor_id → role 映射（从 domain.anchors 中读取，因为 _role 在 filter_noise 中设置）
    anchor_role_map: dict[str, str] = {}
    for domain in domains:
        for role_str, anchors in domain.anchors.items():
            for a in anchors:
                anchor_role_map[a["id"]] = role_str

    for domain in domains:
        domain_anchor_ids = {a["id"] for a in domain.anchors_flat()}
        business_points = []

        for anchor_id in domain_anchor_ids:
            role_str = anchor_role_map.get(anchor_id, "")
            if role_str not in ("controller", "service_impl"):
                continue

            # 提取 public 方法
            methods = []
            for _, tgt_id, data in G.edges(anchor_id, data=True):
                if data.get("relation") == "contains":
                    label = G.nodes[tgt_id].get("label", "") if tgt_id in G.nodes else ""
                    # 方法标签：Java 以 "." 开头，JS 以 "()" 结尾
                    is_method = label.startswith(".") or label.endswith(")")
                    if not is_method:
                        continue
                    name = label.lstrip(".")
                    # 过滤 getter/setter/Object 方法
                    if re.match(r"^(get|set|is)[A-Z]", name):
                        continue
                    if re.match(r"^(get|set|is)[a-z]", name):  # JS get/set prop()
                        continue
                    if name in _NON_BUSINESS_METHOD_NAMES:
                        continue
                    # 过滤单字符方法名（编译器生成/匿名方法）
                    bare = name.rstrip("()")
                    if len(bare) <= 1:
                        continue
                    if bare in _NON_BUSINESS_METHODS:
                        continue
                    methods.append(tgt_id)

            if not methods:
                anchor_data = G.nodes.get(anchor_id, {})
                business_points.append(BusinessPoint(
                    name=anchor_data.get("label", anchor_id),
                    entry_method=anchor_id,
                    entry_file=anchor_data.get("source_file", ""),
                    point_type=classify_business_point_type(anchor_data.get("label", anchor_id)),
                ))
                continue

            for method_id in methods:
                method_data = G.nodes.get(method_id, {})
                bp = BusinessPoint(
                    name=method_data.get("label", method_id),
                    entry_method=method_id,
                    entry_file=method_data.get("source_file", ""),
                    point_type=classify_business_point_type(method_data.get("label", method_id)),
                    cross_domain_calls=analyze_cross_domain(
                        method_id, G, method_to_class, domains,
                    ),
                )
                business_points.append(bp)

        domain.business_points = business_points

    return domains


# ── 主接口 ──

def business_cluster(
    G: nx.Graph,
    root_path: Path,
    language: Language = Language.AUTO,
    merge_threshold: float = 0.3,
    min_domain_size: int = 5,
) -> list[Domain]:
    if language == Language.AUTO:
        language = detect_language(root_path)

    # Step 1: 过滤噪音
    business_nodes = filter_noise(G)
    if not business_nodes:
        raise NoBusinessNodesError("未找到任何业务节点：项目可能没有 Java/JS 源文件")

    # Step 2: 提取锚点
    anchors = extract_anchors(business_nodes)
    if len(anchors) < 3:
        raise TooFewAnchorsError(f"锚点太少（{len(anchors)} 个），无法形成有意义的域划分")

    # Step 3: 包前缀聚类
    candidates = cluster_by_package(anchors, language, root_path)

    # Step 4: import 聚合调整
    domains = adjust_by_imports(candidates, G, merge_threshold, min_domain_size)

    # Step 5: 业务点提取
    domains = extract_business_points(domains, anchors, G)

    return sorted(domains, key=lambda d: -d.anchors_count())
