<template>
  <div class="local-repo app-page">
    <!-- 工具栏 -->
    <div class="toolbar page-shell page-shell--padded page-toolbar">
      <div class="repo-block">
        <el-select v-model="selectedRepoPath" placeholder="请选择本地仓库路径" class="repo-select"
                   @change="handleRepoChange" filterable>
          <el-option v-for="repo in repos" :key="repo.id"
                     :label="`${repo.name} (${repo.localPath})`" :value="repo.localPath" />
        </el-select>
      </div>
      <div class="separator"></div>
      <el-button type="primary" :icon="Refresh" @click="handleRefreshStatus" :disabled="!selectedRepoPath" :loading="loading">
        刷新状态
      </el-button>
      <el-button type="success" :icon="Download" @click="handleUpdate" :disabled="!selectedRepoPath" :loading="updating">
        更新代码
      </el-button>
      <el-button type="warning" :icon="Upload" @click="handleCommit" :disabled="!selectedRepoPath || committableFileCount === 0">
        提交
      </el-button>
      <el-button :icon="Brush" @click="handleCleanup" :disabled="!selectedRepoPath" :loading="cleaning">
        清理
      </el-button>
    </div>

    <!-- 仓库信息 -->
    <div class="repo-info-card page-shell page-shell--padded" v-if="repoInfo">
      <div class="info-grid">
        <div class="info-item">
          <span class="info-label">本地路径</span>
          <span class="info-value path-value">{{ repoInfo.localPath }}</span>
        </div>
        <div class="info-item">
          <span class="info-label">分支</span>
          <span class="info-value">{{ repoInfo.branch || '-' }}</span>
        </div>
        <div class="info-item">
          <span class="info-label">当前版本</span>
          <span class="info-value rev-value">r{{ repoInfo.revision || '?' }}</span>
        </div>
        <div class="info-item">
          <span class="info-label">最新远端版本</span>
          <span class="info-value rev-value">r{{ repoInfo.latestRemoteRevision || '?' }}</span>
        </div>
        <div class="info-item">
          <span class="info-label">整体状态</span>
          <el-tag :type="getOverallStatusType(repoInfo.overallStatus)" size="small">
            {{ getOverallStatusLabel(repoInfo.overallStatus) }}
          </el-tag>
        </div>
      </div>
    </div>

    <!-- 状态统计卡片 -->
    <div class="status-cards page-grid page-grid--auto-fit" v-if="selectedRepoPath">
      <div class="stat-card" :class="{ active: activeSection === 'modified' }" @click="scrollToSection('modified')">
        <div class="stat-icon stat-modified">M</div>
        <div class="stat-info">
          <span class="stat-count">{{ statusCounts.modified }}</span>
          <span class="stat-label">已修改</span>
        </div>
      </div>
      <div class="stat-card" :class="{ active: activeSection === 'added' }" @click="scrollToSection('added')">
        <div class="stat-icon stat-added">A</div>
        <div class="stat-info">
          <span class="stat-count">{{ statusCounts.added }}</span>
          <span class="stat-label">已添加</span>
        </div>
      </div>
      <div class="stat-card" :class="{ active: activeSection === 'deleted' }" @click="scrollToSection('deleted')">
        <div class="stat-icon stat-deleted">D</div>
        <div class="stat-info">
          <span class="stat-count">{{ statusCounts.deleted }}</span>
          <span class="stat-label">已删除</span>
        </div>
      </div>
      <div class="stat-card" :class="{ active: activeSection === 'conflicted' }" @click="scrollToSection('conflicted')">
        <div class="stat-icon stat-conflicted">C</div>
        <div class="stat-info">
          <span class="stat-count">{{ statusCounts.conflicted }}</span>
          <span class="stat-label">冲突</span>
        </div>
      </div>
      <div class="stat-card" :class="{ active: activeSection === 'unversioned' }" @click="scrollToSection('unversioned')">
        <div class="stat-icon stat-unversioned">?</div>
        <div class="stat-info">
          <span class="stat-count">{{ statusCounts.unversioned }}</span>
          <span class="stat-label">未版本化</span>
        </div>
      </div>
    </div>

    <!-- 文件状态列表 -->
    <div class="file-status-section page-shell page-shell--padded page-scroll" v-loading="loading">
      <!-- 已修改 -->
      <div class="status-group" id="section-modified" v-if="fileGroups.modified.length > 0">
        <el-collapse v-model="activeCollapse">
          <el-collapse-item name="modified">
            <template #title>
              <div class="collapse-title">
                <el-tag type="warning" size="small">M</el-tag>
                <span>已修改 ({{ fileGroups.modified.length }})</span>
              </div>
            </template>
            <div class="file-list">
              <div v-for="file in fileGroups.modified" :key="file.path" class="file-item">
                <el-checkbox v-model="file.selected" />
                <el-icon class="file-icon modified"><Edit /></el-icon>
                <span class="file-path" :title="file.path">{{ file.path }}</span>
                <div class="file-actions">
                  <el-button size="small" type="primary" link @click="handleCommitFile(file)">提交</el-button>
                  <el-button size="small" type="info" link @click="handleRevertFile(file)">还原</el-button>
                </div>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>

      <!-- 已添加 -->
      <div class="status-group" id="section-added" v-if="fileGroups.added.length > 0">
        <el-collapse v-model="activeCollapse">
          <el-collapse-item name="added">
            <template #title>
              <div class="collapse-title">
                <el-tag type="success" size="small">A</el-tag>
                <span>已添加 ({{ fileGroups.added.length }})</span>
              </div>
            </template>
            <div class="file-list">
              <div v-for="file in fileGroups.added" :key="file.path" class="file-item">
                <el-checkbox v-model="file.selected" />
                <el-icon class="file-icon added"><Plus /></el-icon>
                <span class="file-path" :title="file.path">{{ file.path }}</span>
                <div class="file-actions">
                  <el-button size="small" type="primary" link @click="handleCommitFile(file)">提交</el-button>
                  <el-button size="small" type="danger" link @click="handleRevertAddedFile(file)">撤销新增</el-button>
                </div>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>

      <!-- 已删除 -->
      <div class="status-group" id="section-deleted" v-if="fileGroups.deleted.length > 0">
        <el-collapse v-model="activeCollapse">
          <el-collapse-item name="deleted">
            <template #title>
              <div class="collapse-title">
                <el-tag type="danger" size="small">D</el-tag>
                <span>已删除 ({{ fileGroups.deleted.length }})</span>
              </div>
            </template>
            <div class="file-list">
              <div v-for="file in fileGroups.deleted" :key="file.path" class="file-item">
                <el-checkbox v-model="file.selected" />
                <el-icon class="file-icon deleted"><Delete /></el-icon>
                <span class="file-path" :title="file.path">{{ file.path }}</span>
                <div class="file-actions">
                  <el-button size="small" type="primary" link @click="handleCommitFile(file)">提交</el-button>
                  <el-button size="small" type="info" link @click="handleRestoreFile(file)">恢复</el-button>
                </div>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>

      <!-- 冲突 -->
      <div class="status-group" id="section-conflicted" v-if="fileGroups.conflicted.length > 0">
        <el-collapse v-model="activeCollapse">
          <el-collapse-item name="conflicted">
            <template #title>
              <div class="collapse-title">
                <el-tag type="danger" size="small">C</el-tag>
                <span>冲突 ({{ fileGroups.conflicted.length }})</span>
              </div>
            </template>
            <div class="file-list">
              <div v-for="file in fileGroups.conflicted" :key="file.path" class="file-item conflicted-item">
                <el-checkbox v-model="file.selected" />
                <el-icon class="file-icon conflicted"><WarningFilled /></el-icon>
                <span class="file-path" :title="file.path">{{ file.path }}</span>
                <div class="file-actions">
                  <el-button size="small" type="danger" link @click="handleResolveConflict(file)">解决冲突</el-button>
                  <el-button size="small" type="primary" link @click="handleOpenFolder(file)">
                    <el-icon><FolderOpened /></el-icon>
                  </el-button>
                </div>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>

      <!-- 未版本化 -->
      <div class="status-group" id="section-unversioned" v-if="fileGroups.unversioned.length > 0">
        <el-collapse v-model="activeCollapse">
          <el-collapse-item name="unversioned">
            <template #title>
              <div class="collapse-title">
                <el-tag type="info" size="small">?</el-tag>
                <span>未版本化 ({{ fileGroups.unversioned.length }})</span>
              </div>
            </template>
            <div class="file-list">
              <div v-for="file in fileGroups.unversioned" :key="file.path" class="file-item">
                <el-checkbox v-model="file.selected" />
                <el-icon class="file-icon unversioned"><Document /></el-icon>
                <span class="file-path" :title="file.path">{{ file.path }}</span>
                <div class="file-actions">
                  <el-button size="small" type="success" link @click="handleAddFile(file)">添加</el-button>
                </div>
              </div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>

      <!-- 空状态 -->
      <div class="empty-state" v-if="selectedRepoPath && !loading && allFiles.length === 0">
        <el-empty description="所有文件都是最新状态" />
      </div>

      <div class="no-repo-state" v-if="!selectedRepoPath">
        <el-empty description="请先选择本地仓库路径" />
      </div>
    </div>

    <!-- 操作历史 -->
    <div class="operation-history page-shell page-shell--padded" v-if="selectedRepoPath">
      <div class="history-header" @click="historyCollapsed = !historyCollapsed" style="cursor: pointer; user-select: none;">
        <span class="history-title">
          <el-icon style="margin-right: 4px; transition: transform 0.3s;" :style="{ transform: historyCollapsed ? '' : 'rotate(90deg)' }">
            <ArrowRight />
          </el-icon>
          最近操作记录
        </span>
        <el-button size="small" text @click.stop="loadOperationHistory">
          <el-icon><Refresh /></el-icon> 刷新
        </el-button>
      </div>
      <div class="history-list" v-loading="historyLoading" v-show="!historyCollapsed">
        <div v-for="log in operationLogs" :key="log.id" class="history-item">
          <el-tag :type="getOperationTypeColor(log.operationType)" size="small">{{ getOperationTypeLabel(log.operationType) }}</el-tag>
          <span class="history-message">{{ getOperationLogMessage(log) }}</span>
          <span class="history-time">{{ formatTime(log.createdAt) }}</span>
        </div>
        <div class="history-empty" v-if="!historyLoading && operationLogs.length === 0">
          <span>暂无操作记录</span>
        </div>
      </div>
    </div>

    <!-- 提交对话框 -->
    <el-dialog v-model="commitDialogVisible" title="提交代码" width="600px">
      <el-form>
        <el-form-item label="提交信息" required>
          <el-input v-model="commitMessage" type="textarea" :rows="4" placeholder="请输入提交说明" />
        </el-form-item>
        <el-form-item label="文件列表">
          <div class="commit-files-list">
            <el-checkbox v-model="selectAllFiles" @change="handleSelectAllCommitFiles">全选</el-checkbox>
            <div v-for="file in commitFiles" :key="file.path" class="commit-file-item">
              <el-checkbox v-model="file.selected" />
              <el-tag :type="getStatusType(file.status)" size="small">{{ getStatusLabel(file.status) }}</el-tag>
              <span>{{ file.path }}</span>
            </div>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="commitDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="doCommit" :loading="committing">确认提交</el-button>
      </template>
    </el-dialog>

    <!-- 冲突解决对话框 -->
    <ConflictResolver
      v-if="conflictDialogVisible"
      :visible="conflictDialogVisible"
      :local-path="selectedRepoPath"
      :file-path="currentConflictFile"
      :conflict-files="fileGroups.conflicted.map(file => file.path)"
      :repo-id="currentRepoId"
      @close="conflictDialogVisible = false"
      @resolved="handleConflictResolved"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Refresh, Download, Upload, Brush, Edit, Plus, Delete, WarningFilled, Document, ArrowRight, FolderOpened } from '@element-plus/icons-vue'
