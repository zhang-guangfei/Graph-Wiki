package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Rcvdetail extends RcvdetailKey implements Serializable {
    private Long id;

    private String rorderFno;

    private String modelNo;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal priceEnduser;

    private BigDecimal eprice;

    private BigDecimal taxRate;

    private BigDecimal ntaxPice;

    private BigDecimal ntaxAmount;

    private BigDecimal taxAmount;

    private BigDecimal amount;

    private BigDecimal discount;

    private Date dlvDate;

    private Date cdlvDate;

    private Date wmsDlvDate;

    private String specOfferNo;

    private String sourceType;

    private String cproductNo;

    private String specMark;

    private String remark;

    private String productName;

    private String opponent;

    private String pplNo;

    private String projectNo;

    private String groupCustomerNo;

    private String shikomiNo;

    private String salesInfoNo;

    private String preSalesOrderNo;

    private String corderNo;

    private String customCode;

    private Short orderType;

    private Short status;

    private Short deleteStatus;

    private String stockCode;

    private String stockType;

    private String inventoryTypeCode;

    private String prodFlag;

    private Date allotTime;

    private Date readyTime;

    private Date expTime;

    private Date shipTime;

    private Integer readyQty;

    private Integer expQty;

    private Integer returnedQty;

    private Integer addressNo;

    private Date processDate;

    private String borrowNo;

    private Integer poQty;

    private String expMsg;

    private Integer expDlvType;

    private String expLinkNo;

    private String invoicegroupkey;

    private Integer invoiceQty;

    private Date invoiceTime;

    private String carrierid;

    private String expressno;

    private Date handoverTime;

    private Integer version;

    private Date createTime;

    private Date updateTime;

    private String updateUser;

    private String createUser;

    private BigDecimal priceUser;

    private Boolean intercept;

    private Date interceptTime;

    private String industryid;

    private String customertype;

    private Date expectedDeliveryTime;

    private Date estimatedDeliveryDay;

    private String inqbApplyNo;

    private Date entryTime;

    private String extOrderNo;

    private String extOrderItem;

    private String prepareOrderno;

    private Date allotStartTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRorderFno() {
        return rorderFno;
    }

    public void setRorderFno(String rorderFno) {
        this.rorderFno = rorderFno == null ? null : rorderFno.trim();
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

    public BigDecimal getPriceEnduser() {
        return priceEnduser;
    }

    public void setPriceEnduser(BigDecimal priceEnduser) {
        this.priceEnduser = priceEnduser;
    }

    public BigDecimal getEprice() {
        return eprice;
    }

    public void setEprice(BigDecimal eprice) {
        this.eprice = eprice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getNtaxPice() {
        return ntaxPice;
    }

    public void setNtaxPice(BigDecimal ntaxPice) {
        this.ntaxPice = ntaxPice;
    }

    public BigDecimal getNtaxAmount() {
        return ntaxAmount;
    }

    public void setNtaxAmount(BigDecimal ntaxAmount) {
        this.ntaxAmount = ntaxAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public Date getCdlvDate() {
        return cdlvDate;
    }

    public void setCdlvDate(Date cdlvDate) {
        this.cdlvDate = cdlvDate;
    }

    public Date getWmsDlvDate() {
        return wmsDlvDate;
    }

    public void setWmsDlvDate(Date wmsDlvDate) {
        this.wmsDlvDate = wmsDlvDate;
    }

    public String getSpecOfferNo() {
        return specOfferNo;
    }

    public void setSpecOfferNo(String specOfferNo) {
        this.specOfferNo = specOfferNo == null ? null : specOfferNo.trim();
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo == null ? null : cproductNo.trim();
    }

    public String getSpecMark() {
        return specMark;
    }

    public void setSpecMark(String specMark) {
        this.specMark = specMark == null ? null : specMark.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent == null ? null : opponent.trim();
    }

    public String getPplNo() {
        return pplNo;
    }

    public void setPplNo(String pplNo) {
        this.pplNo = pplNo == null ? null : pplNo.trim();
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo == null ? null : groupCustomerNo.trim();
    }

    public String getShikomiNo() {
        return shikomiNo;
    }

    public void setShikomiNo(String shikomiNo) {
        this.shikomiNo = shikomiNo == null ? null : shikomiNo.trim();
    }

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo == null ? null : salesInfoNo.trim();
    }

    public String getPreSalesOrderNo() {
        return preSalesOrderNo;
    }

    public void setPreSalesOrderNo(String preSalesOrderNo) {
        this.preSalesOrderNo = preSalesOrderNo == null ? null : preSalesOrderNo.trim();
    }

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo == null ? null : corderNo.trim();
    }

    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode == null ? null : customCode.trim();
    }

    public Short getOrderType() {
        return orderType;
    }

    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Short deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType == null ? null : stockType.trim();
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode == null ? null : inventoryTypeCode.trim();
    }

    public String getProdFlag() {
        return prodFlag;
    }

    public void setProdFlag(String prodFlag) {
        this.prodFlag = prodFlag == null ? null : prodFlag.trim();
    }

    public Date getAllotTime() {
        return allotTime;
    }

    public void setAllotTime(Date allotTime) {
        this.allotTime = allotTime;
    }

    public Date getReadyTime() {
        return readyTime;
    }

    public void setReadyTime(Date readyTime) {
        this.readyTime = readyTime;
    }

    public Date getExpTime() {
        return expTime;
    }

    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public Integer getReadyQty() {
        return readyQty;
    }

    public void setReadyQty(Integer readyQty) {
        this.readyQty = readyQty;
    }

    public Integer getExpQty() {
        return expQty;
    }

    public void setExpQty(Integer expQty) {
        this.expQty = expQty;
    }

    public Integer getReturnedQty() {
        return returnedQty;
    }

    public void setReturnedQty(Integer returnedQty) {
        this.returnedQty = returnedQty;
    }

    public Integer getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(Integer addressNo) {
        this.addressNo = addressNo;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getBorrowNo() {
        return borrowNo;
    }

    public void setBorrowNo(String borrowNo) {
        this.borrowNo = borrowNo == null ? null : borrowNo.trim();
    }

    public Integer getPoQty() {
        return poQty;
    }

    public void setPoQty(Integer poQty) {
        this.poQty = poQty;
    }

    public String getExpMsg() {
        return expMsg;
    }

    public void setExpMsg(String expMsg) {
        this.expMsg = expMsg == null ? null : expMsg.trim();
    }

    public Integer getExpDlvType() {
        return expDlvType;
    }

    public void setExpDlvType(Integer expDlvType) {
        this.expDlvType = expDlvType;
    }

    public String getExpLinkNo() {
        return expLinkNo;
    }

    public void setExpLinkNo(String expLinkNo) {
        this.expLinkNo = expLinkNo == null ? null : expLinkNo.trim();
    }

    public String getInvoicegroupkey() {
        return invoicegroupkey;
    }

    public void setInvoicegroupkey(String invoicegroupkey) {
        this.invoicegroupkey = invoicegroupkey == null ? null : invoicegroupkey.trim();
    }

    public Integer getInvoiceQty() {
        return invoiceQty;
    }

    public void setInvoiceQty(Integer invoiceQty) {
        this.invoiceQty = invoiceQty;
    }

    public Date getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(Date invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public String getCarrierid() {
        return carrierid;
    }

    public void setCarrierid(String carrierid) {
        this.carrierid = carrierid == null ? null : carrierid.trim();
    }

    public String getExpressno() {
        return expressno;
    }

    public void setExpressno(String expressno) {
        this.expressno = expressno == null ? null : expressno.trim();
    }

    public Date getHandoverTime() {
        return handoverTime;
    }

    public void setHandoverTime(Date handoverTime) {
        this.handoverTime = handoverTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public BigDecimal getPriceUser() {
        return priceUser;
    }

    public void setPriceUser(BigDecimal priceUser) {
        this.priceUser = priceUser;
    }

    public Boolean getIntercept() {
        return intercept;
    }

    public void setIntercept(Boolean intercept) {
        this.intercept = intercept;
    }

    public Date getInterceptTime() {
        return interceptTime;
    }

    public void setInterceptTime(Date interceptTime) {
        this.interceptTime = interceptTime;
    }

    public String getIndustryid() {
        return industryid;
    }

    public void setIndustryid(String industryid) {
        this.industryid = industryid == null ? null : industryid.trim();
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype == null ? null : customertype.trim();
    }

    public Date getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(Date expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public Date getEstimatedDeliveryDay() {
        return estimatedDeliveryDay;
    }

    public void setEstimatedDeliveryDay(Date estimatedDeliveryDay) {
        this.estimatedDeliveryDay = estimatedDeliveryDay;
    }

    public String getInqbApplyNo() {
        return inqbApplyNo;
    }

    public void setInqbApplyNo(String inqbApplyNo) {
        this.inqbApplyNo = inqbApplyNo == null ? null : inqbApplyNo.trim();
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getExtOrderNo() {
        return extOrderNo;
    }

    public void setExtOrderNo(String extOrderNo) {
        this.extOrderNo = extOrderNo == null ? null : extOrderNo.trim();
    }

    public String getExtOrderItem() {
        return extOrderItem;
    }

    public void setExtOrderItem(String extOrderItem) {
        this.extOrderItem = extOrderItem == null ? null : extOrderItem.trim();
    }

    public String getPrepareOrderno() {
        return prepareOrderno;
    }

    public void setPrepareOrderno(String prepareOrderno) {
        this.prepareOrderno = prepareOrderno == null ? null : prepareOrderno.trim();
    }

    public Date getAllotStartTime() {
        return allotStartTime;
    }

    public void setAllotStartTime(Date allotStartTime) {
        this.allotStartTime = allotStartTime;
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
        Rcvdetail other = (Rcvdetail) that;
        return (this.getRorderNo() == null ? other.getRorderNo() == null : this.getRorderNo().equals(other.getRorderNo()))
            && (this.getRorderItem() == null ? other.getRorderItem() == null : this.getRorderItem().equals(other.getRorderItem()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRorderFno() == null ? other.getRorderFno() == null : this.getRorderFno().equals(other.getRorderFno()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getPriceEnduser() == null ? other.getPriceEnduser() == null : this.getPriceEnduser().equals(other.getPriceEnduser()))
            && (this.getEprice() == null ? other.getEprice() == null : this.getEprice().equals(other.getEprice()))
            && (this.getTaxRate() == null ? other.getTaxRate() == null : this.getTaxRate().equals(other.getTaxRate()))
            && (this.getNtaxPice() == null ? other.getNtaxPice() == null : this.getNtaxPice().equals(other.getNtaxPice()))
            && (this.getNtaxAmount() == null ? other.getNtaxAmount() == null : this.getNtaxAmount().equals(other.getNtaxAmount()))
            && (this.getTaxAmount() == null ? other.getTaxAmount() == null : this.getTaxAmount().equals(other.getTaxAmount()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getDlvDate() == null ? other.getDlvDate() == null : this.getDlvDate().equals(other.getDlvDate()))
            && (this.getCdlvDate() == null ? other.getCdlvDate() == null : this.getCdlvDate().equals(other.getCdlvDate()))
            && (this.getWmsDlvDate() == null ? other.getWmsDlvDate() == null : this.getWmsDlvDate().equals(other.getWmsDlvDate()))
            && (this.getSpecOfferNo() == null ? other.getSpecOfferNo() == null : this.getSpecOfferNo().equals(other.getSpecOfferNo()))
            && (this.getSourceType() == null ? other.getSourceType() == null : this.getSourceType().equals(other.getSourceType()))
            && (this.getCproductNo() == null ? other.getCproductNo() == null : this.getCproductNo().equals(other.getCproductNo()))
            && (this.getSpecMark() == null ? other.getSpecMark() == null : this.getSpecMark().equals(other.getSpecMark()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getOpponent() == null ? other.getOpponent() == null : this.getOpponent().equals(other.getOpponent()))
            && (this.getPplNo() == null ? other.getPplNo() == null : this.getPplNo().equals(other.getPplNo()))
            && (this.getProjectNo() == null ? other.getProjectNo() == null : this.getProjectNo().equals(other.getProjectNo()))
            && (this.getGroupCustomerNo() == null ? other.getGroupCustomerNo() == null : this.getGroupCustomerNo().equals(other.getGroupCustomerNo()))
            && (this.getShikomiNo() == null ? other.getShikomiNo() == null : this.getShikomiNo().equals(other.getShikomiNo()))
            && (this.getSalesInfoNo() == null ? other.getSalesInfoNo() == null : this.getSalesInfoNo().equals(other.getSalesInfoNo()))
            && (this.getPreSalesOrderNo() == null ? other.getPreSalesOrderNo() == null : this.getPreSalesOrderNo().equals(other.getPreSalesOrderNo()))
            && (this.getCorderNo() == null ? other.getCorderNo() == null : this.getCorderNo().equals(other.getCorderNo()))
            && (this.getCustomCode() == null ? other.getCustomCode() == null : this.getCustomCode().equals(other.getCustomCode()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDeleteStatus() == null ? other.getDeleteStatus() == null : this.getDeleteStatus().equals(other.getDeleteStatus()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getStockType() == null ? other.getStockType() == null : this.getStockType().equals(other.getStockType()))
            && (this.getInventoryTypeCode() == null ? other.getInventoryTypeCode() == null : this.getInventoryTypeCode().equals(other.getInventoryTypeCode()))
            && (this.getProdFlag() == null ? other.getProdFlag() == null : this.getProdFlag().equals(other.getProdFlag()))
            && (this.getAllotTime() == null ? other.getAllotTime() == null : this.getAllotTime().equals(other.getAllotTime()))
            && (this.getReadyTime() == null ? other.getReadyTime() == null : this.getReadyTime().equals(other.getReadyTime()))
            && (this.getExpTime() == null ? other.getExpTime() == null : this.getExpTime().equals(other.getExpTime()))
            && (this.getShipTime() == null ? other.getShipTime() == null : this.getShipTime().equals(other.getShipTime()))
            && (this.getReadyQty() == null ? other.getReadyQty() == null : this.getReadyQty().equals(other.getReadyQty()))
            && (this.getExpQty() == null ? other.getExpQty() == null : this.getExpQty().equals(other.getExpQty()))
            && (this.getReturnedQty() == null ? other.getReturnedQty() == null : this.getReturnedQty().equals(other.getReturnedQty()))
            && (this.getAddressNo() == null ? other.getAddressNo() == null : this.getAddressNo().equals(other.getAddressNo()))
            && (this.getProcessDate() == null ? other.getProcessDate() == null : this.getProcessDate().equals(other.getProcessDate()))
            && (this.getBorrowNo() == null ? other.getBorrowNo() == null : this.getBorrowNo().equals(other.getBorrowNo()))
            && (this.getPoQty() == null ? other.getPoQty() == null : this.getPoQty().equals(other.getPoQty()))
            && (this.getExpMsg() == null ? other.getExpMsg() == null : this.getExpMsg().equals(other.getExpMsg()))
            && (this.getExpDlvType() == null ? other.getExpDlvType() == null : this.getExpDlvType().equals(other.getExpDlvType()))
            && (this.getExpLinkNo() == null ? other.getExpLinkNo() == null : this.getExpLinkNo().equals(other.getExpLinkNo()))
            && (this.getInvoicegroupkey() == null ? other.getInvoicegroupkey() == null : this.getInvoicegroupkey().equals(other.getInvoicegroupkey()))
            && (this.getInvoiceQty() == null ? other.getInvoiceQty() == null : this.getInvoiceQty().equals(other.getInvoiceQty()))
            && (this.getInvoiceTime() == null ? other.getInvoiceTime() == null : this.getInvoiceTime().equals(other.getInvoiceTime()))
            && (this.getCarrierid() == null ? other.getCarrierid() == null : this.getCarrierid().equals(other.getCarrierid()))
            && (this.getExpressno() == null ? other.getExpressno() == null : this.getExpressno().equals(other.getExpressno()))
            && (this.getHandoverTime() == null ? other.getHandoverTime() == null : this.getHandoverTime().equals(other.getHandoverTime()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getPriceUser() == null ? other.getPriceUser() == null : this.getPriceUser().equals(other.getPriceUser()))
            && (this.getIntercept() == null ? other.getIntercept() == null : this.getIntercept().equals(other.getIntercept()))
            && (this.getInterceptTime() == null ? other.getInterceptTime() == null : this.getInterceptTime().equals(other.getInterceptTime()))
            && (this.getIndustryid() == null ? other.getIndustryid() == null : this.getIndustryid().equals(other.getIndustryid()))
            && (this.getCustomertype() == null ? other.getCustomertype() == null : this.getCustomertype().equals(other.getCustomertype()))
            && (this.getExpectedDeliveryTime() == null ? other.getExpectedDeliveryTime() == null : this.getExpectedDeliveryTime().equals(other.getExpectedDeliveryTime()))
            && (this.getEstimatedDeliveryDay() == null ? other.getEstimatedDeliveryDay() == null : this.getEstimatedDeliveryDay().equals(other.getEstimatedDeliveryDay()))
            && (this.getInqbApplyNo() == null ? other.getInqbApplyNo() == null : this.getInqbApplyNo().equals(other.getInqbApplyNo()))
            && (this.getEntryTime() == null ? other.getEntryTime() == null : this.getEntryTime().equals(other.getEntryTime()))
            && (this.getExtOrderNo() == null ? other.getExtOrderNo() == null : this.getExtOrderNo().equals(other.getExtOrderNo()))
            && (this.getExtOrderItem() == null ? other.getExtOrderItem() == null : this.getExtOrderItem().equals(other.getExtOrderItem()))
            && (this.getPrepareOrderno() == null ? other.getPrepareOrderno() == null : this.getPrepareOrderno().equals(other.getPrepareOrderno()))
            && (this.getAllotStartTime() == null ? other.getAllotStartTime() == null : this.getAllotStartTime().equals(other.getAllotStartTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRorderNo() == null) ? 0 : getRorderNo().hashCode());
        result = prime * result + ((getRorderItem() == null) ? 0 : getRorderItem().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRorderFno() == null) ? 0 : getRorderFno().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getPriceEnduser() == null) ? 0 : getPriceEnduser().hashCode());
        result = prime * result + ((getEprice() == null) ? 0 : getEprice().hashCode());
        result = prime * result + ((getTaxRate() == null) ? 0 : getTaxRate().hashCode());
        result = prime * result + ((getNtaxPice() == null) ? 0 : getNtaxPice().hashCode());
        result = prime * result + ((getNtaxAmount() == null) ? 0 : getNtaxAmount().hashCode());
        result = prime * result + ((getTaxAmount() == null) ? 0 : getTaxAmount().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getDlvDate() == null) ? 0 : getDlvDate().hashCode());
        result = prime * result + ((getCdlvDate() == null) ? 0 : getCdlvDate().hashCode());
        result = prime * result + ((getWmsDlvDate() == null) ? 0 : getWmsDlvDate().hashCode());
        result = prime * result + ((getSpecOfferNo() == null) ? 0 : getSpecOfferNo().hashCode());
        result = prime * result + ((getSourceType() == null) ? 0 : getSourceType().hashCode());
        result = prime * result + ((getCproductNo() == null) ? 0 : getCproductNo().hashCode());
        result = prime * result + ((getSpecMark() == null) ? 0 : getSpecMark().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getOpponent() == null) ? 0 : getOpponent().hashCode());
        result = prime * result + ((getPplNo() == null) ? 0 : getPplNo().hashCode());
        result = prime * result + ((getProjectNo() == null) ? 0 : getProjectNo().hashCode());
        result = prime * result + ((getGroupCustomerNo() == null) ? 0 : getGroupCustomerNo().hashCode());
        result = prime * result + ((getShikomiNo() == null) ? 0 : getShikomiNo().hashCode());
        result = prime * result + ((getSalesInfoNo() == null) ? 0 : getSalesInfoNo().hashCode());
        result = prime * result + ((getPreSalesOrderNo() == null) ? 0 : getPreSalesOrderNo().hashCode());
        result = prime * result + ((getCorderNo() == null) ? 0 : getCorderNo().hashCode());
        result = prime * result + ((getCustomCode() == null) ? 0 : getCustomCode().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDeleteStatus() == null) ? 0 : getDeleteStatus().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getStockType() == null) ? 0 : getStockType().hashCode());
        result = prime * result + ((getInventoryTypeCode() == null) ? 0 : getInventoryTypeCode().hashCode());
        result = prime * result + ((getProdFlag() == null) ? 0 : getProdFlag().hashCode());
        result = prime * result + ((getAllotTime() == null) ? 0 : getAllotTime().hashCode());
        result = prime * result + ((getReadyTime() == null) ? 0 : getReadyTime().hashCode());
        result = prime * result + ((getExpTime() == null) ? 0 : getExpTime().hashCode());
        result = prime * result + ((getShipTime() == null) ? 0 : getShipTime().hashCode());
        result = prime * result + ((getReadyQty() == null) ? 0 : getReadyQty().hashCode());
        result = prime * result + ((getExpQty() == null) ? 0 : getExpQty().hashCode());
        result = prime * result + ((getReturnedQty() == null) ? 0 : getReturnedQty().hashCode());
        result = prime * result + ((getAddressNo() == null) ? 0 : getAddressNo().hashCode());
        result = prime * result + ((getProcessDate() == null) ? 0 : getProcessDate().hashCode());
        result = prime * result + ((getBorrowNo() == null) ? 0 : getBorrowNo().hashCode());
        result = prime * result + ((getPoQty() == null) ? 0 : getPoQty().hashCode());
        result = prime * result + ((getExpMsg() == null) ? 0 : getExpMsg().hashCode());
        result = prime * result + ((getExpDlvType() == null) ? 0 : getExpDlvType().hashCode());
        result = prime * result + ((getExpLinkNo() == null) ? 0 : getExpLinkNo().hashCode());
        result = prime * result + ((getInvoicegroupkey() == null) ? 0 : getInvoicegroupkey().hashCode());
        result = prime * result + ((getInvoiceQty() == null) ? 0 : getInvoiceQty().hashCode());
        result = prime * result + ((getInvoiceTime() == null) ? 0 : getInvoiceTime().hashCode());
        result = prime * result + ((getCarrierid() == null) ? 0 : getCarrierid().hashCode());
        result = prime * result + ((getExpressno() == null) ? 0 : getExpressno().hashCode());
        result = prime * result + ((getHandoverTime() == null) ? 0 : getHandoverTime().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getPriceUser() == null) ? 0 : getPriceUser().hashCode());
        result = prime * result + ((getIntercept() == null) ? 0 : getIntercept().hashCode());
        result = prime * result + ((getInterceptTime() == null) ? 0 : getInterceptTime().hashCode());
        result = prime * result + ((getIndustryid() == null) ? 0 : getIndustryid().hashCode());
        result = prime * result + ((getCustomertype() == null) ? 0 : getCustomertype().hashCode());
        result = prime * result + ((getExpectedDeliveryTime() == null) ? 0 : getExpectedDeliveryTime().hashCode());
        result = prime * result + ((getEstimatedDeliveryDay() == null) ? 0 : getEstimatedDeliveryDay().hashCode());
        result = prime * result + ((getInqbApplyNo() == null) ? 0 : getInqbApplyNo().hashCode());
        result = prime * result + ((getEntryTime() == null) ? 0 : getEntryTime().hashCode());
        result = prime * result + ((getExtOrderNo() == null) ? 0 : getExtOrderNo().hashCode());
        result = prime * result + ((getExtOrderItem() == null) ? 0 : getExtOrderItem().hashCode());
        result = prime * result + ((getPrepareOrderno() == null) ? 0 : getPrepareOrderno().hashCode());
        result = prime * result + ((getAllotStartTime() == null) ? 0 : getAllotStartTime().hashCode());
        return result;
    }
}