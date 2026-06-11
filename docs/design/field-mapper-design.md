# Field Mapper 模块详细设计

> 对应总体设计：第四章 4.7 节  
> 状态：详细设计阶段  
> 依赖：`api_mapper.py`（API 映射结果）、`cluster.py`（域划分）

---

## 1. 模块职责

构建字段级别的六层数据流链路：`前端字段 → API 参数 → DTO 字段 → Entity 字段 → 数据库列`。

**输入**：
- `api-map.json`（来自 api_mapper.py）
- 后端 Entity/DO 类（带 JPA/MyBatis-Plus 注解）
- 后端 DTO 类

**输出**：
- `field-map.json`（按域拆分）
- `wiki/{domain}/data-flow.md`

---

## 2. 接口定义

```python
# graph_wiki/field_mapper.py

def build_field_map(
    api_matches: list,     # ApiMatch 列表
    entity_dir: Path,      # Entity 类目录
    dto_dir: Path,         # DTO 类目录
    backend_root: Path,
) -> dict:
    """
    三步流程：
    1. extract_entity_db_mapping() — Entity 注解 → 数据库列映射
    2. match_dto_to_entity()       — DTO 字段 → Entity 字段匹配
    3. build_full_chain()          — 串联 api_mapper 输出，生成六层链路
    """
    entity_map = extract_entity_db_mapping(entity_dir, backend_root)
    dto_map = extract_dto_fields(dto_dir, backend_root)
    field_matches = match_dto_to_entity(dto_map, entity_map)
    full_chain = build_full_chain(api_matches, field_matches, entity_map)
    return full_chain
```

---

## 3. Entity → 数据库列映射

### 3.1 注解解析

```python
import re

# 支持的注解
ANNOTATION_PATTERNS = {
    "mybatis_plus": {
        "table": re.compile(r'@TableName\s*\(\s*["\']([^"\']+)["\']'),
        "field": re.compile(r'@TableField\s*\(\s*["\']([^"\']+)["\']'),
        "id": re.compile(r'@TableId'),
    },
    "jpa": {
        "table": re.compile(r'@Table\s*\(\s*name\s*=\s*["\']([^"\']+)["\']'),
        "column": re.compile(r'@Column\s*\(\s*name\s*=\s*["\']([^"\']+)["\']'),
    },
}

def extract_entity_db_mapping(entity_dir: Path, backend_root: Path) -> dict:
    """
    解析 Entity 类的注解，构建 Java 字段 → 数据库列映射。

    遍历模式：
      ops-backend/smccloud-ops/**/entity/*.java
      ops-backend/ops-db-entity/**/*.java
      ops-backend/ops-dto/**/entity/*.java

    输出：
    {
      "BindataApply": {
        "table": "bin_data_apply",
        "file": "smccloud-ops/.../BindataApply.java",
        "fields": [
          {"java_field": "id", "db_column": "id", "annotation": "@TableId", "type": "Long"},
          {"java_field": "supplierCode", "db_column": "supplier_code",
           "annotation": "@TableField(\"supplier_code\")", "type": "String"},
        ]
      }
    }
    """
    entity_map = {}
    
    for java_file in entity_dir.rglob("*.java"):
        if "/src/test/" in str(java_file):
            continue
        source = java_file.read_text(encoding="utf-8")
        
        # 判断是否为 Entity
        if not is_entity_class(source):
            continue
        
        class_name = extract_class_name(source)
        table_name = extract_table_name(source)
        
        fields = []
        # 提取每个字段的注解和类型
        field_pattern = re.compile(
            r"""(@\w+(?:\([^)]*\))?\s*)*       # 注解
                private\s+(\S+)\s+(\w+)\s*;""",
            re.VERBOSE
        )
        for match in field_pattern.finditer(source):
            annotation_block = match.group(1) or ""
            java_type = match.group(2)
            field_name = match.group(3)
            
            db_column = extract_db_column(annotation_block, field_name)
            annotation_name = extract_annotation_name(annotation_block)
            
            fields.append({
                "java_field": field_name,
                "db_column": db_column,
                "java_type": java_type,
                "annotation": annotation_name,
            })
        
        entity_map[class_name] = {
            "table": table_name,
            "file": str(java_file.relative_to(backend_root)),
            "fields": fields,
        }
    
    return entity_map


def is_entity_class(source: str) -> bool:
    """判断是否为 Entity 类（有 @TableName 或 @Entity 或 @Table 注解）"""
    indicators = ["@TableName", "@Entity", "@Table("]
    return any(indicator in source for indicator in indicators)


def extract_table_name(source: str) -> str:
    """提取表名"""
    for pattern in [
        re.compile(r'@TableName\s*\(\s*["\']([^"\']+)["\']'),
        re.compile(r'@Table\s*\(\s*name\s*=\s*["\']([^"\']+)["\']'),
    ]:
        match = pattern.search(source)
        if match:
            return match.group(1)
    # 回退：类名驼峰转下划线
    return camel_to_snake(extract_class_name(source))


def extract_db_column(annotation_block: str, field_name: str) -> str:
    """从注解中提取数据库列名，无注解时使用驼峰转下划线"""
    if not annotation_block:
        return camel_to_snake(field_name)
    
    # @TableField("supplier_code")
    match = re.search(r'@TableField\s*\(\s*["\']([^"\']+)["\']', annotation_block)
    if match:
        return match.group(1)
    
    # @Column(name = "supplier_code")
    match = re.search(r'@Column\s*\([^)]*name\s*=\s*["\']([^"\']+)["\']', annotation_block)
    if match:
        return match.group(1)
    
    return camel_to_snake(field_name)


def camel_to_snake(name: str) -> str:
    """驼峰转下划线：supplierCode → supplier_code"""
    result = re.sub(r'([A-Z])', r'_\1', name).lower()
    return result.lstrip("_")
```

