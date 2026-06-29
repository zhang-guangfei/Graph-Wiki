package com.smc.smccloud.core.model.enums;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/7/20 8:51
 * @Descripton TODO
 */

public enum  TradeCompanyIdEnum {
    ZDH("CN0","自动化","CN"),
    BF("CN0-B","北分","BJ"),
    GF("CN0-G","广分","GZ"),
    SF("CN0-S","上分","SH");


    private String code;
    private String name;
    private String alias;

    TradeCompanyIdEnum(String code, String name, String alias) {
        this.code = code;
        this.name = name;
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getCodeName (String code) {
        System.out.println("code = " + code + "|");
        if (StringUtils.isBlank(code)) {
            return "CN";
        }
        for (TradeCompanyIdEnum item : TradeCompanyIdEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getAlias();
            }
        }
        return "CN";
    }

    public static String getName (String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        for (TradeCompanyIdEnum item : TradeCompanyIdEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getName();
            }
        }
        return null;
    }

}