import {
  getRepoList,
  getLocalRepoStatus,
  getFileStatusList,
  localRepoUpdate,
  localRepoCommit,
  localRepoRevert,
  localRepoCleanup,
  svnAdd,
  getRecentOperationLogs,
  pollTaskStatus,
  openFolder
} from '@/api'
import ConflictResolver from '@/components/ConflictResolver.vue'
import {
  buildCleanupMessage,
  buildCommitGuard,
  buildRevertConfirm,
  buildUpdateGuard,
  getOperationLogMessage,
  getOperationTypeColor,
  getOperationTypeLabel,
  isCommittableStatus
} from '@/utils/local-repo'

const route = useRoute()
const router = useRouter()

const repos = ref([])
const selectedRepoPath = ref('')
const currentRepoId = ref(null)
const loading = ref(false)
const updating = ref(false)
const cleaning = ref(false)
const committing = ref(false)

const repoInfo = ref(null)
const allFiles = ref([])
const activeSection = ref('')
const activeCollapse = ref([])

const commitDialogVisible = ref(false)
const commitMessage = ref('')
const commitFiles = ref([])
const selectAllFiles = ref(true)

const conflictDialogVisible = ref(false)
const currentConflictFile = ref('')

const operationLogs = ref([])
const historyLoading = ref(false)
const historyCollapsed = ref(true)

