<template>
  <div class="svn-status app-page">
    <!-- 顶部操作栏 -->
    <el-card class="top-bar page-shell page-shell--padded">
      <div class="top-bar-content page-toolbar">
        <div class="selector-left page-toolbar__main">
          <span class="selector-label">当前仓库：</span>
          <el-select v-model="selectedRepoId" placeholder="请选择要操作的仓库"
            style="width: 320px" @change="handleRepoChange" size="default">
            <el-option v-for="repo in repos" :key="repo.id"
              :label="`${repo.name} (${repo.localPath})`" :value="repo.id" />
          </el-select>
          <el-tag v-if="currentRepo" type="info" size="small">r{{ currentRepo.lastRevision || '?' }}</el-tag>
        </div>
        <div class="action-buttons page-toolbar__actions">
          <el-button type="primary" @click="handleCommit"
            :disabled="checkedFiles.length === 0 && changelists.length === 0">
            <el-icon><Upload /></el-icon> 提交
          </el-button>
          <el-button type="success" @click="handleUpdate">
            <el-icon><Download /></el-icon> 更新
          </el-button>
          <el-button type="warning" @click="handleRevert" :disabled="checkedVersionedFiles.length === 0">
            <el-icon><RefreshLeft /></el-icon> 还原
          </el-button>
          <el-button @click="handleCleanup" title="清理工作副本">
            <el-icon><Brush /></el-icon>
          </el-button>
          <el-button @click="loadStatus" title="刷新">
            <el-icon><Refresh /></el-icon>
          </el-button>
        </div>
      </div>
    </el-card>

    <!-- 主体：左右分栏 -->
    <div class="main-content page-responsive-row" v-if="selectedRepoId">
      <!-- 左侧：Changelist树形文件列表 -->
      <TreePanel
        class="status-tree-panel"
        ref="treePanelRef"
        :tree-data="treeData"
        :loading="loading"
        :expanded-keys="expandedKeys"
        :changelists="changelists"
        :active-changelist="activeChangelist"
        :status-count="statusList.length"
        :checked-versioned-count="checkedVersionedFiles.length"
        :checked-unversioned-count="checkedUnversionedFiles.length"
        :status-list="statusList"
        @node-click="handleNodeClick"
        @check-change="handleCheckChange"
        @node-contextmenu="handleNodeContextMenu"
        @node-expand="handleNodeExpand"
        @node-collapse="handleNodeCollapse"
        @create-changelist="showCreateChangelist"
        @refresh="loadStatus"
        @add-single="addSingleFile"
        @revert-single="revertSingle"
        @add-to-version-control="handleAddToVersionControl"
        @move-to-changelist="moveToChangelist"
        @remove-from-changelist="removeFromCurrentChangelist"
        @filter-change="handleFilterChange"
        @search-change="handleSearchChange"
      />

      <!-- 右侧：Diff预览 -->
      <PreviewPanel
        class="status-preview-panel"
        :active-file="activeFile"
        :diff-loaded="diffLoaded"
        :diff-original="diffOriginal"
        :diff-modified="diffModified"
      />
    </div>

    <!-- 右键菜单 -->
    <ContextMenu
      ref="contextMenuRef"
      :group-menu="contextMenu"
      :file-menu="fileContextMenu"
      :changelists="changelists"
      :active-changelist="activeChangelist"
      @group-move-to="doMoveToChangelist"
      @group-move-to-new="doMoveToNewChangelist"
      @set-active="doSetActiveChangelist"
      @edit-name="doEditChangelistName"
      @remove-changelist="doRemoveChangelist"
      @file-add-to-version="fileMenuAddToVersion"
      @file-move-to="fileMenuMoveToChangelist"
      @file-move-to-new="fileMenuMoveToNewChangelist"
      @file-remove-from-changelist="fileMenuRemoveFromChangelist"
      @file-view-diff="fileMenuViewDiff"
      @file-revert="fileMenuRevert"
      @file-resolve-conflict="fileMenuResolveConflict"
    />

    <!-- 提交对话框 -->
    <el-dialog v-model="commitDialogVisible" title="提交代码" width="650px">
      <el-form>
        <el-form-item label="提交方式" v-if="changelists.length > 0">
          <el-radio-group v-model="commitMode">
            <el-radio label="files">按选中文件</el-radio>
            <el-radio label="changelist">按Changelist</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Changelist" v-if="commitMode === 'changelist'">
          <el-select v-model="commitTargetChangelist" placeholder="选择要提交的Changelist" style="width: 100%">
            <el-option v-for="cl in changelists" :key="cl" :label="`${cl} (${getChangelistFileCount(cl)}个文件)`" :value="cl" />
          </el-select>
        </el-form-item>
        <el-form-item label="提交信息" required>
          <div class="commit-message-section">
            <RequirementTemplateManager
              ref="templateManagerRef"
              v-model="commitMessage"
              :repo-id="selectedRepoId"
              @use-template="handleUseTemplate"
            />
            <el-input v-model="commitMessage" type="textarea" :rows="4"
              placeholder="请输入提交说明... 点击模板自动填充，或从需求列表选择" class="commit-textarea" />
            <div class="commit-message-actions">
              <el-button size="small" type="primary" plain @click="saveCommitMessageAsRequirement">
                保存为需求模板
              </el-button>
              <span class="commit-message-hint">仅保存需求模板，不会执行 SVN 提交</span>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="文件列表" v-if="commitMode === 'files'">
          <div class="commit-files-list">
            <div v-for="file in checkedFiles" :key="file.path" class="commit-file-item">
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

    <!-- 更新对话框 -->
    <el-dialog v-model="updateDialogVisible" title="更新代码" width="400px">
      <el-form>
        <el-form-item label="目标版本">
          <el-input v-model="updateRevision" placeholder="留空则更新到最新(HEAD)" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="updateDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="doUpdate" :loading="updating">确认更新</el-button>
      </template>
    </el-dialog>

    <!-- 新建Changelist对话框 -->
    <el-dialog v-model="clDialogVisible" title="新建Changelist分组" width="400px">
      <el-form>
        <el-form-item label="分组名称">
          <el-input v-model="newChangelistName" placeholder="如: bugfix-1234, feature-login" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="clDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="doCreateChangelist">确定</el-button>
      </template>
    </el-dialog>

    <!-- 任务进度 -->
    <el-dialog v-model="taskDialogVisible" title="执行中..." width="400px"
      :close-on-click-modal="false" :close-on-press-escape="false">
      <div class="task-progress">
        <el-progress :percentage="taskProgress" :status="taskProgressStatus" />
        <p class="task-message">{{ taskMessage }}</p>
      </div>
    </el-dialog>

    <ConflictResolver
      v-if="mergeDialogVisible"
      :visible="mergeDialogVisible"
      :file-path="mergeFilePath"
      :conflict-files="conflictedFilePaths"
      :repo-id="selectedRepoId"
      @close="mergeDialogVisible = false"
      @resolved="handleConflictResolved"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount, watch, nextTick } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getRepoList, getRepo, getSvnStatus, getFileContent,
  svnCommit, svnUpdate, svnRevert, svnCleanup, svnAdd,
  addToChangelist, removeFromChangelist, commitByChangelist,
  renameChangelist, pollTaskStatus
} from '@/api'
import { getLastRepoId, saveLastRepo } from '@/utils/storage'
import { buildRequirementPayloadFromMessage } from '@/utils/requirement'
import { getConflictedFilePaths } from '@/utils/status'
import TreePanel from './components/TreePanel.vue'
import PreviewPanel from './components/PreviewPanel.vue'
import ContextMenu from './components/ContextMenu.vue'
import ConflictResolver from '@/components/ConflictResolver.vue'
import RequirementTemplateManager from './components/RequirementTemplateManager.vue'

