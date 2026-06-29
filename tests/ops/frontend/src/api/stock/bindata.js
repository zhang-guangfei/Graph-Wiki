import request from '@/utils/request'

// 查询bidata
export function listBindata(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/bindata/listBindata',
    method: 'post',
    data: data
  })
}

// 上传文件
export function uploadFile(formData) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/bindata/importBindata',
    method: 'post',
    data: formData
  })
}

export function saveBindata(formData) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/bindata/saveBindata',
    method: 'post',
    data: formData
  })
}

// 删除数据
export function deleteBindata(formData) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/bindata/deleteBindata',
    method: 'post',
    data: formData
  })
}

export function listOpsInventoryProperty(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/product/inventory/listOpsInventoryProperty',
    method: 'post',
    data: data
  })
}

export function exportBinData(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/bindata/exportBinData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
// dowmBinDataImport
export function dowmBinDataImport() {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/bindata/dowmBinDataImport',
    method: 'get',
    responseType: 'blob'
  })
}

export function updateProductInfo() {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/bindata/updateProductInfo',
    method: 'post'
  })
}

export function checkImpBindataStatus(key) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/checkImpBindataStatus',
    method: 'get',
    params: { key }
  })
}

// 查询备库客户的自动补库清单
export function getBinDataForAutoPreStock(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/bindata/getBinDataForAutoPreStock',
    method: 'post',
    data: data
  })
}

// 查询库房属性
export function getOpsInventoryProperty(id) {
  return request({
    url: process.env.OPS_STOCK_API + '/product/inventory/getOpsInventoryProperty',
    method: 'get',
    params: { id }
  })
}

