package com.sales.ops.dto.prepareOrder;

public enum HandleWayEnum {

    sp("0", "索赔"),

    cg("1", "购买");

    private String code;

    private String codeName;

    private HandleWayEnum(String code, String codeName) {
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
        for (HandleWayEnum value : HandleWayEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getCodeName();
            }
        }
        return null;
    }
}
