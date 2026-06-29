package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class CsAgentExpData implements Serializable {
    private Long id;

    private String orderFno;

    private String rorderNo;

    private String agentNo;

    private String userNo;

    private String modelNo;

    private Integer quantity;

    private String stockCode;

    private String orderUser;

    private Date orderDate;

    private Short status;

    private Short optCode;

    private String dnNo;

    private String expUser;

    private Date exportDate;

    private Date finishDate;

    private Date cancelDate;

    private String cancelUser;

    private Integer printTimes;

    private String addressNo;

    private String stockCustomerNo;

    private String dlvType1;

    private String dlvType2;

    private Date specDlvdate;

    private String packType;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno == null ? null : orderFno.trim();
    }

    public String getRorderNo() {
        return rorderNo;
    }

    public void setRorderNo(String rorderNo) {
        this.rorderNo = rorderNo == null ? null : rorderNo.trim();
    }

    public String getAgentNo() {
        return agentNo;
    }

    public void setAgentNo(String agentNo) {
        this.agentNo = agentNo == null ? null : agentNo.trim();
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
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

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser == null ? null : orderUser.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getOptCode() {
        return optCode;
    }

    public void setOptCode(Short optCode) {
        this.optCode = optCode;
    }

    public String getDnNo() {
        return dnNo;
    }

    public void setDnNo(String dnNo) {
        this.dnNo = dnNo == null ? null : dnNo.trim();
    }

    public String getExpUser() {
        return expUser;
    }

    public void setExpUser(String expUser) {
        this.expUser = expUser == null ? null : expUser.trim();
    }

    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getCancelUser() {
        return cancelUser;
    }

    public void setCancelUser(String cancelUser) {
        this.cancelUser = cancelUser == null ? null : cancelUser.trim();
    }

    public Integer getPrintTimes() {
        return printTimes;
    }

    public void setPrintTimes(Integer printTimes) {
        this.printTimes = printTimes;
    }

    public String getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo == null ? null : addressNo.trim();
    }

    public String getStockCustomerNo() {
        return stockCustomerNo;
    }

    public void setStockCustomerNo(String stockCustomerNo) {
        this.stockCustomerNo = stockCustomerNo == null ? null : stockCustomerNo.trim();
    }

    public String getDlvType1() {
        return dlvType1;
    }

    public void setDlvType1(String dlvType1) {
        this.dlvType1 = dlvType1 == null ? null : dlvType1.trim();
    }

    public String getDlvType2() {
        return dlvType2;
    }

    public void setDlvType2(String dlvType2) {
        this.dlvType2 = dlvType2 == null ? null : dlvType2.trim();
    }

    public Date getSpecDlvdate() {
        return specDlvdate;
    }

    public void setSpecDlvdate(Date specDlvdate) {
        this.specDlvdate = specDlvdate;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType == null ? null : packType.trim();
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}