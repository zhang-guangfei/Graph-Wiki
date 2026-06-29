<template>
  <div class="svn-log app-page">
    <!-- 仓库选择器 -->
    <el-card class="repo-selector page-shell page-shell--padded" v-if="!currentRepoId">
      <template #header>选择仓库</template>
      <el-select v-model="selectedRepoId" placeholder="请选择仓库" style="width: 100%"
        @change="handleRepoChange">
        <el-option v-for="repo in repos" :key="repo.id"
          :label="`${repo.name} (${repo.localPath})`" :value="repo.id" />
      </el-select>
    </el-card>

    <template v-if="currentRepoId">
      <!-- 筛选条件 -->
      <el-card class="filter-card page-shell page-shell--padded">
        <div class="filter-bar">
          <div class="filter-controls">
            <div class="filter-group">
              <el-input 
                v-model="filterForm.keyword" 
                placeholder="搜索提交信息或作者" 
                clearable 
                size="small"
                class="filter-input"
                @keyup.enter="loadLog"
              >
                <template #prefix>
                  <el-icon><Search /></el-icon>
                </template>
              </el-input>
            </div>
            <div class="filter-group compact">
              <el-select 
                v-model="filterForm.author" 
                placeholder="提交人" 
                clearable 
                size="small"
                class="filter-select"
                filterable
              >
                <el-option 
                  v-for="author in uniqueAuthors" 
                  :key="author" 
                  :label="author" 
                  :value="author" 
                />
              </el-select>
            </div>
            
            <div class="filter-divider"></div>
            
            <div class="filter-group compact">
              <el-select 
                v-model="filterForm.requirementType" 
                placeholder="需求类型" 
                clearable 
                size="small"
                class="filter-select"
                @change="handleRequirementTypeFilterChange"
              >
                <el-option
                  v-for="type in REQUIREMENT_TYPE_OPTIONS"
                  :key="type"
                  :label="type"
                  :value="type"
                />
              </el-select>
            </div>
            <div class="filter-group compact">
              <el-select 
                v-model="filterForm.requirementId" 
                placeholder="按需求筛选" 
                clearable 
                size="small"
                class="filter-select"
                filterable
              >
                <el-option 
                  v-for="req in filteredRequirementsForLog" 
                  :key="req.requirementId" 
                  :label="`#${req.requirementId} ${req.title}`" 
                  :value="req.requirementId" 
                >
                  <div class="requirement-option">
                    <el-tag :type="getRequirementTypeColor(req.type)" size="small">{{ req.type || '-' }}</el-tag>
                    <span class="req-info">#{{ req.requirementId }} {{ req.title }}</span>
                  </div>
                </el-option>
              </el-select>
            </div>
            
            <div class="filter-group compact">
              <el-input-number 
                v-model="filterForm.limit" 
                :min="10" 
                :max="500" 
                :step="10"
                size="small"
                controls-position="right"
                class="limit-input"
              />
              <span class="limit-label">条</span>
            </div>
            
            <div class="filter-divider"></div>
            
            <div class="filter-group compact">
              <el-input 
                v-model="filterForm.startRevision" 
                placeholder="起始版本" 
                clearable 
                size="small"
                class="version-input"
              />
              <span class="range-sep">~</span>
              <el-input 
                v-model="filterForm.endRevision" 
                placeholder="结束版本" 
                clearable 
                size="small"
                class="version-input"
              />
            </div>
            
            <div class="filter-group compact">
              <el-date-picker 
                v-model="filterForm.dateRange" 
                type="daterange" 
                range-separator="~" 
                start-placeholder="开始日期" 
                end-placeholder="结束日期" 
                size="small"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                style="width: 220px;"
              />
            </div>
          </div>
          
          <div class="filter-actions">
            <el-button type="primary" size="small" @click="loadLog">
              <el-icon><Search /></el-icon>
              查询
            </el-button>
            <el-button
              size="small"
              :loading="loading"
              title="更新日志信息"
              @click="handleRefreshLog"
            >
              <el-icon><Refresh /></el-icon>
            </el-button>
            <el-button text size="small" @click="resetFilter">重置</el-button>
            <el-button size="small" @click="toggleViewMode">
              <el-icon><component :is="viewMode === 'table' ? 'List' : viewMode === 'grouped' ? 'Grid' : 'Clock'" /></el-icon>
              {{ viewModeLabel }}
            </el-button>
          </div>
        </div>
      </el-card>

      <!-- 表格模式 -->
      <el-card v-if="viewMode === 'table'" class="content-card page-shell workspace-card">
        <template #header>
          <div class="card-header">
            <span>提交记录</span>
            <el-tag type="info" size="small">共 {{ filteredLogList.length }} 条</el-tag>
          </div>
        </template>

        <el-table 
          ref="logTableRef"
          :data="paginatedLogList" 
          stripe 
          v-loading="loading"
          @row-click="handleRowClick" 
          row-key="revision"
          :expand-row-keys="expandedRows"
          style="width: 100%; cursor: pointer" 
          empty-text="暂无提交记录"
          :default-sort="{prop: 'date', order: 'descending'}"
          class="clickable-table"
          :height="tableHeight"
        >
          <el-table-column type="expand" width="1">
            <template #default="{ row }">
              <div class="log-detail">
                <div v-if="row.paths && row.paths.length > 0">
                  <div class="detail-header">
                    <span class="detail-title">变更文件 ({{ row.paths.length }} 个)</span>
                  </div>
                  <div class="file-list-compact">
                    <div v-for="path in row.paths" :key="path.path" class="file-item-compact">
                      <el-tag :type="getActionType(path.action)" size="small" class="file-action-tag">
                        {{ getActionLabel(path.action) }}
                      </el-tag>
                      <span class="file-path-text">{{ path.path }}</span>
                      <span v-if="path.kind" class="file-kind-tag">{{ path.kind }}</span>
                    </div>
                  </div>
                </div>
                <el-empty v-else description="无变更文件" :image-size="40" />
              </div>
            </template>
          </el-table-column>

          <el-table-column label="版本" width="100" sortable prop="revision">
            <template #default="{ row }">
              <el-tag type="primary" size="small" class="revision-tag">r{{ row.revision }}</el-tag>
            </template>
          </el-table-column>
          
          <el-table-column label="作者" width="140" sortable prop="author">
            <template #default="{ row }">
              <div class="author-cell">
                <el-avatar :size="28" :style="{ backgroundColor: getAuthorColor(row.author) }">
                  {{ getAuthorInitial(row.author) }}
                </el-avatar>
                <span class="author-name">{{ row.author }}</span>
              </div>
            </template>
          </el-table-column>
          
          <el-table-column label="需求" width="120" align="center">
            <template #default="{ row }">
              <el-tooltip v-if="row.requirementTitle" :content="row.requirementTitle" placement="top">
                <el-tag type="success" size="small" effect="plain" class="requirement-tag">
                  {{ row.requirementTitle }}
                </el-tag>
              </el-tooltip>
              <span v-else class="no-requirement">-</span>
            </template>
          </el-table-column>
          
          <el-table-column prop="message" label="提交信息" show-overflow-tooltip min-width="200" />
          
          <el-table-column label="变更" width="80" align="center">
            <template #default="{ row }">
              <el-tag v-if="row.paths" type="info" size="small">{{ row.paths.length }}</el-tag>
              <span v-else>-</span>
            </template>
          </el-table-column>
          
          <el-table-column label="时间" width="180" sortable prop="date">
            <template #default="{ row }">
              {{ formatDate(row.date) }}
            </template>
          </el-table-column>
          
          <el-table-column label="操作" width="100" fixed="right">
            <template #default="{ row }">
              <el-button size="small" type="primary" @click.stop="viewDiff(row)">
                查看Diff
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        
        <!-- 分页 -->
        <div class="pagination-wrapper" v-if="totalPages > 1">
          <el-pagination
            v-model:current-page="currentPage"
            :page-size="pageSize"
            :total="filteredLogList.length"
            layout="total, prev, pager, next"
            @current-change="handlePageChange"
          />
        </div>
      </el-card>

      <!-- 需求聚合模式 -->
      <el-card v-else-if="viewMode === 'grouped'" class="content-card page-shell workspace-card">
        <template #header>
          <div class="card-header">
            <span>提交记录 (需求聚合)</span>
            <el-tag type="info" size="small">共 {{ groupedRequirementLogs.length }} 组 / {{ filteredLogList.length }} 条</el-tag>
          </div>
        </template>

        <div class="grouped-log-list">
          <el-card
            v-for="group in groupedRequirementLogs"
            :key="group.requirementId || 'ungrouped'"
            class="group-card page-shell"
            shadow="never"
          >
            <template #header>
              <div class="group-card-header">
                <div class="group-card-title">
                  <el-tag :type="group.requirementType ? getRequirementTypeColor(group.requirementType) : 'info'" size="small">
                    {{ group.requirementType || '未关联需求' }}
                  </el-tag>
                  <span class="group-requirement-id">{{ group.requirementId ? '#' + group.requirementId : '未关联需求' }}</span>
                  <span v-if="group.requirementTitle" class="group-requirement-title">{{ group.requirementTitle }}</span>
                </div>
                <div class="group-card-meta">
                  <span>共 {{ group.count }} 条</span>
                  <span v-if="group.latestRevision">最新 r{{ group.latestRevision }}</span>
                </div>
              </div>
            </template>

            <div class="group-logs">
              <div
                v-for="log in group.items"
                :key="log.revision"
                class="group-log-item"
                @click="handleRowClick(log)"
              >
                <div class="group-log-main">
                  <el-tag type="primary" size="small" class="revision-tag">r{{ log.revision }}</el-tag>
                  <span class="group-log-author">{{ log.author }}</span>
                  <span class="group-log-time">{{ formatDate(log.date) }}</span>
                </div>
                <div class="group-log-message">{{ log.message || '无提交信息' }}</div>
                <el-button size="small" type="primary" @click.stop="viewDiff(log)">
                  查看Diff
                </el-button>
              </div>
            </div>
          </el-card>
        </div>
      </el-card>

      <!-- 时间轴模式 -->
      <el-card v-else class="content-card page-shell workspace-card">
        <template #header>
          <div class="card-header">
            <span>提交记录 (时间轴)</span>
            <el-tag type="info" size="small">共 {{ filteredLogList.length }} 条</el-tag>
          </div>
        </template>

        <el-timeline v-loading="loading">
          <el-timeline-item
            v-for="log in paginatedLogList"
            :key="log.revision"
            :timestamp="formatDate(log.date)"
            placement="top"
            :color="getTimelineColor(log.author)"
            class="timeline-item"
          >
            <el-card class="log-card" @click="handleRowClick(log)">
              <div class="log-card-header">
                <div class="log-meta">
                  <el-avatar :size="32" :style="{ backgroundColor: getAuthorColor(log.author) }">
                    {{ getAuthorInitial(log.author) }}
                  </el-avatar>
                  <div class="meta-info">
                    <span class="author-name">{{ log.author }}</span>
                    <span class="revision-tag">r{{ log.revision }}</span>
                    <el-tag v-if="log.paths" size="small" type="info">
                      {{ log.paths.length }} 个文件变更
                    </el-tag>
                  </div>
                </div>
                <el-button size="small" type="primary" @click.stop="viewDiff(log)">
                  查看Diff
                </el-button>
              </div>
              <div class="log-message">{{ log.message || '无提交信息' }}</div>
              
              <!-- 展开的变更文件列表 -->
              <div v-if="expandedRevision === log.revision && log.paths" class="changed-files">
                <el-divider content-position="left">变更文件</el-divider>
                <div class="file-list">
                  <div v-for="path in log.paths" :key="path.path" class="file-item">
                    <el-tag :type="getActionType(path.action)" size="small">
                      {{ getActionLabel(path.action) }}
                    </el-tag>
                    <span class="file-path">{{ path.path }}</span>
                  </div>
                </div>
              </div>
            </el-card>
          </el-timeline-item>
        </el-timeline>
        
        <!-- 分页 -->
        <div class="pagination-wrapper" v-if="totalPages > 1">
          <el-pagination
            v-model:current-page="currentPage"
            :page-size="pageSize"
            :total="filteredLogList.length"
            layout="total, prev, pager, next"
            @current-change="handlePageChange"
          />
        </div>
      </el-card>

      <!-- Diff查看对话框 — 代码对比视图 -->
      <el-dialog 
        v-model="diffDialogVisible" 
        title="提交Diff" 
        width="90%" 
        top="3vh" 
        :close-on-click-modal="false"
        class="diff-dialog"
      >
        <!-- 加载状态 -->
        <div v-if="diffLoading" class="diff-loading">
          <el-skeleton :rows="10" animated />
        </div>
        
        <!-- Diff内容 — 多文件对比 -->
        <div v-else-if="diffFiles.length > 0" class="diff-dialog-container">
          <!-- 左侧：文件列表 -->
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
          
          <!-- 右侧：代码对比视图 -->
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
        
        <!-- 空状态 -->
        <el-empty v-else description="暂无Diff内容" />
      </el-dialog>
    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getRepoList, getSvnLogWithFilters, getLogChanges, getFileDiffContent, listRequirements } from '@/api'