const route = useRoute()
const router = useRouter()
const treePanelRef = ref(null)
const contextMenuRef = ref(null)
const templateManagerRef = ref(null)

// 基础状态
const repos = ref([])
const currentRepo = ref(null)
const selectedRepoId = ref(null)
const statusList = ref([])
const loading = ref(false)

// 树形数据
const treeData = ref([])
const expandedKeys = ref([])
const checkedFiles = ref([])
const checkedVersionedFiles = computed(() => checkedFiles.value.filter(f => f.status !== 'unversioned'))
const checkedUnversionedFiles = computed(() => checkedFiles.value.filter(f => f.status === 'unversioned'))
const conflictedFilePaths = computed(() => getConflictedFilePaths(statusList.value))
const activeFile = ref(null)

// 状态筛选
const activeStatusFilters = ref([])

// 搜索
const searchKeyword = ref('')

// Changelist
const changelists = ref([])
const clDialogVisible = ref(false)
const newChangelistName = ref('')

// Diff
const diffOriginal = ref('')
const diffModified = ref('')
const diffLoaded = ref(false)

// 提交
const commitDialogVisible = ref(false)
const commitMessage = ref('')

function insertTemplate(template) {
  const chineseBracketEnd = commitMessage.value.indexOf('】')
  const asciiBracketEnd = commitMessage.value.indexOf('] ')

  if (commitMessage.value.startsWith('【') && chineseBracketEnd !== -1) {
    commitMessage.value = template + commitMessage.value.substring(chineseBracketEnd + 1)
  } else if (commitMessage.value.startsWith('[') && asciiBracketEnd !== -1) {
    commitMessage.value = template + commitMessage.value.substring(asciiBracketEnd + 2)
  } else {
    commitMessage.value = template + commitMessage.value
  }
}
const commitMode = ref('files')
const commitTargetChangelist = ref('')
const committing = ref(false)

