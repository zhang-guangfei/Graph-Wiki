package com.smc.smccloud.model.enums;

/**
 * Author: B90034
 * Date: 2022-07-09 12:29
 * Description:
 */
public enum PreStockNewFlagEnum {

    NEW("1", "新规"),
    CONTINUE("2", "继续"),
    ;

    private String code;
    private String codeName;

    PreStockNewFlagEnum(String code, String codeName) {
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
        for (PreStockNewFlagEnum item : PreStockNewFlagEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
