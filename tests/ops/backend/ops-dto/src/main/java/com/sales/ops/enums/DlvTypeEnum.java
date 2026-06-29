package com.sales.ops.enums;

import com.alibaba.excel.util.StringUtils;

import java.util.Arrays;

public enum DlvTypeEnum {

    ADDRESS("0", "按地址集约"),
    ORDER("1", "按订单集约"),
    User("2", "按用户集约"),
    ;

    private String code;
    private String desc;


    DlvTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static DlvTypeEnum parse(String code) {
        return Arrays.stream(DlvTypeEnum.values()).filter(item -> StringUtils.equals(item.getCode(), code)).findFirst().orElse(null);
    }

}
