package com.smc.smccloud.model.enums;

/**
 * @Author edp02 @Date 2022/6/8 10:25
 */
public enum OpsPoInvoiceStatusEnum {

    IMP(1, "发票入库"),
    RECEIVED(2, "已签收"),
    COST(3, "已成本结算"),
    IMPNOCOST(4, "发票入库(不入成本)"),
    ;
    private Integer type;
    private String desc;

    OpsPoInvoiceStatusEnum(Integer type, String desc) {
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