import { createDefaultLogFilterForm } from '@/utils/log'
import { getLastRepoId, saveLastRepo } from '@/utils/storage'
import { REQUIREMENT_TYPE_OPTIONS, getRequirementTypeColor, groupLogsByRequirement, parseRequirementMessage } from '@/utils/requirement'
import DiffViewer from '@/components/DiffViewer.vue'

const route = useRoute()
const router = useRouter()
const tableHeight = ref(300)
const repos = ref([])
const selectedRepoId = ref(null)
const logList = ref([])
const loading = ref(false)
const viewModes = ['table', 'grouped', 'timeline']
const viewModeLabels = {
  table: '表格',
  grouped: '聚合',
  timeline: '时间轴'
}
const viewMode = ref('table')
const currentPage = ref(1)
const pageSize = 100
const expandedRevision = ref(null)
const logTableRef = ref(null)

// 行展开状态
const expandedRows = ref([])

// 需求相关
const requirements = ref([])
const requirementsMap = computed(() => {
  const map = {}
  requirements.value.forEach(req => {
    map[req.requirementId] = req.title
  })
  return map
})

const requirementLookup = computed(() => {
  const map = {}
  requirements.value.forEach(req => {
    map[req.requirementId] = {
      title: req.title,
      type: req.type
    }
  })
  return map
})

