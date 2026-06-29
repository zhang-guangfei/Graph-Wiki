import json
from pathlib import Path

from graph_wiki.product_data import ProductDataService


def _write_json(base: Path, name: str, data) -> None:
    (base / name).write_text(json.dumps(data, ensure_ascii=False, indent=2), encoding="utf-8")


def _sample_output(base: Path) -> Path:
    (base / "wiki" / "svn").mkdir(parents=True)
    (base / "wiki" / "svn" / "rules.md").write_text("# svn rules\n\n*待填写*\n", encoding="utf-8")
    (base / "wiki" / "svn" / "spec.md").write_text("# svn spec\n\n业务规格。\n", encoding="utf-8")
    (base / "wiki" / "svn" / "summary.md").write_text("# svn summary\n", encoding="utf-8")
    (base / "wiki" / "svn" / "code-map.md").write_text("# code map\n", encoding="utf-8")

    _write_json(base, "build-report.json", {
        "project": {"root": "tests/fixtures/svn-platform-full", "total_files": 3, "code_files": 2},
        "scale": {"graph_lite": {"nodes": 12, "edges": 20}},
        "domains": {"count": 1},
        "api": {"total": 1},
        "field_map": {"fields": 1},
        "quality": {
            "status": "passed",
            "performance_status": "passed",
            "phase3": {"acceptance": {"status": "passed"}},
            "phase4": {"acceptance": {"status": "passed"}},
            "phase5": {"dream_cycle_status": "passed"},
        },
    })
    _write_json(base, "domains.json", [{
        "id": "svn",
        "display_name": "",
        "description": "",
        "anchors": {
            "controller": [{
                "label": "SvnOperationController.java",
                "source_file": "backend/SvnOperationController.java",
            }]
        },
        "business_points": [
            {
                "name": "handleExecuteMerge()",
                "entry_file": "frontend/merge.vue",
                "point_type": "core_action",
            },
            {
                "name": "formatRevisionList()",
                "entry_file": "frontend/merge.vue",
                "point_type": "helper",
            },
        ],
        "dependencies": [{"domain": "repository", "strength": "weak"}],
    }])
    _write_json(base, "api-map.json", [{
        "id": "api-svnmerge",
        "frontend": {
            "function_name": "svnMerge",
            "http_method": "POST",
            "url": "/svn/${repoId}/merge",
            "source_file": "frontend/api.js",
            "callers": [{"page": "views/svn/merge.vue"}],
        },
        "backend": {
            "controller_file": "backend/SvnOperationController.java",
            "controller_class": "SvnOperationController",
            "method_name": "merge",
            "param_type": "MergeRequest",
            "service_chain": ["svnOperationService.merge()"],
        },
        "match_confidence": 0.95,
        "domain": "svn",
    }])
    _write_json(base, "field-map.json", {
        "svn": {
            "repository": {
                "name": [{
                    "api_url": "/svn/${repoId}/merge",
                    "api_function": "svnMerge",
                    "dto_class": "MergeRequest",
                    "dto_field": "sourceBranch",
                    "entity_class": "Repository",
                    "entity_field": "name",
                    "db_table": "repository",
                    "db_column": "name",
                    "callers": ["views/svn/merge.vue"],
                    "confidence": 1.0,
                    "confidence_level": "high",
                    "is_reliable": True,
                }]
            }
        }
    })
    _write_json(base, "impact-analysis.json", {
        "query_examples": [{
            "type": "api_change",
            "question": "如果接口 POST /svn/${repoId}/merge 变化，会影响什么？",
            "answer": "影响合并页面和后端 merge。",
            "evidence": {"wiki_page": "wiki/svn/api-docs.md"},
        }],
        "coverage": {"api_impacts": 1, "field_impacts": 1, "business_point_impacts": 2},
        "acceptance": {"status": "passed"},
    })
    _write_json(base, "dream-cycle-report.json", {
        "schema": {"version": "dream-cycle-v0"},
        "detect": {"new_files": [], "modified_files": [], "deleted_files": [], "unchanged_files": ["a.java"]},
        "reconcile": {"changed_domains": [], "orphan_wiki_domains": [], "archived_stale_domains": []},
        "preserve": {"manual_files": [{"path": "wiki/svn/rules.md"}, {"path": "wiki/svn/spec.md"}]},
        "backlink": {"status": "passed", "missing": []},
        "synthesize": {"duplicate_business_points": []},
        "quality": {"status": "passed"},
    })
    _write_json(base, "manifest.json", {"meta": {"last_full_build": "2026-06-26T00:00:00"}, "files": {"a.java": {}}})
    return base


def test_product_data_service_builds_project_overview(tmp_path):
    service = ProductDataService(_sample_output(tmp_path))

    overview = service.load_project_overview()

    assert overview["projectName"] == "SVN Platform"
    assert overview["quality"]["build"] == "passed"
    assert overview["scale"]["apis"] == 1
    assert overview["domains"][0]["domainKey"] == "svn"
    assert overview["latestDreamCycle"]["status"] == "passed"


def test_product_data_service_builds_domain_detail_with_grouped_business_points(tmp_path):
    service = ProductDataService(_sample_output(tmp_path))

    detail = service.get_domain_detail("svn")

    assert detail["domainKey"] == "svn"
    assert detail["fieldFlows"]["status"] == "ready"
    assert detail["businessPoints"]["coreActions"][0]["businessTitle"] == "执行 SVN 合并"
    assert detail["businessPoints"]["helpers"][0]["codeName"] == "formatRevisionList()"
    assert detail["rules"]["status"] == "placeholder"
    assert detail["spec"]["status"] == "ready"


def test_product_data_service_lists_apis_fields_impact_and_search(tmp_path):
    service = ProductDataService(_sample_output(tmp_path))

    apis = service.list_apis()
    fields = service.list_fields()
    impact = service.get_impact_summary()
    results = service.search("merge")

    assert apis[0]["businessUse"] == "执行 SVN 合并"
    assert apis[0]["backend"]["controller"] == "SvnOperationController.java"
    assert fields[0]["fieldId"] == "repository.name"
    assert impact["queryExamples"][0]["riskLevel"] == "medium"
    assert any(item["type"] == "api" for item in results)
    assert any(item["type"] == "business_point" for item in results)


def test_product_data_service_exports_workbench_data_bundle(tmp_path):
    service = ProductDataService(_sample_output(tmp_path))

    bundle = service.export_workbench_data()

    assert bundle["schema"]["version"] == "graph-wiki-workbench-v0"
    assert bundle["overview"]["projectName"] == "SVN Platform"
    assert bundle["domains"][0]["domainKey"] == "svn"
    assert bundle["domainDetails"]["svn"]["businessPoints"]["coreActions"]
    assert bundle["apis"][0]["url"] == "/svn/${repoId}/merge"
    assert bundle["impact"]["status"] == "passed"
    assert bundle["dreamCycle"]["status"] == "passed"
