package com.acme.order.entity;

@TableName("orders")
public class OrderEntity {
    @TableField("order_no")
    private String orderNo;

    @TableField("customer_code")
    private String customerCode;

    @TableField("quantity")
    private Integer quantity;
}
