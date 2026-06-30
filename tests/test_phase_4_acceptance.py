from types import SimpleNamespace

from graph_wiki import pipeline
from graph_wiki.export import export_wiki
from graph_wiki.impact import build_impact_analysis, evaluate_phase4_acceptance
from graph_wiki.models import ApiMatch, BackendEndpoint, BusinessPoint, Domain, FrontendApiCall


def _sample_domain():
    return Domain(
        id="order",
        name="order",
        display_name="订单管理",
        description="订单管理负责创建、提交和查询订单。",
        anchors={
            "controller": [{"id": "OrderController", "label": "OrderController.java", "source_file": "src/OrderController.java"}],
            "service_impl": [{"id": "OrderServiceImpl", "label": "OrderServiceImpl.java", "source_file": "src/OrderServiceImpl.java"}],
        },
        business_points=[
            BusinessPoint(
                name="createOrder()",
                point_type="core_action",
                entry_file="src/views/order/index.vue",
                cross_domain_calls={"inventory": ["reserveStock()"]},
            )
        ],
        dependencies=[{"domain": "inventory", "import_count": 3}],
    )


def _sample_api():
    return ApiMatch(
        id="api-create-order",
        frontend=FrontendApiCall(
            function_name="createOrder",
            http_method="POST",
            url="/order/create",
            source_file="src/api/order.js",
            callers=[{"page": "views/order/index.vue"}],
        ),
        backend=BackendEndpoint(
            controller_file="src/OrderController.java",
            controller_class="OrderController",
            method_name="createOrder",
            param_type="OrderRequest",
            param_fields=[{"name": "orderNo", "type": "String"}],
            service_chain=["orderService.createOrder()"],
            return_type="OrderResponse",
        ),
        domain="order",
        match_confidence=0.95,
    )


def _other_sample_api():
    return ApiMatch(
        id="api-cancel-order",
        frontend=FrontendApiCall(
            function_name="cancelOrder",
            http_method="POST",
            url="/order/cancel",
            source_file="src/api/order.js",
            callers=[{"page": "views/order/cancel.vue"}],
        ),
        backend=BackendEndpoint(
            controller_file="src/OrderController.java",
            controller_class="OrderController",
            method_name="cancelOrder",
            param_type="CancelOrderRequest",
            service_chain=["orderService.cancelOrder()"],
            return_type="OrderResponse",
        ),
        domain="order",
        match_confidence=0.95,
    )


def _sample_field_map():
    return {
        "order": {
            "orders": {
                "order_no": [
                    {
                        "api_url": "/order/create",
                        "api_function": "createOrder",
                        "dto_class": "OrderRequest",
                        "dto_field": "orderNo",
                        "entity_class": "OrderEntity",
                        "entity_field": "orderNo",
                        "db_column": "order_no",
                        "db_table": "orders",
                        "callers": ["views/order/index.vue"],
                        "confidence": 1.0,
                        "confidence_level": "high",
                        "is_reliable": True,
                    }
                ]
            }
        }
    }


def test_phase4_impact_analysis_covers_five_board_questions(tmp_path):
    wiki_root = tmp_path / "wiki"
    domain_dir = wiki_root / "order"
    domain_dir.mkdir(parents=True)
    (domain_dir / "summary.md").write_text("# order\n", encoding="utf-8")
    (domain_dir / "rules.md").write_text("# order rules\n\n- 订单号必须唯一\n", encoding="utf-8")
    (domain_dir / "spec.md").write_text("# order spec\n\n- 支持订单创建\n", encoding="utf-8")
    ontology = {
        "schema": {"version": "code-ontology-v0"},
        "relationships": [
            {"type": "maps_to", "source": "api:createOrder:/order/create", "target": "field:orders.order_no"},
            {"type": "depends_on", "source": "domain:order", "target": "domain:inventory"},
        ],
        "agent_scope": {"domains": {"order": {"entry_files": ["src/OrderController.java"]}}},
    }

    impact = build_impact_analysis(
        domains=[_sample_domain()],
        api_matches=[_sample_api()],
        field_map=_sample_field_map(),
        ontology=ontology,
        wiki_root=wiki_root,
    )

    assert impact["schema"]["version"] == "phase4-impact-v0"
    assert {item["type"] for item in impact["query_examples"]} == {
        "field_change",
        "api_change",
        "business_point_change",
        "rule_trace",
        "domain_dependency",
    }
    assert impact["impacts"]["fields"][0]["evidence"]["wiki_page"].endswith("order/data-flow.md")
    assert impact["impacts"]["fields"][0]["affected_business_points"] == ["createOrder()"]
    assert impact["impacts"]["apis"][0]["source_paths"]["frontend"] == "src/api/order.js"
    assert impact["impacts"]["business_points"][0]["cross_domain_calls"] == {"inventory": ["reserveStock()"]}
    assert impact["impacts"]["rules"][0]["rules_page"].endswith("order/rules.md")
    assert impact["impacts"]["rules"][0]["code_carriers"] == [
        {"role": "controller", "label": "OrderController.java", "source_file": "src/OrderController.java"},
        {"role": "service_impl", "label": "OrderServiceImpl.java", "source_file": "src/OrderServiceImpl.java"},
    ]
    assert impact["impacts"]["domain_dependencies"][0]["target_domain"] == "inventory"

    audit = evaluate_phase4_acceptance(impact)
    assert audit["status"] == "passed"
    assert all(item["status"] == "passed" for item in audit["items"])


