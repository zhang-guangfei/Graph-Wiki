package com.sales.ops.db.entity;

import java.io.Serializable;

public class InventorySupplierKey implements Serializable {
    private String supplierid;

    private String modelno;

    private static final long serialVersionUID = 1L;

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
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
        InventorySupplierKey other = (InventorySupplierKey) that;
        return (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        return result;
    }
}