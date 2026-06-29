package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Author: B90034
 * Date: 2022-05-07 10:02
 * Description:
 */
public enum CarrierEnum {

    DTW("DTW", "大田"),
    FEDEX("FEDEX", "联邦"),
    JD("JD", "京东"),
    SF("SF", "顺丰特快"),
    SHENGD("SHENGD", "盛典"),
    ZT("ZT", "自提"),

    ZL("ZL", "暂留"),
    NON("NON","物流自选");
    private String code;
    private String name;

    CarrierEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(String code) {

        if (StringUtils.isBlank(code)) {
            return CarrierEnum.NON.getName();
        }

        for (CarrierEnum value : CarrierEnum.values()) {
            if (value.code.equals(code)) {
                return value.name;
            }
        }
        return code;
    }

    public static String getCodeByName(String name) {
        if (StringUtils.isBlank(name)) {
            return CarrierEnum.NON.getCode();
        }
        for (CarrierEnum value : CarrierEnum.values()) {
            if (name.contains(value.name)) {
                return value.code;
            }
        }
        return CarrierEnum.NON.getCode();
    }

    public static CarrierEnum getEnum(String code) {
        for (CarrierEnum value : CarrierEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String name = CarrierEnum.NON.getName();
        System.out.println("name = " + name);
    }
}
