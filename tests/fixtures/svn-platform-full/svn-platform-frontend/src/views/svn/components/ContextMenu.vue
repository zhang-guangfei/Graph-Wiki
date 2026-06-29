<template>
  <!-- Changelist右键上下文菜单 -->
  <div v-show="groupMenu.visible" class="context-menu"
    :style="{ left: groupMenu.x + 'px', top: groupMenu.y + 'px' }"
    @click.stop @contextmenu.prevent.stop>
    <div class="context-menu-item has-submenu" @mouseenter="moveSubmenuVisible = true" @mouseleave="moveSubmenuVisible = false">
      <span>移入另一个更改列表</span>
      <el-icon><ArrowRight /></el-icon>
      <!-- 子菜单 -->
      <div v-show="moveSubmenuVisible" class="context-submenu">
        <div class="context-menu-item" v-for="cl in otherChangelists" :key="cl"
          @click="$emit('group-move-to', cl)">
          {{ cl }}
        </div>
        <div class="context-menu-divider" v-if="otherChangelists.length > 0"></div>
        <div class="context-menu-item" @click="$emit('group-move-to-new')">
          <span><el-icon><Plus/></el-icon> 新建更改列表</span>
        </div>
      </div>
    </div>
    <template v-if="groupMenu.data && groupMenu.data.changelist !== null">
      <div class="context-menu-item" @click="$emit('set-active')">
        <span>设为活跃更改列表</span>
        <el-icon v-if="groupMenu.data.changelist === activeChangelist"><Check /></el-icon>
      </div>
      <div class="context-menu-divider"></div>
      <div class="context-menu-item" @click="$emit('edit-name')">
        <span>编辑更改列表名称</span>
      </div>
      <div class="context-menu-item danger" @click="$emit('remove-changelist')">
        <span>移除更改列表</span>
      </div>
    </template>
  </div>

  <!-- 文件右键上下文菜单 -->
  <div v-show="fileMenu.visible" class="context-menu"
    :style="{ left: fileMenu.x + 'px', top: fileMenu.y + 'px' }"
    @click.stop @contextmenu.prevent.stop>
    <!-- 未版本化文件菜单 -->
    <template v-if="fileMenu.data && fileMenu.data.status === 'unversioned'">
      <div class="context-menu-item" @click="$emit('file-add-to-version')">
        <span>加入版本控制</span>
      </div>
    </template>
    <!-- 版本化文件菜单 -->
    <template v-else>
      <div class="context-menu-item" @click="$emit('file-resolve-conflict')"
        v-if="fileMenu.data && fileMenu.data.status === 'conflicted'">
        <span>解决冲突...</span>
      </div>
      <div class="context-menu-item has-submenu" @mouseenter="fileMoveSubmenuVisible = true" @mouseleave="fileMoveSubmenuVisible = false">
        <span>移入更改列表</span>
        <el-icon><ArrowRight /></el-icon>
        <div v-show="fileMoveSubmenuVisible" class="context-submenu">
          <div class="context-menu-item" v-for="cl in changelists" :key="cl"
            @click="$emit('file-move-to', cl)">
            {{ cl }}
          </div>
          <div class="context-menu-divider" v-if="changelists.length > 0"></div>
          <div class="context-menu-item" @click="$emit('file-move-to-new')">
            <span><el-icon><Plus/></el-icon> 新建更改列表</span>
          </div>
        </div>
      </div>
      <div class="context-menu-item" @click="$emit('file-remove-from-changelist')"
        v-if="fileMenu.data && fileMenu.data.changelist">
        <span>移出更改列表</span>
      </div>
      <div class="context-menu-divider"></div>
      <div class="context-menu-item" @click="$emit('file-view-diff')"
        v-if="fileMenu.data && canPreview(fileMenu.data)">
        <span>查看差异</span>
      </div>
      <div class="context-menu-item danger" @click="$emit('file-revert')">
        <span>还原文件</span>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const props = defineProps({
  groupMenu: { type: Object, required: true },
  fileMenu: { type: Object, required: true },
  changelists: { type: Array, default: () => [] },
  activeChangelist: { type: String, default: '' }
})

defineEmits([
  'group-move-to', 'group-move-to-new', 'set-active', 'edit-name', 'remove-changelist',
  'file-add-to-version', 'file-move-to', 'file-move-to-new',
  'file-remove-from-changelist', 'file-view-diff', 'file-revert', 'file-resolve-conflict'
])

const moveSubmenuVisible = ref(false)
const fileMoveSubmenuVisible = ref(false)

const otherChangelists = computed(() => {
  if (!props.groupMenu.data) return props.changelists
  if (props.groupMenu.data.changelist === null) return props.changelists
  return props.changelists.filter(cl => cl !== props.groupMenu.data.changelist)
})

/** 判断文件是否可预览 */
function canPreview(data) {
  const binaryExts = ['.jar', '.class', '.war', '.zip', '.rar', '.7z', '.gz', '.tar',
    '.png', '.jpg', '.jpeg', '.gif', '.bmp', '.ico', '.svg', '.webp',
    '.exe', '.dll', '.so', '.dylib', '.pdf', '.doc', '.docx', '.xls', '.xlsx',
    '.ppt', '.pptx', '.woff', '.woff2', '.ttf', '.eot', '.mp3', '.mp4', '.avi']
  const ext = (data.path || '').toLowerCase().match(/\.[^.]+$/)?.[0] || ''
  if (binaryExts.includes(ext)) return false
  return ['modified', 'added', 'deleted', 'conflicted', 'unversioned'].includes(data.status)
}

/** 关闭所有子菜单（供父组件调用） */
function closeSubmenus() {
  moveSubmenuVisible.value = false
  fileMoveSubmenuVisible.value = false
}

defineExpose({ closeSubmenus })
</script>

<style scoped>
.context-menu {
  position: fixed;
  z-index: 9999;
  background: #fff;
  border: 1px solid #e4e7ed;
  border-radius: 6px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  padding: 4px 0;
  min-width: 180px;
}

.context-menu-item {
  padding: 8px 16px;
  font-size: 13px;
  color: #303133;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
  white-space: nowrap;
  position: relative;
}

.context-menu-item:hover {
  background: #ecf5ff;
  color: #409EFF;
}

.context-menu-item.danger {
  color: #F56C6C;
}

.context-menu-item.danger:hover {
  background: #fef0f0;
  color: #F56C6C;
}

.context-menu-item.has-submenu {
  position: relative;
}

.context-menu-divider {
  height: 1px;
  background: #ebeef5;
  margin: 4px 0;
}

.context-submenu {
  position: absolute;
  left: 100%;
  top: -4px;
  background: #fff;
  border: 1px solid #e4e7ed;
  border-radius: 6px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  padding: 4px 0;
  min-width: 150px;
  z-index: 10000;
}
</style>
