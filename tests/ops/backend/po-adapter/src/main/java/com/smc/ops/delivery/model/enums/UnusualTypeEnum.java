package com.smc.ops.delivery.model.enums;

/**
 * @Author lyc
 * @Date 2024/4/22 8:27
 * @Descripton TODO
 */
public enum UnusualTypeEnum {
    jdq(0,"接单前异常"),
    jdh(1,"接单后异常");

    private int code;

    private String codeName;

    UnusualTypeEnum(int code, String codeName) {
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
