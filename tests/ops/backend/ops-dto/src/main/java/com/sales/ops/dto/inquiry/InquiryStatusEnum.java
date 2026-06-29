package com.sales.ops.dto.inquiry;

/**
 * 催促处理状态字典
 */
public enum InquiryStatusEnum {
    DAICHULI(0, "待处理"),
    CUICUZHONG(1, "催促中"),
    BUFENDAFU(2, "部分答复"),

    YITUIHUI(4, "已退回"),
    YIDAFU(5, "已答复"),

    ERROR(9,"催促异常");


    private int type;
    private String desc;

    InquiryStatusEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByCode(Integer type) {
        if (type == null) {
            return null;
        }
        for (InquiryStatusEnum inquiryStatusEnum : InquiryStatusEnum.values()) {
            if (inquiryStatusEnum.getType() == type) {
                return inquiryStatusEnum.getDesc();
            }
        }
        return null;
    }

    public static Integer getCodeByDesc(String desc) {
        if (desc == null || desc == "") {
            return null;
        }
        for (InquiryStatusEnum inquiryStatusEnum : InquiryStatusEnum.values()) {
            if (inquiryStatusEnum.getDesc().equals(desc)) {
                return inquiryStatusEnum.getType();
            }
        }
        return null;
    }

}
