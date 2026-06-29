package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class RcvView implements Serializable {
    private String deliveryDeptNo;

    private String deptNo;

    private String rorderNo;

    private Integer rorderItem;

    private String rorderFno;

    private String modelNo;

    private Integer quantity;

    private Short status;

    private Boolean intercept;

    private String customerNo;

    private String userNo;

    private String endUser;

    private String corderNo;

    private String stockType;

    private String stockCode;

    private String prodFlag;

    private Integer readyQty;

    private Integer expQty;

    private Integer returnedQty;

    private Integer invoiceQty;

    private Short orderType;

    private Date rorddate;

    private Date dlvDate;

    private Date wmsDlvDate;

    private Date cdlvDate;

    private String dlvEntire;

    private Date readyTime;

    private Date shipTime;

    private String specMark;

    private Integer expDlvType;

    private String remark;

    private String tradeCompanyid;

    private String dlvtype;

    private String purchaseno;

    private String cproductNo;

    private String dlvsite;

    private String groupCustomerNo;

    private String pplNo;

    private String projectNo;

    private String shikomiNo;

    private String salesInfoNo;

    private Date expectedDeliveryTime;

    private Date estimatedDeliveryDay;

    private Integer hasLowPrice;

    private String purchaseUnitCode;

    private String extOrderNo;

    private String extOrderItem;

    private Date allotStartTime;

    private Date allotEndTime;

    private static final long serialVersionUID = 1L;

    public String getDeliveryDeptNo() {
        return deliveryDeptNo;
    }

    public void setDeliveryDeptNo(String deliveryDeptNo) {
        this.deliveryDeptNo = deliveryDeptNo == null ? null : deliveryDeptNo.trim();
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Boolean getIntercept() {
        return intercept;
    }

    public void setIntercept(Boolean intercept) {
        this.intercept = intercept;
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

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo == null ? null : corderNo.trim();
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType == null ? null : stockType.trim();
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public String getProdFlag() {
        return prodFlag;
    }

    public void setProdFlag(String prodFlag) {
        this.prodFlag = prodFlag == null ? null : prodFlag.trim();
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

    public Integer getInvoiceQty() {
        return invoiceQty;
    }

    public void setInvoiceQty(Integer invoiceQty) {
        this.invoiceQty = invoiceQty;
    }

    public Short getOrderType() {
        return orderType;
    }

    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }

    public Date getRorddate() {
        return rorddate;
    }

    public void setRorddate(Date rorddate) {
        this.rorddate = rorddate;
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public Date getWmsDlvDate() {
        return wmsDlvDate;
    }

    public void setWmsDlvDate(Date wmsDlvDate) {
        this.wmsDlvDate = wmsDlvDate;
    }

    public Date getCdlvDate() {
        return cdlvDate;
    }

    public void setCdlvDate(Date cdlvDate) {
        this.cdlvDate = cdlvDate;
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire == null ? null : dlvEntire.trim();
    }

    public Date getReadyTime() {
        return readyTime;
    }

    public void setReadyTime(Date readyTime) {
        this.readyTime = readyTime;
    }

    public Date getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date shipTime) {
        this.shipTime = shipTime;
    }

    public String getSpecMark() {
        return specMark;
    }

    public void setSpecMark(String specMark) {
        this.specMark = specMark == null ? null : specMark.trim();
    }

    public Integer getExpDlvType() {
        return expDlvType;
    }

    public void setExpDlvType(Integer expDlvType) {
        this.expDlvType = expDlvType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getTradeCompanyid() {
        return tradeCompanyid;
    }

    public void setTradeCompanyid(String tradeCompanyid) {
        this.tradeCompanyid = tradeCompanyid == null ? null : tradeCompanyid.trim();
    }

    public String getDlvtype() {
        return dlvtype;
    }

    public void setDlvtype(String dlvtype) {
        this.dlvtype = dlvtype == null ? null : dlvtype.trim();
    }

    public String getPurchaseno() {
        return purchaseno;
    }

    public void setPurchaseno(String purchaseno) {
        this.purchaseno = purchaseno == null ? null : purchaseno.trim();
    }

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo == null ? null : cproductNo.trim();
    }

    public String getDlvsite() {
        return dlvsite;
    }

    public void setDlvsite(String dlvsite) {
        this.dlvsite = dlvsite == null ? null : dlvsite.trim();
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo == null ? null : groupCustomerNo.trim();
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

    public Integer getHasLowPrice() {
        return hasLowPrice;
    }

    public void setHasLowPrice(Integer hasLowPrice) {
        this.hasLowPrice = hasLowPrice;
    }

    public String getPurchaseUnitCode() {
        return purchaseUnitCode;
    }

    public void setPurchaseUnitCode(String purchaseUnitCode) {
        this.purchaseUnitCode = purchaseUnitCode == null ? null : purchaseUnitCode.trim();
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

    public Date getAllotStartTime() {
        return allotStartTime;
    }

    public void setAllotStartTime(Date allotStartTime) {
        this.allotStartTime = allotStartTime;
    }

    public Date getAllotEndTime() {
        return allotEndTime;
    }

    public void setAllotEndTime(Date allotEndTime) {
        this.allotEndTime = allotEndTime;
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
        RcvView other = (RcvView) that;
        return (this.getDeliveryDeptNo() == null ? other.getDeliveryDeptNo() == null : this.getDeliveryDeptNo().equals(other.getDeliveryDeptNo()))
            && (this.getDeptNo() == null ? other.getDeptNo() == null : this.getDeptNo().equals(other.getDeptNo()))
            && (this.getRorderNo() == null ? other.getRorderNo() == null : this.getRorderNo().equals(other.getRorderNo()))
            && (this.getRorderItem() == null ? other.getRorderItem() == null : this.getRorderItem().equals(other.getRorderItem()))
            && (this.getRorderFno() == null ? other.getRorderFno() == null : this.getRorderFno().equals(other.getRorderFno()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIntercept() == null ? other.getIntercept() == null : this.getIntercept().equals(other.getIntercept()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
            && (this.getEndUser() == null ? other.getEndUser() == null : this.getEndUser().equals(other.getEndUser()))
            && (this.getCorderNo() == null ? other.getCorderNo() == null : this.getCorderNo().equals(other.getCorderNo()))
            && (this.getStockType() == null ? other.getStockType() == null : this.getStockType().equals(other.getStockType()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getProdFlag() == null ? other.getProdFlag() == null : this.getProdFlag().equals(other.getProdFlag()))
            && (this.getReadyQty() == null ? other.getReadyQty() == null : this.getReadyQty().equals(other.getReadyQty()))
            && (this.getExpQty() == null ? other.getExpQty() == null : this.getExpQty().equals(other.getExpQty()))
            && (this.getReturnedQty() == null ? other.getReturnedQty() == null : this.getReturnedQty().equals(other.getReturnedQty()))
            && (this.getInvoiceQty() == null ? other.getInvoiceQty() == null : this.getInvoiceQty().equals(other.getInvoiceQty()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getRorddate() == null ? other.getRorddate() == null : this.getRorddate().equals(other.getRorddate()))
            && (this.getDlvDate() == null ? other.getDlvDate() == null : this.getDlvDate().equals(other.getDlvDate()))
            && (this.getWmsDlvDate() == null ? other.getWmsDlvDate() == null : this.getWmsDlvDate().equals(other.getWmsDlvDate()))
            && (this.getCdlvDate() == null ? other.getCdlvDate() == null : this.getCdlvDate().equals(other.getCdlvDate()))
            && (this.getDlvEntire() == null ? other.getDlvEntire() == null : this.getDlvEntire().equals(other.getDlvEntire()))
            && (this.getReadyTime() == null ? other.getReadyTime() == null : this.getReadyTime().equals(other.getReadyTime()))
            && (this.getShipTime() == null ? other.getShipTime() == null : this.getShipTime().equals(other.getShipTime()))
            && (this.getSpecMark() == null ? other.getSpecMark() == null : this.getSpecMark().equals(other.getSpecMark()))
            && (this.getExpDlvType() == null ? other.getExpDlvType() == null : this.getExpDlvType().equals(other.getExpDlvType()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getTradeCompanyid() == null ? other.getTradeCompanyid() == null : this.getTradeCompanyid().equals(other.getTradeCompanyid()))
            && (this.getDlvtype() == null ? other.getDlvtype() == null : this.getDlvtype().equals(other.getDlvtype()))
            && (this.getPurchaseno() == null ? other.getPurchaseno() == null : this.getPurchaseno().equals(other.getPurchaseno()))
            && (this.getCproductNo() == null ? other.getCproductNo() == null : this.getCproductNo().equals(other.getCproductNo()))
            && (this.getDlvsite() == null ? other.getDlvsite() == null : this.getDlvsite().equals(other.getDlvsite()))
            && (this.getGroupCustomerNo() == null ? other.getGroupCustomerNo() == null : this.getGroupCustomerNo().equals(other.getGroupCustomerNo()))
            && (this.getPplNo() == null ? other.getPplNo() == null : this.getPplNo().equals(other.getPplNo()))
            && (this.getProjectNo() == null ? other.getProjectNo() == null : this.getProjectNo().equals(other.getProjectNo()))
            && (this.getShikomiNo() == null ? other.getShikomiNo() == null : this.getShikomiNo().equals(other.getShikomiNo()))
            && (this.getSalesInfoNo() == null ? other.getSalesInfoNo() == null : this.getSalesInfoNo().equals(other.getSalesInfoNo()))
            && (this.getExpectedDeliveryTime() == null ? other.getExpectedDeliveryTime() == null : this.getExpectedDeliveryTime().equals(other.getExpectedDeliveryTime()))
            && (this.getEstimatedDeliveryDay() == null ? other.getEstimatedDeliveryDay() == null : this.getEstimatedDeliveryDay().equals(other.getEstimatedDeliveryDay()))
            && (this.getHasLowPrice() == null ? other.getHasLowPrice() == null : this.getHasLowPrice().equals(other.getHasLowPrice()))
            && (this.getPurchaseUnitCode() == null ? other.getPurchaseUnitCode() == null : this.getPurchaseUnitCode().equals(other.getPurchaseUnitCode()))
            && (this.getExtOrderNo() == null ? other.getExtOrderNo() == null : this.getExtOrderNo().equals(other.getExtOrderNo()))
            && (this.getExtOrderItem() == null ? other.getExtOrderItem() == null : this.getExtOrderItem().equals(other.getExtOrderItem()))
            && (this.getAllotStartTime() == null ? other.getAllotStartTime() == null : this.getAllotStartTime().equals(other.getAllotStartTime()))
            && (this.getAllotEndTime() == null ? other.getAllotEndTime() == null : this.getAllotEndTime().equals(other.getAllotEndTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeliveryDeptNo() == null) ? 0 : getDeliveryDeptNo().hashCode());
        result = prime * result + ((getDeptNo() == null) ? 0 : getDeptNo().hashCode());
        result = prime * result + ((getRorderNo() == null) ? 0 : getRorderNo().hashCode());
        result = prime * result + ((getRorderItem() == null) ? 0 : getRorderItem().hashCode());
        result = prime * result + ((getRorderFno() == null) ? 0 : getRorderFno().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIntercept() == null) ? 0 : getIntercept().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        result = prime * result + ((getEndUser() == null) ? 0 : getEndUser().hashCode());
        result = prime * result + ((getCorderNo() == null) ? 0 : getCorderNo().hashCode());
        result = prime * result + ((getStockType() == null) ? 0 : getStockType().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getProdFlag() == null) ? 0 : getProdFlag().hashCode());
        result = prime * result + ((getReadyQty() == null) ? 0 : getReadyQty().hashCode());
        result = prime * result + ((getExpQty() == null) ? 0 : getExpQty().hashCode());
        result = prime * result + ((getReturnedQty() == null) ? 0 : getReturnedQty().hashCode());
        result = prime * result + ((getInvoiceQty() == null) ? 0 : getInvoiceQty().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getRorddate() == null) ? 0 : getRorddate().hashCode());
        result = prime * result + ((getDlvDate() == null) ? 0 : getDlvDate().hashCode());
        result = prime * result + ((getWmsDlvDate() == null) ? 0 : getWmsDlvDate().hashCode());
        result = prime * result + ((getCdlvDate() == null) ? 0 : getCdlvDate().hashCode());
        result = prime * result + ((getDlvEntire() == null) ? 0 : getDlvEntire().hashCode());
        result = prime * result + ((getReadyTime() == null) ? 0 : getReadyTime().hashCode());
        result = prime * result + ((getShipTime() == null) ? 0 : getShipTime().hashCode());
        result = prime * result + ((getSpecMark() == null) ? 0 : getSpecMark().hashCode());
        result = prime * result + ((getExpDlvType() == null) ? 0 : getExpDlvType().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getTradeCompanyid() == null) ? 0 : getTradeCompanyid().hashCode());
        result = prime * result + ((getDlvtype() == null) ? 0 : getDlvtype().hashCode());
        result = prime * result + ((getPurchaseno() == null) ? 0 : getPurchaseno().hashCode());
        result = prime * result + ((getCproductNo() == null) ? 0 : getCproductNo().hashCode());
        result = prime * result + ((getDlvsite() == null) ? 0 : getDlvsite().hashCode());
        result = prime * result + ((getGroupCustomerNo() == null) ? 0 : getGroupCustomerNo().hashCode());
        result = prime * result + ((getPplNo() == null) ? 0 : getPplNo().hashCode());
        result = prime * result + ((getProjectNo() == null) ? 0 : getProjectNo().hashCode());
        result = prime * result + ((getShikomiNo() == null) ? 0 : getShikomiNo().hashCode());
        result = prime * result + ((getSalesInfoNo() == null) ? 0 : getSalesInfoNo().hashCode());
        result = prime * result + ((getExpectedDeliveryTime() == null) ? 0 : getExpectedDeliveryTime().hashCode());
        result = prime * result + ((getEstimatedDeliveryDay() == null) ? 0 : getEstimatedDeliveryDay().hashCode());
        result = prime * result + ((getHasLowPrice() == null) ? 0 : getHasLowPrice().hashCode());
        result = prime * result + ((getPurchaseUnitCode() == null) ? 0 : getPurchaseUnitCode().hashCode());
        result = prime * result + ((getExtOrderNo() == null) ? 0 : getExtOrderNo().hashCode());
        result = prime * result + ((getExtOrderItem() == null) ? 0 : getExtOrderItem().hashCode());
        result = prime * result + ((getAllotStartTime() == null) ? 0 : getAllotStartTime().hashCode());
        result = prime * result + ((getAllotEndTime() == null) ? 0 : getAllotEndTime().hashCode());
        return result;
    }
}