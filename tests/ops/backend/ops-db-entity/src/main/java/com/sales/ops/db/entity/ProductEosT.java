package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductEosT implements Serializable {
    private String modelno;

    private String datasource;

    private String eosType;

    private Date warningdate;

    private Date eosDate;

    private String modelnoRecommend;

    private String priorityLevel;

    private String describe;

    private Date updatetime;

    private Date stopdate;

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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getStopdate() {
        return stopdate;
    }

    public void setStopdate(Date stopdate) {
        this.stopdate = stopdate;
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
        ProductEosT other = (ProductEosT) that;
        return (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getDatasource() == null ? other.getDatasource() == null : this.getDatasource().equals(other.getDatasource()))
            && (this.getEosType() == null ? other.getEosType() == null : this.getEosType().equals(other.getEosType()))
            && (this.getWarningdate() == null ? other.getWarningdate() == null : this.getWarningdate().equals(other.getWarningdate()))
            && (this.getEosDate() == null ? other.getEosDate() == null : this.getEosDate().equals(other.getEosDate()))
            && (this.getModelnoRecommend() == null ? other.getModelnoRecommend() == null : this.getModelnoRecommend().equals(other.getModelnoRecommend()))
            && (this.getPriorityLevel() == null ? other.getPriorityLevel() == null : this.getPriorityLevel().equals(other.getPriorityLevel()))
            && (this.getDescribe() == null ? other.getDescribe() == null : this.getDescribe().equals(other.getDescribe()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getStopdate() == null ? other.getStopdate() == null : this.getStopdate().equals(other.getStopdate()));
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
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getStopdate() == null) ? 0 : getStopdate().hashCode());
        return result;
    }
}