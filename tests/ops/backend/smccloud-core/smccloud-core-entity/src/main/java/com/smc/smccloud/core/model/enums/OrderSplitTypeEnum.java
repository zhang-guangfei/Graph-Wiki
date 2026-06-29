package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/4/24 10:12
 * @Descripton  拆分标识(0:不拆分；1:数量拆分;  2:型号拆分)
 */
public enum  OrderSplitTypeEnum {

    noSplit("0","不拆分"),
    qtySplit("1","数量拆分"),
    modelNoSplit("2","型号拆分");


    private String code;
    private String codeName;

    OrderSplitTypeEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static String getCodeName(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (OrderSplitTypeEnum item : OrderSplitTypeEnum.values()) {
            if (item.code.equals(code)) {
                return item.codeName;
            }
        }
        return null;
    }

}
