package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class TempMergeInv implements Serializable {
    private Long id;

    private Long invoiceid;

    private String invoiceno;

    private String orderno;

    private Integer orderitem;

    private Integer orderitemsplit;

    private Integer preQty;

    private Integer invoiceQty;

    private Integer poQty;

    private Date updateTime;

    private Integer handleStatus;

    private String remark;

    private String doId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(Long invoiceid) {
        this.invoiceid = invoiceid;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Integer getOrderitem() {
        return orderitem;
    }

    public void setOrderitem(Integer orderitem) {
        this.orderitem = orderitem;
    }

    public Integer getOrderitemsplit() {
        return orderitemsplit;
    }

    public void setOrderitemsplit(Integer orderitemsplit) {
        this.orderitemsplit = orderitemsplit;
    }

    public Integer getPreQty() {
        return preQty;
    }

    public void setPreQty(Integer preQty) {
        this.preQty = preQty;
    }

    public Integer getInvoiceQty() {
        return invoiceQty;
    }

    public void setInvoiceQty(Integer invoiceQty) {
        this.invoiceQty = invoiceQty;
    }

    public Integer getPoQty() {
        return poQty;
    }

    public void setPoQty(Integer poQty) {
        this.poQty = poQty;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
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
        TempMergeInv other = (TempMergeInv) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceid() == null ? other.getInvoiceid() == null : this.getInvoiceid().equals(other.getInvoiceid()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getOrderno() == null ? other.getOrderno() == null : this.getOrderno().equals(other.getOrderno()))
            && (this.getOrderitem() == null ? other.getOrderitem() == null : this.getOrderitem().equals(other.getOrderitem()))
            && (this.getOrderitemsplit() == null ? other.getOrderitemsplit() == null : this.getOrderitemsplit().equals(other.getOrderitemsplit()))
            && (this.getPreQty() == null ? other.getPreQty() == null : this.getPreQty().equals(other.getPreQty()))
            && (this.getInvoiceQty() == null ? other.getInvoiceQty() == null : this.getInvoiceQty().equals(other.getInvoiceQty()))
            && (this.getPoQty() == null ? other.getPoQty() == null : this.getPoQty().equals(other.getPoQty()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getHandleStatus() == null ? other.getHandleStatus() == null : this.getHandleStatus().equals(other.getHandleStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceid() == null) ? 0 : getInvoiceid().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getOrderno() == null) ? 0 : getOrderno().hashCode());
        result = prime * result + ((getOrderitem() == null) ? 0 : getOrderitem().hashCode());
        result = prime * result + ((getOrderitemsplit() == null) ? 0 : getOrderitemsplit().hashCode());
        result = prime * result + ((getPreQty() == null) ? 0 : getPreQty().hashCode());
        result = prime * result + ((getInvoiceQty() == null) ? 0 : getInvoiceQty().hashCode());
        result = prime * result + ((getPoQty() == null) ? 0 : getPoQty().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getHandleStatus() == null) ? 0 : getHandleStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        return result;
    }
}