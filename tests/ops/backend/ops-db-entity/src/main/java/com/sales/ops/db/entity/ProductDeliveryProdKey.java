package com.sales.ops.db.entity;

import java.io.Serializable;

public class ProductDeliveryProdKey implements Serializable {
    private String modelno;

    private String supplyid;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getSupplyid() {
        return supplyid;
    }

    public void setSupplyid(String supplyid) {
        this.supplyid = supplyid == null ? null : supplyid.trim();
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
        ProductDeliveryProdKey other = (ProductDeliveryProdKey) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getSupplyid() == null ? other.getSupplyid() == null : this.getSupplyid().equals(other.getSupplyid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getSupplyid() == null) ? 0 : getSupplyid().hashCode());
        return result;
    }
}