// Diff相关状态
const diffDialogVisible = ref(false)
const diffLoading = ref(false)
const currentDiffLog = ref(null)
const currentDiffFile = ref('')
const diffFiles = ref([])
const diffOriginal = ref('')
const diffModified = ref('')
const currentFileIndex = ref(0)

const filterForm = ref(createDefaultLogFilterForm())

// 按类型筛选后的需求列表
const filteredRequirementsForLog = computed(() => {
  if (!filterForm.value.requirementType) {
    return requirements.value
  }
  return requirements.value.filter(req => req.type === filterForm.value.requirementType)
})

function handleRequirementTypeFilterChange() {
  filterForm.value.requirementId = ''
  loadLog()
}

const currentRepoId = computed(() => {
  return route.params.repoId || selectedRepoId.value
})

const filteredLogList = computed(() => {
  let list = logList.value
  
  // 为每条记录添加需求标题
  list = list.map(log => {
    const reqId = extractRequirementId(log.message)
    return {
      ...log,
      requirementTitle: reqId ? requirementsMap.value[reqId] : null,
      requirementId: reqId
    }
  })
  
  // 按需求类型筛选
  if (filterForm.value.requirementType) {
    list = list.filter(log => {
      const reqId = log.requirementId
      if (!reqId) return false
      const req = requirements.value.find(r => r.requirementId === reqId)
      return req && req.type === filterForm.value.requirementType
    })
  }
  
  // 按需求ID筛选
  if (filterForm.value.requirementId) {
    list = list.filter(log => log.requirementId === filterForm.value.requirementId)
  }
  
  if (filterForm.value.keyword) {
    const keyword = filterForm.value.keyword.toLowerCase()
    list = list.filter(log => 
      (log.message && log.message.toLowerCase().includes(keyword)) ||
      (log.author && log.author.toLowerCase().includes(keyword))
    )
  }
  
  return list
})

