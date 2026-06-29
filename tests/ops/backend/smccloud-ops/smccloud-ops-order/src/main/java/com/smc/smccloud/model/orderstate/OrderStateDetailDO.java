package com.smc.smccloud.model.orderstate;

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
 * @since 2021-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_statedetail")
public class OrderStateDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(value = "date_type")
    private Integer dateType;
    /**
     * 状态类型
     */
    @TableField(value = "state_code")
    private Integer stateCode;

    /**
     * 变更次数
     */
    @TableField("change_times")
    private Integer changeTimes;

    /**
     * 状态说明
     */
    @TableField("state_des")
    private String stateDes;

    /**
     * 状态的最早时间
     */
    @TableField("mindate")
    private Date minDate;

    /**
     * 状态的最晚时间
     */
    @TableField("maxdate")
    private Date maxDate;

    /**
     * 状态来源
     */
    @TableField("provider")
    private String provider;

    @TableField("delayday")
    private Integer delayDay;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("remark")
    private String remark;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("state_date")
    private Date stateDate;

    @TableField("order_no")
    private String orderNo;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value = "firstdate")
    private Date firstDate;

    @TableField(value = "date_name")
    private String dateName;


}
