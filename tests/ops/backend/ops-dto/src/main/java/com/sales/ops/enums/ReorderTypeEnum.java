package com.sales.ops.enums;

/**
 * @author C12961
 * @date 2022/4/12 13:07
 */
public enum ReorderTypeEnum {
    PO_TO_DO(0,"采购转在库"),
    DO_TO_DO(1,"在库转在库"),
    DO_TO_PO(2,"在库转采购"),
    EX_TO_PO(3,"异常转采购"),
    EX_TO_DO(4,"异常转在库"),
    ;

    private Integer type;
    private String desc;

    ReorderTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
