"""数据模型：Domain、BusinessPoint、NodeRole、Language"""

from dataclasses import dataclass, field
from enum import Enum
from typing import Optional


class Language(Enum):
    JAVA = "java"
    JAVASCRIPT = "javascript"
    TYPESCRIPT = "typescript"
    PYTHON = "python"
    GO = "go"
    AUTO = "auto"


class NodeRole(Enum):
    CONTROLLER = "controller"
    SERVICE_IMPL = "service_impl"
    SERVICE_API = "service_api"
    MAPPER = "mapper"
    DAO = "dao"
    ADAPTER = "adapter"
    ENTITY = "entity"
    DTO = "dto"
    VO = "vo"
    ENUM = "enum"
    HANDLER = "handler"
    CONFIG = "config"
    UTIL = "util"
    NOISE = "noise"


ANCHOR_ROLES = {
    NodeRole.CONTROLLER,
    NodeRole.SERVICE_IMPL,
    NodeRole.SERVICE_API,
    NodeRole.MAPPER,
    NodeRole.DAO,
    NodeRole.ADAPTER,
}


@dataclass
class BusinessPoint:
    """业务点"""
    name: str
    display_name: str = ""
    entry_method: str = ""
    entry_file: str = ""
    point_type: str = "core_action"
    call_chain: list[str] = field(default_factory=list)
    cross_domain_calls: dict[str, list[str]] = field(default_factory=dict)
    internal_calls: list[str] = field(default_factory=list)
    infrastructure_calls: list[str] = field(default_factory=list)


@dataclass
class Domain:
    """业务域"""
    id: str
    name: str = ""                               # LLM 标注的英文 ID（如 "purchase"），用于文件目录名
    display_name: str = ""                       # LLM 标注的中文显示名（如 "采购管理"），用于 UI 展示
    description: str = ""                        # LLM 标注的业务域一句话描述
    core_flows: list[str] = field(default_factory=list)    # 核心业务流程列表
    key_terms: list[dict] = field(default_factory=list)    # 关键术语表 [{term, definition}]
    packages: list[str] = field(default_factory=list)
    modules: list[str] = field(default_factory=list)
    frontend_views: list[str] = field(default_factory=list)
    anchors: dict[str, list[dict]] = field(default_factory=dict)
    business_points: list[BusinessPoint] = field(default_factory=list)
    total_files: int = 0
    dependencies: list[dict] = field(default_factory=list)  # [{domain, name, import_count, strength}]

    def anchors_flat(self) -> list[dict]:
        result = []
        for role_nodes in self.anchors.values():
            result.extend(role_nodes)
        return result

    def anchors_count(self) -> int:
        return len(self.anchors_flat())


@dataclass
class FrontendApiCall:
    """前端 API 调用"""
    function_name: str
    http_method: str
    url: str
    params: list[str] = field(default_factory=list)
    source_file: str = ""
    source_line: int = 0
    callers: list[dict] = field(default_factory=list)


@dataclass
class BackendEndpoint:
    """后端 Controller 端点"""
    controller_file: str = ""
    controller_class: str = ""
    method_name: str = ""
    http_method: str = ""
    url: str = ""
    param_type: Optional[str] = None
    param_fields: list[dict] = field(default_factory=list)
    service_chain: list[str] = field(default_factory=list)
    return_type: Optional[str] = None


@dataclass
class ApiMatch:
    """前后端 API 匹配"""
    id: str
    frontend: FrontendApiCall = field(default_factory=FrontendApiCall)
    backend: BackendEndpoint = field(default_factory=BackendEndpoint)
    match_confidence: float = 0.0
    domain: str = ""
