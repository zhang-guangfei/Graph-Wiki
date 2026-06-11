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

# ── Step 1: 噪音过滤 ──

NOISE_LABELS = {
    "String", "Integer", "Long", "Double", "Float", "Boolean",
    "BigDecimal", "Date", "List", "Map", "Set", "Object", "Class",
    "void", "int", "long", "boolean", "byte", "char",
    "Serializable", "Cloneable", "Comparable",
}

VALID_SUFFIXES = (".java", ".vue", ".js", ".ts", ".jsx", ".tsx", ".py", ".go")


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
        if "/api/" in source:
            return NodeRole.CONTROLLER
        if any(kw in source for kw in ("/utils/", "/helpers/", "/constants/")):
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


def filter_noise(G: nx.Graph) -> list[dict]:
    filtered = []
    for node_id, data in G.nodes(data=True):
        if not is_business_relevant(data):
            continue
        role = classify_role(data)
        if role in (NodeRole.CONFIG, NodeRole.UTIL, NodeRole.NOISE):
            continue
        data["_role"] = role.value
        data["_importance"] = role_importance(role)
        filtered.append(data)
    return filtered


# ── Step 2: 锚点提取 ──

def extract_anchors(business_nodes: list[dict]) -> list[dict]:
    return [n for n in business_nodes if NodeRole(n["_role"]) in ANCHOR_ROLES]


# ── Step 3: 包路径聚类 ──

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
    for i, p in enumerate(path_parts):
        if p in ("java", "src"):
            return i
    return 0


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
        pkg_parts = path_parts[java_idx + 1:-1]
        return ".".join(pkg_parts)
    else:
        src_idx = find_src_root(path_parts)
        return ".".join(path_parts[src_idx + 1:-1])


def extract_domain_key(pkg_path: str, language: Language) -> Optional[str]:
    if not pkg_path:
        return None

    parts = pkg_path.split(".")

    if language == Language.JAVA:
        # 跳过公司域名（com/org/io + 公司名），找第一个"业务级"包
        company_roots = {"com", "org", "io", "net", "gov", "edu"}
        i = 0
        # 跳过 com/org 等 TLD
        if parts and parts[0] in company_roots:
            i = 1
            # 跳过公司名（下一个段）
            if i < len(parts):
                i += 1
        # 如果接下来的段包含子包（像 smccloud 下面有 bin/order 等），
        # 继续跳过平台/产品名
        while i < len(parts) and parts[i] in ("smccloud", "ops", "hshcore", "platform"):
            i += 1
        if i < len(parts):
            return parts[i]
    elif language in (Language.JAVASCRIPT, Language.TYPESCRIPT):
        # 跳过框架目录名（api/views/components/store/router），取下一个
        skip = {"api", "views", "components", "store", "router", "utils", "assets"}
        if len(parts) >= 2 and parts[0] in skip:
            return parts[1]
        return parts[0]
    else:
        return parts[0] if parts else None

    return parts[0] if parts else None


def cluster_by_package(
    anchors: list[dict],
    language: Language,
    root_path: Path,
) -> dict[str, list[dict]]:
    candidates = defaultdict(list)
    for anchor in anchors:
        pkg = extract_package_path(anchor, language, root_path)
        key = extract_domain_key(pkg, language)
        if key:
            key = re.sub(r"[^a-z0-9]", "", key.lower())
            candidates[key].append(anchor)
    return dict(candidates)


# ── Step 4: import 聚合调整 ──

