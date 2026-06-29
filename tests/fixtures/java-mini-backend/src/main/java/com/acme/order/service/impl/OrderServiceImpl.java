package com.acme.order.service.impl;

import com.acme.order.dto.OrderRequest;
import com.acme.order.entity.OrderEntity;
import com.acme.order.mapper.OrderMapper;

public class OrderServiceImpl {
    private final OrderMapper orderMapper = new OrderMapper();

    public void createOrder(OrderRequest request) {
        OrderEntity entity = new OrderEntity();
        entity.orderNo = request.orderNo;
        orderMapper.saveOrder(entity);
    }

    public void approveOrder(String orderNo) {
        orderMapper.updateOrderStatus(orderNo, "APPROVED");
    }

    public void loadOrders() {
        orderMapper.findOrders();
    }
}
