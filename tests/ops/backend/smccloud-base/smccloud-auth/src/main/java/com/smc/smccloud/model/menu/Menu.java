package com.smc.smccloud.model.menu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smc.smccloud.model.login.TreeImpl;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "sales_sys_menu")
public class Menu {

    @TableField(value = "ID")
    private String id;

    @TableField(value = "NAME")
    private String name;

    @TableField(value = "TITLE")
    private String title;

    @TableField(value = "ICON")
    private String icon;

    @TableField(value = "PATH")
    private String path;

    @TableField(value = "SORT")
    private Integer sort;

    @TableField(value = "CODE")
    private String code;

    @TableField(value = "STATUS")
    private String status = "有效";

    @TableField(value = "PID")
    private String pid;

    @TableField(value = "CREATETIME")
    private Date createTime = new Date();

    @TableField(value = "COMPONENT")
    private String component;

    /**
     * hidden: 0 显示，1 隐藏
     */
    @TableField(value = "HIDDEN")
    private String hidden;

    /**
     * 是否保持存活
     */
    @TableField(value = "KEEPALIVE")
    private Boolean keepAlive;

    /**
     * 层级
     */
    @TableField(value = "LEVEL")
    private Integer level;




}
