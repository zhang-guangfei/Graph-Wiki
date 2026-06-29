import request from '@/utils/request'

const url = process.env.OPS_ORDER_API

// 订单日志查询
export function findOrderLog(searchForm) {
  return request({
    url: url + '/order/log/findOrderLog',
    method: 'post',
    data: searchForm
  })
}
