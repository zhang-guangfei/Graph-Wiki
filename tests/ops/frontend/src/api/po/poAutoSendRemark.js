// 采购发单，备注配置画面
import request from '@/utils/request'
export function findList(data) {
  return request({
    url: process.env.OPS_API_PO + '/po/autosendremark/findList',
    method: 'post',
    data
  })
}

export function editData(data) {
  return request({
    url: process.env.OPS_API_PO + '/po/autosendremark/updateData',
    method: 'post',
    data
  })
}

export function addDatas(data) {
  return request({
    url: process.env.OPS_API_PO + '/po/autosendremark/insertData',
    method: 'post',
    data
  })
}

export function deleteData(data) {
  return request({
    url: process.env.OPS_API_PO + '/po/autosendremark/deleteData',
    method: 'post',
    data
  })
}

// 批量删除操作
export function deleteDataBatch(data) {
  return request({
    url: process.env.OPS_API_PO + '/po/autosendremark/deleteDataBatch',
    method: 'post',
    data
  })
}
// 导入操作
export function importBatchData(data) {
  return request({
    url: process.env.OPS_API_PO + '/po/autosendremark/importBatchData',
    method: 'post',
    data: data
  })
}

export function getDepartment() {
  return request({
    url: process.env.OPS_API_PO + '/po/autosendremark/getDepartment',
    method: 'get'
  })
}
// 批量删除操作
export function restoreData(data) {
  return request({
    url: process.env.OPS_API_PO + '/po/autosendremark/restoreData',
    method: 'post',
    data
  })
}
