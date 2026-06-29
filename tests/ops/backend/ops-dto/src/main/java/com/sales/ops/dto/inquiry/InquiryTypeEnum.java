package com.sales.ops.dto.inquiry;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * 催促处理状态字典
 */
public enum InquiryTypeEnum {
    PURCHASE("0", "PO"),
    ORDER("1", "DD"),
    OTHERRE("9", "PA");


    private String type;
    private String desc;

    InquiryTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


    public static InquiryTypeEnum parse(String type) {
        return Arrays.stream(InquiryTypeEnum.values()).filter(e -> StringUtils.equals(e.getType(), type)).findFirst().orElse(null);
    }
}
