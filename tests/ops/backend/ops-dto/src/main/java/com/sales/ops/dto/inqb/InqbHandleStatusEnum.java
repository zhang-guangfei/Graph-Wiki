package com.sales.ops.dto.inqb;

/**
 * 催促处理使用状态字典
 */
public enum InqbHandleStatusEnum {
    DAICHULI(0, "未处理，初始状态"),
    WENXUNZHONG(1, "问询中，已发送供应商"),

    YIHUIFU(2, "已回复，供应商已回复"),

    ERROR(9, "催促异常");


    private int type;
    private String desc;

    InqbHandleStatusEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}
