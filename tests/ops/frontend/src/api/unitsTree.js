import request from '@/utils/request'

export function findAfterFiltrationDeptInfo() {
  return request({
    url: process.env.AUTH_API + '/organ/departInfo',
    method: 'get'
  })
}
