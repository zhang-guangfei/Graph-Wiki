package com.sales.ops.enums;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：ops提交数据到wms 枚举
 * @date ：Created in 2021/10/28 9:48
 */
public enum WmOrderTaskEnum {

    RO("RO", "收货"),
    DO("DO", "发货"),
    PCO("PCO", "加工"),
    ORDER("ORDER", "订单类型"),
    BARCODE("BARCODE", "箱号"),
    ;
    private String type;
    private String desc;

    WmOrderTaskEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }
}
