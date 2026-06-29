package com.smc.smccloud.model.csstock;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("cs_apply")
public class CsApplyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long applyId;

    @TableField("customer_no")
    private String customerNo;

    @TableField("stock_code")
    private String stockCode;

    @TableField("apply_time")
    private Date applyTime;

    @TableField("total_qty")
    private Integer totalQty;

    @TableField(value="create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("status")
    private Integer status;

    @TableField("remark")
    private String remark;

    @TableField("orderNo")
    private String orderNo;


    /**
     * 专备-ppl
     */
    @TableField("ppl_no")
    private  String pplNo;

    /**
     * 专备-项目代码
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 专备-客户集团代码
     */
    @TableField("group_customer_no")
    private  String groupCustomerNo;

    /**
     * 营业所代码
     */
    @TableField("dept_no")
    private String deptNo;

    /**
     * 1-SMC提案 2-代理提案
     */
    @TableField("applyType")
    private Integer applyType;

    /**
     * 门户自定义申请号-不可为空
     */
    @TableField("capplyNo")
    private  String cApplyNo;


    /**
     * 专备-客户代码
     */
    @TableField("user_no")
    private  String userNo;

    @TableField("eamount")
    private BigDecimal eamount;
}
