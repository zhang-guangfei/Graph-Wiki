package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2023/12/21 13:34
 * @Descripton TODO
 */
public enum  PdIsActiveEnum {

    gq("0","已过期"),
    jh("1","激活"),
    zf("2","作废");

    private String code;
    private String codeName;


    PdIsActiveEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String getCodeName(String code) {
        if(StringUtils.isBlank(code)) {
            return null;
        }
        for (PdIsActiveEnum item : PdIsActiveEnum.values() ) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return code;
    }
}
