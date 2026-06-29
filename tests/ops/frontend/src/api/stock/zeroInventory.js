import request from '@/utils/request'
const url = process.env.OPS_STOCK_API
// const url = 'http://localhost:8101'

export function listZeroInventoryData(data) {
  return request({
    url: url + '/product/inventory/listZeroInventoryData',
    method: 'post',
    data: data
  })
}

export function exportZeroInventory(data) {
  return request({
    url: url + '/product/inventory/exportZeroInventoryData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
