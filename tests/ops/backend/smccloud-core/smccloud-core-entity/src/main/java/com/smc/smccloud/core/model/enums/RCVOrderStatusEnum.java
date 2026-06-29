package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: B90034
 * Date: 2022-04-01 12:12
 * Description: 接单状态
 */
public enum RCVOrderStatusEnum {

    WAITING_PROCESS(1, "接单未处理"),
    IN_PURCHASE(2, "对外采购"),
    PURCHASE_INTERCEPT(3, "采购拦截"),
    IN_TRANSFER(4, "仓间调拨"),
    WAITING_DELIVERY(5, "等待出库"),
    IN_DELIVERY(6, "出库处理"),
    DELIVERED(7, "已发货"),
    RETURN(8, "已退货"),
    CANCEL(9, "订单删除"),
    ERROR(10, "异常订单"),
    NO_PROCESS(11, "暂不处理"),
    CREDIT_INTERCEPT(12, "信用拦截"),
    INVOICE(13,"已开票"),
    RESOLVE(14,"二次处理");

    private Integer code;
    private String name;

    RCVOrderStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getName(Integer code) {
        if (code == null) {
            return "";
        }
        for (RCVOrderStatusEnum value : RCVOrderStatusEnum.values()) {
            if (value.code.compareTo(code) == 0) {
                return value.name;
            }
        }
        return "";
    }


    public static List<Integer> getAllCodes() {
        List<Integer> list = new ArrayList<>();
        for (RCVOrderStatusEnum value : RCVOrderStatusEnum.values()) {
            Integer code = value.getCode();
            list.add(code);
        }
        return list;
    }

}
