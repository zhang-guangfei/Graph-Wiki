import request from '@/utils/request'

export function findList(data) {
  return request({
    url: process.env.OPS_API_PO + '/invoiceError/getInvoiceErrorListForPage',
    method: 'post',
    data
  })
}

export function exportData(data) {
  return request({
    url: process.env.OPS_API_PO + '/invoiceError/getInvoiceErrorList',
    method: 'post',
    data
  })
}

export function getErrorType() {
  return request({
    url: process.env.OPS_API_PO + '/invoiceError/getInvoiceCheckRule',
    method: 'get'
  })
}
