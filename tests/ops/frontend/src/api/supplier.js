import request from '@/utils/request'
const mdmApi = process.env.SALE_MDM_API

export function findSupplierByModelNo(modelNo) {
  return request({
    url: mdmApi + '/supplier/findByModelNo',
    method: 'GET',
    params: { modelNo }
  })
}

export function findOrgCountryByModelNo(modelNo) {
  return request({
    url: mdmApi + '/supplier/findOrgCountryByModelNo',
    method: 'GET',
    params: { modelNo }
  })
}

export function findSupplierListByModelNo(modelNoList) {
  return request({
    url: mdmApi + '/product/supplier/findSupplierListByModelNo',
    method: 'POST',
    data: modelNoList
  })
}