// 更新
const updateDialogVisible = ref(false)
const updateRevision = ref('')
const updating = ref(false)

// 任务进度
const taskDialogVisible = ref(false)
const taskProgress = ref(0)
const taskMessage = ref('')
const taskProgressStatus = ref('')

// 右键菜单状态
const contextMenu = ref({ visible: false, x: 0, y: 0, data: null })
const fileContextMenu = ref({ visible: false, x: 0, y: 0, data: null })
const activeChangelist = ref(localStorage.getItem('svn_active_changelist') || '')

onMounted(async () => {
  document.addEventListener('click', onDocumentClick)
  document.addEventListener('contextmenu', onDocumentClick)

  const res = await getRepoList()
  repos.value = res.data || []

  if (route.params.repoId) {
    selectedRepoId.value = Number(route.params.repoId)
  } else {
    const lastId = getLastRepoId()
    if (lastId && repos.value.find(r => r.id === lastId)) {
      selectedRepoId.value = lastId
    }
  }

  if (selectedRepoId.value) {
    await loadRepoInfo()
    await loadStatus()
  }
})

watch(() => route.params.repoId, async (newId) => {
  if (newId) {
    selectedRepoId.value = Number(newId)
    await loadRepoInfo()
    await loadStatus()
  }
})

async function handleRepoChange(repoId) {
  await loadRepoInfo()
  await loadStatus()
  if (currentRepo.value) saveLastRepo(currentRepo.value)
  router.replace(`/svn/status/${repoId}`)
}

async function loadRepoInfo() {
  if (!selectedRepoId.value) return
  try {
    const res = await getRepo(selectedRepoId.value)
    currentRepo.value = res.data
    saveLastRepo(res.data)
  } catch (e) {
    console.error(e)
  }
}

async function loadStatus() {
  if (!selectedRepoId.value) return
  loading.value = true
  activeFile.value = null
  diffLoaded.value = false
  try {
    const res = await getSvnStatus(selectedRepoId.value)
    statusList.value = res.data || []

    // 提取changelists
    const clSet = new Set()
    statusList.value.forEach(item => {
      if (item.changelist) clSet.add(item.changelist)
    })
    changelists.value = [...clSet]

    // 构建树形数据
    buildTreeData()
  } catch (e) {
    ElMessage.error(e.message || '获取状态失败')
  } finally {
    loading.value = false
  }
}

// === 树形数据构建 ===

function buildTreeData() {
  const tree = []

  const filteredStatusList = activeStatusFilters.value.length > 0
    ? statusList.value.filter(f => activeStatusFilters.value.includes(f.status))
    : statusList.value

  const searchedStatusList = searchKeyword.value.trim()
    ? filteredStatusList.filter(f => 
        f.path.toLowerCase().includes(searchKeyword.value.toLowerCase()) ||
        f.path.split(/[/\\]/).pop().toLowerCase().includes(searchKeyword.value.toLowerCase())
      )
    : filteredStatusList

  const groups = {}
  const ungrouped = []
  const unversioned = []

  searchedStatusList.forEach(file => {
    if (file.status === 'unversioned') {
      unversioned.push(file)
    } else if (file.changelist) {
      if (!groups[file.changelist]) groups[file.changelist] = []
      groups[file.changelist].push(file)
    } else {
      ungrouped.push(file)
    }
  })

  for (const [clName, files] of Object.entries(groups)) {
    tree.push({
      id: `cl_${clName}`,
      label: clName,
      isGroup: true,
      isFile: false,
      changelist: clName,
      fileCount: files.length,
      children: buildFolderTree(files, clName)
    })
  }

  if (ungrouped.length > 0) {
    if (changelists.value.length > 0 || unversioned.length > 0) {
      tree.push({
        id: 'cl_ungrouped',
        label: '未分组',
        isGroup: true,
        isFile: false,
        changelist: null,
        fileCount: ungrouped.length,
        children: buildFolderTree(ungrouped, null)
      })
    } else {
      tree.push(...buildFolderTree(ungrouped, null))
    }
  }

  if (unversioned.length > 0) {
    tree.push({
      id: 'cl_unversioned',
      label: '未版本化',
      isGroup: true,
      isFile: false,
      isUnversioned: true,
      changelist: '__unversioned__',
      fileCount: unversioned.length,
      children: buildFolderTree(unversioned, '__unversioned__')
    })
  }

  tree.sort((a, b) => {
    if (a.isUnversioned) return 1
    if (b.isUnversioned) return -1
    if (a.changelist === null) return 1
    if (b.changelist === null) return -1
    if (a.changelist === activeChangelist.value) return -1
    if (b.changelist === activeChangelist.value) return 1
    return 0
  })

  treeData.value = tree
  restoreExpandedState(tree)
}

