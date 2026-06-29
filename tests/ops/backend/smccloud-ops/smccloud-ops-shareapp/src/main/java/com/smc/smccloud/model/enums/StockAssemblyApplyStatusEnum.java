package com.smc.smccloud.model.enums;

/**
 * Author: B90034
 * Date: 2022-10-13 09:37
 * Description:
 */
public enum StockAssemblyApplyStatusEnum {

    editing("1", "编辑中"),
    approving("2", "待确认"),
    processing("3", "待处理"),
    uncheck("4", "不通过"),
    confirm("5", "组装中"),
    finished("6", "已完成"),
    noHandle("7", "不能组装"),
    cancel("9", "取消");

    private String code;
    private String codeName;

    StockAssemblyApplyStatusEnum(String code, String codeName) {
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
        for (StockAssemblyApplyStatusEnum item : StockAssemblyApplyStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
