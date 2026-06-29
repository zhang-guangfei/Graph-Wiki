package com.smc.smccloud.core.model.enums;

/**
 * Author: B90034
 * Date: 2022-04-24 15:10
 * Description: shikomi国内共享分类
 */
public enum ShikomiClassCodeEnum {

    Global("0", "全球共享"),
    China("1", "中国公司共享"),
    CustomerShare("2", "专享可借用"),
    CustomerPrivate("3", "客户专享"),
    ChinaUNAVAIL("4", "国内不可用");

    private String code;
    private String name;

    ShikomiClassCodeEnum(String code, String name) {
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
        for (ShikomiClassCodeEnum value : ShikomiClassCodeEnum.values()) {
            if (value.code.equals(code)) {
                return value.name;
            }
        }
        return null;
    }

    public static ShikomiClassCodeEnum getEnum(String code) {
        for (ShikomiClassCodeEnum value : ShikomiClassCodeEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
