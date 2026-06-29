package com.sales.ops.enums;

public enum RoSourceEnum {

    EMPTY("", "空"),
    SALESINFO("SALESINFO", "情报预约"),
    SALESINFOUNDO("SALESINFOUNDO", "情报预约取消"),
    CG("CG", "采购入库"),
    ALL("ALL", "整型号出库"),
    ASSQTY("ASSQTY", "数量拆分出库"),
    ASSModelNo("ASSModelNo", "型号拆分出库"),
    ASSSpecilal("ASSSpecilal", "特发拆分出库"),
    RETURN("RETURN", "退货入库");

    private String type;
    private String desc;

    RoSourceEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
