<template>
  <div class="operation-history app-page">
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
      <el-card class="history-main-card page-shell workspace-card">
        <template #header>
          <div class="card-header">
            <span>操作历史记录</span>
            <div class="header-actions">
              <el-button size="small" @click="loadLogs">
                <el-icon><Refresh /></el-icon> 刷新
              </el-button>
            </div>
          </div>
        </template>

        <!-- 筛选条件 -->
        <el-form :model="filterForm" inline size="default">
          <el-form-item label="操作类型">
            <el-select v-model="filterForm.operationType" placeholder="全部" clearable
              @change="handleTypeChange" style="width: 150px">
              <el-option label="全部" value="" />
              <el-option label="提交 (COMMIT)" value="COMMIT" />
              <el-option label="更新 (UPDATE)" value="UPDATE" />
              <el-option label="合并 (MERGE)" value="MERGE" />
              <el-option label="还原 (REVERT)" value="REVERT" />
              <el-option label="清理 (CLEANUP)" value="CLEANUP" />
              <el-option label="解决冲突 (RESOLVE)" value="RESOLVE" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="filterForm.status" placeholder="全部" clearable
              @change="handleStatusChange" style="width: 120px">
              <el-option label="全部" value="" />
              <el-option label="成功" value="SUCCESS" />
              <el-option label="失败" value="FAILED" />
              <el-option label="运行中" value="RUNNING" />
            </el-select>
          </el-form-item>
        </el-form>

        <!-- 操作统计 -->
        <div class="stats-row">
          <el-statistic title="总记录数" :value="total" />
          <el-statistic title="成功" :value="successCount" color="#67C23A" />
          <el-statistic title="失败" :value="failedCount" color="#F56C6C" />
        </div>

        <!-- 操作记录列表 -->
        <div class="operation-table-region page-scroll">
          <el-table :data="logList" stripe v-loading="loading" empty-text="暂无操作记录"
            style="width: 100%" class="operation-table" height="100%">
          <el-table-column label="时间" width="180" prop="createdAt">
            <template #default="{ row }">
              <div class="time-cell">
                <el-icon><Clock /></el-icon>
                {{ formatDate(row.createdAt) }}
              </div>
            </template>
          </el-table-column>

          <el-table-column label="操作类型" width="150">
            <template #default="{ row }">
              <el-tag :type="getOperationTypeColor(row.operationType)" size="small">
                <el-icon><component :is="getOperationIcon(row.operationType)" /></el-icon>
                {{ getOperationTypeLabel(row.operationType) }}
              </el-tag>
            </template>
          </el-table-column>

          <el-table-column label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="getStatusColor(row.status)" size="small">
                {{ getStatusLabel(row.status) }}
              </el-tag>
            </template>
          </el-table-column>

          <el-table-column label="详情" min-width="300">
            <template #default="{ row }">
              <div class="detail-cell">
                <span class="detail-text" :title="row.detail">{{ row.detail || '-' }}</span>
              </div>
            </template>
          </el-table-column>

          <el-table-column label="错误信息" min-width="200" v-if="showErrorColumn">
            <template #default="{ row }">
              <el-tooltip v-if="row.errorMessage" :content="row.errorMessage" placement="top">
                <span class="error-text">{{ row.errorMessage }}</span>
              </el-tooltip>
              <span v-else class="no-error">-</span>
            </template>
          </el-table-column>
          </el-table>
        </div>

        <!-- 分页 -->
        <div class="pagination-wrapper">
          <el-pagination
            v-model:current-page="currentPage"
            :page-size="pageSize"
            :total="total"
            layout="total, prev, pager, next"
            @current-change="handlePageChange"
          />
        </div>
      </el-card>

      <!-- 最近操作卡片 -->
      <el-card class="recent-card page-shell" v-if="recentLogs.length > 0">
        <template #header>
          <div class="card-header">
            <span>最近操作</span>
            <el-tag type="info" size="small">最近 {{ recentLogs.length }} 条</el-tag>
          </div>
        </template>
        <div class="recent-list">
          <div v-for="log in recentLogs" :key="log.id" class="recent-item">
            <el-tag :type="getOperationTypeColor(log.operationType)" size="small">
              {{ getOperationTypeLabel(log.operationType) }}
            </el-tag>
            <span class="recent-detail">{{ log.detail || '-' }}</span>
            <el-tag :type="getStatusColor(log.status)" size="small">
              {{ getStatusLabel(log.status) }}
            </el-tag>
            <span class="recent-time">{{ formatTimeAgo(log.createdAt) }}</span>
          </div>
        </div>
      </el-card>
    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { 
  getRepoList, 
  getOperationLogs, 
  getRecentOperationLogs,
  getOperationLogsByType 
} from '@/api'
import { getLastRepoId, saveLastRepo } from '@/utils/storage'
import {
  Clock, Refresh, Upload, Download, Connection, RefreshLeft, Brush, CircleCheck
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

const repos = ref([])
const selectedRepoId = ref(null)
const logList = ref([])
const recentLogs = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = 20
const total = ref(0)

const filterForm = ref({
  operationType: '',
  status: ''
})

const currentRepoId = computed(() => {
  return route.params.repoId || selectedRepoId.value
})

const showErrorColumn = computed(() => {
  return logList.value.some(log => log.errorMessage)
})

const successCount = computed(() => {
  return logList.value.filter(log => log.status === 'SUCCESS').length
})

const failedCount = computed(() => {
  return logList.value.filter(log => log.status === 'FAILED').length
})

onMounted(async () => {
  const res = await getRepoList()
  repos.value = res.data || []

  if (route.params.repoId) {
    selectedRepoId.value = Number(route.params.repoId)
    await loadLogs()
  } else {
    const lastId = getLastRepoId()
    if (lastId && repos.value.find(r => r.id === lastId)) {
      selectedRepoId.value = lastId
      await loadLogs()
    }
  }
})

watch(() => route.params.repoId, async (newId) => {
  if (newId) {
    selectedRepoId.value = Number(newId)
    await loadLogs()
  }
})

function handleRepoChange(repoId) {
  const repo = repos.value.find(r => r.id === repoId)
  if (repo) saveLastRepo(repo)
  router.push(`/svn/operation-history/${repoId}`)
}

async function loadLogs() {
  if (!currentRepoId.value) return
  loading.value = true

  try {
    const params = {
      page: currentPage.value - 1,
      size: pageSize
    }

    let res
    if (filterForm.value.operationType) {
      res = await getOperationLogsByType(
        currentRepoId.value, 
        filterForm.value.operationType, 
        params
      )
    } else {
      res = await getOperationLogs(currentRepoId.value, params)
    }

    const data = res.data || {}
    logList.value = data.records || []
    total.value = data.total || 0

    // 过滤状态
    if (filterForm.value.status) {
      logList.value = logList.value.filter(log => log.status === filterForm.value.status)
    }
  } catch (e) {
    ElMessage.error(e.message || '获取操作记录失败')
  } finally {
    loading.value = false
  }

  // 加载最近操作
  try {
    const res = await getRecentOperationLogs(currentRepoId.value, 10)
    recentLogs.value = res.data || []
  } catch (e) {
    console.error('获取最近操作失败', e)
    recentLogs.value = []
  }
}

function handlePageChange(page) {
  currentPage.value = page
  loadLogs()
}

function handleTypeChange() {
  currentPage.value = 1
  loadLogs()
}

function handleStatusChange() {
  // 状态过滤是客户端过滤
}

function getOperationTypeColor(type) {
  const map = {
    'COMMIT': 'primary',
    'UPDATE': 'success',
    'MERGE': 'warning',
    'REVERT': 'info',
    'CLEANUP': '',
    'RESOLVE': 'success'
  }
  return map[type] || ''
}

function getOperationTypeLabel(type) {
  const map = {
    'COMMIT': '提交',
    'UPDATE': '更新',
    'MERGE': '合并',
    'REVERT': '还原',
    'CLEANUP': '清理',
    'RESOLVE': '解决冲突'
  }
  return map[type] || type
}

function getOperationIcon(type) {
  const map = {
    'COMMIT': 'Upload',
    'UPDATE': 'Download',
    'MERGE': 'Connection',
    'REVERT': 'RefreshLeft',
    'CLEANUP': 'Brush',
    'RESOLVE': 'CircleCheck'
  }
  return map[type] || 'Clock'
}

function getStatusColor(status) {
  const map = {
    'SUCCESS': 'success',
    'FAILED': 'danger',
    'RUNNING': 'warning'
  }
  return map[status] || ''
}

function getStatusLabel(status) {
  const map = {
    'SUCCESS': '成功',
    'FAILED': '失败',
    'RUNNING': '运行中'
  }
  return map[status] || status
}

function formatDate(dateStr) {
  if (!dateStr) return '-'
  try {
    const date = new Date(dateStr)
    return date.toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
    })
  } catch (e) {
    return dateStr
  }
}

