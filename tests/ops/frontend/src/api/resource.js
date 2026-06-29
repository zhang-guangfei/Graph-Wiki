/** 资源API */
import request from '@/utils/request'

export function fetchList() {
  return request({
    url: process.env.BASE_API + '/resource/list',
    method: 'get'
  })
}

export function fetchMenu(id) {
  return request({
    url: process.env.BASE_API + '/resource/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: process.env.BASE_API + '/resource/pv',
    method: 'get',
    params: { pv }
  })
}

export function createResource(data) {
  return request({
    url: process.env.AUTH_API + '/resource/add',
    method: 'post',
    data
  })
}

export function updateResource(data) {
  return request({
    url: process.env.BASE_API + '/resource/update',
    method: 'post',
    data
  })
}

export function deleteResource(data) {
  return request({
    url: process.env.BASE_API + '/resource/deleteByCode/' + data.code,
    method: 'post'
  })
}
