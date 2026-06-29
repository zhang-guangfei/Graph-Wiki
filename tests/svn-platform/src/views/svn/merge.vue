<template>
  <div class="svn-merge app-page">
    <!-- 工具栏 -->
    <div class="toolbar page-shell page-shell--padded page-toolbar">
      <div class="repo-block">
        <el-select v-model="sourceRepoId" placeholder="请选择源仓库" class="repo-select"
                   @change="handleSourceRepoChange" filterable>
          <el-option v-for="repo in repos" :key="repo.id"
                     :label="repo.name" :value="repo.id" />
        </el-select>
      </div>
      <div class="repo-block-arrow">
        <div class="arrow-icon">→</div>
      </div>
      <div class="repo-block">
        <el-select v-model="targetRepoId" placeholder="请选择目标仓库" class="repo-select"
                   @change="handleTargetRepoChange" filterable>
          <el-option v-for="repo in repos" :key="repo.id"
                     :label="repo.name" :value="repo.id" :disabled="repo.id === sourceRepoId" />
        </el-select>
      </div>
      <div class="separator"></div>
      <div class="filter-chip" :class="{ active: mergeStatusFilter === 'all' }" @click="mergeStatusFilter = 'all'">
        全部
      </div>
      <div class="filter-chip" :class="{ active: mergeStatusFilter === 'unmerged' }" @click="mergeStatusFilter = 'unmerged'">
        未合并
      </div>
      <div class="filter-chip" :class="{ active: mergeStatusFilter === 'merged' }" @click="mergeStatusFilter = 'merged'">
        已合并
      </div>
      <div class="separator"></div>
      <div class="filter-chip-wrapper">
        <el-select v-model="requirementTypeFilter" placeholder="需求类型" class="filter-select"
          clearable @change="handleRequirementTypeFilterChange">
          <el-option
            v-for="type in REQUIREMENT_TYPE_OPTIONS"
            :key="type"
            :label="type"
            :value="type"
          />
        </el-select>
      </div>
      <div class="filter-chip-wrapper">
        <el-select v-model="requirementFilters" placeholder="按需求筛选" class="filter-select filter-select--requirements"
          multiple collapse-tags collapse-tags-tooltip
          clearable filterable @change="handleFilterChange">
          <el-option v-for="req in filteredRequirements" :key="req.requirementId"
            :label="`#${req.requirementId} ${req.title}`" :value="req.requirementId">
            <div class="requirement-option">
              <el-tag :type="getRequirementTypeColor(req.type)" size="small">{{ req.type || '-' }}</el-tag>
              <span class="req-info">#{{ req.requirementId }} {{ req.title }}</span>
            </div>
          </el-option>
        </el-select>
      </div>
      <div class="separator"></div>
      <div class="filter-chip-wrapper">
        <el-select v-model="loadLimit" placeholder="查询条数" class="filter-select" size="small"
          @change="handleLimitChange">
          <el-option label="100条" :value="100" />
          <el-option label="200条" :value="200" />
          <el-option label="500条" :value="500" />
          <el-option label="1000条" :value="1000" />
          <el-option label="2000条" :value="2000" />
        </el-select>
      </div>
      <div class="separator"></div>
      <input 
        class="search-box" 
        v-model="searchKeyword" 
        placeholder="搜索提交人/信息/版本号" 
        @keyup.enter="handleFilterChange"
      >
      <div class="separator"></div>
      <div class="filter-chip reset-btn" @click="handleResetFilter" title="重置筛选">⟲</div>
      <div class="separator"></div>
      <div class="filter-chip reset-btn" @click="handleUpdateRepos" title="更新仓库到最新">↻</div>
    </div>

    <div v-if="activeRequirementFilters.length > 0" class="requirement-strip page-shell page-shell--padded">
      <div class="requirement-strip__header">
        <span class="requirement-strip__title">当前需求范围</span>
        <span class="requirement-strip__meta">已选 {{ activeRequirementFilters.length }} 个需求，命中 {{ filteredLogs.length }} 条版本</span>
      </div>
      <div class="requirement-strip__tags">
        <div
          v-for="item in activeRequirementFilters"
          :key="item.requirementId"
          class="requirement-strip__tag"
        >
          <el-tag :type="getRequirementTypeColor(item.type)" size="small">{{ item.type || '-' }}</el-tag>
          <span class="requirement-strip__id">#{{ item.requirementId }}</span>
          <span class="requirement-strip__name">{{ item.title }}</span>
        </div>
      </div>
    </div>

    <!-- 表格 -->
    <div class="table-section page-shell workspace-card">
      <div class="table-header">
        <span class="table-title">合并源提交记录</span>
        <span class="table-count">
          共 {{ filteredLogs.length }} 条
          <span v-if="selectedRevisions.length > 0">，已选 {{ selectedRevisions.length }} 个版本</span>
        </span>
      </div>
      <div class="table-wrapper">
        <el-table 
          ref="mergeTableRef"
          :data="filteredLogs" 
          stripe 
          v-loading="loading"
          @selection-change="handleSelectionChange"
          :row-key="row => row.revision"
          style="width: 100%" 
          empty-text="暂无提交记录"
          class="merge-table"
          :height="tableHeight"
        >
          <template #empty>
            <div class="empty-state">暂无提交记录</div>
          </template>

          <el-table-column type="selection" width="50" :selectable="checkSelectable" />

          <el-table-column label="版本" width="120" sortable>
            <template #default="{ row }">
              <span class="rev-tag">{{ row.revision }}</span>
            </template>
          </el-table-column>
          
          <el-table-column label="作者" width="120" sortable>
            <template #default="{ row }">
              <div class="author-cell">
                <div class="author-avatar" :style="{ backgroundColor: getAuthorColor(row.author) }">
                  {{ getAuthorInitial(row.author) }}
                </div>
                <span class="author-name">{{ row.author }}</span>
              </div>
            </template>
          </el-table-column>
          
          <el-table-column label="日期" width="200" sortable>
            <template #default="{ row }">
              <span class="date-cell">{{ formatDate(row.date) }}</span>
            </template>
          </el-table-column>

          <el-table-column label="需求" min-width="220" show-overflow-tooltip>
            <template #default="{ row }">
              <div class="requirement-cell" v-if="row.requirementId">
                <el-tag :type="getRequirementTypeColor(row.requirementType)" size="small">
                  {{ row.requirementType || '-' }}
                </el-tag>
                <span class="requirement-cell__id">#{{ row.requirementId }}</span>
                <span class="requirement-cell__title">{{ row.requirementTitle || row.message }}</span>
              </div>
              <span v-else class="no-data">未关联需求</span>
            </template>
          </el-table-column>
          
          <el-table-column prop="message" label="提交信息" show-overflow-tooltip min-width="150" max-width="200" />
          
          <el-table-column label="已合并" width="150" align="center">
            <template #default="{ row }">
              <span v-if="row.merged" class="status-badge merged">已合并</span>
              <span v-else-if="targetRepoId" class="status-badge unmerged">未合并</span>
              <span v-else class="no-data">-</span>
            </template>
          </el-table-column>
          
          <el-table-column label="操作" width="150" fixed="right" align="center">
            <template #default="{ row }">
              <button class="btn-diff" @click="viewDiff(row)">查看Diff</button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="table-footer">
        <div class="selection-summary" v-if="selectedRange">
          <span class="selection-info">
          已选版本: <strong>{{ selectedRange.range }}</strong>（共 {{ selectedRange.total }} 个版本，其中 {{ selectedRange.unmerged }} 个未合并）
          </span>
          <span class="selection-subinfo" v-if="selectedRequirementSummary.requirementCount > 0">
            涉及 {{ selectedRequirementSummary.requirementCount }} 个需求
          </span>
        </div>
        <span class="selection-info" v-else>
          请勾选需要合并的版本
        </span>
        <div class="action-options">
          <el-checkbox v-model="skipFailedRevisions" class="skip-checkbox">跳过冲突版本继续合并</el-checkbox>
          <div class="action-buttons">
            <button class="btn-primary btn-preview" @click="handlePreviewMerge" :disabled="selectedRevisions.length === 0">预览合并</button>
            <button class="btn-primary btn-merge" @click="handleExecuteMerge" :disabled="selectedRevisions.length === 0">执行合并</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 预览结果对话框 -->
    <el-dialog v-model="previewDialogVisible" title="合并预览" width="80%" top="5vh">
      <div v-if="previewLoading" class="preview-loading">
        <el-skeleton :rows="8" animated />
      </div>
      <div v-else class="preview-content">
        <!-- 结构化预览 -->
        <div v-if="parsedPreview" class="structured-preview">
          <div v-if="selectedRequirementSummary.requirementCount > 0" class="preview-scope">
            <div class="preview-scope__header">
              <span class="preview-scope__title">本次合并范围</span>
              <span class="preview-scope__meta">
                {{ selectedRequirementSummary.requirementCount }} 个需求 / {{ selectedRequirementSummary.revisionCount }} 个版本
              </span>
            </div>
            <div class="preview-scope__stats">
              <div class="preview-stat">
                <span class="preview-stat__label">需求数</span>
                <strong class="preview-stat__value">{{ selectedRequirementSummary.requirementCount }}</strong>
              </div>
              <div class="preview-stat">
                <span class="preview-stat__label">版本数</span>
                <strong class="preview-stat__value">{{ selectedRequirementSummary.revisionCount }}</strong>
              </div>
              <div class="preview-stat">
                <span class="preview-stat__label">已合并</span>
                <strong class="preview-stat__value">{{ selectedRequirementSummary.mergedCount }}</strong>
              </div>
              <div class="preview-stat">
                <span class="preview-stat__label">待合并</span>
                <strong class="preview-stat__value">{{ selectedRequirementSummary.unmergedCount }}</strong>
              </div>
            </div>
            <div class="preview-scope__list">
              <div
                v-for="group in selectedRequirementSummary.groups"
                :key="group.requirementId || 'ungrouped'"
                class="preview-scope__item"
              >
                <div class="preview-scope__item-main">
                  <el-tag v-if="group.requirementId" :type="getRequirementTypeColor(group.requirementType)" size="small">
                    {{ group.requirementType || '-' }}
                  </el-tag>
                  <span class="preview-scope__item-id">{{ group.requirementId ? '#' + group.requirementId : '未关联需求' }}</span>
                  <span class="preview-scope__item-title">{{ group.requirementTitle || '-' }}</span>
                </div>
                <span class="preview-scope__item-meta">
                  {{ group.revisionCount }} 个版本: {{ formatRevisionList(group.revisions) }}
                </span>
              </div>
            </div>
          </div>

          <!-- 摘要卡片 -->
          <div class="summary-cards">
            <div class="summary-card added">
              <div class="card-count">{{ parsedPreview.added.length }}</div>
              <div class="card-label">新增文件</div>
            </div>
            <div class="summary-card modified">
              <div class="card-count">{{ parsedPreview.modified.length }}</div>
              <div class="card-label">修改文件</div>
            </div>
            <div class="summary-card deleted">
              <div class="card-count">{{ parsedPreview.deleted.length }}</div>
              <div class="card-label">删除文件</div>
            </div>
            <div class="summary-card conflicted">
              <div class="card-count">{{ parsedPreview.conflicted.length }}</div>
              <div class="card-label">冲突文件</div>
            </div>
          </div>

          <!-- 文件列表分组 -->
          <div class="file-lists">
            <!-- 新增文件 -->
            <div v-if="parsedPreview.added.length > 0" class="file-group">
              <div class="file-group-header">
                <el-tag type="success" size="small">新增</el-tag>
                <span class="group-title">{{ parsedPreview.added.length }} 个新增文件</span>
              </div>
              <div class="file-list">
                <div v-for="file in parsedPreview.added" :key="file" class="file-item">
                  <span class="file-path">{{ file }}</span>
                </div>
              </div>
            </div>

            <!-- 修改文件 -->
            <div v-if="parsedPreview.modified.length > 0" class="file-group">
              <div class="file-group-header">
                <el-tag type="warning" size="small">修改</el-tag>
                <span class="group-title">{{ parsedPreview.modified.length }} 个修改文件</span>
              </div>
              <div class="file-list">
                <div v-for="file in parsedPreview.modified" :key="file" class="file-item">
                  <span class="file-path">{{ file }}</span>
                </div>
              </div>
            </div>

            <!-- 删除文件 -->
            <div v-if="parsedPreview.deleted.length > 0" class="file-group">
              <div class="file-group-header">
                <el-tag type="danger" size="small">删除</el-tag>
                <span class="group-title">{{ parsedPreview.deleted.length }} 个删除文件</span>
              </div>
              <div class="file-list">
                <div v-for="file in parsedPreview.deleted" :key="file" class="file-item">
                  <span class="file-path">{{ file }}</span>
                </div>
              </div>
            </div>

            <!-- 冲突文件 -->
            <div v-if="parsedPreview.conflicted.length > 0" class="file-group conflicted-group">
              <div class="file-group-header">
                <el-tag type="danger" size="small">冲突</el-tag>
                <span class="group-title">{{ parsedPreview.conflicted.length }} 个冲突文件</span>
              </div>
              <div class="file-list">
                <div v-for="file in parsedPreview.conflicted" :key="file" class="file-item conflicted-item">
                  <span class="file-path">{{ file }}</span>
                  <el-button 
                    size="small" 
                    type="primary" 
                    @click="goToResolveConflict(file)"
                  >
                    去解决冲突
                  </el-button>
                </div>
              </div>
            </div>

            <!-- 无变更提示 -->
            <div v-if="parsedPreview.totalCount === 0" class="no-changes">
              <el-empty :description="previewEmptyDescription" />
            </div>
          </div>
        </div>

        <!-- 解析失败或原始输出 -->
        <div v-if="previewRawOutputVisible" class="raw-output-section">
          <el-collapse>
            <el-collapse-item title="原始输出">
              <pre class="preview-text">{{ previewResult }}</pre>
            </el-collapse-item>
          </el-collapse>
        </div>
      </div>
      <template #footer>
        <el-button @click="previewDialogVisible = false">关闭</el-button>
        <el-button 
          v-if="parsedPreview && parsedPreview.conflicted.length === 0" 
          type="primary" 
          @click="handleExecuteMerge"
        >
          确认合并
        </el-button>
        <el-button 
          v-if="parsedPreview && parsedPreview.conflicted.length > 0" 
          type="danger" 
          @click="goToResolveConflict"
        >
          去解决冲突 ({{ parsedPreview.conflicted.length }})
        </el-button>
      </template>
    </el-dialog>

    <!-- Diff查看对话框 -->
    <el-dialog 
      v-model="diffDialogVisible" 
      title="提交Diff" 
      width="90%" 
      top="3vh" 
      :close-on-click-modal="false"
      class="diff-dialog"
    >
      <div v-if="diffLoading" class="diff-loading">
        <el-skeleton :rows="10" animated />
      </div>
      
      <div v-else-if="diffFiles.length > 0" class="diff-dialog-container">
        <div class="diff-sidebar">
          <div class="diff-sidebar-header">
            <span class="sidebar-version">r{{ currentDiffLog?.revision }}</span>
            <span class="sidebar-count">{{ diffFiles.length }} 个文件</span>
          </div>
          <div class="diff-file-list">
            <div 
              v-for="file in diffFiles" 
              :key="file.path"
              :class="['diff-file-item', { active: currentDiffFile === file.path }]"
              @click="selectDiffFile(file.path)"
            >
              <el-tag :type="getActionType(file.action)" size="small" class="diff-action-tag">
                {{ getActionLabel(file.action) }}
              </el-tag>
              <span class="diff-file-name" :title="file.path">{{ getFileName(file.path) }}</span>
            </div>
          </div>
        </div>
        
        <div class="diff-main">
          <div class="diff-main-header">
            <span class="current-file-path" :title="currentDiffFile">{{ currentDiffFile }}</span>
            <div class="diff-main-actions">
              <el-button size="small" @click="prevDiffFile" :disabled="currentFileIndex <= 0">
                <el-icon><ArrowLeft /></el-icon>
              </el-button>
              <span class="file-nav">{{ currentFileIndex + 1 }} / {{ diffFiles.length }}</span>
              <el-button size="small" @click="nextDiffFile" :disabled="currentFileIndex >= diffFiles.length - 1">
                <el-icon><ArrowRight /></el-icon>
              </el-button>
            </div>
          </div>
          <div class="diff-editor-wrapper">
            <DiffViewer 
              :key="currentDiffFile"
              :original="diffOriginal" 
              :modified="diffModified" 
              :file-name="currentDiffFile"
            />
          </div>
        </div>
      </div>
      
      <el-empty v-else description="暂无Diff内容" />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch, onUnmounted, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getRepoList, getSvnLogWithFilters, getLogChanges, getFileDiffContent, listRequirements, previewMerge, executeMerge, getMergedRevisions, svnUpdate, pollTaskStatus } from '@/api'
