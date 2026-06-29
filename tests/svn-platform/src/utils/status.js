export function getConflictedFilePaths(statusList = []) {
  return statusList
    .filter(item => item?.status === 'conflicted' && item?.path)
    .map(item => item.path)
}
