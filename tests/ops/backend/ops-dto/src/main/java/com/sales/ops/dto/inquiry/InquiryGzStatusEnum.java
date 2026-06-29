package com.sales.ops.dto.inquiry;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * 催促处理状态字典
 */
public enum InquiryGzStatusEnum {
    SHOULIZHONG("1", "受理中"),
    YIHUIFU("2", "已回复");


    private String type;
    private String desc;

    InquiryGzStatusEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


    public static InquiryGzStatusEnum parse(String type) {
        return Arrays.stream(InquiryGzStatusEnum.values()).filter(e -> StringUtils.equals(e.getType(), type)).findFirst().orElse(null);
    }
}
