package com.smc.smccloud.model.binorder;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2021-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bin_order_app")
public class BinOrderAppDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("model_count")
    private Integer modelCount;

    @TableField("calc_id")
    private Long calcId;

    @TableField("status")
    private Integer status;

    @TableField("approve_text")
    private String approveText;

    @TableField("approve_time")
    private Date approveTime;

    @TableField("apply_time")
    private Date applyTime;

//    @TableId(value = "app_id", type = IdType.AUTO)
    @TableId(value = "app_id")
    private Long appId;

    @TableField("model_qty")
    private Integer modelQty;

    @TableField("warehouse_code")
    private String warehouseCode;

    @TableField("approve_user")
    private String approveUser;

    @TableField("app_user")
    private String appUser;

    @TableField("apply_text")
    private String applyText;

    @TableField("stock_type")
    private Integer stockType;

    @TableField("customer_no")
    private String customerNo;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_user")
    private String updateUser;

    @TableField("property_id")
    private Long propertyId;

    @TableField("eAmount")
    private BigDecimal eamount;


}
