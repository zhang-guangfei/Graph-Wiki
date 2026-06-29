package com.smc.smccloud.model.enums;

/**
 * Author: B90034
 * Date: 2022-07-09 11:12
 * Description: 先行在库申请提案类型
 */
public enum PreStockReplTypeEnum {

    SMC("1", "SMC提案"),
    CSTM("2", "客户提案(专备)"),
    AUTO("3", "自动周转"),
    ;

    private String code;
    private String codeName;

    PreStockReplTypeEnum(String code, String codeName) {
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
        for (PreStockReplTypeEnum item : PreStockReplTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
