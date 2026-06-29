package com.sales.ops.enums;

/**
 * @author ：c02483
 * @date ：Created in 2021/11/10 9:41
 * @description：质量枚举
 */
public enum QAStatusEnum {
    NORMAL("0", "良品"),
    BAD("1", "不良品"),
    UNCHECKED("2", "未检品"),
    ;


    private String type;
    private String desc;

    QAStatusEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