// 从提交信息中提取需求ID（匹配数字如 19313）
function extractRequirementId(message) {
  if (!message) return null
  const parsed = parseRequirementMessage(message)
  if (parsed) return parsed.requirementId
  const match = message.match(/(\d{4,})/)
  return match ? match[1] : null
}

const uniqueAuthors = computed(() => {
  const authors = logList.value.map(log => log.author).filter(Boolean)
  return [...new Set(authors)].sort()
})

const paginatedLogList = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  const end = start + pageSize
  return filteredLogList.value.slice(start, end)
})

const groupedRequirementLogs = computed(() => {
  return groupLogsByRequirement(filteredLogList.value, requirementLookup.value)
})

const viewModeLabel = computed(() => viewModeLabels[viewMode.value] || '表格')

const totalPages = computed(() => {
  return Math.ceil(filteredLogList.value.length / pageSize)
})

onMounted(async () => {
  const res = await getRepoList()
  repos.value = res.data || []
  calcTableHeight()
  window.addEventListener('resize', calcTableHeight)
  // 加载需求列表
  try {
    const reqRes = await listRequirements()
    requirements.value = reqRes.data || []
  } catch (e) {
    console.warn('加载需求列表失败', e)
  }

  if (route.params.repoId) {
    selectedRepoId.value = Number(route.params.repoId)
    loadLog()
  } else {
    const lastId = getLastRepoId()
    if (lastId && repos.value.find(r => r.id === lastId)) {
      selectedRepoId.value = lastId
      loadLog()
    }
  }
})