const statusCounts = computed(() => {
  return {
    modified: fileGroups.value.modified.length,
    added: fileGroups.value.added.length,
    deleted: fileGroups.value.deleted.length,
    conflicted: fileGroups.value.conflicted.length,
    unversioned: fileGroups.value.unversioned.length
  }
})

const fileGroups = computed(() => {
  const groups = {
    modified: [],
    added: [],
    deleted: [],
    conflicted: [],
    unversioned: []
  }
  allFiles.value.forEach(file => {
    if (groups[file.status]) {
      groups[file.status].push(file)
    }
  })
  return groups
})

const committableFileCount = computed(() => {
  return allFiles.value.filter(f => isCommittableStatus(f.status)).length
})

onMounted(async () => {
  const res = await getRepoList()
  repos.value = res.data || []

  if (repos.value.length > 0) {
    initializeRepoSelection()
    await loadRepoStatus()
  }
})

watch(() => route.params.repoId, async (repoId) => {
  if (!repos.value.length || !repoId) return

  const repoChanged = applyRepoSelectionById(repoId)
  if (repoChanged) {
    await loadRepoStatus()
  } else {
    await applyRoutePostActions()
  }
})

async function handleRepoChange(localPath) {
  const repo = repos.value.find(r => r.localPath === localPath)
  if (repo) {
    applyRepoSelection(repo)
    if (String(route.params.repoId || '') !== String(repo.id)) {
      await router.replace({
        name: 'LocalRepo',
        params: { repoId: String(repo.id) }
      })
    }
    await loadRepoStatus()
  }
}

