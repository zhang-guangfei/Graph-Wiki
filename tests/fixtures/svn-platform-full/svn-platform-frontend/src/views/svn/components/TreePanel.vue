<template>
  <div class="left-panel">
    <div class="panel-header">
      <span>变更文件</span>
      <div class="panel-actions">
        <el-button size="small" link @click="selectAll" title="全选">
          <el-icon><Select /></el-icon>
        </el-button>
        <el-button size="small" link @click="invertSelect" title="反选">
          <el-icon><Switch /></el-icon>
        </el-button>
        <el-button size="small" link @click="expandAll" title="全部展开">
          <el-icon><ArrowDown /></el-icon>
        </el-button>
        <el-button size="small" link @click="collapseAll" title="全部折叠">
          <el-icon><ArrowRight /></el-icon>
        </el-button>
        <el-button size="small" link @click="$emit('create-changelist')" title="新建Changelist">
          <el-icon><Plus /></el-icon>
        </el-button>
        <el-button size="small" link @click="$emit('refresh')" title="刷新">
          <el-icon><Refresh /></el-icon>
        </el-button>
      </div>
    </div>
    <!-- 搜索框 -->
    <div class="search-bar">
      <el-input
        v-model="searchKeyword"
        placeholder="搜索文件名或路径..."
        size="small"
        clearable
        @input="handleSearchChange"
        prefix-icon="Search"
      />
    </div>
    <!-- 状态筛选栏 -->
    <div class="filter-bar" v-if="statusCount > 0">
      <el-checkbox-group v-model="activeFilters" size="small" @change="handleFilterChange">
        <el-checkbox-button v-for="filter in statusFilters" :key="filter.value" :value="filter.value">
          <el-tag :type="filter.type" size="small" effect="plain">{{ filter.label }}</el-tag>
        </el-checkbox-button>
      </el-checkbox-group>
      <el-button size="small" link @click="clearFilters" v-if="hasActiveFilters" title="清除筛选">
        <el-icon><Close /></el-icon>
      </el-button>
    </div>
    <!-- 顶部changelist操作 -->
    <div class="panel-footer" v-if="statusCount > 0">
      <el-button size="small" @click="$emit('add-to-version-control')"
                 :disabled="checkedUnversionedCount === 0" type="success">
        <el-icon><Plus /></el-icon> 加入版本控制
      </el-button>
      <el-dropdown @command="handleMoveToChangelist" :disabled="checkedVersionedCount === 0">
        <el-button size="small" :disabled="checkedVersionedCount === 0">
          移入分组 <el-icon><ArrowDown /></el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item v-for="cl in changelists" :key="cl" :command="cl">
              {{ cl }}
            </el-dropdown-item>
            <el-dropdown-item divided command="__new__">
              <el-icon><FolderAdd /></el-icon>
              <span>移入新分组</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <el-button size="small" @click="$emit('remove-from-changelist')"
                 :disabled="checkedVersionedCount === 0">
        <el-icon><Remove /></el-icon> 移出分组
      </el-button>
    </div>
    <div class="tree-container" v-loading="loading">
      <el-tree
        ref="treeRef"
        :data="treeData"
        :props="treeProps"
        node-key="id"
        show-checkbox
        :expanded-keys="expandedKeys"
        highlight-current
        :expand-on-click-node="false"
        @node-click="handleNodeClick"
        @check-change="handleCheckChange"
        @node-contextmenu="handleNodeContextMenu"
        @node-expand="handleNodeExpand"
        @node-collapse="handleNodeCollapse"
      >
        <template #default="{ node, data }">
          <span class="tree-node" :class="{ 'is-file': data.isFile, 'is-group': data.isGroup, 'is-active-cl': data.isGroup && data.changelist === activeChangelist, 'is-unversioned-group': data.isUnversioned }">
            <el-icon v-if="data.isUnversioned" class="node-icon unversioned-group-icon">
              <QuestionFilled />
            </el-icon>
            <el-icon v-else-if="data.isGroup" class="node-icon group-icon">
              <Collection />
            </el-icon>
            <el-icon v-else-if="data.isFolder" class="node-icon folder-icon">
              <Folder />
            </el-icon>
            <el-icon v-else class="node-icon file-icon">
              <Document />
            </el-icon>
            <span class="node-label">{{ data.label }}</span>
            <el-tag v-if="data.isGroup && data.changelist === activeChangelist" type="success" size="small" class="active-cl-tag">活跃</el-tag>
            <span v-if="data.fileCount" class="node-count">{{ data.fileCount }} 个文件</span>
            <el-tag v-if="data.status" :type="getStatusType(data.status)" size="small" class="node-status">
              {{ getStatusLabel(data.status) }}
            </el-tag>
            <!-- 文件操作按钮 -->
            <span class="node-actions" v-if="data.isFile">
              <el-button v-if="data.status === 'unversioned'" link size="small" type="success"
                @click.stop="$emit('add-single', data)" title="加入版本控制">
                <el-icon><Plus /></el-icon>
              </el-button>
              <el-button v-else link size="small" type="danger" @click.stop="$emit('revert-single', data)"
                title="还原此文件">
                <el-icon><RefreshLeft /></el-icon>
              </el-button>
            </span>
          </span>
        </template>
      </el-tree>
      <el-empty v-if="!loading && statusCount === 0" description="工作副本无变更" />
    </div>


  </div>