function buildFolderTree(files, changelist) {
  if (files.length === 0) return []

  const pathParts = files.map(f => ({
    parts: f.path.split(/[/\\]/),
    file: f
  }))

  const root = {}
  pathParts.forEach(({ parts, file }) => {
    let current = root
    for (let i = 0; i < parts.length; i++) {
      const part = parts[i]
      if (i === parts.length - 1) {
        if (!current._files) current._files = []
        current._files.push({ name: part, file })
      } else {
        if (!current[part]) current[part] = {}
        current = current[part]
      }
    }
  })

  return convertToTreeNodes(root, changelist, '')
}

function convertToTreeNodes(node, changelist, prefix) {
  const result = []

  const dirKeys = Object.keys(node).filter(k => k !== '_files')
  for (const dir of dirKeys) {
    const subNode = node[dir]
    const subDirKeys = Object.keys(subNode).filter(k => k !== '_files')
    const subFiles = subNode._files || []

    if (subDirKeys.length === 1 && subFiles.length === 0) {
      let compressedPath = dir
      let current = subNode
      while (true) {
        const nextDirs = Object.keys(current).filter(k => k !== '_files')
        const nextFiles = current._files || []
        if (nextDirs.length === 1 && nextFiles.length === 0) {
          compressedPath += '\\' + nextDirs[0]
          current = current[nextDirs[0]]
        } else {
          break
        }
      }
      const children = convertToTreeNodes(current, changelist, '')
      const fileCount = countFiles(current)
      result.push({
        id: `dir_${changelist}_${prefix}${compressedPath}`,
        label: compressedPath,
        isFolder: true,
        isFile: false,
        fileCount: fileCount,
        children: children
      })
    } else {
      const children = convertToTreeNodes(subNode, changelist, prefix + dir + '\\')
      const fileCount = countFiles(subNode)
      result.push({
        id: `dir_${changelist}_${prefix}${dir}`,
        label: dir,
        isFolder: true,
        isFile: false,
        fileCount: fileCount,
        children: children
      })
    }
  }

  const fileItems = node._files || []
  for (const { name, file } of fileItems) {
    result.push({
      id: `file_${file.path}`,
      label: name,
      isFile: true,
      isFolder: false,
      path: file.path,
      status: file.status,
      changelist: file.changelist || null,
      fileData: file
    })
  }

  return result
}

function countFiles(node) {
  let count = 0
  if (node._files) count += node._files.length
  const dirs = Object.keys(node).filter(k => k !== '_files')
  for (const d of dirs) {
    count += countFiles(node[d])
  }
  return count
}

// === 展开/折叠状态管理 ===

function getStorageKey() {
  return `svn_expanded_${selectedRepoId.value}`
}

function saveExpandedState() {
  try {
    sessionStorage.setItem(getStorageKey(), JSON.stringify(expandedKeys.value))
  } catch (e) { /* ignore */ }
}

function restoreExpandedState(tree) {
  try {
    const saved = sessionStorage.getItem(getStorageKey())
    if (saved) {
      expandedKeys.value = JSON.parse(saved)
    } else {
      expandedKeys.value = getAllNodeIds(tree)
    }
  } catch (e) {
    expandedKeys.value = getAllNodeIds(tree)
  }
}

function getAllNodeIds(nodes) {
  const ids = []
  function walk(list) {
    for (const node of list) {
      if (node.children && node.children.length > 0) {
        ids.push(node.id)
        walk(node.children)
      }
    }
  }
  walk(nodes)
  return ids
}

function handleNodeExpand(payload) {
  // 来自 expandAll：payload 是完整 id 数组
  if (Array.isArray(payload)) {
    expandedKeys.value = payload
  } else {
    if (!expandedKeys.value.includes(payload)) {
      expandedKeys.value.push(payload)
    }
  }
  saveExpandedState()
}

function handleNodeCollapse(payload) {
  // 来自 collapseAll：payload 是空数组
  if (Array.isArray(payload)) {
    expandedKeys.value = []
  } else {
    expandedKeys.value = expandedKeys.value.filter(id => id !== payload)
  }
  saveExpandedState()
}

// === 状态筛选 ===

function handleFilterChange(filters) {
  activeStatusFilters.value = filters
  checkedFiles.value = []
  buildTreeData()
}

// === 搜索 ===

function handleSearchChange(keyword) {
  searchKeyword.value = keyword
  checkedFiles.value = []
  buildTreeData()
}

// === 树节点交互 ===

function handleNodeClick(data) {
  closeContextMenu()
  if (data.isFile && data.status === 'conflicted') {
    openConflictResolver(data.path)
  } else if (data.isFile && canPreview(data)) {
    loadFileDiff(data)
  } else if (data.isFile) {
    activeFile.value = data
    diffLoaded.value = false
  }
}

