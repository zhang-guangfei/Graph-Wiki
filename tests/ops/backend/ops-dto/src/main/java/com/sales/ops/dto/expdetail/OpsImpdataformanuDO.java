package com.sales.ops.dto.expdetail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OpsImpdataformanuDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID号
     */

    private Integer id;

    /**
     * 发票号
     */
    private String InvoiceNo;

    /**
     * 箱号
     */
    private String CaseNo;

    /**
     * 订单号
     */
    private String OrderNo;

    /**
     * 客户代码
     */
    private String CustomerNo;

    /**
     * 型号
     */
    private String ModelNo;

    /**
     * 数量
     */
    private Integer Quantity;

    /**
     * 运输方式
     */
    private String TransType;

    /**
     * 营业物流出库日期
     */
    private Date ImpDate;

    /**
     * 状态,0:数据发布，制造不可更新，故为默认值
     */
    private String OptCode;

    /**
     * 说明
     */
    private String ExpDesc;

    /**
     * BarCode
     */
    private String BarCode;

    /**
     * 操作者
     */
    private String Username;

    /**
     * 处理时间
     */
    private Date Opttime;

    /**
     * 操作记录
     */
    private String OptRecord;

    /**
     * 拍号
     */
    private String PalletNo;

    /**
     * 单价
     */
    private BigDecimal Price;

    /**
     * 外部单号
     */
    private String extOrderno;

    /**
     * 外部项号
     */
    private String extOrderItem;

    /**
     * 价值（未含税）
     */
    private Double Amount;

    /**
     * 价值（含税）
     */
    private BigDecimal TaxAmount;

    /**
     * 税率
     */
    private Double TaxRate;

    private BigDecimal taxPrice;

    private Long FromId;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }

    public String getCaseNo() {
        return CaseNo;
    }

    public void setCaseNo(String caseNo) {
        CaseNo = caseNo;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getCustomerNo() {
        return CustomerNo;
    }

    public void setCustomerNo(String customerNo) {
        CustomerNo = customerNo;
    }

    public String getModelNo() {
        return ModelNo;
    }

    public void setModelNo(String modelNo) {
        ModelNo = modelNo;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public String getTransType() {
        return TransType;
    }

    public void setTransType(String transType) {
        TransType = transType;
    }

    public Date getImpDate() {
        return ImpDate;
    }

    public void setImpDate(Date impDate) {
        ImpDate = impDate;
    }

    public String getOptCode() {
        return OptCode;
    }

    public void setOptCode(String optCode) {
        OptCode = optCode;
    }

    public String getExpDesc() {
        return ExpDesc;
    }

    public void setExpDesc(String expDesc) {
        ExpDesc = expDesc;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Date getOpttime() {
        return Opttime;
    }

    public void setOpttime(Date opttime) {
        Opttime = opttime;
    }

    public String getOptRecord() {
        return OptRecord;
    }

    public void setOptRecord(String optRecord) {
        OptRecord = optRecord;
    }

    public String getPalletNo() {
        return PalletNo;
    }

    public void setPalletNo(String palletNo) {
        PalletNo = palletNo;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    public String getExtOrderno() {
        return extOrderno;
    }

    public void setExtOrderno(String extOrderno) {
        this.extOrderno = extOrderno;
    }

    public String getExtOrderItem() {
        return extOrderItem;
    }

    public void setExtOrderItem(String extOrderItem) {
        this.extOrderItem = extOrderItem;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public BigDecimal getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        TaxAmount = taxAmount;
    }

    public Double getTaxRate() {
        return TaxRate;
    }

    public void setTaxRate(Double taxRate) {
        TaxRate = taxRate;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Long getFromId() {
        return FromId;
    }

    public void setFromId(Long fromId) {
        FromId = fromId;
    }
}