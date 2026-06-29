import request from '@/utils/request'
// const url = 'http://localhost:8103'
const url = process.env.OPS_SHAREAPP_API

// 退货查询
export function fetchList(searchRequest, page) {
  return request({
    url: url + '/shareapp/returnorder/findAll',
    method: 'post',
    data: searchRequest,
    params: page
  })
}

/**
 * 执行退货
 */
export function backOrder(data) {
  return request({
    url: url + '/shareapp/returnorder/backOrder',
    method: 'post',
    data: JSON.stringify(data)
  })
}

/**
 * 预览打印退货单
 */
export function printReturnOrder(data) {
  return request({
    url: url + '/shareapp/returnorder/printReturnOrder',
    responseType: 'blob',
    method: 'post',
    data: data
  })
}
/**
 * 退货确认(退货登记)
 */
export function rcvOrderReGister(data) {
  return request({
    url: url + '/shareapp/returnorder/receiveGoods',
    method: 'post',
    data: data
  })
}
/**
 * 包裹拒收
 */
export function refuseRcv(data) {
  return request({
    url: url + '/shareapp/returnorder/refuseRcv',
    method: 'post',
    data: data
  })
}
export function exportReturnOrder(data) {
  return request({
    url: url + '/shareapp/returnorder/exportReturnOrder',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

export function cancelReturnOrder(data) {
  return request({
    url: url + '/shareapp/returnorder/cancelReturnOrder',
    method: 'post',
    data: data
  })
}

export function creatApplyNo() {
  return request({
    url: url + '/shareapp/returnorder/creatApplyNo',
    method: 'get'
  })
}

export function addReturnOrder(from) {
  return request({
    url: url + '/shareapp/returnorder/insertReturnOrder',
    method: 'post',
    data: from
  })
}

export function getWareHouseByCustomerNo(customerNo) {
  return request({
    url: url + '/shareapp/returnorder/getWareHouseByCustomerNo',
    method: 'get',
    params: { customerNo }
  })
}

export function downloadFile(fileName, applyNo) {
  return request({
    url: url + '/shareapp/returnorder/downloadReturnFile',
    responseType: 'blob',
    method: 'post',
    params: { fileName, applyNo }
  })
}
export function saveTableToRedis(data) {
  return request({
    url: url + '/shareapp/returnorder/saveTableToRedis',
    method: 'post',
    data: data
  })
}
export function getTableFromRedis(data) {
  return request({
    url: url + '/shareapp/returnorder/getTableFromRedis',
    method: 'post',
    data: data
  })
}
export function getAllTableFromRedis(data) {
  return request({
    url: url + '/shareapp/returnorder/getAllTableFromRedis',
    method: 'post',
    data: data
  })
}
