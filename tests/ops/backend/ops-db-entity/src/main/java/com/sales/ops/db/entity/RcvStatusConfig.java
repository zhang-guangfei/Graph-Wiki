package com.sales.ops.db.entity;

import java.io.Serializable;

public class RcvStatusConfig implements Serializable {
    private Byte fromStatus;

    private Byte targetStatus;

    private Boolean enable;

    private String fromDesc;

    private String targetDesc;

    private static final long serialVersionUID = 1L;

    public Byte getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(Byte fromStatus) {
        this.fromStatus = fromStatus;
    }

    public Byte getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(Byte targetStatus) {
        this.targetStatus = targetStatus;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getFromDesc() {
        return fromDesc;
    }

    public void setFromDesc(String fromDesc) {
        this.fromDesc = fromDesc == null ? null : fromDesc.trim();
    }

    public String getTargetDesc() {
        return targetDesc;
    }

    public void setTargetDesc(String targetDesc) {
        this.targetDesc = targetDesc == null ? null : targetDesc.trim();
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
        RcvStatusConfig other = (RcvStatusConfig) that;
        return (this.getFromStatus() == null ? other.getFromStatus() == null : this.getFromStatus().equals(other.getFromStatus()))
            && (this.getTargetStatus() == null ? other.getTargetStatus() == null : this.getTargetStatus().equals(other.getTargetStatus()))
            && (this.getEnable() == null ? other.getEnable() == null : this.getEnable().equals(other.getEnable()))
            && (this.getFromDesc() == null ? other.getFromDesc() == null : this.getFromDesc().equals(other.getFromDesc()))
            && (this.getTargetDesc() == null ? other.getTargetDesc() == null : this.getTargetDesc().equals(other.getTargetDesc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFromStatus() == null) ? 0 : getFromStatus().hashCode());
        result = prime * result + ((getTargetStatus() == null) ? 0 : getTargetStatus().hashCode());
        result = prime * result + ((getEnable() == null) ? 0 : getEnable().hashCode());
        result = prime * result + ((getFromDesc() == null) ? 0 : getFromDesc().hashCode());
        result = prime * result + ((getTargetDesc() == null) ? 0 : getTargetDesc().hashCode());
        return result;
    }
}