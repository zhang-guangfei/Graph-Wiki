package com.smc.smccloud.model.login;

import com.smc.smccloud.service.Condition;
import lombok.Data;

@Data
public class RoleCondition implements Condition {

    private static final long serialVersionUID = -763396787663678913L;

    private String name;
}
