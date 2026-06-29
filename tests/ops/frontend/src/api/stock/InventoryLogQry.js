import request from '@/utils/request'
const url = process.env.OPS_STOCK_API
// const url = 'http://localhost:8101'

export function listInventoryLogData(data) {
  return request({
    url: url + '/product/inventory/listInventoryLogData',
    method: 'post',
    data: data
  })
}

export function getOpsInventoryProperty(id) {
  return request({
    url: url + '/product/inventory/getOpsInventoryProperty',
    method: 'get',
    params: { id }
  })
}

export function exportInventoryLog(data) {
  return request({
    url: url + '/product/inventory/exportInventoryLogData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
