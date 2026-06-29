/* eslint-disable space-before-function-paren */
import request from '@/utils/request'
/** 物理仓库*/
export function findHouseType() {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/types',
    method: 'get'
  })
}

export function findHouseNameByType(type) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/names',
    method: 'get',
    params: { type: type }
  })
}
export function findHouseCodeAndNameByType(type) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/codeAndName',
    method: 'get',
    params: { type: type }
  })
}

export function findAllHouse() {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/list',
    method: 'get'
  })
}

export function findHouse(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/search',
    method: 'post',
    data
  })
}

export function addHouse(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/add',
    method: 'post',
    data
  })
}

export function editHouse(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/update',
    method: 'post',
    data
  })
}
export function findHouseMulAddress(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/warehouseMulAddress/list',
    method: 'post',
    data
  })
}
export function saveMultAddressConfig(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/saveMultAaddressConfig',
    method: 'post',
    data
  })
}

export function saveMultAddress(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/warehouseMulAddress/add',
    method: 'post',
    data
  })
}

export function updateMultAddress(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/warehouseMulAddress/update',
    method: 'post',
    data
  })
}

export function deleteMultAddress(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/warehouseMulAddress/delete/' + data,
    method: 'get'
  })
}

export function deleteListMulAddress(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/warehouseMulAddress/deletes',
    method: 'post',
    data
  })
}
export function checkAddressConfig(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/warehouseMulAddress/checkAddressConfig',
    method: 'post',
    data
  })
}
export function getMultAdressConfig(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/getMultAdressConfig',
    method: 'post',
    data
  })
}

export function getMultAdress(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/getMultAdress',
    method: 'post',
    data
  })
}
export function getMultAdressById(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/getMultAdressById',
    method: 'post',
    data
  })
}
export function deleteHouse(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/delete/' + data,
    method: 'get'
  })
}

export function deletesHouse(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/warehouse/deletes',
    method: 'post',
    data
  })
}
/** 仓库-营业所*/
export function findhouse_branch(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/house_branch/search',
    method: 'post',
    data
  })
}

export function addhouse_branch(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/house_branch/add',
    method: 'post',
    data
  })
}

export function edithouse_branch(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/house_branch/update',
    method: 'post',
    data
  })
}

export function deletehouse_branch(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/house_branch/delete/' + data,
    method: 'get'
  })
}

export function deleteshouse_branch(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/house_branch/deletes',
    method: 'post',
    data
  })
}
/** 库存管理*/
export function findInventory(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/inventory/search',
    method: 'post',
    data
  })
}

export function findInventoryType() {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/inventory/types',
    method: 'GET'
  })
}
export function findInventoryProperty(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/inventory/property',
    method: 'GET',
    params: { id: data }
  })
}

export function finddiffdateList(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/diff/date',
    method: 'get'
  })
}

export function findDiffInventory(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/diff/search',
    method: 'post',
    data
  })
}
/** 指令管理*/
export function findDeliveryOrder(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/do/search',
    method: 'post',
    data
  })
}

export function findDeliveryOrderItem(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/do_item/search',
    method: 'post',
    data
  })
}

/** 收货指令 start  /warehouseManage/receivingInstruction*/
export function findReceivingInstruction(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/ro/search',
    method: 'post',
    data
  })
}

export function findReceivingInstructionItem(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/ro/search_item',
    method: 'post',
    data
  })
}

export function findReceivingInstructionOperationRecord(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/ro/operation_record',
    method: 'post',
    data
  })
}

/** 收货指令 end*/

/** 加工指令 start  /warehouseManage/machiningInstruction*/
export function findMachiningInstruction(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/pco/search',
    method: 'post',
    data
  })
}

export function findMachiningInstructionItem(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/pco/search_item',
    method: 'post',
    data
  })
}

export function findMachiningInstructionOperationRecord(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/pco/operation_record',
    method: 'post',
    data
  })
}

/** 加工指令 end*/

/** 接单查询 */
export function findReceiveView(data, cancelToken) {
  return request({
    url: process.env.OPS_API_WM + '/order/rcvorder/rcvView',
    method: 'post',
    data,
    cancelToken
  })
}

/** 库存分配查询*/
export function findOrderInv(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/status/find',
    method: 'get',
    params: { orderId: data.orderId, orderItem: data.orderItem }
  })
}

/** 省市区代码查询*/
export function findAreas(data) {
  return request({
    url: process.env.OPS_API_WM + '/area/tree',
    method: 'get',
    params: { parentCode: data }
  })
}

/** 营业所字典查询*/
export function findDeptDict() {
  return request({
    url: process.env.AUTH_API + '/organ/departDict',
    method: 'get'
  })
}

