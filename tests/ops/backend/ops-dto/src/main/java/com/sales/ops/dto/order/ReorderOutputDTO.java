package com.sales.ops.dto.order;

import com.sales.ops.db.entity.OpsInventoryMove;

/**
 * @author C12961
 * @date 2022/5/6 13:39
 */
public class ReorderOutputDTO {

    private Long statusId;
    private String targetType;
    private String orderId;
    private String orderItem;
    private Integer splitNo;
    private String toWarehouseCode;
    private String inventoryTableType;
    private String inventoryPropertyType;
    private OpsInventoryMove opsInventoryMove;

    private String associateNo;
    private Integer associateNoItem;
    private Integer associateSplitNo;

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public Integer getSplitNo() {
        return splitNo;
    }

    public void setSplitNo(Integer splitNo) {
        this.splitNo = splitNo;
    }

    public String getToWarehouseCode() {
        return toWarehouseCode;
    }

    public void setToWarehouseCode(String toWarehouseCode) {
        this.toWarehouseCode = toWarehouseCode;
    }

    public String getInventoryTableType() {
        return inventoryTableType;
    }

    public void setInventoryTableType(String inventoryTableType) {
        this.inventoryTableType = inventoryTableType;
    }

    public String getInventoryPropertyType() {
        return inventoryPropertyType;
    }

    public void setInventoryPropertyType(String inventoryPropertyType) {
        this.inventoryPropertyType = inventoryPropertyType;
    }

    public OpsInventoryMove getOpsInventoryMove() {
        return opsInventoryMove;
    }

    public void setOpsInventoryMove(OpsInventoryMove opsInventoryMove) {
        this.opsInventoryMove = opsInventoryMove;
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

    public Integer getAssociateSplitNo() {
        return associateSplitNo;
    }

    public void setAssociateSplitNo(Integer associateSplitNo) {
        this.associateSplitNo = associateSplitNo;
    }
}
