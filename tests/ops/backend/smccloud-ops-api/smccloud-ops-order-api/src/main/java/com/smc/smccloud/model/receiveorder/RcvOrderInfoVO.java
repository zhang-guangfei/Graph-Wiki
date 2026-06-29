package com.smc.smccloud.model.receiveorder;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RcvOrderInfoVO {

    private String modelNo;

    private String stockCode;

    /**
     * 数量
     */
    private Integer quantity;

    private BigDecimal price;

    private BigDecimal amount;

    /**
     * 已退货数量
     */
    private Integer returnedQty;

    private Integer status;

    //private String invoiceNo;发票号

    //private Date invoiceDate;开票日期


    private String rorderNo;
    private Integer rorderItemNo;
    private String customerNo;
    private String customerName;
    private String userNo;
    private String userName;

    private String rorderFno;
    /**
     *
     */
    private String statusName;

    /**
     * 货期状态--order_state
     */
    private String statusText;

    private BigDecimal eprice;

    private Integer expQty;
}