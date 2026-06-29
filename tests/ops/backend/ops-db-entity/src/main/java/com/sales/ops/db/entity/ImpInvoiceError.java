package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ImpInvoiceError implements Serializable {
    private Long id;

    private Long invoiceId;

    private String invoiceNo;

    private String pono;

    private Integer poitemno;

    private String orderNo;

    private String modelNo;

    private Integer qty;

    private String poModelNo;

    private Integer errorType;

    private String errorText;

    private Integer poQty;

    private Integer packQty;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Boolean ignoreError;

    private String ignorePsn;

    private Date ignoreTime;

    private String ignoreReason;

    private Short status;

    private String poWarehouseCode;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono == null ? null : pono.trim();
    }

    public Integer getPoitemno() {
        return poitemno;
    }

    public void setPoitemno(Integer poitemno) {
        this.poitemno = poitemno;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getPoModelNo() {
        return poModelNo;
    }

    public void setPoModelNo(String poModelNo) {
        this.poModelNo = poModelNo == null ? null : poModelNo.trim();
    }

    public Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText == null ? null : errorText.trim();
    }

    public Integer getPoQty() {
        return poQty;
    }

    public void setPoQty(Integer poQty) {
        this.poQty = poQty;
    }

    public Integer getPackQty() {
        return packQty;
    }

    public void setPackQty(Integer packQty) {
        this.packQty = packQty;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIgnoreError() {
        return ignoreError;
    }

    public void setIgnoreError(Boolean ignoreError) {
        this.ignoreError = ignoreError;
    }

    public String getIgnorePsn() {
        return ignorePsn;
    }

    public void setIgnorePsn(String ignorePsn) {
        this.ignorePsn = ignorePsn == null ? null : ignorePsn.trim();
    }

    public Date getIgnoreTime() {
        return ignoreTime;
    }

    public void setIgnoreTime(Date ignoreTime) {
        this.ignoreTime = ignoreTime;
    }

    public String getIgnoreReason() {
        return ignoreReason;
    }

    public void setIgnoreReason(String ignoreReason) {
        this.ignoreReason = ignoreReason == null ? null : ignoreReason.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getPoWarehouseCode() {
        return poWarehouseCode;
    }

    public void setPoWarehouseCode(String poWarehouseCode) {
        this.poWarehouseCode = poWarehouseCode == null ? null : poWarehouseCode.trim();
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
        ImpInvoiceError other = (ImpInvoiceError) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceId() == null ? other.getInvoiceId() == null : this.getInvoiceId().equals(other.getInvoiceId()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getPono() == null ? other.getPono() == null : this.getPono().equals(other.getPono()))
            && (this.getPoitemno() == null ? other.getPoitemno() == null : this.getPoitemno().equals(other.getPoitemno()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQty() == null ? other.getQty() == null : this.getQty().equals(other.getQty()))
            && (this.getPoModelNo() == null ? other.getPoModelNo() == null : this.getPoModelNo().equals(other.getPoModelNo()))
            && (this.getErrorType() == null ? other.getErrorType() == null : this.getErrorType().equals(other.getErrorType()))
            && (this.getErrorText() == null ? other.getErrorText() == null : this.getErrorText().equals(other.getErrorText()))
            && (this.getPoQty() == null ? other.getPoQty() == null : this.getPoQty().equals(other.getPoQty()))
            && (this.getPackQty() == null ? other.getPackQty() == null : this.getPackQty().equals(other.getPackQty()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIgnoreError() == null ? other.getIgnoreError() == null : this.getIgnoreError().equals(other.getIgnoreError()))
            && (this.getIgnorePsn() == null ? other.getIgnorePsn() == null : this.getIgnorePsn().equals(other.getIgnorePsn()))
            && (this.getIgnoreTime() == null ? other.getIgnoreTime() == null : this.getIgnoreTime().equals(other.getIgnoreTime()))
            && (this.getIgnoreReason() == null ? other.getIgnoreReason() == null : this.getIgnoreReason().equals(other.getIgnoreReason()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPoWarehouseCode() == null ? other.getPoWarehouseCode() == null : this.getPoWarehouseCode().equals(other.getPoWarehouseCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceId() == null) ? 0 : getInvoiceId().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getPono() == null) ? 0 : getPono().hashCode());
        result = prime * result + ((getPoitemno() == null) ? 0 : getPoitemno().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQty() == null) ? 0 : getQty().hashCode());
        result = prime * result + ((getPoModelNo() == null) ? 0 : getPoModelNo().hashCode());
        result = prime * result + ((getErrorType() == null) ? 0 : getErrorType().hashCode());
        result = prime * result + ((getErrorText() == null) ? 0 : getErrorText().hashCode());
        result = prime * result + ((getPoQty() == null) ? 0 : getPoQty().hashCode());
        result = prime * result + ((getPackQty() == null) ? 0 : getPackQty().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIgnoreError() == null) ? 0 : getIgnoreError().hashCode());
        result = prime * result + ((getIgnorePsn() == null) ? 0 : getIgnorePsn().hashCode());
        result = prime * result + ((getIgnoreTime() == null) ? 0 : getIgnoreTime().hashCode());
        result = prime * result + ((getIgnoreReason() == null) ? 0 : getIgnoreReason().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPoWarehouseCode() == null) ? 0 : getPoWarehouseCode().hashCode());
        return result;
    }
}