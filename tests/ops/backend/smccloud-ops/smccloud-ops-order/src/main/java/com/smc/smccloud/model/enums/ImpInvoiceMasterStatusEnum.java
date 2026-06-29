package com.smc.smccloud.model.enums;

/**
 * @Author edp02 @Date 2022/6/8 11:09
 */
public enum ImpInvoiceMasterStatusEnum {
    EDIT(10, "编辑中"),
    PREARRIVAL(1, "预计到货"),
    BEIMP(2, "已到货待入库"),
    IMPED(3, "已发票入库"),
    BECOST(4, "待入成本【增值税发票】"),
    COSTED(6, "已入成本【增值税发票】"),
    PACK(7, "分包明细"),
    DELETED(9, "删除"),
    ;
    private Integer type;
    private String desc;

    ImpInvoiceMasterStatusEnum(Integer type, String desc) {
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
