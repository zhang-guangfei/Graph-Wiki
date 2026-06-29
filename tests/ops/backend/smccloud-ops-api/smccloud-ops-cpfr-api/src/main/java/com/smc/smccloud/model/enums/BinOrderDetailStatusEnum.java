package com.smc.smccloud.model.enums;

/**
 * @Author edp02 @Date 2022/6/28 12:15
 */
public enum BinOrderDetailStatusEnum {
    EDIT(0, "编辑"),
    CACUL(1, "计算中"),
    APPROVING(2, "审批中"),
    PASS(4, "审批通过"),
    NoPASS(4, "审批未通过"),
    ORDER(5, "生成订单"),
    ;
    private Integer type;
    private String desc;

    BinOrderDetailStatusEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
