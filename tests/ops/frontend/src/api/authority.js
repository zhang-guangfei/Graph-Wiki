/** 权限管理API */
import request from '@/utils/request'

export function fetchList(condition) {
  return request({
    url: process.env.BASE_API + '/authority/findAll',
    method: 'get',
    params: condition
  })
}

export function fetchMenu(id) {
  return request({
    url: process.env.BASE_API + '/authority/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: process.env.BASE_API + '/authority/pv',
    method: 'get',
    params: { pv }
  })
}

export function createAuthority(data) {
  return request({
    url: process.env.BASE_API + '/authority/add',
    method: 'post',
    data
  })
}

export function updateAuthority(data) {
  return request({
    url: process.env.BASE_API + '/authority/update',
    method: 'post',
    data
  })
}

export function deleteAuthority(data) {
  return request({
    url: process.env.BASE_API + '/authority/deleteByCode/' + data.code,
    method: 'post'
  })
}

export function bindMenu(data) {
  return request({
    url: process.env.BASE_API + '/authority/bind/menu',
    method: 'post',
    data
  })
}

export function findBindMenuById(data) {
  return request({
    url: process.env.BASE_API + '/authority/findBindMenuById/' + data.id,
    method: 'get'
  })
}

export function bindResource(data) {
  return request({
    url: process.env.BASE_API + '/authority/bind/resource/' + data.id,
    method: 'post',
    params: { resourceIds: data.resourceIds }
  })
}

export function findBindResourceById(data) {
  return request({
    url: process.env.BASE_API + '/authority/findBindResourceById/' + data.id,
    method: 'get'
  })
}

export function findBtnsByUserId(userId) {
  return request({
    url: process.env.BASE_API + '/authority/findByUserId/' + userId,
    method: 'get'
  })
}

export function findByRoleId(roleId) {
  return request({
    url: process.env.BASE_API + '/authority/findByRoleId/' + roleId,
    method: 'get'
  })
}
