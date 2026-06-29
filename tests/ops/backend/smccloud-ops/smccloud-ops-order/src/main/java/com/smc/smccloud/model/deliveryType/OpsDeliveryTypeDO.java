package com.smc.smccloud.model.deliveryType;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.xpath.operations.Bool;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author smc
 * @since 2023-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ops_delivery_type")
public class OpsDeliveryTypeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("delivery_priority_high")
    private String deliveryPriorityHigh;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("status")
    private Integer status;

    @TableField("delivery_priority_low")
    private String deliveryPriorityLow;

    @TableField("delivery_priority_low_code")
    private String deliveryPriorityLowCode;

    @TableField("delivery_priority_high_code")
    private String deliveryPriorityHighCode;

    @TableField("disabledSingleChange")
    private Boolean disabledSingleChange;




}
