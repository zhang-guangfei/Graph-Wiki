# Graph-Wiki Workbench

Graph-Wiki Workbench is the lightweight browser experience for generated Graph-Wiki products.

The first version is intentionally simple:

- Vue 3 + Vite + TypeScript
- Reads a static `public/workbench-data.json`
- Does not require a backend service to browse a generated product
- Keeps UI code stable while each Wiki build only refreshes the data bundle

## Run

```powershell
npm install
npm run dev -- --port 5174
```

Then open:

```text
http://127.0.0.1:5174/
```

## Build

```powershell
npm run build
```

## Data Flow

`graph_wiki.product_data.ProductDataService` exports `workbench-data.json` from a generated product directory.

Prepare demo runtime data from any `graph-wiki build` output with the repository-level helper:

```bash
python scripts/prepare_workbench_demo.py output/svn-platform
```

The helper validates that `workbench-data.json` is a Workbench v1 bundle derived from `domain-read-model.json`, then copies it to `workbench/public/workbench-data.json`. That runtime file is ignored by git, so demos are reproducible without committing generated artifacts.

The workbench treats that JSON file as the front-end contract, so future Wiki rebuilds should update data rather than require UI code changes.
