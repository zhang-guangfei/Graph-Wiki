package com.sales.ops.enums;

public enum TransOrderTypeEnum {


    TRANS(1,"调库"),
    EXCHANGE(2,"组换"),
    PURCHASE(3,"采购"),

    ;


    private final Integer code;
    private final String desc;


    TransOrderTypeEnum(Integer event, String desc) {
        this.code = event;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }



}
