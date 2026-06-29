package com.sales.ops.serviceimpl.ips;

import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public enum IPSOutBoundTypeEnum {

    OTHER("2","采购"),

    OUT_BOUND("0","在库"),
    ALIVE("1","生产"),;

    private String  code;
    private String codeName;

    IPSOutBoundTypeEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static String getCodeByCodeName(String codeName) {
        if(StringUtils.isBlank(codeName)){
            return null;
        }
        for (IPSOutBoundTypeEnum obj : IPSOutBoundTypeEnum.values()) {
            if (obj.codeName.equals(codeName)) {
                return obj.getCode();
            }
        }
        return IPSOutBoundTypeEnum.OTHER.getCode();
    }

    public static String getNameByCode(String code) {
        if(StringUtils.isBlank(code)){
            return null;
        }
        for (IPSOutBoundTypeEnum obj : IPSOutBoundTypeEnum.values()) {
            if (obj.code.equals(code)) {
                return obj.getCodeName();
            }
        }
        return IPSOutBoundTypeEnum.OTHER.getCodeName();
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
