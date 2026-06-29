package com.sales.ops.db.entity;

import java.io.Serializable;

public class TempReplaceId implements Serializable {
    private Long id;

    private String doId;

    private String pcoId;

    private String orderId;

    private String orderItem;

    private Integer pcoItem;

    private String subModelno;

    private Integer subQty;

    private Long inventoryId;

    private String inventoryTableType;

    private String inventoryStatus;

    private String waitType;

    private String warehouseCode;

    private String gatherWarehouseCode;

    private Integer handleStatus;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public String getPcoId() {
        return pcoId;
    }

    public void setPcoId(String pcoId) {
        this.pcoId = pcoId == null ? null : pcoId.trim();
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

    public Integer getPcoItem() {
        return pcoItem;
    }

    public void setPcoItem(Integer pcoItem) {
        this.pcoItem = pcoItem;
    }

    public String getSubModelno() {
        return subModelno;
    }

    public void setSubModelno(String subModelno) {
        this.subModelno = subModelno == null ? null : subModelno.trim();
    }

    public Integer getSubQty() {
        return subQty;
    }

    public void setSubQty(Integer subQty) {
        this.subQty = subQty;
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

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus == null ? null : inventoryStatus.trim();
    }

    public String getWaitType() {
        return waitType;
    }

    public void setWaitType(String waitType) {
        this.waitType = waitType == null ? null : waitType.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getGatherWarehouseCode() {
        return gatherWarehouseCode;
    }

    public void setGatherWarehouseCode(String gatherWarehouseCode) {
        this.gatherWarehouseCode = gatherWarehouseCode == null ? null : gatherWarehouseCode.trim();
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        TempReplaceId other = (TempReplaceId) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()))
            && (this.getPcoId() == null ? other.getPcoId() == null : this.getPcoId().equals(other.getPcoId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getPcoItem() == null ? other.getPcoItem() == null : this.getPcoItem().equals(other.getPcoItem()))
            && (this.getSubModelno() == null ? other.getSubModelno() == null : this.getSubModelno().equals(other.getSubModelno()))
            && (this.getSubQty() == null ? other.getSubQty() == null : this.getSubQty().equals(other.getSubQty()))
            && (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getInventoryTableType() == null ? other.getInventoryTableType() == null : this.getInventoryTableType().equals(other.getInventoryTableType()))
            && (this.getInventoryStatus() == null ? other.getInventoryStatus() == null : this.getInventoryStatus().equals(other.getInventoryStatus()))
            && (this.getWaitType() == null ? other.getWaitType() == null : this.getWaitType().equals(other.getWaitType()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getGatherWarehouseCode() == null ? other.getGatherWarehouseCode() == null : this.getGatherWarehouseCode().equals(other.getGatherWarehouseCode()))
            && (this.getHandleStatus() == null ? other.getHandleStatus() == null : this.getHandleStatus().equals(other.getHandleStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        result = prime * result + ((getPcoId() == null) ? 0 : getPcoId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getPcoItem() == null) ? 0 : getPcoItem().hashCode());
        result = prime * result + ((getSubModelno() == null) ? 0 : getSubModelno().hashCode());
        result = prime * result + ((getSubQty() == null) ? 0 : getSubQty().hashCode());
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getInventoryTableType() == null) ? 0 : getInventoryTableType().hashCode());
        result = prime * result + ((getInventoryStatus() == null) ? 0 : getInventoryStatus().hashCode());
        result = prime * result + ((getWaitType() == null) ? 0 : getWaitType().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getGatherWarehouseCode() == null) ? 0 : getGatherWarehouseCode().hashCode());
        result = prime * result + ((getHandleStatus() == null) ? 0 : getHandleStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}