package com.sales.ops.dto.inqb;

/**
 * 催促处理子项的使用状态字典
 */
public enum InqbDetailStatusEnum {
    VALID("0", "当前状态可用，未失效"),
    INVALID("1", "当前状态不可用，已失效"),

    HANDLEVALID("1", "生成采购问询单"),

    HANDLEINVALID("0", "未生成采购问询单");


    private String type;
    private String desc;

    InqbDetailStatusEnum(String type, String desc) {
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
