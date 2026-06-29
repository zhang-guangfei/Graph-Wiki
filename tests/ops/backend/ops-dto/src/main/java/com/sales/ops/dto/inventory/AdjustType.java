package com.sales.ops.dto.inventory;

/**
 * @author C12961
 * @date 2023/4/7 14:55
 */
public enum AdjustType {
    Addition("+", "调账加库存"),
    Subtraction("-", "调账减库存"),
    ;


    AdjustType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private final String type;
    private final String desc;

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
