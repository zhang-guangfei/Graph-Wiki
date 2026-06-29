package com.sales.ops.enums.common;

/**
 * @author ：C14717
 * @version: $ bugid:17799
 * @description：
 * @date ：Created in 2025/6/13 14:00
 */
public enum BomSelectEnum {


    SUCCESS(true,"200", "成功"),
    PARAM_FAIRE(false,"101", "参数错误"),
    NONE(false,"102", "未匹配到任何bom"),
    NONE_DETAIL_ERROR(false,"103", "bom子项缺失"),
    QTY_ERROR(false,"104", "bom子项数量非法"),
    ONE_DETAIL_ERROR(false,"105", "bom子项仅1条非法"),
    SYS_ERROR(false,"500","系统异常"),
    ;

    private Boolean success;
    private String code;
    private String desc;

    BomSelectEnum(Boolean success, String code, String desc) {
        this.success = success;
        this.code = code;
        this.desc = desc;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
