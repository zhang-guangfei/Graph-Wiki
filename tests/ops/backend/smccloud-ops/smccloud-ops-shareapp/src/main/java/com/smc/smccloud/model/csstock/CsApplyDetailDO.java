package com.smc.smccloud.model.csstock;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

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
@TableName("cs_apply_detail")
public class CsApplyDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("apply_id")
    private Long applyId;

    @TableField("item_no")
    private Integer itemNo;

    @TableField("model_no")
    private String modelNo;

    /**
     * 备库数量
     */
    @Size(min = 1,message = "数量必须为大于0")
    @TableField("quantity")
    private Integer quantity;

    /**
     * 状态
     * 1编辑中
     * 2已提交申请
     * 9已删除
     */
    @TableField("status")
    private Integer status;

    @TableField( value = "create_time",  fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    @TableField(value = "update_time",  fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("answer_text")
    private String answerText;

    /**
     * 系统计算出需补货数量
     */
    @TableField("calc_qty")
    private Integer calcQty;


    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;


    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @TableField("init_Total_Qty")
    private Integer initTotalQty;

    @TableField("init_Unit_Qty")
    private Integer initUnitQty;

    @TableField("locationNo")
    private Integer locationNo;

    @TableField("eprice")
    private BigDecimal eprice;

    @TableField("spec_exp_type")
    private Integer specExpType;
}
