import request from '@/utils/request'

const fileUploadApi = process.env.SALE_MANAGE_API

export function uploadBusinessFile(formData) {
  return request({
    url: fileUploadApi + '/file/uploadBusinessFile',
    method: 'post',
    data: formData
  })
}

export function deleteFileById(id) {
  return request({
    url: '/file/deleteByFileId',
    method: 'post',
    params: { id }
  })
}

export function downloadMarketFile(fileUpload) {
  return request({
    url: '/fileUpload/download',
    responseType: 'blob', // !!!!必须
    method: 'post',
    data: fileUpload
  })
}

// 根据单号查询是否有附件
export function findAttachment(params) {
  return request({
    url: fileUploadApi + '/file/findByBussinessKeyValue',
    method: 'get',
    params: params
  })
}

// 根据单号查询是否有附件
export function findAttachmentByBussinessKey(businessKeyValue) {
  return request({
    url: fileUploadApi + '/file/findByBussinessKeyValue',
    method: 'get',
    params: { businessKeyValue }
  })
}

// 下载附件
export function downLoadFile(filePath) {
  return request({
    url: fileUploadApi + '/file/downLoadFile',
    responseType: 'blob', // !!!!必须
    method: 'post',
    params: { filePath }
  })
}

/**
 * 最终可以调用的方法
 * @param {} filePath 全路径：路径+文件名
 * @param {*} fileName 下载下来的文件名
 */
export function downloadAttachment(filePath, fileName) {
  downLoadFile(filePath).then(data => {
    const content = data
    const blob = new Blob([content], { type: 'application/octet-stream' })
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
  })
}

/**
 * 二进制文件流下载
 * @param {*} content 文件内容
 * @param {*} fileName 文件名称
 *
 */
export function downloadFile(content, fileName) {
  const blob = new Blob([content])
  if ('download' in document.createElement('a')) { // 非IE下载
    const elink = document.createElement('a')
    elink.download = fileName
    elink.href = window.URL.createObjectURL(blob)
    document.body.appendChild(elink)
    elink.click()
    window.URL.revokeObjectURL(elink.href) // 释放URL 对象
    document.body.removeChild(elink)
  } else { // IE10+下载
    navigator.msSaveBlob(blob, fileName)
  }
}
