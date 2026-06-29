package com.sales.ops.enums;

/**
 * @author ：c02483
 * @date ：Created in 2021/10/2 19:22
 * @description：加工类型枚举
 */
public enum PcoTypeEnum {
    ZDJG("ZDJG", "主动加工"),
    PTJG("PTJG", "普通加工"),
    ;
    private String type;
    private String desc;

    PcoTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }
    public String getDesc() {
        return desc;
    }
    public static PcoTypeEnum getType(String type) {
        for (PcoTypeEnum typeEnum : values()) {
            if (typeEnum.type.equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }

}