function canPreview(data) {
  const binaryExts = ['.jar', '.class', '.war', '.zip', '.rar', '.7z', '.gz', '.tar',
    '.png', '.jpg', '.jpeg', '.gif', '.bmp', '.ico', '.svg', '.webp',
    '.exe', '.dll', '.so', '.dylib', '.pdf', '.doc', '.docx', '.xls', '.xlsx',
    '.ppt', '.pptx', '.woff', '.woff2', '.ttf', '.eot', '.mp3', '.mp4', '.avi']
  const ext = (data.path || '').toLowerCase().match(/\.[^.]+$/)?.[0] || ''
  if (binaryExts.includes(ext)) return false
  return ['modified', 'added', 'deleted', 'conflicted', 'unversioned'].includes(data.status)
}

function handleCheckChange(files) {
  checkedFiles.value = files
}

async function loadFileDiff(fileNode) {
  activeFile.value = fileNode
  diffLoaded.value = false
  diffOriginal.value = ''
  diffModified.value = ''

  try {
    const res = await getFileContent(selectedRepoId.value, fileNode.path)
    diffOriginal.value = res.data.original || ''
    diffModified.value = res.data.modified || ''
    diffLoaded.value = true
  } catch (e) {
    ElMessage.error(e.message || '获取文件内容失败')
  }
}

// === Changelist 操作 ===

function showCreateChangelist() {
  newChangelistName.value = ''
  clDialogVisible.value = true
}

async function doCreateChangelist() {
  if (!newChangelistName.value.trim()) {
    ElMessage.warning('请输入分组名称')
    return
  }
  try {
    const files = checkedFiles.value
      .filter(f => f.status !== 'unversioned')
      .map(f => f.path)
    if (files.length === 0) {
      ElMessage.warning('请先勾选要移入分组的版本化文件（未版本化文件不能加入更改列表）')
      return
    }
    await addToChangelist(selectedRepoId.value, {
      changelist: newChangelistName.value.trim(),
      files
    })
    ElMessage.success(`已创建分组 "${newChangelistName.value}" 并添加 ${files.length} 个文件`)
    clDialogVisible.value = false
    loadStatus()
  } catch (e) {
    ElMessage.error(e.message || '操作失败')
  }
}

async function moveToChangelist(clName) {
  try {
    const files = checkedFiles.value
      .filter(f => f.status !== 'unversioned')
      .map(f => f.path)
    if (files.length === 0) {
      ElMessage.warning('没有可操作的版本化文件')
      return
    }
    await addToChangelist(selectedRepoId.value, { changelist: clName, files })
    ElMessage.success(`已移入 "${clName}"`)
    loadStatus()
  } catch (e) {
    ElMessage.error(e.message || '操作失败')
  }
}

async function removeFromCurrentChangelist() {
  try {
    const files = checkedVersionedFiles.value.map(f => f.path)
    if (files.length === 0) return
    await removeFromChangelist(selectedRepoId.value, { files })
    ElMessage.success('已移出分组')
    loadStatus()
  } catch (e) {
    ElMessage.error(e.message || '操作失败')
  }
}

function getChangelistFileCount(cl) {
  return statusList.value.filter(f => f.changelist === cl).length
}

// === 右键菜单 ===

function handleNodeContextMenu(event, data, node) {
  if (data.isGroup && !data.isUnversioned) {
    event.preventDefault()
    event.stopPropagation()
    contextMenu.value = { visible: true, x: event.clientX, y: event.clientY, data }
    fileContextMenu.value.visible = false
    return
  }
  if (data.isFile) {
    event.preventDefault()
    event.stopPropagation()
    fileContextMenu.value = { visible: true, x: event.clientX, y: event.clientY, data }
    contextMenu.value.visible = false
    return
  }
}

function closeContextMenu() {
  contextMenu.value.visible = false
  fileContextMenu.value.visible = false
  if (contextMenuRef.value) contextMenuRef.value.closeSubmenus()
}

function onDocumentClick() {
  closeContextMenu()
}

onBeforeUnmount(() => {
  document.removeEventListener('click', onDocumentClick)
  document.removeEventListener('contextmenu', onDocumentClick)
})

// === 分组右键菜单操作 ===

async function doMoveToChangelist(targetCl) {
  closeContextMenu()
  const sourceData = contextMenu.value.data
  if (!sourceData) return

  try {
    const files = statusList.value
      .filter(f => (f.changelist || null) === sourceData.changelist && f.status !== 'unversioned')
      .map(f => f.path)
    if (files.length === 0) {
      ElMessage.warning('没有可操作的版本化文件（未版本化文件不能加入更改列表）')
      return
    }
    await addToChangelist(selectedRepoId.value, { changelist: targetCl, files })
    ElMessage.success(`已将 ${files.length} 个文件移入 "${targetCl}"`)
    loadStatus()
  } catch (e) {
    ElMessage.error(e.message || '移入失败')
  }
}

