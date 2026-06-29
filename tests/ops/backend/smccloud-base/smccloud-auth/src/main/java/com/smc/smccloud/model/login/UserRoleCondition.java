package com.smc.smccloud.model.login;

import lombok.Data;

@Data
public class UserRoleCondition {
    /**
     * 描述
     */
    private String description;

    /**
     * 角色ID
     */
    private String roleId;
}
