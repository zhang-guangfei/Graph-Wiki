package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsPoInvoice implements Serializable {
    private Long id;

    private Long invoiceId;

    private String invoiceNo;

    private Date impDate;

    private Integer status;

    private String supplierCode;

    private Date invoiceDate;

    private String currencyCode;

    private BigDecimal exchangeRate;

    private BigDecimal amount;

    private BigDecimal amountAdjust;

    private BigDecimal amountRmb;

    private Date customsDate;

    private Date shipDate;

    private String declarationNo;

    private BigDecimal grossWeight;

    private BigDecimal weight;

    private BigDecimal customsFee;

    private BigDecimal vatFee;

    private BigDecimal transFee;

    private BigDecimal otherFee;

    private String arrivedWarehouseCode;

    private String receiveWarehouseCode;

    private Integer totalQty;

    private Integer boxQty;

    private Integer orderQty;

    private Date receiveTime;

    private Date costTime;

    private Date createTime;

    private Date updateTime;

    private String updateUser;

    private String createUser;

    private BigDecimal amountTotal;

    private BigDecimal exciseTax;

    private String bargainType;

    private Integer payDay;

    private Integer invoiceType;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
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

    public BigDecimal getAmountRmb() {
        return amountRmb;
    }

    public void setAmountRmb(BigDecimal amountRmb) {
        this.amountRmb = amountRmb;
    }

    public Date getCustomsDate() {
        return customsDate;
    }

    public void setCustomsDate(Date customsDate) {
        this.customsDate = customsDate;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getDeclarationNo() {
        return declarationNo;
    }

    public void setDeclarationNo(String declarationNo) {
        this.declarationNo = declarationNo == null ? null : declarationNo.trim();
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
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

    public String getArrivedWarehouseCode() {
        return arrivedWarehouseCode;
    }

    public void setArrivedWarehouseCode(String arrivedWarehouseCode) {
        this.arrivedWarehouseCode = arrivedWarehouseCode == null ? null : arrivedWarehouseCode.trim();
    }

    public String getReceiveWarehouseCode() {
        return receiveWarehouseCode;
    }

    public void setReceiveWarehouseCode(String receiveWarehouseCode) {
        this.receiveWarehouseCode = receiveWarehouseCode == null ? null : receiveWarehouseCode.trim();
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Integer getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(Integer boxQty) {
        this.boxQty = boxQty;
    }

    public Integer getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getCostTime() {
        return costTime;
    }

    public void setCostTime(Date costTime) {
        this.costTime = costTime;
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

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    public BigDecimal getExciseTax() {
        return exciseTax;
    }

    public void setExciseTax(BigDecimal exciseTax) {
        this.exciseTax = exciseTax;
    }

    public String getBargainType() {
        return bargainType;
    }

    public void setBargainType(String bargainType) {
        this.bargainType = bargainType == null ? null : bargainType.trim();
    }

    public Integer getPayDay() {
        return payDay;
    }

    public void setPayDay(Integer payDay) {
        this.payDay = payDay;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
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
        OpsPoInvoice other = (OpsPoInvoice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoiceId() == null ? other.getInvoiceId() == null : this.getInvoiceId().equals(other.getInvoiceId()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getImpDate() == null ? other.getImpDate() == null : this.getImpDate().equals(other.getImpDate()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSupplierCode() == null ? other.getSupplierCode() == null : this.getSupplierCode().equals(other.getSupplierCode()))
            && (this.getInvoiceDate() == null ? other.getInvoiceDate() == null : this.getInvoiceDate().equals(other.getInvoiceDate()))
            && (this.getCurrencyCode() == null ? other.getCurrencyCode() == null : this.getCurrencyCode().equals(other.getCurrencyCode()))
            && (this.getExchangeRate() == null ? other.getExchangeRate() == null : this.getExchangeRate().equals(other.getExchangeRate()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getAmountAdjust() == null ? other.getAmountAdjust() == null : this.getAmountAdjust().equals(other.getAmountAdjust()))
            && (this.getAmountRmb() == null ? other.getAmountRmb() == null : this.getAmountRmb().equals(other.getAmountRmb()))
            && (this.getCustomsDate() == null ? other.getCustomsDate() == null : this.getCustomsDate().equals(other.getCustomsDate()))
            && (this.getShipDate() == null ? other.getShipDate() == null : this.getShipDate().equals(other.getShipDate()))
            && (this.getDeclarationNo() == null ? other.getDeclarationNo() == null : this.getDeclarationNo().equals(other.getDeclarationNo()))
            && (this.getGrossWeight() == null ? other.getGrossWeight() == null : this.getGrossWeight().equals(other.getGrossWeight()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getCustomsFee() == null ? other.getCustomsFee() == null : this.getCustomsFee().equals(other.getCustomsFee()))
            && (this.getVatFee() == null ? other.getVatFee() == null : this.getVatFee().equals(other.getVatFee()))
            && (this.getTransFee() == null ? other.getTransFee() == null : this.getTransFee().equals(other.getTransFee()))
            && (this.getOtherFee() == null ? other.getOtherFee() == null : this.getOtherFee().equals(other.getOtherFee()))
            && (this.getArrivedWarehouseCode() == null ? other.getArrivedWarehouseCode() == null : this.getArrivedWarehouseCode().equals(other.getArrivedWarehouseCode()))
            && (this.getReceiveWarehouseCode() == null ? other.getReceiveWarehouseCode() == null : this.getReceiveWarehouseCode().equals(other.getReceiveWarehouseCode()))
            && (this.getTotalQty() == null ? other.getTotalQty() == null : this.getTotalQty().equals(other.getTotalQty()))
            && (this.getBoxQty() == null ? other.getBoxQty() == null : this.getBoxQty().equals(other.getBoxQty()))
            && (this.getOrderQty() == null ? other.getOrderQty() == null : this.getOrderQty().equals(other.getOrderQty()))
            && (this.getReceiveTime() == null ? other.getReceiveTime() == null : this.getReceiveTime().equals(other.getReceiveTime()))
            && (this.getCostTime() == null ? other.getCostTime() == null : this.getCostTime().equals(other.getCostTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getAmountTotal() == null ? other.getAmountTotal() == null : this.getAmountTotal().equals(other.getAmountTotal()))
            && (this.getExciseTax() == null ? other.getExciseTax() == null : this.getExciseTax().equals(other.getExciseTax()))
            && (this.getBargainType() == null ? other.getBargainType() == null : this.getBargainType().equals(other.getBargainType()))
            && (this.getPayDay() == null ? other.getPayDay() == null : this.getPayDay().equals(other.getPayDay()))
            && (this.getInvoiceType() == null ? other.getInvoiceType() == null : this.getInvoiceType().equals(other.getInvoiceType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoiceId() == null) ? 0 : getInvoiceId().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getImpDate() == null) ? 0 : getImpDate().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSupplierCode() == null) ? 0 : getSupplierCode().hashCode());
        result = prime * result + ((getInvoiceDate() == null) ? 0 : getInvoiceDate().hashCode());
        result = prime * result + ((getCurrencyCode() == null) ? 0 : getCurrencyCode().hashCode());
        result = prime * result + ((getExchangeRate() == null) ? 0 : getExchangeRate().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getAmountAdjust() == null) ? 0 : getAmountAdjust().hashCode());
        result = prime * result + ((getAmountRmb() == null) ? 0 : getAmountRmb().hashCode());
        result = prime * result + ((getCustomsDate() == null) ? 0 : getCustomsDate().hashCode());
        result = prime * result + ((getShipDate() == null) ? 0 : getShipDate().hashCode());
        result = prime * result + ((getDeclarationNo() == null) ? 0 : getDeclarationNo().hashCode());
        result = prime * result + ((getGrossWeight() == null) ? 0 : getGrossWeight().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getCustomsFee() == null) ? 0 : getCustomsFee().hashCode());
        result = prime * result + ((getVatFee() == null) ? 0 : getVatFee().hashCode());
        result = prime * result + ((getTransFee() == null) ? 0 : getTransFee().hashCode());
        result = prime * result + ((getOtherFee() == null) ? 0 : getOtherFee().hashCode());
        result = prime * result + ((getArrivedWarehouseCode() == null) ? 0 : getArrivedWarehouseCode().hashCode());
        result = prime * result + ((getReceiveWarehouseCode() == null) ? 0 : getReceiveWarehouseCode().hashCode());
        result = prime * result + ((getTotalQty() == null) ? 0 : getTotalQty().hashCode());
        result = prime * result + ((getBoxQty() == null) ? 0 : getBoxQty().hashCode());
        result = prime * result + ((getOrderQty() == null) ? 0 : getOrderQty().hashCode());
        result = prime * result + ((getReceiveTime() == null) ? 0 : getReceiveTime().hashCode());
        result = prime * result + ((getCostTime() == null) ? 0 : getCostTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getAmountTotal() == null) ? 0 : getAmountTotal().hashCode());
        result = prime * result + ((getExciseTax() == null) ? 0 : getExciseTax().hashCode());
        result = prime * result + ((getBargainType() == null) ? 0 : getBargainType().hashCode());
        result = prime * result + ((getPayDay() == null) ? 0 : getPayDay().hashCode());
        result = prime * result + ((getInvoiceType() == null) ? 0 : getInvoiceType().hashCode());
        return result;
    }
}