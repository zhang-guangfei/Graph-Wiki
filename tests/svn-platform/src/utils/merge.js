import { parseRequirementMessage } from './requirement.js'

function normalizeRequirementId(value) {
  return String(value || '').trim()
}

export function buildRequirementLookup(requirements = []) {
  return (Array.isArray(requirements) ? requirements : []).reduce((lookup, item) => {
    const requirementId = normalizeRequirementId(item.requirementId)
    if (!requirementId) return lookup

    lookup[requirementId] = {
      requirementId,
      title: String(item.title || '').trim(),
      type: String(item.type || '').trim(),
      format: item.format || ''
    }
    return lookup
  }, {})
}

export function extractRequirementId(message) {
  if (!message) return null

  const parsed = parseRequirementMessage(message)
  if (parsed?.requirementId) {
    return normalizeRequirementId(parsed.requirementId)
  }

  const match = String(message).match(/(\d{4,})/)
  return match ? normalizeRequirementId(match[1]) : null
}

export function enrichMergeLogs(logs, mergedRevisions = new Set(), requirementLookup = {}) {
  const mergedSet = mergedRevisions instanceof Set ? mergedRevisions : new Set(mergedRevisions || [])

  return (Array.isArray(logs) ? logs : []).map(log => {
    const requirementId = normalizeRequirementId(log.requirementId || extractRequirementId(log.message)) || null
    const requirementInfo = requirementId ? requirementLookup[requirementId] || {} : {}

    return {
      ...log,
      merged: mergedSet.has(Number(log.revision)),
      requirementId,
      requirementTitle: requirementInfo.title || log.requirementTitle || '',
      requirementType: requirementInfo.type || log.requirementType || ''
    }
  })
}

export function filterMergeLogs(logs, filters = {}) {
  const {
    mergeStatusFilter = 'all',
    requirementIds = [],
    searchKeyword = ''
  } = filters

  const normalizedRequirementIds = new Set(
    (Array.isArray(requirementIds) ? requirementIds : [requirementIds])
      .map(normalizeRequirementId)
      .filter(Boolean)
  )

  const keyword = String(searchKeyword || '').trim().toLowerCase()

  return (Array.isArray(logs) ? logs : []).filter(log => {
    if (mergeStatusFilter === 'merged' && !log.merged) return false
    if (mergeStatusFilter === 'unmerged' && log.merged) return false

    if (normalizedRequirementIds.size > 0) {
      const requirementId = normalizeRequirementId(log.requirementId)
      if (!normalizedRequirementIds.has(requirementId)) return false
    }

    if (!keyword) return true

    if (log.author && String(log.author).toLowerCase().includes(keyword)) return true
    if (log.message && String(log.message).toLowerCase().includes(keyword)) return true
    if (String(log.revision || '').includes(keyword)) return true

    return false
  })
}

export function summarizeRequirementSelection(logs = []) {
  const groupsMap = new Map()
  let mergedCount = 0

  for (const log of Array.isArray(logs) ? logs : []) {
    const requirementId = normalizeRequirementId(log.requirementId)
    const groupKey = requirementId || '__ungrouped__'
    const current = groupsMap.get(groupKey) || {
      requirementId: requirementId || null,
      requirementTitle: log.requirementTitle || (requirementId ? '' : '未关联需求'),
      requirementType: log.requirementType || '',
      revisions: [],
      revisionCount: 0,
      mergedCount: 0,
      latestRevision: 0
    }

    const revision = Number(log.revision) || 0
    current.revisions.push(revision)
    current.revisionCount += 1
    current.latestRevision = Math.max(current.latestRevision, revision)

    if (log.merged) {
      current.mergedCount += 1
      mergedCount += 1
    }

    groupsMap.set(groupKey, current)
  }

  const groups = [...groupsMap.values()]
    .map(group => ({
      ...group,
      revisions: group.revisions.slice().sort((a, b) => b - a)
    }))
    .sort((a, b) => b.latestRevision - a.latestRevision)

  return {
    requirementCount: groups.length,
    revisionCount: logs.length,
    mergedCount,
    unmergedCount: logs.length - mergedCount,
    groups
  }
}
