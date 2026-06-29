import request from '@/utils/request'

/**
 * // 预报价单或者报价单PDF打印
 * @param {*} quotationDetailList
 * @param {*} preQuotation
 * @returns
 */
export function quotationPrint(params) {
  return request({
    url: '/pdfPrint/quotationPrint',
    method: 'post',
    responseType: 'blob',
    params: params
  })
}

/**
 * 详情预览，正式报价单，预报价单
 * @param {*} data
 * @param {*} params
 */
export function quotationPreviewPrint(data, params) {
  return request({
    url: '/pdfPrint/quotationPreviewPrint',
    method: 'post',
    responseType: 'blob',
    params: params,
    data: data
  })
}

/**
 * // 供销合同
 * @param {*} quotationDetailList
 * @param {*} preQuotation
 * @returns
 */
export function contractPrint(params) {
  return request({
    url: '/pdfPrint/contractPrint',
    method: 'post',
    responseType: 'blob',
    params: params
  })
}

/**
 * SMC合同预览
 * @param {*} data
 * @param {*} params
 */
export function contractPreviewPrint(data, params) {
  return request({
    url: '/pdfPrint/contractPreviewPrint',
    method: 'post',
    responseType: 'blob',
    params: params,
    data: data
  })
}
