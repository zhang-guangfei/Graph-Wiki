package com.sales.ops.event.bus;

import com.sales.ops.event.publisher.enums.EventTypeEnum;

import java.util.Arrays;

public enum EventCodeEnum implements EventTypeEnum {


    CUSTOMER_STATUS_UPDATE("CUSTOMER_STATUS_UPDATE", "客户订单-状态更新"),
    CUSTOMER_STOCK_ASSIGN("CUSTOMER_STOCK_ASSIGN", "客户订单-库存分配"),
    CUSTOMER_DELIVERY_PLAN("CUSTOMER_DELIVERY_PLAN", "客户订单-交付计划"),
    CUSTOMER_LIFECYCLE_LOG("CUSTOMER_LIFECYCLE_LOG", "客户订单-生命周期日志"),

    ;


    private String code;
    private String desc;

    EventCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    public static EventCodeEnum parse(String code) {
        return Arrays.stream(EventCodeEnum.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst().orElse(null);
    }

}
