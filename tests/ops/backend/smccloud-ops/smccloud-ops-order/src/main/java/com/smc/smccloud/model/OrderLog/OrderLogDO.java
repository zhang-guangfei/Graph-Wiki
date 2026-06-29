package com.smc.smccloud.model.OrderLog;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2021-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("[ops_log].[dbo].[order_log]")
public class OrderLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    @TableField("orderno")
    private String orderNo;

    /**
     * 操作类型
     */
    @TableField("opt_type")
    private Integer optType;

    /**
     * 操作说明
     */
    @TableField("description")
    private String description;

    /**
     * 操作时间
     */
    @TableField("opt_time")
    private Date optTime;

    /**
     * 操作人姓名
     */
    @TableField("opt_user_name")
    private String optUserName;

    /**
     * 操作人id
     */
    @TableField("opt_user_id")
    private String optUserId;

    /**
     * 操作者IP
     */
    @TableField("ip")
    private String ip;

    /**
     * 写入时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;


}
