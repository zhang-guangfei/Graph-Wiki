package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsRo implements Serializable {
    private Long id;

    private String roId;

    private String roSource;

    private String roType;

    private String invoiceNo;

    private String orderId;

    private String orderItem;

    private Integer num;

    private Integer assNum;

    private String warehouseCode;

    private String customerNo;

    private String supplierId;

    private Integer roStatus;

    private String transType;

    private String carried;

    private String expressCode;

    private String remark;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private Integer version;

    private Integer extNum;

    private Integer isWms;

    private String userNo;

    private String extDoId;

    private Long invoiceId;

    private Date receivetime;

    private String username;

    private Boolean isSign;

    private String signUsername;

    private Date signTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoId() {
        return roId;
    }

    public void setRoId(String roId) {
        this.roId = roId == null ? null : roId.trim();
    }

    public String getRoSource() {
        return roSource;
    }

    public void setRoSource(String roSource) {
        this.roSource = roSource == null ? null : roSource.trim();
    }

    public String getRoType() {
        return roType;
    }

    public void setRoType(String roType) {
        this.roType = roType == null ? null : roType.trim();
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem == null ? null : orderItem.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAssNum() {
        return assNum;
    }

    public void setAssNum(Integer assNum) {
        this.assNum = assNum;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public Integer getRoStatus() {
        return roStatus;
    }

    public void setRoStatus(Integer roStatus) {
        this.roStatus = roStatus;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getCarried() {
        return carried;
    }

    public void setCarried(String carried) {
        this.carried = carried == null ? null : carried.trim();
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode == null ? null : expressCode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getExtNum() {
        return extNum;
    }

    public void setExtNum(Integer extNum) {
        this.extNum = extNum;
    }

    public Integer getIsWms() {
        return isWms;
    }

    public void setIsWms(Integer isWms) {
        this.isWms = isWms;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getExtDoId() {
        return extDoId;
    }

    public void setExtDoId(String extDoId) {
        this.extDoId = extDoId == null ? null : extDoId.trim();
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(Date receivetime) {
        this.receivetime = receivetime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Boolean getIsSign() {
        return isSign;
    }

    public void setIsSign(Boolean isSign) {
        this.isSign = isSign;
    }

    public String getSignUsername() {
        return signUsername;
    }

    public void setSignUsername(String signUsername) {
        this.signUsername = signUsername == null ? null : signUsername.trim();
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
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
        OpsRo other = (OpsRo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoId() == null ? other.getRoId() == null : this.getRoId().equals(other.getRoId()))
            && (this.getRoSource() == null ? other.getRoSource() == null : this.getRoSource().equals(other.getRoSource()))
            && (this.getRoType() == null ? other.getRoType() == null : this.getRoType().equals(other.getRoType()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()))
            && (this.getAssNum() == null ? other.getAssNum() == null : this.getAssNum().equals(other.getAssNum()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getSupplierId() == null ? other.getSupplierId() == null : this.getSupplierId().equals(other.getSupplierId()))
            && (this.getRoStatus() == null ? other.getRoStatus() == null : this.getRoStatus().equals(other.getRoStatus()))
            && (this.getTransType() == null ? other.getTransType() == null : this.getTransType().equals(other.getTransType()))
            && (this.getCarried() == null ? other.getCarried() == null : this.getCarried().equals(other.getCarried()))
            && (this.getExpressCode() == null ? other.getExpressCode() == null : this.getExpressCode().equals(other.getExpressCode()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getExtNum() == null ? other.getExtNum() == null : this.getExtNum().equals(other.getExtNum()))
            && (this.getIsWms() == null ? other.getIsWms() == null : this.getIsWms().equals(other.getIsWms()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
            && (this.getExtDoId() == null ? other.getExtDoId() == null : this.getExtDoId().equals(other.getExtDoId()))
            && (this.getInvoiceId() == null ? other.getInvoiceId() == null : this.getInvoiceId().equals(other.getInvoiceId()))
            && (this.getReceivetime() == null ? other.getReceivetime() == null : this.getReceivetime().equals(other.getReceivetime()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getIsSign() == null ? other.getIsSign() == null : this.getIsSign().equals(other.getIsSign()))
            && (this.getSignUsername() == null ? other.getSignUsername() == null : this.getSignUsername().equals(other.getSignUsername()))
            && (this.getSignTime() == null ? other.getSignTime() == null : this.getSignTime().equals(other.getSignTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoId() == null) ? 0 : getRoId().hashCode());
        result = prime * result + ((getRoSource() == null) ? 0 : getRoSource().hashCode());
        result = prime * result + ((getRoType() == null) ? 0 : getRoType().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getAssNum() == null) ? 0 : getAssNum().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getSupplierId() == null) ? 0 : getSupplierId().hashCode());
        result = prime * result + ((getRoStatus() == null) ? 0 : getRoStatus().hashCode());
        result = prime * result + ((getTransType() == null) ? 0 : getTransType().hashCode());
        result = prime * result + ((getCarried() == null) ? 0 : getCarried().hashCode());
        result = prime * result + ((getExpressCode() == null) ? 0 : getExpressCode().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getExtNum() == null) ? 0 : getExtNum().hashCode());
        result = prime * result + ((getIsWms() == null) ? 0 : getIsWms().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        result = prime * result + ((getExtDoId() == null) ? 0 : getExtDoId().hashCode());
        result = prime * result + ((getInvoiceId() == null) ? 0 : getInvoiceId().hashCode());
        result = prime * result + ((getReceivetime() == null) ? 0 : getReceivetime().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getIsSign() == null) ? 0 : getIsSign().hashCode());
        result = prime * result + ((getSignUsername() == null) ? 0 : getSignUsername().hashCode());
        result = prime * result + ((getSignTime() == null) ? 0 : getSignTime().hashCode());
        return result;
    }
}