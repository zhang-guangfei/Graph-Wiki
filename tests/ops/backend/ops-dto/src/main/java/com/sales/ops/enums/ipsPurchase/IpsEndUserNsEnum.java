package com.sales.ops.enums.ipsPurchase;

/**
 * @author ：B91717
 * ips采购单发送，南北方客户的区分枚举类
 */
public enum IpsEndUserNsEnum {

    SOUTH("0","南方订单"),
    NORTH("1","北方订单");


    private String code;
    private String codeName;

    IpsEndUserNsEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }


    public static IpsEndUserNsEnum getEnumByCode(String code) {
        for (IpsEndUserNsEnum codeEnum : values()) {
            if (codeEnum.code.equals(code)) {
                return codeEnum;
            }
        }
        return null;
    }

    public static String getCodeByDesc(String desc) {
        if (desc == null || desc == "") {
            return null;
        }
        for (IpsEndUserNsEnum ipsEndUserNsEnum : IpsEndUserNsEnum.values()) {
            if (ipsEndUserNsEnum.getCodeName().equals(desc)) {
                return ipsEndUserNsEnum.getCode();
            }
        }
        return IpsEndUserNsEnum.SOUTH.getCode();
    }



}
