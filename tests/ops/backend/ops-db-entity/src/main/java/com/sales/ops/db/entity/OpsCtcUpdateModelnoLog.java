package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsCtcUpdateModelnoLog implements Serializable {
    private Integer id;

    private Integer logid;

    private String modelno;

    private Date inserttime;

    private String statuscode;

    private String message;

    private Date updatetime;

    private String typeid;

    private String classification;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode == null ? null : statuscode.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
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
        OpsCtcUpdateModelnoLog other = (OpsCtcUpdateModelnoLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLogid() == null ? other.getLogid() == null : this.getLogid().equals(other.getLogid()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getInserttime() == null ? other.getInserttime() == null : this.getInserttime().equals(other.getInserttime()))
            && (this.getStatuscode() == null ? other.getStatuscode() == null : this.getStatuscode().equals(other.getStatuscode()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getTypeid() == null ? other.getTypeid() == null : this.getTypeid().equals(other.getTypeid()))
            && (this.getClassification() == null ? other.getClassification() == null : this.getClassification().equals(other.getClassification()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLogid() == null) ? 0 : getLogid().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getInserttime() == null) ? 0 : getInserttime().hashCode());
        result = prime * result + ((getStatuscode() == null) ? 0 : getStatuscode().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getTypeid() == null) ? 0 : getTypeid().hashCode());
        result = prime * result + ((getClassification() == null) ? 0 : getClassification().hashCode());
        return result;
    }
}