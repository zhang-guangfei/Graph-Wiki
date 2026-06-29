import request from '@/utils/request'

// 查询订单数据
export function listRcvModifyOrders(formData) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/listRcvModifyOrders',
    method: 'post',
    data: formData
  })
}

// 查询订单修改审批数据
export function listOrderModify(formData) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/listOrderModify',
    method: 'post',
    data: formData
  })
}

// 修改订单数据
export function modifyOrders(formData) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/modifyOrders',
    method: 'post',
    data: formData
  })
}

// 订单修改审批
export function approveOrderModify(formData) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/approveOrderModify',
    method: 'post',
    data: formData
  })
}

// 订单修改驳回
export function returnOrderModify(formData) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/returnOrderModify',
    method: 'post',
    data: formData
  })
}
export function exportOrderModifyData(data) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/exportOrderModifyData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
export function handDelOrderStatusToMenHu(data) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/rcvorder/handDelOrderStatusToMenHu',
    method: 'post',
    data: data
  })
}
// 订单还原
export function orderChangeToInitStatus(info) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/orderChangeToInitStatus',
    method: 'post',
    data: info
  })
}
// 转订 业务审批回复
export function upApproveReplay(info) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/upApproveReplay',
    method: 'post',
    data: info
  })
}
// 采购单变更
export function upPoApproveReplay(info) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/upPoApproveReplay',
    method: 'post',
    data: info
  })
}
// 转订 变更供应商
export function upPurOrderSupplier(info) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/upPurOrderSupplier',
    method: 'post',
    data: info
  })
}
// 订单转订&&变更后内容为“转订出库存”、“转订出在途”
export function zdOrderModifyHand(info) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/zdOrderModifyHand',
    method: 'post',
    data: info
  })
}
export function getOpsOrderStatusInfoByOrderFno(orderFno) {
  return request({
    url: process.env.OPS_API_WM + '/order/status/getOrderStatusInfoByOrderFno',
    method: 'get',
    params: { orderFno }
  })
}
// 二次审批
export function secondProcessWithUi(info) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/secondProcessWithUi',
    method: 'post',
    data: info
  })
}
export function updateOrderModifyStatusById(info) {
  return request({
    url: process.env.OPS_ORDER_API + '/order/modifyorder/updateOrderModifyStatusById',
    method: 'post',
    data: info
  })
}
