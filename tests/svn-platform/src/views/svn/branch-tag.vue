<template>
  <div class="branch-tag-page">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>分支/标签管理</span>
          <div class="header-right">
            <el-select v-model="currentRepoId" placeholder="选择仓库" style="width: 260px" @change="handleRepoChange" filterable>
              <el-option v-for="repo in repoList" :key="repo.id" :label="repo.name" :value="repo.id" />
            </el-select>
          </div>
        </div>
      </template>

      <div class="toolbar" v-if="currentRepoId">
        <el-tabs v-model="activeTab" @tab-change="loadList">
          <el-tab-pane label="分支 (branches)" name="branches" />
          <el-tab-pane label="标签 (tags)" name="tags" />
        </el-tabs>
        <div class="toolbar-actions">
          <el-button type="primary" @click="showCreateDialog">
            <el-icon><Plus /></el-icon>
            新建{{ activeTab === 'branches' ? '分支' : '标签' }}
          </el-button>
          <el-button @click="loadList">
            <el-icon><Refresh /></el-icon>
            刷新
          </el-button>
        </div>
      </div>

      <el-empty v-if="!currentRepoId" description="请先选择仓库" />

      <el-table v-else :data="list" v-loading="loading" style="width: 100%" stripe>
        <el-table-column label="名称" prop="name" min-width="200" />
        <el-table-column label="操作" width="200" align="center">
          <template #default="{ row }">
            <el-button size="small" type="primary" link @click="handleCopyUrl(row)">
              <el-icon><CopyDocument /></el-icon>
              复制URL
            </el-button>
            <el-popconfirm
              :title="`确定删除${activeTab === 'branches' ? '分支' : '标签'} ${row.name} 吗？此操作不可撤销。`"
              @confirm="handleDelete(row)"
            >
              <template #reference>
                <el-button size="small" type="danger" link>
                  <el-icon><Delete /></el-icon>
                  删除
                </el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <div class="empty-tip" v-if="currentRepoId && !loading && list.length === 0">
        <el-empty :description="`暂无${activeTab === 'branches' ? '分支' : '标签'}，请点击上方按钮创建`" />
      </div>
    </el-card>

    <!-- 新建分支/标签对话框 -->
    <el-dialog v-model="createVisible" :title="`新建${activeTab === 'branches' ? '分支' : '标签'}`" width="520px">
      <el-form ref="createFormRef" :model="createForm" :rules="createRules" label-width="100px">
        <el-form-item label="类型" prop="pathType">
          <el-radio-group v-model="createForm.pathType">
            <el-radio-button label="branches">分支</el-radio-button>
            <el-radio-button label="tags">标签</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="createForm.name" placeholder="请输入分支/标签名称" />
        </el-form-item>
        <el-form-item label="源URL" prop="sourceUrl">
          <el-input v-model="createForm.sourceUrl" placeholder="源分支或trunk的完整URL" />
          <div class="form-tip">通常是 trunk 或某个分支的完整 SVN URL</div>
        </el-form-item>
        <el-form-item label="备注" prop="message">
          <el-input v-model="createForm.message" type="textarea" :rows="3" placeholder="提交备注（可选）" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="createVisible = false">取消</el-button>
        <el-button type="primary" @click="handleCreate" :loading="creating">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getRepoList } from '@/api'
import { listBranchesOrTags, createBranchOrTag, deleteBranchOrTag } from '@/api'
import { getLastRepoId } from '@/utils/storage'

const repoList = ref([])
const currentRepoId = ref(null)
const activeTab = ref('branches')
const list = ref([])
const loading = ref(false)
const createVisible = ref(false)
const creating = ref(false)
const createFormRef = ref(null)

const createForm = ref({
  pathType: 'branches',
  name: '',
  sourceUrl: '',
  message: ''
})

const createRules = {
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  sourceUrl: [{ required: true, message: '请输入源URL', trigger: 'blur' }]
}

async function loadRepoList() {
  try {
    const res = await getRepoList()
    repoList.value = res.data || []
    const lastId = getLastRepoId()
    if (lastId && repoList.value.find(r => r.id === lastId)) {
      currentRepoId.value = lastId
    } else if (repoList.value.length > 0) {
      currentRepoId.value = repoList.value[0].id
    }
    if (currentRepoId.value) loadList()
  } catch (e) {
    ElMessage.error('加载仓库列表失败')
  }
}

async function loadList() {
  if (!currentRepoId.value) return
  loading.value = true
  try {
    const res = await listBranchesOrTags(currentRepoId.value, activeTab.value)
    list.value = (res.data || []).map(name => ({ name }))
  } catch (e) {
    ElMessage.error(e.response?.data?.message || '加载列表失败')
    list.value = []
  } finally {
    loading.value = false
  }
}

function handleRepoChange() {
  localStorage.setItem('svn_platform_last_repo_id', currentRepoId.value)
  list.value = []
  loadList()
}

function showCreateDialog() {
  createForm.value = { pathType: activeTab.value, name: '', sourceUrl: '', message: '' }
  createVisible.value = true
}

async function handleCreate() {
  try {
    await createFormRef.value.validate()
  } catch {
    return
  }
  creating.value = true
  try {
    const res = await createBranchOrTag(currentRepoId.value, createForm.value)
    ElMessage.success(res.message || '创建成功')
    createVisible.value = false
    loadList()
  } catch (e) {
    ElMessage.error(e.response?.data?.message || '创建失败')
  } finally {
    creating.value = false
  }
}

async function handleDelete(row) {
  try {
    const res = await deleteBranchOrTag(currentRepoId.value, {
      name: row.name,
      pathType: activeTab.value,
      message: `Delete ${activeTab.value}: ${row.name}`
    })
    ElMessage.success(res.message || '删除成功')
    loadList()
  } catch (e) {
    ElMessage.error(e.response?.data?.message || '删除失败')
  }
}

function handleCopyUrl(row) {
  const url = `${getRepoRoot()}/${activeTab.value}/${row.name}`
  navigator.clipboard.writeText(url).then(() => {
    ElMessage.success('URL已复制到剪贴板')
  }).catch(() => {
    ElMessage.warning('复制失败')
  })
}

function getRepoRoot() {
  const repo = repoList.value.find(r => r.id === currentRepoId.value)
  return repo ? repo.repositoryRoot || repo.svnUrl : ''
}

onMounted(loadRepoList)
</script>

<style scoped>
.branch-tag-page {
  padding: 0;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}
.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}
.toolbar-actions {
  display: flex;
  gap: 8px;
}
.form-tip {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
}
.empty-tip {
  margin-top: 40px;
}
</style>