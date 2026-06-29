import request from '@/utils/request'
const url = process.env.DELIVERY
// 查询接口
export function fetchList(searchRequest, page) {
  return request({
    url: url + '/inquiry/apply/findAll',
    method: 'post',
    data: searchRequest,
    params: page
  })
}

// 根据订单号，查询订单信息
export function getOrderData(data) {
  return request({
    url: url + '/inquiry/apply/getOrderData',
    method: 'post',
    data
  })
}

// 根据订单号，查询订单信息
export function addInquiryData(data) {
  return request({
    url: url + '/inquiry/apply/addInquiry',
    method: 'post',
    data
  })
}

// 导入操作
export function importBatchData(data) {
  return request({
    url: url + '/inquiry/apply/importBatchData',
    method: 'post',
    data: data
  })
}

// 获取最大的申请号
export function creatApplyNo(data) {
  return request({
    url: url + '/inquiry/apply/getApplyNo',
    method: 'post',
    data
  })
}

export function getReasonBySuppily(data) {
  return request({
    url: url + '/inquiry/apply/getReasonBySuppily',
    method: 'post',
    data
  })
}

// 获取催促原因的列表
export function getAllReason() {
  return request({
    url: url + '/inquiry/apply/getAllReason',
    method: 'post'
  })
}

// 获取催促原因的列表
export function getReasonParam() {
  return request({
    url: url + '/inquiry/apply/getAllParamConfig',
    method: 'post'
  })
}

// 获取供应商的特殊配置
export function getReasonBySuppilyParam(data) {
  return request({
    url: url + '/inquiry/apply/getReasonBySuppilyParam',
    method: 'post',
    data
  })
}
// 根据订单号，查询订单催促信息
export function getOrderInquiryVerify(data) {
  return request({
    url: url + '/inquiry/apply/orderInquiryVerify',
    method: 'post',
    data
  })
}

// 订单催促的新增
export function addInquiryOrderData(data) {
  return request({
    url: url + '/inquiry/apply/orderAddInquiry',
    method: 'post',
    data
  })
}

export function importOrderBatchData(data) {
  return request({
    url: url + '/inquiry/apply/importOrderBatchData',
    method: 'post',
    data: data
  })
}

export function deliveryDataVerify(data) {
  return request({
    url: url + '/inquiry/handle/deliveryDataVerify',
    method: 'post',
    data: data
  })
}
