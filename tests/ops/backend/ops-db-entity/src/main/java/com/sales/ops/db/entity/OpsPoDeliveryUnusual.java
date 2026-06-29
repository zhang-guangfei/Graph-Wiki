package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPoDeliveryUnusual implements Serializable {
    private Long id;

    private String orderNo;

    private Integer itemNo;

    private Integer splitItemNo;

    private String modelno;

    private Integer quantity;

    private String unusualClassify;

    private String unusualType;

    private String unusualIdentificationCode;

    private String unusualDescEng;

    private Date handleTime;

    private Integer handleStatus;

    private String handleResult;

    private String handleUser;

    private String handleUserName;

    private Date recordDate;

    private Integer recordCount;

    private String remark;

    private Boolean delFlag;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String orderFno;

    private Date replyTime;

    private Date completeTime;

    private String supplierCode;

    private Date replyDate;

    private String replyContent;

    private Date inquiryDate;

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

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getSplitItemNo() {
        return splitItemNo;
    }

    public void setSplitItemNo(Integer splitItemNo) {
        this.splitItemNo = splitItemNo;
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

    public String getUnusualClassify() {
        return unusualClassify;
    }

    public void setUnusualClassify(String unusualClassify) {
        this.unusualClassify = unusualClassify == null ? null : unusualClassify.trim();
    }

    public String getUnusualType() {
        return unusualType;
    }

    public void setUnusualType(String unusualType) {
        this.unusualType = unusualType == null ? null : unusualType.trim();
    }

    public String getUnusualIdentificationCode() {
        return unusualIdentificationCode;
    }

    public void setUnusualIdentificationCode(String unusualIdentificationCode) {
        this.unusualIdentificationCode = unusualIdentificationCode == null ? null : unusualIdentificationCode.trim();
    }

    public String getUnusualDescEng() {
        return unusualDescEng;
    }

    public void setUnusualDescEng(String unusualDescEng) {
        this.unusualDescEng = unusualDescEng == null ? null : unusualDescEng.trim();
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult == null ? null : handleResult.trim();
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser == null ? null : handleUser.trim();
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName == null ? null : handleUserName.trim();
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
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

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno == null ? null : orderFno.trim();
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent == null ? null : replyContent.trim();
    }

    public Date getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(Date inquiryDate) {
        this.inquiryDate = inquiryDate;
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
        OpsPoDeliveryUnusual other = (OpsPoDeliveryUnusual) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getSplitItemNo() == null ? other.getSplitItemNo() == null : this.getSplitItemNo().equals(other.getSplitItemNo()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getUnusualClassify() == null ? other.getUnusualClassify() == null : this.getUnusualClassify().equals(other.getUnusualClassify()))
            && (this.getUnusualType() == null ? other.getUnusualType() == null : this.getUnusualType().equals(other.getUnusualType()))
            && (this.getUnusualIdentificationCode() == null ? other.getUnusualIdentificationCode() == null : this.getUnusualIdentificationCode().equals(other.getUnusualIdentificationCode()))
            && (this.getUnusualDescEng() == null ? other.getUnusualDescEng() == null : this.getUnusualDescEng().equals(other.getUnusualDescEng()))
            && (this.getHandleTime() == null ? other.getHandleTime() == null : this.getHandleTime().equals(other.getHandleTime()))
            && (this.getHandleStatus() == null ? other.getHandleStatus() == null : this.getHandleStatus().equals(other.getHandleStatus()))
            && (this.getHandleResult() == null ? other.getHandleResult() == null : this.getHandleResult().equals(other.getHandleResult()))
            && (this.getHandleUser() == null ? other.getHandleUser() == null : this.getHandleUser().equals(other.getHandleUser()))
            && (this.getHandleUserName() == null ? other.getHandleUserName() == null : this.getHandleUserName().equals(other.getHandleUserName()))
            && (this.getRecordDate() == null ? other.getRecordDate() == null : this.getRecordDate().equals(other.getRecordDate()))
            && (this.getRecordCount() == null ? other.getRecordCount() == null : this.getRecordCount().equals(other.getRecordCount()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getOrderFno() == null ? other.getOrderFno() == null : this.getOrderFno().equals(other.getOrderFno()))
            && (this.getReplyTime() == null ? other.getReplyTime() == null : this.getReplyTime().equals(other.getReplyTime()))
            && (this.getCompleteTime() == null ? other.getCompleteTime() == null : this.getCompleteTime().equals(other.getCompleteTime()))
            && (this.getSupplierCode() == null ? other.getSupplierCode() == null : this.getSupplierCode().equals(other.getSupplierCode()))
            && (this.getReplyDate() == null ? other.getReplyDate() == null : this.getReplyDate().equals(other.getReplyDate()))
            && (this.getReplyContent() == null ? other.getReplyContent() == null : this.getReplyContent().equals(other.getReplyContent()))
            && (this.getInquiryDate() == null ? other.getInquiryDate() == null : this.getInquiryDate().equals(other.getInquiryDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getSplitItemNo() == null) ? 0 : getSplitItemNo().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getUnusualClassify() == null) ? 0 : getUnusualClassify().hashCode());
        result = prime * result + ((getUnusualType() == null) ? 0 : getUnusualType().hashCode());
        result = prime * result + ((getUnusualIdentificationCode() == null) ? 0 : getUnusualIdentificationCode().hashCode());
        result = prime * result + ((getUnusualDescEng() == null) ? 0 : getUnusualDescEng().hashCode());
        result = prime * result + ((getHandleTime() == null) ? 0 : getHandleTime().hashCode());
        result = prime * result + ((getHandleStatus() == null) ? 0 : getHandleStatus().hashCode());
        result = prime * result + ((getHandleResult() == null) ? 0 : getHandleResult().hashCode());
        result = prime * result + ((getHandleUser() == null) ? 0 : getHandleUser().hashCode());
        result = prime * result + ((getHandleUserName() == null) ? 0 : getHandleUserName().hashCode());
        result = prime * result + ((getRecordDate() == null) ? 0 : getRecordDate().hashCode());
        result = prime * result + ((getRecordCount() == null) ? 0 : getRecordCount().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getOrderFno() == null) ? 0 : getOrderFno().hashCode());
        result = prime * result + ((getReplyTime() == null) ? 0 : getReplyTime().hashCode());
        result = prime * result + ((getCompleteTime() == null) ? 0 : getCompleteTime().hashCode());
        result = prime * result + ((getSupplierCode() == null) ? 0 : getSupplierCode().hashCode());
        result = prime * result + ((getReplyDate() == null) ? 0 : getReplyDate().hashCode());
        result = prime * result + ((getReplyContent() == null) ? 0 : getReplyContent().hashCode());
        result = prime * result + ((getInquiryDate() == null) ? 0 : getInquiryDate().hashCode());
        return result;
    }
}