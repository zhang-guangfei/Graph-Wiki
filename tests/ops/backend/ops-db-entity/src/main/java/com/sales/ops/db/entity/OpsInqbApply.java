package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInqbApply implements Serializable {
    private Long id;

    private String inqbApplyNo;

    private String sourcesApplyNo;

    private String batchNo;

    private String dataSources;

    private String modelNo;

    private Integer quantity;

    private String endUser;

    private String userDept;

    private Integer inqbStatus;

    private String inqbValidity;

    private Date expirationDate;

    private Integer expectedDeliveryDate;

    private String description;

    private Date inqbDate;

    private Integer inqbType;

    private String inqbClassify;

    private String inqbDept;

    private String inqbUser;

    private String inqbUserName;

    private String replyDept;

    private String replySupplierDept;

    private String replyAcceptHl;

    private String replyNo;

    private Integer replyDeliveryDays;

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

    public String getSourcesApplyNo() {
        return sourcesApplyNo;
    }

    public void setSourcesApplyNo(String sourcesApplyNo) {
        this.sourcesApplyNo = sourcesApplyNo == null ? null : sourcesApplyNo.trim();
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources == null ? null : dataSources.trim();
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

    public Integer getInqbStatus() {
        return inqbStatus;
    }

    public void setInqbStatus(Integer inqbStatus) {
        this.inqbStatus = inqbStatus;
    }

    public String getInqbValidity() {
        return inqbValidity;
    }

    public void setInqbValidity(String inqbValidity) {
        this.inqbValidity = inqbValidity == null ? null : inqbValidity.trim();
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Integer expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getInqbDate() {
        return inqbDate;
    }

    public void setInqbDate(Date inqbDate) {
        this.inqbDate = inqbDate;
    }

    public Integer getInqbType() {
        return inqbType;
    }

    public void setInqbType(Integer inqbType) {
        this.inqbType = inqbType;
    }

    public String getInqbClassify() {
        return inqbClassify;
    }

    public void setInqbClassify(String inqbClassify) {
        this.inqbClassify = inqbClassify == null ? null : inqbClassify.trim();
    }

    public String getInqbDept() {
        return inqbDept;
    }

    public void setInqbDept(String inqbDept) {
        this.inqbDept = inqbDept == null ? null : inqbDept.trim();
    }

    public String getInqbUser() {
        return inqbUser;
    }

    public void setInqbUser(String inqbUser) {
        this.inqbUser = inqbUser == null ? null : inqbUser.trim();
    }

    public String getInqbUserName() {
        return inqbUserName;
    }

    public void setInqbUserName(String inqbUserName) {
        this.inqbUserName = inqbUserName == null ? null : inqbUserName.trim();
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

    public String getReplyAcceptHl() {
        return replyAcceptHl;
    }

    public void setReplyAcceptHl(String replyAcceptHl) {
        this.replyAcceptHl = replyAcceptHl == null ? null : replyAcceptHl.trim();
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
        OpsInqbApply other = (OpsInqbApply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInqbApplyNo() == null ? other.getInqbApplyNo() == null : this.getInqbApplyNo().equals(other.getInqbApplyNo()))
            && (this.getSourcesApplyNo() == null ? other.getSourcesApplyNo() == null : this.getSourcesApplyNo().equals(other.getSourcesApplyNo()))
            && (this.getBatchNo() == null ? other.getBatchNo() == null : this.getBatchNo().equals(other.getBatchNo()))
            && (this.getDataSources() == null ? other.getDataSources() == null : this.getDataSources().equals(other.getDataSources()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getEndUser() == null ? other.getEndUser() == null : this.getEndUser().equals(other.getEndUser()))
            && (this.getUserDept() == null ? other.getUserDept() == null : this.getUserDept().equals(other.getUserDept()))
            && (this.getInqbStatus() == null ? other.getInqbStatus() == null : this.getInqbStatus().equals(other.getInqbStatus()))
            && (this.getInqbValidity() == null ? other.getInqbValidity() == null : this.getInqbValidity().equals(other.getInqbValidity()))
            && (this.getExpirationDate() == null ? other.getExpirationDate() == null : this.getExpirationDate().equals(other.getExpirationDate()))
            && (this.getExpectedDeliveryDate() == null ? other.getExpectedDeliveryDate() == null : this.getExpectedDeliveryDate().equals(other.getExpectedDeliveryDate()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getInqbDate() == null ? other.getInqbDate() == null : this.getInqbDate().equals(other.getInqbDate()))
            && (this.getInqbType() == null ? other.getInqbType() == null : this.getInqbType().equals(other.getInqbType()))
            && (this.getInqbClassify() == null ? other.getInqbClassify() == null : this.getInqbClassify().equals(other.getInqbClassify()))
            && (this.getInqbDept() == null ? other.getInqbDept() == null : this.getInqbDept().equals(other.getInqbDept()))
            && (this.getInqbUser() == null ? other.getInqbUser() == null : this.getInqbUser().equals(other.getInqbUser()))
            && (this.getInqbUserName() == null ? other.getInqbUserName() == null : this.getInqbUserName().equals(other.getInqbUserName()))
            && (this.getReplyDept() == null ? other.getReplyDept() == null : this.getReplyDept().equals(other.getReplyDept()))
            && (this.getReplySupplierDept() == null ? other.getReplySupplierDept() == null : this.getReplySupplierDept().equals(other.getReplySupplierDept()))
            && (this.getReplyAcceptHl() == null ? other.getReplyAcceptHl() == null : this.getReplyAcceptHl().equals(other.getReplyAcceptHl()))
            && (this.getReplyNo() == null ? other.getReplyNo() == null : this.getReplyNo().equals(other.getReplyNo()))
            && (this.getReplyDeliveryDays() == null ? other.getReplyDeliveryDays() == null : this.getReplyDeliveryDays().equals(other.getReplyDeliveryDays()))
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
        result = prime * result + ((getSourcesApplyNo() == null) ? 0 : getSourcesApplyNo().hashCode());
        result = prime * result + ((getBatchNo() == null) ? 0 : getBatchNo().hashCode());
        result = prime * result + ((getDataSources() == null) ? 0 : getDataSources().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getEndUser() == null) ? 0 : getEndUser().hashCode());
        result = prime * result + ((getUserDept() == null) ? 0 : getUserDept().hashCode());
        result = prime * result + ((getInqbStatus() == null) ? 0 : getInqbStatus().hashCode());
        result = prime * result + ((getInqbValidity() == null) ? 0 : getInqbValidity().hashCode());
        result = prime * result + ((getExpirationDate() == null) ? 0 : getExpirationDate().hashCode());
        result = prime * result + ((getExpectedDeliveryDate() == null) ? 0 : getExpectedDeliveryDate().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getInqbDate() == null) ? 0 : getInqbDate().hashCode());
        result = prime * result + ((getInqbType() == null) ? 0 : getInqbType().hashCode());
        result = prime * result + ((getInqbClassify() == null) ? 0 : getInqbClassify().hashCode());
        result = prime * result + ((getInqbDept() == null) ? 0 : getInqbDept().hashCode());
        result = prime * result + ((getInqbUser() == null) ? 0 : getInqbUser().hashCode());
        result = prime * result + ((getInqbUserName() == null) ? 0 : getInqbUserName().hashCode());
        result = prime * result + ((getReplyDept() == null) ? 0 : getReplyDept().hashCode());
        result = prime * result + ((getReplySupplierDept() == null) ? 0 : getReplySupplierDept().hashCode());
        result = prime * result + ((getReplyAcceptHl() == null) ? 0 : getReplyAcceptHl().hashCode());
        result = prime * result + ((getReplyNo() == null) ? 0 : getReplyNo().hashCode());
        result = prime * result + ((getReplyDeliveryDays() == null) ? 0 : getReplyDeliveryDays().hashCode());
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