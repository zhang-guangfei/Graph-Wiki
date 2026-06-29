package com.sales.ops.db.entity;

import java.io.Serializable;

public class PurchaseStatusRule implements Serializable {
    private Long id;

    private Integer priority;

    private Integer status;

    private Integer detailStatus;

    private String conditionExpr;

    private String descriptionTpl;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(Integer detailStatus) {
        this.detailStatus = detailStatus;
    }

    public String getConditionExpr() {
        return conditionExpr;
    }

    public void setConditionExpr(String conditionExpr) {
        this.conditionExpr = conditionExpr == null ? null : conditionExpr.trim();
    }

    public String getDescriptionTpl() {
        return descriptionTpl;
    }

    public void setDescriptionTpl(String descriptionTpl) {
        this.descriptionTpl = descriptionTpl == null ? null : descriptionTpl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        PurchaseStatusRule other = (PurchaseStatusRule) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPriority() == null ? other.getPriority() == null : this.getPriority().equals(other.getPriority()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDetailStatus() == null ? other.getDetailStatus() == null : this.getDetailStatus().equals(other.getDetailStatus()))
            && (this.getConditionExpr() == null ? other.getConditionExpr() == null : this.getConditionExpr().equals(other.getConditionExpr()))
            && (this.getDescriptionTpl() == null ? other.getDescriptionTpl() == null : this.getDescriptionTpl().equals(other.getDescriptionTpl()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPriority() == null) ? 0 : getPriority().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDetailStatus() == null) ? 0 : getDetailStatus().hashCode());
        result = prime * result + ((getConditionExpr() == null) ? 0 : getConditionExpr().hashCode());
        result = prime * result + ((getDescriptionTpl() == null) ? 0 : getDescriptionTpl().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }
}