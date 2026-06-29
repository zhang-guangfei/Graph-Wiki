package com.sales.ops.dto.customer;

public class NationalArea {
    // 区域代码
    private String code;
    // 区域名称
    private String name;
    // 上级代码
    private String parentCode;
    // 行政区类型
    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NationalArea{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
