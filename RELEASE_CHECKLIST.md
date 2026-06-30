# Release Checklist

Use this checklist before tagging or publishing Graph-Wiki.

## Required gates

- [ ] Run `python scripts/release_quality_gate.py` from a clean checkout.
- [ ] Confirm `release-quality-gate-report.json` records all commands as `passed`.
- [ ] Confirm `fullstack-enterprise` has `build.status=passed` and `productQuality.deepReadingStatus=passed`.
- [ ] Confirm `svn-platform` has `build.status=passed` and any `productQuality` warning is visible and explained.
- [ ] Confirm npm audit commands in the report use `--registry https://registry.npmjs.org`.
- [ ] Confirm `python -m pip check` passes.
- [ ] Confirm `git status --short` contains no generated artifacts.

## Governance checks

- [ ] `LICENSE` matches `pyproject.toml` license metadata.
- [ ] `SECURITY.md` reporting and secret-handling guidance is current.
- [ ] Code owner / reviewer coverage is configured for release-critical files.
- [ ] Release notes distinguish pipeline success from product readability (`productQuality`).

## Known non-blocking warnings

If `svn-platform` remains `warning`, document whether the cause is missing field rules, evidence, or another explicit product-quality reason. Do not describe a warning smoke as fully passed product readiness.