async function doMoveToNewChangelist() {
  closeContextMenu()
  const sourceData = contextMenu.value.data
  if (!sourceData) return

  try {
    const { value } = await ElMessageBox.prompt('请输入新的更改列表名称', '新建更改列表', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputPattern: /\S+/,
      inputErrorMessage: '名称不能为空'
    })
    if (!value || !value.trim()) return

    const files = statusList.value
      .filter(f => (f.changelist || null) === sourceData.changelist && f.status !== 'unversioned')
      .map(f => f.path)
    if (files.length === 0) {
      ElMessage.warning('没有可操作的版本化文件（未版本化文件不能加入更改列表）')
      return
    }
    await addToChangelist(selectedRepoId.value, { changelist: value.trim(), files })
    ElMessage.success(`已将 ${files.length} 个文件移入新列表 "${value.trim()}"`)
    loadStatus()
  } catch (e) {
    if (e !== 'cancel' && e?.action !== 'cancel') {
      ElMessage.error(e.message || '操作失败')
    }
  }
}

function doSetActiveChangelist() {
  closeContextMenu()
  const sourceData = contextMenu.value.data
  if (!sourceData || !sourceData.changelist) return

  activeChangelist.value = sourceData.changelist
  localStorage.setItem('svn_active_changelist', sourceData.changelist)
  ElMessage.success(`已将 "${sourceData.changelist}" 设为活跃更改列表`)
  buildTreeData()
}

async function doEditChangelistName() {
  closeContextMenu()
  const sourceData = contextMenu.value.data
  if (!sourceData || !sourceData.changelist) return

  try {
    const { value } = await ElMessageBox.prompt('请输入新的更改列表名称', '编辑更改列表名称', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputValue: sourceData.changelist,
      inputPattern: /\S+/,
      inputErrorMessage: '名称不能为空'
    })
    if (!value || value.trim() === sourceData.changelist) return

    await renameChangelist(selectedRepoId.value, sourceData.changelist, value.trim())
    if (activeChangelist.value === sourceData.changelist) {
      activeChangelist.value = value.trim()
      localStorage.setItem('svn_active_changelist', value.trim())
    }
    ElMessage.success(`已重命名为 "${value.trim()}"`)
    loadStatus()
  } catch (e) {
    if (e !== 'cancel' && e?.action !== 'cancel') {
      ElMessage.error(e.message || '重命名失败')
    }
  }
}

async function doRemoveChangelist() {
  closeContextMenu()
  const sourceData = contextMenu.value.data
  if (!sourceData || !sourceData.changelist) return

  try {
    await ElMessageBox.confirm(
      `确定要移除更改列表 "${sourceData.changelist}" 吗？文件将移回"未分组"。`,
      '确认移除', { type: 'warning' }
    )
    const files = statusList.value
      .filter(f => f.changelist === sourceData.changelist)
      .map(f => f.path)
    if (files.length === 0) return

    await removeFromChangelist(selectedRepoId.value, { files })
    if (activeChangelist.value === sourceData.changelist) {
      activeChangelist.value = ''
      localStorage.removeItem('svn_active_changelist')
    }
    ElMessage.success(`已移除更改列表 "${sourceData.changelist}"`)
    loadStatus()
  } catch (e) {
    if (e !== 'cancel' && e?.action !== 'cancel') {
      ElMessage.error(e.message || '移除失败')
    }
  }
}

// === 文件右键菜单操作 ===

async function fileMenuAddToVersion() {
  closeContextMenu()
  const data = fileContextMenu.value.data
  if (!data) return
  try {
    await svnAdd(selectedRepoId.value, [data.path])
    ElMessage.success(`已将「${data.label}」加入版本控制`)
    loadStatus()
  } catch (e) {
    ElMessage.error(e.message || '加入版本控制失败')
  }
}

async function fileMenuMoveToChangelist(clName) {
  closeContextMenu()
  const data = fileContextMenu.value.data
  if (!data) return
  try {
    await addToChangelist(selectedRepoId.value, { changelist: clName, files: [data.path] })
    ElMessage.success(`已移入 "${clName}"`)
    loadStatus()
  } catch (e) {
    ElMessage.error(e.message || '移入失败')
  }
}

async function fileMenuMoveToNewChangelist() {
  closeContextMenu()
  const data = fileContextMenu.value.data
  if (!data) return
  try {
    const { value } = await ElMessageBox.prompt('请输入更改列表名称', '新建更改列表', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputPattern: /\S+/,
      inputErrorMessage: '名称不能为空'
    })
    if (!value || !value.trim()) return
    await addToChangelist(selectedRepoId.value, { changelist: value.trim(), files: [data.path] })
    ElMessage.success(`已移入新列表 "${value.trim()}"`)
    loadStatus()
  } catch (e) {
    if (e !== 'cancel' && e?.action !== 'cancel') {
      ElMessage.error(e.message || '操作失败')
    }
  }
}

