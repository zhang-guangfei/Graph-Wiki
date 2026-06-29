package com.acme.order.service;

import com.acme.order.dto.OrderRequest;

public interface OrderService {
    void createOrder(OrderRequest request);
    void approveOrder(String orderNo);
    void loadOrders();
}
