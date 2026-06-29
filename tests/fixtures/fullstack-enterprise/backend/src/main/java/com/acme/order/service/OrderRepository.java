package com.acme.order.service;

import com.acme.order.entity.OrderEntity;

public interface OrderRepository {
    OrderEntity save(OrderEntity order);
}
