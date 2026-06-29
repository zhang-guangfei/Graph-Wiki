package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInquiryCodeConfig implements Serializable {
    private Long id;

    private String codeType;

    private String opsReasonCode;

    private String opsReasonDesc;

    private String gzReasonCode;

    private String gzReasonDesc;

    private String as400ReasonCode;

    private String as400ReasonDesc;

    private String manuReasonCode;

    private String manuReasonDesc;

    private String purchaseReasonCode;

    private String purchaseReasonDesc;

    private Boolean idDeleted;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

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

    public String getGzReasonCode() {
        return gzReasonCode;
    }

    public void setGzReasonCode(String gzReasonCode) {
        this.gzReasonCode = gzReasonCode == null ? null : gzReasonCode.trim();
    }

    public String getGzReasonDesc() {
        return gzReasonDesc;
    }

    public void setGzReasonDesc(String gzReasonDesc) {
        this.gzReasonDesc = gzReasonDesc == null ? null : gzReasonDesc.trim();
    }

    public String getAs400ReasonCode() {
        return as400ReasonCode;
    }

    public void setAs400ReasonCode(String as400ReasonCode) {
        this.as400ReasonCode = as400ReasonCode == null ? null : as400ReasonCode.trim();
    }

    public String getAs400ReasonDesc() {
        return as400ReasonDesc;
    }

    public void setAs400ReasonDesc(String as400ReasonDesc) {
        this.as400ReasonDesc = as400ReasonDesc == null ? null : as400ReasonDesc.trim();
    }

    public String getManuReasonCode() {
        return manuReasonCode;
    }

    public void setManuReasonCode(String manuReasonCode) {
        this.manuReasonCode = manuReasonCode == null ? null : manuReasonCode.trim();
    }

    public String getManuReasonDesc() {
        return manuReasonDesc;
    }

    public void setManuReasonDesc(String manuReasonDesc) {
        this.manuReasonDesc = manuReasonDesc == null ? null : manuReasonDesc.trim();
    }

    public String getPurchaseReasonCode() {
        return purchaseReasonCode;
    }

    public void setPurchaseReasonCode(String purchaseReasonCode) {
        this.purchaseReasonCode = purchaseReasonCode == null ? null : purchaseReasonCode.trim();
    }

    public String getPurchaseReasonDesc() {
        return purchaseReasonDesc;
    }

    public void setPurchaseReasonDesc(String purchaseReasonDesc) {
        this.purchaseReasonDesc = purchaseReasonDesc == null ? null : purchaseReasonDesc.trim();
    }

    public Boolean getIdDeleted() {
        return idDeleted;
    }

    public void setIdDeleted(Boolean idDeleted) {
        this.idDeleted = idDeleted;
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
        OpsInquiryCodeConfig other = (OpsInquiryCodeConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCodeType() == null ? other.getCodeType() == null : this.getCodeType().equals(other.getCodeType()))
            && (this.getOpsReasonCode() == null ? other.getOpsReasonCode() == null : this.getOpsReasonCode().equals(other.getOpsReasonCode()))
            && (this.getOpsReasonDesc() == null ? other.getOpsReasonDesc() == null : this.getOpsReasonDesc().equals(other.getOpsReasonDesc()))
            && (this.getGzReasonCode() == null ? other.getGzReasonCode() == null : this.getGzReasonCode().equals(other.getGzReasonCode()))
            && (this.getGzReasonDesc() == null ? other.getGzReasonDesc() == null : this.getGzReasonDesc().equals(other.getGzReasonDesc()))
            && (this.getAs400ReasonCode() == null ? other.getAs400ReasonCode() == null : this.getAs400ReasonCode().equals(other.getAs400ReasonCode()))
            && (this.getAs400ReasonDesc() == null ? other.getAs400ReasonDesc() == null : this.getAs400ReasonDesc().equals(other.getAs400ReasonDesc()))
            && (this.getManuReasonCode() == null ? other.getManuReasonCode() == null : this.getManuReasonCode().equals(other.getManuReasonCode()))
            && (this.getManuReasonDesc() == null ? other.getManuReasonDesc() == null : this.getManuReasonDesc().equals(other.getManuReasonDesc()))
            && (this.getPurchaseReasonCode() == null ? other.getPurchaseReasonCode() == null : this.getPurchaseReasonCode().equals(other.getPurchaseReasonCode()))
            && (this.getPurchaseReasonDesc() == null ? other.getPurchaseReasonDesc() == null : this.getPurchaseReasonDesc().equals(other.getPurchaseReasonDesc()))
            && (this.getIdDeleted() == null ? other.getIdDeleted() == null : this.getIdDeleted().equals(other.getIdDeleted()))
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
        result = prime * result + ((getGzReasonCode() == null) ? 0 : getGzReasonCode().hashCode());
        result = prime * result + ((getGzReasonDesc() == null) ? 0 : getGzReasonDesc().hashCode());
        result = prime * result + ((getAs400ReasonCode() == null) ? 0 : getAs400ReasonCode().hashCode());
        result = prime * result + ((getAs400ReasonDesc() == null) ? 0 : getAs400ReasonDesc().hashCode());
        result = prime * result + ((getManuReasonCode() == null) ? 0 : getManuReasonCode().hashCode());
        result = prime * result + ((getManuReasonDesc() == null) ? 0 : getManuReasonDesc().hashCode());
        result = prime * result + ((getPurchaseReasonCode() == null) ? 0 : getPurchaseReasonCode().hashCode());
        result = prime * result + ((getPurchaseReasonDesc() == null) ? 0 : getPurchaseReasonDesc().hashCode());
        result = prime * result + ((getIdDeleted() == null) ? 0 : getIdDeleted().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}