import request from '@/utils/request'
const url = process.env.OPS_STOCK_API
// const url = 'http://localhost:8101'

export function listProductInterceptRule(data) {
  return request({
    url: url + '/product/intercept/listProductInterceptRule',
    method: 'post',
    data: data
  })
}
export function listProductInterceptRuleByPage(data) {
  return request({
    url: url + '/product/intercept/listProductInterceptRuleByPage',
    method: 'post',
    data: data
  })
}
export function exportProductInterceptRule(data) {
  return request({
    url: url + '/product/intercept/exportProductInterceptRule',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

export function addOrUpdateInterceptRule(data) {
  return request({
    url: url + '/product/intercept/addOrUpdateInterceptRule',
    method: 'post',
    data: data
  })
}

export function deleteInterceptRule(id) {
  return request({
    url: url + '/product/intercept/deleteInterceptRule',
    method: 'get',
    params: { id }
  })
}

export function deleteInterceptRuleByIds(data) {
  return request({
    url: url + '/product/intercept/deleteInterceptRuleByIds',
    method: 'post',
    data: data
  })
}
export function deleteInterceptRuleByCondition(data) {
  return request({
    url: url + '/product/intercept/deleteInterceptRuleByCondition',
    method: 'post',
    data: data
  })
}
