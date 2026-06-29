package com.smc.smccloud.core.model.enums;

/**
 * @author edp04
 * @title: ShikomiInspectTypeEnum
 * @date 2022/07/21 12:00
 */
public enum ShikomiInspectTypeEnum {
    warning(1, "警告预警项"),
    normalConsume(2, "正常消耗项"),
    overdue(3, "逾期项"),
    shorttermRetention(4, "短期滞留项"),
    longtermRetention(5, "长期滞留项"),
    delay(6, "决算延期项"),
    ontinue(7, "恢复继续"),
    stop(9, "清算终止项");

    private Integer code;
    private String name;

    ShikomiInspectTypeEnum(Integer code, String name) {
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
        for (ShikomiInspectTypeEnum value : ShikomiInspectTypeEnum.values()) {
            if (value.code.equals(code)) {
                return value.name;
            }
        }
        return null;
    }

    public static ShikomiInspectTypeEnum getEnum(Integer code) {
        for (ShikomiInspectTypeEnum value : ShikomiInspectTypeEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
