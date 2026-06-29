package com.smc.smccloud.core.model.enums;


import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/4/24 10:12
 * @Descripton  阀与汇流板标识
 */
public enum SpecMarkEnum {

    normalOrderGoods("0","正常订单"),
    db("1","底板"),
    zzyj("2","组装原件");


    private String code;
    private String codeName;

    SpecMarkEnum(String code, String codeName) {
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

        for (SpecMarkEnum item : SpecMarkEnum.values()) {
            if (item.code.equals(code)) {
                return item.codeName;
            }
        }
        return null;
    }

}
