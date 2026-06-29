package com.smc.smccloud.core.model.enums;


import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/4/24 9:59
 * @Descripton 运输方式
 */
public enum TransTypeEnum {

    seaTransport("S", "海运"),
    airTransport("A", "空运"),
    landTransport("L", "陆运");
//    SPEED("U", "快船"),
//    SPEED("U", "铁路");

    private String code;
    private String codeName;

    TransTypeEnum(String code, String codeName) {
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
        if(StringUtils.isBlank(code)) {
            return null;
        }
        for (TransTypeEnum item : TransTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.codeName;
            }
        }
        return null;
    }

    public static String getCodeByName (String name) {
        for (TransTypeEnum item : TransTypeEnum.values()) {
            if (item.getCodeName().equals(name)) {
                return item.code;
            }
        }
        return null;
    }
}