</template>

<script setup>
import { ref, nextTick, computed } from 'vue'

const props = defineProps({
  treeData: { type: Array, default: () => [] },
  loading: { type: Boolean, default: false },
  expandedKeys: { type: Array, default: () => [] },
  changelists: { type: Array, default: () => [] },
  activeChangelist: { type: String, default: '' },
  statusCount: { type: Number, default: 0 },
  checkedVersionedCount: { type: Number, default: 0 },
  checkedUnversionedCount: { type: Number, default: 0 },
  statusList: { type: Array, default: () => [] }
})

const emit = defineEmits([
  'node-click', 'check-change', 'node-contextmenu',
  'node-expand', 'node-collapse',
  'create-changelist', 'refresh',
  'add-single', 'revert-single',
  'add-to-version-control', 'move-to-changelist', 'remove-from-changelist',
  'filter-change', 'search-change'
])

const treeRef = ref(null)
const treeProps = { children: 'children', label: 'label' }

// === 搜索 ===
const searchKeyword = ref('')

function handleSearchChange() {
  emit('search-change', searchKeyword.value)
}

// === 状态筛选 ===
const statusFilters = [
  { label: 'M', value: 'modified', type: 'warning' },
  { label: 'A', value: 'added', type: 'success' },
  { label: 'D', value: 'deleted', type: 'danger' },
  { label: 'C', value: 'conflicted', type: 'danger' },
  { label: '?', value: 'unversioned', type: 'info' },
  { label: '!', value: 'missing', type: 'danger' }
]

const activeFilters = ref([])

const hasActiveFilters = computed(() => activeFilters.value.length > 0 && activeFilters.value.length < statusFilters.length)

function handleFilterChange() {
  emit('filter-change', activeFilters.value)
}

function clearFilters() {
  activeFilters.value = statusFilters.map(f => f.value)
  emit('filter-change', activeFilters.value)
}

function getAllFileIds(nodes) {
  const ids = []
  function walk(list) {
    for (const node of list) {
      if (node.isFile) {
        ids.push(node.id)
      }
      if (node.children && node.children.length > 0) {
        walk(node.children)
      }
    }
  }
  walk(nodes)
  return ids
}

function selectAll() {
  if (!treeRef.value) return
  const allFileIds = getAllFileIds(props.treeData)
  treeRef.value.setCheckedKeys(allFileIds)
  nextTick(() => {
    const checkedNodes = treeRef.value.getCheckedNodes(true)
    emit('check-change', checkedNodes.filter(n => n.isFile))
  })
}

function invertSelect() {
  if (!treeRef.value) return
  const allFileIds = getAllFileIds(props.treeData)
  const checkedKeys = treeRef.value.getCheckedKeys()
  const invertedKeys = allFileIds.filter(id => !checkedKeys.includes(id))
  treeRef.value.setCheckedKeys(invertedKeys)
  nextTick(() => {
    const checkedNodes = treeRef.value.getCheckedNodes(true)
    emit('check-change', checkedNodes.filter(n => n.isFile))
  })
}

// === 状态标签 ===
function getStatusType(status) {
  const map = { modified: 'warning', added: 'success', deleted: 'danger', unversioned: 'info', conflicted: 'danger', missing: 'danger' }
  return map[status] || 'info'
}

function getStatusLabel(status) {
  const map = { modified: 'M', added: 'A', deleted: 'D', unversioned: '?', conflicted: 'C', missing: '!' }
  return map[status] || status
}

// === 展开/折叠 ===
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

