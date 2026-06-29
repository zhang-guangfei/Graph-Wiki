package com.smc.smccloud.model.login;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = -378060825099103175L;
    @TableId(value = "ID",type = IdType.AUTO)
    private String id;
    @TableField(value = "CREATEID")
    private String createId;

    @TableField(value = "CREATENAME")
    private String createName;


    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            shape = JsonFormat.Shape.STRING,
            timezone = "GMT+8"
    )
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @TableField(value = "CREATETIME")
    private Date createTime = new Date();

    @TableField(value = "DESCRIPTION")
    private String description;
}
