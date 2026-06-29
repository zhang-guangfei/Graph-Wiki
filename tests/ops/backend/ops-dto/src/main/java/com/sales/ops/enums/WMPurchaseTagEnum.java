package com.sales.ops.enums;
/**
 * @author ：c02483
 * @date ：Created in 2021/11/10 10:31
 * @description：物流产生请购的标识  不用创建字典
 */
public enum WMPurchaseTagEnum {
    WHOLE("W","整型号采购"),
    ASS("A","拆分采购");

    private String type;
    private String desc;

    WMPurchaseTagEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }
}
