import request from '@/utils/request'
const url = process.env.OPS_SHAREAPP_API

// 查询接口
export function fetchList(searchRequest, page) {
  return request({
    url: url + '/shareapp/purchasemodify/findAll',
    method: 'post',
    data: searchRequest,
    params: page
  })
}
// 运输方式变更 处理接口
export function transTypeApprove(data) {
  return request({
    url: url + '/shareapp/purchasemodify/transTypeApproveData',
    method: 'post',
    data: data
  })
}
// 供应商变更 处理接口
export function suppilyDataApprove(data) {
  return request({
    url: url + '/shareapp/purchasemodify/suppilyDataApproveData',
    method: 'post',
    data: data
  })
}

// 删单处理接口
export function deleteApprove(data) {
  return request({
    url: url + '/shareapp/purchasemodify/deleteApproveData',
    method: 'post',
    data: data
  })
}

// 否决和暂不处理操作
export function negateApprove(data) {
  return request({
    url: url + '/shareapp/purchasemodify/layasideData',
    method: 'post',
    data: data
  })
}

// 导入操作
export function importBatchData(data) {
  return request({
    url: url + '/shareapp/purchasemodify/importBatchData',
    method: 'post',
    data: data
  })
}

