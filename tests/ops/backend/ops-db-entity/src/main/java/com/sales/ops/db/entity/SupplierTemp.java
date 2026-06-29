package com.sales.ops.db.entity;

import java.io.Serializable;

public class SupplierTemp implements Serializable {
    private String newCode;

    private String oldCode;

    private static final long serialVersionUID = 1L;

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode == null ? null : newCode.trim();
    }

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode == null ? null : oldCode.trim();
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
        SupplierTemp other = (SupplierTemp) that;
        return (this.getNewCode() == null ? other.getNewCode() == null : this.getNewCode().equals(other.getNewCode()))
            && (this.getOldCode() == null ? other.getOldCode() == null : this.getOldCode().equals(other.getOldCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getNewCode() == null) ? 0 : getNewCode().hashCode());
        result = prime * result + ((getOldCode() == null) ? 0 : getOldCode().hashCode());
        return result;
    }
}