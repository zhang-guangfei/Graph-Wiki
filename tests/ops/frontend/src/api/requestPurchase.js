import request from '@/utils/request'
export function specialNotApprovedColumn() {
  return [
    { field: 'operate', fixed: 'left', title: '操作', width: 100, slots: { default: 'operate' }},
    { field: 'specialPriceNo', fixed: 'left', title: '特价号', minwidth: 100, slots: { default: 'detailOperate' }, sortable: true },
    { field: 'specialPriceType', fixed: 'left', title: '特价类型', width: 100 },
    // { field: 'replyNo', fixed: 'left', title: '批复号', width: 100 },
    // { field: 'customerNo', title: '客户代码', width: 100 },
    { field: 'customerName', title: '客户名称', minWidth: 160, showOverflow: true, slots: { default: 'customer' }},
    // { field: 'userName', title: '用户名称', minWidth: 160, showOverflow: true },
    // { field: 'userId', title: '用户代码', width: 100 },
    { field: 'customerTakeOnId', title: '客户担当', minwidth: 160, showOverflow: true, slots: { default: 'customerBear' }},
    { field: 'status', title: '审批状态', minwidth: 80 },
    { field: 'approvalInformation', title: '审批人信息', width: 190, slots: { default: 'approvalInformation' }},
    { field: 'createTime', title: '申请日期', minwidth: 100, sortable: true }
  ]
}

export function specialApprovedColumn() {
  return [
    { field: 'operate', fixed: 'left', title: '操作', minwidth: 135, slots: { default: 'operate' }},
    // { field: 'replyNo', fixed: 'left', title: '批复号', width: 100 },
    // { field: 'customerNo', title: '客户代码', width: 100 },
    { field: 'customerName', title: '客户名称', minWidth: 160, showOverflow: true, slots: { default: 'customer' }},
    // { field: 'userName', title: '用户名称', minWidth: 160, showOverflow: true },
    // { field: 'userId', title: '用户代码', width: 100 },
    { field: 'customerTakeOnId', title: '客户担当', minwidth: 80 },
    { field: 'status', title: '审批状态', minwidth: 80 },
    // { field: 'approver', title: '审批人', width: 70 },
    { field: 'remainModelItem', title: '剩余型号项', minwidth: 90, showOverflow: true },
    { field: 'remainingQty', title: '剩余量', minwidth: 70 },
    { field: 'remainder', title: '预警提示', minwidth: 80, showOverflow: true }
  ]
}

export function specialApplyDetailColumn() {
  return [
    // { type: 'checkbox', width: 60 },
    { field: 'operate', title: '操作', fixed: 'left', width: 100, slots: { default: 'operate' }},
    { type: 'checkbox', width: 40, fixed: 'left' },
    { field: 'itemNo', title: '#', fixed: 'left', width: 80, filters: [{ data: null }], filterRender: { name: 'input' }, sortable: true },
    { field: 'modelNo', fixed: 'left', title: '申请型号', minWidth: 120, filters: [{ data: null }], slots: { default: 'modelNoDetail' },
      filterRender: { name: 'FilterInput' }, className: {}, editRender: { name: 'input', attrs: { type: 'text' }}, sortable: true, showOverflow: false },
    { field: 'modelNoType', title: '*', fixed: 'left', width: 65, slots: { default: 'productType' }},
    { field: 'competitivenessName', title: '竞争系数', width: 80, sortable: true },
    { field: 'quantity', title: '数量', width: 90, slots: { default: 'quantitySlot' }, sortable: true },
    { field: 'unitPrice', title: '未税单价', width: 80, slots: { default: 'unitPrice' }, sortable: true },
    { field: 'singleTaxAmount', title: '单项税额', width: 80, slots: { default: 'singleTaxAmount' }, sortable: true },
    { field: 'unitPriceWithTax', title: '申请单价(含税)', width: 140, editRender: { name: '$input', props: { type: 'float' }}, sortable: true },
    { field: 'erate', title: '申请E率', width: 110, sortable: true, slots: { default: 'erate' }},
    { field: 'amount', title: '含税金额', width: 80, slots: { default: 'amount' }, sortable: true },
    { field: 'eprice', title: 'E价格', width: 80, slots: { default: 'eprice' }, sortable: true },
    { field: 'marginOfLastDeal', title: '上次成交差额', width: 100, slots: { default: 'marginOfLastDeal' }, sortable: true },
    { field: 'lastYearSalesVolume', title: '去年销售量', width: 80, slots: { default: 'lastYearSalesVolume' }, sortable: true },
    { field: 'amountComposition', title: '金额构成', width: 80, slots: { default: 'amountComposition' }},
    { field: 'competitor', title: '竞争对手', width: 90 },
    { field: 'competitorModelNo', title: '竞争型号', width: 90 },
    { field: 'competitorPrice', title: '竞争价格', width: 90, editRender: { name: 'input', attrs: { type: 'number' }}},
    { field: 'premiumRate', title: '溢价率', width: 90, slots: { default: 'premiumRate' }, titleHelp: { message: '溢价率 = (SMC销售价格-竞争对手5折价格) / 竞争对手5折价格', icon: 'el-icon-question' }}
  ]
}

