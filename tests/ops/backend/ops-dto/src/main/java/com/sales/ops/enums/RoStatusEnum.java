package com.sales.ops.enums;
/**
 * @author ：c02483
 * @date ：Created in 2021/11/17 18:08
 * @description：收货指令状态字典
 */
public enum RoStatusEnum {
    INIT(0,"初始"),
    WAIT(1,"待收货"),
    PART(2,"部分收货"),
    FINISH(3,"收货完成");


    private Integer status;
    private String desc;

    RoStatusEnum(Integer type, String desc) {
        this.status = type;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
