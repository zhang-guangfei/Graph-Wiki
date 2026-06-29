package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ImpInvoiceEventLog implements Serializable {
    private Long id;

    private String opType;

    private String requestParam;

    private String returnData;

    private Integer opStatus;

    private Date opStartTime;

    private Date opEndTime;

    private Long duration;

    private String creator;

    private Date createTime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType == null ? null : opType.trim();
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam == null ? null : requestParam.trim();
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData == null ? null : returnData.trim();
    }

    public Integer getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(Integer opStatus) {
        this.opStatus = opStatus;
    }

    public Date getOpStartTime() {
        return opStartTime;
    }

    public void setOpStartTime(Date opStartTime) {
        this.opStartTime = opStartTime;
    }

    public Date getOpEndTime() {
        return opEndTime;
    }

    public void setOpEndTime(Date opEndTime) {
        this.opEndTime = opEndTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        ImpInvoiceEventLog other = (ImpInvoiceEventLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOpType() == null ? other.getOpType() == null : this.getOpType().equals(other.getOpType()))
            && (this.getRequestParam() == null ? other.getRequestParam() == null : this.getRequestParam().equals(other.getRequestParam()))
            && (this.getReturnData() == null ? other.getReturnData() == null : this.getReturnData().equals(other.getReturnData()))
            && (this.getOpStatus() == null ? other.getOpStatus() == null : this.getOpStatus().equals(other.getOpStatus()))
            && (this.getOpStartTime() == null ? other.getOpStartTime() == null : this.getOpStartTime().equals(other.getOpStartTime()))
            && (this.getOpEndTime() == null ? other.getOpEndTime() == null : this.getOpEndTime().equals(other.getOpEndTime()))
            && (this.getDuration() == null ? other.getDuration() == null : this.getDuration().equals(other.getDuration()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOpType() == null) ? 0 : getOpType().hashCode());
        result = prime * result + ((getRequestParam() == null) ? 0 : getRequestParam().hashCode());
        result = prime * result + ((getReturnData() == null) ? 0 : getReturnData().hashCode());
        result = prime * result + ((getOpStatus() == null) ? 0 : getOpStatus().hashCode());
        result = prime * result + ((getOpStartTime() == null) ? 0 : getOpStartTime().hashCode());
        result = prime * result + ((getOpEndTime() == null) ? 0 : getOpEndTime().hashCode());
        result = prime * result + ((getDuration() == null) ? 0 : getDuration().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}