export function requestMergeColumn() {
  return [
    { type: 'checkbox', width: 40, fixed: 'left' },
    { field: 'operate', fixed: 'left', title: '操作', width: 100, slots: { default: 'operate' }},
    { field: 'orderno', fixed: 'left', title: '请购单号', width: 200 },
    { field: 'customerno', fixed: 'left', title: '客户代码' },
    { field: 'modelno', fixed: 'left', title: '型号', width: 100 },
    { field: 'quantity', fixed: 'left', title: '数量', width: 100 },
    { field: 'quantitySum', fixed: 'left', title: '数量(合计)', width: 100 },
    { field: 'stdprice', title: '价格', width: 100 },
    { field: 'shikomianswerno', title: 'SHKOMI', with: 50, showOverflow: true },
    { field: 'supplierid', title: '供应商', showOverflow: true },
    { field: 'ordtype', title: '订单类型', width: 100 },
    { field: 'orderdate', title: '订货日期', minwidth: 100, sortable: true },
    { field: 'inqno', title: 'INQ-B', minwidth: 80 },
    { field: 'hopeexportdate', title: '指定制造出库日', minwidth: 100, sortable: true },
    { field: 'hopeExportDateMerge', title: '指定制造出库日(合)', minwidth: 100, sortable: true },
    { field: 'transtype', title: '运输方式', width: 100 },
    { field: 'transTypeMerge', title: '运输方式(合)', width: 100 },
    { field: 'requestwarehouseid', title: '入库仓库', width: 100 },
    { field: 'requestWarehouseIdMerge', title: '入库仓库(合)', width: 100 }
  ]
}

export function requestSplitColumn() {
  return [
    { type: 'checkbox', width: 40, fixed: 'left' },
    { field: 'orderno', fixed: 'left', title: '请购单号', width: 200 },
    { field: 'customerno', fixed: 'left', title: '客户代码' },
    { field: 'modelno', fixed: 'left', title: '型号', width: 100 },
    { field: 'quantity', fixed: 'left', title: '数量', width: 100 },
    { field: 'stdprice', title: '价格', width: 100 },
    { field: 'shikomianswerno', title: 'SHKOMI', with: 50, showOverflow: true },
    { field: 'supplierid', title: '供应商', showOverflow: true },
    { field: 'ordtype', title: '订单类型', width: 100 },
    { field: 'orderdate', title: '订货日期', minwidth: 100, sortable: true },
    { field: 'inqno', title: 'INQ-B', minwidth: 80 },
    { field: 'hopeexportdate', title: '指定制造出库日', minwidth: 100, sortable: true },
    { field: 'transtype', title: '运输方式', width: 100 },
    { field: 'requestwarehouseid', title: '入库仓库', width: 100 }
  ]
}
// 变更待处理状态到采购中的状态
export function requestConfirm(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/requestConfirm',
    method: 'post',
    data
  })
}

// 变更待处理状态到采购中的状态
export function requestConfirmOne(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/requestConfirmOne',
    method: 'post',
    data
  })
}

// 查询所有请购单
export function findList(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/findRequestList',
    method: 'post',
    data
  })
}

// 查询所有待处理请购单
export function preProccessList(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/preProccessList',
    method: 'post',
    data
  })
}

// 批量确认全部订单
export function confirmAll(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/requestConfirmAll',
    method: 'post',
    data
  })
}

// 请购单直接转到采购表，不经过合并采购页面
export function toPurchase(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/requestToPurchase',
    method: 'post',
    data
  })
}

// 拦截订单放行操作
export function orderPass(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/orderPass',
    method: 'post',
    data
  })
}

/**
 *
 * @param {*修改供应商,运输方式，指定出库日的方法} data
 */
export function updateSuppily(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/updateSuppily',
    method: 'post',
    data
  })
}

/**
 *
 * @param {*修改供应商,运输方式，指定出库日的方法} data
 */
export function updateRequestBatch(requestPurchaseList) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/updateRequestBatch',
    method: 'post',
    data: requestPurchaseList
  })
}
// 请购单初始合并方法
export function findRequestMergeList() {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/search',
    method: 'get'
  })
}

// 请购单初始合并方法
export function findMergeConfig() {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/findMergeConfig',
    method: 'get'
  })
}

// 请购单初始合并方法
export function updateMergeConfig(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/updateMergeConfig',
    method: 'post',
    data
  })
}

// 单条拆分请购单
export function splitRequestPurchase(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/splitPurchase',
    method: 'post',
    data
  })
}

// 手工合并请购单放法
export function artificialMerge(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/artificialMerge',
    method: 'post',
    data
  })
}

// 后端筛选
export function filterData(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/filterData',
    method: 'post',
    data
  })
}

// 后端排序
export function sortData(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/sortData',
    method: 'post',
    data
  })
}

// 手工合并请购单放法
export function scrollData(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/scrollData',
    method: 'post',
    data
  })
}

// 取消订单方法
export function cancelDatas(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchaseApi/cancelPurchase',
    method: 'post',
    data
  })
}

// 根据订单号获取请购单详情
export function findByOrderNo(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/findByOrderNo',
    method: 'post',
    data
  })
}

// 根据订单号获取请购单详情
export function toPurchaseOne(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/toPurchaseOne',
    method: 'post',
    data
  })
}

// 根据订单号获取请购单详情
export function toPurchaseMerge(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/toPurchase',
    method: 'post',
    data
  })
}

// 根据订单号获取请购单详情
export function unHandleRequest(data) {
  return request({
    url: process.env.OPS_API_PO + '/requestPurchase/unHandle',
    method: 'post',
    data
  })
}

// 特价审批（超级用户）页面查询列表
export function findSpecialPriceSuperList(data, params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/specialPriceSuper/query',
    method: 'post',
    data: data,
    params: params
  })
}

// 根据客户编码查询可用特价号列表
export function findAvailableSpecialPrice(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/specialPrice/availableSpecialPrice',
    method: 'post',
    params: params
  })
}

// 初始化列表
export function findProductItemHandle(data, params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/specialPriceApply/findProductItemHandle',
    method: 'post',
    data: data,
    params: params
  })
}
