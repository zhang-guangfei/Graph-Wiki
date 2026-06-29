import request from '@/utils/request'

const url = process.env.COMMON_API // 发布

// 获取所有一级字典数据
export function listClass(listQuery, page) {
  return request({
    url: url + '/common/dict/queryByClassCode',
    method: 'post',
    data: listQuery,
    params: page
  })
}

export function listByClassCode(listQuery) {
  return request({
    url: url + '/common/dict/listByClassCode',
    method: 'post',
    data: listQuery
  })
}

// 添加 / 修改（id 为空 新增 反之 修改）
export function saveDict(data) {
  return request({
    url: url + '/common/dict/saveDict',
    method: 'post',
    data: data
  })
}
export function deleteDict(data) {
  return request({
    url: url + '/common/dict/deleteDict',
    data: data,
    method: 'post'
  })
}
export function updateDataParam(data) {
  return request({
    url: url + '/common/code/updateDataParam',
    data: data,
    method: 'post'
  })
}
export function updateMaxMonthIntercept(maxmonth) {
  return request({
    url: url + '/common/code/updateMaxMonthIntercept',
    params: { maxmonth },
    method: 'post'
  })
}
// 获取所有的表类型名称和代码
export function getDictDataByPid(classCode) {
  return request({
    url: url + '/common/code/getDataTypes',
    method: 'get',
    params: { classCode }
  })
}
// 根据classCode 和 code 获取参数字典对象
export function getDictByClassCodeAndCode(classCode, code) {
  return request({
    url: url + '/common/code/getDataTypeCodesInfo',
    method: 'get',
    params: { classCode, code }
  })
}

// 根据parentCode 获取参数字典对象
export function getDataTypeByParentCode(parentCode) {
  return request({
    url: url + '/common/code/getDataTypeByParentCode',
    method: 'get',
    params: { parentCode }
  })
}

// 获取树状类型名称和代码
export function getDataCodesTree(classCode) {
  return request({
    url: url + '/common/code/getDataTypes',
    method: 'get',
    params: { classCode }
  })
}

// 查询物体仓库信息
export function listWarehouse(formData) {
  return request({
    url: url + '/common/code/listWarehouse',
    method: 'post',
    data: formData
  })
}

export function listBinRedisMessage() {
  return request({
    url: url + '/binorder/listBinRedisMessage',
    method: 'post'
  })
}

// 查询除委托在库外的仓库代码和名称
export function listWarehouseNoWT() {
  return request({
    url: url + '/common/code/listWarehouseNoWT',
    method: 'post'
  })
}

// 查询对应物流中心
export function getWarehouseParentCode(data) {
  return request({
    url: url + '/common/wareHouse/getWarehouseParentCode',
    method: 'post',
    params: data
  })
}

// 根据不同的申请类型来显示对应的结转方式
export function getSampleBalTypeByApplyType(applyType) {
  return request({
    url: url + '/common/dict/getSampleBalTypeByApplyType',
    method: 'get',
    params: { applyType }
  })
}

// 获取号码
export function generaterBillNo(billType) {
  return request({
    url: url + '/common/code/generaterBillNo',
    method: 'get',
    params: { billType }
  })
}

export function findWareHouseInfoWithLike(code, type) {
  return request({
    url: url + '/common/wareHouse/findWareHouseInfoWithLike',
    method: 'get',
    params: { code, type }
  })
}
export function upWarehouseDelFlag(data) {
  return request({
    url: url + '/common/upWarehouseDelFlag',
    method: 'post',
    data: data
  })
}
