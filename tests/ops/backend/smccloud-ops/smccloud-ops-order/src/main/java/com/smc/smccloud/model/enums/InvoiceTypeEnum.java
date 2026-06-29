package com.smc.smccloud.model.enums;

/**
 * Author: B90034
 * Date: 2022-08-27 09:51
 * Description: 发票类型
 */
public enum InvoiceTypeEnum {

    JP(1, "日本"),
    THREE_COUNTRY(2, "三国"),
    CM(3, "国内工厂"),
    TRIPARTITE(4, "三方"), // 中国工厂/北京工厂/天津工厂/上海
    CN_VAT(5, "国内增值发票"),
    ADJUST(7,"调整"),
    OTHER(9,  "其他");

    private Integer code;

    private String name;

    InvoiceTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }


    public String getName() {
        return this.name;
    }

    public static String getNameByCode(Integer code) {
        if (code == null) {
            return "";
        }
        for (InvoiceTypeEnum item : InvoiceTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getName();
            }
        }
        return null;
    }

}
