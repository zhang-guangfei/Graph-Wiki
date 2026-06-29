/**
 * 本地存储工具 - 记住用户偏好
 */
const STORAGE_PREFIX = 'svn_platform_'

export function getLastRepoId() {
  const val = localStorage.getItem(STORAGE_PREFIX + 'last_repo_id')
  return val ? Number(val) : null
}

export function setLastRepoId(repoId) {
  if (repoId) {
    localStorage.setItem(STORAGE_PREFIX + 'last_repo_id', String(repoId))
  }
}

export function getLastRepoName() {
  return localStorage.getItem(STORAGE_PREFIX + 'last_repo_name') || ''
}

export function setLastRepoName(name) {
  if (name) {
    localStorage.setItem(STORAGE_PREFIX + 'last_repo_name', name)
  }
}

/**
 * 保存上次使用的仓库信息
 */
export function saveLastRepo(repo) {
  if (repo && repo.id) {
    setLastRepoId(repo.id)
    setLastRepoName(repo.name)
  }
}