export function findSupplier() {
  return request({
    url: process.env.COMMON_API + '/common/supplier/findSupplierInfo',
    method: 'get'
  })
}
/** for zd */
export function findRequestPurchaseBySqlitNo(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchaseApi/getPurchaseItem',
    method: 'post',
    params: { orderNo: data.orderId, itemNo: data.orderItem, splitNo: data.splitNo }
  })
}
export function findRequestPurchase(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchaseApi/getPurchase',
    method: 'post',
    params: { orderNo: data.orderId, itemNo: data.orderItem }
  })
}

/** 取消订单*/
export function cancelOrder(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/rcvorder/cancel',
    method: 'post',
    data
  })
}
/** 完纳订单*/
export function finishOrder(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/finish',
    method: 'post',
    data
  })
}

/** 变更订单*/
export function updateOrder(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/update',
    method: 'post',
    data
  })
}

/** for zd */
export function getRoPartStatus(data) {
  return request({
    url: process.env.OPS_API_WM + '/zd/getRoPartStatus',
    method: 'post',
    data: data
  })
}

/** 转定显示可转库存*/
export function showItemZD(order) {
  return request({
    url: process.env.OPS_API_WM + '/zd/showItem',
    method: 'post',
    data: order
  })
}
export function showInvByItemQtyZD(data) {
  return request({
    url: process.env.OPS_API_WM + '/zd/showInvByItemQty',
    method: 'post',
    data
  })
}

export function showInvByItemQtyZDV1(data, cancelToken) {
  return request({
    url: process.env.OPS_API_WM + '/zd/showInvByItemQty',
    method: 'post',
    data,
    cancelToken
  })
}
/**
 * 订单还原显示可转定库存
 */
export function showInvByItemQtyRest(data) {
  return request({
    url: process.env.OPS_API_WM + '/zd/showInvByItemQty',
    method: 'post',
    data
  })
}
export function handleZDToPo(data) {
  return request({
    url: process.env.OPS_API_WM + '/zd/handleZDToPo',
    method: 'post',
    data: data
  })
}
export function handleZD(data) {
  return request({
    url: process.env.OPS_API_WM + '/zd/handleZD',
    method: 'post',
    data: data
  })
}
/** 已占用库存 过时*/
export function findAllocatedInventory(order) {
  return request({
    url: process.env.OPS_API_WM + '/order/rcvorder/reorder/allocated',
    method: 'post',
    data: order
  })
}/** 可用库存 过时*/
export function availableInventory(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/rcvorder/reorder/available',
    method: 'post',
    data
  })
}/** 转定按钮 过时*/
export function reorderInventory(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/rcvorder/reorder/handler',
    method: 'post',
    data: data
  })
}
export function findCustomerAddress(id) {
  return request({
    url: process.env.OPS_API_BA + '/customer/info',
    method: 'post',
    params: { id: id }
  })
}

export function findOrderDetail(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/doView/detail',
    method: 'post',
    data
  })
}

export function findCustomerInfoByCustomerNoOrName(data) {
  return request({
    url: process.env.OPS_API_BA + '/customer/findCustomerInformation',
    method: 'post',
    data
  })
}

export function findCustomerBycustomerNoOrName(customerNoOrName) {
  return request({
    url: process.env.OPS_API_BA + '/customer/findCustomerByCustomerNoOrName',
    method: 'get',
    params: { customerNoOrName: customerNoOrName }
  })
}
export function getExpressInfo(expressNo, type) {
  return request({
    url: process.env.OPS_API_WM + '/tms/expressByNo',
    method: 'get',
    params: { expressNo: expressNo, type: type }
  })
}

export function findOrderByFno(orderFno) {
  return request({
    url: process.env.OPS_API_WM + '/order/rcvdetail',
    method: 'get',
    params: { orderFno: orderFno }
  })
}

export function enableCancelRcv(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/rcvorder/cancel/enable',
    method: 'post',
    data
  })
}

export function askOrderFinish(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/askOrderFinish',
    method: 'post',
    data
  })
}

export function exeFinish(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/exeFinish',
    method: 'post',
    data
  })
}

export function downLoadInventoryExcel(data) {
  return request({
    url: process.env.OPS_REPORT_API + '/report/inventory/relationOrder/download',
    method: 'get',
    responseType: 'blob',
    params: data
  })
}

export function downloadInvExcel(data) {
  return request({
    url: process.env.OPS_API_WM + '/warehouseManage/inventory/download',
    method: 'post',
    responseType: 'blob',
    data
  })
}