def test_cmd_build_writes_phase4_impact_artifacts(tmp_path, monkeypatch):
    project = tmp_path / "project"
    source_file = project / "src" / "api" / "order.js"
    source_file.parent.mkdir(parents=True)
    source_file.write_text("export function createOrder() {}", encoding="utf-8")
    domain = _sample_domain()
    api = _sample_api()
    field_map = _sample_field_map()

    monkeypatch.chdir(tmp_path)
    monkeypatch.setattr(pipeline, "detect_corpus", lambda root: {
        "total_files": 1,
        "total_words": 10,
        "files": {"code": [str(source_file)]},
    })
    monkeypatch.setattr(pipeline, "extract_ast", lambda files: {
        "nodes": [{"id": "order_file", "label": "order.js", "file_type": "code", "source_file": str(source_file)}],
        "edges": [],
    })
    monkeypatch.setattr(pipeline, "business_cluster", lambda graph, root, language=None: [domain])
    monkeypatch.setattr(pipeline, "build_api_map", lambda *args, **kwargs: [api])
    monkeypatch.setattr(pipeline, "build_field_map", lambda *args, **kwargs: field_map)

    output_dir = tmp_path / "phase4-output"
    args = SimpleNamespace(
        path=project,
        name=None,
        language="javascript",
        llm_backend="claude",
        no_llm=True,
        output_dir=output_dir,
    )

    pipeline._cmd_build(args)

    report = pipeline._read_json(output_dir / "build-report.json")
    impact = pipeline._read_json(output_dir / "impact-analysis.json")
    assert impact["schema"]["version"] == "phase4-impact-v0"
    assert (output_dir / "wiki" / "impact-analysis.md").exists()
    assert report["phase4"]["acceptance"]["status"] == "passed"


def test_phase4_derives_domain_dependency_from_cross_domain_field_usage(tmp_path):
    wiki_root = tmp_path / "wiki"
    (wiki_root / "svn").mkdir(parents=True)
    (wiki_root / "repository").mkdir(parents=True)
    domains = [
        Domain(id="svn", name="svn", anchors={"controller": [{"id": "SvnOperationController"}]}, business_points=[BusinessPoint(name="createBranchOrTag()", point_type="core_action")]),
        Domain(id="repository", name="repository", anchors={"controller": [{"id": "RepoController"}]}, business_points=[BusinessPoint(name="importRepo()", point_type="core_action")]),
    ]
    field_map = {
        "svn": {
            "repository": {
                "name": [
                    {
                        "api_url": "/svn/1/branch-tag/create",
                        "api_function": "createBranchOrTag",
                        "dto_class": "BranchTagRequest",
                        "dto_field": "name",
                        "entity_class": "Repository",
                        "entity_field": "name",
                        "db_column": "name",
                        "db_table": "repository",
                        "confidence": 1.0,
                        "is_reliable": True,
                    }
                ]
            }
        }
    }

    impact = build_impact_analysis(domains, [], field_map, {"relationships": []}, wiki_root)

    deps = impact["impacts"]["domain_dependencies"]
    assert deps[0]["source_domain"] == "svn"
    assert deps[0]["target_domain"] == "repository"
    assert deps[0]["evidence"]["source"] == "field_map.cross_domain_table"
    assert any(item["type"] == "domain_dependency" for item in impact["query_examples"])


def test_phase4_field_impacts_link_fields_to_business_points(tmp_path):
    wiki_root = tmp_path / "wiki"
    (wiki_root / "order").mkdir(parents=True)

    impact = build_impact_analysis(
        domains=[_sample_domain()],
        api_matches=[_sample_api()],
        field_map=_sample_field_map(),
        ontology={"relationships": []},
        wiki_root=wiki_root,
    )

    field = impact["impacts"]["fields"][0]
    assert field["table"] == "orders"
    assert field["column"] == "order_no"
    assert field["affected_business_points"] == ["createOrder()"]
    assert field["affected_pages"] == ["views/order/index.vue"]


