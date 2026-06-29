import request from '@/utils/request'

const url = process.env.COMMON_API // 发布

// 根据供应商代码或者名称获取供应商信息
export function findSupplierByIdOrName(params) {
  return request({
    url: url + '/common/supplier/findSupplierByIdOrName',
    method: 'get',
    params: params
  })
}

export function updateSupplierData(form) {
  return request({
    url: url + '/common/supplier/updateSupplierData',
    method: 'post',
    data: form
  })
}
export function findSupplierList(searchFrom) {
  return request({
    url: url + '/common/supplier/findSupplierList',
    method: 'post',
    data: searchFrom
  })
}

export function addSupplier(form) {
  return request({
    url: url + '/common/supplier/addSupplier',
    method: 'post',
    data: form
  })
}

export function editSupplier(form) {
  return request({
    url: url + '/common/supplier/editSupplier',
    method: 'post',
    data: form
  })
}

export function deleteSupplierById(id) {
  return request({
    url: url + '/common/supplier/deleteSupplierById',
    method: 'post',
    data: id
  })
}

export function findSupplierInfo() {
  return request({
    url: url + '/common/supplier/findSupplierInfo',
    method: 'get'
  })
}
