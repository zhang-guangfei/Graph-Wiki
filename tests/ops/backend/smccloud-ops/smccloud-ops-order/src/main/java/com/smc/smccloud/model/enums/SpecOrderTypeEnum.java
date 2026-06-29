package com.smc.smccloud.model.enums;

/**
 * @Author lyc
 * @Date 2024/5/9 13:44
 * @Descripton TODO
 */
public enum SpecOrderTypeEnum {
    ybmy(12,"一般贸易订单"),
    dr(24,"DR订单"),
    cr(25,"CR订单");
    private int code;
    private String codeName;

    SpecOrderTypeEnum(int code, String codeName) {
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

    public static String getCodeName (int code) {
        for (SpecOrderTypeEnum e : SpecOrderTypeEnum.values()) {
            if (e.getCode() == code) {
                return e.getCodeName();
            }
        }
        return String.valueOf(code);
    }

}
