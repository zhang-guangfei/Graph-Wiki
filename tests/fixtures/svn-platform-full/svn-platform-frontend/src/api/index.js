import request from '@/utils/request'

// 仓库管理API
export function importRepo(data) {
  return request.post('/repo/import', data)
}

export function getRepoList() {
  return request.get('/repo/list')
}

export function getRepo(id) {
  return request.get(`/repo/${id}`)
}

export function deleteRepo(id) {
  return request.delete(`/repo/${id}`)
}

export function renameRepo(id, name) {
  return request.put(`/repo/${id}/rename`, { name })
}

export function validateRepoPath(path) {
  return request.get('/repo/validate', { params: { path } })
}

export function browseFolder() {
  return request.post('/repo/browse', {}, { timeout: 300000 }) // 5分钟超时，等待用户选择
}

// SVN操作API
export function getSvnStatus(repoId) {
  return request.get(`/svn/${repoId}/status`)
}

export function getSvnDiff(repoId, filePath) {
  return request.get(`/svn/${repoId}/diff`, { params: { filePath } })
}

export function svnCommit(repoId, data) {
  return request.post(`/svn/${repoId}/commit`, data)
}

export function svnUpdate(repoId, revision) {
  return request.post(`/svn/${repoId}/update`, null, {
    params: { revision: revision || undefined }
  })
}

export function svnMerge(repoId, data) {
  return request.post(`/svn/${repoId}/merge`, data)
}

export function getSvnLog(repoId, params) {
  return request.get(`/svn/${repoId}/log`, { params })
}

// 获取提交记录（增强版）
export function getSvnLogWithFilters(repoId, params) {
  return request.get(`/svn/${repoId}/log/filter`, { params })
}

// 获取指定提交的变更文件列表
export function getLogChanges(repoId, revision) {
  return request.get(`/svn/${repoId}/log/${revision}/changes`)
}

// 获取指定文件在指定提交中的diff内容
export function getLogDiff(repoId, revision, filePath) {
  return request.get(`/svn/${repoId}/log/${revision}/diff`, { params: { filePath } })
}

// 获取指定文件在两个版本间的内容对比（用于Diff Editor）
export function getFileDiffContent(repoId, revision, filePath) {
  return request.get(`/svn/${repoId}/log/${revision}/diff/file`, { params: { filePath } })
}

// 获取已合并版本列表
export function getMergedRevisions(sourceRepoId, targetRepoId) {
  return request.get('/svn/merge/merged-revisions', { params: { sourceRepoId, targetRepoId } })
}

// 获取分支列表
export function getBranches(repoId) {
  return request.get(`/svn/${repoId}/branches`)
}

// 获取标签列表
export function getTags(repoId) {
  return request.get(`/svn/${repoId}/tags`)
}

// 预览合并冲突
export function previewMerge(sourceRepoId, targetRepoId, revisionList) {
  return request.post(`/svn/${targetRepoId}/merge/preview`, {
    sourceRepoId,
    revisionList,
    dryRun: true
  })
}

// 执行合并操作
export function executeMerge(sourceRepoId, targetRepoId, revisionList, skipFailedRevisions = false) {
  return request.post(`/svn/${targetRepoId}/merge/execute`, {
    sourceRepoId,
    revisionList,
    dryRun: false,
    skipFailedRevisions
  })
}

export function svnRevert(repoId, data) {
  return request.post(`/svn/${repoId}/revert`, data)
}

export function svnAdd(repoId, files) {
  return request.post(`/svn/${repoId}/add`, { files })
}

export function svnResolve(repoId, data) {
  return request.post(`/svn/${repoId}/resolve`, data)
}

export function svnCleanup(repoId) {
  return request.post(`/svn/${repoId}/cleanup`)
}

export function getSvnInfo(repoId) {
  return request.get(`/svn/${repoId}/info`)
}

// Changelist API
export function addToChangelist(repoId, data) {
  return request.post(`/svn/${repoId}/changelist/add`, data)
}

export function removeFromChangelist(repoId, data) {
  return request.post(`/svn/${repoId}/changelist/remove`, data)
}

export function renameChangelist(repoId, oldName, newName) {
  return request.post(`/svn/${repoId}/changelist/rename`, { oldName, newName })
}

export function commitByChangelist(repoId, data) {
  return request.post(`/svn/${repoId}/commit/changelist`, data)
}

// 文件内容（用于diff对比）
export function getFileContent(repoId, filePath) {
  return request.get(`/svn/${repoId}/cat`, { params: { filePath } })
}

