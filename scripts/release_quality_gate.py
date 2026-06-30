#!/usr/bin/env python3
"""Run the local/CI release quality gate for Domain Read Model v1.

The gate intentionally separates pipeline execution success (``build.status``)
from product readability (``productQuality.*``) and phase capability gates. A
green build is not sufficient when the Domain Read Model cannot support the
flow -> rules -> evidence reading path.
"""

from __future__ import annotations

import argparse
import json
import shutil
import subprocess
import sys
import tempfile
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFAULT_OUTPUT = ROOT / "output" / "release-quality-gate"
NPM_AUDIT_REGISTRY = "https://registry.npmjs.org"


class GateFailure(RuntimeError):
    """Raised when a release-gate command or artifact assertion fails."""


def prepare_output_dir(output_dir: Path) -> Path:
    """Create a release-gate output directory without allowing broad deletion.

    Re-running the gate deletes only directories that are clearly owned by the
    gate: the default output area, graph-wiki temp directories, or directories
    carrying the gate marker written by a previous run. This prevents an
    accidental `--output-dir .` from deleting the repository or a home folder.
    """
    output_dir = output_dir.resolve()
    home = Path.home().resolve()
    temp_root = Path(tempfile.gettempdir()).resolve()
    root_output = (ROOT / "output").resolve()
    marker = output_dir / ".graph-wiki-release-gate"

    unsafe_targets = {Path(output_dir.anchor).resolve(), home, ROOT}
    if output_dir in unsafe_targets or output_dir in ROOT.parents:
        raise GateFailure(f"refusing to delete unsafe output directory: {output_dir}")

    if output_dir.exists():
        gate_owned = (
            marker.exists()
            or output_dir == DEFAULT_OUTPUT.resolve()
            or _is_relative_to(output_dir, root_output)
            or (_is_relative_to(output_dir, temp_root) and output_dir.name.startswith("graph-wiki-"))
        )
        if not gate_owned:
            raise GateFailure(f"refusing to delete unmarked output directory: {output_dir}")
        shutil.rmtree(output_dir)

    output_dir.mkdir(parents=True, exist_ok=True)
    marker.write_text("Graph-Wiki release quality gate output\n", encoding="utf-8")
    return output_dir


def _is_relative_to(path: Path, parent: Path) -> bool:
    try:
        path.relative_to(parent)
    except ValueError:
        return False
    return True


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description="Run Graph-Wiki release quality gate")
    parser.add_argument("--output-dir", type=Path, default=None, help="Directory for smoke build outputs")
    parser.add_argument("--output-root", type=Path, default=None, help="Backward-compatible alias for --output-dir")
    parser.add_argument(
        "--skip-frontend-installs",
        action="store_true",
        help="Skip npm install/build checks; intended only for offline Python-only diagnosis.",
    )
    args = parser.parse_args(argv)

    output_dir = prepare_output_dir((args.output_dir or args.output_root or DEFAULT_OUTPUT).resolve())

    results: dict[str, Any] = {"commands": [], "artifactChecks": {}, "actions": []}

    core_checks: list[tuple[str, list[str], Path]] = [
        ("pytest", [sys.executable, "-m", "pytest", "-q"], ROOT),
        ("python dependency check", [sys.executable, "-m", "pip", "check"], ROOT),
        (
            "fullstack-enterprise build",
            [
                sys.executable,
                "-m",
                "graph_wiki.pipeline",
                "build",
                "tests/fixtures/fullstack-enterprise",
                "--no-llm",
                "--output-dir",
                str(output_dir / "fullstack-enterprise"),
            ],
            ROOT,
        ),
        (
            "svn-platform graph-wiki smoke",
            [
                sys.executable,
                "-m",
                "graph_wiki.pipeline",
                "build",
                "tests/svn-platform",
                "--no-llm",
                "--output-dir",
                str(output_dir / "svn-platform"),
            ],
            ROOT,
        ),
    ]

    try:
        for name, command, cwd in core_checks:
            results["commands"].append(run_command(name, command, cwd))

        results["artifactChecks"]["fullstack-enterprise"] = validate_build_artifacts(
            output_dir / "fullstack-enterprise",
            expected_product="passed",
            require_field_rules=True,
        )
        results["artifactChecks"]["svn-platform"] = validate_build_artifacts(
            output_dir / "svn-platform",
            allow_warning=True,
        )
        _raise_for_artifact_failures(results["artifactChecks"])

        if not args.skip_frontend_installs:
            frontend_checks: list[tuple[str, list[str], Path]] = [
                ("workbench npm ci", ["npm", "ci"], ROOT / "workbench"),
                ("workbench npm audit high", npm_audit_command(), ROOT / "workbench"),
            ]
            for name, command, cwd in frontend_checks:
                results["commands"].append(run_command(name, command, cwd))

            generated_data = copy_workbench_data_for_build(output_dir / "fullstack-enterprise", ROOT / "workbench")
            results["actions"].append(generated_data)

            for name, command, cwd in [
                ("workbench build with generated data", ["npm", "run", "build"], ROOT / "workbench"),
                # tests/svn-platform/package-lock.json is deliberately not required/tracked;
                # use npm install for the fixture smoke and keep node_modules untracked.
                ("svn-platform npm install", ["npm", "install", "--no-audit", "--no-fund"], ROOT / "tests" / "svn-platform"),
                ("svn-platform npm audit high", npm_audit_command(), ROOT / "tests" / "svn-platform"),
                ("svn-platform frontend build", ["npm", "run", "build"], ROOT / "tests" / "svn-platform"),
            ]:
                results["commands"].append(run_command(name, command, cwd))

        results["status"] = "passed"
        print("\nRelease quality gate: PASS")
        return 0
    except GateFailure as exc:
        results["status"] = "failed"
        results["error"] = str(exc)
        print(f"\nRelease quality gate: FAIL — {exc}", file=sys.stderr)
        return 1
    finally:
        report_path = output_dir / "release-quality-gate-report.json"
        results["report"] = str(report_path)
        report_path.write_text(json.dumps(results, ensure_ascii=False, indent=2), encoding="utf-8")
        print(json.dumps({"status": results.get("status", "failed"), "report": str(report_path)}, ensure_ascii=False, indent=2))


