package com.acme.order.service;

public interface InventoryService {
    void checkStock(String skuId, Integer quantity);
}
