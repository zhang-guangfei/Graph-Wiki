package com.smc.smccloud.model.enums;

/**
 * @author wuweidong
 * @create 2023/12/29 16:04
 * @description
 */
public enum PreOrderDetailStatusEnum {
    editing("1", "决算"),
    approving("2", "审批"),
    processing("3", "清算"),
    delay("4", "延期中"),
    transfer("5", "已清算"),
    ;

    private String code;
    private String codeName;

    PreOrderDetailStatusEnum(String code, String codeName) {
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
        for (PreStockApplyStatusEnum item : PreStockApplyStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
