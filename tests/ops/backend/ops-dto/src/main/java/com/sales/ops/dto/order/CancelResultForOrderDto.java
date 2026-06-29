package com.sales.ops.dto.order;

/**
 * @author C02483
 * @version 1.0
 * @description: 取消返回结果集
 * @date 2021/11/9 13:09
 */
public class CancelResultForOrderDto {

    /* 客户单号 */
    private String orderId;
    /* 客户行号 */
    private String orderItem;
    /* 单据类型 */
    private String orderType;
    /* 取消原因 */
    private String reason;
    /* 指令类型 */
    private String doType;
    /* 指令id */
    private String doId;
    /* 取消结果 */
    private String result;


    public CancelResultForOrderDto(CancelForOrderDto cancelDto, String doType, String doId, String result) {
        this.orderId = cancelDto.getOrderId();
        this.orderItem = cancelDto.getOrderItem();
        this.orderType = cancelDto.getOrderType();
        this.reason = cancelDto.getReason();
        this.doType = doType;
        this.doId = doId;
        this.result = result;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDoType() {
        return doType;
    }

    public void setDoType(String doType) {
        this.doType = doType;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

