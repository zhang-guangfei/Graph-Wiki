import request from '@/utils/request'

export function findPositionList(params) {
  return request({
    url: process.env.SALE_MDM_API + '/position/findPositionNamesLike',
    method: 'get',
    params: params
  })
}
