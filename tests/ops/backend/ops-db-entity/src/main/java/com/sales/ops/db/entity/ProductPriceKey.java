package com.sales.ops.db.entity;

import java.io.Serializable;

public class ProductPriceKey implements Serializable {
    private String modelno;

    private Integer minquantity;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getMinquantity() {
        return minquantity;
    }

    public void setMinquantity(Integer minquantity) {
        this.minquantity = minquantity;
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
        ProductPriceKey other = (ProductPriceKey) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getMinquantity() == null ? other.getMinquantity() == null : this.getMinquantity().equals(other.getMinquantity()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getMinquantity() == null) ? 0 : getMinquantity().hashCode());
        return result;
    }
}