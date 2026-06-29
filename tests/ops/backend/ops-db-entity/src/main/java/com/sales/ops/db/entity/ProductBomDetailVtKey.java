package com.sales.ops.db.entity;

import java.io.Serializable;

public class ProductBomDetailVtKey implements Serializable {
    private String modelno;

    private String submodelno;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getSubmodelno() {
        return submodelno;
    }

    public void setSubmodelno(String submodelno) {
        this.submodelno = submodelno == null ? null : submodelno.trim();
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
        ProductBomDetailVtKey other = (ProductBomDetailVtKey) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getSubmodelno() == null ? other.getSubmodelno() == null : this.getSubmodelno().equals(other.getSubmodelno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getSubmodelno() == null) ? 0 : getSubmodelno().hashCode());
        return result;
    }
}