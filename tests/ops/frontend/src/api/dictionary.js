/** 字典管理API */
import request from '@/utils/request'

// 获取所有字典表数据
export function queryAllList() {
  return request({
    url: process.env.BASE_API + '/dictionary/queryAll',
    method: 'get'
  })
}

// 新增字典表数据
export function addRecord(addData) {
  return request({
    url: process.env.BASE_API + '/dictionary/addRecord',
    method: 'post',
    data: addData
  })
}

// 删除字典表数据
export function deleteRecord(delData) {
  return request({
    url: process.env.BASE_API + '/dictionary/deleteById',
    method: 'post',
    data: delData
  })
}

// 更新字典表数据
export function updateRecord(updateData) {
  return request({
    url: process.env.BASE_API + '/dictionary/updateRecord',
    method: 'post',
    data: updateData
  })
}

// 获取所有的表类型名称和代码
export function getDataByPid(params) {
  return request({
    url: process.env.BASE_API + '/dictionary/findByPid',
    method: 'get',
    params: params
  })
}

/**
 * 通过父节点和需要排除的子节点集合查询剩余的子节点
 * @param {*} pid 父节点
 * @data {*} childIdList 子节点集合
 */
export function getByPidAndChildId(pid, childIdList) {
  return request({
    url: process.env.BASE_API + '/dictionary/findByPidAndChildIds',
    method: 'post',
    params: pid,
    data: childIdList
  })
}
