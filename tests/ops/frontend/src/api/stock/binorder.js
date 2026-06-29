import request from '@/utils/request'

// 查询bin销售
export function listModelExpFreq(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listModelExpFreq',
    method: 'post',
    data: data
  })
}

export function listModeldetail(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listModeldetail',
    method: 'post',
    data: data
  })
}
export function listModeldetailByJob(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listModeldetailByJob',
    method: 'post',
    data: data
  })
}

export function listBinOrderDetail(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listCalcBinOrderDetail',
    method: 'post',
    data: data
  })
}

export function listBinOrderApp(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listBinOrderApp',
    method: 'post',
    data: data
  })
}

export function approveBinOrder(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/approveBinOrder',
    method: 'post',
    data: data
  })
}

export function createOrder(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/createOrder',
    method: 'post',
    data: data
  })
}

export function listBinOrderCalc() {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listBinOrderCalc',
    method: 'post'
  })
}

export function newBinOrderCalcId(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/newBinOrderCalcId',
    method: 'post',
    data: data
  })
}

export function calcBinOrder(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/calcBinOrder',
    method: 'post',
    data: data
  })
}

export function applyBinOrder(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/applyBinOrder',
    method: 'post',
    data: data
  })
}

export function updateBinOrderDlvDate(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/updateBinOrderDlvDate',
    method: 'post',
    data: data
  })
}

export function batchUpdate(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/batchUpdate',
    method: 'post',
    data: data
  })
}

export function clearTransQty(calcId) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/clearTransQty/' + calcId,
    method: 'post'
  })
}

export function listBinOrderDetailSplit(fromId) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listBinOrderDetailSplit',
    method: 'post',
    params: { fromId }
  })
}

export function updateBinOrderDetail(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/updateBinOrderDetail',
    method: 'post',
    data: data
  })
}

export function listWarehouseStock(modelNo) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listBinWarehouseStock',
    method: 'post',
    params: { modelNo }
  })
}

export function addBinOrderDetails(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/saveBinOrderDetails',
    method: 'post',
    data: data
  })
}

export function exportModelExpFreq(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/exportModelExpFreq',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

export function listCstmBindata(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listCstmBindata',
    method: 'post',
    data: data
  })
}

export function getOpsInventoryProperty(id) {
  return request({
    url: process.env.OPS_STOCK_API + '/product/inventory/getOpsInventoryProperty',
    method: 'get',
    params: id
  })
}

export function sendToInStock(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/sendToInStock',
    method: 'post',
    data: data
  })
}

export function finishbinordercalc(id) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/finishbinordercalc',
    method: 'post',
    params: { id }
  })
}

export function exportCalcBinOrderData(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/exportCalcBinOrderData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

export function listBinDetailSplit(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listBinDetailSplit',
    method: 'post',
    data: data
  })
}

export function listOrdingOrder(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listOrdingOrder',
    method: 'get',
    params: data
  })
}

export function getModelExpFreqMain() {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/getModelExpFreqMain',
    method: 'get'
  })
}

export function exportBinDetailSplit(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/exportBinDetailSplit',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}

export function listPrepareOrderView(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listPrepareOrderView',
    method: 'get',
    params: data
  })
}

export function listBinTrialJobManageData(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/listBinTrialJobManageData',
    method: 'post',
    data: data
  })
}

export function saveBinTrialJobManager(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/saveBinTrialJobManager',
    method: 'post',
    data: data
  })
}

export function deleteBinTrialJobManager(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/deleteBinTrialJobManager',
    method: 'post',
    params: data
  })
}

export function sumitBinTrialJob(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/sumitBinTrialJob',
    method: 'post',
    params: data
  })
}
export function getBinTrialJobManageData(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/getBinTrialJobManageData',
    method: 'post',
    data: data
  })
}

export function copyBinTrialJobManager(jobId) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/copyBinTrialJobManager',
    method: 'post',
    params: jobId
  })
}

export function listBinTrialSalesBranchConfigData(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/listBinTrialSalesBranchConfigData',
    method: 'post',
    data: data
  })
}

export function saveBinTrialSalesBranchConfig(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/saveBinTrialSalesBranchConfig',
    method: 'post',
    data: data
  })
}

export function deleteBinTrialSalesBranchConfig(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/deleteBinTrialSalesBranchConfig',
    method: 'post',
    params: data
  })
}
export function createBinTrialSalesBranchConfig(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/createBinTrialSalesBranchConfig',
    method: 'post',
    params: data
  })
}
export function addBinTrialSalesBranchConfig(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/addBinTrialSalesBranchConfig',
    method: 'post',
    params: data
  })
}
export function restoreBinTrialSalesBranchConfig(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/restoreBinTrialSalesBranchConfig',
    method: 'post',
    params: data
  })
}
export function importBinConfigureData(formData) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/importBinConfigureData',
    method: 'post',
    data: formData
  })
}
export function exportBinConfigureData(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/exportBinConfigureData',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
export function listBinTrialSalesBranchDetail(formData) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/listBinTrialSalesBranchDetail',
    method: 'post',
    data: formData
  })
}
export function exporBinTrialSalesBranchDetail(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binTrial/exporBinTrialSalesBranchDetail',
    method: 'post',
    responseType: 'blob',
    data: data
  })
}
