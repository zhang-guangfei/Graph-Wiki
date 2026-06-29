package com.smc.ops.delivery.model.enums;

/**
 * @Author lyc
 * @Date 2024/4/23 10:26
 * @Descripton TODO  0-预到货，1-已到货，2-已报关
 */
public enum GwStatusCodeEnum {

    ydh("0","预到货"),
    dh("1","已到货"),
    ybg("2","已报关");

    private String code;
    private String codeName;


    GwStatusCodeEnum(String code, String codeName) {
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
