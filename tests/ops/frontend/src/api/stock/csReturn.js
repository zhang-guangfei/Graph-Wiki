import request from '@/utils/request'

// 查询退回数据
export function listCalcReturn(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listCalcReturn',
    method: 'get',
    params: data
  })
}

// 分页查询退回数据
export function pageListCalcReturn(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/cs/pageListCalcReturn',
    method: 'post',
    data: data
  })
}

// 查询退货申请数据
export function listApply(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listReturnApply',
    method: 'post',
    data: data
  })
}

export function listApplyMaster(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listCalcReturnMaster',
    method: 'post',
    data: data
  })
}

// 查询退货申请数据
export function listDetail(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listReturnDetail',
    method: 'post',
    data: data
  })
}
// 查询退货申请数据
export function createApply(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/createReturnApply',
    method: 'get',
    params: data
  })
}
// 保存修改数据
export function updateDetail(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/updateReturnDetail',
    method: 'get',
    params: data
  })
}

// 打印退货清单
export function printReturnApply(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/printReturnApply',
    responseType: 'blob',
    method: 'get',
    params: data
  })
}

export function updateReturnQty(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/updateCsTmpReturnCalcDataById',
    method: 'post',
    data: data
  })
}

export function confirmCsReturnApply(applyId) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/cs/confirmCsReturnApply',
    method: 'get',
    params: { applyId }
  })
}

export function deleteCsReturnApply(id) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/deleteCsReturnApplyById',
    method: 'get',
    params: { id }
  })
}

export function exportReturnData() {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/exportCalcRetrunData',
    responseType: 'blob',
    method: 'post'
  })
}
