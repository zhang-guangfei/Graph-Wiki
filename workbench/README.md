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

The current demo data comes from:

```text
docs/wiki-demo/generated/svn-acceptance-audit-2026-06-26
```

The workbench treats that JSON file as the front-end contract, so future Wiki rebuilds should update data rather than require UI code changes.
