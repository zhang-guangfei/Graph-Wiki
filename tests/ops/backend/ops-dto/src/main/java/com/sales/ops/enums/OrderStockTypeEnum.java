package com.sales.ops.enums;

import java.util.Arrays;

/**
 * @author C12961
 * @date 2022/1/29 12:19
 */
public enum OrderStockTypeEnum {

    ZK("N", "在库"),
    ZT("T", "在途"),
    CG("CG", "采购");

    private String type;
    private String desc;

    OrderStockTypeEnum(String type, String desc) {
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

    public static String getDescFromType(String type) {
        OrderStockTypeEnum orderStockTypeEnum = Arrays.stream(OrderStockTypeEnum.values())
                .filter(Enum -> Enum.getType().equals(type)).findFirst().orElse(null);
        return orderStockTypeEnum == null ? "" : orderStockTypeEnum.getDesc();
    }

    public static OrderStockTypeEnum getEnumFromType(String type) {
        return Arrays.stream(OrderStockTypeEnum.values()).filter(item -> item.getType().equals(type)).findFirst().orElse(null);
    }



}
