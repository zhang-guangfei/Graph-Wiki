# Security Policy

## Supported scope

Graph-Wiki is currently pre-1.0. Security fixes target the active `main`/`dev` development line until versioned release branches are introduced.

## Reporting a vulnerability

Please report suspected vulnerabilities privately to the repository maintainers or project owner. Do not include secrets, proprietary source code, or full generated artifacts in public issues.

A useful report includes:

- affected version or commit;
- reproduction steps using a minimal fixture when possible;
- whether the issue involves source-code ingestion, generated Wiki/JSON output, Workbench rendering, dependency installation, or CI/release automation;
- any known workaround.

## Release security gate

Before a production release, maintainers should run:

```bash
python scripts/release_quality_gate.py
```

The gate includes Python dependency consistency (`pip check`) and npm high-severity production audits for the Workbench and smoke fixture using the official npm registry endpoint. Local npm mirrors may not implement audit APIs, so release evidence must use the pinned registry command emitted by the gate.

## Secret handling expectations

- LLM/API credentials must come from environment variables and must not be committed.
- Generated artifacts should not include full source files or secrets.
- Build outputs, `node_modules/`, local smoke outputs, and Workbench data bundles remain ignored and must not be committed.
