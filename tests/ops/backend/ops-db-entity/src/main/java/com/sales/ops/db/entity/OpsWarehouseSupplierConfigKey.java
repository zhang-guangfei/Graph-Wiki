package com.sales.ops.db.entity;

import java.io.Serializable;

public class OpsWarehouseSupplierConfigKey implements Serializable {
    private String warehousecode;

    private String supplierid;

    private static final long serialVersionUID = 1L;

    public String getWarehousecode() {
        return warehousecode;
    }

    public void setWarehousecode(String warehousecode) {
        this.warehousecode = warehousecode == null ? null : warehousecode.trim();
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
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
        OpsWarehouseSupplierConfigKey other = (OpsWarehouseSupplierConfigKey) that;
        return (this.getWarehousecode() == null ? other.getWarehousecode() == null : this.getWarehousecode().equals(other.getWarehousecode()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWarehousecode() == null) ? 0 : getWarehousecode().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        return result;
    }
}