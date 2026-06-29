package com.sales.ops.db.entity;

import java.io.Serializable;

public class ProductErrorNew implements Serializable {
    private String modelno;

    private String errColumndesc;

    private String errCode;

    private String tablename;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getErrColumndesc() {
        return errColumndesc;
    }

    public void setErrColumndesc(String errColumndesc) {
        this.errColumndesc = errColumndesc == null ? null : errColumndesc.trim();
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode == null ? null : errCode.trim();
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
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
        ProductErrorNew other = (ProductErrorNew) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getErrColumndesc() == null ? other.getErrColumndesc() == null : this.getErrColumndesc().equals(other.getErrColumndesc()))
            && (this.getErrCode() == null ? other.getErrCode() == null : this.getErrCode().equals(other.getErrCode()))
            && (this.getTablename() == null ? other.getTablename() == null : this.getTablename().equals(other.getTablename()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getErrColumndesc() == null) ? 0 : getErrColumndesc().hashCode());
        result = prime * result + ((getErrCode() == null) ? 0 : getErrCode().hashCode());
        result = prime * result + ((getTablename() == null) ? 0 : getTablename().hashCode());
        return result;
    }
}