<template>
  <el-dialog
    :model-value="visible"
    title="冲突解决"
    width="95%"
    top="5vh"
    :close-on-click-modal="false"
    @close="$emit('close')"
  >
    <div class="conflict-resolver">
      <!-- 顶部工具栏 -->
      <div class="toolbar">
        <div class="toolbar-left">
          <el-select
            v-if="conflictFiles && conflictFiles.length > 0"
            v-model="currentFileIndex"
            size="small"
            style="width: 250px"
            @change="onFileChange"
          >
            <el-option
              v-for="(file, index) in conflictFiles"
              :key="index"
              :label="file"
              :value="index"
            />
          </el-select>
          <span v-if="localPath" class="file-path">{{ localPath }}</span>
          <span v-if="activeFilePath" class="file-path">{{ activeFilePath }}</span>
        </div>
        <div class="toolbar-center">
          <el-tag size="small" type="danger">
            冲突: {{ currentConflictIndex + 1 }} / {{ conflictBlocks.length }}
          </el-tag>
          <el-button-group style="margin-left: 10px">
            <el-button size="small" @click="prevConflict" :disabled="conflictBlocks.length === 0">
              <el-icon><Top /></el-icon> 上一个
            </el-button>
            <el-button size="small" @click="nextConflict" :disabled="conflictBlocks.length === 0">
              <el-icon><Bottom /></el-icon> 下一个
            </el-button>
          </el-button-group>
        </div>
        <div class="toolbar-right">
          <el-button-group v-if="isLoadingError">
            <el-button size="small" type="warning" @click="retryLoad">
              重试
            </el-button>
          </el-button-group>
          <el-dropdown @command="handleBulkCommand">
            <el-button size="small">
              批量操作 <el-icon><ArrowDown /></el-icon>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="adoptAllLocal">全部采用本地</el-dropdown-item>
                <el-dropdown-item command="adoptAllRemote">全部采用远程</el-dropdown-item>
                <el-dropdown-item command="smartMergeAll">全部智能合并</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <el-button-group>
            <el-button size="small" :disabled="!canUndo" @click="resultEditorInstance.trigger('source', 'undo')">
              <el-icon><RefreshLeft /></el-icon>
            </el-button>
            <el-button size="small" :disabled="!canRedo" @click="resultEditorInstance.trigger('source', 'redo')">
              <el-icon><RefreshRight /></el-icon>
            </el-button>
          </el-button-group>
        </div>
      </div>

      <el-alert
        v-if="hasFallbackWarning"
        title="部分内容加载失败，已使用降级数据"
        type="warning"
        :closable="false"
        show-icon
        style="flex-shrink: 0"
      />

      <!-- 三面板编辑器 -->
      <div class="editors-container">
        <div class="editor-panel">
          <div class="editor-header ours-header">OURS (本地)</div>
          <div ref="oursEditor" class="editor-instance"></div>
        </div>
        <div class="editor-panel">
          <div class="editor-header result-header">RESULT (结果)</div>
          <div ref="resultEditor" class="editor-instance"></div>
        </div>
        <div class="editor-panel">
          <div class="editor-header theirs-header">THEIRS (远程)</div>
          <div ref="theirsEditor" class="editor-instance"></div>
        </div>
      </div>

      <!-- 底部操作栏 -->
      <div class="bottom-bar">
        <div class="progress-section">
          <el-progress
            :percentage="originalConflictCount > 0 ? Math.round((resolvedConflictsCount / originalConflictCount) * 100) : 100"
            :stroke-width="16"
            :text-inside="true"
            style="flex: 1; max-width: 400px"
          />
          <span class="progress-text">已解决 {{ resolvedConflictsCount }} / {{ originalConflictCount }}</span>
        </div>
        <div class="conflict-operations">
          <el-button type="danger" size="small" @click="adoptLocal(currentConflictIndex)" :disabled="conflictBlocks.length === 0">
            采用本地
          </el-button>
          <el-button type="success" size="small" @click="adoptRemote(currentConflictIndex)" :disabled="conflictBlocks.length === 0">
            采用远程
          </el-button>
          <el-button type="warning" size="small" @click="smartMerge(currentConflictIndex)" :disabled="conflictBlocks.length === 0">
            智能合并
          </el-button>
        </div>
        <div class="bottom-actions">
          <el-button @click="handleStash">暂存</el-button>
          <el-button type="primary" @click="handleMarkResolved">标记为已解决</el-button>
          <el-button @click="$emit('close')">取消</el-button>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script setup>
