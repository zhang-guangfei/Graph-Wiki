package com.sales.ops.dto.customer;

public class CustomerShareAddress {

    // 0、通讯地址 1、发货类型
    public static final String POSTAL_TYPE = "0";
    public static final String DELIVERY_TYPE = "1";

    // 客户名称
    private String customerNo;
    // 地址类型
    private String type;
    // 省份行政区代码
    private String provinceCode;
    // 市级行政区代码
    private String cityCode;
    // 区县行政区代码
    private String regionCode;
    // 地址地址
    private String address;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerShareAddress{" +
                "customerNo='" + customerNo + '\'' +
                ", type='" + type + '\'' +
                ", provinceCode=" + provinceCode +
                ", cityCode=" + cityCode +
                ", regionCode=" + regionCode +
                ", address='" + address + '\'' +
                '}';
    }
}
