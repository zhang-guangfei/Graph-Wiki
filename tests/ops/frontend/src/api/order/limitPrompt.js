import request from '@/utils/request'
const url = process.env.OPS_ORDER_API

export function searchStockTransferPlanByPage(data) {
  return request({
    url: url + '/order/limitPrompt/page',
    method: 'post',
    data
  })
}

export function selectBufferDays() {
  return request({
    url: url + '/order/limitPrompt/find/bufferDays',
    method: 'get'
  })
}

export function updateBufferDays(bufferDays) {
  return request({
    url: url + '/order/limitPrompt/update/bufferDays',
    method: 'post',
    data: { 'bufferDays': bufferDays }
  })
}

export function exportData(data) {
  return request({
    url: url + '/order/limitPrompt/export',
    method: 'post',
    data
  })
}