def npm_audit_command() -> list[str]:
    """Return a reproducible npm audit command independent of local registry mirrors."""
    return ["npm", "audit", "--registry", NPM_AUDIT_REGISTRY, "--audit-level=high", "--omit=dev"]


def run_command(name: str, command: list[str], cwd: Path) -> dict[str, Any]:
    print(f"\n==> {name}")
    print(f"$ {' '.join(command)}")
    completed = subprocess.run(command, cwd=cwd, text=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    if completed.stdout:
        print(completed.stdout, end="" if completed.stdout.endswith("\n") else "\n")
    result = {
        "name": name,
        "command": " ".join(command),
        "cwd": str(cwd.relative_to(ROOT) if cwd != ROOT else "."),
        "returncode": completed.returncode,
        "status": "passed" if completed.returncode == 0 else "failed",
        "outputTail": "\n".join(completed.stdout.splitlines()[-80:]),
    }
    if completed.returncode != 0:
        raise GateFailure(f"{name} failed with exit code {completed.returncode}")
    return result


def copy_workbench_data_for_build(product_output_dir: Path, workbench_dir: Path) -> dict[str, Any]:
    """Build Workbench against generated v1 data, not only its TS source."""
    source = product_output_dir / "workbench-data.json"
    if not source.exists():
        raise GateFailure(f"missing generated Workbench data: {source}")
    target = workbench_dir / "public" / "workbench-data.json"
    target.parent.mkdir(parents=True, exist_ok=True)
    shutil.copyfile(source, target)
    payload = json.loads(target.read_text(encoding="utf-8"))
    schema = payload.get("schema", {}) if isinstance(payload, dict) else {}
    if schema.get("source") != "domain-read-model.json":
        raise GateFailure("generated Workbench data is not derived from domain-read-model.json")
    print(f"Prepared generated Workbench data: {source} -> {target}")
    return {
        "name": "copy generated workbench-data.json",
        "status": "passed",
        "source": str(source),
        "target": str(target),
        "schema": schema,
    }


def validate_build_artifacts(
    output_dir: Path,
    *,
    expected_product: str | None = None,
    allow_warning: bool = False,
    require_field_rules: bool = False,
) -> dict[str, Any]:
    report_path = output_dir / "build-report.json"
    read_model_path = output_dir / "domain-read-model.json"
    workbench_path = output_dir / "workbench-data.json"
    errors: list[str] = []
    warnings: list[str] = []

    for path in (report_path, read_model_path, workbench_path):
        if not path.exists():
            errors.append(f"missing artifact: {path.name}")
    if errors:
        return {"status": "failed", "errors": errors, "warnings": warnings}

    report = _read_json(report_path)
    read_model = _read_json(read_model_path)
    workbench = _read_json(workbench_path)

    build_status = report.get("build", {}).get("status")
    reported_product_quality = report.get("productQuality") or {}
    model_product_quality = read_model.get("quality") or {}
    product_quality = model_product_quality if model_product_quality else reported_product_quality
    product_status = product_quality.get("deepReadingStatus")
    quality = report.get("quality") or {}
    phase_statuses = {
        "phase3": (report.get("phase3") or quality.get("phase3") or {}).get("acceptance", {}).get("status")
        or (report.get("phase3") or quality.get("phase3") or {}).get("ontology_status"),
        "phase4": (report.get("phase4") or quality.get("phase4") or {}).get("acceptance", {}).get("status")
        or (report.get("phase4") or quality.get("phase4") or {}).get("impact_status"),
        "phase5": (report.get("phase5") or quality.get("phase5") or {}).get("dream_cycle_status"),
    }

    if build_status != "passed":
        errors.append(f"build.status must be passed, got {build_status!r}")
    if not reported_product_quality:
        errors.append("productQuality is missing; build.status alone is not a release signal")
    if not model_product_quality:
        errors.append("domain-read-model quality is missing; productQuality must be derived from domain-read-model.json")
    elif reported_product_quality and reported_product_quality != model_product_quality:
        errors.append("productQuality must match domain-read-model.json quality")
    if expected_product and product_status != expected_product:
        errors.append(f"productQuality.deepReadingStatus must be {expected_product!r}, got {product_status!r}")
    elif not expected_product and product_status == "failed":
        errors.append("productQuality.deepReadingStatus is failed")
    if not allow_warning and expected_product == "passed" and product_status != "passed":
        errors.append(f"productQuality.deepReadingStatus must be passed, got {product_status!r}")
    if product_quality.get("coreDomainEvidenceStatus") == "failed":
        errors.append("productQuality.coreDomainEvidenceStatus is failed")
    if read_model.get("schema", {}).get("version") != "domain-read-model-v1":
        errors.append("domain-read-model schema is not domain-read-model-v1")
    if workbench.get("schema", {}).get("source") != "domain-read-model.json":
        errors.append("workbench-data is not derived from domain-read-model.json")

    core_domains = [domain for domain in read_model.get("domains", []) if domain.get("core")]
    if not core_domains:
        errors.append("no core domain found")
    evidence_index = read_model.get("evidenceIndex") or {}
    field_rule_count = 0
    for domain in core_domains:
        domain_key = domain.get("domainKey")
        if not domain.get("flows"):
            errors.append(f"{domain_key}: no flows")
        if not domain.get("rules"):
            errors.append(f"{domain_key}: no rules")
        field_rule_count += len(domain.get("fieldRules") or [])
        for container_name in ("flows", "rules", "fieldRules"):
            for item in domain.get(container_name) or []:
                refs = item.get("evidenceRefs") or []
                if not refs:
                    errors.append(f"{domain_key} {container_name}: missing evidenceRefs")
                missing = [ref for ref in refs if ref not in evidence_index]
                if missing:
                    errors.append(f"{domain_key} {container_name}: unresolved evidenceRefs {missing[:3]}")
    if require_field_rules and field_rule_count == 0:
        errors.append("expected at least one fieldRule")

    for name, status in phase_statuses.items():
        if not status:
            warnings.append(f"{name} status not found; keep phase gates separate from build.status")

    result = {
        "status": "failed" if errors else "passed",
        "buildStatus": build_status,
        "productQuality": product_quality,
        "reportedProductQuality": reported_product_quality,
        "phaseStatuses": phase_statuses,
        "errors": errors,
        "warnings": warnings,
    }
    print(
        f"{output_dir.name}: build.status={build_status}, "
        f"productQuality.deepReadingStatus={product_status}, "
        f"phase3={phase_statuses.get('phase3')}, phase4={phase_statuses.get('phase4')}, phase5={phase_statuses.get('phase5')}"
    )
    return result


def _raise_for_artifact_failures(artifact_checks: dict[str, dict[str, Any]]) -> None:
    failed = {name: check for name, check in artifact_checks.items() if check.get("status") != "passed"}
    if failed:
        raise GateFailure(json.dumps(failed, ensure_ascii=False))


def _read_json(path: Path) -> dict[str, Any]:
    if not path.exists():
        raise GateFailure(f"missing artifact: {path}")
    return json.loads(path.read_text(encoding="utf-8"))


if __name__ == "__main__":
    raise SystemExit(main())
