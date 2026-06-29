package com.smc.smccloud.model.enums;

/**
 * Author: B90034
 * Date: 2022-07-09 11:11
 * Description: 先行在库申请状态
 */
public enum PreStockApplyStatusEnum {

    editing("1", "编辑中"),
    approving("2", "待审核"),
    processing("3", "待处理"),
    uncheck("4", "不通过"),
    confirm("5", "已确认(SHIKOMI)"),
    finished("6", "已备库"),
    cancel("9", "取消"),
    ;

    private String code;
    private String codeName;

    PreStockApplyStatusEnum(String code, String codeName) {
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
