package com.sales.ops.dto.inventory;

import java.util.Objects;

public class InventoryDtoForMenhu {
    private String inventoryScope;
    private String warehouseCode;
    private String warehouseName;
    private String modelNo;
    private String deptNo;
    private Integer quantity;
    private Integer prepareQuantity;
    private Integer availableQuantity;

    private String inventoryStatus;
    private String inventoryTypeCode;
    private String inventoryTypeName;
    private String customerNo;
    private String ppl;
    private String projectCode;
    private String groupCustomerNo;
    private String salesInfoNo;


    public String getInventoryScope() {
        return inventoryScope;
    }

    public void setInventoryScope(String inventoryScope) {
        this.inventoryScope = inventoryScope;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrepareQuantity() {
        return prepareQuantity;
    }

    public void setPrepareQuantity(Integer prepareQuantity) {
        this.prepareQuantity = prepareQuantity;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode;
    }

    public String getInventoryTypeName() {
        return inventoryTypeName;
    }

    public void setInventoryTypeName(String inventoryTypeName) {
        this.inventoryTypeName = inventoryTypeName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getPpl() {
        return ppl;
    }

    public void setPpl(String ppl) {
        this.ppl = ppl;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getGroupCustomerNo() {
        return groupCustomerNo;
    }

    public void setGroupCustomerNo(String groupCustomerNo) {
        this.groupCustomerNo = groupCustomerNo;
    }

    public String getSalesInfoNo() {
        return salesInfoNo;
    }

    public void setSalesInfoNo(String salesInfoNo) {
        this.salesInfoNo = salesInfoNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryDtoForMenhu that = (InventoryDtoForMenhu) o;
        return Objects.equals(inventoryScope, that.inventoryScope) && Objects.equals(warehouseCode, that.warehouseCode) && Objects.equals(warehouseName, that.warehouseName) && Objects.equals(modelNo, that.modelNo) && Objects.equals(deptNo, that.deptNo) && Objects.equals(quantity, that.quantity) && Objects.equals(prepareQuantity, that.prepareQuantity) && Objects.equals(availableQuantity, that.availableQuantity) && Objects.equals(inventoryStatus, that.inventoryStatus) && Objects.equals(inventoryTypeCode, that.inventoryTypeCode) && Objects.equals(inventoryTypeName, that.inventoryTypeName) && Objects.equals(customerNo, that.customerNo) && Objects.equals(ppl, that.ppl) && Objects.equals(projectCode, that.projectCode) && Objects.equals(groupCustomerNo, that.groupCustomerNo) && Objects.equals(salesInfoNo, that.salesInfoNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryScope, warehouseCode, warehouseName, modelNo, deptNo, quantity, prepareQuantity, availableQuantity, inventoryStatus, inventoryTypeCode, inventoryTypeName, customerNo, ppl, projectCode, groupCustomerNo, salesInfoNo);
    }
}

