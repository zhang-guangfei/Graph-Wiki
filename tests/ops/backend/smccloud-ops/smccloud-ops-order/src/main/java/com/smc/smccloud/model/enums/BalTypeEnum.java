package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/7/28 12:08
 * @Descripton TODO
 */

public enum BalTypeEnum {

    zp("101","赠品"),
    YP("102","样品"),
    BF("103","已报废处理"),
    WX("104","维修品"),
    ZS("201","展览展示品"),
    XSKP("301","转销售开票"),
    LPFH("401","良品返回");

    private String code;
    private String codeName;

    BalTypeEnum(String code, String codeName) {
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


    public static String getCodeName (String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        for (BalTypeEnum item : BalTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return "";
    }


}
