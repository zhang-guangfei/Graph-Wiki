import request from '@/utils/request'

const url = process.env.OPS_ORDER_API
//  const url = 'http://localhost:8100'
// 查询发票导入数据
export function listImpInvoiceData(formData) {
  return request({
    url: url + '/order/invoice/listImpInvoiceMaster',
    method: 'post',
    data: formData
  })
}

// 上传文件
export function uploadFile(formData) {
  return request({
    url: url + '/order/invoice/importinvoiceData',
    method: 'post',
    data: formData
  })
}

// 取消发票数据
export function cancelImpInvoiceDataById(params) {
  return request({
    url: url + '/order/invoice/cancelImpInvoiceDataById',
    method: 'get',
    params: params
  })
}

// 登记修改预计到达
export function updateImpInvoicePreArriveDate(data) {
  return request({
    url: url + '/order/invoice/updateImpInvoicePreArriveDate',
    method: 'post',
    data: data
  })
}

// 发票数据明细清单
export function listImpInvoiceDetailByInvoiceId(data) {
  return request({
    url: url + '/order/invoice/listImpInvoiceDetailByInvoiceId',
    method: 'post',
    data: data
  })
}

// 发票数据明细清单(分包)
export function listImpInvoiceDetailPackByInvoiceId(data) {
  return request({
    url: url + '/order/invoice/listImpInvoiceDetailPackByInvoiceId',
    method: 'post',
    data: data
  })
}

// 发票数据明细清单(分包)对比
export function listImpInvoiceErrorByInvoiceId(data) {
  return request({
    url: url + '/order/invoice/listImpInvoiceErrorByInvoiceId',
    method: 'post',
    data: data
  })
}

// 修改保存发票数据
export function updateImpInvoiceDetail(data) {
  return request({
    url: url + '/order/invoice/updateImpInvoiceDetail',
    method: 'post',
    data: data
  })
}

// 修改保存发票数据
export function updateImpInvoiceDetailPack(data) {
  return request({
    url: url + '/order/invoice/updateImpInvoiceDetailPack',
    method: 'post',
    data: data
  })
}

// 删除发票子项
export function delImpInvoiceDetail(params) {
  return request({
    url: url + '/order/invoice/delImpInvoiceDetail',
    method: 'get',
    params: params
  })
}

// 删除发票分包子项
export function delImpInvoiceDetailPack(params) {
  return request({
    url: url + '/order/invoice/delImpInvoiceDetailPack',
    method: 'get',
    params: params
  })
}

// 推送发票数据
export function ImpInvoiceDetailToImpData(params) {
  return request({
    url: url + '/order/invoice/ImpInvoiceDetailToImpData',
    method: 'get',
    params: params
  })
}

// 推送发票数据
export function confirmPOInvoices(params) {
  return request({
    url: url + '/order/invoice/confirmPOInvoices',
    method: 'post',
    data: params
  })
}

// 查询发票入库数据
export function listPoinvoice(formData) {
  return request({
    url: url + '/order/invoice/listPoinvoice',
    method: 'post',
    data: formData
  })
}

// 查询发票入库明细数据
export function listPoInvoiceDetail(data) {
  return request({
    url: url + '/order/invoice/listPoInvoiceDetail',
    method: 'post',
    data: data
  })
}

// 更新发票入库数据
export function updatePoinvoice(data) {
  return request({
    url: url + '/order/invoice/updatePoinvoice',
    method: 'post',
    data: data
  })
}

// 导出到成本系统
export function exportDataToCost(data) {
  return request({
    url: url + '/order/invoice/exportDataToCost',
    method: 'post',
    data: data
  })
}

// 导出发票入库数据
export function exportPoinvoice(formData) {
  return request({
    url: url + '/order/invoice/exportPoinvoice',
    method: 'post',
    data: formData,
    responseType: 'blob'
  })
}

// 导出发票入库明细数据
export function exportPoInvoiceDetail(formData) {
  return request({
    url: url + '/order/invoice/exportPoInvoiceDetail',
    method: 'post',
    data: formData,
    responseType: 'blob'
  })
}

// 新增发票主信息
export function addInvoiceMasterData(formData) {
  return request({
    url: url + '/order/invoice/addInvoiceMasterData',
    method: 'post',
    data: formData
  })
}

// 新增发票明细
export function addInvoiceDetailData(formData) {
  return request({
    url: url + '/order/invoice/addInvoiceDetailData',
    method: 'post',
    data: formData
  })
}

// 新增发票分包
export function addInvoiceDetailPackData(formData) {
  return request({
    url: url + '/order/invoice/addInvoiceDetailPackData',
    method: 'post',
    data: formData
  })
}

// 发票转成本结算
export function impInvoiceToCost(formData) {
  return request({
    url: url + '/order/invoice/impInvoiceToCost',
    method: 'post',
    data: formData
  })
}
// 更新为无需入库
export function updateMasterNoStorage(data) {
  return request({
    url: url + '/order/invoice/updateMasterNoStorage',
    method: 'post',
    params: data
  })
}
// 发票明细复制到分包
export function updToInvoicedetailPack(invoiceId) {
  return request({
    url: url + '/order/invoice/updToInvoicedetailPack',
    method: 'post',
    params: invoiceId
  })
}

// 发票入库数据查询
export function listImpdata(formData) {
  return request({
    url: url + '/order/invoice/listImpdata',
    method: 'post',
    data: formData
  })
}

// 明细项完成录入
export function finishImpInvoiceDeatailAdd(invoiceId) {
  return request({
    url: url + '/order/invoice/finishImpInvoiceDeatailAdd',
    method: 'post',
    params: invoiceId
  })
}

