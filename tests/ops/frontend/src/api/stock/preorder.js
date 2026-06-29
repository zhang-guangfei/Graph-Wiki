import request from '@/utils/request'
const url = process.env.OPS_SHAREAPP_API

// 分页查询、搜索
export function listPreOrderAccount(data) {
  return request({
    url: url + '/shareapp/preorderaccount/listPreOrderAccount',
    method: 'post',
    data: data
  })
}
// 查询详细
export function listPreOrderDetail(data) {
  return request({
    url: url + '/shareapp/preorderaccount/listPreOrderDetail',
    method: 'post',
    data: data
  })
}

// 导出主表
export function exportPreOrderAccount(data) {
  return request({
    url: url + '/shareapp/preorderaccount/exportPreOrderAccount',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

// 导出详细
export function exportPreOrderDetail(data) {
  return request({
    url: url + '/shareapp/preorderaccount/exportPreOrderDetail',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
export function handleTransferByIds(data) {
  return request({
    url: url + '/shareapp/preorderaccount/handleTransferByIds',
    method: 'post',
    data: data
  })
}
export function listPreOrderAccountConfig(data) {
  return request({
    url: url + '/shareapp/preorderaccount/listPreOrderAccountConfig',
    method: 'post',
    data: data
  })
}

export function writeAndUpdatePreOrderAccountConfig(data) {
  return request({
    url: url + '/shareapp/preorderaccount/writeAndUpdatePreOrderAccountConfig',
    method: 'post',
    data: data
  })
}

// 导出主表
export function exportPreOrderAccountConfig(data) {
  return request({
    url: url + '/shareapp/preorderaccount/exportPreOrderAccountConfig',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

// 导入数据
export function importPreOrderAccountConfig(formData) {
  return request({
    url: url + '/shareapp/preorderaccount/importPreOrderAccountConfig',
    method: 'post',
    data: formData
  })
}

// 先行在库预决算处理
export function handlePreOrderAccountByIds(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/shareapp/preorderaccount/handlePreOrderAccountByIds',
    method: 'post',
    data: data
  })
}
// 申请记录查询
export function listPreOrderApplyDetail(data) {
  return request({
    url: url + '/shareapp/preorderaccount/listPreOrderApplyDetail',
    method: 'post',
    data: data
  })
}
// 导出主表
export function exportPreOrderApplyDetail(data) {
  return request({
    url: url + '/shareapp/preorderaccount/exportPreOrderApplyDetail',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
// advancedHand
export function advancedHand(data) {
  return request({
    url: url + '/shareapp/preorderaccount/advancedHand',
    method: 'post',
    data: data
  })
}
export function downYQPZTemplate() {
  return request({
    url: url + '/shareapp/preorderaccount/downYQPZTemplate',
    method: 'get',
    responseType: 'blob'
  })
}

export function deletePreOrderAccountConfigById(id) {
  return request({
    url: url + '/shareapp/preorderaccount/deletePreOrderAccountConfigById',
    method: 'get',
    params: { id }
  })
}
