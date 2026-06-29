# Graph-Wiki

Graph-Wiki 是一个从源代码自动构建业务知识库的工具。

它会分析一个代码仓库，把代码文件归入业务域，识别 API、字段链路、代码本体和影响关系，并导出人能读懂的 Markdown Wiki 与机器可消费的 JSON 数据。项目目标不是做“源码搜索引擎”，而是把代码库编译成面向研发人员和 Agent 的业务导航系统。

## 核心定位

- 面向人类程序员：用业务域、业务点、API、字段影响来理解系统。
- 兼容 Agent：输出稳定 JSON 产物，便于 Agent 做 scope reduction 和影响分析。
- 轻量产品化：生成的 `workbench-data.json` 可以被浏览器 Workbench 直接读取。
- 业务域深度阅读：v1 以 `domain-read-model.json` 组织“流程 → 规则 → 证据”的产品阅读路径。

## 构建产物

执行构建后，Graph-Wiki 会把产物写入指定输出目录：

- `wiki/`：业务可读的 Markdown Wiki
- `domains.json`：业务域划分
- `api-map.json`：API 索引与前后端调用证据
- `field-map.json`：字段链路证据
- `ontology.json`：代码/业务本体
- `impact-analysis.json`：影响分析结果
- `dream-cycle-report.json`：知识库维护报告，存在时生成
- `domain-read-model.json`：业务域深度阅读模型，Workbench v1 的核心产品输入
- `workbench-data.json`：Workbench 前端数据包
- `build-report.json`：质量、规模、耗时和验收报告；其中 `build.status` 与 `productQuality.deepReadingStatus` 分离
- `domain_graph.html`：业务域级关系图

生成产物默认不提交到 git。建议始终使用 `--output-dir`，例如写入 `output/xxx`。

## 环境要求

- Python 3.10+
- Node.js 20+，仅在运行 Workbench 前端时需要

## 安装

```powershell
python -m pip install -e .[dev]
```

## 构建 Wiki

使用内置 SVN 示例项目：

```powershell
python -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir output/svn-platform
```

安装后也可以使用命令行入口：

```powershell
graph-wiki build tests/svn-platform --no-llm --output-dir output/svn-platform
```

构建完成后可以查看：

```text
output/svn-platform/wiki/index.md
output/svn-platform/domain_graph.html
```

## 运行测试

```powershell
python -m pytest -q
```

## 发布质量门禁

发布或合并前运行同一条本地/CI 门禁。它会执行 pytest、Workbench TypeScript build、`fullstack-enterprise` 构建、`svn-platform` 构建与前端 smoke，并显式校验 `build-report.json` 中的 `build.status` 与 `productQuality.deepReadingStatus`：

```powershell
python scripts/release_quality_gate.py
```

判定口径：`build.status=passed` 只代表流水线执行成功；`productQuality.deepReadingStatus` 才代表 Domain Read Model v1 是否能支撑“流程 → 规则 → 证据”的产品阅读路径。

## 运行 Workbench 前端

先构建一份产物，确保输出目录里存在 `workbench-data.json`：

```powershell
python -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir output/svn-platform
```

把数据包放到 Workbench 的 public 目录：

```powershell
Copy-Item output/svn-platform/workbench-data.json workbench/public/workbench-data.json
```

启动前端：

```powershell
cd workbench
npm install
npm run dev -- --port 5174
```

浏览器打开：

```text
http://127.0.0.1:5174/
```

## 目录结构

```text
graph_wiki/        Graph-Wiki Python 核心代码
tests/             集成测试和示例项目
workbench/         Vue/Vite 轻量浏览器工作台
docs/architecture/ 核心架构设计
docs/design/       模块与产品化设计
docs/reference/    外部启发、调研和历史分析
pyproject.toml     Python 包配置
```

## 关键文档

- `docs/architecture/graph-wiki 架构设计.md`：项目架构真相源。
- `docs/architecture/domain-read-model-v1-architecture.md`：v1 业务域深度阅读详细架构。
- `docs/design/domain-read-model-contract.md`：`domain-read-model.json` 数据契约。
- `docs/design/domain-read-model-acceptance-matrix.md`：v1 验收矩阵和企业全栈夹具说明。
- `docs/design/模块总体设计.md`：核心模块接口与职责。
- `docs/reference/2026-06-23 企业知识库四层模型对 Graph-Wiki 架构启发.md`：项目从 RAG、LLM Wiki、知识图谱、本体和长期记忆系统中获得的架构启发。
- `AGENTS.md`：项目协作规则和目录治理约定。

## 开发说明

- `--no-llm` 会关闭 LLM 调用，适合本地测试和 CI。
- 每次构建建议显式传入 `--output-dir`，避免把产物写到仓库根目录。
- 构建输出、缓存、Java `target/`、Python bytecode、Node modules、Playwright 记录、本地项目管理产物都会被 `.gitignore` 忽略。
