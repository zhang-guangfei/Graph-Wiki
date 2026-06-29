package com.sales.ops.enums;

/**
 * @author ：c02483
 * @date ：Created in 2021/11/18 19:49
 * @description：区分先行在库
 */
public enum InventoryPropertyTypeEnum {
    FORWARD("FORWARD", "先行在库"),
    NOMAL("NOMAL", "正常在库");


    private String type;
    private String desc;

    InventoryPropertyTypeEnum(String type, String desc) {
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
