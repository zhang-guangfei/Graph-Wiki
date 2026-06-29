/** 作流待办列表API */
import request from '@/utils/request'
export function query(conditions) {
  return request({
    url: process.env.SALE_MANAGE_API + '/approvalCenter/query',
    method: 'get',
    params: conditions
  })
}

/**
 * 已完成任务列表查询
 * @param {} conditions 查询条件
 */
export function done(conditions) {
  return request({
    url: process.env.SALE_MANAGE_API + '/approvalCenter/done',
    method: 'get',
    params: conditions
  })
}
/**
 * 签收
 * @param {*} params
 */
export function sign(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/approvalCenter/sign',
    method: 'post',
    params: params
  })
}

/**
 * 处理
 * @param params
 */
export function detail(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/approvalCenter/detail',
    method: 'post',
    params: params
  })
}

/**
 * 处理
 * @param taskId
 */
export function detailByTaskId(taskId) {
  return request({
    url: process.env.SALE_MANAGE_API + '/approvalCenter/detail',
    method: 'post',
    params: { taskId }
  })
}

/**
 * 查看
 * @param  params
 */
export function check(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/approvalCenter/check',
    method: 'post',
    params: params
  })
}
export function findApprovalDetailByBusinessKey(businessKey) {
  return request({
    url: process.env.SALE_MANAGE_API + '/approval/findApprovalDetailByBusinessKey',
    method: 'get',
    params: { businessKey }
  })
}
// 可驳回的节点集合
export function rejectAssignNodeAggregate(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/history/reject/assignNode/aggregate',
    method: 'get',
    params: params
  })
}
// 驳回到指定节点
export function rejectAssignNode(approvalBean, executionId) {
  return request({
    url: process.env.SALE_MANAGE_API + '/history/reject/assignNode/' + executionId,
    method: 'post',
    params: approvalBean
  })
}
// 驳回
export function reject(approvalBean, processInstanceId) {
  return request({
    url: process.env.SALE_MANAGE_API + '/history/reject/' + processInstanceId,
    method: 'post',
    params: approvalBean
  })
}
// 委派任务: 被委派人办理完后，回到委派人节点
export function delegate(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/task/delegate',
    method: 'get',
    params: params
  })
}
// 转办任务：被委派人办理完后，进入下一个节点
export function transferTask(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/task/transfer',
    method: 'get',
    params: params
  })
}
//  委派任务：被委派人办理时调用
export function resolve(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/task/resolve',
    method: 'get',
    params: params
  })
}

/**
 * 通过businessKey查询task信息
 * @param {*} businessKey 主键
 */
export function findByProcessInstanceBusinessKey(businessKey) {
  return request({
    url: process.env.SALE_MANAGE_API + '/task/findTaskByProcessInstanceBusinessKey',
    method: 'get',
    params: { businessKey }
  })
}

export function findProcessDefinitionKeyList() {
  return request({
    url: process.env.SALE_MANAGE_API + '/todoDefinition/findAll',
    method: 'get'
  })
}