import { computed, ref, watch, onMounted, onBeforeUnmount, nextTick } from 'vue'
import * as monaco from 'monaco-editor'
import { ElMessage, ElMessageBox } from 'element-plus'
import { RefreshLeft, RefreshRight } from '@element-plus/icons-vue'
import { getConflictDetail, getConflictDetailByPath, resolveWithContent } from '@/api'
import {
  applyConflictBlockResolution,
  getConflictFileIndex,
  getConflictContent as getConflictContentFromText,
  normalizeConflictDetail,
  parseConflictBlocks,
  resolveConflictFilePath
} from '@/utils/conflict'

function getStashKey(filePath) {
  return 'conflict_stash_' + filePath
}

function saveStash(filePath, content) {
  const key = getStashKey(filePath)
  const stashData = { content, timestamp: Date.now(), filePath }
  localStorage.setItem(key, JSON.stringify(stashData))
}

function loadStash(filePath) {
  const key = getStashKey(filePath)
  const data = localStorage.getItem(key)
  if (!data) return null
  try {
    return JSON.parse(data)
  } catch {
    return null
  }
}

function clearStash(filePath) {
  const key = getStashKey(filePath)
  localStorage.removeItem(key)
}

function hasStash(filePath) {
  return !!loadStash(filePath)
}

const props = defineProps({
  visible: { type: Boolean, default: false },
  localPath: { type: String, default: '' },
  filePath: { type: String, default: '' },
  conflictFiles: { type: Array, default: () => [] },
  repoId: { type: Number, default: null }
})

const emit = defineEmits(['close', 'resolved'])

const oursEditor = ref(null)
const resultEditor = ref(null)
const theirsEditor = ref(null)

let oursModel = null
let resultModel = null
let theirsModel = null
let oursEditorInstance = null
let resultEditorInstance = null
let theirsEditorInstance = null

let oursDiffDecorations = []
let theirsDiffDecorations = []

const conflictBlocks = ref([])
const currentConflictIndex = ref(0)
const currentFileIndex = ref(0)
const isSyncingScroll = ref(false)
const resolvedConflictsCount = ref(0)
const originalConflictCount = ref(0)

const originalContent = ref('')
const isLoadingError = ref(false)
const retryCount = ref(0)
const maxRetryCount = 3
const hasFallbackWarning = ref(false)
const canUndo = ref(false)
const canRedo = ref(false)
const baseContent = ref('')
let resultContentListenerDispose = null

const activeFilePath = computed(() => resolveConflictFilePath(props.filePath, props.conflictFiles, currentFileIndex.value))

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

function highlightConflictBlocks() {
  if (!resultEditorInstance) return

  const decorations = conflictBlocks.value.map((block, index) => {
    const isCurrent = index === currentConflictIndex.value
    return {
      range: new monaco.Range(block.startLine + 1, 1, block.endLine + 1, 1),
      options: {
        isWholeLine: true,
        className: isCurrent ? 'current-conflict-block' : 'conflict-block',
        linesDecorationsClassName: isCurrent ? 'current-conflict-margin' : 'conflict-margin'
      }
    }
  })

  resultEditorInstance.deltaDecorations([], decorations)
}

function highlightDiffDecorations(oursContent, theirsContent, baseContent) {
  if (!oursEditorInstance || !theirsEditorInstance) return
  
  const baseLines = baseContent.split('\n')
  const oursLines = oursContent.split('\n')
  const theirsLines = theirsContent.split('\n')
  
  oursDiffDecorations = computeDiffDecorations(oursLines, baseLines)
  theirsDiffDecorations = computeDiffDecorations(theirsLines, baseLines)
  
  if (oursEditorInstance) {
    oursEditorInstance.deltaDecorations([], oursDiffDecorations)
  }
  if (theirsEditorInstance) {
    theirsEditorInstance.deltaDecorations([], theirsDiffDecorations)
  }
}

