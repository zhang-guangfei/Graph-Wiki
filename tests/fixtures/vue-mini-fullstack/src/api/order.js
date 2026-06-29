export function createOrder(data) {
  return request.post('/order/create', data)
}

export function loadOrders(params) {
  return request.get('/order/list', { params })
}
