package com.sales.ops.enums.ipsPurchase;

import java.util.Arrays;
import java.util.List;

/**
 * @author ：B91717
 * ips采购单发送，公共的枚举类
 */
public enum IpsPurchaseSendSwitchEnum {

    OLD("0","只发老系统"),
    NEW("1","只发送新系统"),
    ALL("2","新老同时发");


    private String code;
    private String codeName;

    IpsPurchaseSendSwitchEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }


    public static IpsPurchaseSendSwitchEnum getEnumByCode(String code) {
        for (IpsPurchaseSendSwitchEnum codeEnum : values()) {
            if (codeEnum.code.equals(code)) {
                return codeEnum;
            }
        }
        return null;
    }

    public static boolean isValidCode(String code) {
        IpsPurchaseSendSwitchEnum typeEnum = IpsPurchaseSendSwitchEnum.getEnumByCode(code);
        List<IpsPurchaseSendSwitchEnum> dbList = Arrays.asList(OLD, NEW, ALL);
        return dbList.contains(typeEnum);
    }


}