function computeDiffDecorations(otherLines, baseLines) {
  const decorations = []
  const maxLen = Math.max(otherLines.length, baseLines.length)
  
  for (let i = 0; i < maxLen; i++) {
    const otherLine = otherLines[i]
    const baseLine = baseLines[i]
    
    if (baseLine === undefined) {
      decorations.push({
        range: new monaco.Range(i + 1, 1, i + 1, 1000),
        options: {
          isWholeLine: true,
          className: 'diff-added-line'
        }
      })
    } else if (otherLine === undefined) {
      continue
    } else if (otherLine !== baseLine) {
      decorations.push({
        range: new monaco.Range(i + 1, 1, i + 1, 1000),
        options: {
          isWholeLine: true,
          className: 'diff-modified-line'
        }
      })
    }
  }
  
  return decorations
}

function disposeAllEditors() {
  const editors = [oursEditorInstance, resultEditorInstance, theirsEditorInstance]
  const models = [oursModel, resultModel, theirsModel]

  editors.forEach(editor => {
    try {
      if (editor) {
        editor.dispose()
      }
    } catch (e) {
      // ignore duplicate dispose errors
    }
  })

  models.forEach(model => {
    try {
      if (model) {
        model.dispose()
      }
    } catch (e) {
      // ignore duplicate dispose errors
    }
  })

  oursEditorInstance = null
  resultEditorInstance = null
  theirsEditorInstance = null
  oursModel = null
  resultModel = null
  theirsModel = null
}

function createEditors() {
  disposeAllEditors()

  if (!oursEditor.value || !resultEditor.value || !theirsEditor.value) return

  const language = detectLanguage(props.filePath)

  const commonOptions = {
    automaticLayout: true,
    minimap: { enabled: false },
    scrollBeyondLastLine: false,
    fontSize: 13,
    lineNumbers: 'on',
    folding: true,
    wordWrap: 'off'
  }

  oursEditorInstance = monaco.editor.create(oursEditor.value, {
    ...commonOptions,
    readOnly: true
  })

  resultEditorInstance = monaco.editor.create(resultEditor.value, {
    ...commonOptions,
    readOnly: false
  })

  theirsEditorInstance = monaco.editor.create(theirsEditor.value, {
    ...commonOptions,
    readOnly: true
  })

  setupScrollSync()
}

let isScrollSyncing = false

function setupScrollSync() {
  if (!oursEditorInstance || !resultEditorInstance || !theirsEditorInstance) return

  const editors = [oursEditorInstance, resultEditorInstance, theirsEditorInstance]

  editors.forEach(editor => {
    editor.onDidScrollChange(() => {
      if (isScrollSyncing) return
      isScrollSyncing = true

      const topLine = editor.getTopVisibleLineNumber()
      editors.forEach(other => {
        if (other !== editor) {
          other.revealLine(topLine)
        }
      })

      setTimeout(() => { isScrollSyncing = false }, 50)
    })
  })
}

function updateModels(oursContent, theirsContent, resultContent) {
  const language = detectLanguage(props.filePath)

  oursDiffDecorations = []
  theirsDiffDecorations = []

  if (!oursModel) {
    oursModel = monaco.editor.createModel(oursContent, language)
    theirsModel = monaco.editor.createModel(theirsContent, language)
    resultModel = monaco.editor.createModel(resultContent, language)
  } else {
    oursModel.setValue(oursContent)
    theirsModel.setValue(theirsContent)
    resultModel.setValue(resultContent)
  }

  oursEditorInstance.setModel(oursModel)
  resultEditorInstance.setModel(resultModel)
  theirsEditorInstance.setModel(theirsModel)

  originalContent.value = resultContent
  conflictBlocks.value = parseConflictBlocks(resultContent)
  currentConflictIndex.value = conflictBlocks.value.length > 0 ? 0 : -1
  resolvedConflictsCount.value = 0
  originalConflictCount.value = conflictBlocks.value.length

  highlightConflictBlocks()
  highlightDiffDecorations(oursContent, theirsContent, baseContent.value)
  setupUndoRedoListener()
}

