package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Shoplist implements Serializable {
    private Long id;

    private Long invoiceId;

    private String invoiceNoOrg;

    private String supplierNo;

    private Date impDate;

    private String orderNo;

    private String modelNo;

    private Integer quantity;

    private BigDecimal priceOrg;

    private BigDecimal amountOrg;

    private String currency;

    private BigDecimal priceRmb;

    private BigDecimal amountRmb;

    private String prodCountry;

    private BigDecimal customsFee;

    private BigDecimal vatFee;

    private BigDecimal transFee;

    private BigDecimal otherFee;

    private String impType;

    private Integer orderType;

    private BigDecimal amountTotal;

    private BigDecimal priceTotal;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

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

    public String getInvoiceNoOrg() {
        return invoiceNoOrg;
    }

    public void setInvoiceNoOrg(String invoiceNoOrg) {
        this.invoiceNoOrg = invoiceNoOrg == null ? null : invoiceNoOrg.trim();
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo == null ? null : supplierNo.trim();
    }

    public Date getImpDate() {
        return impDate;
    }

    public void setImpDate(Date impDate) {
        this.impDate = impDate;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceOrg() {
        return priceOrg;
    }

    public void setPriceOrg(BigDecimal priceOrg) {
        this.priceOrg = priceOrg;
    }

    public BigDecimal getAmountOrg() {
        return amountOrg;
    }

    public void setAmountOrg(BigDecimal amountOrg) {
        this.amountOrg = amountOrg;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public BigDecimal getPriceRmb() {
        return priceRmb;
    }

    public void setPriceRmb(BigDecimal priceRmb) {
        this.priceRmb = priceRmb;
    }

    public BigDecimal getAmountRmb() {
        return amountRmb;
    }

    public void setAmountRmb(BigDecimal amountRmb) {
        this.amountRmb = amountRmb;
    }

    public String getProdCountry() {
        return prodCountry;
    }

    public void setProdCountry(String prodCountry) {
        this.prodCountry = prodCountry == null ? null : prodCountry.trim();
    }

    public BigDecimal getCustomsFee() {
        return customsFee;
    }

    public void setCustomsFee(BigDecimal customsFee) {
        this.customsFee = customsFee;
    }

    public BigDecimal getVatFee() {
        return vatFee;
    }

    public void setVatFee(BigDecimal vatFee) {
        this.vatFee = vatFee;
    }

    public BigDecimal getTransFee() {
        return transFee;
    }

    public void setTransFee(BigDecimal transFee) {
        this.transFee = transFee;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public String getImpType() {
        return impType;
    }

    public void setImpType(String impType) {
        this.impType = impType == null ? null : impType.trim();
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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
        Shoplist other = (Shoplist) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceId() == null ? other.getInvoiceId() == null : this.getInvoiceId().equals(other.getInvoiceId()))
            && (this.getInvoiceNoOrg() == null ? other.getInvoiceNoOrg() == null : this.getInvoiceNoOrg().equals(other.getInvoiceNoOrg()))
            && (this.getSupplierNo() == null ? other.getSupplierNo() == null : this.getSupplierNo().equals(other.getSupplierNo()))
            && (this.getImpDate() == null ? other.getImpDate() == null : this.getImpDate().equals(other.getImpDate()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPriceOrg() == null ? other.getPriceOrg() == null : this.getPriceOrg().equals(other.getPriceOrg()))
            && (this.getAmountOrg() == null ? other.getAmountOrg() == null : this.getAmountOrg().equals(other.getAmountOrg()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getPriceRmb() == null ? other.getPriceRmb() == null : this.getPriceRmb().equals(other.getPriceRmb()))
            && (this.getAmountRmb() == null ? other.getAmountRmb() == null : this.getAmountRmb().equals(other.getAmountRmb()))
            && (this.getProdCountry() == null ? other.getProdCountry() == null : this.getProdCountry().equals(other.getProdCountry()))
            && (this.getCustomsFee() == null ? other.getCustomsFee() == null : this.getCustomsFee().equals(other.getCustomsFee()))
            && (this.getVatFee() == null ? other.getVatFee() == null : this.getVatFee().equals(other.getVatFee()))
            && (this.getTransFee() == null ? other.getTransFee() == null : this.getTransFee().equals(other.getTransFee()))
            && (this.getOtherFee() == null ? other.getOtherFee() == null : this.getOtherFee().equals(other.getOtherFee()))
            && (this.getImpType() == null ? other.getImpType() == null : this.getImpType().equals(other.getImpType()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getAmountTotal() == null ? other.getAmountTotal() == null : this.getAmountTotal().equals(other.getAmountTotal()))
            && (this.getPriceTotal() == null ? other.getPriceTotal() == null : this.getPriceTotal().equals(other.getPriceTotal()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceId() == null) ? 0 : getInvoiceId().hashCode());
        result = prime * result + ((getInvoiceNoOrg() == null) ? 0 : getInvoiceNoOrg().hashCode());
        result = prime * result + ((getSupplierNo() == null) ? 0 : getSupplierNo().hashCode());
        result = prime * result + ((getImpDate() == null) ? 0 : getImpDate().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPriceOrg() == null) ? 0 : getPriceOrg().hashCode());
        result = prime * result + ((getAmountOrg() == null) ? 0 : getAmountOrg().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getPriceRmb() == null) ? 0 : getPriceRmb().hashCode());
        result = prime * result + ((getAmountRmb() == null) ? 0 : getAmountRmb().hashCode());
        result = prime * result + ((getProdCountry() == null) ? 0 : getProdCountry().hashCode());
        result = prime * result + ((getCustomsFee() == null) ? 0 : getCustomsFee().hashCode());
        result = prime * result + ((getVatFee() == null) ? 0 : getVatFee().hashCode());
        result = prime * result + ((getTransFee() == null) ? 0 : getTransFee().hashCode());
        result = prime * result + ((getOtherFee() == null) ? 0 : getOtherFee().hashCode());
        result = prime * result + ((getImpType() == null) ? 0 : getImpType().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getAmountTotal() == null) ? 0 : getAmountTotal().hashCode());
        result = prime * result + ((getPriceTotal() == null) ? 0 : getPriceTotal().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        return result;
    }
}