DOMAIN_MERGE_RULES = {
    ("bin", "bindata", "binorder"): "bin-data",
    ("order", "preorder", "bigorder", "orderdetail"): "order",
    ("purchase", "po", "purchaseorder"): "purchase",
    ("delivery", "deliver", "ship"): "delivery",
    ("warehouse", "stock", "inventory", "allot"): "inventory",
    ("invoice", "invoicedata", "billing"): "invoice",
    ("supplier", "vendor"): "supplier",
    ("product", "bom", "pd"): "product",
    ("customer", "client"): "customer",
    ("job", "task", "schedule", "xxl"): "job",
    ("event", "publisher", "listener"): "event",
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
    for _, _, data in G.edges(data=True):
        if data.get("relation") != "imports":
            continue
        src = data.get("source", data.get("_src", ""))
        tgt = data.get("target", data.get("_tgt", ""))
        src_domain = find_domain_for_node(src, domain_anchor_ids)
        tgt_domain = find_domain_for_node(tgt, domain_anchor_ids)
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
                    if density > merge_threshold:
                        merged[a + "." + b] = merged.pop(a) + merged.pop(b)
                        changed = True
                        break
            if changed:
                break

    # 合并小域
    small_keys = [k for k, v in merged.items() if len(v) < min_domain_size]
    big_keys = [k for k, v in merged.items() if len(v) >= min_domain_size]
    for sk in small_keys:
        if big_keys:
            best = max(big_keys, key=lambda bk: import_matrix[sk].get(bk, 0))
            merged[best].extend(merged.pop(sk))

    # 构建 Domain 对象
    domains = []
    for key, nodes in merged.items():
        d = Domain(id=key, total_files=len(nodes))
        for node in nodes:
            role = node.get("_role", "entity")
            d.anchors.setdefault(role, []).append(node)
        domains.append(d)

    return domains


# ── Step 5: 业务点提取 ──

def build_method_class_map(G: nx.Graph) -> dict[str, str]:
    """通过 contains 边构建 method_id → class_id 映射"""
    mapping = {}
    for _, _, data in G.edges(data=True):
        if data.get("relation") == "contains":
            src = data.get("source", data.get("_src", ""))
            tgt = data.get("target", data.get("_tgt", ""))
            mapping[tgt] = src
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
    for _, tgt, data in G.edges(method_id, data=True):
        if data.get("relation") != "calls":
            continue
        parent_class = method_to_class.get(tgt, "")
        for d in domains:
            if parent_class in domain_anchor_sets.get(d.id, set()):
                label = G.nodes[tgt].get("label", tgt) if tgt in G.nodes else tgt
                called_domains[d.id].append(label)
                break

    return dict(called_domains)


def extract_business_points(
    domains: list[Domain],
    anchors: list[dict],
    G: nx.Graph,
) -> list[Domain]:
    method_to_class = build_method_class_map(G)

    for domain in domains:
        domain_anchor_ids = {a["id"] for a in domain.anchors_flat()}
        business_points = []

        for anchor_id in domain_anchor_ids:
            anchor_data = G.nodes.get(anchor_id, {})
            role_str = anchor_data.get("_role", "")
            if role_str not in ("controller", "service_impl"):
                continue

            # 提取 public 方法
            methods = []
            for _, tgt, data in G.edges(anchor_id, data=True):
                if data.get("relation") == "contains":
                    label = G.nodes[tgt].get("label", "") if tgt in G.nodes else ""
                    if not label.startswith("."):
                        continue
                    if re.match(r"^(get|set|is)[A-Z]", label.lstrip(".")):
                        continue
                    if label in (".toString()", ".equals()", ".hashCode()"):
                        continue
                    methods.append(tgt)

            for method_id in methods:
                method_data = G.nodes.get(method_id, {})
                bp = BusinessPoint(
                    name=method_data.get("label", method_id),
                    entry_method=method_id,
                    entry_file=method_data.get("source_file", ""),
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
        raise ValueError("未找到任何业务节点：项目可能没有 Java/JS 源文件")

    # Step 2: 提取锚点
    anchors = extract_anchors(business_nodes)
    if len(anchors) < 3:
        raise ValueError(f"锚点太少（{len(anchors)} 个），无法形成有意义的域划分")

    # Step 3: 包前缀聚类
    candidates = cluster_by_package(anchors, language, root_path)

    # Step 4: import 聚合调整
    domains = adjust_by_imports(candidates, G, merge_threshold, min_domain_size)

    # Step 5: 业务点提取
    domains = extract_business_points(domains, anchors, G)

    return sorted(domains, key=lambda d: -d.anchors_count())