// 检查差异
export function checkImpInvoiceError(invoiceId) {
  return request({
    url: url + '/order/invoice/checkImpInvoiceError',
    method: 'post',
    params: { invoiceId }
  })
}

// 忽略差异
export function updateImpInvoiceIgnoreError(data) {
  return request({
    url: url + '/order/invoice/updateImpInvoiceIgnoreError',
    method: 'post',
    data: data
  })
}

// 发票入库数据查询
export function listNoImpInvoiceDetailPack(formData) {
  return request({
    url: url + '/order/invoice/listNoImpInvoiceDetailPack',
    method: 'post',
    data: formData
  })
}

// 发票入库数据查询
export function listNoImpInvoiceDetailPackExcel(formData) {
  return request({
    url: url + '/order/invoice/listNoImpInvoiceDetailPackExcel',
    method: 'post',
    data: formData
  })
}

// 手动入库入库失败的数据
export function handimpInvoiceDetailPack(formData) {
  return request({
    url: process.env.OPS_API_WM + '/wmPurchase/impdata',
    method: 'post',
    params: formData
  })
}

// 从关务导入
export function impImportInvoiceInfo(formData) {
  return request({
    url: url + '/order/invoice/importInvoiceInfo',
    method: 'get',
    params: formData
  })
}
//    <!--add by WuWeiDong 20230202  bug 9450  导出发票主要数据 -->
// 导出发票主表数据
export function exportImpInvoiceMaster(data) {
  return request({
    url: url + '/order/invoice/exportImpInvoiceMaster',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
// 导出发票明细数据
export function exportImpInvoiceDetail(data) {
  return request({
    url: url + '/order/invoice/exportImpInvoiceDetail',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

// 导出发票分包数据
export function exportImpInvoiceDetailPack(data) {
  return request({
    url: url + '/order/invoice/exportImpInvoiceDetailPack',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

// <!--add by WuWeiDong 20221130 bug 8614 -->
// 取消登记删除
export function UpdateDeleteDetailPack(data) {
  return request({
    url: url + '/order/invoice/UpdateDeleteDetailPack',
    method: 'post',
    data: data
  })
}

// 导出发票差异数据
export function exportImpInvoiceError(data) {
  return request({
    url: url + '/order/invoice/exportImpInvoiceError',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

// 登记删除订单(dong)
export function addDeletePurchase(invoicedata) {
  return request({
    url: process.env.OPS_API_WM + '/wmPurchase/addDeletePurchase',
    method: 'post',
    params: invoicedata
  })
}

// 获取采购单数据
export function getopspurchaseInvoice(poNo, poItemNo) {
  return request({
    url: url + '/order/invoice/getopspurchaseInvoice',
    method: 'get',
    params: { poNo, poItemNo }
  })
}

// 获取金额
export function getPoInvoiceDetailAmount(invoiceId) {
  return request({
    url: url + '/order/invoice/getPoInvoiceDetailAmount',
    method: 'get',
    params: { invoiceId }
  })
}

// 获取汇率
export function getExchangeRate(ratequry) {
  return request({
    url: url + '/order/invoice/getExchangeRate',
    method: 'get',
    params: ratequry
  })
}

// 导入入库发票明细
export function confirmPOInvoiceDetail(invoiceId) {
  return request({
    url: url + '/order/invoice/confirmPOInvoiceDetail',
    method: 'get',
    params: { invoiceId }
  })
}

// 清除关务明细
export function clearPOInvoiceDetail(invoiceId) {
  return request({
    url: url + '/order/invoice/clearPOInvoiceDetail',
    method: 'get',
    params: { invoiceId }
  })
}

// 获取发票入库金额合计
export function getImpInvoiceAmountTotal(data) {
  return request({
    url: url + '/order/invoice/getImpInvoiceAmountTotal',
    method: 'post',
    data: data
  })
}

export function exportOtherInvoiceData(data) {
  return request({
    url: url + '/order/invoice/exportOtherInvoiceData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

// 导出发票入库明细数据
export function exportValueImpinvoice(data) {
  return request({
    url: url + '/order/invoice/exportValueImpinvoice',
    method: 'post',
    data: data,
    responseType: 'blob'
  })
}

// 更新发货金额
export function updImpShipAmount(invoiceId) {
  return request({
    url: url + '/order/invoice/updImpShipAmount',
    method: 'get',
    params: { invoiceId }
  })
}

// 重新结转
export function redoCostInvoice(invoiceId) {
  return request({
    url: url + '/order/invoice/redoCostInvoice',
    method: 'get',
    params: { invoiceId }
  })
}

export function listOverseaInvoiceData(invoiceId) {
  return request({
    url: url + '/order/invoice/listOverseaInvoiceData',
    method: 'get',
    params: { invoiceId }
  })
}

// <!--add by WuWeiDong 20230109 bug 9276 --> 手动更改修改成本明细
export function updatePoInvoiceDetailCost(data) {
  return request({
    url: url + '/order/invoice/updatePoInvoiceDetailCost',
    method: 'post',
    data: data
  })
}

//    取汇率
export function getExchangeRateByinvoiceId(invoiceId) {
  return request({
    url: url + '/order/invoice/getExchangeRateByinvoiceId',
    method: 'get',
    params: { invoiceId }
  })
}
//   取发票的主表（ops_）
export function getOpsPoInvoice(invoiceId) {
  return request({
    url: url + '/order/invoice/getOpsPoInvoice',
    method: 'get',
    params: { invoiceId }
  })
}
