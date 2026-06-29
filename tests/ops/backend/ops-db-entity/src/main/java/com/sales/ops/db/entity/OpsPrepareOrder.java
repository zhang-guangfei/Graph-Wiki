package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsPrepareOrder implements Serializable {
    private Long id;

    private String orderFno;

    private String orderNo;

    private Integer itemNo;

    private String supplierCode;

    private String transType;

    private String batchNo;

    private Date hopeExportDate;

    private Integer preQty;

    private Date sendTime;

    private String modelNo;

    private Date dlvDate;

    private String customerNo;

    private String availableCustomers;

    private String applyNo;

    private Long applyId;

    private Integer applyQty;

    private String warehouseCode;

    private String rohs;

    private String remark;

    private String applyPsn;

    private String applyDeptNo;

    private Date applyTime;

    private Long inventoryPropertyId;

    private Integer useQty;

    private String manuOrderNo;

    private String manuFactSupplierCode;

    private Date verificationDate;

    private Date manuDlvDate;

    private Integer status;

    private Boolean delFlag;

    private Boolean canUseFlag;

    private Integer version;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private Long applyDetailId;

    private Date supplierOperateTime;

    private String supplierExpinvCode;

    private String supplierExpinvRemark;

    private Integer remainQty;

    private Date terminateDate;

    private Date finalAccountDate;

    private Date liquidationDate;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno == null ? null : orderFno.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public Date getHopeExportDate() {
        return hopeExportDate;
    }

    public void setHopeExportDate(Date hopeExportDate) {
        this.hopeExportDate = hopeExportDate;
    }

    public Integer getPreQty() {
        return preQty;
    }

    public void setPreQty(Integer preQty) {
        this.preQty = preQty;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public Date getDlvDate() {
        return dlvDate;
    }

    public void setDlvDate(Date dlvDate) {
        this.dlvDate = dlvDate;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getAvailableCustomers() {
        return availableCustomers;
    }

    public void setAvailableCustomers(String availableCustomers) {
        this.availableCustomers = availableCustomers == null ? null : availableCustomers.trim();
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo == null ? null : applyNo.trim();
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Integer getApplyQty() {
        return applyQty;
    }

    public void setApplyQty(Integer applyQty) {
        this.applyQty = applyQty;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getRohs() {
        return rohs;
    }

    public void setRohs(String rohs) {
        this.rohs = rohs == null ? null : rohs.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getApplyPsn() {
        return applyPsn;
    }

    public void setApplyPsn(String applyPsn) {
        this.applyPsn = applyPsn == null ? null : applyPsn.trim();
    }

    public String getApplyDeptNo() {
        return applyDeptNo;
    }

    public void setApplyDeptNo(String applyDeptNo) {
        this.applyDeptNo = applyDeptNo == null ? null : applyDeptNo.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Long getInventoryPropertyId() {
        return inventoryPropertyId;
    }

    public void setInventoryPropertyId(Long inventoryPropertyId) {
        this.inventoryPropertyId = inventoryPropertyId;
    }

    public Integer getUseQty() {
        return useQty;
    }

    public void setUseQty(Integer useQty) {
        this.useQty = useQty;
    }

    public String getManuOrderNo() {
        return manuOrderNo;
    }

    public void setManuOrderNo(String manuOrderNo) {
        this.manuOrderNo = manuOrderNo == null ? null : manuOrderNo.trim();
    }

    public String getManuFactSupplierCode() {
        return manuFactSupplierCode;
    }

    public void setManuFactSupplierCode(String manuFactSupplierCode) {
        this.manuFactSupplierCode = manuFactSupplierCode == null ? null : manuFactSupplierCode.trim();
    }

    public Date getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(Date verificationDate) {
        this.verificationDate = verificationDate;
    }

    public Date getManuDlvDate() {
        return manuDlvDate;
    }

    public void setManuDlvDate(Date manuDlvDate) {
        this.manuDlvDate = manuDlvDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Boolean getCanUseFlag() {
        return canUseFlag;
    }

    public void setCanUseFlag(Boolean canUseFlag) {
        this.canUseFlag = canUseFlag;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public Long getApplyDetailId() {
        return applyDetailId;
    }

    public void setApplyDetailId(Long applyDetailId) {
        this.applyDetailId = applyDetailId;
    }

    public Date getSupplierOperateTime() {
        return supplierOperateTime;
    }

    public void setSupplierOperateTime(Date supplierOperateTime) {
        this.supplierOperateTime = supplierOperateTime;
    }

    public String getSupplierExpinvCode() {
        return supplierExpinvCode;
    }

    public void setSupplierExpinvCode(String supplierExpinvCode) {
        this.supplierExpinvCode = supplierExpinvCode == null ? null : supplierExpinvCode.trim();
    }

    public String getSupplierExpinvRemark() {
        return supplierExpinvRemark;
    }

    public void setSupplierExpinvRemark(String supplierExpinvRemark) {
        this.supplierExpinvRemark = supplierExpinvRemark == null ? null : supplierExpinvRemark.trim();
    }

    public Integer getRemainQty() {
        return remainQty;
    }

    public void setRemainQty(Integer remainQty) {
        this.remainQty = remainQty;
    }

    public Date getTerminateDate() {
        return terminateDate;
    }

    public void setTerminateDate(Date terminateDate) {
        this.terminateDate = terminateDate;
    }

    public Date getFinalAccountDate() {
        return finalAccountDate;
    }

    public void setFinalAccountDate(Date finalAccountDate) {
        this.finalAccountDate = finalAccountDate;
    }

    public Date getLiquidationDate() {
        return liquidationDate;
    }

    public void setLiquidationDate(Date liquidationDate) {
        this.liquidationDate = liquidationDate;
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
        OpsPrepareOrder other = (OpsPrepareOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrderFno() == null ? other.getOrderFno() == null : this.getOrderFno().equals(other.getOrderFno()))
            && (this.getOrderNo() == null ? other.getOrderNo() == null : this.getOrderNo().equals(other.getOrderNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getSupplierCode() == null ? other.getSupplierCode() == null : this.getSupplierCode().equals(other.getSupplierCode()))
            && (this.getTransType() == null ? other.getTransType() == null : this.getTransType().equals(other.getTransType()))
            && (this.getBatchNo() == null ? other.getBatchNo() == null : this.getBatchNo().equals(other.getBatchNo()))
            && (this.getHopeExportDate() == null ? other.getHopeExportDate() == null : this.getHopeExportDate().equals(other.getHopeExportDate()))
            && (this.getPreQty() == null ? other.getPreQty() == null : this.getPreQty().equals(other.getPreQty()))
            && (this.getSendTime() == null ? other.getSendTime() == null : this.getSendTime().equals(other.getSendTime()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getDlvDate() == null ? other.getDlvDate() == null : this.getDlvDate().equals(other.getDlvDate()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getAvailableCustomers() == null ? other.getAvailableCustomers() == null : this.getAvailableCustomers().equals(other.getAvailableCustomers()))
            && (this.getApplyNo() == null ? other.getApplyNo() == null : this.getApplyNo().equals(other.getApplyNo()))
            && (this.getApplyId() == null ? other.getApplyId() == null : this.getApplyId().equals(other.getApplyId()))
            && (this.getApplyQty() == null ? other.getApplyQty() == null : this.getApplyQty().equals(other.getApplyQty()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getRohs() == null ? other.getRohs() == null : this.getRohs().equals(other.getRohs()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getApplyPsn() == null ? other.getApplyPsn() == null : this.getApplyPsn().equals(other.getApplyPsn()))
            && (this.getApplyDeptNo() == null ? other.getApplyDeptNo() == null : this.getApplyDeptNo().equals(other.getApplyDeptNo()))
            && (this.getApplyTime() == null ? other.getApplyTime() == null : this.getApplyTime().equals(other.getApplyTime()))
            && (this.getInventoryPropertyId() == null ? other.getInventoryPropertyId() == null : this.getInventoryPropertyId().equals(other.getInventoryPropertyId()))
            && (this.getUseQty() == null ? other.getUseQty() == null : this.getUseQty().equals(other.getUseQty()))
            && (this.getManuOrderNo() == null ? other.getManuOrderNo() == null : this.getManuOrderNo().equals(other.getManuOrderNo()))
            && (this.getManuFactSupplierCode() == null ? other.getManuFactSupplierCode() == null : this.getManuFactSupplierCode().equals(other.getManuFactSupplierCode()))
            && (this.getVerificationDate() == null ? other.getVerificationDate() == null : this.getVerificationDate().equals(other.getVerificationDate()))
            && (this.getManuDlvDate() == null ? other.getManuDlvDate() == null : this.getManuDlvDate().equals(other.getManuDlvDate()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getCanUseFlag() == null ? other.getCanUseFlag() == null : this.getCanUseFlag().equals(other.getCanUseFlag()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getApplyDetailId() == null ? other.getApplyDetailId() == null : this.getApplyDetailId().equals(other.getApplyDetailId()))
            && (this.getSupplierOperateTime() == null ? other.getSupplierOperateTime() == null : this.getSupplierOperateTime().equals(other.getSupplierOperateTime()))
            && (this.getSupplierExpinvCode() == null ? other.getSupplierExpinvCode() == null : this.getSupplierExpinvCode().equals(other.getSupplierExpinvCode()))
            && (this.getSupplierExpinvRemark() == null ? other.getSupplierExpinvRemark() == null : this.getSupplierExpinvRemark().equals(other.getSupplierExpinvRemark()))
            && (this.getRemainQty() == null ? other.getRemainQty() == null : this.getRemainQty().equals(other.getRemainQty()))
            && (this.getTerminateDate() == null ? other.getTerminateDate() == null : this.getTerminateDate().equals(other.getTerminateDate()))
            && (this.getFinalAccountDate() == null ? other.getFinalAccountDate() == null : this.getFinalAccountDate().equals(other.getFinalAccountDate()))
            && (this.getLiquidationDate() == null ? other.getLiquidationDate() == null : this.getLiquidationDate().equals(other.getLiquidationDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOrderFno() == null) ? 0 : getOrderFno().hashCode());
        result = prime * result + ((getOrderNo() == null) ? 0 : getOrderNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getSupplierCode() == null) ? 0 : getSupplierCode().hashCode());
        result = prime * result + ((getTransType() == null) ? 0 : getTransType().hashCode());
        result = prime * result + ((getBatchNo() == null) ? 0 : getBatchNo().hashCode());
        result = prime * result + ((getHopeExportDate() == null) ? 0 : getHopeExportDate().hashCode());
        result = prime * result + ((getPreQty() == null) ? 0 : getPreQty().hashCode());
        result = prime * result + ((getSendTime() == null) ? 0 : getSendTime().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getDlvDate() == null) ? 0 : getDlvDate().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getAvailableCustomers() == null) ? 0 : getAvailableCustomers().hashCode());
        result = prime * result + ((getApplyNo() == null) ? 0 : getApplyNo().hashCode());
        result = prime * result + ((getApplyId() == null) ? 0 : getApplyId().hashCode());
        result = prime * result + ((getApplyQty() == null) ? 0 : getApplyQty().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getRohs() == null) ? 0 : getRohs().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getApplyPsn() == null) ? 0 : getApplyPsn().hashCode());
        result = prime * result + ((getApplyDeptNo() == null) ? 0 : getApplyDeptNo().hashCode());
        result = prime * result + ((getApplyTime() == null) ? 0 : getApplyTime().hashCode());
        result = prime * result + ((getInventoryPropertyId() == null) ? 0 : getInventoryPropertyId().hashCode());
        result = prime * result + ((getUseQty() == null) ? 0 : getUseQty().hashCode());
        result = prime * result + ((getManuOrderNo() == null) ? 0 : getManuOrderNo().hashCode());
        result = prime * result + ((getManuFactSupplierCode() == null) ? 0 : getManuFactSupplierCode().hashCode());
        result = prime * result + ((getVerificationDate() == null) ? 0 : getVerificationDate().hashCode());
        result = prime * result + ((getManuDlvDate() == null) ? 0 : getManuDlvDate().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getCanUseFlag() == null) ? 0 : getCanUseFlag().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getApplyDetailId() == null) ? 0 : getApplyDetailId().hashCode());
        result = prime * result + ((getSupplierOperateTime() == null) ? 0 : getSupplierOperateTime().hashCode());
        result = prime * result + ((getSupplierExpinvCode() == null) ? 0 : getSupplierExpinvCode().hashCode());
        result = prime * result + ((getSupplierExpinvRemark() == null) ? 0 : getSupplierExpinvRemark().hashCode());
        result = prime * result + ((getRemainQty() == null) ? 0 : getRemainQty().hashCode());
        result = prime * result + ((getTerminateDate() == null) ? 0 : getTerminateDate().hashCode());
        result = prime * result + ((getFinalAccountDate() == null) ? 0 : getFinalAccountDate().hashCode());
        result = prime * result + ((getLiquidationDate() == null) ? 0 : getLiquidationDate().hashCode());
        return result;
    }
}