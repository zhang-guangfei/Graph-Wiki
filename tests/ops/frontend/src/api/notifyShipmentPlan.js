import request from '@/utils/request'
const url = process.env.OPS_API_WM

export function getNotifyShipmentPlanList(data) {
  return request({
    url: url + '/notify/shipment/page/search',
    method: 'post',
    data
  })
}

export function getNotifyShipmentItemPlanList(planNo) {
  return request({
    url: url + '/notify/shipment/page/itemPlan?planNo=' + planNo,
    method: 'get'
  })
}
export function getWLDate(data) {
  return request({
    url: url + '/notify/shipment/create/wl',
    method: 'post',
    data
  })
}

export function getCreateVerify(data) {
  return request({
    url: url + '/notify/shipment/create/verify?orderFno=' + data,
    method: 'get',
    data
  })
}

export function save(data) {
  return request({
    url: url + '/notify/shipment/create/save',
    method: 'post',
    data
  })
}

// excel导入
export function importData(data) {
  return request({
    url: url + '/notify/shipment/excel/import',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: data
  })
}

export function exportData(data) {
  return request({
    url: url + '/notify/shipment/excel/export/data',
    method: 'post',
    data
  })
}
