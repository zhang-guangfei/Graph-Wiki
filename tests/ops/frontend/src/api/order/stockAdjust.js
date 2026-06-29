import request from '@/utils/request'

const url = process.env.OPS_ORDER_API

export function saveAdjustdata(formData) {
  return request({
    url: url + '/order/adjust/addAdjustData',
    method: 'post',
    data: formData
  })
}

export function listAdjustdata(form) {
  return request({
    url: url + '/order/adjust/listAdjustData',
    method: 'post',
    data: form
  })
}

export function exportAdjustData(data) {
  return request({
    url: url + '/order/adjust/exportStockAdjustData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

export function deleteAdjustData(id) {
  return request({
    url: url + '/order/adjust/delAdjustData',
    method: 'get',
    params: { id }
  })
}

export function createInvoiceNo() {
  return request({
    url: url + '/order/adjust/createInvoiceNo',
    method: 'get'
  })
}

export function getOrderInfoForImpAdjuest(fullOrderNo) {
  return request({
    url: url + '/order/adjust/getOrderInfoForImpAdjuest',
    method: 'get',
    params: { fullOrderNo }
  })
}

export function importStockAdjustData(formData) {
  return request({
    url: url + '/order/adjust/importStockAdjustData',
    method: 'post',
    data: formData
  })
}

export function determineStockAdjust(invoiceNo) {
  return request({
    url: url + '/order/adjust/determineStockAdjust',
    method: 'get',
    params: { invoiceNo }
  })
}
