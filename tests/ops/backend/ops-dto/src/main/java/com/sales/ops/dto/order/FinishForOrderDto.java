package com.sales.ops.dto.order;

import com.sales.ops.dto.util.UserDto;

import java.io.Serializable;

/**
 * @author C02483
 * @version 1.0
 * @description: TODO
 * @date 2021/11/9 14:08
 */
public class FinishForOrderDto  implements Serializable {
    private static final long serialVersionUID = -6972950547505416122L;
    /* 客户单号 */
    private String orderNo;
    /* 客户行号 */
    private String orderItem;

    private String orderFulNo;

    private String modelNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderFulNo() {
        return orderFulNo;
    }

    public void setOrderFulNo(String orderFulNo) {
        this.orderFulNo = orderFulNo;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

}
