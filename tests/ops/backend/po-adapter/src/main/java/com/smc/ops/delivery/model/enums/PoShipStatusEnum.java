package com.smc.ops.delivery.model.enums;

/**
 * @Author lyc
 * @Date 2024/2/23 9:11
 * @Descripton TODO
 */
public enum  PoShipStatusEnum {
    F("F","受注确认"),
    N("N","日本返信"),
    W("W","日本出荷发往出库仓库"),
    S("S","发货数据"),
    A("A","标注发货标签"),
    B("B","海运提单"),
    C("C","捆包/运输标签打印"),
    D("D","受注停止");


    private String code;
    private String codeName;

    PoShipStatusEnum(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
