package com.sales.ops.dto.inqb;

import java.util.Arrays;

/**
 * 催促处理整单的有效性状态字典
 */
public enum InqbValidityEnum {
    VALID("1", "可用"),
    INVALID("0", "不可用");


    private String type;
    private String desc;

    InqbValidityEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static InqbValidityEnum parse(String type) {
        return Arrays.stream(InqbValidityEnum.values()).filter(e -> e.getType().equals(type)).findFirst().orElse(null);
    }

}
