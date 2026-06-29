package com.sales.ops.db.entity;

import java.io.Serializable;

public class OpsTomanuConfig implements Serializable {
    private String supplierid;

    private String requesttype;

    private String remark;

    private static final long serialVersionUID = 1L;

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype == null ? null : requesttype.trim();
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
        OpsTomanuConfig other = (OpsTomanuConfig) that;
        return (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getRequesttype() == null ? other.getRequesttype() == null : this.getRequesttype().equals(other.getRequesttype()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getRequesttype() == null) ? 0 : getRequesttype().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}