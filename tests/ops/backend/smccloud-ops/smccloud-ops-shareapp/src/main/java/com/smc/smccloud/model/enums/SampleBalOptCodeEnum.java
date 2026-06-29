package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/8/3 18:06
 * @Descripton TODO
 */
public enum  SampleBalOptCodeEnum {
    DJZ("1","待结转"),
    DGH("2","待归还"),
    DZC("3","待转出"),
    YWJ("4","已完结"),
    ZXS("5","已转销售"),
    NHJZ("6","已内耗结转"),
    YSZ("7","已销账"),
    YZCPD("8","已转成盘点"),
    CANCEL("9","取消");
    private String code;
    private String codeName;

    SampleBalOptCodeEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static String getCodeName(String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        for (SampleBalOptCodeEnum item : SampleBalOptCodeEnum.values()) {
            if (item.code.equals(code)) {
                return item.getCodeName();
            }
        }
        return "";
    }
}
