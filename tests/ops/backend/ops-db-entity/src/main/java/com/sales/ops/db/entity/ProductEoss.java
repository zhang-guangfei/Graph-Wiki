package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class ProductEoss implements Serializable {
    private String modelno;

    private String datasource;

    private String eosType;

    private Date warningdate;

    private Date eosDate;

    private String modelnoRecommend;

    private String priorityLevel;

    private String desc;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
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
}