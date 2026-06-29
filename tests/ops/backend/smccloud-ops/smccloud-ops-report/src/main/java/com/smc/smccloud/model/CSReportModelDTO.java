package com.smc.smccloud.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CSReportModelDTO {

    private String modelNo;
    private int[] qtys;
    private BigDecimal eprice;
    private BigDecimal weight;
    private int minOrdQty;
    private int userCount;
    private String maxUserNo;
    private float maxUserRate;
    private int totalQty;
    private int maxUserQty;
    private int mean;
    private boolean isRepl;
    private int replQty;
    private String oldModelNo;
    private String classCode;
    private int ordMonths;
    private int lastOrdMonths;
    private int orders;
    /**
     * 订单平均数量
     */
    private int avgOrdQty;

}