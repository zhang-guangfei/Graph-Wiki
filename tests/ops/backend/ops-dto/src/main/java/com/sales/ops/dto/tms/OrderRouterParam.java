package com.sales.ops.dto.tms;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderRouterParam implements Serializable {

    private String batchNo;
    // 承运商代码
    private String carrierId;
    // 承运商名称
    private String carrierName;
    // 运单母单号
    private String expressNo;
    // 路由节点操作码 1已揽件、2运输中、3派件中、4已签收、0未知状态（需要IT跟进），一个节点只推送一次
    private Integer stateCode;
    // 路由节点描述
    private String stateDesc;

    private String newExpressNo;
    private String newCarrierId;
    private String newCarrierName;

    private List<String> doId;
    // 路由节点产生的时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actionTime;
    // 路由轨迹明细清单
    private List<TmsTrackingMessage> message;
    // 预计送达日
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expectedDeliveryDate;


    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
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

    public String getNewExpressNo() {
        return newExpressNo;
    }

    public void setNewExpressNo(String newExpressNo) {
        this.newExpressNo = newExpressNo;
    }

    public String getNewCarrierId() {
        return newCarrierId;
    }

    public void setNewCarrierId(String newCarrierId) {
        this.newCarrierId = newCarrierId;
    }

    public String getNewCarrierName() {
        return newCarrierName;
    }

    public void setNewCarrierName(String newCarrierName) {
        this.newCarrierName = newCarrierName;
    }

    public List<String> getDoId() {
        return doId;
    }

    public void setDoId(List<String> doId) {
        this.doId = doId;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public List<TmsTrackingMessage> getMessage() {
        return message;
    }

    public void setMessage(List<TmsTrackingMessage> message) {
        this.message = message;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    @Override
    public String toString() {
        return "OrderRouterParam{" +
                "batchNo='" + batchNo + '\'' +
                ", carrierId='" + carrierId + '\'' +
                ", carrierName='" + carrierName + '\'' +
                ", expressNo='" + expressNo + '\'' +
                ", stateCode=" + stateCode +
                ", stateDesc='" + stateDesc + '\'' +
                ", newExpressNo='" + newExpressNo + '\'' +
                ", newCarrierId='" + newCarrierId + '\'' +
                ", newCarrierName='" + newCarrierName + '\'' +
                ", doId=" + doId +
                ", actionTime=" + actionTime +
                ", message=" + message +
                ", expectedDeliveryDate=" + expectedDeliveryDate +
                '}';
    }
}
