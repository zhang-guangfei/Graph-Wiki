package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/5/21 14:12
 * @Descripton TODO
 */
public enum  CstmTypeEnum {
    ZX("0","直销"),
    DX("1","代销"),
    JX("2","经销");


    private String code;
    private String codeNmae;

    CstmTypeEnum(String code, String codeNmae) {
        this.code = code;
        this.codeNmae = codeNmae;
    }

    public String getCode() {
        return code;
    }

    public String getCodeNmae() {
        return codeNmae;
    }

    public static String getTypeName(String code) {

        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (CstmTypeEnum item : CstmTypeEnum.values()) {
            if (item.code.equals(code)) {
                return item.getCodeNmae();
            }
        }

        return null;
    }

    public static void main(String[] args) {
        String typeName = getTypeName("0");
        System.out.println("typeName = " + typeName);
    }


}
