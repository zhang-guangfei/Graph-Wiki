import request from '@/utils/request'

const url = process.env.OPS_SHAREAPP_API // 发布

const adapter_url = process.env.OPS_ADAPTER_API

// 分页查询
export function listSampleOrderData(searchRequest, page) {
  return request({
    url: url + '/shareapp/sampleorder/listSampleOrderData',
    method: 'post',
    data: searchRequest,
    params: page
  })
}

export function exportSampleOrderApplyData(data) {
  return request({
    url: url + '/shareapp/sampleorder/exportSampleOrderApplyData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

export function listSampleOrderManage(searchForm, page) {
  return request({
    url: url + '/shareapp/sampleorder/listSampleOrderManage',
    method: 'post',
    data: searchForm,
    params: page
  })
}

export function listSampleBalData(searchRequest, page) {
  return request({
    url: url + '/shareapp/sampleorder/listSampleBalData',
    method: 'post',
    data: searchRequest,
    params: page
  })
}

export function splitCarryType(data) {
  return request({
    url: url + '/shareapp/sampleorder/splitCarryType',
    method: 'post',
    data: data
  })
}

export function againBal(data) {
  return request({
    url: url + '/shareapp/sampleorder/againBal',
    method: 'post',
    data: data
  })
}
// export function createOrder(data) {
//   return request({
//     url: url + '/shareapp/sampleorder/createOrder',
//     method: 'post',
//     data: JSON.stringify(data)
//   })
// }

export function createOrder(SampleOrderParams) {
  return request({
    url: url + '/shareapp/sampleorder/createOrder',
    method: 'post',
    data: JSON.stringify(SampleOrderParams)
  })
}

export function rebackOrder(data) {
  return request({
    url: url + '/shareapp/sampleorder/rebackOrder',
    method: 'post',
    data: JSON.stringify(data)
  })
}
export function orderCarryTurn(data) {
  return request({
    url: url + '/shareapp/sampleorder/orderCarryTurn',
    method: 'post',
    data: JSON.stringify(data)
  })
}

export function invoicingSales(invoicingSalesParams) {
  return request({
    url: url + '/shareapp/sampleorder/invoicingSales',
    method: 'post',
    data: invoicingSalesParams
  })
}

export function exportSampleBalData(data) {
  return request({
    url: url + '/shareapp/sampleorder/exportSampleBalData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
export function exportSampleOrderManageData(data) {
  return request({
    url: url + '/shareapp/sampleorder/exportSampleOrderManageData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

export function exportOverdueBalData() {
  return request({
    url: url + '/shareapp/sampleorder/exportOverdueBalData',
    method: 'get'
  })
}
export function cancelTurnSalesInvoice(data) {
  return request({
    url: url + '/shareapp/sampleorder/cancelTurnSalesInvoice',
    method: 'post',
    data: data
  })
}
export function exportZlzsOrderBalance(data) {
  return request({
    url: url + '/shareapp/sampleorder/exportZlzsOrderBalance',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
export function downLoadNoBalFileByYearMonth(yearMonth) {
  return request({
    url: url + '/shareapp/sampleorder/downLoadNoBalFileByYearMonth',
    method: 'get',
    responseType: 'blob',
    params: { yearMonth }
  })
}
export function writeoffForZlzsOrder(data) {
  return request({
    url: url + '/shareapp/sampleorder/writeoffForZlzsOrder',
    method: 'post',
    data: data
  })
}
export function getZLZSExportTime() {
  return request({
    url: url + '/shareapp/sampleorder/getZLZSExportTime',
    method: 'get'
  })
}
export function pushZlzsOrderBalance(data) {
  return request({
    url: url + '/shareapp/sampleorder/pushZlzsOrderBalance',
    method: 'post',
    data: data
  })
}
export function downExcelForWriteOff() {
  return request({
    url: url + '/shareapp/sampleorder/downExcelForWriteOff',
    method: 'get',
    responseType: 'blob'
  })
}
// 通过文件进行销账
export function importWriteOffData(data) {
  return request({
    url: url + '/shareapp/sampleorder/importWriteOffData',
    method: 'post',
    data: data
  })
}
// 变更展示品实物收货部门
export function upZlzsRcvDeptNo(data) {
  return request({
    url: url + '/shareapp/sampleorder/upZlzsRcvDeptNo',
    method: 'post',
    data: data
  })
}
// 下载变更展示品实物所在部门模板
export function downExcelForUpRcvDeptNo() {
  return request({
    url: url + '/shareapp/sampleorder/downExcelForUpRcvDeptNo',
    method: 'get',
    responseType: 'blob'
  })
}
// 通过文件导入变更展示品实物所在部门
export function batchUpRcvDeptNo(data) {
  return request({
    url: url + '/shareapp/sampleorder/batchUpRcvDeptNo',
    method: 'post',
    data: data
  })
}
// 展览展示品管理页面api
export function insertIntoSampleOrderManage(data) {
  return request({
    url: url + '/shareapp/sampleorder/insertIntoSampleOrderManage',
    method: 'post',
    data: data
  })
}
export function exportZlzsManageData(data) {
  return request({
    url: url + '/shareapp/sampleorder/exportZlzsManageData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
export function pushZlzsSampleOrderManageForPdf(data) {
  return request({
    url: url + '/shareapp/sampleorder/pushZlzsSampleOrderManageForPdf',
    method: 'post',
    data: data
  })
}
export function zlzsOrderWriteOff(data) {
  return request({
    url: url + '/shareapp/sampleorder/zlzsOrderWriteOff',
    method: 'post',
    data: data
  })
}
// 通过文件进行销账
export function batchImportWriteOffData(data) {
  return request({
    url: url + '/shareapp/sampleorder/batchImportWriteOffData',
    method: 'post',
    data: data
  })
}
export function upSampleOrderManageDeptNo(data) {
  return request({
    url: url + '/shareapp/sampleorder/upSampleOrderManageDeptNo',
    method: 'post',
    data: data
  })
}
export function batchUpSampleOrderManageDeptNo(data) {
  return request({
    url: url + '/shareapp/sampleorder/batchUpSampleOrderManageDeptNo',
    method: 'post',
    data: data
  })
}
export function batchImportSampleOrderManageData(data) {
  return request({
    url: url + '/shareapp/sampleorder/batchImportSampleOrderManageData',
    method: 'post',
    data: data
  })
}
export function editSampleOrderManage(data) {
  return request({
    url: url + '/shareapp/sampleorder/editSampleOrderManage',
    method: 'post',
    data: data
  })
}
export function importSampleOrderManageData(data) {
  return request({
    url: url + '/shareapp/sampleorder/importSampleOrderManageData',
    method: 'post',
    data: data
  })
}
export function downLoadHistorySampleOrderManageExcel() {
  return request({
    url: url + '/shareapp/sampleorder/downLoadHistorySampleOrderManageExcel',
    method: 'get',
    responseType: 'blob'
  })
}
export function findSampleBalApplyInfoList(data) {
  return request({
    url: url + '/shareapp/sampleorder/findSampleBalApplyInfoList',
    method: 'post',
    data: data
  })
}
export function findAttacheFiledManageInfo(data) {
  return request({
    url: adapter_url + '/adapter/fileManage/findAttacheFiledManageInfo',
    method: 'post',
    data: data
  })
}
export function delAttacheFileManageInfo(data) {
  return request({
    url: adapter_url + '/adapter/fileManage/delAttacheFileManageInfo',
    method: 'post',
    data: data
  })
}
export function dowmLoadAttacheFileManageInfo(data) {
  return request({
    url: adapter_url + '/adapter/fileManage/dowmLoadAttacheFileManageInfo',
    responseType: 'blob',
    method: 'post',
    data: data
  })
}
export function uploadFileAttacheFileManageInfoToServer(data) {
  return request({
    url: url + '/shareapp/sampleorder/uploadFileAttacheFileManageInfoToServer',
    method: 'post',
    data: data
  })
}
export function sureApplySampleBal(data) {
  return request({
    url: url + '/shareapp/sampleorder/sureApplySampleBal',
    method: 'post',
    data: data
  })
}
export function listKdInfos(data) {
  return request({
    url: url + '/shareapp/stockassembly/listKdInfos',
    method: 'post',
    data: data
  })
}
export function exportKdInfoData(data) {
  return request({
    url: url + '/shareapp/stockassembly/exportKdInfoData',
    responseType: 'blob',
    method: 'post',
    data: data
  })
}
