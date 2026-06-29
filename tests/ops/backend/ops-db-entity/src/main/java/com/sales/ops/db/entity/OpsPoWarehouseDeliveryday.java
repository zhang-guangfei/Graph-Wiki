package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPoWarehouseDeliveryday extends OpsPoWarehouseDeliverydayKey implements Serializable {
    private Integer deliveryday;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public Integer getDeliveryday() {
        return deliveryday;
    }

    public void setDeliveryday(Integer deliveryday) {
        this.deliveryday = deliveryday;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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
        OpsPoWarehouseDeliveryday other = (OpsPoWarehouseDeliveryday) that;
        return (this.getFromwarehouse() == null ? other.getFromwarehouse() == null : this.getFromwarehouse().equals(other.getFromwarehouse()))
            && (this.getTowarehouse() == null ? other.getTowarehouse() == null : this.getTowarehouse().equals(other.getTowarehouse()))
            && (this.getDeliveryday() == null ? other.getDeliveryday() == null : this.getDeliveryday().equals(other.getDeliveryday()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFromwarehouse() == null) ? 0 : getFromwarehouse().hashCode());
        result = prime * result + ((getTowarehouse() == null) ? 0 : getTowarehouse().hashCode());
        result = prime * result + ((getDeliveryday() == null) ? 0 : getDeliveryday().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        return result;
    }
}