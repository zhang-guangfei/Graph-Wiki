# 代码分析 / 业务域识别工具对比

> 日期：2026-06-11  
> 目的：评估市面上能从代码中提取业务域边界的工具，作为 OPS 项目后续选型参考

---

## 一、市场定位对比

```
                          
  人类阅读 ─┤                                  ├─ 人类阅读
            │                                  │
    Backstage          Context Mapper     Spring Modulith
    (开发者门户)        (DDD 建模)         (架构测试)
            │                                  │
            └──────────┬───────────────────────┘
                       │
                   业务域层（当前空缺）
                       │
            ┌──────────┴───────────────────────┐
            │                                  │
      jQAssistant        Graphify        CodeScene
      (图查询)           (知识图谱)       (行为分析)
            │                                  │
   Agent 消费 ┤                                ├─ Agent 消费
```

- **上半区**：以人类为服务对象，关注业务域定义和导航
- **下半区**：以 Agent / 架构师为服务对象，关注代码结构和依赖关系
- **中间空缺**：自动从代码推断业务语义 → 目前无成熟方案

---

## 二、各方案详解

### 1. Spring Modulith

| 维度 | 说明 |
|------|------|
| **官网** | https://spring.io/projects/spring-modulith |
| **核心方法** | 开发者显式声明模块，工具自动验证边界 |
| **业务边界来源** | **人工声明**（@ApplicationModule 注解 + package-info.java） |
| **服务对象** | Java/Spring 开发者、架构师 |
| **输出** | PlantUML 模块依赖图、边界违规报告、AsciiDoc 文档 |
| **集成方式** | Spring Boot 原生，通过 JUnit 测试验证 |

**工作原理：**
```java
// package-info.java — 声明业务模块
@ApplicationModule(displayName = "采购订单域")
package com.smc.ops.purchase;

// 边界验证测试
@Test
void verifyModularity() {
    ApplicationModules.of(Application.class).verify();
    // 自动检测：采购订单域是否违规访问了库存域的内部实现
}
```

**核心能力：**
- 模块依赖图（自动生成 PlantUML）
- 边界违规报告（哪个类跨域调用了不该调用的东西）
- 领域事件追踪（跨模块的异步通信 @ApplicationModuleListener）
- 架构文档自动生成（AsciiDoc）

**优势：**
- 与 Spring 生态深度集成
- 将架构规则变成可自动执行的单元测试
- 适合 CI 集成，每次构建自动检查

**局限：**
- 仅支持 Java / Spring Boot 项目
- 需要开发者先声明模块，不能自动发现业务域
- 不提供可视化 UI（依赖 PlantUML 图表）
- 不支持前端代码

**适合场景：** Spring Boot 项目，团队愿意维护模块声明，希望架构规则自动执行

---

### 2. Context Mapper

| 维度 | 说明 |
|------|------|
| **官网** | https://contextmapper.org/ |
| **核心方法** | DDD Strategic Design 的工程化实现 |
| **业务边界来源** | **DSL 建模为主**，支持从代码反向生成 |
| **服务对象** | DDD 实践者、架构师 |
| **输出** | Bounded Context Map、PlantUML 上下文映射图 |

**工作原理（DSL 建模）：**
```
BoundedContext PurchaseOrderContext {
    domainVisionStatement = "管理采购订单的创建、审批、执行"
    type = CORE_DOMAIN
}

BoundedContext InventoryContext {
    domainVisionStatement = "管理库存的预留、扣减、盘点"
    type = SUPPORTING_DOMAIN
}

ContextMap {
    contains PurchaseOrderContext, InventoryContext
    PurchaseOrderContext [U,D] -> InventoryContext : 
        "采购下单时调用库存预留服务"
}
```

**核心能力：**
- DDD 术语体系（限界上下文、聚合、实体、值对象）
- 上下文映射（ACL / 共享内核 / 客户-供应商 / 发布语言）
- PlantUML 图形化
- 支持从 Java/C# 代码反向生成 DSL（实验性）

**优势：**
- 唯一真正以 DDD 为核心的工具
- DSL 可读性强，产品经理也能理解域间关系
- 正向建模 + 反向工程两条路径

**局限：**
- 社区较小，生态不丰富
- 反向工程能力有限（实验性）
- 学习成本较高（需要 DDD 知识）
- UI 不够现代化

**适合场景：** 团队已采用 DDD 方法，需要限界上下文建模和文档化

---

### 3. Backstage（Spotify）

| 维度 | 说明 |
|------|------|
| **官网** | https://backstage.io/ |
| **核心方法** | 开发者门户，Software Catalog + TechDocs |
| **业务边界来源** | **手动 YAML 声明**（catalog-info.yaml） |
| **服务对象** | 产品经理 + 开发者 + 运维 |
| **输出** | Web 门户（业务域导航、文档站点、API 目录） |

**工作原理：**
```yaml
# catalog-info.yaml
apiVersion: backstage.io/v1alpha1
kind: System
metadata:
  name: ops-purchase
  description: 采购管理系统
  annotations:
    backstage.io/techdocs-ref: dir:./business-content/domains/采购管理
spec:
  owner: procurement-team
  domain: procurement
```

**核心能力：**
- Software Catalog（层级：Domain → System → Component → API）
- TechDocs（基于 MkDocs 的文档站点，Markdown → 网页）
- 搜索（跨代码、文档、API）
- 插件生态（Kubernetes、CI/CD、API 文档等）

**优势：**
- 工业级，Spotify 内部验证
- 产品/开发/运维共享一张地图
- 丰富的插件生态
- 搜索能力强大

**局限：**
- 部署和维护成本高（需要 Node.js + PostgreSQL）
- 所有业务域和组件都需手动声明
- 不能自动从代码推断业务域
- 对小型团队过重

