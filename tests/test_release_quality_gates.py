"""Release quality gates for Domain Read Model v1 delivery."""

from __future__ import annotations

import json
import subprocess
from pathlib import Path

import networkx as nx
import pytest

from graph_wiki import pipeline
from graph_wiki.product_data import ProductDataService


_FORBIDDEN_TRACKED_PATH_PREFIXES = (
    "tests/svn-platform/node_modules/",
    "workbench/node_modules/",
    "workbench/dist/",
    "output/",
)
_FORBIDDEN_TRACKED_PATHS = {
    "tests/svn-platform/package-lock.json",
    "workbench/public/workbench-data.json",
    "api-map.json",
    "build-report.json",
    "domains.json",
    "field-map.json",
    "graph-lite.json",
    "manifest.json",
    "domain_graph.html",
    "domain-read-model.json",
    "workbench-data.json",
}
_FORBIDDEN_TRACKED_PATH_SUFFIXES = (
    "/graphify-out/cache/",
    "/graphify-out/",
    "/output/",
    "/.pytest_cache/",
    "/__pycache__/",
)


def _git_ls_files() -> list[str]:
    result = subprocess.run(
        ["git", "ls-files"],
        cwd=Path(__file__).resolve().parents[1],
        check=False,
        text=True,
        capture_output=True,
    )
    if result.returncode != 0:
        pytest.skip(f"git ls-files unavailable: {result.stderr.strip()}")
    return [line.strip() for line in result.stdout.splitlines() if line.strip()]


def _write_json(base: Path, name: str, payload: dict) -> None:
    (base / name).write_text(json.dumps(payload, ensure_ascii=False), encoding="utf-8")


def test_release_gate_rejects_tracked_generated_or_dependency_artifacts():
    """Release fixtures must stay source-only; local installs/build outputs are not source."""
    tracked = _git_ls_files()

    polluted = [
        path
        for path in tracked
        if path in _FORBIDDEN_TRACKED_PATHS
        or any(path.startswith(prefix) for prefix in _FORBIDDEN_TRACKED_PATH_PREFIXES)
        or any(suffix in f"/{path}/" for suffix in _FORBIDDEN_TRACKED_PATH_SUFFIXES)
    ]

    assert polluted == []


def test_release_gate_keeps_build_status_separate_from_product_quality(tmp_path: Path):
    """A successful pipeline run cannot hide a failed deep-reading product gate."""
    report = pipeline._write_build_report(
        tmp_path / "build-report.json",
        root=tmp_path,
        corpus={"total_files": 1, "files": {"code": [str(tmp_path / "Order.java")]}},
        filtered_extraction={"nodes": [], "edges": [], "meta": {}},
        graph=nx.Graph(),
        domains=[],
        api_matches=[],
        field_map={},
        ontology={},
        impact_analysis={},
        dream_report={},
        domain_read_model={
            "schema": {"version": "domain-read-model-v1"},
            "quality": {
                "deepReadingStatus": "failed",
                "coreDomainEvidenceStatus": "failed",
                "ruleCorrectnessRisk": "high",
                "warnings": [],
                "errors": ["没有 core=true 的业务域"],
            },
        },
        timings={"total_seconds": 0.01},
    )

    assert report["build"]["status"] == "passed"
    assert report["productQuality"]["deepReadingStatus"] == "failed"
    assert report["productQuality"]["errors"] == ["没有 core=true 的业务域"]
    assert report["quality"]["phase3"]["acceptance"]["status"] == "failed"
    assert report["quality"]["phase4"]["acceptance"]["status"] == "failed"


def test_release_gate_overview_surfaces_build_product_and_phase_gates_separately(tmp_path: Path):
    """Workbench overview must not collapse build, product quality, and phase gates."""
    _write_json(tmp_path, "build-report.json", {
        "build": {"status": "passed"},
        "project": {"root": "fixture", "total_files": 1, "code_files": 1},
        "scale": {"graph_lite": {"nodes": 0, "edges": 0}},
        "domains": {"count": 0},
        "api": {"total": 0},
        "field_map": {"fields": 0},
        "productQuality": {
            "deepReadingStatus": "failed",
            "coreDomainEvidenceStatus": "failed",
            "ruleCorrectnessRisk": "high",
        },
        "quality": {
            "status": "passed",
            "performance_status": "passed",
            "phase3": {"acceptance": {"status": "failed"}},
            "phase4": {"acceptance": {"status": "warning"}},
            "phase5": {"dream_cycle_status": "passed"},
        },
    })
    _write_json(tmp_path, "manifest.json", {"meta": {"last_full_build": "2026-06-29T00:00:00Z"}})
    _write_json(tmp_path, "domains.json", [])

    quality = ProductDataService(tmp_path).load_project_overview()["quality"]

    assert quality == {
        "build": "passed",
        "deepReading": "failed",
        "coreEvidence": "failed",
        "performance": "passed",
        "phase3": "failed",
        "phase4": "warning",
        "phase5": "passed",
    }


def test_release_gate_failure_report_marks_build_failed_and_product_unreadable(tmp_path: Path):
    report = pipeline._write_failure_report(
        tmp_path / "build-report.json",
        root=tmp_path,
        corpus={"total_files": 0, "files": {"code": []}},
        filtered_extraction={"nodes": [], "edges": [], "meta": {}},
        graph=None,
        domains=[],
        api_matches=[],
        field_map={},
        ontology={},
        impact_analysis={},
        dream_report={},
        timings={"total_seconds": 0.01},
        step="detect_corpus",
        error=RuntimeError("fixture failed"),
    )

    assert report["build"] == {"status": "failed", "failedStep": "detect_corpus"}
    assert report["productQuality"]["deepReadingStatus"] == "failed"
    assert "domain-read-model.json 未生成" in report["productQuality"]["errors"]
    assert report["failure"]["step"] == "detect_corpus"


def test_release_gate_late_failure_preserves_existing_product_quality(tmp_path: Path):
    product_quality = {
        "deepReadingStatus": "passed",
        "coreDomainEvidenceStatus": "passed",
        "ruleCorrectnessRisk": "low",
        "warnings": [],
        "errors": [],
    }

    report = pipeline._write_failure_report(
        tmp_path / "build-report.json",
        root=tmp_path,
        corpus={"total_files": 1, "files": {"code": [str(tmp_path / "Order.java")]}},
        filtered_extraction={"nodes": [], "edges": [], "meta": {}},
        graph=nx.Graph(),
        domains=[],
        api_matches=[],
        field_map={},
        ontology={},
        impact_analysis={},
        dream_report={},
        timings={"total_seconds": 0.01},
        step="workbench_data",
        error=RuntimeError("late failure"),
        domain_read_model={"schema": {"version": "domain-read-model-v1"}, "quality": product_quality},
    )

    assert report["build"]["status"] == "failed"
    assert report["productQuality"] == product_quality
