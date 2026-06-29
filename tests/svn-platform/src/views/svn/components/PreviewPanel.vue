<template>
  <div class="right-panel">
    <div class="panel-header" v-if="activeFile">
      <span class="active-file-path">{{ activeFile.path }}</span>
    </div>
    <div class="panel-header" v-else>
      <span class="placeholder-text">点击左侧文件查看差异</span>
    </div>
    <div class="diff-container">
      <DiffViewer
        v-if="activeFile && diffLoaded"
        :original="diffOriginal"
        :modified="diffModified"
        :file-name="activeFile.path"
      />
      <el-empty v-else-if="activeFile && !diffLoaded" description="加载中..." />
      <div v-else class="empty-diff">
        <el-icon size="48" color="#c0c4cc"><Document /></el-icon>
        <p>选择左侧已修改的文件以预览差异</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import DiffViewer from '@/components/DiffViewer.vue'

defineProps({
  activeFile: { type: Object, default: null },
  diffLoaded: { type: Boolean, default: false },
  diffOriginal: { type: String, default: '' },
  diffModified: { type: String, default: '' }
})
</script>

<style scoped>
.right-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.98) 0%, rgba(246, 250, 255, 0.95) 100%);
  border: 1px solid var(--app-border);
  border-radius: 20px;
  box-shadow: var(--app-shadow-soft);
  overflow: hidden;
  min-width: 0;
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
}

.active-file-path {
  font-family: 'Consolas', 'Monaco', monospace;
  font-size: 12px;
  color: var(--app-text-soft);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.placeholder-text {
  color: var(--app-text-muted);
  font-weight: normal;
}

.diff-container {
  flex: 1;
  min-height: 0;
  padding: 8px;
}

.empty-diff {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: var(--app-text-muted);
  gap: 12px;
}

.empty-diff p {
  font-size: 14px;
}
</style>