function expandAll() {
  if (!treeRef.value) return
  const allIds = getAllNodeIds(props.treeData)
  // 直接设置 expandedKeys 到父组件
  emit('node-expand', allIds)
  // 同时调用 el-tree 的展开方法确保展开
  nextTick(() => {
    allIds.forEach(id => {
      const node = treeRef.value.getNode(id)
      if (node) node.expand()
    })
  })
}

function collapseAll() {
  if (!treeRef.value) return
  emit('node-collapse', [])
  // 逐个折叠所有节点
  const allIds = getAllNodeIds(props.treeData)
  nextTick(() => {
    allIds.forEach(id => {
      const node = treeRef.value.getNode(id)
      if (node) node.collapse()
    })
  })
}

// === 树节点交互 ===
function handleNodeClick(data) {
  emit('node-click', data)
}

function handleCheckChange() {
  nextTick(() => {
    if (!treeRef.value) return
    const checkedNodes = treeRef.value.getCheckedNodes(true)
    emit('check-change', checkedNodes.filter(n => n.isFile))
  })
}

function handleNodeContextMenu(event, data, node) {
  emit('node-contextmenu', event, data, node)
}

function handleNodeExpand(data) {
  emit('node-expand', data.id)
}

function handleNodeCollapse(data) {
  emit('node-collapse', data.id)
}

function handleMoveToChangelist(command) {
  if (command === '__new__') {
    emit('create-changelist')
  } else {
    emit('move-to-changelist', command)
  }
}

// 初始化时默认全选所有状态
activeFilters.value = statusFilters.map(f => f.value)

defineExpose({ treeRef })
</script>

<style scoped>
.left-panel {
  width: 340px;
  min-width: 280px;
  display: flex;
  flex-direction: column;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.98) 0%, rgba(246, 250, 255, 0.95) 100%);
  border: 1px solid var(--app-border);
  border-radius: 20px;
  box-shadow: var(--app-shadow-soft);
  overflow: hidden;
}

.panel-header {
  padding: 10px 14px;
  border-bottom: 1px solid var(--app-border);
  font-size: 13px;
  font-weight: 600;
  color: var(--app-text);
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-shrink: 0;
  background: linear-gradient(180deg, rgba(243, 248, 255, 0.95) 0%, rgba(237, 245, 255, 0.92) 100%);
  gap: 10px;
  flex-wrap: wrap;
}

.panel-actions {
  display: flex;
  gap: 4px;
}

.tree-container {
  flex: 1;
  overflow-y: auto;
  padding: 6px 0;
}

/* 树节点样式 */
.tree-node {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  width: 100%;
  padding-right: 8px;
}

.tree-node.is-group {
  font-weight: 600;
  color: var(--app-text);
}

.node-icon {
  font-size: 14px;
  flex-shrink: 0;
}

.group-icon {
  color: #E6A23C;
}

.unversioned-group-icon {
  color: #909399;
}

.is-unversioned-group {
  color: #909399;
  font-style: italic;
}

.folder-icon {
  color: var(--app-text-muted);
}

.file-icon {
  color: var(--app-accent);
}

.node-label {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.node-count {
  font-size: 11px;
  color: var(--app-text-muted);
  margin-left: 6px;
  flex-shrink: 0;
}

.node-status {
  margin-left: auto;
  flex-shrink: 0;
}

.node-actions {
  margin-left: 4px;
  opacity: 0;
  transition: opacity 0.2s;
}

.tree-node:hover .node-actions {
  opacity: 1;
}

/* 活跃changelist */
.is-active-cl {
  color: #67C23A;
}

.active-cl-tag {
  margin-left: 6px;
  flex-shrink: 0;
}

.panel-footer {
  padding: 8px 12px;
  border-top: 1px solid var(--app-border);
  display: flex;
  gap: 8px;
  flex-shrink: 0;
  background: rgba(243, 248, 255, 0.92);
  flex-wrap: wrap;
}

.filter-bar {
  padding: 8px 12px;
  border-bottom: 1px solid var(--app-border);
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 6px;
  flex-shrink: 0;
  background: rgba(243, 248, 255, 0.92);
  flex-wrap: wrap;
}

.filter-bar .el-checkbox-group {
  display: flex;
  gap: 4px;
  flex-wrap: wrap;
}

.filter-bar .el-checkbox-button .el-tag {
  pointer-events: none;
}

@media (max-width: 1200px) {
  .left-panel {
    width: 100%;
    min-width: 0;
  }
}
</style>
