package com.smc.smccloud.model.authority;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sales_sys_authority_resource")
public class AuthorityResource implements Serializable {
    private static final long serialVersionUID = -1647841234351979442L;

    /**
     * 权限ID
     */
    @TableField(value = "AUTHORITY_ID")
    private String authorityId;

    /**
     * 资源ID
     */
    @TableField(value = "RESOURCE_ID")
    private String resourceId;
}
