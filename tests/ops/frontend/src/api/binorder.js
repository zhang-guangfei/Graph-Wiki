import request from '@/utils/request'

// 查询bin销售
export function listModelExpFreq(data, page) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listModelExpFreq',
    method: 'post',
    data: data,
    params: page
  })
}

export function listModeldetail(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listModeldetail',
    method: 'post',
    data: data
  })
}

export function listBinOrderDetail(data) {
  return request({
    url: process.env.OPS_STOCK_API + '/cpfr/binorder/listCalcBinOrderDetail',
    method: 'post',
    data: data
  })
}
