package com.sales.ops.enums;

/**
 * @author ：c02483
 * @date ：Created in 2022/1/24 16:38
 * @description：到货确认类型枚举
 */
public enum RoConfirmOptypeEnum {
    CK("CK", "出库单"),
    JG("JG", "加工单"),
    NONE("", "无"),
    ;

    RoConfirmOptypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private String type;
    private String desc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
