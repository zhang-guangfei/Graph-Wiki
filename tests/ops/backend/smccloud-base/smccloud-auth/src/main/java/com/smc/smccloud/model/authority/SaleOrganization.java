package com.smc.smccloud.model.authority;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 组织机构
 */

@Data
@TableName("hr_organization")
public class SaleOrganization implements Serializable
{
    /**
     * id
     */
    @TableField(value = "Id")
    private String id;

    /**
     * 父Id
     */
    @TableField(value = "ParentId")
    private String pid;
    /**
     * 名称
     */
    @TableField(value = "Name")
    private String name;
    /**
     * 全称
     */
    @TableField(value = "FullName")
    private String fullName;
    /**
     * 组织机构级别
     */
    @TableField(value = "Level")
    private String level;

    @TableField(value = "Status")
    private String status;

    @TableField(value = "unitCode")
    private String unitCode;

}