def test_phase4_export_dependencies_uses_structured_cross_domain_dependencies(tmp_path):
    domains = [
        Domain(id="svn", name="svn", anchors={"controller": [{"id": "SvnController"}]}, business_points=[BusinessPoint(name="createBranchOrTag()", entry_file="src/views/svn/branch-tag.vue")]),
        Domain(id="repository", name="repository", anchors={"controller": [{"id": "RepoController"}]}, business_points=[BusinessPoint(name="importRepo()", entry_file="src/views/repository/list.vue")]),
    ]
    field_map = {
        "svn": {
            "repository": {
                "name": [
                    {
                        "api_url": "/svn/1/branch-tag/create",
                        "api_function": "createBranchOrTag",
                        "dto_class": "BranchTagRequest",
                        "dto_field": "name",
                        "entity_class": "Repository",
                        "entity_field": "name",
                        "db_column": "name",
                        "db_table": "repository",
                        "callers": ["views/svn/branch-tag.vue"],
                        "confidence": 1.0,
                        "is_reliable": True,
                    }
                ]
            }
        }
    }

    export_wiki(domains, [], field_map, tmp_path / "wiki", ontology={"relationships": []})

    dependencies = (tmp_path / "wiki" / "svn" / "dependencies.md").read_text(encoding="utf-8")
    assert "[[repository]]" in dependencies
    assert "field_map.cross_domain_table" in dependencies
    assert "*无跨域依赖*" not in dependencies


def test_phase4_rule_spec_impacts_include_code_carriers(tmp_path):
    wiki_root = tmp_path / "wiki"
    (wiki_root / "order").mkdir(parents=True)

    impact = build_impact_analysis(
        domains=[_sample_domain()],
        api_matches=[_sample_api()],
        field_map=_sample_field_map(),
        ontology={"relationships": []},
        wiki_root=wiki_root,
    )

    rule = impact["impacts"]["rules"][0]
    assert rule["code_carriers"] == [
        {"role": "controller", "label": "OrderController.java", "source_file": "src/OrderController.java"},
        {"role": "service_impl", "label": "OrderServiceImpl.java", "source_file": "src/OrderServiceImpl.java"},
    ]
    assert rule["code_carrier_status"] == "ready"
    assert rule["evidence"]["code_map"].endswith("order/code-map.md")


def test_phase4_single_domain_records_no_external_dependency_as_evidence(tmp_path):
    wiki_root = tmp_path / "wiki"
    (wiki_root / "order").mkdir(parents=True)

    domain = _sample_domain()
    domain.dependencies = []
    domain.business_points[0].cross_domain_calls = {}

    impact = build_impact_analysis(
        domains=[domain],
        api_matches=[_sample_api()],
        field_map=_sample_field_map(),
        ontology={"relationships": []},
        wiki_root=wiki_root,
    )

    dependency = impact["impacts"]["domain_dependencies"][0]
    assert dependency["source_domain"] == "order"
    assert dependency["target_domain"] == "无外部域依赖"
    assert dependency["dependency_status"] == "none_detected"
    assert dependency["evidence"]["wiki_page"].endswith("order/dependencies.md")
    assert "domain_dependency" in {item["type"] for item in impact["query_examples"]}
    assert evaluate_phase4_acceptance(impact)["status"] == "passed"


def test_phase4_field_impacts_fall_back_to_page_anchor_when_page_has_only_helpers(tmp_path):
    wiki_root = tmp_path / "wiki"
    (wiki_root / "svn").mkdir(parents=True)
    domain = Domain(
        id="svn",
        name="svn",
        anchors={
            "controller": [
                {
                    "id": "svn_branch_tag",
                    "label": "branch-tag.vue",
                    "source_file": "src/views/svn/branch-tag.vue",
                }
            ]
        },
        business_points=[
            BusinessPoint(
                name="validate()",
                point_type="helper",
                entry_file="src/views/svn/branch-tag.vue",
            )
        ],
    )
    field_map = {
        "svn": {
            "repository": {
                "name": [
                    {
                        "api_url": "/svn/1/branch-tag/create",
                        "api_function": "createBranchOrTag",
                        "dto_class": "BranchTagRequest",
                        "dto_field": "name",
                        "entity_class": "Repository",
                        "entity_field": "name",
                        "db_column": "name",
                        "db_table": "repository",
                        "callers": ["views/svn/branch-tag.vue"],
                        "confidence": 1.0,
                        "is_reliable": True,
                    }
                ]
            }
        }
    }

    impact = build_impact_analysis(
        domains=[domain],
        api_matches=[],
        field_map=field_map,
        ontology={"relationships": []},
        wiki_root=wiki_root,
    )

    assert impact["impacts"]["fields"][0]["affected_business_points"] == ["branch-tag.vue"]


def test_phase4_business_point_impacts_use_page_specific_apis_and_code(tmp_path):
    wiki_root = tmp_path / "wiki"
    (wiki_root / "order").mkdir(parents=True)

    impact = build_impact_analysis(
        domains=[_sample_domain()],
        api_matches=[_sample_api(), _other_sample_api()],
        field_map=_sample_field_map(),
        ontology={"relationships": []},
        wiki_root=wiki_root,
    )

    point = impact["impacts"]["business_points"][0]
    assert point["business_point"] == "createOrder()"
    assert point["related_apis"] == ["POST /order/create"]
    assert point["implementation_files"] == [
        "src/views/order/index.vue",
        "src/api/order.js",
        "src/OrderController.java",
    ]
    assert point["service_chain"] == ["orderService.createOrder()"]
