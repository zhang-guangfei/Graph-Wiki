package com.sales.ops.db.entity;

import java.io.Serializable;

public class OpsOrderAssignResultKey implements Serializable {
    private String orderNo;

    private Integer orderItem;

    private Integer seqno;

    private static final long serialVersionUID = 1L;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
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
        OpsOrderAssignResultKey other = (OpsOrderAssignResultKey) that;
        return (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getSeqno() == null ? other.getSeqno() == null : this.getSeqno().equals(other.getSeqno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getSeqno() == null) ? 0 : getSeqno().hashCode());
        return result;
    }
}