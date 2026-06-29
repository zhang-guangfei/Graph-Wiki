package com.sales.ops.dto.delivery;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/8/27 14:03
 */
public class InqAExpPoRequestDto implements Serializable {

    private static final long serialVersionUID = 6541220302444499651L;
    // 输入客户货期
    private Date expDate;
    // 订单号
    private String orderNo;
    // 订单项号
    private Integer orderItem;

    public InqAExpPoRequestDto(){}

    public InqAExpPoRequestDto(Date expDatem, String orderNo, Integer orderItem){
       this.expDate = expDatem;
       this.orderNo = orderNo;
       this.orderItem = orderItem;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }
}
