# Graph-Wiki Product-to-Production Gap Audit

Date: 2026-06-30  
Team task: `product-to-production-42c7f743/task-1`  
Scope: audit only; no code changes.  
Product acceptance source: `docs/architecture/graph-wiki 架构设计.md`  
Engineering source: `docs/architecture/graph-wiki 工程架构设计.md`

## Executive verdict

Graph-Wiki now has the v1 product backbone needed for a demoable product path:

- `graph-wiki build` writes `domain-read-model.json`, `workbench-data.json`, and `build-report.json`.
- `build-report.json.build.status` and `build-report.json.productQuality.deepReadingStatus` are separated.
- `workbench-data.json.schema.source == "domain-read-model.json"` is enforced by release artifact checks.
- The Workbench renders a domain deep-reading path around `flows -> rules -> evidence`, including field rules.
- The primary `fullstack-enterprise` fixture reaches `productQuality.deepReadingStatus=passed`.

Production launch is not yet defensible for a broad enterprise codebase claim. The remaining gaps concentrate in four areas: production-scale gate coverage, extractor robustness, evidence validation depth, and demo UX/release packaging.

## Current verified baseline

Commands run in this audit worktree:

| Check | Result | Evidence |
| --- | --- | --- |
| Python test suite | PASS | `python3 -m pytest -q` -> `71 passed in 67.44s` |
| Workbench typecheck/build | PASS | `cd workbench && npm ci && npm run build` -> `vue-tsc --noEmit && vite build`, `✓ built in 26.17s` |
| fullstack enterprise build | PASS | `python3 -m graph_wiki.pipeline build tests/fixtures/fullstack-enterprise --no-llm --output-dir output/audit-fullstack` -> `build.status=passed`, `productQuality.deepReadingStatus=passed`, 1 domain, 74 evidence refs, Workbench v1 source |
| svn-platform smoke build | PASS with product warning | `python3 -m graph_wiki.pipeline build tests/svn-platform --no-llm --output-dir output/audit-svn` -> `build.status=passed`, `productQuality.deepReadingStatus=warning`, warnings for missing field rules |
| Lint on modified files | PASS / not applicable | Modified artifact is Markdown audit documentation only; no configured Markdown linter in `pyproject.toml` |

Important observed product signals:

- `output/audit-fullstack/build-report.json`: `productQuality.deepReadingStatus=passed`, `coreDomainEvidenceStatus=passed`, `ruleCorrectnessRisk=low`.
- `output/audit-svn/build-report.json`: `productQuality.deepReadingStatus=warning`, with explicit warnings: missing field rules in `svn`, `repository`, and `requirement` domains.
- Both generated `workbench-data.json` files report schema source `domain-read-model.json`.
- Phase 4 impact acceptance currently fails in generated builds because domain dependency query coverage / structured impact coverage is incomplete.

## Lane 1 — Product acceptance mapping

### What is already aligned

1. **Business-domain entry point**  
   `domain-read-model.json` is treated as the product truth source and `ProductDataService` derives Workbench DTOs from it.

2. **Flow -> rule -> evidence path**  
   The Workbench domain page renders flows, steps, associated rules, field rules, and evidence chips. `tests/test_workbench_domain_reading_contract.py` locks this DTO derivation path.

3. **Build success vs product success separation**  
   `pipeline._write_build_report()` emits `build.status` and `productQuality` separately. `scripts/release_quality_gate.py` rejects missing or drifted `productQuality`.

4. **Primary fixture reaches v1 acceptance**  
   `tests/fixtures/fullstack-enterprise` generates a passed Domain Read Model and Workbench v1 bundle.

### Gaps to close before production-ready launch

| Priority | Gap | Why it matters | Recommended implementation task |
| --- | --- | --- | --- |
| P0 | Release/demo gate is too small for enterprise production claims | Current launch gate covers small fixtures only; `tests/ops` is much larger and not gated | Add a bounded 300-500 file OPS slice to release gate; add optional/nightly full OPS smoke with time and productQuality thresholds |
| P0 | Deep-reading gate under-validates step-level contracts | Product spec requires traceable flow/rule/evidence, but gate does not fully validate every flow step has evidence and rule links | Extend release artifact validation: every core-domain flow step must have valid `evidenceRefs`; business steps must link `ruleRefs`; field rules required per core domain where analyzable |
| P1 | Product warning UX is not explicit enough | Users need to see what is incomplete and what to review next, not only status chips | Add Workbench global/domain quality panels for warnings/errors, missing evidence categories, machine-draft status, and recommended review actions |
| P1 | Wiki path is still legacy-derived | Product truth source is Domain Read Model, but Markdown export still renders mostly from legacy artifacts | Generate flow/rule/field/evidence Markdown pages from Domain Read Model while preserving manual `rules.md` / `spec.md` ownership |

