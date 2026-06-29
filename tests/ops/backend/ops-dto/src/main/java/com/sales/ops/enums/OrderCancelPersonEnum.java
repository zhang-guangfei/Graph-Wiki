package com.sales.ops.enums;

/**
 * 客户订单取消原因
 * @author C12961
 * @date 2022/6/30 16:11
 */
public enum OrderCancelPersonEnum {
    客户(1,"客户"),
    代理店(2,"代理店"),
    营业所(3,"营业所"),
    业务处理中心(4,"业务处理中心"),
    中国制造通知(5,"中国制造通知"),
    日本通知(6,"日本通知"),
    业务课(7,"业务课"),
    ;


    private Integer code;
    private String desc;

    OrderCancelPersonEnum(Integer code, String desc) {
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