async function loadRepoStatus() {
  if (!selectedRepoPath.value) return
  loading.value = true
  try {
    const [statusRes, filesRes] = await Promise.all([
      getLocalRepoStatus(selectedRepoPath.value),
      getFileStatusList(selectedRepoPath.value)
    ])
    repoInfo.value = statusRes.data || {}
    allFiles.value = (filesRes.data?.files || []).map(f => ({ ...f, selected: false }))
    await applyRoutePostActions()
    await loadOperationHistory()
  } catch (e) {
    ElMessage.error(e.message || '获取仓库状态失败')
  } finally {
    loading.value = false
  }
}

async function handleRefreshStatus() {
  await loadRepoStatus()
  ElMessage.success('状态已刷新')
}

async function handleUpdate() {
  try {
    const updateGuard = buildUpdateGuard(allFiles.value)
    if (updateGuard.mode === 'blocked') {
      ElMessage.warning(updateGuard.message)
      return
    }

    if (updateGuard.mode === 'confirm') {
      await ElMessageBox.confirm(updateGuard.message, '确认更新', {
        type: 'warning',
        confirmButtonText: '继续更新',
        cancelButtonText: '先不更新'
      })
    }

    updating.value = true
    const res = await localRepoUpdate(selectedRepoPath.value)
    const taskId = res.data?.taskId
    if (taskId) {
      await pollTaskStatus(taskId, null)
    }
    ElMessage.success('更新成功')
    await loadRepoStatus()
  } catch (e) {
    ElMessage.error(e.message || '更新失败')
  } finally {
    updating.value = false
  }
}

function handleCommit() {
  commitFiles.value = allFiles.value
    .filter(f => isCommittableStatus(f.status))
    .map(f => ({ ...f, selected: true }))
  selectAllFiles.value = true
  commitMessage.value = ''
  commitDialogVisible.value = true
}

function handleSelectAllCommitFiles(val) {
  commitFiles.value.forEach(f => f.selected = val)
}

