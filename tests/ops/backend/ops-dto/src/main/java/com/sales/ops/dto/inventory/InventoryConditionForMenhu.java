package com.sales.ops.dto.inventory;

import java.io.Serializable;
import java.util.List;

public class InventoryConditionForMenhu implements Serializable {

    //库存所属 N:在库 M:在途 S:供应商
    private String inventoryScope;
    private String warehouseCode;
    //private String modelNo;
    private List<String> modelNoList;
    private String deptNo;//部门代码
    private String inventoryType;//库存类别
    private String customerNo;

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

    public List<String> getModelNoList() {
        return modelNoList;
    }

    public void setModelNoList(List<String> modelNoList) {
        this.modelNoList = modelNoList;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
}

