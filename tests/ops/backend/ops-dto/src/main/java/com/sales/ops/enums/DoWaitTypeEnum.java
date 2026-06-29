package com.sales.ops.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum DoWaitTypeEnum {
    EMPTY("", "空"),
    OK("OK", "货齐"),
    WaitCG("CG", "等待采购"),
    WaitDB("DB", "等待调拨"),
    WaitJG("JG", "等待加工"),
    CANCEL("DEL", "已删单"),
    EXCEPTION("EXCEPTION", "异常状态，等待处理"),
    ;

    private String type;
    private String desc;

    DoWaitTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static DoWaitTypeEnum parse(String code) {
        return Arrays.stream(DoWaitTypeEnum.values()).filter(e -> StringUtils.equals(e.getType(), code)).findFirst().orElse(null);
    }


}
