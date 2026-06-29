package com.smc.smccloud.model.invoice;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author edp02 @Date 2022/7/1 11:24
 */
@Data
public class PoInvoiceDetailReportVO {

    private String suppliercode;

    private String invoiceno;

    private String orderno;

    private String overseainvoiceno;

    private Date costtime;

    private BigDecimal amount;

    private BigDecimal amountrmb;

    private BigDecimal customsfee;

    private BigDecimal transfee;
    private BigDecimal excisetax;

    private BigDecimal otherfee;

    private BigDecimal exchangerate;

    private String currencycode;

    private BigDecimal amounttotal;

    private Date invoicedate;

    private Integer payday;
}
