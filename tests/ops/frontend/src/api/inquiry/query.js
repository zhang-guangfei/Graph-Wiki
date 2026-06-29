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

// 查询接口
export function fetchReplyList(applyno) {
  return request({
    url: url + '/inquiry/apply/fetchReplyList',
    method: 'post',
    data: applyno
  })
}

// 导出接口
export function exportExcelData(searchRequest) {
  return request({
    url: url + '/inquiry/apply/exportExcel',
    method: 'post',
    data: searchRequest,
    responseType: 'blob'
  })
}
