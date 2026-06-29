package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class RcvorderAllocate implements Serializable {
    private Long id;

    private Integer fromType;

    private Long orderId;

    private String orderFno;

    private String rorderNo;

    private Integer status;

    private Integer deleteStatus;

    private String modelNo;

    private Integer quantity;

    private Integer expQty;

    private Integer readyQty;

    private Date shipTime;

    private String deliveryNo;

    private Long inventoryId;

    private String inventoryNo;

    private String stockCode;

    private String stockType;

    private String transactionNo;

    private Integer transactionType;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno == null ? null : orderFno.trim();
    }

    public String getRorderNo() {
        return rorderNo;
    }

    public void setRorderNo(String rorderNo) {
        this.rorderNo = rorderNo == null ? null : rorderNo.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getExpQty() {
        return expQty;
    }

    public void setExpQty(Integer expQty) {
        this.expQty = expQty;
    }

    public Integer getReadyQty() {
        return readyQty;
    }

    public void setReadyQty(Integer readyQty) {
        this.readyQty = readyQty;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo == null ? null : deliveryNo.trim();
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryNo() {
        return inventoryNo;
    }

    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo == null ? null : inventoryNo.trim();
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType == null ? null : stockType.trim();
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo == null ? null : transactionNo.trim();
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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
        RcvorderAllocate other = (RcvorderAllocate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFromType() == null ? other.getFromType() == null : this.getFromType().equals(other.getFromType()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderFno() == null ? other.getOrderFno() == null : this.getOrderFno().equals(other.getOrderFno()))
            && (this.getRorderNo() == null ? other.getRorderNo() == null : this.getRorderNo().equals(other.getRorderNo()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDeleteStatus() == null ? other.getDeleteStatus() == null : this.getDeleteStatus().equals(other.getDeleteStatus()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getExpQty() == null ? other.getExpQty() == null : this.getExpQty().equals(other.getExpQty()))
            && (this.getReadyQty() == null ? other.getReadyQty() == null : this.getReadyQty().equals(other.getReadyQty()))
            && (this.getShipTime() == null ? other.getShipTime() == null : this.getShipTime().equals(other.getShipTime()))
            && (this.getDeliveryNo() == null ? other.getDeliveryNo() == null : this.getDeliveryNo().equals(other.getDeliveryNo()))
            && (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getInventoryNo() == null ? other.getInventoryNo() == null : this.getInventoryNo().equals(other.getInventoryNo()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getStockType() == null ? other.getStockType() == null : this.getStockType().equals(other.getStockType()))
            && (this.getTransactionNo() == null ? other.getTransactionNo() == null : this.getTransactionNo().equals(other.getTransactionNo()))
            && (this.getTransactionType() == null ? other.getTransactionType() == null : this.getTransactionType().equals(other.getTransactionType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFromType() == null) ? 0 : getFromType().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderFno() == null) ? 0 : getOrderFno().hashCode());
        result = prime * result + ((getRorderNo() == null) ? 0 : getRorderNo().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDeleteStatus() == null) ? 0 : getDeleteStatus().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getExpQty() == null) ? 0 : getExpQty().hashCode());
        result = prime * result + ((getReadyQty() == null) ? 0 : getReadyQty().hashCode());
        result = prime * result + ((getShipTime() == null) ? 0 : getShipTime().hashCode());
        result = prime * result + ((getDeliveryNo() == null) ? 0 : getDeliveryNo().hashCode());
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getInventoryNo() == null) ? 0 : getInventoryNo().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getStockType() == null) ? 0 : getStockType().hashCode());
        result = prime * result + ((getTransactionNo() == null) ? 0 : getTransactionNo().hashCode());
        result = prime * result + ((getTransactionType() == null) ? 0 : getTransactionType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        return result;
    }
}