package com.smc.smccloud.model.group;

import lombok.Data;

@Data
public class GroupRoleCondition {
    private static final long serialVersionUID = 4662957396169360340L;

    /**
     * 描述
     */
    private String description;

    /**
     * 角色ID
     */
    private String roleId;
}
