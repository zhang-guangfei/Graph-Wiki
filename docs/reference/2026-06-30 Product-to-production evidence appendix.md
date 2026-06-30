# Graph-Wiki product-to-production evidence appendix

> Date: 2026-06-30  
> Purpose: provide concrete file-path and command evidence for the product-to-production audit without changing runtime behavior.

## Evidence map

| Evidence area | File paths | Verification commands |
| --- | --- | --- |
| Product acceptance source | `docs/architecture/graph-wiki 架构设计.md` | `sed -n '1,220p' 'docs/architecture/graph-wiki 架构设计.md'` |
| Engineering acceptance source | `docs/architecture/graph-wiki 工程架构设计.md` | `sed -n '1,260p' 'docs/architecture/graph-wiki 工程架构设计.md'` |
| Domain Read Model architecture and contract | `docs/architecture/domain-read-model-v1-architecture.md`, `docs/design/domain-read-model-contract.md`, `docs/design/domain-read-model-acceptance-matrix.md` | `sed -n '1,220p' docs/architecture/domain-read-model-v1-architecture.md`; `sed -n '1,220p' docs/design/domain-read-model-contract.md`; `sed -n '1,220p' docs/design/domain-read-model-acceptance-matrix.md` |
| Core build orchestration | `graph_wiki/pipeline.py` | `python -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir output/evidence-svn-platform` |
| Domain Read Model generation | `graph_wiki/domain_read_model.py`, `graph_wiki/product_data.py` | `python -m graph_wiki.pipeline build tests/fixtures/fullstack-enterprise --no-llm --output-dir output/evidence-fullstack-enterprise` |
| Evidence reference normalization | `graph_wiki/evidence.py`, `tests/test_domain_read_model_contract.py` | `python -m pytest -q tests/test_domain_read_model_contract.py` |
| Product quality reporting | `tests/test_product_quality_report.py`, `tests/test_release_quality_gate.py`, `scripts/release_quality_gate.py` | `python -m pytest -q tests/test_product_quality_report.py tests/test_release_quality_gate.py`; `python scripts/release_quality_gate.py --output-dir output/evidence-release-gate` |
| Workbench product consumption | `workbench/src/App.vue`, `workbench/src/types.ts`, `workbench/package.json` | `(cd workbench && npm run build)` |
| CI/release path | `.github/workflows/ci.yml`, `scripts/release_quality_gate.py` | `python scripts/release_quality_gate.py --output-dir output/evidence-release-gate` |
| Demo fixture readiness | `tests/svn-platform/`, `tests/fixtures/fullstack-enterprise/` | `python -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir output/evidence-svn-platform`; `python -m graph_wiki.pipeline build tests/fixtures/fullstack-enterprise --no-llm --output-dir output/evidence-fullstack-enterprise` |

## Required generated artifacts to inspect

After running the build commands above, inspect these generated files under the selected output directory:

- `domain-read-model.json` — primary product model for business-domain deep reading.
- `workbench-data.json` — browser Workbench consumption package.
- `build-report.json` — separates `build.status` from `productQuality.deepReadingStatus`.
- `wiki/index.md` and `wiki/api-index.md` — Markdown consumption path.
- `domain_graph.html` — domain-level visualization path.

## Minimum launch-readiness command set

```bash
python -m pytest -q
python -m graph_wiki.pipeline build tests/fixtures/fullstack-enterprise --no-llm --output-dir output/evidence-fullstack-enterprise
python -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir output/evidence-svn-platform
(cd workbench && npm run build)
python scripts/release_quality_gate.py --output-dir output/evidence-release-gate
```

## Evidence status captured in this worker pass

This appendix is evidence-only. It does not assert final product readiness by itself; readiness remains owned by the product-to-production audit and the release quality gate.

## Worker-2 verification evidence (2026-06-30)

| Check | Command | Result |
| --- | --- | --- |
| Full Python test suite | `python3 -m pytest -q` | PASS — `71 passed in 56.68s` |
| SVN demo build | `python3 -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir output/evidence-svn-platform` | PASS — `build.status: passed`, `productQuality.deepReadingStatus: warning` |
| Fullstack enterprise demo build | `python3 -m graph_wiki.pipeline build tests/fixtures/fullstack-enterprise --no-llm --output-dir output/evidence-fullstack-enterprise` | PASS — `build.status: passed`, `productQuality.deepReadingStatus: passed` |
| Workbench dependency install | `(cd workbench && npm ci)` | PASS — `added 44 packages` |
| Workbench typecheck/build | `(cd workbench && npm run build)` | PASS — `vue-tsc --noEmit && vite build`, `✓ built` |
| Release quality gate | `python3 scripts/release_quality_gate.py --output-dir output/evidence-release-gate` | PASS — `Release quality gate: PASS`; report at `output/evidence-release-gate/release-quality-gate-report.json` |
| Modified-file evidence sanity | Python path/command presence check for this Markdown file | PASS — `43 lines` before this verification section |

Notes:

- The first attempted `python -m pytest -q` failed with `python: command not found`; all successful Python verification used `python3`.
- The first attempted Workbench build failed with `vue-tsc: not found`; `npm ci` installed local dependencies, then the same build command passed.
- Generated outputs remain under ignored paths (`output/`, `workbench/dist/`, `workbench/public/workbench-data.json`, fixture `node_modules/`/`dist/`) and are not intended for commit.
