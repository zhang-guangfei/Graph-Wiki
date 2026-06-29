package com.smc.smccloud.model.login;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sales_sys_user_role")
public class UserRole implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1647841234351979442L;
    /**
     * 用户ID
     */
    @TableField(value = "USER_ID")
    private String userId;

    /**
     * 角色ID
     */

    private String roleId;

    /**
     * 描述
     */
    private String description;
}
