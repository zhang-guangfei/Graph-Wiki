package com.sales.ops.dto.order;

import java.util.Date;
import java.util.List;

/**
 * @author C12961
 * @date 2021/11/25 17:06
 */
public class ReceiveCondition {

    //接单号
    private String rorderNo;
    //detail信息

    //订单号
    private String rorderFno;

    //订单号
    private List<String> rorderFnoList;


    private Boolean intercept;

    //处理状态
    private Short  status;
    //拆分类型
    private  String prodFlag;
    //型号
    private String modelNo;
    //客户产品代码
    private String cproductNo;

    private Date[] shipTime;

    //master信息
    //客户代码
    private String customerNo;
    //用户代码
    private String userNo;
    //营业所代码
    private List<String> deptNo;
    //接单日期范围开始
    private Date[] rorddate;
    //客户采购单号
    private String purchaseno;
    private String stockType;
    private String stockCode;
    private String dlvEntire;


    //订单类型
    private Short orderType;

    private String corderNo;

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo;
    }

    public String getRorderNo() {
        return rorderNo;
    }

    public void setRorderNo(String rorderNo) {
        this.rorderNo = rorderNo;
    }

    public String getRorderFno() {
        return rorderFno;
    }

    public void setRorderFno(String rorderFno) {
        this.rorderFno = rorderFno;
    }

    public List<String> getRorderFnoList() {
        return rorderFnoList;
    }

    public void setRorderFnoList(List<String> rorderFnoList) {
        this.rorderFnoList = rorderFnoList;
    }

    public String getProdFlag() {
        return prodFlag;
    }

    public void setProdFlag(String prodFlag) {
        this.prodFlag = prodFlag;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo;
    }

    public Date[] getShipTime() {
        return shipTime;
    }

    public void setShipTime(Date[] shipTime) {
        this.shipTime = shipTime;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }


    public Date[] getRorddate() {
        return rorddate;
    }

    public void setRorddate(Date[] rorddate) {
        this.rorddate = rorddate;
    }

    public String getPurchaseno() {
        return purchaseno;
    }

    public void setPurchaseno(String purchaseno) {
        this.purchaseno = purchaseno;
    }

    public Short getOrderType() {
        return orderType;
    }

    public void setOrderType(Short orderType) {
        this.orderType = orderType;
    }


    public List<String> getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(List<String> deptNo) {
        this.deptNo = deptNo;
    }


    public Boolean getIntercept() {
        return intercept;
    }

    public void setIntercept(Boolean intercept) {
        this.intercept = intercept;
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire;
    }
}
