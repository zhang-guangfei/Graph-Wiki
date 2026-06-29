/** 日志API */
import request from '@/utils/request'

export function fetchList(condition, page) {
  return request({
    url: process.env.BASE_API + '/log/query',
    method: 'post',
    data: condition,
    params: page
  })
}
export function deleteBatchById(idList) {
  return request({
    url: process.env.BASE_API + '/log/delete',
    method: 'post',
    data: idList
  })
}
export function deleteAll(condition) {
  return request({
    url: process.env.BASE_API + '/log/deleteAll',
    method: 'post',
    data: condition
  })
}
