package com.smc.smccloud.model.shikomi;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author edp04
 * @title: ShikomiModelDO
 * @date 2022/04/28 16:58
 */
@Data
@TableName("shikomi_model")
public class ShikomiModelDO {

    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @TableField(value = "shikomino")
    private String shikomino;

    @TableField(value = "modelno")
    private String modelno;

    @TableField(value = "serialModel")
    private String serialModel;

    @TableField(value = "UpdateTime")
    private Date updateTime;

    @TableField(value = "CreateTime")
    private Date createTime;

    @TableField(value = "CreateUser")
    private String createUser;

    @TableField(value = "UpdateUser")
    private String updateUser;

    @TableField(value = "modelType")
    private String modelType;

    @TableField(value = "mainModelFlag")
    private Integer mainModelFlag;
}
