package com.sales.ops.dto.order;

import com.sales.ops.db.entity.Rcvdetail;
import com.sales.ops.db.entity.Rcvmaster;
import com.sales.ops.dto.util.UserDto;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;

@AllArgsConstructor
public class CustomerOrderCancelForDeliveryDateDTO {
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
    //bug19719  推送交货期的字段 C12961 2026-01-08
    private String modelno;
    private Integer quantity;
    private String customerNo;
    private String userNo;
    private String employeeno;
    private String ororderno;
    private String deptNo;

    public CustomerOrderCancelForDeliveryDateDTO() {
    }

    public CustomerOrderCancelForDeliveryDateDTO(CancelForOrderDto dto, Rcvmaster rcv, Rcvdetail rcvdetail) {
        this.orderFno = dto.getOrderFno();
        this.orderId = dto.getOrderId();
        this.orderItem = dto.getOrderItem();
        this.orderType = dto.getOrderType();
        this.reason = dto.getReason();
        this.duty = dto.getDuty();
        this.userDto = dto.getUserDto();
        this.origin = dto.getOrigin();
        this.secondProcess = dto.isSecondProcess();
        this.endUser = dto.getEndUser();
        this.customerNo = rcv.getCustomerNo();
        this.userNo = rcv.getUserNo();
        this.employeeno = rcv.getEmployeeno();
        this.ororderno = rcv.getOrorderno();
        this.modelno = rcvdetail.getModelNo();
        this.quantity = rcvdetail.getQuantity();
        this.deptNo = rcv.getDeliveryDeptNo();
        if (StringUtils.isBlank(this.deptNo)) {
            this.deptNo = rcv.getDeptNo();
        }
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

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getEmployeeno() {
        return employeeno;
    }

    public void setEmployeeno(String employeeno) {
        this.employeeno = employeeno;
    }

    public String getOrorderno() {
        return ororderno;
    }

    public void setOrorderno(String ororderno) {
        this.ororderno = ororderno;
    }

    public String getModelno() {
        return modelno;
    }

    public void setModelno(String modelno) {
        this.modelno = modelno;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }
}