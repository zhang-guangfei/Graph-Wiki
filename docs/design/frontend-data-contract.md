# Frontend Data Contract：Graph-Wiki 工作台 v0

日期：2026-06-26

## 目标

本文定义 Graph-Wiki 产品化工作台 v0 的前端数据契约。

目标不是把现有 JSON 原样暴露给前端，而是把 `domains.json`、`api-map.json`、`field-map.json`、`ontology.json`、`impact-analysis.json`、`dream-cycle-report.json`、`build-report.json` 聚合成稳定、业务化、可审计的页面 DTO。

前端实现采用 **Vue 3 + Vite + TypeScript**。本契约必须能被 TypeScript 类型直接表达，确保每次 Wiki 重新编译时只替换数据包，不需要改前端代码。

## 设计原则

### 1. 页面 DTO 优先

前端消费页面模型，不消费底层产物模型。

底层产物可以演进，但以下页面 DTO 必须保持稳定：

- `ProjectOverview`
- `DomainListItem`
- `DomainDetail`
- `ApiIndexItem`
- `FieldFlowItem`
- `BusinessPointDetail`
- `ImpactSummary`
- `DreamCycleSummary`
- `EvidenceRef`

### 2. 每个结论必须带证据

Graph-Wiki 的可信度来自证据链。任何业务域、API、字段影响、业务点解释，都必须能回到至少一种证据：

- source file
- wiki page
- API mapping
- field mapping
- ontology relationship
- impact-analysis entry
- build-report quality item

### 3. 支持人类和 Agent 共用

同一个接口既要能渲染人类 UI，也要能给 Agent 缩小阅读范围。因此 DTO 中要同时包含：

- 人类可读字段：`title`、`summary`、`businessMeaning`
- 机器可读字段：`id`、`type`、`domainKey`、`sourcePath`、`relationshipType`
- 证据字段：`evidence`

### 4. 缺失数据要显式表达

前端不猜测数据缺失原因。后端必须用结构化状态表达：

- `ready`
- `partial`
- `empty`
- `unsupported`
- `failed`

例如某个业务域没有字段流页面，不应让前端展示空白，而应返回 `fieldFlow.status = empty` 和 `reason`。

## 数据来源

| 源产物 | 用途 |
| --- | --- |
| `build-report.json` | 项目规模、质量状态、性能、阶段验收 |
| `domains.json` | 业务域、锚点、业务点、依赖 |
| `api-map.json` | API、前后端调用关系、业务域归属 |
| `field-map.json` | API → DTO → Entity → DB 字段链路 |
| `ontology.json` | typed relationships、agent scope、本体对象 |
| `impact-analysis.json` | 字段/API/业务点/规则/域依赖影响分析 |
| `dream-cycle-report.json` | 维护变化、人工文件保护、孤立页面 |
| `manifest.json` | 文件清单、域清单、构建时间 |
| `wiki/**/*.md` | Markdown 页面链接和人工维护内容 |

## API 建议

首版可以是本地文件读取层，也可以是 HTTP API。接口语义保持一致。

v0 默认不启动 HTTP 服务，而是由 Python 导出静态数据包：

```text
workbench-data.json
```

Vue 工作台只读取该数据包。后续如果接入 FastAPI，接口返回结构必须保持和该数据包一致。

```text
GET /api/projects/{projectId}/overview
GET /api/projects/{projectId}/domains
GET /api/projects/{projectId}/domains/{domainKey}
GET /api/projects/{projectId}/apis
GET /api/projects/{projectId}/fields
GET /api/projects/{projectId}/business-points/{pointId}
GET /api/projects/{projectId}/impact
GET /api/projects/{projectId}/dream-cycle
GET /api/projects/{projectId}/evidence
GET /api/projects/{projectId}/search?q={query}
```

## 通用结构

### EvidenceRef

```json
{
  "type": "source_file | wiki_page | api_map | field_map | ontology | impact | build_report | dream_cycle",
  "label": "SvnOperationController.java",
  "path": "svn-platform-backend/src/main/java/com/svnplatform/controller/SvnOperationController.java",
  "section": "optional heading or relationship id",
  "confidence": 0.9,
  "confidenceLabel": "high | medium | low | unknown"
}
```

### QualityBadge

```json
{
  "status": "passed | warning | failed | partial | unknown",
  "label": "Phase 4 impact analysis",
  "message": "5 query types passed",
  "evidence": []
}
```

### EmptyState

```json
{
  "status": "empty",
  "reason": "No field mapping was detected for this domain.",
  "recommendedAction": "Check DTO/entity naming or add field mapping rules."
}
```

