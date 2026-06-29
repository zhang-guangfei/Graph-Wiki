package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsDeliveryEstimateResult implements Serializable {
    private Long id;

    private String associationKey;

    private String modelNo;

    private String quantityLevel;

    private Integer qty;

    private String deptNo;

    private String customerNo;

    private String endUser;

    private String pplNo;

    private String projectNo;

    private String groupCustomerNo;

    private String salesInfoNo;

    private Integer deliveryDays;

    private Integer instock;

    private String createUser;

    private Date createTime;

    private String allotResult;

    private String gatherWarehouseCode;

    //拆分类型 型号拆分 数量拆分 采购
    private String doSource;

    //出库方式 采购 异地调拨 在库
    private String ckType;
    //供应方
    private String supplier;

    //运输方式 空运 快船 海运 陆运
    private String transportWay;

    private String detail;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssociationKey() {
        return associationKey;
    }

    public void setAssociationKey(String associationKey) {
        this.associationKey = associationKey == null ? null : associationKey.trim();
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo == null ? null : modelNo.trim();
    }

    public String getQuantityLevel() {
        return quantityLevel;
    }

    public void setQuantityLevel(String quantityLevel) {
        this.quantityLevel = quantityLevel == null ? null : quantityLevel.trim();
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser == null ? null : endUser.trim();
    }

    public String getPplNo() {
        return pplNo;
    }

    public void setPplNo(String pplNo) {
        this.pplNo = pplNo == null ? null : pplNo.trim();
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo == null ? null : groupCustomerNo.trim();
    }

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo == null ? null : salesInfoNo.trim();
    }

    public Integer getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(Integer deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public Integer getInstock() {
        return instock;
    }

    public void setInstock(Integer instock) {
        this.instock = instock;
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


    public String getAllotResult() {
        return allotResult;
    }

    public void setAllotResult(String allotResult) {
        this.allotResult = allotResult;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getGatherWarehouseCode() {
        return gatherWarehouseCode;
    }

    public void setGatherWarehouseCode(String gatherWarehouseCode) {
        this.gatherWarehouseCode = gatherWarehouseCode == null ? null : gatherWarehouseCode.trim();
    }

    public String getDoSource() {
        return doSource;
    }

    public void setDoSource(String doSource) {
        this.doSource = doSource;
    }

    public String getCkType() {
        return ckType;
    }

    public void setCkType(String ckType) {
        this.ckType = ckType;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getTransportWay() {
        return transportWay;
    }

    public void setTransportWay(String transportWay) {
        this.transportWay = transportWay;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
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
        OpsDeliveryEstimateResult other = (OpsDeliveryEstimateResult) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAssociationKey() == null ? other.getAssociationKey() == null : this.getAssociationKey().equals(other.getAssociationKey()))
            && (this.getModelNo() == null ? other.getModelNo() == null : this.getModelNo().equals(other.getModelNo()))
            && (this.getQuantityLevel() == null ? other.getQuantityLevel() == null : this.getQuantityLevel().equals(other.getQuantityLevel()))
            && (this.getDeptNo() == null ? other.getDeptNo() == null : this.getDeptNo().equals(other.getDeptNo()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getEndUser() == null ? other.getEndUser() == null : this.getEndUser().equals(other.getEndUser()))
            && (this.getPplNo() == null ? other.getPplNo() == null : this.getPplNo().equals(other.getPplNo()))
            && (this.getProjectNo() == null ? other.getProjectNo() == null : this.getProjectNo().equals(other.getProjectNo()))
            && (this.getGroupCustomerNo() == null ? other.getGroupCustomerNo() == null : this.getGroupCustomerNo().equals(other.getGroupCustomerNo()))
            && (this.getSalesInfoNo() == null ? other.getSalesInfoNo() == null : this.getSalesInfoNo().equals(other.getSalesInfoNo()))
            && (this.getDeliveryDays() == null ? other.getDeliveryDays() == null : this.getDeliveryDays().equals(other.getDeliveryDays()))
            && (this.getInstock() == null ? other.getInstock() == null : this.getInstock().equals(other.getInstock()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getAllotResult() == null ? other.getAllotResult() == null : this.getAllotResult().equals(other.getAllotResult()))
            && (this.getGatherWarehouseCode() == null ? other.getGatherWarehouseCode() == null : this.getGatherWarehouseCode().equals(other.getGatherWarehouseCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAssociationKey() == null) ? 0 : getAssociationKey().hashCode());
        result = prime * result + ((getModelNo() == null) ? 0 : getModelNo().hashCode());
        result = prime * result + ((getQuantityLevel() == null) ? 0 : getQuantityLevel().hashCode());
        result = prime * result + ((getDeptNo() == null) ? 0 : getDeptNo().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getEndUser() == null) ? 0 : getEndUser().hashCode());
        result = prime * result + ((getPplNo() == null) ? 0 : getPplNo().hashCode());
        result = prime * result + ((getProjectNo() == null) ? 0 : getProjectNo().hashCode());
        result = prime * result + ((getGroupCustomerNo() == null) ? 0 : getGroupCustomerNo().hashCode());
        result = prime * result + ((getSalesInfoNo() == null) ? 0 : getSalesInfoNo().hashCode());
        result = prime * result + ((getDeliveryDays() == null) ? 0 : getDeliveryDays().hashCode());
        result = prime * result + ((getInstock() == null) ? 0 : getInstock().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getAllotResult() == null) ? 0 : getAllotResult().hashCode());
        result = prime * result + ((getGatherWarehouseCode() == null) ? 0 : getGatherWarehouseCode().hashCode());
        return result;
    }
}