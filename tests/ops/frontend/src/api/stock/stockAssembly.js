import request from '@/utils/request'
const url = process.env.OPS_SHAREAPP_API

// 保存组换调库申请
export function saveApply(data) {
  return request({
    url: url + '/shareapp/stockassembly/save',
    method: 'post',
    data: data
  })
}

// 查询单个申请详情
export function getApplyByNo(params) {
  return request({
    url: url + '/shareapp/stockassembly/getApplyByNo',
    method: 'get',
    params: params
  })
}

// 分页查询申请项
export function listApplyDetail(data) {
  return request({
    url: url + '/shareapp/stockassembly/listApplyDetail',
    method: 'post',
    data: data
  })
}

// 取消申请
export function removeApply(params) {
  return request({
    url: url + '/shareapp/stockassembly/removeApply',
    method: 'get',
    params: params
  })
}

// 删除组换调库申请项
export function removeDetail(params) {
  return request({
    url: url + '/shareapp/stockassembly/removeDetail',
    method: 'get',
    params: params
  })
}

// 查询
export function listApply(data) {
  return request({
    url: url + '/shareapp/stockassembly/listStockAssembly',
    method: 'post',
    data: data
  })
}

// 提交申请
export function submitApply(data) {
  return request({
    url: url + '/shareapp/stockassembly/submit',
    method: 'post',
    data: data
  })
}

// 审核确认
export function approveApply(params) {
  return request({
    url: url + '/shareapp/stockassembly/approve',
    method: 'post',
    data: params
  })
}

// 处理申请
export function handleApply(params) {
  return request({
    url: url + '/shareapp/stockassembly/handle',
    method: 'post',
    data: params
  })
}

// 直接执行
export function fastHandleApply(data) {
  return request({
    url: url + '/shareapp/stockassembly/fastHandle',
    method: 'post',
    data: data
  })
}

// 多条件查询申请详情
export function listDetail(params) {
  return request({
    url: url + '/shareapp/stockassembly/listDetail',
    method: 'post',
    data: params
  })
}

// 导出组换调库申请项清单
export function exportDetail(data) {
  return request({
    url: url + '/shareapp/stockassembly/exportStockAssemblyDetail',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

// 导出组换调库申请项导入模板
export function exportTemplate() {
  return request({
    url: url + '/shareapp/stockassembly/exportApplyDetailTemplate',
    method: 'get',
    responseType: 'blob'
  })
}

// 导入组换调库申请项
export function importApplyDetail(data) {
  return request({
    url: url + '/shareapp/stockassembly/importStockAssemblyApplyDetail',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: data
  })
}

// 根据型号查询在库通用库存与在库专用库存
export function getWarehouseStock(params) {
  return request({
    url: url + '/product/inventory/getWarehouseStock',
    method: 'post',
    params: params
  })
}

// 查询客户专备情况
export function getSpecStock(data) {
  return request({
    url: url + '/product/inventory/listCustomerSpecStock',
    method: 'post',
    data: data
  })
}
