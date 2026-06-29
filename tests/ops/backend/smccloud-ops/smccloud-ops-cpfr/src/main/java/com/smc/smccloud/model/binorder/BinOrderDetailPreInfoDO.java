package com.smc.smccloud.model.binorder;

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
 * @since 2025-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bin_order_detail_pre_info")
public class BinOrderDetailPreInfoDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("calc_id")
    private Long calcId;

    @TableField("bin_detail_id")
    private Long binDetailId;

    @TableField("order_type")
    private String orderType;

    @TableField("po_order_no")
    private String poOrderNo;

    @TableField("order_no")
    private String orderNo;

    @TableField("modelno")
    private String modelno;

    @TableField("quantity")
    private Integer quantity;

    @TableField("del_flag")
    private Integer delFlag;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_user")
    private String updateUser;



}
