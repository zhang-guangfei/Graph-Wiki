import request from '@/utils/request'
const url = process.env.DELIVERY
// 查询接口
export function fetchList(searchRequest, page) {
  return request({
    url: url + '/inqb/apply/queryInqbList',
    method: 'post',
    data: searchRequest,
    params: page
  })
}

// 获取催促原因
export function findAllReason() {
  return request({
    url: url + '/inqb/handle/getAllReason',
    method: 'post'
  })
}

// 导出接口
export function exportExcelData(searchRequest) {
  return request({
    url: url + '/inqb/apply/exportExcel',
    method: 'post',
    data: searchRequest,
    responseType: 'blob'
  })
}
