package com.smc.smccloud.model.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductPriceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 限制最低销售价格(不含税)
     */
    private BigDecimal lowestPrice;

    /**
     * 进口FOB价
     */
    private BigDecimal importFobPrice;

    /**
     * 出口FOB价
     */
    private BigDecimal exportFobPrice;

    private BigDecimal spriceRMB;

    private Boolean isDeleted;

    private Integer minQuantity;

    private BigDecimal epriceJP;

    private BigDecimal epricePra;

    private BigDecimal fobPrice;

    private BigDecimal eprice;

    private String modelNo;

    private Date updateTime;

}