watch(() => route.params.repoId, (newId) => {
  if (newId) {
    selectedRepoId.value = Number(newId)
    loadLog()
  }
})

// 翻页时清空展开状态
watch(currentPage, () => {
  expandedRows.value = []
})

function handleRepoChange(repoId) {
  const repo = repos.value.find(r => r.id === repoId)
  if (repo) saveLastRepo(repo)
  router.push(`/svn/log/${repoId}`)
}

async function loadLog() {
  if (!currentRepoId.value) return
  loading.value = true
  currentPage.value = 1
  expandedRows.value = []
  
  try {
    const params = {
      limit: filterForm.value.limit
    }
    if (filterForm.value.startRevision) {
      params.startRevision = Number(filterForm.value.startRevision)
    }
    if (filterForm.value.endRevision) {
      params.endRevision = Number(filterForm.value.endRevision)
    }
    if (filterForm.value.author) {
      params.author = filterForm.value.author
    }
    if (filterForm.value.path) {
      params.path = filterForm.value.path
    }
    if (filterForm.value.dateRange && filterForm.value.dateRange.length === 2) {
      params.startDate = new Date(filterForm.value.dateRange[0]).toISOString()
      params.endDate = new Date(filterForm.value.dateRange[1]).toISOString()
    }
    
    const res = await getSvnLogWithFilters(currentRepoId.value, params)
    logList.value = res.data || []
  } catch (e) {
    ElMessage.error(e.message || '获取提交记录失败')
  } finally {
    loading.value = false
  }
}

function resetFilter() {
  filterForm.value = createDefaultLogFilterForm()
  currentPage.value = 1
  loadLog()
}

function handleRefreshLog() {
  loadLog()
}

function handleRowClick(row, column) {
  // 表格模式：点击行切换展开；跳过操作列按钮点击
  if (viewMode.value === 'table') {
    if (column?.label === '操作') return
    
    const idx = expandedRows.value.indexOf(row.revision)
    if (idx > -1) {
      expandedRows.value.splice(idx, 1)
    } else {
      expandedRows.value.push(row.revision)
    }
    return
  }

  // 时间轴模式
  expandedRevision.value = expandedRevision.value === row.revision ? null : row.revision
}
function calcTableHeight() {
  // 根据页面高度计算表格高度
  const windowHeight = window.innerHeight
  const offset = 330
  tableHeight.value = Math.max(400, windowHeight - offset)
}
function toggleViewMode() {
  const currentIndex = viewModes.indexOf(viewMode.value)
  const nextIndex = currentIndex === -1 ? 0 : (currentIndex + 1) % viewModes.length
  viewMode.value = viewModes[nextIndex]
  expandedRows.value = []
}

