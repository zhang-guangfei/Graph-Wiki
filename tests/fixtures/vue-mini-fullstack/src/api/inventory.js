export function submitStockTransfer(data) {
  return request.post('/inventory/transfer/submit', data)
}

export function queryStock(params) {
  return request.get('/inventory/stock/query', { params })
}
