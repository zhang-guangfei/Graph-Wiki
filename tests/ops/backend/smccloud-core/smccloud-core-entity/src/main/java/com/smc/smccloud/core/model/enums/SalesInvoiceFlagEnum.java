package com.smc.smccloud.core.model.enums;

/**
 * @author edp04
 * @title: SalesInvoiceFlagEnum
 * @date 2022/08/08 17:48
 */
public enum  SalesInvoiceFlagEnum {
    WaitInvoiced("0", "待开票"),
    Confirmed("1", "已确认"),
    Invoiced("2", "已开票"),
    SampleCost("3", "样品结转"),
    Split("D", "拆分");

    private String code;
    private String name;

    SalesInvoiceFlagEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getName(String code) {
        for (SalesInvoiceFlagEnum value : SalesInvoiceFlagEnum.values()) {
            if (value.code.equals(code)) {
                return value.name;
            }
        }
        return null;
    }

    public static SalesInvoiceFlagEnum getEnum(String code) {
        for (SalesInvoiceFlagEnum value : SalesInvoiceFlagEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
