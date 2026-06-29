package com.sales.ops.dto.order;

//todo 13为单号
public class OrderAdjustDto {

    private String orderId;
    private String orderItem;
    private Integer splitNo;
    //doId\pcoId

    private String relationId;

    public OrderAdjustDto(String orderId, String orderItem, Integer splitNo, String relationId) {
        this.orderId = orderId;
        this.orderItem = orderItem;
        this.splitNo = splitNo;
        this.relationId = relationId;
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

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }
}
