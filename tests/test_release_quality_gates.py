"""Release quality gates for Domain Read Model v1 delivery."""

from __future__ import annotations

import subprocess
from pathlib import Path

import networkx as nx
import pytest

from graph_wiki import pipeline


_FORBIDDEN_TRACKED_PATH_PREFIXES = (
    "tests/svn-platform/node_modules/",
)
_FORBIDDEN_TRACKED_PATHS = {
    "tests/svn-platform/package-lock.json",
}


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


def test_release_gate_rejects_tracked_svn_platform_dependency_artifacts():
    """SVN smoke fixture must stay source-only; npm install output is local state."""
    tracked = _git_ls_files()

    polluted = [
        path
        for path in tracked
        if path in _FORBIDDEN_TRACKED_PATHS
        or any(path.startswith(prefix) for prefix in _FORBIDDEN_TRACKED_PATH_PREFIXES)
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
