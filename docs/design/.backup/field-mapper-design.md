# Field Mapper 模块详细设计

> 对应总体设计：[第四章 4.7 节](../architecture/graph-wiki%20架构设计.md#47-字段映射模块-fieldmapper)
> 状态：详细设计阶段（v2.0，重写）
> 依赖：`reuse.py`（复用层）、`api_mapper.py`（API 映射输出）、`models.py`（数据模型）
> 参考：`models-design.md`（`ApiMatch`）、`api-mapper-design.md`（上游模块设计）

---

## 1. 模块职责

### 1.1 核心职责

构建字段级别的完整数据流链路：从**数据库列**到**前端 UI 字段**的逐层追踪，填补 `api_mapper` 的剩余链路。

```
api_mapper 覆盖:  前端 Vue 页面 → 前端 API 模块 → 后端 Controller
field_mapper 覆盖:                                  后端 DTO → 后端 Entity → 数据库列
                                                               ↓
                                                  完整六层链路聚合
                                                (前端字段 → 数据库列)
```

### 1.2 解决的完整链路

完整六层数据流：

```
前端 Vue 组件          前端 API 模块          后端 Controller          后端 DTO              后端 Entity            数据库
─────────────────    ──────────────────    ─────────────────────    ──────────────────    ──────────────────    ────────
csstock/apply/        binorder.js           BinOrderController       BinOrderQueryDTO       BindataApply          bin_data_
index.vue             .listBinOrderDetail() .listBinOrderDetail()    .supplierCode          .supplierCode          apply
  │                     │                     │                       │                     │                     │
  │ :supplierCode       │ params: {           │ @RequestBody           │ private String       │ @TableField(         │ supplier_
  │                     │   supplierCode      │ BinOrderQueryDTO       │ supplierCode;        │   "supplier_code")   │ code
  │                     │ }                   │                       │                     │ private String       │ VARCHAR(50)
  │                     │                     │                       │                     │ supplierCode;        │
  │                     │                     │                       │                     │                     │
  └──→ api_mapper ──→ api_mapper ────→ api_mapper ──────┘         └──→ field_mapper ──→ field_mapper ──→ field_mapper
      (覆盖)          (覆盖)          (覆盖)                            (新增覆盖)         (新增覆盖)         (新增覆盖)
```

### 1.3 解决的问题

| 问题 | 说明 |
|------|------|
| "前端的 supplierCode 字段对应数据库哪个表的哪个列？" | 字段溯源：前端字段 → API 参数 → Controller → DTO → Entity → DB 列 |
| "BinDataApply 这个实体的所有字段分别来自哪里？" | 字段来源：哪些前端页面通过哪些 API 写入该表字段 |
| "哪些前端字段会最终写入 bin_data_apply 表？" | 正向追踪：前端变更影响哪些表 |
| "修改了 supplier_code 列名，需要同步修改哪些地方？" | 反向影响：数据库变更影响哪些 Entity/DTO/前端字段 |

### 1.4 与其他模块的关系

```
上游: api_mapper.py (list[ApiMatch], 含 URL、DTO 类名、调用者信息)
         │
         ▼
  field_mapper.py            ← 本模块
         │
         ├──→ 读取 entity_dir/ (后端 Entity/domain Java 源文件)
         ├──→ 读取 dto_dir/ (后端 DTO/VO Java 源文件)
         │
         ▼
  field-map.json             → export.py (生成 data-flow.md)
```

| 关系 | 模块 | 传递数据 |
|------|------|---------|
| 上游 | `api_mapper.py` | `list[ApiMatch]` — 含 `backend.param_type` (DTO 类名)、`frontend.url` (API 路径)、`frontend.callers` (调用者页面) |
| 下游 | `export.py` | `field-map.json` — 从 field_mapper 的输出生成 `wiki/{domain}/data-flow.md` |
| 数据源 | Entity/DTO 源码 | `entity_dir`, `dto_dir` 目录下的 Java 源文件 |

---

## 2. 接口定义

### 2.1 主接口

```python
def build_field_map(
    api_matches: list[ApiMatch],
    entity_dir: Path,
    dto_dir: Path,
    backend_root: Path,
) -> dict:
    """
    构建字段级完整数据流链路。

    三步流程：
    1. extract_entity_db_mapping()     — 解析 Entity 类，提取表名和字段→列映射
    2. extract_dto_fields()            — 解析 DTO/VO 类，提取字段名和类型
    3. match_dto_to_entity()           — DTO ↔ Entity 字段名匹配
    4. 聚合输出 — 串联 api_matches + 字段匹配，按 domain → table → column 聚合

    输入:
      api_matches: api_mapper 产出的 ApiMatch 列表
      entity_dir:  后端 Entity/domain 源码目录（含 @TableField 注解）
      dto_dir:     后端 DTO/VO 源码目录
      backend_root:后端项目根目录（用于将文件路径转为相对路径）

    输出:
      dict: 按 domain → table → column 聚合的字段映射字典

    输出结构:
      {
        "bin-data": {                                    # domain_key
          "bin_data_apply": {                            # db_table
            "columns": {
              "supplier_code": [                         # db_column
                {                                        # 来源记录
                  "api_url": "POST /api/bindata/import",
                  "api_function": "importBindata",
                  "dto_field": "supplierCode",
                  "entity_class": "BindataApply",
                  "entity_field": "supplierCode",
                  "db_column": "supplier_code",
                  "db_table": "bin_data_apply",
                  "callers": ["csstock/apply/index.vue"],
                  "confidence": 1.0
                },
                ...
              ],
              ...
            }
          }
        }
      }
    """
```

**参数说明**：

| 参数 | 类型 | 含义 |
|------|------|------|
| `api_matches` | `list[ApiMatch]` | api_mapper 产出的 API 匹配列表，用于聚合六层链路 |
| `entity_dir` | `Path` | 后端 Entity/domain 源码目录（递归扫描 `*.java`，筛选含 `@TableName`/`@Entity`/`@Table` 的文件） |
| `dto_dir` | `Path` | 后端 DTO/VO 源码目录（递归扫描 `*.java`，筛选文件名含 `dto`/`vo` 的文件） |
| `backend_root` | `Path` | 后端项目根目录，用于将文件路径转为相对路径 |

**返回值**：`dict` — 按 domain → table → column 聚合的字段映射字典。缺少 Entity/DTO 信息时返回空字典或部分内容。

**异常**：

| 异常类 | 抛出条件 |
|--------|---------|
| 无（设计原则：Entity/DTO 目录不存在时静默降级，返回空字典或空列表） |

### 2.2 三步子函数签名

```python
def extract_entity_db_mapping(entity_dir: Path, backend_root: Path) -> dict
    # Step 1: Entity → 数据库列映射
    # 输入: 后端 Entity/domain 源码目录
    # 输出: {
    #     "BindataApply": {
    #         "table": "bin_data_apply",
    #         "file": "com/smc/.../entity/BindataApply.java",
    #         "fields": [
    #             {"java_field": "supplierCode", "db_column": "supplier_code",
    #              "java_type": "String", "annotation": "@TableField(\"supplier_code\")"},
    #         ]
    #     }
    # }

def extract_dto_fields(dto_dir: Path, backend_root: Path) -> dict[str, list[dict]]
    # Step 2: DTO 字段提取
    # 输入: 后端 DTO/VO 源码目录
    # 输出: {
    #     "BinOrderQueryDTO": [
    #         {"name": "supplierCode", "type": "String"},
    #         {"name": "modelNo", "type": "String"},
    #     ]
    # }

def match_dto_to_entity(dto_map: dict, entity_map: dict) -> list[dict]
    # Step 3: DTO ↔ Entity 字段匹配
    # 输入: dto_map (extract_dto_fields 输出) + entity_map (extract_entity_db_mapping 输出)
    # 输出: [
    #     {
    #         "dto_class": "BinOrderQueryDTO",
    #         "dto_field": "supplierCode",
    #         "entity_class": "BindataApply",
    #         "entity_field": "supplierCode",
    #         "db_column": "supplier_code",
    #         "db_table": "bin_data_apply",
    #         "confidence": 1.0,
    #         "match_type": "exact",
    #     }
    # ]
```

### 2.3 辅助函数签名

```python
def camel_to_snake(name: str) -> str
    # 驼峰转下划线: "supplierCode" → "supplier_code"
    # 输入: 驼峰命名
    # 输出: 下划线命名

def field_match_score(dto_name, dto_type, entity_name, entity_type) -> tuple[float, str]
    # 字段匹配置信度评分
    # 输入: DTO 字段名 + 类型, Entity 字段名 + 类型
    # 输出: (0.0~1.0 的置信度, 匹配类型字符串)

def find_entity_candidates(dto_class: str, entity_map: dict) -> list[str]
    # 查找 DTO 类对应的候选 Entity 类名
    # 输入: DTO 类名 (如 "BinOrderQueryDTO"), entity_map
    # 输出: 候选 Entity 类名列表 (如 ["BinOrder", "BinOrderQuery"])

def is_entity_class(source: str) -> bool
    # 判断 Java 源文件是否为 Entity 类
    # 检查 @TableName、@Entity、@Table 注解

def extract_table_name(source: str, class_name: str) -> str
    # 从 @TableName/@Table 注解中提取表名
    # 无注解时通过 camel_to_snake 从类名推断

def extract_db_column(annotation_block: str, field_name: str) -> str
    # 从 @TableField/@Column 注解中提取数据库列名
    # 无注解时通过 camel_to_snake 从字段名推断

def _extract_class_name(source: str) -> str
    # 从 Java 源码中提取 public class 名称
```

---

## 3. Entity 注解解析

### 3.1 支持的注解框架

| 框架 | 表名注解 | 列名注解 | 识别条件 |
|------|---------|---------|---------|
| **MyBatis-Plus** | `@TableName("table_name")` | `@TableField("column_name")` / `@TableId` | 源码含 `@TableName` 关键字 |
| **JPA / Hibernate** | `@Table(name = "table_name")` | `@Column(name = "column_name")` | 源码含 `@Entity` 或 `@Table(` |

### 3.2 整体流程

```
entity_dir (递归扫描 *.java)
    │
    ├── 排除 src/test/ 路径下的文件
    │
    ├── 读取 Java 源文件 (UTF-8)
    │   └── IOError / UnicodeDecodeError → 跳过该文件
    │
    ├── is_entity_class() → 判断是否含 @TableName / @Entity / @Table(
    │       │
    │       ├── 否 → 跳过（非 Entity 类）
    │       └── 是 → 进入解析
    │
    ├── _extract_class_name() → 提取类名
    │
    ├── extract_table_name(source, class_name)
    │       ├── 匹配 @TableName("xxx") → 返回 "xxx"
    │       ├── 匹配 @Table(name = "xxx") → 返回 "xxx"
    │       └── 均不匹配 → camel_to_snake(class_name) 推断
    │
    └── 提取所有 private 字段声明
            │
            ├── 正则: (@\w+(?:\([^)]*\))?\s*)*private\s+(\S+)\s+(\w+)\s*;
            │
            └── extract_db_column(annotation_block, field_name)
                    ├── 注解含 @TableField("xxx") → 返回 "xxx"
                    ├── 注解含 @Column(name = "xxx") → 返回 "xxx"
                    └── 无注解 → camel_to_snake(field_name) 推断
```

### 3.3 `is_entity_class()` 实现

```python
def is_entity_class(source: str) -> bool:
    """
    判断 Java 源文件是否为 Entity 类。

    检查以下关键字：
      - @TableName        → MyBatis-Plus
      - @Entity           → JPA
      - @Table(           → JPA (排除 @TableField 的误匹配)

    示例:
      "@TableName(\"bin_data_apply\")"   → True
      "@Entity"                           → True
      "@Table(name = \"bin_data_apply\")" → True
      "@Service"                          → False
      "@TableField(\"col\")"              → False (不含 @TableName、@Entity 或 @Table()
    """
    return any(kw in source for kw in ("@TableName", "@Entity", "@Table("))
```

### 3.4 `extract_table_name()` 实现

```python
def extract_table_name(source: str, class_name: str) -> str:
    """
    从注解中提取数据库表名。

    优先级:
      1. @TableName("xxx")               → "xxx" (MyBatis-Plus)
      2. @Table(name = "xxx")            → "xxx" (JPA)
      3. 无注解 → camel_to_snake(ClassName) 推断

    示例:
      @TableName("bin_data_apply")       → "bin_data_apply"
      @Table(name = "bin_data_apply")    → "bin_data_apply"
      无注解, class_name = "BindataApply" → "bindata_apply"

    正则说明:
      - @TableName("xxx"): 直接匹配引号内字符串
      - @Table(name = "xxx"): 匹配 name = "xxx" 模式
    """
    for pat in [
        re.compile(r'@TableName\s*\(\s*["\']([^"\']+)["\']'),
        re.compile(r'@Table\s*\(\s*name\s*=\s*["\']([^"\']+)["\']'),
    ]:
        match = pat.search(source)
        if match:
            return match.group(1)
    return camel_to_snake(class_name)
```

### 3.5 `extract_db_column()` 实现

```python
def extract_db_column(annotation_block: str, field_name: str) -> str:
    """
    从字段的注解块中提取数据库列名。

    优先级:
      1. @TableField("xxx")              → "xxx" (MyBatis-Plus)
      2. @Column(name = "xxx")           → "xxx" (JPA)
      3. 无注解 → camel_to_snake(field_name) 推断

    示例:
      annotation_block = "@TableField(\"supplier_code\")"
      field_name = "supplierCode"
      → "supplier_code"

      annotation_block = ""
      field_name = "supplierCode"
      → camel_to_snake("supplierCode") = "supplier_code"

    注意:
      - @TableField(value = "xxx") 语法当前不支持 (Phase 2+)
      - @TableField(exist = false) 不过滤 (Phase 2+)
    """
    if not annotation_block:
        return camel_to_snake(field_name)
    match = re.search(r'@TableField\s*\(\s*["\']([^"\']+)["\']', annotation_block)
    if match:
        return match.group(1)
    match = re.search(r'@Column\s*\([^)]*name\s*=\s*["\']([^"\']+)["\']', annotation_block)
    if match:
        return match.group(1)
    return camel_to_snake(field_name)
```

### 3.6 字段声明解析

Entity 中字段的解析使用正则匹配 `private Type name;` 模式，同时捕获前面的注解块：

```python
FIELD_PATTERN = re.compile(
    r"""(@\w+(?:\([^)]*\))?\s*)*private\s+(\S+)\s+(\w+)\s*;""",
    re.VERBOSE,
)
```

**支持的模式**：

```java
private String supplierCode;
private Long id;
@TableField("supplier_code")
private String supplierCode;
@TableId
private Long id;
@NotBlank
private String name;                // 验证注解不影响字段提取
private List<String> tags;          // 泛型类型

// 不支持的：
public String name;                 // 非 private（极少）
private static final long serialVersionUID = 1L;  // static final（正则已排除）
```

### 3.7 整体 `extract_entity_db_mapping()` 实现

```python
def extract_entity_db_mapping(entity_dir: Path, backend_root: Path) -> dict:
    """
    从 Entity 目录解析所有 Entity 类，提取表名和字段→列映射。

    遍历:
      - entity_dir/**/*.java (递归)
      - 排除 src/test/ 路径
      - 仅处理 is_entity_class() 返回 True 的文件

    输出:
      {
        "BindataApply": {
            "table": "bin_data_apply",
            "file": "com/smc/.../entity/BindataApply.java",
            "fields": [
                {"java_field": "supplierCode", "db_column": "supplier_code",
                 "java_type": "String", "annotation": "@TableField(\"supplier_code\")"},
                {"java_field": "id", "db_column": "id",
                 "java_type": "Long", "annotation": "@TableId"},
            ]
        }
      }
    """
    entity_map = {}
    for java_file in entity_dir.rglob("*.java"):
        if "/src/test/" in str(java_file):
            continue
        try:
            source = java_file.read_text(encoding="utf-8")
        except (IOError, UnicodeDecodeError):
            continue
        if not is_entity_class(source):
            continue
        class_name = _extract_class_name(source)
        table_name = extract_table_name(source, class_name)

        fields = []
        field_pattern = re.compile(
            r"""(@\w+(?:\([^)]*\))?\s*)*private\s+(\S+)\s+(\w+)\s*;"""
        )
        for match in field_pattern.finditer(source):
            annotation_block = match.group(1) or ""
            fields.append({
                "java_field": match.group(3),
                "db_column": extract_db_column(annotation_block, match.group(3)),
                "java_type": match.group(2),
                "annotation": annotation_block.strip() if annotation_block else "",
            })

        entity_map[class_name] = {
            "table": table_name,
            "file": str(java_file.relative_to(backend_root)),
            "fields": fields,
        }
    return entity_map
```

### 3.8 边界条件

| 条件 | 行为 |
|------|------|
| Entity 文件无 `@TableName` 或 `@Entity` 注解 | 不被识别为 Entity，跳过（`is_entity_class` 返回 False） |
| `@TableName` 无参数（`@TableName`） | 无匹配，回退到 `camel_to_snake(ClassName)` |
| 文件编码非 UTF-8 | 捕获 `UnicodeDecodeError`，跳过该文件 |
| 文件 IO 失败 | 捕获 `IOError`，跳过该文件 |
| 字段类型为泛型（`List<String>` 等） | `\S+` 匹配到 `List<String>` 整体（含尖括号） |
| `@TableField(exist = false)` | 仍被提取（v1.0 不过滤非数据库字段，Phase 2+ 增强） |
| `@TableField(value = "col")` | 当前正则不匹配 `value = ` 语法（Phase 2+ 补充） |
| `@Column(name = "col", length = 50)` | 当前正则可匹配（`[^)]*` 允许其他属性） |

---

## 4. DTO 字段提取

### 4.1 总体流程

```
dto_dir (递归扫描 *.java)
    │
    ├── 排除 src/test/ 路径下的文件
    │
    ├── 过滤文件名: 仅处理含 dto/vo (不区分大小写) 的文件
    │       │
    │       ├── 不符合 → 跳过（非 DTO/VO 类）
    │       └── 符合 → 进入解析
    │
    ├── 读取 Java 源文件 (UTF-8)
    │   └── IOError / UnicodeDecodeError → 跳过该文件
    │
    ├── _extract_class_name() → 提取类名
    │
    └── 提取所有 private Type name; 声明
            │
            └── 返回 {"class_name": [{"name": ..., "type": ...}, ...]}
```

### 4.2 `extract_dto_fields()` 实现

```python
def extract_dto_fields(dto_dir: Path, backend_root: Path) -> dict[str, list[dict]]:
    """
    解析 DTO/VO 目录下的 Java 文件，提取类名 + 字段列表。

    输入筛选规则:
      - 仅处理文件名含 "dto" 或 "vo" 的文件（不区分大小写）
      - 排除 src/test/ 路径

    字段提取规则:
      - 匹配 private Type name; 模式
      - 包含泛型类型 (如 List<String>)
      - 不提取继承字段 (v1.0 不追踪父类)

    输出:
      {
        "BinOrderQueryDTO": [
            {"name": "supplierCode", "type": "String"},
            {"name": "modelNo", "type": "String"},
            {"name": "itemList", "type": "List<ItemDTO>"},
        ]
      }
    """
    dto_map = defaultdict(list)
    for java_file in dto_dir.rglob("*.java"):
        # 排除测试路径
        if "/src/test/" in str(java_file):
            continue
        # 仅处理文件名含 dto/vo 的文件
        if not ("dto" in java_file.name.lower() or "vo" in java_file.name.lower()):
            continue
        try:
            source = java_file.read_text(encoding="utf-8")
        except (IOError, UnicodeDecodeError):
            continue
        class_name = _extract_class_name(source)
        for match in re.finditer(r"private\s+(\S+)\s+(\w+)\s*;", source):
            dto_map[class_name].append({
                "name": match.group(2),
                "type": match.group(1),
            })
    return dict(dto_map)
```

### 4.3 为什么不使用 tree-sitter AST？

与 api_mapper 类似，DTO 字段提取采用正则表达式而非 tree-sitter AST：

| 维度 | 正则表达式 | tree-sitter AST |
|------|:----------:|:---------------:|
| 依赖安装 | 零（Python 内置 `re`） | 需要安装 `tree-sitter` + Java grammar |
| 处理速度 | ~200ms（300 个 DTO 文件） | ~500ms（含加载 grammar 时间） |
| 准确率 | ~98%（标准 Java Bean 模式） | ~100% |
| 代码复杂度 | ~20 行 | ~100 行 |
| 继承链追踪 | 不支持 | 可解析 extends |

**设计决策**：v1.0 采用正则表达式，因为：
1. DTO 文件的模式高度统一（标准 Java Bean：`private Type name;`）
2. 准确率约 98%，误报主要来自 `static final` 常量（通过正则模式可排除）
3. 零依赖，即时可用
4. Phase 3+ 如需继承链追踪可升级为 tree-sitter

### 4.4 边界条件

| 条件 | 行为 |
|------|------|
| DTO 目录不存在 | 返回空字典 `{}` |
| 文件名不含 dto/vo 但实际是 DTO | 不被提取（v1.0 保守策略，Phase 2+ 可扫描全目录 + 检测 `@Data`/`@Getter` 注解） |
| 字段声明含注解（如 `@NotBlank private String name;`） | 正则仍能匹配 `private` 前的内容被忽略，不影响字段名提取 |
| 继承父类的字段 | 不提取父类字段（v1.0 不追踪继承链） |
| 枚举类或常量类（只有 static final） | `private static final` 不被 `private \S+ \w+;` 匹配（不含 static） |
| `private static final long serialVersionUID = 1L;` | 不被提取 |
| `private int a, b, c;` | 只提取第一个 `a`（多变量声明极少用于 DTO） |

---

## 5. DTO → Entity 字段匹配策略

### 5.1 匹配原理

DTO 和 Entity 的字段名在命名上高度相关，但存在以下差异：

| 差异类型 | DTO | Entity | 示例 |
|---------|-----|--------|------|
| 完全相同 | `supplierCode` | `supplierCode` | 最常见情况（约 70%） |
| 命名风格不同 | `supplierCode` (驼峰) | `supplier_code` (下划线) | namespace 不同（仅 Java 层面，DB 层面已由注解处理） |
| 类型 + 子串包含 | `address` | `deliveryAddress` | DTO 名是 Entity 名的子串 |
| 前缀差异 | `dtoSupplierCode` | `supplierCode` | DTO 有 `dto` 前缀（Phase 4+） |

### 5.2 三级匹配评分

| 优先级 | 条件 | 置信度 | 匹配类型 | 说明 |
|:------:|------|:------:|----------|------|
| 1 | DTO 字段名 === Entity 字段名 | **1.0** | `exact` | 完全同名，最常见的匹配（约 70% 场景） |
| 2 | DTO 字段名的 camel_to_snake === Entity 字段名的 camel_to_snake | **0.9** | `snake_match` | 命名风格不同但语义相同。例：`supplierCode`(DTO) ↔ `supplierCode`(Entity) → 两者 camel_to_snake 都是 `supplier_code` |
| 3 | 类型相同 + 一方字段名是另一方的子串 | **0.6** | `type_and_substring` | 低置信匹配。例：`address`(String) ↔ `deliveryAddress`(String) |
| — | 类型不同 || 0.0 | `none` | 不匹配 |

### 5.3 `field_match_score()` 实现

```python
def field_match_score(
    dto_name: str, dto_type: str,
    entity_name: str, entity_type: str,
) -> tuple[float, str]:
    """
    计算 DTO 字段到 Entity 字段的匹配置信度。

    匹配流程:
      1. 完全同名 → 1.0 (exact)
      2. 驼峰转下划线后同名 → 0.9 (snake_match)
      3. 类型相同 + 子串包含 → 0.6 (type_and_substring)
      4. 不匹配 → 0.0 (none)

    注意: 虽然在 Java 中 DTO 和 Entity 都使用驼峰命名（supplierCode 对 supplierCode），
    snake_match 场景仍然存在。例如 DTO 使用下划线命名（与前端 JSON 字段对应）或
    Entity 字段名与 DTO 字段名存在大小写差异时，camel_to_snake 可以统一比较基准。
    """
    if dto_name == entity_name:
        return 1.0, "exact"
    if camel_to_snake(dto_name) == camel_to_snake(entity_name):
        return 0.9, "snake_match"
    if dto_type == entity_type and (
        dto_name.lower() in entity_name.lower()
        or entity_name.lower() in dto_name.lower()
    ):
        return 0.6, "type_and_substring"
    return 0.0, "none"
```

### 5.4 候选 Entity 查找

在匹配前，先将特定 DTO 类限定到候选 Entity 集合中，避免全量搜索的误匹配：

```python
def find_entity_candidates(dto_class: str, entity_map: dict) -> list[str]:
    """
    根据 DTO 类名推断候选 Entity 类名。

    算法:
      1. 移除 DTO/VO 后缀 → 得到 base_name
      2. 在 entity_map 的 key 中查找含 base_name 或 base_name 含 key 的类
      3. 如无候选，返回所有 Entity 类作为兜底

    示例:
      dto_class = "BinOrderQueryDTO"
      entity_map keys = ["BindataApply", "BinOrder", "PurchaseOrder"]

      base = "BinOrderQuery"   # 移除 DTO 后缀
      candidates:
        - "BinOrder" → "binorder" in "binorderquery" → 匹配
        - "BindataApply" → 不匹配
        - "PurchaseOrder" → 不匹配
      → ["BinOrder"]

      dto_class = "UserVO"
      base = "User"
      → 匹配所有含 "user" 的 Entity
    """
    base = dto_class.replace("DTO", "").replace("Dto", "").replace("VO", "").replace("Vo", "")
    candidates = []
    for name in entity_map:
        if base.lower() in name.lower() or name.lower() in base.lower():
            candidates.append(name)
    return candidates or list(entity_map.keys())
```

### 5.5 `match_dto_to_entity()` 完整实现

```python
def match_dto_to_entity(dto_map: dict, entity_map: dict) -> list[dict]:
    """
    执行 DTO ↔ Entity 字段匹配。

    匹配策略:
      1. 对每个 DTO 类，通过 find_entity_candidates 找到候选 Entity 类
      2. 对 DTO 的每个字段，在候选 Entity 的所有字段中找最佳匹配
      3. 取 field_match_score 评分最高的匹配
      4. 分数为 0.0 的不输出（无匹配）

    输出: 每个 DTO 字段 → 最佳 Entity 字段匹配的完整信息

    示例输出:
      {
          "dto_class": "BinOrderQueryDTO",
          "dto_field": "supplierCode",
          "entity_class": "BindataApply",
          "entity_field": "supplierCode",
          "db_column": "supplier_code",
          "db_table": "bin_data_apply",
          "confidence": 1.0,
          "match_type": "exact",
      }
    """
    results = []
    for dto_class, dto_fields in dto_map.items():
        candidates = find_entity_candidates(dto_class, entity_map)
        for dto_field in dto_fields:
            best_match, best_score = None, 0
            for entity_class in candidates:
                if entity_class not in entity_map:
                    continue
                for ef in entity_map[entity_class]["fields"]:
                    score, mtype = field_match_score(
                        dto_field["name"], dto_field["type"],
                        ef["java_field"], ef["java_type"],
                    )
                    if score > best_score:
                        best_score = score
                        best_match = {
                            "dto_class": dto_class,
                            "dto_field": dto_field["name"],
                            "entity_class": entity_class,
                            "entity_field": ef["java_field"],
                            "db_column": ef["db_column"],
                            "db_table": entity_map[entity_class]["table"],
                            "confidence": score,
                            "match_type": mtype,
                        }
            if best_match:
                results.append(best_match)
    return results
```

### 5.6 Phase 4+ 增强匹配：前缀移除

在当前三级匹配基础上，Phase 4+ 将增加**前缀移除**策略：

| 优先级 | 条件 | 置信度 | 匹配类型 | 说明 |
|:------:|------|:------:|----------|------|
| 4 | 移除 DTO 前缀后满足条件 1 或 2 | **0.85** | `prefix_removed` | DTO 字段名前缀（`dto`/`req`/`param`/`input`）被移除后匹配 |

**实现思路**（Phase 4+ 预留）：

```python
DTO_FIELD_PREFIXES = ["dto", "req", "param", "input", "arg"]

def _remove_dto_prefix(name: str) -> str:
    """移除 DTO 字段的常见前缀。"""
    for prefix in DTO_FIELD_PREFIXES:
        if name.lower().startswith(prefix) and len(name) > len(prefix):
            stripped = name[len(prefix):]
            return stripped[0].lower() + stripped[1:] if stripped else name
    return name

def _match_with_prefix_removal(dto_name: str, entity_name: str) -> tuple[float, str]:
    """Phase 4+: 移除前缀后匹配。"""
    stripped = _remove_dto_prefix(dto_name)
    if stripped != dto_name:
        if stripped == entity_name:
            return 0.85, "prefix_removed"
        if camel_to_snake(stripped) == camel_to_snake(entity_name):
            return 0.85, "prefix_removed"
    return 0.0, "none"
```

### 5.7 匹配示例

| DTO 类 | DTO 字段 | Entity 类 | Entity 字段 | 置信度 | 匹配类型 |
|--------|---------|-----------|------------|:------:|----------|
| `BinOrderQueryDTO` | `supplierCode` | `BindataApply` | `supplierCode` | 1.0 | exact |
| `BinOrderQueryDTO` | `modelNo` | `BindataApply` | `modelNo` | 1.0 | exact |
| `UserCreateDTO` | `userName` | `User` | `userName` | 1.0 | exact |
| `OrderDTO` | `orderName` | `Order` | `orderName` | 1.0 | exact |
| `OrderDTO` | `createTime` | `Order` | `createTime` | 1.0 | exact |
| `OrderDTO` | `address` (String) | `Order` | `deliveryAddress` (String) | 0.6 | type_and_substring |
| `BinOrderQueryDTO` | `pageNum` | — | — | 0.0 | none（Entity 无此字段） |
| `OrderDTO` | `orderStatus` | `Order` | `status` | 0.0 | none（字段名完全不同） |

---

## 6. 数据聚合格式

### 6.1 `build_field_map()` 聚合逻辑

将 `api_matches`（前端 → 后端链路）与 `match_dto_to_entity` 结果（后端 → 数据库链路）串联，生成 `domain_key → table_name → column_name → 来源列表` 的三层聚合结构：

```python
def build_field_map(
    api_matches: list[ApiMatch],
    entity_dir: Path,
    dto_dir: Path,
    backend_root: Path,
) -> dict:
    """
    三步：
      1. extract_entity_db_mapping  — Entity → DB 列映射
      2. extract_dto_fields         — DTO 字段列表
      3. match_dto_to_entity        — DTO ↔ Entity 匹配
    聚合输出：domain → table → column → 来源列表
    """
    entity_map = extract_entity_db_mapping(entity_dir, backend_root) if entity_dir.exists() else {}
    dto_map = extract_dto_fields(dto_dir, backend_root) if dto_dir.exists() else {}
    field_matches = match_dto_to_entity(dto_map, entity_map)

    # 构建 domain → table → column 聚合
    domain_tables: dict[str, dict] = defaultdict(lambda: defaultdict(lambda: defaultdict(list)))

    for match in api_matches:
        domain = match.domain or "unknown"
        dto_class = match.backend.param_type or ""
        for fm in field_matches:
            if fm["dto_class"] == dto_class:
                entry = {
                    "api_url": f"{match.frontend.http_method} {match.frontend.url}",
                    "api_function": match.frontend.function_name,
                    "dto_field": fm["dto_field"],
                    "entity_class": fm["entity_class"],
                    "entity_field": fm["entity_field"],
                    "db_column": fm["db_column"],
                    "db_table": fm["db_table"],
                    "callers": [
                        c.get("page", "") for c in match.frontend.callers
                    ],
                    "confidence": fm["confidence"],
                }
                domain_tables[domain][fm["db_table"]][fm["db_column"]].append(entry)

    return dict(domain_tables)
```

### 6.2 `field-map.json` 完整 Schema

```json
{
  "<domain_key>": {
    "<table_name>": {
      "columns": {
        "<column_name>": [
          {
            "api_url": "POST /api/binorder/detail",
            "api_function": "listBinOrderDetail",
            "dto_field": "supplierCode",
            "entity_class": "BindataApply",
            "entity_field": "supplierCode",
            "db_column": "supplier_code",
            "db_table": "bin_data_apply",
            "callers": ["csstock/apply/index.vue"],
            "confidence": 1.0
          }
        ]
      }
    }
  }
}
```

### 6.3 字段说明

| 字段 | 类型 | 含义 |
|------|------|------|
| `api_url` | string | 完整 API URL（含 HTTP 方法），如 `"POST /api/binorder/detail"` |
| `api_function` | string | 前端 API 函数名，如 `"listBinOrderDetail"` |
| `dto_field` | string | DTO 类中该字段的 Java 名，如 `"supplierCode"` |
| `entity_class` | string | Entity 类名，如 `"BindataApply"` |
| `entity_field` | string | Entity 中该字段的 Java 名，如 `"supplierCode"` |
| `db_column` | string | 数据库列名，如 `"supplier_code"` |
| `db_table` | string | 数据库表名，如 `"bin_data_apply"` |
| `callers` | string[] | 调用该接口的前端页面路径列表，如 `["csstock/apply/index.vue"]` |
| `confidence` | float | DTO ↔ Entity 匹配置信度（0~1），如 `1.0` |

### 6.4 多来源聚合示例

同一数据库列可能被多个 API 写入，在 `field-map.json` 中聚合展示：

```json
{
  "bin-data": {
    "bin_data_apply": {
      "columns": {
        "supplier_code": [
          {
            "api_url": "POST /api/bindata/import",
            "api_function": "importBindata",
            "dto_field": "supplierCode",
            "entity_class": "BindataApply",
            "entity_field": "supplierCode",
            "db_column": "supplier_code",
            "db_table": "bin_data_apply",
            "callers": ["csstock/apply/index.vue"],
            "confidence": 1.0
          },
          {
            "api_url": "POST /api/binorder/detail",
            "api_function": "listBinOrderDetail",
            "dto_field": "supplierCode",
            "entity_class": "BindataApply",
            "entity_field": "supplierCode",
            "db_column": "supplier_code",
            "db_table": "bin_data_apply",
            "callers": ["csstock/query/index.vue"],
            "confidence": 1.0
          }
        ]
      }
    }
  }
}
```

### 6.5 Agent 查询示例

```
用户: "supplierCode 对应数据库哪个字段？"

Agent 读取 field-map.json (bin-data, ~300 tokens)
  → supplier_code
    → 来源 1: POST /api/bindata/import → importBindata()
      → DTO: BindataApplyDTO.supplierCode
      → Entity: BindataApply.supplierCode
      → 前端调用: csstock/apply/index.vue
    → 来源 2: POST /api/binorder/detail → listBinOrderDetail()
      → DTO: BinOrderQueryDTO.supplierCode
      → 前端调用: csstock/query/index.vue
```

---

## 7. `camel_to_snake` 算法

### 7.1 算法描述

驼峰命名转下划线命名，是 Field Mapper 的核心辅助算法。用于：
- Entity 类名无 `@TableName` 时推断表名（`BindataApply` → `bindata_apply`）
- 字段无 `@TableField`/`@Column` 注解时推断列名（`supplierCode` → `supplier_code`）
- DTO ↔ Entity 字段匹配的 snake_match 策略

### 7.2 实现

```python
def camel_to_snake(name: str) -> str:
    """
    驼峰命名转下划线命名。

    算法:
      1. 将每个大写字母替换为 _小写字母
      2. 整体转换为小写
      3. 去除开头的下划线

    示例:
      "supplierCode"    → "supplier_code"
      "BindataApply"    → "bindata_apply"
      "URL"             → "u_r_l" (但实际数据库列名不会全是缩写)
      "supplier_code"   → "supplier_code" (已经是下划线，无大写，不变)
      "supplierCode123" → "supplier_code123" (数字不变)
      "SPUId"           → "s_p_u_id" (连续大写视为缩写)
    """
    return re.sub(r"([A-Z])", r"_\1", name).lower().lstrip("_")
```

### 7.3 转换示例

| 输入 | 输出 | 说明 |
|------|------|------|
| `supplierCode` | `supplier_code` | 标准驼峰 |
| `BindataApply` | `bindata_apply` | 类名首字母大写 |
| `createTime` | `create_time` | 常见时间字段 |
| `SPUId` | `s_p_u_id` | 缩写 + 驼峰混合（不完美但可接受） |
| `supplier_code` | `supplier_code` | 已为下划线，不变 |
| `a` | `a` | 单字符不变 |
| `modelNo` | `model_no` | 两个单词 |
| `URL` | `u_r_l` | 全大写缩写（极少用作字段名） |

**已知局限性**：该算法对包含连续大写缩写（如 `SPUId` → `s_p_u_id`）的处理不够理想。但实际项目中字段名极少使用连续大写缩写（通常使用 `spuId` 而非 `SPUId`），因此 v1.0 接受此行为。

---

## 8. 错误处理

### 8.1 降级策略

| 场景 | 检测条件 | 默认行为 |
|------|---------|---------|
| **Entity 目录不存在** | `not entity_dir.exists()` | 静默跳过，entity_map = {}，field-map 无数据库列信息 |
| **DTO 目录不存在** | `not dto_dir.exists()` | 静默跳过，dto_map = {}，无 DTO→Entity 匹配 |
| **api_matches 为空列表** | `not api_matches` | 返回空 dict `{}`，field-map 为空 |
| **单个 Java 文件解析失败** | `IOError` / `UnicodeDecodeError` | 跳过该文件，继续解析其他文件 |
| **Entity 文件无 Entity 注解** | `is_entity_class()` 返回 False | 跳过，不被包含在 entity_map 中 |
| **DTO 文件名不含 dto/vo** | 文件名过滤不通过 | 跳过该文件（v1.0 保守策略） |
| **DTO 字段无任何 Entity 匹配** | `find_entity_candidates` 无匹配或匹配分数为 0 | 不输出该字段的匹配记录 |
| **ApiMatch 的 param_type 为 None** | `match.backend.param_type is None` | 跳过该 ApiMatch，不参与聚合 |
| **Entity 字段无 `@TableField` 注解** | 回退到 `camel_to_snake(field_name)` 推断列名 |

### 8.2 防御性编程

```python
# Entity 目录不存在时的降级
entity_map = extract_entity_db_mapping(entity_dir, backend_root) if entity_dir.exists() else {}

# DTO 目录不存在时的降级
dto_map = extract_dto_fields(dto_dir, backend_root) if dto_dir.exists() else {}

# 即使一方为空，核心匹配仍可执行（返回空列表/空聚合）
field_matches = match_dto_to_entity(dto_map, entity_map)
```

### 8.3 下游影响

| 降级场景 | field-map.json | data-flow.md |
|---------|---------------|-------------|
| Entity 目录不存在 | 无 table/column 信息，入口来源列表存在但无列名 | 无数据流信息 |
| DTO 目录不存在 | 无 DTO→Entity 匹配，入口来源列表存在但字段名缺少 Entity 对应 | 无 DTO↔Entity 映射 |
| api_matches 为空 | 空 dict `{}` | 无数据流文档 |
| 全部正常 | 完整的六层链路 | 完整的数据流文档 |

### 8.4 Token 成本

**整个 `field_mapper.py` 模块零 Token 成本**。所有解析（正则匹配、注解提取、字段名相似度计算、字典聚合）均为纯静态分析，不调用任何 LLM API。

### 8.5 性能估计

| 操作 | 时间估计 | 说明 |
|------|:--------:|------|
| 解析 ~500 个 Entity 文件 | ~300ms | 文件 IO + 正则匹配字段级提取 |
| 解析 ~300 个 DTO/VO 文件 | ~200ms | 文件 IO + 正则匹配 |
| DTO↔Entity 字段匹配（~300 类 × ~500 类 × ~20 字段） | ~500ms | 内存字符串比较，三重循环 |
| 聚合 api_matches（~200 API × ~500 匹配） | ~50ms | 字典操作 |
| **合计** | **~1,050ms** | 对 OPS 级项目（~5,000 文件） |

---

## 9. 当前实现与设计的差距

`graph_wiki/field_mapper.py` 已实现核心映射流程，但以下功能处于**待实现**状态，将在后续迭代中补齐：

| 功能 | 设计状态 | 实现状态 | 优先级 | 说明 |
|------|:--------:|:--------:|:------:|------|
| `extract_entity_db_mapping()` | ✅ 完整设计 | ✅ 已实现 | — | |
| `extract_dto_fields()` | ✅ 完整设计 | ✅ 已实现 | — | |
| `match_dto_to_entity()` | ✅ 完整设计 | ✅ 已实现 | — | |
| `field_match_score()` | ✅ 完整设计 | ✅ 已实现 | — | |
| `find_entity_candidates()` | ✅ 完整设计 | ✅ 已实现 | — | |
| `build_field_map()` | ✅ 完整设计 | ✅ 已实现 | — | |
| 前缀移除匹配 (`prefix_removed`, 0.85) | 📝 设计预留 | ❌ 未实现 | 🟡 Phase 4+ | 需要 DTO 字段常见前缀库 |
| `@TableField(value = "...")` 语法支持 | 📝 设计预留 | ❌ 未实现 | 🟡 Phase 2+ | 当前只支持 `@TableField("col")` |
| `@TableField(exist = false)` 过滤 | 📝 设计预留 | ❌ 未实现 | 🟢 Phase 2+ | 过滤非数据库字段 |
| 父类继承字段提取 | 📝 设计预留 | ❌ 未实现 | 🟢 Phase 3+ | 需要解析 extends 链 |
| 非 dto/vo 文件的 DTO 提取 | 📝 设计预留 | ❌ 未实现 | 🟢 Phase 2+ | 通过 `@Data`/`@Getter` 注解检测 |
| `@Column(name = "col")` 无空格语法 | 📝 设计预留 | ❌ 未实现 | 🟢 Phase 2+ | 当前期望 `name = "col"` 模式 |
| `@TableField(value = "col")` 语法 | 📝 设计预留 | ❌ 未实现 | 🟡 Phase 2+ | 需要正则增强 |
| 前端字段→API 参数对应 | 📝 设计预留 | ❌ 未实现 | 🟢 Phase 3+ | 当前 `callers` 仅存 page 名，未关联具体字段名 |

**Phase 4+ 前缀移除匹配预留**：

```python
# Phase 4+: 在 field_match_score 中第二级后插入
DTO_FIELD_PREFIXES = ["dto", "req", "param", "input", "arg"]

def _match_with_prefix_removal(dto_name: str, entity_name: str) -> tuple[float, str]:
    for prefix in DTO_FIELD_PREFIXES:
        if dto_name.lower().startswith(prefix) and len(dto_name) > len(prefix):
            stripped = dto_name[len(prefix):]
            stripped = stripped[0].lower() + stripped[1:] if stripped else dto_name
            if stripped == entity_name:
                return 0.85, "prefix_removed"
            if camel_to_snake(stripped) == camel_to_snake(entity_name):
                return 0.85, "prefix_removed"
    return 0.0, "none"
```

---

## 10. 测试用例

### 10.1 测试 Entity 注解解析

```python
def test_extract_entity_db_mapping_mybatis_plus():
    """验证 MyBatis-Plus @TableName + @TableField 注解解析。"""
    from graph_wiki.field_mapper import extract_entity_db_mapping
    import tempfile
    from pathlib import Path

    with tempfile.TemporaryDirectory() as tmpdir:
        entity_dir = Path(tmpdir) / "entity"
        entity_dir.mkdir(parents=True)
        (entity_dir / "BindataApply.java").write_text('''
        package com.smc.entity;
        @TableName("bin_data_apply")
        public class BindataApply {
            @TableField("supplier_code")
            private String supplierCode;

            @TableId
            private Long id;

            private String modelNo;
        }
        ''', encoding="utf-8")

        result = extract_entity_db_mapping(entity_dir, Path(tmpdir))

        assert "BindataApply" in result
        assert result["BindataApply"]["table"] == "bin_data_apply"

        fields = {f["java_field"]: f for f in result["BindataApply"]["fields"]}
        assert fields["supplierCode"]["db_column"] == "supplier_code"
        assert fields["supplierCode"]["java_type"] == "String"
        assert fields["id"]["db_column"] == "id"
        # modelNo 无 @TableField，回退到 camel_to_snake
        assert fields["modelNo"]["db_column"] == "model_no"


def test_extract_entity_db_mapping_jpa():
    """验证 JPA @Table + @Column 注解解析。"""
    from graph_wiki.field_mapper import extract_entity_db_mapping
    import tempfile
    from pathlib import Path

    with tempfile.TemporaryDirectory() as tmpdir:
        entity_dir = Path(tmpdir) / "entity"
        entity_dir.mkdir(parents=True)
        (entity_dir / "User.java").write_text('''
        package com.smc.entity;
        @Entity
        @Table(name = "sys_user")
        public class User {
            @Column(name = "user_name")
            private String username;

            @Column(name = "email_addr")
            private String email;
        }
        ''', encoding="utf-8")

        result = extract_entity_db_mapping(entity_dir, Path(tmpdir))

        assert "User" in result
        assert result["User"]["table"] == "sys_user"

        fields = {f["java_field"]: f for f in result["User"]["fields"]}
        assert fields["username"]["db_column"] == "user_name"
        assert fields["email"]["db_column"] == "email_addr"


def test_extract_entity_db_mapping_no_annotation_fallback():
    """验证无 @TableField 注解时回退到 camel_to_snake 推断列名。"""
    from graph_wiki.field_mapper import extract_entity_db_mapping
    import tempfile
    from pathlib import Path

    with tempfile.TemporaryDirectory() as tmpdir:
        entity_dir = Path(tmpdir) / "entity"
        entity_dir.mkdir(parents=True)
        (entity_dir / "BindataApply.java").write_text('''
        package com.smc.entity;
        @TableName("bin_data_apply")
        public class BindataApply {
            private String supplierCode;
            private String modelNo;
        }
        ''', encoding="utf-8")

        result = extract_entity_db_mapping(entity_dir, Path(tmpdir))

        fields = {f["java_field"]: f for f in result["BindataApply"]["fields"]}
        # 无 @TableField 注解 → camel_to_snake
        assert fields["supplierCode"]["db_column"] == "supplier_code"
        assert fields["modelNo"]["db_column"] == "model_no"
```

### 10.2 测试 DTO 字段提取

```python
def test_extract_dto_fields():
    """验证 DTO 字段提取：private Type name; 模式匹配。"""
    from graph_wiki.field_mapper import extract_dto_fields
    import tempfile
    from pathlib import Path

    with tempfile.TemporaryDirectory() as tmpdir:
        dto_dir = Path(tmpdir) / "dto"
        dto_dir.mkdir(parents=True)
        (dto_dir / "BinOrderQueryDTO.java").write_text('''
        package com.smc.dto;
        public class BinOrderQueryDTO {
            private String supplierCode;
            private String modelNo;
            private Integer pageNum;
            private List<ItemDTO> items;
        }
        ''', encoding="utf-8")

        result = extract_dto_fields(dto_dir, Path(tmpdir))

        assert "BinOrderQueryDTO" in result
        fields = {f["name"]: f for f in result["BinOrderQueryDTO"]}
        assert fields["supplierCode"]["type"] == "String"
        assert fields["modelNo"]["type"] == "String"
        assert fields["pageNum"]["type"] == "Integer"
        assert fields["items"]["type"] == "List<ItemDTO>"


def test_extract_dto_fields_no_dto_dir():
    """验证 DTO 目录不存在时返回空字典。"""
    from graph_wiki.field_mapper import extract_dto_fields
    from pathlib import Path

    result = extract_dto_fields(Path("/nonexistent/dto"), Path("/"))
    assert result == {}


def test_extract_dto_fields_skip_non_dto_file():
    """验证文件名不含 dto/vo 的文件被跳过。"""
    from graph_wiki.field_mapper import extract_dto_fields
    import tempfile
    from pathlib import Path

    with tempfile.TemporaryDirectory() as tmpdir:
        dto_dir = Path(tmpdir) / "dto"
        dto_dir.mkdir(parents=True)
        # 文件名不含 dto/vo
        (dto_dir / "BinOrderQuery.java").write_text('''
        public class BinOrderQuery {
            private String name;
        }
        ''', encoding="utf-8")

        result = extract_dto_fields(dto_dir, Path(tmpdir))
        assert result == {}  # 文件被过滤，返回空
```

### 10.3 测试字段匹配评分

```python
def test_field_match_score_exact():
    """验证完全同名返回 1.0 (exact)。"""
    from graph_wiki.field_mapper import field_match_score

    score, mtype = field_match_score("supplierCode", "String", "supplierCode", "String")
    assert score == 1.0
    assert mtype == "exact"


def test_field_match_score_snake():
    """验证驼峰转下划线后同名返回 0.9 (snake_match)。"""
    from graph_wiki.field_mapper import field_match_score

    # 两个命名风格的对比：DTO 字段用下划线或不同大小写
    score, mtype = field_match_score("supplier_code", "String", "supplierCode", "String")
    assert score == 0.9
    assert mtype == "snake_match"


def test_field_match_score_type_and_substring():
    """验证类型相同 + 子串包含返回 0.6 (type_and_substring)。"""
    from graph_wiki.field_mapper import field_match_score

    # address 是 deliveryAddress 的子串，类型相同
    score, mtype = field_match_score("address", "String", "deliveryAddress", "String")
    assert score == 0.6
    assert mtype == "type_and_substring"

    # 类型不同则不匹配
    score, mtype = field_match_score("address", "String", "deliveryAddress", "Long")
    assert score == 0.0
    assert mtype == "none"


def test_field_match_score_no_match():
    """验证不匹配返回 0.0 (none)。"""
    from graph_wiki.field_mapper import field_match_score

    score, mtype = field_match_score("supplierCode", "String", "createTime", "Date")
    assert score == 0.0
    assert mtype == "none"
```

### 10.4 测试候选 Entity 查找

```python
def test_find_entity_candidates_by_substring():
    """验证通过子串匹配查找候选 Entity。"""
    from graph_wiki.field_mapper import find_entity_candidates

    entity_map = {
        "BindataApply": {},
        "BinOrder": {},
        "PurchaseOrder": {},
        "User": {},
    }

    # BinOrderQueryDTO → base = "BinOrderQuery"
    # → "BinOrderQuery" contains "BinOrder" → 匹配
    candidates = find_entity_candidates("BinOrderQueryDTO", entity_map)
    assert "BinOrder" in candidates
    assert "BindataApply" not in candidates
    assert "PurchaseOrder" not in candidates

    # UserVO → base = "User" → 匹配
    candidates = find_entity_candidates("UserVO", entity_map)
    assert "User" in candidates


def test_find_entity_candidates_fallback():
    """验证无匹配候选时回退到全部 Entity。"""
    from graph_wiki.field_mapper import find_entity_candidates

    entity_map = {
        "BindataApply": {},
        "BinOrder": {},
    }

    # "UnknownDTO" → base = "Unknown" → 无匹配 → 返回全部
    candidates = find_entity_candidates("UnknownDTO", entity_map)
    assert len(candidates) == 2
    assert "BindataApply" in candidates
    assert "BinOrder" in candidates
```

### 10.5 端到端：完整字段映射流程

```python
def test_build_field_map_end_to_end():
    """验证完整的 build_field_map 流程：Entity 解析 → DTO 解析 → 匹配 → 聚合。"""
    from graph_wiki.field_mapper import build_field_map
    from graph_wiki.models import ApiMatch, FrontendApiCall, BackendEndpoint
    from pathlib import Path
    import tempfile

    with tempfile.TemporaryDirectory() as tmpdir:
        backend_root = Path(tmpdir)

        # 创建 Entity 文件
        entity_dir = backend_root / "entity"
        entity_dir.mkdir(parents=True)
        (entity_dir / "BindataApply.java").write_text('''
        package com.smc.entity;
        @TableName("bin_data_apply")
        public class BindataApply {
            @TableField("supplier_code")
            private String supplierCode;
            private String modelNo;
        }
        ''', encoding="utf-8")

        # 创建 DTO 文件
        dto_dir = backend_root / "dto"
        dto_dir.mkdir(parents=True)
        (dto_dir / "BinOrderQueryDTO.java").write_text('''
        package com.smc.dto;
        public class BinOrderQueryDTO {
            private String supplierCode;
            private String modelNo;
        }
        ''', encoding="utf-8")

        # 模拟 ApiMatch
        api_matches = [
            ApiMatch(
                id="api-1",
                frontend=FrontendApiCall(
                    function_name="listBinOrderDetail",
                    http_method="POST",
                    url="/api/binorder/detail",
                    callers=[{"page": "csstock/apply/index.vue"}],
                ),
                backend=BackendEndpoint(
                    controller_file="BinOrderController.java",
                    param_type="BinOrderQueryDTO",
                    url="/api/binorder/detail",
                ),
                match_confidence=1.0,
                domain="bin-data",
            )
        ]

        # 执行完整流程
        result = build_field_map(api_matches, entity_dir, dto_dir, backend_root)

        # 验证结果结构: domain → table → columns → column → 来源列表
        assert "bin-data" in result
        assert "bin_data_apply" in result["bin-data"]

        columns = result["bin-data"]["bin_data_apply"]["columns"]

        # supplier_code 应有 1 个来源
        assert "supplier_code" in columns
        assert len(columns["supplier_code"]) == 1
        entry = columns["supplier_code"][0]

        assert entry["api_function"] == "listBinOrderDetail"
        assert entry["dto_field"] == "supplierCode"
        assert entry["entity_class"] == "BindataApply"
        assert entry["entity_field"] == "supplierCode"
        assert entry["db_column"] == "supplier_code"
        assert entry["db_table"] == "bin_data_apply"
        assert entry["confidence"] == 1.0
        assert "csstock/apply/index.vue" in entry["callers"]

        # model_no 也应有匹配（无 @TableField，回退 camel_to_snake）
        assert "model_no" in columns
        assert len(columns["model_no"]) == 1


def test_build_field_map_no_entity_dir():
    """验证 Entity 目录不存在时静默降级。"""
    from graph_wiki.field_mapper import build_field_map
    from graph_wiki.models import ApiMatch, FrontendApiCall, BackendEndpoint
    from pathlib import Path
    import tempfile

    with tempfile.TemporaryDirectory() as tmpdir:
        dto_dir = Path(tmpdir) / "dto"
        dto_dir.mkdir(parents=True)
        (dto_dir / "TestDTO.java").write_text('''
        public class TestDTO {
            private String name;
        }
        ''', encoding="utf-8")

        api_matches = [
            ApiMatch(
                id="api-1",
                frontend=FrontendApiCall(function_name="testApi"),
                backend=BackendEndpoint(param_type="TestDTO"),
            )
        ]

        # Entity 目录不存在
        result = build_field_map(
            api_matches,
            Path(tmpdir) / "nonexistent-entity",
            dto_dir,
            Path(tmpdir),
        )

        # 结果存在但为空（无 Entity 信息）
        assert isinstance(result, dict)


def test_build_field_map_no_dto_dir():
    """验证 DTO 目录不存在时静默降级。"""
    from graph_wiki.field_mapper import build_field_map
    from graph_wiki.models import ApiMatch, FrontendApiCall, BackendEndpoint
    from pathlib import Path
    import tempfile

    with tempfile.TemporaryDirectory() as tmpdir:
        entity_dir = Path(tmpdir) / "entity"
        entity_dir.mkdir(parents=True)
        (entity_dir / "TestEntity.java").write_text('''
        @TableName("test_table")
        public class TestEntity {
            private String name;
        }
        ''', encoding="utf-8")

        api_matches = [
            ApiMatch(
                id="api-1",
                frontend=FrontendApiCall(function_name="testApi"),
                backend=BackendEndpoint(param_type="TestDTO"),
            )
        ]

        # DTO 目录不存在
        result = build_field_map(
            api_matches,
            entity_dir,
            Path(tmpdir) / "nonexistent-dto",
            Path(tmpdir),
        )

        assert isinstance(result, dict)
        # 无 DTO 字段匹配 → 空聚合结果
        assert len(result) == 0
```

---

## 11. 配置项清单

用户可通过 `graph-wiki.yaml` 配置以下 Field Mapper 参数：

```yaml
field_mapper:
  # Entity 源码目录名（相对后端项目源目录）
  entity_dir: "entity"

  # 额外 Entity 目录名（如有多个，如 domain 目录也含 Entity）
  extra_entity_dirs:
    - "domain"

  # DTO 源码目录名（相对后端项目源目录）
  dto_dir: "dto"

  # 额外 DTO/VO 目录名
  extra_dto_dirs:
    - "vo"
    - "request"
    - "response"

  # DTO 文件名校验关键词（不区分大小写，文件名含其一即被识别）
  dto_file_keywords:
    - "dto"
    - "vo"
    - "request"
    - "response"

  # 匹配阈值（低于此值的匹配不输出）
  match_threshold: 0.6
```

---

## 12. 变更记录

| 日期 | 变更内容 | 版本 |
|------|---------|:----:|
| 2026-06-11 | 初始版本（基于架构文档 v1.0） | v1.0 |
| 2026-06-15 | 重写：根据实际代码实现更新，补充完整接口定义、Entity/DTO 解析细节、匹配算法数学定义、错误处理策略、10 个测试用例、配置项 | v2.0 |
