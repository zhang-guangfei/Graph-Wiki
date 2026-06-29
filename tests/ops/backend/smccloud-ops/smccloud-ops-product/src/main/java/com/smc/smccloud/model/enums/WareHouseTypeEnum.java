package com.smc.smccloud.model.enums;

/**
 * @Author lyc
 * @Date 2022/11/18 13:36
 * @Descripton TODO
 */
public enum WareHouseTypeEnum {
    SUB("SUB","分库"),
    MASTER("MASTER","中心仓(大库)"),
    WT("WT","服务备库"),
    SUPPLIER("SUPPLIER","供应商库存")
    ;
    private String code;
    private String codeName;

    WareHouseTypeEnum(String code, String codeName) {
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
