import request from '@/utils/request'

export function fetchList() {
  return request({
    url: process.env.BASE_API + '/group/findAll',
    method: 'get'
  })
}

export function fetchGroup(id) {
  return request({
    url: process.env.BASE_API + '/group/detail',
    method: 'get',
    params: { id }
  })
}

export function createGroup(data) {
  return request({
    url: process.env.BASE_API + '/group/add',
    method: 'post',
    data
  })
}

export function updateGroup(data) {
  return request({
    url: process.env.BASE_API + '/group/update',
    method: 'post',
    data
  })
}

export function deleteGroup(data) {
  return request({
    url: process.env.BASE_API + '/group/deleteByCode/' + data.code,
    method: 'post'
  })
}

export function bindRole(data) {
  return request({
    url: process.env.BASE_API + '/group/bind/role',
    method: 'post',
    data
  })
}

export function findBindRoleById(data) {
  return request({
    url: process.env.BASE_API + '/group/findBindRoleById/' + data.id,
    method: 'get'
  })
}
