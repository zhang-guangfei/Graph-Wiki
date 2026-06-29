/** 字典管理API */
import request from '@/utils/request'

// shikomi拦截查询方法
export function findList(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/findRequestShikomiList',
    method: 'post',
    data
  })
}

// shikomi拦截查询方法
export function findListShikomi(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/findRequestShikomiList',
    method: 'post',
    data
  })
}

// 查询所有供应商
export function updateShikomiRequest(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/shikomiPass',
    method: 'post',
    data
  })
}
