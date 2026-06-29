package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderStatusView implements Serializable {
    private String orderId;

    private Integer orderItem;

    private Integer splitNo;

    private Integer pcoItem;

    private String splitType;

    private String modelno;

    private Integer qtyDo;

    private Integer qty;

    private Integer qtyIn;

    private Integer qtyOut;

    private String associateNo;

    private String status;

    private String statusDesc;

    private String statusInfo;

    private String wmOrderId;

    private Long inventoryId;

    private String inventoryTable;

    private Date wlDate;

    private String warehouseCode;

    private Date estimatedDeliveryDay;

    private Integer reliability;

    private Date modifyTime;

    private static final long serialVersionUID = 1L;

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

    public String getSplitType() {
        return splitType;
    }

    public void setSplitType(String splitType) {
        this.splitType = splitType == null ? null : splitType.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getQtyDo() {
        return qtyDo;
    }

    public void setQtyDo(Integer qtyDo) {
        this.qtyDo = qtyDo;
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

    public String getAssociateNo() {
        return associateNo;
    }

    public void setAssociateNo(String associateNo) {
        this.associateNo = associateNo == null ? null : associateNo.trim();
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

    public String getWmOrderId() {
        return wmOrderId;
    }

    public void setWmOrderId(String wmOrderId) {
        this.wmOrderId = wmOrderId == null ? null : wmOrderId.trim();
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

    public Date getWlDate() {
        return wlDate;
    }

    public void setWlDate(Date wlDate) {
        this.wlDate = wlDate;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public Date getEstimatedDeliveryDay() {
        return estimatedDeliveryDay;
    }

    public void setEstimatedDeliveryDay(Date estimatedDeliveryDay) {
        this.estimatedDeliveryDay = estimatedDeliveryDay;
    }

    public Integer getReliability() {
        return reliability;
    }

    public void setReliability(Integer reliability) {
        this.reliability = reliability;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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
        OrderStatusView other = (OrderStatusView) that;
        return (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getSplitNo() == null ? other.getSplitNo() == null : this.getSplitNo().equals(other.getSplitNo()))
            && (this.getPcoItem() == null ? other.getPcoItem() == null : this.getPcoItem().equals(other.getPcoItem()))
            && (this.getSplitType() == null ? other.getSplitType() == null : this.getSplitType().equals(other.getSplitType()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQtyDo() == null ? other.getQtyDo() == null : this.getQtyDo().equals(other.getQtyDo()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getQtyIn() == null ? other.getQtyIn() == null : this.getQtyIn().equals(other.getQtyIn()))
            && (this.getQtyOut() == null ? other.getQtyOut() == null : this.getQtyOut().equals(other.getQtyOut()))
            && (this.getAssociateNo() == null ? other.getAssociateNo() == null : this.getAssociateNo().equals(other.getAssociateNo()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getStatusDesc() == null ? other.getStatusDesc() == null : this.getStatusDesc().equals(other.getStatusDesc()))
            && (this.getStatusInfo() == null ? other.getStatusInfo() == null : this.getStatusInfo().equals(other.getStatusInfo()))
            && (this.getWmOrderId() == null ? other.getWmOrderId() == null : this.getWmOrderId().equals(other.getWmOrderId()))
            && (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getInventoryTable() == null ? other.getInventoryTable() == null : this.getInventoryTable().equals(other.getInventoryTable()))
            && (this.getWlDate() == null ? other.getWlDate() == null : this.getWlDate().equals(other.getWlDate()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getEstimatedDeliveryDay() == null ? other.getEstimatedDeliveryDay() == null : this.getEstimatedDeliveryDay().equals(other.getEstimatedDeliveryDay()))
            && (this.getReliability() == null ? other.getReliability() == null : this.getReliability().equals(other.getReliability()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getSplitNo() == null) ? 0 : getSplitNo().hashCode());
        result = prime * result + ((getPcoItem() == null) ? 0 : getPcoItem().hashCode());
        result = prime * result + ((getSplitType() == null) ? 0 : getSplitType().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQtyDo() == null) ? 0 : getQtyDo().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getQtyIn() == null) ? 0 : getQtyIn().hashCode());
        result = prime * result + ((getQtyOut() == null) ? 0 : getQtyOut().hashCode());
        result = prime * result + ((getAssociateNo() == null) ? 0 : getAssociateNo().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getStatusDesc() == null) ? 0 : getStatusDesc().hashCode());
        result = prime * result + ((getStatusInfo() == null) ? 0 : getStatusInfo().hashCode());
        result = prime * result + ((getWmOrderId() == null) ? 0 : getWmOrderId().hashCode());
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getInventoryTable() == null) ? 0 : getInventoryTable().hashCode());
        result = prime * result + ((getWlDate() == null) ? 0 : getWlDate().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getEstimatedDeliveryDay() == null) ? 0 : getEstimatedDeliveryDay().hashCode());
        result = prime * result + ((getReliability() == null) ? 0 : getReliability().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        return result;
    }
}