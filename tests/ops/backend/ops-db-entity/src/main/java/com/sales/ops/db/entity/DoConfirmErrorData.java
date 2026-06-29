package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class DoConfirmErrorData implements Serializable {
    private String outQty;

    private String deliveryNo;

    private Integer handleStatus;

    private String handleMsg;

    private Date insertTime;

    private static final long serialVersionUID = 1L;

    public String getOutQty() {
        return outQty;
    }

    public void setOutQty(String outQty) {
        this.outQty = outQty == null ? null : outQty.trim();
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo == null ? null : deliveryNo.trim();
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getHandleMsg() {
        return handleMsg;
    }

    public void setHandleMsg(String handleMsg) {
        this.handleMsg = handleMsg == null ? null : handleMsg.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
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
        DoConfirmErrorData other = (DoConfirmErrorData) that;
        return (this.getOutQty() == null ? other.getOutQty() == null : this.getOutQty().equals(other.getOutQty()))
            && (this.getDeliveryNo() == null ? other.getDeliveryNo() == null : this.getDeliveryNo().equals(other.getDeliveryNo()))
            && (this.getHandleStatus() == null ? other.getHandleStatus() == null : this.getHandleStatus().equals(other.getHandleStatus()))
            && (this.getHandleMsg() == null ? other.getHandleMsg() == null : this.getHandleMsg().equals(other.getHandleMsg()))
            && (this.getInsertTime() == null ? other.getInsertTime() == null : this.getInsertTime().equals(other.getInsertTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOutQty() == null) ? 0 : getOutQty().hashCode());
        result = prime * result + ((getDeliveryNo() == null) ? 0 : getDeliveryNo().hashCode());
        result = prime * result + ((getHandleStatus() == null) ? 0 : getHandleStatus().hashCode());
        result = prime * result + ((getHandleMsg() == null) ? 0 : getHandleMsg().hashCode());
        result = prime * result + ((getInsertTime() == null) ? 0 : getInsertTime().hashCode());
        return result;
    }
}