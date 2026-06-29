package com.sales.ops.db.entity;

import java.io.Serializable;

public class MtWarehousedeptconfig implements Serializable {
    private String deptnotwo;

    private String deptname;

    private String warehouse;

    private String newwarehouse;

    private static final long serialVersionUID = 1L;

    public String getDeptnotwo() {
        return deptnotwo;
    }

    public void setDeptnotwo(String deptnotwo) {
        this.deptnotwo = deptnotwo == null ? null : deptnotwo.trim();
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse == null ? null : warehouse.trim();
    }

    public String getNewwarehouse() {
        return newwarehouse;
    }

    public void setNewwarehouse(String newwarehouse) {
        this.newwarehouse = newwarehouse == null ? null : newwarehouse.trim();
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
        MtWarehousedeptconfig other = (MtWarehousedeptconfig) that;
        return (this.getDeptnotwo() == null ? other.getDeptnotwo() == null : this.getDeptnotwo().equals(other.getDeptnotwo()))
            && (this.getDeptname() == null ? other.getDeptname() == null : this.getDeptname().equals(other.getDeptname()))
            && (this.getWarehouse() == null ? other.getWarehouse() == null : this.getWarehouse().equals(other.getWarehouse()))
            && (this.getNewwarehouse() == null ? other.getNewwarehouse() == null : this.getNewwarehouse().equals(other.getNewwarehouse()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeptnotwo() == null) ? 0 : getDeptnotwo().hashCode());
        result = prime * result + ((getDeptname() == null) ? 0 : getDeptname().hashCode());
        result = prime * result + ((getWarehouse() == null) ? 0 : getWarehouse().hashCode());
        result = prime * result + ((getNewwarehouse() == null) ? 0 : getNewwarehouse().hashCode());
        return result;
    }
}