package com.sales.ops.dto.query;

import java.util.Date;

/**
 * @description 出库指令的查询条件
 * @date 2021/9/23 11:53
 * @auther C12961
 */
public class OpsDeliveryOrderQO {

    //客户号
    private String customerNo;
    //仓库代码
    private String warehouseCode;
    //出库指令号
    private String doId;
    //客户单号
    private String orderId;

    //出库类型
    private String doType;
    //出库状态
    private Integer doStatus;
    //开始时间
    private Date beginTime;
    //结束时间
    private Date endTime;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getDoId() {
        return doId;
    }

    public void setDoId(String doId) {
        this.doId = doId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDoType() {
        return doType;
    }

    public void setDoType(String doType) {
        this.doType = doType;
    }

    public Integer getDoStatus() {
        return doStatus;
    }

    public void setDoStatus(Integer doStatus) {
        this.doStatus = doStatus;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
