package com.sales.ops.db.entity;

import java.io.Serializable;

public class OrderInventory implements Serializable {
    private String orderId;

    private String orderItem;

    private Long doKey;

    private Long doItemKey;

    private Long doItemInvKey;

    private String doId;

    private Integer doItem;

    private Long inventoryId;

    private String inventoryTableType;

    private static final long serialVersionUID = 1L;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem == null ? null : orderItem.trim();
    }

    public Long getDoKey() {
        return doKey;
    }

    public void setDoKey(Long doKey) {
        this.doKey = doKey;
    }

    public Long getDoItemKey() {
        return doItemKey;
    }

    public void setDoItemKey(Long doItemKey) {
        this.doItemKey = doItemKey;
    }

    public Long getDoItemInvKey() {
        return doItemInvKey;
    }

    public void setDoItemInvKey(Long doItemInvKey) {
        this.doItemInvKey = doItemInvKey;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public Integer getDoItem() {
        return doItem;
    }

    public void setDoItem(Integer doItem) {
        this.doItem = doItem;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryTableType() {
        return inventoryTableType;
    }

    public void setInventoryTableType(String inventoryTableType) {
        this.inventoryTableType = inventoryTableType == null ? null : inventoryTableType.trim();
    }
}