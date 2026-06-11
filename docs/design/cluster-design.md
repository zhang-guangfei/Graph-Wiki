# Cluster 模块详细设计

> 对应总体设计：第四章 4.2 节、第十一章 三层粒度模型  
> 状态：详细设计阶段  
> 依赖：`reuse.py`（graphifyy API 封装）

---

## 1. 模块职责

将 NetworkX 图（34K 节点）中的业务节点聚类为 20-30 个**业务域**，并将每个域内的 Controller/Service 公共方法分组为 **业务点**（500-800 个）。

**输入**：NetworkX Graph（`build_graph()` 输出）  
**输出**：`domains.json`（域划分 + 业务点列表）

---

## 2. 接口定义

```python
# graph_wiki/cluster.py

from dataclasses import dataclass, field
from enum import Enum
from pathlib import Path
import networkx as nx
from collections import defaultdict
import re
from typing import Optional

# ── 数据模型 ──

class Language(Enum):
    JAVA = "java"
    JAVASCRIPT = "javascript"
    TYPESCRIPT = "typescript"
    PYTHON = "python"
    GO = "go"
    AUTO = "auto"

class NodeRole(Enum):
    CONTROLLER = "controller"
    SERVICE_IMPL = "service_impl"
    SERVICE_API = "service_api"
    MAPPER = "mapper"
    DAO = "dao"
    ADAPTER = "adapter"
    ENTITY = "entity"
    DTO = "dto"
    VO = "vo"
    ENUM = "enum"
    HANDLER = "handler"
    CONFIG = "config"
    UTIL = "util"
    NOISE = "noise"              # 方法调用、getter/setter、JDK 类型

@dataclass
class BusinessPoint:
    """业务点"""
    name: str                    # 方法名（如 "listBinOrderDetail"）
    display_name: str            # LLM 标注的中文名（如 "Bin订单明细查询"）
    entry_method: str            # 入口方法节点 ID
    entry_file: str              # 入口方法所在文件
    call_chain: list[str]        # 调用链（方法节点 ID 列表）
    cross_domain_calls: dict     # 跨域调用 {domain_name: [method_labels]}
    internal_calls: list[str]    # 域内调用
    infrastructure_calls: list[str]  # 基础设施调用

@dataclass
class Domain:
    """业务域"""
    id: str                      # 唯一英文 ID（如 "bin-data"）
    name: str                    # LLM 标注的中文名（如 "Bin数据管理"）
    packages: list[str]          # 包含的 Java 包路径
    modules: list[str]           # 包含的 Maven 模块
    frontend_views: list[str]    # 包含的前端视图目录
    anchors: dict[str, list]     # 按角色分组的锚点
    business_points: list[BusinessPoint]
    total_files: int
    dependencies: dict[str, int] # {domain_id: import_count}

# ── 主接口 ──

def business_cluster(
    G: nx.Graph,
    root_path: Path,
    language: Language = Language.AUTO,
    merge_threshold: float = 0.3,
    min_domain_size: int = 5,
) -> list[Domain]:
    """
    主入口：将代码图聚类为业务域。

    Args:
        G: NetworkX Graph（来自 build_graph）
        root_path: 项目根目录（用于解析文件路径）
        language: 项目语言（auto 时自动检测）
        merge_threshold: 候选域合并阈值（0-1，越大越不容易合并）
        min_domain_size: 最小域大小（锚点数少于此的候选域被合并）

    Returns:
        排序后的 Domain 列表（按锚点数降序）
    """
    if language == Language.AUTO:
        language = detect_language(root_path)
    
    # Step 1: 过滤噪音
    business_nodes = filter_noise(G, root_path)
    
    # Step 2: 提取锚点（Controller/Service/Mapper）
    anchors = extract_anchors(business_nodes)
    
    # Step 3: 包路径聚类
    candidates = cluster_by_package(anchors, language, root_path)
    
    # Step 4: import 聚合调整
    domains = adjust_by_imports(candidates, G, merge_threshold, min_domain_size)
    
    # Step 5: 业务点提取
    domains = extract_business_points(domains, anchors, G)
    
    return sorted(domains, key=lambda d: -d.anchors_count())
```

