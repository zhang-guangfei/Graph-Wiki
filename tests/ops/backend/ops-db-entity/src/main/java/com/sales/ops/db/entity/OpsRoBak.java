package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsRoBak implements Serializable {
    private String roId;

    private String roSource;

    private String bindOrderId;

    private String roType;

    private String subOrderId;

    private String warehouseCode;

    private Integer roStatus;

    private String carried;

    private String expressCode;

    private String remark;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private String customerNo;

    private String invoiceNo;

    private String supplierId;

    private String transType;

    private Integer wmsStatus;

    private String matchPcoId;

    private Integer version;

    private String orderId;

    private String orderItem;

    private static final long serialVersionUID = 1L;

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

    public String getBindOrderId() {
        return bindOrderId;
    }

    public void setBindOrderId(String bindOrderId) {
        this.bindOrderId = bindOrderId == null ? null : bindOrderId.trim();
    }

    public String getRoType() {
        return roType;
    }

    public void setRoType(String roType) {
        this.roType = roType == null ? null : roType.trim();
    }

    public String getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId == null ? null : subOrderId.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public Integer getRoStatus() {
        return roStatus;
    }

    public void setRoStatus(Integer roStatus) {
        this.roStatus = roStatus;
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

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public Integer getWmsStatus() {
        return wmsStatus;
    }

    public void setWmsStatus(Integer wmsStatus) {
        this.wmsStatus = wmsStatus;
    }

    public String getMatchPcoId() {
        return matchPcoId;
    }

    public void setMatchPcoId(String matchPcoId) {
        this.matchPcoId = matchPcoId == null ? null : matchPcoId.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
        OpsRoBak other = (OpsRoBak) that;
        return (this.getRoId() == null ? other.getRoId() == null : this.getRoId().equals(other.getRoId()))
            && (this.getRoSource() == null ? other.getRoSource() == null : this.getRoSource().equals(other.getRoSource()))
            && (this.getBindOrderId() == null ? other.getBindOrderId() == null : this.getBindOrderId().equals(other.getBindOrderId()))
            && (this.getRoType() == null ? other.getRoType() == null : this.getRoType().equals(other.getRoType()))
            && (this.getSubOrderId() == null ? other.getSubOrderId() == null : this.getSubOrderId().equals(other.getSubOrderId()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getRoStatus() == null ? other.getRoStatus() == null : this.getRoStatus().equals(other.getRoStatus()))
            && (this.getCarried() == null ? other.getCarried() == null : this.getCarried().equals(other.getCarried()))
            && (this.getExpressCode() == null ? other.getExpressCode() == null : this.getExpressCode().equals(other.getExpressCode()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getSupplierId() == null ? other.getSupplierId() == null : this.getSupplierId().equals(other.getSupplierId()))
            && (this.getTransType() == null ? other.getTransType() == null : this.getTransType().equals(other.getTransType()))
            && (this.getWmsStatus() == null ? other.getWmsStatus() == null : this.getWmsStatus().equals(other.getWmsStatus()))
            && (this.getMatchPcoId() == null ? other.getMatchPcoId() == null : this.getMatchPcoId().equals(other.getMatchPcoId()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoId() == null) ? 0 : getRoId().hashCode());
        result = prime * result + ((getRoSource() == null) ? 0 : getRoSource().hashCode());
        result = prime * result + ((getBindOrderId() == null) ? 0 : getBindOrderId().hashCode());
        result = prime * result + ((getRoType() == null) ? 0 : getRoType().hashCode());
        result = prime * result + ((getSubOrderId() == null) ? 0 : getSubOrderId().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getRoStatus() == null) ? 0 : getRoStatus().hashCode());
        result = prime * result + ((getCarried() == null) ? 0 : getCarried().hashCode());
        result = prime * result + ((getExpressCode() == null) ? 0 : getExpressCode().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getSupplierId() == null) ? 0 : getSupplierId().hashCode());
        result = prime * result + ((getTransType() == null) ? 0 : getTransType().hashCode());
        result = prime * result + ((getWmsStatus() == null) ? 0 : getWmsStatus().hashCode());
        result = prime * result + ((getMatchPcoId() == null) ? 0 : getMatchPcoId().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        return result;
    }
}