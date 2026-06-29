package com.sales.ops.serviceimpl.dispatch.dodispatch.domain.goodsconfirm;

public enum GoodsConfirmReason {
    SAME_RO_WITH_OTHER_MOVE(1, "与其他move的ro相同"),
    NOT_MASTER(2, "入分库"),
    MERGE_PURCHASE(3, "合并采购"),
    NOT_FULL_PRE_QTY(4, "库存预约数不满"),
    NO_RELATION(5, "无关联的物流出库指令"),
    MULTIPLE_DO_REPATION(6, "多条do预约关系"),
    MULTIPLE_PCO_REPATION(7, "多条pco预约关系"),
    BOTH_DO_AND_PCO_REPATION(8, "多条do+pco预约关系"),
    DBCK_NOT_ENOUGH(9, "调拨出库货不齐"),
    DBCK_ENOUGH_CROSS(10, "调拨出库 【货齐越库】"),
    JYCK_NOT_ENOUGH(11, "交易出库货不齐"),
    JYCK_NOT_ARRIVEL_DELIVERY_DATE(12, "未到交货期 【货齐预约】"),
    JYCK_CREDIT_INTERCEPTED(13, "信用拦截 【货齐预约】"),
    JYCK_2_ENOUGH_CROSS(14, "交易出库-随发分批 【货齐越库】"),
    JYCK_0_MULTIPLE_BATCHES_ENOUGH(15, "交易出库-随发 多批到货"),
    JYCK_0_ENOUGH_CROSS(16, "交易出库-随发 【货齐越库】"),
    JYCK_13_MULTIPLE_BATCHES_ENOUGH(17, "交易出库-单仓/多仓 多批到货"),
    JYCK_13_ENOUGH_CROSS(18, "交易出库-单仓/多仓 【货齐越库】"),
    PCO_NOT_ENOUGH(19, "加工单 货不齐"),
    PCO_NOT_ARRIVEL_DELIVERY_DATE(20, "未到交货期 【货齐预约】"),
    PCO_CREDIT_INTERCEPTED(21, "信用拦截 【货齐预约】"),
    PCO_02_ENOUGH_CROSS(22, "加工单-随发/分批 【货齐越库】"),
    PCO_13_ENOUGH_PREPARE(23, "加工单-单仓/多仓 【货齐预约】"),
    ;


    private Integer code;
    private String desc;

    GoodsConfirmReason(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
