import request from '@/utils/request'
const url = process.env.OPS_STOCK_API
// const url = 'http://localhost:8101'

export function findList(data) {
  return request({
    url: url + '/product/shikomi/listShikomi',
    method: 'post',
    data: data
  })
}

export function saveShikomidata(formData) {
  return request({
    url: url + '/product/shikomi/saveShikomidata',
    method: 'post',
    data: formData
  })
}

export function savecusdata(formData) {
  return request({
    url: url + '/product/shikomi/saveCustomerdata',
    method: 'post',
    data: formData
  })
}

// 上传文件
export function uploadFile(formData) {
  return request({
    url: url + '/product/shikomi/importJPApplyFile',
    method: 'post',
    data: formData
  })
}

export function saveModel(formData) {
  return request({
    url: url + '/product/shikomi/addRelationModel',
    method: 'post',
    data: formData
  })
}

export function findCustomerDataByNo(shikomiNo) {
  return request({
    url: url + '/product/shikomi/findCustomerDataByNo',
    method: 'get',
    params: { shikomiNo }
  })
}

export function sendInspectionReport() {
  return request({
    url: url + '/product/shikomi/sendInspection',
    method: 'post'
  })
}

export function checkShikomiInspectionByDept() {
  return request({
    url: url + '/product/shikomi/checkShikomiInspectionByDept',
    method: 'post'
  })
}

export function calcShikomiInspection(formData) {
  return request({
    url: url + '/product/shikomi/calcShikomiInspection',
    method: 'post',
    data: formData
  })
}

export function checkStatus() {
  return request({
    url: url + '/product/shikomi/checkStatus',
    method: 'get'
  })
}

export function findInspectionData(shikomiNo) {
  return request({
    url: url + '/product/shikomi/findInspectionInfo',
    method: 'post',
    params: { shikomiNo }
  })
}

export function getModelEPrice(modelNo) {
  return request({
    url: url + '/product/shikomi/getModelEPrice',
    method: 'get',
    params: { modelNo }
  })
}

export function findModelNoByshikomi(shikomiNo) {
  return request({
    url: url + '/product/shikomi/findModelByNo',
    method: 'get',
    params: { shikomiNo }
  })
}

export function inspectProcess(data) {
  return request({
    url: url + '/product/shikomi/inspectProcess',
    method: 'post',
    data: data
  })
}

export function exportShikomi(data) {
  return request({
    url: url + '/product/shikomi/exportShikomi',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

export function listShikomiInspection(data) {
  return request({
    url: url + '/product/shikomi/listPageShikomiInspection',
    method: 'post',
    data: data
  })
}

export function exportShikomiInspection(data) {
  return request({
    url: url + '/product/shikomi/exportShikomiInspectionData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

export function importCNFile(formData) {
  return request({
    url: url + '/product/shikomi/importCNReplyFile',
    method: 'post',
    data: formData
  })
}

export function delShikomiCustomerById(id) {
  return request({
    url: url + '/product/shikomi/delShikomiCustomerById',
    method: 'get',
    params: { id }
  })
}

export function delShikomiByNo(shikomiNo, modelNo, supplierCode) {
  return request({
    url: url + '/product/shikomi/delShikomiByNo',
    method: 'get',
    params: { shikomiNo, modelNo, supplierCode }
  })
}

export function getShikomiSupplier() {
  return request({
    url: url + '/product/shikomi/getShikomiSupplier',
    method: 'get'
  })
}

export function findShikomiBudget(data) {
  return request({
    url: url + '/product/SMSAdapter/findShikomiBudget',
    method: 'post',
    data: data
  })
}
export function exportShikomiBudget(data) {
  return request({
    url: url + '/product/SMSAdapter/exportShikomiBudget',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
export function findAdjustBatchNo(batchNo) {
  return request({
    url: url + '/product/SMSAdapter/findAdjustBatchNo',
    method: 'get',
    params: { batchNo }
  })
}
