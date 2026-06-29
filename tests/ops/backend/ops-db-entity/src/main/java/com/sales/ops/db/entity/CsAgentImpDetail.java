package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class CsAgentImpDetail implements Serializable {
    private Long id;

    private String barcode;

    private String agentNo;

    private String stockCode;

    private String caseNo;

    private String orderNo;

    private String modelNo;

    private Date inTime;

    private String inUsername;

    private Integer quantity;

    private String locationno;

    private Integer remainQty;

    private Integer status;

    private Integer prepareQty;

    private String inventoryKeys;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getAgentNo() {
        return agentNo;
    }

    public void setAgentNo(String agentNo) {
        this.agentNo = agentNo == null ? null : agentNo.trim();
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo == null ? null : caseNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getInUsername() {
        return inUsername;
    }

    public void setInUsername(String inUsername) {
        this.inUsername = inUsername == null ? null : inUsername.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLocationno() {
        return locationno;
    }

    public void setLocationno(String locationno) {
        this.locationno = locationno == null ? null : locationno.trim();
    }

    public Integer getRemainQty() {
        return remainQty;
    }

    public void setRemainQty(Integer remainQty) {
        this.remainQty = remainQty;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPrepareQty() {
        return prepareQty;
    }

    public void setPrepareQty(Integer prepareQty) {
        this.prepareQty = prepareQty;
    }

    public String getInventoryKeys() {
        return inventoryKeys;
    }

    public void setInventoryKeys(String inventoryKeys) {
        this.inventoryKeys = inventoryKeys == null ? null : inventoryKeys.trim();
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
        this.createUser = createUser == null ? null : createUser.trim();
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
}