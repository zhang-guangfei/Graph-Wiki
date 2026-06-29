package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInventoryRuleConfig implements Serializable {
    private String ruleCode;

    private String ruleDesc;

    private String ruleType;

    private Integer ruleAssFlag;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private String warehouseTypeCode;

    private Integer ruleQtyFlag;

    private static final long serialVersionUID = 1L;

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode == null ? null : ruleCode.trim();
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc == null ? null : ruleDesc.trim();
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType == null ? null : ruleType.trim();
    }

    public Integer getRuleAssFlag() {
        return ruleAssFlag;
    }

    public void setRuleAssFlag(Integer ruleAssFlag) {
        this.ruleAssFlag = ruleAssFlag;
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

    public String getWarehouseTypeCode() {
        return warehouseTypeCode;
    }

    public void setWarehouseTypeCode(String warehouseTypeCode) {
        this.warehouseTypeCode = warehouseTypeCode == null ? null : warehouseTypeCode.trim();
    }

    public Integer getRuleQtyFlag() {
        return ruleQtyFlag;
    }

    public void setRuleQtyFlag(Integer ruleQtyFlag) {
        this.ruleQtyFlag = ruleQtyFlag;
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
        OpsInventoryRuleConfig other = (OpsInventoryRuleConfig) that;
        return (this.getRuleCode() == null ? other.getRuleCode() == null : this.getRuleCode().equals(other.getRuleCode()))
            && (this.getRuleDesc() == null ? other.getRuleDesc() == null : this.getRuleDesc().equals(other.getRuleDesc()))
            && (this.getRuleType() == null ? other.getRuleType() == null : this.getRuleType().equals(other.getRuleType()))
            && (this.getRuleAssFlag() == null ? other.getRuleAssFlag() == null : this.getRuleAssFlag().equals(other.getRuleAssFlag()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getWarehouseTypeCode() == null ? other.getWarehouseTypeCode() == null : this.getWarehouseTypeCode().equals(other.getWarehouseTypeCode()))
            && (this.getRuleQtyFlag() == null ? other.getRuleQtyFlag() == null : this.getRuleQtyFlag().equals(other.getRuleQtyFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRuleCode() == null) ? 0 : getRuleCode().hashCode());
        result = prime * result + ((getRuleDesc() == null) ? 0 : getRuleDesc().hashCode());
        result = prime * result + ((getRuleType() == null) ? 0 : getRuleType().hashCode());
        result = prime * result + ((getRuleAssFlag() == null) ? 0 : getRuleAssFlag().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getWarehouseTypeCode() == null) ? 0 : getWarehouseTypeCode().hashCode());
        result = prime * result + ((getRuleQtyFlag() == null) ? 0 : getRuleQtyFlag().hashCode());
        return result;
    }
}