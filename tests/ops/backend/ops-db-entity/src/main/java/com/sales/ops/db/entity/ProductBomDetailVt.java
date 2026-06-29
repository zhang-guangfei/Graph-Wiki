package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductBomDetailVt extends ProductBomDetailVtKey implements Serializable {
    private Long id;

    private Integer quantity;

    private Boolean isbaseboard;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsbaseboard() {
        return isbaseboard;
    }

    public void setIsbaseboard(Boolean isbaseboard) {
        this.isbaseboard = isbaseboard;
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
        ProductBomDetailVt other = (ProductBomDetailVt) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getSubmodelno() == null ? other.getSubmodelno() == null : this.getSubmodelno().equals(other.getSubmodelno()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getIsbaseboard() == null ? other.getIsbaseboard() == null : this.getIsbaseboard().equals(other.getIsbaseboard()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getSubmodelno() == null) ? 0 : getSubmodelno().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getIsbaseboard() == null) ? 0 : getIsbaseboard().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        return result;
    }
}