## ProjectOverview

用于项目总览页。

```json
{
  "projectId": "svn-platform-full",
  "projectName": "SVN Platform",
  "generatedAt": "2026-06-26T00:00:00",
  "sourceRoot": "tests/fixtures/svn-platform-full",
  "quality": {
    "build": "passed",
    "performance": "passed",
    "phase3": "passed",
    "phase4": "passed",
    "phase5": "passed"
  },
  "scale": {
    "totalFiles": 72,
    "codeFiles": 70,
    "graphNodes": 223,
    "graphEdges": 238,
    "apis": 46,
    "fields": 6,
    "domains": 3
  },
  "domains": [],
  "topQuestions": [],
  "recommendedReading": [],
  "latestDreamCycle": {},
  "evidence": []
}
```

必填字段：

- `projectId`
- `projectName`
- `quality.build`
- `scale.domains`
- `domains`

缺失降级：

- 没有 `dream-cycle-report.json` 时，`latestDreamCycle.status = unsupported`。
- 没有 `impact-analysis.json` 时，`topQuestions.status = unsupported`。

## DomainListItem

用于项目总览页和左侧导航。

```json
{
  "domainKey": "svn",
  "displayName": "SVN 操作",
  "summary": "围绕 SVN 状态、日志、分支标签、合并和冲突处理的业务域。",
  "anchorCount": 9,
  "businessPointCount": 26,
  "apiCount": 34,
  "fieldFlowCount": 2,
  "dependencyCount": 2,
  "quality": "ready",
  "evidence": []
}
```

显示规则：

- `displayName` 优先使用业务中文名。
- 如果只有英文 key，前端可以显示 key，但必须标记为 `needsHumanLabel = true`。

## DomainDetail

用于业务域详情页。

```json
{
  "domainKey": "svn",
  "displayName": "SVN 操作",
  "businessMeaning": "该业务域负责本地仓库状态、提交、日志、合并、分支标签和冲突处理。",
  "health": {
    "status": "ready",
    "warnings": []
  },
  "entryFiles": [
    {
      "role": "controller",
      "name": "SvnOperationController.java",
      "path": "svn-platform-backend/src/main/java/com/svnplatform/controller/SvnOperationController.java"
    }
  ],
  "businessPoints": {
    "coreActions": [],
    "interactions": [],
    "helpers": []
  },
  "apis": [],
  "fieldFlows": {
    "status": "ready | empty | partial",
    "items": [],
    "emptyState": null
  },
  "dependencies": [],
  "rules": {
    "status": "placeholder | ready | missing",
    "wikiPage": "wiki/svn/rules.md"
  },
  "spec": {
    "status": "placeholder | ready | missing",
    "wikiPage": "wiki/svn/spec.md"
  },
  "agentScope": {
    "entryFiles": [],
    "apis": [],
    "tables": []
  },
  "evidence": []
}
```

质量门禁：

- 没有 `entryFiles` 时，域详情不能标记 `ready`。
- 没有业务点分级时，域详情最多为 `partial`。
- `rules/spec` 只有占位内容时，必须显示 `placeholder`，不能显示为已完成。

## ApiIndexItem

用于 API 索引页和域详情页。

```json
{
  "apiId": "svnCommit:/svn/${repoId}/commit",
  "method": "POST",
  "url": "/svn/${repoId}/commit",
  "domainKey": "svn",
  "businessUse": "提交 SVN 工作副本变更。",
  "frontendCallers": [
    "views/svn/status.vue"
  ],
  "backend": {
    "controller": "SvnOperationController.java",
    "method": "commit",
    "serviceCall": "svnOperationService.commit()"
  },
  "dto": "CommitRequest",
  "confidence": 0.9,
  "evidence": []
}
```

显示规则：

- 前端调用者为空时，不显示 `—` 即结束；必须显示原因：
  - `no_frontend_caller_detected`
  - `backend_only`
  - `dynamic_call_possible`
  - `unknown`

## FieldFlowItem

用于字段影响页。

```json
{
  "fieldId": "repository.local_path",
  "domainKey": "repository",
  "table": "repository",
  "column": "local_path",
  "api": {
    "method": "POST",
    "url": "/repo/import",
    "functionName": "importRepo"
  },
  "dto": {
    "className": "RepoImportRequest",
    "field": "localPath"
  },
  "entity": {
    "className": "Repository",
    "field": "localPath"
  },
  "frontendCallers": [
    "views/repository/list.vue"
  ],
  "confidence": 1.0,
  "confidenceLabel": "high",
  "evidence": []
}
```

