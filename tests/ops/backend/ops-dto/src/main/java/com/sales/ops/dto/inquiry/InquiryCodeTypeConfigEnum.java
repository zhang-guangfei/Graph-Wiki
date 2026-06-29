package com.sales.ops.dto.inquiry;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * ops_inquiry_code_config 催促字典配置表，code_type字段字典
 */
public enum InquiryCodeTypeConfigEnum {
    SEND("0", "发送字典配置"),
    REPLY("1", "回复字典配置");


    private String type;
    private String desc;

    InquiryCodeTypeConfigEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


    public static InquiryCodeTypeConfigEnum parse(String type) {
        return Arrays.stream(InquiryCodeTypeConfigEnum.values()).filter(e -> StringUtils.equals(e.getType(), type)).findFirst().orElse(null);
    }
}
