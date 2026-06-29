import request from '@/utils/request'

// 导出
export function exportUnusual(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/unusual/export',
    method: 'post',
    data
  })
}

// 查询
export function searchUnusual(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/unusual/search',
    method: 'post',
    data
  })
}

export function deleteUnusualByIds(ids) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/unusual/delete',
    method: 'post',
    data: ids
  })
}

export function updateUnusualById(id, username) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/unusual/update',
    method: 'get',
    params: { id: id, username: username }
  })
}
