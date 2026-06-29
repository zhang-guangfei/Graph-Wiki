package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsOrderInventoryLog implements Serializable {
    private Long id;

    private String orderId;

    private String orderItem;

    private Long inventoryId;

    private Integer qty;

    private String inventoryTableType;

    private Date creTime;

    private String modelno;

    private Integer orderQty;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getInventoryTableType() {
        return inventoryTableType;
    }

    public void setInventoryTableType(String inventoryTableType) {
        this.inventoryTableType = inventoryTableType == null ? null : inventoryTableType.trim();
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OpsOrderInventoryLog other = (OpsOrderInventoryLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getInventoryTableType() == null ? other.getInventoryTableType() == null : this.getInventoryTableType().equals(other.getInventoryTableType()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getOrderQty() == null ? other.getOrderQty() == null : this.getOrderQty().equals(other.getOrderQty()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getInventoryTableType() == null) ? 0 : getInventoryTableType().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getOrderQty() == null) ? 0 : getOrderQty().hashCode());
        return result;
    }
}