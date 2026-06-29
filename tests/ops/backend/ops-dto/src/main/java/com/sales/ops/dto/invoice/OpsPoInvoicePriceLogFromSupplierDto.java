package com.sales.ops.dto.invoice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2024-02-29
 */
public class OpsPoInvoicePriceLogFromSupplierDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String shipment;

    private Date createTime;

    private BigDecimal amount;

    private String updateUser;

    private Date supplierSystemExecTime;

    private String sourceType;

    private BigDecimal customsFee;

    private String invoiceDate;

    private String gwInvoiceNo;

    private String smccode;

    private String gwStatusCode;

    private Long sourceId;

    private String paymentTerm;

    private Integer boxQty;

    private Long id;

    private BigDecimal amountRmb;

    private BigDecimal vatFee;

    private BigDecimal exciseTax;

    private Long pid;

    private BigDecimal transFee;

    private String tradeMethod;

    private Integer delFlag;

    private String currency;

    private String createUser;

    private String plantMark;

    private BigDecimal grossWeight;

    private Date updateTime;

    private BigDecimal exchangeRate;

    private BigDecimal weight;

    private Integer totalQty;

    private String invoiceNo;

    private String importCustomer;

    private String transType;

    private String bargainType;

    public String getBargainType() {
        return bargainType;
    }

    public void setBargainType(String bargainType) {
        this.bargainType = bargainType;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getSupplierSystemExecTime() {
        return supplierSystemExecTime;
    }

    public void setSupplierSystemExecTime(Date supplierSystemExecTime) {
        this.supplierSystemExecTime = supplierSystemExecTime;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public BigDecimal getCustomsFee() {
        return customsFee;
    }

    public void setCustomsFee(BigDecimal customsFee) {
        this.customsFee = customsFee;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getGwInvoiceNo() {
        return gwInvoiceNo;
    }

    public void setGwInvoiceNo(String gwInvoiceNo) {
        this.gwInvoiceNo = gwInvoiceNo;
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode;
    }

    public String getGwStatusCode() {
        return gwStatusCode;
    }

    public void setGwStatusCode(String gwStatusCode) {
        this.gwStatusCode = gwStatusCode;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public Integer getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(Integer boxQty) {
        this.boxQty = boxQty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmountRmb() {
        return amountRmb;
    }

    public void setAmountRmb(BigDecimal amountRmb) {
        this.amountRmb = amountRmb;
    }

    public BigDecimal getVatFee() {
        return vatFee;
    }

    public void setVatFee(BigDecimal vatFee) {
        this.vatFee = vatFee;
    }

    public BigDecimal getExciseTax() {
        return exciseTax;
    }

    public void setExciseTax(BigDecimal exciseTax) {
        this.exciseTax = exciseTax;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public BigDecimal getTransFee() {
        return transFee;
    }

    public void setTransFee(BigDecimal transFee) {
        this.transFee = transFee;
    }

    public String getTradeMethod() {
        return tradeMethod;
    }

    public void setTradeMethod(String tradeMethod) {
        this.tradeMethod = tradeMethod;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getPlantMark() {
        return plantMark;
    }

    public void setPlantMark(String plantMark) {
        this.plantMark = plantMark;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getImportCustomer() {
        return importCustomer;
    }

    public void setImportCustomer(String importCustomer) {
        this.importCustomer = importCustomer;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
}
