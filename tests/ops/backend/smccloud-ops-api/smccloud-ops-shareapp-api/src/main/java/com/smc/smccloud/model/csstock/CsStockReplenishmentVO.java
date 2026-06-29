package com.smc.smccloud.model.csstock;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 补货信息
 *
 * @author wsf
 * @version 1.0
 * @date 2021/11/8 18:19
 */
@Data
public class CsStockReplenishmentVO  {

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 库房代码
     */
    private String stockCode;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 设定备库数值
     */
    private Integer initStockQty;

    /**
     * 单位规格数量
     */
    private Integer initUnitQty;

    /**
     * 在途中数量
     */
    private Integer ordingQty;

    /**
     * 当前库存数
     */
    private Integer onhandQty;

    /**
     * 补货数量
     */
    private Integer replQty;

    /**
     * 在途中数量
     */
    private Integer transQty;

    /**
     * 补货后合计（在库数+补货数 +在途）
     */
    private Integer totalQty;

    /**
     * E价格
     */
    private BigDecimal eprice;

    /**
     * 申请中数量
     */
    private Integer applyQty;

    /**
     * 在库可用
     */
    private Integer onhandCanUseQty;
}