async function doCommit() {
  if (!commitMessage.value.trim()) {
    ElMessage.warning('请填写提交说明')
    return
  }

  const commitGuard = buildCommitGuard(commitFiles.value)
  if (commitGuard.mode === 'blocked') {
    ElMessage.warning(commitGuard.message)
    return
  }

  const selectedFiles = commitFiles.value.filter(f => f.selected && isCommittableStatus(f.status))
  committing.value = true
  try {
    const res = await localRepoCommit(
      selectedRepoPath.value,
      commitMessage.value,
      selectedFiles.map(f => f.path),
      true
    )
    const taskId = res.data?.taskId
    if (taskId) {
      await pollTaskStatus(taskId, null)
    }
    commitDialogVisible.value = false
    ElMessage.success('提交成功')
    await loadRepoStatus()
  } catch (e) {
    ElMessage.error(e.message || '提交失败')
  } finally {
    committing.value = false
  }
}

async function handleCleanup() {
  try {
    await ElMessageBox.confirm(buildCleanupMessage(), '确认清理', {
      type: 'info',
      confirmButtonText: '继续清理',
      cancelButtonText: '取消'
    })

    cleaning.value = true
    await localRepoCleanup(selectedRepoPath.value)
    ElMessage.success('工作副本清理完成')
    await loadRepoStatus()
  } catch (e) {
    if (e !== 'cancel') {
      ElMessage.error(e.message || '清理失败')
    }
  } finally {
    cleaning.value = false
  }
}

async function handleCommitFile(file) {
  try {
    const { value } = await ElMessageBox.prompt('请输入提交说明', '提交文件', {
      confirmButtonText: '提交',
      cancelButtonText: '取消',
      inputPattern: /\S+/,
      inputErrorMessage: '提交说明不能为空'
    })
    if (!value) return
    const res = await localRepoCommit(selectedRepoPath.value, value, [file.path], true)
    const taskId = res.data?.taskId
    if (taskId) {
      await pollTaskStatus(taskId, null)
    }
    ElMessage.success('提交成功')
    await loadRepoStatus()
  } catch (e) {
    if (e !== 'cancel') {
      ElMessage.error(e.message || '提交失败')
    }
  }
}

async function handleRevertFile(file) {
  await confirmAndRevertFile(file)
}

async function handleRevertAddedFile(file) {
  await confirmAndRevertFile(file)
}

async function handleRestoreFile(file) {
  await confirmAndRevertFile(file)
}

async function confirmAndRevertFile(file) {
  const confirmConfig = buildRevertConfirm(file)
  try {
    await ElMessageBox.confirm(confirmConfig.message, confirmConfig.title, {
      type: 'warning',
      confirmButtonText: confirmConfig.confirmButtonText,
      cancelButtonText: '取消'
    })
    await localRepoRevert(selectedRepoPath.value, [file.path])
    ElMessage.success(confirmConfig.successMessage)
    await loadRepoStatus()
  } catch (e) {
    if (e !== 'cancel') {
      ElMessage.error(e.message || confirmConfig.failureMessage)
    }
  }
}

async function handleAddFile(file) {
  try {
    await svnAdd(currentRepoId.value, [file.path])
    ElMessage.success(`已将「${file.path}」加入版本控制`)
    await loadRepoStatus()
  } catch (e) {
    ElMessage.error(e.message || '加入版本控制失败')
  }
}

function handleResolveConflict(file) {
  currentConflictFile.value = file.path
  conflictDialogVisible.value = true
}

async function handleOpenFolder(file) {
  if (!file || !file.path) return
  const folderPath = file.path.includes('\\')
    ? file.path.substring(0, file.path.lastIndexOf('\\'))
    : file.path.substring(0, file.path.lastIndexOf('/'))
  const fullPath = folderPath.startsWith(selectedRepoPath.value)
    ? folderPath
    : `${selectedRepoPath.value}\\${folderPath}`

  try {
    await openFolder(fullPath)
    ElMessage.success('已打开文件夹')
  } catch (error) {
    ElMessage.error(error.message || '打开文件夹失败')
  }
}

async function handleConflictResolved() {
  conflictDialogVisible.value = false
  ElMessage.success('冲突已解决')
  await loadRepoStatus()
}

async function loadOperationHistory() {
  if (!currentRepoId.value) return
  historyLoading.value = true
  try {
    const res = await getRecentOperationLogs(currentRepoId.value, 20)
    operationLogs.value = res.data || []
  } catch (e) {
    console.error('加载操作历史失败', e)
  } finally {
    historyLoading.value = false
  }
}

