package com.sales.ops.db.entity;

import java.io.Serializable;

public class DoConfirm implements Serializable {
    private String gatherWarehouseCode;

    private String deliveryordercode;

    private String wmsordercode;

    private Integer qty;

    private static final long serialVersionUID = 1L;

    public String getGatherWarehouseCode() {
        return gatherWarehouseCode;
    }

    public void setGatherWarehouseCode(String gatherWarehouseCode) {
        this.gatherWarehouseCode = gatherWarehouseCode == null ? null : gatherWarehouseCode.trim();
    }

    public String getDeliveryordercode() {
        return deliveryordercode;
    }

    public void setDeliveryordercode(String deliveryordercode) {
        this.deliveryordercode = deliveryordercode == null ? null : deliveryordercode.trim();
    }

    public String getWmsordercode() {
        return wmsordercode;
    }

    public void setWmsordercode(String wmsordercode) {
        this.wmsordercode = wmsordercode == null ? null : wmsordercode.trim();
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
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
        DoConfirm other = (DoConfirm) that;
        return (this.getGatherWarehouseCode() == null ? other.getGatherWarehouseCode() == null : this.getGatherWarehouseCode().equals(other.getGatherWarehouseCode()))
            && (this.getDeliveryordercode() == null ? other.getDeliveryordercode() == null : this.getDeliveryordercode().equals(other.getDeliveryordercode()))
            && (this.getWmsordercode() == null ? other.getWmsordercode() == null : this.getWmsordercode().equals(other.getWmsordercode()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGatherWarehouseCode() == null) ? 0 : getGatherWarehouseCode().hashCode());
        result = prime * result + ((getDeliveryordercode() == null) ? 0 : getDeliveryordercode().hashCode());
        result = prime * result + ((getWmsordercode() == null) ? 0 : getWmsordercode().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        return result;
    }
}