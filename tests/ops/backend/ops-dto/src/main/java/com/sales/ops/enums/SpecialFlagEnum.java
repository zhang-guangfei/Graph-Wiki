package com.sales.ops.enums;

/**
 * @author C12961
 * @date 2022/4/11 15:47
 */
public enum SpecialFlagEnum {
    SPECIAL(1,"特发"),
    NORMAL(2,"普通"),
    ;

    private Integer code;
    private String desc;


    SpecialFlagEnum(Integer code, String desc) {
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