## Lane 2 — Current implementation and data-contract gaps

### What is already aligned

- `graph_wiki/domain_read_model.py` builds v1 `domains`, `flows`, `businessRules`, `fieldRules`, `evidenceIndex`, and `quality`.
- `graph_wiki/product_data.py` exports Workbench v1 data when a valid read model exists.
- `scripts/release_quality_gate.py::validate_build_artifacts()` checks required artifacts, `productQuality` drift, and Workbench data source.
- Tests cover Domain Read Model contract, Workbench DTO derivation, product quality reporting, and release gate drift behavior.

### Gaps to close before production-ready launch

| Priority | Gap | Why it matters | Recommended implementation task |
| --- | --- | --- | --- |
| P0 | API/field extraction is still heuristic and narrow | Real enterprise code commonly uses TypeScript, arrow exports, request config wrappers, multiple DTO/entity directories, and richer Spring annotations | Expand `api_mapper.py` and `field_mapper.py` coverage: `.ts`, arrow exports, request config patterns, all DTO/entity roots, `@RequestMapping(method=...)`; add regression fixtures |
| P0 | Evidence validity is shallow | Release checks refs exist, but not that source paths and symbols/line anchors resolve accurately | Add source-path and symbol/line validation; unresolved core evidence should fail or force warning with visible reason |
| P1 | Artifact contract is not single canonical list | Engineering docs and gate rely on slightly different required artifact sets | Create one canonical artifact contract covering `domain-read-model.json`, `workbench-data.json`, `build-report.json`, `ontology.json`, `wiki/`; make docs and gate use it |
| P1 | `build-report.json` schema drift risk | Docs examples and implementation can diverge across `graph`, `input`, `quality`, and `productQuality` fields | Formalize build-report schema with contract tests or JSON Schema; update docs/examples to match implementation |
| P1 | LLM sensitive-file filter is not enforced end-to-end | Architecture says secrets must be filtered before prompts; implementation currently risks over-trusting later layers | Enforce sensitive-file exclusion before LLM prompt sampling; add tests for `.env`, credential, token/password file paths |

## Lane 3 — Workbench UX and demo readiness gaps

### What is already aligned

- Workbench builds with the generated data contract.
- `workbench/src/App.vue` renders the main domain deep-reading path, field rules, and evidence cards.
- `workbench/src/types.ts` models the v1 Workbench DTO.
- README explains how to build output, copy `workbench-data.json`, and run Workbench.

### Gaps to close before launch/demo

| Priority | Gap | Why it matters | Recommended implementation task |
| --- | --- | --- | --- |
| P0 | Demo path is not packaged as one command | A user must manually build product data, copy JSON, install npm deps, and start the UI | Add a documented demo command/script: build fixture -> copy `workbench-data.json` -> build/serve Workbench; use generated fullstack fixture by default |
| P1 | Evidence drill-down is visible but not enough as a product interaction | Product acceptance expects users to understand evidence quality and missing layers | Add evidence detail drawer/panel with source path, symbol/line, status, missing reason, and next action |
| P1 | Empty/warning states need stronger explanatory copy | `svn-platform` correctly warns about missing field rules, but UX should teach users why and what to do | Surface `productQuality.warnings/errors` in overview and domain pages with recommended review actions |
| P2 | Workbench distribution model is unclear | Python package excludes `workbench`, so pip installs do not ship the demo UI | Decide launch packaging: static artifact, separate npm app, or Python command serving a built Workbench bundle |

## Lane 4 — Production quality, CI, security, and release gaps

### What is already aligned

- `.github/workflows/ci.yml` calls `scripts/release_quality_gate.py`.
- Release gate runs Python tests, builds both product fixtures, validates artifacts, installs/builds Workbench, and builds the svn frontend smoke fixture.
- `.gitignore` excludes generated output, node modules, and Workbench generated data.
- Release gate has tests for refusing repo-root deletion, generated artifact pollution, missing productQuality, and drifted productQuality.

