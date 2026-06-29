import request from '@/utils/request'

// const holonUrl = process.env.SALE_MDM_API
const holonUrl = process.env.AUTH_API
export function getSupplerList(condition, page) {
  return request({
    url: process.env.SERVICE_API + '/supplier/query',
    method: 'post',
    data: condition,
    params: page
  })
}

export function getInternalList(condition, page) {
  return request({
    url: holonUrl + '/employee/queryAllEmployees',
    method: 'post',
    data: condition,
    params: page
  })
}
export function findAllRole() {
  return request({
    url: process.env.BASE_API + '/role/findAll/',
    method: 'get'
  })
}

export function findBindRoleById(id) {
  return request({
    url: process.env.BASE_API + '/user/findBindRoleById/' + id,
    method: 'get'
  })
}

export function bindRole(data) {
  return request({
    url: process.env.BASE_API + '/user/bind/role/' + data.id,
    method: 'post',
    params: { psnname: data.psnname, roleIds: data.roleIds }
  })
}

/**
 * 通过部门编码获取部门信息
 * @param {部门编码列表} unitIdList
 */
export function findDeptInfoByUnitId(unitIdList) {
  return request({
    url: holonUrl + '/organ/findDeptInfoByUnitId',
    method: 'post',
    data: unitIdList
  })
}

export function findDeptName(unitId) {
  return request({
    url: holonUrl + '/organ/findUnitNameByUnitId',
    method: 'get',
    params: unitId
  })
}

/**
 * 通过职级编码获取上级领导信息
 * @param {职级} condition {填报人职级名称,填报人部门，填报人信息（编码，名字），操作人职级Id}
 */
export function findSonListByPositionId(condition) {
  return request({
    url: holonUrl + '/organ/findSonListByPositionId',
    method: 'get',
    params: condition
  })
}

/**
 * 通过PositionId获取填报人上级
 * @param {职位} condition {填报人userId,PositionId}
 */
export function findParentListByPositionId(condition) {
  return request({
    url: holonUrl + '/organ/findParentListByPositionId',
    method: 'get',
    params: condition
  })
}

/**
 * 通过职级名称获取人员信息
 * @param {职级名称} postilNameList
 */
export function findUserByPostilName(postilNameList) {
  return request({
    url: holonUrl + '/organ/findUserByPostilName',
    method: 'post',
    data: postilNameList
  })
}

export function findUserByPositionId(positionIdList) {
  return request({
    url: '/baseInfo/findUserByPositionId',
    method: 'post',
    data: positionIdList
  })
}

export function findPositionsByUserId(userIdList) {
  return request({
    url: holonUrl + '/organ/findPositionsByUserId',
    method: 'post',
    data: userIdList
  })
}

/**
 * 指定部门下所有人
 * @returns
 */
export function findEmployeesByUnitId(unitId) {
  return request({
    url: holonUrl + '/empInfo/findByUnitId',
    method: 'get',
    params: { unitId }
  })
}

/**
 * 业务中心部门下所有人
 * @returns
 */
export function findOrderMakers() {
  var unitId = '239830'
  return new Promise((resolve, reject) => {
    findEmployeesByUnitId(unitId).then(data => {
      if (!data.content || data.content.length === 0) {
        resolve([])
      }
      const resMap = new Map()
      var result = data.content.filter(f => !resMap.has(f.employeeId) && resMap.set(f.employeeId, 1))
      resolve(result)
    }).catch(e => {
      resolve([])
    })
  })
}
