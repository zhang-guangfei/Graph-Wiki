package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/4/24 9:59
 * @Descripton 请购单类别
 */
public enum PurchaseTypeEnum {

    SALE("A", "销售订单"),
    BIN("K", "BIN补库订单"),
    PRE("B", "先行在库订单"),
    SPEED("U", "加急订单");

    private String code;
    private String codeName;

    PurchaseTypeEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static String getName (String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (PurchaseTypeEnum item : PurchaseTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.codeName;
            }
        }
        return null;
    }

    public static String getCodeByName (String name) {
        for (PurchaseTypeEnum item : PurchaseTypeEnum.values()) {
            if (item.getCodeName().equals(name)) {
                return item.code;
            }
        }
        return null;
    }
}
