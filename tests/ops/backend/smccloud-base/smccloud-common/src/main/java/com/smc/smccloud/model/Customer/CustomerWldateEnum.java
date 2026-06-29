package com.smc.smccloud.model.Customer;


/**
 * @Author lyc
 * @Date 2022/12/7 9:11
 * @Descripton TODO
 */
public enum  CustomerWldateEnum {
    SENDORDERBYTIME(1,"指定时间发货")
    ;
    private Integer code;
    private String codeName;

    CustomerWldateEnum(Integer code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public Integer getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static String getCodeNameByCode(Integer code) {
        if (code == null) {
            return "";
        }
        for (CustomerWldateEnum item : CustomerWldateEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return "";

    }

}