function handlePageChange(page) {
  currentPage.value = page
}

// ========== Diff功能 ==========

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
    const changesRes = await getLogChanges(currentRepoId.value, row.revision)
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
    const res = await getFileDiffContent(currentRepoId.value, currentDiffLog.value.revision, filePath)
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

function getActionType(action) {
  const map = { A: 'success', M: 'warning', D: 'danger', R: 'info' }
  return map[action] || 'info'
}

function getActionLabel(action) {
  const map = { A: '新增', M: '修改', D: '删除', R: '替换' }
  return map[action] || action
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
  if (!author) return '#409EFF'
  let hash = 0
  for (let i = 0; i < author.length; i++) {
    hash = author.charCodeAt(i) + ((hash << 5) - hash)
  }
  const hue = Math.abs(hash % 360)
  return `hsl(${hue}, 50%, 50%)`
}

function getAuthorInitial(author) {
  if (!author) return '?'
  return author.charAt(0).toUpperCase()
}

function getTimelineColor(author) {
  return getAuthorColor(author)
}
</script>

<style scoped>
.svn-log {
  overflow: hidden;
}

.repo-selector {
  margin-bottom: 0;
}

.filter-card {
  margin-bottom: 0;
}

.filter-card :deep(.el-card__body) {
  padding: 0;
}

.filter-card :deep(.el-card__body),
.repo-selector :deep(.el-card__body),
.group-card :deep(.el-card__body) {
  min-width: 0;
}

.content-card {
  flex: 1;
  min-height: 0;
  overflow: hidden;
}

.content-card :deep(.el-card__body) {
  height: 100%;
  display: flex;
  flex-direction: column;
  min-height: 0;
  overflow: hidden;
}

.filter-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  flex-wrap: wrap;
}

.filter-controls {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
  flex: 1;
}

.filter-group {
  display: flex;
  align-items: center;
  gap: 6px;
}

.filter-input {
  width: 220px;
}

.filter-select {
  width: 130px;
}

.limit-input {
  width: 90px;
}

.limit-label {
  color: #909399;
  font-size: 12px;
  margin-left: -4px;
}

.version-input {
  width: 100px;
}

.date-input {
  width: 120px;
}

.filter-divider {
  width: 1px;
  height: 24px;
  background-color: rgba(159, 180, 214, 0.28);
  margin: 0 4px;
}

.range-sep {
  color: #909399;
  margin: 0 2px;
  font-size: 12px;
}

