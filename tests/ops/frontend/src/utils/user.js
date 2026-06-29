/**
 * 判断登录人是否有批示权
 * @param {*} operatorId 登录人用户Id
 * @param {*} parentList 日报填写人上级列表
 */
export function isCanPostilMethod(operatorId, parentList) {
  for (const key in parentList) {
    if (parentList[key].psnsmcId === operatorId) {
      return true
    }
  }
  return false
}
