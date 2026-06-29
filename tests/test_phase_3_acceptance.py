import json
from types import SimpleNamespace

from graph_wiki import pipeline
from graph_wiki.export import export_wiki
from graph_wiki.api_mapper import (
    match_apis,
    parse_backend_controllers,
    parse_frontend_apis,
    trace_frontend_callers,
    url_match_score,
)
from graph_wiki.field_mapper import build_field_map
from graph_wiki.models import ApiMatch, BackendEndpoint, BusinessPoint, Domain, FrontendApiCall
from graph_wiki.ontology import build_code_ontology, evaluate_phase3_acceptance


def test_backend_controller_extraction_includes_dto_fields_and_service_chain(tmp_path):
    java_root = tmp_path / "src" / "main" / "java"
    controller_dir = java_root / "com" / "acme" / "order" / "controller"
    dto_dir = java_root / "com" / "acme" / "order" / "dto"
    controller_dir.mkdir(parents=True)
    dto_dir.mkdir(parents=True)
    (controller_dir / "OrderController.java").write_text(
        """
        package com.acme.order.controller;
        import com.acme.order.dto.OrderRequest;

        @RequestMapping("/order")
        public class OrderController {
            private OrderService orderService;

            @PostMapping("/create")
            public OrderResponse createOrder(@RequestBody OrderRequest request) {
                return orderService.createOrder(request);
            }
        }
        """,
        encoding="utf-8",
    )
    (dto_dir / "OrderRequest.java").write_text(
        """
        package com.acme.order.dto;
        public class OrderRequest {
            private String orderNo;
            private String customerCode;
        }
        """,
        encoding="utf-8",
    )

    endpoints = parse_backend_controllers(controller_dir, java_root)

    assert len(endpoints) == 1
    endpoint = endpoints[0]
    assert endpoint.url == "/order/create"
    assert endpoint.param_type == "OrderRequest"
    assert endpoint.param_fields == [
        {"name": "orderNo", "type": "String"},
        {"name": "customerCode", "type": "String"},
    ]
    assert endpoint.service_chain == ["orderService.createOrder()"]


def test_backend_controller_extraction_handles_real_spring_mapping_forms(tmp_path):
    java_root = tmp_path / "src" / "main" / "java"
    controller_dir = java_root / "com" / "svn" / "controller"
    entity_dir = java_root / "com" / "svn" / "entity"
    controller_dir.mkdir(parents=True)
    entity_dir.mkdir(parents=True)
    (controller_dir / "RequirementController.java").write_text(
        """
        package com.svn.controller;

        @RestController
        @RequestMapping("/api/requirement")
        @RequiredArgsConstructor
        public class RequirementController {
            private final RequirementService requirementService;

            @GetMapping("/list")
            public Result<List<Requirement>> listAll() {
                return Result.success(requirementService.listAll());
            }

            @GetMapping("/search")
            public Result<List<Requirement>> search(@RequestParam(required = false) String keyword) {
                return Result.success(requirementService.search(keyword));
            }

            @GetMapping("/{requirementId}")
            public Result<Requirement> getById(@PathVariable String requirementId) {
                return Result.success(requirementService.getById(requirementId));
            }

            @PostMapping
            public Result<Requirement> create(@RequestBody Requirement requirement) {
                return Result.success(requirementService.create(requirement));
            }

            @PutMapping("/{requirementId}")
            public Result<Requirement> update(
                    @PathVariable String requirementId,
                    @RequestBody Requirement requirement) {
                return Result.success(requirementService.update(requirementId, requirement));
            }

            @PostMapping("/createOrUpdate")
            public Result<Requirement> createOrUpdate(@RequestBody Requirement requirement) {
                return Result.success(requirementService.createOrUpdate(requirement));
            }

            @PostMapping("/batch")
            public Result<List<Requirement>> batchCreateOrUpdate(@RequestBody List<Requirement> requirements) {
                return Result.success(requirementService.batchCreateOrUpdate(requirements));
            }

            @PutMapping("/{requirementId}/metadata")
            public Result<Requirement> updateMetadata(
                    @PathVariable String requirementId,
                    @RequestBody Map<String, String> body) {
                return Result.success(requirementService.updateMetadata(requirementId, body));
            }

            @DeleteMapping("/{requirementId}")
            public Result<Void> delete(@PathVariable String requirementId) {
                requirementService.delete(requirementId);
                return Result.success();
            }
        }
        """,
        encoding="utf-8",
    )
    (entity_dir / "Requirement.java").write_text(
        """
        package com.svn.entity;
        public class Requirement {
            private String requirementId;
            private String title;
        }
        """,
        encoding="utf-8",
    )

    endpoints = parse_backend_controllers(controller_dir, java_root)

    assert [endpoint.method_name for endpoint in endpoints] == [
        "listAll",
        "search",
        "getById",
        "create",
        "update",
        "createOrUpdate",
        "batchCreateOrUpdate",
        "updateMetadata",
        "delete",
    ]
    create = next(endpoint for endpoint in endpoints if endpoint.method_name == "create")
    update = next(endpoint for endpoint in endpoints if endpoint.method_name == "update")
    batch = next(endpoint for endpoint in endpoints if endpoint.method_name == "batchCreateOrUpdate")
    metadata = next(endpoint for endpoint in endpoints if endpoint.method_name == "updateMetadata")
    assert create.url == "/api/requirement"
    assert create.http_method == "POST"
    assert create.param_type == "Requirement"
    assert create.param_fields == [
        {"name": "requirementId", "type": "String"},
        {"name": "title", "type": "String"},
    ]
    assert update.url == "/api/requirement/{requirementId}"
    assert update.param_type == "Requirement"
    assert batch.param_type == "Requirement"
    assert "requirementService.batchCreateOrUpdate()" in batch.service_chain
    assert metadata.param_type == "Map"
    assert metadata.param_fields == []
    assert "requirementService.updateMetadata()" in metadata.service_chain


