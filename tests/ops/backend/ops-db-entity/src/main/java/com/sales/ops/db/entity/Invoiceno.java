package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Invoiceno implements Serializable {
    private Long id;

    private Long invoiceId;

    private String invoiceNo;

    private Date impDate;

    private String modelNo;

    private Integer quantity;

    private BigDecimal priceOrg;

    private BigDecimal amountOrg;

    private BigDecimal priceRmb;

    private BigDecimal amountRmb;

    private String currency;

    private String nameEn;

    private BigDecimal weight;

    private String optCode;

    private String supplierCode;

    private String prodCountry;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private String warehouseCode;

    private Short impType;

    private BigDecimal taxTariff;

    private BigDecimal taxValueAdded;

    private String remark;

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

    public Date getImpDate() {
        return impDate;
    }

    public void setImpDate(Date impDate) {
        this.impDate = impDate;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getOptCode() {
        return optCode;
    }

    public void setOptCode(String optCode) {
        this.optCode = optCode == null ? null : optCode.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getProdCountry() {
        return prodCountry;
    }

    public void setProdCountry(String prodCountry) {
        this.prodCountry = prodCountry == null ? null : prodCountry.trim();
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

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public Short getImpType() {
        return impType;
    }

    public void setImpType(Short impType) {
        this.impType = impType;
    }

    public BigDecimal getTaxTariff() {
        return taxTariff;
    }

    public void setTaxTariff(BigDecimal taxTariff) {
        this.taxTariff = taxTariff;
    }

    public BigDecimal getTaxValueAdded() {
        return taxValueAdded;
    }

    public void setTaxValueAdded(BigDecimal taxValueAdded) {
        this.taxValueAdded = taxValueAdded;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}