import request from '@/utils/request'

const url = process.env.COMMON_API

export function findDepartmentData(formData, page) {
  return request({
    url: url + '/common/department/findDepartmentData',
    method: 'post',
    data: formData,
    params: page
  })
}

export function saveDepartmentInfo(formData) {
  return request({
    url: url + '/common/department/saveDepartmentInfo',
    method: 'post',
    data: formData
  })
}

export function deleteDeptById(id) {
  return request({
    url: url + '/common/department/deleteDeptInfoById',
    method: 'post',
    params: { id }
  })
}

/**
 * 根据部门编号获取部门信息
 */
export function getDepartmentInfo(deptNo) {
  return request({
    url: url + '/common/department/getDepartmentInfo',
    method: 'get',
    params: { deptNo }
  })
}

/**
 * 获取所有的部门
 */
export function listDepartment() {
  return request({
    url: url + '/common/department/listDepartment',
    method: 'get'
  })
}

// 查询营业所代码和名称 DataCodeVO
export function findDepartments() {
  return request({
    url: url + '/common/department/findDepartments',
    method: 'post'
  })
}

// 根据营业所代码获取营业所名称
export function getDeptNameByNo(params) {
  return request({
    url: url + '/common/department/getDeptNameByNo',
    method: 'get',
    params: params
  })
}

// 获取所有营业所驻在所信息
export function getDeptAllData(deptNo) {
  return request({
    url: url + '/common/hrOrgan/listHrOrganAllData',
    method: 'get',
    params: { deptNo }
  })
}

// 获取所有营业所驻在所信息
export function getDeptTreeByNo(deptNos) {
  return request({
    url: url + '/common/department/getDeptTreeByNo',
    method: 'get',
    params: deptNos
  })
}
