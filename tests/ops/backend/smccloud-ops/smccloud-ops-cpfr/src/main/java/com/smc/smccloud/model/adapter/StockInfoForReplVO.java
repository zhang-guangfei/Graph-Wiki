package com.smc.smccloud.model.adapter;

import lombok.Data;

import java.util.List;

/*
型号的在库信息for补库
* */
@Data
public class StockInfoForReplVO {

    private  String modelNo;

    /**
     * 物流中心和分仓在库
     */
    private List<StockInfoByWarehouseForReplVO> warehouseStocks;

    /**
     * 全国所有在库
     */
    private  Integer allAvaQty;
    /**
     * 全国所有在途
     */
    private  Integer allTransQty;

    /**
     * 全国通用在库可用数量
     */
    private  Integer tyAvaQty;

    /**
     * 全国通用在库在途数量
     */
    private  Integer tyTransQty;


    /**
     * 专用在库可用数量
     */
    private  Integer specAvaQty;

    /**
     * 专用在途数量
     */
    private  Integer specTransQty;

    /**
     * 全国月均（12个月）
     */
    private  Integer allAvgQty;

    /**
     * 全国订货频率（12个月）
     */
    private  Integer allFreq;

    /**
     * 全国可用月数
     */
    //private  Integer canUseMonths;

    /**
     * 专用月均
     */
    private  Integer specAvgQty;

    /**
     * 专用订货频率（12个月）
     */
    private  Integer specFreq;

    /**
     * 专用可用月数
     */
    private  Integer specCanUseMonths;

    /**
     * shikomi可用数
     */
    private Integer shikomiQty;


    /**
     * 是BIN型号 物流中心任意一个仓为bin
     */
    private  Boolean isBinModel;



}
