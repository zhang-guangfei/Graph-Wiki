<template>
  <div class="requirement-page app-page">
    <el-card class="page-card page-shell">
      <template #header>
        <div class="page-header page-toolbar">
          <span>需求管理</span>
          <el-button type="primary" @click="openCreateDialog">
            <el-icon><Plus /></el-icon>
            新增需求
          </el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <div class="search-bar page-toolbar">
        <el-input
          v-model="searchKeyword"
          placeholder="搜索需求ID或标题"
          clearable
          style="width: 300px"
          @keyup.enter="handleSearch"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button @click="handleReset">重置</el-button>
      </div>

      <!-- 需求列表 -->
      <div class="table-region page-scroll">
        <el-table :data="requirementList" v-loading="loading" stripe style="width: 100%">
        <el-table-column prop="requirementId" label="需求ID" width="120">
          <template #default="{ row }">
            <el-link type="primary" @click="handleViewDetail(row)">{{ row.requirementId }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="需求类型" width="120">
          <template #default="{ row }">
            <el-tag :type="getRequirementTypeColor(row.type)" size="small">{{ row.type || '-' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="需求标题" min-width="200" show-overflow-tooltip />
        <el-table-column prop="description" label="需求描述" min-width="200" show-overflow-tooltip />
        <el-table-column label="模板格式" min-width="200" show-overflow-tooltip>
          <template #default="{ row }">
            <span style="color: #909399; font-size: 12px;">{{ row.format || '-' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="更新时间" width="160">
          <template #default="{ row }">
            {{ formatDate(row.updatedAt) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
        </el-table>
      </div>
    </el-card>

    <!-- 详情对话框 -->
    <el-dialog
      v-model="detailDialogVisible"
      title="需求详情"
      width="640px"
    >
      <el-descriptions :column="1" border>
        <el-descriptions-item label="需求ID">{{ detailRow.requirementId || '-' }}</el-descriptions-item>
        <el-descriptions-item label="需求类型">{{ detailRow.type || '-' }}</el-descriptions-item>
        <el-descriptions-item label="需求标题">{{ detailRow.title || '-' }}</el-descriptions-item>
        <el-descriptions-item label="模板格式">
          <span style="word-break: break-all;">{{ detailRow.format || '-' }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="需求描述">
          <span style="white-space: pre-wrap; word-break: break-all;">{{ detailRow.description || '无' }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ formatDate(detailRow.createdAt) }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{ formatDate(detailRow.updatedAt) }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <el-button @click="detailDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑需求' : '新增需求'"
      width="600px"
      @close="resetForm"
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="需求ID" prop="requirementId">
          <el-input v-model="form.requirementId" placeholder="请输入需求ID" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="需求类型" prop="type">
          <el-select v-model="form.type" placeholder="选择需求类型" style="width: 100%">
            <el-option
              v-for="type in REQUIREMENT_TYPE_OPTIONS"
              :key="type"
              :label="type"
              :value="type"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="需求标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入需求标题" />
        </el-form-item>
        <el-form-item label="需求描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="4"
            placeholder="请输入需求描述"
          />
        </el-form-item>
        <el-form-item label="模板格式">
          <el-input :model-value="generatedFormFormat" readonly placeholder="自动生成">
            <template #append>自动生成</template>
          </el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listRequirements, searchRequirements, createRequirement, updateRequirement, deleteRequirement } from '@/api'
import { DEFAULT_REQUIREMENT_TYPE, REQUIREMENT_TYPE_OPTIONS, buildRequirementFormat, buildRequirementPayload, getRequirementTypeColor } from '@/utils/requirement'

const requirementList = ref([])
const loading = ref(false)
const searchKeyword = ref('')
const dialogVisible = ref(false)
const detailDialogVisible = ref(false)
const isEdit = ref(false)
const submitting = ref(false)
const formRef = ref(null)
const detailRow = ref({})

const form = reactive({
  requirementId: '',
  title: '',
  description: '',
  type: DEFAULT_REQUIREMENT_TYPE
})

const generatedFormFormat = computed(() => buildRequirementFormat(form.type, form.requirementId, form.title))

const rules = {
  requirementId: [
    { required: true, message: '请输入需求ID', trigger: 'blur' },
    { min: 1, max: 50, message: '需求ID长度应在1-50之间', trigger: 'blur' }
  ],
  title: [
    { required: true, message: '请输入需求标题', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择需求类型', trigger: 'change' }
  ]
}

// 获取类型标签颜色
onMounted(() => {
  loadData()
})

async function loadData(keyword) {
  loading.value = true
  try {
    let res
    if (keyword) {
      res = await searchRequirements(keyword)
    } else {
      res = await listRequirements()
    }
    requirementList.value = res.data || []
  } catch (e) {
    ElMessage.error(e.message || '加载需求列表失败')
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  loadData(searchKeyword.value.trim())
}

function handleReset() {
  searchKeyword.value = ''
  loadData()
}

function openCreateDialog() {
  isEdit.value = false
  resetForm()
  dialogVisible.value = true
}

function handleEdit(row) {
  isEdit.value = true
  form.requirementId = row.requirementId
  form.title = row.title
  form.description = row.description
  form.type = row.type || DEFAULT_REQUIREMENT_TYPE
  dialogVisible.value = true
}

function handleViewDetail(row) {
  detailRow.value = { ...row }
  detailDialogVisible.value = true
}

async function handleDelete(row) {
  try {
    await ElMessageBox.confirm(`确认删除需求 "${row.requirementId}" 吗？`, '删除确认', {
      type: 'warning'
    })
    await deleteRequirement(row.requirementId)
    ElMessage.success('删除成功')
    loadData(searchKeyword.value.trim())
  } catch (e) {
    if (e !== 'cancel') {
      ElMessage.error(e.message || '删除失败')
    }
  }
}

async function handleSubmit() {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    submitting.value = true
    try {
      const data = buildRequirementPayload(form)
      if (isEdit.value) {
        await updateRequirement(form.requirementId, data)
        ElMessage.success('更新成功')
      } else {
        await createRequirement(data)
        ElMessage.success('创建成功')
      }
      dialogVisible.value = false
      loadData(searchKeyword.value.trim())
    } catch (e) {
      ElMessage.error(e.message || '操作失败')
    } finally {
      submitting.value = false
    }
  })
}

function resetForm() {
  form.requirementId = ''
  form.title = ''
  form.description = ''
  form.type = DEFAULT_REQUIREMENT_TYPE
  if (formRef.value) formRef.value.clearValidate()
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
</script>

<style scoped>
.requirement-page {
  padding: 0;
  gap: 0;
}

.page-card {
  flex: 1;
  margin: 0;
  display: flex;
  flex-direction: column;
  min-height: 0;
  overflow: hidden;
}

.page-card :deep(.el-card__header) {
  padding: 16px 20px;
}

.page-card :deep(.el-card__body) {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 16px;
  min-height: 0;
  padding-top: 16px;
  overflow: hidden;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.search-bar {
  display: flex;
  gap: 10px;
  align-items: center;
  flex-wrap: wrap;
  justify-content: flex-start;
}

.search-bar :deep(.el-input) {
  max-width: 100%;
}

.search-bar :deep(.el-input),
.search-bar :deep(.el-input__wrapper) {
  width: 100%;
}

.table-region {
  min-height: 0;
}

.page-card :deep(.el-table) {
  width: 100%;
}

@media (max-width: 1200px) {
  .page-card {
    min-height: 0;
  }
}
</style>
