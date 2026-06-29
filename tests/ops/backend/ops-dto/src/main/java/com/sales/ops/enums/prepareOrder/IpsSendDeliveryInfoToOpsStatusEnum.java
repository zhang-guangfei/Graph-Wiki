package com.sales.ops.enums.prepareOrder;


public enum IpsSendDeliveryInfoToOpsStatusEnum {

    P("p","接单失败"),
    D("D","供应商受注停止"),
    F("F","供应商的订单已接单"),
    I("I","供应商的订单已分配"),
    N("N","供应商已返信"),
    PU("PU","已备库"),
    PA("PA","待清算"),
    PE("PE","已清算");


    private String code;

    private String codeName;

    IpsSendDeliveryInfoToOpsStatusEnum(String code, String codeName) {
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

    public static String getCodeNameByCode(String code) {
        for (IpsSendDeliveryInfoToOpsStatusEnum value : IpsSendDeliveryInfoToOpsStatusEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getCodeName();
            }
        }
        return String.valueOf(code);
    }

}
