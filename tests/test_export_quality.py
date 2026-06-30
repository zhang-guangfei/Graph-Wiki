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


def test_export_domain_read_model_wiki_preserves_drm_refs_for_wiki_and_agent(tmp_path):
    from graph_wiki.export import export_domain_read_model_wiki

    model = {
        "schema": {"version": "domain-read-model-v1"},
        "evidenceIndex": {
            "api:POST:/orders": {"id": "api:POST:/orders", "type": "api", "label": "POST /orders", "path": "api-map.json", "status": "ready"},
            "field:orders.customer_id": {"id": "field:orders.customer_id", "type": "field", "label": "orders.customer_id", "path": "field-map.json", "status": "ready"},
            "source:backend/OrderController.java#create": {"id": "source:backend/OrderController.java#create", "type": "source", "label": "OrderController#create", "path": "backend/OrderController.java", "sourcePath": "backend/OrderController.java", "status": "ready"},
        },
        "domains": [{
            "domainKey": "order",
            "displayName": "订单管理",
            "flows": [{
                "flowId": "order.create",
                "title": "创建订单",
                "summary": "提交订单。",
                "steps": [{"order": 1, "text": "调用创建接口。", "ruleRefs": ["order.rule"], "evidenceRefs": ["api:POST:/orders"]}],
                "evidenceRefs": ["api:POST:/orders", "source:backend/OrderController.java#create"],
            }],
            "rules": [{"ruleId": "order.rule", "statement": "必须调用后端。", "status": "ready", "confidence": 1, "evidenceRefs": ["source:backend/OrderController.java#create"]}],
            "fieldRules": [{
                "fieldId": "orders.customer_id",
                "statement": "字段链路。",
                "chain": [{"layer": "api", "ref": "api:POST:/orders"}, {"layer": "db", "ref": "field:orders.customer_id"}],
                "chainCompleteness": {"presentLayers": ["api", "db"], "missingRequiredLayers": [], "missingOptionalLayers": ["frontend", "controller", "dto", "entity"]},
                "evidenceRefs": ["field:orders.customer_id", "api:POST:/orders"],
            }],
            "evidenceRefs": ["api:POST:/orders"],
        }],
    }

    export_domain_read_model_wiki(model, tmp_path)

    page = (tmp_path / "order" / "domain-read-model.md").read_text(encoding="utf-8")
    agent = (tmp_path / "agent-contract.md").read_text(encoding="utf-8")
    assert "创建订单" in page
    assert "`api:POST:/orders`" in page
    assert "`field:orders.customer_id`" in page
    assert "frontend, controller, dto, entity" in page
    assert "source: `domain-read-model.json`" in agent
    assert "wiki/order/domain-read-model.md" in agent
