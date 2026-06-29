package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsOrderCancelItem implements Serializable {
    private Long id;

    private Long cancelId;

    private String wlOrderId;

    private String wlType;

    private String result;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCancelId() {
        return cancelId;
    }

    public void setCancelId(Long cancelId) {
        this.cancelId = cancelId;
    }

    public String getWlOrderId() {
        return wlOrderId;
    }

    public void setWlOrderId(String wlOrderId) {
        this.wlOrderId = wlOrderId == null ? null : wlOrderId.trim();
    }

    public String getWlType() {
        return wlType;
    }

    public void setWlType(String wlType) {
        this.wlType = wlType == null ? null : wlType.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
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
        OpsOrderCancelItem other = (OpsOrderCancelItem) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCancelId() == null ? other.getCancelId() == null : this.getCancelId().equals(other.getCancelId()))
            && (this.getWlOrderId() == null ? other.getWlOrderId() == null : this.getWlOrderId().equals(other.getWlOrderId()))
            && (this.getWlType() == null ? other.getWlType() == null : this.getWlType().equals(other.getWlType()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCancelId() == null) ? 0 : getCancelId().hashCode());
        result = prime * result + ((getWlOrderId() == null) ? 0 : getWlOrderId().hashCode());
        result = prime * result + ((getWlType() == null) ? 0 : getWlType().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        return result;
    }
}