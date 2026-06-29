package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Author: B90034
 * Date: 2022-04-22 09:24
 * Description: 集约方式
 */
public enum IntensiveEnum {

    ADDRESS("0", "地址集约"),
    ORDER("1", "按订单集约"),
    USER("2", "按用户集约");

    private String code;
    private String name;

    IntensiveEnum(String code, String name) {
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

        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (IntensiveEnum value : IntensiveEnum.values()) {
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
        for (IntensiveEnum value : IntensiveEnum.values()) {
            if (value.getName().equals(name)) {
                return value.getCode();
            }
        }
        return null;
    }

}
