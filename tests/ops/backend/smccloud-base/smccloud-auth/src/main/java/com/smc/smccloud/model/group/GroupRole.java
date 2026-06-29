package com.smc.smccloud.model.group;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sales_sys_group_role")
public class GroupRole implements Serializable
{
    private static final long serialVersionUID = -1647841234351979442L;

    /**
     * 用户组ID
     */
    @TableField(value = "GROUP_ID")
    private String groupId;

    /**
     * 角色ID
     */
    @TableField(value = "ROLE_ID")
    private String roleId;

    /**
     * 描述
     */
    @TableField(value = "DESCRIPTION")
    private String description;
}
