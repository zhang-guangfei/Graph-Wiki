package com.smc.smccloud.model.enums;

/**
 * Author: B90034
 * Date: 2022-07-09 11:12
 * Description: 先行在库申请类型
 */
public enum PreStockApplyTypeEnum {

    PRE_STOCK("1", "专备"),
    SHIKOMI("2", "SHIKOMI"),
    WT("3", "服务备库"),
    ;

    private String code;
    private String codeName;

    PreStockApplyTypeEnum(String code, String codeName) {
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
        for (PreStockApplyTypeEnum item : PreStockApplyTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
