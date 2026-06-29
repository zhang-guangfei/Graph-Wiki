package com.sales.ops.enums;

/**
 * @author C12961
 * @date 2023/5/5 9:52
 */
public enum EnableCancelRequestPurchaseEnum {


    AUTO(1, "允许自动删单"),
    MANUAL(2, "允许手动删单"),
    UNALLOWED(3, "不允许删单"),
    ;

    private Integer code;
    private String desc;

    EnableCancelRequestPurchaseEnum(Integer code, String desc) {
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
