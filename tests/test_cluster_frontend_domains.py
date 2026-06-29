import networkx as nx

from graph_wiki.cluster import business_cluster, extract_package_path
from graph_wiki.models import Language


def test_frontend_view_domains_are_preserved_when_each_has_one_anchor(tmp_path):
    graph = nx.Graph()
    nodes = [
        ("api_index", "index.js", "src/api/index.js"),
        ("layout_index", "index.vue", "src/layout/index.vue"),
        ("dashboard_index", "index.vue", "src/views/dashboard/index.vue"),
        ("repo_list", "list.vue", "src/views/repository/list.vue"),
        ("requirement_page", "requirement.vue", "src/views/requirement/requirement.vue"),
        ("svn_status", "status.vue", "src/views/svn/status.vue"),
        ("svn_log", "log.vue", "src/views/svn/log.vue"),
        ("svn_merge", "merge.vue", "src/views/svn/merge.vue"),
        ("svn_branch", "branch-tag.vue", "src/views/svn/branch-tag.vue"),
        ("svn_local", "local-repo.vue", "src/views/svn/local-repo.vue"),
    ]
    for node_id, label, source_file in nodes:
        graph.add_node(node_id, id=node_id, label=label, file_type="code", source_file=source_file)
    graph.add_edge("repo_list", "api_index", relation="imports_from")
    graph.add_edge("requirement_page", "api_index", relation="imports_from")
    graph.add_edge("svn_status", "api_index", relation="imports_from")

    domains = business_cluster(graph, tmp_path, language=Language.JAVASCRIPT)
    domain_ids = {domain.id for domain in domains}

    assert {"repository", "requirement", "svn"}.issubset(domain_ids)
    assert "api" not in domain_ids
    assert "layout" not in domain_ids
    assert "dashboard" not in domain_ids


def test_frontend_business_points_exclude_ui_helper_methods(tmp_path):
    graph = nx.Graph()
    graph.add_node("repo_list", id="repo_list", label="list.vue", file_type="code", source_file="src/views/repository/list.vue")
    graph.add_node("requirement_page", id="requirement_page", label="requirement.vue", file_type="code", source_file="src/views/requirement/requirement.vue")
    graph.add_node("svn_status", id="svn_status", label="status.vue", file_type="code", source_file="src/views/svn/status.vue")
    for method in ["handleImport()", "loadRepos()", "formatDate()", "calcTableHeight()", "resetForm()"]:
        method_id = method.rstrip("()")
        graph.add_node(method_id, id=method_id, label=method, file_type="code", source_file="src/views/repository/list.vue")
        graph.add_edge("repo_list", method_id, relation="contains")

    domains = business_cluster(graph, tmp_path, language=Language.JAVASCRIPT, min_domain_size=1)
    points = {bp.name for domain in domains for bp in domain.business_points}

    assert "handleImport()" in points
    assert "loadRepos()" in points
    assert "formatDate()" not in points
    assert "calcTableHeight()" not in points
    assert "resetForm()" not in points


def test_business_point_type_classification_understands_handle_wrappers():
    from graph_wiki.cluster import classify_business_point_type

    assert classify_business_point_type("handleSubmit()") == "core_action"
    assert classify_business_point_type("handleDelete()") == "core_action"
    assert classify_business_point_type("handleReset()") == "helper"
    assert classify_business_point_type("handleSearch()") == "interaction"


def test_java_relative_source_paths_keep_business_package():
    node = {"source_file": "order/controller/OrderController.java"}

    assert extract_package_path(node, Language.JAVA, None) == "order.controller"


def test_java_anchor_without_method_nodes_creates_class_level_business_point(tmp_path):
    graph = nx.Graph()
    for node_id, label, source_file in [
        ("order_controller", "OrderController.java", "order/controller/OrderController.java"),
        ("invoice_controller", "InvoiceController.java", "invoice/controller/InvoiceController.java"),
        ("customer_controller", "CustomerController.java", "customer/controller/CustomerController.java"),
    ]:
        graph.add_node(node_id, id=node_id, label=label, file_type="code", source_file=source_file)

    domains = business_cluster(graph, tmp_path, language=Language.JAVA, min_domain_size=1)
    points = {bp.name for domain in domains for bp in domain.business_points}

    assert {"OrderController.java", "InvoiceController.java", "CustomerController.java"}.issubset(points)


def test_mixed_frontend_backend_project_infers_business_domains_from_paths_and_class_names(tmp_path):
    graph = nx.Graph()
    nodes = [
        ("repo_controller", "RepoController.java", "svn-platform-backend/src/main/java/com/svnplatform/controller/RepoController.java"),
        ("repo_service", "RepoService.java", "svn-platform-backend/src/main/java/com/svnplatform/service/RepoService.java"),
        ("repo_impl", "RepoServiceImpl.java", "svn-platform-backend/src/main/java/com/svnplatform/service/impl/RepoServiceImpl.java"),
        ("requirement_controller", "RequirementController.java", "svn-platform-backend/src/main/java/com/svnplatform/controller/RequirementController.java"),
        ("requirement_service", "RequirementService.java", "svn-platform-backend/src/main/java/com/svnplatform/service/RequirementService.java"),
        ("requirement_impl", "RequirementServiceImpl.java", "svn-platform-backend/src/main/java/com/svnplatform/service/impl/RequirementServiceImpl.java"),
        ("svn_controller", "SvnOperationController.java", "svn-platform-backend/src/main/java/com/svnplatform/controller/SvnOperationController.java"),
        ("svn_service", "SvnOperationService.java", "svn-platform-backend/src/main/java/com/svnplatform/service/SvnOperationService.java"),
        ("svn_impl", "SvnOperationServiceImpl.java", "svn-platform-backend/src/main/java/com/svnplatform/service/impl/SvnOperationServiceImpl.java"),
        ("repo_view", "list.vue", "svn-platform-frontend/src/views/repository/list.vue"),
        ("requirement_view", "requirement.vue", "svn-platform-frontend/src/views/requirement/requirement.vue"),
        ("svn_status_view", "status.vue", "svn-platform-frontend/src/views/svn/status.vue"),
    ]
    for node_id, label, source_file in nodes:
        graph.add_node(node_id, id=node_id, label=label, file_type="code", source_file=source_file)

    domains = business_cluster(graph, tmp_path, language=Language.AUTO, min_domain_size=1)
    domain_ids = {domain.id for domain in domains}

    assert {"repository", "requirement", "svn"}.issubset(domain_ids)
    assert "svnplatform" not in domain_ids
    assert "views" not in domain_ids
