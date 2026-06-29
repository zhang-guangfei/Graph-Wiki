import request from '@/utils/request'
const url = process.env.COMMON_API // 发布

// 根据客户代码获取客户名称
export function getCustomerNameByNo(params) {
  return request({
    url: url + '/common/customer/getCustomerNameByNo',
    method: 'get',
    params: params
  })
}

// 调用北京API
// 根据客户代码获取客户收货地址
export function findyCusDeliveryInfo(cusNo) {
  return request({
    url: 'http://10.116.32.10:9213' + '/interface/customer2/findyCusDeliveryInfo',
    method: 'post',
    params: { cusNo },
    from: 'BJ'
  })
}

// 根据客户代码或客户名称获取客户信息
export function getCustomerByNo(customerNo) {
  return request({
    url: url + '/common/customer/findCustomerInfoByNoOrName',
    method: 'get',
    params: { customerNo }
  })
}

// 根据客户代码获取客户信息
export function getCustomerInfoByCustomerNo(customerNo) {
  return request({
    url: url + '/common/customer/getCustomerInfoByCustomerNo',
    method: 'get',
    params: { customerNo }
  })
}

// 获取客户备案列表
export function findCustWldList(data) {
  return request({
    url: url + '/common/customer/findCustWldList',
    method: 'post',
    data: data
  })
}
// 新增客户备案
export function addCustomerWldInfo(data) {
  return request({
    url: url + '/common/customer/addCustomerWldInfo',
    method: 'post',
    data: data
  })
}
// 软删除客户备案
export function delCustomerWldByCustomerNos(data) {
  return request({
    url: url + '/common/customer/delCustomerWldByCustomerNos',
    method: 'post',
    data: data
  })
}
// 导出
export function exportCustomerWldList(data) {
  return request({
    url: url + '/common/customer/exportCustomerWldList',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
// 模板下载
export function downLoadExcel(data) {
  return request({
    url: url + '/common/customer/downLoadExcel',
    method: 'get',
    responseType: 'blob'
  })
}
// 通过文件导入客户备案
export function importCustomerWld(data) {
  return request({
    url: url + '/common/customer/importCustomerWld',
    method: 'post',
    data: data
  })
}
// 通过客户编码批量新增
export function batchCustomerWld(data) {
  return request({
    url: url + '/common/customer/batchCustomerWld',
    method: 'post',
    data: data
  })
}
// 获取所有的客户 getAllCustomerInfo
export function getAllCustomerInfo(customerNo) {
  return request({
    url: url + '/common/customer/getAllCustomerInfo',
    method: 'get',
    params: { customerNo }
  })
}
