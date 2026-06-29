package com.smc.smccloud.model.enums;

/**
 * @Author lyc
 * @Date 2024/5/9 13:42
 * @Descripton TODO
 */
public enum SpecOrderStatusEnum {
    edit(1, "编辑中"),
    alreadyCreateOrder(2, "已生成订单");

    private int code;
    private String codeName;

    SpecOrderStatusEnum(int code, String codeName) {
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
        for (SpecOrderStatusEnum value : SpecOrderStatusEnum.values()) {
            if (value.getCode() == code) {
                return value.getCodeName();
            }
        }
        return String.valueOf(code);
    }
}
