/** 终端管理API */
import request from '@/utils/request'
export function query(params) {
  return request({
    url: process.env.BASE_API + '/oauthClientDetails/query',
    method: 'get',
    params: params
  })
}

/**
 * 删除
 * @param
 */
export function deleteTerminal(clientId) {
  return request({
    url: process.env.BASE_API + '/oauthClientDetails/delete/' + clientId,
    method: 'post',
    params: clientId
  })
}

/**
 * 添加
 * @param {*新增数据} dataConditions
 */
export function addTerminal(data) {
  return request({
    url: process.env.BASE_API + '/oauthClientDetails/add',
    method: 'post',
    params: data
  })
}

/**
 * 更新
 * @param {*更新数据} dataConditions
 */
export function updateTerminal(data) {
  return request({
    url: process.env.BASE_API + '/oauthClientDetails/update',
    method: 'post',
    params: data
  })
}

export function findClientId() {
  return request({
    url: process.env.BASE_API + '/oauthClientDetails/findClientId',
    method: 'get'
  })
}
