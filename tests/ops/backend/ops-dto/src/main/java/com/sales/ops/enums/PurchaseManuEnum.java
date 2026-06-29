package com.sales.ops.enums;

/**
 * @author B91717
 * @date 2023/05/22
 * @apiNote
 * 关务接口，返回token失效时的状态码
 */
public enum PurchaseManuEnum {
    INVALID_TOKEN("2000","token无效"),
    EXPIRED_TOKEN("2007","token过期"),
    SIGNATURE("2013","签名失败"),
    BAD_REQUEST("4000","非法参数");


    PurchaseManuEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private String type;
    private String desc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
