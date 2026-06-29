package com.smc.smccloud.core.model.enums;

/**
 * @author C18117
 * @title: ReturnDutyTypeEnum
 * @date 2022/11/10 10:29
 */
public enum  ReturnDutyTypeEnum {
    YH(1,"用户"),
    JXS(2,"经销商"),
    DLD(3,"代理店"),
    YYS(4,"营业所"),
    GC(5,"工厂"),
    JP(6,"日本"),
    QT(7,"其它");

    private int code;
    private String codeName;

    ReturnDutyTypeEnum(int code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public int getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static String getNameByCode(Integer delStatus) {
        if (delStatus == null) {
            return null;
        }
        for (ReturnDutyTypeEnum item : ReturnDutyTypeEnum.values()) {
            if (item.getCode() == delStatus) {
                return item.getCodeName();
            }
        }
        return null;
    }
}
