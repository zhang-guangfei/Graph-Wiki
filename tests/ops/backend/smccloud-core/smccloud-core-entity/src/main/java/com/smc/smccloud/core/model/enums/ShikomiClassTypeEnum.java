package com.smc.smccloud.core.model.enums;

/**
 * Author: B90034
 * Date: 2022-04-24 15:11
 * Description: shikomi日本分类
 */
public enum ShikomiClassTypeEnum {

    A("A", "国内管理"),
    B("B", "国外管理"),
    C("C", "所有客户");

    private String code;
    private String name;

    ShikomiClassTypeEnum(String code, String name) {
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
        for (ShikomiClassTypeEnum value : ShikomiClassTypeEnum.values()) {
            if (value.code.equals(code)) {
                return value.name;
            }
        }
        return null;
    }

    public static ShikomiClassTypeEnum getEnum(String code) {
        for (ShikomiClassTypeEnum value : ShikomiClassTypeEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
