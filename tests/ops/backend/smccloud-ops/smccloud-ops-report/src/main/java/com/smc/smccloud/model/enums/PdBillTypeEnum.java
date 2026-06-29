package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Author: C18097
 * Date: 2023-06-15
 * Description: 发票类型
 */
public enum PdBillTypeEnum {
    XPBILL("1","现品票"),
    SJBILL("2","数据票"),
    QDBILL("3","清单票"),
    XPBLANKBILL("4","现品空白票"),
    DHWRBLANKBILL("5","到货未入空白票");

    private String code;

    private String codeName;

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

    PdBillTypeEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static String getCodeName(String code) {
        if(StringUtils.isBlank(code)) {
            return null;
        }
        for (PdBillTypeEnum item : PdBillTypeEnum.values() ) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return code;
    }

}
