package com.sales.ops.dto.ips;

/**
 * @description:
 * @author: B91717
 * @time: 2025/2/6 17:03
 */
public class IpsAssembleContentDto {
    private String orderNo; // 订单主单号
    private String itemNo; // 订单项号
    private String assembleType; // 组装标识，0：非组装；1：组装

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getAssembleType() {
        return assembleType;
    }

    public void setAssembleType(String assembleType) {
        this.assembleType = assembleType;
    }
}
