package com.sales.ops.dto.inquiry;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * 催促处理状态字典
 */
public enum InquiryACommonEnum {

    PURCHASE("0", "PO");


    private String type;
    private String desc;

    InquiryACommonEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


    public static InquiryACommonEnum parse(String type) {
        return Arrays.stream(InquiryACommonEnum.values()).filter(e -> StringUtils.equals(e.getType(), type)).findFirst().orElse(null);
    }

}
