package com.sales.ops.enums;

/**
 * @author ：C14717
 * @version: 1.0$
 * @description：task 优先级
 * @date ：Created in 2023/10/26 9:48
 */
public enum WmOrderTaskPriorityEnum {

    NINE(9, "最高级越库"),
    EIGHT(8, "上预约"),
    ;
    private Integer type;
    private String desc;

    WmOrderTaskPriorityEnum(Integer type, String desc) {
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
