package com.sales.ops.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BinOrderApp implements Serializable {
    private Long appId;

    private Short status;

    private Date applyTime;

    private String appUser;

    private String approveText;

    private Date approveTime;

    private String applyText;

    private Integer modelCount;

    private Integer modelQty;

    private String approveUser;

    private Long calcId;

    private String warehouseCode;

    private Integer stockType;

    private String customerNo;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private Long propertyId;

    private BigDecimal eamount;

    private static final long serialVersionUID = 1L;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getAppUser() {
        return appUser;
    }

    public void setAppUser(String appUser) {
        this.appUser = appUser == null ? null : appUser.trim();
    }

    public String getApproveText() {
        return approveText;
    }

    public void setApproveText(String approveText) {
        this.approveText = approveText == null ? null : approveText.trim();
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getApplyText() {
        return applyText;
    }

    public void setApplyText(String applyText) {
        this.applyText = applyText == null ? null : applyText.trim();
    }

    public Integer getModelCount() {
        return modelCount;
    }

    public void setModelCount(Integer modelCount) {
        this.modelCount = modelCount;
    }

    public Integer getModelQty() {
        return modelQty;
    }

    public void setModelQty(Integer modelQty) {
        this.modelQty = modelQty;
    }

    public String getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser == null ? null : approveUser.trim();
    }

    public Long getCalcId() {
        return calcId;
    }

    public void setCalcId(Long calcId) {
        this.calcId = calcId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public BigDecimal getEamount() {
        return eamount;
    }

    public void setEamount(BigDecimal eamount) {
        this.eamount = eamount;
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
        BinOrderApp other = (BinOrderApp) that;
        return (this.getAppId() == null ? other.getAppId() == null : this.getAppId().equals(other.getAppId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getApplyTime() == null ? other.getApplyTime() == null : this.getApplyTime().equals(other.getApplyTime()))
            && (this.getAppUser() == null ? other.getAppUser() == null : this.getAppUser().equals(other.getAppUser()))
            && (this.getApproveText() == null ? other.getApproveText() == null : this.getApproveText().equals(other.getApproveText()))
            && (this.getApproveTime() == null ? other.getApproveTime() == null : this.getApproveTime().equals(other.getApproveTime()))
            && (this.getApplyText() == null ? other.getApplyText() == null : this.getApplyText().equals(other.getApplyText()))
            && (this.getModelCount() == null ? other.getModelCount() == null : this.getModelCount().equals(other.getModelCount()))
            && (this.getModelQty() == null ? other.getModelQty() == null : this.getModelQty().equals(other.getModelQty()))
            && (this.getApproveUser() == null ? other.getApproveUser() == null : this.getApproveUser().equals(other.getApproveUser()))
            && (this.getCalcId() == null ? other.getCalcId() == null : this.getCalcId().equals(other.getCalcId()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getStockType() == null ? other.getStockType() == null : this.getStockType().equals(other.getStockType()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getPropertyId() == null ? other.getPropertyId() == null : this.getPropertyId().equals(other.getPropertyId()))
            && (this.getEamount() == null ? other.getEamount() == null : this.getEamount().equals(other.getEamount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAppId() == null) ? 0 : getAppId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getApplyTime() == null) ? 0 : getApplyTime().hashCode());
        result = prime * result + ((getAppUser() == null) ? 0 : getAppUser().hashCode());
        result = prime * result + ((getApproveText() == null) ? 0 : getApproveText().hashCode());
        result = prime * result + ((getApproveTime() == null) ? 0 : getApproveTime().hashCode());
        result = prime * result + ((getApplyText() == null) ? 0 : getApplyText().hashCode());
        result = prime * result + ((getModelCount() == null) ? 0 : getModelCount().hashCode());
        result = prime * result + ((getModelQty() == null) ? 0 : getModelQty().hashCode());
        result = prime * result + ((getApproveUser() == null) ? 0 : getApproveUser().hashCode());
        result = prime * result + ((getCalcId() == null) ? 0 : getCalcId().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getStockType() == null) ? 0 : getStockType().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getPropertyId() == null) ? 0 : getPropertyId().hashCode());
        result = prime * result + ((getEamount() == null) ? 0 : getEamount().hashCode());
        return result;
    }
}