// 获取冲突文件三方内容
export function getConflictDetail(repoId, filePath) {
  return request.get(`/svn/${repoId}/conflict`, { params: { filePath } })
}

// 以合并内容解决冲突
export function resolveWithContent(repoId, data) {
  return request.post(`/svn/${repoId}/resolve/merge`, data)
}

// 异步任务状态查询
export function getTaskStatus(taskId) {
  return request.get(`/svn/task/${taskId}`)
}

/**
 * 轮询任务状态直到完成
 * @param {string} taskId 任务ID
 * @param {function} onProgress 进度回调
 * @param {number} interval 轮询间隔（毫秒）
 * @returns {Promise} 任务结果
 */
export function pollTaskStatus(taskId, onProgress, interval = 1000) {
  return new Promise((resolve, reject) => {
    const timer = setInterval(async () => {
      try {
        const res = await getTaskStatus(taskId)
        const task = res.data
        if (onProgress) onProgress(task)

        if (task.status === 'COMPLETED') {
          clearInterval(timer)
          resolve(task)
        } else if (task.status === 'FAILED') {
          clearInterval(timer)
          reject(new Error(task.errorMessage || '任务执行失败'))
        }
      } catch (error) {
        clearInterval(timer)
        reject(error)
      }
    }, interval)
  })
}

// 分支/标签管理 API
export function listBranchesOrTags(repoId, pathType) {
  return request.get(`/svn/${repoId}/branch-tag/list`, { params: { pathType } })
}

export function createBranchOrTag(repoId, data) {
  return request.post(`/svn/${repoId}/branch-tag/create`, data)
}

export function deleteBranchOrTag(repoId, data) {
  return request.post(`/svn/${repoId}/branch-tag/delete`, data)
}

// 操作历史记录 API
export function getOperationLogs(repoId, params) {
  return request.get(`/svn/${repoId}/operation-logs`, { params })
}

export function getRecentOperationLogs(repoId, limit) {
  return request.get(`/svn/${repoId}/operation-logs/recent`, { params: { limit } })
}

export function getOperationLogsByType(repoId, operationType, params) {
  return request.get(`/svn/${repoId}/operation-logs/type/${operationType}`, { params })
}

// ==================== 需求管理 ====================

// 获取需求列表
export function listRequirements() {
  return request.get('/requirement/list')
}

// 搜索需求
export function searchRequirements(keyword) {
  return request.get('/requirement/search', { params: { keyword } })
}

// 获取需求详情
export function getRequirement(requirementId) {
  return request.get(`/requirement/${requirementId}`)
}

// 创建需求
export function createRequirement(data) {
  return request.post('/requirement', data)
}

// 更新需求
export function updateRequirement(requirementId, data) {
  return request.put(`/requirement/${requirementId}`, data)
}

// 删除需求
export function deleteRequirement(requirementId) {
  return request.delete(`/requirement/${requirementId}`)
}

// 创建或更新需求（需求号已存在则更新）
export function createOrUpdateRequirement(data) {
  return request.post('/requirement/createOrUpdate', data)
}

// 批量创建或更新需求
export function batchCreateOrUpdateRequirements(data) {
  return request.post('/requirement/batch', data)
}

// ==================== 本地仓库管理 ====================

// 获取本地仓库状态
export function getLocalRepoStatus(localPath) {
  return request.post('/svn/local-repo/status', { localPath })
}

// 获取文件状态列表
export function getFileStatusList(localPath) {
  return request.post('/svn/local-repo/file-status', { localPath })
}

// 更新本地仓库
export function localRepoUpdate(localPath, revision) {
  return request.post('/svn/local-repo/update', { localPath, revision })
}

// 提交本地仓库
export function localRepoCommit(localPath, message, files, autoAdd) {
  return request.post('/svn/local-repo/commit', { localPath, message, files, autoAdd })
}

// 还原本地仓库文件
export function localRepoRevert(localPath, files) {
  return request.post('/svn/local-repo/revert', { localPath, files })
}

// 清理本地仓库
export function localRepoCleanup(localPath) {
  return request.post('/svn/local-repo/cleanup', { localPath })
}

// 获取冲突文件详情
export function getConflictDetailByPath(localPath, filePath) {
  return request.post('/svn/local-repo/conflict', { localPath, filePath })
}

// 打开本地文件夹
export function openFolder(folderPath) {
  return request.post('/svn/local-repo/open-folder', { folderPath })
}
