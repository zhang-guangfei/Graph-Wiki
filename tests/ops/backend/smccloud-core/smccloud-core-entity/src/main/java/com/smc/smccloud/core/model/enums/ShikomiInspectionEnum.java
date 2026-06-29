package com.smc.smccloud.core.model.enums;

/**
 * @author edp04
 * @title: Shikomi点检
 * @date 2022/06/30 17:18
 */
public enum ShikomiInspectionEnum {

    shikomiStop("1", "清算中止中"),
    shikomiApply("2", "追加申请中"),
    shikomiContinue("3", "继续申请中"),
    shikomiOverDay("4", "逾期项"),
    shikomiNormalConsume("5", "正常消耗项"),
    shikomiShortRetention("6", "短期滞留项"),
    shikomiLongRetention("7", "长期滞留项");

    private String code;
    private String name;

    ShikomiInspectionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getName(String code) {
        for (ShikomiInspectionEnum value : ShikomiInspectionEnum.values()) {
            if (value.code.equals(code)) {
                return value.name;
            }
        }
        return null;
    }

    public static ShikomiInspectionEnum getEnum(String code) {
        for (ShikomiInspectionEnum value : ShikomiInspectionEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
