/** 岗位节点配置API */
import request from '@/utils/request'

// 获取业务类型列表
export function findBussinessTypeOptions() {
  return request({
    url: process.env.SALE_MANAGE_API + '/processBusinessType/findProcessBusinessType',
    method: 'get'
  })
}

// 根据业务类型获取审批节点列表
export function findProcessNode(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processNode/findByBusinessType',
    method: 'get',
    params: params
  })
}

// 添加岗位节点绑定信息
export function addProcessNodePosition(datas) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processNodePosition/addProcessNodePosition',
    method: 'post',
    data: datas
  })
}

// 根据岗位名称获取审批节点信息
export function findByPositionName(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/processNodePosition/findByPositionNameAndBusinessType',
    method: 'get',
    params: params
  })
}
