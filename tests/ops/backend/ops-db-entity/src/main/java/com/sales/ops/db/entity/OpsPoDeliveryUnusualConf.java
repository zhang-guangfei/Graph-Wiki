package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPoDeliveryUnusualConf implements Serializable {
    private Long id;

    private String unusualIdentificationCode;

    private String unusualClassify;

    private String unusualType;

    private Integer unusualLevel;

    private String unusualRule;

    private String unusualRuleCondition;

    private String unusualCode;

    private String unusualDescEng;

    private String unusualDescChn;

    private String orderType;

    private Integer jobType;

    private String jobDeptCode;

    private String jobDeptName;

    private String jobUserCode;

    private String jobUserName;

    private String jobUserMail;

    private String jobHandle;

    private String remark;

    private Boolean delFlag;

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

    public String getUnusualIdentificationCode() {
        return unusualIdentificationCode;
    }

    public void setUnusualIdentificationCode(String unusualIdentificationCode) {
        this.unusualIdentificationCode = unusualIdentificationCode == null ? null : unusualIdentificationCode.trim();
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

    public Integer getUnusualLevel() {
        return unusualLevel;
    }

    public void setUnusualLevel(Integer unusualLevel) {
        this.unusualLevel = unusualLevel;
    }

    public String getUnusualRule() {
        return unusualRule;
    }

    public void setUnusualRule(String unusualRule) {
        this.unusualRule = unusualRule == null ? null : unusualRule.trim();
    }

    public String getUnusualRuleCondition() {
        return unusualRuleCondition;
    }

    public void setUnusualRuleCondition(String unusualRuleCondition) {
        this.unusualRuleCondition = unusualRuleCondition == null ? null : unusualRuleCondition.trim();
    }

    public String getUnusualCode() {
        return unusualCode;
    }

    public void setUnusualCode(String unusualCode) {
        this.unusualCode = unusualCode == null ? null : unusualCode.trim();
    }

    public String getUnusualDescEng() {
        return unusualDescEng;
    }

    public void setUnusualDescEng(String unusualDescEng) {
        this.unusualDescEng = unusualDescEng == null ? null : unusualDescEng.trim();
    }

    public String getUnusualDescChn() {
        return unusualDescChn;
    }

    public void setUnusualDescChn(String unusualDescChn) {
        this.unusualDescChn = unusualDescChn == null ? null : unusualDescChn.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public String getJobDeptCode() {
        return jobDeptCode;
    }

    public void setJobDeptCode(String jobDeptCode) {
        this.jobDeptCode = jobDeptCode == null ? null : jobDeptCode.trim();
    }

    public String getJobDeptName() {
        return jobDeptName;
    }

    public void setJobDeptName(String jobDeptName) {
        this.jobDeptName = jobDeptName == null ? null : jobDeptName.trim();
    }

    public String getJobUserCode() {
        return jobUserCode;
    }

    public void setJobUserCode(String jobUserCode) {
        this.jobUserCode = jobUserCode == null ? null : jobUserCode.trim();
    }

    public String getJobUserName() {
        return jobUserName;
    }

    public void setJobUserName(String jobUserName) {
        this.jobUserName = jobUserName == null ? null : jobUserName.trim();
    }

    public String getJobUserMail() {
        return jobUserMail;
    }

    public void setJobUserMail(String jobUserMail) {
        this.jobUserMail = jobUserMail == null ? null : jobUserMail.trim();
    }

    public String getJobHandle() {
        return jobHandle;
    }

    public void setJobHandle(String jobHandle) {
        this.jobHandle = jobHandle == null ? null : jobHandle.trim();
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
        OpsPoDeliveryUnusualConf other = (OpsPoDeliveryUnusualConf) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUnusualIdentificationCode() == null ? other.getUnusualIdentificationCode() == null : this.getUnusualIdentificationCode().equals(other.getUnusualIdentificationCode()))
            && (this.getUnusualClassify() == null ? other.getUnusualClassify() == null : this.getUnusualClassify().equals(other.getUnusualClassify()))
            && (this.getUnusualType() == null ? other.getUnusualType() == null : this.getUnusualType().equals(other.getUnusualType()))
            && (this.getUnusualLevel() == null ? other.getUnusualLevel() == null : this.getUnusualLevel().equals(other.getUnusualLevel()))
            && (this.getUnusualRule() == null ? other.getUnusualRule() == null : this.getUnusualRule().equals(other.getUnusualRule()))
            && (this.getUnusualRuleCondition() == null ? other.getUnusualRuleCondition() == null : this.getUnusualRuleCondition().equals(other.getUnusualRuleCondition()))
            && (this.getUnusualCode() == null ? other.getUnusualCode() == null : this.getUnusualCode().equals(other.getUnusualCode()))
            && (this.getUnusualDescEng() == null ? other.getUnusualDescEng() == null : this.getUnusualDescEng().equals(other.getUnusualDescEng()))
            && (this.getUnusualDescChn() == null ? other.getUnusualDescChn() == null : this.getUnusualDescChn().equals(other.getUnusualDescChn()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getJobType() == null ? other.getJobType() == null : this.getJobType().equals(other.getJobType()))
            && (this.getJobDeptCode() == null ? other.getJobDeptCode() == null : this.getJobDeptCode().equals(other.getJobDeptCode()))
            && (this.getJobDeptName() == null ? other.getJobDeptName() == null : this.getJobDeptName().equals(other.getJobDeptName()))
            && (this.getJobUserCode() == null ? other.getJobUserCode() == null : this.getJobUserCode().equals(other.getJobUserCode()))
            && (this.getJobUserName() == null ? other.getJobUserName() == null : this.getJobUserName().equals(other.getJobUserName()))
            && (this.getJobUserMail() == null ? other.getJobUserMail() == null : this.getJobUserMail().equals(other.getJobUserMail()))
            && (this.getJobHandle() == null ? other.getJobHandle() == null : this.getJobHandle().equals(other.getJobHandle()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUnusualIdentificationCode() == null) ? 0 : getUnusualIdentificationCode().hashCode());
        result = prime * result + ((getUnusualClassify() == null) ? 0 : getUnusualClassify().hashCode());
        result = prime * result + ((getUnusualType() == null) ? 0 : getUnusualType().hashCode());
        result = prime * result + ((getUnusualLevel() == null) ? 0 : getUnusualLevel().hashCode());
        result = prime * result + ((getUnusualRule() == null) ? 0 : getUnusualRule().hashCode());
        result = prime * result + ((getUnusualRuleCondition() == null) ? 0 : getUnusualRuleCondition().hashCode());
        result = prime * result + ((getUnusualCode() == null) ? 0 : getUnusualCode().hashCode());
        result = prime * result + ((getUnusualDescEng() == null) ? 0 : getUnusualDescEng().hashCode());
        result = prime * result + ((getUnusualDescChn() == null) ? 0 : getUnusualDescChn().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getJobType() == null) ? 0 : getJobType().hashCode());
        result = prime * result + ((getJobDeptCode() == null) ? 0 : getJobDeptCode().hashCode());
        result = prime * result + ((getJobDeptName() == null) ? 0 : getJobDeptName().hashCode());
        result = prime * result + ((getJobUserCode() == null) ? 0 : getJobUserCode().hashCode());
        result = prime * result + ((getJobUserName() == null) ? 0 : getJobUserName().hashCode());
        result = prime * result + ((getJobUserMail() == null) ? 0 : getJobUserMail().hashCode());
        result = prime * result + ((getJobHandle() == null) ? 0 : getJobHandle().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        return result;
    }
}