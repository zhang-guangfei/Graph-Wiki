package com.sales.ops.dto.ips;

import java.math.BigDecimal;
import java.util.Date;


public class OpsVManuorderToSales {

    private Integer id;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 国别代码，营业不会使用
     */
    private String SMCCode;

    /**
     * 交货期
     */
    private Date dlvydate;

    /**
     * 运输方式
     */
    private String transType;

    /**
     * 工序代码
     */
    private String processcode;

    /**
     * 制造订单号
     */
    private String orderno;

    /**
     * 处理状态
     */
    private String optStatus;

    /**
     * 订货区分
     */
    private String expInvCode;

    /**
     * 发票区分：生产：0； 非生产：1
     */
    private Integer produce;

    /**
     * 阀板标识
     */
    private Integer isGroup;

    /**
     * 客户名称
     */
    private String cstmName;

    /**
     * 发货地址
     */
    private String dlvAddress;

    /**
     * 联系人
     */
    private String contactPsn;

    /**
     * 电话
     */
    private String teleNo;

    /**
     * 项号
     */
    private String itemno;

    /**
     * 客户编码
     */
    private String customerNo;

    private Date insertDate;

    /**
     * 营业取消原因
     */
    private String salesCancelReason;

    /**
     * 营业取消状态 0未取消  1已取消
     */
    private Integer salesCancelStatus;

    /**
     * 营业取消时间
     */
    private Date salesCancelTime;

    /**
     * 营业处理备注说明
     */
    private String salesRemark;

    /**
     * 营业处理状态码：0订单待受理，1订单受理中，2订单受理成功 ，3订单受理失败
     */
    private Integer salesStatus;

    /**
     * 营业反馈时间
     */
    private Date salesUpdateTime;

    private String salesOrderNo;

    private Date salesDeliveryTime;

    private String salesordernoJp;

    /**
     * 制造品名
     */
    private String productName;

    /**
     * 未税价格
     */
    private BigDecimal unitprice;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 含税价格
     */
    private BigDecimal unitPricewithTax;

    private String userNo;

    private Date expectedArrivalDate;


    private String expectedArrivalinvNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSMCCode() {
        return SMCCode;
    }

    public void setSMCCode(String SMCCode) {
        this.SMCCode = SMCCode;
    }

    public Date getDlvydate() {
        return dlvydate;
    }

    public void setDlvydate(Date dlvydate) {
        this.dlvydate = dlvydate;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getProcesscode() {
        return processcode;
    }

    public void setProcesscode(String processcode) {
        this.processcode = processcode;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(String optStatus) {
        this.optStatus = optStatus;
    }

    public String getExpInvCode() {
        return expInvCode;
    }

    public void setExpInvCode(String expInvCode) {
        this.expInvCode = expInvCode;
    }

    public Integer getProduce() {
        return produce;
    }

    public void setProduce(Integer produce) {
        this.produce = produce;
    }

    public Integer getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Integer isGroup) {
        this.isGroup = isGroup;
    }

    public String getCstmName() {
        return cstmName;
    }

    public void setCstmName(String cstmName) {
        this.cstmName = cstmName;
    }

    public String getDlvAddress() {
        return dlvAddress;
    }

    public void setDlvAddress(String dlvAddress) {
        this.dlvAddress = dlvAddress;
    }

    public String getContactPsn() {
        return contactPsn;
    }

    public void setContactPsn(String contactPsn) {
        this.contactPsn = contactPsn;
    }

    public String getTeleNo() {
        return teleNo;
    }

    public void setTeleNo(String teleNo) {
        this.teleNo = teleNo;
    }

    public String getItemno() {
        return itemno;
    }

    public void setItemno(String itemno) {
        this.itemno = itemno;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getSalesCancelReason() {
        return salesCancelReason;
    }

    public void setSalesCancelReason(String salesCancelReason) {
        this.salesCancelReason = salesCancelReason;
    }

    public Integer getSalesCancelStatus() {
        return salesCancelStatus;
    }

    public void setSalesCancelStatus(Integer salesCancelStatus) {
        this.salesCancelStatus = salesCancelStatus;
    }

    public Date getSalesCancelTime() {
        return salesCancelTime;
    }

    public void setSalesCancelTime(Date salesCancelTime) {
        this.salesCancelTime = salesCancelTime;
    }

    public String getSalesRemark() {
        return salesRemark;
    }

    public void setSalesRemark(String salesRemark) {
        this.salesRemark = salesRemark;
    }

    public Integer getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(Integer salesStatus) {
        this.salesStatus = salesStatus;
    }

    public Date getSalesUpdateTime() {
        return salesUpdateTime;
    }

    public void setSalesUpdateTime(Date salesUpdateTime) {
        this.salesUpdateTime = salesUpdateTime;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public Date getSalesDeliveryTime() {
        return salesDeliveryTime;
    }

    public void setSalesDeliveryTime(Date salesDeliveryTime) {
        this.salesDeliveryTime = salesDeliveryTime;
    }

    public String getSalesordernoJp() {
        return salesordernoJp;
    }

    public void setSalesordernoJp(String salesordernoJp) {
        this.salesordernoJp = salesordernoJp;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getUnitPricewithTax() {
        return unitPricewithTax;
    }

    public void setUnitPricewithTax(BigDecimal unitPricewithTax) {
        this.unitPricewithTax = unitPricewithTax;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public Date getExpectedArrivalDate() {
        return expectedArrivalDate;
    }

    public void setExpectedArrivalDate(Date expectedArrivalDate) {
        this.expectedArrivalDate = expectedArrivalDate;
    }

    public String getExpectedArrivalinvNo() {
        return expectedArrivalinvNo;
    }

    public void setExpectedArrivalinvNo(String expectedArrivalinvNo) {
        this.expectedArrivalinvNo = expectedArrivalinvNo;
    }
}
