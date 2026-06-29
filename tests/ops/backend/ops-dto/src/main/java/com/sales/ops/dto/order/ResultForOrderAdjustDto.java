package com.sales.ops.dto.order;

public class ResultForOrderAdjustDto {
    private String adjustType; // 转订类型，【1.采购转在库，2.在库转采购】
    private String orderId;
    private String orderItem;
    private String modelno;//不允许为空
    private int qty;//不允许为0
    private Long oldMoveId;//允许为空
    private Long newInvId;// 不允许为空
    private String tableType;
    private Boolean riskFlag =false;

    public static final String PURCHASE_TO_NORMAL = "1";
    public static final String NORMAL_TO_PURCHASE = "2";
    public static final String EX_TO_NORMAL = "3";
    public static final String EX_TO_PURCHASE = "4";
    public static final String TRANSIT_TO_PURCHASE = "5";
    public static final String PURCHASE_TO_TRANSIT = "6";
    public static final String EX_TO_TRANSIT = "7";
    public static final String TRANSIT_TO_TRANSIT = "8";
    public static final String TRANSIT_TO_NORMAL = "9";
    public static final String NORMAL_TO_TRANSIT = "10";

    public ResultForOrderAdjustDto() {
    }


    /**
     * 采购转在库
     *
     * @param qty       要转订的数量
     * @param oldMoveId 要转订的原始moveId
     * @param newInvId  转成新的库存id
     * @param tableType 新库存id的表名
     * @return
     */
    public static ResultForOrderAdjustDto purchaseToNormal(String orderId, String orderItem, String modelno, int qty, Long oldMoveId, Long newInvId, String tableType) {
        ResultForOrderAdjustDto resultDTO = new ResultForOrderAdjustDto();
        resultDTO.setAdjustType(PURCHASE_TO_NORMAL);
        resultDTO.setOrderId(orderId);
        resultDTO.setOrderItem(orderItem);
        resultDTO.setModelno(modelno);
        resultDTO.setQty(qty);
        resultDTO.setOldMoveId(oldMoveId);
        resultDTO.setNewInvId(newInvId);
        resultDTO.setTableType(tableType);
        return resultDTO;
    }
    public static ResultForOrderAdjustDto exceptionToNormal(String orderId, String orderItem, String modelno, int qty, Long oldMoveId, Long newInvId, String tableType) {
        ResultForOrderAdjustDto resultDTO = new ResultForOrderAdjustDto();
        resultDTO.setAdjustType(EX_TO_NORMAL);
        resultDTO.setOrderId(orderId);
        resultDTO.setOrderItem(orderItem);
        resultDTO.setModelno(modelno);
        resultDTO.setQty(qty);
        resultDTO.setOldMoveId(oldMoveId);
        resultDTO.setNewInvId(newInvId);
        resultDTO.setTableType(tableType);
        return resultDTO;
    }

    /**
     * @param orderId
     * @param orderItem
     * @param modelno
     * @param qty
     * @return 其他不填
     */
    public static ResultForOrderAdjustDto normalToPurchase(String orderId, String orderItem, String modelno, int qty) {
        ResultForOrderAdjustDto resultDTO = new ResultForOrderAdjustDto();
        resultDTO.setAdjustType(NORMAL_TO_PURCHASE);
        resultDTO.setOrderId(orderId);
        resultDTO.setOrderItem(orderItem);
        resultDTO.setModelno(modelno);
        resultDTO.setQty(qty);
        return resultDTO;
    }

    public static ResultForOrderAdjustDto exceptionToPurchase(String orderId, String orderItem, String modelno, int qty) {
        ResultForOrderAdjustDto resultDTO = new ResultForOrderAdjustDto();
        resultDTO.setAdjustType(EX_TO_PURCHASE);
        resultDTO.setOrderId(orderId);
        resultDTO.setOrderItem(orderItem);
        resultDTO.setModelno(modelno);
        resultDTO.setQty(qty);
        return resultDTO;
    }

