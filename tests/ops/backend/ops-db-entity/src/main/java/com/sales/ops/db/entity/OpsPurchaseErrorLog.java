package com.sales.ops.db.entity;

import java.io.Serializable;

public class OpsPurchaseErrorLog implements Serializable {
    private Long id;

    private String orderno;

    private Integer itemno;

    private Integer splititemno;

    private Boolean mergeflag;

    private String sourcetype;

    private String errorcode;

    private String errormsg;

    private String operator;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Integer getItemno() {
        return itemno;
    }

    public void setItemno(Integer itemno) {
        this.itemno = itemno;
    }

    public Integer getSplititemno() {
        return splititemno;
    }

    public void setSplititemno(Integer splititemno) {
        this.splititemno = splititemno;
    }

    public Boolean getMergeflag() {
        return mergeflag;
    }

    public void setMergeflag(Boolean mergeflag) {
        this.mergeflag = mergeflag;
    }

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype == null ? null : sourcetype.trim();
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode == null ? null : errorcode.trim();
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg == null ? null : errormsg.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
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
        OpsPurchaseErrorLog other = (OpsPurchaseErrorLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getItemno() == null ? other.getItemno() == null : this.getItemno().equals(other.getItemno()))
            && (this.getSplititemno() == null ? other.getSplititemno() == null : this.getSplititemno().equals(other.getSplititemno()))
            && (this.getMergeflag() == null ? other.getMergeflag() == null : this.getMergeflag().equals(other.getMergeflag()))
            && (this.getSourcetype() == null ? other.getSourcetype() == null : this.getSourcetype().equals(other.getSourcetype()))
            && (this.getErrorcode() == null ? other.getErrorcode() == null : this.getErrorcode().equals(other.getErrorcode()))
            && (this.getErrormsg() == null ? other.getErrormsg() == null : this.getErrormsg().equals(other.getErrormsg()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getItemno() == null) ? 0 : getItemno().hashCode());
        result = prime * result + ((getSplititemno() == null) ? 0 : getSplititemno().hashCode());
        result = prime * result + ((getMergeflag() == null) ? 0 : getMergeflag().hashCode());
        result = prime * result + ((getSourcetype() == null) ? 0 : getSourcetype().hashCode());
        result = prime * result + ((getErrorcode() == null) ? 0 : getErrorcode().hashCode());
        result = prime * result + ((getErrormsg() == null) ? 0 : getErrormsg().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        return result;
    }
}