function getConflictContent(blockIndex) {
  if (!resultModel || blockIndex < 0 || blockIndex >= conflictBlocks.value.length) return null

  return getConflictContentFromText(resultModel.getValue(), conflictBlocks.value, blockIndex)
}

function computeDiffLines(baseLines, otherLines) {
  const m = baseLines.length
  const n = otherLines.length

  const dp = Array.from({ length: m + 1 }, () => Array(n + 1).fill(0))

  for (let i = 1; i <= m; i++) {
    for (let j = 1; j <= n; j++) {
      if (baseLines[i - 1] === otherLines[j - 1]) {
        dp[i][j] = dp[i - 1][j - 1] + 1
      } else {
        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1])
      }
    }
  }

  const result = []
  let i = m
  let j = n

  const baseChanges = new Map()
  const addedLines = []

  while (i > 0 || j > 0) {
    if (i > 0 && j > 0 && baseLines[i - 1] === otherLines[j - 1]) {
      baseChanges.set(i - 1, { status: 'unchanged', otherIndex: j - 1 })
      i--
      j--
    } else if (j > 0 && (i === 0 || dp[i][j - 1] >= dp[i - 1][j])) {
      addedLines.push(j - 1)
      j--
    } else if (i > 0) {
      baseChanges.set(i - 1, { status: 'modified' })
      i--
    }
  }

  for (let idx = 0; idx < m; idx++) {
    if (baseChanges.has(idx)) {
      const change = baseChanges.get(idx)
      result.push({ lineIndex: idx, status: change.status, otherIndex: change.otherIndex })
    } else {
      result.push({ lineIndex: idx, status: 'modified' })
    }
  }

  addedLines.reverse()

  return { baseDiffs: result, addedLines }
}

function resolveModifiedLine(baseIdx, baseLines, otherLines, diffMap, prevAddedLines) {
  const info = diffMap.get(baseIdx)
  
  if (info?.status === 'unchanged' && info.otherIndex != null) {
    return otherLines[info.otherIndex]
  }
  
  let prevUnchanged = null
  for (let i = baseIdx - 1; i >= 0; i--) {
    const prevInfo = diffMap.get(i)
    if (prevInfo?.status === 'unchanged' && prevInfo.otherIndex != null) {
      prevUnchanged = prevInfo
      break
    }
  }
  
  if (prevUnchanged) {
    const baseGap = baseIdx - prevUnchanged.lineIndex
    const addedBetween = prevAddedLines.filter(idx => idx > prevUnchanged.otherIndex && idx < prevUnchanged.otherIndex + baseGap + (otherLines.length - baseLines.length))
    const approxIndex = prevUnchanged.otherIndex + baseGap + addedBetween.length
    if (approxIndex < otherLines.length) {
      return otherLines[approxIndex]
    }
  }
  
  return baseLines[baseIdx]
}

function threeWayMerge(localContent, baseContentStr, remoteContent) {
  const baseLines = baseContentStr.split('\n')
  const localLines = localContent.split('\n')
  const remoteLines = remoteContent.split('\n')

  const localDiff = computeDiffLines(baseLines, localLines)
  const remoteDiff = computeDiffLines(baseLines, remoteLines)

  const localMap = new Map()
  for (const d of localDiff.baseDiffs) localMap.set(d.lineIndex, d)

  const remoteMap = new Map()
  for (const d of remoteDiff.baseDiffs) remoteMap.set(d.lineIndex, d)

  const mergedLines = []

  for (let bi = 0; bi < baseLines.length; bi++) {
    const l = localMap.get(bi)
    const r = remoteMap.get(bi)

    const lUnchanged = l?.status === 'unchanged'
    const rUnchanged = r?.status === 'unchanged'

    if (lUnchanged && rUnchanged) {
      mergedLines.push(baseLines[bi])
    } else if (lUnchanged) {
      mergedLines.push(resolveModifiedLine(bi, baseLines, remoteLines, remoteMap, remoteDiff.addedLines))
    } else if (rUnchanged) {
      mergedLines.push(resolveModifiedLine(bi, baseLines, localLines, localMap, localDiff.addedLines))
    } else {
      const lLine = resolveModifiedLine(bi, baseLines, localLines, localMap, localDiff.addedLines)
      const rLine = resolveModifiedLine(bi, baseLines, remoteLines, remoteMap, remoteDiff.addedLines)
      if (lLine === rLine) {
        mergedLines.push(lLine)
      } else {
        mergedLines.push('<<<<<<< HEAD')
        mergedLines.push(lLine)
        mergedLines.push('=======')
        mergedLines.push(rLine)
        mergedLines.push('>>>>>>> remote')
      }
    }
  }

  return mergedLines.join('\n')
}

