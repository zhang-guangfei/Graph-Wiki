package com.sales.ops.dto.tms;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TmsTrackingResult implements Serializable {

    // 承运商名称
    private String carrierName;
    // 承运商代码
    private String carrierId;
    // 运单母单号
    private String expressNo;
    // 路由节点操作码 1已揽收,2运输中,3派件中,4已签收
    private Integer stateCode;
    // 路由节点操作码
    private String stateDesc;
    // 新加的
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actionTime;
    // 路由节点具体描述
    private List<TmsTrackingResultItem> routeList;
    // 预计送达日
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expectedDeliveryDate;


    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public List<TmsTrackingResultItem> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<TmsTrackingResultItem> routeList) {
        this.routeList = routeList;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }
}
