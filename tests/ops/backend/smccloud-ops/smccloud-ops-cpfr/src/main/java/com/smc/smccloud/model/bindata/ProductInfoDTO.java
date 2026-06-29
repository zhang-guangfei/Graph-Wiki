package com.smc.smccloud.model.bindata;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoDTO {
    private String modelno;
    private BigDecimal EPrice;
    private Double weight;
    private Integer stddlvday;
    private Integer minPackUnit;
    private Integer OuterBoxQuantity;
    private String OuterBoxPartNo;
    private String supplyId;
}
