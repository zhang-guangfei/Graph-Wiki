package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsPoInvoiceDetail implements Serializable {
    private Long id;

    private Date impDate;

    private String supplierCode;

    private Long invoiceId;

    private String invoiceNo;

    private String orderNo;

    private String modelNo;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal amountAdjust;

    private BigDecimal priceRmb;

    private BigDecimal amountRmb;

    private String productNameEn;

    private BigDecimal weight;

    private String prodCountry;

    private String impType;

    private BigDecimal customsFee;

    private BigDecimal vatFee;

    private BigDecimal transFee;

    private BigDecimal otherFee;

    private BigDecimal priceTotal;

    private BigDecimal amountTotal;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private Short orderType;

    private String currency;

    private Short status;

    private String noncommercial;

    private BigDecimal exciseTax;

    private String overseaInvoiceNo;

    private String productName;

    private String ecode;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getImpDate() {
        return impDate;
    }

    public void setImpDate(Date impDate) {
        this.impDate = impDate;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountAdjust() {
        return amountAdjust;
    }

    public void setAmountAdjust(BigDecimal amountAdjust) {
        this.amountAdjust = amountAdjust;
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

    public String getProductNameEn() {
        return productNameEn;
    }

    public void setProductNameEn(String productNameEn) {
        this.productNameEn = productNameEn == null ? null : productNameEn.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getProdCountry() {
        return prodCountry;
    }

    public void setProdCountry(String prodCountry) {
        this.prodCountry = prodCountry == null ? null : prodCountry.trim();
    }

    public String getImpType() {
        return impType;
    }

    public void setImpType(String impType) {
        this.impType = impType == null ? null : impType.trim();
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

    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
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

    public Short getOrderType() {
        return orderType;
    }

    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getNoncommercial() {
        return noncommercial;
    }

    public void setNoncommercial(String noncommercial) {
        this.noncommercial = noncommercial == null ? null : noncommercial.trim();
    }

    public BigDecimal getExciseTax() {
        return exciseTax;
    }

    public void setExciseTax(BigDecimal exciseTax) {
        this.exciseTax = exciseTax;
    }

    public String getOverseaInvoiceNo() {
        return overseaInvoiceNo;
    }

    public void setOverseaInvoiceNo(String overseaInvoiceNo) {
        this.overseaInvoiceNo = overseaInvoiceNo == null ? null : overseaInvoiceNo.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode == null ? null : ecode.trim();
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
        OpsPoInvoiceDetail other = (OpsPoInvoiceDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getImpDate() == null ? other.getImpDate() == null : this.getImpDate().equals(other.getImpDate()))
            && (this.getSupplierCode() == null ? other.getSupplierCode() == null : this.getSupplierCode().equals(other.getSupplierCode()))
            && (this.getInvoiceId() == null ? other.getInvoiceId() == null : this.getInvoiceId().equals(other.getInvoiceId()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getAmountAdjust() == null ? other.getAmountAdjust() == null : this.getAmountAdjust().equals(other.getAmountAdjust()))
            && (this.getPriceRmb() == null ? other.getPriceRmb() == null : this.getPriceRmb().equals(other.getPriceRmb()))
            && (this.getAmountRmb() == null ? other.getAmountRmb() == null : this.getAmountRmb().equals(other.getAmountRmb()))
            && (this.getProductNameEn() == null ? other.getProductNameEn() == null : this.getProductNameEn().equals(other.getProductNameEn()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getProdCountry() == null ? other.getProdCountry() == null : this.getProdCountry().equals(other.getProdCountry()))
            && (this.getImpType() == null ? other.getImpType() == null : this.getImpType().equals(other.getImpType()))
            && (this.getCustomsFee() == null ? other.getCustomsFee() == null : this.getCustomsFee().equals(other.getCustomsFee()))
            && (this.getVatFee() == null ? other.getVatFee() == null : this.getVatFee().equals(other.getVatFee()))
            && (this.getTransFee() == null ? other.getTransFee() == null : this.getTransFee().equals(other.getTransFee()))
            && (this.getOtherFee() == null ? other.getOtherFee() == null : this.getOtherFee().equals(other.getOtherFee()))
            && (this.getPriceTotal() == null ? other.getPriceTotal() == null : this.getPriceTotal().equals(other.getPriceTotal()))
            && (this.getAmountTotal() == null ? other.getAmountTotal() == null : this.getAmountTotal().equals(other.getAmountTotal()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getNoncommercial() == null ? other.getNoncommercial() == null : this.getNoncommercial().equals(other.getNoncommercial()))
            && (this.getExciseTax() == null ? other.getExciseTax() == null : this.getExciseTax().equals(other.getExciseTax()))
            && (this.getOverseaInvoiceNo() == null ? other.getOverseaInvoiceNo() == null : this.getOverseaInvoiceNo().equals(other.getOverseaInvoiceNo()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getEcode() == null ? other.getEcode() == null : this.getEcode().equals(other.getEcode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getImpDate() == null) ? 0 : getImpDate().hashCode());
        result = prime * result + ((getSupplierCode() == null) ? 0 : getSupplierCode().hashCode());
        result = prime * result + ((getInvoiceId() == null) ? 0 : getInvoiceId().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getAmountAdjust() == null) ? 0 : getAmountAdjust().hashCode());
        result = prime * result + ((getPriceRmb() == null) ? 0 : getPriceRmb().hashCode());
        result = prime * result + ((getAmountRmb() == null) ? 0 : getAmountRmb().hashCode());
        result = prime * result + ((getProductNameEn() == null) ? 0 : getProductNameEn().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getProdCountry() == null) ? 0 : getProdCountry().hashCode());
        result = prime * result + ((getImpType() == null) ? 0 : getImpType().hashCode());
        result = prime * result + ((getCustomsFee() == null) ? 0 : getCustomsFee().hashCode());
        result = prime * result + ((getVatFee() == null) ? 0 : getVatFee().hashCode());
        result = prime * result + ((getTransFee() == null) ? 0 : getTransFee().hashCode());
        result = prime * result + ((getOtherFee() == null) ? 0 : getOtherFee().hashCode());
        result = prime * result + ((getPriceTotal() == null) ? 0 : getPriceTotal().hashCode());
        result = prime * result + ((getAmountTotal() == null) ? 0 : getAmountTotal().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getNoncommercial() == null) ? 0 : getNoncommercial().hashCode());
        result = prime * result + ((getExciseTax() == null) ? 0 : getExciseTax().hashCode());
        result = prime * result + ((getOverseaInvoiceNo() == null) ? 0 : getOverseaInvoiceNo().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getEcode() == null) ? 0 : getEcode().hashCode());
        return result;
    }
}