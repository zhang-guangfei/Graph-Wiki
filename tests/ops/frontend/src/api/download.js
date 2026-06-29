import request from '@/utils/request'
export function downloadTemplateByType(params) {
  return request({
    url: '/template/downLoadTemplate',
    responseType: 'blob',
    method: 'post',
    params: params
  })
}
