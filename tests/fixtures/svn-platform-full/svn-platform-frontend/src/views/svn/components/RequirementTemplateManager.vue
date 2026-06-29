<template>
  <div class="requirement-template-manager">
    <!-- 需求下拉选择器 -->
    <div class="requirement-select">
      <el-select
        v-model="selectedRequirement"
        placeholder="选择需求模板（支持搜索）"
        filterable
        clearable
        style="width: 100%"
        @change="handleRequirementChange"
      >
        <el-option
          v-for="req in requirementTemplates"
          :key="req.id"
          :label="req.format"
          :value="req.id"
        >
          <div class="select-option">
            <el-tag :type="getTypeColor(req.type)" size="small">{{ req.type }}</el-tag>
            <span class="req-id">#{{ req.requirementId }}</span>
            <span class="req-title">{{ req.title }}</span>
          </div>
        </el-option>
      </el-select>
    </div>

    <!-- 类型标签按钮组 -->
    <div class="template-buttons" v-if="requirementTypeTemplates.length > 0">
      <el-button 
        v-for="tpl in requirementTypeTemplates" 
        :key="tpl.name"
        size="small" 
        @click="insertTemplate(tpl.format)"
        :type="tpl.name === defaultTemplateName ? 'primary' : ''"
      >
        {{ tpl.format }}
      </el-button>
      <el-button size="small" link @click="showManageDialog = true">
        <el-icon><Setting /></el-icon> 管理模板
      </el-button>
    </div>

    <!-- 模板管理对话框 -->
    <el-dialog 
      v-model="showManageDialog" 
      title="需求模板管理" 
      width="750px"
      :close-on-click-modal="false"
    >
      <div class="manage-content">
        <!-- 添加新模板表单（最上方，大气布局） -->
        <div class="add-form">
          <div class="form-title">添加新需求模板</div>
          <el-form :model="newTemplate" label-width="100px" size="default">
            <el-form-item label="类型标签">
              <el-select v-model="newTemplate.type" placeholder="选择类型" style="width: 100%">
                <el-option 
                  v-for="tpl in requirementTypeTemplates" 
                  :key="tpl.name" 
                  :label="tpl.format" 
                  :value="tpl.name" 
                />
              </el-select>
            </el-form-item>
            <el-form-item label="需求 ID">
              <el-input v-model="newTemplate.requirementId" placeholder="如: 19313">
                <template #prepend>#</template>
              </el-input>
            </el-form-item>
            <el-form-item label="需求标题">
              <el-input v-model="newTemplate.title" placeholder="如: BIN计算 月用量取setfreq" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addTemplate">保存新模板</el-button>
              <el-button @click="resetNewTemplate">重置</el-button>
            </el-form-item>
          </el-form>
        </div>

        <el-divider />

        <!-- SVN 历史记录搜索（中间，紧凑布局） -->
        <div class="history-section">
          <div class="section-title">从 SVN 历史提交记录解析</div>
          <el-input
            v-model="historySearchKeyword"
            placeholder="搜索关键词..."
            size="small"
            clearable
            prefix-icon="Search"
            @input="filterHistoryRecords"
          />
          <div class="history-list" v-loading="historyLoading">
            <div 
              v-for="(record, index) in filteredHistoryRecords" 
              :key="index"
              class="history-item"
              @click="parseHistoryRecord(record)"
            >
              <div class="history-content">
                <div class="history-meta">
                  <span class="history-revision">r{{ record.revision }}</span>
                  <span class="history-author">{{ record.author }}</span>
                  <span class="history-date">{{ formatDate(record.date) }}</span>
                </div>
                <span class="history-text">{{ record.message }}</span>
              </div>
              <el-tag size="small" type="success">解析</el-tag>
            </div>
            <el-empty v-if="!historyLoading && filteredHistoryRecords.length === 0" description="暂无匹配的 SVN 提交记录" :image-size="60" />
          </div>
        </div>

        <el-divider />

        <!-- 已保存的需求模板（最下方，紧凑显示） -->
        <div class="template-list-section">
          <div class="section-title">已保存的需求模板</div>
          <draggable 
            v-model="requirementTemplates" 
            item-key="id"
            handle=".drag-handle"
            @end="onDragEnd"
          >
            <template #item="{ element, index }">
              <div class="manage-item">
                <div class="drag-handle">
                  <el-icon><Rank /></el-icon>
                </div>
                <div class="manage-item-content">
                  <div class="item-index">{{ index + 1 }}.</div>
                  <div class="item-info">
                    <div class="item-type">
                      <el-tag :type="getTypeColor(element.type)" size="small">{{ element.type }}</el-tag>
                    </div>
                    <div class="item-detail">
                      <span class="req-id">#{{ element.requirementId }}</span>
                      <span class="req-title">{{ element.title }}</span>
                    </div>
                  </div>
                </div>
                <div class="item-actions">
                  <el-button size="small" link @click="editTemplate(element)">
                    <el-icon><Edit /></el-icon> 编辑
                  </el-button>
                  <el-button size="small" link type="danger" @click="deleteTemplate(element.requirementId)">
                    <el-icon><Delete /></el-icon> 删除
                  </el-button>
                </div>
              </div>
            </template>
          </draggable>
        </div>
      </div>
    </el-dialog>

    <!-- 编辑模板对话框 -->
    <el-dialog 
      v-model="showEditDialog" 
      title="编辑需求模板" 
      width="450px"
      :close-on-click-modal="false"
    >
      <el-form :model="editingTemplate" label-width="80px" size="small">
        <el-form-item label="类型标签">
          <el-select v-model="editingTemplate.type" placeholder="选择类型" style="width: 100%">
            <el-option 
              v-for="tpl in requirementTypeTemplates" 
              :key="tpl.name" 
              :label="tpl.format" 
              :value="tpl.name" 
            />
          </el-select>
        </el-form-item>
        <el-form-item label="需求 ID">
          <el-input v-model="editingTemplate.requirementId" placeholder="如: 19313">
            <template #prepend>#</template>
          </el-input>
        </el-form-item>
        <el-form-item label="需求标题">
          <el-input v-model="editingTemplate.title" placeholder="如: BIN计算 月用量取setfreq" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showEditDialog = false">取消</el-button>
        <el-button type="primary" @click="saveEditTemplate">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { getSvnLog, listRequirements, createOrUpdateRequirement, deleteRequirement as deleteReqApi } from '@/api'
