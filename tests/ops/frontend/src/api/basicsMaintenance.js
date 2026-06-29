
import request from '@/utils/request'

export function findProductModelByNo(modelNo, fuzzy, page, startTime, endTime) {
  return request({
    url: process.env.OPS_API_BA + '/basics/findProductModel',
    method: 'get',
    params: { modelNo, fuzzy, 'pageNumber': page.pageNumber, 'pageSize': page.pageSize, startTime, endTime }
  })
}

export function findSplitInfoByWholeNo(modelNo) {
  return request({
    url: process.env.OPS_API_BA + '/basics/findSplitInfoByWholeNo',
    method: 'get',
    params: { modelNo }
  })
}

export function findWholeModelInfoByModelNo(modelNo) {
  return request({
    url: process.env.OPS_API_BA + '/basics/findWholeModel',
    method: 'get',
    params: { modelNo }
  })
}

export function updateWholeModelInfoByModelNo(data) {
  return request({
    url: process.env.OPS_API_BA + '/basics/updateWholeModel',
    method: 'post',
    data
  })
}

export function insertProductModel(data) {
  return request({
    url: process.env.OPS_API_BA + '/basics/insert',
    method: 'post',
    data
  })
}

export function findProductDeliveryByNo(modelNo, page, startTime, endTime) {
  return request({
    url: process.env.OPS_API_BA + '/basics/findProductDelivery',
    method: 'get',
    params: { modelNo, 'pageNumber': page.pageNumber, 'pageSize': page.pageSize, startTime, endTime }
  })
}

export function findAllSupplier() {
  return request({
    url: process.env.OPS_API_BA + '/basics/findAllSupplier',
    method: 'get'
  })
}

export function findAllCountry() {
  return request({
    url: process.env.OPS_API_BA + '/basics/findAllCountry',
    method: 'get'
  })
}

export function findCountryBySupplyId(supplyId) {
  return request({
    url: process.env.OPS_API_BA + '/basics/findCountryBySupplyId',
    method: 'get',
    params: { supplyId }
  })
}

export function insertProductDelivery(data) {
  return request({
    url: process.env.OPS_API_BA + '/basics/insertDelivery',
    method: 'post',
    data
  })
}

export function deleteProductDelivery(modelNo, supplyId) {
  return request({
    url: process.env.OPS_API_BA + '/basics/delete',
    method: 'get',
    params: { modelNo, supplyId }
  })
}

export function findDeliveryByNo(modelNo, supplierId) {
  return request({
    url: process.env.OPS_API_BA + '/basics/findDeliveryByNo',
    method: 'get',
    params: { modelNo, supplierId }
  })
}

export function updateProductDelivery(data) {
  return request({
    url: process.env.OPS_API_BA + '/basics/updateDelivery',
    method: 'post',
    data
  })
}

export function findDeliveryListByNo(modelNo) {
  return request({
    url: process.env.OPS_API_BA + '/basics/findDeliveryList',
    method: 'get',
    params: { modelNo }
  })
}

export function findProductModelListByNo(modelNo) {
  return request({
    url: process.env.OPS_API_BA + '/basics/findProductModelList',
    method: 'get',
    params: { modelNo }
  })
}

export function findBeingSuppliersByModelNo(modelNo) {
  return request({
    url: process.env.OPS_API_BA + '/basics/findBeingSuppliers',
    method: 'get',
    params: { modelNo }
  })
}

export function findQueryPriceNewModelByModelNo(modelNo) {
  return request({
    url: process.env.OPS_API_BA + '/basics/findQueryPriceByModelNo',
    method: 'get',
    params: { modelNo }
  })
}

export function insertQueryPrice(data) {
  return request({
    url: process.env.OPS_API_BA + '/basics/insertQueryPrice',
    method: 'post',
    data
  })
}

