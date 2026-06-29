from pathlib import Path

from graph_wiki.api_mapper import infer_frontend_api_domain
from graph_wiki.models import Domain, FrontendApiCall


def test_infer_frontend_api_domain_prefers_api_module_name():
    domains = [
        Domain(id="approvalCenter", name="approvalCenter"),
        Domain(id="workflow", name="workflow"),
    ]
    api = FrontendApiCall(
        function_name="fetchTasks",
        http_method="GET",
        url="/workflow/tasks",
        source_file="src/api/approvalCenter.js",
        callers=[{"page": "views/workflow/list.vue"}],
    )

    assert infer_frontend_api_domain(api, domains, Path(".")) == "approvalCenter"


def test_infer_frontend_api_domain_uses_view_then_url_segment():
    domains = [
        Domain(id="approvalCenter", name="approvalCenter"),
        Domain(id="workflow", name="workflow"),
    ]
    api = FrontendApiCall(
        function_name="fetchTasks",
        http_method="GET",
        url="/workflow/tasks",
        source_file="src/api/index.js",
        callers=[{"page": "views/approvalCenter/list.vue"}],
    )

    assert infer_frontend_api_domain(api, domains, Path(".")) == "approvalCenter"

    api.callers = []
    assert infer_frontend_api_domain(api, domains, Path(".")) == "workflow"

