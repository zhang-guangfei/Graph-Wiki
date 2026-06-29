package com.smc.smccloud.core.model.enums;

/**
 * Author: B90034
 * Date: 2022-04-22 09:34
 * Description: 运费负担
 */
public enum TransFeeEnum {

    SMC("0", "SMC负担"),
    GK("1", "客户负担"),
    QD("2", "渠道负担");

    private String code;
    private String name;

    TransFeeEnum(String code, String name) {
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
        for (TransFeeEnum value : TransFeeEnum.values()) {
            if (value.code.equals(code)) {
                return value.name;
            }
        }
        return null;
    }
}
