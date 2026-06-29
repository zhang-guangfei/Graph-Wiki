package com.acme.order.service;

import com.acme.order.dto.OrderRequest;
import com.acme.order.entity.OrderEntity;
import com.acme.order.mapper.OrderMapper;

public class OrderService {
    private final OrderMapper orderMapper = new OrderMapper();

    public OrderResponse createOrder(OrderRequest request) {
        OrderEntity entity = new OrderEntity();
        orderMapper.saveOrder(entity);
        return new OrderResponse();
    }

    public OrderResponse loadOrders() {
        orderMapper.findOrders();
        return new OrderResponse();
    }
}
