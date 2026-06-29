package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPoUnusualOrderLog implements Serializable {
    private Long id;

    private String supplierCode;

    private String sourceType;

    private String sourceId;

    private String pono;

    private Integer lineItem;

    private String supplierOrderNo;

    private String supplierLineItemNo;

    private String modelNo;

    private Integer quantity;

    private String orderUnusualReasonCode;

    private String srOrderUnusualReasonCode;

    private String orderUnusualReason;

    private Date supplierSystemExecTime;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer delFlag;

    private Integer unusualType;

    private String srcReplyContent;

    private String srcReplyDate;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    public String getPono() {
        return pono;
    }

    public void setPono(String pono) {
        this.pono = pono == null ? null : pono.trim();
    }

    public Integer getLineItem() {
        return lineItem;
    }

    public void setLineItem(Integer lineItem) {
        this.lineItem = lineItem;
    }

    public String getSupplierOrderNo() {
        return supplierOrderNo;
    }

    public void setSupplierOrderNo(String supplierOrderNo) {
        this.supplierOrderNo = supplierOrderNo == null ? null : supplierOrderNo.trim();
    }

    public String getSupplierLineItemNo() {
        return supplierLineItemNo;
    }

    public void setSupplierLineItemNo(String supplierLineItemNo) {
        this.supplierLineItemNo = supplierLineItemNo == null ? null : supplierLineItemNo.trim();
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

    public String getOrderUnusualReasonCode() {
        return orderUnusualReasonCode;
    }

    public void setOrderUnusualReasonCode(String orderUnusualReasonCode) {
        this.orderUnusualReasonCode = orderUnusualReasonCode == null ? null : orderUnusualReasonCode.trim();
    }

    public String getSrOrderUnusualReasonCode() {
        return srOrderUnusualReasonCode;
    }

    public void setSrOrderUnusualReasonCode(String srOrderUnusualReasonCode) {
        this.srOrderUnusualReasonCode = srOrderUnusualReasonCode == null ? null : srOrderUnusualReasonCode.trim();
    }

    public String getOrderUnusualReason() {
        return orderUnusualReason;
    }

    public void setOrderUnusualReason(String orderUnusualReason) {
        this.orderUnusualReason = orderUnusualReason == null ? null : orderUnusualReason.trim();
    }

    public Date getSupplierSystemExecTime() {
        return supplierSystemExecTime;
    }

    public void setSupplierSystemExecTime(Date supplierSystemExecTime) {
        this.supplierSystemExecTime = supplierSystemExecTime;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getUnusualType() {
        return unusualType;
    }

    public void setUnusualType(Integer unusualType) {
        this.unusualType = unusualType;
    }

    public String getSrcReplyContent() {
        return srcReplyContent;
    }

    public void setSrcReplyContent(String srcReplyContent) {
        this.srcReplyContent = srcReplyContent == null ? null : srcReplyContent.trim();
    }

    public String getSrcReplyDate() {
        return srcReplyDate;
    }

    public void setSrcReplyDate(String srcReplyDate) {
        this.srcReplyDate = srcReplyDate == null ? null : srcReplyDate.trim();
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
        OpsPoUnusualOrderLog other = (OpsPoUnusualOrderLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSourceType() == null ? other.getSourceType() == null : this.getSourceType().equals(other.getSourceType()))
            && (this.getSourceId() == null ? other.getSourceId() == null : this.getSourceId().equals(other.getSourceId()))
            && (this.getPono() == null ? other.getPono() == null : this.getPono().equals(other.getPono()))
            && (this.getLineItem() == null ? other.getLineItem() == null : this.getLineItem().equals(other.getLineItem()))
            && (this.getSupplierOrderNo() == null ? other.getSupplierOrderNo() == null : this.getSupplierOrderNo().equals(other.getSupplierOrderNo()))
            && (this.getSupplierLineItemNo() == null ? other.getSupplierLineItemNo() == null : this.getSupplierLineItemNo().equals(other.getSupplierLineItemNo()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getOrderUnusualReasonCode() == null ? other.getOrderUnusualReasonCode() == null : this.getOrderUnusualReasonCode().equals(other.getOrderUnusualReasonCode()))
            && (this.getSrOrderUnusualReasonCode() == null ? other.getSrOrderUnusualReasonCode() == null : this.getSrOrderUnusualReasonCode().equals(other.getSrOrderUnusualReasonCode()))
            && (this.getOrderUnusualReason() == null ? other.getOrderUnusualReason() == null : this.getOrderUnusualReason().equals(other.getOrderUnusualReason()))
            && (this.getSupplierSystemExecTime() == null ? other.getSupplierSystemExecTime() == null : this.getSupplierSystemExecTime().equals(other.getSupplierSystemExecTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getUnusualType() == null ? other.getUnusualType() == null : this.getUnusualType().equals(other.getUnusualType()))
            && (this.getSrcReplyContent() == null ? other.getSrcReplyContent() == null : this.getSrcReplyContent().equals(other.getSrcReplyContent()))
            && (this.getSrcReplyDate() == null ? other.getSrcReplyDate() == null : this.getSrcReplyDate().equals(other.getSrcReplyDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSourceType() == null) ? 0 : getSourceType().hashCode());
        result = prime * result + ((getSourceId() == null) ? 0 : getSourceId().hashCode());
        result = prime * result + ((getPono() == null) ? 0 : getPono().hashCode());
        result = prime * result + ((getLineItem() == null) ? 0 : getLineItem().hashCode());
        result = prime * result + ((getSupplierOrderNo() == null) ? 0 : getSupplierOrderNo().hashCode());
        result = prime * result + ((getSupplierLineItemNo() == null) ? 0 : getSupplierLineItemNo().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getOrderUnusualReasonCode() == null) ? 0 : getOrderUnusualReasonCode().hashCode());
        result = prime * result + ((getSrOrderUnusualReasonCode() == null) ? 0 : getSrOrderUnusualReasonCode().hashCode());
        result = prime * result + ((getOrderUnusualReason() == null) ? 0 : getOrderUnusualReason().hashCode());
        result = prime * result + ((getSupplierSystemExecTime() == null) ? 0 : getSupplierSystemExecTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getUnusualType() == null) ? 0 : getUnusualType().hashCode());
        result = prime * result + ((getSrcReplyContent() == null) ? 0 : getSrcReplyContent().hashCode());
        result = prime * result + ((getSrcReplyDate() == null) ? 0 : getSrcReplyDate().hashCode());
        return result;
    }
}