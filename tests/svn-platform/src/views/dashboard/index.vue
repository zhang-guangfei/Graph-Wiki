<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <el-icon><FolderOpened /></el-icon>
              <span>仓库总数</span>
            </div>
          </template>
          <div class="card-value">{{ repoCount }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <el-icon><CircleCheck /></el-icon>
              <span>系统状态</span>
            </div>
          </template>
          <div class="card-value">
            <el-tag :type="svnAvailable ? 'success' : 'danger'">
              {{ svnAvailable ? 'SVN可用' : 'SVN不可用' }}
            </el-tag>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <el-icon><InfoFilled /></el-icon>
              <span>快速操作</span>
            </div>
          </template>
          <div class="quick-actions">
            <el-button type="primary" size="small" @click="$router.push('/repo/list')">
              管理仓库
            </el-button>
            <el-button size="small" @click="$router.push('/svn/status')">
              查看状态
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 最近操作的仓库 -->
    <el-card class="repo-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span>已导入仓库</span>
          <el-button type="primary" size="small" @click="$router.push('/repo/list')">
            管理仓库
          </el-button>
        </div>
      </template>
      <el-table :data="repos" stripe style="width: 100%" empty-text="暂无仓库，请先导入">
        <el-table-column prop="name" label="仓库名称" />
        <el-table-column prop="svnUrl" label="SVN地址" show-overflow-tooltip />
        <el-table-column prop="lastRevision" label="版本号" width="100">
          <template #default="{ row }">
            <el-tag size="small">r{{ row.lastRevision || '?' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="primary" link size="small"
              @click="$router.push(`/svn/status/${row.id}`)">
              状态
            </el-button>
            <el-button type="primary" link size="small"
              @click="$router.push(`/svn/log/${row.id}`)">
              日志
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getRepoList } from '@/api'

const repos = ref([])
const repoCount = ref(0)
const svnAvailable = ref(true)

onMounted(async () => {
  try {
    const res = await getRepoList()
    repos.value = res.data || []
    repoCount.value = repos.value.length
  } catch (e) {
    console.error('Failed to load repos', e)
  }
})
</script>

<style scoped>
.dashboard {
  padding: 0;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
}

.card-value {
  font-size: 28px;
  font-weight: bold;
  color: #409EFF;
  text-align: center;
}

.quick-actions {
  display: flex;
  gap: 10px;
  justify-content: center;
}

.repo-card {
  margin-top: 20px;
}
</style>
