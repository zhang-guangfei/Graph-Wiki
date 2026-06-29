/** 请假管理API */
import request from '@/utils/request'
export function query(conditions) {
  return request({
    url: process.env.SALE_MANAGE_API + '/leave/query',
    method: 'get',
    params: conditions
  })
}

/**
 * 删除
 * @param
 */
export function deleteLeave(clientId) {
  return request({
    url: process.env.SALE_MANAGE_API + '/leave/delete/' + clientId,
    method: 'post',
    params: clientId
  })
}

/**
 * 添加
 * @param {*新增数据} dataConditions
 */
export function addLeave(data) {
  return request({
    url: process.env.SALE_MANAGE_API + '/leave/add',
    method: 'post',
    params: data
  })
}

/**
 * 更新
 * @param {*更新数据} dataConditions
 */
export function updateLeave(data) {
  return request({
    url: process.env.SALE_MANAGE_API + '/leave/update',
    method: 'post',
    params: data
  })
}
export function queryApproval(condition) {
  return request({
    url: process.env.SALE_MANAGE_API + '/leave/queryApprove',
    method: 'get',
    params: condition
  })
}
export function detail(leaveId) {
  return request({
    url: process.env.SALE_MANAGE_API + '/leave/detail',
    method: 'get',
    params: leaveId
  })
}
// 请假审批
export function leaveApproval(approvalBean, activitiId) {
  return request({
    url: process.env.SALE_MANAGE_API + '/leave/' + activitiId,
    method: 'post',
    params: approvalBean
  })
}
