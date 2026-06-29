package com.sales.ops.db.entity;

import java.io.Serializable;

public class OrderStatedetailKey implements Serializable {
    private String orderNo;

    private Integer dateType;

    private static final long serialVersionUID = 1L;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
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
        OrderStatedetailKey other = (OrderStatedetailKey) that;
        return (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getDateType() == null ? other.getDateType() == null : this.getDateType().equals(other.getDateType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getDateType() == null) ? 0 : getDateType().hashCode());
        return result;
    }
}