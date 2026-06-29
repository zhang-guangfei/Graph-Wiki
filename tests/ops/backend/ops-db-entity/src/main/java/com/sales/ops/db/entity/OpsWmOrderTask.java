package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsWmOrderTask implements Serializable {
    private Long id;

    private String wmOrderId;

    private String wmOrderType;

    private String taskType;

    private Integer flag;

    private Integer retryNum;

    private String msg;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private Integer priority;

    private Integer delflag;

    private Integer changeFlag;

    private Date sentSuccessTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWmOrderId() {
        return wmOrderId;
    }

    public void setWmOrderId(String wmOrderId) {
        this.wmOrderId = wmOrderId == null ? null : wmOrderId.trim();
    }

    public String getWmOrderType() {
        return wmOrderType;
    }

    public void setWmOrderType(String wmOrderType) {
        this.wmOrderType = wmOrderType == null ? null : wmOrderType.trim();
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType == null ? null : taskType.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getRetryNum() {
        return retryNum;
    }

    public void setRetryNum(Integer retryNum) {
        this.retryNum = retryNum;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Integer getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(Integer changeFlag) {
        this.changeFlag = changeFlag;
    }

    public Date getSentSuccessTime() {
        return sentSuccessTime;
    }

    public void setSentSuccessTime(Date sentSuccessTime) {
        this.sentSuccessTime = sentSuccessTime;
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
        OpsWmOrderTask other = (OpsWmOrderTask) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getWmOrderId() == null ? other.getWmOrderId() == null : this.getWmOrderId().equals(other.getWmOrderId()))
            && (this.getWmOrderType() == null ? other.getWmOrderType() == null : this.getWmOrderType().equals(other.getWmOrderType()))
            && (this.getTaskType() == null ? other.getTaskType() == null : this.getTaskType().equals(other.getTaskType()))
            && (this.getFlag() == null ? other.getFlag() == null : this.getFlag().equals(other.getFlag()))
            && (this.getRetryNum() == null ? other.getRetryNum() == null : this.getRetryNum().equals(other.getRetryNum()))
            && (this.getMsg() == null ? other.getMsg() == null : this.getMsg().equals(other.getMsg()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getPriority() == null ? other.getPriority() == null : this.getPriority().equals(other.getPriority()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getChangeFlag() == null ? other.getChangeFlag() == null : this.getChangeFlag().equals(other.getChangeFlag()))
            && (this.getSentSuccessTime() == null ? other.getSentSuccessTime() == null : this.getSentSuccessTime().equals(other.getSentSuccessTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getWmOrderId() == null) ? 0 : getWmOrderId().hashCode());
        result = prime * result + ((getWmOrderType() == null) ? 0 : getWmOrderType().hashCode());
        result = prime * result + ((getTaskType() == null) ? 0 : getTaskType().hashCode());
        result = prime * result + ((getFlag() == null) ? 0 : getFlag().hashCode());
        result = prime * result + ((getRetryNum() == null) ? 0 : getRetryNum().hashCode());
        result = prime * result + ((getMsg() == null) ? 0 : getMsg().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getPriority() == null) ? 0 : getPriority().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getChangeFlag() == null) ? 0 : getChangeFlag().hashCode());
        result = prime * result + ((getSentSuccessTime() == null) ? 0 : getSentSuccessTime().hashCode());
        return result;
    }
}