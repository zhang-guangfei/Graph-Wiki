package com.smc.smccloud.model.sampleorder;

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
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sampleOrder_detail")
public class SampleorderDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * samplyOrder_apply.id
     */
    @TableField("apply_id")
    private Long applyId;

    /**
     * 项号
     */
    @TableField("item_no")
    private Integer itemNo;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 单价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 客户型号
     */
    @TableField("cmodel_no")
    private String cmodelNo;

    /**
     * 希望货期
     */
    @TableField("dlv_date")
    private Date dlvDate;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_user")
    private String updateUser;

    /**
     * 结转状态
     */
    @TableField("cost_status")
    private Integer costStatus;

    @TableField("cost_time")
    private Date costTime;

    @TableField("invoice_no")
    private String invoiceNo;

    @TableField("invoice_type")
    private String invoiceType;

    // 客户类型  0 直销 1 代销 2 经销
    @TableField("customer_type")
    private String customerType;

    @TableField("spec_mark")
    private String specMark;

    @TableField("industryId")
    private String industryId;
}
