package com.smc.smccloud.model.binorder;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BinOrderInventoryInfoVO {

    private String modelNo;

    /**
     * 通用可用在库
     */
    private Integer avaQty_ty;
    /**
     * 专用可用在库
     */
    private Integer avaQty_zy;

    /***
     * 过剩库存
     */
    private Integer excessQty;

    /**
     * 频率
     */
    private Integer freq;

    /**
     * 月用量
     */
    private Integer mean;

    /**
     * 可用月数
     */
    private Integer months;

    /**
     * 库房代码
     */
    private String warehouseCode;

    /**
     * BINData库存类型: 1-BIN; 2-GSS; 3-非BIN; 4-客户BIN; null-型号未登录BinData信息
     */
    private Integer stockType;

    /**
     * BIN数量
     */
    private Integer qtyBin;

    /**
     * BIN数
     */
    private Integer binCell;

    /**
     * 是否中心仓
     */
    private Boolean isCenterWareHouse;

    /**
     * 是否bin
     */
    private Boolean isBin;

    /**
     * 安全在库量
     */
    private int safeQty;
}
