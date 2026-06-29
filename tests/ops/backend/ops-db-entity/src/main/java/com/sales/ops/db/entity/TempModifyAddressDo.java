package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class TempModifyAddressDo implements Serializable {
    private Long id;

    private String doId;

    private String orderId;

    private String orderItem;

    private Boolean updateDo;

    private Boolean updateWms;

    private Boolean wmsSuccess;

    private String remark;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem == null ? null : orderItem.trim();
    }

    public Boolean getUpdateDo() {
        return updateDo;
    }

    public void setUpdateDo(Boolean updateDo) {
        this.updateDo = updateDo;
    }

    public Boolean getUpdateWms() {
        return updateWms;
    }

    public void setUpdateWms(Boolean updateWms) {
        this.updateWms = updateWms;
    }

    public Boolean getWmsSuccess() {
        return wmsSuccess;
    }

    public void setWmsSuccess(Boolean wmsSuccess) {
        this.wmsSuccess = wmsSuccess;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        TempModifyAddressDo other = (TempModifyAddressDo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getUpdateDo() == null ? other.getUpdateDo() == null : this.getUpdateDo().equals(other.getUpdateDo()))
            && (this.getUpdateWms() == null ? other.getUpdateWms() == null : this.getUpdateWms().equals(other.getUpdateWms()))
            && (this.getWmsSuccess() == null ? other.getWmsSuccess() == null : this.getWmsSuccess().equals(other.getWmsSuccess()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getUpdateDo() == null) ? 0 : getUpdateDo().hashCode());
        result = prime * result + ((getUpdateWms() == null) ? 0 : getUpdateWms().hashCode());
        result = prime * result + ((getWmsSuccess() == null) ? 0 : getWmsSuccess().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }
}