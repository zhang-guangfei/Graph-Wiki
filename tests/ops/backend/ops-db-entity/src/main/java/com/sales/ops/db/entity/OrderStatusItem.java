package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderStatusItem implements Serializable {
    private Long id;

    private String orderId;

    private Integer orderItem;

    private Integer splitNo;

    private Integer pcoItem;

    private Integer qty;

    private Integer qtyIn;

    private Integer qtyOut;

    private Long inventoryId;

    private String inventoryTable;

    private String status;

    private String statusDesc;

    private String statusInfo;

    private String inventoryType;

    private String invoiceNo;

    private String associateNo;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

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

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public Integer getSplitNo() {
        return splitNo;
    }

    public void setSplitNo(Integer splitNo) {
        this.splitNo = splitNo;
    }

    public Integer getPcoItem() {
        return pcoItem;
    }

    public void setPcoItem(Integer pcoItem) {
        this.pcoItem = pcoItem;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getQtyIn() {
        return qtyIn;
    }

    public void setQtyIn(Integer qtyIn) {
        this.qtyIn = qtyIn;
    }

    public Integer getQtyOut() {
        return qtyOut;
    }

    public void setQtyOut(Integer qtyOut) {
        this.qtyOut = qtyOut;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryTable() {
        return inventoryTable;
    }

    public void setInventoryTable(String inventoryTable) {
        this.inventoryTable = inventoryTable == null ? null : inventoryTable.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc == null ? null : statusDesc.trim();
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo == null ? null : statusInfo.trim();
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType == null ? null : inventoryType.trim();
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getAssociateNo() {
        return associateNo;
    }

    public void setAssociateNo(String associateNo) {
        this.associateNo = associateNo == null ? null : associateNo.trim();
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
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
        OrderStatusItem other = (OrderStatusItem) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getSplitNo() == null ? other.getSplitNo() == null : this.getSplitNo().equals(other.getSplitNo()))
            && (this.getPcoItem() == null ? other.getPcoItem() == null : this.getPcoItem().equals(other.getPcoItem()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getQtyIn() == null ? other.getQtyIn() == null : this.getQtyIn().equals(other.getQtyIn()))
            && (this.getQtyOut() == null ? other.getQtyOut() == null : this.getQtyOut().equals(other.getQtyOut()))
            && (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getInventoryTable() == null ? other.getInventoryTable() == null : this.getInventoryTable().equals(other.getInventoryTable()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getStatusDesc() == null ? other.getStatusDesc() == null : this.getStatusDesc().equals(other.getStatusDesc()))
            && (this.getStatusInfo() == null ? other.getStatusInfo() == null : this.getStatusInfo().equals(other.getStatusInfo()))
            && (this.getInventoryType() == null ? other.getInventoryType() == null : this.getInventoryType().equals(other.getInventoryType()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getAssociateNo() == null ? other.getAssociateNo() == null : this.getAssociateNo().equals(other.getAssociateNo()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getSplitNo() == null) ? 0 : getSplitNo().hashCode());
        result = prime * result + ((getPcoItem() == null) ? 0 : getPcoItem().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getQtyIn() == null) ? 0 : getQtyIn().hashCode());
        result = prime * result + ((getQtyOut() == null) ? 0 : getQtyOut().hashCode());
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getInventoryTable() == null) ? 0 : getInventoryTable().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getStatusDesc() == null) ? 0 : getStatusDesc().hashCode());
        result = prime * result + ((getStatusInfo() == null) ? 0 : getStatusInfo().hashCode());
        result = prime * result + ((getInventoryType() == null) ? 0 : getInventoryType().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getAssociateNo() == null) ? 0 : getAssociateNo().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        return result;
    }
}