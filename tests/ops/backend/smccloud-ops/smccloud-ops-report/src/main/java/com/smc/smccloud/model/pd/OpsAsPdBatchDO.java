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
 * @since 2023-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_as_pd_batch")
public class OpsAsPdBatchDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 盘点批次号
     */
    @TableId(value = "pd_batch_no")
    private String pdBatchNo;

    /**
     * 0 盘点数据准备，1盘点开始（点击生成盘点票一旦被点击则盘点状态为【盘点开始】），2 实盘数据录入（盘点票录入一旦被点击则盘点状态为【实盘数据录入】），3实盘数据确认（盘点差异导出按钮一旦被点击则盘点状态为【实盘数据确认】），4 盘点数据已确认（盘点调整按钮一旦被点击则盘点状态为【盘点数据已确认】）
     */
    @TableField("pd_state")
    private String pdState;

    /**
     * 盘点开始时间
     */
    @TableField("pd_start_time")
    private Date pdStartTime;

    /**
     * 点击盘点调整按钮时间
     */
    @TableField("pd_data_end_time")
    private Date pdDataEndTime;

    /**
     * 0已过期，1激活，2作废
     */
    @TableField("is_active")
    private String isActive;

    @TableField("create_user")
    private String createUser;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_user")
    private String updateUser;

    @TableField("update_time")
    private Date updateTime;

    @TableField("pd_data_type_gd")
    private String pdDataTypeGd;

    @TableField("pd_bill_type_gd")
    private String pdBillTypeGd;

    @TableField("pd_data_type_dhwr")
    private String pdDataTypeDhwr;

    @TableField("pd_bill_type_dhwr")
    private String pdBillTypeDhwr;

    @TableField("pd_data_type_jy")
    private String pdDataTypeJy;

    @TableField("pd_bill_type_jy")
    private String pdBillTypeJy;

}
