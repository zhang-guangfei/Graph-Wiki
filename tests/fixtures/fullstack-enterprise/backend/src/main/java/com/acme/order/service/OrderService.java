package com.acme.order.service;

import com.acme.order.dto.CreateOrderRequest;

public interface OrderService {
    OrderEntity createOrder(CreateOrderRequest request);
    OrderPreview previewOrder(CreateOrderRequest request);
}
