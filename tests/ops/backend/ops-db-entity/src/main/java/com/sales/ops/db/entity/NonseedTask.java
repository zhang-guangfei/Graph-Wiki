package com.sales.ops.db.entity;

import java.io.Serializable;

public class NonseedTask implements Serializable {
    private String doId;

    private Integer handleStatus;

    private String remark;

    private Integer flag;

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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
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
        NonseedTask other = (NonseedTask) that;
        return (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()))
            && (this.getHandleStatus() == null ? other.getHandleStatus() == null : this.getHandleStatus().equals(other.getHandleStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getFlag() == null ? other.getFlag() == null : this.getFlag().equals(other.getFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        result = prime * result + ((getHandleStatus() == null) ? 0 : getHandleStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getFlag() == null) ? 0 : getFlag().hashCode());
        return result;
    }
}