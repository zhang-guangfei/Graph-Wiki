package com.sales.ops.db.entity;

import java.io.Serializable;
import java.util.Date;

public class OpsOrderDispatchView implements Serializable {
    private String doId;

    private String orderId;

    private String orderItem;

    private String modelno;

    private Integer num;

    private String warehouseCode;

    private String gatherWarehouseCode;

    private String waitTypeDesc;

    private String waitType;

    private String doState;

    private String doStateDetail;

    private Integer qty;

    private Integer outQty;

    private Date finishTime;

    private static final long serialVersionUID = 1L;

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

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno == null ? null : modelno.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getGatherWarehouseCode() {
        return gatherWarehouseCode;
    }

    public void setGatherWarehouseCode(String gatherWarehouseCode) {
        this.gatherWarehouseCode = gatherWarehouseCode == null ? null : gatherWarehouseCode.trim();
    }

    public String getWaitTypeDesc() {
        return waitTypeDesc;
    }

    public void setWaitTypeDesc(String waitTypeDesc) {
        this.waitTypeDesc = waitTypeDesc == null ? null : waitTypeDesc.trim();
    }

    public String getWaitType() {
        return waitType;
    }

    public void setWaitType(String waitType) {
        this.waitType = waitType == null ? null : waitType.trim();
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getOutQty() {
        return outQty;
    }

    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}