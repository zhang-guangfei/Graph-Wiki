import request from '@/utils/request'

// 查询申请数据
export function listApply(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listCsStockApply',
    method: 'post',
    data: data
  })
}

// 计算申请数据
export function listReplDetail(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listReplDetail',
    method: 'get',
    params: data
  })
}

// 确认并生成申请号
export function createApply(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/createReplApply',
    method: 'get',
    params: data
  })
}

// 申请清单明细
export function listApplyDetail(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listCsStockApplyDetail',
    method: 'post',
    data: data
  })
}
// 删除申请子项
export function removeDetail(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/removeDetail',
    method: 'get',
    params: data
  })
}
// 删除申请
export function removeApply(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/removeApply',
    method: 'get',
    params: data
  })
}
// 提交申请申请
export function confirmApply(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/confirmCsApply',
    method: 'post',
    data: data
  })
}
// 新增子项
export function addDetail(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/addDetail',
    method: 'post',
    data: data
  })
}
// 修改子项
export function updateDetail(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/updateDetail',
    method: 'post',
    data: data
  })
}
// 归属寄售库房
export function listWarehourseStockCode(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listWarehourseStockCode',
    method: 'get',
    params: data
  })
}
