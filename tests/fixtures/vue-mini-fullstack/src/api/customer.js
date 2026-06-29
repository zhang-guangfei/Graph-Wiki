export function approveCustomer(data) {
  return request.post('/customer/approve', data)
}

export function searchCustomers(params) {
  return request.get('/customer/search', { params })
}
