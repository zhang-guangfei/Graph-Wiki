package com.smc.smccloud.model.group;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "sales_sys_group")
public class Group {

    @TableField(value = "ID")
    private String id;

    @TableField(value = "PID")
    private String pid;

    @TableField(value = "NAME")
    private String name;

    @TableField(value = "CODE")
    private String code;

    @TableField(value = "STATUS")
    private String status = "有效";

    @TableField(value = "CREATETIME")
    private Date createTime = new Date();

}
