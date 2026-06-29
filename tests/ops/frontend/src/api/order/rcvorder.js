import request from '@/utils/request'

const url = process.env.OPS_ORDER_API
const wmUrl = process.env.OPS_API_WM

// 接单查询
export function fetchList(searchRequest, page) {
  return request({
    url: url + '/order/rcvorder/listRcvOrder',
    method: 'post',
    data: searchRequest,
    params: page
  })
}

// 订单接入查询
export function listOrderSales(orderReceiveForm, page) {
  return request({
    url: url + '/order/rcvorder/listOrderSales',
    method: 'post',
    data: orderReceiveForm,
    params: page
  })
}

export function queryOrderItem(queryParams) {
  return request({
    url: process.env.OPS_API_WM + '/order/cust/dispatch/result',
    method: 'get',
    params: queryParams
  })
}

export function getOrderCountByDeptNo(data) {
  return request({
    url: url + '/order/rcvorder/getOrderCountByDeptNo',
    method: 'post',
    data: data
  })
}

/**
 * 手动接入订单
 */
export function rcvOrderByOrderSales(data) {
  return request({
    url: url + '/order/rcvorder/rcvOrderByOrderSales',
    method: 'post',
    data: data
  })
}

/**
 * 接单处理定时任务状态 状态
 */
export function xxlJobAutoOrderHandlerStatus(parityFlag) {
  return request({
    url: url + '/order/rcvorder/xxlJobAutoOrderHandlerStatus',
    method: 'get',
    params: { parityFlag }
  })
}

/**
 * 接单处理定时任务状态 开关
 */

export function xxlJobAutoOrderHandlerSwitch(flag, parityFlag) {
  return request({
    url: url + '/order/rcvorder/xxlJobAutoOrderHandlerSwitch',
    method: 'get',
    params: { flag, parityFlag }
  })
}

/**
 * 接单处理定时任务状态 状态
 */
export function xxlJobAutoOrderOneDayHandlerStatus() {
  return request({
    url: wmUrl + '/order/xxlJobAutoOrderOneDayHandlerStatus',
    method: 'get'
  })
}

/**
 * 接单处理定时任务状态 开关
 */

export function xxlJobAutoOrderOneDayHandlerSwitch(flag) {
  return request({
    url: wmUrl + '/order/xxlJobAutoOrderOneDayHandlerSwitch',
    method: 'get',
    params: { flag }
  })
}
/**
 * 转为处理订单
 */
export function convertToProcessing(data) {
  return request({
    url: url + '/order/rcvorder/convertToProcessing',
    method: 'post',
    data: JSON.stringify(data)
  })
}

/**
 * 自动接单处理
 */
export function createByorder(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/create/order?rorderNo=' + data.rorderNo + '&rorderItem=' + data.rorderItem,
    method: 'post'
  })
}

export function getRcvOrderDataByNo(orderNo) {
  return request({
    url: url + '/order/rcvorder/getRcvOrderDataByNo',
    method: 'get',
    params: { orderNo }
  })
}
