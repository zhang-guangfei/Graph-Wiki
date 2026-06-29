package com.sales.ops.enums;

/**
 * @author ：c02483
 * @date ：Created in 2022/1/24 16:40
 * @description：到货确认指令类型
 */
public enum RoConfirmRecTypeEnum {
    INZK(0, "INZK", "入库存"),
    INYY(1, "INYY", "上预约"),
    PCOYY(1, "PCOYY", "子项预约"),
    YK(2, "YK", "越库"),//直接下发,
    PCOYK(2, "PCOYK", "子项整单越库"),//直接下发
    ;

    private Integer code;
    private String type;
    private String desc;

    RoConfirmRecTypeEnum(Integer code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }
}
