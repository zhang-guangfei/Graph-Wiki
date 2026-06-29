package com.sales.ops.enums;

import java.util.Arrays;

/**
 * 订单状态变更事件
 *
 * @author C12961
 * @date 2022/2/21 15:45
 */
public enum CustomerOrderEventEnum implements EventTypeEnum {

    CANCEL_ORDER("CANCEL_ORDER", "取消订单"),
    ORDER_ADJUST("ORDER_ADJUST", "转订"),
    ORDER_ADJUST_RESULT("ORDER_ADJUST_RESULT", "转订"),
    ORDER_FINISH("ORDER_FINISH", "完纳"),
    ORDER_ALLOT("ORDER_ALLOT", "订单分配库存"),
    ORDER_ALLOT_RESULT("ORDER_ALLOT_RESULT", "订单分配库存"),
    PURCHASE_RESET("PURCHASE_RESET", "采购转订"),
    REQUEST_PREPROCESS("REQUEST_PREPROCESS", "请购预处理"),
    INTERCEPT_FOR_REQUEST("INTERCEPT_FOR_REQUEST", "请购拦截"),
    RELEASE_FOR_REQUEST("RELEASE_FOR_REQUEST", "请购拦截放行"),
    PURCHASE_SENT_PO("PURCHASE_SENT_PO", "采购单发送"),
    PURCHASE_RECEIVE("PURCHASE_RECEIVE", "采购单接单"),
    PURCHASE_UPDATE("PURCHASE_UPDATE", "采购单更新"),
    INVOICE_IMPORT("INVOICE_IMPORT", "发票导入"),
    INVOICE_CONFIRM("INVOICE_CONFIRM", "发票确认"),
    UPDATE_WAREHOUSE("UPDATE_WAREHOUSE", "修改发货仓"),
    SPLIT_STATUS("SPLIT_STATUS", "随发分批拆分"),

    INVOICE_SIGN_IN("INVOICE_SIGN_IN", "发票签收"),
    CONFIRM_GOODS("CONFIRM_GOODS", "到货确认"),

    DELIVERY_PREPARE("DELIVERY_PREPARE", "调拨下发"),
    DELIVERY_START("DELIVERY_START", "调拨开始出库"),
    DELIVERY_OPERATION("DELIVERY_OPERATION", "调拨出库中"),
    DELIVERY_FINISHED("DELIVERY_FINISHED", "调拨已出库"),
    DELIVERY_HANDCONFIRM("DELIVERY_HANDCONFIRM", "调拨已发运截止"),

    WM_READY("WM_READY", "就绪"),
    INTERCEPT_FOR_CREDIT("INTERCEPT_FOR_CREDIT", "信用拦截"),
    RELEASE_FOR_CREDIT("RELEASE_FOR_CREDIT", "信用拦截释放"),

    WM_INSTORAGE("WM_INSTORAGE", "已入库"),
    WM_PREPARE("WM_PREPARE", "准备中"),
    WM_START("WM_START", "开始出库"),
    WM_RELEASE_OPERATION("WM_RELEASE_OPERATION", "释放作业"),
    WM_OUTING("WM_OUTING", "出库中"),
    WM_OUT("WM_OUT", "已出库"),
    IPS_INVOICED("IPS_INVOICED", "已开票"),
    IPS_INVOICE_DEL("IPS_INVOICE_DEL", "已开票"),
    IPS_INVOICE_BACK("IPS_INVOICE_BACK", "已开票"),
    IPS_RETURN("IPS_RETURN", "已退货"),
    TMS_CALLBACK("TMS_CALLBACK", "tms回调"),

    ;

    private final String code;
    private final String desc;


    CustomerOrderEventEnum(String event, String desc) {
        this.code = event;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static CustomerOrderEventEnum getEventEnum(String event) {
        return Arrays.stream(CustomerOrderEventEnum.values()).filter(Enum -> Enum.getCode().equals(event)).findFirst().orElse(null);
    }


}