import { ElMessage } from 'element-plus'
import draggable from 'vuedraggable'
import { DEFAULT_REQUIREMENT_TYPE, buildRequirementFormat, buildRequirementPayload, getDefaultCommitTemplates as buildDefaultCommitTemplates, getRequirementTypeColor, parseRequirementMessage } from '@/utils/requirement'

const props = defineProps({
  modelValue: { type: String, default: '' },
  repoId: { type: Number, default: null }
})

const emit = defineEmits(['update:modelValue', 'use-template'])

// 需求类型标签模板
const requirementTypeTemplates = ref(buildDefaultCommitTemplates())

// 需求模板列表（从后端API加载）
const requirementTemplates = ref([])
const requirementTemplatesLoading = ref(false)

// 选中的需求
const selectedRequirement = ref(null)

// SVN 历史记录（从 SVN 服务器获取，不做本地缓存）
const svnHistoryRecords = ref([])
const historyLoading = ref(false)

// 过滤后的历史记录
const filteredHistoryRecords = ref([])

// 历史搜索关键词
const historySearchKeyword = ref('')

// 对话框状态
const showManageDialog = ref(false)
const showEditDialog = ref(false)

// 新增模板表单
const newTemplate = ref({
  type: DEFAULT_REQUIREMENT_TYPE,
  requirementId: '',
  title: ''
})

// 编辑中的模板
const editingTemplate = ref({
  requirementId: '',
  type: '',
  title: ''
})

// 默认模板名称（第一条）
const defaultTemplateName = computed(() => {
  return requirementTypeTemplates.value.length > 0 ? requirementTypeTemplates.value[0].name : ''
})

async function saveRequirement(payload) {
  const normalized = buildRequirementPayload(payload)
  await createOrUpdateRequirement(normalized)
  await loadRequirementTemplates()
  return normalized
}

