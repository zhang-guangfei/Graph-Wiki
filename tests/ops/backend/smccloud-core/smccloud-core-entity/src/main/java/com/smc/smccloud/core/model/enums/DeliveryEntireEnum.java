package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Author: B90034
 * Date: 2022-04-22 09:17
 * Description: 发货方式
 */
public enum DeliveryEntireEnum {

    ANYTIME("0", "随到随发"),
    SIGN_DB("1", "货齐单仓"),
    BATCH("2", "随发分批"),
    MULTI_DB("3", "货齐多仓"),
    BATCH_SIGN_DB("4", "随发单仓");

    private String code;
    private String name;

    DeliveryEntireEnum(String code, String name) {
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
        for (DeliveryEntireEnum value : DeliveryEntireEnum.values()) {
            if (value.code.equals(code)) {
                return value.name;
            }
        }
        return null;
    }

    public static String getCodeByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (DeliveryEntireEnum value : DeliveryEntireEnum.values()) {
           if (value.getName().equals(name)) {
               return value.getCode();
           }
        }
        return null;
    }

}
