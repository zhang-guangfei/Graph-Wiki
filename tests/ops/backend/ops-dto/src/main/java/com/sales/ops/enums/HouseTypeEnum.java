package com.sales.ops.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 仓库表类别
 * @auther C12961
 * @date 2021/11/18 8:09
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HouseTypeEnum {

    WT("WT","服务备库"),
    FDC("FDC","分库"),
    RDC("RDC","物流中心"),
    ;

    HouseTypeEnum(String houseTypeCode, String houseTypeDesc) {
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
