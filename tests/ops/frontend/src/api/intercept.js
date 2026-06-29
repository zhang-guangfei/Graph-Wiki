/** 字典管理API */
import request from '@/utils/request'

// 正常拦截初始化页面
export function findList(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/findRequestList',
    method: 'post',
    data
  })
}

// 拦截订单放行操作
export function orderPass(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/orderPass',
    method: 'post',
    data
  })
}

// 拦截订单还原
export function interceptRestore(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/interceptRestore',
    method: 'post',
    data
  })
}

// 查询所有供应商
export function getSuppily() {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/getSuppily',
    method: 'get'
  })
}

// 查询所有运输方式
export function getTrans() {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/getTrans',
    method: 'get'
  })
}

export function editRemarkData(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/editRemark',
    method: 'post',
    data
  })
}

/**
 * 仓库名称转换
 */
export function getWarehouse() {
  return request({
    url: process.env.OPS_API_PO + '/intercept/getWarehouse',
    method: 'post'
  })
}

// 拦截批量还原
export function restoreBatch(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/restoreBatch',
    method: 'post',
    data
  })
}

// 拦截批量放行
export function orderpassBatch(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/orderpassBatch',
    method: 'post',
    data
  })
}
