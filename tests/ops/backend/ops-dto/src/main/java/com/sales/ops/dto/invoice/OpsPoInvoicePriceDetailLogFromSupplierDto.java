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
 * @since 2024-02-22
 */
public class OpsPoInvoicePriceDetailLogFromSupplierDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原产国
     */
    private String countryOrigin;

    /**
     * 发给供应商的订单号
     */
    private String pono;

    /**
     * 发给供应商的订单行号
     */
    private Integer lineItem;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 海关品名
     */
    private String descriptionCustoms;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 人民币金额
     */
    private BigDecimal rmbMoney;

    /**
     * 净重
     */
    private BigDecimal weight;

    /**
     * 运保费
     */
    private BigDecimal trafficInsurance;

    /**
     * 关税
     */
    private BigDecimal customsFee;

    /**
     * 增值税
     */
    private BigDecimal vatFee;

    /**
     * 消费税
     */
    private BigDecimal exciseTax;

    /**
     * 其他税
     */
    private BigDecimal otherTax;

    /**
     * 无商业价值
     */
    private String nonCommercial;

    /**
     * 供应商系统提供数据时间
     */
    private Date supplierSystemExecTime;


    private String updateUser;

    private Date createTime;

    private Long id;

    private String createUser;

    private BigDecimal amount;

    private Integer delFlag;

    private Long pid;

    private Date updateTime;

    private BigDecimal unitMoney;


    private Long fromId;

    private String impOrderNo;

    private String invoiceOriginal;

    public String getCountryOrigin() {
        return countryOrigin;
    }

    public void setCountryOrigin(String countryOrigin) {
        this.countryOrigin = countryOrigin;
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono;
    }

    public Integer getLineItem() {
        return lineItem;
    }

    public void setLineItem(Integer lineItem) {
        this.lineItem = lineItem;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getDescriptionCustoms() {
        return descriptionCustoms;
    }

    public void setDescriptionCustoms(String descriptionCustoms) {
        this.descriptionCustoms = descriptionCustoms;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getRmbMoney() {
        return rmbMoney;
    }

    public void setRmbMoney(BigDecimal rmbMoney) {
        this.rmbMoney = rmbMoney;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getTrafficInsurance() {
        return trafficInsurance;
    }

    public void setTrafficInsurance(BigDecimal trafficInsurance) {
        this.trafficInsurance = trafficInsurance;
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

    public BigDecimal getExciseTax() {
        return exciseTax;
    }

    public void setExciseTax(BigDecimal exciseTax) {
        this.exciseTax = exciseTax;
    }

    public BigDecimal getOtherTax() {
        return otherTax;
    }

    public void setOtherTax(BigDecimal otherTax) {
        this.otherTax = otherTax;
    }

    public String getNonCommercial() {
        return nonCommercial;
    }

    public void setNonCommercial(String nonCommercial) {
        this.nonCommercial = nonCommercial;
    }

    public Date getSupplierSystemExecTime() {
        return supplierSystemExecTime;
    }

    public void setSupplierSystemExecTime(Date supplierSystemExecTime) {
        this.supplierSystemExecTime = supplierSystemExecTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getUnitMoney() {
        return unitMoney;
    }

    public void setUnitMoney(BigDecimal unitMoney) {
        this.unitMoney = unitMoney;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getImpOrderNo() {
        return impOrderNo;
    }

    public void setImpOrderNo(String impOrderNo) {
        this.impOrderNo = impOrderNo;
    }

    public String getInvoiceOriginal() {
        return invoiceOriginal;
    }

    public void setInvoiceOriginal(String invoiceOriginal) {
        this.invoiceOriginal = invoiceOriginal;
    }
}