.filter-actions {
  display: flex;
  gap: 8px;
  align-items: center;
  flex-shrink: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.author-cell {
  display: flex;
  align-items: center;
  gap: 8px;
}

.author-name {
  font-size: 14px;
}

.revision-tag {
  font-family: monospace;
}

/* 表格行点击展开样式 */
.clickable-table :deep(.el-table__expand-column) {
  width: 0 !important;
  min-width: 0 !important;
  padding: 0 !important;
}

.clickable-table :deep(.el-table__expand-column .cell) {
  display: none;
}

.clickable-table :deep(.el-table__row) {
  cursor: pointer;
  transition: background-color 0.15s;
}

.clickable-table :deep(.el-table__row:hover) {
  background-color: rgba(51, 143, 255, 0.06);
}

.clickable-table :deep(.el-table__expanded-cell) {
  padding: 0 !important;
  background: rgba(243, 248, 255, 0.9);
}

/* 展开行详细内容 */
.log-detail {
  padding: 16px 20px;
  background: rgba(243, 248, 255, 0.9);
}

.detail-header {
  margin-bottom: 10px;
  padding-bottom: 8px;
  border-bottom: 1px solid rgba(159, 180, 214, 0.2);
}

.detail-title {
  font-size: 13px;
  font-weight: 500;
  color: #606266;
}

.file-list-compact {
  max-height: 300px;
  overflow-y: auto;
}

.file-item-compact {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 5px 8px;
  border-radius: 4px;
  transition: background-color 0.15s;
}

.file-item-compact:hover {
  background: #f0f0f0;
}

.file-action-tag {
  flex-shrink: 0;
  min-width: 40px;
  text-align: center;
}

.file-path-text {
  flex: 1;
  font-family: 'Consolas', 'Monaco', monospace;
  font-size: 13px;
  color: #606266;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.file-kind-tag {
  flex-shrink: 0;
  font-size: 12px;
  color: #909399;
}

/* 分页 */
.pagination-wrapper {
  flex-shrink: 0;
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

/* 时间轴模式 */
.timeline-item {
  padding-bottom: 20px;
}

.log-card {
  cursor: pointer;
  transition: box-shadow 0.2s;
}

.log-card:hover {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.log-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.log-meta {
  display: flex;
  align-items: center;
  gap: 10px;
}

.meta-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.meta-info .author-name {
  font-weight: 500;
  font-size: 14px;
}

.log-message {
  color: #606266;
  line-height: 1.5;
  margin-bottom: 10px;
}

.changed-files {
  margin-top: 15px;
}

.file-list {
  max-height: 300px;
  overflow-y: auto;
}

.file-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 4px 0;
}

.file-path {
  font-family: monospace;
  font-size: 13px;
  color: #606266;
}

/* Diff对话框样式 */
.diff-loading {
  padding: 20px;
  min-height: 300px;
}

.diff-dialog-container {
  display: flex;
  height: 80vh;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  overflow: hidden;
}

.diff-sidebar {
  width: 280px;
  border-right: 1px solid #e4e7ed;
  display: flex;
  flex-direction: column;
  background: #fafafa;
  flex-shrink: 0;
}

.diff-sidebar-header {
  padding: 12px 16px;
  border-bottom: 1px solid #e4e7ed;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-shrink: 0;
}

.sidebar-version {
  font-size: 14px;
  font-weight: 600;
  color: #303133;
}

.sidebar-count {
  color: #909399;
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
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.15s;
  margin-bottom: 2px;
}

.diff-file-item:hover {
  background: #e8e8e8;
}

.diff-file-item.active {
  background: #ecf5ff;
}

.diff-action-tag {
  flex-shrink: 0;
  min-width: 40px;
  text-align: center;
}

.diff-file-name {
  flex: 1;
  font-size: 12px;
  font-family: 'Consolas', 'Monaco', monospace;
  color: #606266;
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
  border-bottom: 1px solid #e4e7ed;
  background: #f5f7fa;
  flex-shrink: 0;
}

.current-file-path {
  font-family: 'Consolas', 'Monaco', monospace;
  font-size: 13px;
  color: #303133;
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
  color: #909399;
  min-width: 60px;
  text-align: center;
}

.diff-editor-wrapper {
  flex: 1;
  min-height: 0;
}

.grouped-log-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.group-card {
  overflow: hidden;
}

.group-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
  flex-wrap: wrap;
}

.group-card-title {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
}

.group-requirement-id {
  font-weight: 600;
  color: #409eff;
}

.group-requirement-title {
  color: #303133;
}

.group-card-meta {
  display: flex;
  align-items: center;
  gap: 12px;
  color: #909399;
  font-size: 12px;
  white-space: nowrap;
  flex-wrap: wrap;
}

.group-logs {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.group-log-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 12px;
  border: 1px solid #ebeef5;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.15s ease, border-color 0.15s ease;
  flex-wrap: wrap;
}

.group-log-item:hover {
  background: #f5f7fa;
  border-color: #d9ecff;
}

.group-log-main {
  display: flex;
  align-items: center;
  gap: 10px;
  min-width: 180px;
  flex-shrink: 0;
}

.group-log-author {
  font-weight: 500;
  color: #303133;
}

.group-log-time {
  color: #909399;
  font-size: 12px;
}

.group-log-message {
  flex: 1;
  color: #606266;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

@media (max-width: 1280px) {
  .filter-controls,
  .filter-actions {
    width: 100%;
  }

  .filter-input,
  .filter-select,
  .version-input,
  .limit-input {
    width: 100%;
  }

  .filter-divider {
    display: none;
  }

  .group-log-main {
    width: 100%;
  }

  .group-log-message {
    width: 100%;
  }
}
</style>
