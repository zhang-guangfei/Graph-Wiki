package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @Author lyc
 * @Date 2022/7/13 15:10
 * @Descripton TODO
 */
public enum  OrderStockTypeEnum {

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

    public static String getTypeName(String type) {
        if (StringUtils.isBlank(type)) {
            return "";
        }
        for (OrderStockTypeEnum item: OrderStockTypeEnum.values()) {
            if (item.getType().equals(type)) {
                return item.getDesc();
            }
        }
        return null;
    }

}
