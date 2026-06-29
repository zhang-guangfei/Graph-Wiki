package com.smc.smccloud.core.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lyc
 * @Date 2022/5/11 8:58
 * @Descripton TODO
 */

public enum ChinaSuppplierEnum {

    CM("CM","SMC（北京)"),
    CN("CN","SMC（中国）"),
    GN("GN","国内采购"),
    GZ("GZ","SMC（广州）"),
    YZ("YZ","SMC（制造六课）"),
    CT("CT","SMC (天津)"),
    TZ("TZ","SMC (上海)"),
    ;

    private String code;
    private String codeName;

    ChinaSuppplierEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }

    public static List<String> getAllCNSupplier() {
        List<String> list = new ArrayList<>();
        for (ChinaSuppplierEnum item : ChinaSuppplierEnum.values()) {
            list.add(item.getCode());
        }
        return list;
    }

}
