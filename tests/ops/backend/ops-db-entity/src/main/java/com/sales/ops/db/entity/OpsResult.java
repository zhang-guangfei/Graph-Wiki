package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsResult implements Serializable {
    private Integer id;

    private String model;

    private String typeid;

    private String splitmethod;

    private String splitmodelno;

    private String decideresult;

    private String defaultsplitmethod;

    private Integer splitquantity;

    private String describe;

    private Boolean classify;

    private String createduser;

    private Date createdtime;

    private String updateduser;

    private Date updatedtime;

    private String status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid == null ? null : typeid.trim();
    }

    public String getSplitmethod() {
        return splitmethod;
    }

    public void setSplitmethod(String splitmethod) {
        this.splitmethod = splitmethod == null ? null : splitmethod.trim();
    }

    public String getSplitmodelno() {
        return splitmodelno;
    }

    public void setSplitmodelno(String splitmodelno) {
        this.splitmodelno = splitmodelno == null ? null : splitmodelno.trim();
    }

    public String getDecideresult() {
        return decideresult;
    }

    public void setDecideresult(String decideresult) {
        this.decideresult = decideresult == null ? null : decideresult.trim();
    }

    public String getDefaultsplitmethod() {
        return defaultsplitmethod;
    }

    public void setDefaultsplitmethod(String defaultsplitmethod) {
        this.defaultsplitmethod = defaultsplitmethod == null ? null : defaultsplitmethod.trim();
    }

    public Integer getSplitquantity() {
        return splitquantity;
    }

    public void setSplitquantity(Integer splitquantity) {
        this.splitquantity = splitquantity;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public Boolean getClassify() {
        return classify;
    }

    public void setClassify(Boolean classify) {
        this.classify = classify;
    }

    public String getCreateduser() {
        return createduser;
    }

    public void setCreateduser(String createduser) {
        this.createduser = createduser == null ? null : createduser.trim();
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public String getUpdateduser() {
        return updateduser;
    }

    public void setUpdateduser(String updateduser) {
        this.updateduser = updateduser == null ? null : updateduser.trim();
    }

    public Date getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(Date updatedtime) {
        this.updatedtime = updatedtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
        OpsResult other = (OpsResult) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getTypeid() == null ? other.getTypeid() == null : this.getTypeid().equals(other.getTypeid()))
            && (this.getSplitmethod() == null ? other.getSplitmethod() == null : this.getSplitmethod().equals(other.getSplitmethod()))
            && (this.getSplitmodelno() == null ? other.getSplitmodelno() == null : this.getSplitmodelno().equals(other.getSplitmodelno()))
            && (this.getDecideresult() == null ? other.getDecideresult() == null : this.getDecideresult().equals(other.getDecideresult()))
            && (this.getDefaultsplitmethod() == null ? other.getDefaultsplitmethod() == null : this.getDefaultsplitmethod().equals(other.getDefaultsplitmethod()))
            && (this.getSplitquantity() == null ? other.getSplitquantity() == null : this.getSplitquantity().equals(other.getSplitquantity()))
            && (this.getDescribe() == null ? other.getDescribe() == null : this.getDescribe().equals(other.getDescribe()))
            && (this.getClassify() == null ? other.getClassify() == null : this.getClassify().equals(other.getClassify()))
            && (this.getCreateduser() == null ? other.getCreateduser() == null : this.getCreateduser().equals(other.getCreateduser()))
            && (this.getCreatedtime() == null ? other.getCreatedtime() == null : this.getCreatedtime().equals(other.getCreatedtime()))
            && (this.getUpdateduser() == null ? other.getUpdateduser() == null : this.getUpdateduser().equals(other.getUpdateduser()))
            && (this.getUpdatedtime() == null ? other.getUpdatedtime() == null : this.getUpdatedtime().equals(other.getUpdatedtime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        result = prime * result + ((getTypeid() == null) ? 0 : getTypeid().hashCode());
        result = prime * result + ((getSplitmethod() == null) ? 0 : getSplitmethod().hashCode());
        result = prime * result + ((getSplitmodelno() == null) ? 0 : getSplitmodelno().hashCode());
        result = prime * result + ((getDecideresult() == null) ? 0 : getDecideresult().hashCode());
        result = prime * result + ((getDefaultsplitmethod() == null) ? 0 : getDefaultsplitmethod().hashCode());
        result = prime * result + ((getSplitquantity() == null) ? 0 : getSplitquantity().hashCode());
        result = prime * result + ((getDescribe() == null) ? 0 : getDescribe().hashCode());
        result = prime * result + ((getClassify() == null) ? 0 : getClassify().hashCode());
        result = prime * result + ((getCreateduser() == null) ? 0 : getCreateduser().hashCode());
        result = prime * result + ((getCreatedtime() == null) ? 0 : getCreatedtime().hashCode());
        result = prime * result + ((getUpdateduser() == null) ? 0 : getUpdateduser().hashCode());
        result = prime * result + ((getUpdatedtime() == null) ? 0 : getUpdatedtime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }
}