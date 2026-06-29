/** 菜单API */
import request from '@/utils/request'

export function fetchList() {
  return request({
    url: process.env.BASE_API + '/menu/findAll',
    method: 'get'
  })
}

export function fetchMenu(id) {
  return request({
    url: process.env.BASE_API + '/menu/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: process.env.BASE_API + '/menu/pv',
    method: 'get',
    params: { pv }
  })
}

export function createMenu(data) {
  return request({
    url: process.env.BASE_API + '/menu/add',
    method: 'post',
    data
  })
}

export function updateMenu(data) {
  return request({
    url: process.env.BASE_API + '/menu/update',
    method: 'post',
    data
  })
}

export function deleteMenu(data) {
  return request({
    url: process.env.BASE_API + '/menu/deleteByCode/' + data.code,
    method: 'post'
  })
}

export function findMenuByUserId(userId, groupIds) {
  return request({
    url: process.env.BASE_API + '/menu/findByUserId/' + userId,
    method: 'POST',
    params: { groupIds: groupIds }
  })
}
