package com.smc.smccloud.model.binorder;

import lombok.Data;

@Data
public class BinOrderCalcRequestVO {
    private Long calcId;
    private Integer calcType;
    private String warehouseCode;
    private  String custoemrNo;
    private  Long propertyId;
    private Boolean onlyCaculate;
    private  String optUser;
}