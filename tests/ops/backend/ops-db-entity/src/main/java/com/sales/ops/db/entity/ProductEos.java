package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductEos implements Serializable {
    private String modelno;

    private String datasource;

    private String eosType;

    private Date warningdate;

    private Date eosDate;

    private String modelnoRecommend;

    private String priorityLevel;

    private String describe;

    private Date stopdate;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource == null ? null : datasource.trim();
    }

    public String getEosType() {
        return eosType;
    }

    public void setEosType(String eosType) {
        this.eosType = eosType == null ? null : eosType.trim();
    }

    public Date getWarningdate() {
        return warningdate;
    }

    public void setWarningdate(Date warningdate) {
        this.warningdate = warningdate;
    }

    public Date getEosDate() {
        return eosDate;
    }

    public void setEosDate(Date eosDate) {
        this.eosDate = eosDate;
    }

    public String getModelnoRecommend() {
        return modelnoRecommend;
    }

    public void setModelnoRecommend(String modelnoRecommend) {
        this.modelnoRecommend = modelnoRecommend == null ? null : modelnoRecommend.trim();
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel == null ? null : priorityLevel.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public Date getStopdate() {
        return stopdate;
    }

    public void setStopdate(Date stopdate) {
        this.stopdate = stopdate;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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
        ProductEos other = (ProductEos) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getDatasource() == null ? other.getDatasource() == null : this.getDatasource().equals(other.getDatasource()))
            && (this.getEosType() == null ? other.getEosType() == null : this.getEosType().equals(other.getEosType()))
            && (this.getWarningdate() == null ? other.getWarningdate() == null : this.getWarningdate().equals(other.getWarningdate()))
            && (this.getEosDate() == null ? other.getEosDate() == null : this.getEosDate().equals(other.getEosDate()))
            && (this.getModelnoRecommend() == null ? other.getModelnoRecommend() == null : this.getModelnoRecommend().equals(other.getModelnoRecommend()))
            && (this.getPriorityLevel() == null ? other.getPriorityLevel() == null : this.getPriorityLevel().equals(other.getPriorityLevel()))
            && (this.getDescribe() == null ? other.getDescribe() == null : this.getDescribe().equals(other.getDescribe()))
            && (this.getStopdate() == null ? other.getStopdate() == null : this.getStopdate().equals(other.getStopdate()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getDatasource() == null) ? 0 : getDatasource().hashCode());
        result = prime * result + ((getEosType() == null) ? 0 : getEosType().hashCode());
        result = prime * result + ((getWarningdate() == null) ? 0 : getWarningdate().hashCode());
        result = prime * result + ((getEosDate() == null) ? 0 : getEosDate().hashCode());
        result = prime * result + ((getModelnoRecommend() == null) ? 0 : getModelnoRecommend().hashCode());
        result = prime * result + ((getPriorityLevel() == null) ? 0 : getPriorityLevel().hashCode());
        result = prime * result + ((getDescribe() == null) ? 0 : getDescribe().hashCode());
        result = prime * result + ((getStopdate() == null) ? 0 : getStopdate().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }
}