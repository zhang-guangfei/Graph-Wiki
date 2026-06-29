package com.smc.smccloud.model.csstock;

import lombok.Data;

/**
 * @Author lyc
 * @Date 2022/4/10 15:37
 * @Descripton
 */
@Data
public class CsMonthBalanceConfirmRequest {

    private String agentNo;

    private String warehouseCode;

    private String monthDate;

    private String operator;

    private Integer type;
}
