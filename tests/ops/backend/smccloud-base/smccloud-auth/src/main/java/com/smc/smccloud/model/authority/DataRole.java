package com.smc.smccloud.model.authority;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "sales_sys_data_role")
public class DataRole
{

    private static final long serialVersionUID = 4326470535844403382L;

    @TableId(value = "ID",type = IdType.AUTO)
    private String id;

    /**
     * 角色ID
     */
    @TableField(value = "ROLE_ID")
    private String roleId;

    /**
     * 暂定为部门编码，6位
     */
    @TableField(value = "OBJECT_ID")
    private String objectId;

    /**
     * 读权限
     */
    @TableField(value = "READ_PERMISSION")
    private boolean readPermission;

    /**
     * 编辑权限
     */
    @TableField(value = "EDIT_PERMISSION")
    private boolean editPermission;

    /**
     * 删除权限
     */
    @TableField(value = "DELETE_PERMISSION")
    private boolean deletePermission;

    @TableField(value = "CREATEID")
    private String createId;

    @TableField(value = "CREATENAME")
    private String createName;

    @TableField(value = "CREATETIME")
    private Date createTime = new Date();

    @TableField(value = "DESCRIPTION")
    private String description;
}
