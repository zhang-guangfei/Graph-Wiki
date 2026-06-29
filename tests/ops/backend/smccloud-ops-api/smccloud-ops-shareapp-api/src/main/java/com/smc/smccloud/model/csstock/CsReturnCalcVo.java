package com.smc.smccloud.model.csstock;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 委托在库退货计算
 *
 * @author wsf
 * @version 1.0
 * @date 2022/1/22 14:55
 */
@Data
public class CsReturnCalcVo {
    private Long id;
    private String agentNo;
    private String warehouseCode;
    private String modelNo;
    /**
     * 退货数
     */
    private Integer returnQty;
    /**
     * 超期数量
     */
    private Integer overTimeQty;
    /**
     * 超过设定数
     */
    private Integer overCttQty;
    /**
     * 超过天数
     */
    private Integer overDay;

    /**
     * 备库设置数量
     */
    private Integer initStockQty;
    private Integer qtyOnhand;
    private Integer returningQty;

    private Integer qtyprepare;

    private Date updateTime;

    private BigDecimal ePrice;

    private Integer isBinModel;

    private Date lastImpDate;

    private Integer customers;

    private Integer orders;

    private Integer applyType;

    private String deptNo;

    private Date minShipDate;
}
