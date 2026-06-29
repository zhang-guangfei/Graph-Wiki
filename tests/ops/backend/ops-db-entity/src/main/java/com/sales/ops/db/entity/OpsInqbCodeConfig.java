package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInqbCodeConfig implements Serializable {
    private Long id;

    private String codeType;

    private String opsReasonCode;

    private String opsReasonDesc;

    private String supplieCode;

    private String supplieReasonCode;

    private String supplieReasonDesc;

    private Boolean isDeleted;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private String updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType == null ? null : codeType.trim();
    }

    public String getOpsReasonCode() {
        return opsReasonCode;
    }

    public void setOpsReasonCode(String opsReasonCode) {
        this.opsReasonCode = opsReasonCode == null ? null : opsReasonCode.trim();
    }

    public String getOpsReasonDesc() {
        return opsReasonDesc;
    }

    public void setOpsReasonDesc(String opsReasonDesc) {
        this.opsReasonDesc = opsReasonDesc == null ? null : opsReasonDesc.trim();
    }

    public String getSupplieCode() {
        return supplieCode;
    }

    public void setSupplieCode(String supplieCode) {
        this.supplieCode = supplieCode == null ? null : supplieCode.trim();
    }

    public String getSupplieReasonCode() {
        return supplieReasonCode;
    }

    public void setSupplieReasonCode(String supplieReasonCode) {
        this.supplieReasonCode = supplieReasonCode == null ? null : supplieReasonCode.trim();
    }

    public String getSupplieReasonDesc() {
        return supplieReasonDesc;
    }

    public void setSupplieReasonDesc(String supplieReasonDesc) {
        this.supplieReasonDesc = supplieReasonDesc == null ? null : supplieReasonDesc.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
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
        OpsInqbCodeConfig other = (OpsInqbCodeConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCodeType() == null ? other.getCodeType() == null : this.getCodeType().equals(other.getCodeType()))
            && (this.getOpsReasonCode() == null ? other.getOpsReasonCode() == null : this.getOpsReasonCode().equals(other.getOpsReasonCode()))
            && (this.getOpsReasonDesc() == null ? other.getOpsReasonDesc() == null : this.getOpsReasonDesc().equals(other.getOpsReasonDesc()))
            && (this.getSupplieCode() == null ? other.getSupplieCode() == null : this.getSupplieCode().equals(other.getSupplieCode()))
            && (this.getSupplieReasonCode() == null ? other.getSupplieReasonCode() == null : this.getSupplieReasonCode().equals(other.getSupplieReasonCode()))
            && (this.getSupplieReasonDesc() == null ? other.getSupplieReasonDesc() == null : this.getSupplieReasonDesc().equals(other.getSupplieReasonDesc()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCodeType() == null) ? 0 : getCodeType().hashCode());
        result = prime * result + ((getOpsReasonCode() == null) ? 0 : getOpsReasonCode().hashCode());
        result = prime * result + ((getOpsReasonDesc() == null) ? 0 : getOpsReasonDesc().hashCode());
        result = prime * result + ((getSupplieCode() == null) ? 0 : getSupplieCode().hashCode());
        result = prime * result + ((getSupplieReasonCode() == null) ? 0 : getSupplieReasonCode().hashCode());
        result = prime * result + ((getSupplieReasonDesc() == null) ? 0 : getSupplieReasonDesc().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}