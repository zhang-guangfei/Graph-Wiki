package com.acme.order.controller;

import com.acme.order.dto.CreateOrderRequest;
import com.acme.order.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderEntity createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PostMapping("/preview")
    public OrderPreview previewOrder(@RequestBody CreateOrderRequest request) {
        return orderService.previewOrder(request);
    }
}
