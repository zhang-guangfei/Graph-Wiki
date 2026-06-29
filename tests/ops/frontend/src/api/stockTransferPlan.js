import request from '@/utils/request'

const url = process.env.OPS_API_WM
// const url = 'http://10.116.1.15:9000'

export function getStockTransferPlanList(data) {
  return request({
    url: url + '/inventory/getStockTransferPlanList',
    method: 'post',
    data
  })
}
export function getStockTransferPlanItemList(data) {
  return request({
    url: url + '/inventory/getStockTransferPlanItemList',
    method: 'post',
    data
  })
}

export function delStockTransferPlan(data, username) {
  return request({
    url: url + '/inventory/delStockTransferPlan?userName=' + username,
    method: 'post',
    data
  })
}
