package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPoAutosendRemarkConfig implements Serializable {
    private Long id;

    private String deptno;

    private String modelno;

    private String remark;

    private Date insertTime;

    private String insertUser;

    private Date updateTime;

    private String updateUser;

    private Boolean isDeleted;

    private String customerNo;

    private String supplierClass;

    private Boolean fuzzy;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getSupplierClass() {
        return supplierClass;
    }

    public void setSupplierClass(String supplierClass) {
        this.supplierClass = supplierClass == null ? null : supplierClass.trim();
    }

    public Boolean getFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(Boolean fuzzy) {
        this.fuzzy = fuzzy;
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
        OpsPoAutosendRemarkConfig other = (OpsPoAutosendRemarkConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getInsertTime() == null ? other.getInsertTime() == null : this.getInsertTime().equals(other.getInsertTime()))
            && (this.getInsertUser() == null ? other.getInsertUser() == null : this.getInsertUser().equals(other.getInsertUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getSupplierClass() == null ? other.getSupplierClass() == null : this.getSupplierClass().equals(other.getSupplierClass()))
            && (this.getFuzzy() == null ? other.getFuzzy() == null : this.getFuzzy().equals(other.getFuzzy()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getInsertTime() == null) ? 0 : getInsertTime().hashCode());
        result = prime * result + ((getInsertUser() == null) ? 0 : getInsertUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getSupplierClass() == null) ? 0 : getSupplierClass().hashCode());
        result = prime * result + ((getFuzzy() == null) ? 0 : getFuzzy().hashCode());
        return result;
    }
}