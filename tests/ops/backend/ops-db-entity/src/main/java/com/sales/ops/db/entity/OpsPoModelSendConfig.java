package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPoModelSendConfig implements Serializable {
    private String model;

    private Boolean fuzzy;

    private String modi;

    private String supplier;

    private Boolean isDeleted;

    private Date insertTime;

    private String insertUser;

    private Date updateTime;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public Boolean getFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(Boolean fuzzy) {
        this.fuzzy = fuzzy;
    }

    public String getModi() {
        return modi;
    }

    public void setModi(String modi) {
        this.modi = modi == null ? null : modi.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser == null ? null : insertUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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
        OpsPoModelSendConfig other = (OpsPoModelSendConfig) that;
        return (this.getModel() == null ? other.getModel() == null : this.getModel().equals(other.getModel()))
            && (this.getFuzzy() == null ? other.getFuzzy() == null : this.getFuzzy().equals(other.getFuzzy()))
            && (this.getModi() == null ? other.getModi() == null : this.getModi().equals(other.getModi()))
            && (this.getSupplier() == null ? other.getSupplier() == null : this.getSupplier().equals(other.getSupplier()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getInsertTime() == null ? other.getInsertTime() == null : this.getInsertTime().equals(other.getInsertTime()))
            && (this.getInsertUser() == null ? other.getInsertUser() == null : this.getInsertUser().equals(other.getInsertUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getModel() == null) ? 0 : getModel().hashCode());
        result = prime * result + ((getFuzzy() == null) ? 0 : getFuzzy().hashCode());
        result = prime * result + ((getModi() == null) ? 0 : getModi().hashCode());
        result = prime * result + ((getSupplier() == null) ? 0 : getSupplier().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getInsertTime() == null) ? 0 : getInsertTime().hashCode());
        result = prime * result + ((getInsertUser() == null) ? 0 : getInsertUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        return result;
    }
}