function applyToBlock(blockIndex, action) {
  if (!resultModel || blockIndex < 0 || blockIndex >= conflictBlocks.value.length) return

  const conflictInfo = getConflictContent(blockIndex)
  if (!conflictInfo) return

  const { block, localContent, remoteContent } = conflictInfo
  const currentResult = resultModel.getValue()

  const oldCount = conflictBlocks.value.length
  if (action === 'merge') {
    const lines = currentResult.split('\n')
    const newContent = threeWayMerge(localContent, baseContent.value, remoteContent)
    lines.splice(block.startLine, block.endLine - block.startLine + 1, newContent)
    resultModel.setValue(lines.join('\n'))
    conflictBlocks.value = parseConflictBlocks(resultModel.getValue())
  } else {
    const result = applyConflictBlockResolution(currentResult, conflictBlocks.value, blockIndex, action)
    resultModel.setValue(result.content)
    conflictBlocks.value = result.remainingBlocks
  }
  resolvedConflictsCount.value += (oldCount - conflictBlocks.value.length)
  if (conflictBlocks.value.length > 0) {
    currentConflictIndex.value = Math.min(currentConflictIndex.value, conflictBlocks.value.length - 1)
  } else {
    currentConflictIndex.value = -1
  }

  highlightConflictBlocks()
}

function adoptLocal(blockIndex) {
  if (blockIndex >= 0) {
    applyToBlock(blockIndex, 'local')
  }
}

function adoptRemote(blockIndex) {
  if (blockIndex >= 0) {
    applyToBlock(blockIndex, 'remote')
  }
}

function smartMerge(blockIndex) {
  if (blockIndex >= 0) {
    applyToBlock(blockIndex, 'merge')
  }
}

function adoptAllLocal() {
  for (let i = conflictBlocks.value.length - 1; i >= 0; i--) {
    applyToBlock(i, 'local')
  }
  ElMessage.success('已采用所有本地内容')
}

function adoptAllRemote() {
  for (let i = conflictBlocks.value.length - 1; i >= 0; i--) {
    applyToBlock(i, 'remote')
  }
  ElMessage.success('已采用所有远程内容')
}

function smartMergeAll() {
  for (let i = conflictBlocks.value.length - 1; i >= 0; i--) {
    applyToBlock(i, 'merge')
  }
  ElMessage.success('已智能合并所有冲突')
}

function handleBulkCommand(command) {
  switch (command) {
    case 'adoptAllLocal':
      adoptAllLocal()
      break
    case 'adoptAllRemote':
      adoptAllRemote()
      break
    case 'smartMergeAll':
      smartMergeAll()
      break
  }
}

function prevConflict() {
  if (conflictBlocks.value.length === 0) return
  currentConflictIndex.value = currentConflictIndex.value <= 0
    ? conflictBlocks.value.length - 1
    : currentConflictIndex.value - 1
  scrollToConflict(currentConflictIndex.value)
  highlightConflictBlocks()
}

function nextConflict() {
  if (conflictBlocks.value.length === 0) return
  currentConflictIndex.value = (currentConflictIndex.value + 1) % conflictBlocks.value.length
  scrollToConflict(currentConflictIndex.value)
  highlightConflictBlocks()
}

function scrollToConflict(index) {
  if (!resultEditorInstance || index < 0 || index >= conflictBlocks.value.length) return
  const block = conflictBlocks.value[index]
  resultEditorInstance.revealLineInCenter(block.startLine + 1)
  resultEditorInstance.setPosition({ lineNumber: block.startLine + 1, column: 1 })
}

