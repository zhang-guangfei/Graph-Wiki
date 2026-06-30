from graph_wiki.export import evaluate_wiki_quality
from graph_wiki.models import ApiMatch, BusinessPoint, Domain, FrontendApiCall


def test_evaluate_wiki_quality_reports_uncategorized_api_and_technical_domains():
    domains = [
        Domain(
            id="api",
            name="api",
            anchors={"controller": [{"id": "ApiController"}]},
            business_points=[BusinessPoint(name="submit()")],
        ),
        Domain(
            id="order",
            name="order",
            display_name="订单管理",
            anchors={"controller": [{"id": "OrderController"}]},
            business_points=[BusinessPoint(name="createOrder()")],
        ),
    ]
    api_matches = [
        ApiMatch(id="a1", frontend=FrontendApiCall(function_name="a", http_method="GET", url="/a"), domain=""),
        ApiMatch(id="a2", frontend=FrontendApiCall(function_name="b", http_method="GET", url="/b"), domain="order"),
    ]

    report = evaluate_wiki_quality(domains, api_matches, {})

    assert report["domains"]["count"] == 2
    assert report["domains"]["business_points"] == 2
    assert report["domains"]["technical_name_ratio"] == 0.5
    assert report["api"]["uncategorized_ratio"] == 0.5
    assert report["quality"]["status"] in {"warning", "failed"}
    assert report["quality"]["warnings"]


def test_evaluate_wiki_quality_warns_when_single_domain_is_too_coarse():
    domain = Domain(
        id="svn",
        name="svn",
        anchors={"controller": [{"id": "SvnController"}]},
        business_points=[BusinessPoint(name=f"operation{i}()") for i in range(60)],
    )

    report = evaluate_wiki_quality([domain], [], {})

    assert report["quality"]["status"] == "warning"
    assert any("业务域过粗" in warning for warning in report["quality"]["warnings"])


def test_export_wiki_archives_stale_domain_dirs_on_clean_build(tmp_path):
    from graph_wiki.export import export_wiki

    stale = tmp_path / "old-domain"
    stale.mkdir()
    (stale / "rules.md").write_text("manual note", encoding="utf-8")

    current = Domain(id="order", name="order", anchors={"controller": [{"id": "OrderController"}]})
    export_wiki([current], [], {}, tmp_path, clean_stale=True)

    assert (tmp_path / "order").exists()
    assert not stale.exists()
    archived = list((tmp_path / "_stale").rglob("old-domain/rules.md"))
    assert archived
    assert archived[0].read_text(encoding="utf-8") == "manual note"


def test_export_wiki_writes_phase3_business_explanation_layer(tmp_path):
    from graph_wiki.export import export_wiki

    domain = Domain(
        id="requirement",
        name="requirement",
        display_name="需求管理",
        anchors={
            "controller": [
                {
                    "id": "requirement_page",
                    "label": "requirement.vue",
                    "source_file": "src/views/requirement/requirement.vue",
                }
            ]
        },
        business_points=[
            BusinessPoint(
                name="handleSubmit()",
                entry_file="src/views/requirement/requirement.vue",
                point_type="core_action",
            ),
            BusinessPoint(
                name="handleReset()",
                entry_file="src/views/requirement/requirement.vue",
                point_type="helper",
            ),
        ],
    )
    api = FrontendApiCall(
        function_name="createRequirement",
        http_method="POST",
        url="/requirement",
        source_file="src/api/requirement.js",
        callers=[{"page": "views/requirement/requirement.vue"}],
    )
    setattr(api, "domain", "requirement")

    export_wiki([domain], [api], {}, tmp_path)

    summary = (tmp_path / "requirement" / "summary.md").read_text(encoding="utf-8")
    code_map = (tmp_path / "requirement" / "code-map.md").read_text(encoding="utf-8")
    api_docs = (tmp_path / "requirement" / "api-docs.md").read_text(encoding="utf-8")

    assert "业务说明" in summary
    assert "需求管理负责" in summary
    assert "代码职责" in code_map
    assert "requirement.vue" in code_map
    assert "负责需求管理的页面入口" in code_map
    assert "类型：核心业务动作" in code_map
    assert "说明：提交需求管理相关数据或执行关键变更" in code_map
    assert "辅助方法" in code_map
    assert "用途：提交需求管理相关数据或执行关键变更" in api_docs
    assert "关联业务点：handleSubmit()" in api_docs


def test_evaluate_wiki_quality_reports_phase3_explanation_readiness():
    domain = Domain(
        id="repository",
        name="repository",
        display_name="仓库管理",
        anchors={"controller": [{"id": "repo", "label": "list.vue", "source_file": "src/views/repository/list.vue"}]},
        business_points=[
            BusinessPoint(name="handleImport()", point_type="core_action"),
            BusinessPoint(name="formatDate()", point_type="helper"),
        ],
    )
    api = FrontendApiCall(
        function_name="importRepo",
        http_method="POST",
        url="/repo/import",
        callers=[{"page": "views/repository/list.vue"}],
    )
    setattr(api, "domain", "repository")

    report = evaluate_wiki_quality([domain], [api], {})

    assert report["phase3"]["explanation_status"] == "ready"
    assert report["phase3"]["domains_with_description"] == 1
    assert report["phase3"]["business_points_with_type"] == 2
    assert report["phase3"]["api_with_callers"] == 1


