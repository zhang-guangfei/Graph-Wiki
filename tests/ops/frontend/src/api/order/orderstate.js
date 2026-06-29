import request from '@/utils/request'

const url = process.env.OPS_ORDER_API

// 货期状态查询
export function fetchList(searchForm) {
  return request({
    url: url + '/order/state/list',
    method: 'post',
    data: searchForm
  })
}

export function queryByOrderNo(orderNo) {
  return request({
    url: url + '/order/state/getStateDetail',
    method: 'get',
    params: { orderNo }
  })
}
export function exportOrderState(data) {
  return request({
    url: url + '/order/rcvorder/exportOrderState',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
export function getSplitOrderState(rorderNo, itemNo) {
  return request({
    url: url + '/order/state/getSplitOrderState',
    method: 'get',
    params: { rorderNo, itemNo }
  })
}
