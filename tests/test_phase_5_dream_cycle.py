from graph_wiki.dream import build_dream_cycle_report
from graph_wiki.models import BusinessPoint, Domain


def test_dream_cycle_detects_manifest_changes_and_changed_domains(tmp_path):
    previous_manifest = {
        "files": {
            "src/order/OrderController.java": {"hash": "old1", "domain": "order"},
            "src/order/OrderService.java": {"hash": "same", "domain": "order"},
            "src/legacy/LegacyController.java": {"hash": "gone", "domain": "legacy"},
        },
        "domains": {
            "order": {"anchors_hash": "old-order"},
            "legacy": {"anchors_hash": "old-legacy"},
        },
    }
    current_manifest = {
        "files": {
            "src/order/OrderController.java": {"hash": "new1", "domain": "order"},
            "src/order/OrderService.java": {"hash": "same", "domain": "order"},
            "src/order/OrderDTO.java": {"hash": "new2", "domain": "order"},
        },
        "domains": {
            "order": {"anchors_hash": "new-order"},
        },
    }
    domains = [
        Domain(
            id="order",
            name="order",
            business_points=[BusinessPoint(name="createOrder()", entry_file="src/order/OrderController.java")],
        )
    ]

    report = build_dream_cycle_report(
        wiki_root=tmp_path / "wiki",
        domains=domains,
        previous_manifest=previous_manifest,
        current_manifest=current_manifest,
        impact_analysis={"impacts": {}},
    )

    assert report["schema"]["version"] == "dream-cycle-v0"
    assert report["detect"]["new_files"] == ["src/order/OrderDTO.java"]
    assert report["detect"]["modified_files"] == ["src/order/OrderController.java"]
    assert report["detect"]["deleted_files"] == ["src/legacy/LegacyController.java"]
    assert report["reconcile"]["changed_domains"] == ["order"]
    assert report["reconcile"]["removed_domains"] == ["legacy"]
    assert report["quality"]["status"] == "passed"


def test_dream_cycle_reports_manual_rules_and_spec_preserved(tmp_path):
    wiki_root = tmp_path / "wiki"
    order_dir = wiki_root / "order"
    order_dir.mkdir(parents=True)
    (order_dir / "rules.md").write_text("# Rules\n\nmanual rule", encoding="utf-8")
    (order_dir / "spec.md").write_text("# Spec\n\nmanual spec", encoding="utf-8")
    domains = [Domain(id="order", name="order")]

    report = build_dream_cycle_report(
        wiki_root=wiki_root,
        domains=domains,
        previous_manifest={},
        current_manifest={"files": {}, "domains": {"order": {"anchors_hash": "x"}}},
    )

    preserved = report["preserve"]["manual_files"]
    assert {"domain": "order", "file": "rules.md", "status": "preserved"} in preserved
    assert {"domain": "order", "file": "spec.md", "status": "preserved"} in preserved
    assert report["quality"]["status"] == "passed"


def test_dream_cycle_detects_orphan_and_archived_wiki_domains(tmp_path):
    wiki_root = tmp_path / "wiki"
    (wiki_root / "order").mkdir(parents=True)
    (wiki_root / "legacy").mkdir(parents=True)
    archived = wiki_root / "_stale" / "20260626000000" / "old-domain"
    archived.mkdir(parents=True)
    domains = [Domain(id="order", name="order")]

    report = build_dream_cycle_report(
        wiki_root=wiki_root,
        domains=domains,
        previous_manifest={},
        current_manifest={"files": {}, "domains": {"order": {"anchors_hash": "x"}}},
    )

    assert report["reconcile"]["orphan_wiki_domains"] == ["legacy"]
    assert report["reconcile"]["archived_stale_domains"] == ["old-domain"]


def test_dream_cycle_checks_dependency_backlinks_and_duplicate_business_points(tmp_path):
    wiki_root = tmp_path / "wiki"
    svn_dir = wiki_root / "svn"
    svn_dir.mkdir(parents=True)
    (svn_dir / "dependencies.md").write_text("# svn\n\n- [[repository]]", encoding="utf-8")
    domains = [
        Domain(
            id="svn",
            name="svn",
            dependencies=[{"domain": "repository", "import_count": 0}],
            business_points=[
                BusinessPoint(name="loadRepos()", entry_file="src/views/svn/a.vue"),
                BusinessPoint(name="loadRepos()", entry_file="src/views/svn/b.vue"),
            ],
        ),
        Domain(id="repository", name="repository"),
    ]

    report = build_dream_cycle_report(
        wiki_root=wiki_root,
        domains=domains,
        previous_manifest={},
        current_manifest={"files": {}, "domains": {"svn": {"anchors_hash": "x"}, "repository": {"anchors_hash": "y"}}},
    )

    assert report["backlink"]["status"] == "passed"
    assert report["backlink"]["missing"] == []
    assert report["synthesize"]["duplicate_business_points"] == [
        {"domain": "svn", "business_point": "loadRepos()", "count": 2}
    ]


def test_cmd_build_writes_dream_cycle_report_and_changelog(tmp_path, monkeypatch):
    from types import SimpleNamespace

    from graph_wiki import pipeline

    project = tmp_path / "project"
    source_file = project / "src" / "OrderController.java"
    source_file.parent.mkdir(parents=True)
    source_file.write_text("class OrderController {}", encoding="utf-8")
    output_dir = tmp_path / "output"
    old_wiki = output_dir / "wiki" / "order"
    old_wiki.mkdir(parents=True)
    (old_wiki / "rules.md").write_text("# Rules\n\nmanual", encoding="utf-8")
    (old_wiki / "spec.md").write_text("# Spec\n\nmanual", encoding="utf-8")
    (output_dir / "manifest.json").write_text('{"files": {}, "domains": {}}', encoding="utf-8")

    domain = Domain(
        id="order",
        name="order",
        anchors={"controller": [{"id": "OrderController", "source_file": str(source_file)}]},
        business_points=[BusinessPoint(name="OrderController.java", entry_file=str(source_file))],
    )
    monkeypatch.setattr(pipeline, "detect_corpus", lambda root: {
        "total_files": 1,
        "total_words": 10,
        "files": {"code": [str(source_file)]},
    })
    monkeypatch.setattr(pipeline, "extract_ast", lambda files: {
        "nodes": [{"id": "n1", "label": "OrderController.java", "file_type": "code", "source_file": str(source_file)}],
        "edges": [],
    })
    monkeypatch.setattr(pipeline, "business_cluster", lambda graph, root, language=None: [domain])
    monkeypatch.setattr(pipeline, "build_api_map", lambda *args, **kwargs: [])
    monkeypatch.setattr(pipeline, "build_field_map", lambda *args, **kwargs: {})

    args = SimpleNamespace(
        path=project,
        name=None,
        language="java",
        llm_backend="claude",
        no_llm=True,
        output_dir=output_dir,
    )
    pipeline._cmd_build(args)

    report = pipeline._read_json(output_dir / "dream-cycle-report.json")
    build_report = pipeline._read_json(output_dir / "build-report.json")
    assert report["schema"]["version"] == "dream-cycle-v0"
    assert (output_dir / "wiki" / "changelog.md").exists()
    assert "Dream Cycle" in (output_dir / "wiki" / "changelog.md").read_text(encoding="utf-8")
    assert report["preserve"]["manual_files"]
    assert build_report["quality"]["phase5"]["dream_cycle_status"] in {"passed", "warning"}
