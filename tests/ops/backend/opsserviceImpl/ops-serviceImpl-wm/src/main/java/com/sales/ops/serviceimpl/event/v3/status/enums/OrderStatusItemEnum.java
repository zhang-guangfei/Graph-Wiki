package com.sales.ops.serviceimpl.event.v3.status.enums;

import java.util.Arrays;

public enum OrderStatusItemEnum {
    EXCEPTION(1, "EXCEPTION", "异常"),

    // 采购
    REQUEST_PREPROCESS(11, "REQUEST_PREPROCESS", "请购预处理"),
    REQUEST_INTERCEPT(21, "REQUEST_INTERCEPT", "请购拦截"),
    PURCHASE_SEND(31, "PURCHASE_SEND", "采购业务已发单"),
    PURCHASE_RECEIVE_ERROR(32, "PURCHASE_RECEIVE_ERROR", "供应商接单异常"),
    PURCHASE_RECEIVED(33, "PURCHASE_RECEIVED", "供应商已接单"),
    PURCHASE_PRODUCTION(34, "PURCHASE_PRODUCTION", "供应商生产中"),
    PURCHASE_TRANSIT(35, "PURCHASE_TRANSIT", "供应商运输中"),
    PURCHASE_CUSTOMS(36, "PURCHASE_CUSTOMS", "采购报关中"),
    PURCHASE_INVOICE_CONFIRM(37, "PURCHASE_INVOICE_CONFIRM", "采购已发票确认"),
    PURCHASE_SIGN_CONFIRM(38, "PURCHASE_SIGN_CONFIRM", "采购已签收等待处理"),
    PURCHASE_GOODS_CONFIRM(39, "PURCHASE_GOODS_CONFIRM", "采购收货处理"),
    PURCHASE_RECEIVE_CONFIRM(39, "PURCHASE_RECEIVE_CONFIRM", "采购等待货齐出库"),

    // 调拨
    TRANSFER_TASK_WAITING(41, "TRANSFER_TASK_WAITING", "调拨待下发"),
    TRANSFER_TASK_SEND(42, "TRANSFER_TASK_SEND", "调拨仓库待处理"),
    TRANSFER_WAVE_CONFIRM(43, "TRANSFER_WAVE_CONFIRM", "调拨生成发货统计"),
    TRANSFER_PICKING(44, "TRANSFER_PICKING", "调拨仓库拣货中"),
    TRANSFER_DELIVERY(44, "TRANSFER_DELIVERY", "调拨已出库待揽收"),
    TRANSFER_TRANSIT(45, "TRANSFER_TRANSIT", "调拨运输中"),
    TRANSFER_SIGN_CONFIRM(46, "TRANSFER_SIGN_CONFIRM", "调拨已签收等待处理"),
    TRANSFER_GOODS_CONFIRM(47, "TRANSFER_GOODS_CONFIRM", "调拨收货处理"),
    TRANSFER_RECEIVE_CONFIRM(48, "TRANSFER_RECEIVE_CONFIRM", "调拨等待货齐出库"),

    // 等待货齐
    WAREHOUSE_SIGN_CONFIRM(51, "WAREHOUSE_SIGN_CONFIRM", "仓库已签收等待处理"),
    WAREHOUSE_GOODS_CONFIRM(52, "WAREHOUSE_GOODS_CONFIRM", "收货处理中"),
    WAREHOUSE_RECEIVE_CONFIRM(53, "WAREHOUSE_RECEIVE_CONFIRM", "等待货齐出库"),
    // 就绪待下发
    WAREHOUSE_TASK_WAITING(61, "WAREHOUSE_TASK_WAITING", "等待出库"),
    // 已下发
    WAREHOUSE_TASK_SEND(62, "WAREHOUSE_TASK_SEND", "仓库待处理"),
    WAREHOUSE_INTERCEPTION(63, "WAREHOUSE_INTERCEPTION", "仓库信用拦截"),

    WAREHOUSE_WAVE_CONFIRM(71, "WAREHOUSE_WAVE_CONFIRM", "仓库生成发货统计"),
    WAREHOUSE_PICKING(72, "WAREHOUSE_PICKING", "仓库拣货中"),
    WAREHOUSE_DELIVERY(81, "WAREHOUSE_DELIVERY", "仓库已出库待揽收"),



    TRANSPORT_COLLECTION(82, "TRANSPORT_COLLECTION", "承运商已揽收"),
    TRANSPORT_TRANSIT(83, "TRANSPORT_TRANSIT", "承运商运输中"),
    TRANSPORT_SENDING(84, "TRANSPORT_SENDING", "承运商派件中"),
    TRANSPORT_DELIVERED(91, "TRANSPORT_DELIVERED", "承运商已送达"),


    CANCEL(110, "CANCEL", "删单"),
    ;


    private Integer index;
    private String code;
    private String desc;

    OrderStatusItemEnum(Integer index, String code, String desc) {
        this.index = index;
        this.code = code;
        this.desc = desc;
    }

    public static OrderStatusItemEnum getEnumByCode(String code) {
        return Arrays.stream(OrderStatusItemEnum.values()).filter(Enum -> Enum.getCode().equals(code)).findFirst().orElse(null);
    }

    public static OrderStatusEnum getOrderStatus(OrderStatusItemEnum e){
        return  OrderStatusEnum.getEnumByIndex(e.getIndex()/10);
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
