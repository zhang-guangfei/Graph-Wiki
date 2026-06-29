import request from '@/utils/request'

// 查询型号设置
export function listSetting(data, page) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listCsStockSetting',
    method: 'post',
    data: data,
    params: page
  })
}
// 上传文件
export function uploadFile(formData, type) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/importCsStockSettingData',
    method: 'post',
    data: formData,
    params: { type }
  })
}
// 查询寄售清单
export function listCsWarehouse(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/listCsWarehouse',
    method: 'post',
    data: data
  })
}
// 保存寄售库房管理
export function saveCsWarehourse(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/saveCsWarehourse',
    method: 'post',
    data: data
  })
}
// 删除寄售库房
export function deleteCsWarehourse(params) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/deleteCsWarehourse',
    method: 'get',
    params: params
  })
}
// 删除寄售库房
export function deleteCsSetting(params) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/deleteCsStockSettingById',
    method: 'get',
    params: params
  })
}
// 启停用寄售库房
export function updateCsWarehourseStatus(params) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/updateCsWarehourseStatus',
    method: 'get',
    params: params
  })
}
// 修改寄售库房
export function updateCsSetting(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/updateCsStockSetting',
    method: 'post',
    data: data
  })
}
// 启停用寄售型号备库
export function updateCsSettingStatus(params) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/csstock/updateCsSettingModelStatus',
    method: 'get',
    params: params
  })
}
// 导出库房清单
export function exportWareHouseData(data) {
  return request({
    url: process.env.OPS_SHAREAPP_API + '/shareapp/cs/exportWareHouseData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