import { REQUIREMENT_TYPE_OPTIONS, getRequirementTypeColor } from '@/utils/requirement'
import { buildRequirementLookup, enrichMergeLogs, filterMergeLogs, summarizeRequirementSelection } from '@/utils/merge'
import { buildLocalRepoConflictRoute } from '@/utils/navigation'
import DiffViewer from '@/components/DiffViewer.vue'

const router = useRouter()

const repos = ref([])
const sourceRepoId = ref(localStorage.getItem('mergeSourceRepo') ? Number(localStorage.getItem('mergeSourceRepo')) : null)
const targetRepoId = ref(localStorage.getItem('mergeTargetRepo') ? Number(localStorage.getItem('mergeTargetRepo')) : null)
const sourceLogs = ref([])
const loading = ref(false)
const requirements = ref([])

// 筛选条件
const mergeStatusFilter = ref('all')
const searchKeyword = ref('')
const requirementFilters = ref([])
const requirementTypeFilter = ref('')
const mergeTableRef = ref(null)

// 按类型筛选后的需求列表
const filteredRequirements = computed(() => {
  if (!requirementTypeFilter.value) {
    return requirements.value
  }
  return requirements.value.filter(req => req.type === requirementTypeFilter.value)
})

const requirementLookup = computed(() => buildRequirementLookup(requirements.value))

