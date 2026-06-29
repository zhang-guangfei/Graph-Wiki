package com.acme.order.controller;

import com.acme.order.dto.OrderRequest;
import com.acme.order.service.impl.OrderServiceImpl;

public class OrderController {
    private final OrderServiceImpl orderService = new OrderServiceImpl();

    public void createOrder(OrderRequest request) {
        orderService.createOrder(request);
    }

    public void approveOrder(String orderNo) {
        orderService.approveOrder(orderNo);
    }

    public void loadOrders() {
        orderService.loadOrders();
    }
}
