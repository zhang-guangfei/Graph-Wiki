package com.smc.smccloud.model.preaccount;

/**
 * @Author lyc
 * @Date 2024/7/17 17:44
 * @Descripton TODO
 */
public enum PreAccountOrderSalesCallBackStatusEnum {
    dsp(0,"待审批"),
    sptg(1,"审批通过"),
    zf(2,"作废"),
    bh(3,"驳回");

    private int code;
    private String codeName;

    PreAccountOrderSalesCallBackStatusEnum(int code, String codeName) {
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
