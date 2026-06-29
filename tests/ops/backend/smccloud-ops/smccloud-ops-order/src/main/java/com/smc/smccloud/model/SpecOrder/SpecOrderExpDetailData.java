package com.smc.smccloud.model.SpecOrder;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SpecOrderExpDetailData {

    private Long id;

    private String customerNo;

    private Date dlvDate;

    private String fullOrderNo;

    private String modelNo;

    private Integer quantity;

    private String caseNo;

    private BigDecimal weight;

    private String orgCurrency;

    private Date shipDate;

    private String invoiceNo;

    private BigDecimal price;

    private String volume;

    private String remark;

    private Integer boxType;
}
