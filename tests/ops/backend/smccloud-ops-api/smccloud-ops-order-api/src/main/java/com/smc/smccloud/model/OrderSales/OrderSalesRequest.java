package com.smc.smccloud.model.OrderSales;

import lombok.Data;

@Data
public class OrderSalesRequest {
    private String tradecompanyId;
    private Integer typeCode;
    private Integer status;
    private String rorderFno;
    private String areaDeptNo;
    private String fullOrderNo;
}
