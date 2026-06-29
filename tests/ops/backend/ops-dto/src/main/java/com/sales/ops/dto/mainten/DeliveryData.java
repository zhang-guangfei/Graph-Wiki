package com.sales.ops.dto.mainten;

public class DeliveryData {
    // 型号
    private String modelNo;
    // 开始时间
    private String startTime;
    // 结束时间
    private String endTime;
    // 原产国
    private String countryId;
    // 供应商
    private String supplyId;
    // 最大订货数量
    private Integer maxNumber;
    // 主供应商
    private Boolean isPrimary;

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }
}