function onFileChange() {
  currentConflictIndex.value = 0
  conflictBlocks.value = []
  loadConflictDetail()
}

function handleStash() {
  if (!resultModel || !activeFilePath.value) {
    ElMessage.warning('当前没有可暂存的冲突文件')
    return
  }
  const content = resultModel.getValue()
  saveStash(activeFilePath.value, content)
  ElMessage.success('内容已暂存')
}

async function handleMarkResolved() {
  if (!props.repoId || !activeFilePath.value) {
    ElMessage.error('缺少必要参数')
    return
  }

  try {
    const content = resultModel.getValue()
    await resolveWithContent(props.repoId, {
      filePath: activeFilePath.value,
      content
    })
    ElMessage.success('冲突已解决')
    emit('resolved')
  } catch (error) {
    ElMessage.error('解决冲突失败: ' + error.message)
  }
}

async function loadConflictDetail() {
  if (!activeFilePath.value) return

  isLoadingError.value = false
  hasFallbackWarning.value = false

  try {
    let data
    if (props.localPath) {
      const res = await getConflictDetailByPath(props.localPath, activeFilePath.value)
      data = res.data
    } else if (props.repoId) {
      const res = await getConflictDetail(props.repoId, activeFilePath.value)
      data = res.data
    } else {
      ElMessage.error('缺少本地路径或仓库ID')
      return
    }

    const normalizedDetail = normalizeConflictDetail(data)
    let oursContent = normalizedDetail.oursContent
    let theirsContent = normalizedDetail.theirsContent
    baseContent.value = normalizedDetail.baseContent
    const resultContent = normalizedDetail.resultContent

    hasFallbackWarning.value = false

    if (!oursContent) {
      if (baseContent.value) {
        oursContent = baseContent.value
        hasFallbackWarning.value = true
      } else {
        oursContent = resultContent
        hasFallbackWarning.value = true
      }
    }

    if (!theirsContent) {
      if (baseContent.value) {
        theirsContent = baseContent.value
      } else {
        theirsContent = oursContent
      }
      hasFallbackWarning.value = true
    }

    isLoadingError.value = false
    retryCount.value = 0
    originalContent.value = resultContent
    updateModels(oursContent, theirsContent, resultContent)

    const stashData = loadStash(activeFilePath.value)
    if (stashData && stashData.content) {
      const stashTime = new Date(stashData.timestamp).toLocaleString('zh-CN')
      try {
        await ElMessageBox.confirm(
          `检测到该文件的暂存内容（暂存时间：${stashTime}），是否恢复？`,
          '发现暂存内容',
          {
            confirmButtonText: '恢复',
            cancelButtonText: '忽略',
            type: 'info'
          }
        )
        if (resultModel) {
          resultModel.setValue(stashData.content)
          conflictBlocks.value = parseConflictBlocks(stashData.content)
          highlightConflictBlocks()
        }
        clearStash(activeFilePath.value)
        ElMessage.success('已恢复暂存内容')
      } catch {
        clearStash(activeFilePath.value)
      }
    }
  } catch (error) {
    retryCount.value++
    if (retryCount.value < maxRetryCount) {
      ElMessage.error(`加载失败 (${retryCount.value}/${maxRetryCount})，可点击重试按钮重试`)
      isLoadingError.value = true
    } else {
      ElMessage.error('加载冲突详情失败: ' + error.message)
      isLoadingError.value = true
    }
  }
}

function retryLoad() {
  if (!isLoadingError.value) return
  isLoadingError.value = false
  retryCount.value = 0
  loadConflictDetail()
}

function setupUndoRedoListener() {
  if (resultContentListenerDispose) {
    resultContentListenerDispose.dispose()
    resultContentListenerDispose = null
  }
  if (!resultModel) return

  resultContentListenerDispose = resultModel.onDidChangeContent(() => {
    canUndo.value = resultEditorInstance?.getModel()?.canUndo || false
    canRedo.value = resultEditorInstance?.getModel()?.canRedo || false
  })
}

