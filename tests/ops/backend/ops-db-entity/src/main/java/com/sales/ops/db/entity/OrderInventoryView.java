package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderInventoryView implements Serializable {
    private String orderId;

    private String orderItem;

    private String doId;

    private Integer doItem;

    private Long inventoryId;

    private String gatherWarehouseCode;

    private String modelno;

    private Integer useQty;

    private Integer qty;

    private String doState;

    private String doStateDetail;

    private Integer haveQty;

    private Integer outQty;

    private String waitType;

    private String inventoryTypeCode;

    private String warehouseCode;

    private String supplierid;

    private String associateNo;

    private Date modifyTime;

    private static final long serialVersionUID = 1L;

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

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId == null ? null : doId.trim();
    }

    public Integer getDoItem() {
        return doItem;
    }

    public void setDoItem(Integer doItem) {
        this.doItem = doItem;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getGatherWarehouseCode() {
        return gatherWarehouseCode;
    }

    public void setGatherWarehouseCode(String gatherWarehouseCode) {
        this.gatherWarehouseCode = gatherWarehouseCode == null ? null : gatherWarehouseCode.trim();
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getUseQty() {
        return useQty;
    }

    public void setUseQty(Integer useQty) {
        this.useQty = useQty;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getDoState() {
        return doState;
    }

    public void setDoState(String doState) {
        this.doState = doState == null ? null : doState.trim();
    }

    public String getDoStateDetail() {
        return doStateDetail;
    }

    public void setDoStateDetail(String doStateDetail) {
        this.doStateDetail = doStateDetail == null ? null : doStateDetail.trim();
    }

    public Integer getHaveQty() {
        return haveQty;
    }

    public void setHaveQty(Integer haveQty) {
        this.haveQty = haveQty;
    }

    public Integer getOutQty() {
        return outQty;
    }

    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }

    public String getWaitType() {
        return waitType;
    }

    public void setWaitType(String waitType) {
        this.waitType = waitType == null ? null : waitType.trim();
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

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getAssociateNo() {
        return associateNo;
    }

    public void setAssociateNo(String associateNo) {
        this.associateNo = associateNo == null ? null : associateNo.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}