package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class IpsReceiveInvoiceMasterFromAll implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 发票ID
     */
    private String invoiceBatchNo;

    /**
     * 箱单批次条数
     */
    private Integer invoiceBatchBarcodeNum;

    /**
     * 采购方
     */
    private String purchaseUnitCode;

    /**
     * 采购来源系统，O OPS系统，M 制造PMS系统，O_P新采购系统，I_P集团内采购系统
     */
    private String sourceSystem;

    /**
     * 开票主体
     */
    private String invoiceIssuer;

    /**
     * 供应商代码
     */
    private String supplierNo;

    /**
     * 发票号
     */
    private String invoiceNo;

    /**
     * 开票日期
     */
    private Date invdate;

    /**
     * SMCcode
     */
    private String smccode;

    /**
     * 收货地址编码
     */
    private String receivingAddressCode;

    /**
     * 发票数据状态，0数据未就绪，1数据已就绪
     */
    private String state;

    /**
     * 发票对应总数量
     */
    private Double qtySum;

    /**
     * 箱数
     */
    private Double boxQty;

    /**
     * 发票对应订单总数
     */
    private Double orderNum;

    /**
     * 发票对应总毛重
     */
    private Double weightSum;

    /**
     * 开始通关时间
     */
    private Date customsStartDate;

    /**
     * 通关结束时间
     */
    private Date customsEndDate;

    /**
     * 到港时间
     */
    private Date portArrivalDate;

    /**
     * 关税
     */
    private Double customsTax;

    /**
     * 运费
     */
    private Double shippingCost;

    /**
     * 增值税
     */
    private Double vatTax;

    /**
     * 其他税费
     */
    private Double otherTax;

    /**
     * 消费税
     */
    private Double consumptionTax;

    /**
     * 币种ID
     */
    private String currencyId;

    /**
     * 币种编码
     */
    private String currencyCode;

    /**
     * 汇率
     */
    private Double exchangeRate;

    /**
     * 发票金额
     */
    private Double invoiceAmount;

    /**
     * 原始发票金额
     */
    private Double srcInvoiceAmount;

    /**
     * 原始发票号
     */
    private String srcInvoiceNo;

    /**
     * 贸易方式：FOB、CIF、FCA
     */
    private String tradeMethod;

    /**
     * 付款方式
     */
    private String payType;

    /**
     * 发票类型：0 增值税发票；1普票
     */
    private String invoiceType;

    /**
     * 补充信息
     */
    private String addtionInfo;

    /**
     * 关务系统的发票状态
     */
    private String gwInvoiceStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 删除标识：0正常；1删除
     */
    private Integer delflag;

    private String dataType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceBatchNo() {
        return invoiceBatchNo;
    }

    public void setInvoiceBatchNo(String invoiceBatchNo) {
        this.invoiceBatchNo = invoiceBatchNo;
    }

    public Integer getInvoiceBatchBarcodeNum() {
        return invoiceBatchBarcodeNum;
    }

    public void setInvoiceBatchBarcodeNum(Integer invoiceBatchBarcodeNum) {
        this.invoiceBatchBarcodeNum = invoiceBatchBarcodeNum;
    }

    public String getPurchaseUnitCode() {
        return purchaseUnitCode;
    }

    public void setPurchaseUnitCode(String purchaseUnitCode) {
        this.purchaseUnitCode = purchaseUnitCode;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getInvoiceIssuer() {
        return invoiceIssuer;
    }

    public void setInvoiceIssuer(String invoiceIssuer) {
        this.invoiceIssuer = invoiceIssuer;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getInvdate() {
        return invdate;
    }

    public void setInvdate(Date invdate) {
        this.invdate = invdate;
    }

    public String getSmccode() {
        return smccode;
    }

    public void setSmccode(String smccode) {
        this.smccode = smccode;
    }

    public String getReceivingAddressCode() {
        return receivingAddressCode;
    }

    public void setReceivingAddressCode(String receivingAddressCode) {
        this.receivingAddressCode = receivingAddressCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getQtySum() {
        return qtySum;
    }

    public void setQtySum(Double qtySum) {
        this.qtySum = qtySum;
    }

    public Double getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(Double boxQty) {
        this.boxQty = boxQty;
    }

    public Double getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Double orderNum) {
        this.orderNum = orderNum;
    }

    public Double getWeightSum() {
        return weightSum;
    }

    public void setWeightSum(Double weightSum) {
        this.weightSum = weightSum;
    }

    public Date getCustomsStartDate() {
        return customsStartDate;
    }

    public void setCustomsStartDate(Date customsStartDate) {
        this.customsStartDate = customsStartDate;
    }

    public Date getCustomsEndDate() {
        return customsEndDate;
    }

    public void setCustomsEndDate(Date customsEndDate) {
        this.customsEndDate = customsEndDate;
    }

    public Date getPortArrivalDate() {
        return portArrivalDate;
    }

    public void setPortArrivalDate(Date portArrivalDate) {
        this.portArrivalDate = portArrivalDate;
    }

    public Double getCustomsTax() {
        return customsTax;
    }

    public void setCustomsTax(Double customsTax) {
        this.customsTax = customsTax;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public Double getVatTax() {
        return vatTax;
    }

    public void setVatTax(Double vatTax) {
        this.vatTax = vatTax;
    }

    public Double getOtherTax() {
        return otherTax;
    }

    public void setOtherTax(Double otherTax) {
        this.otherTax = otherTax;
    }

    public Double getConsumptionTax() {
        return consumptionTax;
    }

    public void setConsumptionTax(Double consumptionTax) {
        this.consumptionTax = consumptionTax;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Double getSrcInvoiceAmount() {
        return srcInvoiceAmount;
    }

    public void setSrcInvoiceAmount(Double srcInvoiceAmount) {
        this.srcInvoiceAmount = srcInvoiceAmount;
    }

    public String getSrcInvoiceNo() {
        return srcInvoiceNo;
    }

    public void setSrcInvoiceNo(String srcInvoiceNo) {
        this.srcInvoiceNo = srcInvoiceNo;
    }

    public String getTradeMethod() {
        return tradeMethod;
    }

    public void setTradeMethod(String tradeMethod) {
        this.tradeMethod = tradeMethod;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getAddtionInfo() {
        return addtionInfo;
    }

    public void setAddtionInfo(String addtionInfo) {
        this.addtionInfo = addtionInfo;
    }

    public String getGwInvoiceStatus() {
        return gwInvoiceStatus;
    }

    public void setGwInvoiceStatus(String gwInvoiceStatus) {
        this.gwInvoiceStatus = gwInvoiceStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
