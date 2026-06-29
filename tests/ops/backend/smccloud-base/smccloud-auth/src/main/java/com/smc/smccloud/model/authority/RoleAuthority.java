package com.smc.smccloud.model.authority;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sales_sys_role_authority")
public class RoleAuthority implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1647841234351979442L;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private String roleId;

    /**
     * 权限ID
     */
    @TableField("AUTHORITY_ID")
    private String authorityId;
}
