<template>
  <div class="diff-viewer">
    <div class="diff-toolbar">
      <div class="diff-info">
        <el-tag size="small">原始版本 (BASE)</el-tag>
        <el-icon><Right /></el-icon>
        <el-tag size="small" type="warning">当前修改</el-tag>
        <span class="diff-count" v-if="diffCount > 0">
          共 {{ diffCount }} 处差异
          <span v-if="currentDiffIndex >= 0">({{ currentDiffIndex + 1 }}/{{ diffCount }})</span>
        </span>
      </div>
      <div class="diff-nav">
        <el-button size="small" @click="prevDiff" :disabled="diffCount === 0">
          <el-icon><Top /></el-icon> 上一个差异
        </el-button>
        <el-button size="small" @click="nextDiff" :disabled="diffCount === 0">
          <el-icon><Bottom /></el-icon> 下一个差异
        </el-button>
        <el-divider direction="vertical" />
        <el-button size="small" @click="toggleInline">
          {{ inlineView ? '并排视图' : '内联视图' }}
        </el-button>
      </div>
    </div>
    <div ref="editorContainer" class="editor-container"></div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, watch, nextTick } from 'vue'
import * as monaco from 'monaco-editor'

const props = defineProps({
  original: { type: String, default: '' },
  modified: { type: String, default: '' },
  language: { type: String, default: 'plaintext' },
  fileName: { type: String, default: '' }
})

const editorContainer = ref(null)
let diffEditor = null
let diffNavigator = null

const diffCount = ref(0)
const currentDiffIndex = ref(-1)
const inlineView = ref(false)

// 根据文件扩展名推断语言
function detectLanguage(fileName) {
  if (!fileName) return 'plaintext'
  const ext = fileName.split('.').pop().toLowerCase()
  const langMap = {
    js: 'javascript', ts: 'typescript', vue: 'html', jsx: 'javascript',
    java: 'java', py: 'python', xml: 'xml', json: 'json',
    html: 'html', css: 'css', scss: 'scss', less: 'less',
    md: 'markdown', sql: 'sql', sh: 'shell', bat: 'bat',
    yml: 'yaml', yaml: 'yaml', properties: 'ini',
    cpp: 'cpp', c: 'c', h: 'c', cs: 'csharp',
    go: 'go', rs: 'rust', rb: 'ruby', php: 'php',
    txt: 'plaintext', log: 'plaintext', conf: 'ini'
  }
  return langMap[ext] || 'plaintext'
}

function createEditor() {
  if (!editorContainer.value) return

  const lang = props.language !== 'plaintext' ? props.language : detectLanguage(props.fileName)

  diffEditor = monaco.editor.createDiffEditor(editorContainer.value, {
    automaticLayout: true,
    readOnly: true,
    renderSideBySide: !inlineView.value,
    minimap: { enabled: false },
    scrollBeyondLastLine: false,
    fontSize: 13,
    lineNumbers: 'on',
    folding: true,
    wordWrap: 'off',
    renderIndicators: true,
    originalEditable: false,
    enableSplitViewResizing: true
  })

  updateModels(lang)
}

function updateModels(lang) {
  if (!diffEditor) return

  const language = lang || (props.language !== 'plaintext' ? props.language : detectLanguage(props.fileName))

  diffEditor.setModel({
    original: monaco.editor.createModel(props.original, language),
    modified: monaco.editor.createModel(props.modified, language)
  })

  // 等待diff计算完成后更新差异数量
  setTimeout(() => {
    updateDiffCount()
  }, 500)
}

function updateDiffCount() {
  if (!diffEditor) return
  const changes = diffEditor.getLineChanges()
  diffCount.value = changes ? changes.length : 0
  currentDiffIndex.value = -1
}

function nextDiff() {
  if (!diffEditor || diffCount.value === 0) return
  const changes = diffEditor.getLineChanges()
  if (!changes || changes.length === 0) return

  currentDiffIndex.value = (currentDiffIndex.value + 1) % changes.length
  scrollToChange(changes[currentDiffIndex.value])
}

function prevDiff() {
  if (!diffEditor || diffCount.value === 0) return
  const changes = diffEditor.getLineChanges()
  if (!changes || changes.length === 0) return

  currentDiffIndex.value = currentDiffIndex.value <= 0
    ? changes.length - 1
    : currentDiffIndex.value - 1
  scrollToChange(changes[currentDiffIndex.value])
}

function scrollToChange(change) {
  if (!diffEditor || !change) return
  const modifiedEditor = diffEditor.getModifiedEditor()
  const line = change.modifiedStartLineNumber || change.originalStartLineNumber
  modifiedEditor.revealLineInCenter(line)
  modifiedEditor.setPosition({ lineNumber: line, column: 1 })
}

function toggleInline() {
  inlineView.value = !inlineView.value
  if (diffEditor) {
    diffEditor.updateOptions({ renderSideBySide: !inlineView.value })
  }
}

onMounted(() => {
  createEditor()
})

watch(() => [props.original, props.modified, props.fileName], () => {
  nextTick(() => {
    updateModels()
  })
})

onBeforeUnmount(() => {
  if (diffEditor) {
    diffEditor.dispose()
    diffEditor = null
  }
})
</script>

<style scoped>
.diff-viewer {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.diff-toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 8px 12px;
  background: #f5f7fa;
  border: 1px solid #e4e7ed;
  border-bottom: none;
  border-radius: 4px 4px 0 0;
  flex-shrink: 0;
}

.diff-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.diff-count {
  font-size: 12px;
  color: #909399;
  margin-left: 10px;
}

.diff-nav {
  display: flex;
  align-items: center;
  gap: 6px;
}

.editor-container {
  flex: 1;
  min-height: 0;
  border: 1px solid #e4e7ed;
  border-radius: 0 0 4px 4px;
}
</style>
