package com.sales.ops.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

public enum DoSourceEnum {

    EMPTY("", "空"),
    SALESINFO("SALESINFO", "情报预约"),
    SALESINFOUNDO("SALESINFOUNDO", "情报预约取消"),
    ALL("ALL", "整数量出库"),
    ASSQTY("ASSQTY", "数量拆分出库"),
    ASSModelNo("ASSModelNo", "型号拆分出库"),
    ASSSpecilal("ASSSpecilal", "特发拆分出库"),
    CG("CG", "整单采购出库"),
    ;

    private String type;
    private String desc;

    DoSourceEnum(String type, String desc) {
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

    public static boolean isAssModel(String type) {
        return ASSModelNo.getType().equalsIgnoreCase(type);
    }

    public static DoSourceEnum parse(String type) {
        return Arrays.stream(DoSourceEnum.values()).filter(e -> StringUtils.equals(e.getType(), type)).findFirst().orElse(null);
    }


    public OrderSplitTypeEnum splitType() {
        if (this == ALL) {
            return OrderSplitTypeEnum.ALL;
        } else if (this == CG) {
            return OrderSplitTypeEnum.CG;
        } else if (this == ASSQTY) {
            return OrderSplitTypeEnum.ASSQTY;
        } else if (this == ASSModelNo) {
            return OrderSplitTypeEnum.ASSModelNo;
        }
        return null;
    }
}
