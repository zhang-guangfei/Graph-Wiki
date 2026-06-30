"""Stable evidence reference helpers for Domain Read Model v1."""

from __future__ import annotations

import re
from pathlib import Path
from typing import Any

_API_RE = re.compile(r"^api:[A-Z]+:/.+")
_SOURCE_RE = re.compile(r"^source:.+#[^#]+$")
_FIELD_RE = re.compile(r"^field:[A-Za-z0-9_.$-]+\.[A-Za-z0-9_.$-]+$")
_WIKI_RE = re.compile(r"^wiki:.+#[^#]+$")
_ONTOLOGY_RE = re.compile(r"^ontology:.+")

_REF_PATTERNS = {
    "api": _API_RE,
    "source": _SOURCE_RE,
    "field": _FIELD_RE,
    "wiki": _WIKI_RE,
    "ontology": _ONTOLOGY_RE,
}


SENSITIVE_PATH_KEYWORDS = {
    "secret", "password", "passwd", "token", "credential", "credentials",
    "private-key", "private_key", ".env", "application-", "bootstrap-",
    "id_rsa", "id_dsa", "keystore", "truststore",
}


def is_sensitive_source_path(path: str | Path | None) -> bool:
    """Return True when a source path is likely to expose secrets/config credentials.

    Evidence chains should remain auditable without linking users directly to
    secret-bearing files. This path-level guard is intentionally conservative
    and is shared by DRM generation and product-facing DTOs.
    """
    text = normalize_source_path(path or "").lower()
    if not text:
        return False
    name = Path(text).name
    return any(keyword in text or keyword in name for keyword in SENSITIVE_PATH_KEYWORDS)


def api_ref(method: str | None, path: str | None) -> str:
    """Return a stable `api:<METHOD>:<PATH>` evidence id."""
    normalized_method = (method or "GET").strip().upper() or "GET"
    normalized_path = normalize_api_path(path or "/")
    return f"api:{normalized_method}:{normalized_path}"


def source_ref(path: str | Path | None, symbol_or_line: str | int | None = None) -> str:
    """Return a stable `source:<path>#<symbol-or-line>` evidence id."""
    normalized_path = normalize_source_path(path or "unknown")
    anchor = str(symbol_or_line or "file").strip() or "file"
    anchor = anchor.replace(" ", "-")
    return f"source:{normalized_path}#{anchor}"


def field_ref(table: str | None, column: str | None) -> str:
    table_name = normalize_token(table or "unknown_table")
    column_name = normalize_token(column or "unknown_column")
    return f"field:{table_name}.{column_name}"


def wiki_ref(path: str | Path | None, heading: str | None) -> str:
    normalized_path = normalize_source_path(path or "wiki/unknown.md")
    normalized_heading = (heading or "section").strip().replace(" ", "-") or "section"
    return f"wiki:{normalized_path}#{normalized_heading}"


def ontology_ref(relationship_id: str | int | None) -> str:
    return f"ontology:{relationship_id or 'unknown'}"


def evidence_ref_type(ref: str) -> str:
    return ref.split(":", 1)[0] if isinstance(ref, str) and ":" in ref else "unknown"


def is_valid_evidence_ref(ref: str) -> bool:
    kind = evidence_ref_type(ref)
    pattern = _REF_PATTERNS.get(kind)
    return bool(pattern and pattern.match(ref))


def normalize_api_path(path: str) -> str:
    text = str(path or "/").strip()
    if not text.startswith("/"):
        text = "/" + text
    text = re.sub(r"/{2,}", "/", text)
    return text.rstrip("/") or "/"


def normalize_source_path(path: str | Path) -> str:
    text = str(path).replace("\\", "/").strip()
    text = re.sub(r"/{2,}", "/", text)
    if text.startswith("./"):
        text = text[2:]
    return text or "unknown"


def normalize_token(value: str) -> str:
    text = str(value).replace("\\", "/").strip()
    text = re.sub(r"[^A-Za-z0-9_.$/-]+", "_", text)
    text = text.replace("/", "_").strip("_")
    return text or "unknown"


def make_evidence(
    ref: str,
    *,
    label: str = "",
    path: str = "",
    source_path: str = "",
    confidence: float | None = 1.0,
    status: str = "ready",
) -> dict[str, Any]:
    """Build a serializable evidence object for `evidenceIndex`."""
    return {
        "id": ref,
        "type": evidence_ref_type(ref),
        "label": label or _default_label(ref),
        "path": path,
        "sourcePath": source_path,
        "confidence": confidence,
        "status": status,
    }


def _default_label(ref: str) -> str:
    kind = evidence_ref_type(ref)
    if kind == "api":
        _, method, path = ref.split(":", 2)
        return f"{method} {path}"
    if kind == "field":
        return ref.removeprefix("field:")
    if kind == "source":
        return ref.removeprefix("source:")
    return ref