const activeRequirementFilters = computed(() => {
  const selectedIds = new Set(requirementFilters.value)
  return filteredRequirements.value.filter(req => selectedIds.has(req.requirementId))
})

function handleRequirementTypeFilterChange() {
  requirementFilters.value = []
  handleFilterChange()
}

// 加载限制
const loadLimit = ref(100)
const tableHeight = ref(300)

// 选中版本
const selectedRevisions = ref([])

// Diff相关
const diffDialogVisible = ref(false)
const diffLoading = ref(false)
const currentDiffLog = ref(null)
const currentDiffFile = ref('')
const diffFiles = ref([])
const diffOriginal = ref('')
const diffModified = ref('')
const currentFileIndex = ref(0)

// 预览相关
const previewDialogVisible = ref(false)
const previewLoading = ref(false)
const previewResult = ref('')

// 解析后的预览数据
const parsedPreview = computed(() => {
  if (!previewResult.value || previewResult.value.includes('预览失败') || previewResult.value.includes('正在预览')) {
    return null
  }

  const added = []
  const modified = []
  const deleted = []
  const conflicted = []

  const lines = previewResult.value.split('\n')
  
  for (const line of lines) {
    const trimmed = line.trim()
    
    // 匹配冲突文件: "C    path/to/file"
    const conflictMatch = trimmed.match(/^C\s+(.+)/)
    if (conflictMatch) {
      conflicted.push(conflictMatch[1].trim())
      continue
    }

    // 匹配新增文件: "A    path/to/file"
    const addMatch = trimmed.match(/^A\s+(.+)/)
    if (addMatch) {
      added.push(addMatch[1].trim())
      continue
    }

    // 匹配修改文件: "U    path/to/file" 或 "G    path/to/file"
    const modifyMatch = trimmed.match(/^[UG]\s+(.+)/)
    if (modifyMatch) {
      modified.push(modifyMatch[1].trim())
      continue
    }

    // 匹配删除文件: "D    path/to/file"
    const deleteMatch = trimmed.match(/^D\s+(.+)/)
    if (deleteMatch) {
      deleted.push(deleteMatch[1].trim())
      continue
    }

    // 匹配替换文件: "R    path/to/file"
    const replaceMatch = trimmed.match(/^R\s+(.+)/)
    if (replaceMatch) {
      modified.push(replaceMatch[1].trim())
      continue
    }
  }

  // 如果解析到任何文件，返回结构化数据
  const totalCount = added.length + modified.length + deleted.length + conflicted.length
  if (totalCount > 0) {
    return {
      added,
      modified,
      deleted,
      conflicted,
      totalCount
    }
  }

  // 解析失败，返回空数据结构
  return {
    added: [],
    modified: [],
    deleted: [],
    conflicted: [],
    totalCount: 0
  }
})

