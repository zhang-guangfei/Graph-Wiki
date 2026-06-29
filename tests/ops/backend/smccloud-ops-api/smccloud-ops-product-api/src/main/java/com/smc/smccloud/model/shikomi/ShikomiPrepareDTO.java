package com.smc.smccloud.model.shikomi;

import lombok.Data;


@Data
public class ShikomiPrepareDTO {
    private String orderNo;
    private String shikomiNo;
    private Integer quantity;
    private String supplierCode;
}