def test_url_match_score_tolerates_backend_api_prefix():
    assert url_match_score("/requirement", "/api/requirement") >= 0.8
    assert url_match_score("/repo/list", "/api/repo/list") >= 0.8
    assert url_match_score("/requirement/${requirementId}", "/api/requirement/{requirementId}") > url_match_score(
        "/requirement/${requirementId}", "/api/requirement/search"
    )


def test_frontend_api_tracing_supports_relative_vue_imports(tmp_path):
    api_dir = tmp_path / "src" / "api"
    views_dir = tmp_path / "src" / "views"
    api_dir.mkdir(parents=True)
    (views_dir / "order").mkdir(parents=True)
    (api_dir / "order.js").write_text(
        """
        export function createOrder(data) {
          return request.post('/order/create', data)
        }
        """,
        encoding="utf-8",
    )
    (views_dir / "order" / "index.vue").write_text(
        """
        <script>
        import { createOrder } from '../../api/order'
        export default {
          methods: {
            submitOrder() {
              return createOrder(this.form)
            }
          }
        }
        </script>
        """,
        encoding="utf-8",
    )

    calls = trace_frontend_callers(parse_frontend_apis(api_dir), views_dir)

    assert calls[0].callers == [{"page": "views/order/index.vue", "fields_used": []}]


def test_api_matching_prefers_business_path_domain_over_technical_main_domain():
    domains = [
        Domain(
            id="main",
            name="main",
            anchors={"controller": [{"source_file": "src/main/java/com/acme/order/OrderController.java"}]},
        ),
        Domain(
            id="order",
            name="order",
            anchors={"controller": [{"source_file": "src/api/order.js"}]},
        ),
    ]
    frontend = [
        FrontendApiCall(
            function_name="createOrder",
            http_method="POST",
            url="/order/create",
            source_file="src/api/order.js",
        )
    ]
    backend = [
        BackendEndpoint(
            controller_file="src/main/java/com/acme/order/OrderController.java",
            controller_class="OrderController",
            method_name="createOrder",
            http_method="POST",
            url="/order/create",
        )
    ]

    matches = match_apis(frontend, backend, domains)

    assert matches[0].domain == "order"


def test_api_matching_prefers_frontend_url_domain_for_flat_backend_controllers():
    domains = [
        Domain(id="svn", name="svn", anchors={"controller": [{"source_file": "SvnOperationController.java"}]}),
        Domain(id="repository", name="repository", anchors={"controller": [{"source_file": "views/repository/list.vue"}]}),
        Domain(id="requirement", name="requirement", anchors={"controller": [{"source_file": "RequirementController.java"}]}),
    ]
    frontend = [
        FrontendApiCall(
            function_name="importRepo",
            http_method="POST",
            url="/repo/import",
            source_file="src/api/index.js",
            callers=[{"page": "views/repository/list.vue"}],
        ),
        FrontendApiCall(
            function_name="listRequirements",
            http_method="GET",
            url="/requirement/list",
            source_file="src/api/index.js",
            callers=[{"page": "views/requirement/requirement.vue"}],
        ),
    ]
    backend = [
        BackendEndpoint(
            controller_file="src/main/java/com/svnplatform/controller/RepoController.java",
            controller_class="RepoController",
            method_name="importRepo",
            http_method="POST",
            url="/api/repo/import",
        ),
        BackendEndpoint(
            controller_file="src/main/java/com/svnplatform/controller/RequirementController.java",
            controller_class="RequirementController",
            method_name="listAll",
            http_method="GET",
            url="/api/requirement/list",
        ),
    ]

    matches = match_apis(frontend, backend, domains)

    assert {match.frontend.function_name: match.domain for match in matches} == {
        "importRepo": "repository",
        "listRequirements": "requirement",
    }


