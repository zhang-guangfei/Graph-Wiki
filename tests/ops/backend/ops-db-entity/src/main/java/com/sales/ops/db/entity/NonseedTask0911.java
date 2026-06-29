package com.sales.ops.db.entity;

import java.io.Serializable;

public class NonseedTask0911 implements Serializable {
    private String doId;

    private Integer handleStatus;

    private String remark;

    private static final long serialVersionUID = 1L;

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
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
        NonseedTask0911 other = (NonseedTask0911) that;
        return (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()))
            && (this.getHandleStatus() == null ? other.getHandleStatus() == null : this.getHandleStatus().equals(other.getHandleStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        result = prime * result + ((getHandleStatus() == null) ? 0 : getHandleStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}