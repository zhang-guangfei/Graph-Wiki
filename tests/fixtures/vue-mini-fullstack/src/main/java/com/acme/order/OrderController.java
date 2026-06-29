package com.acme.order;

import com.acme.order.dto.OrderRequest;
import com.acme.order.service.OrderService;

@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService = new OrderService();

    @PostMapping("/create")
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/list")
    public OrderResponse loadOrders() {
        return orderService.loadOrders();
    }
}
