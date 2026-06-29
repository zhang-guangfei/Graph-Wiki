package com.smc.smccloud.model.enums;

/**
 * Author: B90034
 * Date: 2022-10-13 09:51
 * Description:
 */
public enum StockAssemblyAssemblyTypeEnum {

    ASSEMBLY("1", "同仓组换"),
    SPLIT("2", "同仓组换--拆分"),
    TRANSFER("3", "调库"),
    ASSEMBLY_BUSINESS_ONLY("4", "组换-仅业务"),
    ASSEMBLY_FINANCE_ONLY("5", "组换-仅财务"),
    ASSEMBLY_WMS_ONLY("6", "组换-仅WMS"),
    ;

    private String code;
    private String codeName;

    StockAssemblyAssemblyTypeEnum(String code, String codeName) {
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
        for (StockAssemblyAssemblyTypeEnum item : StockAssemblyAssemblyTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
