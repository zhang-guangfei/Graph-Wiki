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
 * @since 2024-03-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tmp_shikomi_base")
public class TmpShikomiBaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 决算号
     */
    @TableField("batch_no")
    private String batchNo;

    /**
     * shikomi号
     */
    @TableField("shikomi_no")
    private String shikomiNo;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 申请部门
     */
    @TableField("apply_dept_no")
    private String applyDeptNo;

    /**
     * 1 申请新建 2 申请中止 3 申请继续
     */
    @TableField("apply_type")
    private Integer applyType;

    /**
     * 0 未处理 1已处理
     */
    @TableField("hand_status")
    private Integer handStatus;

    @TableField("create_user")
    private String createUser;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("create_time")
    private Date createTime;

    // 计划使用月份
    @TableField("plan_use_month")
    private Date planUseMonth;

    // 客户需求计划数
    @TableField("plan_use_month_qty")
    private int planUseMonthQty;

}
