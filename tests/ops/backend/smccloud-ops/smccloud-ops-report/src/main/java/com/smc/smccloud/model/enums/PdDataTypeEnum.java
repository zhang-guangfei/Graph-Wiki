package com.smc.smccloud.model.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author lyc
 * @Date 2023/6/15 10:17
 * @Descripton TODO
 */
public enum  PdDataTypeEnum {
    // 数据类型1 实体货架库存，2 过渡库位库存，3 采购到货未入，4 调拨到货未入，5退货到货未入
    ST("1","实体货架库存"),
    GD("2","过渡库位库存"),
    CG("3","采购到货未入"),
    DB("4","调拨到货未入"),
    TH("5","退货到货未入"),
    WT("6","寄售库存"),
    JY("7","集约待交货区"),
    ZHDHWR("8","组换到货未入"),;


    private String code;

    private String codeName;

    PdDataTypeEnum(String code, String codeName) {
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
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (PdDataTypeEnum item : PdDataTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item.getCodeName();
            }
        }
        return code;
    }
}
