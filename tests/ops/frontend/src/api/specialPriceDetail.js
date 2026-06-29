import request from '@/utils/request'
/**
 * 获取特价详情相关信息
 */
export function getSpecialDetail(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/specialPrice/detail',
    method: 'get',
    params: params
  })
}
/**
 * 获取审批详情
 */
export function getApprovalDetail(params) {
  return request({
    url: process.env.SALE_MDM_API + '/approval/findApprovalDetailByBusinessKey',
    method: 'get',
    params: params
  })
}
/**
 * 获取整单利润率
 */
export function getProfitRate(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/specialPrice/profitRate',
    method: 'get',
    params: params
  })
}
/**
 * 获取整单亏损额度颜色
 */
export function getAllItemColor(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/specialPrice/allItemColor',
    method: 'get',
    params: params
  })
}
/**
 * 获取特价明细的单项颜色显示
 */
export function getItemColor(userId, positionName, specialPriceNo) {
  return request({
    url: process.env.SALE_MANAGE_API + '/specialPrice/itemColor',
    method: 'get',
    params: { userId, positionName, specialPriceNo }
  })
}
