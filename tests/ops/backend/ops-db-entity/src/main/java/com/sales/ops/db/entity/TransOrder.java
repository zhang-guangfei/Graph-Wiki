package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class TransOrder implements Serializable {
    private Long id;

    private Integer transType;

    private String transNo;

    private Integer itemNo;

    private String modelNo;

    private Integer quantity;

    private Integer status;

    private String fromNo;

    private Long fromId;

    private Integer fromType;

    private Long fromInventoryPropertyId;

    private String fromWarehouseCode;

    private String fromInventoryTypeCode;

    private String fromPpl;

    private String fromProjectCode;

    private String fromGroupCustomerNo;

    private String fromSalesInfoNo;

    private String fromCustomerNo;

    private Long toInventoryPropertyId;

    private String toWarehouseCode;

    private String toInventoryTypeCode;

    private String toPpl;

    private String toProjectCode;

    private String toGroupCustomerNo;

    private String toSalesInfoNo;

    private String toCustomerNo;

    private Integer inQuantity;

    private Date wmsDlvDate;

    private Date finishTime;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private String invoiceNo;

    private Integer shipQty;

    private Date shipDate;

    private Boolean transFlag;

    private Long fromInventoryId;

    private String fromAssociateNo;

    private Integer fromAssociateNoItem;

    private Integer fromAssociateNoSplit;

    private Long invoiceId;

    private String corderNo;
    private String cproductNo;

    public String getCorderNo() {
        return corderNo;
    }

    public void setCorderNo(String corderNo) {
        this.corderNo = corderNo;
    }

    public String getCproductNo() {
        return cproductNo;
    }

    public void setCproductNo(String cproductNo) {
        this.cproductNo = cproductNo;
    }

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo == null ? null : fromNo.trim();
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }

    public Long getFromInventoryPropertyId() {
        return fromInventoryPropertyId;
    }

    public void setFromInventoryPropertyId(Long fromInventoryPropertyId) {
        this.fromInventoryPropertyId = fromInventoryPropertyId;
    }

    public String getFromWarehouseCode() {
        return fromWarehouseCode;
    }

    public void setFromWarehouseCode(String fromWarehouseCode) {
        this.fromWarehouseCode = fromWarehouseCode == null ? null : fromWarehouseCode.trim();
    }

    public String getFromInventoryTypeCode() {
        return fromInventoryTypeCode;
    }

    public void setFromInventoryTypeCode(String fromInventoryTypeCode) {
        this.fromInventoryTypeCode = fromInventoryTypeCode == null ? null : fromInventoryTypeCode.trim();
    }

    public String getFromPpl() {
        return fromPpl;
    }

    public void setFromPpl(String fromPpl) {
        this.fromPpl = fromPpl == null ? null : fromPpl.trim();
    }

    public String getFromProjectCode() {
        return fromProjectCode;
    }

    public void setFromProjectCode(String fromProjectCode) {
        this.fromProjectCode = fromProjectCode == null ? null : fromProjectCode.trim();
    }

    public String getFromGroupCustomerNo() {
        return fromGroupCustomerNo;
    }

    public void setFromGroupCustomerNo(String fromGroupCustomerNo) {
        this.fromGroupCustomerNo = fromGroupCustomerNo == null ? null : fromGroupCustomerNo.trim();
    }

    public String getFromSalesInfoNo() {
        return fromSalesInfoNo;
    }

    public void setFromSalesInfoNo(String fromSalesInfoNo) {
        this.fromSalesInfoNo = fromSalesInfoNo == null ? null : fromSalesInfoNo.trim();
    }

    public String getFromCustomerNo() {
        return fromCustomerNo;
    }

    public void setFromCustomerNo(String fromCustomerNo) {
        this.fromCustomerNo = fromCustomerNo == null ? null : fromCustomerNo.trim();
    }

    public Long getToInventoryPropertyId() {
        return toInventoryPropertyId;
    }

    public void setToInventoryPropertyId(Long toInventoryPropertyId) {
        this.toInventoryPropertyId = toInventoryPropertyId;
    }

    public String getToWarehouseCode() {
        return toWarehouseCode;
    }

    public void setToWarehouseCode(String toWarehouseCode) {
        this.toWarehouseCode = toWarehouseCode == null ? null : toWarehouseCode.trim();
    }

    public String getToInventoryTypeCode() {
        return toInventoryTypeCode;
    }

    public void setToInventoryTypeCode(String toInventoryTypeCode) {
        this.toInventoryTypeCode = toInventoryTypeCode == null ? null : toInventoryTypeCode.trim();
    }

    public String getToPpl() {
        return toPpl;
    }

    public void setToPpl(String toPpl) {
        this.toPpl = toPpl == null ? null : toPpl.trim();
    }

    public String getToProjectCode() {
        return toProjectCode;
    }

    public void setToProjectCode(String toProjectCode) {
        this.toProjectCode = toProjectCode == null ? null : toProjectCode.trim();
    }

    public String getToGroupCustomerNo() {
        return toGroupCustomerNo;
    }

    public void setToGroupCustomerNo(String toGroupCustomerNo) {
        this.toGroupCustomerNo = toGroupCustomerNo == null ? null : toGroupCustomerNo.trim();
    }

    public String getToSalesInfoNo() {
        return toSalesInfoNo;
    }

    public void setToSalesInfoNo(String toSalesInfoNo) {
        this.toSalesInfoNo = toSalesInfoNo == null ? null : toSalesInfoNo.trim();
    }

    public String getToCustomerNo() {
        return toCustomerNo;
    }

    public void setToCustomerNo(String toCustomerNo) {
        this.toCustomerNo = toCustomerNo == null ? null : toCustomerNo.trim();
    }

    public Integer getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(Integer inQuantity) {
        this.inQuantity = inQuantity;
    }

    public Date getWmsDlvDate() {
        return wmsDlvDate;
    }

    public void setWmsDlvDate(Date wmsDlvDate) {
        this.wmsDlvDate = wmsDlvDate;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public Integer getShipQty() {
        return shipQty;
    }

    public void setShipQty(Integer shipQty) {
        this.shipQty = shipQty;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public Boolean getTransFlag() {
        return transFlag;
    }

    public void setTransFlag(Boolean transFlag) {
        this.transFlag = transFlag;
    }

    public Long getFromInventoryId() {
        return fromInventoryId;
    }

    public void setFromInventoryId(Long fromInventoryId) {
        this.fromInventoryId = fromInventoryId;
    }

    public String getFromAssociateNo() {
        return fromAssociateNo;
    }

    public void setFromAssociateNo(String fromAssociateNo) {
        this.fromAssociateNo = fromAssociateNo == null ? null : fromAssociateNo.trim();
    }

    public Integer getFromAssociateNoItem() {
        return fromAssociateNoItem;
    }

    public void setFromAssociateNoItem(Integer fromAssociateNoItem) {
        this.fromAssociateNoItem = fromAssociateNoItem;
    }

    public Integer getFromAssociateNoSplit() {
        return fromAssociateNoSplit;
    }

    public void setFromAssociateNoSplit(Integer fromAssociateNoSplit) {
        this.fromAssociateNoSplit = fromAssociateNoSplit;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
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
        TransOrder other = (TransOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTransType() == null ? other.getTransType() == null : this.getTransType().equals(other.getTransType()))
            && (this.getTransNo() == null ? other.getTransNo() == null : this.getTransNo().equals(other.getTransNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getFromNo() == null ? other.getFromNo() == null : this.getFromNo().equals(other.getFromNo()))
            && (this.getFromId() == null ? other.getFromId() == null : this.getFromId().equals(other.getFromId()))
            && (this.getFromType() == null ? other.getFromType() == null : this.getFromType().equals(other.getFromType()))
            && (this.getFromInventoryPropertyId() == null ? other.getFromInventoryPropertyId() == null : this.getFromInventoryPropertyId().equals(other.getFromInventoryPropertyId()))
            && (this.getFromWarehouseCode() == null ? other.getFromWarehouseCode() == null : this.getFromWarehouseCode().equals(other.getFromWarehouseCode()))
            && (this.getFromInventoryTypeCode() == null ? other.getFromInventoryTypeCode() == null : this.getFromInventoryTypeCode().equals(other.getFromInventoryTypeCode()))
            && (this.getFromPpl() == null ? other.getFromPpl() == null : this.getFromPpl().equals(other.getFromPpl()))
            && (this.getFromProjectCode() == null ? other.getFromProjectCode() == null : this.getFromProjectCode().equals(other.getFromProjectCode()))
            && (this.getFromGroupCustomerNo() == null ? other.getFromGroupCustomerNo() == null : this.getFromGroupCustomerNo().equals(other.getFromGroupCustomerNo()))
            && (this.getFromSalesInfoNo() == null ? other.getFromSalesInfoNo() == null : this.getFromSalesInfoNo().equals(other.getFromSalesInfoNo()))
            && (this.getFromCustomerNo() == null ? other.getFromCustomerNo() == null : this.getFromCustomerNo().equals(other.getFromCustomerNo()))
            && (this.getToInventoryPropertyId() == null ? other.getToInventoryPropertyId() == null : this.getToInventoryPropertyId().equals(other.getToInventoryPropertyId()))
            && (this.getToWarehouseCode() == null ? other.getToWarehouseCode() == null : this.getToWarehouseCode().equals(other.getToWarehouseCode()))
            && (this.getToInventoryTypeCode() == null ? other.getToInventoryTypeCode() == null : this.getToInventoryTypeCode().equals(other.getToInventoryTypeCode()))
            && (this.getToPpl() == null ? other.getToPpl() == null : this.getToPpl().equals(other.getToPpl()))
            && (this.getToProjectCode() == null ? other.getToProjectCode() == null : this.getToProjectCode().equals(other.getToProjectCode()))
            && (this.getToGroupCustomerNo() == null ? other.getToGroupCustomerNo() == null : this.getToGroupCustomerNo().equals(other.getToGroupCustomerNo()))
            && (this.getToSalesInfoNo() == null ? other.getToSalesInfoNo() == null : this.getToSalesInfoNo().equals(other.getToSalesInfoNo()))
            && (this.getToCustomerNo() == null ? other.getToCustomerNo() == null : this.getToCustomerNo().equals(other.getToCustomerNo()))
            && (this.getInQuantity() == null ? other.getInQuantity() == null : this.getInQuantity().equals(other.getInQuantity()))
            && (this.getWmsDlvDate() == null ? other.getWmsDlvDate() == null : this.getWmsDlvDate().equals(other.getWmsDlvDate()))
            && (this.getFinishTime() == null ? other.getFinishTime() == null : this.getFinishTime().equals(other.getFinishTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getInvoiceNo() == null ? other.getInvoiceNo() == null : this.getInvoiceNo().equals(other.getInvoiceNo()))
            && (this.getShipQty() == null ? other.getShipQty() == null : this.getShipQty().equals(other.getShipQty()))
            && (this.getShipDate() == null ? other.getShipDate() == null : this.getShipDate().equals(other.getShipDate()))
            && (this.getTransFlag() == null ? other.getTransFlag() == null : this.getTransFlag().equals(other.getTransFlag()))
            && (this.getFromInventoryId() == null ? other.getFromInventoryId() == null : this.getFromInventoryId().equals(other.getFromInventoryId()))
            && (this.getFromAssociateNo() == null ? other.getFromAssociateNo() == null : this.getFromAssociateNo().equals(other.getFromAssociateNo()))
            && (this.getFromAssociateNoItem() == null ? other.getFromAssociateNoItem() == null : this.getFromAssociateNoItem().equals(other.getFromAssociateNoItem()))
            && (this.getFromAssociateNoSplit() == null ? other.getFromAssociateNoSplit() == null : this.getFromAssociateNoSplit().equals(other.getFromAssociateNoSplit()))
            && (this.getInvoiceId() == null ? other.getInvoiceId() == null : this.getInvoiceId().equals(other.getInvoiceId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTransType() == null) ? 0 : getTransType().hashCode());
        result = prime * result + ((getTransNo() == null) ? 0 : getTransNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getFromNo() == null) ? 0 : getFromNo().hashCode());
        result = prime * result + ((getFromId() == null) ? 0 : getFromId().hashCode());
        result = prime * result + ((getFromType() == null) ? 0 : getFromType().hashCode());
        result = prime * result + ((getFromInventoryPropertyId() == null) ? 0 : getFromInventoryPropertyId().hashCode());
        result = prime * result + ((getFromWarehouseCode() == null) ? 0 : getFromWarehouseCode().hashCode());
        result = prime * result + ((getFromInventoryTypeCode() == null) ? 0 : getFromInventoryTypeCode().hashCode());
        result = prime * result + ((getFromPpl() == null) ? 0 : getFromPpl().hashCode());
        result = prime * result + ((getFromProjectCode() == null) ? 0 : getFromProjectCode().hashCode());
        result = prime * result + ((getFromGroupCustomerNo() == null) ? 0 : getFromGroupCustomerNo().hashCode());
        result = prime * result + ((getFromSalesInfoNo() == null) ? 0 : getFromSalesInfoNo().hashCode());
        result = prime * result + ((getFromCustomerNo() == null) ? 0 : getFromCustomerNo().hashCode());
        result = prime * result + ((getToInventoryPropertyId() == null) ? 0 : getToInventoryPropertyId().hashCode());
        result = prime * result + ((getToWarehouseCode() == null) ? 0 : getToWarehouseCode().hashCode());
        result = prime * result + ((getToInventoryTypeCode() == null) ? 0 : getToInventoryTypeCode().hashCode());
        result = prime * result + ((getToPpl() == null) ? 0 : getToPpl().hashCode());
        result = prime * result + ((getToProjectCode() == null) ? 0 : getToProjectCode().hashCode());
        result = prime * result + ((getToGroupCustomerNo() == null) ? 0 : getToGroupCustomerNo().hashCode());
        result = prime * result + ((getToSalesInfoNo() == null) ? 0 : getToSalesInfoNo().hashCode());
        result = prime * result + ((getToCustomerNo() == null) ? 0 : getToCustomerNo().hashCode());
        result = prime * result + ((getInQuantity() == null) ? 0 : getInQuantity().hashCode());
        result = prime * result + ((getWmsDlvDate() == null) ? 0 : getWmsDlvDate().hashCode());
        result = prime * result + ((getFinishTime() == null) ? 0 : getFinishTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getInvoiceNo() == null) ? 0 : getInvoiceNo().hashCode());
        result = prime * result + ((getShipQty() == null) ? 0 : getShipQty().hashCode());
        result = prime * result + ((getShipDate() == null) ? 0 : getShipDate().hashCode());
        result = prime * result + ((getTransFlag() == null) ? 0 : getTransFlag().hashCode());
        result = prime * result + ((getFromInventoryId() == null) ? 0 : getFromInventoryId().hashCode());
        result = prime * result + ((getFromAssociateNo() == null) ? 0 : getFromAssociateNo().hashCode());
        result = prime * result + ((getFromAssociateNoItem() == null) ? 0 : getFromAssociateNoItem().hashCode());
        result = prime * result + ((getFromAssociateNoSplit() == null) ? 0 : getFromAssociateNoSplit().hashCode());
        result = prime * result + ((getInvoiceId() == null) ? 0 : getInvoiceId().hashCode());
        return result;
    }
}