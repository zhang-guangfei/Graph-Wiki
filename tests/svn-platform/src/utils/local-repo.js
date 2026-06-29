function countByStatus(files = [], status) {
  return files.filter(file => file?.status === status).length
}

export function analyzeWorkingCopyChanges(files = []) {
  const modified = countByStatus(files, 'modified')
  const added = countByStatus(files, 'added')
  const deleted = countByStatus(files, 'deleted')
  const conflicted = countByStatus(files, 'conflicted')
  const versionedChangeCount = modified + added + deleted + conflicted

  return {
    modified,
    added,
    deleted,
    conflicted,
    hasConflicts: conflicted > 0,
    hasPendingVersionedChanges: versionedChangeCount > 0,
    versionedChangeCount
  }
}

export function buildUpdateGuard(files = []) {
  const summary = analyzeWorkingCopyChanges(files)

  if (summary.hasConflicts) {
    return {
      mode: 'blocked',
      message: `当前存在 ${summary.conflicted} 个冲突文件，请先解决冲突后再更新，避免把工作副本状态变得更复杂。`
    }
  }

  if (!summary.hasPendingVersionedChanges) {
    return { mode: 'safe', message: '' }
  }

  const detailParts = [
    summary.modified ? `已修改 ${summary.modified} 个` : '',
    summary.added ? `已添加 ${summary.added} 个` : '',
    summary.deleted ? `已删除 ${summary.deleted} 个` : ''
  ].filter(Boolean)

  return {
    mode: 'confirm',
    message: `当前工作副本还有未提交的版本化变更（${detailParts.join('，')}）。继续更新可能引入额外合并或冲突，是否仍要继续？`
  }
}

export function buildCleanupMessage() {
  return '该操作仅执行 SVN cleanup，用于清理工作副本锁和中间状态，不会删除未版本化文件。是否继续？'
}

export function isCommittableStatus(status) {
  return ['modified', 'added', 'deleted'].includes(status)
}

export function buildCommitGuard(files = []) {
  const selectedFiles = files.filter(file => file?.selected)

  if (selectedFiles.length === 0) {
    return { mode: 'blocked', message: '请至少选择一个要提交的版本化文件。' }
  }

  const conflictedCount = selectedFiles.filter(file => file?.status === 'conflicted').length
  if (conflictedCount > 0) {
    return {
      mode: 'blocked',
      message: `当前选择中包含 ${conflictedCount} 个冲突文件，请先解决冲突后再提交。`
    }
  }

  const committableCount = selectedFiles.filter(file => isCommittableStatus(file?.status)).length
  if (committableCount === 0) {
    return { mode: 'blocked', message: '当前选择中没有可提交的修改、新增或删除文件。' }
  }

  return { mode: 'safe', message: '' }
}

export function buildRevertConfirm(file = {}) {
  const path = file.path || ''

  if (file.status === 'added') {
    return {
      title: '确认撤销新增',
      confirmButtonText: '撤销新增',
      successMessage: '已撤销新增状态',
      failureMessage: '撤销新增失败',
      message: `确定要撤销「${path}」的新增状态吗？该操作会取消 svn add，但不会删除你本地已经创建的文件。`
    }
  }

  if (file.status === 'deleted') {
    return {
      title: '确认恢复删除',
      confirmButtonText: '恢复文件',
      successMessage: '恢复成功',
      failureMessage: '恢复失败',
      message: `确定要恢复「${path}」吗？该操作会从版本库恢复被删除的文件。`
    }
  }

  return {
    title: '确认还原',
    confirmButtonText: '还原文件',
    successMessage: '还原成功',
    failureMessage: '还原失败',
    message: `确定要还原「${path}」吗？该操作会丢弃本地修改，且不可恢复。`
  }
}

function normalizeOperationType(type) {
  return String(type || '').toUpperCase()
}

export function getOperationTypeLabel(type) {
  const map = { COMMIT: '提交', UPDATE: '更新', REVERT: '还原', CLEANUP: '清理', RESOLVE: '解决冲突', MERGE: '合并' }
  return map[normalizeOperationType(type)] || type
}

export function getOperationTypeColor(type) {
  const map = { COMMIT: 'success', UPDATE: 'primary', REVERT: 'warning', CLEANUP: 'info', RESOLVE: 'danger', MERGE: 'warning' }
  return map[normalizeOperationType(type)] || 'info'
}

export function getOperationLogMessage(log = {}) {
  return log.detail || log.message || ''
}