function formatTimeAgo(dateStr) {
  if (!dateStr) return ''
  try {
    const date = new Date(dateStr)
    const now = new Date()
    const diff = Math.floor((now - date) / 1000)

    if (diff < 60) return `${diff}秒前`
    if (diff < 3600) return `${Math.floor(diff / 60)}分钟前`
    if (diff < 86400) return `${Math.floor(diff / 3600)}小时前`
    return `${Math.floor(diff / 86400)}天前`
  } catch (e) {
    return ''
  }
}
</script>

<style scoped>
.operation-history {
  overflow: hidden;
  min-width: 0;
}

.repo-selector {
  margin-bottom: 0;
}

.history-main-card :deep(.el-card__body),
.recent-card :deep(.el-card__body),
.repo-selector :deep(.el-card__body) {
  min-width: 0;
}

.history-main-card {
  flex: 1;
  min-height: 0;
  overflow: hidden;
}

.history-main-card :deep(.el-card__body) {
  height: 100%;
  display: flex;
  flex-direction: column;
  min-height: 0;
  overflow: hidden;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.stats-row {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
  gap: 14px;
  margin-bottom: 16px;
  padding: 14px;
  background: var(--app-surface-soft);
  border: 1px solid var(--app-border);
  border-radius: 12px;
}

.time-cell {
  display: flex;
  align-items: center;
  gap: 5px;
  color: #606266;
}

.detail-cell {
  overflow: hidden;
}

.detail-text {
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #303133;
}

.error-text {
  color: #F56C6C;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: block;
  cursor: help;
}

.no-error {
  color: #909399;
}

.pagination-wrapper {
  flex-shrink: 0;
  display: flex;
  justify-content: center;
  margin-top: 16px;
}

.recent-card {
  margin-top: 0;
}

.recent-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.recent-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 12px;
  background: var(--app-surface-soft);
  border: 1px solid var(--app-border);
  border-radius: 10px;
  flex-wrap: wrap;
}

.recent-detail {
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: #606266;
  font-size: 13px;
}

.recent-time {
  color: #909399;
  font-size: 12px;
  margin-left: auto;
}

.operation-table {
  margin-top: 15px;
}

.operation-table-region {
  min-height: 0;
}

@media (max-width: 1280px) {
  .recent-detail {
    width: 100%;
  }

  .recent-time {
    margin-left: 0;
  }
}
</style>
