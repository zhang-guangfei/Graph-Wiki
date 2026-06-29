import request from '@/utils/request'
const url = process.env.OPS_STOCK_API
// const url = 'http://localhost:8101'

// 上传文件
export function uploadFile(formData) {
  return request({
    url: url + '/product/slow/importSlowModelData',
    method: 'post',
    data: formData
  })
}

export function listSlowData(formData) {
  return request({
    url: url + '/product/slow/listSlowMovingModelData',
    method: 'post',
    data: formData
  })
}

export function updateSlowModelData(formData) {
  return request({
    url: url + '/product/slow/updateSlowModelData',
    method: 'post',
    data: formData
  })
}

export function delSlowModelData(id) {
  return request({
    url: url + '/product/slow/delSlowModelData',
    method: 'get',
    params: { id }
  })
}
