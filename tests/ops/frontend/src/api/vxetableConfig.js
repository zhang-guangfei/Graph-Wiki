import request from '@/utils/request'

export function findVxeColumn(params) {
  return request({
    url: process.env.BASE_API + '/vxeTableConfig/findColumnByTable',
    method: 'get',
    params: params
  })
}
