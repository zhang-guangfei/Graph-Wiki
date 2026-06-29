package com.smc.smccloud.model.authority;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sales_sys_authority_menu")
public class AuthorityMenu {
    private static final long serialVersionUID = -1647841234351979442L;

    /**
     * 权限ID
     */
    @TableField(value = "AUTHORITY_ID")
    private String authorityId;

    /**
     * 资源ID
     */
    @TableField(value = "MENU_ID")
    private String menuId;
}
