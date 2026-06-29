package com.sales.ops.db.entity;

import java.io.Serializable;

public class ShikomiTotalKey implements Serializable {
    private String shikomino;

    private String suppliercode;

    private static final long serialVersionUID = 1L;

    public String getShikomino() {
        return shikomino;
    }

    public void setShikomino(String shikomino) {
        this.shikomino = shikomino == null ? null : shikomino.trim();
    }

    public String getSuppliercode() {
        return suppliercode;
    }

    public void setSuppliercode(String suppliercode) {
        this.suppliercode = suppliercode == null ? null : suppliercode.trim();
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
        ShikomiTotalKey other = (ShikomiTotalKey) that;
        return (this.getShikomino() == null ? other.getShikomino() == null : this.getShikomino().equals(other.getShikomino()))
            && (this.getSuppliercode() == null ? other.getSuppliercode() == null : this.getSuppliercode().equals(other.getSuppliercode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getShikomino() == null) ? 0 : getShikomino().hashCode());
        result = prime * result + ((getSuppliercode() == null) ? 0 : getSuppliercode().hashCode());
        return result;
    }
}