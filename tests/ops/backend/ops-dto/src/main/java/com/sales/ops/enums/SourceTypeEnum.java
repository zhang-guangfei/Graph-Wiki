package com.sales.ops.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * @author ：c02483
 * @date ：Created in 2021/10/2 13:36
 * @description：在途来源类型
 */
public enum SourceTypeEnum {
    PURCHASE("0", "采购"),
    DB("1", "调拨"),
    RETURN("2", "退货"),
    ZH("3", "组换"),
    ;


    private String type;
    private String desc;

    SourceTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


    public static SourceTypeEnum parse(String type) {
        return Arrays.stream(SourceTypeEnum.values()).filter(e -> StringUtils.equals(e.getType(), type)).findFirst().orElse(null);
    }
}
