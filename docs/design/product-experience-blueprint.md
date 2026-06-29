# Product Experience Blueprint：Graph-Wiki 工作台 v0

日期：2026-06-26

## 产品定位

Graph-Wiki 工作台 v0 是一个面向研发、架构师、项目负责人和 Agent 的代码业务知识库浏览器。

它不是源码搜索引擎，也不是普通 Markdown 文档站。它的核心价值是把代码仓库编译成业务域、API、字段流、影响分析和长期维护摘要，让用户能从业务问题进入代码证据。

## 前端技术路线

工作台 v0 采用 **Vue 3 + Vite + TypeScript**。

选择理由：

- Vue 对企业后台、知识库工作台和中文团队更友好，学习和协作成本低。
- Vite 足够轻，首版可以构建成静态站点，不需要服务端。
- TypeScript 能保护前端数据契约，减少 DTO 字段变化带来的隐性错误。
- 组件化能支撑搜索、筛选、详情页、证据下钻和维护摘要等交互。
- 当前项目已经有大量 Vue/SVN Platform 样本语境，前端设计和验证更顺手。

首版数据模式：

```text
Graph-Wiki build
        ↓
ProductDataService
        ↓
workbench-data.json
        ↓
Vue 3 Workbench
```

关键原则：**Wiki 每次重新编译，只替换 `workbench-data.json`，不修改前端代码。**

只有当前端数据契约本身发生变化时，才需要调整前端组件。

## 目标用户

### 新加入研发

核心问题：

- 这个项目有哪些业务域？
- 我应该先读哪些文件？
- 某个页面或接口背后有哪些后端代码？

成功标准：5 分钟内找到一个业务域的关键页面、Controller、Service 和 API。

### 架构师

核心问题：

- 业务域划分是否合理？
- 哪些域互相依赖？
- 本体关系是否能支撑影响分析？

成功标准：能从域详情下钻到 API、字段、关系和证据。

### 项目负责人

核心问题：

- 本项目当前知识库质量如何？
- 这次构建或维护发生了什么变化？
- 哪些地方需要人工审核？

成功标准：能看懂项目总览、质量状态和 Dream Cycle 摘要。

### Agent

核心问题：

- 修改某个字段/API/业务点前应该读哪些文件？
- 哪些证据可以缩小上下文范围？

成功标准：能通过 DTO 拿到 agent scope 和 evidence。

## 设计原则

### 1. 先业务，后代码

页面标题和主说明使用业务语言，代码名作为证据或副标题。

示例：

- 主标题：执行 SVN 合并
- 副标题：`handleExecuteMerge()` in `views/svn/merge.vue`

### 2. 信息密集但不拥挤

这是研发/架构工具，不做营销式大卡片页面。页面应安静、清晰、适合扫描：

- 左侧稳定导航
- 中间主内容
- 右侧证据和上下文
- 表格支持筛选和搜索
- 复杂关系默认折叠

### 3. 每个结论都有证据

所有业务解释都要能下钻：

- 为什么这个 API 属于 svn？
- 为什么这个字段影响 repository？
- 为什么这个业务点是 core_action？

前端应提供“查看证据”而不是把证据全部摊开。

### 4. 空状态也要有解释

不要显示空白表格或 `—` 后结束。必须说明：

- 没有检测到
- 当前不支持
- 需要人工补充
- 可能是动态调用

### 5. 让维护变化可审阅

Dream Cycle 页面不只是显示 0/0/0，而是告诉用户：

- 本次是否稳定
- 是否有过期页面
- 是否保护了人工内容
- 是否有待审核的重复业务点

## 信息架构

```text
Graph-Wiki Workbench
├── Project Overview
│   ├── Quality Summary
│   ├── Domain Map
│   ├── Recommended Reading
│   └── Latest Maintenance Summary
├── Domains
│   ├── Domain Detail
│   ├── Code Map
│   ├── Business Points
│   ├── APIs
│   ├── Field Flows
│   ├── Dependencies
│   └── Evidence
├── APIs
│   ├── API Index
│   ├── API Detail
│   └── Caller / Controller / DTO
├── Impact Analysis
│   ├── Field Impact
│   ├── API Impact
│   ├── Business Point Impact
│   ├── Rule Trace
│   └── Domain Dependency
├── Maintenance
│   ├── Dream Cycle Summary
│   ├── Review Queue
│   └── Manual Files
└── Search
```

## 页面蓝图

### 1. Project Overview

目标：让用户一眼知道这个项目是什么、质量如何、从哪里开始。

布局：

- 顶部：项目名称、构建时间、质量状态。
- 指标栏：业务域、API、字段链路、代码文件、图节点。
- 主区域：业务域列表。
- 右侧：推荐阅读、最近维护摘要、风险提示。

关键内容：

- `SVN Platform`
- `3 个业务域`
- `46 个 API`
- `6 条字段链路`
- `Dream Cycle passed`
- `推荐先阅读：SVN 操作 → 仓库管理 → 需求管理`

