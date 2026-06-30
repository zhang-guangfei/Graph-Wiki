#!/usr/bin/env python3
"""Prepare generated Graph-Wiki data for the static Workbench demo.

This script copies a generated ``workbench-data.json`` into
``workbench/public/workbench-data.json`` after validating that it is a v1
Workbench bundle derived from ``domain-read-model.json``. The target file is
ignored by git, so demos are reproducible without committing generated data.
"""

from __future__ import annotations

import argparse
import json
import shutil
import sys
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
DEFAULT_WORKBENCH_DIR = ROOT / "workbench"


class DemoDataError(RuntimeError):
    """Raised when generated demo data is missing or not Workbench-v1 safe."""


def prepare_workbench_demo_data(
    product_output_dir: Path,
    workbench_dir: Path = DEFAULT_WORKBENCH_DIR,
    *,
    target: Path | None = None,
) -> dict[str, Any]:
    """Validate and copy generated Workbench data for local demo runtime.

    Args:
        product_output_dir: Directory produced by ``graph_wiki.pipeline build``.
        workbench_dir: Workbench app directory containing ``package.json``.
        target: Optional explicit destination. Defaults to
            ``<workbench_dir>/public/workbench-data.json``.

    Returns:
        A JSON-serializable summary of the prepared demo data.
    """
    product_output_dir = product_output_dir.resolve()
    workbench_dir = workbench_dir.resolve()
    source = product_output_dir / "workbench-data.json"
    read_model = product_output_dir / "domain-read-model.json"
    build_report = product_output_dir / "build-report.json"
    destination = (target or (workbench_dir / "public" / "workbench-data.json")).resolve()

    if not source.exists():
        raise DemoDataError(f"missing generated Workbench data: {source}")
    if not read_model.exists():
        raise DemoDataError(f"missing Domain Read Model beside Workbench data: {read_model}")
    if not build_report.exists():
        raise DemoDataError(f"missing build report beside Workbench data: {build_report}")
    if not (workbench_dir / "package.json").exists():
        raise DemoDataError(f"not a Workbench directory: {workbench_dir}")

    payload = _read_json(source)
    model = _read_json(read_model)
    report = _read_json(build_report)

    schema = payload.get("schema", {}) if isinstance(payload, dict) else {}
    model_schema = model.get("schema", {}) if isinstance(model, dict) else {}
    product_quality = report.get("productQuality", {}) if isinstance(report, dict) else {}
    model_quality = model.get("quality", {}) if isinstance(model, dict) else {}

    if schema.get("version") != "graph-wiki-workbench-v1":
        raise DemoDataError(f"workbench-data schema.version must be graph-wiki-workbench-v1, got {schema.get('version')!r}")
    if schema.get("source") != "domain-read-model.json":
        raise DemoDataError("workbench-data must be derived from domain-read-model.json")
    if model_schema.get("version") != "domain-read-model-v1":
        raise DemoDataError(f"domain-read-model schema.version must be domain-read-model-v1, got {model_schema.get('version')!r}")
    if product_quality and model_quality and product_quality != model_quality:
        raise DemoDataError("build-report.productQuality must match domain-read-model.json quality")

    destination.parent.mkdir(parents=True, exist_ok=True)
    shutil.copyfile(source, destination)

    return {
        "status": "passed",
        "source": str(source),
        "target": str(destination),
        "schema": schema,
        "projectName": payload.get("overview", {}).get("projectName") if isinstance(payload, dict) else None,
        "domainCount": len(payload.get("domains", [])) if isinstance(payload, dict) else 0,
        "buildStatus": report.get("build", {}).get("status") if isinstance(report, dict) else None,
        "productQuality": product_quality or model_quality,
        "nextCommands": [
            "cd workbench",
            "npm ci",
            "npm run dev -- --port 5174",
        ],
    }


def _read_json(path: Path) -> dict[str, Any]:
    try:
        data = json.loads(path.read_text(encoding="utf-8"))
    except json.JSONDecodeError as exc:
        raise DemoDataError(f"invalid JSON in {path}: {exc}") from exc
    if not isinstance(data, dict):
        raise DemoDataError(f"expected JSON object in {path}")
    return data


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description="Prepare generated Graph-Wiki data for Workbench demo runtime")
    parser.add_argument("product_output_dir", type=Path, help="Output directory from graph-wiki build")
    parser.add_argument("--workbench-dir", type=Path, default=DEFAULT_WORKBENCH_DIR, help="Workbench app directory")
    parser.add_argument("--target", type=Path, default=None, help="Override destination workbench-data.json path")
    args = parser.parse_args(argv)

    try:
        result = prepare_workbench_demo_data(args.product_output_dir, args.workbench_dir, target=args.target)
    except DemoDataError as exc:
        print(f"Workbench demo data: FAIL — {exc}", file=sys.stderr)
        return 1

    print(json.dumps(result, ensure_ascii=False, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
