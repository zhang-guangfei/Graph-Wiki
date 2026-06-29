export const APP_ROUTE_NAMES = {
  dashboard: 'Dashboard',
  repoList: 'RepoList',
  svnStatus: 'SvnStatus',
  svnLog: 'SvnLog',
  svnBranchTag: 'SvnBranchTag',
  operationHistory: 'OperationHistory',
  svnMerge: 'SvnMerge',
  localRepo: 'LocalRepo',
  requirementList: 'RequirementList'
}

export const ROUTE_PATHS = {
  [APP_ROUTE_NAMES.dashboard]: '/dashboard',
  [APP_ROUTE_NAMES.repoList]: '/repo/list',
  [APP_ROUTE_NAMES.svnStatus]: '/svn/status/:repoId?',
  [APP_ROUTE_NAMES.svnLog]: '/svn/log/:repoId?',
  [APP_ROUTE_NAMES.svnBranchTag]: '/svn/branch-tag',
  [APP_ROUTE_NAMES.operationHistory]: '/svn/operation-history/:repoId?',
  [APP_ROUTE_NAMES.svnMerge]: '/svn/merge',
  [APP_ROUTE_NAMES.localRepo]: '/svn/local-repo/:repoId?',
  [APP_ROUTE_NAMES.requirementList]: '/requirement/list'
}

export const NAV_MENU_INDEXES = [
  '/dashboard',
  '/repo/list',
  '/svn/status',
  '/svn/log',
  '/svn/branch-tag',
  '/svn/operation-history',
  '/svn/merge',
  '/svn/local-repo',
  '/requirement/list'
]

const ACTIVE_MENU_PREFIXES = [
  '/svn/operation-history',
  '/svn/local-repo',
  '/svn/branch-tag',
  '/svn/status',
  '/svn/merge',
  '/svn/log',
  '/requirement/list',
  '/repo/list',
  '/dashboard'
]

function normalizePath(path = '') {
  const cleanPath = String(path).split(/[?#]/)[0] || '/dashboard'
  return cleanPath.length > 1 ? cleanPath.replace(/\/+$/, '') : cleanPath
}

export function getActiveMenu(path = '') {
  const normalizedPath = normalizePath(path)
  return ACTIVE_MENU_PREFIXES.find(prefix => normalizedPath === prefix || normalizedPath.startsWith(`${prefix}/`)) || normalizedPath
}

export function getRoutePathByName(routeName) {
  return ROUTE_PATHS[routeName] || ''
}

export function buildLocalRepoConflictRoute(repoId, filePath = '') {
  const query = { focus: 'conflicted' }
  if (filePath) {
    query.filePath = filePath
  }

  return {
    name: APP_ROUTE_NAMES.localRepo,
    params: { repoId: String(repoId) },
    query
  }
}