const previewResultText = computed(() => String(previewResult.value || '').trim())

const previewEmptyDescription = computed(() => {
  if (!previewResultText.value) {
    return '预览已完成，但 SVN 未返回文件级变更明细'
  }

  if (previewResultText.value.startsWith('预览完成')) {
    return previewResultText.value.split('\n')[0]
  }

  return '本次合并没有可解析的文件级变更内容'
})

const previewRawOutputVisible = computed(() => {
  if (!parsedPreview.value || parsedPreview.value.totalCount === 0) {
    return previewResultText.value.length > 0
  }

  return false
})

// 跳过失败版本选项
const skipFailedRevisions = ref(false)

// 已合并版本集合
const mergedRevisions = ref(new Set())

onMounted(async () => {
  const res = await getRepoList()
  repos.value = res.data || []

  try {
    const reqRes = await listRequirements()
    requirements.value = reqRes.data || []
  } catch (e) {
    console.warn('加载需求列表失败', e)
  }

  if (sourceRepoId.value && targetRepoId.value) {
    await loadSourceLogs()
    await loadMergedRevisionsData()
  }

  calcTableHeight()
  window.addEventListener('resize', calcTableHeight)
})

onUnmounted(() => {
  window.removeEventListener('resize', calcTableHeight)
})

function calcTableHeight() {
  // 根据页面高度计算表格高度
  const windowHeight = window.innerHeight
  const offset = 318
  tableHeight.value = Math.max(400, windowHeight - offset)
}

watch([sourceRepoId, targetRepoId], async ([newSource, newTarget]) => {
  if (newSource) {
    await loadSourceLogs()
  }
  if (newSource && newTarget) {
    await loadMergedRevisionsData()
  }
})

async function loadSourceLogs() {
  if (!sourceRepoId.value) return
  loading.value = true
  try {
    const res = await getSvnLogWithFilters(sourceRepoId.value, { limit: loadLimit.value })
    sourceLogs.value = res.data || []
  } catch (e) {
    ElMessage.error(e.message || '加载提交记录失败')
    sourceLogs.value = []
  } finally {
    loading.value = false
  }
}

async function loadMergedRevisionsData() {
  if (!sourceRepoId.value || !targetRepoId.value) {
    mergedRevisions.value = new Set()
    return
  }
  try {
    const res = await getMergedRevisions(sourceRepoId.value, targetRepoId.value)
    // 统一转为 Number 类型，确保与提交记录的 revision 类型一致
    mergedRevisions.value = new Set((res.data || []).map(r => Number(r)))
  } catch (e) {
    console.error('获取已合并版本失败', e)
    mergedRevisions.value = new Set()
  }
}

function handleSourceRepoChange(repoId) {
  localStorage.setItem('mergeSourceRepo', repoId)
  targetRepoId.value = null
  sourceLogs.value = []
  selectedRevisions.value = []
  mergedRevisions.value = new Set()
}

function handleTargetRepoChange() {
  localStorage.setItem('mergeTargetRepo', targetRepoId.value)
  selectedRevisions.value = []
}

// 计算属性
const logsWithMergedStatus = computed(() => {
  return enrichMergeLogs(sourceLogs.value, mergedRevisions.value, requirementLookup.value)
})

const filteredLogs = computed(() => {
  return filterMergeLogs(logsWithMergedStatus.value, {
    mergeStatusFilter: mergeStatusFilter.value,
    requirementIds: requirementFilters.value,
    searchKeyword: searchKeyword.value
  })
})

const selectedRange = computed(() => {
  if (selectedRevisions.value.length === 0) return null
  const revs = selectedRevisions.value.map(r => r.revision).sort((a, b) => a - b)
  const unmergedCount = selectedRevisions.value.filter(r => !r.merged).length
  
  // 如果选中的是连续版本，显示范围；否则显示离散版本列表
  const isContinuous = revs.length > 1 && revs[revs.length - 1] - revs[0] === revs.length - 1
  
  let rangeText
  if (revs.length === 1) {
    rangeText = `r${revs[0]}`
  } else if (isContinuous) {
    rangeText = `${revs[0]} ~ ${revs[revs.length - 1]}`
  } else {
    // 显示首尾版本，中间用省略号
    if (revs.length <= 5) {
      rangeText = revs.map(r => `r${r}`).join(', ')
    } else {
      rangeText = `r${revs[0]}, r${revs[1]}, ..., r${revs[revs.length - 2]}, r${revs[revs.length - 1]}`
    }
  }
  
  return {
    range: rangeText,
    total: revs.length,
    unmerged: unmergedCount
  }
})

