package com.sales.ops.dto.order;

import com.sales.ops.dto.util.UserDto;

/**
 * @author C02483
 * @version 1.0
 * @description: 取消订单
 * @date 2021/10/25 11:44
 */
public class CancelForOrderDto {
    // 订单完整单号
    private String orderFno;
    // 订单主单号
    private String orderId;
    // 订单项号
    private String orderItem;
    // 单据类型
    private String orderType;
    // 取消原因 必填
    private String reason;
    // 删单责任人
    private String duty;

    // 用户信息 必填
    private UserDto userDto;
    // 来源 必填 自动删单、手动处理、接单查询
    private String origin;

    private boolean secondProcess;

    private Boolean isAdjust;

    //bugid:14473 c14717 20240626
    private String endUser;

    public CancelForOrderDto(){}

    public CancelForOrderDto(String orderId,String orderItem,String orderType,UserDto userDto,String msg){
        this.orderId = orderId;
        this.orderItem = orderItem;
        this.orderType = orderType;
        this.userDto = userDto;
        this.reason = msg  ;
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

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getOrderFno() {
        return orderFno;
    }

    public void setOrderFno(String orderFno) {
        this.orderFno = orderFno;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public boolean isSecondProcess() {
        return secondProcess;
    }

    public void setSecondProcess(boolean secondProcess) {
        this.secondProcess = secondProcess;
    }

    public Boolean getAdjust() {
        return isAdjust;
    }

    public void setAdjust(Boolean adjust) {
        isAdjust = adjust;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }
}
