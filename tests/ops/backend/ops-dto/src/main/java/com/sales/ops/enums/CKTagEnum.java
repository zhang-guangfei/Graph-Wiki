package com.sales.ops.enums;

/**
 * @author ：
 * @date ：
 * @description：出库特殊标识对应出库流程枚举：1无特殊标识 2. 单项组装 3.整单组装 4.不出委托 5.必须出委托 6.不出委托和单项组装
 * 7.不出委托和整单组装 8.rohs 9 rohs和单项组装 10 rohs和整单组装 11 rosh和不出委托 12 rosh和必须出委托 13 rosh和不出委托和单项组装 14 rosh和不出委托和整单组装
 */
public enum CKTagEnum {

    NOT("1", "NOT", "无特殊标识"),
    ONE_ASS("2", "ONE_ASS", "单项组装"),
    ALL_ASS("3", "ALL_ASS", "整单组装"),
    NO_WT("4", "NO_WT", "不出服务备库"),
    MUST_WT("5", "MUST_WT", "必须出服务备库"),
    NO_WT_ONE_ASS("6", "NO_WT_ONE_ASS", "不出服务备库和单项组装"),
    NO_WT_ALL_ASS("7", "NO_WT_ALL_ASS", "不出服务备库和整单组装"),
    ROHS("8", "ROHS", "rohs10"),
    ROHS_ONE_ASS("9", "ROHS_ONE_ASS", "rohs10和单项组装"),
    ROHS_ALL_ASS("10", "ROHS_ALL_ASS", "rohs10和整单组装"),
    ROHS_NO_WT("11", "ROHS_NO_WT", "rosh10和不出服务备库"),
    ROHS_MUST_WT("12", "ROHS_MUST_WT", "rosh10和必须出服务备库"),
    ROHS_NO_WT_ONE_ASS("13", "ROHS_NO_WT_ONE_ASS", "rosh10和不出服务备库和单项组装"),
    ROHS_NO_WT_ALL_ASS("14", "ROHS_NO_WT", "rosh10和不出不出服务备库和整单组装"),
    ;

    private String code;
    private String type;
    private String desc;

    CKTagEnum(String code, String type, String desc) {
        this.code = code;
        this.type = type;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
