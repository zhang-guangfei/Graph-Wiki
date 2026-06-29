package com.smc.smccloud.model.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: B90034
 * Date: 2022-03-03 17:09
 * Description:
 */
@Data
public class ProductInfoVO {

    private String modelNo;

    private String eCode;

    private String chineseName;

    private String englishName;

    private Integer minPackUnit;

    private String ePrice;

    private BigDecimal lotPrice;

    private Integer designTypeId;
}
