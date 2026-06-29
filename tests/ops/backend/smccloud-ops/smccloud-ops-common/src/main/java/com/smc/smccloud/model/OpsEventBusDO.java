package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ops_event_bus")
public class OpsEventBusDO {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField("order_id")
    private String orderId;

    @TableField("order_item")
    private String orderItem;

    @TableField("split_no")
    private Integer splitNo;

    @TableField("event_code")
    private String eventCode;

    @TableField("params")
    private String params;

    @TableField("deal_flag")
    private Integer dealFlag;

    @TableField("cre_time")
    private Date creTime;

    @TableField("creator")
    private String creator;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("modifier")
    private String modifier;

    @TableField("remark")
    private String remark;

    @TableField("version")
    private Integer version;

}