// 从后端API加载需求模板
async function loadRequirementTemplates() {
  requirementTemplatesLoading.value = true
  try {
    const res = await listRequirements()
    requirementTemplates.value = (res.data || []).map(req => ({
      id: req.requirementId,
      requirementId: req.requirementId,
      title: req.title,
      description: req.description,
      type: req.type,
      format: req.format || buildRequirementFormat(req.type, req.requirementId, req.title)
    }))
    if (!props.modelValue?.trim() && requirementTemplates.value.length > 0) {
      emit('update:modelValue', requirementTemplates.value[0].format)
    }
  } catch (e) {
    console.error('加载需求模板失败:', e)
    ElMessage.error('加载需求模板失败')
    requirementTemplates.value = []
  } finally {
    requirementTemplatesLoading.value = false
  }
}

// 从 SVN 服务器加载历史提交记录
async function loadSvnHistory() {
  if (!props.repoId) return
  historyLoading.value = true
  try {
    const res = await getSvnLog(props.repoId, { limit: 50 })
    const records = res.data || []
    // 解析 SVN 日志，提取 message 字段
    svnHistoryRecords.value = records
      .filter(r => r.message && r.message.trim())
      .map(r => ({
        revision: r.revision,
        author: r.author,
        date: r.date,
        message: r.message.trim()
      }))
    filterHistoryRecords()
  } catch (e) {
    console.error('加载 SVN 历史记录失败:', e)
    ElMessage.error('加载 SVN 历史记录失败')
    svnHistoryRecords.value = []
    filteredHistoryRecords.value = []
  } finally {
    historyLoading.value = false
  }
}

// 过滤历史记录
function filterHistoryRecords() {
  if (!historySearchKeyword.value.trim()) {
    filteredHistoryRecords.value = svnHistoryRecords.value.slice(0, 20)
    return
  }
  
  const keyword = historySearchKeyword.value.toLowerCase()
  filteredHistoryRecords.value = svnHistoryRecords.value.filter(record =>
    record.message.toLowerCase().includes(keyword)
  )
}

// 格式化日期
function formatDate(dateStr) {
  if (!dateStr) return ''
  try {
    const date = new Date(dateStr)
    const y = date.getFullYear()
    const m = String(date.getMonth() + 1).padStart(2, '0')
    const d = String(date.getDate()).padStart(2, '0')
    const h = String(date.getHours()).padStart(2, '0')
    const min = String(date.getMinutes()).padStart(2, '0')
    return `${y}-${m}-${d} ${h}:${min}`
  } catch {
    return dateStr
  }
}

// 获取类型标签颜色
function getTypeColor(type) {
  return getRequirementTypeColor(type)
}

// 选择需求
function handleRequirementChange(reqId) {
  if (!reqId) return
  const req = requirementTemplates.value.find(r => r.id === reqId)
  if (req) {
    emit('update:modelValue', req.format)
    emit('use-template', req)
  }
}

// 插入类型标签
function insertTemplate(format) {
  const current = props.modelValue || ''
  const hasTag = current.startsWith('【') && current.includes('】')
  if (hasTag) {
    const bracketEnd = current.indexOf('】') + 1
    emit('update:modelValue', format + current.substring(bracketEnd))
  } else {
    emit('update:modelValue', format + current)
  }
}

// 解析 SVN 历史记录
function parseHistoryRecord(record) {
  const message = record.message || record
  const parsed = parseRequirementMessage(message)
  if (parsed) {
    const { type, requirementId, title } = parsed
    
    const existingIndex = requirementTemplates.value.findIndex(
      t => t.requirementId === requirementId
    )
    
    if (existingIndex !== -1) {
      ElMessage.info('该需求模板已存在')
      return
    }
    
    newTemplate.value = {
      type: type,
      requirementId: requirementId,
      title: title
    }
    
    ElMessage.success('已解析，请检查并保存')
  } else {
    ElMessage.warning('无法解析该记录，请确保格式为：【类型】#需求ID 标题')
  }
}

// 添加新模板
async function addTemplate() {
  if (!newTemplate.value.type || !newTemplate.value.requirementId || !newTemplate.value.title) {
    ElMessage.warning('请填写完整信息')
    return
  }

  const template = buildRequirementPayload(newTemplate.value)

  try {
    await saveRequirement(template)
    ElMessage.success('模板保存成功')
    resetNewTemplate()
  } catch (e) {
    console.error('保存失败:', e)
    ElMessage.error('保存失败: ' + (e.response?.data?.message || e.message))
  }
}