**适合场景：** 中大型组织（50+ 服务），多团队协作，需要统一的开发者门户

**轻量替代：** VitePress / Docusaurus（纯静态文档站点）

---

### 4. jQAssistant

| 维度 | 说明 |
|------|------|
| **官网** | https://jqassistant.org/ |
| **核心方法** | 代码扫描 → Neo4j 图数据库 → Cypher 查询自定义规则 |
| **业务边界来源** | **自定义查询规则**（Cypher） |
| **服务对象** | 架构师、高级开发者 |
| **输出** | 规则违规报告、关系图 |

**工作原理：**
```cypher
// 业务边界检测：找出跨域的调用
MATCH (f1:File)-[:DEPENDS_ON]->(f2:File)
WHERE f1.module = 'ops-delivery' 
  AND f2.module = 'ops-event'
RETURN f1.name, f2.name, COUNT(*) AS call_count
```

**核心能力：**
- Neo4j 图数据库存储，任意 Cypher 查询
- 自定义约束规则，自动化检查
- 支持 Java、XML、YAML 等多种文件类型
- Maven / Gradle 插件集成

**优势：**
- 灵活性极高（Cypher 图查询语言）
- 可以回答几乎任何代码结构问题
- 丰富的内置规则和分析器
- CI 集成

**局限：**
- 学习曲线陡峭（需要学 Cypher）
- 不提供业务域自动发现
- UI 老旧（Neo4j Browser）
- 配置文件复杂（XML 格式）

**适合场景：** 架构治理自动化，需要自定义规则的团队，愿意投入学习成本

**与 Graphify 的对比：**

| 维度 | jQAssistant | Graphify |
|------|------------|----------|
| 存储 | Neo4j 数据库 | JSON 文件（NetworkX） |
| 查询 | Cypher | graphify query（BFS/DFS） |
| 业务域 | 手动写规则 | 不支持 |
| Agent 友好 | 间接（Cypher → Neo4j） | 直接（query/path/explain） |
| 部署 | 需要 Neo4j | 仅需 Python CLI |

---

### 5. CodeScene

| 维度 | 说明 |
|------|------|
| **官网** | https://codescene.com/ |
| **核心方法** | Git 提交历史分析，文件共变模式发现 |
| **业务边界来源** | **行为分析**（哪些文件被一起修改） |
| **服务对象** | 技术管理、架构师 |
| **输出** | 热点图、耦合分析、组织对齐度 |

**工作原理：**
```
Git 历史 → 文件共变矩阵 → 隐藏耦合发现

"开发者修改采购订单功能时，
 经常被迫同时修改库存模块的代码"
→ 说明这两个域在实际工作中紧密耦合
→ DDD 边界可能画错了
```

**核心能力：**
- 热点分析（哪些模块修改最频繁）
- 隐藏耦合检测（代码中被忽视的依赖）
- 组织对齐度（团队边界 vs 代码边界）
- 技术债务量化

**优势：**
- 基于实际行为，不是静态分析
- 能发现文档和代码都看不到的隐藏耦合
- 商业产品的可视化质量高

**局限：**
- 商业产品（付费）
- 需要长期 Git 历史
- 不分析代码内部结构
- 不支持前端代码（主要是 Java/C++/C#）

**适合场景：** 有长期 Git 历史的遗留系统，想了解真实的代码耦合模式

---

## 三、综合对比

| 维度 | Spring Modulith | Context Mapper | Backstage | jQAssistant | CodeScene | Graphify |
|------|:--:|:--:|:--:|:--:|:--:|:--:|
| **业务域发现** | ❌ 手动声明 | ⚠️ 半自动 | ❌ 手动声明 | ❌ 手动查询 | ⚠️ 行为推断 | ❌ 链接密度 |
| **自动关系提取** | ✅ 模块依赖 | ✅ 正向建模 | ❌ | ✅ 图查询 | ✅ 共变分析 | ✅ AST 提取 |
| **人类可读 UI** | ⚠️ PlantUML | ⚠️ PlantUML | ✅ Web 门户 | ⚠️ Neo4j Browser | ✅ 商业产品 | ⚠️ D3 力导向 |
| **Agent 友好** | ❌ | ❌ | ❌ | ⚠️ Cypher | ❌ | ✅ 核心设计目标 |
| **前端支持** | ❌ | ❌ | ✅ | ❌ | ❌ | ✅ .vue/.js |
| **DDD 术语** | ⚠️ 模块 | ✅ 限界上下文 | ⚠️ System | ❌ | ❌ | ❌ |
| **CI 集成** | ✅ JUnit | ❌ | ✅ | ✅ Maven | ✅ | ⚠️ 手动 |
| **成本** | 免费开源 | 免费开源 | 免费（自部署） | 免费开源 | 商业付费 | 免费开源 |
| **学习门槛** | 低 | 中 | 中 | 高 | 低 | 低 |

---

## 四、关键结论

1. **没有任何工具能自动从代码推断出业务域边界** — 这是当前整个市场的空白点。业务语义存在于代码之外的人脑中，只能通过包结构、命名约定等方式间接推断

2. **Graphify 的社区检测（Louvain）≠ 业务域划分** — 它按链接密度聚类，本质上就是 Maven module / Vue 目录结构的镜像，不是业务语义

3. **Spring Modulith + Backstage 组合最接近 OPS 项目需求** — Modulith 在代码层定义和验证边界，Backstage 在展示层提供业务导航

4. **如果要在"自动发现"这个方向上深入**：需要做的是包路径分析 + 类名语义聚类 + LLM 标注的组合，而不是纯图聚类

5. **所有工具都要求一定的维护成本** — 不存在"跑一次就永远准确"的方案。代码变了，文档和域划分就需要 review
