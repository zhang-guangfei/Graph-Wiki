package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2025/7/14 10:22
 */
public enum OrderLimItTypeEnum {
    ORDER_LIMIT("OL", "限制订单"),;

    private String type;
    private String desc;

    OrderLimItTypeEnum(String type, String desc) {
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

    public static String getDesc (String type) {
        if (StringUtils.isBlank(type)) {
            return "";
        }
        for (OrderLimItTypeEnum item : OrderLimItTypeEnum.values()) {
            if (item.getType().equals(type)) {
                return item.getDesc();
            }
        }
        return "";
    }
}
