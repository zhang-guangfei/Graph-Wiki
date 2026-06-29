package com.smc.smccloud.model.binorder;

import com.smc.smccloud.core.model.page.BaseQuery;

import lombok.Data;

@Data
public class BinOrderCalcQueryDTO extends BaseQuery {
    
    private Long calcId;
    private Long appId;
    private String modelNo;
    private Boolean onlyHasOrderQty;
    private String moreContent;
    private Integer status;
    private String warehouseCode;
    private String calcType;
    private String orderNo;
//    private String customerNo;
//    private Long propertyID;

}
