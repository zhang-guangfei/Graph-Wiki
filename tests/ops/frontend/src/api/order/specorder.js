import request from '@/utils/request'

const url = process.env.OPS_ORDER_API
// const url = 'http://10.116.1.15:9000'

export function saveOrderdata(formData) {
  return request({
    url: url + '/order/sepcorder/saveSpecOrder',
    method: 'post',
    data: formData
  })
}

export function listOrderdata(formData, page) {
  return request({
    url: url + '/order/sepcorder/listSpecOrder',
    method: 'post',
    data: formData,
    params: page
  })
}

export function createSpecOrder(formData) {
  return request({
    url: url + '/order/sepcorder/generateSpecOrder',
    method: 'post',
    data: formData
  })
}

export function findSpecOrder(groupNo) {
  return request({
    url: url + '/order/sepcorder/findSpecOrder',
    method: 'post',
    params: { groupNo }
  })
}

export function deleteSpecOrder(id) {
  return request({
    url: url + '/order/sepcorder/deleteSpecOrder',
    method: 'post',
    params: { id }
  })
}

export function findCustomerByNo(customerNo) {
  return request({
    url: url + '/order/sepcorder/findCustomerByNo',
    method: 'post',
    params: { customerNo }
  })
}
export function exportListSpecOrder(data) {
  return request({
    url: url + '/order/sepcorder/exportListSpecOrder',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
