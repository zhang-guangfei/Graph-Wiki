package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsHandover implements Serializable {
    private Long id;

    private String invoiceno;

    private Date handDate;

    private String doId;

    private String modelno;

    private Integer qty;

    private String caseno;

    private String barcode;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private Integer delflag;

    private String fromwarehousecode;

    private String towarehousecode;

    private String logisticscode;

    private String expresscode;

    private Long invoiceId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno == null ? null : invoiceno.trim();
    }

    public Date getHandDate() {
        return handDate;
    }

    public void setHandDate(Date handDate) {
        this.handDate = handDate;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno == null ? null : caseno.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
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

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public String getFromwarehousecode() {
        return fromwarehousecode;
    }

    public void setFromwarehousecode(String fromwarehousecode) {
        this.fromwarehousecode = fromwarehousecode == null ? null : fromwarehousecode.trim();
    }

    public String getTowarehousecode() {
        return towarehousecode;
    }

    public void setTowarehousecode(String towarehousecode) {
        this.towarehousecode = towarehousecode == null ? null : towarehousecode.trim();
    }

    public String getLogisticscode() {
        return logisticscode;
    }

    public void setLogisticscode(String logisticscode) {
        this.logisticscode = logisticscode == null ? null : logisticscode.trim();
    }

    public String getExpresscode() {
        return expresscode;
    }

    public void setExpresscode(String expresscode) {
        this.expresscode = expresscode == null ? null : expresscode.trim();
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
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
        OpsHandover other = (OpsHandover) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceno() == null ? other.getInvoiceno() == null : this.getInvoiceno().equals(other.getInvoiceno()))
            && (this.getHandDate() == null ? other.getHandDate() == null : this.getHandDate().equals(other.getHandDate()))
            && (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getCaseno() == null ? other.getCaseno() == null : this.getCaseno().equals(other.getCaseno()))
            && (this.getBarcode() == null ? other.getBarcode() == null : this.getBarcode().equals(other.getBarcode()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getFromwarehousecode() == null ? other.getFromwarehousecode() == null : this.getFromwarehousecode().equals(other.getFromwarehousecode()))
            && (this.getTowarehousecode() == null ? other.getTowarehousecode() == null : this.getTowarehousecode().equals(other.getTowarehousecode()))
            && (this.getLogisticscode() == null ? other.getLogisticscode() == null : this.getLogisticscode().equals(other.getLogisticscode()))
            && (this.getExpresscode() == null ? other.getExpresscode() == null : this.getExpresscode().equals(other.getExpresscode()))
            && (this.getInvoiceId() == null ? other.getInvoiceId() == null : this.getInvoiceId().equals(other.getInvoiceId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceno() == null) ? 0 : getInvoiceno().hashCode());
        result = prime * result + ((getHandDate() == null) ? 0 : getHandDate().hashCode());
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getCaseno() == null) ? 0 : getCaseno().hashCode());
        result = prime * result + ((getBarcode() == null) ? 0 : getBarcode().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getFromwarehousecode() == null) ? 0 : getFromwarehousecode().hashCode());
        result = prime * result + ((getTowarehousecode() == null) ? 0 : getTowarehousecode().hashCode());
        result = prime * result + ((getLogisticscode() == null) ? 0 : getLogisticscode().hashCode());
        result = prime * result + ((getExpresscode() == null) ? 0 : getExpresscode().hashCode());
        result = prime * result + ((getInvoiceId() == null) ? 0 : getInvoiceId().hashCode());
        return result;
    }
}