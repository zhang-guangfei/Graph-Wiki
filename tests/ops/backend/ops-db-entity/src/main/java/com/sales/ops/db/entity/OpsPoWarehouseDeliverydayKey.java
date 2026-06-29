package com.sales.ops.db.entity;

import java.io.Serializable;

public class OpsPoWarehouseDeliverydayKey implements Serializable {
    private String fromwarehouse;

    private String towarehouse;

    private static final long serialVersionUID = 1L;

    public String getFromwarehouse() {
        return fromwarehouse;
    }

    public void setFromwarehouse(String fromwarehouse) {
        this.fromwarehouse = fromwarehouse == null ? null : fromwarehouse.trim();
    }

    public String getTowarehouse() {
        return towarehouse;
    }

    public void setTowarehouse(String towarehouse) {
        this.towarehouse = towarehouse == null ? null : towarehouse.trim();
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
        OpsPoWarehouseDeliverydayKey other = (OpsPoWarehouseDeliverydayKey) that;
        return (this.getFromwarehouse() == null ? other.getFromwarehouse() == null : this.getFromwarehouse().equals(other.getFromwarehouse()))
            && (this.getTowarehouse() == null ? other.getTowarehouse() == null : this.getTowarehouse().equals(other.getTowarehouse()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFromwarehouse() == null) ? 0 : getFromwarehouse().hashCode());
        result = prime * result + ((getTowarehouse() == null) ? 0 : getTowarehouse().hashCode());
        return result;
    }
}