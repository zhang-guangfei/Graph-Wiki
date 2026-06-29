package com.smc.smccloud.model.preaccount;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/10/11 12:58
 * @Descripton TODO
 */
@Data
public class PreOrderAccountApplyDetailVO {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 入库日期
     */
    private String roDate;

    /**
     * 入库数
     */
    private Integer roQty;

    /**
     * 可用在库
     */
    private Integer avaQty;

    /**
     * 需求日期
     */
    private String requirementDate;

    /**
     * 决算日期
     */
    private String countDate;

    /**
     * 决算状态
     */
    private Integer status;

    /**
     * 推送决算数
     */
    private Integer pushQty;

    /**
     * 确认延期日期
     */
    private Date delayDate;

    /**
     * 确认决算数，门户传值
     */
    private Integer sureAccountQty;

    /**
     * 确认延期数，门户传值
     */
    private Integer delayQty;

    /**
     * 调库号
     */
    private String transNo;

    /**
     * 调库时间
     */
    private Date transTime;

    /**
     * 清算数，实际处理的调库数量
     */
    private Integer transQty;

    /**
     * 显示该条订单申请时的LOT E价格
     */
    private BigDecimal eprice;

    /**
     * E价格*决算数
     */
    private BigDecimal eamount;

    /**
     * BIN(ALL存在1个BIN设置，就显示“是”)
     */
    private Boolean isBin;

    /**
     * 担当
     */
    private String charger;

    /**
     * 库存日志id
     */
    private String inventotyLogId;

    /**
     * 库存id项号
     */
    private Integer inventoryIdItem;

    /**
     * yyyyMMdd格式批次号
     */
    private String batchNo;

    /**
     * 最大延期日
     */
    private String maxDelayDate;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 申请项号
     */
    private String applyItemNo;

    /**
     * 决算说明
     */
    private String accountDesc;

    /**
     * 延期说明
     */
    private String delayDesc;

    /**
     * 实际延期数量
     */
    private Integer factDelayQty;

    /**
     * 决算申请号
     */
    private String accountApplyNo;

    private String modifier;

    private Long inventoryId;

    private Date createTime;

    private String modelNo;

    private Date modifyTime;

    private Long id;

    private String creator;

    private int approveQty;

}
