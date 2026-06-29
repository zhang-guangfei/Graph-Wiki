import request from '@/utils/request'
/**
 *标签补打
 * @param {*} params
 */
export function preQuotation(params) {
  return request({
    url: '/preQuotation/viewPreQuotationPdf',
    responseType: 'blob',
    method: 'post',
    params: params
  })
}
