package com.smc.smccloud.model.prestock;

import lombok.Data;

/**
 * Author: B90034
 * Date: 2022-05-19 21:42
 * Description: 备库型号在库情况
 */
@Data
public class PreModelStockInfo {

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 通用可用在库（有效在库=在库数-预占数）
     */
    private int avaQty_ty;
    /**
     * 专用可用在库
     */
    private int avaQty_zy;

    /***
     * 过剩库存
     */
    private int excessQty;

    /**
     * 可用在库=在库数量-2倍月用量
     */
    private int avaQty;

    /**
     * 安全库存
     */
    private int safeQty;

    /**
     * 频率
     */
    private int freq;

    /**
     * 月用量
     */
    private int mean;

    /**
     * 可用月数
     */
    private int months;

    /**
     * 库房代码
     */
    private String warehouseCode;
    /**
     * 库存类型
     */
    private String inventoryTypeCode;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * ppl
     */
    private String ppl;

    /**
     * 项目代码
     */
    private String projectCode;

    /**
     * 集团代码
     */
    private String groupCustomerNo;

    /**
     * BINData库存类型: 1-BIN; 2-GSS; 3-非BIN; 4-客户BIN; null-型号未登录BinData信息
     */
    private Integer stockType;

    /**
     * BIN数量
     */
    private int qtyBin;

    /**
     * BIN数
     */
    private int binCell;
}
