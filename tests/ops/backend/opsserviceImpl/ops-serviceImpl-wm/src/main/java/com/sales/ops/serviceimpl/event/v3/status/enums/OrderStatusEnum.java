package com.sales.ops.serviceimpl.event.v3.status.enums;

import java.util.Arrays;

public enum OrderStatusEnum {
    Exception(0, "Exception", "异常"),
    Request(1, "Request", "请购处理"),
    RequestIntercept(2, "RequestIntercept", "请购拦截"),
    Purchase(3, "Purchase", "对外采购"),
    Transfer(4, "Transfer", "仓间调拨"),
    WMReceive(5, "WMReceive", "仓库收货"),
    WMTask(6, "WMTask", "仓库待出库"),
    WMDelivery(7, "WMDelivery", "仓库出库"),
    Transition(8, "Transition", "运输中"),
    Finish(9, "Finish", "已送达"),

    CANCEL(11, "CANCEL", "删单"),
    ;


    private Integer index;
    private String code;
    private String desc;

    OrderStatusEnum(Integer index, String code, String desc) {
        this.index = index;
        this.code = code;
        this.desc = desc;
    }

    public static OrderStatusEnum getEnumByCode(String code) {
        return Arrays.stream(OrderStatusEnum.values()).filter(Enum -> Enum.getCode().equals(code)).findFirst().orElse(null);
    }

    public static OrderStatusEnum getEnumByIndex(Integer index) {
        return Arrays.stream(OrderStatusEnum.values()).filter(Enum -> Enum.getIndex().equals(index)).findFirst().orElse(null);
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getIndex() {
        return index;
    }


}
