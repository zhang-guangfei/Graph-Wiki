package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsOrderAssignResult implements Serializable {
    private Long id;

    private String orderNo;

    private Integer orderItem;

    private Integer seqno;

    private String modelno;

    private Integer quantity;

    private String stockType;

    private String stockCode;

    private String inventoryTypeCode;

    private String associateNo;

    private Integer associateNoItem;

    private Integer associateNoSplitNo;

    private String supplierid;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer delflag;

    private Integer sourceType;

    private Boolean inventoryRisk;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public Integer getSeqno() {
        return seqno;
    }

    public void setSeqno(Integer seqno) {
        this.seqno = seqno;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode == null ? null : inventoryTypeCode.trim();
    }

    public String getAssociateNo() {
        return associateNo;
    }

    public void setAssociateNo(String associateNo) {
        this.associateNo = associateNo == null ? null : associateNo.trim();
    }

    public Integer getAssociateNoItem() {
        return associateNoItem;
    }

    public void setAssociateNoItem(Integer associateNoItem) {
        this.associateNoItem = associateNoItem;
    }

    public Integer getAssociateNoSplitNo() {
        return associateNoSplitNo;
    }

    public void setAssociateNoSplitNo(Integer associateNoSplitNo) {
        this.associateNoSplitNo = associateNoSplitNo;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
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

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Boolean getInventoryRisk() {
        return inventoryRisk;
    }

    public void setInventoryRisk(Boolean inventoryRisk) {
        this.inventoryRisk = inventoryRisk;
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
        OpsOrderAssignResult other = (OpsOrderAssignResult) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getSeqno() == null ? other.getSeqno() == null : this.getSeqno().equals(other.getSeqno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getStockType() == null ? other.getStockType() == null : this.getStockType().equals(other.getStockType()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getInventoryTypeCode() == null ? other.getInventoryTypeCode() == null : this.getInventoryTypeCode().equals(other.getInventoryTypeCode()))
            && (this.getAssociateNo() == null ? other.getAssociateNo() == null : this.getAssociateNo().equals(other.getAssociateNo()))
            && (this.getAssociateNoItem() == null ? other.getAssociateNoItem() == null : this.getAssociateNoItem().equals(other.getAssociateNoItem()))
            && (this.getAssociateNoSplitNo() == null ? other.getAssociateNoSplitNo() == null : this.getAssociateNoSplitNo().equals(other.getAssociateNoSplitNo()))
            && (this.getSupplierid() == null ? other.getSupplierid() == null : this.getSupplierid().equals(other.getSupplierid()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getSourceType() == null ? other.getSourceType() == null : this.getSourceType().equals(other.getSourceType()))
            && (this.getInventoryRisk() == null ? other.getInventoryRisk() == null : this.getInventoryRisk().equals(other.getInventoryRisk()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getSeqno() == null) ? 0 : getSeqno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getStockType() == null) ? 0 : getStockType().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getInventoryTypeCode() == null) ? 0 : getInventoryTypeCode().hashCode());
        result = prime * result + ((getAssociateNo() == null) ? 0 : getAssociateNo().hashCode());
        result = prime * result + ((getAssociateNoItem() == null) ? 0 : getAssociateNoItem().hashCode());
        result = prime * result + ((getAssociateNoSplitNo() == null) ? 0 : getAssociateNoSplitNo().hashCode());
        result = prime * result + ((getSupplierid() == null) ? 0 : getSupplierid().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getSourceType() == null) ? 0 : getSourceType().hashCode());
        result = prime * result + ((getInventoryRisk() == null) ? 0 : getInventoryRisk().hashCode());
        return result;
    }
}