package com.sales.ops.dto.order;

import com.sales.ops.dto.purchase.PurchaseInfoForCancel;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：自动订单还原实体类
 * @date ：Created in 2024/6/26 8:47
 */
public class AutoOrderChangeToInitStatusDto implements Serializable {

    private static final long serialVersionUID = 4418939295882505753L;

    //客户订单号
    private String orderId;
    //客户项号
    private String orderItem;
    //登录名
    private String userName;

    // 是否制定调库计划
    private boolean transfer = false;
    // 调入顾客专备 顾客号
    private String endUser;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }
}
