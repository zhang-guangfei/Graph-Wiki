export const DEFAULT_REQUIREMENT_TYPE = '功能开发'

export const REQUIREMENT_TYPE_OPTIONS = [
  '功能开发',
  '功能优化',
  'Bug修复',
  '优化',
  '重构',
  '配置调整',
  '代码清理'
]

const REQUIREMENT_TYPE_COLOR_MAP = {
  '功能开发': 'success',
  '功能优化': 'success',
  'Bug修复': 'danger',
  '优化': 'warning',
  '重构': 'info',
  '配置调整': 'primary',
  '代码清理': 'info'
}

export function normalizeRequirementType(type) {
  const value = String(type || '').trim()
  return REQUIREMENT_TYPE_OPTIONS.includes(value) ? value : DEFAULT_REQUIREMENT_TYPE
}

export function getRequirementTypeColor(type) {
  return REQUIREMENT_TYPE_COLOR_MAP[(type || '').trim()] || 'info'
}

export function buildRequirementFormat(type, requirementId, title) {
  if (type && typeof type === 'object' && !Array.isArray(type)) {
    const input = type
    return buildRequirementFormat(input.type, input.requirementId, input.title)
  }
  const normalizedType = normalizeRequirementType(type)
  const normalizedId = String(requirementId || '').trim()
  const normalizedTitle = String(title || '').trim()
  return `【${normalizedType}】#${normalizedId} ${normalizedTitle}`.trim()
}

export function buildRequirementPayload(input = {}) {
  const requirementId = String(input.requirementId || '').trim()
  const title = String(input.title || '').trim()
  const description = String(input.description || '').trim()
  const type = normalizeRequirementType(input.type)

  return {
    requirementId,
    title,
    description,
    type,
    format: buildRequirementFormat(type, requirementId, title)
  }
}

export function buildRequirementPayloadFromMessage(message) {
  const parsed = parseRequirementMessage(message)
  return parsed ? buildRequirementPayload(parsed) : null
}

export function getDefaultCommitTemplates() {
  return REQUIREMENT_TYPE_OPTIONS.map(type => ({
    name: type,
    format: `【${type}】`
  }))
}

export function parseRequirementMessage(message) {
  if (!message) return null

  const normalized = String(message).trim()
  const patterns = [
    /^【(.+?)】\s*#?(\d+)\s+(.+)$/,
    /^\[(.+?)\]\s*#?(\d+)\s+(.+)$/
  ]

  for (const pattern of patterns) {
    const match = normalized.match(pattern)
    if (match) {
      const [, type, requirementId, title] = match
      return {
        type: normalizeRequirementType(type),
        requirementId: requirementId.trim(),
        title: title.trim()
      }
    }
  }

  return null
}

function toRevisionNumber(log) {
  const raw = log?.revision
  const value = Number(raw)
  return Number.isFinite(value) ? value : 0
}

function toDateValue(log) {
  const raw = log?.date || log?.createdAt || log?.updatedAt
  const value = raw ? new Date(raw).getTime() : 0
  return Number.isFinite(value) ? value : 0
}

export function groupLogsByRequirement(logs, requirementLookup = {}) {
  const groupMap = new Map()
  const ungrouped = []

  for (const log of Array.isArray(logs) ? logs : []) {
    const parsed = log.requirementId ? null : parseRequirementMessage(log.message)
    const requirementId = String(log.requirementId || parsed?.requirementId || '').trim()

    if (!requirementId) {
      ungrouped.push({
        ...log,
        requirementId: null,
        requirementTitle: null,
        requirementType: null
      })
      continue
    }

    const requirementInfo = requirementLookup[requirementId] || {}
    const existing = groupMap.get(requirementId) || {
      requirementId,
      requirementTitle: requirementInfo.title || log.requirementTitle || null,
      requirementType: requirementInfo.type || log.requirementType || null,
      count: 0,
      latestRevision: 0,
      latestDate: 0,
      items: []
    }

    const revisionNumber = toRevisionNumber(log)
    const dateValue = toDateValue(log)

    existing.count += 1
    existing.items.push({
      ...log,
      requirementId,
      requirementTitle: existing.requirementTitle,
      requirementType: existing.requirementType
    })

    if (
      revisionNumber > existing.latestRevision ||
      (revisionNumber === existing.latestRevision && dateValue >= existing.latestDate)
    ) {
      existing.latestRevision = revisionNumber
      existing.latestDate = dateValue
      existing.latestLog = log
    }

    groupMap.set(requirementId, existing)
  }

  const groups = [...groupMap.values()].sort((a, b) => {
    if (b.latestRevision !== a.latestRevision) return b.latestRevision - a.latestRevision
    return b.latestDate - a.latestDate
  }).map(group => ({
    ...group,
    items: group.items.slice().sort((a, b) => {
      const revisionDiff = toRevisionNumber(b) - toRevisionNumber(a)
      if (revisionDiff !== 0) return revisionDiff
      return toDateValue(b) - toDateValue(a)
    })
  }))

  if (ungrouped.length > 0) {
    groups.push({
      requirementId: null,
      requirementTitle: '未关联需求',
      requirementType: null,
      count: ungrouped.length,
      latestRevision: 0,
      latestDate: 0,
      items: ungrouped,
      isUngrouped: true
    })
  }

  return groups
}
