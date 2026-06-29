package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/7/28 13:18
 * @Descripton TODO
 */
public enum  SampleBalAppTypeEnum {
    SYPMFSY("1","试用品免费试用"),
    SYPDZXS("2","试用品待转销售"),
    ZLZS("3","展览展示品"),
    PKBS("4","盘亏报损"),
    HWSH("41","货物损害"),
    HWDS("42","货物丢失"),
    CTC("5","CTC实验品"),
    GZTDP("6","故障替代品"),
    WX("7","维修品");
    private String code;
    private String codeName;

    SampleBalAppTypeEnum(String code, String codeName) {
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



    public static String getCodeName(String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        for (SampleBalAppTypeEnum item : SampleBalAppTypeEnum.values()) {
            if (item.code.equals(code)) {
                return item.getCodeName();
            }
        }
        return code;
    }

}
