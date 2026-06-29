package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsCtcUpdate implements Serializable {
    private Integer logid;

    private String typeid;

    private Integer ctcid;

    private String statuscode;

    private Date inserttime;

    private Date updatetime;

    private String classification;

    private static final long serialVersionUID = 1L;

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
    }

    public Integer getCtcid() {
        return ctcid;
    }

    public void setCtcid(Integer ctcid) {
        this.ctcid = ctcid;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode == null ? null : statuscode.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification == null ? null : classification.trim();
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
        OpsCtcUpdate other = (OpsCtcUpdate) that;
        return (this.getLogid() == null ? other.getLogid() == null : this.getLogid().equals(other.getLogid()))
            && (this.getTypeid() == null ? other.getTypeid() == null : this.getTypeid().equals(other.getTypeid()))
            && (this.getCtcid() == null ? other.getCtcid() == null : this.getCtcid().equals(other.getCtcid()))
            && (this.getStatuscode() == null ? other.getStatuscode() == null : this.getStatuscode().equals(other.getStatuscode()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getClassification() == null ? other.getClassification() == null : this.getClassification().equals(other.getClassification()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogid() == null) ? 0 : getLogid().hashCode());
        result = prime * result + ((getTypeid() == null) ? 0 : getTypeid().hashCode());
        result = prime * result + ((getCtcid() == null) ? 0 : getCtcid().hashCode());
        result = prime * result + ((getStatuscode() == null) ? 0 : getStatuscode().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getClassification() == null) ? 0 : getClassification().hashCode());
        return result;
    }
}