package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsWarehouseSupplierConfig implements Serializable {
    private Long id;

    private String supplierid;

    private String warehousecode;

    private Integer priority;

    private Integer day;

    private String matchpattern;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getWarehousecode() {
        return warehousecode;
    }

    public void setWarehousecode(String warehousecode) {
        this.warehousecode = warehousecode == null ? null : warehousecode.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getMatchpattern() {
        return matchpattern;
    }

    public void setMatchpattern(String matchpattern) {
        this.matchpattern = matchpattern == null ? null : matchpattern.trim();
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
        OpsWarehouseSupplierConfig other = (OpsWarehouseSupplierConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getWarehousecode() == null ? other.getWarehousecode() == null : this.getWarehousecode().equals(other.getWarehousecode()))
            && (this.getPriority() == null ? other.getPriority() == null : this.getPriority().equals(other.getPriority()))
            && (this.getDay() == null ? other.getDay() == null : this.getDay().equals(other.getDay()))
            && (this.getMatchpattern() == null ? other.getMatchpattern() == null : this.getMatchpattern().equals(other.getMatchpattern()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getWarehousecode() == null) ? 0 : getWarehousecode().hashCode());
        result = prime * result + ((getPriority() == null) ? 0 : getPriority().hashCode());
        result = prime * result + ((getDay() == null) ? 0 : getDay().hashCode());
        result = prime * result + ((getMatchpattern() == null) ? 0 : getMatchpattern().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        return result;
    }
}