/** 角色API */
import request from '@/utils/request'

export function fetchList(condition) {
  return request({
    url: process.env.BASE_API + '/role/query',
    method: 'get',
    params: condition
  })
}

export function getUserRoleList(condition) {
  return request({
    url: process.env.BASE_API + '/user/findUserRoleDetail',
    method: 'get',
    params: condition
  })
}

export function getGroupRoleList(condition) {
  return request({
    url: process.env.BASE_API + '/group/findGroupRoleDetail',
    method: 'get',
    params: condition
  })
}

export function fetchListAll() {
  return request({
    url: process.env.BASE_API + '/role/findAll',
    method: 'get'
  })
}

export function findDeptById(id) {
  return request({
    url: process.env.BASE_API + '/baseInfo/findDeptById',
    method: 'get',
    params: { id }
  })
}

export function fetchRole(id) {
  return request({
    url: process.env.BASE_API + '/role/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: process.env.BASE_API + '/role/pv',
    method: 'get',
    params: { pv }
  })
}

export function createRole(data) {
  return request({
    url: process.env.BASE_API + '/role/add',
    method: 'post',
    data
  })
}

export function updateRole(data) {
  return request({
    url: process.env.BASE_API + '/role/update',
    method: 'post',
    data
  })
}

export function deleteRole(data) {
  return request({
    url: process.env.BASE_API + '/role/delete/' + data.id,
    method: 'POST'
  })
}

export function bindAuthority(id, authorityIds) {
  return request({
    url: process.env.BASE_API + '/role/bind/authority/' + id,
    method: 'post',
    data: authorityIds
  })
}

export function findAuthorityById(data) {
  return request({
    url: process.env.BASE_API + '/role/findBindAuthorityById/' + data.id,
    method: 'get'
  })
}

export function bindDataFilter(data, depts) {
  return request({
    url: process.env.BASE_API + '/role/bind/dataFilter',
    method: 'post',
    params: { objects: depts },
    data
  })
}

export function findDataFilterByRoleId(id) {
  return request({
    url: process.env.BASE_API + '/role/findDataFilterByRoleId?date=' + new Date(),
    method: 'get',
    params: { id }
  })
}

export function findDataRoleByRoleId(id) {
  return request({
    url: process.env.BASE_API + '/role/findDataRoleByRoleId?date=' + new Date(),
    method: 'get',
    params: { id }
  })
}
