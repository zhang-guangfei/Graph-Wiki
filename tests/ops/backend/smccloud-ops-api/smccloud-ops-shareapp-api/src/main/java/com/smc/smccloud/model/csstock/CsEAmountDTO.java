package com.smc.smccloud.model.csstock;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author edp04
 * @title: CsEAmountDTO
 * @date 2022/07/07 11:49
 */
@Data
public class CsEAmountDTO {

    private BigDecimal onhandEAmt;

    private BigDecimal ordIngEAmt;

    private BigDecimal transEAmt;

    private BigDecimal canUseEAmt;

    private BigDecimal maxEAmt;

}