---

## 3. Step 1：噪音过滤

### 3.1 过滤规则

```python
def filter_noise(G: nx.Graph, root_path: Path) -> list[dict]:
    """
    从 34K 节点中过滤出 ~2,800 个业务节点。

    排除规则（按优先级）：
    1. file_type 不是 'code'
    2. label 不以 .java/.vue/.js/.ts/.py 结尾
    3. label 以 '.' 或 '_' 开头（匿名方法调用）
    4. label 匹配 JDK 基础类型（String, Integer, Long, Date...）
    5. label 匹配 getter/setter 模式（getXxx, setXxx, isXxx）
    6. source_file 在 src/test/ 下
    7. 角色分类为 CONFIG/UTIL/NOISE
    """
    filtered = []
    for node_id, data in G.nodes(data=True):
        if not is_business_relevant(data, root_path):
            continue
        role = classify_role(data)
        if role in (NodeRole.CONFIG, NodeRole.UTIL, NodeRole.NOISE):
            continue
        data['_role'] = role.value
        data['_importance'] = role_importance(role)
        filtered.append(data)
    return filtered
```

### 3.2 业务相关判断

```python
def is_business_relevant(data: dict, root_path: Path) -> bool:
    label = data.get('label', '')
    source = data.get('source_file', '')
    
    # 排除非代码文件
    if data.get('file_type') != 'code':
        return False
    
    # 排除测试文件
    if '/src/test/' in source.replace('\\', '/'):
        return False
    
    # 排除匿名方法和 JDK 类型
    if label.startswith('.') or label.startswith('_'):
        return False
    
    NOISE_LABELS = {
        'String', 'Integer', 'Long', 'Double', 'Float', 'Boolean',
        'BigDecimal', 'Date', 'List', 'Map', 'Set', 'Object', 'Class',
        'void', 'int', 'long', 'boolean', 'byte', 'char',
        'Serializable', 'Cloneable', 'Comparable',
    }
    if label in NOISE_LABELS:
        return False
    
    # 排除 getter/setter/toString
    if re.match(r'^(get|set|is)[A-Z]', label):
        return False
    if label in ('toString()', 'equals()', 'hashCode()', 'clone()', 'finalize()'):
        return False
    
    # 必须是以 .java/.vue/.js 等结尾的源文件
    VALID_SUFFIXES = ('.java', '.vue', '.js', '.ts', '.jsx', '.tsx', '.py', '.go')
    if not label.endswith(VALID_SUFFIXES):
        return False
    
    return True
```

### 3.3 角色分类

```python
def classify_role(data: dict) -> NodeRole:
    label = data.get('label', '').lower()
    source = data.get('source_file', '').lower()
    
    # Java
    if 'controller' in label and label.endswith('.java'):
        return NodeRole.CONTROLLER
    if 'serviceimpl' in label and label.endswith('.java'):
        return NodeRole.SERVICE_IMPL
    if 'service' in label and 'serviceimpl' not in label and label.endswith('.java'):
        return NodeRole.SERVICE_API
    if 'mapper' in label and label.endswith('.java'):
        return NodeRole.MAPPER
    if 'dao' in label and label.endswith('.java'):
        return NodeRole.DAO
    if 'adapter' in label and label.endswith('.java'):
        return NodeRole.ADAPTER
    if label.endswith('.java'):
        if any(kw in label for kw in ('config', 'configuration', 'properties')):
            return NodeRole.CONFIG
        if any(kw in label for kw in ('util', 'helper', 'tool', 'constant')):
            return NodeRole.UTIL
        if 'entity' in label or label.endswith('do.java'):
            return NodeRole.ENTITY
        if 'dto' in label:
            return NodeRole.DTO
        if 'vo.java' in label:
            return NodeRole.VO
        if 'enum' in label:
            return NodeRole.ENUM
    
    # Vue/JS
    if label.endswith('.vue'):
        if '/components/' in source:
            return NodeRole.HANDLER  # 通用组件
        # views/ 下的页面组件
        return NodeRole.CONTROLLER  # Vue 页面 = 前端 Controller
    
    if label.endswith(('.js', '.ts')):
        if '/api/' in source:
            return NodeRole.CONTROLLER  # API 调用文件 = 前端 Controller
        if any(kw in source for kw in ('/utils/', '/helpers/', '/constants/')):
            return NodeRole.UTIL
    
    return NodeRole.ENTITY  # 默认：普通实体
```

