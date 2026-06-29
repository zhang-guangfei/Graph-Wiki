/** 流程管理模型API */
import request from '@/utils/request'
export function fetchList(condition) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processDefinition/query',
    method: 'get',
    params: condition
  })
}
export function uploadFiles(formData) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processDefinition/deploy',
    method: 'POST',
    data: formData
  })
}
export function uploadFilesDirect(formData) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processDefinition/deploy/direct',
    method: 'POST',
    data: formData
  })
}
export function deleteProcessDefinition(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processDefinition/delete',
    method: 'POST',
    data: params
  })
}
export function detail(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processDefinition/detail',
    method: 'GET',
    responseType: 'arraybuffer',
    params: params
  })
}
export function xmlDetail(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processDefinition/detail',
    method: 'GET',
    params: params
  })
}
// 查找进程实例
export function queryProcessInstance(condition) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processInstance/query',
    method: 'GET',
    params: condition
  })
}
// 中断 进程
export function suspend(processInstanceId) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processInstance/suspend',
    method: 'POST',
    params: processInstanceId
  })
}
// 激活 进程
export function resume(processInstanceId) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processInstance/resume',
    method: 'POST',
    params: processInstanceId
  })
}
// 获取参数
export function getVariables(processInstanceId) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processInstance/variables',
    method: 'get',
    params: processInstanceId
  })
}

