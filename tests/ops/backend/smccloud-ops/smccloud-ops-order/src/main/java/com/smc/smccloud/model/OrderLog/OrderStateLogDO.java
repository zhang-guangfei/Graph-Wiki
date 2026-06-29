package com.smc.smccloud.model.OrderLog;

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
 * @since 2022-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_state_log")
public class OrderStateLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("user_no")
    private String userNo;

    @TableField("description")
    private String description;

    @TableField("data")
    private String data;

    @TableField("create_user")
    private String createUser;

    @TableField("order_item")
    private Integer orderItem;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("order_id")
    private String orderId;

    @TableField("order_date")
    private Date orderDate;

    @TableField("customer_no")
    private String customerNo;

    @TableField("split_no")
    private Integer splitNo;

    @TableField("order_no")
    private String orderNo;

    @TableField("create_time")
    private Date createTime;

    @TableField("order_type")
    private String orderType;


}