---

## 4. Step 2：提取锚点

```python
ANCHOR_ROLES = {
    NodeRole.CONTROLLER, NodeRole.SERVICE_IMPL, NodeRole.SERVICE_API,
    NodeRole.MAPPER, NodeRole.DAO, NodeRole.ADAPTER,
}

def extract_anchors(business_nodes: list[dict]) -> list[dict]:
    """从业务节点中提取锚点"""
    return [n for n in business_nodes if NodeRole(n['_role']) in ANCHOR_ROLES]
```

---

## 5. Step 3：包路径聚类

### 5.1 包路径提取

```python
def extract_package_path(node: dict, language: Language, root_path: Path) -> str:
    """从 source_file 提取可聚类路径"""
    source = node['source_file'].replace('\\', '/')
    
    if language in (Language.JAVA,):
        # com/smc/smccloud/bin/service/impl/BindataServiceImpl.java
        # → ['com', 'smc', 'smccloud', 'bin', 'service', 'impl']
        path_parts = source.split('/')
        # 找到 java/ 或 src/main/java/ 之后的路径
        java_idx = find_java_source_root(path_parts)
        pkg_parts = path_parts[java_idx + 1:-1]  # 去掉文件名
        return '.'.join(pkg_parts)
    
    elif language in (Language.JAVASCRIPT, Language.TYPESCRIPT):
        # src/api/binorder.js → api/binorder
        # src/views/csstock/apply/index.vue → views/csstock/apply
        src_idx = find_src_root(path_parts)
        return '.'.join(path_parts[src_idx + 1:-1])
    
    elif language == Language.PYTHON:
        # ops/bin/service/bindata_service.py → bin.service
        src_idx = find_src_root(path_parts)
        return '.'.join(path_parts[src_idx + 1:-1])
    
    elif language == Language.GO:
        # internal/bin/service.go
        src_idx = find_src_root(path_parts)
        return '.'.join(path_parts[src_idx + 1:-1])
    
    return source
```

### 5.2 域键提取

```python
def extract_domain_key(pkg_path: str, language: Language) -> Optional[str]:
    """
    从包路径提取候选域键。

    Java:   com.smc.smccloud.bin.service.impl → 'bin'
            提取公司域名后第 1 级
            org.springframework.web → 'springframework' (无公司域名，取根)
    
    JS/TS:  api.binorder → 'binorder'
            views.csstock.apply → 'csstock'
            取 src/ 后第 1 级
    
    Python: bin.service → 'bin'
            取 root 后第 1 级
    
    Go:     bin.service → 'bin'
            取 root 后第 1 级
    """
    if not pkg_path:
        return None
    
    parts = pkg_path.split('.')
    
    if language == Language.JAVA:
        # 找到公司域名的最后一级（com 或 org 或 io 之后的包）
        company_roots = {'com', 'org', 'io', 'net', 'gov', 'edu'}
        start_idx = 0
        for i, part in enumerate(parts):
            if part in company_roots:
                start_idx = i + 2  # 跳过 com.company
                break
        if start_idx < len(parts):
            return parts[start_idx]
    
    elif language in (Language.JAVASCRIPT, Language.TYPESCRIPT):
        if len(parts) >= 1:
            return parts[0]  # api/views/components 后第一级
        return parts[0]
    
    else:  # Python, Go, etc.
        if len(parts) >= 1:
            return parts[0]
    
    return parts[0] if parts else None
```