def test_field_map_links_api_dto_entity_and_database_columns(tmp_path):
    backend_root = tmp_path / "backend"
    dto_dir = backend_root / "src" / "main" / "java" / "com" / "acme" / "order" / "dto"
    entity_dir = backend_root / "src" / "main" / "java" / "com" / "acme" / "order" / "entity"
    dto_dir.mkdir(parents=True)
    entity_dir.mkdir(parents=True)
    (dto_dir / "OrderRequest.java").write_text(
        """
        public class OrderRequest {
            private String orderNo;
            private String customerCode;
        }
        """,
        encoding="utf-8",
    )
    (entity_dir / "OrderEntity.java").write_text(
        """
        @TableName("orders")
        public class OrderEntity {
            @TableField("order_no")
            private String orderNo;
            @TableField("customer_code")
            private String customerCode;
        }
        """,
        encoding="utf-8",
    )
    match = ApiMatch(
        id="api-create-order",
        frontend=FrontendApiCall(
            function_name="createOrder",
            http_method="POST",
            url="/order/create",
            callers=[{"page": "views/order/index.vue"}],
        ),
        backend=BackendEndpoint(param_type="OrderRequest"),
        domain="order",
    )

    field_map = build_field_map([match], entity_dir, dto_dir, backend_root)

    assert field_map["order"]["orders"]["order_no"][0]["dto_field"] == "orderNo"
    assert field_map["order"]["orders"]["order_no"][0]["entity_class"] == "OrderEntity"
    assert field_map["order"]["orders"]["customer_code"][0]["api_function"] == "createOrder"
    assert field_map["order"]["orders"]["order_no"][0]["confidence_level"] == "high"
    assert field_map["order"]["orders"]["order_no"][0]["is_reliable"] is True


def test_low_confidence_field_map_is_marked_as_candidate_and_hidden_from_wiki(tmp_path):
    output_dir = tmp_path / "wiki"
    field_map = {
        "svn": {
            "operation_log": {
                "error_message": [
                    {
                        "api_url": "/svn/commit",
                        "api_function": "commit",
                        "dto_class": "CommitRequest",
                        "dto_field": "message",
                        "entity_class": "OperationLog",
                        "entity_field": "errorMessage",
                        "db_column": "error_message",
                        "db_table": "operation_log",
                        "confidence": 0.6,
                        "confidence_level": "low",
                        "is_reliable": False,
                    }
                ]
            }
        }
    }
    domain = Domain(
        id="svn",
        name="svn",
        anchors={"controller": [{"id": "SvnOperationController"}]},
        business_points=[BusinessPoint(name="commit()", point_type="core_action")],
    )

    export_wiki([domain], [], field_map, output_dir)

    data_flow = output_dir / "svn" / "data-flow.md"
    assert not data_flow.exists()


def test_code_ontology_v0_contains_business_typed_relationships():
    domain = Domain(
        id="order",
        name="order",
        display_name="订单管理",
        anchors={"controller": [{"id": "OrderController", "label": "OrderController.java", "source_file": "src/OrderController.java"}]},
        business_points=[
            BusinessPoint(name="createOrder()", point_type="core_action", entry_file="src/views/order/index.vue")
        ],
        dependencies=[{"domain": "customer", "import_count": 3}],
    )
    api = ApiMatch(
        id="api-create-order",
        frontend=FrontendApiCall(
            function_name="createOrder",
            http_method="POST",
            url="/order/create",
            source_file="src/api/order.js",
            callers=[{"page": "views/order/index.vue"}],
        ),
        backend=BackendEndpoint(
            controller_class="OrderController",
            method_name="createOrder",
            param_type="OrderRequest",
            param_fields=[{"name": "orderNo", "type": "String"}],
            service_chain=["orderService.createOrder()"],
            return_type="OrderResponse",
        ),
        domain="order",
    )
    field_map = {
        "order": {
            "orders": {
                "order_no": [
                    {
                        "api_url": "/order/create",
                        "api_function": "createOrder",
                        "dto_field": "orderNo",
                        "entity_class": "OrderEntity",
                        "entity_field": "orderNo",
                        "db_column": "order_no",
                        "db_table": "orders",
                        "callers": ["views/order/index.vue"],
                    }
                ]
            }
        }
    }

    ontology = build_code_ontology([domain], [api], field_map)

    relation_types = {item["type"] for item in ontology["relationships"]}
    assert {"contains", "exposes", "consumes", "calls", "maps_to", "depends_on"} <= relation_types
    assert ontology["schema"]["version"] == "code-ontology-v0"
    assert ontology["agent_scope"]["domains"]["order"]["entry_files"] == ["src/OrderController.java"]
    assert ontology["coverage"]["relationship_types_count"] >= 6


