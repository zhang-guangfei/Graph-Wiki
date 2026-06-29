package com.sales.ops.enums;

/**
 * @author ：c02483
 * @date ：Created in 2021/11/20 14:01
 * @description：物流管理模块日志类型
 */
public enum LogWmsTypeEnum {

    CUST_ORDER("CUSTORDER", "客户单分配"),
    WMS_DO("DO", "物流发货"),
    WMS_RO("RO", "物流收货"),
    WMS_PCO("PCO", "物流加工");


    private String type;
    private String desc;

    LogWmsTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
