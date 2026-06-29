package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsOrderInventoryRuleConfig implements Serializable {
    private Long orderInventoryRuleConfigId;

    private String orderType;

    private String orderTag;

    private String ruleCode;

    private Integer sort;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private String orderDesc;

    private String ruleDesc;

    private String propertyType;

    private String dlvEntire;

    private static final long serialVersionUID = 1L;

    public Long getOrderInventoryRuleConfigId() {
        return orderInventoryRuleConfigId;
    }

    public void setOrderInventoryRuleConfigId(Long orderInventoryRuleConfigId) {
        this.orderInventoryRuleConfigId = orderInventoryRuleConfigId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getOrderTag() {
        return orderTag;
    }

    public void setOrderTag(String orderTag) {
        this.orderTag = orderTag == null ? null : orderTag.trim();
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode == null ? null : ruleCode.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc == null ? null : orderDesc.trim();
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc == null ? null : ruleDesc.trim();
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType == null ? null : propertyType.trim();
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire == null ? null : dlvEntire.trim();
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
        OpsOrderInventoryRuleConfig other = (OpsOrderInventoryRuleConfig) that;
        return (this.getOrderInventoryRuleConfigId() == null ? other.getOrderInventoryRuleConfigId() == null : this.getOrderInventoryRuleConfigId().equals(other.getOrderInventoryRuleConfigId()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getOrderTag() == null ? other.getOrderTag() == null : this.getOrderTag().equals(other.getOrderTag()))
            && (this.getRuleCode() == null ? other.getRuleCode() == null : this.getRuleCode().equals(other.getRuleCode()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getOrderDesc() == null ? other.getOrderDesc() == null : this.getOrderDesc().equals(other.getOrderDesc()))
            && (this.getRuleDesc() == null ? other.getRuleDesc() == null : this.getRuleDesc().equals(other.getRuleDesc()))
            && (this.getPropertyType() == null ? other.getPropertyType() == null : this.getPropertyType().equals(other.getPropertyType()))
            && (this.getDlvEntire() == null ? other.getDlvEntire() == null : this.getDlvEntire().equals(other.getDlvEntire()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderInventoryRuleConfigId() == null) ? 0 : getOrderInventoryRuleConfigId().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getOrderTag() == null) ? 0 : getOrderTag().hashCode());
        result = prime * result + ((getRuleCode() == null) ? 0 : getRuleCode().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getOrderDesc() == null) ? 0 : getOrderDesc().hashCode());
        result = prime * result + ((getRuleDesc() == null) ? 0 : getRuleDesc().hashCode());
        result = prime * result + ((getPropertyType() == null) ? 0 : getPropertyType().hashCode());
        result = prime * result + ((getDlvEntire() == null) ? 0 : getDlvEntire().hashCode());
        return result;
    }
}