function scrollToSection(section) {
  activeSection.value = section
  if (!activeCollapse.value.includes(section)) {
    activeCollapse.value = [...activeCollapse.value, section]
  }
  nextTick(() => {
    const el = document.getElementById(`section-${section}`)
    if (el) {
      el.scrollIntoView({ behavior: 'smooth', block: 'start' })
    }
  })
}

function getOverallStatusType(status) {
  const map = { clean: 'success', modified: 'warning', conflict: 'danger', outdated: 'info' }
  return map[status] || 'info'
}

function getOverallStatusLabel(status) {
  const map = { clean: '干净', modified: '有修改', conflict: '有冲突', outdated: '需要更新', unknown: '未知' }
  return map[status] || '未知'
}

function getStatusType(status) {
  const map = { modified: 'warning', added: 'success', deleted: 'danger', conflicted: 'danger', unversioned: 'info' }
  return map[status] || 'info'
}

function getStatusLabel(status) {
  const map = { modified: 'M', added: 'A', deleted: 'D', conflicted: 'C', unversioned: '?' }
  return map[status] || status
}

function formatTime(timeStr) {
  if (!timeStr) return ''
  try {
    const date = new Date(timeStr)
    return date.toLocaleString('zh-CN', {
      year: 'numeric', month: '2-digit', day: '2-digit',
      hour: '2-digit', minute: '2-digit', second: '2-digit'
    })
  } catch (e) {
    return timeStr
  }
}

function initializeRepoSelection() {
  const routeRepoId = String(route.params.repoId || '').trim()
  if (routeRepoId && applyRepoSelectionById(routeRepoId)) {
    return
  }

  const savedRepoId = localStorage.getItem('svn_platform_last_repo_id')
  if (savedRepoId && applyRepoSelectionById(savedRepoId)) {
    return
  }

  applyRepoSelection(repos.value[0])
}

function applyRepoSelectionById(repoId) {
  const repo = repos.value.find(item => String(item.id) === String(repoId))
  if (!repo) return false

  applyRepoSelection(repo)
  return true
}

function applyRepoSelection(repo) {
  selectedRepoPath.value = repo.localPath
  currentRepoId.value = repo.id
  localStorage.setItem('svn_platform_last_repo_id', String(repo.id))
}

async function applyRoutePostActions() {
  await nextTick()

  if (route.query.focus === 'conflicted') {
    scrollToSection('conflicted')
  }

  const filePath = String(route.query.filePath || '').trim()
  if (!filePath) return

  const conflictedFile = fileGroups.value.conflicted.find(file => file.path === filePath)
  if (conflictedFile) {
    handleResolveConflict(conflictedFile)
  }
}
</script>

<style scoped>
.local-repo {
  flex: 1;
  height: 100%;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  min-width: 0;
  min-height: 0;
}

/* 工具栏 */
.toolbar {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
  flex-shrink: 0;
}

.repo-block {
  flex: 1;
  min-width: 260px;
  max-width: 420px;
}

.repo-select {
  width: 100%;
}

.repo-select :deep(.el-input__wrapper) {
  min-height: 42px;
}

.separator {
  width: 1px;
  height: 20px;
  background: rgba(159, 180, 214, 0.28);
  margin: 0 4px;
  flex-shrink: 0;
}

/* 仓库信息卡片 */
.repo-info-card {
  flex-shrink: 0;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 16px;
}

.info-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.info-label {
  font-size: 11px;
  font-weight: 600;
  color: var(--app-accent-strong);
  letter-spacing: 0.5px;
}

.info-value {
  font-size: 13px;
  color: var(--app-text);
  font-weight: 500;
}

.path-value {
  font-family: 'JetBrains Mono', monospace;
  font-size: 12px;
  color: var(--app-text-soft);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.rev-value {
  font-family: 'JetBrains Mono', monospace;
  color: var(--app-accent);
  font-weight: 600;
}

/* 状态统计卡片 */
.status-cards {
  display: grid;
  flex-shrink: 0;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  cursor: pointer;
  transition: all 0.25s;
  min-width: 180px;
  border: 1px solid var(--app-border);
  border-radius: 18px;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.96) 0%, rgba(244, 249, 255, 0.92) 100%);
  box-shadow: var(--app-shadow-soft);
}

.stat-card:hover {
  transform: translateY(-2px);
  border-color: rgba(51, 143, 255, 0.3);
  box-shadow: var(--app-shadow-glow);
}

