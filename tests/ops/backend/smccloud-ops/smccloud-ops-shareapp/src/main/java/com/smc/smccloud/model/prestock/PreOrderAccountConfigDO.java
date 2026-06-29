package com.smc.smccloud.model.prestock;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author wuweidong
 * @create 2023/12/29 09:36
 * @description
 */
@Data
@TableName("preorder_account_config")
public class PreOrderAccountConfigDO
{
    /**ID*/
    @TableId(type = IdType.AUTO)
    @TableField("id")
    public Integer id ;

    // 优先级( 值越小优先级越高)
    @TableField("priority")
    public Integer priority ;

    // 优先级( 值越小优先级越高)
    @TableField("inventory_type_code")
    public String inventoryTypeCode ;

    @TableField(exist = false)
    private String inventoryTypeCodeName;

    // 客户代码
    @TableField("customer_no")
    public String customerNo ;
    @TableField(exist = false)
    public String customerName ;


    // PPL号
    @TableField("ppl")
    public String ppl ;

    // 项目代码
    @TableField("project_code")
    public String projectCode ;

    // 群代码
    @TableField("group_customer_no")
    public String groupCustomerNo ;

    // 是否允许延期
    @TableField("isdelay")
    public Boolean isDelay ;

    @TableField(exist = false)
    private String isDelayStr;

    // 延期MAX天数
    @TableField("delay_max_day")
    public Integer delayMaxDay ;

    @TableField("delflag")
    public Boolean delFlag ;

    @TableField("create_time")
    public Date createTime ;

    @TableField("creator")
    public String creator ;

    @TableField("modify_time")
    public Date modifyTime ;

    @TableField("modifier")
    public String modifier ;

}