### Gaps to close before production-ready launch

| Priority | Gap | Why it matters | Recommended implementation task |
| --- | --- | --- | --- |
| P0 | No production-scale CI/performance tier | Launch claims need evidence beyond tiny fixtures | Add CI tiers: PR gate for fast fixtures, scheduled/nightly gate for OPS slice/full OPS with timing, memory, productQuality, and artifact size budgets |
| P0 | Phase 4 impact acceptance fails on generated audit builds | Product roadmap includes cross-module impact; current generated outputs lack domain dependency coverage | Make impact analysis produce all five query types and structured impacts for fullstack/svn where applicable, or narrow release criteria/document non-v1 status |
| P1 | Unsupported CLI/backend options overpromise | CLI/docs mention multiple LLM backends and update/query paths that are not launch-complete | Either implement and test backend/env checks plus update/query DRM behavior, or mark/remove non-v1 options from launch docs |
| P1 | Security filter needs executable gate coverage | Policy in docs is insufficient for production claims | Add tests and release gate checks for sensitive-file exclusion from corpus/LLM samples and generated evidence |
| P2 | Generated artifact governance is mostly process-based | `.gitignore` helps, but launch release should fail on tracked generated/dependency artifacts | Keep and expand tracked-pollution checks; include Workbench dist/public JSON and output dirs in release-gate enforcement |

## Recommended implementation backlog

### P0 — Launch blocker backlog

1. **Production-scale release gate tier**
   - Add 300-500 file OPS slice fixture/gate.
   - Add scheduled full OPS performance smoke.
   - Capture elapsed time, node/edge counts, productQuality, warnings, artifact sizes.

2. **Extractor robustness for real enterprise code**
   - Expand frontend API extraction to `.ts`, arrow exports, request wrappers, and config objects.
   - Expand backend mapping to Spring annotation variants.
   - Scan all DTO/entity source roots.
   - Add regression fixtures covering each pattern.

3. **Deep evidence validation**
   - Validate all core evidence source paths and symbol/line anchors.
   - Validate every flow step has evidence and expected rule refs.
   - Fail or warning-gate unresolved core evidence with reason surfaced in Workbench.

4. **Phase 4 generated acceptance**
   - Ensure generated builds cover the five intended impact query types or formally split Phase 4 from v1 launch criteria.

### P1 — Productization backlog

5. **Workbench warning/review UX**
   - Add overview/domain quality panels and evidence detail panels.
   - Show missing categories, partial reasons, and recommended review actions.

6. **Domain Read Model-derived Wiki**
   - Move Markdown deep-reading pages to DRM-derived generation.
   - Preserve manual pages as explicit human-owned overlays.

7. **Canonical artifact/build-report contract**
   - Formalize schema and contract tests.
   - Align docs, gate, and README examples.

8. **Security enforcement**
   - Apply sensitive-file filtering before LLM prompt sampling and generated evidence.
   - Add tests and release gate checks.

### P2 — Launch clarity backlog

9. **CLI capability truth-in-advertising**
   - Either finish update/query/LLM backend behavior or mark non-v1 commands unsupported in launch docs.

10. **Workbench packaging decision**
   - Choose static, npm, or Python-served Workbench delivery.
   - Add one-command demo path.

## Boundary and coordination notes

- Coordination protocol: coordinated - product/engineering/data/UI/release boundaries checked against architecture docs, code artifacts, Workbench, CI gate, generated outputs, and two independent native subagent probes.
- No handoff-blocking shared file conflict was encountered; this audit changed only a documentation artifact.
- Leader-owned aggregate audit should treat this document as worker-1 evidence and not as final board-level signoff.

## Subagent evidence

Subagents spawned: 2

- `019f1614-89b2-7aa2-a6b2-1d6723f0d388` / repo-map probe: identified key files and current coverage across product acceptance, data contracts, Workbench readiness, and CI/security/release.
- `019f1614-a31e-70e3-8dbc-0c40a9507467` / review-risk probe: identified prioritized P0/P1/P2 production risks, including scale-gate weakness, extractor narrowness, shallow evidence validation, Workbench warning UX, artifact/schema drift, LLM security, CLI/backend overpromise, and packaging ambiguity.

Findings integrated into the four lane sections and recommended backlog above.
