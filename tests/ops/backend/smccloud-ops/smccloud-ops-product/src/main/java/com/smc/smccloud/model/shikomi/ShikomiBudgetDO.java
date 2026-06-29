package com.smc.smccloud.model.shikomi;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2024-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("shikomi_budget")
public class ShikomiBudgetDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 营业所
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
     * shikomo号
     */
    @TableField("shikomi_no")
    private String shikomiNo;

    /**
     * 申请号
     */
    @TableField("apply_no")
    private String applyNo;

    /**
     * 申请时间
     */
    @TableField("apply_time")
    private Date applyTime;

    /**
     * 申请数量
     */
    @TableField("apply_qty")
    private Integer applyQty;

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
     * 行业代码
     */
    @TableField("ind_code")
    private String indCode;

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

    /**
     * 申请担当
     */
    @TableField("application_name")
    private String applicationName;

    /**
     * 营业所点检确认 1.追加2.中止3.继续
     */
    @TableField("inspect_confirm_type")
    private Integer inspectConfirmType;

    /**
     * 营业所点检回复
     */
    @TableField("inspect_answer_text")
    private String inspectAnswerText;

    /**
     * 最晚消耗日期
     */
    @TableField("last_use_date")
    private Date lastUseDate;

    /**
     * 负责人审批备注
     */
    @TableField("remark")
    private String remark;

    @TableField("update_time")
    private Date updateTime;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("batch_no")
    private String batchNo;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;


}
