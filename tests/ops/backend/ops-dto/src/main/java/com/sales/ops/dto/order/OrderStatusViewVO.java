package com.sales.ops.dto.order;

import java.util.Date;

public class OrderStatusViewVO {

    private String planNo;
    private Integer planQty; //通知发货数量
    private Integer matchQty;//匹配数量
    private Integer outQty ;//发货数量
    private Date hopeDate;//客户货期

    private String orderId;

    private Integer orderItem;

    private Integer splitNo;

    private Integer pcoItem;

    private String splitType;

    private String modelno;

    private Integer qtyDo;

    private Integer qty;

    private Integer qtyIn;

    private Integer qtyOut;

    private String associateNo;

    private String status;

    private String statusDesc;

    private String statusInfo;

    private String wmOrderId;

    private Long inventoryId;

    private String inventoryTable;

    private Date wlDate;

    private String warehouseCode;

    private Date estimatedDeliveryDay;

    private Integer reliability;

    private Date modifyTime;

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

    public Integer getMatchQty() {
        return matchQty;
    }

    public void setMatchQty(Integer matchQty) {
        this.matchQty = matchQty;
    }

    public Integer getOutQty() {
        return outQty;
    }

    public void setOutQty(Integer outQty) {
        this.outQty = outQty;
    }

    public Date getHopeDate() {
        return hopeDate;
    }

    public void setHopeDate(Date hopeDate) {
        this.hopeDate = hopeDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public Integer getSplitNo() {
        return splitNo;
    }

    public void setSplitNo(Integer splitNo) {
        this.splitNo = splitNo;
    }

    public Integer getPcoItem() {
        return pcoItem;
    }

    public void setPcoItem(Integer pcoItem) {
        this.pcoItem = pcoItem;
    }

    public String getSplitType() {
        return splitType;
    }

    public void setSplitType(String splitType) {
        this.splitType = splitType;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Integer getQtyDo() {
        return qtyDo;
    }

    public void setQtyDo(Integer qtyDo) {
        this.qtyDo = qtyDo;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getQtyIn() {
        return qtyIn;
    }

    public void setQtyIn(Integer qtyIn) {
        this.qtyIn = qtyIn;
    }

    public Integer getQtyOut() {
        return qtyOut;
    }

    public void setQtyOut(Integer qtyOut) {
        this.qtyOut = qtyOut;
    }

    public String getAssociateNo() {
        return associateNo;
    }

    public void setAssociateNo(String associateNo) {
        this.associateNo = associateNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public String getWmOrderId() {
        return wmOrderId;
    }

    public void setWmOrderId(String wmOrderId) {
        this.wmOrderId = wmOrderId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryTable() {
        return inventoryTable;
    }

    public void setInventoryTable(String inventoryTable) {
        this.inventoryTable = inventoryTable;
    }

    public Date getWlDate() {
        return wlDate;
    }

    public void setWlDate(Date wlDate) {
        this.wlDate = wlDate;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public Date getEstimatedDeliveryDay() {
        return estimatedDeliveryDay;
    }

    public void setEstimatedDeliveryDay(Date estimatedDeliveryDay) {
        this.estimatedDeliveryDay = estimatedDeliveryDay;
    }

    public Integer getReliability() {
        return reliability;
    }

    public void setReliability(Integer reliability) {
        this.reliability = reliability;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
