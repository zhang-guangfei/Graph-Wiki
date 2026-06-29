import request from '@/utils/request'

const url = process.env.COMMON_API // 发布

// 查询员工信息
export function listApplicant(data) {
  return request({
    url: url + '/common/employee/findEmployeeInfo',
    method: 'post',
    data: data
  })
}

// 根据工号获取员工姓名
export function getEmployeeNameByNo(params) {
  return request({
    url: url + '/common/employee/getEmployeeNameByNo',
    method: 'get',
    params: params
  })
}

// 根据部门代码获取所属员工的CodeName
export function getEmployeeCodeByDeptNo(deptNo) {
  return request({
    url: url + '/common/employee/getEmployeeCodeByDeptNo',
    method: 'get',
    params: deptNo
  })
}
