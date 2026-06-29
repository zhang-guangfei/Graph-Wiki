package com.sales.ops.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum OrderStatusEnum {
    CANCEL(0, "CANCEL", "已删单"),
    EXCEPTION(1, "EXCEPTION", "待处理"),

    CG_INTERCEPT(2, "CG_INTERCEPT", "采购拦截"),
    CG(3, "CG", "对外采购"),
    DB(4, "DB", "仓间调拨"),

    OK(5, "OK", "等待出库"),
    OK_INTERCEPT(6, "OK_INTERCEPT", "信用拦截"),
    WM(7, "WM", "出库处理"),
    OUT(8, "OUT", "已发货"),
    FINISH(9, "FINISH", "完成");

    private Integer num;
    private String state;
    private String desc;

    OrderStatusEnum(Integer num, String state, String desc) {
        this.num = num;
        this.state = state;
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getNum() {
        return num;
    }

    public static OrderStatusEnum getEnumByType(String type) {
        return Arrays.stream(OrderStatusEnum.values()).filter(Enum -> Enum.getState().equals(type)).findFirst().orElse(null);
    }


    public static RcvOrderStatusEnum getOrderStatusEnum(OrderStatusEnum doState) {
        if (doState == OrderStatusEnum.CANCEL) {
            return RcvOrderStatusEnum.CANCEL;
        }
        if (doState == OrderStatusEnum.EXCEPTION) {
            return RcvOrderStatusEnum.RESOLVE;
        }
        if (doState == OrderStatusEnum.CG_INTERCEPT) {
            return RcvOrderStatusEnum.INTCP;
        }
        if (doState == OrderStatusEnum.CG) {
            return RcvOrderStatusEnum.CGING;
        }
        if (doState == OrderStatusEnum.DB) {
            return RcvOrderStatusEnum.DBING;
        }
        if (doState == OrderStatusEnum.OK_INTERCEPT) {
            return RcvOrderStatusEnum.CREDIT;
        }
        if (doState == OrderStatusEnum.OK) {
            return RcvOrderStatusEnum.WAITCK;
        }
        if (doState == OrderStatusEnum.WM) {
            return RcvOrderStatusEnum.CKING;
        }
        if (doState == OrderStatusEnum.OUT) {
            return RcvOrderStatusEnum.CKED;
        }

        return null;
    }

    public static String getDescByState(String state) {
        if (StringUtils.isBlank(state)) {
            return null;
        }
        for (OrderStatusEnum item : OrderStatusEnum.values()) {
            if (item.getState().equals(state)) {
                return item.getDesc();
            }
        }
        return null;
    }

}
