function safeString(value) {
  return typeof value === 'string' ? value : ''
}

export function resolveConflictFilePath(filePath = '', conflictFiles = [], currentFileIndex = 0) {
  const normalizedFiles = Array.isArray(conflictFiles) ? conflictFiles.filter(Boolean) : []
  const directPath = safeString(filePath).trim()

  if (directPath) {
    return directPath
  }

  if (normalizedFiles.length === 0) {
    return ''
  }

  const safeIndex = Math.min(Math.max(Number(currentFileIndex) || 0, 0), normalizedFiles.length - 1)
  return normalizedFiles[safeIndex] || ''
}

export function getConflictFileIndex(filePath = '', conflictFiles = []) {
  const normalizedFiles = Array.isArray(conflictFiles) ? conflictFiles.filter(Boolean) : []
  const directPath = safeString(filePath).trim()

  if (!directPath || normalizedFiles.length === 0) {
    return 0
  }

  const matchIndex = normalizedFiles.findIndex(item => item === directPath)
  return matchIndex >= 0 ? matchIndex : 0
}

export function normalizeConflictDetail(detail = {}) {
  const mine = safeString(detail.mine)
  const ours = safeString(detail.ours)
  const theirs = safeString(detail.theirs)
  const base = safeString(detail.base)
  const merged = safeString(detail.merged)

  return {
    oursContent: ours || mine,
    resultContent: merged || mine,
    theirsContent: theirs,
    baseContent: base
  }
}

export function parseConflictBlocks(content = '') {
  const blocks = []
  const lines = safeString(content).split('\n')
  let i = 0

  while (i < lines.length) {
    if (lines[i].startsWith('<<<<<<<')) {
      const outerStartLine = i
      let separatorLine = -1
      let remoteEnd = -1

      i++
      while (i < lines.length) {
        if (lines[i].startsWith('=======') && separatorLine === -1) {
          separatorLine = i
        } else if (lines[i].startsWith('>>>>>>>')) {
          remoteEnd = i
          i++
          break
        }
        i++
      }

      if (separatorLine === -1 || remoteEnd === -1) {
        continue
      }

      const localEnd = separatorLine - 1
      const remoteStart = separatorLine + 1

      if (!(outerStartLine < separatorLine && separatorLine < remoteEnd)) {
        continue
      }

      blocks.push({
        startLine: outerStartLine,
        localEnd,
        separatorLine,
        remoteStart,
        remoteEnd,
        endLine: remoteEnd
      })
    } else {
      i++
    }
  }

  return blocks
}

export function getConflictContent(content = '', blocks = [], blockIndex = 0) {
  if (!Array.isArray(blocks) || blockIndex < 0 || blockIndex >= blocks.length) {
    return null
  }

  const block = blocks[blockIndex]
  const lines = safeString(content).split('\n')

  return {
    block,
    localContent: lines.slice(block.startLine + 1, block.separatorLine).join('\n'),
    remoteContent: lines.slice(block.remoteStart, block.remoteEnd).join('\n'),
    fullBlockContent: lines.slice(block.startLine, block.endLine + 1).join('\n')
  }
}

export function applyConflictBlockResolution(content = '', blocks = [], blockIndex = 0, action = 'local') {
  const conflictInfo = getConflictContent(content, blocks, blockIndex)
  if (!conflictInfo) {
    return {
      content: safeString(content),
      remainingBlocks: Array.isArray(blocks) ? blocks : [],
      resolvedCount: 0
    }
  }

  const { block, localContent, remoteContent } = conflictInfo
  const replacement = action === 'remote' ? remoteContent : localContent
  const lines = safeString(content).split('\n')
  lines.splice(block.startLine, block.endLine - block.startLine + 1, replacement)

  const nextContent = lines.join('\n')
  const remainingBlocks = parseConflictBlocks(nextContent)

  return {
    content: nextContent,
    remainingBlocks,
    resolvedCount: Math.max(0, blocks.length - remainingBlocks.length)
  }
}
