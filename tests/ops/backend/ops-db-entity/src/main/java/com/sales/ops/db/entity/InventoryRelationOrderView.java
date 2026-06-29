package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class InventoryRelationOrderView implements Serializable {
    private Long inventoryId;

    private String warehouseCode;

    private String inventoryTypeCode;

    private String modelno;

    private String warehouseType;

    private String customerNo;

    private String groupCustomerNo;

    private String ppl;

    private String projectCode;

    private String salesInfoNo;

    private String deptCode;

    private String inventoryStatus;

    private Integer invQty;

    private Integer invPreQty;

    private String doId;

    private String orderId;

    private String orderItem;

    private Integer num;

    private Integer assNum;

    private String doSource;

    private String doType;

    private Integer doQty;

    private Integer doUseQty;

    private Date hopeDate;

    private String customer;

    private String userNo;

    private String deptNo;

    private String gatherWarehouseCode;

    private Date wlDate;

    private String dlvEntire;

    private static final long serialVersionUID = 1L;

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode == null ? null : inventoryTypeCode.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public String getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(String warehouseType) {
        this.warehouseType = warehouseType == null ? null : warehouseType.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo == null ? null : groupCustomerNo.trim();
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl == null ? null : ppl.trim();
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode == null ? null : projectCode.trim();
    }

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo == null ? null : salesInfoNo.trim();
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus == null ? null : inventoryStatus.trim();
    }

    public Integer getInvQty() {
        return invQty;
    }

    public void setInvQty(Integer invQty) {
        this.invQty = invQty;
    }

    public Integer getInvPreQty() {
        return invPreQty;
    }

    public void setInvPreQty(Integer invPreQty) {
        this.invPreQty = invPreQty;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem == null ? null : orderItem.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAssNum() {
        return assNum;
    }

    public void setAssNum(Integer assNum) {
        this.assNum = assNum;
    }

    public String getDoSource() {
        return doSource;
    }

    public void setDoSource(String doSource) {
        this.doSource = doSource == null ? null : doSource.trim();
    }

    public String getDoType() {
        return doType;
    }

    public void setDoType(String doType) {
        this.doType = doType == null ? null : doType.trim();
    }

    public Integer getDoQty() {
        return doQty;
    }

    public void setDoQty(Integer doQty) {
        this.doQty = doQty;
    }

    public Integer getDoUseQty() {
        return doUseQty;
    }

    public void setDoUseQty(Integer doUseQty) {
        this.doUseQty = doUseQty;
    }

    public Date getHopeDate() {
        return hopeDate;
    }

    public void setHopeDate(Date hopeDate) {
        this.hopeDate = hopeDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer == null ? null : customer.trim();
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo == null ? null : deptNo.trim();
    }

    public String getGatherWarehouseCode() {
        return gatherWarehouseCode;
    }

    public void setGatherWarehouseCode(String gatherWarehouseCode) {
        this.gatherWarehouseCode = gatherWarehouseCode == null ? null : gatherWarehouseCode.trim();
    }

    public Date getWlDate() {
        return wlDate;
    }

    public void setWlDate(Date wlDate) {
        this.wlDate = wlDate;
    }

    public String getDlvEntire() {
        return dlvEntire;
    }

    public void setDlvEntire(String dlvEntire) {
        this.dlvEntire = dlvEntire == null ? null : dlvEntire.trim();
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
        InventoryRelationOrderView other = (InventoryRelationOrderView) that;
        return (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getWarehouseCode() == null ? other.getWarehouseCode() == null : this.getWarehouseCode().equals(other.getWarehouseCode()))
            && (this.getInventoryTypeCode() == null ? other.getInventoryTypeCode() == null : this.getInventoryTypeCode().equals(other.getInventoryTypeCode()))
            && (this.getModelno() == null ? other.getModelno() == null : this.getModelno().equals(other.getModelno()))
            && (this.getWarehouseType() == null ? other.getWarehouseType() == null : this.getWarehouseType().equals(other.getWarehouseType()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getGroupCustomerNo() == null ? other.getGroupCustomerNo() == null : this.getGroupCustomerNo().equals(other.getGroupCustomerNo()))
            && (this.getPpl() == null ? other.getPpl() == null : this.getPpl().equals(other.getPpl()))
            && (this.getProjectCode() == null ? other.getProjectCode() == null : this.getProjectCode().equals(other.getProjectCode()))
            && (this.getSalesInfoNo() == null ? other.getSalesInfoNo() == null : this.getSalesInfoNo().equals(other.getSalesInfoNo()))
            && (this.getDeptCode() == null ? other.getDeptCode() == null : this.getDeptCode().equals(other.getDeptCode()))
            && (this.getInventoryStatus() == null ? other.getInventoryStatus() == null : this.getInventoryStatus().equals(other.getInventoryStatus()))
            && (this.getInvQty() == null ? other.getInvQty() == null : this.getInvQty().equals(other.getInvQty()))
            && (this.getInvPreQty() == null ? other.getInvPreQty() == null : this.getInvPreQty().equals(other.getInvPreQty()))
            && (this.getDoId() == null ? other.getDoId() == null : this.getDoId().equals(other.getDoId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderItem() == null ? other.getOrderItem() == null : this.getOrderItem().equals(other.getOrderItem()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()))
            && (this.getAssNum() == null ? other.getAssNum() == null : this.getAssNum().equals(other.getAssNum()))
            && (this.getDoSource() == null ? other.getDoSource() == null : this.getDoSource().equals(other.getDoSource()))
            && (this.getDoType() == null ? other.getDoType() == null : this.getDoType().equals(other.getDoType()))
            && (this.getDoQty() == null ? other.getDoQty() == null : this.getDoQty().equals(other.getDoQty()))
            && (this.getDoUseQty() == null ? other.getDoUseQty() == null : this.getDoUseQty().equals(other.getDoUseQty()))
            && (this.getHopeDate() == null ? other.getHopeDate() == null : this.getHopeDate().equals(other.getHopeDate()))
            && (this.getCustomer() == null ? other.getCustomer() == null : this.getCustomer().equals(other.getCustomer()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
            && (this.getDeptNo() == null ? other.getDeptNo() == null : this.getDeptNo().equals(other.getDeptNo()))
            && (this.getGatherWarehouseCode() == null ? other.getGatherWarehouseCode() == null : this.getGatherWarehouseCode().equals(other.getGatherWarehouseCode()))
            && (this.getWlDate() == null ? other.getWlDate() == null : this.getWlDate().equals(other.getWlDate()))
            && (this.getDlvEntire() == null ? other.getDlvEntire() == null : this.getDlvEntire().equals(other.getDlvEntire()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getWarehouseCode() == null) ? 0 : getWarehouseCode().hashCode());
        result = prime * result + ((getInventoryTypeCode() == null) ? 0 : getInventoryTypeCode().hashCode());
        result = prime * result + ((getModelno() == null) ? 0 : getModelno().hashCode());
        result = prime * result + ((getWarehouseType() == null) ? 0 : getWarehouseType().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getGroupCustomerNo() == null) ? 0 : getGroupCustomerNo().hashCode());
        result = prime * result + ((getPpl() == null) ? 0 : getPpl().hashCode());
        result = prime * result + ((getProjectCode() == null) ? 0 : getProjectCode().hashCode());
        result = prime * result + ((getSalesInfoNo() == null) ? 0 : getSalesInfoNo().hashCode());
        result = prime * result + ((getDeptCode() == null) ? 0 : getDeptCode().hashCode());
        result = prime * result + ((getInventoryStatus() == null) ? 0 : getInventoryStatus().hashCode());
        result = prime * result + ((getInvQty() == null) ? 0 : getInvQty().hashCode());
        result = prime * result + ((getInvPreQty() == null) ? 0 : getInvPreQty().hashCode());
        result = prime * result + ((getDoId() == null) ? 0 : getDoId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderItem() == null) ? 0 : getOrderItem().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        result = prime * result + ((getAssNum() == null) ? 0 : getAssNum().hashCode());
        result = prime * result + ((getDoSource() == null) ? 0 : getDoSource().hashCode());
        result = prime * result + ((getDoType() == null) ? 0 : getDoType().hashCode());
        result = prime * result + ((getDoQty() == null) ? 0 : getDoQty().hashCode());
        result = prime * result + ((getDoUseQty() == null) ? 0 : getDoUseQty().hashCode());
        result = prime * result + ((getHopeDate() == null) ? 0 : getHopeDate().hashCode());
        result = prime * result + ((getCustomer() == null) ? 0 : getCustomer().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        result = prime * result + ((getDeptNo() == null) ? 0 : getDeptNo().hashCode());
        result = prime * result + ((getGatherWarehouseCode() == null) ? 0 : getGatherWarehouseCode().hashCode());
        result = prime * result + ((getWlDate() == null) ? 0 : getWlDate().hashCode());
        result = prime * result + ((getDlvEntire() == null) ? 0 : getDlvEntire().hashCode());
        return result;
    }
}