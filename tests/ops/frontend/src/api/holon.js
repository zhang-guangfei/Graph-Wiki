/** MDM（组织机构、人员、客户等）API */
import request from '@/utils/request'

// const holonUrl = process.env.SALE_MDM_API
const holonUrl = process.env.BASE_API
export function queryOrgPosition(condition) {
  return request({
    url: holonUrl + '/organ/queryOrgPosition',
    method: 'get',
    params: condition
  })
}

export function getHolonList(unitId) {
  return request({
    url: holonUrl + '/holon/findAllByUnitId',
    method: 'get',
    params: { unitId }
  })
}

export function fetchHolon(id) {
  return request({
    url: holonUrl + '/holon/detail',
    method: 'get',
    params: { id }
  })
}

export function deleteById(id) {
  return request({
    url: holonUrl + '/holonUser/deleteById',
    method: 'get',
    params: { id }
  })
}

export function updateFuZeRenStatus(code, id) {
  return request({
    url: holonUrl + '/holonUser/updateFuZeRenStatus',
    method: 'get',
    params: { code, id }
  })
}

export function cancelFuZeRenById(id) {
  return request({
    url: holonUrl + '/holonUser/cancelFuZeRenById',
    method: 'get',
    params: { id }
  })
}

export function createHolon(data) {
  return request({
    url: holonUrl + '/holon/add',
    method: 'post',
    data
  })
}

export function updateHolon(data) {
  return request({
    url: holonUrl + '/holon/update',
    method: 'post',
    data
  })
}

export function deleteHolon(code) {
  return request({
    url: holonUrl + '/holon/deleteByCode',
    method: 'get',
    params: { code }
  })
}

export function findHolonUserByCode(code) {
  return request({
    url: holonUrl + '/holonUser/findHolonUserByCode',
    method: 'get',
    params: { code }
  })
}

export function bindUser(data, code) {
  return request({
    url: holonUrl + '/holonUser/bindUser',
    method: 'post',
    params: { code },
    data: data
  })
}

export function findFuZeRenByCode(code) {
  return request({
    url: holonUrl + '/holonUser/findFuZeRenByCode',
    method: 'get',
    params: { code }
  })
}

export function fetchDeptsList() {
  return request({
    url: holonUrl + '/organ/findDepts',
    method: 'get'
  })
}

export function findOrgPositionByPostionIdAndUnitId(positionIds, unitIds) {
  return request({
    url: holonUrl + '/organ/findOrgPositionByPostionIdAndUnitId',
    method: 'get',
    params: { positionIds, unitIds }
  })
}

/**
 * 批量更新
 * @param {日期} dayList
 */
export function updateOptFlagAll(dayList) {
  return request({
    url: holonUrl + '/calendar/findOptFlagAll',
    method: 'post',
    data: dayList
  })
}
/**
 * 获取休日=数据
 * @param {日期} workdayDate
 */
export function getHoliday(workdayDate) {
  return request({
    url: holonUrl + '/calendar/findHoliday',
    method: 'get',
    params: workdayDate
  })
}

export function findParentOrganization(id) {
  return request({
    url: holonUrl + '/organization/findParentOrganization',
    method: 'post',
    params: { id }
  })
}
