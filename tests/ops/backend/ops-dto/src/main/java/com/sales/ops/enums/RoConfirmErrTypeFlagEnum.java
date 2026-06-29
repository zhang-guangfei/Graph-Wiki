package com.sales.ops.enums;

import java.util.Arrays;

/**
 * @author ：B28029
 * @date ：Created in 2022/10/25 10:58
 * @description：入库扫描确认失败标识
 */
public enum RoConfirmErrTypeFlagEnum {
    PARAM_ERROR("1", "参数错误"),
    NO_RO_ERROR("2", "无此收货单据"),
    RO_FINISH_ERROR("3", "已完成收货"),
    WAREHOUSE_ERROR("4", "签收仓错误"),
    EXCESS_ERROR("5", "超额收货"),
    NO_SIGN_ERROR("6", "发票未签收"),
    NO_BARCODE_ERROR("7", "无箱码数据"),
    BARCODE_QTY_UNEQUAL_ERROR("8", "单据数量与箱码数量不符"),
    MOVE_QTY_UNEQUAL_ERROR("9", "单据数量与在途数量不符"),
    PRE_QTY_EXCESS_ERROR("10", "在途数据异常，预约数大于在途数量"),
    NO_DO_ERROR("11", "无发运单据"),
    NO_MOVE_ERROR("12", "无在途数据"),
    TASK_DUPLICATION("13", "task重复"),
    UPDATE_ROSTATUS("14", "状态更新异常"),
    OTHER_ERROR("99", "其他错误");

    private String flag;
    private String desc;

    RoConfirmErrTypeFlagEnum(String flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

    public String getFlag() {
        return flag;
    }

    public static RoConfirmErrTypeFlagEnum getRoConfirmErrTypeFlagEnum(String event) {
        return Arrays.stream(RoConfirmErrTypeFlagEnum.values()).filter(Enum -> Enum.getFlag().equals(event)).findFirst().orElse(null);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}