### 2. Domain Detail

目标：让用户理解一个业务域由哪些页面、接口、服务和规则承载。

布局：

- 顶部：业务域名、业务说明、健康状态。
- 左侧分组：核心动作、交互动作、辅助方法。
- 中间：关键代码入口和 API。
- 右侧：证据链、依赖域、规则/规格状态。

关键交互：

- 点击业务点打开详情。
- 点击 API 跳转 API 详情。
- 点击源码路径复制或打开。
- 低置信度字段默认折叠。

### 3. API Index

目标：让用户快速查接口归属和调用链。

布局：

- 顶部搜索框：支持 URL、方法名、Controller、页面名。
- 筛选：业务域、HTTP 方法、是否有前端调用者、置信度。
- 表格：方法、URL、用途、业务域、前端调用者、后端入口、DTO。

状态：

- 有前端调用者：正常显示。
- 无前端调用者：显示原因标签，而不是简单 `—`。

### 4. Impact Analysis

目标：回答“改这里会影响什么”。

布局：

- 顶部问题选择器：字段 / API / 业务点 / 规则 / 域依赖。
- 左侧：问题列表或搜索。
- 中间：答案摘要、影响范围、建议动作。
- 右侧：证据链。

必须增强：

- 风险等级
- 推荐修改顺序
- 建议回归页面
- 建议测试点

### 5. Maintenance

目标：让用户知道知识库本次发生了什么变化，是否需要人工处理。

布局：

- 顶部：Dream Cycle 状态。
- 变更摘要：新增、修改、删除、变更域。
- 维护质量：孤立页面、归档页面、重复业务点、人工文件保护。
- 审核队列：需要人工处理的事项。

空状态：

- “本次知识库稳定，无需人工处理。”

## 视觉方向

Graph-Wiki 是企业研发知识工具，应采用专业、克制、信息清晰的视觉风格。

建议：

- 背景：浅色为主，支持后续暗色。
- 主色：冷静的蓝绿色或钢蓝，用于导航和关键状态。
- 强调色：用于风险、警告和维护变化。
- 字体：系统 sans-serif；代码使用 monospace。
- 图标：使用统一线性图标，不使用 emoji 作为结构图标。
- 卡片：只用于重复实体或信息分组，避免卡片套卡片。
- 圆角：控制在 6-8px。
- 表格：紧凑、可排序、可筛选。

避免：

- 过度营销式 hero。
- 大面积渐变背景。
- 只有装饰没有信息的图形。
- 过多悬浮卡片。
- 把 JSON 字段名直接暴露给普通用户。

## 交互规则

### 搜索

搜索必须支持：

- 业务域
- API URL
- 字段名
- 业务点
- 源码文件

搜索结果要显示类型、所属域和跳转目标。

### 筛选

API、字段、业务点列表必须支持筛选。筛选状态应保留在 URL query 中，支持分享。

### 证据下钻

证据默认折叠，用户点击后展开：

- 源码路径
- Wiki 页面
- ontology relationship
- field-map entry
- impact entry

### 状态反馈

所有页面必须显示质量状态：

- ready
- partial
- empty
- warning
- failed

### 可访问性

首版至少做到：

- 文字对比度满足 4.5:1。
- 交互元素有可见 focus。
- 图标按钮有文本或 aria-label。
- 搜索和筛选可键盘操作。
- 不依赖颜色单独表达状态。

## 内容规范

### 业务动作命名

业务点显示优先级：

1. `businessTitle`
2. 人工命名
3. 规则生成标题
4. `codeName`

示例：

| codeName | businessTitle |
| --- | --- |
| `handleExecuteMerge()` | 执行 SVN 合并 |
| `loadFileDiff()` | 加载文件差异 |
| `handleUpdateRepos()` | 刷新合并仓库数据 |
| `handleImport()` | 导入仓库 |

### API 用途命名

API 用途不应只写“提交相关数据”。应尽量表达具体动作：

- `POST /svn/${repoId}/commit`：提交 SVN 工作副本变更
- `POST /svn/${targetRepoId}/merge/preview`：预览 SVN 合并结果
- `POST /repo/import`：导入本地 SVN 仓库

## v0 验收标准

1. 项目负责人能看懂项目总览。
2. 新研发能从业务域详情找到关键代码入口。
3. API 索引能按域筛选并找到前后端证据。
4. 影响分析能给出建议动作，而不只是证据列表。
5. 维护摘要能说明是否需要人工审核。
6. 空状态有解释和下一步。
7. 页面视觉达到专业企业工具水准。

## 不做范围

v0 不做：

- 完整聊天问答。
- 多租户 SaaS。
- 权限系统。
- 在线编辑 rules/spec。
- 复杂图谱画布。

v0 聚焦：把当前产物变成一个可浏览、可搜索、可下钻、可审阅的知识库工作台。
