package com.smc.smccloud.model.enums;

/**
 * Author: B90034
 * Date: 2022-07-09 11:12
 * Description: 先行在库申请项状态
 */
public enum PreStockDetailStatusEnum {

    editing("1", "提交中"),
    processing("2", "处理中"),
    purchaseCancel("3", "退单"),//采购，调库取消
    uncheck("4", "驳回"),
    confirm("5", "已确认"),
    finished("6", "已处理"),
    error("7", "异常拦截"),
    cancel("9", "取消"),
    no_process("10", "暂不处理"),
    ;

    private String code;
    private String codeName;

    PreStockDetailStatusEnum(String code, String codeName) {
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
        for (PreStockDetailStatusEnum item : PreStockDetailStatusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
