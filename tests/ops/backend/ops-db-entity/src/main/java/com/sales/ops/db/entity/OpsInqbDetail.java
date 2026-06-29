package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInqbDetail implements Serializable {
    private Long id;

    private String inqbApplyNo;

    private Integer itemNo;

    private String splitType;

    private String taskNo;

    private String bomId;

    private String supplierCode;

    private String stockCode;

    private String handleResult;

    private String modelNo;

    private Integer quantity;

    private Integer useQty;

    private Integer expectedDeliveryDate;

    private String endUser;

    private String userDept;

    private String status;

    private Date expirationDate;

    private String replyDept;

    private String replySupplierDept;

    private String replyNo;

    private Integer replyDeliveryDays;

    private String replyAcceptHl;

    private String replyUser;

    private Date replyTime;

    private String replyReasonCode;

    private String replyReason;

    private String replyRemark;

    private String remark;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInqbApplyNo() {
        return inqbApplyNo;
    }

    public void setInqbApplyNo(String inqbApplyNo) {
        this.inqbApplyNo = inqbApplyNo == null ? null : inqbApplyNo.trim();
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getSplitType() {
        return splitType;
    }

    public void setSplitType(String splitType) {
        this.splitType = splitType == null ? null : splitType.trim();
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo == null ? null : taskNo.trim();
    }

    public String getBomId() {
        return bomId;
    }

    public void setBomId(String bomId) {
        this.bomId = bomId == null ? null : bomId.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult == null ? null : handleResult.trim();
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

    public Integer getUseQty() {
        return useQty;
    }

    public void setUseQty(Integer useQty) {
        this.useQty = useQty;
    }

    public Integer getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Integer expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser == null ? null : endUser.trim();
    }

    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept == null ? null : userDept.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getReplyDept() {
        return replyDept;
    }

    public void setReplyDept(String replyDept) {
        this.replyDept = replyDept == null ? null : replyDept.trim();
    }

    public String getReplySupplierDept() {
        return replySupplierDept;
    }

    public void setReplySupplierDept(String replySupplierDept) {
        this.replySupplierDept = replySupplierDept == null ? null : replySupplierDept.trim();
    }

    public String getReplyNo() {
        return replyNo;
    }

    public void setReplyNo(String replyNo) {
        this.replyNo = replyNo == null ? null : replyNo.trim();
    }

    public Integer getReplyDeliveryDays() {
        return replyDeliveryDays;
    }

    public void setReplyDeliveryDays(Integer replyDeliveryDays) {
        this.replyDeliveryDays = replyDeliveryDays;
    }

    public String getReplyAcceptHl() {
        return replyAcceptHl;
    }

    public void setReplyAcceptHl(String replyAcceptHl) {
        this.replyAcceptHl = replyAcceptHl == null ? null : replyAcceptHl.trim();
    }

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser == null ? null : replyUser.trim();
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyReasonCode() {
        return replyReasonCode;
    }

    public void setReplyReasonCode(String replyReasonCode) {
        this.replyReasonCode = replyReasonCode == null ? null : replyReasonCode.trim();
    }

    public String getReplyReason() {
        return replyReason;
    }

    public void setReplyReason(String replyReason) {
        this.replyReason = replyReason == null ? null : replyReason.trim();
    }

    public String getReplyRemark() {
        return replyRemark;
    }

    public void setReplyRemark(String replyRemark) {
        this.replyRemark = replyRemark == null ? null : replyRemark.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        OpsInqbDetail other = (OpsInqbDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInqbApplyNo() == null ? other.getInqbApplyNo() == null : this.getInqbApplyNo().equals(other.getInqbApplyNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getSplitType() == null ? other.getSplitType() == null : this.getSplitType().equals(other.getSplitType()))
            && (this.getTaskNo() == null ? other.getTaskNo() == null : this.getTaskNo().equals(other.getTaskNo()))
            && (this.getBomId() == null ? other.getBomId() == null : this.getBomId().equals(other.getBomId()))
            && (this.getSupplierCode() == null ? other.getSupplierCode() == null : this.getSupplierCode().equals(other.getSupplierCode()))
            && (this.getStockCode() == null ? other.getStockCode() == null : this.getStockCode().equals(other.getStockCode()))
            && (this.getHandleResult() == null ? other.getHandleResult() == null : this.getHandleResult().equals(other.getHandleResult()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getUseQty() == null ? other.getUseQty() == null : this.getUseQty().equals(other.getUseQty()))
            && (this.getExpectedDeliveryDate() == null ? other.getExpectedDeliveryDate() == null : this.getExpectedDeliveryDate().equals(other.getExpectedDeliveryDate()))
            && (this.getEndUser() == null ? other.getEndUser() == null : this.getEndUser().equals(other.getEndUser()))
            && (this.getUserDept() == null ? other.getUserDept() == null : this.getUserDept().equals(other.getUserDept()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getExpirationDate() == null ? other.getExpirationDate() == null : this.getExpirationDate().equals(other.getExpirationDate()))
            && (this.getReplyDept() == null ? other.getReplyDept() == null : this.getReplyDept().equals(other.getReplyDept()))
            && (this.getReplySupplierDept() == null ? other.getReplySupplierDept() == null : this.getReplySupplierDept().equals(other.getReplySupplierDept()))
            && (this.getReplyNo() == null ? other.getReplyNo() == null : this.getReplyNo().equals(other.getReplyNo()))
            && (this.getReplyDeliveryDays() == null ? other.getReplyDeliveryDays() == null : this.getReplyDeliveryDays().equals(other.getReplyDeliveryDays()))
            && (this.getReplyAcceptHl() == null ? other.getReplyAcceptHl() == null : this.getReplyAcceptHl().equals(other.getReplyAcceptHl()))
            && (this.getReplyUser() == null ? other.getReplyUser() == null : this.getReplyUser().equals(other.getReplyUser()))
            && (this.getReplyTime() == null ? other.getReplyTime() == null : this.getReplyTime().equals(other.getReplyTime()))
            && (this.getReplyReasonCode() == null ? other.getReplyReasonCode() == null : this.getReplyReasonCode().equals(other.getReplyReasonCode()))
            && (this.getReplyReason() == null ? other.getReplyReason() == null : this.getReplyReason().equals(other.getReplyReason()))
            && (this.getReplyRemark() == null ? other.getReplyRemark() == null : this.getReplyRemark().equals(other.getReplyRemark()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInqbApplyNo() == null) ? 0 : getInqbApplyNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getSplitType() == null) ? 0 : getSplitType().hashCode());
        result = prime * result + ((getTaskNo() == null) ? 0 : getTaskNo().hashCode());
        result = prime * result + ((getBomId() == null) ? 0 : getBomId().hashCode());
        result = prime * result + ((getSupplierCode() == null) ? 0 : getSupplierCode().hashCode());
        result = prime * result + ((getStockCode() == null) ? 0 : getStockCode().hashCode());
        result = prime * result + ((getHandleResult() == null) ? 0 : getHandleResult().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getUseQty() == null) ? 0 : getUseQty().hashCode());
        result = prime * result + ((getExpectedDeliveryDate() == null) ? 0 : getExpectedDeliveryDate().hashCode());
        result = prime * result + ((getEndUser() == null) ? 0 : getEndUser().hashCode());
        result = prime * result + ((getUserDept() == null) ? 0 : getUserDept().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getExpirationDate() == null) ? 0 : getExpirationDate().hashCode());
        result = prime * result + ((getReplyDept() == null) ? 0 : getReplyDept().hashCode());
        result = prime * result + ((getReplySupplierDept() == null) ? 0 : getReplySupplierDept().hashCode());
        result = prime * result + ((getReplyNo() == null) ? 0 : getReplyNo().hashCode());
        result = prime * result + ((getReplyDeliveryDays() == null) ? 0 : getReplyDeliveryDays().hashCode());
        result = prime * result + ((getReplyAcceptHl() == null) ? 0 : getReplyAcceptHl().hashCode());
        result = prime * result + ((getReplyUser() == null) ? 0 : getReplyUser().hashCode());
        result = prime * result + ((getReplyTime() == null) ? 0 : getReplyTime().hashCode());
        result = prime * result + ((getReplyReasonCode() == null) ? 0 : getReplyReasonCode().hashCode());
        result = prime * result + ((getReplyReason() == null) ? 0 : getReplyReason().hashCode());
        result = prime * result + ((getReplyRemark() == null) ? 0 : getReplyRemark().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}