watch(() => [props.filePath, props.conflictFiles], () => {
  currentFileIndex.value = getConflictFileIndex(props.filePath, props.conflictFiles)
  if (props.visible && activeFilePath.value) {
    currentConflictIndex.value = 0
    conflictBlocks.value = []
    loadConflictDetail()
  }
}, { deep: true })

watch(() => props.visible, (newVal) => {
  if (newVal) {
    currentFileIndex.value = getConflictFileIndex(props.filePath, props.conflictFiles)
    nextTick(() => {
      if (!oursEditorInstance) {
        createEditors()
      }
      loadConflictDetail()
    })
  }
})

watch(() => currentConflictIndex.value, () => {
  highlightConflictBlocks()
})

onMounted(() => {
  if (props.visible) {
    nextTick(() => {
      createEditors()
      loadConflictDetail()
    })
  }
})

onBeforeUnmount(() => {
  if (resultContentListenerDispose) {
    resultContentListenerDispose.dispose()
    resultContentListenerDispose = null
  }
  disposeAllEditors()
})

defineExpose({
  adoptLocal,
  adoptRemote,
  smartMerge,
  adoptAllLocal,
  adoptAllRemote,
  smartMergeAll,
  retryLoad
})
</script>

<style scoped>
.conflict-resolver {
  height: 80vh;
  display: flex;
  flex-direction: column;
  min-width: 0;
  background: linear-gradient(180deg, #fbfdff 0%, #f3f7fc 100%);
  border-radius: 16px;
  overflow: hidden;
}

.toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  background: rgba(255, 255, 255, 0.94);
  border: 1px solid var(--app-border);
  border-bottom: none;
  flex-shrink: 0;
  gap: 12px;
  flex-wrap: wrap;
}

.toolbar-left,
.toolbar-center,
.toolbar-right {
  display: flex;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
  min-width: 0;
}

.file-path {
  font-size: 12px;
  color: #606266;
  font-family: monospace;
  max-width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.editors-container {
  flex: 1;
  display: flex;
  gap: 10px;
  background: transparent;
  min-height: 0;
  padding: 10px;
}

.editor-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
  border: 1px solid var(--app-border);
  border-radius: 14px;
  overflow: hidden;
  background: #fff;
  box-shadow: var(--app-shadow-soft);
}

.editor-header {
  padding: 10px 12px;
  background: #f5f7fa;
  border-bottom: 1px solid var(--app-border);
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 0.04em;
  color: #606266;
  flex-shrink: 0;
}

.ours-header {
  background: #fef0f0;
  color: #f56c6c;
}

.result-header {
  background: #fdf6ec;
  color: #e6a23c;
}

.theirs-header {
  background: #f0f9ff;
  color: #409eff;
}

.editor-instance {
  flex: 1;
  min-height: 0;
}

.bottom-bar {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  padding: 14px 16px;
  background: rgba(255, 255, 255, 0.96);
  border: 1px solid var(--app-border);
  border-top: none;
  flex-shrink: 0;
  gap: 12px;
}

.progress-section {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.progress-text {
  font-size: 13px;
  color: #606266;
  white-space: nowrap;
}

.conflict-operations,
.bottom-actions {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
}

:deep(.conflict-block) {
  background: #fff3cd !important;
}

:deep(.current-conflict-block) {
  background: #ffe0e0 !important;
}

:deep(.conflict-margin) {
  background: #fff3cd !important;
  width: 5px !important;
  margin-left: 2px;
}

:deep(.current-conflict-margin) {
  background: #ffe0e0 !important;
  width: 5px !important;
  margin-left: 2px;
}

:deep(.diff-added-line) {
  background: rgba(103, 194, 58, 0.15) !important;
}

:deep(.diff-deleted-line) {
  background: rgba(245, 108, 108, 0.15) !important;
}

:deep(.diff-modified-line) {
  background: rgba(230, 162, 60, 0.15) !important;
}

@media (max-width: 1280px) {
  .editors-container {
    flex-direction: column;
  }

  .editor-panel {
    min-height: 220px;
  }

  .toolbar-left,
  .toolbar-center,
  .toolbar-right,
  .conflict-operations,
  .bottom-actions {
    width: 100%;
  }

  .bottom-actions {
    justify-content: flex-end;
  }
}
</style>
