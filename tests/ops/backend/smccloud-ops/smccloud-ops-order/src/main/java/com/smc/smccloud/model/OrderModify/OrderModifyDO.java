package com.smc.smccloud.model.OrderModify;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2021-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_modify")
public class OrderModifyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务批次号
     */
    @TableField("batch_no")
    private String batchNo;

    /**
     * 申请号
     */
    @TableField("apply_no")
    private String applyNo;

    /**
     * 变更类型
     */
    @TableField("modify_type")
    private String modifyType;

    /**
     * 处理状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 完整订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 营业所
     */
    @TableField("dept_no")
    private String deptNo;

    /**
     * 客户代码
     */
    @TableField("customer_no")
    private String customerNo;

    /**
     * 转订类型
     */
    @TableField("change_type")
    private String changeType;

    /**
     * 变更后内容
     */
    @TableField("change_message")
    private String changeMessage;

    /**
     * 特价号
     */
    @TableField("sp_answerno")
    private String spAnswerno;

    /**
     * 原因说明,申请理由
     */
    @TableField("reason")
    private String reason;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 申请人
     */
    @TableField("apply_person_no")
    private String applyPersonNo;

    /**
     * 申请时间
     */
    @TableField("apply_time")
    private Date applyTime;

    /**
     * 申请部门
     */
    @TableField("apply_dept_no")
    private String applyDeptNo;

    /**
     * 业务处理人
     */
    @TableField("answer_pns")
    private String answerPns;

    /**
     * 业务处理回复内容
     */
    @TableField("answer_text")
    private String answerText;

    /**
     * 业务处理时间
     */
    @TableField("answer_time")
    private Date answerTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private Date updateTime;

    @TableField("create_time")
    private Date createTime;

    // 手配号
    @TableField("supplier_orderNo")
    private String supplierOrderNo;

    @TableField("special")
    private String special;

    // 责任方
    @TableField("responsible_party")
    private String responsibleParty;

    // 删单原因
    @TableField("cancel_reason")
    private String cancelReason;

    // 手续费率
    @TableField("processing_fee_rate")
    private BigDecimal processingFeeRate;
    //删单对策
    @TableField("cancel_strategy")
    private String cancelStrategy;
}
