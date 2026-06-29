package com.sales.ops.db.entity;

import java.io.Serializable;

public class InventoryPropertyView implements Serializable {
    private Long inventoryId;

    private String inventoryTableType;

    private Long inventoryPropertyId;

    private String inventoryTypeCode;

    private String warehouseCode;

    private static final long serialVersionUID = 1L;

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryTableType() {
        return inventoryTableType;
    }

    public void setInventoryTableType(String inventoryTableType) {
        this.inventoryTableType = inventoryTableType == null ? null : inventoryTableType.trim();
    }

    public Long getInventoryPropertyId() {
        return inventoryPropertyId;
    }

    public void setInventoryPropertyId(Long inventoryPropertyId) {
        this.inventoryPropertyId = inventoryPropertyId;
    }

    public String getInventoryTypeCode() {
        return inventoryTypeCode;
    }

    public void setInventoryTypeCode(String inventoryTypeCode) {
        this.inventoryTypeCode = inventoryTypeCode == null ? null : inventoryTypeCode.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }
}