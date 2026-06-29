package com.sales.ops.db.entity;

import java.io.Serializable;

public class TmpImpInvoiceError implements Serializable {
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

    private String poWarehouseCode;

    private String poSupplierCode;

    private static final long serialVersionUID = 1L;

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

    public String getPoWarehouseCode() {
        return poWarehouseCode;
    }

    public void setPoWarehouseCode(String poWarehouseCode) {
        this.poWarehouseCode = poWarehouseCode == null ? null : poWarehouseCode.trim();
    }

    public String getPoSupplierCode() {
        return poSupplierCode;
    }

    public void setPoSupplierCode(String poSupplierCode) {
        this.poSupplierCode = poSupplierCode == null ? null : poSupplierCode.trim();
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
        TmpImpInvoiceError other = (TmpImpInvoiceError) that;
        return (this.getInvoiceId() == null ? other.getInvoiceId() == null : this.getInvoiceId().equals(other.getInvoiceId()))
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
            && (this.getPoWarehouseCode() == null ? other.getPoWarehouseCode() == null : this.getPoWarehouseCode().equals(other.getPoWarehouseCode()))
            && (this.getPoSupplierCode() == null ? other.getPoSupplierCode() == null : this.getPoSupplierCode().equals(other.getPoSupplierCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        result = prime * result + ((getPoWarehouseCode() == null) ? 0 : getPoWarehouseCode().hashCode());
        result = prime * result + ((getPoSupplierCode() == null) ? 0 : getPoSupplierCode().hashCode());
        return result;
    }
}