const selectedRequirementSummary = computed(() => summarizeRequirementSelection(selectedRevisions.value))

function handleLimitChange() {
  if (sourceRepoId.value) {
    loadSourceLogs()
  }
}

function handleFilterChange() {
}

function handleResetFilter() {
  mergeStatusFilter.value = 'all'
  searchKeyword.value = ''
  requirementFilters.value = []
  requirementTypeFilter.value = ''
  loadLimit.value = 100
}

async function handleUpdateRepos() {
  if (!sourceRepoId.value || !targetRepoId.value) {
    ElMessage.warning('请先选择源仓库和目标仓库')
    return
  }
  loading.value = true
  try {
    await Promise.all([
      svnUpdate(sourceRepoId.value),
      svnUpdate(targetRepoId.value)
    ])
    ElMessage.success('仓库已更新到最新')
    await loadSourceLogs()
    await loadMergedRevisionsData()
  } catch (e) {
    ElMessage.error(e.message || '更新失败')
  } finally {
    loading.value = false
  }
}

function handleSelectionChange(selection) {
  selectedRevisions.value = selection
}

watch(filteredLogs, async (newLogs) => {
  const visibleRevisionSet = new Set(newLogs.map(log => Number(log.revision)))
  const nextSelection = selectedRevisions.value.filter(row => visibleRevisionSet.has(Number(row.revision)))

  if (nextSelection.length === selectedRevisions.value.length) return

  selectedRevisions.value = nextSelection

  await nextTick()
  if (!mergeTableRef.value) return

  mergeTableRef.value.clearSelection()
  nextSelection.forEach(row => {
    mergeTableRef.value.toggleRowSelection(row, true)
  })
})

function checkSelectable(row) {
  return true
}

// Diff功能
async function viewDiff(row) {
  currentDiffLog.value = row
  diffDialogVisible.value = true
  diffLoading.value = true
  diffFiles.value = []
  currentDiffFile.value = ''
  diffOriginal.value = ''
  diffModified.value = ''
  currentFileIndex.value = 0

  try {
    const changesRes = await getLogChanges(sourceRepoId.value, row.revision)
    diffFiles.value = changesRes.data || []

    if (diffFiles.value.length > 0) {
      currentFileIndex.value = 0
      currentDiffFile.value = diffFiles.value[0].path
      await loadFileDiff(diffFiles.value[0].path)
    }
  } catch (e) {
    ElMessage.error(e.message || '获取Diff失败')
  } finally {
    diffLoading.value = false
  }
}

async function selectDiffFile(filePath) {
  if (filePath === currentDiffFile.value) return
  currentDiffFile.value = filePath
  currentFileIndex.value = diffFiles.value.findIndex(f => f.path === filePath)
  await loadFileDiff(filePath)
}

async function loadFileDiff(filePath) {
  diffLoading.value = true
  try {
    const res = await getFileDiffContent(sourceRepoId.value, currentDiffLog.value.revision, filePath)
    diffOriginal.value = res.data?.original || ''
    diffModified.value = res.data?.modified || ''
  } catch (e) {
    ElMessage.error(e.message || '获取文件Diff失败')
    diffOriginal.value = ''
    diffModified.value = ''
  } finally {
    diffLoading.value = false
  }
}

function prevDiffFile() {
  if (currentFileIndex.value > 0) {
    currentFileIndex.value--
    selectDiffFile(diffFiles.value[currentFileIndex.value].path)
  }
}

function nextDiffFile() {
  if (currentFileIndex.value < diffFiles.value.length - 1) {
    currentFileIndex.value++
    selectDiffFile(diffFiles.value[currentFileIndex.value].path)
  }
}

function getFileName(filePath) {
  if (!filePath) return ''
  const parts = filePath.split(/[/\\]/)
  return parts[parts.length - 1]
}

function formatRevisionList(revisions) {
  const list = Array.isArray(revisions) ? revisions : []
  if (list.length <= 4) {
    return list.map(revision => `r${revision}`).join(', ')
  }
  return `${list.slice(0, 2).map(revision => `r${revision}`).join(', ')}, ..., r${list[list.length - 1]}`
}

async function handlePreviewMerge() {
  if (selectedRevisions.value.length === 0) return
  if (!sourceRepoId.value || !targetRepoId.value) {
    ElMessage.warning('请先选择源仓库和目标仓库')
    return
  }
  previewDialogVisible.value = true
  previewLoading.value = true
  previewResult.value = ''

  try {
    const revs = selectedRevisions.value.map(r => r.revision)
    const res = await previewMerge(sourceRepoId.value, targetRepoId.value, revs)
    const taskId = res.data?.taskId
    if (!taskId) {
      previewResult.value = '预览失败: 未获取到任务ID'
      return
    }

    // 轮询任务状态
    const task = await pollTaskStatus(taskId, (progressTask) => {
      previewResult.value = `正在预览合并... 进度: ${progressTask.progress}%`
    })

    previewResult.value = task.result || '无变更内容'
  } catch (e) {
    previewResult.value = '预览失败: ' + (e.message || '未知错误')
  } finally {
    previewLoading.value = false
  }
}

async function handleExecuteMerge() {
  if (selectedRevisions.value.length === 0) return
  if (!sourceRepoId.value || !targetRepoId.value) {
    ElMessage.warning({ message: '请先选择源仓库和目标仓库', duration: 0 })
    return
  }

  try {
    await ElMessageBox.confirm(
      `确认将 ${selectedRevisions.value.length} 个版本从源仓库合并到目标仓库？${skipFailedRevisions.value ? '\n（将跳过冲突版本继续合并）' : ''}`,
      '合并确认',
      { type: 'warning' }
    )
  } catch {
    return
  }

  loading.value = true
  try {
    const revs = selectedRevisions.value.map(r => r.revision)
    const res = await executeMerge(sourceRepoId.value, targetRepoId.value, revs, skipFailedRevisions.value)
    const taskId = res.data?.taskId
    if (!taskId) {
      ElMessage.error({ message: '合并失败: 未获取到任务ID', duration: 0 })
      return
    }

    // 轮询任务状态
    await pollTaskStatus(taskId, null)

    ElMessage.success('合并成功')
    await loadMergedRevisionsData()
    selectedRevisions.value = []
  } catch (e) {
    ElMessage.error({ message: e.message || '合并失败', duration: 0 })
  } finally {
    loading.value = false
  }
}


