package com.sales.ops.enums;


/**
 * describe : 出库回传确认失败
 * @author C14717
 * @date 2022/10/24
 */

/**

 * doConfirm异常
 * 1.参数错误
 * 2.无此法货单
 * 3.发货单已完成，不允许继续过账
 * 4.发货单数量大于应发数量，不允许继续
 * 5.无此加工单
 * 6.出库确认回传成功，无库存关联关系
 * 7.出库确认成功，有关联关系无法扣库存，a.在途库存；b.并发扣库存异常；
 */
public enum DoConfirmErrorCodeEnum {
    SYSTEM_ERR("0","系统异常"),
    PARAMETER("1","参数错误"),
    NONEXISTENT_DO("2","指令不存在"),
    COMPLETED("3","发运单已完成不允许继续过账"),
    ABNORMAL_ISSUE_QUANTITY("4","发货单数量大于应发数量，不允许继续"),
    NONEXISTENT_PCO("5","指令不存在"),
    REPEAT_CALL("6","重复调用接口"),
    NON_RELATION("7","出库确认回传成功，无库存关联关系"),
    HAVE_RELATION("8","出库确认成功，有关联关系无法扣库存，a.在途库存；b.并发扣库存异常"),
    WAREHOUSE_DIFF("9","出库库存不一致"),
    DEL_PO("10","删采购关联指令已下发"),
    DEL_PO_NOT_MOVE("11","删采购无关联在途表"),
    DEL_PO_HAVE_NOT_P("12","删采购存在非生成在途"),
    DEL_PO_NOT_ITEM_INV("13","删采购无关联关系"),
    DEL_ReqPO_HAVE_ITEM_INV("14","请购状态删除采购有关联关系"),
    DEL_ReqPO_SIZE_ONE("15","请购状态删除请购参数不等于1"),
    UPDATE_DO_FAIR("16","并发异常，更新do失败"),
    SPECAIL_MODELNO("17","特殊型号出需出指定仓"),
    ZHCK_MORE_COMPANY("18","组换出库多个子公司"),
    ;

    private String code;
    private String desc;


    DoConfirmErrorCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
