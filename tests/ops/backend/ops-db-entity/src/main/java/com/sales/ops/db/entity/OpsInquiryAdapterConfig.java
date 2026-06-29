package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsInquiryAdapterConfig implements Serializable {
    private Long id;

    private String supplierId;

    private String supplierClass;

    private String supplierName;

    private String connectType;

    private String sendClassName;

    private String sendMethodName;

    private String callbackClassName;

    private String callbackMethodName;

    private Boolean isDeleted;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String callbackIncrementLastid;

    private Date callbackIncrementLasttime;

    private String callbackIncrementClassName;

    private String callbackIncrementMethodName;

    private String warningMailTo;

    private String warningMailCc;

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

    public String getSupplierClass() {
        return supplierClass;
    }

    public void setSupplierClass(String supplierClass) {
        this.supplierClass = supplierClass == null ? null : supplierClass.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getConnectType() {
        return connectType;
    }

    public void setConnectType(String connectType) {
        this.connectType = connectType == null ? null : connectType.trim();
    }

    public String getSendClassName() {
        return sendClassName;
    }

    public void setSendClassName(String sendClassName) {
        this.sendClassName = sendClassName == null ? null : sendClassName.trim();
    }

    public String getSendMethodName() {
        return sendMethodName;
    }

    public void setSendMethodName(String sendMethodName) {
        this.sendMethodName = sendMethodName == null ? null : sendMethodName.trim();
    }

    public String getCallbackClassName() {
        return callbackClassName;
    }

    public void setCallbackClassName(String callbackClassName) {
        this.callbackClassName = callbackClassName == null ? null : callbackClassName.trim();
    }

    public String getCallbackMethodName() {
        return callbackMethodName;
    }

    public void setCallbackMethodName(String callbackMethodName) {
        this.callbackMethodName = callbackMethodName == null ? null : callbackMethodName.trim();
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

    public String getCallbackIncrementLastid() {
        return callbackIncrementLastid;
    }

    public void setCallbackIncrementLastid(String callbackIncrementLastid) {
        this.callbackIncrementLastid = callbackIncrementLastid == null ? null : callbackIncrementLastid.trim();
    }

    public Date getCallbackIncrementLasttime() {
        return callbackIncrementLasttime;
    }

    public void setCallbackIncrementLasttime(Date callbackIncrementLasttime) {
        this.callbackIncrementLasttime = callbackIncrementLasttime;
    }

    public String getCallbackIncrementClassName() {
        return callbackIncrementClassName;
    }

    public void setCallbackIncrementClassName(String callbackIncrementClassName) {
        this.callbackIncrementClassName = callbackIncrementClassName == null ? null : callbackIncrementClassName.trim();
    }

    public String getCallbackIncrementMethodName() {
        return callbackIncrementMethodName;
    }

    public void setCallbackIncrementMethodName(String callbackIncrementMethodName) {
        this.callbackIncrementMethodName = callbackIncrementMethodName == null ? null : callbackIncrementMethodName.trim();
    }

    public String getWarningMailTo() {
        return warningMailTo;
    }

    public void setWarningMailTo(String warningMailTo) {
        this.warningMailTo = warningMailTo == null ? null : warningMailTo.trim();
    }

    public String getWarningMailCc() {
        return warningMailCc;
    }

    public void setWarningMailCc(String warningMailCc) {
        this.warningMailCc = warningMailCc == null ? null : warningMailCc.trim();
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
        OpsInquiryAdapterConfig other = (OpsInquiryAdapterConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSupplierId() == null ? other.getSupplierId() == null : this.getSupplierId().equals(other.getSupplierId()))
            && (this.getSupplierClass() == null ? other.getSupplierClass() == null : this.getSupplierClass().equals(other.getSupplierClass()))
            && (this.getSupplierName() == null ? other.getSupplierName() == null : this.getSupplierName().equals(other.getSupplierName()))
            && (this.getConnectType() == null ? other.getConnectType() == null : this.getConnectType().equals(other.getConnectType()))
            && (this.getSendClassName() == null ? other.getSendClassName() == null : this.getSendClassName().equals(other.getSendClassName()))
            && (this.getSendMethodName() == null ? other.getSendMethodName() == null : this.getSendMethodName().equals(other.getSendMethodName()))
            && (this.getCallbackClassName() == null ? other.getCallbackClassName() == null : this.getCallbackClassName().equals(other.getCallbackClassName()))
            && (this.getCallbackMethodName() == null ? other.getCallbackMethodName() == null : this.getCallbackMethodName().equals(other.getCallbackMethodName()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCallbackIncrementLastid() == null ? other.getCallbackIncrementLastid() == null : this.getCallbackIncrementLastid().equals(other.getCallbackIncrementLastid()))
            && (this.getCallbackIncrementLasttime() == null ? other.getCallbackIncrementLasttime() == null : this.getCallbackIncrementLasttime().equals(other.getCallbackIncrementLasttime()))
            && (this.getCallbackIncrementClassName() == null ? other.getCallbackIncrementClassName() == null : this.getCallbackIncrementClassName().equals(other.getCallbackIncrementClassName()))
            && (this.getCallbackIncrementMethodName() == null ? other.getCallbackIncrementMethodName() == null : this.getCallbackIncrementMethodName().equals(other.getCallbackIncrementMethodName()))
            && (this.getWarningMailTo() == null ? other.getWarningMailTo() == null : this.getWarningMailTo().equals(other.getWarningMailTo()))
            && (this.getWarningMailCc() == null ? other.getWarningMailCc() == null : this.getWarningMailCc().equals(other.getWarningMailCc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSupplierId() == null) ? 0 : getSupplierId().hashCode());
        result = prime * result + ((getSupplierClass() == null) ? 0 : getSupplierClass().hashCode());
        result = prime * result + ((getSupplierName() == null) ? 0 : getSupplierName().hashCode());
        result = prime * result + ((getConnectType() == null) ? 0 : getConnectType().hashCode());
        result = prime * result + ((getSendClassName() == null) ? 0 : getSendClassName().hashCode());
        result = prime * result + ((getSendMethodName() == null) ? 0 : getSendMethodName().hashCode());
        result = prime * result + ((getCallbackClassName() == null) ? 0 : getCallbackClassName().hashCode());
        result = prime * result + ((getCallbackMethodName() == null) ? 0 : getCallbackMethodName().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCallbackIncrementLastid() == null) ? 0 : getCallbackIncrementLastid().hashCode());
        result = prime * result + ((getCallbackIncrementLasttime() == null) ? 0 : getCallbackIncrementLasttime().hashCode());
        result = prime * result + ((getCallbackIncrementClassName() == null) ? 0 : getCallbackIncrementClassName().hashCode());
        result = prime * result + ((getCallbackIncrementMethodName() == null) ? 0 : getCallbackIncrementMethodName().hashCode());
        result = prime * result + ((getWarningMailTo() == null) ? 0 : getWarningMailTo().hashCode());
        result = prime * result + ((getWarningMailCc() == null) ? 0 : getWarningMailCc().hashCode());
        return result;
    }
}