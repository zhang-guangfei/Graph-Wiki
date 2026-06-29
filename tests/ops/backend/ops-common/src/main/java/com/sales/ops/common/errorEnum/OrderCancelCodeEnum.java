package com.sales.ops.common.errorEnum;

import com.sales.ops.common.opsexception.ErrorCode;

/**
 * @author C12961
 * @date 2022/10/24 14:03
 */
public enum OrderCancelCodeEnum implements ErrorCode {

    // 处理成功的结果
    SUCCESS_CANCEL(1, "订单取消成功"),//=>完成
    AUTO_SUCCESS_CANCEL(2, "自动取消成功"),//=>完成
    SUCCESS_CANCEL_AND_LOW_RISK(3, "自动取消成功，采购单未删除"),//=>完成
    SUCCESS_CANCEL_AND_FAIL_TRANS(4, "自动取消成功，未生成调库计划"),//=>完成

    // 处理失败的结果
    STATUS_NOT_ALLOWED(-1, "订单当前状态不允许取消订单"), //=>不能变更
    WM_NOT_ALLOWED(-2, "物流不允许取消"), //=>不能变更
    PURCHASE_NOT_ALLOWED(-3, "自动取消失败，需要人工确认"), //=>待处理


    // 数据异常
    NOT_RECEIVED(5001, "订单未接入"), // 没有rcvdetail
    SEARCH_PO_FAILURE(5002, "查询采购单失败"), // 没有po

    // 校验
    ALREADY_DEL(6001,"订单已删单"),
    STATUS_NOT_DEL(6001,"状态不可进行删单"),
    ;

    OrderCancelCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }




}
