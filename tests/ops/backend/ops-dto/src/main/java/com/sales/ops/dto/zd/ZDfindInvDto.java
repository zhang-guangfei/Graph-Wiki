package com.sales.ops.dto.zd;

import com.sales.ops.db.entity.OpsInventory;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

/**
 * 转定在库 展示该订单可用在库
 */
public class ZDfindInvDto implements Serializable {

    private static final long serialVersionUID = 1941268679589849790L;

    private String orderId;
    private Integer orderItem;
    private Integer qty;

    private String modelNo;

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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }
}
