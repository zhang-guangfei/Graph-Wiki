package com.smc.smccloud.model.pd;

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
 * @since 2024-08-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_pd_no_adjust")
public class OpsPdNoAdjustDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 盘点批次号
     */
    @TableField("pd_batch_no")
    private String pdBatchNo;

    /**
     * 非调整库存ID
     */
    @TableField("inventory_id")
    private Long inventoryId;

    /**
     * 非调整库存型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 库存类型
     */
    @TableField("inventory_type")
    private String inventoryType;

    /**
     * 客户代码
     */
    @TableField("customer_no")
    private String customerNo;

    @TableField("ppl")
    private String ppl;

    /**
     * 项目号
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 项目群
     */
    @TableField("group_customer_no")
    private String groupCustomerNo;

    /**
     * 情报号
     */
    @TableField("sales_info_no")
    private String salesInfoNo;

    @TableField("is_valid")
    private Integer isValid;

    @TableField("create_time")
    private String createTime;

    @TableField("create_user")
    private String createUser;


}
