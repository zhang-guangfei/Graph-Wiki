import request from '@/utils/request'

const url = process.env.OPS_API_WM
// const url = 'http://10.116.1.15:9000'

export function modifyOrder(data) {
  return request({
    url: url + '/order/rcvorder/modify/handle',
    method: 'post',
    data
  })
}
export function resetOrder(data) {
  return request({
    url: url + '/order/orderChangeToInitStatus',
    method: 'post',
    data
  })
}

export function getOrderStatus(orderId, orderItem) {
  return request({
    url: url + '/order/status/search',
    method: 'get',
    params: { orderId, orderItem }
  })
}

export function getOrderStatusMigrate(orderId, orderItem) {
  return request({
    url: url + '/order/status/search/migrate',
    method: 'get',
    params: { orderId, orderItem }
  })
}

export function getOrderAssign(orderId, orderItem) {
  return request({
    url: url + '/order/assign/search',
    method: 'get',
    params: { orderId, orderItem }
  })
}

export function getOldOrderAssign(orderId, orderItem) {
  return request({
    url: url + '/order/rcvorder/assign/search/order',
    method: 'get',
    params: { orderId, orderItem }
  })
}
