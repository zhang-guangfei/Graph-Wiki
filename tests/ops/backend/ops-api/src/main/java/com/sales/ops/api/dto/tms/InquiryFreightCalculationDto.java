package com.sales.ops.api.dto.tms;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description： 7.2 询价运费测算
 * @date ：Created in 2021/12/27 16:16
 */
public class InquiryFreightCalculationDto implements Serializable {

    private static final long serialVersionUID = 7111696177836570537L;

    private String warehouseId;    //仓库ID
    private String carrierId;    //承运商
    private String grossWeightOrdered;    //预计重量
    private String erpNo;    //ERP订单号
    private String consigneeAddress;    //地址
    private String requestBy;    //请求人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date requestTime;    //请求时间

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getGrossWeightOrdered() {
        return grossWeightOrdered;
    }

    public void setGrossWeightOrdered(String grossWeightOrdered) {
        this.grossWeightOrdered = grossWeightOrdered;
    }

    public String getErpNo() {
        return erpNo;
    }

    public void setErpNo(String erpNo) {
        this.erpNo = erpNo;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(String requestBy) {
        this.requestBy = requestBy;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
}
