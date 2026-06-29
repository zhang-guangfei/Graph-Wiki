package com.smc.smccloud.model.pd;

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
 * @since 2024-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_pd_adjust")
public class OpsPdAdjustDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 盘点批次号
     */
    @TableField("pd_batch_no")
    private String pdBatchNo;

    /**
     * 调整票号
     */
    @TableField("adjust_invoice_no")
    private String adjustInvoiceNo;

    /**
     * 调账单号
     */
    @TableField("adjust_no")
    private String adjustNo;

    @TableField("adjust_item_no")
    private int adjustItemNo;

    /**
     * 型号
     */
    @TableField("model_no")
    private String modelNo;

    /**
     * 调整数量
     */
    @TableField("adjust_qty")
    private Integer adjustQty;

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

    /**
     * PPL
     */
    @TableField("ppl")
    private String ppl;

    /**
     * 项目号
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 客户群代码
     */
    @TableField("group_customer_no")
    private String groupCustomerNo;

    /**
     * 情报号
     */
    @TableField("sales_info_no")
    private String salesInfoNo;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    /**
     * 确认人
     */
    @TableField("confirm_user")
    private String confirmUser;

    /**
     * 确认时间
     */
    @TableField("confirm_time")
    private Date confirmTime;

    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("inventory_id")
    private Long inventoryId;

    @TableField(exist = false)
    private String createTimeStr;

    @TableField(exist = false)
    private String confirmTimeStr;

    @TableField("hand_status")
    private int handStatus;

    @TableField("data_source")
    private int dataSource;

    @TableField(exist = false)
    private String handStatusName;

    @TableField("remark")
    private String remark;
}
