import request from '@/utils/request'

const url = process.env.OPS_REPORT_API
// const url = 'http://localhost:8104'
// const url = 'http://10.116.2.123:8104'

export function listPdBatchList(data) {
  return request({
    url: url + '/report/pd/listPdBatchList',
    method: 'post',
    data: data
  })
}

export function findBatchNo(batchNo) {
  return request({
    url: url + '/report/pd/findBatchNo',
    method: 'get',
    params: { batchNo }
  })
}

export function findAdjustDoList(pdBatchNo) {
  return request({
    url: url + '/report/pd/findAdjustDoList',
    method: 'get',
    params: { pdBatchNo }
  })
}

export function newPd() {
  return request({
    url: url + '/report/pd/newPd',
    method: 'get'
  })
}

export function pdDataImp() {
  return request({
    url: url + '/report/pd/pdDataImp',
    method: 'get'
  })
}

export function cleanPdData() {
  return request({
    url: url + '/report/pd/cleanPdData',
    method: 'get'
  })
}
export function exportArriveNotIn(data) {
  return request({
    url: url + '/report/pd/exportArriveNotIn',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
export function getExportMsgPrompt() {
  return request({
    url: url + '/report/pd/getExportMsgPrompt',
    method: 'get'
  })
}
export function downImpExel() {
  return request({
    url: url + '/report/pd/downImpExel',
    method: 'post',
    responseType: 'blob'
  })
}
export function batchImpArriverNotIn(data) {
  return request({
    url: url + '/report/pd/batchImpArriverNotIn',
    method: 'post',
    data: data
  })
}

export function listArriveNotInWithGroup(data) {
  return request({
    url: url + '/report/pd/listArriveNotInWithGroup',
    method: 'post',
    data: data
  })
}

export function expotToArriveNotInByWmsData() {
  return request({
    url: url + '/report/pd/expotToArriveNotInByWmsData',
    method: 'get'
  })
}

export function listArriveNotInDetail(data) {
  return request({
    url: url + '/report/pd/listArriveNotInDetail',
    method: 'post',
    data: data
  })
}
export function arriveNotInInsertToBillData() {
  return request({
    url: url + '/report/pd/arriveNotInInsertToBillData',
    method: 'get'
  })
}
export function getBatchNoWithIsActive() {
  return request({
    url: url + '/report/pd/getBatchNoWithIsActive',
    method: 'get'
  })
}

export function surePdType(pdType, pdDataType) {
  return request({
    url: url + '/report/pd/surePdType',
    method: 'get',
    params: { pdType, pdDataType }
  })
}
export function createPdBill(data) {
  return request({
    url: url + '/report/pd/createPdBill',
    method: 'post',
    data: data
  })
}
export function printPdXpBillDataWithPdf(title) {
  return request({
    url: url + '/report/pd/printPdXpBillDataWithPdf',
    method: 'get',
    params: { title },
    responseType: 'blob'
  })
}
export function printPdArriveNotInWithPdf(title) {
  return request({
    url: url + '/report/pd/printPdArriveNotInWithPdf',
    method: 'get',
    params: { title },
    responseType: 'blob'
  })
}

export function printWtBillDataWithPdf(warehouseCode) {
  return request({
    url: url + '/report/pd/printWtBillDataWithPdf',
    method: 'get',
    params: { warehouseCode },
    responseType: 'blob'
  })
}

export function printPdXpBlankBillDataWithPdf(title) {
  return request({
    url: url + '/report/pd/printPdXpBlankBillDataWithPdf',
    method: 'get',
    params: { title },
    responseType: 'blob'
  })
}

export function printPdArriveNotInBlankBillDataWithPdf(title) {
  return request({
    url: url + '/report/pd/printPdArriveNotInBlankBillDataWithPdf',
    method: 'get',
    params: { title },
    responseType: 'blob'
  })
}

export function findModelNoCount(dataType) {
  return request({
    url: url + '/report/pd/findModelNoCount',
    method: 'get',
    params: { dataType }
  })
}
export function findModelNoCountByMaster(dataType) {
  return request({
    url: url + '/report/pd/findModelNoCountByMaster',
    method: 'get',
    params: { dataType }
  })
}
export function findModelNoCountBySUB(dataType) {
  return request({
    url: url + '/report/pd/findModelNoCountBySUB',
    method: 'get',
    params: { dataType }
  })
}
export function findModelNoCountByWT(dataType) {
  return request({
    url: url + '/report/pd/findModelNoCountByWT',
    method: 'get',
    params: { dataType }
  })
}
export function printLhBillDataWithPdf(title) {
  return request({
    url: url + '/report/pd/printLhBillDataWithPdf',
    method: 'get',
    params: { title },
    responseType: 'blob'
  })
}
export function printPdDataBillDataWithPdf(title) {
  return request({
    url: url + '/report/pd/printPdDataBillDataWithPdf',
    method: 'get',
    params: { title },
    responseType: 'blob'
  })
}
export function listPdBillData(data) {
  return request({
    url: url + '/report/pd/listPdBillData',
    method: 'post',
    data: data
  })
}
export function updatePdBillDataById(data) {
  return request({
    url: url + '/report/pd/updatePdBillDataById',
    method: 'post',
    data: data
  })
}
export function clearPdXpBillDataQtyById(data) {
  return request({
    url: url + '/report/pd/clearPdXpBillDataQtyById',
    method: 'post',
    data: data
  })
}
export function upXpBlankBillInputInfo(data) {
  return request({
    url: url + '/report/pd/upXpBlankBillInputInfo',
    method: 'post',
    data: data
  })
}
export function clearPdXpBlankBillDataQtyById(data) {
  return request({
    url: url + '/report/pd/clearPdXpBlankBillDataQtyById',
    method: 'post',
    data: data
  })
}
export function upArriveNotINBlankBillInputInfo(data) {
  return request({
    url: url + '/report/pd/upArriveNotINBlankBillInputInfo',
    method: 'post',
    data: data
  })
}
export function clearArriverNotInBlankBillDataQtyById(data) {
  return request({
    url: url + '/report/pd/clearArriverNotInBlankBillDataQtyById',
    method: 'post',
    data: data
  })
}
export function downBorrowExel() {
  return request({
    url: url + '/report/pd/downBorrowExel',
    method: 'post',
    responseType: 'blob'
  })
}
export function batchImpBorrowData(data) {
  return request({
    url: url + '/report/pd/batchImpBorrowData',
    method: 'post',
    data: data
  })
}
export function delBorrowDataById(data) {
  return request({
    url: url + '/report/pd/delBorrowDataById',
    method: 'post',
    data: data
  })
}
export function upBorrowInfo(data) {
  return request({
    url: url + '/report/pd/upBorrowInfo',
    method: 'post',
    data: data
  })
}
export function listBorrowDataList(data) {
  return request({
    url: url + '/report/pd/listBorrowDataList',
    method: 'post',
    data: data
  })
}
export function findPdBillDataListWithDiff(data) {
  return request({
    url: url + '/report/pd/findPdBillDataListWithDiff',
    method: 'post',
    data: data
  })
}
export function exportPdBillDiffFindDataList(data) {
  return request({
    url: url + '/report/pd/exportPdBillDiffFindDataList',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
export function updateDiffPdBillDataById(data) {
  return request({
    url: url + '/report/pd/updateDiffPdBillDataById',
    method: 'post',
    data: data
  })
}
export function delDiffPdBillDataById(data) {
  return request({
    url: url + '/report/pd/delDiffPdBillDataById',
    method: 'post',
    data: data
  })
}
export function listPdOtherDataImp() {
  return request({
    url: url + '/report/pd/listPdOtherDataImp',
    method: 'post'
  })
}
export function delOtherDataById(data) {
  return request({
    url: url + '/report/pd/delOtherDataById',
    method: 'post',
    data: data
  })
}
export function editOtherData(data) {
  return request({
    url: url + '/report/pd/editOtherData',
    method: 'post',
    data: data
  })
}
export function suerAgainInput(warehouseCode) {
  return request({
    url: url + '/report/pd/suerAgainInput',
    method: 'get',
    params: { warehouseCode }
  })
}

export function exportOtherData(methodCode) {
  return request({
    url: url + '/report/pd/exportOtherData',
    method: 'get',
    params: { methodCode }
  })
}
export function getOpsAsWmsTaskNoticeStatus() {
  return request({
    url: url + '/report/pd/getOpsAsWmsTaskNoticeStatus',
    method: 'get'
  })
}
export function getPdStatusFromRedis() {
  return request({
    url: url + '/report/pd/getPdStatusFromRedis',
    method: 'get'
  })
}
export function remoteInvoiceWithArriveNotIn(invoice) {
  return request({
    url: url + '/report/pd/remoteInvoiceWithArriveNotIn',
    method: 'get',
    params: { invoice }
  })
}
export function remotePdBillNo(remotePdBillNoVo) {
  return request({
    url: url + '/report/pd/remotePdBillNo',
    method: 'post',
    data: remotePdBillNoVo
  })
}
export function isCreDhwrBlankBill() {
  return request({
    url: url + '/report/pd/isCreDhwrBlankBill',
    method: 'get'
  })
}
export function getPdSureDateType() {
  return request({
    url: url + '/report/pd/getPdSureDateType',
    method: 'get'
  })
}
export function exportOtherDataWithExcel(methodCode) {
  return request({
    url: url + '/report/pd/exportOtherDataWithExcel',
    method: 'get',
    params: { methodCode },
    responseType: 'blob'
  })
}
export function exportShelvesData() {
  return request({
    url: url + '/report/pd/exportShelvesData',
    method: 'get',
    responseType: 'blob'
  })
}

export function exportBillCountData(dataType) {
  return request({
    url: url + '/report/pd/exportBillCountData',
    method: 'get',
    params: { dataType },
    responseType: 'blob'
  })
}

// ------报表------
export function pdReportList(data) {
  return request({
    url: url + '/report/pd/pdReportList',
    method: 'post',
    data: data
  })
}
export function markPdReport() {
  return request({
    url: url + '/report/pd/markPdReport',
    method: 'get'
  })
}
export function exportThreePdReport(data) {
  return request({
    url: url + '/report/pd/exportThreePdReport',
    method: 'post',
    data: data
  })
}
export function exportTwoPdReport() {
  return request({
    url: url + '/report/pd/exportTwoPdReport',
    method: 'get'
  })
}
export function makeCurPBatchCancel() {
  return request({
    url: url + '/report/pd/makeCurPBatchCancel',
    method: 'get'
  })
}
export function findPdAdjustList(data) {
  return request({
    url: url + '/report/pd/findPdAdjustList',
    method: 'post',
    data: data
  })
}
export function exportPdAdjustData(data) {
  return request({
    url: url + '/report/pd/exportPdAdjustData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
export function createPdAdjust(optUser) {
  return request({
    url: url + '/report/pd/createPdAdjust',
    method: 'get',
    params: { optUser }
  })
}
export function confirmPdAccount(data) {
  return request({
    url: url + '/report/pd/confirmPdAccount',
    method: 'post',
    data: data
  })
}
export function createAdjustNo(warehouseCode) {
  return request({
    url: url + '/report/pd/createAdjustNo',
    method: 'get',
    params: { warehouseCode }
  })
}
export function createAdjustform(data) {
  return request({
    url: url + '/report/pd/createAdjustform',
    method: 'post',
    data: data
  })
}
export function dowmBatchAdjusExcel() {
  return request({
    url: url + '/report/pd/dowmBatchAdjusExcel',
    method: 'get',
    responseType: 'blob'
  })
}
// 通过文件进行销账
export function batchAdjustData(data) {
  return request({
    url: url + '/report/pd/batchAdjustData',
    method: 'post',
    data: data
  })
}
export function surePdDataDiff(optUser) {
  return request({
    url: url + '/report/pd/surePdDataDiff',
    method: 'get',
    params: { optUser }
  })
}
export function listYdPdBatchList(data) {
  return request({
    url: url + '/report/pd/listYdPdBatchList',
    method: 'post',
    data: data
  })
}
export function listPdExecPlan(data) {
  return request({
    url: url + '/report/pd/listPdExecPlan',
    method: 'post',
    data: data
  })
}
export function updateOrAddPdExecPlan(data) {
  return request({
    url: url + '/report/pd/updateOrAddPdExecPlan',
    method: 'post',
    data: data
  })
}

export function makeExecPlan() {
  return request({
    url: url + '/report/pd/makeExecPlan',
    method: 'get'
  })
}
export function uiExecYdPd(code) {
  return request({
    url: url + '/report/pd/uiExecYdPd',
    method: 'get',
    params: { code }
  })
}
export function getExecStepList() {
  return request({
    url: url + '/report/pd/getExecStepList',
    method: 'get'
  })
}
export function getPdBatchNoListFromReportWare(pdBatchNo) {
  return request({
    url: url + '/report/pd/getPdBatchNoListFromReportWare',
    method: 'get',
    params: { pdBatchNo }
  })
}
export function listYdPdThreeReportWare(data) {
  return request({
    url: url + '/report/pd/listYdPdThreeReportWare',
    method: 'post',
    data: data
  })
}
export function exportYdPdReport(data) {
  return request({
    url: url + '/report/pd/exportYdPdReport',
    method: 'post',
    data: data
  })
}
export function exportYdPdStep(methodCode) {
  return request({
    url: url + '/report/pd/exportYdPdStep',
    method: 'get',
    params: { methodCode },
    responseType: 'blob'
  })
}
export function makePdReport() {
  return request({
    url: url + '/report/pd/makePdReport',
    method: 'get'
  })
}