async function fileMenuRemoveFromChangelist() {
  closeContextMenu()
  const data = fileContextMenu.value.data
  if (!data) return
  try {
    await removeFromChangelist(selectedRepoId.value, { files: [data.path] })
    ElMessage.success('已移出更改列表')
    loadStatus()
  } catch (e) {
    ElMessage.error(e.message || '移出失败')
  }
}

function fileMenuViewDiff() {
  closeContextMenu()
  const data = fileContextMenu.value.data
  if (!data) return
  loadFileDiff(data)
}

function fileMenuResolveConflict() {
  closeContextMenu()
  const data = fileContextMenu.value.data
  if (!data) return
  openConflictResolver(data.path)
}

async function fileMenuRevert() {
  closeContextMenu()
  const data = fileContextMenu.value.data
  if (!data) return
  try {
    await ElMessageBox.confirm(`确定要还原「${data.label}」吗？此操作不可恢复。`, '确认还原', { type: 'warning' })
    await svnRevert(selectedRepoId.value, { files: [data.path] })
    ElMessage.success('还原成功')
    loadStatus()
  } catch (e) {
    if (e !== 'cancel' && e?.action !== 'cancel') {
      ElMessage.error(e.message || '还原失败')
    }
  }
}

// === 状态标签（提交对话框用） ===
function getStatusType(status) {
  const map = { modified: 'warning', added: 'success', deleted: 'danger', unversioned: 'info', conflicted: 'danger', missing: 'danger' }
  return map[status] || 'info'
}
function getStatusLabel(status) {
  const map = { modified: 'M', added: 'A', deleted: 'D', unversioned: '?', conflicted: 'C', missing: '!' }
  return map[status] || status
}

// === 提交 ===
function handleCommit() {
  commitMessage.value = ''
  commitMode.value = checkedFiles.value.length > 0 ? 'files' : 'changelist'
  commitTargetChangelist.value = changelists.value[0] || ''
  commitDialogVisible.value = true
  
  // 应用默认模板（第一条需求模板）
  nextTick(() => {
    if (templateManagerRef.value) {
      const defaultFormat = templateManagerRef.value.getDefaultFormat()
      if (defaultFormat) {
        commitMessage.value = defaultFormat
      }
    }
  })
}

function handleUseTemplate(req) {
  // 需求模板使用后的回调，可以在这里记录日志等
}

async function doCommit() {
  if (!commitMessage.value.trim()) {
    ElMessage.warning('请填写提交说明')
    return
  }
  committing.value = true
  try {
    let res
    if (commitMode.value === 'changelist' && commitTargetChangelist.value) {
      res = await commitByChangelist(selectedRepoId.value, {
        message: commitMessage.value,
        changelist: commitTargetChangelist.value
      })
    } else {
      const files = checkedFiles.value.map(f => f.path)
      res = await svnCommit(selectedRepoId.value, { message: commitMessage.value, files, autoAdd: true })
    }
    // 解析提交信息并更新需求模板
    await persistRequirementFromCommitMessage(commitMessage.value, { silent: true })

    commitDialogVisible.value = false
    await showTaskProgress('正在提交...', res.data.taskId, '提交成功')
    loadStatus()
  } catch (e) {
    ElMessage.error(e.message || '提交失败')
  } finally {
    committing.value = false
  }
}

async function saveCommitMessageAsRequirement() {
  await persistRequirementFromCommitMessage(commitMessage.value, { silent: false })
}

// 解析提交信息并更新需求模板
async function persistRequirementFromCommitMessage(message, { silent = false } = {}) {
  const payload = buildRequirementPayloadFromMessage(message)
  if (!payload) {
    if (!silent) {
      ElMessage.warning('提交信息需符合格式：【类型】#需求ID 需求标题')
    }
    return false
  }

  if (!templateManagerRef.value) {
    if (!silent) {
      ElMessage.error('需求模板管理器尚未初始化')
    }
    return false
  }

  try {
    const saved = await templateManagerRef.value.saveRequirement(payload)
    if (saved && !silent) {
      ElMessage.success(`已保存需求模板 #${saved.requirementId}`)
    }
    return Boolean(saved)
  } catch (e) {
    if (!silent) {
      ElMessage.error(e.response?.data?.message || e.message || '保存需求模板失败')
    }
    return false
  }
}

// === 更新 ===
function handleUpdate() {
  updateRevision.value = ''
  updateDialogVisible.value = true
}

async function doUpdate() {
  updating.value = true
  try {
    const revision = updateRevision.value ? Number(updateRevision.value) : null
    const res = await svnUpdate(selectedRepoId.value, revision)
    updateDialogVisible.value = false
    await showTaskProgress('正在更新...', res.data.taskId, '更新成功')
    loadStatus()
  } catch (e) {
    ElMessage.error(e.message || '更新失败')
  } finally {
    updating.value = false
  }
}

