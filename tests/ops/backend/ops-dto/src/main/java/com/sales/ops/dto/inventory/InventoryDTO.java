package com.sales.ops.dto.inventory;

import com.sales.ops.db.entity.OpsInventory;
import com.sales.ops.db.entity.OpsInventoryMove;
import com.sales.ops.enums.InventoryTableTypeEnum;

/**
 * @description 库存统一格式类（在库、在途）
 * @date 2021/10/11 9:13
 * @auther C12961
 */
public class InventoryDTO {

    //库存ID
    private Long inventoryId;
    //库存状态（在库{N,P,W,X}、在途T）
    private String inventoryStatus;

    private String inventoryTableType;
    //型号
    private String modelno;
    //物理仓库代码
    private String warehouseCode;
    //批属性ID
    private Long inventoryPropertyId;
    //数量
    private Integer quantity;
    //占用数量
    private Integer prepareQuantity;

    public InventoryDTO(OpsInventory inv) {
        this.inventoryTableType= InventoryTableTypeEnum.NORMAL.getType();
        this.inventoryId=inv.getInventoryId();
        this.modelno = inv.getModelno();
        this.warehouseCode = inv.getWarehouseCode();
        this.inventoryPropertyId = inv.getInventoryPropertyId();
        this.inventoryStatus = inv.getInventoryStatus();
        this.quantity = inv.getQuantity();
        this.prepareQuantity = inv.getPrepareQuantity();
    }

    public InventoryDTO(OpsInventoryMove inv) {
        this.inventoryTableType= InventoryTableTypeEnum.TRANS.getType();
        this.inventoryId=inv.getInventoryId();
        this.modelno = inv.getModelno();
        this.warehouseCode = inv.getWarehouseCode();
        this.inventoryPropertyId = inv.getInventoryPropertyId();
        this.inventoryStatus = inv.getInventoryStatus();
        this.quantity = inv.getQuantity();
        this.prepareQuantity = inv.getPrepareQuantity();
    }

    public InventoryDTO() {

    }

    public Integer getAvailableQuantity() {
        return quantity - prepareQuantity;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getInventoryTableType() {
        return inventoryTableType;
    }

    public void setInventoryTableType(String inventoryTableType) {
        this.inventoryTableType = inventoryTableType;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public Long getInventoryPropertyId() {
        return inventoryPropertyId;
    }

    public void setInventoryPropertyId(Long inventoryPropertyId) {
        this.inventoryPropertyId = inventoryPropertyId;
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

}
