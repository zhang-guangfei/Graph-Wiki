package com.acme.order.entity;

@TableName("orders")
public class OrderEntity {
    @TableField("customer_id")
    private String customerId;

    @TableField("sku_id")
    private String skuId;

    @TableField("quantity")
    private Integer quantity;

    @TableField("status")
    private String status;

    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setSkuId(String skuId) { this.skuId = skuId; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setStatus(String status) { this.status = status; }
}
