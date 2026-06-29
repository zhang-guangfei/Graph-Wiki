package com.smc.smccloud.model.enums;

/**
 * @Author lyc
 * @Date 2023/8/17 12:19
 * @Descripton TODO
 */
public enum  PdRedisStatusEnum {
    canhand("0","可执行 "),
    handing("1","正在执行中 "),
    alreadyHand("2","已生成盘点票 "),
    notHandToArriveNotIn("4","未进行到货未入数据确认 "),
    alreadyHandToSureData("5","已盘点形式数据确认 ")
    ;

    private String code;
    private String codeName;

    PdRedisStatusEnum(String code, String codeName) {
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