质量门禁：

- `confidenceLabel = low` 的字段链路默认折叠，并提示需要人工确认。
- 字段页必须提供“按表 / 按 API / 按业务域”三种筛选方式。

## BusinessPointDetail

用于业务点详情页。

```json
{
  "pointId": "svn:handleExecuteMerge",
  "domainKey": "svn",
  "codeName": "handleExecuteMerge()",
  "businessTitle": "执行 SVN 合并",
  "pointType": "core_action",
  "businessMeaning": "用户确认合并参数后，触发后端合并接口并更新合并结果。",
  "entryFile": "svn-platform-frontend/src/views/svn/merge.vue",
  "relatedApis": [],
  "relatedFields": [],
  "implementationFiles": [],
  "testSuggestions": [],
  "evidence": []
}
```

重要约束：

- 前端页面显示时，优先显示 `businessTitle`，其次显示 `codeName`。
- 如果 `businessTitle` 缺失，UI 必须标记“需要业务命名”。

## ImpactSummary

用于影响分析页。

```json
{
  "status": "ready",
  "queryExamples": [
    {
      "type": "field_change",
      "question": "如果字段 repository.local_path 变化，会影响什么？",
      "answer": "影响 /repo/import、RepoImportRequest.localPath、Repository.localPath、views/repository/list.vue。",
      "riskLevel": "medium",
      "recommendedActions": [
        "检查仓库导入页面",
        "回归 /repo/import 接口",
        "验证 repository.local_path 持久化"
      ],
      "evidence": []
    }
  ],
  "coverage": {
    "fields": 4,
    "apis": 46,
    "businessPoints": 38,
    "rules": 3,
    "domainDependencies": 2
  }
}
```

v0 可先从 `impact-analysis.json` 映射；`riskLevel` 和 `recommendedActions` 可以先由规则生成，后续再引入 LLM。

## DreamCycleSummary

用于维护摘要页。

```json
{
  "status": "passed",
  "version": "dream-cycle-v0",
  "changes": {
    "newFiles": 0,
    "modifiedFiles": 0,
    "deletedFiles": 0,
    "changedDomains": []
  },
  "maintenance": {
    "orphanWikiDomains": [],
    "archivedStaleDomains": [],
    "manualFilesProtected": 6,
    "duplicateBusinessPoints": []
  },
  "reviewQueue": [],
  "evidence": []
}
```

显示规则：

- 没有变化时，不应只显示一串 0；应显示“本次知识库稳定，无需人工处理”。
- 有人工文件时，应显示“已保护人工维护内容”。
- 有孤立页面或重复业务点时，应进入 `reviewQueue`。

## SearchResult

用于全局搜索。

```json
{
  "id": "api:POST:/repo/import",
  "type": "domain | api | field | business_point | rule | file",
  "title": "POST /repo/import",
  "subtitle": "仓库导入接口",
  "domainKey": "repository",
  "matchedText": "importRepo",
  "targetRoute": "/projects/svn-platform-full/apis/api:POST:/repo/import",
  "evidence": []
}
```

搜索范围：

- 业务域名称
- API URL / function name
- 字段名 / 表名
- 业务点 codeName / businessTitle
- 源码文件名
- rules/spec 页面标题

## 后端聚合层职责

后端工程师负责实现 `ProductDataService`，而不是让前端读文件：

```text
ProductDataService
  load_project_overview(output_dir) -> ProjectOverview
  list_domains(output_dir) -> list[DomainListItem]
  get_domain_detail(output_dir, domain_key) -> DomainDetail
  list_apis(output_dir, filters) -> list[ApiIndexItem]
  list_fields(output_dir, filters) -> list[FieldFlowItem]
  get_impact_summary(output_dir) -> ImpactSummary
  get_dream_cycle_summary(output_dir) -> DreamCycleSummary
  search(output_dir, query) -> list[SearchResult]
```

## 前端验收标准

1. 项目总览页不直接展示原始 JSON 字段名。
2. 业务域详情页必须同时展示业务解释和证据链入口。
3. API 索引必须支持按业务域筛选。
4. 字段影响页必须显示可信度。
5. 影响分析页必须给出行动建议。
6. Dream Cycle 页面必须告诉用户是否需要人工处理。
7. 所有关键页面都必须支持深链接。
8. 所有空状态都必须说明原因和下一步。

## 本契约的边界

本文不定义最终 UI 视觉样式，不规定数据库选型，也不要求立即启动 HTTP 服务。首版可以从本地文件聚合开始，但 DTO 名称和字段语义应保持稳定。
