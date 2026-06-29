package com.sales.ops.dto.order;

import java.io.Serializable;

/**
 * @author C12961
 * @date 2022/11/27 15:12
 */
public class TransOrderQueryMoveResult implements Serializable {


    private Long inventoryId;
    private String warehouseCode;
    private String inventoryStatus;
    private String modelno;
    private int quantity;
    private int prepareQty;
    private int availableQty;
    private String associateNo;
    private Integer associateNoItem;
    private Integer associateNoSplit;
    private Long inventoryPropertyId;


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
        this.warehouseCode = warehouseCode;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrepareQty() {
        return prepareQty;
    }

    public void setPrepareQty(int prepareQty) {
        this.prepareQty = prepareQty;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    public String getAssociateNo() {
        return associateNo;
    }

    public void setAssociateNo(String associateNo) {
        this.associateNo = associateNo;
    }

    public Integer getAssociateNoItem() {
        return associateNoItem;
    }

    public void setAssociateNoItem(Integer associateNoItem) {
        this.associateNoItem = associateNoItem;
    }

    public Integer getAssociateNoSplit() {
        return associateNoSplit;
    }

    public void setAssociateNoSplit(Integer associateNoSplit) {
        this.associateNoSplit = associateNoSplit;
    }

    public Long getInventoryPropertyId() {
        return inventoryPropertyId;
    }

    public void setInventoryPropertyId(Long inventoryPropertyId) {
        this.inventoryPropertyId = inventoryPropertyId;
    }
}