.stat-card.active {
  border-color: rgba(51, 143, 255, 0.34);
  background: linear-gradient(135deg, rgba(51, 143, 255, 0.14), rgba(32, 203, 184, 0.12));
  box-shadow: 0 0 0 1px rgba(51, 143, 255, 0.18);
}

.stat-icon {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  font-weight: 700;
  color: #fff;
  flex-shrink: 0;
}

.stat-modified { background: #e6a23c; }
.stat-added { background: #67c23a; }
.stat-deleted { background: #f56c6c; }
.stat-conflicted { background: #f56c6c; }
.stat-unversioned { background: #909399; }

.stat-info {
  display: flex;
  flex-direction: column;
}

.stat-count {
  font-size: 20px;
  font-weight: 700;
  color: var(--app-text);
  font-family: 'JetBrains Mono', monospace;
}

.stat-label {
  font-size: 12px;
  color: var(--app-text-soft);
  font-weight: 500;
}

/* 文件状态区域 */
.file-status-section {
  flex: 1;
  min-height: 0;
}

.status-group {
  margin-bottom: 8px;
}

.status-group :deep(.el-collapse) {
  border: none;
}

.status-group :deep(.el-collapse-item__header) {
  background: rgba(243, 248, 255, 0.92);
  border-radius: 12px;
  padding: 0 16px;
  border: 1px solid rgba(159, 180, 214, 0.2);
  height: 44px;
  line-height: 44px;
}

.status-group :deep(.el-collapse-item__content) {
  padding: 8px 0;
}

.collapse-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  font-weight: 600;
  color: var(--app-text);
}

.file-list {
  padding-left: 8px;
}

.file-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 12px;
  border-radius: 6px;
  transition: background-color 0.15s;
  min-width: 0;
}

.file-item:hover {
  background: rgba(51, 143, 255, 0.06);
}

.conflicted-item {
  background: rgba(239, 68, 68, 0.08);
}

.conflicted-item:hover {
  background: rgba(239, 68, 68, 0.12);
}

.file-icon {
  font-size: 16px;
  flex-shrink: 0;
}

.file-icon.modified { color: #e6a23c; }
.file-icon.added { color: #67c23a; }
.file-icon.deleted { color: #f56c6c; }
.file-icon.conflicted { color: #f56c6c; }
.file-icon.unversioned { color: #909399; }

.file-path {
  flex: 1;
  font-size: 13px;
  font-family: 'JetBrains Mono', monospace;
  color: var(--app-text-soft);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.file-actions {
  display: flex;
  gap: 4px;
  flex-shrink: 0;
}

.empty-state,
.no-repo-state {
  padding: 40px;
  text-align: center;
}

/* 操作历史 */
.operation-history {
  flex-shrink: 0;
  max-height: 200px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.history-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 8px;
  flex-shrink: 0;
}

.history-title {
  font-size: 13px;
  font-weight: 700;
  color: var(--app-text);
}

.history-list {
  flex: 1;
  overflow-y: auto;
  min-height: 0;
}

.history-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 0;
  border-bottom: 1px solid #f5f5f5;
  font-size: 12px;
}

.history-item:last-child {
  border-bottom: none;
}

.history-message {
  flex: 1;
  color: var(--app-text-soft);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.history-time {
  color: var(--app-text-muted);
  font-family: 'JetBrains Mono', monospace;
  font-size: 11px;
  flex-shrink: 0;
}

.history-empty {
  text-align: center;
  color: var(--app-text-muted);
  padding: 12px;
  font-size: 12px;
}

/* 提交对话框 */
.commit-files-list {
  max-height: 250px;
  overflow-y: auto;
  border: 1px solid var(--app-border);
  border-radius: 12px;
  padding: 10px;
  background: rgba(247, 250, 255, 0.82);
}

.commit-file-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 4px 0;
  font-size: 12px;
}

@media (max-width: 1280px) {
  .repo-block {
    max-width: none;
    width: 100%;
  }

  .toolbar {
    align-items: stretch;
  }

  .toolbar .el-button {
    flex: 1 1 auto;
  }

  .status-cards {
    grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
  }

  .file-item {
    flex-wrap: wrap;
  }

  .file-path {
    min-width: 220px;
  }

  .file-actions {
    width: 100%;
    justify-content: flex-end;
  }

  .history-item {
    flex-wrap: wrap;
  }

  .history-message {
    width: 100%;
  }
}
</style>
