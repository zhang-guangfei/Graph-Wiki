package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;


public enum PurchaseModifyTypeEnum {
    bggys("A","变更供应商"),
    cancel("C","删除订单"),
    bgchr("D","变更指定工厂出荷日"),
    bgysfs("T","变更采购运输方式");


    private String code;
    private String codeName;



    PurchaseModifyTypeEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public static String getNameByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (PurchaseModifyTypeEnum item : PurchaseModifyTypeEnum.values() ) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;

    }

    public static PurchaseModifyTypeEnum toType(String value) {
        return Stream.of(PurchaseModifyTypeEnum.values())
                .filter(p -> p.code.equals(value))
                .findAny()
                .orElse(null);
    }
}
