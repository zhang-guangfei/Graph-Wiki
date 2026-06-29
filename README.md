# Graph-Wiki

Graph-Wiki builds a human-readable business Wiki from source code.

It analyzes a code repository, groups files into business domains, maps APIs and fields, generates impact-analysis data, and exports Markdown/JSON artifacts that can be viewed directly or through the lightweight Workbench UI.

## What It Produces

A build writes artifacts to an output directory:

- `wiki/` business-readable Markdown Wiki
- `domains.json` business domains
- `api-map.json` API index and frontend/backend links
- `field-map.json` field flow evidence
- `ontology.json` code/business ontology
- `impact-analysis.json` impact-analysis answers
- `dream-cycle-report.json` maintenance report when available
- `workbench-data.json` browser Workbench data bundle
- `build-report.json` quality, scale, timing, and acceptance report
- `domain_graph.html` domain-level graph view

Generated artifacts are ignored by git. Keep them under `output/` or pass `--output-dir`.

## Requirements

- Python 3.10+
- Node.js 20+ only if you want to run the Workbench UI

## Install

```powershell
python -m pip install -e .[dev]
```

## Build A Wiki

```powershell
python -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir output/svn-platform
```

Or, after installation:

```powershell
graph-wiki build tests/svn-platform --no-llm --output-dir output/svn-platform
```

Open:

```text
output/svn-platform/wiki/index.md
output/svn-platform/domain_graph.html
```

## Run Tests

```powershell
python -m pytest -q
```

## Run The Workbench UI

First build a product so `workbench-data.json` exists:

```powershell
python -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir output/svn-platform
```

Copy the generated Workbench data into the UI public folder:

```powershell
Copy-Item output/svn-platform/workbench-data.json workbench/public/workbench-data.json
```

Start the UI:

```powershell
cd workbench
npm install
npm run dev -- --port 5174
```

Open:

```text
http://127.0.0.1:5174/
```

## Repository Layout

```text
graph_wiki/        core Python package
tests/             integration tests and small fixture projects
workbench/         lightweight Vue/Vite browser UI
docs/              design and reference material
pyproject.toml     Python package configuration
```

## Development Notes

- `--no-llm` keeps builds deterministic and does not require an API key.
- Use `--output-dir` for every build to avoid writing generated files to the repository root.
- Build outputs, caches, Java `target/`, Python bytecode, Node modules, Playwright traces, and local project-management artifacts are intentionally ignored.
