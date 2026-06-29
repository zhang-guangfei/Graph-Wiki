package com.sales.ops.enums;

/**
 * @author C12961
 * @date 2022/5/28 14:29
 */
public enum DlvSiteEnum {

    CUSTOMER(1,"直发客户"),
    DEPARTMENT(2,"直发营业所"),
    SELF(3,"自提"),
    ;

    private Integer code;
    private String desc;


    DlvSiteEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
