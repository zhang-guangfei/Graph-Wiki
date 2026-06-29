package com.sales.ops.enums;

/**
 * @author ：c02483
 * @date ：Created in 2022/1/24 16:58
 * @description：富勒成功与失败标识
 */
public enum FluxMsgFlagEnum {
    SUCCESS("success", "成功"),
    FAILURE("failure", "失败");

    private String flag;
    private String desc;

    FluxMsgFlagEnum(String flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

    public String getFlag() {
        return flag;
    }
}