---

## 4. DTO → Entity 字段匹配

```python
def match_dto_to_entity(
    dto_map: dict[str, list[dict]],
    entity_map: dict[str, dict],
) -> list[dict]:
    """
    将 DTO 字段匹配到 Entity 字段。

    匹配策略（按优先级）：
    1. 完全同名 → 置信度 1.0
    2. 去掉前缀后同名 → 0.9（dto.supplierCode ≈ entity.supplierCode）
    3. 驼峰转下划线后同名 → 0.9
    4. 包含匹配（子串） → 0.6
    5. 相同类型 + 相似名 → 0.4

    返回: [{dto_class, dto_field, entity_class, entity_field, confidence, match_type}]
    """
    results = []
    
    for dto_class, dto_fields in dto_map.items():
        # 推断对应的 Entity 类名（去掉 DTO 后缀）
        entity_candidates = find_entity_candidates(dto_class, entity_map)
        
        for dto_field in dto_fields:
            best_match = None
            best_score = 0
            
            for entity_class in entity_candidates:
                if entity_class not in entity_map:
                    continue
                for entity_field in entity_map[entity_class]["fields"]:
                    score, match_type = field_match_score(
                        dto_field["name"], dto_field["type"],
                        entity_field["java_field"], entity_field["java_type"]
                    )
                    if score > best_score:
                        best_score = score
                        best_match = {
                            "dto_class": dto_class,
                            "dto_field": dto_field["name"],
                            "entity_class": entity_class,
                            "entity_field": entity_field["java_field"],
                            "db_column": entity_field["db_column"],
                            "confidence": score,
                            "match_type": match_type,
                        }
            
            if best_match:
                results.append(best_match)
    
    return results


def field_match_score(
    dto_name: str, dto_type: str,
    entity_name: str, entity_type: str,
) -> tuple[float, str]:
    """计算两个字段的匹配度"""
    # 完全同名
    if dto_name == entity_name:
        return 1.0, "exact"
    # 驼峰转下划线后同名
    if camel_to_snake(dto_name) == camel_to_snake(entity_name):
        return 0.9, "snake_match"
    # 去掉常见前缀后匹配
    dto_clean = re.sub(r'^(dto|vo|req|resp)', '', dto_name, flags=re.IGNORECASE)
    entity_clean = re.sub(r'^(entity|do|po)', '', entity_name, flags=re.IGNORECASE)
    if dto_clean.lower() == entity_clean.lower():
        return 0.85, "prefix_removed"
    # 相同类型 + 包含匹配
    if dto_type == entity_type and (
        dto_name.lower() in entity_name.lower() or
        entity_name.lower() in dto_name.lower()
    ):
        return 0.6, "type_and_substring"
    return 0.0, "none"
```

