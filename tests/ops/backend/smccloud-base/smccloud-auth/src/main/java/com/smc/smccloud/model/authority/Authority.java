package com.smc.smccloud.model.authority;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sales_sys_authority")
public class Authority implements Serializable
{
    @TableId(value = "ID")
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
    /**
     * 类型: menu表示菜单，resource表示功能，common表示通用（默认绑定）
     */
    @TableField(value = "TYPE")
    private String type;
}
