import request from '@/utils/request'

const url = process.env.OPS_API_PO
// const url = 'http://10.116.1.232:8023'

export function queryPrepareOrderList(data) {
  return request({
    url: url + '/prepareOrder/queryPrepareOrderList',
    method: 'post',
    data: data
  })
}
export function exportPrepareOrder(data) {
  return request({
    url: url + '/prepareOrder/exportPrepareOrder',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
export function editPrepareOrderAvailableUser(data) {
  return request({
    url: url + '/prepareOrder/editPrepareOrderAvailableUser',
    method: 'post',
    data: data
  })
}
export function getPrepareOrderBomDetail(orderFno) {
  return request({
    url: url + '/prepareOrder/getPrepareOrderBomDetail',
    method: 'get',
    params: { orderFno }
  })
}

export function getPrepareOrderVerifyInfo(orderFno) {
  return request({
    url: url + '/prepareOrder/getPrepareOrderVerifyInfo',
    method: 'get',
    params: { orderFno }
  })
}

export function getPrepareOrderAccountInfo(orderFno) {
  return request({
    url: url + '/prepareOrder/getPrepareOrderAccountInfo',
    method: 'get',
    params: { orderFno }
  })
}

export function getPrepareOrderLiquidationInfo(orderFno) {
  return request({
    url: url + '/prepareOrder/getPrepareOrderLiquidationInfo',
    method: 'get',
    params: { orderFno }
  })
}

export function getPrepareOrderInfo(orderFno) {
  return request({
    url: url + '/prepareOrder/getPrepareOrderInfo',
    method: 'get',
    params: { orderFno }
  })
}
export function updatePreQty(data) {
  return request({
    url: url + '/prepareOrder/updatePreQty',
    method: 'post',
    data: data
  })
}
export function prepareOrderTransfer(data) {
  return request({
    url: url + '/prepareOrder/prepareOrderTransfer',
    method: 'post',
    data: data
  })
}
export function updateDlvDays(dlvDays) {
  return request({
    url: url + '/prepareOrder/updateDlvDays',
    method: 'get',
    params: { dlvDays }
  })
}
// getDlvDays
export function getDlvDays() {
  return request({
    url: url + '/prepareOrder/getDlvDays',
    method: 'get'
  })
}
