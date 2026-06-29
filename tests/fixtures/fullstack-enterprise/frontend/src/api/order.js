import request from '../utils/request'

export function createOrder(data) {
  return request.post('/orders', data)
}

export function previewOrder(data) {
  return request.post('/orders/preview', data)
}