    public static ResultForOrderAdjustDto transitToPurchase(String orderId, String orderItem, String modelno, int qty, Long oldMoveId) {
        ResultForOrderAdjustDto resultDTO = new ResultForOrderAdjustDto();
        resultDTO.setAdjustType(TRANSIT_TO_PURCHASE);
        resultDTO.setOrderId(orderId);
        resultDTO.setOrderItem(orderItem);
        resultDTO.setModelno(modelno);
        resultDTO.setQty(qty);
        resultDTO.setOldMoveId(oldMoveId);
        return resultDTO;
    }

    public static ResultForOrderAdjustDto purchaseToTransit(String orderId, String orderItem, String modelno, int qty, Long newInvId, String tableType) {
        ResultForOrderAdjustDto resultDTO = new ResultForOrderAdjustDto();
        resultDTO.setAdjustType(PURCHASE_TO_TRANSIT);
        resultDTO.setOrderId(orderId);
        resultDTO.setOrderItem(orderItem);
        resultDTO.setModelno(modelno);
        resultDTO.setQty(qty);
        resultDTO.setNewInvId(newInvId);
        resultDTO.setTableType(tableType);
        return resultDTO;
    }

    public static ResultForOrderAdjustDto exceptionToTransit(String orderId, String orderItem, String modelno, int qty, Long newInvId, String tableType) {
        ResultForOrderAdjustDto resultDTO = new ResultForOrderAdjustDto();
        resultDTO.setAdjustType(EX_TO_TRANSIT);
        resultDTO.setOrderId(orderId);
        resultDTO.setOrderItem(orderItem);
        resultDTO.setModelno(modelno);
        resultDTO.setQty(qty);
        resultDTO.setNewInvId(newInvId);
        resultDTO.setTableType(tableType);
        return resultDTO;
    }

    public static ResultForOrderAdjustDto transitToTransit(String orderId, String orderItem, String modelno, int qty, Long oldMoveId, Long newInvId, String tableType) {
        ResultForOrderAdjustDto resultDTO = new ResultForOrderAdjustDto();
        resultDTO.setAdjustType(TRANSIT_TO_TRANSIT);
        resultDTO.setOrderId(orderId);
        resultDTO.setOrderItem(orderItem);
        resultDTO.setModelno(modelno);
        resultDTO.setQty(qty);
        resultDTO.setOldMoveId(oldMoveId);
        resultDTO.setNewInvId(newInvId);
        resultDTO.setTableType(tableType);
        return resultDTO;
    }

    public static ResultForOrderAdjustDto transitToNormal(String orderId, String orderItem, String modelno, int qty, Long oldMoveId, Long newInvId, String tableType) {
        ResultForOrderAdjustDto resultDTO = new ResultForOrderAdjustDto();
        resultDTO.setAdjustType(TRANSIT_TO_NORMAL);
        resultDTO.setOrderId(orderId);
        resultDTO.setOrderItem(orderItem);
        resultDTO.setModelno(modelno);
        resultDTO.setQty(qty);
        resultDTO.setOldMoveId(oldMoveId);
        resultDTO.setNewInvId(newInvId);
        resultDTO.setTableType(tableType);
        return resultDTO;
    }

    public String getAdjustType() {
        return adjustType;
    }

    public void setAdjustType(String adjustType) {
        this.adjustType = adjustType;
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

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Long getOldMoveId() {
        return oldMoveId;
    }

    public void setOldMoveId(Long oldMoveId) {
        this.oldMoveId = oldMoveId;
    }

    public Long getNewInvId() {
        return newInvId;
    }

    public void setNewInvId(Long newInvId) {
        this.newInvId = newInvId;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public Boolean getRiskFlag() {
        return riskFlag;
    }

    public void setRiskFlag(Boolean riskFlag) {
        this.riskFlag = riskFlag;
    }
}
