package com.smc.smccloud.model.sampleorder;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * @Author lyc
 * @Date 2022/2/5 11:05
 * @Descripton TODO
 */
@Data
public class InvoicingSalesParams {

    @NotEmpty
    private String price;

    @Valid
    @NotEmpty
    private String[] id;
}
