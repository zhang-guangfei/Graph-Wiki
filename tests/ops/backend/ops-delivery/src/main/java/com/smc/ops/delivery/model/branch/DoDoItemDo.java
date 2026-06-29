package com.smc.ops.delivery.model.branch;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/1/3 15:25
 */
public class DoDoItemDo implements Serializable {
    private static final long serialVersionUID = 5647623969658859189L;

    private String orderId;

    private Integer orderItem;

    private String doId;

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

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }
}
