package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Author: B90034
 * Date: 2022-03-22 15:11
 * Description:
 */
public enum InventoryTypeEnum {

    GK_PJ("GK-PJ", "顾客在库项目"),
    GK_PPL("GK-PPL", "顾客在库PPL"),
    GK_TY("GK-TY", "顾客在库通用"),
    QB_NO("QB_NO", "情报号"),
    TY("TY", "通用在库"),
    ZL_CP("ZL-CP", "战略在库（产品）"),
    ZL_HY("ZL-HY", "战略在库（行业）"),
    ZL_JT("ZL-JT", "战略在库（集团）"),
    ZL_PJ("ZL-PJ", "战略在库（项目）");

    private String code;

    private String name;

    InventoryTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static String getName(String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        for (InventoryTypeEnum e : InventoryTypeEnum.values()) {
            if (e.getCode().equals(code)) {
                return e.getName();
            }
        }
        return null;
    }
}
