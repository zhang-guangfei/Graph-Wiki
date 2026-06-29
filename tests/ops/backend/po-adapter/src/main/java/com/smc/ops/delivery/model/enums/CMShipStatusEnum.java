package com.smc.ops.delivery.model.enums;

/**
 * @author ：C14717
 * @version: $
 * @description：
 * @date ：Created in 2024/2/27 10:00
 */
public enum CMShipStatusEnum {

    NIL("","不处理"),
    F("F","作业未进行"),
    W("W","出口等待"),
    C("C","捆包作业中"),;

    private String code;
    private String codeName;

    CMShipStatusEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static String  getCodeByCodeName(String codeName) {

        for (CMShipStatusEnum obj : CMShipStatusEnum.values()) {
            if (obj.codeName.equals(codeName)) {
                return obj.getCode();
            }
        }
        return CMShipStatusEnum.NIL.getCode();
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
