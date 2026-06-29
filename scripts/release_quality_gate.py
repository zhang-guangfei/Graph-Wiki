#!/usr/bin/env python3
"""Run the local/CI release quality gate for Domain Read Model v1.

This gate intentionally separates pipeline execution success (`build.status`) from
product readability (`productQuality.*`). A green build is not sufficient when
the Domain Read Model cannot support flow -> rules -> evidence reading.
"""

from __future__ import annotations

import argparse
import json
import shutil
import subprocess
import sys
from pathlib import Path
from typing import Iterable

ROOT = Path(__file__).resolve().parents[1]
DEFAULT_OUTPUT = ROOT / "output" / "release-quality-gate"


class GateFailure(RuntimeError):
    """Raised when a release-gate command or artifact assertion fails."""


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description="Run Graph-Wiki release quality gate")
    parser.add_argument("--output-dir", type=Path, default=DEFAULT_OUTPUT, help="Directory for smoke build outputs")
    parser.add_argument(
        "--skip-frontend-installs",
        action="store_true",
        help="Skip npm install/build checks; intended only for offline Python-only diagnosis.",
    )
    args = parser.parse_args(argv)

    output_dir = args.output_dir.resolve()
    if output_dir.exists():
        shutil.rmtree(output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)

    checks: list[tuple[str, list[str], Path]] = [
        ("pytest", [sys.executable, "-m", "pytest", "-q"], ROOT),
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

    if not args.skip_frontend_installs:
        checks.extend(
            [
                ("workbench npm ci", ["npm", "ci"], ROOT / "workbench"),
                ("workbench build", ["npm", "run", "build"], ROOT / "workbench"),
                # tests/svn-platform/package-lock.json is deliberately not required/tracked;
                # use npm install for the fixture smoke and keep node_modules untracked.
                ("svn-platform npm install", ["npm", "install", "--no-audit", "--no-fund"], ROOT / "tests" / "svn-platform"),
                ("svn-platform frontend build", ["npm", "run", "build"], ROOT / "tests" / "svn-platform"),
            ]
        )

    for name, command, cwd in checks:
        _run(name, command, cwd)

    _assert_product_gate(output_dir / "fullstack-enterprise", expected_product="passed")
    _assert_product_gate(output_dir / "svn-platform", allow_warning=True)
    print("\nRelease quality gate: PASS")
    return 0


def _run(name: str, command: list[str], cwd: Path) -> None:
    print(f"\n==> {name}")
    print(f"$ {' '.join(command)}")
    completed = subprocess.run(command, cwd=cwd)
    if completed.returncode != 0:
        raise GateFailure(f"{name} failed with exit code {completed.returncode}")


def _assert_product_gate(output_dir: Path, *, expected_product: str | None = None, allow_warning: bool = False) -> None:
    report = _read_json(output_dir / "build-report.json")
    model = _read_json(output_dir / "domain-read-model.json")
    workbench = _read_json(output_dir / "workbench-data.json")

    build_status = report.get("build", {}).get("status")
    product_status = report.get("productQuality", {}).get("deepReadingStatus")
    artifact_status = report.get("build", {}).get("artifactStatus")

    if build_status != "passed":
        raise GateFailure(f"{output_dir.name}: build.status={build_status!r}, expected 'passed'")
    if expected_product and product_status != expected_product:
        raise GateFailure(f"{output_dir.name}: productQuality.deepReadingStatus={product_status!r}, expected {expected_product!r}")
    if not allow_warning and product_status != "passed":
        raise GateFailure(f"{output_dir.name}: product quality is not passed: {product_status!r}")
    if allow_warning and product_status == "failed":
        raise GateFailure(f"{output_dir.name}: product quality failed despite successful build")
    if model.get("schema", {}).get("version") != "domain-read-model-v1":
        raise GateFailure(f"{output_dir.name}: invalid domain-read-model schema")
    if workbench.get("schema", {}).get("source") != "domain-read-model.json":
        raise GateFailure(f"{output_dir.name}: Workbench data is not derived from domain-read-model.json")

    print(
        f"{output_dir.name}: build.status={build_status}, "
        f"artifactStatus={artifact_status}, productQuality.deepReadingStatus={product_status}"
    )


def _read_json(path: Path) -> dict:
    if not path.exists():
        raise GateFailure(f"missing artifact: {path}")
    return json.loads(path.read_text(encoding="utf-8"))


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except GateFailure as exc:
        print(f"\nRelease quality gate: FAIL — {exc}", file=sys.stderr)
        raise SystemExit(1)
