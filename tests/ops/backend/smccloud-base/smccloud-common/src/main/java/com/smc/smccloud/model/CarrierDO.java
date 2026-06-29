package com.smc.smccloud.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author lyc
 * @Date 2022/4/7 8:57
 * @Descripton TODO
 */

@Data
@TableName("carrier")
public class CarrierDO {

    @TableField(value = "carrierId")
    private String carrierId;
    @TableField(value = "name")
    private String name;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "create_user")
    private String createUser;
    @TableField(value = "update_time")
    private Date updateTime;
    @TableField(value = "update_user")
    private String updateUser;
}
