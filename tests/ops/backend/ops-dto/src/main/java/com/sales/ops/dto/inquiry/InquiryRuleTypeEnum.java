package com.sales.ops.dto.inquiry;

/**
 * 催促处理状态字典
 */
public enum InquiryRuleTypeEnum {
    STATUS("inquiry_status", "催促状态"),
    REPLY_DESC("reply_description", "催促结果描述"),

    ERROR_WARNING("error_warning", "异常提醒");


    private String type;
    private String desc;

    InquiryRuleTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}
