package com.smc.smccloud.model.aboutsms;

import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2023/12/26 11:51
 * @Descripton TODO
 */
@Data
public class OpsDlvPromptingVO {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 采购单号
     */
    private String poOrderFno;

    /**
     * 采购单主单号
     */
    private String poOrderNo;

    /**
     * 采购项号
     */
    private Integer poItemNo;

    /**
     * 采购拆分项号
     */
    private Integer poSplitItemNo;

    /**
     * 完整客单号
     */
    private String rorderFno;

    /**
     * 客单主单号
     */
    private String rorderNo;

    /**
     * 客单项号
     */
    private Integer rorderItemNo;

    /**
     * 客单拆分项号
     */
    private Integer rorderSplitItemNo;

    /**
     * 型号
     */
    private String modelNo;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 手配号,即供应商接单号
     */
    private String supplierOrderNo;

    /**
     * 供应商代码
     */
    private String supplierId;

    /**
     * 供应商名称
     */
    private String supplierCodeName;

    /**
     * 供应方式(出库区分)
     */
    private String produceFactory;

    /**
     * 客户
     */
    private String customerNo;

    /**
     * 用户
     */
    private String userNo;

    /**
     * 最终用户
     */
    private String endUser;

    /**
     * 指定出荷日
     */
    private Date hopeExportDate;

    /**
     * 变更前返信
     */
    private String replyDateModBefore;

    /**
     * 变更后返信
     */
    private String replyDateMod;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单类型名称
     */
    private String orderTypeName;

    private Date createTime;

    private String createUser;

    // 实际出荷日
    private Date facExpdate;

    // 客户货期
    private Date hopeDeliveryDate;
}
