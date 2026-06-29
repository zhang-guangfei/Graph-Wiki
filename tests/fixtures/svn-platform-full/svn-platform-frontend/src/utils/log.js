export function createDefaultLogFilterForm() {
  return {
    limit: 500,
    startRevision: '',
    endRevision: '',
    author: '',
    dateRange: [],
    path: '',
    keyword: '',
    requirementType: '',
    requirementId: ''
  }
}
