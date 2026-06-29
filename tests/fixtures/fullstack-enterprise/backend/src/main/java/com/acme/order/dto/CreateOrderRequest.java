package com.acme.order.dto;

public class CreateOrderRequest {
    private String customerId;
    private String skuId;
    private Integer quantity;

    public String getCustomerId() { return customerId; }
    public String getSkuId() { return skuId; }
    public Integer getQuantity() { return quantity; }
}
