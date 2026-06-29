package com.smc.smccloud.model.authority;

import com.smc.smccloud.service.Condition;
import lombok.Data;

@Data
public class AuthorityCondition implements Condition
{
    /**
     *
     */
    private static final long serialVersionUID = -5920529260275835793L;

    /**
     * 类型: menu表示菜单，resource表示功能，common表示通用（默认绑定）
     */
    private String type;
}
