package com.smc.smccloud.core.model.enums;

/**
 * @Author lyc
 * @Date 2023/7/27 8:42
 * @Descripton TODO  处理结果：0申请成功；1申请失败；2删除成功；3删除失败
 */
public enum CancelOrderToSalesStatus {
    apply_success("0","申请成功"),
    apply_fail("1","申请失败"),
    del_success("2","删除成功"),
    del_fail("3","删除失败"),
    second_process("4","二次审批"),
    confirming("5","供应商确认中");

    private String code;

    private String codeName;

    CancelOrderToSalesStatus(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
