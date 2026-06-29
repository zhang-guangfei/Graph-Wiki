package com.smc.smccloud.model.salesData;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author lyc
 * @Date 2022/10/28 10:38
 * @Descripton TODO
 */
@Data
public class EDisvStatisticsVO {

    // 交易主体
    private String tradeCompanyId;
    // 不含税额
    private BigDecimal ntaxAmount;
    // E销售额
    private BigDecimal eamount;
    // E差益率
    private BigDecimal edisvRate;
    // BNS金额
    private BigDecimal bnsAmount;
    // 修正前E差益率
    private BigDecimal beforeUpEdisvRate;

}
