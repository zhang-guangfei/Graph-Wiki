package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInventoryRuleConfigItem implements Serializable {
    private Integer inventoryRuleConfigItemId;

    private String ruleCode;

    private Integer ruleItem;

    private String inventoryMatchCode;

    private Integer ruleSort;

    private Integer delflag;

    private Date creTime;

    private String creator;

    private Date modifyTime;

    private String modifier;

    private static final long serialVersionUID = 1L;

    public Integer getInventoryRuleConfigItemId() {
        return inventoryRuleConfigItemId;
    }

    public void setInventoryRuleConfigItemId(Integer inventoryRuleConfigItemId) {
        this.inventoryRuleConfigItemId = inventoryRuleConfigItemId;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode == null ? null : ruleCode.trim();
    }

    public Integer getRuleItem() {
        return ruleItem;
    }

    public void setRuleItem(Integer ruleItem) {
        this.ruleItem = ruleItem;
    }

    public String getInventoryMatchCode() {
        return inventoryMatchCode;
    }

    public void setInventoryMatchCode(String inventoryMatchCode) {
        this.inventoryMatchCode = inventoryMatchCode == null ? null : inventoryMatchCode.trim();
    }

    public Integer getRuleSort() {
        return ruleSort;
    }

    public void setRuleSort(Integer ruleSort) {
        this.ruleSort = ruleSort;
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
        OpsInventoryRuleConfigItem other = (OpsInventoryRuleConfigItem) that;
        return (this.getInventoryRuleConfigItemId() == null ? other.getInventoryRuleConfigItemId() == null : this.getInventoryRuleConfigItemId().equals(other.getInventoryRuleConfigItemId()))
            && (this.getRuleCode() == null ? other.getRuleCode() == null : this.getRuleCode().equals(other.getRuleCode()))
            && (this.getRuleItem() == null ? other.getRuleItem() == null : this.getRuleItem().equals(other.getRuleItem()))
            && (this.getInventoryMatchCode() == null ? other.getInventoryMatchCode() == null : this.getInventoryMatchCode().equals(other.getInventoryMatchCode()))
            && (this.getRuleSort() == null ? other.getRuleSort() == null : this.getRuleSort().equals(other.getRuleSort()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreTime() == null ? other.getCreTime() == null : this.getCreTime().equals(other.getCreTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInventoryRuleConfigItemId() == null) ? 0 : getInventoryRuleConfigItemId().hashCode());
        result = prime * result + ((getRuleCode() == null) ? 0 : getRuleCode().hashCode());
        result = prime * result + ((getRuleItem() == null) ? 0 : getRuleItem().hashCode());
        result = prime * result + ((getInventoryMatchCode() == null) ? 0 : getInventoryMatchCode().hashCode());
        result = prime * result + ((getRuleSort() == null) ? 0 : getRuleSort().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreTime() == null) ? 0 : getCreTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        return result;
    }
}