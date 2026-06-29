package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2023/7/28 14:16
 * @Descripton TODO
 */
public enum  OtherDataExcResultEnum {
    not_exc("0","未执行"),
    exc_success("1","执行成功"),
    exc_fail("2","执行失败");

    private String code;
    private String  codeName;

    OtherDataExcResultEnum(String code, String codeName) {
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

    public static String getNameBycode(String code) {
        if ( StringUtils.isBlank(code) ) {
            return null;
        }
        for (OtherDataExcResultEnum item : OtherDataExcResultEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
