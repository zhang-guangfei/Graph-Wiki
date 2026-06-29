package com.smc.smccloud.model.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author edp04
 * @title: ProductPriceDTO
 * @date 2022/05/11 15:00
 */
@Data
public class ProductPriceDTO {

    private BigDecimal eprice;

    private Integer minPackUnit;

    private BigDecimal netWeight;
}
