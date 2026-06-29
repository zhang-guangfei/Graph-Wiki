package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class StockTransferPlan implements Serializable {
    private Long id;

    private String planNo;

    private String associateNo;

    private Integer associateNoItem;

    private Integer associateNoSplitno;

    private String initInvTypeCode;

    private String initCustomerNo;

    private String initInvTypeValue;

    private String goalInvTypeCode;

    private String goalCustomerNo;

    private String goalInvTypeValue;

    private String modelno;

    private Integer planQty;

    private Integer finishQty;

    private Integer status;

    private Integer delflag;

    private Date createTime;

    private String creator;

    private Date updateTime;

    private String updator;

    private String doid;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo == null ? null : planNo.trim();
    }

    public String getAssociateNo() {
        return associateNo;
    }

    public void setAssociateNo(String associateNo) {
        this.associateNo = associateNo == null ? null : associateNo.trim();
    }

    public Integer getAssociateNoItem() {
        return associateNoItem;
    }

    public void setAssociateNoItem(Integer associateNoItem) {
        this.associateNoItem = associateNoItem;
    }

    public Integer getAssociateNoSplitno() {
        return associateNoSplitno;
    }

    public void setAssociateNoSplitno(Integer associateNoSplitno) {
        this.associateNoSplitno = associateNoSplitno;
    }

    public String getInitInvTypeCode() {
        return initInvTypeCode;
    }

    public void setInitInvTypeCode(String initInvTypeCode) {
        this.initInvTypeCode = initInvTypeCode == null ? null : initInvTypeCode.trim();
    }

    public String getInitCustomerNo() {
        return initCustomerNo;
    }

    public void setInitCustomerNo(String initCustomerNo) {
        this.initCustomerNo = initCustomerNo == null ? null : initCustomerNo.trim();
    }

    public String getInitInvTypeValue() {
        return initInvTypeValue;
    }

    public void setInitInvTypeValue(String initInvTypeValue) {
        this.initInvTypeValue = initInvTypeValue == null ? null : initInvTypeValue.trim();
    }

    public String getGoalInvTypeCode() {
        return goalInvTypeCode;
    }

    public void setGoalInvTypeCode(String goalInvTypeCode) {
        this.goalInvTypeCode = goalInvTypeCode == null ? null : goalInvTypeCode.trim();
    }

    public String getGoalCustomerNo() {
        return goalCustomerNo;
    }

    public void setGoalCustomerNo(String goalCustomerNo) {
        this.goalCustomerNo = goalCustomerNo == null ? null : goalCustomerNo.trim();
    }

    public String getGoalInvTypeValue() {
        return goalInvTypeValue;
    }

    public void setGoalInvTypeValue(String goalInvTypeValue) {
        this.goalInvTypeValue = goalInvTypeValue == null ? null : goalInvTypeValue.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public Integer getFinishQty() {
        return finishQty;
    }

    public void setFinishQty(Integer finishQty) {
        this.finishQty = finishQty;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelflag() {
        return delflag;
    }

    public void setDelflag(Integer delflag) {
        this.delflag = delflag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    public String getDoid() {
        return doid;
    }

    public void setDoid(String doid) {
        this.doid = doid == null ? null : doid.trim();
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
        StockTransferPlan other = (StockTransferPlan) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlanNo() == null ? other.getPlanNo() == null : this.getPlanNo().equals(other.getPlanNo()))
            && (this.getAssociateNo() == null ? other.getAssociateNo() == null : this.getAssociateNo().equals(other.getAssociateNo()))
            && (this.getAssociateNoItem() == null ? other.getAssociateNoItem() == null : this.getAssociateNoItem().equals(other.getAssociateNoItem()))
            && (this.getAssociateNoSplitno() == null ? other.getAssociateNoSplitno() == null : this.getAssociateNoSplitno().equals(other.getAssociateNoSplitno()))
            && (this.getInitInvTypeCode() == null ? other.getInitInvTypeCode() == null : this.getInitInvTypeCode().equals(other.getInitInvTypeCode()))
            && (this.getInitCustomerNo() == null ? other.getInitCustomerNo() == null : this.getInitCustomerNo().equals(other.getInitCustomerNo()))
            && (this.getInitInvTypeValue() == null ? other.getInitInvTypeValue() == null : this.getInitInvTypeValue().equals(other.getInitInvTypeValue()))
            && (this.getGoalInvTypeCode() == null ? other.getGoalInvTypeCode() == null : this.getGoalInvTypeCode().equals(other.getGoalInvTypeCode()))
            && (this.getGoalCustomerNo() == null ? other.getGoalCustomerNo() == null : this.getGoalCustomerNo().equals(other.getGoalCustomerNo()))
            && (this.getGoalInvTypeValue() == null ? other.getGoalInvTypeValue() == null : this.getGoalInvTypeValue().equals(other.getGoalInvTypeValue()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getPlanQty() == null ? other.getPlanQty() == null : this.getPlanQty().equals(other.getPlanQty()))
            && (this.getFinishQty() == null ? other.getFinishQty() == null : this.getFinishQty().equals(other.getFinishQty()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDelflag() == null ? other.getDelflag() == null : this.getDelflag().equals(other.getDelflag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdator() == null ? other.getUpdator() == null : this.getUpdator().equals(other.getUpdator()))
            && (this.getDoid() == null ? other.getDoid() == null : this.getDoid().equals(other.getDoid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlanNo() == null) ? 0 : getPlanNo().hashCode());
        result = prime * result + ((getAssociateNo() == null) ? 0 : getAssociateNo().hashCode());
        result = prime * result + ((getAssociateNoItem() == null) ? 0 : getAssociateNoItem().hashCode());
        result = prime * result + ((getAssociateNoSplitno() == null) ? 0 : getAssociateNoSplitno().hashCode());
        result = prime * result + ((getInitInvTypeCode() == null) ? 0 : getInitInvTypeCode().hashCode());
        result = prime * result + ((getInitCustomerNo() == null) ? 0 : getInitCustomerNo().hashCode());
        result = prime * result + ((getInitInvTypeValue() == null) ? 0 : getInitInvTypeValue().hashCode());
        result = prime * result + ((getGoalInvTypeCode() == null) ? 0 : getGoalInvTypeCode().hashCode());
        result = prime * result + ((getGoalCustomerNo() == null) ? 0 : getGoalCustomerNo().hashCode());
        result = prime * result + ((getGoalInvTypeValue() == null) ? 0 : getGoalInvTypeValue().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getPlanQty() == null) ? 0 : getPlanQty().hashCode());
        result = prime * result + ((getFinishQty() == null) ? 0 : getFinishQty().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDelflag() == null) ? 0 : getDelflag().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdator() == null) ? 0 : getUpdator().hashCode());
        result = prime * result + ((getDoid() == null) ? 0 : getDoid().hashCode());
        return result;
    }
}