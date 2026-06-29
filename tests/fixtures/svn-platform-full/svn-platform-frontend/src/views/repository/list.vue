<template>
  <div class="repo-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>仓库管理</span>
          <el-button type="primary" @click="showImportDialog">
            <el-icon><Plus /></el-icon>
            导入仓库
          </el-button>
        </div>
      </template>

      <el-table :data="repos" stripe v-loading="loading" style="width: 100%"
        empty-text="暂无仓库，点击右上角导入">
        <el-table-column prop="name" label="仓库名称" width="180" />
        <el-table-column prop="localPath" label="本地路径" show-overflow-tooltip />
        <el-table-column prop="svnUrl" label="SVN地址" show-overflow-tooltip />
        <el-table-column prop="lastRevision" label="版本号" width="100">
          <template #default="{ row }">
            <el-tag size="small" type="info">r{{ row.lastRevision || '?' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="导入时间" width="180" />
        <el-table-column label="操作" width="400" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small"
              @click="$router.push(`/svn/status/${row.id}`)">
              <el-icon><View /></el-icon> 状态
            </el-button>
            <el-button type="success" link size="small"
              @click="$router.push(`/svn/log/${row.id}`)">
              <el-icon><Clock /></el-icon> 日志
            </el-button>
            <el-button type="warning" link size="small"
              @click="handleMergeAsTarget(row)">
              <el-icon><Connection /></el-icon> 合并
            </el-button>
            <el-button type="info" link size="small"
              @click="handleRename(row)">
              <el-icon><Edit /></el-icon> 重命名
            </el-button>
            <el-button type="warning" link size="small"
              @click="handleRefreshInfo(row)">
              <el-icon><Refresh /></el-icon> 刷新
            </el-button>
            <el-button type="danger" link size="small"
              @click="handleDelete(row)">
              <el-icon><Delete /></el-icon> 删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 导入仓库对话框 -->
    <el-dialog v-model="importDialogVisible" title="导入SVN仓库" width="580px">
      <el-form :model="importForm" label-width="100px">
        <el-form-item label="本地路径" required>
          <div class="path-input-group">
            <el-input v-model="importForm.localPath"
              placeholder="请输入SVN工作副本的绝对路径，如 D:\projects\my-project"
              clearable />
            <el-button type="primary" @click="handleBrowse" :loading="browsing">
              <el-icon><FolderOpened /></el-icon> 浏览
            </el-button>
          </div>
        </el-form-item>
        <el-form-item label="仓库名称">
          <el-input v-model="importForm.name"
            placeholder="可选，为空则使用目录名" />
        </el-form-item>
      </el-form>

      <!-- 验证结果 -->
      <el-descriptions v-if="validateResult" :column="1" border size="small" class="validate-result">
        <el-descriptions-item label="SVN地址">{{ validateResult.url }}</el-descriptions-item>
        <el-descriptions-item label="仓库根">{{ validateResult['repository-root'] }}</el-descriptions-item>
        <el-descriptions-item label="当前版本">r{{ validateResult['entry-revision'] }}</el-descriptions-item>
        <el-descriptions-item label="最后修改">{{ validateResult['last-author'] }}</el-descriptions-item>
      </el-descriptions>

      <template #footer>
        <el-button @click="importDialogVisible = false">取消</el-button>
        <el-button type="warning" @click="handleValidate" :loading="validating">
          验证路径
        </el-button>
        <el-button type="primary" @click="handleImport" :loading="importing"
          :disabled="!validateResult">
          确认导入
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getRepoList, importRepo, deleteRepo, validateRepoPath, getSvnInfo, browseFolder, renameRepo } from '@/api'
import { useRouter } from 'vue-router'

const router = useRouter()

const repos = ref([])
const loading = ref(false)
const importDialogVisible = ref(false)
const importForm = ref({ localPath: '', name: '' })
const validateResult = ref(null)
const validating = ref(false)
const importing = ref(false)
const browsing = ref(false)

onMounted(() => {
  loadRepos()
})

async function loadRepos() {
  loading.value = true
  try {
    const res = await getRepoList()
    repos.value = res.data || []
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

function showImportDialog() {
  importForm.value = { localPath: '', name: '' }
  validateResult.value = null
  importDialogVisible.value = true
}

async function handleBrowse() {
  browsing.value = true
  try {
    const res = await browseFolder()
    if (res.data) {
      importForm.value.localPath = res.data
      validateResult.value = null
      // 选择后自动验证
      handleValidate()
    }
  } catch (e) {
    // 用户取消选择不提示错误
    if (e.message && !e.message.includes('未选择')) {
      ElMessage.error(e.message || '打开文件夹选择器失败')
    }
  } finally {
    browsing.value = false
  }
}

async function handleValidate() {
  if (!importForm.value.localPath) {
    ElMessage.warning('请输入本地路径')
    return
  }
  validating.value = true
  validateResult.value = null
  try {
    const res = await validateRepoPath(importForm.value.localPath)
    validateResult.value = res.data
    ElMessage.success('验证通过，可以导入')
  } catch (e) {
    ElMessage.error(e.message || '验证失败')
  } finally {
    validating.value = false
  }
}

async function handleImport() {
  importing.value = true
  try {
    await importRepo(importForm.value)
    ElMessage.success('仓库导入成功')
    importDialogVisible.value = false
    loadRepos()
  } catch (e) {
    ElMessage.error(e.message || '导入失败')
  } finally {
    importing.value = false
  }
}

async function handleDelete(row) {
  try {
    await ElMessageBox.confirm(
      `确定要删除仓库「${row.name}」吗？仅删除管理记录，不会删除本地文件。`,
      '确认删除',
      { type: 'warning' }
    )
    await deleteRepo(row.id)
    ElMessage.success('已删除')
    loadRepos()
  } catch (e) {
    if (e !== 'cancel') {
      ElMessage.error(e.message || '删除失败')
    }
  }
}

async function handleRename(row) {
  try {
    const { value } = await ElMessageBox.prompt(
      '请输入新的仓库名称',
      '重命名仓库',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: row.name,
        inputPattern: /\S+/,
        inputErrorMessage: '仓库名称不能为空'
      }
    )
    if (value && value.trim() !== row.name) {
      await renameRepo(row.id, value.trim())
      ElMessage.success('仓库名称已修改')
      loadRepos()
    }
  } catch (e) {
    if (e !== 'cancel' && e?.action !== 'cancel') {
      ElMessage.error(e.message || '重命名失败')
    }
  }
}

async function handleRefreshInfo(row) {
  try {
    const res = await getSvnInfo(row.id)
    ElMessage.success('信息已刷新')
    loadRepos()
  } catch (e) {
    ElMessage.error(e.message || '刷新失败')
  }
}

function handleMergeAsTarget(row) {
  localStorage.setItem('mergeTargetRepo', row.id)
  router.push('/svn/merge')
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.validate-result {
  margin-top: 15px;
}

.path-input-group {
  display: flex;
  gap: 8px;
  width: 100%;
}

.path-input-group .el-input {
  flex: 1;
}

.path-input-group .el-button {
  flex-shrink: 0;
}
</style>
