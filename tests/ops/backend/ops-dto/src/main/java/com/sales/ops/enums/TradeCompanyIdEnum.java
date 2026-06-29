package com.sales.ops.enums;

public enum TradeCompanyIdEnum {

    /**
     * B91717
     * 配置交易主体对应的客户代码
     * 广州表制造发单使用
     */
    CN0("CN0", "C1D7V"),
    CNS("CNS", "C1D71"),
    CNC("CNC", "C1D70"),
    CNM("CNM", "A0000"),
    CNT("CNT", "C07GR"),
    CNOB("CN0-B", "C1E7H"),
    CN0S("CN0-S", "C1E7G"),
    CNG("CN0-G", "C1E7F"),
    CN0G("CNG", "C1D72"),
    CN0J("CN0-J", "C3DSO");
    private String code;
    private String customerno;

    public String getCustomerno() {
        return customerno;
    }

    public String getCode() {
        return code;
    }


    private TradeCompanyIdEnum(String code, String customerno) {
        this.code = code;
        this.customerno = customerno;
    }

    public static String getCustomerno(String code) {
        for (TradeCompanyIdEnum c : TradeCompanyIdEnum.values()) {
            if (c.getCode().equals(code)) {
                return c.customerno;
            }
        }
        return null;
    }


}
