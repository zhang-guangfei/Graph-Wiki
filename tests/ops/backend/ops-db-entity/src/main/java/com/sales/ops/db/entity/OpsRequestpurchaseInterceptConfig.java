package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsRequestpurchaseInterceptConfig implements Serializable {
    private Integer id;

    private String typeid;

    private String rulekey;

    private String reason;

    private String remark;

    private Boolean enable;

    private String defaultaction;

    private String operator;

    private Date updatetime;

    private Boolean autoStockOut;

    private String ruleKey1;

    private Integer ruleKey2;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
    }

    public String getRulekey() {
        return rulekey;
    }

    public void setRulekey(String rulekey) {
        this.rulekey = rulekey == null ? null : rulekey.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getDefaultaction() {
        return defaultaction;
    }

    public void setDefaultaction(String defaultaction) {
        this.defaultaction = defaultaction == null ? null : defaultaction.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Boolean getAutoStockOut() {
        return autoStockOut;
    }

    public void setAutoStockOut(Boolean autoStockOut) {
        this.autoStockOut = autoStockOut;
    }

    public String getRuleKey1() {
        return ruleKey1;
    }

    public void setRuleKey1(String ruleKey1) {
        this.ruleKey1 = ruleKey1 == null ? null : ruleKey1.trim();
    }

    public Integer getRuleKey2() {
        return ruleKey2;
    }

    public void setRuleKey2(Integer ruleKey2) {
        this.ruleKey2 = ruleKey2;
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
        OpsRequestpurchaseInterceptConfig other = (OpsRequestpurchaseInterceptConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTypeid() == null ? other.getTypeid() == null : this.getTypeid().equals(other.getTypeid()))
            && (this.getRulekey() == null ? other.getRulekey() == null : this.getRulekey().equals(other.getRulekey()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getEnable() == null ? other.getEnable() == null : this.getEnable().equals(other.getEnable()))
            && (this.getDefaultaction() == null ? other.getDefaultaction() == null : this.getDefaultaction().equals(other.getDefaultaction()))
            && (this.getOperator() == null ? other.getOperator() == null : this.getOperator().equals(other.getOperator()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getAutoStockOut() == null ? other.getAutoStockOut() == null : this.getAutoStockOut().equals(other.getAutoStockOut()))
            && (this.getRuleKey1() == null ? other.getRuleKey1() == null : this.getRuleKey1().equals(other.getRuleKey1()))
            && (this.getRuleKey2() == null ? other.getRuleKey2() == null : this.getRuleKey2().equals(other.getRuleKey2()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTypeid() == null) ? 0 : getTypeid().hashCode());
        result = prime * result + ((getRulekey() == null) ? 0 : getRulekey().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getEnable() == null) ? 0 : getEnable().hashCode());
        result = prime * result + ((getDefaultaction() == null) ? 0 : getDefaultaction().hashCode());
        result = prime * result + ((getOperator() == null) ? 0 : getOperator().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getAutoStockOut() == null) ? 0 : getAutoStockOut().hashCode());
        result = prime * result + ((getRuleKey1() == null) ? 0 : getRuleKey1().hashCode());
        result = prime * result + ((getRuleKey2() == null) ? 0 : getRuleKey2().hashCode());
        return result;
    }
}