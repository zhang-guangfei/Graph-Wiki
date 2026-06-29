package com.smc.smccloud.core.model.enums;

/**
 * @author edp04
 * @title: TransFromTypeEnum
 * @date 2022/08/18 11:00
 */
public enum TransFromTypeEnum {

    binbuku(1, "bin补库"),
    diaoku(2, "调库申请"),
    xianxingzaiku(3, "先行在库申请"),
    weituozaiku(4, "服务备库");


    private Integer code;
    private String codeName;

    TransFromTypeEnum(Integer code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public Integer getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static String getName (Integer code) {
        if(code == null) {
            return null;
        }
        for (TransFromTypeEnum item : TransFromTypeEnum.values()) {
            if (item.getCode() == code) {
                return item.codeName;
            }
        }
        return null;
    }

    public static Integer getCodeByName (String name) {
        for (TransFromTypeEnum item : TransFromTypeEnum.values()) {
            if (item.getCodeName().equals(name)) {
                return item.code;
            }
        }
        return null;
    }
}
