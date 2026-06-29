package com.sales.ops.dto.inquiry;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * inqa的催促级别字典
 */
public enum InquiryALevel {

    INQ_A("INQ_A", "催促次数在0-2时，显示INQ-A"), // 催促次数在0-2时，显示INQ-A
    HOT_1("HOT_1", "H1"), // 催促次数在3-4时，显示HOT_1
    HOT_2("HOT_2", "H2"); //催促次数在5-6时，显示HOT_2


    private String type;
    private String desc;

    InquiryALevel(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


    public static InquiryALevel parse(String type) {
        return Arrays.stream(InquiryALevel.values()).filter(e -> StringUtils.equals(e.getType(), type)).findFirst().orElse(null);
    }

}
