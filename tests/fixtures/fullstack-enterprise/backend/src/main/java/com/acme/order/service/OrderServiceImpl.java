package com.acme.order.service;

import com.acme.order.dto.CreateOrderRequest;
import com.acme.order.entity.OrderEntity;

@Service
public class OrderServiceImpl implements OrderService {
    private final InventoryService inventoryService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(InventoryService inventoryService, OrderRepository orderRepository) {
        this.inventoryService = inventoryService;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderEntity createOrder(CreateOrderRequest request) {
        validateQuantity(request.getQuantity());
        inventoryService.checkStock(request.getSkuId(), request.getQuantity());
        OrderEntity order = new OrderEntity();
        order.setCustomerId(request.getCustomerId());
        order.setSkuId(request.getSkuId());
        order.setQuantity(request.getQuantity());
        order.setStatus("CREATED");
        return orderRepository.save(order);
    }

    @Override
    public OrderPreview previewOrder(CreateOrderRequest request) {
        inventoryService.checkStock(request.getSkuId(), request.getQuantity());
        return new OrderPreview(request.getSkuId(), request.getQuantity());
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
    }
}