function formatDate(dateStr) {
  if (!dateStr) return ''
  try {
    const date = new Date(dateStr)
    return date.toLocaleString('zh-CN', {
      year: 'numeric', month: '2-digit', day: '2-digit',
      hour: '2-digit', minute: '2-digit', second: '2-digit'
    })
  } catch (e) {
    return dateStr
  }
}

function getAuthorColor(author) {
  if (!author) return '#2563eb'
  let hash = 0
  for (let i = 0; i < author.length; i++) {
    hash = author.charCodeAt(i) + ((hash << 5) - hash)
  }
  const hue = Math.abs(hash % 360)
  return `hsl(${hue}, 60%, 55%)`
}

function getAuthorInitial(author) {
  if (!author) return '?'
  return author.charAt(0).toUpperCase()
}

function getActionType(action) {
  const map = { A: 'success', M: 'warning', D: 'danger', R: 'info' }
  return map[action] || 'info'
}

function getActionLabel(action) {
  const map = { A: '新增', M: '修改', D: '删除', R: '替换' }
  return map[action] || action
}

function goToResolveConflict(filePath = '') {
  if (!targetRepoId.value) {
    ElMessage.warning('请先选择目标仓库')
    return
  }
  previewDialogVisible.value = false
  router.push(buildLocalRepoConflictRoute(targetRepoId.value, filePath))
}
</script>

<style scoped>
.svn-merge {
  flex: 1;
  background: transparent;
  height: 100%;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  min-height: 0;
}

/* 仓库选择 */
.repo-selector {
  display: flex;
  align-items: center;
  gap: 0;
  background: #fff;
  border-radius: 12px;
  padding: 14px 20px;
  margin-bottom: 12px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.06), 0 1px 2px rgba(0,0,0,0.04);
  border: 1px solid #f0f0f0;
  flex-shrink: 0;
}

.repo-block {
  flex: 1;
  padding: 0 16px;
  min-width: 220px;
}

.repo-block:first-child {
  padding-left: 0;
}

.repo-block-label {
  display: block;
  font-size: 11px;
  font-weight: 600;
  color: #2563eb;
  margin-bottom: 6px;
  letter-spacing: 0.5px;
}

.repo-select {
  width: 100%;
}

.repo-select :deep(.el-input__wrapper) {
  min-height: 42px;
}

.repo-block-arrow {
  display: flex;
  align-items: center;
  justify-content: center;
  min-width: 44px;
}

.arrow-icon {
  width: 50px;
  height: 36px;
  background: linear-gradient(135deg, var(--app-accent) 0%, var(--app-accent-violet) 100%);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 16px;
  box-shadow: 0 10px 24px rgba(63, 106, 242, 0.28);
}

/* 工具栏 */
.toolbar {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
  flex-shrink: 0;
}

.filter-chip {
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 6px 12px;
  border-radius: 18px;
  font-size: 12px;
  border: 1px solid rgba(159, 180, 214, 0.28);
  background: rgba(247, 250, 255, 0.9);
  color: var(--app-text-soft);
  cursor: pointer;
  transition: all 0.25s;
  font-weight: 500;
  white-space: nowrap;
}

.filter-chip:hover {
  border-color: rgba(51, 143, 255, 0.3);
  color: var(--app-accent-strong);
  transform: translateY(-1px);
}

.filter-chip.active {
  background: linear-gradient(135deg, var(--app-accent) 0%, var(--app-accent-strong) 100%);
  color: #fff;
  border-color: transparent;
  box-shadow: 0 10px 22px rgba(52, 116, 255, 0.22);
}

.filter-chip.reset-btn {
  padding: 5px 8px;
}

.filter-chip-wrapper {
  position: relative;
}

.filter-select {
  width: 120px;
}

.filter-select--requirements {
  width: 240px;
}

.requirement-strip {
  flex-shrink: 0;
}

.requirement-strip__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  flex-wrap: wrap;
  margin-bottom: 10px;
}

.requirement-strip__title {
  font-size: 13px;
  font-weight: 700;
  color: var(--app-text);
}

.requirement-strip__meta {
  font-size: 12px;
  color: var(--app-text-soft);
}

.requirement-strip__tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.requirement-strip__tag {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 8px 10px;
  border: 1px solid rgba(159, 180, 214, 0.22);
  border-radius: 14px;
  background: rgba(247, 250, 255, 0.84);
  min-width: 0;
}

.requirement-strip__id {
  font-family: 'JetBrains Mono', monospace;
  font-size: 12px;
  font-weight: 700;
  color: var(--app-accent-strong);
}

.requirement-strip__name {
  font-size: 12px;
  color: var(--app-text-soft);
}

.filter-select :deep(.el-input__wrapper) {
  min-height: 36px;
}

.search-box {
  flex: 1;
  min-width: 160px;
  padding: 6px 12px;
  border: 1px solid rgba(159, 180, 214, 0.28);
  border-radius: 18px;
  font-size: 12px;
  color: var(--app-text);
  background: rgba(247, 250, 255, 0.92);
  font-family: inherit;
  outline: none;
  transition: border-color 0.25s;
  white-space: nowrap;
}

.search-box:focus {
  border-color: var(--app-accent);
  background: #fff;
}

.search-box::placeholder {
  color: var(--app-text-muted);
}

.separator {
  width: 1px;
  height: 20px;
  background: rgba(159, 180, 214, 0.28);
  margin: 0 4px;
  flex-shrink: 0;
}

/* 表格 */
.table-section {
  overflow: hidden;
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
}

.table-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 20px;
  border-bottom: 1px solid rgba(159, 180, 214, 0.2);
  flex-shrink: 0;
  gap: 12px;
  flex-wrap: wrap;
}

.table-title {
  font-size: 14px;
  font-weight: 700;
  color: var(--app-text);
}

.table-count {
  font-size: 11px;
  color: var(--app-accent-strong);
  background: rgba(51, 143, 255, 0.08);
  padding: 4px 10px;
  border-radius: 10px;
  font-weight: 600;
}

.table-wrapper {
  overflow: auto;
  flex: 1;
  min-height: 0;
}

.empty-state {
  padding: 30px;
  text-align: center;
  color: var(--app-text-muted);
  font-size: 13px;
}

/* 覆盖 Element Plus 表格样式 */
.merge-table :deep(.el-table__header th) {
  padding: 10px 16px;
  font-size: 11px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  color: var(--app-text-soft);
  background: rgba(239, 246, 255, 0.9);
}

.merge-table :deep(.el-table__body td) {
  padding: 8px 16px;
  font-size: 12px;
}

