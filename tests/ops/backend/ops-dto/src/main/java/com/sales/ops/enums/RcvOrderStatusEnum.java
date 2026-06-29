package com.sales.ops.enums;

import com.sales.ops.db.entity.RcvStatusConfig;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author ：c02483
 * @date ：Created in 2021/11/17 18:58
 * @description：客户订单状态
 */
public enum RcvOrderStatusEnum {
    INIT(1, "接单未处理"),
    CGING(2, "对外采购"),
    INTCP(3, "采购拦截"),
    DBING(4, "仓间调拨"),
    WAITCK(5, "等待出库"),
    CKING(6, "出库处理"),
    CKED(7, "已发货"),
    RETURN(8, "已退货"),
    CANCEL(9, "订单删除"),
    EXCEPT(10, "异常订单"),
    UNDEAL(11, "暂不处理"),
    CREDIT(12, "信用拦截"),
    INVOICE(13, "已开票"),
    RESOLVE(14, "二次处理中"),
    HISTORYDO(-1, "历史过程订单待处理"),
    HISTORYSTATUS(-2, "历史过程订单待处理状态"),
    ORDERSTATUSFAIL(-3, "订单状态生成失败"),
    HISTORYFAIL(-4, "历史过程订单生成失败"),
    ;


    private short type;
    private String desc;

    RcvOrderStatusEnum(int type, String desc) {
        this.type = (short) type;
        this.desc = desc;
    }

    public short getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static RcvOrderStatusEnum getEnumByType(int type) {
        return Arrays.stream(RcvOrderStatusEnum.values()).filter(Enum -> Enum.getType() == type).findFirst().orElse(null);
    }

}
