package com.sales.ops.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum OrderStatusDetailEnum {
    CANCEL(0, "CANCEL", "已删单"),
    EXCEPTION(1, "EXCEPTION", "待解决"),

    CG_REQUEST(31, "CG_REQUEST", "请购处理"),
    CG_SENDPO(32, "CG_SENDPO", "业务已发单"),
    CG_RECEIVE(33, "CG_RECEIVE", "工厂已接单"),
    CG_PRODUCT(34, "CG_PRODUCT", "工厂已排产"),
    CG_DELIVERY(35, "CG_DELIVERY", "工厂运输中"),
    CG_CUSTOMS(36, "CG_CUSTOMS", "货物已报关"),

    DB_WAITING(41, "DB_WAITING", "等待出库"),
    DB_START(42, "DB_START", "开始出库"),
    DB_OPERATION(43, "DB_OPERATION", "出库中"),
    DB_PICK(43, "DB_PICK", "拣货完成"),
    DB_PACKAGE(44, "DB_PACKAGE", "包装完成"),
    DB_COLLECTING(45, "DB_COLLECTING", "包装完成"),
    DB_DELIVERED(46, "DB_DELIVERED", "装箱完成"),
    DB_DELIVERY(47, "DB_DELIVERY", "已出库"),

    OK_SIGNIN(51, "OK_SIGNIN", "到货签收"),
    OK_WAITING(51, "OK_WAITING", "入库货不齐"),  //等待中
    OK_READY(52, "OK_READY", "就绪"),//计算货齐了，没下发
    OK_PREPARE(53, "OK_PREPARE", "货齐待发货"), //已下发
    OK_INTERCEPT(60, "OK_INTERCEPT", "信用拦截"),//此状态已废弃
    WM_START(71, "WM_START", "发货统计生成完毕"), //组波次
    WM_OUTING(72, "WM_OUTING", "出库中"),
    WM_PICK(72, "WM_PICK", "拣货完成"),
    WM_PACKAGE(73, "WM_PACKAGE", "包装完成"),
    WM_DELIVERED(74, "WM_DELIVERED", "装箱完成"),
    WM_COLLECTING(75, "WM_COLLECTING", "待揽收"),

    WM_DELIVERY(81, "WM_DELIVERY", "已发货"),


    IPS_INVOICED(91,"IPS_INVOICED","已开票"),
    ;

    private Integer num;
    private String state;
    private String desc;

    OrderStatusDetailEnum(Integer num, String state, String desc) {
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

    public static OrderStatusDetailEnum getEnumByType(String type) {
        return Arrays.stream(OrderStatusDetailEnum.values()).filter(Enum -> Enum.getState().equals(type)).findFirst().orElse(null);
    }

    public static String getDescByState(String desc) {
        if (StringUtils.isBlank(desc)) {
            return null;
        }
        for (OrderStatusDetailEnum item : OrderStatusDetailEnum.values()) {
            if (item.getState().equals(desc)) {
                return item.getDesc();
            }
        }
        return null;
    }


    public static OrderStatusEnum getOrderStatusEnum(OrderStatusDetailEnum detail){
        int i = detail.getNum() / 10;
        return Arrays.stream(OrderStatusEnum.values()).filter(Enum -> Enum.getNum()==i).findFirst().orElse(null);
    }


}
