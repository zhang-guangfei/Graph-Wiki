package com.sales.ops.dto.flux;

import java.io.Serializable;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：4.7 销售/发运订单/加工单/盘点差异调账取消
 * @date ：Created in 2021/12/17 14:49
 */
public class CancelDocOrderDto implements Serializable {
    private static final long serialVersionUID = 8143407728155259695L;

    private String customerId;
    private Long wmId;
    private String warehouseId;
    private String docNo;
    private String orderType;
    private String erpCancelReason; //取消原因

    public CancelDocOrderDto(){};

    public CancelDocOrderDto(Long wmId,String warehouseId,String docNo,String orderType,String erpCancelReason){
        this.customerId = "SMC";
        this.wmId = wmId;
        this.warehouseId = warehouseId;
        this.docNo = docNo;
        this.orderType = orderType;
        this.erpCancelReason = erpCancelReason;
    };

    public Long getWmId() {
        return wmId;
    }

    public void setWmId(Long wmId) {
        this.wmId = wmId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getErpCancelReason() {
        return erpCancelReason;
    }

    public void setErpCancelReason(String erpCancelReason) {
        this.erpCancelReason = erpCancelReason;
    }

    @Override
    public String toString() {
        return "CancelDocOrderDto{" +
                "wmId=" + wmId +
                ", warehouseId='" + warehouseId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", docNo='" + docNo + '\'' +
                ", orderType='" + orderType + '\'' +
                '}';
    }
}
