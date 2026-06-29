import request from '@/utils/request'
const url = process.env.ETA

// excel导入
export function importData(data) {
  return request({
    url: url + '/importExcel/getResult',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data: data
  })
}
