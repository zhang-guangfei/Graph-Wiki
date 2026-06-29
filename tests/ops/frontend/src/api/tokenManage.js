/** 令牌管理API */
import request from '@/utils/request'
export function fetchList(condition) {
  return request({
    url: process.env.BASE_API + '/tokenManage/token',
    method: 'get',
    params: condition
  })
}

export function deleteToken(token) {
  return request({
    url: process.env.BASE_API + '/tokenManage/delete/' + token,
    method: 'POST'
  })
}
