# Graph-Wiki

从源代码自动构建业务域知识图谱和 Wiki 文档。

## 核心能力

- **业务域自动划分** — 包路径 + import 聚合（替代 Louvain 链接密度聚类）
- **LLM 业务标注** — 自动生成域名称、业务摘要、核心流程
- **前后端接口对接** — 前端 API → 后端 Controller → Mapper 的完整链路
- **字段级数据流** — 前端字段 → DTO → Entity → 数据库列的六层追踪
- **Obsidian 兼容 Wiki** — 双向链接 Markdown 文档树
- **Agent 友好** — Wiki 入口 → code-map → graph.json 三级查询

## 架构

```
graph-wiki build .      复用层 (graphifyy)        新建层 (Graph-Wiki)
─────────────────      ─────────────────        ──────────────────
                        detect()    ← 复用
                        extract()   ← 复用
                        build()     ← 复用
                        ═════════════════        ══════════════════
                                                 cluster()    ← 替换 Louvain
                                                 api_mapper() ← 新增
                                                 field_mapper() ← 新增
                                                 label()      ← 替换关键词拼凑
                                                 export()     ← 替换导出
```

## 快速开始

```bash
pip install graph-wiki

# 构建知识图谱
graph-wiki build .

# 增量更新
graph-wiki update .

# 查询
graph-wiki query "supplierCode 的数据流"
graph-wiki path "BinController" "BinDataMapper"
```

## 项目结构

```
graph_wiki/
├── models.py          # 数据模型
├── reuse.py           # graphifyy API 封装
├── cluster.py         # 业务域聚类（核心）
├── api_mapper.py      # 前后端接口映射
├── field_mapper.py    # 字段级数据流
├── label.py           # LLM 标注
├── export.py          # Wiki 导出
├── visualize.py       # HTML 可视化
└── pipeline.py        # CLI 入口

docs/
├── architecture/      # 总体设计
├── design/            # 详细设计（每个模块一份）
└── reference/         # 市场分析、缺陷分析、审核报告
```

## License

MIT
