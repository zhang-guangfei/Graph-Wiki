package com.sales.ops.enums;

import java.util.Arrays;

/**
 * 采购模块触发的事件
 *
 * @author C12961
 * @date 2022/4/16 14:37
 */
public enum PurchaseOrderEventEnum implements EventTypeEnum {

    CANCEL_PURCHASE("CANCEL_PURCHASE", "取消采购单"),
    TRANSFER_PLAN("TRANSFER_PLAN", "调库计划"),
    PURCHASE_REPLY_DATE("PURCHASE_REPLY_DATE", "采购单返信变更"),
    PURCHASE_DELAY_DELIVERY("PURCHASE_DELAY_DELIVERY", "采购单货期延期"),
    ;
    private final String code;
    private final String desc;


    PurchaseOrderEventEnum(String event, String desc) {
        this.code = event;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static PurchaseOrderEventEnum getEventEnum(String event) {
        return Arrays.stream(PurchaseOrderEventEnum.values()).filter(Enum -> Enum.getCode().equals(event)).findFirst().orElse(null);
    }


}
