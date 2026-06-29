import request from '@/utils/request'

export function findParentOrgan(id) {
  return request({
    url: process.env.SALE_MDM_API + '/organization/findParentOrganization',
    method: 'post',
    params: { id }
  })
}

/**
	 * findOrganizationByPsnSmcId: 根据员工ID获取员工所属部门
	 * @param psnSmcId
	 * @return
	 */
export function findOrganizationByPsnSmcId(psnSmcId) {
  return request({
    url: process.env.SALE_MDM_API + '/organization/findOrganizationByPsnSmcId',
    method: 'post',
    params: psnSmcId
  })
}

/**
 * 获取所有的营业部（tree结构）
 */
export function findDeptByParentIdsToTrees(deptParam) {
  return request({
    url: process.env.AUTH_API + '/organ/findDeptByParentIdsToTrees',
    method: 'post',
    data: deptParam
  })
}

/**
 * 获取所有的营业部的id（tree结构）
 */
export function listOrganIdByPid(deptParam) {
  return request({
    url: process.env.AUTH_API + '/organ/listOrganIdByPid',
    method: 'post',
    data: deptParam
  })
}

/**
 * 获取所有的营业所（tree结构）
 */
export function findDeptsToTree() {
  return request({
    url: process.env.AUTH_API + '/organ/findDeptsToTree',
    method: 'get'
  })
}
