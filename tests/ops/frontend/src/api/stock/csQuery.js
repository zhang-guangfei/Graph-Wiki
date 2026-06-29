import request from '@/utils/request'

// 查询退回数据
export function listImpData(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listCsImportDetail',
    method: 'post',
    data: data
  })
}

// 查询退回数据
export function listExpData(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listCsExportDetail',
    method: 'post',
    data: data
  })
}
// 查询月结数据
export function listBalData(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listMonthBalance',
    method: 'post',
    data: data
  })
}

// 打印月结数据
export function printBalance(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/printBalance',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

export function calcBalance(monthDate) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/calcBalance',
    method: 'get',
    params: { monthDate }
  })
}

export function getCalcbanceDate() {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/getCalcbanceDate',
    method: 'get'
  })
}

export function updateBalanceDate(id) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/updateCsBalanceDateById',
    method: 'get',
    params: { id }
  })
}

export function updateMothDate(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/updateCsBalaceMothDate',
    method: 'post',
    data: data
  })
}

// 导出月结数据
export function exportCsBalanceData(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/exportCsBalanceData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

// 导出入库数据
export function exportCsImpData(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/exportCsImpData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

// 导出出库数据
export function exportCsExpData(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/exportCsExpData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