// === 还原 ===
async function handleRevert() {
  const count = checkedFiles.value.length
  try {
    await ElMessageBox.confirm(`确定要还原选中的 ${count} 个文件吗？此操作不可恢复。`, '确认还原', { type: 'warning' })
    const files = checkedFiles.value.map(f => f.path)
    await svnRevert(selectedRepoId.value, { files })
    ElMessage.success('还原成功')
    loadStatus()
  } catch (e) {
    if (e !== 'cancel') ElMessage.error(e.message || '还原失败')
  }
}

async function revertSingle(data) {
  try {
    await ElMessageBox.confirm(`确定要还原「${data.label}」吗？`, '确认还原', { type: 'warning' })
    await svnRevert(selectedRepoId.value, { files: [data.path] })
    ElMessage.success('还原成功')
    loadStatus()
  } catch (e) {
    if (e !== 'cancel') ElMessage.error(e.message || '还原失败')
  }
}

// === 加入版本控制 ===
async function addSingleFile(data) {
  try {
    await svnAdd(selectedRepoId.value, [data.path])
    ElMessage.success(`已将「${data.label}」加入版本控制`)
    loadStatus()
  } catch (e) {
    ElMessage.error(e.message || '加入版本控制失败')
  }
}

async function handleAddToVersionControl() {
  const files = checkedUnversionedFiles.value.map(f => f.path)
  if (files.length === 0) return
  try {
    await svnAdd(selectedRepoId.value, files)
    ElMessage.success(`已将 ${files.length} 个文件加入版本控制`)
    loadStatus()
  } catch (e) {
    ElMessage.error(e.message || '加入版本控制失败')
  }
}

// === 清理 ===
async function handleCleanup() {
  try {
    await svnCleanup(selectedRepoId.value)
    ElMessage.success('清理完成')
  } catch (e) {
    ElMessage.error(e.message || '清理失败')
  }
}

// === 任务进度封装 ===
async function showTaskProgress(msg, taskId, successMsg) {
  taskMessage.value = msg
  taskProgress.value = 0
  taskProgressStatus.value = ''
  taskDialogVisible.value = true
  try {
    await pollTaskStatus(taskId, (task) => { taskProgress.value = task.progress })
    taskProgressStatus.value = 'success'
    taskMessage.value = successMsg + '！'
    ElMessage.success(successMsg)
    setTimeout(() => { taskDialogVisible.value = false }, 1200)
  } catch (e) {
    taskProgressStatus.value = 'exception'
    taskMessage.value = '失败: ' + (e.message || '')
    throw e
  }
}

// === 三方合并编辑器 ===
const mergeDialogVisible = ref(false)
const mergeFilePath = ref('')
async function openConflictResolver(filePath) {
  mergeFilePath.value = filePath
  mergeDialogVisible.value = true
}

async function handleConflictResolved() {
  mergeDialogVisible.value = false
  ElMessage.success('冲突已解决')
  await loadStatus()
}
</script>

<style scoped>
.svn-status {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 0;
  overflow: hidden;
}

.top-bar {
  flex-shrink: 0;
}

.top-bar-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
  min-width: 0;
}

.selector-left {
  display: flex;
  align-items: center;
  gap: 10px;
  min-width: 0;
  flex-wrap: wrap;
}

.selector-label {
  font-weight: 600;
  color: var(--app-text-soft);
  white-space: nowrap;
}

.action-buttons {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.main-content {
  flex: 1;
  min-height: 0;
  flex-wrap: nowrap;
  align-items: stretch;
}

.status-tree-panel {
  flex: 0 0 clamp(360px, 39%, 520px);
  min-width: 320px;
  max-width: 520px;
}

.status-preview-panel {
  flex: 1 1 0;
  min-width: 0;
}

@media (max-width: 1200px) {
  .main-content {
    flex-wrap: wrap;
  }

  .status-tree-panel,
  .status-preview-panel {
    flex: 1 1 100%;
    max-width: none;
    min-width: 0;
  }
}

/* 提交文件列表 */
.commit-files-list {
  max-height: 200px;
  overflow-y: auto;
  border: 1px solid var(--app-border);
  border-radius: 12px;
  padding: 10px;
  background: rgba(247, 250, 255, 0.82);
}

/* 提交信息区域 */
.commit-message-section {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.template-buttons {
  display: flex;
  gap: 4px;
  flex-wrap: wrap;
}

.commit-textarea {
  width: 100%;
}

.commit-message-actions {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
}

.commit-message-hint {
  font-size: 12px;
  color: var(--app-text-muted);
}

.commit-file-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 6px 0;
  font-size: 13px;
}

.task-progress {
  text-align: center;
  padding: 20px;
}

.task-message {
  margin-top: 15px;
  color: var(--app-text-soft);
}
</style>
