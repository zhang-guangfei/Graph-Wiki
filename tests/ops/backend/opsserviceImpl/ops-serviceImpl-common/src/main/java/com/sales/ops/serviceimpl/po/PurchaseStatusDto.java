package com.sales.ops.serviceimpl.po;

import cn.hutool.json.JSONUtil;

import java.util.Map;

public class PurchaseStatusDto {

    private String orderNo;
    private Integer itemNo;
    private Integer splitNo;
    private boolean purchase;
    private Integer stateCode;

    private Map<String, String> statusInfo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getSplitNo() {
        return splitNo;
    }

    public void setSplitNo(Integer splitNo) {
        this.splitNo = splitNo;
    }

    public boolean isPurchase() {
        return purchase;
    }

    public void setPurchase(boolean purchase) {
        this.purchase = purchase;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public Map<String, String> getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(Map<String, String> statusInfo) {
        this.statusInfo = statusInfo;
    }

    public String infoToJson() {
        return JSONUtil.toJsonStr(this.getStatusInfo());
    }


}