def test_phase3_acceptance_requires_ontology_fields_and_wiki_pages():
    ontology = {
        "schema": {"version": "code-ontology-v0"},
        "coverage": {
            "relationship_types": ["contains", "exposes", "consumes", "calls", "maps_to", "depends_on"],
            "relationship_types_count": 6,
        },
        "agent_scope": {"domains": {"order": {"entry_files": ["src/OrderController.java"]}}},
    }
    quality = {
        "phase3": {
            "explanation_status": "ready",
            "domains_with_description": 2,
            "business_points_with_type": 4,
            "api_with_callers": 2,
            "ontology_status": "ready",
            "field_flow_status": "ready",
        }
    }

    audit = evaluate_phase3_acceptance(
        ontology=ontology,
        quality_report=quality,
        wiki_evidence={
            "summary_pages": 2,
            "code_map_pages": 2,
            "api_docs_pages": 1,
            "data_flow_pages": 1,
            "ontology_pages": 2,
        },
    )

    assert audit["status"] == "passed"
    assert all(item["status"] == "passed" for item in audit["items"])


def test_cmd_build_writes_phase3_ontology_artifact(tmp_path, monkeypatch):
    project = tmp_path / "project"
    source_file = project / "src" / "api" / "order.js"
    source_file.parent.mkdir(parents=True)
    source_file.write_text("export function createOrder() {}", encoding="utf-8")
    domain = Domain(
        id="order",
        name="order",
        display_name="订单管理",
        anchors={"controller": [{"id": "order_file", "label": "order.js", "source_file": str(source_file)}]},
        business_points=[BusinessPoint(name="createOrder()", point_type="core_action")],
    )
    api = ApiMatch(
        id="api-create-order",
        frontend=FrontendApiCall(
            function_name="createOrder",
            http_method="POST",
            url="/order/create",
            source_file=str(source_file),
            callers=[{"page": "views/order/index.vue"}],
        ),
        backend=BackendEndpoint(
            controller_class="OrderController",
            method_name="createOrder",
            param_type="OrderRequest",
            param_fields=[{"name": "orderNo", "type": "String"}],
            service_chain=["orderService.createOrder()"],
            return_type="OrderResponse",
        ),
        domain="order",
    )
    field_map = {
        "order": {
            "orders": {
                "order_no": [
                    {
                        "api_url": "/order/create",
                        "api_function": "createOrder",
                        "dto_field": "orderNo",
                        "entity_class": "OrderEntity",
                        "entity_field": "orderNo",
                        "db_column": "order_no",
                        "db_table": "orders",
                        "callers": ["views/order/index.vue"],
                    }
                ]
            }
        }
    }

    monkeypatch.chdir(tmp_path)
    monkeypatch.setattr(pipeline, "detect_corpus", lambda root: {
        "total_files": 1,
        "total_words": 10,
        "files": {"code": [str(source_file)]},
    })
    monkeypatch.setattr(pipeline, "extract_ast", lambda files: {
        "nodes": [{"id": "order_file", "label": "order.js", "source_file": str(source_file)}],
        "edges": [],
    })
    monkeypatch.setattr(pipeline, "business_cluster", lambda graph, root, language=None: [domain])
    monkeypatch.setattr(pipeline, "build_api_map", lambda *args, **kwargs: [api])
    monkeypatch.setattr(pipeline, "build_field_map", lambda *args, **kwargs: field_map)

    output_dir = tmp_path / "phase3-output"
    args = SimpleNamespace(
        path=project,
        name=None,
        language="javascript",
        llm_backend="claude",
        no_llm=True,
        output_dir=output_dir,
    )

    pipeline._cmd_build(args)

    ontology = json.loads((output_dir / "ontology.json").read_text(encoding="utf-8"))
    report = pipeline._read_json(output_dir / "build-report.json")
    assert ontology["schema"]["version"] == "code-ontology-v0"
    assert "maps_to" in ontology["coverage"]["relationship_types"]
    assert report["phase3"]["ontology_status"] == "ready"
    assert (output_dir / "wiki" / "order" / "ontology.md").exists()
    assert (output_dir / "wiki" / "order" / "data-flow.md").exists()
