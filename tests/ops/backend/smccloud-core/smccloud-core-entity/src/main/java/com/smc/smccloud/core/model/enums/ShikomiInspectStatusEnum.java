package com.smc.smccloud.core.model.enums;

/**
 * @author edp04
 * @title: ShikomiInspectStatusEnum
 * @date 2022/07/21 11:06
 */
public enum  ShikomiInspectStatusEnum {
    normal(0, "正常"),
    pendingReply(1, "门户待回复"),
    pending(2, "待处理"),
    sendToSupplier(4, "已发给供应商"),
    complete(6, "点检完成"),
    cancel(9, "取消");

    private Integer code;
    private String name;

    ShikomiInspectStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getName(Integer code) {
        for (ShikomiInspectStatusEnum value : ShikomiInspectStatusEnum.values()) {
            if (value.code.equals(code)) {
                return value.name;
            }
        }
        return null;
    }

    public static ShikomiInspectStatusEnum getEnum(Integer code) {
        for (ShikomiInspectStatusEnum value : ShikomiInspectStatusEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
