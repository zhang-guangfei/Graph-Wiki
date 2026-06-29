package com.smc.ops.delivery.model.enums;

/**
 * @author ：C14717
 * @version: $
 * @description：制造出库区分
 * @date ：Created in 2024/2/27 10:26
 */
public enum CMOutBoundTypeEnum {

    OTHER("","其他"),

    OUT_BOUND("0","在库出荷"),
    ALIVE("1","现场"),;

    private String  code;
    private String codeName;

    CMOutBoundTypeEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static String getCodeByCodeName(String codeName) {

        for (CMOutBoundTypeEnum obj : CMOutBoundTypeEnum.values()) {
            if (obj.codeName.equals(codeName)) {
                return obj.getCode();
            }
        }
        return CMOutBoundTypeEnum.OTHER.getCode();
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
