package com.smc.smccloud.model.ordermodify;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lyc
 * @Date 2024/1/15 10:14
 * @Descripton TODO
 */
@Data
public class PurchaseModifyVO {
    private static final long serialVersionUID = 1L;

    /**
     * 变更类型(变更采购运输方式、变更指定工厂出荷日、变更供应商、删除采购单)
     */
    private String modifyType;

    /**
     * 门户任务批次号
     */
    private String batchNo;
    /**
     * 处理状态(待处理、暂不处理、不可变更、完成)
     */
    private Integer status;

    /**
     * 申请号
     */
    private String applyNo;

    /**
     * 申请表更内容
     */
    private String applyContent;

    /**
     * 原因说明,申请理由
     */
    private String applyReason;

    /**
     * 申请人
     */
    private String applyPersonNo;

    private String applyDeptNo;

    /**
     * 申请时间
     */
    private Date applyTime;


    /**
     * 完整的采购单号（包含拆分单号）
     */
    private String orderFno;

    /**
     * 主单号
     */
    private String orderNo;

    /**
     * 子项号
     */
    private Integer itemNo;

    /**
     * 拆分单号
     */
    private Integer splitItemNo;

    /**
     * 型号
     */
    private String modelno;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 营业所
     */
    private String deptNo;

    /**
     * 客户代码
     */
    private String customerNo;

    /**
     * 采购单状态
     */
    private String purchaseStatecode;

    /**
     * 采购供应商
     */
    private String supplierId;

    /**
     * 重量
     */
    //todo 查 ops_requestPurchase
    private BigDecimal netweight;

    /**
     * 运输方式
     */
    private String transType;

    /**
     * 指定工厂出荷日
     */
    private Date hopeExportDate;

    /**
     * 业务处理人
     */
    private String handler;

    /**
     * 业务处理结果
     */
    private String handleResult;

    /**
     * 业务处理时间
     */
    private Date handleTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 日本回复手配号
     */
    private String supplierOrderno;

    private Long id;

    private String userNo;

    private String remark;

    private String sourceType;
}
