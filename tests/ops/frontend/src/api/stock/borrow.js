import request from '@/utils/request'
const url = process.env.OPS_SHAREAPP_API
export function findbrwstock(data, page) {
  return request({
    url: url + '/shareapp/borrowstock/listBrwStockInfo',
    method: 'post',
    data: data,
    params: page
  })
}

export function findNotReturn(data, page) {
  return request({
    url: url + '/shareapp/borrowstock/listNotReturn',
    method: 'post',
    data: data,
    params: page
  })
}

export function findbrwDetail(data) {
  return request({
    url: url + '/shareapp/borrowstock/listBrwDetail',
    method: 'post',
    params: data
  })
}

export function exWarehouse(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/Adjust',
    method: 'post',
    data: data
  })
}
