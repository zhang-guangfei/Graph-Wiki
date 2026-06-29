package com.smc.smccloud.model;

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
 * @since 2025-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("purchase_reply_push_job")
public class PurchaseReplyPushJobDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * M纳期延期,N返信变更
     */
    @TableField("business_code")
    private String businessCode;

    /**
     * 客单完整单号
     */
    @TableField("order_fno")
    private String orderFno;

    /**
     * 参数信息
     */
    @TableField("parameter")
    private String parameter;

    /**
     * 处理状态：0未处理，1已处理
     */
    @TableField("handle_status")
    private Integer handleStatus;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 处理时间
     */
    @TableField("handle_time")
    private Date handleTime;

    /**
     * 处理结果
     */
    @TableField("result")
    private String result;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


}
