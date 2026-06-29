package com.smc.smccloud.core.model.enums;

/**
 * @Author lyc
 * @Date 2023/8/23 13:09
 * @Descripton TODO
 */
public enum  OpsCommonoSalesApplyTypeEnum {
    zlzk(1,"战略在库申请"),
    order_modify(2,"订单修改"),
    cg_modify(3,"采购单变更"),
    shikomi_modify(5,"shikomi变更")
    ;
    private int code;
    private String codeName;

    OpsCommonoSalesApplyTypeEnum(int code, String codeName) {
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
