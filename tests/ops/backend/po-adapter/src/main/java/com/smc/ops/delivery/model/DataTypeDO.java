package com.smc.ops.delivery.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tbl_datatype")
public class DataTypeDO
{
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "code")
    private String code;
    @TableField(value = "code_name")
    private String codeName;
    @TableField(value = "parent_code")
    private String parentCode;
    @TableField(value = "class_code")
    private String classCode;
    @TableField(value = "status")
    private int status;
    @TableField(value = "remark")
    private String remark;
    @TableField(value = "ext_note1")
    private String extNote1;
    @TableField(value = "ext_note2")
    private String extNote2;
    @TableField(value = "ext_note3")
    private String extNote3;
    @TableField(value = "locked")
    private Boolean locked;
    @TableField(value = "create_time")
    private Date createTime;
    @TableField(value = "update_time")
    private Date updateTime;
    @TableField(value = "create_user")
    private String createUser;
    @TableField(value = "update_user")
    private String updateUser;
    @TableField(value = "sort")
    private int sort;
}