def test_api_docs_link_mutation_apis_to_core_business_points(tmp_path):
    from graph_wiki.export import export_wiki

    domain = Domain(
        id="requirement",
        name="requirement",
        display_name="需求管理",
        anchors={"controller": [{"id": "requirement_page", "label": "requirement.vue"}]},
        business_points=[
            BusinessPoint(
                name="loadData()",
                entry_file="src/views/requirement/requirement.vue",
                point_type="interaction",
            ),
            BusinessPoint(
                name="handleSubmit()",
                entry_file="src/views/requirement/requirement.vue",
                point_type="core_action",
            ),
            BusinessPoint(
                name="handleDelete()",
                entry_file="src/views/requirement/requirement.vue",
                point_type="core_action",
            ),
        ],
    )
    create_api = FrontendApiCall(
        function_name="createRequirement",
        http_method="POST",
        url="/requirement",
        callers=[{"page": "views/requirement/requirement.vue"}],
    )
    delete_api = FrontendApiCall(
        function_name="deleteRequirement",
        http_method="DELETE",
        url="/requirement/${requirementId}",
        callers=[{"page": "views/requirement/requirement.vue"}],
    )
    for api in (create_api, delete_api):
        setattr(api, "domain", "requirement")

    export_wiki([domain], [create_api, delete_api], {}, tmp_path)

    api_docs = (tmp_path / "requirement" / "api-docs.md").read_text(encoding="utf-8")

    assert "POST /requirement\n\n- 用途：提交需求管理相关数据或执行关键变更\n- 关联业务点：handleSubmit()" in api_docs
    assert "DELETE /requirement/${requirementId}\n\n- 用途：删除需求管理相关数据\n- 关联业务点：handleDelete()" in api_docs


def test_export_wiki_writes_domain_read_model_sections(tmp_path):
    from graph_wiki.export import export_wiki

    domain = Domain(id="order", name="order", display_name="订单管理", anchors={"controller": [{"id": "OrderController"}]})
    read_model = {
        "schema": {"version": "domain-read-model-v1"},
        "quality": {"deepReadingStatus": "passed", "coreDomainEvidenceStatus": "passed"},
        "domains": [
            {
                "domainKey": "order",
                "displayName": "订单管理",
                "summary": "处理订单创建、校验和落库。",
                "core": True,
                "quality": {"deepReadingStatus": "passed"},
                "evidenceRefs": ["api:POST:/orders"],
                "flows": [
                    {
                        "flowId": "order.create",
                        "title": "创建订单",
                        "status": "ready",
                        "evidenceRefs": ["api:POST:/orders"],
                        "steps": [
                            {
                                "stepId": "order.create.submit",
                                "title": "提交订单",
                                "ruleRefs": ["order.create.rule"],
                                "evidenceRefs": ["source:backend/OrderController.java#createOrder"],
                            }
                        ],
                    }
                ],
                "rules": [
                    {
                        "ruleId": "order.create.rule",
                        "ruleType": "validation",
                        "status": "ready",
                        "confidence": 0.9,
                        "statement": "创建订单前必须校验库存。",
                        "flowRefs": ["order.create"],
                        "evidenceRefs": ["source:backend/OrderController.java#createOrder"],
                    }
                ],
                "fieldRules": [
                    {
                        "ruleId": "order.customer_id.field",
                        "fieldId": "orders.customer_id",
                        "status": "ready",
                        "confidence": 1.0,
                        "chainCompleteness": {"presentLayers": ["api", "dto", "entity", "db"], "missingRequiredLayers": []},
                        "chain": [{"layer": "api", "ref": "api:POST:/orders", "label": "POST /orders"}],
                        "evidenceRefs": ["field:orders.customer_id", "api:POST:/orders"],
                    }
                ],
            }
        ],
        "evidenceIndex": {
            "api:POST:/orders": {"id": "api:POST:/orders", "label": "POST /orders"},
            "source:backend/OrderController.java#createOrder": {"id": "source:backend/OrderController.java#createOrder", "label": "OrderController#createOrder"},
            "field:orders.customer_id": {"id": "field:orders.customer_id", "label": "orders.customer_id"},
        },
    }

    export_wiki([domain], [], {}, tmp_path, domain_read_model=read_model)

    index = (tmp_path / "index.md").read_text(encoding="utf-8")
    summary = (tmp_path / "order" / "summary.md").read_text(encoding="utf-8")
    drm_page = (tmp_path / "order" / "domain-read-model.md").read_text(encoding="utf-8")
    rules_page = (tmp_path / "order" / "rules.md").read_text(encoding="utf-8")

    assert "schema: `domain-read-model-v1`" in index
    assert "DRM 流程**: 1" in summary
    assert "[[domain-read-model]]" in summary
    for expected in [
        "order.create",
        "order.create.rule",
        "orders.customer_id",
        "api:POST:/orders",
        "source:backend/OrderController.java#createOrder",
    ]:
        assert expected in drm_page
        assert expected in rules_page or expected == "api:POST:/orders"
