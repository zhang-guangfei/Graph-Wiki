package com.sales.ops.serviceimpl.event.v3.status.enums;

public enum RelationErrorCode {

    RCV_CANCELED("1","客户订单已删除"),
    NOT_FOUND_RCV("1","找不到客户订单"),
    NOT_FOUND_JYCK("2","找不到交易出库单"),
    PARSE_FAIL_DO_SOURCE("3","无法解析拆分方式（既有型号拆分也有数量拆分）"),
    NOT_FULL_RELATION_QTY("4","关联关系没有找全"),
    NOT_FOUND_INVENTORY("5","找不到库存"),

    ;

    private String code;
    private String desc;

    RelationErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
