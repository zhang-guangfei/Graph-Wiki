import request from '@/utils/request'
const url = process.env.OPS_SHAREAPP_API

// 分页查询、搜索
export function listApply(data) {
  return request({
    url: url + '/shareapp/prestock/listPreStockApply',
    method: 'post',
    data: data
  })
}

// 保存申请
export function saveApply(data) {
  return request({
    url: url + '/shareapp/prestock/saveApply',
    method: 'post',
    data: data
  })
}

// 查询申请详情
export function getApply(params) {
  return request({
    url: url + '/shareapp/prestock/getApply',
    method: 'get',
    params: params
  })
}

// 分页查询申请项
export function listApplyDetail(data) {
  return request({
    url: url + '/shareapp/prestock/listApplyDetail',
    method: 'post',
    data: data
  })
}

// 根据id查询申请项对应的处理结果
export function getApplyDetailResult(params) {
  return request({
    url: url + '/shareapp/prestock/getApplyDetailResult',
    method: 'get',
    params: params
  })
}

// 根据id删除申请项
export function removeDetail(params) {
  return request({
    url: url + '/shareapp/prestock/removeDetail',
    method: 'get',
    params: params
  })
}

// 生成日本SHIKOMI文件
export function exportJPShikomiFile() {
  return request({
    url: url + '/shareapp/prestock/exportShikomiFile',
    method: 'get'
  })
}

// 查询处理结果详情
export function getResult(params) {
  return request({
    url: url + '/shareapp/prestock/getResult',
    method: 'get',
    params: params
  })
}

// 提交申请
export function submitApply(data) {
  return request({
    url: url + '/shareapp/prestock/submitApply',
    method: 'post',
    data: data
  })
}

// 审核确认申请
export function approveApply(data) {
  return request({
    url: url + '/shareapp/prestock/approveApply',
    method: 'post',
    data: data
  })
}

// 保存Shikomi申请信息
export function saveShikomiInfo(data) {
  return request({
    url: url + '/shareapp/prestock/saveShikomiInfo',
    method: 'post',
    data: data
  })
}

// 按申请自动处理
export function autoHandleApply(data) {
  return request({
    url: url + '/shareapp/prestock/autoHandleApply',
    method: 'post',
    data: data
  })
}

// 多条件查询先行在库申请详情视图
export function listDetail(data) {
  return request({
    url: url + '/shareapp/prestock/listPreStockDetail',
    method: 'post',
    data: data
  })
}

// 导出先行在库申请项清单
export function exportDetail(data) {
  return request({
    url: url + '/shareapp/prestock/exportPreStockDetail',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

// 根据detailId生成手动处理项
export function getProcessItem(detailId) {
  return request({
    url: url + '/shareapp/prestock/getProcessItemByDetailId',
    method: 'get',
    params: detailId
  })
}

// 执行手动处理项
export function handleProcessItem(data) {
  return request({
    url: url + '/shareapp/prestock/handleProcessItem',
    method: 'post',
    data: data
  })
}

// 重置申请处理状态
export function resetApplyProcessStatus(data) {
  return request({
    url: url + '/shareapp/prestock/resetApplyProcessStatus',
    method: 'post',
    data: data
  })
}

// 重置SHIKOMI申请处理
export function resetShikomiProcess(data) {
  return request({
    url: url + '/shareapp/prestock/resetShikomiProcess',
    method: 'post',
    data: data
  })
}

// 采购退单（3）  =》 处理中（2），或暂不备库（9）
export function updatePreStockDetailStatus(data) {
  return request({
    url: url + '/shareapp/prestock/updatePreStockDetailStatus',
    method: 'post',
    data: data
  })
}
//
export function updatePreStockDetailTranFlag(data) {
  return request({
    url: url + '/shareapp/prestock/updatePreStockDetailTranFlag',
    method: 'post',
    data: data
  })
}

// 查询inventoryPropertyId库存信息
export function getInventoryPropertyId(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/product/inventory/checkInventoryProperty',
    method: 'post',
    data: data
  })
}

// 按库存属性汇总型号库存信息
export function listInventorySummary(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/product/inventory/listInventorySummaryByPropertyId',
    method: 'post',
    data: data
  })
}
