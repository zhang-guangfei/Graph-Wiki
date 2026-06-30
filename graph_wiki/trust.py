"""Trust and sensitive-source filtering policy for Graph-Wiki.

The product trust boundary is conservative for configuration/secret-like files:
files that look like environment dumps, credential stores, tokens, passwords, or
secrets are excluded from corpus, LLM prompts, and product evidence unless their
filename explicitly marks them as non-secret examples/templates.
"""

from __future__ import annotations

import re
from pathlib import Path
from typing import Iterable, TypeVar

T = TypeVar("T")

_SENSITIVE_FILENAMES = {
    ".env",
    ".env.local",
    ".envrc",
    "credentials",
    "credential",
    "secrets",
    "secret",
    "password",
    "passwd",
    "token",
    "tokens",
}
_SENSITIVE_NAME_RE = re.compile(
    r"(^|[._-])(secret|secrets|credential|credentials|password|passwd|token|tokens|api[_-]?key|private[_-]?key)([._-]|$)",
    re.IGNORECASE,
)
_EXPLICIT_SAFE_MARKERS = {
    "example",
    "examples",
    "sample",
    "samples",
    "template",
    "templates",
    "mock",
    "mocks",
    "dummy",
}
_CONFIG_SECRET_EXTENSIONS = {
    "", ".env", ".properties", ".yml", ".yaml", ".json", ".xml", ".toml", ".ini", ".conf", ".config", ".pem", ".key", ".crt"
}
_DOC_EXTENSIONS = {".md", ".rst", ".txt", ".adoc"}
_SAFE_SUFFIXES = (".example", ".sample", ".template", ".dist")


def normalize_trust_path(path: str | Path) -> str:
    return str(path or "").replace("\\", "/").strip()


def is_explicitly_safe_sensitive_path(path: str | Path) -> bool:
    """Return True when a secret-like name is clearly a non-secret sample."""
    text = normalize_trust_path(path).lower()
    if not text:
        return False
    name = Path(text).name
    name_parts = [part for part in re.split(r"[._-]+", name) if part]
    return (
        "/tests/fixtures/" in f"/{text}"
        or "/fixtures/" in f"/{text}"
        or any(marker in name_parts for marker in _EXPLICIT_SAFE_MARKERS)
        or any(name.endswith(suffix) for suffix in _SAFE_SUFFIXES)
    )


def is_sensitive_path(path: str | Path) -> bool:
    """Detect files that must not enter corpus, LLM prompts, or product evidence."""
    text = normalize_trust_path(path).lower()
    if not text or is_explicitly_safe_sensitive_path(text):
        return False
    name = Path(text).name
    if name in _SENSITIVE_FILENAMES or name.startswith(".env."):
        return True
    suffixes = Path(name).suffixes
    suffix = suffixes[-1] if suffixes else ""
    if suffix in _DOC_EXTENSIONS:
        return False
    return suffix in _CONFIG_SECRET_EXTENSIONS and bool(_SENSITIVE_NAME_RE.search(name))


def filter_safe_paths(paths: Iterable[T]) -> list[T]:
    """Keep only paths that pass the Graph-Wiki trust policy."""
    return [path for path in paths if not is_sensitive_path(path)]
