package com.smc.smccloud.core.security.service;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sales_sys_resource")
public class Resource implements Serializable {

    private static final long serialVersionUID = 8618846685650967726L;

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
     * 模式
     */
    @TableField(value = "PATTERN")
    private String pattern;
}