.merge-table :deep(.el-table__row) {
  height: 40px;
}

.merge-table :deep(.el-table__row:hover) {
  background: rgba(51, 143, 255, 0.05);
}

.merge-table :deep(.el-table__row.current-row) {
  background: rgba(51, 143, 255, 0.08);
}

.rev-tag {
  font-family: 'JetBrains Mono', monospace;
  font-size: 11px;
  font-weight: 600;
  color: var(--app-accent-strong);
  background: rgba(51, 143, 255, 0.08);
  padding: 3px 8px;
  border-radius: 8px;
  display: inline-block;
}

.author-cell {
  display: flex;
  align-items: center;
  gap: 6px;
}

.author-avatar {
  width: 26px;
  height: 26px;
  border-radius: 8px;
  color: #fff;
  font-size: 10px;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 6px rgba(0,0,0,0.15);
  flex-shrink: 0;
}

.author-name {
  font-size: 12px;
  font-weight: 500;
  color: var(--app-text);
  white-space: nowrap;
}

.date-cell {
  font-family: 'JetBrains Mono', monospace;
  font-size: 11px;
  color: var(--app-text-muted);
  white-space: nowrap;
}

.requirement-cell {
  display: flex;
  align-items: center;
  gap: 8px;
  min-width: 0;
}

.requirement-cell__id {
  font-family: 'JetBrains Mono', monospace;
  font-size: 12px;
  font-weight: 700;
  color: var(--app-accent-strong);
  flex-shrink: 0;
}

.requirement-cell__title {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: var(--app-text-soft);
}

.no-data {
  color: var(--app-text-muted);
}

.status-badge {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 3px 10px;
  border-radius: 14px;
  font-size: 11px;
  font-weight: 600;
  white-space: nowrap;
}

.status-badge.merged {
  background: #e8f5e9;
  color: #2e7d32;
}

.status-badge.merged::before {
  content: '';
  width: 5px;
  height: 5px;
  border-radius: 50%;
  background: #4caf50;
}

.status-badge.unmerged {
  background: #ffebee;
  color: #c62828;
}

.status-badge.unmerged::before {
  content: '';
  width: 5px;
  height: 5px;
  border-radius: 50%;
  background: #f44336;
}

.btn-diff {
  padding: 4px 12px;
  border-radius: 10px;
  font-size: 11px;
  font-weight: 600;
  border: 1px solid rgba(51, 143, 255, 0.32);
  background: rgba(51, 143, 255, 0.06);
  color: var(--app-accent-strong);
  cursor: pointer;
  transition: all 0.25s;
  font-family: inherit;
  white-space: nowrap;
}

.btn-diff:hover {
  background: linear-gradient(135deg, var(--app-accent) 0%, var(--app-accent-strong) 100%);
  color: #fff;
  box-shadow: 0 10px 22px rgba(52, 116, 255, 0.22);
  transform: translateY(-1px);
}

.table-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 20px;
  background: rgba(243, 248, 255, 0.9);
  border-top: 1px solid rgba(159, 180, 214, 0.2);
  flex-shrink: 0;
}

.selection-info {
  font-size: 12px;
  color: var(--app-text-soft);
  font-weight: 500;
  white-space: nowrap;
}

.selection-summary {
  display: flex;
  flex-direction: column;
  gap: 4px;
  min-width: 0;
}

.selection-subinfo {
  font-size: 12px;
  color: var(--app-text-muted);
}

.selection-info strong {
  font-family: 'JetBrains Mono', monospace;
  color: var(--app-accent-strong);
  font-weight: 700;
}

.action-options {
  display: flex;
  align-items: center;
  gap: 16px;
  flex-shrink: 0;
  flex-wrap: wrap;
}

.skip-checkbox :deep(.el-checkbox__label) {
  font-size: 12px;
  color: var(--app-text-soft);
  font-weight: 500;
  white-space: nowrap;
}

.action-buttons {
  display: flex;
  gap: 8px;
  flex-shrink: 0;
  flex-wrap: wrap;
}

.btn-primary {
  padding: 8px 20px;
  border-radius: 10px;
  font-size: 12px;
  font-weight: 700;
  border: none;
  cursor: pointer;
  transition: all 0.25s;
  font-family: inherit;
  white-space: nowrap;
}

.btn-primary:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  transform: none !important;
}

.btn-merge {
  background: linear-gradient(135deg, var(--app-accent) 0%, var(--app-accent-strong) 100%);
  color: #fff;
  box-shadow: 0 10px 24px rgba(52, 116, 255, 0.22);
}

.btn-merge:hover:not(:disabled) {
  box-shadow: 0 14px 28px rgba(52, 116, 255, 0.3);
  transform: translateY(-2px);
}

.btn-preview {
  background: rgba(255, 255, 255, 0.95);
  color: var(--app-accent-strong);
  border: 1px solid rgba(159, 180, 214, 0.28);
}

.btn-preview:hover:not(:disabled) {
  border-color: rgba(51, 143, 255, 0.28);
  box-shadow: 0 10px 18px rgba(52, 116, 255, 0.12);
  transform: translateY(-1px);
}

/* Diff对话框 */
.diff-dialog-container {
  display: flex;
  height: 80vh;
  border: 1px solid rgba(159, 180, 214, 0.24);
  border-radius: 18px;
  overflow: hidden;
}

.diff-sidebar {
  width: 280px;
  border-right: 1px solid rgba(159, 180, 214, 0.2);
  display: flex;
  flex-direction: column;
  background: rgba(243, 248, 255, 0.9);
  flex-shrink: 0;
}

.diff-sidebar-header {
  padding: 12px 16px;
  border-bottom: 1px solid rgba(159, 180, 214, 0.2);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-shrink: 0;
}

.sidebar-version {
  font-size: 14px;
  font-weight: 600;
  color: var(--app-text);
  font-family: 'JetBrains Mono', monospace;
}

.sidebar-count {
  color: var(--app-text-muted);
  font-size: 12px;
}

.diff-file-list {
  flex: 1;
  overflow-y: auto;
  padding: 6px;
}

.diff-file-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 10px;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.15s;
  margin-bottom: 2px;
}

.diff-file-item:hover {
  background: rgba(51, 143, 255, 0.06);
}

.diff-file-item.active {
  background: rgba(51, 143, 255, 0.1);
}

.diff-action-tag {
  flex-shrink: 0;
  min-width: 40px;
  text-align: center;
}