---

## 5. 完整链路构建

```python
def build_full_chain(
    api_matches: list,
    field_matches: list[dict],
    entity_map: dict,
) -> dict:
    """
    将 api_mapper 输出和 field_mapper 输出串联为六层链路。

    六层链路：
      layer_1_vue:      前端 Vue 组件字段
      layer_2_api:      前端 API 请求参数
      layer_3_controller: 后端 Controller 入参
      layer_4_dto:      后端 DTO 字段
      layer_5_entity:   后端 Entity 字段
      layer_6_db:       数据库列
    """
    # 构建 DTO → Entity 索引
    dto_entity_index = defaultdict(list)
    for fm in field_matches:
        key = (fm["dto_class"], fm["dto_field"])
        dto_entity_index[key].append(fm)
    
    # 构建 domain → table 聚合
    domain_tables = defaultdict(lambda: defaultdict(list))
    
    for match in api_matches:
        domain = match.domain
        dto_class = match.backend.param_type or ""
        
        for param in match.backend.param_fields:
            key = (dto_class, param["name"])
            entity_matches = dto_entity_index.get(key, [])
            
            for em in entity_matches:
                entry = {
                    "layer_1_vue": {
                        "page": caller["page"],
                        "field": field,
                    } for caller in match.frontend.callers
                    for field in caller.get("fields_used", []),
                    "layer_2_api": {
                        "url": match.frontend.url,
                        "function": match.frontend.function_name,
                        "param": param["name"],
                    },
                    "layer_3_controller": {
                        "class": match.backend.controller_class,
                        "method": match.backend.method_name,
                        "param": f"{dto_class}.{param['name']}",
                    },
                    "layer_4_dto": {
                        "class": dto_class,
                        "field": param["name"],
                    },
                    "layer_5_entity": {
                        "class": em["entity_class"],
                        "field": em["entity_field"],
                    },
                    "layer_6_db": {
                        "table": entity_map[em["entity_class"]]["table"],
                        "column": em["db_column"],
                    },
                }
                domain_tables[domain][entity_map[em["entity_class"]]["table"]].append(entry)
    
    return dict(domain_tables)
```

---

## 6. 输出示例

```json
{
  "domain": "Bin数据管理",
  "tables": {
    "bin_data_apply": {
      "columns": {
        "supplier_code": {
          "java_field": "supplierCode",
          "full_chain": [
            {
              "vue": {"page": "csstock/apply/index.vue", "field": "supplierCode"},
              "api": {"url": "POST /api/bindata/import", "function": "importBindata"},
              "controller": {"class": "BindataController", "param": "BindataApplyDTO.supplierCode"},
              "dto": {"class": "BindataApplyDTO", "field": "supplierCode"},
              "entity": {"class": "BindataApply", "field": "supplierCode"},
              "db": {"table": "bin_data_apply", "column": "supplier_code"}
            }
          ]
        }
      }
    }
  }
}
```

---

## 7. 增量更新

- Entity 文件变更 → 重新解析该文件，更新 entity_map 中对应条目
- DTO 文件变更 → 重新运行 match_dto_to_entity
- API 变更 → 依赖 api_mapper 先行更新，field_mapper 只追加映射
- 依赖 manifest.json 检测文件变更（与 cluster 共用增量机制）

---

## 8. 测试用例

```python
def test_extract_table_name_mybatis_plus():
    source = '@TableName("bin_data_apply")\npublic class BindataApply {'
    assert extract_table_name(source) == "bin_data_apply"

def test_camel_to_snake():
    assert camel_to_snake("supplierCode") == "supplier_code"
    assert camel_to_snake("id") == "id"

def test_field_match_exact():
    score, mtype = field_match_score("supplierCode", "String", "supplierCode", "String")
    assert score == 1.0
    assert mtype == "exact"
```
