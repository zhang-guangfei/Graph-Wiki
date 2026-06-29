import { downLoadNoticeFile } from '@/api/notice'
import { downloadMarketFile } from '@/api/upload'
import { downloadTemplateByType } from '@/api/download'
import { quotationPrint, contractPrint } from '@/api/pdfPrint'

export function downLoadAttachedFile(noticeId, fileName) {
  downLoadNoticeFile(noticeId).then(data => {
    const content = data
    const blob = new Blob([content])
    downLoadFile(blob, fileName)
  })
}
/**
 * 报价或者特价明细模板下载
 */
export function downloadTemplate(params, fileName) {
  downloadTemplateByType(params).then(data => {
    const content = data
    const blob = new Blob([content])
    downLoadFile(blob, fileName)
  })
}
/**
 * 报价单或者预报价单PDF下载
 */
export function downloadQuotationPdf(params, fileName) {
  quotationPrint(params).then(data => {
    const content = data
    const blob = new Blob([content])
    downLoadFile(blob, fileName)
  })
}

/**
 * 供销合同PDF下载
 */
export function downloadContractPdf(params, fileName) {
  contractPrint(params).then(data => {
    const content = data
    const blob = new Blob([content])
    downLoadFile(blob, fileName)
  })
}

export function downloadMarketAttachment(fileUpload) {
  downloadMarketFile(fileUpload).then(data => {
    const content = data
    const blob = new Blob([content])
    downLoadFile(blob, fileUpload.realFileName)
  })
}

export function downloadTransferAttachment(fileUpload) {
  downloadMarketFile(fileUpload).then(data => {
    const content = data
    const blob = new Blob([content])
    downLoadFile(blob, fileUpload.realFileName)
  })
}

export function downLoadFile(blob, fileName) {
  if ('download' in document.createElement('a')) { // 非IE下载
    const link = document.createElement('a')
    link.download = fileName
    link.style.display = 'none'
    link.href = URL.createObjectURL(blob)
    document.body.appendChild(link)
    link.click()
    URL.revokeObjectURL(link.href) // 释放URL 对象
    document.body.removeChild(link)
  } else { // IE10+下载
    navigator.msSaveBlob(blob, fileName)
  }
}