.diff-file-name {
  flex: 1;
  font-size: 12px;
  font-family: 'JetBrains Mono', monospace;
  color: var(--app-text-soft);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.diff-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.diff-main-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 16px;
  border-bottom: 1px solid rgba(159, 180, 214, 0.2);
  background: rgba(243, 248, 255, 0.9);
  flex-shrink: 0;
}

.current-file-path {
  font-family: 'JetBrains Mono', monospace;
  font-size: 13px;
  color: var(--app-text);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 60%;
}

.diff-main-actions {
  display: flex;
  align-items: center;
  gap: 8px;
}

.file-nav {
  font-size: 12px;
  color: var(--app-text-muted);
  min-width: 60px;
  text-align: center;
}

.diff-editor-wrapper {
  flex: 1;
  min-height: 0;
}

/* 预览 */
.preview-loading {
  padding: 20px;
}

.preview-content {
  max-height: 65vh;
  overflow: auto;
}

.preview-scope {
  border: 1px solid rgba(159, 180, 214, 0.2);
  border-radius: 16px;
  padding: 16px;
  background: rgba(245, 249, 255, 0.9);
}

.preview-scope__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  flex-wrap: wrap;
  margin-bottom: 14px;
}

.preview-scope__title {
  font-size: 14px;
  font-weight: 700;
  color: var(--app-text);
}

.preview-scope__meta {
  font-size: 12px;
  color: var(--app-text-soft);
}

.preview-scope__stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: 10px;
  margin-bottom: 14px;
}

.preview-stat {
  display: flex;
  flex-direction: column;
  gap: 4px;
  padding: 10px 12px;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.9);
  border: 1px solid rgba(159, 180, 214, 0.16);
}

.preview-stat__label {
  font-size: 12px;
  color: var(--app-text-soft);
}

.preview-stat__value {
  font-family: 'JetBrains Mono', monospace;
  font-size: 18px;
  color: var(--app-text);
}

.preview-scope__list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.preview-scope__item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  padding: 10px 12px;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.88);
  border: 1px solid rgba(159, 180, 214, 0.16);
  flex-wrap: wrap;
}

.preview-scope__item-main {
  display: flex;
  align-items: center;
  gap: 8px;
  min-width: 0;
  flex-wrap: wrap;
}

.preview-scope__item-id {
  font-family: 'JetBrains Mono', monospace;
  font-size: 12px;
  font-weight: 700;
  color: var(--app-accent-strong);
}

.preview-scope__item-title {
  color: var(--app-text-soft);
}

.preview-scope__item-meta {
  font-size: 12px;
  color: var(--app-text-muted);
  font-family: 'JetBrains Mono', monospace;
}

.preview-text {
  font-family: 'JetBrains Mono', monospace;
  font-size: 13px;
  line-height: 1.5;
  white-space: pre-wrap;
  margin: 0;
  padding: 16px;
  background: #fafafa;
  border: 1px solid #f0f0f0;
  border-radius: 8px;
}

/* 结构化预览 */
.structured-preview {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.summary-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 12px;
}

.summary-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px 16px;
  border-radius: 12px;
  border: 1px solid #f0f0f0;
  background: #fafafa;
  transition: all 0.2s;
  min-width: 0;
}

.summary-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
}

.summary-card.added {
  border-color: #d1fae5;
  background: #f0fdf4;
}

.summary-card.modified {
  border-color: #fef3c7;
  background: #fefce8;
}

.summary-card.deleted {
  border-color: #fee2e2;
  background: #fef2f2;
}

.summary-card.conflicted {
  border-color: #fecaca;
  background: #fef2f2;
}

.card-count {
  font-size: 32px;
  font-weight: 700;
  line-height: 1;
  margin-bottom: 8px;
}

.summary-card.added .card-count {
  color: #16a34a;
}

.summary-card.modified .card-count {
  color: #ca8a04;
}

.summary-card.deleted .card-count {
  color: #dc2626;
}

.summary-card.conflicted .card-count {
  color: #dc2626;
}

.card-label {
  font-size: 12px;
  color: #666;
  font-weight: 500;
}

/* 文件列表分组 */
.file-lists {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.file-group {
  border: 1px solid #f0f0f0;
  border-radius: 10px;
  overflow: hidden;
  background: #fafafa;
}

.file-group.conflicted-group {
  border-color: #fecaca;
  background: #fff5f5;
}

.file-group-header {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 16px;
  background: #f8f8f8;
  border-bottom: 1px solid #f0f0f0;
}

.conflicted-group .file-group-header {
  background: #fef2f2;
  border-bottom-color: #fecaca;
}

.group-title {
  font-size: 13px;
  font-weight: 600;
  color: #333;
}

.file-list {
  max-height: 200px;
  overflow-y: auto;
}

.file-item {
  display: flex;
  align-items: center;
  padding: 8px 16px;
  border-bottom: 1px solid #f5f5f5;
  transition: background-color 0.15s;
  gap: 10px;
  flex-wrap: wrap;
}

.file-item:last-child {
  border-bottom: none;
}

.file-item:hover {
  background: #f0f0f0;
}

.file-path {
  font-family: 'JetBrains Mono', monospace;
  font-size: 12px;
  color: #555;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.conflicted-item {
  justify-content: space-between;
}

@media (max-width: 1280px) {
  .repo-block {
    min-width: 180px;
    flex: 1 1 240px;
    padding: 0;
  }

  .repo-block-arrow,
  .separator {
    display: none;
  }

  .filter-chip-wrapper,
  .filter-select,
  .search-box {
    width: 100%;
  }

  .filter-select--requirements {
    width: 100%;
  }

  .table-footer {
    gap: 12px;
    flex-wrap: wrap;
  }

  .selection-info {
    width: 100%;
    white-space: normal;
  }

  .action-options {
    width: 100%;
    justify-content: space-between;
  }

  .action-buttons {
    width: 100%;
    justify-content: flex-end;
  }

  .selection-summary {
    width: 100%;
  }

  .summary-cards {
    grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
  }

  .file-item {
    align-items: flex-start;
  }

  .preview-scope__item {
    align-items: flex-start;
  }
}

.no-changes {
  padding: 40px 0;
  text-align: center;
}

/* 原始输出区域 */
.raw-output-section {
  margin-top: 16px;
}

.raw-output-section :deep(.el-collapse-item__header) {
  font-size: 13px;
  color: #888;
}

/* 需求选项 */
.requirement-option {
  display: flex;
  align-items: center;
  gap: 8px;
}

.req-info {
  color: #555;
}
</style>
