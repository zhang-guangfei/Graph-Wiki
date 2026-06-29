package com.smc.smccloud.model.shikomi;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2024-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("shikomi_budget_for_sales")
public class ShikomiBudgetForSalesDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 批次号
     */
    @TableField("batch_no")
    private String batchNo;

    /**
     * 申请部署
     */
    @TableField("dept_no")
    private String deptNo;

    /**
     * 备库地点
     */
    @TableField("supplier_code")
    private String supplierCode;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * shikomi号
     */
    @TableField("shikomi_no")
    private String shikomiNo;

    /**
     * shikomi残数
     */
    @TableField("remain_qty")
    private Integer remainQty;

    /**
     * 已预约残数
     */
    @TableField("qty_order_pre")
    private Integer qtyOrderPre;

    /**
     * shikomi区分
     */
    @TableField("class_type")
    private String classType;

    /**
     * shikomi客户范围
     */
    @TableField("class_code")
    private String classCode;

    /**
     * 有订货权限的客户
     */
    @TableField("avliable_customer_no")
    private String avliableCustomerNo;

    /**
     * 归属客户
     */
    @TableField("belong_customer_no")
    private String belongCustomerNo;

    /**
     * 未订货月数
     */
    @TableField("qty_noord")
    private Integer qtyNoord;

    /**
     * 在库数量
     */
    @TableField("qty_onhand")
    private Integer qtyOnhand;

    /**
     * 在途数量
     */
    @TableField("qty_po")
    private Integer qtyPo;

    /**
     * 近6个月订货数量
     */
    @TableField("avg_ord_qty")
    private Integer avgOrdQty;

    /**
     * shikomi追加需提前(预留月数)
     */
    @TableField("reserve_month")
    private Integer reserveMonth;

    /**
     * 点检状态
     */
    @TableField("inspect_status")
    private Integer inspectStatus;

    /**
     * 点检类别
     */
    @TableField("inspect_type")
    private String inspectType;

    /**
     * 申请担当代码
     */
    @TableField("applicant_no")
    private String applicantNo;


    @TableField("supplier_code_name")
    private String supplierCodeName;

    @TableField("class_type_name")
    private String classTypeName;

    @TableField("class_code_name")
    private String classCodeName;
}
