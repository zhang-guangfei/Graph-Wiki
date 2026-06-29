package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class PurchaseReplyPushJob implements Serializable {
    private Long id;

    private String businessCode;

    private String orderFno;

    private String parameter;

    private Integer handleStatus;

    private Date createTime;

    private Date handleTime;

    private String result;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode == null ? null : businessCode.trim();
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno == null ? null : orderFno.trim();
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
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
        PurchaseReplyPushJob other = (PurchaseReplyPushJob) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBusinessCode() == null ? other.getBusinessCode() == null : this.getBusinessCode().equals(other.getBusinessCode()))
            && (this.getOrderFno() == null ? other.getOrderFno() == null : this.getOrderFno().equals(other.getOrderFno()))
            && (this.getParameter() == null ? other.getParameter() == null : this.getParameter().equals(other.getParameter()))
            && (this.getHandleStatus() == null ? other.getHandleStatus() == null : this.getHandleStatus().equals(other.getHandleStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getHandleTime() == null ? other.getHandleTime() == null : this.getHandleTime().equals(other.getHandleTime()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBusinessCode() == null) ? 0 : getBusinessCode().hashCode());
        result = prime * result + ((getOrderFno() == null) ? 0 : getOrderFno().hashCode());
        result = prime * result + ((getParameter() == null) ? 0 : getParameter().hashCode());
        result = prime * result + ((getHandleStatus() == null) ? 0 : getHandleStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getHandleTime() == null) ? 0 : getHandleTime().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        return result;
    }
}