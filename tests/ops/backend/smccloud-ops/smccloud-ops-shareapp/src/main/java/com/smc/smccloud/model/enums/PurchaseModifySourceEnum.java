package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;


public enum PurchaseModifySourceEnum {
    cgxg("0","采购批量变更界面维护"),
    mhxg("1","门户批量修改接入");


    private String code;
    private String codeName;



    PurchaseModifySourceEnum(String code, String codeName) {
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
        for (PurchaseModifySourceEnum item : PurchaseModifySourceEnum.values() ) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;

    }
}
