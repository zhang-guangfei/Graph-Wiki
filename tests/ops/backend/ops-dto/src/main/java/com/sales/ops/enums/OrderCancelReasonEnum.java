package com.sales.ops.enums;

/**
 * 客户订单取消原因
 * @author C12961
 * @date 2022/6/30 16:11
 */
public enum OrderCancelReasonEnum {
    项目取消(1, "项目取消"),
    选型错误(2, "选型错误"),
    对手替换(3, "对手替换"),
    客户货期提前(4, "客户货期提前"),
    制造货期延迟(5, "制造货期延迟"),
    客户订单错误(6, "客户订单错误"),
    SMC内部订单错误(7, "SMC内部订单错误"),
    组装问题(8, "组装问题"),
    欠款问题(9, "欠款问题"),
    错误型号(10, "错误型号"),
    收敛品(11, "收敛品"),
    贩卖限制品(12, "贩卖限制品"),
    申请特注(13, "申请特注"),
    海关不可进口(14, "海关不可进口"),
    SHIKOMI不足(15, "SHIKOMI不足"),
    产地问题(16, "产地问题"),
    其他(17, "其他"),


    ;


    private Integer code;
    private String desc;

    OrderCancelReasonEnum(Integer code, String desc) {
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
