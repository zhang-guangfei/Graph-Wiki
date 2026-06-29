package com.sales.ops.dto.tms;

public enum TMSRouteStatusEnum {

    UNKNOWN("0", "未知状态"),
    COLLECTED("1", "已揽件"),
    TRANSFERING("2", "运输中"),
    DELIVERYING("3", "派件中"),
    SIGNED("4", "已签收"),

    ;

    private String code;
    private String desc;


    TMSRouteStatusEnum(String code, String desc) {
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


}
