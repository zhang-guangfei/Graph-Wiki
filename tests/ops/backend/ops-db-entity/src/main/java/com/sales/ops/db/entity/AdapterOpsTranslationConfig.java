package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class AdapterOpsTranslationConfig implements Serializable {
    private Long id;

    private String adapterCode;

    private String opsCode;

    private String info;

    private String codeType;

    private Date insertTime;

    private String insertUser;

    private Date updateTime;

    private String updateUser;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdapterCode() {
        return adapterCode;
    }

    public void setAdapterCode(String adapterCode) {
        this.adapterCode = adapterCode == null ? null : adapterCode.trim();
    }

    public String getOpsCode() {
        return opsCode;
    }

    public void setOpsCode(String opsCode) {
        this.opsCode = opsCode == null ? null : opsCode.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType == null ? null : codeType.trim();
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
        AdapterOpsTranslationConfig other = (AdapterOpsTranslationConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAdapterCode() == null ? other.getAdapterCode() == null : this.getAdapterCode().equals(other.getAdapterCode()))
            && (this.getOpsCode() == null ? other.getOpsCode() == null : this.getOpsCode().equals(other.getOpsCode()))
            && (this.getInfo() == null ? other.getInfo() == null : this.getInfo().equals(other.getInfo()))
            && (this.getCodeType() == null ? other.getCodeType() == null : this.getCodeType().equals(other.getCodeType()))
            && (this.getInsertTime() == null ? other.getInsertTime() == null : this.getInsertTime().equals(other.getInsertTime()))
            && (this.getInsertUser() == null ? other.getInsertUser() == null : this.getInsertUser().equals(other.getInsertUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAdapterCode() == null) ? 0 : getAdapterCode().hashCode());
        result = prime * result + ((getOpsCode() == null) ? 0 : getOpsCode().hashCode());
        result = prime * result + ((getInfo() == null) ? 0 : getInfo().hashCode());
        result = prime * result + ((getCodeType() == null) ? 0 : getCodeType().hashCode());
        result = prime * result + ((getInsertTime() == null) ? 0 : getInsertTime().hashCode());
        result = prime * result + ((getInsertUser() == null) ? 0 : getInsertUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        return result;
    }
}