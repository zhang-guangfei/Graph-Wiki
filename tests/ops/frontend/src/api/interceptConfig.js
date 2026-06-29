// 自定义拦截清单
// 正常拦截初始化页面
import request from '@/utils/request'
export function findList(data) {
  return request({
    url: process.env.OPS_API_PO + '/intercept/findList',
    method: 'post',
    data
  })
}

export function editData(data) {
  return request({
    url: process.env.OPS_API_PO + '/intercept/updateData',
    method: 'post',
    data
  })
}

// export function addDatas(data) {
//   return request({
//     url: process.env.OPS_API_PO + '/intercept/insertData',
//     method: 'post',
//     data
//   })
// }

export function deleteData(data) {
  return request({
    url: process.env.OPS_API_PO + '/intercept/deleteData',
    method: 'post',
    data
  })
}

// 批量删除操作
export function deleteDataBatch(data) {
  return request({
    url: process.env.OPS_API_PO + '/intercept/deleteDataBatch',
    method: 'post',
    data
  })
}

// 批量删除操作
export function editDataBatch(data) {
  return request({
    url: process.env.OPS_API_PO + '/intercept/editDataBatch',
    method: 'post',
    data
  })
}
export function addDatas(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/product/SMSAdapter/addData',
    method: 'post',
    data
  })
}
