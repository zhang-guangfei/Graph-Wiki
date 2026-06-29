package com.smc.smccloud.core.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2022/5/7 13:23
 * @Descripton TODO
 */
public enum  InvoiceTypeEnum {

    zzsfp("1","增值税发票"),
    ptfp("2","普通发票");

    private String code;

    private String name;

    InvoiceTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getCodeName(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (InvoiceTypeEnum item :InvoiceTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getName();
            }
        }
        return null;

    }


}
