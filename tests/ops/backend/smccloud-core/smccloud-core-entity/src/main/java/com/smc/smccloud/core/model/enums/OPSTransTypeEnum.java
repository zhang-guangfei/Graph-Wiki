package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/6/9 15:47
 * @Descripton TODO
 */

public enum  OPSTransTypeEnum {
    seaTran("0","海运"),
    airTran("1","空运"),
    lendTran("3","陆运"),
    clippersTran("4","快船"),
    railway("5","铁路"),
    courier("G","COURIER"),
    ;

    private String code;
    private String codeName;

    OPSTransTypeEnum(String code, String codeName) {
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

    public static String  getNameByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (OPSTransTypeEnum item : OPSTransTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }

}
