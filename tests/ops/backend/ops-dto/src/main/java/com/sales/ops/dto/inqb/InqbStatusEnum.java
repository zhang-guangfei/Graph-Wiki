package com.sales.ops.dto.inqb;

import java.util.Arrays;

/**
 * 催促处理状态字典
 */
public enum InqbStatusEnum {
    DAICHULI(0, "待处理"),
    CUICUZHONG(1, "催促中"),
    BUFENDAFU(2, "部分答复"),
    BUKEWENXUN(3, "不可问询"),
    YITUIHUI(4, "已退回"),
    YIDAFU(5, "已答复"),
    YISHIYONG(6, "已使用"),
    YIGUOQI(7, "已过期"),

    ERROR(9,"催促异常");


    private int type;
    private String desc;

    InqbStatusEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static InqbStatusEnum parse(int type) {
        return Arrays.stream(InqbStatusEnum.values()).filter(e -> e.getType() == type).findFirst().orElse(null);
    }

}
