package com.smc.smccloud.model.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderModelInfoVO {

    private  String modelNo;

    /*
    型号名称
     */
    private  String productName;
    
    private BigDecimal eprice;
    /**
     * 最小包装数量
     */
    private  Integer minPackQty;

}
