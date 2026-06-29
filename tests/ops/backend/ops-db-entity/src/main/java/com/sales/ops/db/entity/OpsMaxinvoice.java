package com.sales.ops.db.entity;

import java.io.Serializable;

public class OpsMaxinvoice implements Serializable {
    private String maxcode;

    private String pid;

    private String description;

    private String remark;

    private static final long serialVersionUID = 1L;

    public String getMaxcode() {
        return maxcode;
    }

    public void setMaxcode(String maxcode) {
        this.maxcode = maxcode == null ? null : maxcode.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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
        OpsMaxinvoice other = (OpsMaxinvoice) that;
        return (this.getMaxcode() == null ? other.getMaxcode() == null : this.getMaxcode().equals(other.getMaxcode()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMaxcode() == null) ? 0 : getMaxcode().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}