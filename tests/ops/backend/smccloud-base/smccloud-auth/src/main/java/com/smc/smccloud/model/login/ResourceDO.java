package com.smc.smccloud.model.login;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("sales_sys_resource")
public class ResourceDO {

    @TableField("ID")
    private String id;
    @TableField("NAME")
    private String name;
    @TableField("PATTERN")
    private String pattern;
    @TableField("CODE")
    private String code;
    @TableField("STATUS")
    private String status;
    @TableField("PID")
    private String pid;
    @TableField("CREATETIME")
    private String createTime;

}