### 5.3 候选域聚类

```python
def cluster_by_package(
    anchors: list[dict], 
    language: Language, 
    root_path: Path
) -> dict[str, list[dict]]:
    """
    按域键将锚点分组为候选域。

    返回: {domain_key: [anchor_nodes]}
    """
    candidates = defaultdict(list)
    unseen = set()
    
    for anchor in anchors:
        pkg = extract_package_path(anchor, language, root_path)
        key = extract_domain_key(pkg, language)
        if key:
            # 标准化：小写 + 去除非字母数字
            key = re.sub(r'[^a-z0-9]', '', key.lower())
            candidates[key].append(anchor)
    
    return dict(candidates)
```

---

## 6. Step 4：import 聚合调整

### 6.1 域间 import 密度计算

```python
def adjust_by_imports(
    candidates: dict[str, list[dict]],
    G: nx.Graph,
    merge_threshold: float,
    min_domain_size: int,
) -> list[Domain]:
    """
    1. 计算候选域之间的 import 密度矩阵
    2. 合并互相 import 密度 > merge_threshold 的候选域
    3. 将 < min_domain_size 的候选域合并到最近的域
    """
    domain_keys = list(candidates.keys())
    n = len(domain_keys)
    
    # 构建域间 import 矩阵
    import_matrix = defaultdict(lambda: defaultdict(int))
    domain_anchor_ids = {
        key: {a['id'] for a in anchors}
        for key, anchors in candidates.items()
    }
    
    for link in G.edges(data=True):
        if link[2].get('relation') != 'imports':
            continue
        src, tgt = link[0], link[1]
        src_domain = find_domain_for_node(src, domain_anchor_ids)
        tgt_domain = find_domain_for_node(tgt, domain_anchor_ids)
        if src_domain and tgt_domain and src_domain != tgt_domain:
            import_matrix[src_domain][tgt_domain] += 1
    
    # 合并高密度域
    merged_domains = merge_dense_candidates(
        candidates, import_matrix, domain_anchor_ids, merge_threshold
    )
    
    # 合并小域
    merged_domains = merge_small_domains(
        merged_domains, import_matrix, min_domain_size
    )
    
    return [create_domain(key, nodes) for key, nodes in merged_domains.items()]


def merge_dense_candidates(candidates, import_matrix, anchor_ids, threshold):
    """
    如果两个候选域相互 import 密度 > threshold，合并它们。
    
    密度 = mutual_imports / (size_A * size_B)
    """
    # 贪心合并
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
                density = mutual / (len(merged[a]) * len(merged[b]))
                if density > threshold:
                    merged[a + '.' + b] = merged.pop(a) + merged.pop(b)
                    changed = True
                    break
            if changed:
                break
    return merged
```

### 6.2 相似候选域合并

```python
# 关键词合并规则（处理 bin/bindata/binorder → 'Bin数据管理' 的情况）
DOMAIN_MERGE_RULES = {
    # (关键词组) → 合并后的域 ID
    ('bin', 'bindata', 'binorder'): 'bin-data',
    ('order', 'preorder', 'bigorder', 'orderdetail'): 'order',
    ('purchase', 'po', 'purchaseorder'): 'purchase',
    ('delivery', 'deliver', 'ship'): 'delivery',
    ('warehouse', 'stock', 'inventory', 'allot'): 'inventory',
    ('invoice', 'invoicedata', 'billing'): 'invoice',
    ('supplier', 'vendor'): 'supplier',
    ('product', 'bom', 'pd'): 'product',
    ('customer', 'client'): 'customer',
    ('job', 'task', 'schedule', 'xxl'): 'job',
    ('event', 'publisher', 'listener'): 'event',
    ('config', 'setting', 'properties'): None,  # None = 排除，不是业务域
}

def apply_merge_rules(candidates: dict) -> dict:
    """
    应用预定义的关键词合并规则。
    例如: 'bin' + 'bindata' + 'binorder' → 'bin-data'
    """
    merged = dict(candidates)
    for keywords, target in DOMAIN_MERGE_RULES.items():
        matched = {k: merged.pop(k) for k in keywords if k in merged}
        if matched:
            if target is not None:
                combined = []
                for nodes in matched.values():
                    combined.extend(nodes)
                merged[target] = combined
    return merged
```