// 编辑模板
function editTemplate(template) {
  editingTemplate.value = { ...template }
  showEditDialog.value = true
}

// 保存编辑
async function saveEditTemplate() {
  try {
    await saveRequirement(editingTemplate.value)
    showEditDialog.value = false
    ElMessage.success('模板更新成功')
  } catch (e) {
    console.error('更新失败:', e)
    ElMessage.error('更新失败: ' + (e.response?.data?.message || e.message))
  }
}

// 删除模板
async function deleteTemplate(requirementId) {
  try {
    await deleteReqApi(requirementId)
    ElMessage.success('模板已删除')
    await loadRequirementTemplates()
  } catch (e) {
    console.error('删除失败:', e)
    ElMessage.error('删除失败: ' + (e.response?.data?.message || e.message))
  }
}

// 重置新增表单
function resetNewTemplate() {
  newTemplate.value = {
    type: DEFAULT_REQUIREMENT_TYPE,
    requirementId: '',
    title: ''
  }
}

// 拖拽结束
function onDragEnd() {
  // 拖拽后无需保存，后端无排序概念
}

// 监听 repoId 变化，重新加载
watch(() => props.repoId, () => {
  loadRequirementTemplates()
  selectedRequirement.value = null
  loadSvnHistory()
})

// 组件挂载时加载数据
onMounted(() => {
  loadRequirementTemplates()
  loadSvnHistory()
})

// 暴露方法
defineExpose({
  saveRequirement,
  addRequirement: async function(type, requirementId, title) {
    try {
      return await saveRequirement({ requirementId, title, type })
    } catch (e) {
      console.error('添加需求失败:', e)
      ElMessage.error('添加需求失败')
      return null
    }
  },
  getDefaultFormat: function() {
    if (requirementTemplates.value.length > 0) {
      return requirementTemplates.value[0].format
    }
    const templates = buildDefaultCommitTemplates()
    return templates.length > 0 ? templates[0].format : ''
  }
})
</script>

<style scoped>
.requirement-template-manager {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.requirement-select {
  width: 100%;
}

.select-option {
  display: flex;
  align-items: center;
  gap: 8px;
}

.req-id {
  font-weight: 500;
  color: #409eff;
}

.req-title {
  color: #606266;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.template-buttons {
  display: flex;
  gap: 6px;
  flex-wrap: wrap;
  align-items: center;
}

.manage-content {
  max-height: 600px;
  overflow-y: auto;
}

.history-section {
  margin-bottom: 12px;
}

.section-title {
  font-size: 14px;
  font-weight: 500;
  color: #303133;
  margin-bottom: 12px;
}

.history-list {
  max-height: 180px;
  overflow-y: auto;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  margin-top: 8px;
}

.history-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 8px 10px;
  border-bottom: 1px solid #ebeef5;
  cursor: pointer;
  transition: background 0.2s;
}

.history-item:hover {
  background: #ecf5ff;
}

.history-item:last-child {
  border-bottom: none;
}

.history-content {
  flex: 1;
  overflow: hidden;
}

.history-meta {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 3px;
  font-size: 11px;
  color: #909399;
}

.history-revision {
  font-weight: 600;
  color: #409eff;
}

.history-author {
  color: #606266;
}

.history-date {
  color: #909399;
}

.history-text {
  font-size: 12px;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: block;
}

.template-list-section {
  margin-top: 12px;
}

.manage-item {
  display: flex;
  align-items: center;
  padding: 8px 10px;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  margin-bottom: 6px;
  background: #fff;
  cursor: move;
}

.drag-handle {
  display: flex;
  align-items: center;
  color: #909399;
  margin-right: 12px;
  cursor: move;
}

.manage-item-content {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 12px;
}

.item-index {
  font-weight: 500;
  color: #909399;
  min-width: 24px;
}

.item-info {
  display: flex;
  align-items: center;
  gap: 8px;
  flex: 1;
}

.item-detail {
  display: flex;
  align-items: center;
  gap: 6px;
}

.item-actions {
  display: flex;
  gap: 4px;
}

.add-form {
  margin-top: 0;
  padding: 16px;
  background: #fafbfc;
  border-radius: 8px;
}

.form-title {
  font-size: 15px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 16px;
}

.sortable-ghost {
  opacity: 0.4;
  background: #ecf5ff !important;
}

.sortable-chosen {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>
