package com.smc.smccloud.model.enums;

/**
 * @Author lyc
 * @Date 2023/8/17 16:48
 * @Descripton TODO
 */
public enum  PdUploadFilePathEnum {
    xp("xpBill","现品票"),
    hjpdkbp("hjpdkbpBill","货架盘点空白票"),
    dhwrkbp("dhwrkbpBill","到货未入空白票"),
    lh("lhBill","立会票"),
    sj("sjBill","数据票"),
    dhwrqdp("dhwrqdpBill","到货未入清单票"),
    wt("wtBill","寄售库存盘点票");

    private String code;
    private String codeName;

    PdUploadFilePathEnum(String code, String codeName) {
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
}
