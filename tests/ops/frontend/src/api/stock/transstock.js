import request from '@/utils/request'
const url = process.env.OPS_STOCK_API

export function findTransOrder(data) {
  return request({
    url: url + '/cpfr/trans/findTransOrder',
    method: 'post',
    data: data
  })
}

export function cancelTransOrder(data) {
  return request({
    url: url + '/cpfr/trans/cancelTransOrder',
    method: 'post',
    data: data
  })
}

export function exportTrans(data) {
  return request({
    url: url + '/cpfr/trans/exportTransData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

export function askOrderFinish(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/askTKCKFinish',
    method: 'post',
    data
  })
}

export function exeFinish(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/exeTKCKFinish',
    method: 'post',
    data
  })
}
