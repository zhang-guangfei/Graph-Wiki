package com.sales.ops.dto.order;

import com.sales.ops.dto.purchase.PurchaseInfoForCancel;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：订单还原前端调用实体类
 * @date ：Created in 2023/3/21 8:47
 */
public class OrderChangeToInitStatusDto implements Serializable {

    private static final long serialVersionUID = 4418939295882505753L;

    //客户订单号
    private String orderId;
    //客户项号
    private String orderItem;
    //是否删除采购
    private boolean delPo;
    //登录名
    private String userName;
    //采购取消调用接口
    private List<PurchaseInfoForCancel> po;

    public OrderChangeToInitStatusDto(){}

    public OrderChangeToInitStatusDto(AutoOrderChangeToInitStatusDto in){
        this.orderId = in.getOrderId();
        this.orderItem = in.getOrderItem();
        this.userName = in.getUserName();
        this.delPo = false;
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

    public boolean isDelPo() {
        return delPo;
    }

    public void setDelPo(boolean delPo) {
        this.delPo = delPo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<PurchaseInfoForCancel> getPo() {
        return po;
    }

    public void setPo(List<PurchaseInfoForCancel> po) {
        this.po = po;
    }
}
