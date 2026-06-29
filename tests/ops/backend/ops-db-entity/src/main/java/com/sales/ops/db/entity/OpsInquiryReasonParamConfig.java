package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInquiryReasonParamConfig implements Serializable {
    private Long id;

    private String supplierId;

    private String supplierName;

    private String opsReasonCode;

    private String paramName;

    private String paramType;

    private String dataLength;

    private Boolean isFixedLength;

    private String otherTypeInfo;

    private String paramRemark;

    private Boolean isDeleted;

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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getOpsReasonCode() {
        return opsReasonCode;
    }

    public void setOpsReasonCode(String opsReasonCode) {
        this.opsReasonCode = opsReasonCode == null ? null : opsReasonCode.trim();
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType == null ? null : paramType.trim();
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength == null ? null : dataLength.trim();
    }

    public Boolean getIsFixedLength() {
        return isFixedLength;
    }

    public void setIsFixedLength(Boolean isFixedLength) {
        this.isFixedLength = isFixedLength;
    }

    public String getOtherTypeInfo() {
        return otherTypeInfo;
    }

    public void setOtherTypeInfo(String otherTypeInfo) {
        this.otherTypeInfo = otherTypeInfo == null ? null : otherTypeInfo.trim();
    }

    public String getParamRemark() {
        return paramRemark;
    }

    public void setParamRemark(String paramRemark) {
        this.paramRemark = paramRemark == null ? null : paramRemark.trim();
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
        OpsInquiryReasonParamConfig other = (OpsInquiryReasonParamConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSupplierId() == null ? other.getSupplierId() == null : this.getSupplierId().equals(other.getSupplierId()))
            && (this.getSupplierName() == null ? other.getSupplierName() == null : this.getSupplierName().equals(other.getSupplierName()))
            && (this.getOpsReasonCode() == null ? other.getOpsReasonCode() == null : this.getOpsReasonCode().equals(other.getOpsReasonCode()))
            && (this.getParamName() == null ? other.getParamName() == null : this.getParamName().equals(other.getParamName()))
            && (this.getParamType() == null ? other.getParamType() == null : this.getParamType().equals(other.getParamType()))
            && (this.getDataLength() == null ? other.getDataLength() == null : this.getDataLength().equals(other.getDataLength()))
            && (this.getIsFixedLength() == null ? other.getIsFixedLength() == null : this.getIsFixedLength().equals(other.getIsFixedLength()))
            && (this.getOtherTypeInfo() == null ? other.getOtherTypeInfo() == null : this.getOtherTypeInfo().equals(other.getOtherTypeInfo()))
            && (this.getParamRemark() == null ? other.getParamRemark() == null : this.getParamRemark().equals(other.getParamRemark()))
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
        result = prime * result + ((getSupplierId() == null) ? 0 : getSupplierId().hashCode());
        result = prime * result + ((getSupplierName() == null) ? 0 : getSupplierName().hashCode());
        result = prime * result + ((getOpsReasonCode() == null) ? 0 : getOpsReasonCode().hashCode());
        result = prime * result + ((getParamName() == null) ? 0 : getParamName().hashCode());
        result = prime * result + ((getParamType() == null) ? 0 : getParamType().hashCode());
        result = prime * result + ((getDataLength() == null) ? 0 : getDataLength().hashCode());
        result = prime * result + ((getIsFixedLength() == null) ? 0 : getIsFixedLength().hashCode());
        result = prime * result + ((getOtherTypeInfo() == null) ? 0 : getOtherTypeInfo().hashCode());
        result = prime * result + ((getParamRemark() == null) ? 0 : getParamRemark().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}