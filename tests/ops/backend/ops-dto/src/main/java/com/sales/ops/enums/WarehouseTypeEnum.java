package com.sales.ops.enums;

import com.fasterxml.jackson.annotation.JsonFormat;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WarehouseTypeEnum {
    //委托在库：WT， 分库：SUB， 中心仓（大库）：MASTER
    WT("WT","服务备库"),
    FDC("SUB","分库"),
    RDC("MASTER","中心仓（大库）"),
    ;

    WarehouseTypeEnum(String houseTypeCode, String houseTypeDesc) {
        HouseTypeCode = houseTypeCode;
        HouseTypeDesc = houseTypeDesc;
    }

    private String HouseTypeCode;
    private String HouseTypeDesc;

    public String getHouseTypeCode() {
        return HouseTypeCode;
    }

    public String getHouseTypeDesc() {
        return HouseTypeDesc;
    }

}
