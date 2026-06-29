package com.smc.smccloud.model.enums;

/**
 * @Author lyc
 * @Date 2024/1/11 16:41
 * @Descripton TODO
 */
public enum  CallBackSMSApplyTypeEnum {
    INSTOCK_STRATEGY (1,"战略在库申请"),
    ORDER_MODIFY(2,"订单修改"),
    PURCHASE_ORDER_CHANGE(3,"采购单变更"),
    SHIKOMI_REMAIN_WARNING (4,"Shikomi残数警告信息"),
    SHIKOMI_QTY_WARNING(5,"Shikomi警告数维护结果"),
    SAMPLE_CARRY_OVER(6,"样品结转"),
    ORDER_RELEASE(7,"信用拦截"),
    ORDER_DELETE(8,"删单申请"),
    BATCH_UP_DLV(9,"批量变更交货期"),
    INQA_BACK(10,"inqA问询回复"),
    INQB_BACK(11,"inqB问询回复"),

    PRE_ACCOUNT(12,"先行在库预决算"),

    SAMPLE_BAL_SOURCEID (13,"样品结转来源ID"),
    NOTIFY_SHIP_PLAN (14,"通知发货计划");

    private int code;
    private String codeName;

    CallBackSMSApplyTypeEnum(int code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
