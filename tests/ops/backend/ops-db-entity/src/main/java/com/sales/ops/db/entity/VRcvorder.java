package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VRcvorder implements Serializable {
    private Long id;

    private String rorderNo;

    private Integer rorderItem;

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

    private String dlvType;

    private BigDecimal discount;

    private Date dlvDate;

    private Date cdlvDate;

    private String specOfferNo;

    private Short status;

    private Integer deleteStatus;

    private String sourceType;

    private String cproductNo;

    private String stockCode;

    private String stockType;

    private String specMark;

    private String remark;

    private String productName;

    private String opponent;

    private Date allotTime;

    private Date readyTime;

    private Date expTime;

    private Date shipTime;

    private Integer readyQty;

    private Integer expQty;

    private Integer returnedQty;

    private Integer allocatedQty;

    private String pplNo;

    private String projectNo;

    private String addressNo;

    private String customCode;

    private Integer version;

    private Date createTime;

    private Date updateTime;

    private String updateUser;

    private String createUser;

    private String customerNo;

    private String userNo;

    private String endUser;

    private Date rorddate;

    private String dlvEntire;

    private String dlvsite;

    private String transfee;

    private String transchannel;

    private String prjcode;

    private String ororderno;

    private String dlvtype;

    private String employee;

    private String employeeno;

    private Date opttime;

    private String operator;

    private String deptNo;

    private String contractno;

    private String quotationno;

    private String purchaseno;

    private String cttflag;

    private Boolean pricechecked;

    private String hlcode;

    private String tradeCompanyid;

    private Short orderType;

    private Date processDate;

    private String salesInfoNo;

    private String shikomiNo;

    private String corderNo;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRorderNo() {
        return rorderNo;
    }

    public void setRorderNo(String rorderNo) {
        this.rorderNo = rorderNo == null ? null : rorderNo.trim();
    }

    public Integer getRorderItem() {
        return rorderItem;
    }

    public void setRorderItem(Integer rorderItem) {
        this.rorderItem = rorderItem;
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

    public String getDlvType() {
        return dlvType;
    }

    public void setDlvType(String dlvType) {
        this.dlvType = dlvType == null ? null : dlvType.trim();
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

    public String getSpecOfferNo() {
        return specOfferNo;
    }

    public void setSpecOfferNo(String specOfferNo) {
        this.specOfferNo = specOfferNo == null ? null : specOfferNo.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
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

    public Integer getAllocatedQty() {
        return allocatedQty;
    }

    public void setAllocatedQty(Integer allocatedQty) {
        this.allocatedQty = allocatedQty;
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

    public String getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo == null ? null : addressNo.trim();
    }

    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode == null ? null : customCode.trim();
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

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser == null ? null : endUser.trim();
    }

    public Date getRorddate() {
        return rorddate;
    }

    public void setRorddate(Date rorddate) {
        this.rorddate = rorddate;
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire == null ? null : dlvEntire.trim();
    }

    public String getDlvsite() {
        return dlvsite;
    }

    public void setDlvsite(String dlvsite) {
        this.dlvsite = dlvsite == null ? null : dlvsite.trim();
    }

    public String getTransfee() {
        return transfee;
    }

    public void setTransfee(String transfee) {
        this.transfee = transfee == null ? null : transfee.trim();
    }

    public String getTranschannel() {
        return transchannel;
    }

    public void setTranschannel(String transchannel) {
        this.transchannel = transchannel == null ? null : transchannel.trim();
    }

    public String getPrjcode() {
        return prjcode;
    }

    public void setPrjcode(String prjcode) {
        this.prjcode = prjcode == null ? null : prjcode.trim();
    }

    public String getOrorderno() {
        return ororderno;
    }

    public void setOrorderno(String ororderno) {
        this.ororderno = ororderno == null ? null : ororderno.trim();
    }

    public String getDlvtype() {
        return dlvtype;
    }

    public void setDlvtype(String dlvtype) {
        this.dlvtype = dlvtype == null ? null : dlvtype.trim();
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee == null ? null : employee.trim();
    }

    public String getEmployeeno() {
        return employeeno;
    }

    public void setEmployeeno(String employeeno) {
        this.employeeno = employeeno == null ? null : employeeno.trim();
    }

    public Date getOpttime() {
        return opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno == null ? null : contractno.trim();
    }

    public String getQuotationno() {
        return quotationno;
    }

    public void setQuotationno(String quotationno) {
        this.quotationno = quotationno == null ? null : quotationno.trim();
    }

    public String getPurchaseno() {
        return purchaseno;
    }

    public void setPurchaseno(String purchaseno) {
        this.purchaseno = purchaseno == null ? null : purchaseno.trim();
    }

    public String getCttflag() {
        return cttflag;
    }

    public void setCttflag(String cttflag) {
        this.cttflag = cttflag == null ? null : cttflag.trim();
    }

    public Boolean getPricechecked() {
        return pricechecked;
    }

    public void setPricechecked(Boolean pricechecked) {
        this.pricechecked = pricechecked;
    }

    public String getHlcode() {
        return hlcode;
    }

    public void setHlcode(String hlcode) {
        this.hlcode = hlcode == null ? null : hlcode.trim();
    }

    public String getTradeCompanyid() {
        return tradeCompanyid;
    }

    public void setTradeCompanyid(String tradeCompanyid) {
        this.tradeCompanyid = tradeCompanyid == null ? null : tradeCompanyid.trim();
    }

    public Short getOrderType() {
        return orderType;
    }

    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo == null ? null : salesInfoNo.trim();
    }

    public String getShikomiNo() {
        return shikomiNo;
    }

    public void setShikomiNo(String shikomiNo) {
        this.shikomiNo = shikomiNo == null ? null : shikomiNo.trim();
    }

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo == null ? null : corderNo.trim();
    }
}