---

## 7. Step 5：业务点提取

```python
def extract_business_points(
    domains: list[Domain],
    anchors: list[dict],
    G: nx.Graph,
) -> list[Domain]:
    """
    对每个域，提取其 public Controller/Service 方法作为业务点。
    同时分析跨域调用。
    """
    # 构建 method → parent_class 映射（通过 contains 边）
    method_to_class = build_method_class_map(G)
    
    for domain in domains:
        domain_anchor_ids = {a['id'] for a in domain.anchors_flat()}
        business_points = []
        
        for anchor_id in domain_anchor_ids:
            anchor = find_node(G, anchor_id)
            role = NodeRole(anchor['_role'])
            
            # 对每个 Controller/ServiceImpl，提取其 public 方法
            if role in (NodeRole.CONTROLLER, NodeRole.SERVICE_IMPL):
                methods = get_public_methods(anchor_id, G, method_to_class)
                for method_id in methods:
                    method = G.nodes[method_id]
                    bp = BusinessPoint(
                        name=method['label'],
                        display_name='',  # 由 label.py 填充
                        entry_method=method_id,
                        entry_file=method.get('source_file', ''),
                        call_chain=extract_call_chain(method_id, G),
                        cross_domain_calls=analyze_cross_domain(
                            method_id, G, method_to_class, domains
                        ),
                        internal_calls=[],
                        infrastructure_calls=[],
                    )
                    business_points.append(bp)
        
        domain.business_points = business_points
    
    return domains


def analyze_cross_domain(
    method_id: str,
    G: nx.Graph,
    method_to_class: dict,
    domains: list[Domain],
) -> dict[str, list[str]]:
    """
    分析一个方法调用了哪些其他域（参见总体设计 12.7 节伪代码）。
    
    1. 沿 calls 边找到被调用方法
    2. 通过 contains 边找到被调用方法的父类
    3. 将父类映射到域
    4. 返回 {domain_name: [called_method_labels]}
    """
    # 见总体设计 12.7 节完整伪代码
    pass
```

---

## 8. 依赖关系

```
cluster.py 依赖:
  ├── reuse.py            ← graphifyy API (detect/build)
  │     └── graphifyy      ← Python 包（external）
  ├── models.py            ← Domain, BusinessPoint, NodeRole 数据类
  └── networkx             ← Python 包（external）
```

---

## 9. 错误处理

```python
class ClusterError(Exception): pass

class NoBusinessNodesError(ClusterError):
    """未找到任何业务节点：项目可能没有 Java/JS 源文件"""

class TooFewAnchorsError(ClusterError):
    """锚点太少（< 3）：无法形成有意义的域划分"""

class LanguageDetectionError(ClusterError):
    """无法自动检测项目语言：需要手动指定 --language"""
```

---

## 10. 测试用例

```python
# tests/test_cluster.py

def test_filter_noise_removes_method_calls():
    """验证 34K → ~2.8K 的噪音过滤"""
    pass

def test_extract_domain_key_java():
    """验证 com.smc.smccloud.bin.service → 'bin'"""
    pass

def test_extract_domain_key_javascript():
    """验证 src/api/binorder.js → 'binorder'"""
    pass

def test_merge_similar_candidates():
    """验证 bin + bindata + binorder → bin-data"""
    pass

def test_analyze_cross_domain_detects_inventory_call():
    """验证跨域调用分析能检测到库存域调用"""
    pass
```
