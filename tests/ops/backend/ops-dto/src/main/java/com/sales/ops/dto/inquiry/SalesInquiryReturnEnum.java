package com.sales.ops.dto.inquiry;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * 催促模块，门户返回信息，字典
 */
public enum SalesInquiryReturnEnum {
    ADDSUCCESS("受理成功", "受理成功"),
    ADDERROR("受理失败", "受理失败"),
    REPLYSUCCESS("处理成功", "处理成功"),
    REPLYERROR("处理失败", "处理失败");


    private String type;
    private String desc;

    SalesInquiryReturnEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


    public static SalesInquiryReturnEnum parse(String type) {
        return Arrays.stream(SalesInquiryReturnEnum.values()).filter(e -> StringUtils.equals(e.getType(), type)).findFirst().orElse(null);
    }
}
