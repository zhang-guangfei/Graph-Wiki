import request from '@/utils/request'

export function findList(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchaseDeliver/selectDeliverInfo',
    method: 'post',
    data
  })
}

export function findListDetail(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchaseDeliver/selectDetailInfo',
    method: 'post',
    data
  })
}
