package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class BinOrderCalc implements Serializable {
    private Long id;

    private Integer status;

    private Date calcTime;

    private String calcPsn;

    private String confirmPsn;

    private Boolean gss;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Short calcType;

    private String warehouseCode;

    private String custoemrNo;

    private Long propertyId;

    private Integer reportType;

    private Date calcFinishTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCalcTime() {
        return calcTime;
    }

    public void setCalcTime(Date calcTime) {
        this.calcTime = calcTime;
    }

    public String getCalcPsn() {
        return calcPsn;
    }

    public void setCalcPsn(String calcPsn) {
        this.calcPsn = calcPsn == null ? null : calcPsn.trim();
    }

    public String getConfirmPsn() {
        return confirmPsn;
    }

    public void setConfirmPsn(String confirmPsn) {
        this.confirmPsn = confirmPsn == null ? null : confirmPsn.trim();
    }

    public Boolean getGss() {
        return gss;
    }

    public void setGss(Boolean gss) {
        this.gss = gss;
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

    public Short getCalcType() {
        return calcType;
    }

    public void setCalcType(Short calcType) {
        this.calcType = calcType;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getCustoemrNo() {
        return custoemrNo;
    }

    public void setCustoemrNo(String custoemrNo) {
        this.custoemrNo = custoemrNo == null ? null : custoemrNo.trim();
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public Date getCalcFinishTime() {
        return calcFinishTime;
    }

    public void setCalcFinishTime(Date calcFinishTime) {
        this.calcFinishTime = calcFinishTime;
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
        BinOrderCalc other = (BinOrderCalc) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCalcTime() == null ? other.getCalcTime() == null : this.getCalcTime().equals(other.getCalcTime()))
            && (this.getCalcPsn() == null ? other.getCalcPsn() == null : this.getCalcPsn().equals(other.getCalcPsn()))
            && (this.getConfirmPsn() == null ? other.getConfirmPsn() == null : this.getConfirmPsn().equals(other.getConfirmPsn()))
            && (this.getGss() == null ? other.getGss() == null : this.getGss().equals(other.getGss()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCalcType() == null ? other.getCalcType() == null : this.getCalcType().equals(other.getCalcType()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getCustoemrNo() == null ? other.getCustoemrNo() == null : this.getCustoemrNo().equals(other.getCustoemrNo()))
            && (this.getPropertyId() == null ? other.getPropertyId() == null : this.getPropertyId().equals(other.getPropertyId()))
            && (this.getReportType() == null ? other.getReportType() == null : this.getReportType().equals(other.getReportType()))
            && (this.getCalcFinishTime() == null ? other.getCalcFinishTime() == null : this.getCalcFinishTime().equals(other.getCalcFinishTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCalcTime() == null) ? 0 : getCalcTime().hashCode());
        result = prime * result + ((getCalcPsn() == null) ? 0 : getCalcPsn().hashCode());
        result = prime * result + ((getConfirmPsn() == null) ? 0 : getConfirmPsn().hashCode());
        result = prime * result + ((getGss() == null) ? 0 : getGss().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCalcType() == null) ? 0 : getCalcType().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getCustoemrNo() == null) ? 0 : getCustoemrNo().hashCode());
        result = prime * result + ((getPropertyId() == null) ? 0 : getPropertyId().hashCode());
        result = prime * result + ((getReportType() == null) ? 0 : getReportType().hashCode());
        result = prime * result + ((getCalcFinishTime() == null) ? 0 : getCalcFinishTime().hashCode());
        return result;
    }
}